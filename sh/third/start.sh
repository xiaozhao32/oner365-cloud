#!/bin/bash

# redis
redis-server /Library/redis-6.0.5/redis.conf

# rabbitmq
rabbitmq-server -detached

# elasticsearch
/Library/elasticsearch-7.6.2/bin/elasticsearch -d 

# nacos
/Library/nacos/bin/startup.sh -m standalone

# skywalking
#/Library/apache-skywalking/bin/startup.sh

# zookeeper
#/Library/apache-zookeeper-3.6.1/bin/zkServer.sh start

# kafka
#/Library/kafka_2.13-2.5.0/bin/kafka-server-start.sh -daemon /Library/kafka_2.13-2.5.0/config/server.properties

# canal
#/Library/canal.deployer-1.1.5/bin/stop.sh
#/Library/canal.deployer-1.1.5/bin/startup.sh

# rocketmq
#nohup sh /Library/rocketmq/bin/mqnamesrv &
#nohup sh /Library/rocketmq/bin/mqbroker -n localhost:9876 &

# sentinel
#java -Dserver.port=8850 -Dcsp.sentinel.dashboard.server=localhost:8851 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.2.jar

echo 'finish!'
