package org.eq.modules.enums;

/**
 * 广告订单类型
 * @author kaka
 * @date 2019-06-01
 */
public enum  OrderAdTypeEnum {

    ORDER_SALE(1,"出售广告"),
    ORDER_BUY(2,"求购广告");


    OrderAdTypeEnum(int type, String remark) {
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
        OrderAdTypeEnum[] values = OrderAdTypeEnum.values();
        for(OrderAdTypeEnum temp : values ){
            if(temp.type == type){
                return temp.remark;
            }
        }
        return null;
    }
}
