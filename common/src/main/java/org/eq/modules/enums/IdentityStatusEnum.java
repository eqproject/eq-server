package org.eq.modules.enums;

/**
 * 实名认证状态
 */
public enum IdentityStatusEnum {
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

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

    private IdentityStatusEnum(int state, String remark) {
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
        IdentityStatusEnum[] values = IdentityStatusEnum.values();

        for (IdentityStatusEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }

}
