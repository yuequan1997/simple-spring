package org.yuequan.spring.beans.factory.config;

public class BeanDefinition {
    private String beanClassName;
    private String beanName;

    public BeanDefinition(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public BeanDefinition(String beanClassName, Class<?> beanClass, String beanName) {
        this.beanClassName = beanClassName;
        this.beanName = beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public Class<?> getBeanClass() {
        try {
            return Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
