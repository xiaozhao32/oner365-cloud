#!/bin/bash

LOGSTASH_HOME=/Library/logstash-8.1.0
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

echo 'stop logstash'
tpid=`cat $LOGSTASH_HOME/bin/logstash.pid`
rm -f $LOGSTASH_HOME/bin/logstash.pid
if [ ${tpid} ]; then
kill -9 $tpid
fi
rm -rf $LOGSTASH_HOME/logs/*.log