package org.eq.modules.bc.common.validate;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Validatable {
    /**
     * Property name, used to be handled as representation.
     * @return property name
     */
    String value();

    /**
     * Can be regular expression. This condition is exclusive, prior to other conditions.
     *
     * @return regular expression
     */
    String expression() default "";

    /**
     * Min length of the property, default 1;
     * @return
     */
    long minLength() default 1;

    /**
     * Max length of the property, default is Integer.MAX_VALUE;
     * @return
     */
    long maxLength() default Long.MAX_VALUE;

    /**
     * Min value of the property, default 1;
     * @return
     */
    long minValue() default 0;

    /**
     * Max Value of the property, default is Integer.MAX_VALUE;
     * @return
     */
    long maxValue() default Long.MAX_VALUE;

    /**
     * Could be null or not.
     * @return
     */
    boolean nullable() default false;

    /**
     * Whether is a numeric.
     * @return
     */
    boolean isNumeric() default false;
    boolean isPositiveLong() default false;
    /**
     * Minimum number.
     * @return
     */
    long minNumeric() default Long.MIN_VALUE;

    /**
     * Maximum number.
     * @return
     */
    long maxNumeric() default Long.MAX_VALUE;

    /**
     * Minimum amount.
     * @return
     */
    String minAmount() default "0.00";
    
    /**
     * Whether is Currency.
     * @return
     */
    boolean isCurrency() default false;

    /**
     * Whether is Date.
     * @return
     */
    boolean isDate() default false;

    /**
     * Whether is email.
     * @return
     */
    boolean isEmail() default false;

    /**
     * Whether is identity.
     * @return
     */
    boolean isIdentity() default false;

    /**
     * @return
     */
    boolean isPhone() default false;

    /**
     * Test whether is a valid URL.
     * @return
     */
    boolean isUrl() default false;
    
    /**
     *  Condition. If the specified method returns true, the property is validated.
     * @return method which indicates true or false
     */
    String dependesOn() default "";

    /**
     * @return Class of a enum which implements toEnum method
     */
    Class<?> enumScope() default Void.class;
    
    /**
     * @return
     */
    int maxSize() default 10;
    
    int minSize() default 0;
    
}
