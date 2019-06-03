package org.eq.modules.bc.common.validate;

import java.lang.annotation.*;

/**
 * 请求幂等型Annotation, 有此标识的字段参加幂等检查.
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Idempotent {
}
