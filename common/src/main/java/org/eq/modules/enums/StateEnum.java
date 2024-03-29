package org.eq.modules.enums;

public enum StateEnum {
    VALID(0,"正常"),
    DEL(1,"删除");

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

    private StateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        StateEnum[] values = StateEnum.values();

        for(StateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }
}
