package org.eq.modules.bc.task;

import com.alibaba.fastjson.JSON;
import io.bumo.encryption.crypto.keystore.KeyStore;
import io.bumo.encryption.crypto.keystore.entity.KeyStoreEty;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.model.response.TransactionSubmitResponse;
import org.eq.modules.bc.biz.InitiatorPoolBiz;
import org.eq.modules.bc.common.ConstantsUtil;
import org.eq.modules.bc.common.util.DecimalCalculateUtil;
import org.eq.modules.bc.common.util.Tools;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BlockchainTx;
import org.eq.modules.bc.entity.InitiatorAcc;
import org.eq.modules.bc.enums.BcStatusEnum;
import org.eq.modules.bc.external.bc.BlockChainManager;
import org.eq.modules.bc.external.bc.req.BatchSubmitTxReq;
import org.eq.modules.bc.external.bc.req.BcTransferReq;
import org.eq.modules.bc.external.bc.req.SignEntity;
import org.eq.modules.bc.external.bc.resp.BlobDataResp;
import org.eq.modules.bc.init.KeyStoreManager;
import org.eq.modules.bc.service.BcTxService;
import org.eq.modules.bc.service.BlockChainTxService;
import org.eq.modules.wallet.dao.UserWalletMapper;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.entity.UserWalletExample;
import org.eq.modules.wallet.util.WalletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HandleBcTxTransferJob {

	private static Logger logger = LoggerFactory.getLogger(HandleBcTxTransferJob.class);
	
	@Autowired
	private BcTxService bcTxService;
	@Autowired
	private BlockChainTxService blockChainTxService;
	@Autowired
	private InitiatorPoolBiz initiatorPoolBiz;
	@Autowired
	private BlockChainManager blockChainManager;
	@Autowired
	private UserWalletMapper userWalletMapper;

	@Scheduled(cron = "* 0/1 * * * ?")
	public void execute(){
		//是否设置keyStore密码
		boolean keyStorePwdFlag = Tools.isNull(KeyStoreManager.getKeyStorePwd());
		if(!keyStorePwdFlag){
			try {
				logger.info("send bc tx start...");
				//查询发送红包区块链未转账的记录
				List<BcTxRecord> bcTxRecordList = bcTxService.queryBcTxRecord4Init();
				if(!Tools.isNullByList(bcTxRecordList)) {
					//组装参数
					BatchSubmitTxReq submitTxReq = bulidSumTxReq(bcTxRecordList);
					//更新交易状态为已提交
					bcTxService.updateBcTxRecord4BcProcess(bcTxRecordList, submitTxReq.getHash());
					// 提交到blokchain
					TransactionSubmitResponse bcResponse = blockChainManager.batchSubmitTx(submitTxReq);
					logger.info("提交交易返回:"+JSON.toJSONString(bcResponse));
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
						logger.error("提交交易到blockchain异常"+bcResponse.getErrorCode()+" Desc:"+bcResponse.getErrorCode());
					}
				}
				logger.info("send bc tx end...");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("交易上链异常:" + e);
			}
		}else{
			logger.error("keyStore密码未设置 ");
		}
	}


	private BatchSubmitTxReq bulidSumTxReq(List<BcTxRecord> bcTxRecordList) throws Exception {
		List<BcTransferReq> batchTransferList = new ArrayList<>();
		
		List<String> fromAddressList = new ArrayList<>();

		for(BcTxRecord bcTxRecord : bcTxRecordList){
			//检查是否激活
			if(!blockChainManager.checkActivated(bcTxRecord.getToAddress())){
				logger.info("账户未激活:"+bcTxRecord.getToAddress());
				continue;
			}
			if(!blockChainManager.checkActivated(bcTxRecord.getFromAddress())){
				logger.info("账户未激活:"+bcTxRecord.getFromAddress());
				continue;
			}
			//资产的精度，暂时用不到
			//Integer assetDecimal = bcTxRecord.getAssetDecimal();
			String assetCode = bcTxRecord.getAssetCode();
			String assetIssuer= bcTxRecord.getAssetIssuer();
			String fromAddress = bcTxRecord.getFromAddress();
			fromAddressList.add(fromAddress);

			long bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long(bcTxRecord.getTransferAmount()+"", 0);//资产数量
			
			BcTransferReq bcTransferReq = new BcTransferReq();
			bcTransferReq.setAmount(bcAmount);
			bcTransferReq.setFromAddress(fromAddress);
			bcTransferReq.setToAddress(bcTxRecord.getToAddress());
			bcTransferReq.setIssuer(assetIssuer);
			bcTransferReq.setCode(assetCode);
			bcTransferReq.setMetadata(bcTxRecord.getOptMetadata());
			batchTransferList.add(bcTransferReq);
		}
		//提交人地址
		String initiatorAddress = initiatorPoolBiz.getInitiator();
		InitiatorAcc initiatorAcc = initiatorPoolBiz.getInitiatorInfo(initiatorAddress);
		
		BlobDataResp blobDataResp = blockChainManager.getBatchOptTransferBlob(batchTransferList, initiatorAddress);
		//logger.info("生成blob:"+JSON.toJSONString(blobDataResp));
		/*
		//生成提交人签名
		List<SignEntity> listSigner = initiatorSignBlob(blobDataResp,initiatorAcc);
		//中间账户的签名
		listSigner = middleAccSignBlob(blobDataResp,listSigner,middleSet);
		//普通账户签名
		listSigner = userSignBlob(blobDataResp,listSigner,userSingerSet);
		*/

		BatchSubmitTxReq submitTxReq = new BatchSubmitTxReq();
		submitTxReq.setBlob(blobDataResp.getBlob());
		submitTxReq.setListSigner(getUsersKeystore(fromAddressList,blobDataResp));
		submitTxReq.setHash(blobDataResp.getHash());
		submitTxReq.setInitiator(initiatorAddress);
		return submitTxReq;
	}

	private List<SignEntity> getUsersKeystore(List<String> fromAddressList,BlobDataResp blobDataResp){
		List<SignEntity> list = new ArrayList<>();
		fromAddressList.forEach(o->{
			UserWallet wallet = getKeystore(o);
			String privateKey = WalletUtil.getPrivateKey(wallet.getKeyStore());

			PrivateKey privateObj = new PrivateKey(privateKey);
			byte[] signByte = privateObj.sign(HexFormat.hexStringToBytes(blobDataResp.getBlob()));
			String signBlob = HexFormat.byteToHex(signByte);

			SignEntity entity = new SignEntity();
			entity.setPublicKey(wallet.getPubilcKey());
			entity.setSignBlob(signBlob);

			list.add(entity);
		});
		return list;
	}

	private UserWallet getKeystore(String fromAddress){
		UserWalletExample example = new UserWalletExample();
		example.or().andAddressEqualTo(fromAddress);
		List<UserWallet> list = userWalletMapper.selectByExample(example);
		if(list==null || list.isEmpty()){
			return null;
		}
		return list.get(0);
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
	
	private Boolean checkFromAddress(String fromAddress){
		InitiatorAcc buyMiddleAcc = initiatorPoolBiz.getBuyMiddleInfo();
		InitiatorAcc pickUpMiddleAcc = initiatorPoolBiz.getPickUpMiddleInfo();
		InitiatorAcc refundMiddleAcc = initiatorPoolBiz.getRefundMiddleInfo();
		InitiatorAcc withdrawMiddleAcc = initiatorPoolBiz.getWithdrawMiddleInfo();
		InitiatorAcc rewardMiddleAcc = initiatorPoolBiz.getRewardMiddleInfo();
		InitiatorAcc activityMiddleAcc = initiatorPoolBiz.getActivityMiddleInfo();
		if(fromAddress.equals(withdrawMiddleAcc.getAddress()) 
				|| fromAddress.equals(buyMiddleAcc.getAddress()) 
				|| fromAddress.equals(pickUpMiddleAcc.getAddress())
				|| fromAddress.equals(refundMiddleAcc.getAddress())
				|| fromAddress.equals(rewardMiddleAcc.getAddress())
				|| fromAddress.equals(activityMiddleAcc.getAddress())){
			return true;
		}
		return false;
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
	/*
	private List<SignEntity> userSignBlob(BlobDataResp blobDataResp,List<SignEntity> listSigner,HashSet<String> userSetList) throws Exception {
//		if(!Tools.isNullByList(userSetList)){
//			for(String signer : userSetList){
//				String accessToken = userBiz.getAccountCenterAccessToken();
//				AccCenterSignBlobResp signBlobResp = accountCenterManager.signBlob(signer, blobDataResp.getBlob(), accessToken);
//				if(Tools.isNull(signBlobResp)){
//					throw new APIException(ExceptionEnum.ACCOUNT_CENTER_SIGN_ERROR);
//				}
//				SignEntity entity = new SignEntity();
//				entity.setPublicKey(signBlobResp.getPublicKey());
//				entity.setSignBlob(signBlobResp.getSignBlob());
//				listSigner.add(entity);
//			}
//		}
		AccountCenterSignBlobListResp signBlobResp = accountCenterManager.signBlobList(userSetList, blobDataResp.getBlob(), accessToken);
		if(Tools.isNull(signBlobResp)){
			throw new APIException(ExceptionEnum.ACCOUNT_CENTER_SIGN_ERROR);
		}
		List<ACSignBlobVO> listSignBlob = signBlobResp.getSignBlobList();
		for(ACSignBlobVO vo : listSignBlob){
			SignEntity entity = new SignEntity();
			entity.setPublicKey(vo.getPublicKey());
			entity.setSignBlob(vo.getSignBlob());
			listSigner.add(entity);
		}
		return listSigner;
	}
	*/

	public static void main(String[] args) {
		long bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long("1", 0);
		System.out.println(bcAmount);
		bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long("1", 0);
		System.out.println(bcAmount);
	}
}
