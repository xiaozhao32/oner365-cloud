#!/bin/bash
echo 'Welcome oner365 cloud project'!
echo 'start...'

#------------------------------------------------------------------

selfpath=$(cd "$(dirname "$0")"; pwd) 
cd $selfpath
mkdir logs
VERSION=1.0.0-SNAPSHOT.jar

function execute() {
    ./$1/start.sh
    sleep 15
}

#------------------------------------------------------------------

execute oner365-gateway
execute oner365-system
execute oner365-monitor
echo 'finish!'
