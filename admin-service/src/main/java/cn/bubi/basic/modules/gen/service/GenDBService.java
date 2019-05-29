/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.gen.service;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.gen.config.WebManger;
import cn.bubi.basic.gen.entity.Table;
import cn.bubi.basic.gen.entity.TableColumn;
import cn.bubi.basic.modules.gen.entity.GenDB;
import cn.bubi.basic.modules.gen.entity.GenDBExample;
import cn.bubi.basic.modules.gen.entity.GenTableModal;

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