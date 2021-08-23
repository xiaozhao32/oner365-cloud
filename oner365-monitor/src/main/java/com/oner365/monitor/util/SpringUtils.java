package com.oner365.monitor.util;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author zhaoyong
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor {
    /** Spring应用上下文环境 */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 获取对象
     *
     * @param name 对象
     * @return Object 一个以所给名字注册的bean的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类
     * @return 对象
     */
    public static <T> T getBean(Class<T> clz) {
        return beanFactory.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name 名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name 名称
     * @return boolean
     */
    public static boolean isSingleton(String name) {
        return beanFactory.isSingleton(name);
    }

    /**
     * @param name 名称
     * @return Class 注册对象的类型
     */
    public static Class<?> getType(String name) {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name 名称
     * @return String[]
     */
    public static String[] getAliases(String name) {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     *
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy() {
        return (T) AopContext.currentProxy();
    }
}
