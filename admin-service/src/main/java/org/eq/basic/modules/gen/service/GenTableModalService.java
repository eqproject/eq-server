/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.service;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.gen.entity.GenPlan;
import org.eq.basic.modules.gen.entity.GenTableModal;
import org.eq.basic.modules.gen.entity.GenTableModalExample;

import java.util.Map;

/**
 * 代码生成表配置Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface GenTableModalService extends ServiceExtend<GenTableModal, GenTableModalExample> {

    /**
     * 分页查询计划的tablemodal
     *
     * @param genTableModal
     * @param params
     * @return
     */
    BaseTableData findTableModalForPage(GenTableModal genTableModal, Map<String, Object> params);

    /**
     * table_modal 代码生成
     *
     * @param genTableModal
     * @param genPlan
     * @return
     */
    boolean codeMake(GenTableModal genTableModal, GenPlan genPlan);
}