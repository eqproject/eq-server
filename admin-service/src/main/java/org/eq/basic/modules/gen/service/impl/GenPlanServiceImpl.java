/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.SpringContextUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.gen.bean.DBFactory;
import org.eq.basic.gen.config.GenConfig;
import org.eq.basic.gen.config.WebManger;
import org.eq.basic.gen.entity.TableDataModal;
import org.eq.basic.modules.gen.dao.GenDBMapper;
import org.eq.basic.modules.gen.dao.GenPlanMapper;
import org.eq.basic.modules.gen.dao.GenTableModalMapper;
import org.eq.basic.modules.gen.entity.GenDB;
import org.eq.basic.modules.gen.entity.GenPlan;
import org.eq.basic.modules.gen.entity.GenPlanExample;
import org.eq.basic.modules.gen.entity.GenTableModal;
import org.eq.basic.modules.gen.entity.GenTableModalExample;
import org.eq.basic.modules.gen.service.GenPlanService;

/**
 * 代码生成计划ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class GenPlanServiceImpl extends ServiceImplExtend<GenPlanMapper, GenPlan, GenPlanExample>
        implements GenPlanService {

    @Autowired
    private GenTableModalMapper genTableModalMapper;

    @Autowired
    private GenDBMapper genDBMapper;

    @Override
    public GenPlanExample getExampleFromEntity(GenPlan genPlan, Map<String, Object> params) {

        GenPlanExample example = new GenPlanExample();
        GenPlanExample.Criteria ca = example.or();
        if (genPlan == null) {
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
        if (genPlan.getId() != null) {
            ca.andIdEqualTo(genPlan.getId());
        }
        if (StringLowUtils.isNotBlank(genPlan.getPlanName())) {
            ca.andPlanNameLike("%" + genPlan.getPlanName() + "%");
        }
        if (StringLowUtils.isNotBlank(genPlan.getBasicPackage())) {
            ca.andBasicPackageEqualTo(genPlan.getBasicPackage());
        }
        if (StringLowUtils.isNotBlank(genPlan.getLocation())) {
            ca.andLocationEqualTo(genPlan.getLocation());
        }
        if (StringLowUtils.isNotBlank(genPlan.getVersion())) {
            ca.andVersionEqualTo(genPlan.getVersion());
        }
        if (genPlan.getCreateBy() != null) {
            ca.andCreateByEqualTo(genPlan.getCreateBy());
        }
        if (genPlan.getCreateDate() != null) {
            ca.andCreateDateEqualTo(genPlan.getCreateDate());
        }
        if (genPlan.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(genPlan.getUpdateBy());
        }
        if (genPlan.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(genPlan.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(genPlan.getRemarks())) {
            ca.andRemarksEqualTo(genPlan.getRemarks());
        }
        if (StringLowUtils.isNotBlank(genPlan.getDelFlag())) {
            ca.andDelFlagEqualTo(genPlan.getDelFlag());
        }
        return example;
    }

    @Override
    public boolean codeMake(GenPlan genPlan) {

        if (genPlan != null && genPlan.getId() != null) {
            // 查出所有计划的方案 逐个生成
            genPlan = this.getMapper().selectByPrimaryKey(genPlan.getId());
            GenTableModalExample example = new GenTableModalExample();
            example.or().andPlanIdEqualTo(genPlan.getId()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            List<GenTableModal> genTableModalList = this.genTableModalMapper.findTableModal(example);
            for (GenTableModal tableModal : genTableModalList) {
                String dbMangerName = tableModal.getDbMangerName();
                if (dbMangerName != null) {
                    WebManger webManger = null;
                    try {
                        webManger = (WebManger) SpringContextUtil.getBean(dbMangerName);
                    } catch(Exception e) {
                        GenDB genDB = this.genDBMapper.selectByPrimaryKey(tableModal.getDbId());
                        DBFactory.addBean(new WebManger(genDB));
                        webManger = (WebManger) SpringContextUtil.getBean(dbMangerName);
                    }
                    if (webManger != null) {
                        GenConfig config = webManger.getGenConfig();
                        // 指定表信息 作者 版本 时间 如果没有配置取properties文件 properties文件没有则为空
                        TableDataModal tableDataModal = new TableDataModal();
                        tableDataModal = new TableDataModal();
                        tableDataModal.setFunctionName(tableModal.getFunctionName());
                        tableDataModal.setCategory(tableModal.getCategory());
                        tableDataModal.setAuthor(tableModal.getAuthor());
                        tableDataModal.setVersion(tableModal.getVersion());
                        tableDataModal.getTable().setTableName(tableModal.getTableName());
                        tableDataModal.setClassName(tableModal.getClassName());
                        tableDataModal.setModuleName(tableModal.getModuleName());
                        config.setTableNames(new ArrayList<>());
                        config.addTable(tableDataModal);
                        if (StringLowUtils.isNotBlank(genPlan.getLocation())) {
                            config.setLocation(genPlan.getLocation());
                        }
                        if (StringLowUtils.isNotBlank(genPlan.getBasicPackage())) {
                            config.setBasicPackage(genPlan.getBasicPackage());
                        }
                        if (!webManger.generator()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}