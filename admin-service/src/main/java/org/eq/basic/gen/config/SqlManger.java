package org.eq.basic.gen.config;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eq.basic.gen.status.DBTypeEnum;
import org.eq.basic.gen.status.GenTypeEnum;

/**
 * 生成必要的sql 语句
 * <var> 代表要替换的变量
 *
 * @Author: JoinHan
 * @Date: Created in 17:25 2018/2/6
 * @Modified By：
 */
public class SqlManger {

 // 查询数据库中所有的表
    private String allTableInfoSql;

    // 查询数据库某表的字段
    private String allCoulmnInfoSql;

    // 验证连接可用性
    private String validSql;

    public String getAllTableInfoSql() {

        return this.allTableInfoSql;
    }

    public String getAllCoulmnInfoSql() {

        return this.allCoulmnInfoSql;
    }

    public String getValidSql() {

        return this.validSql;
    }

    public static SqlManger initSql(GenConfig genConfig, DbManger dbManger) {

        SqlManger sqlManger = new SqlManger();
        if (genConfig.getGenTypeEnum() == GenTypeEnum.DBTABLEASSIGN) {// java 代码指定
            if (dbManger.getDbTypeEnum() == DBTypeEnum.MYSQLDB) {
                sqlManger.allTableInfoSql = "select table_name tableName,table_schema schemaName,table_comment comments from information_schema.tables where table_schema = '"
                        + dbManger.getDbName() + "' ";
                sqlManger.allCoulmnInfoSql = "select table_name tableName,column_name columnName,data_type dataType,column_comment columnComment,table_schema schemaName "
                        + "from information_schema.columns where table_schema = '" + dbManger.getDbName()
                        + "' and table_name = '<tableName>'";
                sqlManger.validSql = "select 'x'";
            }
        } else if (genConfig.getGenTypeEnum() == GenTypeEnum.WEBCONFIGASSIGN) {// Web 配置
            if (dbManger.getDbTypeEnum() == DBTypeEnum.MYSQLDB) {
                sqlManger.allTableInfoSql = "select table_name tableName,table_schema schemaName,table_comment comments from information_schema.tables where table_schema = '"
                        + dbManger.getDbName() + "' <order> <limit>";
                sqlManger.allCoulmnInfoSql = "select table_name tableName,column_name columnName,data_type dataType,column_comment columnComment,table_schema schemaName "
                        + "from information_schema.columns where table_schema = '" + dbManger.getDbName()
                        + "' and table_name = '<tableName>'" + " <order> <limit>";
                sqlManger.validSql = "select 'x'";
            } else if (dbManger.getUrl().indexOf("db2") > -1) {
                sqlManger.allTableInfoSql = "select tabname tableName,current SCHEMA schemaName,REMARKS comments from syscat.tables where tabschema = current schema <order>";
                sqlManger.allCoulmnInfoSql = "select TABNAME tableName,COLNAME columnName,TYPENAME dataType,REMARKS columnComment,TABSCHEMA schemaName from SYSCAT.COLUMNS where TABSCHEMA=current SCHEMA and TABNAME='<tableName>' <order>";
                sqlManger.validSql = "SELECT current date FROM sysibm.sysdummy1";
            }
        }
        return sqlManger;
    }

    public static String replaceVar(String orignal, Map<String, Object> param) {

        Pattern p = Pattern.compile("<(\\S*)>");
        Matcher matcher = p.matcher(orignal);
        while (matcher.find()) {
            String matcherStr = matcher.group();
            String sub = matcherStr.substring(1, matcherStr.length() - 1);
            String value = (String) param.get(sub);
            if (value != null) {
                orignal = orignal.replace(matcherStr, value);
            }
        }
        return orignal;
    }
}
