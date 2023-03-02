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
        /**com.sun.proxy.$Proxy5 cannot be cast to com.hjx.service.UserService
         * jdk的代理对象只会将类的继承的额接口代理出来而不是这个类  所以接口不能强转成为一个类 会导致报错
         * */
        UserService userService = (UserService) applicationContext.getBean("userService");
//        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
       userService.test();
        //2 扫描
//        获取扫描路径
    }
}
