/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.bc.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.util.Date;

/**
 * 区块链任务Entity
 * @author gb
 * @version 2019-06-03
 */
@Data
public class InitiatorAcc {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String address;        // 账户地址
    private String publicKey;        // 公钥
    private String keyStore;        // 加密私钥
    private Integer type;        // 0交易发起人
    private Integer status;        // 0可用  1不可用
    private Date createTime;
    private Date updateTime;
}