package cn.bubi.c2c.enums;

/**
 * 用户订单状态枚举类
 */
public enum UserStateEnum {
    AUTHENTICATION_NO(1,"未认证"),
    AUTHENTICATION_YES(2,"已认证");

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

    private UserStateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        UserStateEnum[] values = UserStateEnum.values();

        for(UserStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
