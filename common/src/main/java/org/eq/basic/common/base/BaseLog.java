package org.eq.basic.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author admin
 * @Title: BaseLog
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: 123.com
 * @Created on 2019/6/1下午11:41
 */
public abstract class BaseLog {

    // 公共 controller 打印日志定义
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

}
