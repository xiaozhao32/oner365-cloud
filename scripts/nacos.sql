/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 09/05/2022 09:26:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_configdata_relation_pubs
-- ----------------------------
DROP TABLE IF EXISTS `app_configdata_relation_pubs`;
CREATE TABLE `app_configdata_relation_pubs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_name` varchar(128) NOT NULL COMMENT 'app_name',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_pub_config_datagroup` (`app_name`,`data_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用发布关联表';

-- ----------------------------
-- Table structure for app_configdata_relation_subs
-- ----------------------------
DROP TABLE IF EXISTS `app_configdata_relation_subs`;
CREATE TABLE `app_configdata_relation_subs` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_name` varchar(128) NOT NULL COMMENT 'app_name',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_sub_config_datagroup` (`app_name`,`data_id`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用订阅关联表';

-- ----------------------------
-- Table structure for app_list
-- ----------------------------
DROP TABLE IF EXISTS `app_list`;
CREATE TABLE `app_list` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_name` varchar(128) NOT NULL COMMENT 'app_name',
  `is_dynamic_collect_disabled` smallint DEFAULT '0' COMMENT 'is_dynamic',
  `last_sub_info_collected_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `sub_info_lock_owner` varchar(128) DEFAULT NULL COMMENT 'lock_owner',
  `sub_info_lock_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'lock_time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_appname` (`app_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用列表';

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'group id',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'app name',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'desc',
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'use',
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'effect',
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'type',
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'schema',
  `encrypted_data_key` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'encrypted_data_key',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (1, 'application-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # servlet file\n  servlet:\n    multipart:\n      enabled: true\n      max-file-size: 1024MB\n      max-request-size: 1024MB\n\n  main:\n    allow-bean-definition-overriding: true\n    allow-circular-references: true\n\n  # boot:\n  #   admin:\n  #     client:\n  #       url: http://localhost:8706\n        \n  data:\n    jpa:\n      repositories:\n        enabled: true\n        \n    redis:\n      repositories:\n        enabled: true\n    \n  # redis\n  redis:\n    enable: true\n    host: localhost\n    port: 6379\n    password:\n    database: 0\n    timeout: 10s\n    lettuce:\n      pool:\n        min-idle: 0\n        max-idle: 8\n        max-active: 8\n        max-wait: -1ms\n\n  # rabbitmq\n  rabbitmq:\n    addresses: localhost:5672\n    uri: http://localhost:15672\n    virtual-host: /\n    username: admin\n    password: admin123\n    connection-timeout: 6000\n\n  jpa:\n    open-in-view: true\n\n  # 线程池\n  task:\n    scheduling:\n      thread-name-prefix: SyncDataTask-\n    execution:\n      pool:\n        allow-core-thread-timeout: false\n        max-size: 60\n        core-size: 10\n        queue-capacity: 100\n        keep-alive: 90\n\n# 请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n\n# feign 配置\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n\n# Access Token\ntoken:\n  secret: test\n  expire-time: 60\n\n# 雪花算法生成id\nsequence:\n  snowflake:\n    datacenterId: 1\n    workerId: 1\n\n# 文件下载地址\nfile:\n  # 下载地址\n  download: /Users/zhaoyong/Downloads\n  excel:\n    suffix: xlsx\n\n# Logger\nlogging:\n  file:\n    path: /Users/zhaoyong/Downloads/logs\n  level:\n    web: warn\n    org:\n      springframework: error\n      elasticsearch: error\n  logstash:\n    enabled: false\n    addresses: localhost:5044\n    ', '85d15a9ac58124ea47a43f56310dc10d', '2021-07-04 06:36:29', '2022-12-02 13:55:06', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (2, 'oner365-system-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n\n  # sentinel监控\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: 127.0.0.1:8850\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-oner365-gateway.json\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nmybatis:\n  mapper-locations: classpath:mapper/*.xml\n  type-aliases-package: com.oner365.sys.entity\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', '2a0b439074710caab6b85ebbde241619', '2021-07-04 06:37:04', '2022-01-18 16:23:27', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (3, 'oner365-gateway-dev.yml', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations: \n          \'[/**]\': \n            # 允许携带认证信息\n            allow-credentials: true\n            # 允许跨域的源(网站域名/ip)，设置*为全部\n            allowedOriginPatterns: \"*\"\n            # 允许跨域的method， 默认为GET和OPTIONS，设置*为全部\n            allowedMethods: \"*\"\n            # 允许跨域请求里的head字段，设置*为全部\n            allowedHeaders: \"*\"\n      default-filters: \n        - DedupeResponseHeader=Access-Control-Allow-Origin, RETAIN_UNIQUE\n      discovery:\n        locator:\n          enabled: true\n          lower-case-service-id: true\n          \n  datasource:\n    #mysql\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.33.42:3306/uem-cloud?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n    username: root\n    password: 1234\n\n# 白名单\nignore:\n  whites:\n    - /api\n    - /system/auth\n    - /system/file/download\n    - /swagger\n    - /webjars\n    - /instances\n    - /v3\n    - /csrf\n    - /doc.html\n    - /ws\n\n\n\n      \n', 'a29c921417186a7e61b7c4f0986bb8a2', '2021-07-04 10:54:06', '2022-04-28 16:40:08', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (4, 'oner365-monitor-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\n  jpa: \n    hibernate:\n      ddl-auto: update\n    show-sql: false\n    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect\n    open-in-view: true\n    \n  redis:\n    database: 1\n  \n  data:\n    redis:\n      repositories:\n        enabled: false\n\n  # sentinel监控\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: 127.0.0.1:8850\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-oner365-gateway.json\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n\nmybatis:\n  mapper-locations: classpath:mapper/*.xml\n  type-aliases-package: com.oner365.monitor.entity\n\n#fdfs\nfdfs:\n  ip: 192.168.101.130\n  port: 22\n  user: root\n  password: root\n  storage:\n    path: /data/fastdfs-storage/data/M00/00\n  webServerUrl:          \n  \n#服务器配置  \nservers:\n  deploy: true\n  path: /usr/local/tomcat/oner365\n  version: 1.0.0-SNAPSHOT\n  suffix: jar\n  #部署多台服务器 必须包含ip 端口 账号 密码 逗号分隔\n  ip: 192.168.101.130\n  port: 22\n  username: root\n  password: root\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', '441e8b64ed1eb0dac6e7192abc84a3a5', '2021-07-04 12:15:05', '2021-07-14 10:47:39', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (5, 'oner365-files-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n      \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8 \n\nfile:\n  # 存储方式 local fdfs minio\n  storage: local\n  \n# local\nlocal:\n  web: http://localhost/uploads\n  upload: /Users/zhaoyong/Downloads\n\n# fdfs\nfdfs:\n  connect-timeout: 60\n  so-timeout: 1500\n  tracker-list: 192.168.213.131:22122\n  thumb-image:\n    height: 150\n    width: 150\n  web-server-url: http://192.168.213.131\n  ip: 192.168.213.131\n  port: 22\n  user: root\n  password: 1234\n  storage:\n    # 用于文件路径展示\n    path: /data/fastdfs-storage/data\n  pool:\n    #连接池最大数量\n    max-total: 200\n    #每个tracker地址kkkjjj的最大连接数\n    max-total-per-key: 50\n    #连接耗尽时等待获取连接的最大毫秒数\n    max-wait-millis: 5000\n\n# minio\nminio:\n  username: root\n  password: e8818da9cc9\n  url: http://localhost:9001\n  bucket: ${spring.application.name}\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', '341b0addc6d411644c177ef0727aa20e', '2021-07-05 05:12:43', '2022-02-27 14:37:20', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (6, 'oner365-elasticsearch-dev.yml', 'DEFAULT_GROUP', 'spring:\n  \n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n    \n  elasticsearch:\n    uris: http://localhost:9200\n  data:\n    elasticsearch:\n      repositories:\n        enabled: true\n\nlogging:\n  config: classpath:logback-spring.xml', 'c689d78f28c94ce0e4b6d2aa2d5e621f', '2021-07-05 06:48:49', '2022-02-27 14:34:59', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (7, 'oner365-swagger-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n      \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8 \n    \nswagger:\n  enabled: true\n\n  name: oner365-swagger\n  version: 1.0\n  url: https://www.oner365.com\n  email: service@oner365.com\n  description: Oner365 Description\n\n# knife4j \nknife4j:\n  enable: true\n\nlogging:\n  config: classpath:logback-spring.xml\n\n  ', 'a1693ec594861a119aacfaba2e6ef3d5', '2021-07-19 14:23:04', '2022-01-24 15:37:59', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (8, 'oner365-kafka-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\n  kafka:\n    bootstrap-servers: localhost:9092\n    listener:\n      missing-topics-fatal: false\n    template:\n      default-topic: test\n      \n    producer:\n      retries: 0\n      acks: 1\n      batch-size: 16384\n      buffer-memory: 33554432\n      key-serializer: org.apache.kafka.common.serialization.StringSerializer\n      value-serializer: org.apache.kafka.common.serialization.StringSerializer   \n      properties:\n        linger:\n          ms: 0     \n      \n    consumer:\n      group-id: defaultConsumerGroup\n      enable-auto-commit: true\n      auto-offset-reset: latest\n      auto-commit-interval: 1000\n      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer\n      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer\n      properties:\n        session:\n          timeout:\n            ms: 120000\n        request:\n          timeout:\n            ms: 120000\n            \nlogging:\n  config: classpath:logback-spring.xml', '4cbd3fb62a5cecab902e33e8c44690e9', '2021-07-05 08:15:52', '2022-01-25 13:15:20', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (9, 'oner365-hadoop-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # hive\n  datasource:\n    hive:\n      driver-class-name: org.apache.hive.jdbc.HiveDriver\n      url: jdbc:hive2://localhost:10000/default\n      username: zhaoyong\n      password: 1234\n  \n  jpa:\n    database-platform: org.hibernate.dialect.MySQLDialect\n\n# hdfs配置\nhdfs:\n  path: hdfs://localhost:9000\n  username: zhaoyong\n  \nlogging:\n  config: classpath:logback-spring.xml', '8eed9299a8427510dc64aa2e26c91dd7', '2021-07-07 13:32:30', '2021-07-08 10:03:43', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (10, 'oner365-rocketmq-dev.yml', 'DEFAULT_GROUP', 'spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n    \nrocketmq:\n  consumer:\n    topic: example\n  name-server: localhost:9876\n  producer:\n    group: test\n    send-message-timeout: 3000 \n\nlogging:\n  config: classpath:logback-spring.xml\n', '40b5cfaafb03ef3ad9c320357138f2ed', '2021-07-07 16:02:52', '2021-07-29 17:00:48', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (11, 'oner365-generator-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n    \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\n  # sentinel监控\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: 127.0.0.1:8850\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-oner365-gateway.json\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n\nmybatis:\n  mapper-locations: classpath:mapper/*.xml\n  type-aliases-package: com.oner365.generator.entity\n\n# 代码生成\ngen: \n  # 作者\n  author: zhaoyong\n  # 默认生成包路径 system 需改成自己的模块名称 如 system monitor tool\n  packageName: com.oner365.system\n  # 自动去除表前缀，默认是false\n  autoRemovePre: true\n  # 表前缀（生成类名不会包含表前缀，多个用逗号分隔）\n  tablePrefix: nt_\n\nlogging:\n  config: classpath:logback-spring.xml\n', 'd963c1e8465226ead477ab4e015f69e1', '2021-07-07 16:02:52', '2021-10-26 16:53:36', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (12, 'sentinel-oner365-gateway.json', 'DEFAULT_GROUP', '[\n    {\n        \"resource\": \"oner365-system\",\n        \"count\": 500,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"oner365-monitor\",\n        \"count\": 1000,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"oner365-files\",\n        \"count\": 200,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"oner365-elasticsearch\",\n        \"count\": 300,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"oner365-kafka\",\n        \"count\": 300,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    },\n	{\n        \"resource\": \"oner365-generator\",\n        \"count\": 300,\n        \"grade\": 1,\n        \"limitApp\": \"default\",\n        \"strategy\": 0,\n        \"controlBehavior\": 0\n    }\n]', 'f64e89fcf2c8a86d8fa4f01e432fbe37', '2021-07-06 07:04:18', '2021-07-08 10:03:49', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'json', '', '');
INSERT INTO `config_info` VALUES (13, 'com.oner365.dubbo.api.service.IEchoService:1.0::provider:oner365-dubbo', 'dubbo', '{\"parameters\":{\"version\":\"1.0\",\"side\":\"provider\",\"interface\":\"com.oner365.dubbo.api.service.IEchoService\",\"metadata-type\":\"remote\",\"application\":\"oner365-dubbo\",\"dubbo\":\"2.1.0\",\"release\":\"3.0.3\",\"anyhost\":\"true\",\"methods\":\"echo\",\"deprecated\":\"false\",\"dynamic\":\"true\",\"service-name-mapping\":\"true\",\"qos.enable\":\"false\",\"generic\":\"false\",\"revision\":\"1.0\"},\"canonicalName\":\"com.oner365.dubbo.api.service.IEchoService\",\"codeSource\":\"file:/Users/zhaoyong/Documents/git/oner365-cloud/oner365-dubbo/oner365-dubbo-api/target/classes/\",\"methods\":[{\"name\":\"echo\",\"parameterTypes\":[\"java.lang.String\"],\"returnType\":\"java.lang.String\",\"annotations\":[]}],\"types\":[{\"type\":\"java.lang.String\"}],\"annotations\":[]}', 'daeef3ed9de212fb37d2628a67453df5', '2021-10-27 13:13:46', '2022-03-28 10:59:50', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (14, 'com.oner365.dubbo.api.service.IEchoService', 'mapping', 'oner365-dubbo', 'c65e630b4e8a9685454a7bfa7f0f9b24', '2021-10-27 13:13:46', '2021-10-27 13:13:46', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (15, 'org.apache.dubbo.metadata.MetadataService:1.0.0:oner365-dubbo:provider:oner365-dubbo', 'dubbo', '{\"parameters\":{\"version\":\"1.0.0\",\"side\":\"provider\",\"interface\":\"org.apache.dubbo.metadata.MetadataService\",\"group\":\"oner365-dubbo\",\"metadata-type\":\"remote\",\"application\":\"oner365-dubbo\",\"dubbo\":\"2.1.0\",\"release\":\"3.0.3\",\"anyhost\":\"true\",\"delay\":\"0\",\"methods\":\"getExportedURLs,getAndListenInstanceMetadata,toURLs,isMetadataServiceURL,serviceName,getSubscribedURLs,version,getExportedServiceURLs,exportInstanceMetadata,getMetadataInfo,toSortedStrings,getMetadataInfos,getServiceDefinition,getInstanceMetadataChangedListenerMap\",\"getAndListenInstanceMetadata.return\":\"true\",\"deprecated\":\"false\",\"getAndListenInstanceMetadata.sent\":\"true\",\"dynamic\":\"true\",\"qos.enable\":\"false\",\"generic\":\"false\",\"getAndListenInstanceMetadata.1.callback\":\"true\",\"revision\":\"3.0.3\"},\"canonicalName\":\"org.apache.dubbo.metadata.MetadataService\",\"codeSource\":\"dubbo-metadata-api-3.0.3.jar\",\"methods\":[{\"name\":\"getMetadataInfos\",\"parameterTypes\":[],\"returnType\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.MetadataInfo\\u003e\",\"annotations\":[]},{\"name\":\"getServiceDefinition\",\"parameterTypes\":[\"java.lang.String\",\"java.lang.String\",\"java.lang.String\"],\"returnType\":\"java.lang.String\",\"annotations\":[]},{\"name\":\"getServiceDefinition\",\"parameterTypes\":[\"java.lang.String\"],\"returnType\":\"java.lang.String\",\"annotations\":[]},{\"name\":\"getInstanceMetadataChangedListenerMap\",\"parameterTypes\":[],\"returnType\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.InstanceMetadataChangedListener\\u003e\",\"annotations\":[]},{\"name\":\"getMetadataInfo\",\"parameterTypes\":[\"java.lang.String\"],\"returnType\":\"org.apache.dubbo.metadata.MetadataInfo\",\"annotations\":[]},{\"name\":\"getSubscribedURLs\",\"parameterTypes\":[],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedURLs\",\"parameterTypes\":[\"java.lang.String\"],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedURLs\",\"parameterTypes\":[\"java.lang.String\",\"java.lang.String\"],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedURLs\",\"parameterTypes\":[\"java.lang.String\",\"java.lang.String\",\"java.lang.String\"],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedURLs\",\"parameterTypes\":[\"java.lang.String\",\"java.lang.String\",\"java.lang.String\",\"java.lang.String\"],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedURLs\",\"parameterTypes\":[],\"returnType\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"annotations\":[]},{\"name\":\"getExportedServiceURLs\",\"parameterTypes\":[],\"returnType\":\"java.util.Set\\u003corg.apache.dubbo.common.URL\\u003e\",\"annotations\":[]},{\"name\":\"getAndListenInstanceMetadata\",\"parameterTypes\":[\"java.lang.String\",\"org.apache.dubbo.metadata.InstanceMetadataChangedListener\"],\"returnType\":\"java.lang.String\",\"annotations\":[]},{\"name\":\"exportInstanceMetadata\",\"parameterTypes\":[\"java.lang.String\"],\"returnType\":\"void\",\"annotations\":[]},{\"name\":\"version\",\"parameterTypes\":[],\"returnType\":\"java.lang.String\",\"annotations\":[]},{\"name\":\"serviceName\",\"parameterTypes\":[],\"returnType\":\"java.lang.String\",\"annotations\":[]}],\"types\":[{\"type\":\"void\"},{\"type\":\"org.apache.dubbo.metadata.MetadataInfo\",\"properties\":{\"app\":\"java.lang.String\",\"services\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.MetadataInfo$ServiceInfo\\u003e\",\"revision\":\"java.lang.String\"}},{\"type\":\"long[]\",\"items\":[\"long\"]},{\"type\":\"java.util.Map\\u003cjava.lang.String,java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\\u003e\",\"items\":[\"java.lang.String\",\"java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\"]},{\"type\":\"org.apache.dubbo.metadata.InstanceMetadataChangedListener\"},{\"type\":\"java.util.Map\\u003cjava.lang.String,java.lang.Object\\u003e\",\"items\":[\"java.lang.String\",\"java.lang.Object\"]},{\"type\":\"org.apache.dubbo.metadata.MetadataInfo.ServiceInfo\",\"properties\":{\"path\":\"java.lang.String\",\"protocol\":\"java.lang.String\",\"name\":\"java.lang.String\",\"params\":\"java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\",\"version\":\"java.lang.String\",\"group\":\"java.lang.String\"}},{\"type\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.InstanceMetadataChangedListener\\u003e\",\"items\":[\"java.lang.String\",\"org.apache.dubbo.metadata.InstanceMetadataChangedListener\"]},{\"type\":\"java.lang.Integer\"},{\"type\":\"int\"},{\"type\":\"long\"},{\"type\":\"java.util.SortedSet\\u003cjava.lang.String\\u003e\",\"items\":[\"java.lang.String\"]},{\"type\":\"java.util.Set\\u003corg.apache.dubbo.common.URL\\u003e\",\"items\":[\"org.apache.dubbo.common.URL\"]},{\"type\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.MetadataInfo\\u003e\",\"items\":[\"java.lang.String\",\"org.apache.dubbo.metadata.MetadataInfo\"]},{\"type\":\"org.apache.dubbo.common.url.component.URLParam\",\"properties\":{\"hashCodeCache\":\"int\",\"DEFAULT_KEY\":\"java.util.BitSet\",\"enableCompressed\":\"boolean\",\"VALUE\":\"java.lang.Integer[]\",\"EXTRA_PARAMS\":\"java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\",\"rawParam\":\"java.lang.String\",\"KEY\":\"java.util.BitSet\",\"METHOD_PARAMETERS\":\"java.util.Map\\u003cjava.lang.String,java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\\u003e\"}},{\"type\":\"org.apache.dubbo.common.URL\",\"properties\":{\"attributes\":\"java.util.Map\\u003cjava.lang.String,java.lang.Object\\u003e\",\"urlAddress\":\"org.apache.dubbo.common.url.component.URLAddress\",\"urlParam\":\"org.apache.dubbo.common.url.component.URLParam\"}},{\"type\":\"java.lang.Integer[]\",\"items\":[\"java.lang.Integer\"]},{\"type\":\"boolean\"},{\"type\":\"org.apache.dubbo.common.url.component.URLAddress\",\"properties\":{\"port\":\"int\",\"host\":\"java.lang.String\"}},{\"type\":\"java.util.Map\\u003cjava.lang.String,org.apache.dubbo.metadata.MetadataInfo$ServiceInfo\\u003e\",\"items\":[\"java.lang.String\",\"org.apache.dubbo.metadata.MetadataInfo.ServiceInfo\"]},{\"type\":\"java.lang.Object\"},{\"type\":\"java.lang.String\"},{\"type\":\"java.util.BitSet\",\"properties\":{\"words\":\"long[]\"}},{\"type\":\"java.util.Map\\u003cjava.lang.String,java.lang.String\\u003e\",\"items\":[\"java.lang.String\",\"java.lang.String\"]}],\"annotations\":[]}', '57d40742f744b2b3857e8ced026240a6', '2021-10-27 13:13:46', '2022-03-28 10:59:50', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (16, 'com.oner365.dubbo.api.service.IEchoService:1.0::consumer:oner365-dubbo', 'dubbo', '{\"version\":\"1.0\",\"side\":\"consumer\",\"interface\":\"com.oner365.dubbo.api.service.IEchoService\",\"metadata-type\":\"remote\",\"application\":\"oner365-dubbo\",\"dubbo\":\"2.1.0\",\"release\":\"3.0.3\",\"sticky\":\"false\",\"methods\":\"echo\",\"qos.enable\":\"false\",\"revision\":\"1.0\"}', '3e53ec45327d1eca167855b038a60887', '2021-10-27 13:14:17', '2022-03-28 11:00:04', NULL, '0:0:0:0:0:0:0:1', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (17, 'oner365-pulsar-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\npulsar:\n  url: localhost:6650\n  topic: test-topic\n  subscription: test-listener\n            \nlogging:\n  config: classpath:logback-spring.xml', 'b624d74d774b31f725e19ceaf981dd0f', '2022-03-25 09:56:35', '2022-03-25 10:22:53', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (18, 'oner365-datasource-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n  # shardingSphere 分库分表\n  shardingsphere:\n    datasource:\n      names: ds0,ds1\n\n      ds0:\n        type: com.alibaba.druid.pool.DruidDataSource\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        url: jdbc:mysql://localhost:3306/oner365_ds0?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n        username: root\n        password: 1234\n\n      ds1:\n        type: com.alibaba.druid.pool.DruidDataSource\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        url: jdbc:mysql://localhost:3306/oner365_ds1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n        username: root\n        password: 1234\n\n    sharding:\n      default-data-source-name: ds0\n\n      # 分库策略\n      default-database-strategy:\n        inline:\n          sharding-column: user_id\n          algorithm-expression: ds$->{user_id % 2}\n\n      # 分表策略\n      tables:\n        t_order:\n          actual-data-nodes: ds$->{0..1}.t_order_$->{0..1}\n\n          table-strategy:\n            inline:\n              sharding-column: order_id\n              algorithm-expression: t_order_$->{order_id % 2}\n\n          # 分表算法\n          key-generator:\n            type: SNOWFLAKE\n            column: order_id\n    \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', 'fdcc7428ed8ca4241d78b884b309227f', '2022-04-07 13:05:28', '2022-04-08 13:28:19', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (19, 'oner365-influx-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\n  influx:\n    url: http://localhost:8086\n    user: root\n    password: 12345678\n    token: KQQQL_LpMrdpoopKu5_QvYPNVlL-KOoc1zY3dBTfu4xZGnicgAPhbSsryghUH1A21fXzHxPZ-W5hrNVG6tgIbQ==\n    bucket: oner365\n    org: oner365\n            \nlogging:\n  config: classpath:logback-spring.xml', '7457b1534b5d75d9810e35df322dbbf1', '2022-04-15 09:08:04', '2022-04-15 09:35:52', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (20, 'oner365-neo4j-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\n  neo4j:\n    uri: neo4j://127.0.0.1:7687\n    authentication:\n      username: neo4j\n      password: 1234\n            \nlogging:\n  config: classpath:logback-spring.xml', '8f1fbae418ae80a8a1ee340655fc73a8', '2022-04-15 15:21:02', '2022-04-15 15:26:01', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (21, 'oner365-cassandra-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\n  data:\n    cassandra:\n      contact-points:\n      - localhost\n      port: 9042\n      keyspace-name: tutorialspoint\n      local-datacenter: normal\n    \nlogging:\n  config: classpath:logback-spring.xml\n', '716cc58314e231109cc7ce54e5af2f6b', '2021-07-05 08:15:52', '2023-02-23 18:58:23', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (22, 'oner365-vault-dev.yml', 'DEFAULT_GROUP', 'spring:\n\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n\n  cloud:\n    vault:\n      enabled: true\n      application-name: ${spring.application.name}\n      uri: http://localhost:8200\n      token: hvs.4oTEEv4Nd4b3Ki6V357vmTxD\n\n    \nlogging:\n  config: classpath:logback-spring.xml\n', '5fdec3e381b63b95931a7c6e4e0af680', '2021-07-05 08:15:52', '2023-03-28 18:08:58', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (23, 'oner365-statemachine-dev.yml', 'DEFAULT_GROUP', 'spring:\n  autoconfigure:\n    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure\n    \nlogging:\n  config: classpath:logback-spring.xml\n', '3268df083c1d19dd3c31e8cdb5f50158', '2021-07-05 08:15:52', '2023-03-29 14:30:58', 'nacos', '127.0.0.1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (24, 'oner365-mqtt-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n      \nmqtt:\n  uri: tcp://localhost:1883\n  username: admin\n  password: 1234\n  client-id: oner365-client\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', '03ce7d3c13e96d8d0c8488fc7d8194e2', '2023-08-02 18:22:41', '2023-08-14 18:34:24', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (25, 'oner365-ldap-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n\n  ldap:\n    urls: ldap://localhost:389\n    base: dc=ldap,dc=oner365,dc=com\n    username: cn=admin,dc=ldap,dc=oner365,dc=com\n    password: 1234\n            \nlogging:\n  config: classpath:logback-spring.xml\n  ', '230d02896bc76b4481e14ef11397cfcb', '2023-12-12 11:51:54', '2023-12-14 10:58:54', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (26, 'oner365-spark-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # spark\n  spark:\n    master: spark://127.0.0.1:7077\n    app-name: oner365-spark\n    home: 127.0.0.1\n  \nlogging:\n  config: classpath:logback-spring.xml', '96c4f1335ee9deaa3a209a91d5bc608f', '2024-01-18 15:57:24', '2024-01-23 13:57:08', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (27, 'oner365-mongodb-dev.yml', 'DEFAULT_GROUP', 'spring:\n  data:\n    mongodb:\n      database: database\n      uri: mongodb://localhost:27017\n            \nlogging:\n  config: classpath:logback-spring.xml\n  ', 'e097f6c9f2744531910fc2760e6d97b0', '2024-04-09 15:57:54', '2024-04-09 15:58:16', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (28, 'oner365-kubernetes-dev.yml', 'DEFAULT_GROUP', '\nlogging:\n  config: classpath:logback-spring.xml\n  ', '3db845a06141e4f40e07e6db11bd7822', '2024-08-08 15:46:07', '2024-08-08 15:46:26', 'nacos', '127.0.0.1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (29, 'oner365-postgis-dev.yml', 'DEFAULT_GROUP', 'spring:\n  \n  jpa:\n    database-platform: org.hibernate.spatial.dialect.postgis.PostgisPG9Dialect\n    \n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    # postgres\n    driver-class-name: org.postgresql.Driver\n    url: jdbc:postgresql://localhost:5432/postgis?characterEncodeing=utf8&TimeZone=Asia/Shanghai\n    username: root\n    password: 1234\n    \n    druid:\n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n    \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', 'ae52b2a9149692af8254800626613edd', '2024-08-22 10:46:03', '2024-08-22 12:58:41', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (30, 'oner365-shardingsphere-dev.yml', 'DEFAULT_GROUP', 'spring:\n  jpa:\n    hibernate:\n      ddl-auto: update\n  \n  shardingsphere:\n    \n    props:\n      sql-show: true\n        \n    datasource:\n      names: master,slave1\n      \n      master:\n        type: com.zaxxer.hikari.HikariDataSource\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        jdbc-url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n        username: root\n        password: 1234\n        \n      slave1:\n        type: com.zaxxer.hikari.HikariDataSource\n        driver-class-name: com.mysql.cj.jdbc.Driver\n        jdbc-url: jdbc:mysql://localhost:3307/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n        username: root\n        password: 1234\n        \n      sharding:\n        key-generators:\n          snowflake:\n            type: SNOWFLAKE\n            \n    rules:\n      sharding:\n        tables:\n          # 分库分表\n          t_order:\n            # 双主 ds0 ds1 \n#            actual-data-nodes: ds$->{0..1}.t_order_$->{0..1}\n#            database-strategy:\n#              standard:\n#                sharding-column: user_id\n#                sharding-algorithm-name: database_inline\n            actual-data-nodes: master.t_order_$->{0..1}\n                \n            table-strategy:\n              standard:\n                sharding-column: order_id\n                sharding-algorithm-name: table_inline\n        binding-tables:\n        - t_order\n        sharding-algorithms:\n          # 双主 ds0 ds1 \n#          database_inline:\n#            type: INLINE\n#            props:\n#              algorithm-expression: ds$->{user_id % 2}\n          table_inline:\n            type: INLINE\n            props:\n              algorithm-expression: t_order_$->{order_id % 2}\n            \n      # 读写分离    \n      readwrite-splitting:\n        data-sources:\n          read-write-datasource:\n            static-strategy:\n              write-data-source-name: master\n              read-data-source-names: slave1\n            transactional-read-query-strategy: PRIMARY\n            load-balancer-name: random\n        load-balancers:\n          random:\n            type: RANDOM\n\nlogging:\n  config: classpath:logback-spring.xml', '1ddc631fcbc0ed7ca043e08b735d7af0', '2024-09-30 16:04:46', '2024-10-11 19:49:14', 'nacos', '127.0.0.1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (31, 'oner365-clickhouse-dev.yml', 'DEFAULT_GROUP', 'spring:\n  jpa:\n    database-platform: org.hibernate.dialect.MySQL55Dialect  \n\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.clickhouse.jdbc.ClickHouseDriver\n      url: jdbc:clickhouse://localhost:8123/default\n      username: default\n      password: 1234\n      \n      test-while-idle: false\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n    \n  sql:\n    init:\n      mode: never\n      schema-locations:\n      - classpath:db/schema.sql\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nlogging:\n  config: classpath:logback-spring.xml\n  \n  level:\n    com:\n      clickhouse: error', '3920ca4b9fde7a078551d6e3d6a3d19b', '2024-12-30 17:00:17', '2024-12-30 17:59:01', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (32, 'oner365-activemq-dev.yml', 'DEFAULT_GROUP', 'spring:\n  activemq:\n    broker-url: tcp://127.0.0.1:61616?connectionTimeout=3000&soTimeout=500\n    user: admin\n    password: admin\n\nlogging:\n  config: classpath:logback-spring.xml\n  ', 'bd0d8038d6bd9e0a7f2cae83117e4fe4', '2025-04-28 17:01:01', '2025-04-28 17:01:24', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (33, 'oner365-keycloak-dev.yml', 'DEFAULT_GROUP', 'logging:\n  config: classpath:logback-spring.xml\n\nkeycloak:\n\n  realm: test\n  auth-server-url: http://uem.nacos.cn:32329\n  \n  resource: spring-test-scope\n  credentials:\n    secret: wBhFfOBZbnI9PtOrr4AZNqXZFe3L8zaj\n  confidential-port: 0\n  cors: true\n  \n  security-constraints:\n  - auth-roles:\n    - default-roles-test\n    security-collections:\n    - patterns:\n      - /admin/*  ', 'bd0d8038d6bd9e0a7f2cae83117e4fe5', '2025-04-28 17:01:01', '2025-04-28 17:01:24', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (50, 'oner365-websocket-dev.yml', 'DEFAULT_GROUP', 'spring:\n  # datasource\n  datasource:\n    druid:\n      db-type: com.alibaba.druid.pool.DruidDataSource\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://localhost:3306/oner365?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8\n      username: root\n      password: 1234\n      \n      test-while-idle: true\n      initial-size: 1\n      max-active: 10\n      min-idle: 1\n\n  # sentinel监控\n  cloud:\n    sentinel:\n      eager: true\n      transport:\n        dashboard: 127.0.0.1:8850\n      datasource:\n        ds1:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            dataId: sentinel-oner365-gateway.json\n            groupId: DEFAULT_GROUP\n            data-type: json\n            rule-type: flow\n    \n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n\nmybatis:\n  mapper-locations: classpath:mapper/*.xml\n  type-aliases-package: com.oner365.websocket.entity\n\nlogging:\n  config: classpath:logback-spring.xml\n  file:\n    path: /home/cloud/logs\n\n#ssl crt key path\nssl:\n  crt:\n    path: /home/cloud/Download\n  key:\n    path: /home/cloud/Download\n\n\n  ', '3b8389001d86e75c49a9a89cdf2d6c64', '2022-05-26 06:12:59', '2022-05-26 06:37:31', 'nacos', '0:0:0:0:0:0:0:1', 'oner365-cloud', 'dev', '', '', '', 'yaml', '', '');

COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `datum_id` varchar(255) NOT NULL COMMENT 'datum_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext COMMENT '内容',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='config_info_aggr';

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext COMMENT '内容',
  `beta_ips` varchar(1024) DEFAULT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` varchar(128) DEFAULT NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `encrypted_data_key` mediumtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='config_info_beta';

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `tenant_id` varchar(128) DEFAULT '',
  `tag_id` varchar(128) NOT NULL,
  `app_name` varchar(128) DEFAULT NULL,
  `content` longtext,
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` varchar(128) DEFAULT NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL,
  `tag_name` varchar(128) NOT NULL,
  `tag_type` varchar(64) DEFAULT NULL,
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `tenant_id` varchar(128) DEFAULT '',
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `config_tags_tenant_id_idx` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='config_tags_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` varchar(128) DEFAULT '',
  `quota` int DEFAULT '0',
  `usage` int DEFAULT '0',
  `max_size` int DEFAULT '0',
  `max_aggr_count` int DEFAULT '0',
  `max_aggr_size` int DEFAULT '0',
  `max_history_count` int DEFAULT '0',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='group_capacity';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid',
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `tenant_id` varchar(128) DEFAULT '',
  `app_name` varchar(128) DEFAULT NULL,
  `content` longtext,
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` varchar(128) DEFAULT NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) DEFAULT NULL,
  `encrypted_data_key` mediumtext,
  PRIMARY KEY (`nid`),
  KEY `hisconfiginfo_dataid_key_idx` (`data_id`),
  KEY `hisconfiginfo_gmt_create_idx` (`gmt_create`),
  KEY `hisconfiginfo_gmt_modified_idx` (`gmt_modified`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='历史配置表';

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(512) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `uk_username_role` (`username`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(128) DEFAULT '',
  `quota` int DEFAULT '0',
  `usage` int DEFAULT '0',
  `max_size` int DEFAULT '0',
  `max_aggr_count` int DEFAULT '0',
  `max_aggr_size` int DEFAULT '0',
  `max_history_count` int DEFAULT '0',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) NOT NULL,
  `tenant_id` varchar(128) DEFAULT '',
  `tenant_name` varchar(128) DEFAULT '',
  `tenant_desc` varchar(256) DEFAULT NULL,
  `create_source` varchar(32) DEFAULT NULL,
  `gmt_create` bigint NOT NULL,
  `gmt_modified` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `tenant_info_tenant_id_idx` (`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
INSERT INTO `tenant_info` VALUES (1, '1', 'dev', 'dev', '开发环境', 'oner365-cloud', 1625707491397, 1625708174502);
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
