package org.yuequan.spring.beans.factory.support;

import org.yuequan.spring.beans.factory.BeanFactory;
import org.yuequan.spring.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinition.setBeanName(beanName);
        Class<?> clazz = beanDefinition.getBeanClass();
        if("".equals(beanDefinition.getBeanName())){
            beanDefinition.setBeanName(lowerCaseFirstChar(clazz.getSimpleName()));
        }
        beanDefinitionMap.put(beanDefinition.getBeanName(), beanDefinition);
        for (Class<?> classInterface : clazz.getInterfaces()) {
            beanDefinitionMap.put(classInterface.getName(), beanDefinition);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    private String lowerCaseFirstChar(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
