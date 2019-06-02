package org.eq.modules.enums;

public enum DocTypeEnum {
    TERMS(1,"服务条款"),
    LEGAL(2,"法务支持"),
    BUY_DOC(3,"求购文案");

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

    private DocTypeEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        DocTypeEnum[] values = DocTypeEnum.values();

        for(DocTypeEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;

    }
}
