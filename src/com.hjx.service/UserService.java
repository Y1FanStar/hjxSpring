package com.hjx.service;

import com.hjx.spring.*;

/**
 * @author hjx
 * @date 2023/1/14 23:47
 */
@Comonent()
@Scope()
public class UserService implements UserInterface,BeanNameAware, InitializingBean {

    //依赖注入
    @Autowired
    private OrderService orderService;
    //beanName
    private String beanName;

    public void test(){
        System.out.println(orderService);
    }


    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        //todo 给对象赋值 todosomething 初始化方法

    }


}
