package org.eq.modules.bc.task;

import com.alibaba.fastjson.JSON;
import io.bumo.model.response.result.data.TransactionHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eq.modules.bc.common.ConstantsUtil;
import org.eq.modules.bc.common.util.DateUtil;
import org.eq.modules.bc.common.util.DecimalCalculateUtil;
import org.eq.modules.bc.common.util.Tools;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.BlockchainTx;
import org.eq.modules.bc.enums.BcStatusEnum;
import org.eq.modules.bc.external.bc.BlockChainManager;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.bc.service.BcTxService;
import org.eq.modules.bc.service.BlockChainTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HandleQueryTransferResultJob {

	private final BlockChainTxService blockChainTxService;
	private final BlockChainManager blockChainManager;
	private final BcTxService bcTxService;

	private String defaultFee = "0";
	private Integer existError = 104;
	
	@Value("${blockchain.tx.confirm.result.timeout}")
	private Integer timeNumber;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void execute(){
		try{
			log.info("query block  tx result start...");
			List<BlockchainTx> bcTXList = blockChainTxService.queryBcTX4Status(BcStatusEnum.INIT.getCode());
			if(!Tools.isNullByList(bcTXList)){
				for(BlockchainTx tx : bcTXList){
					String hash = tx.getTxHash();
					TransactionHistory transactionHistory = blockChainManager.getTransactionByHash(hash);
					log.info("transaction result:{}", JSON.toJSONString(transactionHistory));
					if(!Tools.isNull(transactionHistory)){
						String txFee = DecimalCalculateUtil.divide10Pow(transactionHistory.getActualFee(),8);
						Integer errorCode = transactionHistory.getErrorCode();
						if(ConstantsUtil.BC_SUCCESS.equals(errorCode)){
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
			log.info("query block  tx result end...");
		}catch(Exception e){
			log.error("定时任务查询交易结果异常");
			log.error(e.getMessage(),e);
		}
    }	
	
	private void processBizSuccess(List<BcTxRecord> bcTxlist){
		if(!Tools.isNull(bcTxlist)){
			for(BcTxRecord bcTxRecord : bcTxlist){
				Integer bizType = bcTxRecord.getBizType();
				String txId = String.valueOf(bcTxRecord.getId());
				AbstractTaskCallBack.get(bizType).success(txId);
			}
		}
	}
	
	private void processBizFail(List<BcTxRecord> bcTxlist){
		if(!Tools.isNull(bcTxlist)){

		}
	}
}