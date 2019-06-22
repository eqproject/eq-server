package org.eq;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.eq.modules.common.enums.LogTypeEnum;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.enums.SystemConfigStateEnum;
import org.eq.modules.enums.SystemConfigTypeEnum;
import org.eq.modules.log.OrderLogService;
import org.eq.modules.order.entity.OrderAdLog;
import org.eq.modules.support.dao.SystemConfigMapper;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.entity.SystemConfigExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * * @author gb
 *
 * @version 2019/6/18
 */
public class LogTest extends BaseTest{
    @Autowired
    private OrderLogService logService;

    /**
     * 系统配置表
     */
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Test
    public void test(){
        OrderAdLog orderAdLog = new OrderAdLog();
        orderAdLog.setCreateDate(new Date());
        orderAdLog.setNewStatus(OrderAdStateEnum.ORDER_CANCEL.getState());
        orderAdLog.setOldStatus(OrderAdStateEnum.ORDER_DEFAULT.getState());
        orderAdLog.setOrderAdId(Long.parseLong(RandomStringUtils.random(4, false, true)));
        orderAdLog.setRemarks("test case");
        logService.save(LogTypeEnum.AD,orderAdLog);
    }


    @Test
    public void test02(){
        System.out.println(getServiceFee());
        BigDecimal bigDecimal = new BigDecimal(Double.valueOf(2)).multiply(new BigDecimal(1000));
        int servicefee = bigDecimal.setScale(0, BigDecimal.ROUND_UP).intValue();
        System.out.println(servicefee);
    }

    /**
     * 获取服务费
     * @return
     */
    private String getServiceFee(){
        String result = "0" ;
        SystemConfigExample example = new SystemConfigExample();
        SystemConfigExample.Criteria ca = example.or();
        ca.andTypeEqualTo(SystemConfigTypeEnum.FEE_SERVICE.getType());
        ca.andStateEqualTo(SystemConfigStateEnum.DEFAULT.getState());

        try{
            List<SystemConfig> list = systemConfigMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)){

                return result;
            }
            SystemConfig systemConfig = list.get(0);
            result =systemConfig.getValue();
        }catch (Exception e){
            e.printStackTrace();

        }
        return result;
    }
}
