# 商城介绍

## 项目说明

前台演示：[http://renchao05.top/](http://renchao05.top/)

后台演示：[http://admin.renchao05.top/manage/](http://admin.renchao05.top/manage/)

> **需要说明的是：**
>
> 这个原商城项目确实已经烂大街了，但是作为练手项目，也确实是一个好项目。
>
> 因为本项目中的代码除了逆向工程生产部分，其他每个功能的代码都是经过自己的思考，按照自己的逻辑写的。
>
> 所以由于时间和精力有限，整个项目还只是一个大体的框架。还还有很多需要完善的地方。



## 微服务划分图
![输入图片说明](https://foruda.gitee.com/images/1665235821062848163/87b5a16c_10481880.png "image-20221007151011780.png")

## 软件架构
结合 SpringCloud Alibaba 技术搭配方案：

- SpringCloud Alibaba - Nacos：注册中心（服务发现/注册）
- SpringCloud Alibaba - Nacos：配置中心（动态配置管理）
- SpringCloud - Ribbon：负载均衡
- SpringCloud - Feign：声明式 HTTP 客户端（调用远程服务）
- SpringCloud Alibaba - Sentinel：服务容错（限流、降级、熔断）
- SpringCloud - Gateway：API 网关（webflux 编程模式）
- SpringCloud - Sleuth：调用链监控
- SpringCloud Alibaba - Seata：分布式事务



## 模块介绍

### 商品服务

#### 后台管理

采用前后端分离模式，前端采用开源的renren-fast后台管理系统。

实现的功能有：

- 三级分类
- 品牌管理
- 属性分组
- 平台属性
- 商品维护

**使用的主要技术：**

- 发布商品时，分布式事务使用的是**seata**



#### 前台展示

**使用的主要技术：**

- 首页三级分类采用 Redis + SpringCache 进行缓存。
- 商品检索，采用的是ElasticSearch。
- 商品详情，采用多线程异步编排。



### 仓储服务

![输入图片说明](https://foruda.gitee.com/images/1665235864597791967/78532b6e_10481880.png "image-20221007153952827.png")

实现的功能有：

- 仓库列表
- 查询商品库存
- 查询采购需求
- 合并采购需求
- 查询未领取的采购单
- 领取采购单
- 完成采购



### 认证服务

实现的功能有：

- 注册
- 登录

**使用的主要技术：**

- redis 实现 分布式session

### 购物车服务

![输入图片说明](https://foruda.gitee.com/images/1665235880974823931/829238e4_10481880.png "image-20221007164434269.png")

![输入图片说明](https://foruda.gitee.com/images/1665235894098207937/f3620d75_10481880.png "image-20221007164452964.png")

### 订单服务

**使用的主要技术：**

- 分布式事务，利用RabbitMQ实现：柔性事务
  - 可靠消息+最终一致性方案

> 发布确认功能没有做，也就是说并不是真的可靠消息+最终一致性方案
>
> 后期有机会再补充。

#### 提交订单

![输入图片说明](https://foruda.gitee.com/images/1665235907309044526/7bd3a6a3_10481880.png "image-20221007172808115.png")

#### 支付宝支付

![输入图片说明](https://foruda.gitee.com/images/1665235919891828193/e064ae10_10481880.png "image-20221007172937733.png")

> 上面是基本流程，很多细节功能没有做。



### 秒杀服务

> 比较复杂，原项目也也有很多不合理的地方。所以就没有做。
>
> 以后有机会再补充。



## 部署

> 使用 docker-compose 进行部署。

![输入图片说明](https://foruda.gitee.com/images/1665235943692956284/fff46e4a_10481880.png "image-20221008110236781.png")





