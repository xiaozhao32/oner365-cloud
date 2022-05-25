#!/bin/bash

LOGSTASH_HOME=/Library/logstash-8.1.0
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

nohup $LOGSTASH_HOME/bin/logstash -f config/logstash-sample.conf > ../logs/logstash.log 2>&1 &
echo $! > logstash.pid
echo 'Logstash Start Success'!
echo $!
