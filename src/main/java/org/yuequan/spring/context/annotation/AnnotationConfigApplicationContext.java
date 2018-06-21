package org.yuequan.spring.context.annotation;

import org.yuequan.spring.context.support.GenericApplicationContext;
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotaionConfigRegistry {
   // private final BeanDefinitionRegistry registry;
    private final ClassPathBeanDefinitionScanner scanner;

    public AnnotationConfigApplicationContext(String ... basePackages){
        this.scanner = new ClassPathBeanDefinitionScanner(this);
        scan(basePackages);
        refresh();
    }

    private void refresh() {
    }

    public void registry(Class<?>... annotatedClasses) {

    }

    public void scan(String... basePackages) {
        this.scanner.scan(basePackages);
    }

}
