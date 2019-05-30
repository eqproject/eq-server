package org.eq.basic.license.entity;

/**
 * @Author: JoinHan
 * @Date: Created in 12:56 2018/2/12
 * @Modified By：
 */
public enum LicenseEnum {

    STANDALONE(1, "单机版授权");

    LicenseEnum(Integer id, String description) {
        this.description = description;
        this.id = id;
    }

    private String description;

    private Integer id;

    public String getDescription() {

        return this.description;
    }

    public Integer getId() {

        return this.id;
    }
}
