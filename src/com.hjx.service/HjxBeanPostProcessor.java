package com.hjx.service;

import com.hjx.spring.BeanPostProcessor;
import com.hjx.spring.Comonent;

import javax.management.ObjectInstance;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author hjx
 * @date 2023/3/2 18:07
 */
@Comonent
public class HjxBeanPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        //可以对某些bean进行处理 Bean的名称以及该可以获取
        if(beanName.equals("userService")) {
            System.out.println("BeforeuserService");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        //可以对某些bean进行处理 Bean的名称以及该可以获取
        if(beanName.equals("userService")) {
            //创建一个代理对象
            Object objectInstace = Proxy.newProxyInstance(HjxBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("....各种切面逻辑");
                    return method.invoke(bean,args);
                }
            });
            System.out.println("AfteruserService");
            return objectInstace;
        }
        return bean;
    }
}
