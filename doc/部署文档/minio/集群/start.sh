#!/bin/bash

MINIO_HOME=/home/minio
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

export MINIO_ROOT_USER=root
export MINIO_ROOT_PASSWORD=e8818da9cc9

nohup  ${MINIO_HOME}/minio server --address ":9001" --console-address ":9003" \
http://192.168.33.41:9001/home/minio/data1 http://192.168.33.41:9001/home/minio/data2 \
http://192.168.33.41:9001/home/minio/data3 http://192.168.33.41:9001/home/minio/data4 \
http://192.168.33.42:9001/home/minio/data1 http://192.168.33.42:9001/home/minio/data2 \
http://192.168.33.42:9001/home/minio/data3 http://192.168.33.42:9001/home/minio/data4 \
http://192.168.33.43:9001/home/minio/data1 http://192.168.33.43:9001/home/minio/data2 \
http://192.168.33.43:9001/home/minio/data3 http://192.168.33.43:9001/home/minio/data4 \
http://192.168.33.44:9001/home/minio/data1 http://192.168.33.44:9001/home/minio/data2 \
http://192.168.33.44:9001/home/minio/data3 http://192.168.33.44:9001/home/minio/data4  > ${MINIO_HOME}/minio.log 2>&1 &
echo $! > minio.pid
echo 'Minio Start Success'!
echo $!
