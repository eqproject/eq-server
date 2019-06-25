/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.DocTypeEnum;
import org.eq.modules.enums.StateEnum;
import org.eq.modules.support.dao.SupportMapper;
import org.eq.modules.support.entity.Support;
import org.eq.modules.support.entity.SupportExample;
import org.eq.modules.support.service.SupportService;
import org.eq.modules.support.vo.ConfigVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 文案相关ServiceImpl
 *
 * @author hobe
 * @version 2019-06-02
 */
@Service
@Transactional
@AutowiredService
public class SupportServiceImpl extends ServiceImplExtend<SupportMapper, Support, SupportExample> implements SupportService {

    @Value("${aes.key}")
    private String aesKey;

    @Override
    public SupportExample getExampleFromEntity(Support support, Map<String, Object> params) {
        SupportExample example = new SupportExample();
        SupportExample.Criteria ca = example.or();
        if (support == null) {
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
        if (support.getId() != null) {
            ca.andIdEqualTo(support.getId());
        }
        if (support.getGrouping() != null) {
            ca.andGroupingEqualTo(support.getGrouping());
        }
        if (support.getType() != null) {
            ca.andTypeEqualTo(support.getType());
        }
        if (StringLowUtils.isNotBlank(support.getContent())) {
            ca.andContentEqualTo(support.getContent());
        }
        if (support.getValue() != null) {
            ca.andValueEqualTo(support.getValue());
        }
        if (support.getState() != null) {
            ca.andStateEqualTo(support.getState());
        }
        return example;
    }

    /**
     * 获取服务条款
     * @return
     */
    @Override
    public ResponseData getTerms() {
        Support support = new Support();
        support.setType(DocTypeEnum.TERMS.getState());
        support.setState(StateEnum.VALID.getState());
        Support data = selectByRecord(support);

        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }

    }

    /**
     * 获取法务支持
     * @return
     */
    @Override
    public ResponseData getLegal() {
        Support support = new Support();
        support.setType(DocTypeEnum.LEGAL.getState());
        support.setState(StateEnum.VALID.getState());
        Support data = selectByRecord(support);

        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }
    }

    /**
     * 获取求购文案
     * @return
     */
    @Override
    public ResponseData getBuydoc() {
        Support support = new Support();
        support.setType(DocTypeEnum.BUY_DOC.getState());
        support.setState(StateEnum.VALID.getState());
        Support data = selectByRecord(support);
        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }
    }


    @Override
    public ConfigVO getConfigVo() {
        ConfigVO configVO = new ConfigVO();
        configVO.setAesKey(aesKey);
        return configVO;
    }
}