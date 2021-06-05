Web 开发
===

# 1 Spring MVC 自动配置概览

参考文档： [Spring MVC Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications)

Spring Boot 为 Spring MVC 提供了自动配置，适用于大多数应用程序。

自动配置在 Spring 的默认值之上添加了以下功能：

- 包含内容协商解析起 `ContentNegotiatingViewResolver` 和BeanName视图解析器 `BeanNameViewResolver` 组件
- 支持提供静态资源，包括对 WebJars 的支持
- 自动注册 `Converter`，`GenericConverter` 和 `Formatter` 组件
- 支持 `HttpMessageConverters`（后来我们配合内容协商理解原理）
- 自动注册 `MessageCodesResolver`（国际化）
- 静态 `index.html` 页支持
- 自定义 Favicon  
- 自动使用 `ConfigurableWebBindingInitializer` ，（DataBinder负责将请求数据绑定到JavaBean上）

如果您想保留那些 Spring Boot MVC 自定义并进行更多 MVC 自定义（拦截器、格式化程序、视图控制器和其他功能），您可以添加自己的 WebMvcConfigurer 类型的 `@Configuration` 类，但不添加 `@EnableWebMvc`。

如果您想提供 `RequestMappingHandlerMapping``、RequestMappingHandlerAdapter` 或 `ExceptionHandlerExceptionResolver` 的自定义实例，并且仍然保留 Spring Boot MVC 自定义，您可以声明一个类型为 `WebMvcRegistrations` 的组件并使用它来提供这些组件的自定义实例。

如果您想**完全控制** Spring MVC，您可以添加您自己的带有 `@EnableWebMvc` 注释的 `@Configuration`，或者添加您自己的 @Configuration-annotated `DelegatingWebMvcConfiguration`，如@EnableWebMvc 的 Javadoc 中所述。

# 2 简单功能分析

## 2.1 静态资源访问

参考文档：[Static Content](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications.spring-mvc.static-content)

1. 静态资源目录

默认情况下，Spring Boot 提供来自**类路径**或 **ServletContext 根目录**中名为 `/static`（或 `/public` 或 `/resources` 或 `/META-INF/resources`）的目录中的静态内容。

可以通过 `spring.web.resources.static-locations` 属性对静态资源目录进行调整。例如将静态资源目录设置为类路径下的 `/my-static/` 目录。

```yaml
spring:
  web:
    resources:
      static-locations: [classpath: /my-static/]
```

注：`static-locations` 中总是包含 `/META-INF/resources`（迷惑）

2. 静态资源访问前缀

默认情况下，静态资源会在 `/**` 路径上添加一个最低优先级的处理器映射

可以使用 `spring.mvc.static-path-pattern` 属性对其进行调整。例如，将所有资源重定位到 `/resources/**` 可以实现如下：

```yaml
spring:
  mvc:
    static-path-pattern: /resources/**
```

3. WebJars

WebJars 可以通过 `webjars/**` 路径对 WebJars 中的静态资源进行访问。

添加一个 jquery WebJars 依赖

```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.6.0</version>
</dependency>
```

WebJars 的目录结构如下：

```
jquery-3.6.0.jar
└── META-INF
    ├── MANIFEST.MF
    ├── maven
    │   └── org.webjars
    │       └── jquery
    │           ├── pom.properties
    │           └── pom.xml
    └── resources
        └── webjars
            └── jquery
                └── 3.6.0
                    ├── jquery.js
                    ├── jquery.min.js
                    ├── jquery.min.map
                    ├── jquery.slim.js
                    ├── jquery.slim.min.js
                    ├── jquery.slim.min.map
                    └── webjars-requirejs.js
```

根据 WebJars 的目录结构可以通过 `localhost:8080/webjars/jquery/3.6.0/jquery.js` 访问 `jquery.js` 文件

注：WebJars 资源与 `spring.web.resources.static-locations` 和 `spring.mvc.static-path-pattern` 属性配置无关


## 2.2 欢迎页和 Favicon

参考：[Welcome Page](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-web-applications.spring-mvc.welcome-page)

Spring Boot 支持**静态**和**模板化**欢迎页面。它首先在配置的静态内容位置查找 `index.html` 文件。如果未找到，则查找索引模板。如果找到其中之一，它会自动用作应用程序的欢迎页面。

将 favicon.ico 文件放入配置的静态路径中即可设定网页的 `Favicon`

