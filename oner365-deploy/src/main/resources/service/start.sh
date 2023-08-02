#!/bin/bash
SERVICE_NAME=
VERSION=
RESOURCE_NAME=$SERVICE_NAME-$VERSION.jar

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

if [ -f tpid ];then
    tpid=`cat tpid`
    if kill -0 "$tpid" &> /dev/null; then 
        echo $SERVICE_NAME "Stop '$tpid' Process!"
        kill -15 $tpid
        sleep 15
    fi
    if kill -0 "$tpid" &> /dev/null; then 
        echo $SERVICE_NAME "Kill '$tpid' Process!"
        kill -9 $tpid
        sleep 15
    fi
    rm -f tpid
fi

# sh start
#nohup java -jar ./$RESOURCE_NAME  > ../logs/$RESOURCE_NAME.log 2>&1 &

# sh skywalking start
#nohup java -jar -javaagent:../agent/skywalking-agent.jar -Dskywalking.agent.service_name=$SERVICE_NAME -Dskywalking.collector.backend_service=localhost:11800 ./$RESOURCE_NAME  > ../logs/$RESOURCE_NAME.log 2>&1 &

#docker start
java -jar ./$RESOURCE_NAME

echo $! > tpid

#------------------------------------------------------------------

echo $RESOURCE_NAME 'Start Success'!
echo $!
