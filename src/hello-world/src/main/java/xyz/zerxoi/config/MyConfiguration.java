package xyz.zerxoi.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xyz.zerxoi.bean.Pet;
import xyz.zerxoi.bean.User;

/**
 * 1. 配置类本身也是 Bean 对象 2. 配置类里面使用 @Bean 标注在方法上给容器注册组件，默认是单实例的（通过 proxyBeanMethods
 * 控制） 3. proxyBeanMethods：代理bean的方法 - Full(proxyBeanMethods = true)
 * 【保证每个@Bean方法被调用多少次返回的组件都是单实例的】 - Lite(proxyBeanMethods = false)
 * 【每个@Bean方法被调用多少次返回的组件都是新创建的】 - 组件依赖必须使用Full模式默认。其他默认是否Lite模式
 * 
 */

// @Configuration(proxyBeanMethods = false) // Lite mode
@Configuration
public class MyConfiguration {
    @Bean
    // @Bean("tom") // 将 Bean 命名为 tom
    public Pet tomcat() {
        return new Pet("tomcat");
    }

    @Bean
    @ConditionalOnBean(name = "tomcat") // 条件装配：当容器中有 tomcat 组件时才创建 jack
    public User jack() {
        User jack = new User();
        jack.setAge(18);
        jack.setName("jack");
        // User 组件依赖 Pet 组件
        jack.setPet(tomcat());
        return jack;
    }

}
