package org.eq.modules.enums;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderAcceptStateEnum {
    ACCEPT_WAIT(1,"等待承兑"),
    ACCEPT_PADDING(2,"承兑中"),
    ACCEPT_FINISH(3,"承兑完成"),
    ACCEPT_CANCEL(4,"取消承兑"),
    ACCEPT_FAIL(5,"承兑失败");


    OrderAcceptStateEnum(int state, String remark) {
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
        OrderAcceptStateEnum[] values = OrderAcceptStateEnum.values();
        for(OrderAcceptStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }
}
