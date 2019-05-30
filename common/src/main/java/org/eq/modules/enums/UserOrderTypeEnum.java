package org.eq.enums;

/**
 * 用户订单状态枚举类
 */
public enum UserOrderTypeEnum {

    ORDER_TYPE_SELL(1,"出售"),
    ORDER_TYPE_BUY(2,"求购");
    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;

    private UserOrderTypeEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemark(int state){
        UserOrderTypeEnum[] values = UserOrderTypeEnum.values();
       
        for(UserOrderTypeEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
