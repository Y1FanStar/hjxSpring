package com.hjx.spring;

import com.sun.org.apache.regexp.internal.recompile;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hjx
 * @date 2023/1/14 23:49
 */
public class HjxSpringApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private ArrayList<BeanPostProcessor> beanPostProcessorArrayList = new ArrayList<>();
    public HjxSpringApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描bean 如果加上了compent注解 判断bean是否为单例 是则在spring容器启动的时候就把这个bean添加到map中去
        //判断是否加上了扫描的注解
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value();//获取路径
            path = path.replace(".", "/");
            ClassLoader classLoader = HjxSpringApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);

            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getAbsolutePath();
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");
                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            //代表是用户定义的bean
                            if (clazz.isAnnotationPresent(Comonent.class)) {
                                // isAssignableFrom 判断class文件是否有继承某某接口
                                // clazz instanceof 判断java类的方式
                                if (BeanPostProcessor.class.isAssignableFrom(clazz)){
                                    BeanPostProcessor instance = (BeanPostProcessor)clazz.newInstance();
                                    beanPostProcessorArrayList.add(instance);
                                }
                                Comonent comonent = clazz.getAnnotation(Comonent.class);
                                String beanName = comonent.value();
                                //没有注解名称的时候
                                if (beanName.equals("")) {
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }
                                //则为我们需要的Bean
                                // 多例bean 在需要的时候再创建
                                BeanDefinition beanDefinition = new BeanDefinition();
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScrope(scopeAnnotation.value());
                                } else {
                                    //默认单例
                                    beanDefinition.setScrope("singleton");
                                }
                                //type 保存当前bean的class
                                beanDefinition.setType(clazz);
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        // 获取单例bean

        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScrope())) {
                Object bean = creatBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    /**
     * 创建一个bean对象模拟bean的生命周期
     *
     * @return
     */
    private Object creatBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            // 依赖注入----> 并不是给所有的属性都进行赋值 、对有加上autowried的注解的属性赋值
            // 所以第一步就是获取到有这个名称的
            Object instance = clazz.getConstructor().newInstance();
            //遍历所有的属性
            for (Field f : clazz.getDeclaredFields()) {
                //只处理有这个注解的属性
                if (f.isAnnotationPresent(Autowired.class)) {
                    //开启属性赋值
                    f.setAccessible(true);
                    //把我们需要的对象类附入
                    f.set(instance, getBean(f.getName()));
                }
            }
            // 判断对象是否有实现beanName接口 aware回调实现
            if(instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            // 扫描的时候就会调用方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorArrayList) {
                beanPostProcessor.postProcessBeforeInitialization(beanName,instance);
                beanPostProcessor.postProcessAfterInitialization(beanName,instance);
            }
            // 初始化 做一些事情
            if(instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }
            // 初始化后进行 AOP
            // BeanPostProcessor bean的后置处理

            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getBean(String beanName) {
        // 判断bean为单例还是多例（自行创建一个逐渐注解上加上名称 表示多例或者单例）

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (null == beanDefinition) {
            throw new NullPointerException();
        } else {
            String scrope = beanDefinition.getScrope();
            if ("singleton".equals(scrope)) {
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    //单例池中没有对应的对象的时候
                    Object o = creatBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                }
                return bean;
            } else {
                //多例情况下每次都会进行创建
                return creatBean(beanName, beanDefinition);
            }
        }
    }
}
