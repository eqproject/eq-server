package org.eq.modules.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 商品状态枚举类
 * @author  kaka
 * @date  0526
 */
public enum ProductStateEnum {
    DEFAULT(0,"待上架状态"),
    ONLINE(1,"上线状态"),
    OVERDUE(2,"过期状态"),
    OFFLINE(3,"下线状态");

    /**
     * 终止状态集合
     */
    private static Set<Integer> overState = new HashSet<>();

    /**
     * 中间状态集合
     */
    private static Set<Integer> runState = new HashSet<>();

    static{
        overState.add(ProductStateEnum.OVERDUE.getState());
        overState.add(ProductStateEnum.OFFLINE.getState());

        runState.add(ProductStateEnum.DEFAULT.getState());
        runState.add(ProductStateEnum.ONLINE.getState());
    }


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

    public static boolean isOverState(int state){
        return overState.contains(state);
    }

    /**
     * 获取中间状态集合
     * @return
     */
    public static List<Integer> getRunStatus(){
        return new ArrayList<>(runState);
    }


}
