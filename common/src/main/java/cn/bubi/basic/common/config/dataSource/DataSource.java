package cn.bubi.basic.common.config.dataSource;

import java.lang.annotation.*;

/**
 * @author 韩炜
 * @date 2019-03-11 12:26
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceTypes value() default DataSourceTypes.SLAVE;
}
