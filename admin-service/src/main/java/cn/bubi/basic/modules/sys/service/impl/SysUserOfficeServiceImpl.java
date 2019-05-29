/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service.impl;

import java.util.Map;

import cn.bubi.basic.common.base.ServiceImplExtend;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bubi.basic.common.annotation.AutowiredService;
import cn.bubi.basic.modules.sys.dao.SysUserOfficeMapper;
import cn.bubi.basic.modules.sys.entity.SysUserOffice;
import cn.bubi.basic.modules.sys.entity.SysUserOfficeExample;
import cn.bubi.basic.modules.sys.service.SysUserOfficeService;

/**
 * 用户机构表ServiceImpl
 *
 * @author JoinHan
 * @version 2018-04-11
 */
@Service
@Transactional
@AutowiredService
public class SysUserOfficeServiceImpl extends
        ServiceImplExtend<SysUserOfficeMapper, SysUserOffice, SysUserOfficeExample> implements SysUserOfficeService {

    @Override
    public SysUserOfficeExample getExampleFromEntity(SysUserOffice sysUserOffice, Map<String, Object> params) {

        SysUserOfficeExample example = new SysUserOfficeExample();
        SysUserOfficeExample.Criteria ca = example.or();
        if (sysUserOffice == null) {
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
        if (sysUserOffice.getId() != null) {
            ca.andIdEqualTo(sysUserOffice.getId());
        }
        if (sysUserOffice.getUserId() != null) {
            ca.andUserIdEqualTo(sysUserOffice.getUserId());
        }
        if (sysUserOffice.getOfficeId() != null) {
            ca.andOfficeIdEqualTo(sysUserOffice.getOfficeId());
        }
        if (sysUserOffice.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysUserOffice.getCreateBy());
        }
        if (sysUserOffice.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysUserOffice.getCreateDate());
        }
        if (sysUserOffice.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysUserOffice.getUpdateBy());
        }
        if (sysUserOffice.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysUserOffice.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysUserOffice.getRemarks())) {
            ca.andRemarksEqualTo(sysUserOffice.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysUserOffice.getDelFlag())) {
            ca.andDelFlagEqualTo(sysUserOffice.getDelFlag());
        }
        return example;
    }

}