package cn.bubi.basic.gen.bean;

import cn.bubi.basic.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.stereotype.Component;

import cn.bubi.basic.gen.config.WebManger;

/**
 * @Author: JoinHan
 * @Date: Created in 10:10 2018/3/12
 * @Modified By：
 */
@Component
public class DBFactory {

    private static Logger logger = LoggerFactory.getLogger(DBFactory.class);

    public DBFactory() {
        addBean(null);
    }

    public static boolean addBean(WebManger webManger) {

        try {
            if (webManger != null) {
                // 判断
                if (SpringContextUtil.containsBean(webManger.getDbManger().getName())) {
                    // 如果Spring上下文已存在Bean，先删除
                    SpringContextUtil.removeBean(webManger.getDbManger().getName());
                }
                // 创建
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WebManger.class);
                SpringContextUtil.registerBean(webManger.getDbManger().getName(), builder);

                // 对注入的bean 初始化
                WebManger springBean = (WebManger) SpringContextUtil.getBean(webManger.getDbManger().getName());
                springBean.init(webManger);
            } else {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WebManger.class);
                webManger = new WebManger();
                SpringContextUtil.registerBean(webManger.getDbManger().getName(), builder);
            }
            return true;
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
        return false;
    }
}
