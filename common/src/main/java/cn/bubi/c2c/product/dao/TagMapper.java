/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.c2c.product.entity.Tag;
import cn.bubi.c2c.product.entity.TagExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签管理Mapper接口
 * @author kaka
 * @version 1.0.1
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag, TagExample> {
	
}