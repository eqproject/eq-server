/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.service;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.gen.entity.GenPlan;
import cn.bubi.basic.modules.gen.entity.GenTableModal;
import cn.bubi.basic.modules.gen.entity.GenTableModalExample;

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