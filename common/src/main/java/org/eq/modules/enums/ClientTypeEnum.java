package org.eq.modules.enums;

/**
 * 客户端状态枚举类
 */
public enum ClientTypeEnum {
    C(1,"C端用户"),
    B(2,"B端用户");

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

    private ClientTypeEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        ClientTypeEnum[] values = ClientTypeEnum.values();

        for(ClientTypeEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }


}
