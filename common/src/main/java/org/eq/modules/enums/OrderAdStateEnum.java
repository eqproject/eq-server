package org.eq.modules.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * 广告订单状态
 * @author kaka
 * @date 2019-06-01
 */
public enum OrderAdStateEnum {

    ORDER_DEFAULT(1,"待审核"),
    ORDER_CANCEL(2,"取消"),
    ORDER_TRADEING(3,"交易中"),
    ORDER_FINISH(4,"完成"),
    ORDER_REJECT(5,"审核不通过");


    private static Set<Integer> overStatus = new HashSet<>();

    /**
     * 有效状态
     */
    private static Set<Integer> effectiveStatus = new HashSet<>();

    static {
        overStatus.add(OrderAdStateEnum.ORDER_CANCEL.getState());
        overStatus.add(OrderAdStateEnum.ORDER_FINISH.getState());
        overStatus.add(OrderAdStateEnum.ORDER_REJECT.getState());

        effectiveStatus.add(OrderAdStateEnum.ORDER_TRADEING.getState());

    }


    OrderAdStateEnum(int state, String remark) {
        this.state = state;
        this.remark = remark;
    }

    private int state;
    private String remark;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取枚举类介绍
     * @param state
     * @return
     */
    public static String  getRemarkByState(int state){
        OrderAdStateEnum[] values = OrderAdStateEnum.values();
        for(OrderAdStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }

    /**
     * 判断状态是否到达最终状态
     * @param state
     * @return
     */
    public static  boolean isOverState(int state){
        return overStatus.contains(state);
    }


    /**
     * 判断状态是否有效
     * @param state
     * @return
     */
    public static  boolean isEffectState(int state){
        return effectiveStatus.contains(state);
    }


}
