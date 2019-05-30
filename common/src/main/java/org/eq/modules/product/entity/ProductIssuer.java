/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;
import org.eq.basic.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class ProductIssuer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 发行商名称
     */
	private String name;

    /**
     * 发行方图片
     */
	private String icon;
    /**
     * 发行方区块链地址
     */
	private String address;
    /**
     * 简介
     */
	private String intro;
    /**
     * 创建日期
     */
    protected Date createDate;

    /**
     * 更新日期
     */
    protected Date updateDate;

}