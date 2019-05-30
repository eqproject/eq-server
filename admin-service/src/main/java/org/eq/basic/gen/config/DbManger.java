package org.eq.basic.gen.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.eq.basic.gen.status.DBTypeEnum;

/**
 * 数据库管理类
 *
 * @Author: JoinHan
 * @Date: Created in 10:45 2018/2/2
 * @Modified By：
 */
public class DbManger {

    private static Logger logger = LoggerFactory.getLogger(DbManger.class);

    public static final String DBPREFIX = "spring.datasource.master.";// 配置文件中数据库配置前缀 默认

    private DruidDataSource druidDataSource = null;

    private Map<String, String> propertiesMap = new HashMap<>();// 数据库配置保存

    private DBTypeEnum dbTypeEnum;

    private String dbName;

    public String getDbName() {

        return this.dbName;
    }

    public void setDbName(String dbName) {

        this.dbName = dbName;
    }

    public DBTypeEnum getDbTypeEnum() {

        return this.dbTypeEnum;
    }

    public void setDbTypeEnum(DBTypeEnum dbTypeEnum) {

        this.dbTypeEnum = dbTypeEnum;
    }

    private DbManger(Properties properties) throws Exception {
        // 提取有用的数据库配置 并替换默认配置 创建连接池
        this.druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(this.beforeInit(properties));
    }

    private Properties beforeInit(Properties properties) {

        Properties dbproperties = new Properties();
        for (Object obj : properties.keySet()) {// 遍历取出数据的配置
            String propertiesName = (String) obj;
            if (this.propertiesMap.get(propertiesName) == null) {
                dbproperties.setProperty(propertiesName, (String) properties.get(propertiesName));
                this.propertiesMap.put(propertiesName, (String) properties.get(propertiesName));
            } else {
                dbproperties.setProperty(propertiesName, this.propertiesMap.get(propertiesName));
            }
            if (propertiesName.equals("driverClassName")) {
                if ("com.mysql.jdbc.Driver".equals(properties.get(propertiesName))) {
                    this.dbTypeEnum = DBTypeEnum.MYSQLDB;
                } else if ("com.ibm.db2.jcc.DB2Driver".equals(properties.get(propertiesName))) {
                    this.dbTypeEnum = DBTypeEnum.DB2;
                }
            }
            if (propertiesName.equals("dbname")) {
                this.dbName = (String) properties.get(propertiesName);
            }

        }
        return dbproperties;
    }

    /**
     * 数据库连接池
     *
     * @return
     */
    public static DbManger initDb(Properties properties) {

        // if (null == dbManger){
        try {
            DbManger dbManger = new DbManger(properties);
            return dbManger;
        } catch(Exception e) {
            logger.debug(e.getMessage());
            logger.error("初始化数据库连接池失败");
        }
        // }else {
        // logger.error("数据库连接池以有，取默认配置");
        // }
        return null;
    }

    /**
     * 返回druid数据库连接
     *
     * @return
     * @throws SQLException
     */
    public DruidPooledConnection getConnection() throws SQLException {

        return this.druidDataSource.getConnection();
    }

    public String getName() {

        return this.propertiesMap.get("name");
    }

    public void setName(String name) {

        this.propertiesMap.put("name", name);
    }

    public String getUrl() {

        return this.propertiesMap.get("url");
    }

    public void setUrl(String url) {

        this.propertiesMap.put("url", url);
    }

    public String getUsername() {

        return this.propertiesMap.get("username");
    }

    public void setUsername(String username) {

        this.propertiesMap.put("username", username);
    }

    public String getPassword() {

        return this.propertiesMap.get("password");
    }

    public void setPassword(String password) {

        this.propertiesMap.put("password", password);
    }

    public String getDriverClassName() {

        return this.propertiesMap.get("driverClassName");
    }

    public void setDriverClassName(String driverClassName) {

        this.propertiesMap.put("driverClassName", driverClassName);
    }

    public DruidDataSource getDruidDataSource() {

        return this.druidDataSource;
    }

    public void setDruidDataSource(DruidDataSource druidDataSource) {

        this.druidDataSource = druidDataSource;
    }
}
