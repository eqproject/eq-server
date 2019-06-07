package org.eq.modules.enums;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderFinishStateEnum {

    ORDER_FINISH(1,"完成"),
    ORDER_CLOSE(2,"关闭"),
    ORDER_CANCEL(3,"取消");


    OrderFinishStateEnum(int state, String remark) {
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
        OrderFinishStateEnum[] values = OrderFinishStateEnum.values();
        for(OrderFinishStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }
}
