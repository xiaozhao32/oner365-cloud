# oner365-cloud

#### 介绍
个人练习 Springcloud Nacos

#### 软件架构
软件架构说明


#### 安装教程

1. 数据库：MySQL
2. 缓存：Redis
3. 队列：RabbitMQ && RocketMQ && Kafka
4. 文件处理：Fastdfs && Hadoop
5. 搜索引擎：Elasticserach
6. 配置管理：Alibaba Nacos
7. 分布式事务：Alibaba Seata
8. 限流策略：Alibaba Sentinel
9. 通信框架：Apache Dubbo
10. 分库分表：Apache Shardingsphere 
11. 链路监控：Apache Skywalking
12. API框架：Swagger

#### 使用说明

##### 前端架构
1. 前端地址 - 8701 oner365-vue

##### 后端架构
1. 工具包 oner365-common
2. 打包工具 oner365-deploy
3. 网关前置 - 8704 oner365-gateway
4. 文件中心 - 8705 oner365-files
5. 监控中心 - 8706 oner365-monitor
6. 认证中心 - 8707 oner365-system

### 服务组件
1. Dubbo提供者 - 8708 oner365-dubbo-provider 
2. Dubbo消费者 - 8718 oner365-dubbo-consumer 
3. Elasticsearch - 8709 oner365-elasticsearch 
4. Kafka - 8710 oner365-kafka
5. 生成框架 - 8711 oner365-generator
6. Hadoop - 8712 oner365-hadoop
7. Rocketmq 	- 8713 oner365-rocketmq
8. Swagger - 8714 oner365-swagger 

### 链路监控
1. 启动 Skywalking web端口(8080) 服务端口(11800)
2. 修改服务start.sh 启动服务如下: 

	nohup java 

	-javaagent:apache-skywalking/agent/skywalking-agent.jar 
	
	-Dskywalking.agent.service_name=$RESOURCE_NAME
	
	-Dskywalking.collector.backend_service=localhost:11800 
	
	-jar ./$RESOURCE_NAME > ../logs/$RESOURCE_NAME.log 2>&1 &

---

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
