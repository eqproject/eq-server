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
 *
 * @author gb
 * @version 2019-06-03
 */
@Data
public class BcTxException {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String txHash;        // 交易hash
    private Integer actionStatus;        // 0未处理 1已处理
    private Date createTime;
    private Date updateTime;
}