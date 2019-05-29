package cn.bubi.basic.gen.status;

/**
 * @Author: JoinHan
 * @Date: Created in 17:49 2018/2/2
 * @Modified By：
 */
public enum GenTypeEnum {

    DBTABLEASSIGN(1, "指定数据库中的表"), // 默认值
    DBTABLEALL(2, "指定数据库中所有表"), WEBCONFIGASSIGN(3, "从web配置中生成");

    private Integer genTypeId;

    private String genTypeDescription;

    GenTypeEnum(Integer genTypeId, String genTypeDescription) {

        this.genTypeId = genTypeId;
        this.genTypeDescription = genTypeDescription;
    }

    public Integer getGenTypeId() {

        return this.genTypeId;
    }

    public String getGenTypeDescription() {

        return this.genTypeDescription;
    }
}
