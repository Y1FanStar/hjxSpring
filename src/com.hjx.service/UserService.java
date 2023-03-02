package com.hjx.service;

import com.hjx.spring.Autowired;
import com.hjx.spring.Comonent;
import com.hjx.spring.Scope;

/**
 * @author hjx
 * @date 2023/1/14 23:47
 */
@Comonent()
@Scope()
public class UserService {

    //依赖注入
    @Autowired
    private orderService orderService;

    public void test(){
        System.out.println(orderService);
    }

}
