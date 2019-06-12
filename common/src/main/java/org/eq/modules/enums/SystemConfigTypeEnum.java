package org.eq.modules.enums;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum SystemConfigTypeEnum {

    TRADE_HOUR(1,"交易支付时长"),
    FEE_SERVICE(2,"服务费比例"),
    RISK_SMS(3,"短信风控"),
    ORDER_CLOSE_HOUR(4,"广告关闭时长");



    SystemConfigTypeEnum(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    private int type;
    private String remark;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取枚举类介绍
     * @param type
     * @return
     */
    public static String  getRemarkByState(int type){
        SystemConfigTypeEnum[] values = SystemConfigTypeEnum.values();
        for(SystemConfigTypeEnum temp : values ){
            if(temp.type == type){
                return temp.remark;
            }
        }
        return null;
    }


}
