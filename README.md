# stock

## 升级日志
2021-01-16
- 支持etf, 转债交易
- 接口升级
- 任务管理增加执行功能
- 其他体验优化 

------------


## 项目介绍
- 股票数据抓取
 1. 股票名字及新增自动更新
 2. 股票每日数据抓取
 3. 股票实时价格提示
- 自动交易
 1. 股票交易接口(已实现东方财富网, 支持a股, etf, 债券交易)
 2. 交易策略
- 报表分析
- 注意事项
 1. 不要把调用接口频率调太大, 以免被东方财富监控. 有朋友循环不停歇请求, 导致账号被东方财富警告
 2. 自带一个自动策略, 如需要使用, 请务必弄清楚后再配置启用, 以免自动挂错误的单造成不必要损失

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

<font color="red" size=8>把wiki clone下来就可以看到数据库表结构和部署文档</font>

<font color="red" size=8>如需和github集成自动构建可以引入[deployment](https://github.com/bosspen1/deployment)</font>

------------


## 使用说明

### service
```shell
gradle build -x test
```

### web
```shell
npm install
npm start
```

------------

##### 有什么问题和建议或者有好的交易策略欢迎和我交流, 币的交易也可以, 微信: wildaww
[点击查看微信群二维码](http://qmta9pe04.hd-bkt.clouddn.com/wechat.jpg)
二维码过期可以在issue里通知我更新
