/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.modules.sys.dao.SqlMapper;
import cn.bubi.basic.modules.sys.dao.SysDictMapper;
import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.entity.SysDictExample;
import cn.bubi.basic.modules.sys.service.SysDictService;

/**
 * 数据字典表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysDictServiceImpl extends ServiceImplExtend<SysDictMapper, SysDict, SysDictExample>
        implements SysDictService {

    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public SysDictExample getExampleFromEntity(SysDict sysDict, Map<String, Object> params) {

        SysDictExample example = new SysDictExample();
        SysDictExample.Criteria ca = example.or();
        if (sysDict == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (sysDict.getId() != null) {
            ca.andIdEqualTo(sysDict.getId());
        }
        if (StringLowUtils.isNotBlank(sysDict.getValue())) {
            ca.andValueEqualTo(sysDict.getValue());
        }
        if (StringLowUtils.isNotBlank(sysDict.getLabel())) {
            ca.andLabelLike("%" + sysDict.getLabel() + "%");
        }
        if (StringLowUtils.isNotBlank(sysDict.getType())) {
            ca.andTypeEqualTo(sysDict.getType());
        }
        if (StringLowUtils.isNotBlank(sysDict.getDescription())) {
            ca.andDescriptionLike("%" + sysDict.getDescription() + "%");
        }
        if (sysDict.getSort() != null) {
            ca.andSortEqualTo(sysDict.getSort());
        }
        if (sysDict.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysDict.getCreateBy());
        }
        if (sysDict.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysDict.getCreateDate());
        }
        if (sysDict.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysDict.getUpdateBy());
        }
        if (sysDict.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysDict.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysDict.getRemarks())) {
            ca.andRemarksEqualTo(sysDict.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysDict.getDelFlag())) {
            ca.andDelFlagEqualTo(sysDict.getDelFlag());
        }
        return example;
    }

    @Override
    public List<Map> findDictType() {

        String sql = "SELECT DISTINCT sd.description description,sd.type type FROM sys_dict sd WHERE sd.del_flag <>1 and sd.description IS NOT NULL order by sd.description";
        List<Map> result = this.sqlMapper.superManagerSelect(sql);
        if (result != null) {
            return result;
        }
        return null;
    }

    @Override
    public List<Map> exceImportSql(String sql) {

        List<Map> result = this.sqlMapper.superManagerSelect(sql);
        if (result != null) {
            return result;
        }
        return null;
    }
}