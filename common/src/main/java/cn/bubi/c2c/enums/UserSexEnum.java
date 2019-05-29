package cn.bubi.c2c.enums;

/**
 * 用户订单状态枚举类
 */
public enum UserSexEnum {
    UNKNOW(0,"未知"),
    MALE(1,"男"),
    FEMALE(2,"女");

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

    private UserSexEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        UserSexEnum[] values = UserSexEnum.values();

        for(UserSexEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
