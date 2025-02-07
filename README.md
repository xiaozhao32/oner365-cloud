
<h3 align="center">Oner365 Cloud</h3>

---

<p align="center">
	<a href="https://spring.io/projects/spring-framework" target="_blank"><img src="https://shields.io/badge/Spring%20Framework-5.3.39-blue" alt="Spring Framework 5.3.39"></a>
    <a href="https://spring.io/projects/spring-boot" target="_blank"><img src="https://shields.io/badge/Spring%20Boot-2.7.18-blue" alt="Spring Boot 2.7.18"></a>
    <a href="https://spring.io/projects/spring-cloud" target="_blank"><img src="https://shields.io/badge/Spring%20Cloud-2021.0.9-blue" alt="Spring Cloud 2021.0.9"></a>
    <a href="https://github.com/alibaba/spring-cloud-alibaba" target="_blank"><img src="https://shields.io/badge/Spring%20Cloud%20Alibaba-2021.1-blue" alt="Spring Cloud Alibaba 2021.1"></a>
    <a href="https://nacos.io/zh-cn/index.html" target="_blank"><img src="https://shields.io/badge/Nacos-2.3.2-brightgreen" alt="Nacos 2.3.2"></a>
	<a href="./LICENSE"><img src="https://shields.io/badge/License-Apache--2.0-green" alt="License Apache 2.0"></a>
    <a href="https://www.oracle.com/java/technologies/javase-downloads.html" target="_blank"><img src="https://img.shields.io/badge/JDK-8%2C11%2C17-green" alt="Java 8,11,17"></a>
</p>
<p align="center">
    <a href="#"><img src="https://shields.io/badge/Author-Zy&Lt-orange" alt="Zy&Lt"></a>
    <a href="#"><img src="https://shields.io/badge/Version-2.1.0-red" alt="Version 2.1.0"></a>
    <a href="https://github.com/xiaozhao32/oner365-cloud"><img src="https://img.shields.io/github/stars/xiaozhao32/oner365-cloud?style=flat&logo=github" alt="Github star"></a>
    <a href="https://github.com/xiaozhao32/oner365-cloud"><img src="https://img.shields.io/github/forks/xiaozhao32/oner365-cloud?style=flat&logo=github" alt="Github fork"></a>
    <a href="https://gitee.com/xiaozhao32/oner365-cloud"><img src="https://gitee.com/xiaozhao32/oner365-cloud/badge/star.svg?theme=dark" alt="Gitee star"></a>
    <a href="https://gitee.com/xiaozhao32/oner365-cloud"><img src="https://gitee.com/xiaozhao32/oner365-cloud/badge/fork.svg?theme=dark" alt="Gitee fork"></a>
</p>
<p align="center">
    <a href="https://github.com/xiaozhao32/oner365-cloud">Github 仓库</a> &nbsp; | &nbsp;
    <a href="https://gitee.com/xiaozhao32/oner365-cloud">Gitee 仓库</a>
</p>

---


#### 介绍
1. 个人练习 Springcloud Nacos

#### 软件架构
软件架构说明


#### 技术框架

1. 数据库: MySQL && Postgres
2. 缓存: Redis
3. 队列: RabbitMQ && RocketMQ && Kafka &&  && Mqtt
4. 文件处理: Fastdfs && Hadoop && Minio && Local
5. 搜索引擎: Elasticserach
6. 配置管理: Alibaba Nacos
7. 分布式事务: Alibaba Seata
8. 限流策略: Alibaba Sentinel
9. 通信框架: Apache Dubbo
10. 分库分表: Apache Shardingsphere 
11. 链路监控: Apache Skywalking
12. API框架: 
13. 用户认证: Ldap
14. 时序数据库: Influx
15. 图数据库: Neo4j
16. 密码加密: Spring Vault
17. 状态机Statemachine: Spring Statemachine
18. Hadoop Zookeeper: Zookeeper 
19. Hadoop Spark: Spark
20. 非关系型数据库: Mongodb
21. Kubernetes对接: Kubernetes
22. Postgis服务: Postgis
23. Clickhouse: Clickhouse

