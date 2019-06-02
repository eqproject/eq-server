package org.eq.modules.trade.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 * @Title: OrderTradeDetailUser
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/2下午2:23
 */
@Data
public class OrderTradeDetailUser implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long sellUserId;
    private String sellUserNickName;
    private String sellUserName;
    private String sellUserAccount;
    private Long buyUserId;
    private String buyUserNickName;



}
