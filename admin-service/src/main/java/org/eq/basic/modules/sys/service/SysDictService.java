/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysDictExample;

/**
 * 数据字典表Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface SysDictService extends ServiceExtend<SysDict, SysDictExample> {

    /**
     * 查询数据字典所有类型
     *
     * @return
     */
    List<Map> findDictType();

    /**
     * 执行sql 用户缓存导入导出转换数据
     *
     * @param sql
     * @return
     */
    List<Map> exceImportSql(String sql);
}