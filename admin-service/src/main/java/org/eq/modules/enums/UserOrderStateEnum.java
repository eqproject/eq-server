package org.eq.modules.enums;

/**
 * 用户订单状态枚举类
 */
public enum UserOrderStateEnum {
    ORDER_STATE_CREATE(1,"创建"),
    ORDER_STATE_CONCEL(2,"取消"),
    ORDER_STATE_RUNNING(3,"交易中"),
    ORDER_STATE_FINISH(4,"已完成");
    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;

    public int getState() {
        return state;
    }

    public String getRemark() {
        return remark;
    }

    private UserOrderStateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        UserOrderStateEnum[] values = UserOrderStateEnum.values();

        for(UserOrderStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
