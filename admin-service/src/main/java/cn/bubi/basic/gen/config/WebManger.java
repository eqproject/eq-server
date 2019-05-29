package cn.bubi.basic.gen.config;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bubi.basic.common.util.ObjectUtils;
import cn.bubi.basic.common.util.ParseUtil;
import cn.bubi.basic.common.util.StringLowUtils;
import cn.bubi.basic.common.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.bubi.basic.gen.entity.Table;
import cn.bubi.basic.gen.entity.TableColumn;
import cn.bubi.basic.gen.entity.TableDataModal;
import cn.bubi.basic.gen.entity.xml.Category;
import cn.bubi.basic.gen.entity.xml.DbType;
import cn.bubi.basic.gen.entity.xml.Field;
import cn.bubi.basic.gen.entity.xml.Template;
import cn.bubi.basic.gen.status.DBTypeEnum;
import cn.bubi.basic.gen.status.GenTypeEnum;
import cn.bubi.basic.gen.util.TemplateLowUtil;
import cn.bubi.basic.modules.gen.entity.GenDB;

/**
 * @Author: JoinHan
 * @Date: Created in 10:38 2018/3/1
 * @Modified By：
 */
public class WebManger {

    private GenConfig genConfig = null;

    private DbManger dbManger = null;// 数据库连接管理

    private SqlManger sqlManger = null;

    private GenDB genDB = null;

    private List<TableDataModal> tableDataModalList = null;// 代码生成的数据来源

    private static Logger logger = LoggerFactory.getLogger(WebManger.class);

    public WebManger() {
        this(null);
    }

    public WebManger(GenDB genDB) {
        this.genDB = genDB;
        this.genConfig = new GenConfig(genDB);
        // 根据配置数据连接
        this.dbManger = DbManger.initDb(this.genConfig.getDBProperties());
        // 根据数据库配置 形成所需使用的sql
        this.sqlManger = SqlManger.initSql(this.genConfig, this.dbManger);
    }

    public void init(WebManger webManger) {

        this.genDB = webManger.getGenDB();
        this.genConfig = webManger.getGenConfig();
        this.dbManger = webManger.getDbManger();
        this.sqlManger = webManger.getSqlManger();
    }

    public static void main(String[] args) {

        // 数据库获取一个配置
        // 根据配置 生成一个webManger 进行操作 1.查询web连接的表，表字段
        WebManger webManger = new WebManger();
        webManger.selectTable();
    }

