#!/bin/bash
echo 'Stop oner365 cloud project'!
echo 'start...'

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

function exec() {
    tpid=`cat $1/tpid`
    rm -f $1/tpid
    if [ ${tpid} ]; then
        echo "Kill '${tpid}' $1 Process!"
        kill -9 $tpid
    else
        echo "$1 Stop Success!"
    fi
    sleep 5
}

#------------------------------------------------------------------

exec oner365-files
exec oner365-turbine
exec oner365-system
exec oner365-monitor
exec oner365-gateway
exec oner365-zuul
exec oner365-config
exec oner365-zipkin
exec oner365-eureka
echo 'finish!'