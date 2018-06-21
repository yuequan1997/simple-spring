package org.yuequan.spring.beans.factory.support;

import org.yuequan.spring.beans.factory.BeanFactory;
import org.yuequan.spring.beans.factory.annotation.Autowired;
import org.yuequan.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> beanCacheMap = new ConcurrentHashMap<>();
    @Override
    public Object getBean(String name) {
        if(beanCacheMap.containsKey(name)){
            return beanCacheMap.get(name);
        }
        return doGetBean(name);
    }

    private Object doGetBean(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        try {
            Object object = beanDefinition.getBeanClass().newInstance();
            populateBean(beanDefinition.getBeanName(), object, beanDefinition);
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public void preInstantiateBeans(){
        beanDefinitionMap.forEach((key, value) -> {
            if(!beanCacheMap.containsKey(key)){
                getBean(key);
            }
        });
    }

    protected void populateBean(String beanName, Object object, BeanDefinition beanDefinition){
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Autowired.class)){
                Autowired autowired = field.getAnnotation(Autowired.class);
                String autowiredName = autowired.value().trim();
                if("".equals(autowiredName)){
                    if(field.getType().isInterface()){
                        autowiredName = field.getType().getName();
                    }else{
                        autowiredName = lowerCaseFirstChar(field.getType().getSimpleName());
                    }
                }
                Object autowiredObj = beanCacheMap.get(autowiredName);
                if(autowiredObj == null){
                    autowiredObj = getBean(autowiredName);
                }
                try {
                    field.setAccessible(true);
                    field.set(object, autowiredObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
