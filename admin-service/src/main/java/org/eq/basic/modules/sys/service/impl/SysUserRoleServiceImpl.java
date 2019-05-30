/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.Map;

import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.modules.sys.dao.SysUserRoleMapper;
import org.eq.basic.modules.sys.entity.SysUserRole;
import org.eq.basic.modules.sys.entity.SysUserRoleExample;
import org.eq.basic.modules.sys.service.SysUserRoleService;

/**
 * 用户角色表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysUserRoleServiceImpl extends ServiceImplExtend<SysUserRoleMapper, SysUserRole, SysUserRoleExample>
        implements SysUserRoleService {

    @Override
    public SysUserRoleExample getExampleFromEntity(SysUserRole sysUserRole, Map<String, Object> params) {

        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria ca = example.or();
        if (sysUserRole == null) {
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
        if (sysUserRole.getId() != null) {
            ca.andIdEqualTo(sysUserRole.getId());
        }
        if (sysUserRole.getUserId() != null) {
            ca.andUserIdEqualTo(sysUserRole.getUserId());
        }
        if (sysUserRole.getRoleId() != null) {
            ca.andRoleIdEqualTo(sysUserRole.getRoleId());
        }
        if (sysUserRole.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysUserRole.getCreateBy());
        }
        if (sysUserRole.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysUserRole.getCreateDate());
        }
        if (sysUserRole.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysUserRole.getUpdateBy());
        }
        if (sysUserRole.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysUserRole.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysUserRole.getRemarks())) {
            ca.andRemarksEqualTo(sysUserRole.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysUserRole.getDelFlag())) {
            ca.andDelFlagEqualTo(sysUserRole.getDelFlag());
        }
        return example;
    }

}