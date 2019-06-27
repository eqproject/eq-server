package org.eq.modules.bc.task;

import com.alibaba.fastjson.JSON;
import io.bumo.encryption.crypto.keystore.KeyStore;
import io.bumo.encryption.crypto.keystore.entity.KeyStoreEty;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.model.response.TransactionSubmitResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eq.modules.bc.biz.InitiatorPoolBiz;
import org.eq.modules.bc.common.ConstantsUtil;
import org.eq.modules.bc.common.util.DecimalCalculateUtil;
import org.eq.modules.bc.common.util.Tools;
import org.eq.modules.bc.dao.InitiatorAccMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BlockchainTx;
import org.eq.modules.bc.entity.InitiatorAcc;
import org.eq.modules.bc.entity.InitiatorAccExample;
import org.eq.modules.bc.enums.BcStatusEnum;
import org.eq.modules.bc.external.bc.BlockChainManager;
import org.eq.modules.bc.external.bc.req.BatchSubmitTxReq;
import org.eq.modules.bc.external.bc.req.BcTransferReq;
import org.eq.modules.bc.external.bc.req.SignEntity;
import org.eq.modules.bc.external.bc.resp.BlobDataResp;
import org.eq.modules.bc.init.KeyStoreManager;
import org.eq.modules.bc.service.BcTxService;
import org.eq.modules.bc.service.BlockChainTxService;
import org.eq.modules.enums.BcAccountTypeEnum;
import org.eq.modules.wallet.dao.UserWalletMapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
import org.eq.modules.wallet.util.WalletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HandleBcTxTransferJob {

	private final BcTxService bcTxService;
	private final BlockChainTxService blockChainTxService;
	private final InitiatorPoolBiz initiatorPoolBiz;
	private final BlockChainManager blockChainManager;
	private final UserWalletMapper userWalletMapper;
    private final InitiatorAccMapper initiatorAccMapper;

	@Scheduled(cron = "0 0/5 * * * ?")
	public void execute(){
		//是否设置keyStore密码
		boolean keyStorePwdFlag = Tools.isNull(KeyStoreManager.getKeyStorePwd());
		if(!keyStorePwdFlag){
			try {
				log.info("send bc tx start...");
				//查询发送红包区块链未转账的记录
				List<BcTxRecord> bcTxRecordList = bcTxService.queryBcTxRecord4Init();
				if(!Tools.isNullByList(bcTxRecordList)) {
					//组装参数
					BatchSubmitTxReq submitTxReq = bulidSumTxReq(bcTxRecordList);
					//更新交易状态为已提交
					bcTxService.updateBcTxRecord4BcProcess(bcTxRecordList, submitTxReq.getHash());
					// 提交到blokchain
					TransactionSubmitResponse bcResponse = blockChainManager.batchSubmitTx(submitTxReq);
					log.info("提交交易返回:"+JSON.toJSONString(bcResponse));
					if(bcResponse.getErrorCode() .equals(ConstantsUtil.BC_SUCCESS) ){
						//记录到区跨链交易表
						BlockchainTx blockchainTx = new BlockchainTx();
						blockchainTx.setTxBlob(submitTxReq.getBlob());
						blockchainTx.setTxHash(submitTxReq.getHash());
						blockchainTx.setTxInitiatorAddress(submitTxReq.getInitiator());
						blockchainTx.setTxStatus(BcStatusEnum.INIT.getCode());
						blockChainTxService.addBcTx(blockchainTx);
					}else{
						//记录到异常表
						blockChainTxService.addBcTxException(submitTxReq.getHash());
						log.error("提交交易到blockchain异常"+bcResponse.getErrorCode()+" Desc:"+bcResponse.getErrorCode());
					}
				}
				log.info("send bc tx end...");
			} catch (Exception e) {
				log.error("交易上链异常:" + e);
			}
		}else{
			log.error("keyStore密码未设置 ");
		}
	}


	private BatchSubmitTxReq bulidSumTxReq(List<BcTxRecord> bcTxRecordList) throws Exception {
		List<BcTransferReq> batchTransferList = new ArrayList<>();
		
		List<String> fromAddressList = new ArrayList<>();
		//用户集合
		HashSet<String> userSingerSet = new HashSet<>();
		for(BcTxRecord bcTxRecord : bcTxRecordList){
			//检查是否激活
            if (!bcTxRecord.getBizType().equals(BcAccountTypeEnum.ACTIVITY.getCode())
                    && !blockChainManager.checkActivated(bcTxRecord.getToAddress())) {
                log.info("账户未激活:" + bcTxRecord.getToAddress());
                continue;
            }
			if(!blockChainManager.checkActivated(bcTxRecord.getFromAddress())){
				log.info("账户未激活:"+bcTxRecord.getFromAddress());
				continue;
			}
			//资产的精度，暂时用不到
			//Integer assetDecimal = bcTxRecord.getAssetDecimal();
			String assetIssuer= bcTxRecord.getAssetIssuer();
			String fromAddress = bcTxRecord.getFromAddress();
			fromAddressList.add(fromAddress);
			userSingerSet.add(fromAddress);

			long bcAmount = 0;
			if(bcTxRecord.getBizType().equals(BcAccountTypeEnum.ACTIVITY.getCode())){
				bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long(bcTxRecord.getTransferAmount()+"", 8);//资产数量
			}

			BcTransferReq bcTransferReq = new BcTransferReq();
			bcTransferReq.setAmount(bcAmount);
			bcTransferReq.setFromAddress(fromAddress);
			bcTransferReq.setToAddress(bcTxRecord.getToAddress());
			bcTransferReq.setIssuer(assetIssuer);
			bcTransferReq.setMetadata(bcTxRecord.getOptMetadata());
			bcTransferReq.setInput(bcTxRecord.getInput());
			bcTransferReq.setTxType(bcTxRecord.getTxType());
			batchTransferList.add(bcTransferReq);
		}
		//提交人地址
		String initiatorAddress = initiatorPoolBiz.getInitiator();
		InitiatorAcc initiatorAcc = initiatorPoolBiz.getInitiatorInfo(initiatorAddress);
		
		BlobDataResp blobDataResp = blockChainManager.getBatchOptTransferBlob(batchTransferList, initiatorAddress);
		//log.info("生成blob:"+JSON.toJSONString(blobDataResp));

		//生成提交人签名
		List<SignEntity> listSigner = initiatorSignBlob(blobDataResp,initiatorAcc);

		//普通账户签名
		listSigner = userSignBlob(blobDataResp,listSigner,userSingerSet);


		BatchSubmitTxReq submitTxReq = new BatchSubmitTxReq();
		submitTxReq.setBlob(blobDataResp.getBlob());
		submitTxReq.setListSigner(listSigner);
		submitTxReq.setHash(blobDataResp.getHash());
		submitTxReq.setInitiator(initiatorAddress);
		return submitTxReq;
	}

	private Map<String,String> getKeystore(String fromAddress){
	    Map<String,String> map = new HashMap<>(2);

	    UserWalletExample example = new UserWalletExample();
		example.or().andAddressEqualTo(fromAddress);
		List<UserWallet> list = userWalletMapper.selectByExample(example);
		if(list!=null && !list.isEmpty()){
            UserWallet o =  list.get(0);
            map.put("keystore",o.getKeyStore());
            map.put("publicKey",o.getPublicKey());
            return map;
		}

        InitiatorAccExample example1 = new InitiatorAccExample();
        example1.or().andAddressEqualTo(fromAddress);
        List<InitiatorAcc> list1 = initiatorAccMapper.selectByExample(example1);
        if(list1!=null && !list1.isEmpty()){
            InitiatorAcc o =  list1.get(0);
            map.put("keystore",o.getKeyStore());
            map.put("publicKey",o.getPublicKey());
        }
        return map;
	}


	private List<SignEntity> initiatorSignBlob(BlobDataResp blobDataResp, InitiatorAcc initiatorAcc) throws Exception {
		List<SignEntity> listSigner = new ArrayList<>();
		String privateStr = KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(initiatorAcc.getKeyStore(), KeyStoreEty.class));
		PrivateKey privateObj = new PrivateKey(privateStr);
		byte[] signByte = privateObj.sign(HexFormat.hexStringToBytes(blobDataResp.getBlob()));
		String signBlob = HexFormat.byteToHex(signByte);
		SignEntity entity = new SignEntity();
		entity.setPublicKey(initiatorAcc.getPublicKey());
		entity.setSignBlob(signBlob);
		listSigner.add(entity);
		return listSigner;
	}
	
	private List<SignEntity> middleAccSignBlob(BlobDataResp blobDataResp,List<SignEntity> listSigner,HashSet<String> middleSetList) throws Exception {
		if(!Tools.isNullByList(middleSetList)){
			for(String middleAddress : middleSetList){
				InitiatorAcc middleAcc = initiatorPoolBiz.getInitiatorInfo(middleAddress);
				String privateStr = KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(middleAcc.getKeyStore(), KeyStoreEty.class));
				PrivateKey privateObj = new PrivateKey(privateStr);
				byte[] signByte = privateObj.sign(HexFormat.hexStringToBytes(blobDataResp.getBlob()));
				String signBlob = HexFormat.byteToHex(signByte);
				SignEntity entity = new SignEntity();
				entity.setPublicKey(middleAcc.getPublicKey());
				entity.setSignBlob(signBlob);
				listSigner.add(entity);
			}
		}
		return listSigner;
	}
	private List<SignEntity> userSignBlob(BlobDataResp blobDataResp,List<SignEntity> listSigner,HashSet<String> userSetList) throws Exception {
		userSetList.forEach(o->{
            Map<String,String> map = getKeystore(o);
			String privateKey = WalletUtil.getPrivateKey(map.get("keystore"));

			PrivateKey privateObj = new PrivateKey(privateKey);
			byte[] signByte = privateObj.sign(HexFormat.hexStringToBytes(blobDataResp.getBlob()));
			String signBlob = HexFormat.byteToHex(signByte);

			SignEntity entity = new SignEntity();
			entity.setPublicKey(map.get("publicKey"));
			entity.setSignBlob(signBlob);

			listSigner.add(entity);
		});

		return listSigner;
	}

	public static void main(String[] args) {
		long bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long("0.01", 2);
		System.out.println(bcAmount * 100000000 /100);
		bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long("0.01", 8);
		System.out.println(bcAmount);
	}
}
