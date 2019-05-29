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
import cn.bubi.basic.gen.entity.TableColumn;
import cn.bubi.basic.gen.entity.TableDataModal;
import cn.bubi.basic.gen.entity.xml.Category;
import cn.bubi.basic.gen.entity.xml.DbType;
import cn.bubi.basic.gen.entity.xml.Field;
import cn.bubi.basic.gen.entity.xml.Template;
import cn.bubi.basic.gen.status.GenTypeEnum;
import cn.bubi.basic.gen.util.TemplateLowUtil;

/**
 * 代码生成Manager
 *
 * @author JoinHan
 * @version 2017-09-06
 */
public class GenManger {

    private static Logger logger = LoggerFactory.getLogger(GenManger.class);

    private GenConfig genConfig = null;

    private DbManger dbManger = null;// 数据库连接管理

    private SqlManger sqlManger = null;

    private List<TableDataModal> tableDataModalList = null;// 代码生成的数据来源

    public GenManger(GenConfig genConfig) {
        if (genConfig == null) {
            // 初始化基础配置
            this.genConfig = new GenConfig();
        } else {
            this.genConfig = genConfig;
        }
        // 根据配置数据连接
        this.dbManger = DbManger.initDb(this.genConfig.getDBProperties());
        // 根据数据库配置 形成所需使用的sql
        this.sqlManger = SqlManger.initSql(this.genConfig, this.dbManger);
    }

    public static void main(String[] args) {

        // 目前仅支持 mysql数据库
        GenConfig genConfig = new GenConfig();
        // 指定表信息 作者 版本 时间 如果没有配置取properties文件 properties文件没有则为空
        TableDataModal tableDataModal = new TableDataModal();
        /* sys_user */
        /*
         * tableDataModal.setFunctionName("系统用户表");
         * tableDataModal.setCategory("curd");
         * tableDataModal.getTable().setTableName("sys_user");
         * tableDataModal.setClassName("SysUser");
         * tableDataModal.setModuleName("sys");
         * genConfig.addTable(tableDataModal);//可以添加多个表配置
         */

        /* gen_db */
        /*
         * tableDataModal = new TableDataModal();
         * tableDataModal.setFunctionName("代码生成数据库");
         * tableDataModal.setCategory("curd");
         * tableDataModal.getTable().setTableName("gen_db");
         * tableDataModal.setClassName("GenDB");
         * tableDataModal.setModuleName("gen");
         * genConfig.addTable(tableDataModal);
         */

        /* gen_plan */
        /*
         * tableDataModal = new TableDataModal();
         * tableDataModal.setFunctionName("代码生成计划");
         * tableDataModal.setCategory("curd");
         * tableDataModal.getTable().setTableName("gen_plan");
         * tableDataModal.setClassName("GenPlan");
         * tableDataModal.setModuleName("gen");
         * genConfig.addTable(tableDataModal);//可以添加多个表配置
         */

        /* gen_table_modal */
        /*
         * tableDataModal = new TableDataModal();
         * tableDataModal.setFunctionName("代码生成表配置");
         * tableDataModal.setCategory("curd");
         * tableDataModal.getTable().setTableName("gen_table_modal");
         * tableDataModal.setClassName("GenTableModal");
         * tableDataModal.setModuleName("gen");
         * genConfig.addTable(tableDataModal);//可以添加多个表配置
         */
        /* gen_db */
        tableDataModal = new TableDataModal();
        tableDataModal.setFunctionName("机构表");
        tableDataModal.setCategory("curd");
        tableDataModal.getTable().setTableName("sys_office");
        tableDataModal.setClassName("SysOffice");
        tableDataModal.setModuleName("sys");
        genConfig.addTable(tableDataModal);

        GenManger genManger = new GenManger(genConfig);
        // 第一种方式 指定数据库中某一张表 根据表的结构生成文件
        genManger.generator();

    }

    /**
     * 代码生成的入口
     */
    public void generator() {

        if (this.genConfig.getGenTypeEnum() == GenTypeEnum.DBTABLEASSIGN) {
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

                                TemplateLowUtil.generateToFile(tpl, modal, true);
                            } catch(Exception e) {
                                logger.debug(e.getMessage());
                                ;
                            }
                        }
                    }
                }
            }
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

                // 配置优先
                tableDataModal.setAuthor(tbname.getAuthor() == null ? genConfig.getAuthor() : tbname.getAuthor());
                tableDataModal.setTime(
                        tbname.getTime() == null ? ParseUtil.parseDate(new Date(), "yyyy-MM-dd") : tbname.getTime());
                tableDataModal.setVersion(tbname.getVersion() == null ? genConfig.getVersion() : tbname.getVersion());
                // 默认文件位置
                tableDataModal.setMapperLocation(
                        ( tbname.getMapperLocation() == null ? genConfig.getLocation() : tbname.getMapperLocation())
                                + StringLowUtils.replaceEach("/src/main/resources/mappings/",
                                        new String[] {"//", "/", "." },
                                        new String[] {File.separator, File.separator, File.separator })
                                + tableDataModal.getModuleName());

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

                param.put("tableName", tbname.getTable().getTableName());
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
                // 表所有列完事之后处理
                this.afterColumn(tableDataModal, tableColumnList);
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
            if (dbType.getValue().equals(dbManger.getDbTypeEnum().getDbTypeDescription())) {
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
            if (f.getValue().equals(tableColumn.getDataType())) {
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

    public List<TableDataModal> getTableDataModalList() {

        return this.tableDataModalList;
    }

    public void setTableDataModalList(List<TableDataModal> tableDataModalList) {

        this.tableDataModalList = tableDataModalList;
    }
}