package org.eq.modules.enums;

public enum WalletStateEnum {
    NO_ACTIVE(0, "未激活"),
    ACTIVE(1, "已激活");

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

    private WalletStateEnum(int state, String remark) {
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
        WalletStateEnum[] values = WalletStateEnum.values();

        for (WalletStateEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }
}
