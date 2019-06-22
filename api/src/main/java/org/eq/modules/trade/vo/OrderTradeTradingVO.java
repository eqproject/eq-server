package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 正在交易实体
 */
@Data
public class OrderTradeTradingVO extends OrderTradeBaseVO implements Serializable{

    private static final long serialVersionUID = 1L;


    /**
     * 交易最大时长
     */
    private int payTimeOut;

    /**
     * 状态描述
     */
    private String stateRemark;

    /**
     * 用户昵称
     */
    private  String userNickName;

    /**
     * 用户头像
     */
    private String photoHead;

    /**
     * 是否允许申诉
     */
    private int allAppeal;



}
