# stock

## 升级日志
### 升级只针对以前已经部署的朋友, 新来的朋友直接按最新步骤来就行

2021-08-29
- 任务优化
- 自动登录

2021-06-25
- 优化请求行情方法

2021-05-29
- 添加自动申购新股新债功能
- 持仓页面优化
- 处理docker部署pull请求

2021-03-27
- 添加成交提醒功能

2021-03-07
- mock开关, 方便测试
- 将原有策略修改成更易懂的网格交易策略
- 其他体验优化

2021-01-16
- 支持etf, 转债交易
- 接口升级
- 任务管理增加执行功能
- 其他体验优化

------------


## 项目介绍
- 股票数据抓取
 1. 股票名字及新增自动更新
 2. 股票每日数据抓取, 需要历史每日行情的朋友可以从单元测试类爬取
 3. 股票实时价格提示
- 自动交易
 1. 股票交易接口(对接东方财富网, 支持a股, etf, 债券交易)
 2. 交易策略
- 报表分析
- 注意事项
 1. 不要把调用接口频率调太大, 以免被东方财富监控. 有朋友循环不停歇请求, 导致账号被东方财富警告
 2. 自带一个网格交易策略, 如需要使用, 可以先配置mock和小成交量测试 请务必弄清楚后再配置启用, 以免自动挂错误的单造成不必要损失
 3. 习惯用maven的朋友可以搜索gradle转maven, 进行项目转换.
 4. 熟悉项目建议从任务(ScheduledTasks)和前端页面调用的接口开始
- 请在法律允许范围内使用, 祝大家早日找到适合自己的方法

------------


## 软件架构
- springboot
- js

------------


## 所需环境
- java ide
- mysql
- gradle
- nodejs
- redis

<font color="red" size=8>文档和数据库表见[wiki](https://github.com/bosspen1/stock/wiki)</font>

<font color="red" size=10>**注意: 把wiki clone下来就可以看到数据库表结构和部署和升级文档!!!**</font>

<font color="red" size=8>如需和github集成自动构建可以引入[deployment](https://github.com/bosspen1/deployment)</font>

------------


## 使用说明

按照wiki里面的deployment文档准备好环境和配置文件后, 可以常规运行或者容器运行

### 常规运行

#### service
```shell
gradle build -x test
java -jar stock-service.jar
```

#### web
```shell
npm install
npm start
```
也可以直接部署到 nginx tomcat 之类的服务器启动

### docker容器启动
```shell
docker-compose up -d --build
```

------------

## 有什么问题和建议或者好的交易策略欢迎进群交流
[点击查看微信群二维码](http://qylmezjpg.hn-bkt.clouddn.com/wechat.jpg)
二维码过期可以在issue里通知我更新, 或者晚点再来
