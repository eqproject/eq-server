package org.eq.modules.enums;

public enum BindStatusEnum {
    YES(1, "绑定"),
    NO(2, "解绑");

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

    private BindStatusEnum(int state, String remark) {
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
        BindStatusEnum[] values = BindStatusEnum.values();

        for (BindStatusEnum temp : values) {
            if (temp.state == state) {
                return temp.remark;
            }
        }
        return null;

    }
}
