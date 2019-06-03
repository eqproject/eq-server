package org.eq.modules.bc.common.validate;

import java.lang.annotation.*;

/**
 * 参与签名标识.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Signable {
    /**
     * 签名字段名.
     * @return 中文描述
     */
    String value() default "";

    /**
     * 签名顺序.
     * @return 顺序
     */
    int sequence() default 0;
}
