/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.entity;

import lombok.Data;

/**
 * 文案相关Entity
 * @author hobe
 * @version 2019-06-02
 */
@Data
public class Support{

	private static final long serialVersionUID = 1L;
	private Long id;		// 主键
	private Integer grouping;		// 分组
	private Integer type;		// 字典类型(1:服务条款,2:法务支持,3:求购文案,4:出售文案,5:实名认证)
	private String content;		// 显示文案内容
	private Integer value;		// 具体数值
	private Integer state;		// 状态(0:正常 1:删除)

}