#### 使用说明

##### 前端架构 oner365-ui
<p>
	<a href="https://github.com/xiaozhao32/oner365-vue">Github 前端仓库</a> &nbsp; | &nbsp; <a href="https://gitee.com/xiaozhao32/oner365-vue">Gitee 前端仓库</a>
</p>

##### 后端架构 基础服务组件
```lua
1. 工具包 oner365-common
2. 打包工具 oner365-deploy
3. 网关前置 - 8704 oner365-gateway
4. 文件中心 - 8705 oner365-files
5. 监控中心 - 8706 oner365-monitor
6. 认证中心 - 8707 oner365-system
```

##### 后端服务 oner365-cloud
```lua
└── doc -- 参考文档
     └── 部分模块压测 部署文档 流程图等。

├── oner365-api -- Web 引用的 jar 包
├── oner365-cassandra -- spring-data-Cassandra。 [8720]
└── oner365-clickhouse -- 数据库 Clickhouse。 [8731]

└── oner365-data -- 工具包
     ├── oner365-data-commons -- 公共组件包括: 输出封装 工具类等。
     ├── oner365-data-datasource -- 多数据源工具类。
     ├── oner365-data-jpa -- Jpa 封装查询工具类。
     ├── oner365-data-redis -- 缓存封装工具类。
     └── oner365-data-web -- Web端公共组件包括: 过滤器 拦截器 Token 配置等。

├── oner365-datasource -- 分库分表多数据源。(4.0.0-RC1) [8716]
└── oner365-deploy -- 自动化打包部署工具包括: 本地 Server端 Docker等。

└── oner365-dubbo -- Apache Dubbo
     ├── oner365-dubbo-api -- Dubbo 公共组件。
     ├── oner365-dubbo-consumer -- Dubbo消费者。 [8718]
     └── oner365-dubbo-provider -- Dubbo提供者。 [8708]

├── oner365-elasticsearch -- 后台索引组件 Elasticsearch. [8709]
├── oner365-files -- 后台文件存储组件。包括: 本地 Minio Fdfs等。[8705]
├── oner365-gateway -- 后台网关服务组件。[8704]
├── oner365-generator -- 后台脚手架组件。生成CRUD 借鉴RuoYi. [8711]
├── oner365-hadoop -- Apache Hadoop. [8712]
├── oner365-influx -- 时序数据库 Influxdb. [8717]
├── oner365-kafka -- Apache Kafka. [8710]
├── oner365-kubernetes -- K8s [8728]
├── oner365-ldap -- spring-data-Ldap. [8724]
├── oner365-mongodb -- 数据库 Mongodb. [8727]
├── oner365-monitor -- 后台监控服务组件 Redis缓存 Rabbitmq队列 Server服务 定时任务等。[8706]
├── oner365-mqtt -- 消息队列 MQTT [8723]
├── oner365-neo4j -- 图数据库 Neo4j. [8719]
├── oner365-postgis -- 地图数据库 Postgis. [8729]
├── oner365-pulsar -- 消息队列 Apache Pulsar. [8715]
├── oner365-rocketmq -- 消息队列 Apache Rocketmq. [8713]
├── oner365-shardingsphere -- Shardingsphere 分库分表多数据源。(5.2.1) [8730]
├── oner365-spark -- Apache Spark. [8726]
├── oner365-statemachine -- 状态机 spring-statemachine. [8721]
├── oner365-swagger -- Knife4j Swagger. [8714]
├── oner365-system -- 后台系统服务组件。包括认证 用户权限 字典等。[8707]
├── oner365-vault -- 密码加密 spring-vault. [8722]
├── oner365-websocket -- spring-websocket. [8703]
├── oner365-zookeeper -- Apache Zookeeper. [8725]
├── scripts -- 数据库脚本。包括: Nacos Mysql Postgres脚本等。
├── sh -- Shell 服务器脚本。包括: docker 服务端 引用第3方工具等。
└── README.md -- 说明
```
### 启动服务
1. 配置nacos地址到 hosts 中 如: 127.0.0.1 oner365-nacos
2. 启动各服务主函数。
---