    public List<Table> selectTable() {

        List<Table> tableList = null;
        Map<String, Object> param = new HashMap<>();
        param.put("limit", "");
        param.put("order", "");
        String sql = SqlManger.replaceVar(this.sqlManger.getAllTableInfoSql(), param);// 读取 查询table sql
        try (DruidPooledConnection connection = this.dbManger.getConnection()) {
            DruidPooledPreparedStatement ps = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (tableList == null) {
                    tableList = new ArrayList<>();
                }
                Table table = new Table();
                table.setTableName(rs.getString("tableName"));
                table.setSchemaName(this.dbManger.getDbName());
                table.setComments(rs.getString("comments"));
                tableList.add(table);
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return tableList;
    }

    public PageInfo selectTableByPage(Integer pageNum, Integer pageSize, Map<String, Object> param) {

        PageInfo pageInfo = new PageInfo();
        param.put("limit", " limit " + pageNum + "," + pageSize);
        String sql = SqlManger.replaceVar(this.sqlManger.getAllTableInfoSql(), param);// 读取 查询table sql
        String countSql = this.countSql(sql);
        try (DruidPooledConnection connection = this.dbManger.getConnection()) {

            DruidPooledPreparedStatement countps = (DruidPooledPreparedStatement) connection.prepareStatement(countSql);
            ResultSet countrs = countps.executeQuery();
            while (countrs.next()) {
                int countNum = ParseUtil.getInteger(countrs.getString("count"));
                pageInfo.setTotal(countNum);
                List<Table> tableList = new ArrayList<>();
                if (countNum > 0) {
                    DruidPooledPreparedStatement ps = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
                    PageHelper.startPage(pageNum, pageSize, true);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        Table table = new Table();
                        table.setTableName(rs.getString("tableName"));
                        table.setSchemaName(this.dbManger.getDbName());
                        table.setComments(rs.getString("comments"));
                        tableList.add(table);
                    }
                }
                pageInfo.setList(tableList);
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return pageInfo;
    }

    public PageInfo<TableColumn> selectTableColumnByPage(Integer pageNum, Integer pageSize, Map<String, Object> param) {

        PageInfo pageInfo = new PageInfo();
        param.put("limit", " limit " + pageNum + "," + pageSize);
        String sql = SqlManger.replaceVar(this.sqlManger.getAllCoulmnInfoSql(), param);// 读取 查询table sql
        String countSql = this.countSql(sql);
        try (DruidPooledConnection connection = this.dbManger.getConnection()) {

            DruidPooledPreparedStatement countps = (DruidPooledPreparedStatement) connection.prepareStatement(countSql);
            ResultSet countrs = countps.executeQuery();
            while (countrs.next()) {
                int countNum = ParseUtil.getInteger(countrs.getString("count"));
                pageInfo.setTotal(countNum);
                List<TableColumn> tableList = new ArrayList<>();
                if (countNum > 0) {
                    DruidPooledPreparedStatement ps = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
                    PageHelper.startPage(pageNum, pageSize, true);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TableColumn tableColumn = new TableColumn();
                        tableColumn.setTableName(rs.getString("tableName"));
                        tableColumn.setSchemaName(this.dbManger.getDbName());
                        tableColumn.setColumnName(rs.getString("columnName"));
                        tableColumn.setDataType(rs.getString("dataType"));
                        tableColumn.setColumnComment(rs.getString("columnComment"));
                        tableList.add(tableColumn);
                    }
                }
                pageInfo.setList(tableList);
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return pageInfo;
    }

    public List<TableColumn> selectTableColumn(Map<String, Object> param) {

        List<TableColumn> tableColumnList = null;
        if (this.dbManger.getDbTypeEnum() == DBTypeEnum.DB2) {
            param.put("order", "order by columnName asc");
        }
        param.put("limit", "");
        String sql = SqlManger.replaceVar(this.sqlManger.getAllCoulmnInfoSql(), param);// 读取 查询table sql
        String countSql = this.countSql(sql);
        try (DruidPooledConnection connection = this.dbManger.getConnection()) {

            DruidPooledPreparedStatement countps = (DruidPooledPreparedStatement) connection.prepareStatement(countSql);
            ResultSet countrs = countps.executeQuery();
            while (countrs.next()) {
                int countNum = ParseUtil.getInteger(countrs.getString("count"));
                if (countNum > 0) {
                    DruidPooledPreparedStatement ps = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    if (tableColumnList == null) {
                        tableColumnList = new ArrayList<>();
                    }
                    while (rs.next()) {
                        TableColumn tableColumn = new TableColumn();
                        tableColumn.setTableName(rs.getString("tableName"));
                        tableColumn.setSchemaName(this.dbManger.getDbName());
                        tableColumn.setColumnName(rs.getString("columnName"));
                        tableColumn.setDataType(rs.getString("dataType"));
                        tableColumn.setColumnComment(rs.getString("columnComment"));
                        tableColumnList.add(tableColumn);
                    }
                }
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return tableColumnList;
    }

    public boolean valid() {

        try (DruidPooledConnection connection = this.dbManger.getConnection()) {
            return true;
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return false;
    }

    private String countSql(String sql) {

        sql = sql.replace(sql.substring(sql.indexOf("select") + 6, sql.indexOf("from")), " count(*) count ");
        sql = sql.substring(0, sql.lastIndexOf("order"));
        return sql;
    }

    /**
     * 代码生成的入口
     */
    public boolean generator() {

        if (this.genConfig.getGenTypeEnum() == GenTypeEnum.WEBCONFIGASSIGN) {
            // 根据指定的表生成TableDataModal 数据列表
            this.tableDataModalList = this.makeModal(this.dbManger, this.genConfig, this.sqlManger);
            // 读取模板配置
            for (TableDataModal tdm : this.tableDataModalList) {
                List<Category> categories = this.genConfig.getDictionary().getCategories().getCategoryList();
                for (Category category : categories) {
                    if (category.getValue().equals(tdm.getCategory())) {// 配置的分类在系统字典中
                        for (String template : category.getTemplateList()) {
                            // 读取模板
                            Template tpl = (Template) XmlUtil.xmlToObject(template, Template.class);
                            try {
                                Map<String, Object> modal = new HashMap<>();
                                // 对象转成mapper
                                ObjectUtils.objectToMap(tdm, true, modal);
                                if (!TemplateLowUtil.generateToFile(tpl, modal, true)) {
                                    return false;
                                }
                            } catch(Exception e) {
                                logger.debug(e.getMessage());
                                ;
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return true;
        }
    }

    private List<TableDataModal> makeModal(DbManger dbManger, GenConfig genConfig, SqlManger sqlManger) {

        List<TableDataModal> tableDataModals = new ArrayList<>();
        // 获取数据库连接 查询表信息
        try (DruidPooledConnection connection = dbManger.getConnection()) {
            connection.createStatement();

            Map<String, Object> param = new HashMap<>();
            for (TableDataModal tbname : genConfig.getTableNames()) {
                TableDataModal tableDataModal = new TableDataModal();

                // 自定义 属性
                // 必填
                tableDataModal.setCategory(tbname.getCategory());
                tableDataModal.setClassName(tbname.getClassName());
                tableDataModal.setModuleName(
                        tbname.getModuleName() == null ? genConfig.getModuleName() : tbname.getModuleName());
                // 非必填
                tableDataModal.setFunctionName(tbname.getFunctionName());
                tableDataModal.getPageTypeEnum().setPageModal(tbname.getPageTypeEnum().getPageModal());

                // 配置优先
                tableDataModal.setAuthor(tbname.getAuthor() == null ? genConfig.getAuthor() : tbname.getAuthor());
                tableDataModal.setTime(
                        tbname.getTime() == null ? ParseUtil.parseDate(new Date(), "yyyy-MM-dd") : tbname.getTime());
                tableDataModal.setVersion(tbname.getVersion() == null ? genConfig.getVersion() : tbname.getVersion());
                // 默认文件位置
                tableDataModal
                        .setMapperLocation(
                                ( tbname.getMapperLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach("/src/main/resources/mappings/",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                                + tableDataModal.getModuleName()
                                        : tbname.getMapperLocation()));

                tableDataModal
                        .setDaoLocation(
                                tbname.getDaoLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach(
                                                        "/src/main/java/"
                                                                + genConfig.getBasicPackage().replace(".",
                                                                        File.separator)
                                                                + File.separator + tableDataModal.getModuleName()
                                                                + "/dao",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                        : tbname.getDaoLocation());
                tableDataModal.setDaoPackage(tbname.getDaoPackage() == null
                        ? genConfig.getBasicPackage() + "." + tableDataModal.getModuleName() + ".dao"
                        : tbname.getDaoPackage());

                tableDataModal
                        .setEntityLocation(
                                tbname.getEntityLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach(
                                                        "/src/main/java/"
                                                                + genConfig.getBasicPackage().replace(".",
                                                                        File.separator)
                                                                + File.separator + tableDataModal.getModuleName()
                                                                + "/entity",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                        : tbname.getEntityLocation());
                tableDataModal.setEntityPackage(tbname.getEntityPackage() == null
                        ? genConfig.getBasicPackage() + "." + tableDataModal.getModuleName() + ".entity"
                        : tbname.getEntityPackage());

                tableDataModal
                        .setServiceLocation(
                                tbname.getServiceLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach(
                                                        "/src/main/java/"
                                                                + genConfig.getBasicPackage().replace(".",
                                                                        File.separator)
                                                                + File.separator + tableDataModal.getModuleName()
                                                                + "/service",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                        : tbname.getServiceLocation());
                tableDataModal.setServicePackage(tbname.getServicePackage() == null
                        ? genConfig.getBasicPackage() + "." + tableDataModal.getModuleName() + ".service"
                        : tbname.getServicePackage());

                tableDataModal
                        .setServiceImplLocation(
                                tbname.getServiceImplLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach(
                                                        "/src/main/java/"
                                                                + genConfig.getBasicPackage().replace(".",
                                                                        File.separator)
                                                                + File.separator + tableDataModal.getModuleName()
                                                                + "/service/impl",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                        : tbname.getServiceImplLocation());
                tableDataModal.setServiceImplPackage(tbname.getServiceImplPackage() == null
                        ? genConfig.getBasicPackage() + "." + tableDataModal.getModuleName() + ".service.impl"
                        : tbname.getServiceImplPackage());

                tableDataModal
                        .setControllerLocation(
                                tbname.getControllerLocation() == null
                                        ? genConfig.getLocation()
                                                + StringLowUtils.replaceEach(
                                                        "/src/main/java/"
                                                                + genConfig.getBasicPackage().replace(".",
                                                                        File.separator)
                                                                + File.separator + tableDataModal.getModuleName()
                                                                + "/controller",
                                                        new String[] {"//", "/", "." },
                                                        new String[] {File.separator, File.separator, File.separator })
                                        : tbname.getControllerLocation());
                tableDataModal.setControllerPackage(tbname.getControllerPackage() == null
                        ? genConfig.getBasicPackage() + "." + tableDataModal.getModuleName() + ".controller"
                        : tbname.getControllerPackage());

                tableDataModal.setPageLocation(tbname.getPageLocation() == null
                        ? genConfig.getLocation() + StringLowUtils.replaceEach(
                                "/src/main/resources/templates/modules/" + tableDataModal.getModuleName(),
                                new String[] {"//", "/", "." },
                                new String[] {File.separator, File.separator, File.separator })
                        : tbname.getPageLocation());

                param.put("tableName", tbname.getTable().getTableName());
                param.put("order", "");
                param.put("limit", "");
                String sql = SqlManger.replaceVar(sqlManger.getAllCoulmnInfoSql(), param);// 读取sql
                DruidPooledPreparedStatement ps = (DruidPooledPreparedStatement) connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                List<TableColumn> tableColumnList = null;
                while (rs.next()) {
                    tableColumnList = tableDataModal.getTable().getTableColumnList();
                    if (tableColumnList == null) {
                        tableColumnList = new ArrayList<>();
                        tableDataModal.getTable().setTableName(rs.getString("tableName"));
                        tableDataModal.getTable().setSchemaName(rs.getString("schemaName"));
                    }
                    TableColumn tableColumn = new TableColumn();
                    tableColumn.setColumnComment(rs.getString("columnComment"));
                    tableColumn.setColumnName(rs.getString("columnName"));
                    tableColumn.setDataType(rs.getString("dataType"));
                    tableColumn.setTableName(rs.getString("tableName"));
                    tableColumn.setSchemaName(rs.getString("schemaName"));
                    this.exchangeProperty(tableColumn, dbManger, genConfig);
                    tableColumnList.add(tableColumn);
                    tableDataModal.getTable().setTableColumnList(tableColumnList);
                }
                if (tableColumnList != null) {
                    // 表所有列完事之后处理
                    this.afterColumn(tableDataModal, tableColumnList);
                }
                tableDataModals.add(tableDataModal);
            }
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return tableDataModals;
    }

    /**
     * 表字段查询完之后 表级别处理
     *
     * @param tableDataModal
     * @param tableColumnList
     */
    private void afterColumn(TableDataModal tableDataModal, List<TableColumn> tableColumnList) {

        // 查询所需字段
        StringBuilder allFieldB = new StringBuilder();
        StringBuilder insertFieldB = new StringBuilder();
        List<TableColumn> insertFieldList = new ArrayList<>();
        Set<String> entityPackageList = new HashSet<>();
        for (TableColumn tableColumn : tableColumnList) {
            allFieldB.append(tableColumn.getColumnName() + ",");
            if (!tableColumn.getColumnName().equals("id")) {
                insertFieldB.append(tableColumn.getColumnName() + ",");
                insertFieldList.add(tableColumn);
            }
            if (tableColumn.getJavaType().equals("Date")) {
                entityPackageList.add("Date");
            }
            if (tableColumn.isIfExport() && tableColumn.isIfImport()) {
                entityPackageList.add("ExcelResources");
            }
        }
        // entity 导入包列表
        tableDataModal.getTable().setEntityPackageImportList(entityPackageList);
        tableDataModal.getTable().setAllField(allFieldB.substring(0, allFieldB.length() - 1));
        // 插入所需字段 id 字段不作为插入字段
        tableDataModal.getTable().setInsertField(insertFieldB.substring(0, insertFieldB.length() - 1));
        tableDataModal.getTable().setInsertFieldList(insertFieldList);
        tableDataModal.getTable().setUpdateFieldList(insertFieldList);// 更新表字段默认等于插入表字段

    }

    /**
     * 对数据库查询出来的字段信息进行处理
     *
     * @param tableColumn
     * @param dbManger
     * @param genConfig
     */
    private void exchangeProperty(TableColumn tableColumn, DbManger dbManger, GenConfig genConfig) {

        // mybatis 对应的字段类型 java 对应的字段类型 java 属性名称
        // 数据库和mybatis的对应关系
        List<DbType> dbTypeList = genConfig.getDictionary().getDbFieldType().getDbTypeList();
        for (DbType dbType : dbTypeList) {
            if (dbType.getValue().equalsIgnoreCase(dbManger.getDbTypeEnum().getDbTypeDescription())) {
                for (Field f : dbType.getFieldList()) {
                    if (f.getValue().equalsIgnoreCase(tableColumn.getDataType())) {
                        tableColumn.setMybatisType(f.getLabel());
                    }
                }
            }
        }
        // 数据库类型和Java类型对应关系
        List<Field> fieldList = genConfig.getDictionary().getJavaFieldType().getFieldList();
        for (Field f : fieldList) {
            if (f.getValue().equalsIgnoreCase(tableColumn.getDataType())) {
                tableColumn.setJavaType(f.getLabel());
            }
        }
        // java 属性名处理
        tableColumn.setJavaColumnName(
                StringLowUtils.toLowerCaseFirstOne(StringLowUtils.lineToHump(tableColumn.getColumnName())));
        // 基本查询所有字段

    }

    public GenConfig getGenConfig() {

        return this.genConfig;
    }

    public void setGenConfig(GenConfig genConfig) {

        this.genConfig = genConfig;
    }

    public DbManger getDbManger() {

        return this.dbManger;
    }

    public void setDbManger(DbManger dbManger) {

        this.dbManger = dbManger;
    }

    public SqlManger getSqlManger() {

        return this.sqlManger;
    }

    public void setSqlManger(SqlManger sqlManger) {

        this.sqlManger = sqlManger;
    }

    public GenDB getGenDB() {

        return this.genDB;
    }

    public void setGenDB(GenDB genDB) {

        this.genDB = genDB;
    }
}
