package org.yuequan.spring.context.support;

import org.yuequan.spring.beans.factory.config.BeanDefinition;
import org.yuequan.spring.beans.factory.support.BeanDefinitionRegistry;
import org.yuequan.spring.beans.factory.support.DefaultListableBeanFactory;
import org.yuequan.spring.context.ApplicationContext;

public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext(){
        beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return beanFactory.getBeanDefinition(beanName);
    }

    public boolean containsBeanDefinition(String beanName) {
        return beanFactory.containsBeanDefinition(beanName);
    }

    public int getBeanDefinitionCount() {
        return beanFactory.getBeanDefinitionCount();
    }

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }
}
