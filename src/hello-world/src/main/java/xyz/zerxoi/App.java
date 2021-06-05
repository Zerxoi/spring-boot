package xyz.zerxoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import xyz.zerxoi.bean.Car;
import xyz.zerxoi.bean.Pet;
import xyz.zerxoi.bean.User;
import xyz.zerxoi.config.MyConfiguration;

// 等同于 @EnableAutoConfiguration @ComponentScan @Configuration
// @SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
// 给容器中自动创建出这两个类型的组件、默认组件的名字就是全类名
@Import({ User.class, Pet.class })
// 使用 xml 的方式导入 spring 配置文件
@ImportResource("classpath:beans.xml")
// 配置绑定：开启 Car 配置绑定功能并将组件注册到容器中
@EnableConfigurationProperties(Car.class) // 类似于在 Car 类上加 @Component 注解
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        for (String s : context.getBeanDefinitionNames()) {
            System.out.println(s);
        }
        // 获取配置类 Bean
        MyConfiguration configuration = context.getBean(MyConfiguration.class);
        User jack = context.getBean("jack", User.class);
        // 配置类的 Lite mode 通过配置文件 @Configuration 的 proxyBeanMethods 属性控制
        System.out.println("is Lite mode? " + (configuration.jack() != jack));
        Pet tomcat = context.getBean("tomcat", Pet.class);
        // User 和 Pet 组件是否有依赖关系
        System.out.println("is jack's pet is tomcat? " + (jack.getPet() == tomcat));

        for (String name : context.getBeanNamesForType(User.class)) {
            System.out.println(name);
        }
        for (String name : context.getBeanNamesForType(Pet.class)) {
            System.out.println(name);
        }

        User morty = context.getBean("morty", User.class);
        System.out.println(morty.getPet());

        // 使用配置属性创建组件
        for (String name : context.getBeanNamesForType(Car.class)) {
            System.out.println(name);
        }
        System.out.println(context.getBean(Car.class));
    }
}
