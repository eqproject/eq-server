package org.eq.modules.enums;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderTransferStateEnum {

    TRANSFER_PADDING(1,"等待确认"),
    TRANSFER_SUCCESS(2,"转让成功"),
    TRANSFER_CANCEL(3,"取消转让"),
    TRANSFER_FAIL(4,"转让失败");


    OrderTransferStateEnum(int state, String remark) {
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
        OrderTransferStateEnum[] values = OrderTransferStateEnum.values();
        for(OrderTransferStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }
}
