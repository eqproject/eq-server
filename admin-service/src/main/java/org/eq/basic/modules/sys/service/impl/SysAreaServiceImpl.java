/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.modules.sys.dao.SysAreaMapper;
import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysAreaExample;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.security.UserInfo;
import org.eq.basic.modules.sys.service.SysAreaService;

/**
 * 地区表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysAreaServiceImpl extends ServiceImplExtend<SysAreaMapper, SysArea, SysAreaExample>
        implements SysAreaService {

    @Override
    public SysAreaExample getExampleFromEntity(SysArea sysArea, Map<String, Object> params) {

        SysAreaExample example = new SysAreaExample();
        SysAreaExample.Criteria ca = example.or();
        if (sysArea == null) {
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
        if (sysArea.getId() != null) {
            ca.andIdEqualTo(sysArea.getId());
        }
        if (sysArea.getParentId() != null) {
            ca.andParentIdEqualTo(sysArea.getParentId());
        }
        if (StringLowUtils.isNotBlank(sysArea.getParentIds())) {
            ca.andParentIdsEqualTo(sysArea.getParentIds());
        }
        if (StringLowUtils.isNotBlank(sysArea.getName())) {
            ca.andNameLike("%" + sysArea.getName() + "%");
        }
        if (StringLowUtils.isNotBlank(sysArea.getCode())) {
            ca.andCodeLike("%" + sysArea.getCode() + "%");
        }
        if (sysArea.getType() != null) {
            ca.andTypeEqualTo(sysArea.getType());
        }
        if (sysArea.getSort() != null) {
            ca.andSortEqualTo(sysArea.getSort());
        }
        if (sysArea.getAreaLevel() != null) {
            ca.andAreaLevelEqualTo(sysArea.getAreaLevel());
        }
        if (sysArea.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysArea.getCreateBy());
        }
        if (sysArea.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysArea.getCreateDate());
        }
        if (sysArea.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysArea.getUpdateBy());
        }
        if (sysArea.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysArea.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysArea.getRemarks())) {
            ca.andRemarksEqualTo(sysArea.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysArea.getDelFlag())) {
            ca.andDelFlagEqualTo(sysArea.getDelFlag());
        }
        return example;
    }

    @Override
    public List<SysArea> findListPermission(UserInfo userInfo, SysArea sysArea) {

        SysAreaExample sysAreaExample = this.getExampleFromEntity(sysArea, null);
        sysAreaExample.getOredCriteria().get(0)
                .andParentIdsLike(userInfo.getSysArea().getParentIds() + userInfo.getSysArea().getId() + ",%");
        sysAreaExample.getOredCriteria().add(this.getExampleFromEntity(sysArea, null).getOredCriteria().get(0)
                .andIdEqualTo(userInfo.getSysArea().getId()));
        return this.getMapper().selectByExample(sysAreaExample);
    }

    @Override
    public List<SysDict> getAreaTypePermission(List<Integer> typeLongs) {

        List<SysDict> areaTypeList = SysCacheUtil.getInstance().getDictMapCache().get("areaType");
        if (!UserUtil.getInstance().isAdmin(null)) {// 管理员
            // 只显示 roleType 比角色大的
            int areaType = UserUtil.getInstance().getUserInfo().getSysArea().getType();

            Iterator<SysDict> iterator = areaTypeList.iterator();
            List<SysDict> list = new ArrayList<>();
            while (iterator.hasNext()) {
                SysDict sysDict = iterator.next();
                if (ParseUtil.getInteger(sysDict.getValue()) > areaType) {
                    list.add(sysDict);
                    if (typeLongs != null) {
                        typeLongs.add(ParseUtil.getInteger(sysDict.getValue()));
                    }
                }
            }
            areaTypeList = list;
        } else {
            if (typeLongs != null) {
                for (SysDict sysDict : areaTypeList) {
                    typeLongs.add(ParseUtil.getInteger(sysDict.getValue()));
                }
            }
        }
        return areaTypeList;
    }
}