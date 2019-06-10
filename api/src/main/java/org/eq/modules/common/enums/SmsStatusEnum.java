package org.eq.modules.common.enums;

public enum SmsStatusEnum {
    SENDING(1, "发送中"), SUCCESS(2, "发送成功"), FAIL(3, "发送失败");


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

    private SmsStatusEnum(int state, String remark) {
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     *
     * @param state
     * @return
     */
    public static String getRemarkByState(int state) {
        SmsStatusEnum[] values = SmsStatusEnum.values();

        for (SmsStatusEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }
}
