#!/bin/bash

MINIO_HOME=/Users/taoliu/app/minio
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

export MINIO_ROOT_USER=root
export MINIO_ROOT_PASSWORD=e8818da9cc9

nohup $MINIO_HOME/minio server $MINIO_HOME/data --address localhost:9001 --console-address localhost:9003 > minio.log 2>&1 &
echo $! > minio.pid
echo 'Minio Start Success'!
echo $!
