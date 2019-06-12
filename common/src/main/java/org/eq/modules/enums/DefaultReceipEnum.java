package org.eq.modules.enums;

/**
 * 默认收款
 */
public enum DefaultReceipEnum {
    YES(1, "是"),
    NO(2, "否");
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

    private DefaultReceipEnum(int state, String remark) {
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
        DefaultReceipEnum[] values = DefaultReceipEnum.values();

        for (DefaultReceipEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }
}
