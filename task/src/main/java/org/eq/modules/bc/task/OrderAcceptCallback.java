package org.eq.modules.bc.task;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.bc.enums.BcTxRecordBizTypeEnum;
import org.eq.modules.bc.service.AbstractTaskCallBack;
import org.eq.modules.enums.OrderAcceptStateEnum;
import org.eq.modules.order.dao.OrderAcceptLogMapper;
import org.eq.modules.order.dao.OrderAcceptMapper;
import org.eq.modules.order.entity.OrderAccept;
import org.eq.modules.order.entity.OrderAcceptExample;
import org.eq.modules.order.entity.OrderAcceptLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author  kaka
 * @version 2019/6/10
 */
@Component
public class OrderAcceptCallback {

    private static Logger logger = LoggerFactory.getLogger(OrderAcceptCallback.class);


    @Autowired
    private static OrderAcceptMapper orderAcceptMapper;
    @Autowired
    private static OrderAcceptLogMapper orderAcceptLogMapper;

    public static void main(String[] args) {
        new AbstractTaskCallBack(BcTxRecordBizTypeEnum.ACCEPT,new OrderAcceptCallback()){

            @Override
            public void success(String txId) {
                OrderAccept accept  =  getAcceptByTxId(txId);
                if(accept ==null){
                    return ;
                }
                boolean updateResult = opAccept(accept,true);
                if(!updateResult){
                    logger.error("更新承兑表数据失败,承兑表Id:{}",accept.getId());
                }
            }

            @Override
            public void fail(String txId) {
                OrderAccept accept  =  getAcceptByTxId(txId);
                if(accept ==null){
                    return ;
                }
                boolean updateResult = opAccept(accept,false);
                if(!updateResult){
                    logger.error("更新承兑表数据失败,承兑表Id:{}",accept.getId());
                }
            }
        };
    }

    /**
     * 通过TXid 获取订单信息
     * @param txId
     * @return
     */
    private static OrderAccept getAcceptByTxId(String txId){
        OrderAcceptExample orderAcceptExample = new OrderAcceptExample();
        OrderAcceptExample.Criteria ca = orderAcceptExample.or();
        ca.andTxIdEqualTo(Long.valueOf(txId));
        List<OrderAccept> result = orderAcceptMapper.selectByExample(orderAcceptExample);
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    /**
     * 处理承兑订单
     * @param orderAccept
     * @param isSuccess
     * @return
     */
    private static boolean opAccept(OrderAccept orderAccept,boolean isSuccess){
        if(orderAccept == null){
            return false;
        }
        int oldAcceptState = orderAccept.getStatus();
        int newAcceptState =isSuccess? OrderAcceptStateEnum.ACCEPT_FINISH.getState() : OrderAcceptStateEnum.ACCEPT_FAIL.getState();

        OrderAcceptExample orderAcceptExample = new OrderAcceptExample();
        OrderAcceptExample.Criteria ca = orderAcceptExample.or();
        ca.andIdEqualTo(orderAccept.getId());
        ca.andStatusEqualTo(oldAcceptState);

        OrderAccept updateObj  = new OrderAccept();
        updateObj.setStatus(newAcceptState);
        updateObj.setUpdateDate(new Date());
        int updateResult = orderAcceptMapper.updateByExampleSelective(updateObj,orderAcceptExample);
        if(updateResult<=0){
            return false;
        }
        OrderAcceptLog orderAcceptLog = new OrderAcceptLog();
        orderAcceptLog.setOldStatus(oldAcceptState);
        orderAcceptLog.setNewStatus(newAcceptState);
        orderAcceptLog.setOrderAcceptId(orderAccept.getId());
        orderAcceptLog.setCreateDate(new Date());
        orderAcceptLog.setRemarks("区块链接口回调结果。返回"+isSuccess +", 更新承兑表");
        orderAcceptLogMapper.insertSelective(orderAcceptLog);
        return true;




    }
}
