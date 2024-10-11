
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

#### 使用说明

##### 前端架构
1. 前端地址 - 8701 
<p>
	<a href="https://github.com/xiaozhao32/oner365-vue">Github 前端仓库</a> &nbsp; | &nbsp; <a href="https://gitee.com/xiaozhao32/oner365-vue">Gitee 前端仓库</a>
</p>

##### 后端架构
##### 基础服务组件
1. 工具包 oner365-common
2. 打包工具 oner365-deploy
3. 网关前置 - 8704 oner365-gateway
4. 文件中心 - 8705 oner365-files
5. 监控中心 - 8706 oner365-monitor
6. 认证中心 - 8707 oner365-system

##### 扩展服务组件
1. Dubbo提供者 - 8708 oner365-dubbo-provider 
2. Dubbo消费者 - 8718 oner365-dubbo-consumer 
3. Elasticsearch - 8709 oner365-elasticsearch 
4. Kafka - 8710 oner365-kafka
5. 生成框架 - 8711 oner365-generator
6. Hadoop - 8712 oner365-hadoop
7. Rocketmq 	- 8713 oner365-rocketmq
8. Swagger - 8714 oner365-swagger 
9. Pulsar - 8715 oner365-pulsar
10. 分库分表 - 8716 oner365-datasource
11. 时序库测试 - 8717 oner365-influx
12. 图数据库测试 - 8719 oner365-neo4j
13. 数据库Cassandra - 8720 oner365-cassandra
14. 状态机Statemachine - 8721 oner365-statemachine
15. 密码加密Vault - 8722 oner365-vault
16. Mqtt - 8723 oner365-mqtt
17. Ldap - 8724 oner365-ldap
18. Zookeeper - 8725 oner365-zookeeper
19. Spark - 8726 oner365-spark
20. Mongodb - 8727 oner365-mongodb
21. Kubernetes - 8728 oner365-kubernetes
22. Postgis - 8729 oner365-postgis
23. 主从分表 - 8730 oner365-shardingsphere

### 启动服务
1. 配置nacos地址到 hosts 中 如: 127.0.0.1 oner365-nacos

### 链路监控
1. 启动 Skywalking web端口(8080) 服务端口(11800)
2. 修改服务start.sh 启动服务如下: 

	nohup java 

	-javaagent:apache-skywalking/agent/skywalking-agent.jar 
	
	-Dskywalking.agent.service_name=$RESOURCE_NAME
	
	-Dskywalking.collector.backend_service=localhost:11800 
	
	-jar ./$RESOURCE_NAME > ../logs/$RESOURCE_NAME.log 2>&1 &

---

