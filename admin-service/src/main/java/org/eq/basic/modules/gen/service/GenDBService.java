/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.service;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.gen.config.WebManger;
import org.eq.basic.gen.entity.Table;
import org.eq.basic.gen.entity.TableColumn;
import org.eq.basic.modules.gen.entity.GenDB;
import org.eq.basic.modules.gen.entity.GenDBExample;
import org.eq.basic.modules.gen.entity.GenTableModal;

import java.util.List;
import java.util.Map;

/**
 * 代码生成数据库Service
 *
 * @author JoinHan
 * @version 0.0.1
 */
public interface GenDBService extends ServiceExtend<GenDB, GenDBExample> {

    BaseTableData findDBTableForPage(Map<String, Object> params, String dbMangerName);

    BaseTableData findDBTableColumnForPage(Map<String, Object> params, String dbMangerName);

    List<Table> findDBTable(GenDB genDB);

    List<TableColumn> findDBTableColumn(GenTableModal tableColumn);

    WebManger getDBConfig(GenDB genDB);
}