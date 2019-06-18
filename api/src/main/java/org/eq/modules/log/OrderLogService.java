package org.eq.modules.log;

import org.eq.modules.common.enums.LogTypeEnum;
import org.eq.modules.log.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : gb 2019/6/18
 */
@Service
public class OrderLogService {
    private final static String NAME = "OrderLogService";


    @Autowired
    private Map<String, CommonLogService> map;

    public void save(LogTypeEnum type, Object obj) {
        get(type).save(obj);
    }

    public List<Object> list(LogTypeEnum type,long orderId) {
        return get(type).list(orderId);
    }


    private static Map<LogTypeEnum,String> MAPPING = new HashMap();
    static {
        MAPPING.put(LogTypeEnum.AD,"OrderAdLogServiceImpl");
        MAPPING.put(LogTypeEnum.TRADE,"OrderTradeLogServiceImpl");
        MAPPING.put(LogTypeEnum.TRADE_PAYMENT,"OrderPaymentTradeLogServiceImpl");
        MAPPING.put(LogTypeEnum.TRADE_REFUND,"OrderRefundTradeLogServiceImpl");
        MAPPING.put(LogTypeEnum.ACCEPT,"OrderAcceptLogServiceImpl");
        MAPPING.put(LogTypeEnum.TRANSFER,"OrderTransferLogServiceImpl");
    }

    private CommonLogService get(LogTypeEnum type) {
       return map.get(MAPPING.get(type));
    }
}
