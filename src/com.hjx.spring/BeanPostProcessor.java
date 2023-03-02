package com.hjx.spring;

/**
 * @author hjx
 * @date 2023/3/2 18:05
 */
public interface BeanPostProcessor {
    public Object postProcessBeforeInitialization(String beanName,Object bean);
    public Object postProcessAfterInitialization(String beanName,Object bean);
}
