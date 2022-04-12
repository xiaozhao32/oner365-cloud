#!/bin/bash

# minio
MINIO_HOME=/home/minio
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

echo 'stop minio'
tpid=`cat $MINIO_HOME/minio.pid`
rm -f $MINIO_HOME/minio.pid
if [ ${tpid} ]; then
kill -9 $tpid
fi
rm -rf $MINIO_HOME/*.log