package com.hjx.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模拟spring的扫描方法
 * @author hjx
 * @date 2023/1/14 23:55
 */
@Retention(RetentionPolicy.RUNTIME)//生效时间
@Target(ElementType.TYPE)//此注解使用的位置
public @interface ComponentScan {
    String value() default "";//获取路径
}
