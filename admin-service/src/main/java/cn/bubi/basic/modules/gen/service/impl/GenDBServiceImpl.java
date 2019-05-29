/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.SpringContextUtil;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.gen.bean.DBFactory;
import cn.bubi.basic.gen.config.WebManger;
import cn.bubi.basic.gen.entity.Table;
import cn.bubi.basic.gen.entity.TableColumn;
import cn.bubi.basic.gen.status.DBTypeEnum;
import cn.bubi.basic.modules.gen.dao.GenDBMapper;
import cn.bubi.basic.modules.gen.entity.GenDB;
import cn.bubi.basic.modules.gen.entity.GenDBExample;
import cn.bubi.basic.modules.gen.entity.GenTableModal;
import cn.bubi.basic.modules.gen.service.GenDBService;

/**
 * 代码生成数据库ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class GenDBServiceImpl extends ServiceImplExtend<GenDBMapper, GenDB, GenDBExample> implements GenDBService {

    @Override
    public GenDBExample getExampleFromEntity(GenDB genDB, Map<String, Object> params) {

        GenDBExample example = new GenDBExample();
        GenDBExample.Criteria ca = example.or();
        if (genDB == null) {
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
        if (genDB.getId() != null) {
            ca.andIdEqualTo(genDB.getId());
        }
        if (StringLowUtils.isNotBlank(genDB.getName())) {
            ca.andNameEqualTo(genDB.getName());
        }
        if (StringLowUtils.isNotBlank(genDB.getDbname())) {
            ca.andDbnameEqualTo(genDB.getDbname());
        }
        if (StringLowUtils.isNotBlank(genDB.getUrl())) {
            ca.andUrlEqualTo(genDB.getUrl());
        }
        if (StringLowUtils.isNotBlank(genDB.getUsername())) {
            ca.andUsernameEqualTo(genDB.getUsername());
        }
        if (StringLowUtils.isNotBlank(genDB.getPassword())) {
            ca.andPasswordEqualTo(genDB.getPassword());
        }
        if (StringLowUtils.isNotBlank(genDB.getDriverClassName())) {
            ca.andDriverClassNameEqualTo(genDB.getDriverClassName());
        }
        if (StringLowUtils.isNotBlank(genDB.getType())) {
            ca.andTypeEqualTo(genDB.getType());
        }
        if (genDB.getCreateBy() != null) {
            ca.andCreateByEqualTo(genDB.getCreateBy());
        }
        if (genDB.getCreateDate() != null) {
            ca.andCreateDateEqualTo(genDB.getCreateDate());
        }
        if (genDB.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(genDB.getUpdateBy());
        }
        if (genDB.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(genDB.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(genDB.getRemarks())) {
            ca.andRemarksEqualTo(genDB.getRemarks());
        }
        if (StringLowUtils.isNotBlank(genDB.getDelFlag())) {
            ca.andDelFlagEqualTo(genDB.getDelFlag());
        }
        return example;
    }

    @Override
    public BaseTableData findDBTableForPage(Map<String, Object> params, String dbMangerName) {

        BaseTableData<Table> baseTableData = new BaseTableData();
        PageInfo<Table> pageInfo = new PageInfo<>();
        // 从params 里获取分页信息
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        String order = "";
        if (params.get("orderName") != null) {
            order += " order by " + (String) params.get("orderName") + " " + (String) params.get("orderDir");
        }
        params.put("order", order);
        WebManger webManger = null;
        if (SpringContextUtil.getBean(dbMangerName) != null) {
            webManger = (WebManger) SpringContextUtil.getBean(dbMangerName);
        }
        if (webManger != null) {
            if (webManger.getDbManger().getDbTypeEnum() == DBTypeEnum.DB2) {
                params.put("order", "order by tableName");
            }
            pageInfo = webManger.selectTableByPage(pageNum, pageSize, params);
        }
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }

    @Override
    public BaseTableData findDBTableColumnForPage(Map<String, Object> params, String dbMangerName) {

        BaseTableData<TableColumn> baseTableData = new BaseTableData();
        PageInfo<TableColumn> pageInfo = new PageInfo<>();
        // 从params 里获取分页信息
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        String order = "";
        if (params.get("orderName") != null) {
            order += " order by " + (String) params.get("orderName") + " " + (String) params.get("orderDir");
        }
        params.put("order", order);
        WebManger webManger = null;
        if (SpringContextUtil.getBean(dbMangerName) != null) {
            webManger = (WebManger) SpringContextUtil.getBean(dbMangerName);
        }
        ;
        if (webManger != null) {
            pageInfo = webManger.selectTableColumnByPage(pageNum, pageSize, params);
        }
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }

    @Override
    public List<Table> findDBTable(GenDB genDB) {

        List<Table> tableList = new ArrayList<>();
        WebManger webManger = this.getDBConfig(genDB);
        if (webManger != null) {
            tableList = webManger.selectTable();
        }
        return tableList;
    }

    @Override
    public List<TableColumn> findDBTableColumn(GenTableModal tableColumn) {

        List<TableColumn> tableColumnList = new ArrayList<>();
        WebManger webManger = null;
        Map<String, Object> param = new HashMap<>();
        param.put("tableName", tableColumn.getTableName());
        String order = " order by column_name asc";
        param.put("order", order);
        try {
            webManger = (WebManger) SpringContextUtil.getBean(tableColumn.getDbMangerName());
        } catch(Exception e) {
            GenDB genDB = this.selectByPrimaryKey(tableColumn.getDbId());
            DBFactory.addBean(new WebManger(genDB));
            webManger = (WebManger) SpringContextUtil.getBean(tableColumn.getDbMangerName());
        }
        if (webManger != null) {
            tableColumnList = webManger.selectTableColumn(param);
        }
        return tableColumnList;
    }

    @Override
    public WebManger getDBConfig(GenDB genDB) {

        WebManger webManger = null;
        genDB = this.selectByRecord(genDB);
        try {
            webManger = (WebManger) SpringContextUtil.getBean(genDB.getName());
        } catch(Exception e) {
            DBFactory.addBean(new WebManger(genDB));
            webManger = (WebManger) SpringContextUtil.getBean(genDB.getName());
        }
        return webManger;
    }
}