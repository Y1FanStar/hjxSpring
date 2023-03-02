package com.hjx.service;

import com.hjx.spring.HjxSpringApplicationContext;

/**
 * @author hjx
 * @date 2023/1/14 23:47
 */
public class Test {
    public static void main(String[] args) {
        //1 spring容器
        HjxSpringApplicationContext applicationContext = new HjxSpringApplicationContext(AppConfig.class);

//        UserService useranme = (UserService)aplicationContext.getBean("UserService");
       UserService userService = (UserService) applicationContext.getBean("userService");
       userService.test();
        //2 扫描
//        获取扫描路径
    }
}
