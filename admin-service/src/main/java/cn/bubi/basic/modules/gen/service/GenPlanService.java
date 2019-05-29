/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.service;

import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.gen.entity.GenPlan;
import cn.bubi.basic.modules.gen.entity.GenPlanExample;

/**
 * 代码生成计划Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface GenPlanService extends ServiceExtend<GenPlan, GenPlanExample> {

    boolean codeMake(GenPlan genPlan);
}