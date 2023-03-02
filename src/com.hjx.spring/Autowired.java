package com.hjx.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hjx
 * @date 2023/1/15 0:03
 */
@Retention(RetentionPolicy.RUNTIME)//生效时间
@Target(ElementType.FIELD)//此注解使用的位置 FIELD字符串上面 type 类上面
public @interface Autowired {
//    String value() default "";//给加上注解的bean设置一个名称
}
