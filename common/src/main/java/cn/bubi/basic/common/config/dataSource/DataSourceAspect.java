package cn.bubi.basic.common.config.dataSource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 韩炜
 * @date 2019-03-11 12:54
 */
@Aspect
@Component
public class DataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@within(cn.bubi.basic.common.config.dataSource.DataSource) || @annotation(cn.bubi.basic.common.config.dataSource.DataSource)")
    public void pointCut(){

    }

    @Before("pointCut() && @annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        logger.info("选择数据源---"+dataSource.value().getValue());
        DataSourceTypeManager.set(dataSource.value());
    }

    @After("pointCut()")
    public void doAfter(){
        DataSourceTypeManager.reset();
    }
}
