package org.eq.basic.gen.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eq.basic.common.util.FileLowUtils;
import org.eq.basic.common.util.PropertyUtil;
import org.eq.basic.common.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eq.basic.gen.entity.TableDataModal;
import org.eq.basic.gen.entity.xml.GenDictionary;
import org.eq.basic.gen.status.GenTypeEnum;
import org.eq.basic.modules.gen.entity.GenDB;

/**
 * 配置类
 *
 * @Author: JoinHan
 * @Date: Created in 12:56 2018/2/2
 * @Modified By：
 */
public class GenConfig {

    private static Logger logger = LoggerFactory.getLogger(GenConfig.class);

    private static final String DICTIONARY_PATH = "classpath:dictionary.xml";

    private static final String PRPPERTIES_PATH = "classpath:gen.properties";

    private static GenDictionary dictionary_defalut = null;// 基础数据 字典数据

    private static Properties properties_defalut = null;// 默认 properties 配置数据

    private static Properties dbproperties_defalut = null;// dbproperties 数据库配置

    private GenDictionary dictionary = null;// 基础数据 字典数据

    private Properties properties = null;// 默认 properties 配置数据

    private Properties dbproperties = null;// dbproperties 数据库配置

    // 默认从数据库中指定表生成 （作为jar使用）
    private GenTypeEnum genTypeEnum = GenTypeEnum.DBTABLEASSIGN;

    private List<TableDataModal> tableNames;// 要生成的表列表

    // 用户配置
    private static String GEN_PREFIX = "gen.";

    public Map<String, String> genMap = new HashMap<>();

    // 暂不生成页面Map 页面属性数据 支持自定义

    // 默认各个包的路径

    public GenConfig() {
        // 读基础数据 字典数据 properties数据 （默认 有新的配置数据会替换掉）
        if (dictionary_defalut == null) {
            logger.info("加载dictionary文件......." + DICTIONARY_PATH);
            dictionary_defalut = (GenDictionary) XmlUtil.xmlToObject(DICTIONARY_PATH, GenDictionary.class);
            logger.info("加载properties文件......." + PRPPERTIES_PATH);
            properties_defalut = PropertyUtil.loadProps(PRPPERTIES_PATH);
        }

        this.dictionary = dictionary_defalut;
        this.properties = properties_defalut;

        this.dbproperties = new Properties();
        for (Object obj : this.properties.keySet()) {// 遍历取出数据的配置
            String key = (String) obj;
            if (key.contains(DbManger.DBPREFIX)) {
                String propertiesName = key.replace(DbManger.DBPREFIX, "");
                this.dbproperties.setProperty(propertiesName, (String) this.properties.get(key));
            }
            if (key.contains(GEN_PREFIX)) {
                String propertiesName = key.replace(GEN_PREFIX, "");
                this.genMap.put(propertiesName, (String) this.properties.get(key));
            }
        }

        // 初始化要处理的表
        this.tableNames = new ArrayList<>();
    }

    public GenConfig(GenDB genDB) {
        this();
        this.genTypeEnum = GenTypeEnum.WEBCONFIGASSIGN;
        if (genDB != null) {
            // 替换掉默认配置
            this.dbproperties.setProperty("name", genDB.getName());
            this.dbproperties.setProperty("dbname", genDB.getDbname());
            this.dbproperties.setProperty("url", genDB.getUrl());
            this.dbproperties.setProperty("username", genDB.getUsername());
            this.dbproperties.setProperty("password", genDB.getPassword());
            this.dbproperties.setProperty("driverClassName", genDB.getDriverClassName());
            if (genDB.getType() == null) {
                this.dbproperties.setProperty("type", "com.alibaba.druid.pool.DruidDataSource");
            } else {
                this.dbproperties.setProperty("type", genDB.getType());
            }
            // db2
            if (this.dbproperties.getProperty("url").indexOf("db2") > -1) {
                this.dbproperties.setProperty("validationQuery", "SELECT current date FROM sysibm.sysdummy1");
            }
        }
    }

    public Properties getDBProperties() {

        return this.dbproperties;
    }

    public GenDictionary getDictionary() {

        return this.dictionary;
    }

    public void setDictionary(GenDictionary dictionary) {

        this.dictionary = dictionary;
    }

    public Properties getProperties() {

        return this.properties;
    }

    public void setProperties(Properties properties) {

        this.properties = properties;
    }

    public void updateProperties(String name, String value) {

        this.properties.setProperty(name, value);
    }

    public List<TableDataModal> getTableNames() {

        return this.tableNames;
    }

    public void setTableNames(List<TableDataModal> tableNames) {

        this.tableNames = tableNames;
    }

    public void addTable(TableDataModal tableName) {

        this.tableNames.add(tableName);
    }

    public GenTypeEnum getGenTypeEnum() {

        return this.genTypeEnum;
    }

    public void setGenTypeEnum(GenTypeEnum genTypeEnum) {

        this.genTypeEnum = genTypeEnum;
    }

    public String getBasicPackage() {

        return this.genMap.get("basicPackage");
    }

    public void setBasicPackage(String basicPackage) {

        this.genMap.put("basicPackage", basicPackage);
    }

    public String getModuleName() {

        return this.genMap.get("moduleName");
    }

    public void setModuleName(String moduleName) {

        this.genMap.put("moduleName", moduleName);
    }

    public String getLocation() {

        if (this.genMap.get("location") == null) {
            return FileLowUtils.getProjectPath();
        }
        return this.genMap.get("location");
    }

    public void setLocation(String location) {

        this.genMap.put("location", location);
    }

    public String getAuthor() {

        return this.genMap.get("author");
    }

    public void setAuthor(String author) {

        this.genMap.put("author", author);
    }

    public String getVersion() {

        return this.genMap.get("version");
    }

    public void setVersion(String version) {

        this.genMap.put("version", version);
    }
    
    
}
