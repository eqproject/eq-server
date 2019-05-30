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
import org.eq.basic.modules.sys.dao.SysUserInfoMapper;
import org.eq.basic.modules.sys.entity.SysUserInfo;
import org.eq.basic.modules.sys.entity.SysUserInfoExample;
import org.eq.basic.modules.sys.service.SysUserInfoService;

/**
 * 用户信息表ServiceImpl
 *
 * @author JoinHan
 * @version 2018-04-11
 */
@Service
@Transactional
@AutowiredService
public class SysUserInfoServiceImpl extends ServiceImplExtend<SysUserInfoMapper, SysUserInfo, SysUserInfoExample>
        implements SysUserInfoService {

    @Override
    public SysUserInfoExample getExampleFromEntity(SysUserInfo sysUserInfo, Map<String, Object> params) {

        SysUserInfoExample example = new SysUserInfoExample();
        SysUserInfoExample.Criteria ca = example.or();
        if (sysUserInfo == null) {
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
        if (sysUserInfo.getId() != null) {
            ca.andIdEqualTo(sysUserInfo.getId());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getName())) {
            ca.andNameEqualTo(sysUserInfo.getName());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getSex())) {
            ca.andSexEqualTo(sysUserInfo.getSex());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getTel())) {
            ca.andTelEqualTo(sysUserInfo.getTel());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getIdCard())) {
            ca.andIdCardEqualTo(sysUserInfo.getIdCard());
        }
        if (sysUserInfo.getStatus() != null) {
            ca.andStatusEqualTo(sysUserInfo.getStatus());
        }
        if (sysUserInfo.getType() != null) {
            ca.andTypeEqualTo(sysUserInfo.getType());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getPhotoHead())) {
            ca.andPhotoHeadEqualTo(sysUserInfo.getPhotoHead());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getJob())) {
            ca.andJobEqualTo(sysUserInfo.getJob());
        }
        if (sysUserInfo.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysUserInfo.getCreateBy());
        }
        if (sysUserInfo.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysUserInfo.getCreateDate());
        }
        if (sysUserInfo.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysUserInfo.getUpdateBy());
        }
        if (sysUserInfo.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysUserInfo.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getRemarks())) {
            ca.andRemarksEqualTo(sysUserInfo.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysUserInfo.getDelFlag())) {
            ca.andDelFlagEqualTo(sysUserInfo.getDelFlag());
        }
        return example;
    }

}