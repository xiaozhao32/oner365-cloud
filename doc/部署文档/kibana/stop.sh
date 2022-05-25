#!/bin/bash

KIBANA_HOME=/Library/kibana-7.6.2-darwin-x86_64
selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

echo 'stop kibana'
tpid=`cat $KIBANA_HOME/bin/kibana.pid`
rm -f $KIBANA_HOME/bin/kibana.pid
if [ ${tpid} ]; then
kill -9 $tpid
fi
rm -rf $KIBANA_HOME/logs/*.log