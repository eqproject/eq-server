package org.eq.basic.gen.status;

/**
 * @Author: JoinHan
 * @Date: Created in 17:49 2018/2/2
 * @Modified By：
 */
public enum DBTypeEnum {

    MYSQLDB(1, "mysql"), ORACLEDB(2, "oracle"),DB2(3, "db2");

    private Integer dbTypeId;

    private String dbTypeDescription;

    DBTypeEnum(Integer dbTypeId, String dbTypeDescription) {

        this.dbTypeId = dbTypeId;
        this.dbTypeDescription = dbTypeDescription;
    }

    public Integer getDbTypeId() {

        return this.dbTypeId;
    }

    public String getDbTypeDescription() {

        return this.dbTypeDescription;
    }

}
