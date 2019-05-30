package org.eq.basic.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来在对象的get方法上加入的annotation，通过该annotation说明某个属性所对应的标题 Created by 钟述林
 * 393156105@qq.com on 2016/10/29 0:14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {

    /**
     * 属性的标题名称
     *
     * @return
     */
    String title();

    /**
     * 顺序
     *
     * @return
     */
    int order() default 9999;

    /**
     * 属性类型 数据字典 1 ，时间 2，sql 3，地区 4，机构 5，默认为空 普通值类型
     *
     * @return
     */
    String type() default "";

    /**
     * 日期格式
     *
     * @return
     */
    String dateFormat() default "yyyy-MM-dd";

    /**
     * 是否允许导入
     *
     * @return
     */
    boolean ifImport() default false;

    /**
     * 是否允许导出
     *
     * @return
     */
    boolean ifExport() default false;

    /**
     * 数据字典类型
     *
     * @return
     */
    String dictionary() default "";

    /**
     * 可执行sql 多个
     *
     * @return
     */
    List[] sqlList() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        /**
         * 缓存使用的sql {id=**,name=++}
         *
         * @return
         */
        String sql();

        /**
         * 导入是否预缓存，导出一律使用缓存
         *
         * @return
         */
        boolean ifCash() default true;
    }
}
