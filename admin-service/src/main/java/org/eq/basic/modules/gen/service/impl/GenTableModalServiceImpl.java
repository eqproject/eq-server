/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.SpringContextUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.gen.bean.DBFactory;
import org.eq.basic.gen.config.GenConfig;
import org.eq.basic.gen.config.WebManger;
import org.eq.basic.gen.entity.TableDataModal;
import org.eq.basic.gen.entity.page.PageCurdModal;
import org.eq.basic.modules.gen.dao.GenDBMapper;
import org.eq.basic.modules.gen.dao.GenPlanMapper;
import org.eq.basic.modules.gen.dao.GenTableModalMapper;
import org.eq.basic.modules.gen.entity.GenDB;
import org.eq.basic.modules.gen.entity.GenPlan;
import org.eq.basic.modules.gen.entity.GenTableModal;
import org.eq.basic.modules.gen.entity.GenTableModalExample;
import org.eq.basic.modules.gen.service.GenTableModalService;

/**
 * 代码生成表配置ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class GenTableModalServiceImpl extends
        ServiceImplExtend<GenTableModalMapper, GenTableModal, GenTableModalExample> implements GenTableModalService {

    @Autowired
    private GenDBMapper genDBMapper;

    @Autowired
    private GenPlanMapper genPlanMapper;

    @Override
    public GenTableModalExample getExampleFromEntity(GenTableModal genTableModal, Map<String, Object> params) {

        GenTableModalExample example = new GenTableModalExample();
        GenTableModalExample.Criteria ca = example.or();
        if (genTableModal == null) {
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
        if (genTableModal.getId() != null) {
            ca.andIdEqualTo(genTableModal.getId());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getModalName())) {
            ca.andModalNameLike("%" + genTableModal.getModalName() + "%");
        }
        if (genTableModal.getPlanId() != null) {
            ca.andPlanIdEqualTo(genTableModal.getPlanId());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getFunctionName())) {
            ca.andFunctionNameEqualTo(genTableModal.getFunctionName());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getAuthor())) {
            ca.andAuthorEqualTo(genTableModal.getAuthor());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getVersion())) {
            ca.andVersionEqualTo(genTableModal.getVersion());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getClassName())) {
            ca.andClassNameEqualTo(genTableModal.getClassName());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getModuleName())) {
            ca.andModuleNameEqualTo(genTableModal.getModuleName());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getIfInheritBase())) {
            ca.andIfInheritBaseEqualTo(genTableModal.getIfInheritBase());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getCategory())) {
            ca.andCategoryEqualTo(genTableModal.getCategory());
        }
        if (genTableModal.getDbId() != null) {
            ca.andDbIdEqualTo(genTableModal.getDbId());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getTableName())) {
            ca.andTableNameEqualTo(genTableModal.getTableName());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getPageSetting())) {
            ca.andPageSettingEqualTo(genTableModal.getPageSetting());
        }
        if (genTableModal.getCreateBy() != null) {
            ca.andCreateByEqualTo(genTableModal.getCreateBy());
        }
        if (genTableModal.getCreateDate() != null) {
            ca.andCreateDateEqualTo(genTableModal.getCreateDate());
        }
        if (genTableModal.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(genTableModal.getUpdateBy());
        }
        if (genTableModal.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(genTableModal.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getRemarks())) {
            ca.andRemarksEqualTo(genTableModal.getRemarks());
        }
        if (StringLowUtils.isNotBlank(genTableModal.getDelFlag())) {
            ca.andDelFlagEqualTo(genTableModal.getDelFlag());
        }
        return example;
    }

    @Override
    public BaseTableData findTableModalForPage(GenTableModal genTableModal, Map<String, Object> params) {

        GenTableModalExample example = this.getExampleFromEntity(genTableModal, params);
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");

        BaseTableData<GenTableModal> baseTableData = new BaseTableData();
        PageHelper.startPage(pageNum, pageSize, true);

        List<GenTableModal> result = this.getMapper().findTableModal(example);
        PageInfo pageInfo = new PageInfo(result);
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }

    @Override
    public boolean codeMake(GenTableModal genTableModal, GenPlan genPlan) {

        Map<String, Object> params = new HashMap<>();
        GenTableModalExample example = this.getExampleFromEntity(genTableModal, params);
        List<GenTableModal> list = this.getMapper().findTableModal(example);
        if (list != null && !( list.isEmpty())) {
            GenTableModal tableModal = list.get(0);
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
                    if (tableModal.getPageSetting() != null) {
                        Gson gson = new Gson();
                        tableDataModal.getPageTypeEnum()
                                .setPageModal(gson.fromJson(tableModal.getPageSetting(), PageCurdModal.class));
                    }
                    config.setTableNames(new ArrayList<>());
                    config.addTable(tableDataModal);
                    if (genPlan == null && genTableModal.getPlanId() != null) {
                        genPlan = this.genPlanMapper.selectByPrimaryKey(genTableModal.getPlanId());
                    }
                    if (genPlan != null) {
                        if (StringLowUtils.isNotBlank(genPlan.getLocation())) {
                            config.setLocation(genPlan.getLocation());
                        }
                        if (StringLowUtils.isNotBlank(genPlan.getBasicPackage())) {
                            config.setBasicPackage(genPlan.getBasicPackage());
                        }
                    }
                    return webManger.generator();
                }
            }
        }
        return false;
    }
}