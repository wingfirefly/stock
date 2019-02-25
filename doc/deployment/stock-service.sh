#!/bin/bash

App='stock-service'
ProjectHome='/home/vagrant/stock/'$App
RunHome='/opt/stock'
Version='0.0.1'
Time=`date '+%Y%m%d%H%M%S'`

cd $ProjectHome
git pull
gradle build --no-daemon -x test

kill -9 $(ps -ef|grep java|awk '/'$App.jar'/{print $2}')

cp -f $RunHome/$App.jar $RunHome/$App-$Time.jar
cp -f $ProjectHome/build/libs/$App-$Version.jar $RunHome/$App.jar

cd $RunHome
nohup java -jar -Xms32m -Xmx64m -XX:MetaspaceSize=16m $App.jar > /dev/null 2>&1 &
