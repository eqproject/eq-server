package org.eq.basic.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听Session的创建与销毁 servlet api 级别
 *
 * @Author: JoinHan
 * @Date: Created in 11:57 2018/3/5
 * @Modified By：
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

        System.out.println("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        System.out.println("Session 销毁");
    }

}