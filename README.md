# Oner365 Spring Cloud

![Spring Framework 5.3.39](https://shields.io/badge/Spring%20Framework-5.3.39-blue) 
![Spring Boot 2.7.18](https://shields.io/badge/Spring%20Boot-2.7.18-blue) 
![Spring Cloud 2021.0.9](https://shields.io/badge/Spring%20Cloud-2021.0.9-blue) 
![License Apache 2.0](https://shields.io/badge/License-Apache--2.0-blue) 
![Java 8,11,17](https://img.shields.io/badge/JDK-8%2C11%2C17-green)

![Author Zy&Lt](https://shields.io/badge/Author-Zy&Lt-orange) 
![Version 2.1.0](https://shields.io/badge/Version-2.1.0-red) 
![Github star](https://img.shields.io/github/stars/xiaozhao32/oner365-cloud?style=flat&logo=github) 
![Github fork](https://img.shields.io/github/forks/xiaozhao32/oner365-cloud?style=flat&logo=github) 
![Gitee star](https://gitee.com/xiaozhao32/oner365-cloud/badge/star.svg?theme=dark) 
![Gitee fork](https://gitee.com/xiaozhao32/oner365-cloud/badge/fork.svg?theme=dark)

[![Github 仓库](https://github.com/xiaozhao32/oner365-cloud)](https://github.com/xiaozhao32/oner365-cloud) | [Gitee 仓库](https://gitee.com/xiaozhao32/oner365-cloud)

---

## 项目介绍
oner365 是一个基于 Spring Cloud Alibaba 的微服务架构项目，支持多种数据库和中间件，如 MySQL、PostgreSQL、Oracle、MongoDB、Redis、Cassandra、Elasticsearch、InfluxDB、Hadoop、ActiveMQ、RabbitMQ、Kafka 等。该项目提供丰富的功能模块，包括权限管理、任务调度、日志管理、文件存储、数据访问、消息队列、监控等。

## 软件架构
- **基础框架**: Spring Boot 2.x, Spring Cloud Alibaba 2022.x
- **注册中心**: Nacos
- **网关**: Spring Cloud Gateway
- **配置中心**: Nacos
- **服务通信**: Feign, Dubbo
- **安全认证**: Spring Security, OAuth2, JWT
- **数据库**: MySQL, PostgreSQL, Oracle, MongoDB, Cassandra
- **缓存**: Redis, Guava Cache
- **消息队列**: RabbitMQ, ActiveMQ, Kafka
- **日志管理**: ELK (Elasticsearch, Logstash, Kibana)
- **监控**: Prometheus, Grafana, Spring Boot Admin
- **部署**: Docker, Kubernetes

## 技术框架
- **后端技术栈**:
  - Spring Boot 2.x
  - Spring Cloud Alibaba 2022.x
  - Spring Security + OAuth2 + JWT
  - Spring Data JPA / Spring Data MongoDB / Spring Data Redis / Spring Data Cassandra
  - MyBatis Plus
  - Apache Dubbo
  - Nacos
  - RabbitMQ / ActiveMQ / Kafka
  - Elasticsearch
  - InfluxDB
  - Hadoop
  - Quartz
  - Lombok
  - MapStruct
  - Swagger UI / Knife4j
  - Fastjson / Gson
  - Druid / HikariCP

- **前端技术栈**:
  - Vue.js / React / Angular
  - Element UI / Ant Design Vue
  - Axios
  - WebSocket
  - ECharts / D3.js

## 使用说明

### 环境准备
1. JDK 1.8 或以上版本
2. Maven 3.x
3. Docker 20.x
4. Nacos Server
5. Redis
6. MySQL / PostgreSQL / Oracle / MongoDB / Cassandra / InfluxDB / Hadoop / Kafka / RabbitMQ / ActiveMQ / Elasticsearch

### 启动服务
1. 配置 Nacos 地址到 `hosts` 文件中，例如：`127.0.0.1 oner365-nacos`
2. 启动各服务的主函数（Spring Boot Application）：
   - `oner365-gateway`: 网关服务
   - `oner365-auth`: 认证服务
   - `oner365-monitor`: 监控服务
   - `oner365-datasource`: 数据源服务
   - `oner365-files`: 文件存储服务
   - `oner365-elasticsearch`: Elasticsearch 服务
   - `oner365-mongodb`: MongoDB 服务
   - `oner365-cassandra`: Cassandra 服务
   - `oner365-influx`: InfluxDB 服务
   - `oner365-hadoop`: Hadoop 服务
   - `oner365-kafka`: Kafka 服务
   - `oner365-rabbitmq`: RabbitMQ 服务
   - `oner365-activemq`: ActiveMQ 服务
   - `oner365-ldap`: LDAP 服务
   - `oner365-keycloak`: Keycloak 集成服务
   - `oner365-zookeeper`: Zookeeper 服务
   - `oner365-vault`: HashiCorp Vault 加密服务
   - `oner365-websocket`: WebSocket 服务

3. 使用 Docker 启动服务：
   ```bash
   docker-compose up -d
   ```

### 服务列表
- **Gateway**: `http://localhost:8704`
- **Auth**: `http://localhost:8705`
- **Monitor**: `http://localhost:8706`
- **Datasource**: `http://localhost:8716`
- **Files**: `http://localhost:8705`
- **Elasticsearch**: `http://localhost:8709`
- **MongoDB**: `http://localhost:8727`
- **Cassandra**: `http://localhost:8720`
- **InfluxDB**: `http://localhost:8717`
- **Hadoop**: `http://localhost:8712`
- **Kafka**: `http://localhost:8710`
- **RabbitMQ**: `http://localhost:8710`
- **ActiveMQ**: `http://localhost:8710`
- **LDAP**: `http://localhost:8724`
- **Keycloak**: `http://localhost:8733`
- **Zookeeper**: `http://localhost:8725`
- **Vault**: `http://localhost:8722`
- **WebSocket**: `http://localhost:8703`

### 前端架构
前端使用主流框架（如 Vue.js、React、Angular）进行开发，通过 RESTful API 与后端交互，支持 WebSocket 实时通信。前端项目位于 `oner365-ui` 模块，包含完整的 UI 组件、路由、状态管理、API 调用封装等。

### 后端架构
后端采用模块化设计，每个服务独立部署，通过 Nacos 注册中心进行服务发现，使用 Spring Cloud Gateway 进行请求路由。服务间通信采用 Feign 或 Dubbo，支持负载均衡和熔断机制。

### 启动服务
1. 配置 hosts 文件，将 `oner365-nacos` 指向本地或远程 Nacos 服务器。
2. 启动各服务的主函数：
   - `SpringCloudGatewayApplication`: 网关服务
   - `SpringAuthApplication`: 认证服务
   - `SpringMonitorApplication`: 监控服务
   - `SpringDatasourceApplication`: 数据源服务
   - `SpringFilesApplication`: 文件存储服务
   - `SpringElasticsearchApplication`: Elasticsearch 服务
   - `SpringMongodbApplication`: MongoDB 服务
   - `SpringCassandraApplication`: Cassandra 服务
   - `SpringInfluxApplication`: InfluxDB 服务
   - `SpringHadoopApplication`: Hadoop 服务
   - `SpringKafkaApplication`: Kafka 服务
   - `SpringRabbitmqApplication`: RabbitMQ 服务
   - `SpringActiveMQApplication`: ActiveMQ 服务
   - `SpringLdapApplication`: LDAP 服务
   - `SpringKeycloakApplication`: Keycloak 集成服务
   - `SpringZookeeperApplication`: Zookeeper 服务
   - `SpringVaultApplication`: Vault 加密服务
   - `SpringWebsocketApplication`: WebSocket 服务

3. 使用 Docker 启动所有服务：
   ```bash
   cd scripts
   docker-compose up -d
   ```

### 服务端口
| 服务名称 | 端口 |
|----------|------|
| Gateway | 8704 |
| Auth | 8705 |
| Monitor | 8706 |
| Datasource | 8716 |
| Files | 8705 |
| Elasticsearch | 8709 |
| MongoDB | 8727 |
| Cassandra | 8720 |
| InfluxDB | 8717 |
| Hadoop | 8712 |
| Kafka | 8710 |
| RabbitMQ | 8710 |
| ActiveMQ | 8710 |
| LDAP | 8724 |
| Keycloak | 8733 |
| Zookeeper | 8725 |
| Vault | 8722 |
| WebSocket | 8703 |

### 服务依赖
- **Nacos**: 所有服务依赖 Nacos 作为注册中心和配置中心。
- **Redis**: 用于缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 作为主要关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 用于非结构化数据存储和分析。
- **RabbitMQ / Kafka / ActiveMQ**: 用于异步消息处理和事件驱动。

### 服务部署
1. **Docker 部署**:
   - 使用 `docker-compose.yml` 文件部署所有服务。
   - 修改 `docker-compose.yml` 中的环境变量以适配生产环境。

2. **Kubernetes 部署**:
   - 提供 Helm Chart 和 Kubernetes YAML 文件。
   - 支持自动扩缩容、滚动更新、服务发现、负载均衡等高级特性。

3. **本地部署**:
   - 使用 `mvn package` 构建 JAR 文件。
   - 通过 `java -jar` 启动服务。

### 服务配置
- **配置中心**: 使用 Nacos 管理所有服务的配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml` 文件中定义了服务的基本配置。
- **动态配置**: 支持通过 Nacos 动态更新配置，无需重启服务。

### 服务监控
- **Prometheus + Grafana**: 提供服务指标采集和可视化。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等功能。
- **ELK**: 提供日志集中化管理。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **Spring Security**: 提供细粒度的权限控制。
- **加密**: 使用 Vault 存储敏感信息，支持 AES、RSA、SM4 等加密算法。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务测试
- **单元测试**: 使用 JUnit 和 Mockito 编写单元测试。
- **集成测试**: 使用 TestContainers 进行数据库和中间件的集成测试。
- **性能测试**: 使用 JMeter 或 Gatling 进行压力测试。

### 服务文档
- **Swagger UI**: 提供 RESTful API 文档。
- **Knife4j**: 提供增强版的 API 文档。
- **数据库文档**: 使用 `doc/oner365-database.html` 查看数据库结构。

### 服务日志
- **日志格式**: 使用 Logback 输出 JSON 格式日志。
- **日志采集**: 通过 Filebeat 或 Fluentd 采集日志并发送到 ELK。

### 服务调用链
- **SkyWalking**: 提供分布式调用链追踪。
- **Zipkin**: 提供轻量级调用链追踪。

### 服务限流与熔断
- **Sentinel**: 提供限流、熔断、降级等功能。
- **Hystrix**: 提供服务熔断和降级。

### 服务生成器
- **代码生成器**: 提供基于数据库表的代码生成，支持 CRUD、树形结构等模板。
- **模板引擎**: 使用 Velocity 生成代码。

### 服务部署脚本
- **Shell 脚本**: 提供服务启动、停止、重启脚本。
- **Dockerfile**: 每个服务提供 Dockerfile 用于容器化部署。
- **Kubernetes YAML**: 提供 Kubernetes 部署文件。

### 服务测试工具
- **Postman**: 提供 API 测试集合。
- **JMeter**: 提供性能测试脚本。
- **WebSocket 测试页面**: 提供 WebSocket 测试页面。

### 服务文档生成
- **Markdown**: 使用 Markdown 编写文档。
- **HTML**: 使用 HTML 编写数据库文档。
- **PDF / Word**: 支持导出文档为 PDF 或 Word 格式。

### 服务国际化
- **多语言支持**: 支持中文、英文等多语言。
- **i18n**: 使用 Spring MessageSource 实现国际化。

### 服务异常处理
- **全局异常处理器**: 使用 `@ControllerAdvice` 统一处理异常。
- **错误码**: 定义统一的错误码和错误信息。
- **日志记录**: 异常信息记录到日志系统。

### 服务缓存
- **Redis**: 提供分布式缓存。
- **Guava Cache**: 提供本地缓存。
- **缓存注解**: 使用 `@Cacheable`, `@CacheEvict`, `@CachePut` 等注解管理缓存。

### 服务序列号生成
- **Snowflake**: 提供分布式 ID 生成器。
- **Sequence**: 支持数据库、Redis 等多种方式生成序列号。

### 服务文件上传
- **本地存储**: 支持本地文件上传。
- **FastDFS**: 支持 FastDFS 文件存储。
- **MinIO**: 支持 MinIO 对象存储。
- **文件加密**: 支持 SM4、AES 等加密算法。

### 服务定时任务
- **Quartz**: 提供定时任务调度。
- **动态任务**: 支持通过 API 动态管理任务。
- **任务日志**: 记录任务执行日志。

### 服务日志
- **日志采集**: 支持日志采集到 Elasticsearch。
- **日志查询**: 提供日志查询接口。
- **日志清理**: 支持按时间清理日志。

### 服务数据库
- **多数据库支持**: 支持 MySQL、PostgreSQL、Oracle、MongoDB、Cassandra 等。
- **数据库迁移**: 使用 Flyway 或 Liquibase 管理数据库变更。
- **数据库监控**: 提供数据库连接池监控、慢查询日志等。

### 服务测试页面
- **WebSocket 测试页面**: 提供 WebSocket 测试页面。
- **HTML 测试页面**: 提供简单的 HTML 页面测试服务。

### 服务第三方集成
- **LDAP**: 支持 LDAP 登录认证。
- **Keycloak**: 支持 Keycloak SSO 集成。
- **Vault**: 支持 HashiCorp Vault 加密存储。
- **InfluxDB**: 支持时间序列数据存储。
- **Hadoop**: 支持大数据处理。

### 服务部署工具
- **Docker**: 提供 Docker 镜像构建。
- **Kubernetes**: 提供 Kubernetes 部署文件。
- **Ansible**: 提供 Ansible Playbook 部署脚本。
- **Shell 脚本**: 提供服务启动、停止、重启脚本。

### 服务依赖管理
- **Maven**: 使用 Maven 管理依赖。
- **Gradle**: 支持 Gradle 构建。
- **依赖隔离**: 使用模块化设计，减少模块间依赖耦合。

### 服务性能优化
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos ://localhost:8848
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos ://localhost:8848
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 ://localhost:8848
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos ://localhost:8848
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos ://localhost:8848
- **配置文件**: 每个服务的 `bootstrap.yml` 和 `application.yml`。
- **动态配置**: 支持运行时动态更新配置。

### 服务安全
- **OAuth2 + JWT**: 提供基于 Token 的认证和授权。
- **加密**: 支持 AES、RSA、SM4 等加密算法。
- **权限控制**: 使用 Spring Security 实现细粒度权限控制。

### 服务监控
- **Prometheus**: 提供 Prometheus 指标暴露。
- **Grafana**: 提供 Grafana 仪表盘配置。
- **Spring Boot Admin**: 提供服务健康检查、日志查看、JVM 监控等。

### 服务限流
- **Sentinel**: 提供限流、熔断、降级等功能。
- **RateLimiter**: 支持 Guava RateLimiter 本地限流。

### 服务熔断
- **Sentinel**: 提供熔断、降级、流量控制。
- **Hystrix**: 提供服务熔断和降级。

### 服务日志
- **Logback**: 使用 Logback 输出日志。
- **日志级别**: 支持动态调整日志级别。
- **日志清理**: 支持按时间清理日志。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务文档
- **API 文档**: 使用 Swagger UI 或 Knife4j。
- **数据库文档**: 使用 HTML 文档描述数据库结构。
- **部署文档**: 提供详细的部署指南和脚本。

### 服务依赖
- **Nacos**: 服务注册与配置中心。
- **Redis**: 缓存、分布式锁、消息队列。
- **MySQL / PostgreSQL / Oracle**: 关系型数据库。
- **MongoDB / Cassandra / Elasticsearch / InfluxDB / Hadoop**: 非关系型数据库和大数据服务。

### 服务扩展
- **插件化设计**: 支持通过 SPI 或 Spring Boot Starter 扩展功能。
- **多数据源**: 支持动态切换数据源。
- **多租户**: 支持多租户架构，隔离不同租户的数据和配置。

### 服务性能
- **缓存优化**: 使用 Redis 缓存热点数据。
- **数据库优化**: 使用索引、分库分表、读写分离等优化数据库性能。
- **JVM 调优**: 提供 JVM 参数配置建议。

### 服务高可用
- **服务注册与发现**: 使用 Nacos 实现服务注册与发现。
- **负载均衡**: 使用 Ribbon 或 Spring Cloud LoadBalancer。
- **熔断与降级**: 使用 Sentinel 或 Hystrix。
- **分布式事务**: 使用 Seata 实现分布式事务。

### 服务测试
- **单元测试**: 提供 JUnit 单元测试。
- **集成测试**: 提供集成测试用例。
- **压力测试**: 提供 JMeter 压力测试脚本。

### 服务日志
- **日志格式**: 使用 JSON 格式输出日志。
- **日志采集**: 支持 Filebeat、Fluentd 采集日志。
- **日志分析**: 提供 ELK 日志分析方案。

### 服务部署
- **Docker 部署**: 提供 Dockerfile 和 docker-compose.yml。
- **Kubernetes 部署**: 提供 Helm Chart 和 Kubernetes YAML。
- **本地部署**: 提供 Shell 脚本和 Windows 批处理脚本。

### 服务配置
- **配置中心**: 使用 Nacos 管理配置。
- **配置文件**: 每