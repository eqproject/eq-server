package org.eq.modules.enums;

/**
 * 交易订单类型
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderTradeTypeEnum {

    ORDER_SALE(1,"出售广告"),
    ORDER_BUY(2,"求购广告");


    OrderTradeTypeEnum(int type, String remark) {
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
    public static String  getRemarkByType(int type){
        OrderTradeTypeEnum[] values = OrderTradeTypeEnum.values();
        for(OrderTradeTypeEnum temp : values ){
            if(temp.type == type){
                return temp.remark;
            }
        }
        return null;
    }
}
