/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * 标签管理Entity
 * @author kaka
 * @version 1.0.1
 */
@Data
public class Tag {

	private static final long serialVersionUID = 1L;
	/**
	 * 标签ID
	 */
	private Long id;
	/**
	 * 标签名称
	 */
	private String name;

	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新人
	 */
	private Long updateBy;

	/**
	 * 更新日期
	 */
	private Date updateDate;


	/**
	 * 备注
	 */
	private String remarks;


	/**
	 * 标签状态
	 * @see  cn.bubi.c2c.enums.TagStateEnum;
	 */
	private Integer status;


	/**
	 * 创建人名称
	 */
	private String createName;


}