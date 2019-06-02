package org.eq.modules.enums;

/**
 * @author admin
 * @Title: OrderTradeBlockChainStateEnum
 * @Copyright: Copyright (c) 2018
 * @Description: 区块链处理状态
 * @Company: 123.com
 * @Created on 2019/6/2上午11:18
 */
public enum OrderTradeBlockChainStateEnum {
    PROCESSING(1,"区块链处理中"),
    SUCCESS(2,"区块链处理成功"),
    FAIL(3,"区块链处理失败"),

    ;


    /**
     * 状态
     */
    private int state;
    /**
     * 具体备注
     */
    private String remark;

    OrderTradeBlockChainStateEnum(int state, String remark) {
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
}
