#!/bin/bash
echo 'Stop oner365 cloud project'!
echo 'start...'

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath

function exec() {
	if [ -f $1/tpid ];then
	    tpid=`cat $1/tpid`
	    if kill -0 "$tpid" &> /dev/null; then 
	        echo $1 "Stop '$tpid' Process!"
	        kill -15 $tpid
	        sleep 15
	    fi
	    if kill -0 "$tpid" &> /dev/null; then 
	        echo $1 "Kill '$tpid' Process!"
	        kill -9 $tpid
	        sleep 15
	    fi
	    rm -f $1/tpid
	fi
}

#------------------------------------------------------------------

exec oner365-elasticsearch
exec oner365-files
exec oner365-system
exec oner365-monitor
exec oner365-gateway
echo 'finish!'