package org.eq.modules.enums;

/**
 * 商品状态枚举类
 * @author  kaka
 * @date  0526
 */
public enum SystemConfigStateEnum {
    DEFAULT(0,"正常状态"),
    OFFLINE(1,"下线状态");

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

    SystemConfigStateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        SystemConfigStateEnum[] values = SystemConfigStateEnum.values();
        for(SystemConfigStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }

}
