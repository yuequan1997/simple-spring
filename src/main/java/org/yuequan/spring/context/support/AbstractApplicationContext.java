package org.yuequan.spring.context.support;

import org.yuequan.spring.beans.factory.support.DefaultListableBeanFactory;
import org.yuequan.spring.context.ApplicationContext;
import org.yuequan.spring.context.ConfiguableApplicationContext;

public abstract class AbstractApplicationContext implements ConfiguableApplicationContext {
    @Override
    public void refresh() {
        DefaultListableBeanFactory beanFactory = obtainFreshBeanFactory();
        finishBeanFactoryInitialization(beanFactory);
    }

    private DefaultListableBeanFactory obtainFreshBeanFactory() {
        return getBeanFactory();
    }

    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
        beanFactory.preInstantiateBeans();
    }

    public abstract DefaultListableBeanFactory getBeanFactory();
}
