package cn.bubi.basic.gen.entity;

import java.util.List;

/**
 * 表字段描述类
 *
 * @Author: JoinHan
 * @Date: Created in 17:46 2018/2/6
 * @Modified By：
 */
public class TableColumn {

    // ----------------------------- 数据库 属性-----------------------------
    private String columnName;// 字段名

    private String schemaName;// 所属数据库名称

    private String tableName;// 所属表名称

    private Table table;// 所属表引用

    private String dataType; // 数据库类型

    private String columnComment;// 字段备注

    private String mybatisType;// mybatis 对应的字段类型

    private String javaType;// java 对应的字段类型

    private String javaColumnName;// java 属性名称

    private String columnLength;// 字段长度

    // ----------------------------- 导入导出 属性-----------------------------
    private boolean ifImport = false;// 是否允许导入 默认不允许

    private boolean ifExport = false;// 是否允许导出 默认不允许

    private String title;// 标题

    private String order;// 顺序

    private String type;// 属性类型 数据字典，时间，sql ，默认为空 普通值类型

    private String dateFormat; // 日期格式

    private String dictionary;// 数据字典类型

    private List<String> sqlList;// 可执行sql 多个

    // ----------------------------- 页面显示 属性-----------------------------
    private String selectType;// 页面字段查询方式

    private String showType;// 页面字段展示方式

    public String getColumnName() {

        return this.columnName;
    }

    public void setColumnName(String columnName) {

        this.columnName = columnName;
    }

    public String getSchemaName() {

        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {

        this.schemaName = schemaName;
    }

    public String getTableName() {

        return this.tableName;
    }

    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

    public String getDataType() {

        return this.dataType;
    }

    public void setDataType(String dataType) {

        this.dataType = dataType;
    }

    public String getColumnComment() {

        return this.columnComment;
    }

    public void setColumnComment(String columnComment) {

        this.columnComment = columnComment;
    }

    public String getMybatisType() {

        return this.mybatisType;
    }

    public void setMybatisType(String mybatisType) {

        this.mybatisType = mybatisType;
    }

    public String getJavaType() {

        return this.javaType;
    }

    public void setJavaType(String javaType) {

        this.javaType = javaType;
    }

    public String getJavaColumnName() {

        return this.javaColumnName;
    }

    public void setJavaColumnName(String javaColumnName) {

        this.javaColumnName = javaColumnName;
    }

    public boolean isIfImport() {

        return this.ifImport;
    }

    public void setIfImport(boolean ifImport) {

        this.ifImport = ifImport;
    }

    public boolean isIfExport() {

        return this.ifExport;
    }

    public void setIfExport(boolean ifExport) {

        this.ifExport = ifExport;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getOrder() {

        return this.order;
    }

    public void setOrder(String order) {

        this.order = order;
    }

    public String getType() {

        return this.type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getDateFormat() {

        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {

        this.dateFormat = dateFormat;
    }

    public String getDictionary() {

        return this.dictionary;
    }

    public void setDictionary(String dictionary) {

        this.dictionary = dictionary;
    }

    public List<String> getSqlList() {

        return this.sqlList;
    }

    public void setSqlList(List<String> sqlList) {

        this.sqlList = sqlList;
    }

    public String getSelectType() {

        return this.selectType;
    }

    public void setSelectType(String selectType) {

        this.selectType = selectType;
    }

    public String getShowType() {

        return this.showType;
    }

    public void setShowType(String showType) {

        this.showType = showType;
    }

    public String getColumnLength() {

        return this.columnLength;
    }

    public void setColumnLength(String columnLength) {

        this.columnLength = columnLength;
    }

    public Table getTable() {

        return this.table;
    }

    public void setTable(Table table) {

        this.table = table;
    }
}
