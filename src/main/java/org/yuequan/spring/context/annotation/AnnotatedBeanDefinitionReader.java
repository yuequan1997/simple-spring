package org.yuequan.spring.context.annotation;

import org.yuequan.spring.beans.factory.support.BeanDefinitionRegistry;

public class AnnotatedBeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


}
