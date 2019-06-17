package org.eq.modules.bc.task;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.enums.OrderTransferStateEnum;
import org.eq.modules.order.dao.OrderTransferLogMapper;
import org.eq.modules.order.dao.OrderTransferMapper;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.entity.OrderTransferExample;
import org.eq.modules.order.entity.OrderTransferLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author  kaka
 *
 * @version 2019/6/10
 */
@Component
public class OrderTradeCallback {

    private static Logger logger = LoggerFactory.getLogger(OrderTradeCallback.class);


    @Autowired
    private static OrderTransferMapper orderTransferMapper;
    @Autowired
    private static OrderTransferLogMapper orderTransferLogMapper;

    public static void main(String[] args) {
        new AbstractTaskCallBack(BcTxRecordBizTypeEnum.TRANSFER,new OrderTradeCallback()){

            @Override
            public void success(String txId) {
                OrderTransfer orderTransfer =   getTransferByTxId(txId);
                if(orderTransfer ==null){
                    return ;
                }
                boolean updateResult = opTransfer(orderTransfer,true);
                if(!updateResult){
                    logger.error("更新转让表数据失败,转让表Id:{}",orderTransfer.getId());
                }
            }

            @Override
            public void fail(String txId) {
                OrderTransfer orderTransfer =   getTransferByTxId(txId);
                if(orderTransfer ==null){
                    return ;
                }
                boolean updateResult = opTransfer(orderTransfer,false);
                if(!updateResult){
                    logger.error("更新转让表数据失败,转让表Id:{}",orderTransfer.getId());
                }
            }
        };
    }

    /**
     * 通过TXid 获取订单信息
     * @param txId
     * @return
     */
    private static OrderTransfer getTransferByTxId(String txId){
        OrderTransferExample orderTransferExample = new OrderTransferExample();
        OrderTransferExample.Criteria ca = orderTransferExample.or();
        ca.andTxIdEqualTo(Long.valueOf(txId));
        List<OrderTransfer> result = orderTransferMapper.selectByExample(orderTransferExample);
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    /**
     * 处理转让订单
     * @param orderTransfer
     * @param isSuccess
     * @return
     */
    private static boolean opTransfer(OrderTransfer orderTransfer,boolean isSuccess){
        if(orderTransfer == null){
            return false;
        }
        int oldTransferState = orderTransfer.getStatus();
        int newTransferState =isSuccess? OrderTransferStateEnum.TRANSFER_SUCCESS.getState() : OrderTransferStateEnum.TRANSFER_FAIL.getState();

        OrderTransferExample orderTransferExample = new OrderTransferExample();
        OrderTransferExample.Criteria ca = orderTransferExample.or();
        ca.andIdEqualTo(orderTransfer.getId());
        ca.andStatusEqualTo(oldTransferState);

        OrderTransfer updateObj  = new OrderTransfer();
        updateObj.setStatus(newTransferState);

        int updateResult = orderTransferMapper.updateByExampleSelective(updateObj,orderTransferExample);
        if(updateResult<=0){
            return false;
        }
        OrderTransferLog orderTransferLog = new OrderTransferLog();
        orderTransferLog.setOldStatus(oldTransferState);
        orderTransferLog.setNewStatus(newTransferState);
        orderTransferLog.setOrderTransferId(orderTransfer.getId());
        orderTransferLog.setCreateDate(new Date());
        orderTransferLog.setRemarks("区块链接口回调结果。返回"+isSuccess +", 更新转让表");
        orderTransferLogMapper.insertSelective(orderTransferLog);
        return true;




    }
}
