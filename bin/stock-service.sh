#!/bin/bash

App='stock-service'
ProjectHome='../'$App
Version='0.0.1'

if test -z "$JAVA_HOME";then
    export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.x86_64
fi

if test -z "$GRADLE_HOME";then
    export GRADLE_HOME=/opt/7.0-rc-1
fi

cd $ProjectHome

kill -9 $(ps -ef|grep java|awk '/'$App-$Version.jar'/{print $2}')

$GRADLE_HOME/bin/gradle build --no-daemon -x test

nohup java -jar -Xms32m -Xmx64m -XX:MetaspaceSize=16m $ProjectHome/build/libs/$App-$Version.jar > /dev/null 2>&1 &
