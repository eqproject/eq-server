package org.eq.modules.enums;

/**
 * @author admin
 * @Title: OrderNoPreFixEnum
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2上午10:53
 */
public enum OrderNoPreFixEnum {
    ADVERTISING_NO("AD","广告订单"),
    TRADE_NO("JY","交易订单"),
    ;

    OrderNoPreFixEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
