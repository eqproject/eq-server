package org.eq.basic.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用@WebListener注解，实现ServletContextListener接口 servlet api 级别
 *
 * @Author: JoinHan
 * @Date: Created in 11:53 2018/3/5
 * @Modified By：
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        System.out.println("ServletContex初始化");
        System.out.println(servletContextEvent.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        System.out.println("ServletContex销毁");
    }

}