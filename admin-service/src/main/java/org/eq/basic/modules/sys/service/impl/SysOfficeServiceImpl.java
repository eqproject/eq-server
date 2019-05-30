/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.modules.sys.dao.SysOfficeMapper;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysOfficeExample;
import org.eq.basic.modules.sys.security.UserInfo;
import org.eq.basic.modules.sys.service.SysOfficeService;

/**
 * 机构表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysOfficeServiceImpl extends ServiceImplExtend<SysOfficeMapper, SysOffice, SysOfficeExample>
        implements SysOfficeService {

    @Override
    public SysOfficeExample getExampleFromEntity(SysOffice sysOffice, Map<String, Object> params) {

        SysOfficeExample example = new SysOfficeExample();
        SysOfficeExample.Criteria ca = example.or();
        if (sysOffice == null) {
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
        if (sysOffice.getId() != null) {
            ca.andIdEqualTo(sysOffice.getId());
        }
        if (sysOffice.getParentId() != null) {
            ca.andParentIdEqualTo(sysOffice.getParentId());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getParentIds())) {
            ca.andParentIdsEqualTo(sysOffice.getParentIds());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getName())) {
            ca.andNameLike("%" + sysOffice.getName() + "%");
        }
        if (StringLowUtils.isNotBlank(sysOffice.getCode())) {
            ca.andCodeLike("%" + sysOffice.getCode() + "%");
        }
        if (sysOffice.getSort() != null) {
            ca.andSortEqualTo(sysOffice.getSort());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getType())) {
            ca.andTypeEqualTo(sysOffice.getType());
        }
        if (sysOffice.getGrade() != null) {
            ca.andGradeEqualTo(sysOffice.getGrade());
        }
        if (sysOffice.getAreaId() != null) {
            ca.andAreaIdEqualTo(sysOffice.getAreaId());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getAddress())) {
            ca.andAddressEqualTo(sysOffice.getAddress());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getPhone())) {
            ca.andPhoneEqualTo(sysOffice.getPhone());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getEmail())) {
            ca.andEmailEqualTo(sysOffice.getEmail());
        }
        if (sysOffice.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysOffice.getCreateBy());
        }
        if (sysOffice.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysOffice.getCreateDate());
        }
        if (sysOffice.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysOffice.getUpdateBy());
        }
        if (sysOffice.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysOffice.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getRemarks())) {
            ca.andRemarksEqualTo(sysOffice.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysOffice.getDelFlag())) {
            ca.andDelFlagEqualTo(sysOffice.getDelFlag());
        }
        return example;
    }

    @Override
    public List<SysOffice> findListPermission(UserInfo userInfo, SysOffice sysOffice) {

        SysOfficeExample sysOfficeExample = new SysOfficeExample();
        for (SysOffice so : UserUtil.getInstance().getUserInfo().getSysOfficeList()) {
            sysOfficeExample.getOredCriteria().add(this.getExampleFromEntity(sysOffice, null).getOredCriteria().get(0)
                    .andParentIdsLike(so.getParentIds() + so.getId() + ",%"));
            sysOfficeExample.getOredCriteria()
                    .add(this.getExampleFromEntity(sysOffice, null).getOredCriteria().get(0).andIdEqualTo(so.getId()));
        }
        return this.getMapper().selectByExample(sysOfficeExample);
    }
}