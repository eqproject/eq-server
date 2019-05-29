package cn.bubi.basic.common.listener;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import cn.bubi.basic.common.annotation.AutowiredService;

/**
 * spring容器初始化完成事件
 * Created by shuzheng on 2017/1/7.
 */
@Component
public class ApplicationContextListener {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        /*
         * while(context.getParent()!=null){
         * context = context.getParent();
         * }
         */
        // root application context
        // if(null == context.getParent()) {
        this.logger.debug(">>>>> spring初始化完毕 initMapper<<<<<");
        // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
        Map<String, Object> baseServices = context.getBeansWithAnnotation(AutowiredService.class);
        for (Object service : baseServices.values()) {

            try {
                Method initMapper = service.getClass().getMethod("initMapper");
                initMapper.invoke(service);
            } catch(Exception e) {
                this.logger.error("初始化BaseService的initMapper方法异常", e);
            }
        }
        // }
    }

}
