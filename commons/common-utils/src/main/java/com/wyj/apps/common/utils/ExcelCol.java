package com.wyj.apps.common.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义导出Excel列的属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelCol {

    enum OPER {
        ADD, //加法œ
        MULTIPLY, //乘法
        APPEND, //拼接
        REPLACE, //替换
    }

    /**
     * 导出到Excel中的名字.
     */
    String name() default "";

    /**
     * 配置列的名称,对应0,1,2,3,4,5....
     */
    int colIndex();

    /**
     * 结合其他列
     * ADD: 将其他列的值相加
     * MULTIPLY: 将其他列的值相乘
     * APPEND：最后一个值为连接的值
     * REPLACE:第i个值可替换为第i+1个的值
     */
    String[] combo() default {};

    /**
     * 是否显示标题
     */
    boolean showTitle() default true;

    /**
     * 是否显示内容
     */
    boolean showContent() default true;

    /**
     * 列之间的操作
     */
    OPER operation() default OPER.ADD;

    /**
     * 输出的的格式
     */
    String format() default "";
}