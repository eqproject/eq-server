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
import org.eq.basic.modules.sys.dao.SysRoleMenuMapper;
import org.eq.basic.modules.sys.entity.SysRoleMenu;
import org.eq.basic.modules.sys.entity.SysRoleMenuExample;
import org.eq.basic.modules.sys.service.SysRoleMenuService;

/**
 * 角色菜单表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysRoleMenuServiceImpl extends ServiceImplExtend<SysRoleMenuMapper, SysRoleMenu, SysRoleMenuExample>
        implements SysRoleMenuService {

    @Override
    public SysRoleMenuExample getExampleFromEntity(SysRoleMenu sysRoleMenu, Map<String, Object> params) {

        SysRoleMenuExample example = new SysRoleMenuExample();
        SysRoleMenuExample.Criteria ca = example.or();
        if (sysRoleMenu == null) {
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
        if (sysRoleMenu.getId() != null) {
            ca.andIdEqualTo(sysRoleMenu.getId());
        }
        if (sysRoleMenu.getRoleId() != null) {
            ca.andRoleIdEqualTo(sysRoleMenu.getRoleId());
        }
        if (sysRoleMenu.getMenuId() != null) {
            ca.andMenuIdEqualTo(sysRoleMenu.getMenuId());
        }
        if (sysRoleMenu.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysRoleMenu.getCreateBy());
        }
        if (sysRoleMenu.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysRoleMenu.getCreateDate());
        }
        if (sysRoleMenu.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysRoleMenu.getUpdateBy());
        }
        if (sysRoleMenu.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysRoleMenu.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysRoleMenu.getRemarks())) {
            ca.andRemarksEqualTo(sysRoleMenu.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysRoleMenu.getDelFlag())) {
            ca.andDelFlagEqualTo(sysRoleMenu.getDelFlag());
        }
        return example;
    }

}