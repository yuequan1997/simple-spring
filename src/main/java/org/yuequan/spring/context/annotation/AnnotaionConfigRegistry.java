package org.yuequan.spring.context.annotation;

public interface AnnotaionConfigRegistry {
    void registry(Class<?> ... annotatedClasses);
    void scan(String ... basePackages);
}
