/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品相关信息复合类
 * @author kaka
 * @version 1.0.1
 */
@Data
public class ProductAll  extends Product implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 发行商名称
	 */
	private String issuerName;

	/**
	 * 发行商图片
	 */
	private String issuerIcon;
	/**
	 * 发行商区块链地址
	 */
	private String issuerAddress;
	/**
	 * 发行商简介
	 */
	private String issuerIntro;

	/**
	 * 承兑商名称
	 */
	private String acceptName;
	/**
	 * 承兑商图片
	 */
	private String acceptIcon;
	/**
	 * 承兑商方区块链地址
	 */
	private String acceptAddress;
	/**
	 *  承兑商简介
	 */
	private String acceptIntro;

	/**
	 * 资产code
	 */
	private String assetCode;
	/**
	 *  资产发行人
	 */
	private String assetIssuer;
	/**
	 * 合约地址
	 */
	private String contractAddress;
	/**
	 * 区块链券ID
	 */
	private String ticketid;
	/**
	 * 区块链券有效期分组ID
	 */
	private String trancheid;

	/**
	 * 商品数量
	 */
	private int number;





}