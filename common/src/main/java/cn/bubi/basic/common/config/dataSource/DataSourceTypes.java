package cn.bubi.basic.common.config.dataSource;

/**
 * @author 韩炜
 * @date 2019-03-11 11:00
 */
public enum DataSourceTypes {
    MASTER("master"),SLAVE("slave");

    private String value;

    DataSourceTypes(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
