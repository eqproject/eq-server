/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.modules.sys.dao.SysMenuMapper;
import org.eq.basic.modules.sys.dao.SysRoleMenuMapper;
import org.eq.basic.modules.sys.entity.SysMenu;
import org.eq.basic.modules.sys.entity.SysMenuExample;
import org.eq.basic.modules.sys.entity.SysRole;
import org.eq.basic.modules.sys.entity.SysRoleMenu;
import org.eq.basic.modules.sys.entity.SysRoleMenuExample;
import org.eq.basic.modules.sys.security.UserInfo;
import org.eq.basic.modules.sys.service.SysMenuService;

/**
 * 菜单表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysMenuServiceImpl extends ServiceImplExtend<SysMenuMapper, SysMenu, SysMenuExample>
        implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysMenuExample getExampleFromEntity(SysMenu sysMenu, Map<String, Object> params) {

        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria ca = example.or();
        if (sysMenu == null) {
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
        if (sysMenu.getId() != null) {
            ca.andIdEqualTo(sysMenu.getId());
        }
        if (sysMenu.getParentId() != null) {
            ca.andParentIdEqualTo(sysMenu.getParentId());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getParentIds())) {
            ca.andParentIdsEqualTo(sysMenu.getParentIds());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getName())) {
            ca.andNameLike("%" + sysMenu.getName() + "%");
        }
        if (StringLowUtils.isNotBlank(sysMenu.getHref())) {
            ca.andHrefEqualTo(sysMenu.getHref());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getTarget())) {
            ca.andTargetEqualTo(sysMenu.getTarget());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getIcon())) {
            ca.andIconEqualTo(sysMenu.getIcon());
        }
        if (sysMenu.getSort() != null) {
            ca.andSortEqualTo(sysMenu.getSort());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getCode())) {
            ca.andCodeLike("%" + sysMenu.getCode() + "%");
        }
        if (sysMenu.getStatus() != null) {
            ca.andStatusEqualTo(sysMenu.getStatus());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getIsShow())) {
            ca.andIsShowEqualTo(sysMenu.getIsShow());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getPermission())) {
            ca.andPermissionEqualTo(sysMenu.getPermission());
        }
        if (sysMenu.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysMenu.getCreateBy());
        }
        if (sysMenu.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysMenu.getCreateDate());
        }
        if (sysMenu.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysMenu.getUpdateBy());
        }
        if (sysMenu.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysMenu.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getRemarks())) {
            ca.andRemarksEqualTo(sysMenu.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysMenu.getDelFlag())) {
            ca.andDelFlagEqualTo(sysMenu.getDelFlag());
        }
        return example;
    }

    @Override
    public List<SysMenu> findListPermission(UserInfo userInfo, SysMenu sysMenu) {

        List<SysRole> roleList = userInfo.getRoleList();
        List<Long> roleIds = new ArrayList<>();
        List<Long> menuIds = new ArrayList<>();
        if (roleList != null && !( roleList.isEmpty())) {
            for (SysRole role : roleList) {
                roleIds.add(role.getId());
            }
            SysRoleMenuExample sysRoleMenuExample = new SysRoleMenuExample();
            sysRoleMenuExample.or().andRoleIdIn(roleIds).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuMapper.selectByExample(sysRoleMenuExample);
            for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
                menuIds.add(sysRoleMenu.getMenuId());
            }
            SysMenuExample example = this.getExampleFromEntity(sysMenu, null);
            example.getOredCriteria().get(0).andIdIn(menuIds);
            return this.getMapper().selectByExample(example);
        }
        return new ArrayList<>();
    }
}