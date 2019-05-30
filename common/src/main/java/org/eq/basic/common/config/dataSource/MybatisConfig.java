package org.eq.basic.common.config.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * Mybatis 配置
 * Created by JoinHan on 2017/4/14.
 */
@Configuration
@EnableTransactionManagement//启用事务控制
@MapperScan(basePackages ="org.eq.**.**.**.dao")
public class MybatisConfig {

    /**
     * master DataSource
     * @ConfigurationProperties 注解用于从 application.properties 文件中读取配置，为 Bean 设置属性
     * @return data source
     */
    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave() {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDefaultReadOnly(true);
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 动态数据源配置
     * @Primary 注解用于标识默认使用的 DataSource Bean
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource db1, @Qualifier("slave") DataSource db2) {
        ThreadLocalRountingDataSource multipleDataSource = new ThreadLocalRountingDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypes.MASTER.getValue(), db1);
        targetDataSources.put(DataSourceTypes.SLAVE.getValue(), db2);
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(db2);
        return multipleDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(multipleDataSource(master(),slave()));
        //bean.setTypeAliasesPackage("com.reporting.modules.");
        //添加数据权限过滤
        //bean.setPlugins(new Interceptor[]{new DataAuthorityInterceptor()});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mappings/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 事务管理器配置
     * @return
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
