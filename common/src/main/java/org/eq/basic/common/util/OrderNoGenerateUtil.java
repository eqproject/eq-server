package org.eq.basic.common.util;

import org.eq.modules.enums.OrderNoPreFixEnum;

/**
 * @author admin
 * @Title: OrderNoGenerateUtil
 * @Copyright: Copyright (c) 2018
 * @Description: 订单号生成工具类
 * @Company: 123.com
 * @Created on 2019/6/2上午10:29
 */
public class OrderNoGenerateUtil {


    /**
     * 生成订单号
     * @param orderNoPreFixEnum
     * @return
     */
    public static String generateNo(OrderNoPreFixEnum orderNoPreFixEnum) {
        String dateStr = DateUtil.dateToStr(DateUtil.getNowTime(),DateUtil.DATE_FORMAT_FULL);
        return orderNoPreFixEnum.getCode()+dateStr;
    }


}
