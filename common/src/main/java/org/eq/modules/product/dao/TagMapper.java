/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.dao;

import org.eq.basic.common.base.BaseMapper;
import org.eq.modules.product.entity.Tag;
import org.eq.modules.product.entity.TagExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签管理Mapper接口
 * @author kaka
 * @version 1.0.1
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag, TagExample> {
	
}