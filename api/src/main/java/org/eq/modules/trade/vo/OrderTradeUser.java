package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeUser
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:23
 */
@Data
public class OrderTradeUser implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 售卖用户ID
     */
    private Long sellUserId;
    /**
     * 售卖用户昵称
     */
    private String sellUserNickName;

    /**
     * 购买用户ID
     */
    private Long buyUserId;

    /**
     * 购买用户昵称
     */
    private String buyUserNickName;


    /**
     * 头像
     */
    private String phoneHead;


    /**
     * 认证状态
     * @see org.eq.modules.enums.UserStateEnum
     */
    private int authstatus;



}
