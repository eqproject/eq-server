/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.service.impl;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.StringLowUtils;
import cn.bubi.c2c.product.dao.TagMapper;
import cn.bubi.c2c.product.entity.Tag;
import cn.bubi.c2c.product.entity.TagExample;
import cn.bubi.c2c.product.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 标签管理ServiceImpl
 * @author kaka
 * @version 1.0.1
 */
@Service
@Transactional
@AutowiredService
public class TagServiceImpl extends ServiceImplExtend<TagMapper, Tag, TagExample> implements TagService {

	@Override
	public TagExample getExampleFromEntity(Tag tag, Map<String, Object> params) {
		TagExample example = new TagExample();
		TagExample.Criteria ca = example.or();
		if(tag==null){
			return example;
		}
		String orderName = null;
		String orderDir = null;
		if(params!=null){
			orderDir = (String)params.get("orderDir");
			orderName = (String)params.get("orderName");
		}
		if(orderName!=null&&!"".equals(orderName)){
			example.setOrderByClause(orderName+" "+orderDir);
		}else{
			example.setOrderByClause("id asc");
		}
		if(tag.getId()!=null){
			ca.andIdEqualTo(tag.getId());
		}
		if(StringLowUtils.isNotBlank(tag.getName())){
			ca.andNameLike(tag.getName());
		}
		if(tag.getStatus()!=null){
			ca.andStateEqualTo(tag.getStatus());
		}
		return example;
	}

}