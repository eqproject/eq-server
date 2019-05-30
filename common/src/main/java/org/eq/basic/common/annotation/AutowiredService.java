package org.eq.basic.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的service
 *
 * @Author: JoinHan
 * @Date: Created in 10:53 2018/2/24
 */
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutowiredService {
}
