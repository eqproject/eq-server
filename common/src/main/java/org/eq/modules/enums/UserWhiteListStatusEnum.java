package org.eq.modules.enums;

/**
 * 用户白名单状态枚举类
 */
public enum UserWhiteListStatusEnum {
    NO(0,"未关联"),
    YES(1,"已关联");

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

    private UserWhiteListStatusEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        UserWhiteListStatusEnum[] values = UserWhiteListStatusEnum.values();

        for(UserWhiteListStatusEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
