Spring Boot 2 入门
===

# 1 系统需求

Spring Boot 版本：2.4.5

|软件|版本|
|--------|-------|
|Java|8 - 16|
|Maven|3.3+|
|Gradle|6（6.3或更高版本） 5.6.x（已弃用）|

# 2 Hello World

## 2.1 创建 POM

创建一个Maven `pom.xml`文件用于构建项目。

Spring Boot提供了许多“启动器”，使您可以将 jar 添加到类路径中。我们的应用程序在POM的父部分中使用 `spring-boot-starter-parent`。`spring-boot-starter-parent` 是一个特殊的启动器，提供有用的 Maven 默认值。它还提供了一个依赖项管理部分，以便您可以为依赖项省略版本标签。`mvn dependency:tree` 命令显示项目依赖关系的树形表示。您可以看到 `spring-boot-starter-parent` 本身不提供任何依赖关系。

由于我们正在开发Web应用程序，因此添加了 `spring-boot-starter-web` 依赖项。如果再次运行 `mvn dependency:tree`，则会看到现在还有许多其他依赖项，包括Tomcat Web服务器和Spring Boot本身。

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>xyz.zerxoi</groupId>
  <artifactId>hello-world</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <java.version>11</java.version>
  </properties>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.5</version>
  </parent>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
  </dependencies>

</project>
```

## 2.2 程序编写

### 2.2.1 主程序

```java
package xyz.zerxoi;

import org.springframework.boot.autoconfigure.SpringBootApplication;

// 等同于 @EnableAutoConfiguration @ComponentScan @Configuration
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
    }
}
```

### 2.2.2 业务程序

```java
package xyz.zerxoi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, 世界!";
    }
}
```

## 2.3  代码讲解

- `@RestController` 是一个 `@Controller` 和 `@ResponseBody` 复合注释：带有此批注的类型被视为控制器，其中 `@RequestMapping` 方法默认情况下采用 `@ResponseBody` 语义。
- `@RequestMapping` 批注提供“路由”信息：用于将Web请求映射到具有灵活方法签名的请求处理类中的方法的注释。
- `@SpringBootApplication` 是一个 `@EnableAutoConfiguration`、`@ComponentScan` 和 `@SpringBootConfiguration` 的复合注解
    - `@SpringBootConfiguration`：表示一个类提供Spring Boot应用程序 `@Configuration`。
        - `@Configuration` 指示一个类声明了一个或多个 `@Bean` 方法，并且可以由 Spring 容器进行处理以在运行时为这些 Bean 生成Bean定义和服务请求
    - `@EnableAutoConfiguration`：启用S​​pring Application Context的自动配置，尝试猜测和配置您可能需要的Bean。通常根据您的类路径和定义的Bean来应用自动配置类。
    - `@ComponentScan`：配置组件扫描指令以与@Configuration类一起使用。
        - 可以指定 `basePackageClasses()` 或 `basePackages()`（或其别名`value()`）来定义要扫描的特定程序包。如果未定义特定的程序包，则将从声明此批注的类的程序包中进行扫描。
        - `@SpringBootApplication`中的 `@ComponentScan` 注解通常放置在您的主类上，该注释会扫描所有主类所在包及其子包的组件。
- `main`：`main` 方法通过调用 `run` 委托给Spring Boot的 `SpringApplication` 类。`SpringApplication` 引导我们的应用程序，启动 Spring，然后启动自动配置的 Tomcat web 服务器。我们需要将 `App.class` 作为参数传递给 `run` 方法，以告诉 `SpringApplication` 哪个是 Spring 的主要组件。`args` 数组也被传递以公开任何命令行参数。

## 2.4 简化部署

通过创建一个可以在生产环境中运行的完全独立的**可执行jar文件**来简化部署。可执行jar（有时称为“fat jar”）是包含您的已编译类以及代码需要运行的所有jar依赖项的归档文件。

要创建可执行jar，我们需要将 `spring-boot-maven-plugin` 添加到我们的 `pom.xml` 中。为此，请在 `dependencies` 部分的下面插入以下行：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## 2.5 代码运行

- 运行主方法来运行应用程序
- 从根项目目录执行 `mvn spring-boot:run` 以启动应用程序
- 通过 `java -jar <fat-jar>` 启动应用程序