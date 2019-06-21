package org.eq.modules.bc.external.bc;

import io.bumo.SDK;
import io.bumo.blockchain.TransactionService;
import io.bumo.blockchain.impl.TransactionServiceImpl;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.AssetSendOperation;
import io.bumo.model.request.operation.BUSendOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.TransactionBuildBlobResult;
import io.bumo.model.response.result.data.AssetInfo;
import io.bumo.model.response.result.data.Signature;
import io.bumo.model.response.result.data.TransactionHistory;
import org.eq.modules.bc.common.util.Tools;
import org.eq.modules.bc.external.bc.enums.StatusEnum;
import org.eq.modules.bc.external.bc.req.BatchSubmitTxReq;
import org.eq.modules.bc.external.bc.req.BcAssetReq;
import org.eq.modules.bc.external.bc.req.BcTransferReq;
import org.eq.modules.bc.external.bc.req.SignEntity;
import org.eq.modules.bc.external.bc.resp.BcAssetResp;
import org.eq.modules.bc.external.bc.resp.BlobDataResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BlockChainManager {
	
	@Value("${blockchain.node.ip}")
	private String bcUrl;
	@Value("${blockchain.single.tx.fee}")
	private long fee;
	@Value("${blockchain.tx.gasprice}")
	private long gasprice;
	
	/**
	 * 获取转移BU交易Blob
	 * @param bcTransferBu
	 * @return
	 */
	public BlobDataResp getTransferBuBlob(BcTransferReq bcTransfer){
		BlobDataResp blobData = null;
		BUSendOperation buSendOperation = new BUSendOperation();
        buSendOperation.setSourceAddress(bcTransfer.getFromAddress());
        buSendOperation.setDestAddress(bcTransfer.getToAddress());
        buSendOperation.setAmount(bcTransfer.getAmount());
        
        //获取交易nonce
        long accNonce = getAccountNonce(bcTransfer.getFromAddress())+1;
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(bcTransfer.getFromAddress());
        transactionBuildBlobRequest.setNonce(accNonce);
        transactionBuildBlobRequest.setFeeLimit(bcTransfer.getFee());
        transactionBuildBlobRequest.setGasPrice(gasprice);
        transactionBuildBlobRequest.setMetadata(bcTransfer.getMetadata());
        transactionBuildBlobRequest.addOperation(buSendOperation);
        // 获取交易BLob串
        TransactionService transactionService = new TransactionServiceImpl();
        TransactionBuildBlobResponse transactionBuildBlobResponse = transactionService.buildBlob(transactionBuildBlobRequest);
        if(transactionBuildBlobResponse.getErrorCode()==StatusEnum.SUCCESS.getCode()){
        	TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        	blobData = new BlobDataResp();
            blobData.setBlob(transactionBuildBlobResult.getTransactionBlob());
            blobData.setHash(transactionBuildBlobResult.getHash());
        }
		return blobData;
	}
	
	/**
	 * 获取转移资产交易Blob
	 * @param bcTransferAsset
	 * @return
	 */
	public BlobDataResp getTransferAssetBlob(BcTransferReq bcTransfer){
		BlobDataResp blobData = null;
		AssetSendOperation assetSendOperation = new AssetSendOperation();
		assetSendOperation.setSourceAddress(bcTransfer.getFromAddress());
		assetSendOperation.setDestAddress(bcTransfer.getToAddress());
		assetSendOperation.setAmount(bcTransfer.getAmount());
		assetSendOperation.setCode(bcTransfer.getCode());
		assetSendOperation.setIssuer(bcTransfer.getIssuer());
		
        //获取交易nonce
        long accNonce = getAccountNonce(bcTransfer.getFromAddress())+1;
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(bcTransfer.getFromAddress());
        transactionBuildBlobRequest.setNonce(accNonce);
        transactionBuildBlobRequest.setFeeLimit(bcTransfer.getFee());
        transactionBuildBlobRequest.setGasPrice(gasprice);
        transactionBuildBlobRequest.setMetadata(bcTransfer.getMetadata());
        
        //转移BU
        transactionBuildBlobRequest.addOperation(assetSendOperation);
        
  		
        // 获取交易BLob串
        TransactionService transactionService = new TransactionServiceImpl();
        TransactionBuildBlobResponse transactionBuildBlobResponse = transactionService.buildBlob(transactionBuildBlobRequest);
        if(transactionBuildBlobResponse.getErrorCode()==StatusEnum.SUCCESS.getCode()){
        	TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        	blobData = new BlobDataResp();
            blobData.setBlob(transactionBuildBlobResult.getTransactionBlob());
            blobData.setHash(transactionBuildBlobResult.getHash());
        }
		return blobData;
	}
	
	public BlobDataResp getTransferAssetBlob4Activated(BcTransferReq bcTransfer){
		BlobDataResp blobData = null;
		AssetSendOperation assetSendOperation = new AssetSendOperation();
		assetSendOperation.setSourceAddress(bcTransfer.getFromAddress());
		assetSendOperation.setDestAddress(bcTransfer.getToAddress());
		assetSendOperation.setAmount(bcTransfer.getAmount());
		assetSendOperation.setCode(bcTransfer.getCode());
		assetSendOperation.setIssuer(bcTransfer.getIssuer());
		
        //获取交易nonce
        long accNonce = getAccountNonce(bcTransfer.getFromAddress())+1;
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(bcTransfer.getFromAddress());
        transactionBuildBlobRequest.setNonce(accNonce);
        transactionBuildBlobRequest.setFeeLimit(bcTransfer.getFee());
        transactionBuildBlobRequest.setGasPrice(gasprice);
        transactionBuildBlobRequest.setMetadata(bcTransfer.getMetadata());
        //转移TOKEN
        transactionBuildBlobRequest.addOperation(assetSendOperation);
        // 获取交易BLob串
        TransactionService transactionService = new TransactionServiceImpl();
        TransactionBuildBlobResponse transactionBuildBlobResponse = transactionService.buildBlob(transactionBuildBlobRequest);
        if(transactionBuildBlobResponse.getErrorCode()==StatusEnum.SUCCESS.getCode()){
        	TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        	blobData = new BlobDataResp();
            blobData.setBlob(transactionBuildBlobResult.getTransactionBlob());
            blobData.setHash(transactionBuildBlobResult.getHash());
        }
		return blobData;
	}
	
	/**
	 * 提交交易
	 * @param transactionSubmitRequest
	 * @throws Exception
	 */
	public TransactionSubmitResponse submitTx(TransactionSubmitRequest transactionSubmit) throws Exception{
		SDK sdk = SDK.getInstance(bcUrl);
		
		TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmit);
		
		return transactionSubmitResponse;
	}
	
	/**
	 * 通过hash查询交易信息
	 * @param txhash
	 * @return
	 */
	public TransactionHistory getTransactionByHash(String txhash) {
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			TransactionGetInfoRequest request = new TransactionGetInfoRequest();
			request.setHash(txhash);
			TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
			if(4 == response.getErrorCode()) {
				return null;
			}
			if(0 == response.getErrorCode()) {
				List<TransactionHistory> listHis = Arrays.asList(response.getResult().getTransactions());
				for (TransactionHistory transactionHistory : listHis) {
					if(txhash.equals(transactionHistory.getHash())) {
						return transactionHistory;
					}
				}
			}
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	public Long getAccountNonce(String address){
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountGetNonceRequest request = new AccountGetNonceRequest();
		    request.setAddress(address);
		    AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
		    if(StatusEnum.SUCCESS.getCode() == response.getErrorCode()){
		        return response.getResult().getNonce();
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	public boolean checkActivated(String address){
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountCheckActivatedRequst request = new AccountCheckActivatedRequst();
		    request.setAddress(address);
		    AccountCheckActivatedResponse response = sdk.getAccountService().checkActivated(request);
		    if(StatusEnum.SUCCESS.getCode() == response.getErrorCode()){
		        return response.getResult().getIsActivated();
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return false;
	}
	/**
	 * 查询账户地址余额
	 * @param address
	 * @return
	 */
	public Long getAccountBalance(String address){
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountGetBalanceRequest request = new AccountGetBalanceRequest();
		    request.setAddress(address);
		    AccountGetBalanceResponse response = sdk.getAccountService().getBalance(request);
		    if(StatusEnum.SUCCESS.getCode() == response.getErrorCode()){
		        return response.getResult().getBalance();
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	public Long getAccountAssetBalance(String address,String assetCode,String issuer){
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountGetAssetsRequest accountGetAssetsRequest = new AccountGetAssetsRequest();
			accountGetAssetsRequest.setAddress(address);
			AccountGetAssetsResponse response = sdk.getAccountService().getAssets(accountGetAssetsRequest);
			if(StatusEnum.SUCCESS.getCode() == response.getErrorCode()){
				AssetInfo[] assetList = response.getResult().getAssets();
				for(AssetInfo assetInfo : assetList){
					String code = assetInfo.getKey().getCode();
					String issueAddress = assetInfo.getKey().getIssuer();
					long amount = assetInfo.getAmount();
					if(code.equals(assetCode) && issueAddress.equals(issuer)){
						return amount;
					}
				}
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	public BlobDataResp getBatchOptTransferBlob(List<BcTransferReq> list,String initiator){
		BlobDataResp blobData = null;
        //获取交易nonce
        long accNonce = getAccountNonce(initiator)+1;
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(initiator);
        transactionBuildBlobRequest.setNonce(accNonce);
        transactionBuildBlobRequest.setFeeLimit(list.size()*fee);
        transactionBuildBlobRequest.setGasPrice(gasprice);
        
        for(BcTransferReq bcTransfer : list){
        	String issueAddress = bcTransfer.getIssuer();
        	if(Tools.isNull(issueAddress)){//BU
        		BUSendOperation buSendOperation = new BUSendOperation();
                buSendOperation.setSourceAddress(bcTransfer.getFromAddress());
                buSendOperation.setDestAddress(bcTransfer.getToAddress());
                buSendOperation.setAmount(bcTransfer.getAmount());
                buSendOperation.setMetadata(bcTransfer.getMetadata());
                transactionBuildBlobRequest.addOperation(buSendOperation);
        	}else{
        		AssetSendOperation assetSendOperation = new AssetSendOperation();
        		assetSendOperation.setSourceAddress(bcTransfer.getFromAddress());
        		assetSendOperation.setDestAddress(bcTransfer.getToAddress());
        		assetSendOperation.setAmount(bcTransfer.getAmount());
        		assetSendOperation.setCode(bcTransfer.getCode());
        		assetSendOperation.setIssuer(bcTransfer.getIssuer());
        		assetSendOperation.setMetadata(bcTransfer.getMetadata());
        		transactionBuildBlobRequest.addOperation(assetSendOperation);
        		
        	}
        }
        
        // 获取交易BLob串
        TransactionService transactionService = new TransactionServiceImpl();
        TransactionBuildBlobResponse transactionBuildBlobResponse = transactionService.buildBlob(transactionBuildBlobRequest);
        if(transactionBuildBlobResponse.getErrorCode() == StatusEnum.SUCCESS.getCode()){
        	TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        	blobData = new BlobDataResp();
            blobData.setBlob(transactionBuildBlobResult.getTransactionBlob());
            blobData.setHash(transactionBuildBlobResult.getHash());
        }
		return blobData;
	}
	
	
	public TransactionSubmitResponse batchSubmitTx(BatchSubmitTxReq submitTxReq){
		TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
		transactionSubmitRequest.setTransactionBlob(submitTxReq.getBlob());
		
		List<SignEntity> listSigner = submitTxReq.getListSigner();
		int length = listSigner.size();
		Signature[] signatures = new Signature[length];
		for(int i=0;i<length;i++){
			SignEntity signEntity = listSigner.get(i);
			Signature signature = new Signature();
			signature.setPublicKey(signEntity.getPublicKey());
			signature.setSignData(signEntity.getSignBlob());
			signatures[i]=signature;
		}
		
		transactionSubmitRequest.setSignatures(signatures);
		SDK sdk = SDK.getInstance(bcUrl);
		TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
		return transactionSubmitResponse;
	}
	/**
	 * 查询账户资产列表
	 * @param address
	 * @return
	 */
	public AccountGetAssetsResponse getAccountAssets(String address) {
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountGetAssetsRequest accountGetAssetsRequest = new AccountGetAssetsRequest();
			accountGetAssetsRequest.setAddress(address);
			
			AccountGetAssetsResponse response = sdk.getAccountService().getAssets(accountGetAssetsRequest);
			if(StatusEnum.SUCCESS.getCode() .equals(response.getErrorCode())){
				return response;
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	
	public List<BcAssetResp> getAccountAsset4List(String address, List<BcAssetReq> bcAssetReqList){
		List<BcAssetResp> result = new ArrayList<BcAssetResp>();
		try{
			SDK sdk = SDK.getInstance(bcUrl);
			AccountGetAssetsRequest accountGetAssetsRequest = new AccountGetAssetsRequest();
			accountGetAssetsRequest.setAddress(address);
			AccountGetAssetsResponse response = sdk.getAccountService().getAssets(accountGetAssetsRequest);
			if(StatusEnum.SUCCESS.getCode() == response.getErrorCode()){
				AssetInfo[] assetList = response.getResult().getAssets();
				for(AssetInfo assetInfo : assetList){
					String code = assetInfo.getKey().getCode();
					String issueAddress = assetInfo.getKey().getIssuer();
					long amount = assetInfo.getAmount();
					for(BcAssetReq bcAssetReq : bcAssetReqList){
						if(code.equals(bcAssetReq.getAssetCode()) && issueAddress.equals(bcAssetReq.getAssetIssuer())){
							BcAssetResp bcAssetResp = new BcAssetResp();
							bcAssetResp.setAmount(amount);
							bcAssetResp.setAssetCode(code);
							bcAssetResp.setAssetIssuer(issueAddress);
							bcAssetResp.setGoodsId(bcAssetReq.getGoodsId());
							bcAssetResp.setAssetDecimals(bcAssetReq.getAssetDecimals());
							result.add(bcAssetResp);
						}
					}
				}
		    }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return result;
	}
}
