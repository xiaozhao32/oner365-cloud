#!/bin/bash

# redis
redis-cli shutdown

# rabbitmq
rabbitmqctl stop

# elasticsearch
tpid=`cat /Users/zhaoyong/logs/elasticsearch_9200.pid`
rm -f /Users/zhaoyong/logs/elasticsearch_9200.pid
if [ ${tpid} ]; then
kill -9 $tpid
fi
rm -f /Library/elasticsearch-7.6.2/logs/*

# nacos
/Library/nacos/bin/shutdown.sh
rm -f /Users/zhaoyong/logs/*

# skywalking
#/Library/apache-skywalking/bin/shutdown.sh
#rm -f /Library/apache-skywalking/logs/*

# zookeeper
#/Library/apache-zookeeper-3.6.1/bin/zkServer.sh stop
#rm -f /Library/apache-zookeeper-3.6.1/logs/*

# kafka
#/Library/kafka_2.13-2.5.0/bin/kafka-server-stop.sh
#rm -f /Library/kafka_2.13-2.5.0/logs/*

# canal
#/Library/canal.deployer-1.1.5/bin/stop.sh
#rm -f /Library/canal.deployer-1.1.5/logs/*

# rocketmq
#/Library/rocketmq/bin/mqshutdown broker
#/Library/rocketmq/bin/mqshutdown namesrv
#rm -f /Library/rocketmq/logs/*

echo 'finish!'
