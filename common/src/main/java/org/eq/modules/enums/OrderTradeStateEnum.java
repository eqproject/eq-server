package org.eq.modules.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * @Title: OrderTradeStateEnum
 * @Copyright: Copyright (c) 2018
 * @Description: 订单交易状态
 * @Company: 123.com
 * @Created on 2019/6/2上午11:02
 */
public enum OrderTradeStateEnum {

    WAIT_PAY(1,"待支付"),
    CANCEL(2,"取消交易"),
    PAY_ING(3,"支付中"),
    PAY_SUCCESS(4,"支付成功"),
    PAY_FAIL(5,"支付失败"),
    CANCEL_PAY_TIMEOUT(6,"关闭交易(支付超时)"),
    VOUCHER_ING(7,"放券中"),
    LOAN_ING(8,"放款中"),
    LOAN_FAIL(9,"放款失败"),
    TRADE_SUCCESS(10,"交易成功"),
    REFUND_ING(11,"退款中"),
    REFUND_SUCCESS(12,"退款成功");

    /**
     * 可发起支付状态集合
     */
    private static Set<Integer> runPayStatus = new HashSet<>();

    /**
     * 允许可取消的订单
     */
    private static Set<Integer> allowCancelStatus = new HashSet<>();

    /**
     * 未结束的状态
     */
    private static Set<Integer> runningStatus = new HashSet<>();

    static {
        runPayStatus.add(OrderTradeStateEnum.WAIT_PAY.getState());
        runPayStatus.add(OrderTradeStateEnum.PAY_FAIL.getState());

        allowCancelStatus.add(OrderTradeStateEnum.CANCEL.getState());
        allowCancelStatus.add(OrderTradeStateEnum.PAY_FAIL.getState());

        runningStatus.add(OrderTradeStateEnum.WAIT_PAY.getState());
        runningStatus.add(OrderTradeStateEnum.PAY_ING.getState());
        runningStatus.add(OrderTradeStateEnum.PAY_SUCCESS.getState());
        runningStatus.add(OrderTradeStateEnum.PAY_FAIL.getState());
        runningStatus.add(OrderTradeStateEnum.VOUCHER_ING.getState());
        runningStatus.add(OrderTradeStateEnum.LOAN_ING.getState());
        runningStatus.add(OrderTradeStateEnum.LOAN_FAIL.getState());
        runningStatus.add(OrderTradeStateEnum.REFUND_ING.getState());

    }


    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;


    OrderTradeStateEnum(int state, String remark) {
        this.state = state;
        this.remark = remark;
    }

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
        OrderTradeStateEnum[] values = OrderTradeStateEnum.values();
        for(OrderTradeStateEnum temp : values ){
            if(temp.state == state){
                return temp.remark;
            }
        }
        return null;
    }

    /**
     * 判断是否允许发起支付
     * @param state
     * @return
     */
    public static  boolean isRunPay(int state){
        return runPayStatus.contains(state);
    }

    /**
     * 判断是否允许用户自主取消
     * @param state
     * @return
     */
    public static  boolean isAllCancel(int state){
        return allowCancelStatus.contains(state);
    }

    /**
     * 判断是否正在进行
     * @param state
     * @return
     */
    public static  boolean isRunningState(int state){
        return runningStatus.contains(state);
    }

    /**
     * 获取正在进行中的集合
     * @return
     */
    public static List<Integer> getRunningStates(){
        return new ArrayList<>(runningStatus);
    }


}