## 2.3 静态资源配置原理

Spring Boot 启动默认加载 `XxxAutoConfiguration` 类（自动配置类），Spring MVC 功能的自动配置类 `WebMvcAutoConfiguration`。

`WebMvcAutoConfiguration` 自动配置类定义如下，并且自动配置生效。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
		ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
    // ...
}
```

该配置向容器中注册了一些组件和配置定义。

`WebMvcAutoConfiguration` 类中包含了一个配置定义如下：

```java
@Configuration(proxyBeanMethods = false)
@Import(EnableWebMvcConfiguration.class)
@EnableConfigurationProperties({ WebMvcProperties.class,
        org.springframework.boot.autoconfigure.web.ResourceProperties.class, WebProperties.class })
@Order(0)
public static class WebMvcAutoConfigurationAdapter implements WebMvcConfigurer, ServletContextAware {
    // ...
}
```

其中 `@EnableConfigurationProperties` 注解将配置文件和类进行绑定：

- 配置文件中的 `spring.mvc` 属性和 `WebMvcProperties` 进行绑定
- 配置文件中的 `spring.web` 属性和 `WebProperties` 进行绑定
- 配置文件中的 `spring.resources` 属性和 `ResourceProperties` 进行绑定

其中 `WebMvcAutoConfiguration` 这个配置类只有一个有参构造器，有参构造器中所有的参数都会从容器中获取。

```java
// resourceProperties 从容器中获取和 spring.resources 配置绑定的对象
// webProperties 从容器中获取和 spring.web 配置绑定的对象
// mvcProperties 从容器中获取和 spring.mvc 配置绑定的对象
// beanFactory Spring 的 IoC 容器
// messageConvertersProvider 从容器中获取所有的 HTTP 消息转换器
// resourceHandlerRegistrationCustomizerProvider 从容器中获取资源处理器的自定义器
// ...
public WebMvcAutoConfigurationAdapter(
        org.springframework.boot.autoconfigure.web.ResourceProperties resourceProperties,
        WebProperties webProperties, WebMvcProperties mvcProperties, ListableBeanFactory beanFactory,
        ObjectProvider<HttpMessageConverters> messageConvertersProvider,
        ObjectProvider<ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider,
        ObjectProvider<DispatcherServletPath> dispatcherServletPath,
        ObjectProvider<ServletRegistrationBean<?>> servletRegistrations) {
    this.resourceProperties = resourceProperties.hasBeenCustomized() ? resourceProperties
            : webProperties.getResources();
    this.mvcProperties = mvcProperties;
    this.beanFactory = beanFactory;
    this.messageConvertersProvider = messageConvertersProvider;
    this.resourceHandlerRegistrationCustomizer = resourceHandlerRegistrationCustomizerProvider.getIfAvailable();
    this.dispatcherServletPath = dispatcherServletPath;
    this.servletRegistrations = servletRegistrations;
    this.mvcProperties.checkConfiguration();
}
```

配置静态资源映射

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    // 将 /webjars/** 路径和 lasspath:/META-INF/resources/webjars/ 中的资源进行映射
    addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
    // 将 spring.mvc.static-path-pattern 属性和 spring.web.resources.static-locations 中的资源进行映射
    addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
        registration.addResourceLocations(this.resourceProperties.getStaticLocations());
        if (this.servletContext != null) {
            ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
            registration.addResourceLocations(resource);
        }
    });
}
```

欢迎页处理器映射

```java
@Bean
public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext,
        FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
    WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
            new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
            this.mvcProperties.getStaticPathPattern());
    welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
    welcomePageHandlerMapping.setCorsConfigurations(getCorsConfigurations());
    return welcomePageHandlerMapping;
}
```

其中欢迎页的映射规则如下

```java
WelcomePageHandlerMapping(TemplateAvailabilityProviders templateAvailabilityProviders,
        ApplicationContext applicationContext, Resource welcomePage, String staticPathPattern) {
    // index 页面不为空并且静态资源映射路径为 /** 则 forward 到 index.html
    if (welcomePage != null && "/**".equals(staticPathPattern)) {
        logger.info("Adding welcome page: " + welcomePage);
        setRootViewName("forward:index.html");
    }
    // 否则调用 controller 处理
    else if (welcomeTemplateExists(templateAvailabilityProviders, applicationContext)) {
        logger.info("Adding welcome page template: index");
        setRootViewName("index");
    }
}
```

