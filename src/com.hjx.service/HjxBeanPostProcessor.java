package com.hjx.service;

import com.hjx.spring.BeanPostProcessor;
import com.hjx.spring.Comonent;

/**
 * @author hjx
 * @date 2023/3/2 18:07
 */
@Comonent
public class HjxBeanPostProcessor implements BeanPostProcessor{
    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        //可以对某些bean进行处理 Bean的名称以及该可以获取
        if(beanName.equals("userService")) {
            System.out.println("BeforeuserService");
        }
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {
        //可以对某些bean进行处理 Bean的名称以及该可以获取
        if(beanName.equals("userService")) {
            System.out.println("AfteruserService");
        }
    }
}
