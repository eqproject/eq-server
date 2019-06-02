package org.eq.modules.enums;

/**
 * 广告终结状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderFinishTypeEnum {

    ORDER_AD_SALE(1,"广告出售订单"),
    ORDER_AD_BUY(2,"广告求购订单"),
    ORDER_TRADE_SALE(3,"交易出售订单"),
    ORDER_TRADE_BUY(4,"交易求购订单");


    OrderFinishTypeEnum(int type, String remark) {
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
        OrderFinishTypeEnum[] values = OrderFinishTypeEnum.values();
        for(OrderFinishTypeEnum temp : values ){
            if(temp.type == type){
                return temp.remark;
            }
        }
        return null;
    }
}
