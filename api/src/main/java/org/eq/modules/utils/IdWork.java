package org.eq.modules.utils;

import org.eq.basic.common.util.DateUtil;

/**
 * id 生成器
 */
public class IdWork {


    public static  byte[] lock = new byte[1];

    /**
     * 创建订单编号
     * @param order
     * @return
     */
    public static String getOrderCode(String order){
        StringBuffer buffer = new StringBuffer(order);
        synchronized (lock){
            String number = String.valueOf((Math.random()*9+1)*100000);
            if(number.contains(".")){
                number=number.substring(0,number.indexOf("."));
            }
            buffer.append(DateUtil.getLockNowTime()).append(number);
        }
        return buffer.toString();
    }

}

