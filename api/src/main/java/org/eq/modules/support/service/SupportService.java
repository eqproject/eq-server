/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.support.entity.Support;
import org.eq.modules.support.entity.SupportExample;
import org.eq.modules.support.vo.ConfigVO;

/**
 * 文案相关Service
 * @author hobe
 * @version 2019-06-02
 */
public interface SupportService extends ServiceExtend<Support, SupportExample> {

    ResponseData getTerms();

    ResponseData getLegal();

    ResponseData getBuydoc();

    /**
     * 获取平台配置项
     * @return
     */
    ConfigVO getConfigVo();
}