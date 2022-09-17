#!/bin/bash

App='stock-web'
ProjectHome='../'$App

cd $ProjectHome

kill -9 $(ps -ef|grep node|awk '/'$App.js'/{print $2}')

npm install

nohup npm start > /dev/null 2>&1 &
