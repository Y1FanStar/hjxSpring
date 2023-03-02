package com.hjx.spring;

/**
 * @author hjx
 * @date 2023/1/15 0:39
 */
public class BeanDefinition {
    private Class type;
    private String scrope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScrope() {
        return scrope;
    }

    public void setScrope(String scrope) {
        this.scrope = scrope;
    }
}
