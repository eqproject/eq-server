package org.eq.modules.common.enums;

public enum SmsTypeEnum {
    REGISTER(1, "注册验证码"), BUY_AD(2, "发布求购广告成功通知"), SALE_AD(3, "发布出售广告成功通知"),
    BUY_SUCESS(4, "购买成功收货通知"), SUCESS(5, "转出成功通知"), PAY(6, "通知买家付款");

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

    private SmsTypeEnum(int state, String remark) {
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
        SmsTypeEnum[] values = SmsTypeEnum.values();

        for (SmsTypeEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }

    /**
     * 根据state获取枚举类型，在switch中使用
     * @param state
     * @return
     */
    public static SmsTypeEnum getEnumByState(int state) {
        SmsTypeEnum[] values = SmsTypeEnum.values();

        for (SmsTypeEnum temp : values) {
            if (temp.state == state) {
                return temp;
            }
        }
        return null;

    }
}
