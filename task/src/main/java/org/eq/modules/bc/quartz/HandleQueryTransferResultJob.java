package org.eq.modules.bc.quartz;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.bumo.mall.talent.biz.EarningsBiz;
import io.bumo.mall.talent.biz.PayBiz;
import io.bumo.mall.talent.biz.UserDeliveryBiz;
import io.bumo.mall.talent.biz.WithdrawBiz;
import io.bumo.mall.talent.common.ConstantsUtil;
import io.bumo.mall.talent.common.log.Logger;
import io.bumo.mall.talent.common.log.LoggerFactory;
import io.bumo.mall.talent.common.util.DateUtil;
import io.bumo.mall.talent.common.util.DecimalCalculateUtil;
import io.bumo.mall.talent.common.util.Tools;
import io.bumo.mall.talent.domain.BcTxRecord;
import io.bumo.mall.talent.domain.BlockChainTX;
import io.bumo.mall.talent.enums.BcStatusEnum;
import io.bumo.mall.talent.enums.BcTxRecordBizTypeEnum;
import io.bumo.mall.talent.external.bc.BlockChainManager;
import io.bumo.mall.talent.service.BcTxService;
import io.bumo.mall.talent.service.BlockChainTxService;
import io.bumo.model.response.result.data.TransactionHistory;

public class HandleQueryTransferResultJob {
	
	private static Logger logger = LoggerFactory.getLogger(HandleQueryTransferResultJob.class);
	
	@Autowired
	private BlockChainTxService blockChainTxService;
	@Autowired
	private BlockChainManager blockChainManager;
	@Autowired
	private BcTxService bcTxService;
	@Autowired
	private PayBiz payBiz;
	@Autowired
	private WithdrawBiz withdrawBiz;
	@Autowired
	private UserDeliveryBiz userDeliveryBiz;
	@Autowired
	private EarningsBiz earningsBiz;
	
	private String defaultFee = "0";
	private Integer existError = 104;
	
	@Value("${blockchain.tx.confirm.result.timeout}")
	private Integer timeNumber;
	
	public void execute(){
		try{
			logger.info("query block  tx result start...");
			List<BlockChainTX> bcTXList = blockChainTxService.queryBcTX4Status(BcStatusEnum.INIT.getCode());
			if(!Tools.isNullByList(bcTXList)){
				for(BlockChainTX tx : bcTXList){
					String hash = tx.getTxHash();
					TransactionHistory transactionHistory = blockChainManager.getTransactionByHash(hash);
					if(!Tools.isNull(transactionHistory)){
						String txFee = DecimalCalculateUtil.divide10Pow(transactionHistory.getActualFee(),8);
						Integer errorCode = transactionHistory.getErrorCode();
						if(ConstantsUtil.BC_SUCCESS==errorCode){
							//更新交易状态为成功 
							blockChainTxService.updateSuccessBcTX(hash,txFee, tx.getId(),errorCode);
							//处理业务状态
							List<BcTxRecord> bcTxRecordlist  = bcTxService.queryBcTxRecord4Hash(hash);
							processBizSuccess(bcTxRecordlist);
							
						}else{
							blockChainTxService.updateFailBcTX(hash,txFee, tx.getId(),errorCode);
							//处理业务状态
							List<BcTxRecord> bcTxRecordlist  = bcTxService.queryBcTxRecord4Hash(hash);
							processBizFail(bcTxRecordlist);
						}
					}else{
						//TODO 暂时不处理 查询不到 或者网络异常
						Date createTime = tx.getCreateTime();
						int diffSecond = DateUtil.getDiffSecond(new Date(),createTime);
						if(diffSecond>timeNumber){ //超过超时时间
							blockChainTxService.updateFailBcTX(hash,defaultFee, tx.getId(),existError);
							List<BcTxRecord> bcTxRecordlist = bcTxService.queryBcTxRecord4Hash(hash);
							processBizFail(bcTxRecordlist);
						}
					}
				}
			}
			logger.info("query block  tx result end...");
		}catch(Exception e){
			logger.error("定时任务查询交易结果异常");
			logger.error(e.getMessage(),e);
		}
    }	
	
	private void processBizSuccess(List<BcTxRecord> bcTxlist){
		if(!Tools.isNull(bcTxlist)){
			for(BcTxRecord bcTxRecord : bcTxlist){
				Integer bizType = bcTxRecord.getBizType();
				String txId = String.valueOf(bcTxRecord.getId());
				if(bizType == BcTxRecordBizTypeEnum.DELIVERY_GOODS.getCode()){//商品提货
					userDeliveryBiz.deliverySuccessExecute(txId);
				}else if(bizType == BcTxRecordBizTypeEnum.REFUND.getCode()){ //商品退款
					payBiz.refundExecute(txId);
				}else if(bizType == BcTxRecordBizTypeEnum.WITHDRAW.getCode()){ //用户提现
					withdrawBiz.wxWithdrawExecute(txId);
				}else if(bizType == BcTxRecordBizTypeEnum.EARNINGS.getCode()){ //用户收益
					earningsBiz.earningExecute(txId);
				}
			}
		}
	}
	
	private void processBizFail(List<BcTxRecord> bcTxlist){
		if(!Tools.isNull(bcTxlist)){
			
		}
	}
}