package org.yuequan.spring.context.annotation;

import org.yuequan.spring.beans.factory.config.BeanDefinition;
import org.yuequan.spring.beans.factory.support.BeanDefinitionRegistry;
import org.yuequan.spring.core.stereotype.Component;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public int scan(String... basePackages) {
        int beanCountAtScanStart = this.registry.getBeanDefinitionCount();
        doScan(basePackages);
        return (this.registry.getBeanDefinitionCount() - beanCountAtScanStart);
    }

    private void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            candidates.forEach(candidate -> {
                Class<?> clazz = candidate.getBeanClass();
                if(clazz.isAnnotationPresent(Component.class)){
                    Component component = clazz.getAnnotation(Component.class);
                    registerBeanDefinition(component.value(), candidate, registry);
                }
            });
        }
    }

    private void registerBeanDefinition(String componentValue, BeanDefinition candidate, BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(componentValue, candidate);
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        return findCandidateComponents(basePackage, candidates);
    }
    private Set<BeanDefinition> findCandidateComponents(String basePackage, Set<BeanDefinition> candidates) {
        String packageSearchPath = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/")).getFile();
        File files = new File(packageSearchPath);
        for (File file : files.listFiles()) {
            if(file.isDirectory()){
                findCandidateComponents(basePackage + "." + file.getName(), candidates);
            }else{
                BeanDefinition beanDefinition = new BeanDefinition(basePackage + "." + file.getName().replace(".class", ""));
                candidates.add(beanDefinition);
            }
        }
        return candidates;
    }
}
