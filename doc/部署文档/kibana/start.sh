#!/bin/bash

KIBANA_HOME=/Library/kibana-7.6.2-darwin-x86_64
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

nohup $KIBANA_HOME/bin/kibana > ../logs/kibana.log 2>&1 &
echo $! > kibana.pid
echo 'Kibana Start Success'!
echo $!
