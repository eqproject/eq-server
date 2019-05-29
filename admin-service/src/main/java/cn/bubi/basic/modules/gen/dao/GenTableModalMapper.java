/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.dao;

import cn.bubi.basic.common.base.BaseMapper;
import cn.bubi.basic.modules.gen.entity.GenTableModal;
import cn.bubi.basic.modules.gen.entity.GenTableModalExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 代码生成表配置Mapper接口
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Mapper
public interface GenTableModalMapper extends BaseMapper<GenTableModal, GenTableModalExample> {

    /**
     * 根据 example 查询 tableModal 数据
     *
     * @param example
     * @return
     */
    List<GenTableModal> findTableModal(GenTableModalExample example);
}