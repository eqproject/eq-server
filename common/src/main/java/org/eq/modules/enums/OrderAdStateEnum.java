package org.eq.modules.enums;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderAdStateEnum {

    ORDER_DEFAULT(1,"待审核"),
    ORDER_CANCEL(2,"取消"),
    ORDER_TRADEING(3,"交易中"),
    ORDER_FINISH(4,"完成"),
    ORDER_REJECT(5,"审核不通过");


    OrderAdStateEnum(int state, String remark) {
        this.state = state;
        this.remark = remark;
    }

    private int state;
    private String remark;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        OrderAdStateEnum[] values = OrderAdStateEnum.values();
        for(OrderAdStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }
}
