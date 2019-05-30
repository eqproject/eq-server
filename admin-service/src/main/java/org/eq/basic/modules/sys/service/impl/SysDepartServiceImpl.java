/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.Map;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.modules.sys.dao.SysDepartMapper;
import org.eq.basic.modules.sys.entity.SysDepart;
import org.eq.basic.modules.sys.entity.SysDepartExample;
import org.eq.basic.modules.sys.security.UserInfo;
import org.eq.basic.modules.sys.service.SysDepartService;

/**
 * 部门表ServiceImpl
 *
 * @author JoinHan
 * @version 2018-03-30
 */
@Service
@Transactional
@AutowiredService
public class SysDepartServiceImpl extends ServiceImplExtend<SysDepartMapper, SysDepart, SysDepartExample>
        implements SysDepartService {

    @Override
    public SysDepartExample getExampleFromEntity(SysDepart sysDepart, Map<String, Object> params) {

        SysDepartExample example = new SysDepartExample();
        SysDepartExample.Criteria ca = example.or();
        if (sysDepart == null) {
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
        if (sysDepart.getId() != null) {
            ca.andIdEqualTo(sysDepart.getId());
        }
        if (StringLowUtils.isNotBlank(sysDepart.getName())) {
            ca.andNameLike("%" + sysDepart.getName() + "%");
        }
        if (StringLowUtils.isNotBlank(sysDepart.getCode())) {
            ca.andCodeLike("%" + sysDepart.getCode() + "%");
        }
        if (sysDepart.getOfficeId() != null) {
            ca.andOfficeIdEqualTo(sysDepart.getOfficeId());
        }
        if (StringLowUtils.isNotBlank(sysDepart.getType())) {
            ca.andTypeEqualTo(sysDepart.getType());
        }
        if (sysDepart.getSort() != null) {
            ca.andSortEqualTo(sysDepart.getSort());
        }
        if (sysDepart.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysDepart.getCreateBy());
        }
        if (sysDepart.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysDepart.getCreateDate());
        }
        if (sysDepart.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysDepart.getUpdateBy());
        }
        if (sysDepart.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysDepart.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysDepart.getRemarks())) {
            ca.andRemarksEqualTo(sysDepart.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysDepart.getDelFlag())) {
            ca.andDelFlagEqualTo(sysDepart.getDelFlag());
        }
        return example;
    }

    @Override
    public BaseTableData findDataTableByRecordPermission(UserInfo userInfo, SysDepart sysDepart,
                                                         Map<String, Object> params) {

        // 从params 里获取分页信息
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        SysDepartExample sysDepartExample = this.getExampleFromEntity(sysDepart, null);
        sysDepartExample.getOredCriteria().get(0).andOfficeIdIn(userInfo.getUnderOfficeIdList());

        BaseTableData<SysDepart> baseTableData = new BaseTableData();
        PageInfo pageInfo = this.findListByExampleForPage(sysDepartExample, pageNum / pageSize + 1, pageSize);
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }
}