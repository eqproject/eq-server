package cn.bubi.basic.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 使用ehcache 做数据缓存
 * 缓存配置类
 *
 * @author JoinHan on 2017/04/24
 */
@Configuration
@EnableCaching // 标注启动了缓存
public class CacheConfiguration {

    /**
     * ehcache 主要的管理器
     *
     * @param bean
     * @return
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {

        EhCacheCacheManager ehCacheManager = null;
        if (bean != null) {
            ehCacheManager = new EhCacheCacheManager(bean.getObject());
        }
        return ehCacheManager;
    }

    /**
     * 读取配置文件
     *
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {

        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

}
