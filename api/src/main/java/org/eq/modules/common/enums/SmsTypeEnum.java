package org.eq.modules.common.enums;

public enum SmsTypeEnum {
    REGISTER(1, "登陆注册验证码"), AD(2, "发布广告成功通知"), SALE_SUCCESS(3, "数字券已转到券包通知"),
    SUCCESS(4, "数字券已成功转出通知"), ORDER(5, "订单确认通知");

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
     *
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
