package cn.bubi.basic.common.config.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 韩炜
 * @date 2019-03-11 11:09
 */
public class ThreadLocalRountingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceTypeManager.get();
    }
}
