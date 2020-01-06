数据库慢查询监控
---


## 部署
目前是 定时任务`task` 和 `web` 部署在一个容器里  
后续，`web`性能不够的话，再把`task` 和 `web` 分开部署

## 告警
目前告警是通过 ` 企业微信机器人` 来发送的

###  阈值
有`保存阈值`和`告警阈值`

列表详见 配置文件  `mm-web/src/main/resources/application.yml`


`min-cost` 要收集、保存的慢查询耗时阈值， 类型：数字， 单位 ms  
`max-docs` 最大的文档扫描数， 类型：数字  
`details-host` 域名，可以用来查看详情  

`major-db` 监控的 db name  
`save-db` 要保存到哪里  

