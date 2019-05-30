package org.eq.basic.common.config;

import org.eq.basic.common.filter.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc web 设置
 * Created by JoinHan on 2017/4/14.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态访问资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // 可以直接使用addResourceLocations 指定磁盘绝对路径，同样可以配置多个位置，注意路径写法需要加上file:
        // registry.addResourceHandler("/myimgs/**").addResourceLocations("file:H:/myimgs/");
        // registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars");
    }

    // 配置便捷URL访问
    /*
     * @Override
     * public void addViewControllers(ViewControllerRegistry registry) {
     * registry.addViewController("/home").setViewName("home");
     * registry.addViewController("/login").setViewName("login");
     * }
     */

    /**
     * spring 级别的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
