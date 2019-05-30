package org.eq.basic.common.config.dataSource;


/**
 * @author 韩炜
 * @date 2019-03-11 11:03
 */
public class DataSourceTypeManager {
    private static final ThreadLocal<DataSourceTypes> dataSourceTypes = new ThreadLocal<DataSourceTypes>(){
        @Override
        protected DataSourceTypes initialValue(){
            return DataSourceTypes.SLAVE;
        }
    };

    public static DataSourceTypes get(){
        return dataSourceTypes.get();
    }

    public static void set(DataSourceTypes dataSourceType){
        dataSourceTypes.set(dataSourceType);
    }

    public static void reset(){
        dataSourceTypes.set(DataSourceTypes.SLAVE);
    }
}
