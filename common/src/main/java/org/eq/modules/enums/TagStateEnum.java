package org.eq.modules.enums;

/**
 * 标签状态枚举类
 * @author  kaka
 * @date  0526
 */
public enum TagStateEnum {
    DEFAULT(0,"启用"),
    DEL(1,"禁用");

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

    TagStateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        TagStateEnum[] values = TagStateEnum.values();
        for(TagStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }


}
