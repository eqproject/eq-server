/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bubi.basic.common.base.BaseEntity;
import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.ParseUtil;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.common.config.sysUtil.SysCacheUtil;
import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.modules.sys.dao.SysRoleMapper;
import cn.bubi.basic.modules.sys.dao.SysRoleMenuMapper;
import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.entity.SysRole;
import cn.bubi.basic.modules.sys.entity.SysRoleExample;
import cn.bubi.basic.modules.sys.entity.SysRoleMenu;
import cn.bubi.basic.modules.sys.entity.SysRoleMenuExample;
import cn.bubi.basic.modules.sys.service.SysRoleService;

/**
 * 角色表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysRoleServiceImpl extends ServiceImplExtend<SysRoleMapper, SysRole, SysRoleExample>
        implements SysRoleService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRoleExample getExampleFromEntity(SysRole sysRole, Map<String, Object> params) {

        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria ca = example.or();
        if (sysRole == null) {
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
        if (sysRole.getId() != null) {
            ca.andIdEqualTo(sysRole.getId());
        }
        if (StringLowUtils.isNotBlank(sysRole.getCode())) {
            ca.andCodeEqualTo(sysRole.getCode());
        }
        if (StringLowUtils.isNotBlank(sysRole.getName())) {
            ca.andNameLike("%" + sysRole.getName() + "%");
        }
        if (StringLowUtils.isNotBlank(sysRole.getEnname())) {
            ca.andEnnameEqualTo(sysRole.getEnname());
        }
        if (sysRole.getType() != null) {
            ca.andTypeEqualTo(sysRole.getType());
        }
        if (sysRole.getDataScope() != null) {
            ca.andDataScopeEqualTo(sysRole.getDataScope());
        }
        if (sysRole.getStatus() != null) {
            ca.andStatusEqualTo(sysRole.getStatus());
        }
        if (sysRole.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysRole.getCreateBy());
        }
        if (sysRole.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysRole.getCreateDate());
        }
        if (sysRole.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysRole.getUpdateBy());
        }
        if (sysRole.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysRole.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysRole.getRemarks())) {
            ca.andRemarksEqualTo(sysRole.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysRole.getDelFlag())) {
            ca.andDelFlagEqualTo(sysRole.getDelFlag());
        }
        return example;
    }

    @Override
    public boolean insertRole(SysRole sysRole) {

        if (this.getMapper().insert(sysRole) > 0) {
            for (SysRoleMenu sysRoleMenu : sysRole.getSysRoleMenuList()) {
                sysRoleMenu.setRoleId(sysRole.getId());
                this.sysRoleMenuMapper.insert(sysRoleMenu);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRole(SysRole sysRole) {

        if (this.getMapper().updateByPrimaryKeySelective(sysRole) > 0) {
            // 删除之前的sysRoleMenu
            SysRoleMenuExample example = new SysRoleMenuExample();
            example.or().andRoleIdEqualTo(sysRole.getId());
            this.sysRoleMenuMapper.deleteByExample(example);
            for (SysRoleMenu sysRoleMenu : sysRole.getSysRoleMenuList()) {
                sysRoleMenu.setRoleId(sysRole.getId());
                this.sysRoleMenuMapper.insert(sysRoleMenu);
            }
            return true;
        }
        return false;
    }

    @Override
    public int deleteRole(String ids, String virtual) {

        int delNum = 0;
        // 删除之前的sysRoleMenu
        List<Long> idLongs = new ArrayList<>();
        for (String str : ids.split(",")) {
            idLongs.add(ParseUtil.getLong(str));
        }
        SysRoleMenuExample example = new SysRoleMenuExample();
        example.or().andRoleIdIn(idLongs);
        this.sysRoleMenuMapper.deleteByExample(example);
        if (virtual.equals("false")) {
            delNum = this.deleteByPrimaryKeys(ids);
        } else {
            delNum = this.deleteVirtualByPrimaryKeys(ids);
        }
        return delNum;
    }

    @Override
    public List<SysDict> getRoleTypePermission(List<Integer> typeLongs) {

        List<SysDict> roleTypeList = SysCacheUtil.getInstance().getDictMapCache().get("roleType");
        if (!UserUtil.getInstance().isAdmin(null)) {// 不是管理员
            // 只显示 roleType 比角色大的
            List<SysRole> roleList = UserUtil.getInstance().getUserInfo().getRoleList();
            int maxRole = 9999;
            for (SysRole role : roleList) {
                if (maxRole > role.getType()) {
                    maxRole = role.getType();
                }
            }
            Iterator<SysDict> iterator = roleTypeList.iterator();
            List<SysDict> list = new ArrayList<>();
            while (iterator.hasNext()) {
                SysDict sysDict = iterator.next();
                if (ParseUtil.getInteger(sysDict.getValue()) >= maxRole) {
                    if (typeLongs != null) {
                        typeLongs.add(ParseUtil.getInteger(sysDict.getValue()));
                    }
                    list.add(sysDict);
                }
            }
            roleTypeList = list;
        } else {
            if (typeLongs != null) {
                for (SysDict sysDict : roleTypeList) {
                    typeLongs.add(ParseUtil.getInteger(sysDict.getValue()));
                }
            }
        }
        return roleTypeList;
    }

    @Override
    public BaseTableData findDataTableByRecordForPagePermission(SysRole sysRole, Map<String, Object> params) {

        List<Integer> typeLongs = new ArrayList<>();
        this.getRoleTypePermission(typeLongs);
        SysRoleExample example = this.getExampleFromEntity(sysRole, params);
        example.getOredCriteria().get(0).andTypeIn(typeLongs);
        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        return this.findDataTableByExampleForPage(example, pageNum, pageSize);
    }

    @Override
    public List<SysRole> findRolePermission(Integer type) {

        if (type == 0) {
            List<SysRole> roleList = UserUtil.getInstance().getUserInfo().getRoleList();
            int maxRole = 99999;
            for (SysRole sysRole : roleList) {
                if (sysRole.getType() < maxRole) {
                    maxRole = sysRole.getType();
                }
            }
            List<SysDict> roltTypeList = SysCacheUtil.getInstance().getDictMapCache().get("roleType");
            List<Integer> typeLongs = new ArrayList<>();
            for (SysDict sysDict : roltTypeList) {
                if (ParseUtil.getInteger(sysDict.getValue()) >= maxRole) {
                    typeLongs.add(ParseUtil.getInteger(sysDict.getValue()));
                }
            }
            SysRoleExample example = new SysRoleExample();
            example.or().andTypeIn(typeLongs).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            return this.getMapper().selectByExample(example);
        }
        return new ArrayList<>();
    }
}