package org.eq.enums;

/**
 * 商品状态枚举类
 * @author  kaka
 * @date  0526
 */
public enum ProductStateEnum {
    DEFAULT(0,"待上架状态"),
    ONLINE(1,"上线状态"),
    OFFLINE(2,"下线状态");

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

    ProductStateEnum(int state, String remark){
        this.remark = remark;
        this.state = state;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        ProductStateEnum[] values = ProductStateEnum.values();
        for(ProductStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }


}
