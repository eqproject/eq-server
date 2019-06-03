package org.eq.modules.bc.quartz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import io.bumo.encryption.crypto.keystore.KeyStore;
import io.bumo.encryption.crypto.keystore.entity.KeyStoreEty;
import io.bumo.encryption.key.PrivateKey;
import io.bumo.encryption.utils.hex.HexFormat;
import io.bumo.mall.talent.biz.InitiatorPoolBiz;
import io.bumo.mall.talent.biz.UserBiz;
import io.bumo.mall.talent.common.ConstantsUtil;
import io.bumo.mall.talent.common.util.DecimalCalculateUtil;
import io.bumo.mall.talent.common.util.Tools;
import io.bumo.mall.talent.domain.BcTxRecord;
import io.bumo.mall.talent.domain.BlockChainTX;
import io.bumo.mall.talent.domain.InitiatorAcc;
import io.bumo.mall.talent.enums.BcStatusEnum;
import io.bumo.mall.talent.enums.ExceptionEnum;
import io.bumo.mall.talent.exception.APIException;
import io.bumo.mall.talent.external.accountCenter.AccountCenterManager;
import io.bumo.mall.talent.external.accountCenter.resp.ACSignBlobVO;
import io.bumo.mall.talent.external.accountCenter.resp.AccountCenterSignBlobListResp;
import io.bumo.mall.talent.external.bc.BlockChainManager;
import io.bumo.mall.talent.external.bc.req.BatchSubmitTxReq;
import io.bumo.mall.talent.external.bc.req.BcTransferReq;
import io.bumo.mall.talent.external.bc.req.SignEntity;
import io.bumo.mall.talent.external.bc.resp.BlobDataResp;
import io.bumo.mall.talent.init.KeyStoreManager;
import io.bumo.mall.talent.service.BcTxService;
import io.bumo.mall.talent.service.BlockChainTxService;
import io.bumo.model.response.TransactionSubmitResponse;

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
	private UserBiz userBiz;
	@Autowired
	private AccountCenterManager accountCenterManager;
	
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
					if(bcResponse.getErrorCode() == ConstantsUtil.BC_SUCCESS){
						//记录到区跨链交易表
						BlockChainTX blockChainTX = new BlockChainTX();
						blockChainTX.setTxBlob(submitTxReq.getBlob());
						blockChainTX.setTxHash(submitTxReq.getHash());
						blockChainTX.setTxInitiatorAddress(submitTxReq.getInitiator());
						blockChainTX.setTxStatus(BcStatusEnum.INIT.getCode());
						blockChainTxService.addBcTx(blockChainTX);
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
		List<BcTransferReq> batchTransferList = new ArrayList<BcTransferReq>();
		
		//中间账户
		HashSet<String> middleSet = new HashSet<String>();
		//用户集合
		HashSet<String> userSingerSet = new HashSet<String>();
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
			Integer assetDecimal = bcTxRecord.getAssetDecimal();
			String assetCode = bcTxRecord.getAssetCode();
			String assetIssuer= bcTxRecord.getAssetIssuer();
			String fromAddress = bcTxRecord.getFromAddress();
			if(checkFromAddress(fromAddress)){
				middleSet.add(fromAddress);
			}else{
				userSingerSet.add(fromAddress);
			}
			long bcAmount = DecimalCalculateUtil.numberMultiply10Pow4Long(bcTxRecord.getTransferAmount()+"", assetDecimal);//资产数量
			
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
		//生成提交人签名
		List<SignEntity> listSigner = initiatorSignBlob(blobDataResp,initiatorAcc);
		//中间账户的签名
		listSigner = middleAccSignBlob(blobDataResp,listSigner,middleSet);
		//普通账户签名
		listSigner = userSignBlob(blobDataResp,listSigner,userSingerSet);
		
		BatchSubmitTxReq submitTxReq = new BatchSubmitTxReq();
		submitTxReq.setBlob(blobDataResp.getBlob());
		submitTxReq.setListSigner(listSigner);
		submitTxReq.setHash(blobDataResp.getHash());
		submitTxReq.setInitiator(initiatorAddress);
		return submitTxReq;
	}


	private List<SignEntity> initiatorSignBlob(BlobDataResp blobDataResp, InitiatorAcc initiatorAcc) throws Exception {
		List<SignEntity> listSigner = new ArrayList<SignEntity>();
		String privateStr = KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(initiatorAcc.getPrivateKey(), KeyStoreEty.class));
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
				String privateStr = KeyStore.decipherKeyStore(KeyStoreManager.getKeyStorePwd(), JSON.parseObject(middleAcc.getPrivateKey(), KeyStoreEty.class));
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
		String accessToken = userBiz.getAccountCenterAccessToken();
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
}
