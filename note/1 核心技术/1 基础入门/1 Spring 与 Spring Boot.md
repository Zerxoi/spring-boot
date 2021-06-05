Spring 与 Spring Boot
===

# 1 Spring 

## 1.1 Spring 功能

![Spring 功能](imgs\Spring%20功能.png)

## 1.2 Spring 生态

![Spring%20项目](imgs\Spring%20项目.png)

Spring 项目覆盖了 **Web开发**、**数据访问**、**安全控制**、**分布式**、**消息服务**、**移动开发**、**批处理**等方面

## 1.3 Spring5 重大升级

参考：[What's New in Version 5.0](https://github.com/spring-projects/spring-framework/wiki/What%27s-New-in-Spring-Framework-5.x#whats-new-in-version-50)

### 1.3.1 响应式编程

![响应式编程](imgs\响应式编程.png)

### 1.3.2 内部源码设计

基于 Java 8 的一些新特性，如：接口默认实现，Lambda表达式等。重新设计源码架构。

# 2 Spring Boot

> Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

Spring Boot 能快速创建出能独立运行的生产级别 Spring 应用

## 2.1 Spring Boot 特点

- 创建独立的Spring应用程序
- 内嵌Tomcat，Jetty 或 Undertow 等 Web服务器（无需部署WAR文件）
- 提供“启动器”依赖项来简化构建配置
- 尽可能自动配置 Spring 和第三方库
- 提供生产级别的监控、健康检查及外部化配置
- 无代码生成，不需要XML配置

Spring Boot 是整合 Spring 技术栈的一站式框架，最大程度上简化了额外的配置。

## 2.2 Spring Boot 缺点

- 版本帝，迭代快，需要时刻关注变化
- 封装太深，内部原理复杂，不容易精通

# 3 时代背景

## 3.1 微服务

[James Lewis and Martin Fowler (2014)](https://martinfowler.com/articles/microservices.html) 提出微服务完整概念。

> In short, the **microservice architectural style** is an approach to developing a single application as **a suite of small services**, each **running in its own process** and communicating with **lightweight** mechanisms, often an **HTTP** resource API. These services are **built around business capabilities** and **independently deployable** by fully **automated deployment** machinery. There is a **bare minimum of centralized management** of these services, which may be **written in different programming languages** and use different data storage technologies.
>
> -- James Lewis and Martin Fowler (2014)

- 微服务是一种架构风格
- 一个应用拆分为一组小型服务
- 每个服务运行在自己的进程内，也就是可独立部署和升级
- 服务之间使用轻量级HTTP交互
- 服务围绕业务功能拆分
- 可以由全自动部署机制独立部署
- 去中心化，服务自治
- 服务可以使用不同的语言、不同的存储技术

## 3.2 分布式

### 3.2.1 分布式困难

- 远程调用
- 服务发现
- 负载均衡
- 服务容错
- 配置管理
- 服务监控
- 链路追踪
- 日志管理
- 任务调度

### 3.2.2 分布式解决

Spring Boot + Spring Cloud

![Spring Cloud 架构](imgs\Spring%20Cloud%20架构.png)

## 3.3 云原生

### 3.3.1 上云的困难

- 服务自愈
- 弹性伸缩
- 服务隔离
- 自动化部署
- 灰度发布
- 流量治理

### 3.3.2 上云的解决

1. Cloud Native - 云原生
2. Docker - 容器化技术
3. Kubernetes - 容器编排
4. DevOps
5. Service Mesh 和 Serverless - 新一代架构

# 4 如何学习 Spring Boot

![官网文档架构1](imgs\官网文档架构1.png)

![官方文档架构2](imgs\官方文档架构2.png)

[版本特性](https://github.com/spring-projects/spring-boot/wiki#release-notes)