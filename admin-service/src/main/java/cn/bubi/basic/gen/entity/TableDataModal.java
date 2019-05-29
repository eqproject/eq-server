package cn.bubi.basic.gen.entity;

import cn.bubi.basic.gen.status.PageTypeEnum;

/**
 * 数据modal
 *
 * @Author: JoinHan
 * @Date: Created in 12:38 2018/2/2
 * @Modified By：
 */
public class TableDataModal {

    // ----------------------------- 代码生成 属性 --------------------------
    private String functionName;// 功能名

    private String author; // 作者

    private String time; // 时间

    private String version;// 版本

    private String className;// 类名

    private String moduleName;// 模块名称

    // ----------------------------- 代码生成 位置属性 --------------------------
    private String mapperLocation;// mapper 位置

    private String daoLocation;// dao 位置

    private String daoPackage;// dao 包位置

    private String entityLocation;// entity 位置

    private String entityPackage;// entity 包位置

    private String serviceLocation;// service 位置

    private String servicePackage;// service 包位置

    private String serviceImplLocation;// serviceImpl 位置

    private String serviceImplPackage;// serviceImpl 包位置

    private String controllerLocation;// controller 位置

    private String controllerPackage;// controller 包位置

    private String pageLocation;// 页面位置

    // ----------------------------- 代码生成 设置属性 --------------------------
    private boolean ifInheritBaseEntity = true;// 是否继承基础实体entity 默认继承

    // 代码生成分类 增删改查（单表）或 仅持久层
    private String category;

    private Table table = new Table();// 表信息

    // ----------------------------- 页面 设置属性 --------------------------
    private PageTypeEnum pageTypeEnum = PageTypeEnum.CURD;// 默认页面配置

    private String pageTitle;// 页面标题

    private String pagePath;// 页面菜单索引

    public String getFunctionName() {

        return this.functionName;
    }

    public void setFunctionName(String functionName) {

        this.functionName = functionName;
    }

    public String getAuthor() {

        return this.author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getTime() {

        return this.time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getVersion() {

        return this.version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public Table getTable() {

        return this.table;
    }

    public void setTable(Table table) {

        this.table = table;
    }

    public String getCategory() {

        return this.category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getMapperLocation() {

        return this.mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {

        this.mapperLocation = mapperLocation;
    }

    public String getClassName() {

        return this.className;
    }

    public void setClassName(String className) {

        this.className = className;
    }

    public String getDaoLocation() {

        return this.daoLocation;
    }

    public void setDaoLocation(String daoLocation) {

        this.daoLocation = daoLocation;
    }

    public String getDaoPackage() {

        return this.daoPackage;
    }

    public void setDaoPackage(String daoPackage) {

        this.daoPackage = daoPackage;
    }

    public String getEntityLocation() {

        return this.entityLocation;
    }

    public void setEntityLocation(String entityLocation) {

        this.entityLocation = entityLocation;
    }

    public String getEntityPackage() {

        return this.entityPackage;
    }

    public void setEntityPackage(String entityPackage) {

        this.entityPackage = entityPackage;
    }

    public boolean isIfInheritBaseEntity() {

        return this.ifInheritBaseEntity;
    }

    public void setIfInheritBaseEntity(boolean ifInheritBaseEntity) {

        this.ifInheritBaseEntity = ifInheritBaseEntity;
    }

    public String getServiceLocation() {

        return this.serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {

        this.serviceLocation = serviceLocation;
    }

    public String getServicePackage() {

        return this.servicePackage;
    }

    public void setServicePackage(String servicePackage) {

        this.servicePackage = servicePackage;
    }

    public String getServiceImplLocation() {

        return this.serviceImplLocation;
    }

    public void setServiceImplLocation(String serviceImplLocation) {

        this.serviceImplLocation = serviceImplLocation;
    }

    public String getServiceImplPackage() {

        return this.serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {

        this.serviceImplPackage = serviceImplPackage;
    }

    public String getControllerLocation() {

        return this.controllerLocation;
    }

    public void setControllerLocation(String controllerLocation) {

        this.controllerLocation = controllerLocation;
    }

    public String getControllerPackage() {

        return this.controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {

        this.controllerPackage = controllerPackage;
    }

    public String getModuleName() {

        return this.moduleName;
    }

    public void setModuleName(String moduleName) {

        this.moduleName = moduleName;
    }

    public PageTypeEnum getPageTypeEnum() {

        return this.pageTypeEnum;
    }

    public void setPageTypeEnum(PageTypeEnum pageTypeEnum) {

        this.pageTypeEnum = pageTypeEnum;
    }

    public String getPageLocation() {

        return this.pageLocation;
    }

    public void setPageLocation(String pageLocation) {

        this.pageLocation = pageLocation;
    }
}
