* redis安装后，默认有16个数据库0~15
* Redis的五大数据类型：
    * String 字符串
        * 一个key对应一个value
        * 二进制安全的
        * 除了字符串，还可以存放图片数据
        * 字符串value最大是512M
    * Hash 哈希
        * 一个hash对应一个键值对集合，字段不可以重复
        * 一个string类型的field和value的映射表
        * 适合存储对象
    * List 列表
        * 列表是简单的字符串列表
        * 按照插入顺序排序
        * 可以在头部或尾部添加元素
        * 本质是链表
        * 列表有序、可以重复
        * <label style="color:red">如果所有元素都被移除了，对应都键会消失</label>
    * Set 集合
        * Set是string类型的无序集合
        * 底层是HashTable数据结构，set可以存放很多字符串元素，且元素无序不重复
    * zset (sorted set) 有序集合
* 基本操作
    * 添加key：`set key value`
    * 查看当前redis的所有key: `keys *`
    * 获取key对应的值：`get key`
    * 查看当前数据库的key-val数量：`dbsize`
    * 清空当前数据库：`flushdb`
    * 清空所有数据库：`flushall`
* string-CRUD操作
    * set：key存在=修改，key不存在=添加
    * get：取得
    * del：删除
    * setex(set with expire)：`setex key time value`超时删除key
    * mset key value [key value ...] 一次性设置多个key value
    * mget key [key...] 一次性获取多个value
* hash-CRUD操作
    * `hset key field value`，添加，可以重复对一个key添加多个字段
    * `hget key field` 查询
    * `hgetall key` 查询所有字段
    * `hdel key` 删除
    * `hmset key field value [field value ...]` 对一个key一次性添加多个字段
    * `hmget key filed [field ...]`查询一个key的多个字段
    * `hlen key` 查询有多少个字段
    * `hexists key fidle` 检查是否存在指定字段
* list-CRUD操作
    * `lpush key node....` 从左边添加
        * `lpush key a b c` 链表的内存结构 c-->b-->a，即从左边添加
    * `rpush key node....` 从右边添加
    * `lrange key start end` end=-1/-2... 表示取到最后一个/取到倒数第二个
    * `lpop key` 从左侧弹出一个数据
    * `rpop key` 从右侧弹出一个数据
    * `del key` 删除key-value
    * `lindex key index` 使用索引获取元素，如果index越界，则返回nil
    * `llen key` 返回list的长度，如果key不存在，key被解释为空列表，返回0
* set-CRUD操作
    * `sadd key value [value...]` 添加元素
    * `smembers emails` 从集合中取出所有元素
    * `sismember key value` 判断是否是集合成员
    * `srem key value` 删除某个元素








<span id="catalog"></span>

- [NoSql入门](#NoSql入门)
    - [web结构的演进](#web结构的演进)
    - [nosql是什么](#nosql是什么)
    - [为什么用nosql](为什么用nosql)
    - [nosql的优点](#nosql的优点)
    - [RDBMS与NoSql的比较](#RDBMS与NoSql的比较)
    - [几种NoSql](#几种NoSql)
    - [NoSql的经典应用](#NoSql的经典应用)
    - [多数据源多数据类型的存储问题](#多数据源多数据类型的存储问题)
    - [大型互联网应用的难点和解决方案](#大型互联网应用的难点和解决方案)
    - [NoSql数据模型简介](#NoSql数据模型简介)
    - [NoSql数据库的四大分类](#NoSql数据库的四大分类)
    - [MongoDB简介](#MongoDB简介)
    - [分布式数据库中CAP原理](#分布式数据库中CAP原理)

- [Redis](#Redis)
- [](#)
- [](#)

# NoSql入门
## web结构的演进
[top](#catalog)
- Cache缓存 + 垂直拆分(业务拆分) + MySql主从复制、读写分离 + 分表分库 + 水平拆分(数据量拆分) + mysql集群
- web应用结构的变化
    - 阶段1：单数据库实例
        - web应用的构成
            ```
            app --> dao --> MySql Instance
            ```
        - 数据存储的瓶颈
            - 访问量：访问量一个数据库实例无法接受
            - 索引量：数据的索引(B+Tree)一个机器的内存放不下
            - 数据量：数据量过多一台机器无法保存
    - 阶段2：`Memcached`(缓存) + MySql + 垂直拆分
        - **架构改进的原因：访问压力增大**
        - web应用的构成
            ```
                                  --> MySql Instance
            app --> dao --> Cache --> MySql Instance
                                  --> MySql Instance
            ```
        - 垂直拆分--优化数据库的结构和索引
            - 针对业务模块来拆分数据库
            - 如：原来买家和卖家的数据都在同一个`MySql Instance`中，现在分别放到两个`MySql Instance`中
        - 通过`Memcached`缓解数据库压力
            - 使用缓存技术来缓解数据库的压力
            - 通过文件缓存来缓解数据库压力，但有一些**缺点**
                - 当访问量继续增大的时候，多台web机器通过文件缓存无法共享
                - 大量的小文件缓存造成了比较高的IO压力
            - 但是`Memcached`只能缓解数据库的**读取压力**
    - 阶段3：MySql主从复制、读写分离
        - **架构改进的原因：写入压力增大**
        - web应用的构成
            ```
                                     读数据 -------------------> MySql Instance(从库 slave)
            app --> dao --> Cache ---写数据---> MySql Instance(主库 master)
                                     读数据 -------------------> MySql Instance(从库 slave)
            ```

        - 每个业务模块的数据库的读写集中在一个数据库， 但是`Memcached`只能缓解数据库的**读取压力**，数据库不堪重负
        - 使用主从复制技术（MySql master-slave），来达到读写分离，来提高读写性能和读库的扩展性

    - 阶段4：分表分库+水平拆分+mysql集群
        - **架构改进的原因**
            - 数据量仍然在增加，导致**主库的写压力开始出现瓶颈**
            - 因为`MyISAM`使用锁表(读写都锁)，在高并发下会出现严重的锁问题，改成使用`InnoDB`引擎
            - MySql推出了**高可靠性**的**MySql Cluster集群**，
        - web应用的构成
            ```
                                        MySql Cluster 1
                                            读数据 ------------> MySql Instance(从库 slave)
                                ------->    写数据 --> MySql Instance(主库 master)
                                            读数据 ------------> MySql Instance(从库 slave)

                                        MySql Cluster 2
                                            读数据 -----------> MySql Instance(从库 slave)
            app --> dao --> Cache ----->    写数据 --> MySql Instance(主库 master)
                                            读数据 -----------> MySql Instance(从库 slave)

                                        MySql Cluster 3
                                            读数据 ------------> MySql Instance(从库 slave)
                                ------->    写数据 --> MySql Instance(主库 master)
                                            读数据 ------------> MySql Instance(从库 slave)                         
            ```
        - 分区分库分表：
            - 拆分方式1：紧耦合数据之间的关系，将不常修改的数据放在一个库，将经常修改的数据放在另一个库
            - 拆分方式2：
                1. 海量数据都保存到一个表中(tableX)压力会非常大，所以设定数据量的阀值：a\b\c...
                2. 数据量达到阀值a时，数据进入库1的tableX
                3. 数据量达到阀值b时，数据进入库2的tableX，以此类推
                4. 将一个表的数据压力平分到各个数据库的表中
    - 阶段5：MySql的扩展性能瓶颈
        - 产生的问题在MySql层面无法很好的解决
        - MySql会存储一些大文本字段(blob)，导致数据库表非常大，做**数据库恢复时会非常的慢，不容易快速恢复数据库**
            - 如1000万4KB的文本=10_000_000 * 4KB / 1024 / 1024 = 38.147G
        - **如果能将这些数据省去，MySql将变得非常小**
        - MySql无法处理这种问题，导致MySql的扩展性差，海量数据下的IO压力大，表结构更改困难

- **alibaba的架构演进**
    |时间|关键内容|
    |-|-|
    |1999|Perl, CGI, Oracle|
    |2000|Java, Servlet|
    |2001-2004<br/>EJB时代|EJB(SLSB, CMP, MDB)<br/>Pattern(ServiceLocator, Delegate, Facade, DAO, DTO)|
    |2005-2007<br/>WithOut EJB重构|去EJB重构：Spring + iBatis + Webx, Antx<br/>底层架构:iSearch, MQ+ESB, 数据挖掘，CMS|
    |2009-2009<br/>海量数据|Memcached集群，MySql+数据切分= Cobar, 分布式存储， Hadoop, KV, CDN|
    |2010<br/>安全，镜像|安全、镜像、应用服务器升级、秒杀、NoSql、SSD|

## nosql是什么
[top](#catalog)
- **NoSql泛指非关系型数据库**
- nosql的产生就是为了解决大规模数据集合多重数据种类带来的挑战，尤其是海量数据应用难题，包括超大规模数据的存储
    - **这些类型的数据存储不需要固定的模式，无需多余操作就可以横向扩展**

## 为什么用nosql
- 传统的关系型数据库已经无法解决数据量增长的问题

- 解决web结构中的无法解决的数据量问题
    - 数据的3V
        - 海量Volume
        - 多样Variety
        - 实时Velocity
    - 互联网需求的3高
        - 高并发
        - 高可扩，主要是横向扩展
            - 横向扩展
                - 在机器数量上进行扩展
                - 可以进行**任务分配和负载均衡**
            - 纵向扩展
                - 在一台机器上进行扩展
                - 但是一台机器是有极限的，无法长期进行扩展
        - 高性能

    - 3v 更多的体现的是对**系统可能会出现的问题**的描述

- <label style="color:red">**重点解决：多数据源、多数据类型的存储问题**</label>


## nosql的优点
[top](#catalog)
- 易扩展
    - nosql种类繁多，它们的共同特点是去掉了关系数据库的关系型特性
    - 数据之间无关系，就可以很容易的扩展，同时也提升了架构的可扩展能力
- 大数据量高性能
    - NoSQL数据库都具有**非常高的读写性能**，尤其在大数据量下，同样表现优秀，这得益于它的无关系性，数据库结构简单
        - 每秒：读11万，写8万
    - 与MySql的Cache比较
        - MySql使用`Query Cache`，每次**表的更新**Cache就**失效**，是一种大粒度的Cache，在针对web2.0的交互频繁的应用，`Query Cache`性能不高
        - NoSQL的Cache是**记录级**的，是一种细粒度的Cache，所以性能更高
- 多样灵活的数据模型
    - NoSql**不需要事先**为需要存储的数据**建立字段**，随时可以存储自定义的数据格式
    - 在关系型数据库中，增删字段比较麻烦，如果是大数据量的表，比较困难

## RDBMS与NoSql的比较
[top](#catalog)
- RDBMS
    - 高度组织化、结构化的数据
    - 结构化查询语言：SQL
    - 数据和关系都存储在单独的表中
    - 数据操纵语言，数据定义语言
    - 严格的一致性
    - 基础事务
- NoSql
    - 没有声明性查询语言
    - 没有预定义的模式
    - 存储方式
        - **KV对**存储
        - 列存储
        - 文档存储
        - 图形数据库
    - `最终一致性`，不是ACID属性
    - 非结构化和不可预知的数据
    - CAP定理
    - 高性能、高可用、和可伸缩性

## 几种NoSql
[top](#catalog)
- 几种NoSql
    - Redis
    - Memcache
    - Mongdb
- 如果需要专注于高速缓存，应该使用：`Memcache`
- 如果需要数据类型丰富，应该使用`Redis`

## 多数据源多数据类型的存储问题
- 数据架构?????????图vedio3
- 由于数据架构的**复杂性**，需要在不同场景采用了多种类型的数据源
    - 关系数据库：基本的数据存储
    - 搜索引擎：提供商业搜索服务
    - Cache、KV：用于高性能场景
    - 外部数据接口：如淘宝/支付宝接口
    - 文档数据库: Schema free的结构化数据检索/管理 场景
    - 列数据库：用于后台的大规模计算场景

- 业务模型
    - 图???????????vedio3
    - 基本信息 ---> sql
    - 商品SPU属性 ---> Document DB
    - 图片 ---> 图片银行接口
    - 相关关键字 ---> Search Engine

## NoSql的经典应用
[top](#catalog)
- 以taobao首页为例,包含以下信息
    - 商品基本信息
    - 商品的描述、详情、评价信息(多文字类)
    - 商品的图片
    - 商品的关键字
    - 商品的波段性的热点高频信息
    - 商品的交易、价格计算、积分累计

- 各数据的分析
    - 商品基本信息
        - 包含的内容：名称、价格、出场日期、生产厂商等
            - 属于冷数据，不会经常变化
        - 存储方式：关系型数据库
            - mysql

    - 商品的描述、详情、评价信息(多文字类)
        - 全部都是大段的文字
        - 存储方式：文档数据库_Documnet DB ---> MongoDB

    - 商品的图片
        - 存储方式：分布式文件系统：
            - taobao：TFS
            - Google：GFS
            - Hadoop：HDFS

    - 商品的关键字
        - 搜索引擎、淘宝内用
        - ISearch

    - 商品的波段性的热点高频信息
        - 存储方式：内存数据库
            - Tair
            - Redis
            - Memcache

    - 商品的交易、价格计算、积分累计
        - 外部系统，外部第3方支付接口
        - 支付宝接口

## 大型互联网应用的难点和解决方案
[top](#catalog)
- 难点
    - 数据类型多样性
    - 数据源多样性和变化重构
    - 数据源改造而数据服务平台不需要大面积重构
        - 防止重构的过程中影响正常使用
- 解决方法
    - EAI和统一数据平台服务层
        - 通过统一接口来访问各种数据
    - 淘宝: `UDSL`

- UDSL : 统一数据服务层
    - UDSL做了什么
        - 在网站应用集群和底层数据源之间，构建一层代理，统一数据层
        - 统一数据层的特性
            - 模型数据映射
                - 实现业务模型 各属性 与 底层不同类型数据源的 模型数据映射
                - 目前支持关系数据库、iSearch、redis、mongodb
            - 统一的查询和更新API
                - 提供了基于业务模型的统一查询和更新的API，简化网站应用跨不同数据源的开发模式
            - 性能优化策略
                - 字段延迟加载，按需返回设置
                - 基于热点缓存平台的二级缓存
                - 异步并行的查询数据：异步并行加载模型中来自不同数据源的字段
                - 并发保护：拒绝访问频率过高的主机IP或IP段
                - 过滤高危的查询：如可能会导致数据库崩溃的全表扫描

    - UDSL是如何做的
        - 映射
            - 解决的问题
                - 传统ORM框架的问题
                    - 不能实现非关系数据库和不同类型数据库的对象数据映射
                    - 早期ORM框架，简化了对关系数据库的查询方式，采用面向对象的方式查询、管理数据库
                    - 对非关系数据类型，不能实现对象数据映射，更不能跨不同数据源
                - USDL提供出关系数据库以外的数据源的对象关系映射
                - UDSL支持同一业务模型的个字段映射到不同的数据
            - 解决方法
                - 设计1
                    - 模型数据映射`DSL`，定义模型字段和底层数据库的映射关系
                        - 描述模型对应哪些类型的数据源，各个数据源的访问方式是什么
                        - 模型有哪些字段，每个字段对于哪个数据源的哪个数据接口方法
                        - 模型字段是否支持延迟加载
                        - 模型字段的值如果需要对原始数据进行逻辑处理，用何种方式
                    - UDSL阅读DSL定义的数据路由，访问不同数据源，组装模型
                        - 通过DSL可以定义模型各个字段和各数据源的数据路由，UDSL在查询数据时通过阅读DSL路由规则来**聚合**各数据源的数据，组装模型

        - 统一查询更新API
            - UDSL采用统一的查询/更新API，统一了不同数据源的查询方式
                - 查询API （Criteria API）：提供类似JPA
                    - 基于模型表达式的查询方式：
                    - 支持按模型属性的结果排序
                    - 支持限定结果返回函数和返回哪些字段
                    - 和JPA不同的是
                        - 支持按需加载：可以指定只返回哪些字段，或者过滤哪些字段
                - 持久化API（Persist API）：提供简单的接口
                    - Dml.insert(product), Dml.update(product), Dml.delete(product)
                    - Dml.insertOrUpdate(product)
            - UDSL自动分析查询/更新参数，并根据模型字段映射配置，转换成底层过数据源的native语句进行数据操作
                

        - 热点缓存

## NoSql数据模型简介
[top](#catalog)
- 示例说明
    - 示例：以一个电商客户、订单、订购、地址模型来对比关系型数据库和非关系型数据库
        - 关系型数据库
            - ER图：关系(1:1,1:n,n:n)，基本范式
        - 非关系型数据库
            - BSON， MongoDB中常用的格式
                - BSON是一种类json的二进制存储格式：Binary Json
                - 与JSON相同，支持内嵌的文档对象和数据对象
                ```json
                // 各部分可以全部聚合
                {
                    "custom":{
                        "adderss":[{"city":"Beijing"}],
                        "order":{...}
                    }
                }

                // 各部分可以全部拆分
                {
                    "adderss":[{"city":"Beijing"}]，
                    
                    "custom":{
                        "adderss":adderss,
                        "order":{"..."}
                    },

                    
                }
                ```

    - 两种方式的问题和难点
        - 为什么示例中的情况可以用聚合模型来处理
            - <label style="color:red">高并发的操作时不太建议有关联查询的，互联网公司用冗余数据来避免关联查询(即减少 Join操作)</label>
                - 分布式跨库连接会浪费资源
                - RDBMS中通过外键主键来完成表间的强连接，不易修改
                - NoSql可以对关系进行快速修改，同时可以从json中提取结果
            - <label style="color:red">分布式事务是支持不了太多的并发的</label>

- 传统数据库中的数据模型
    - number
    - char
    - date
    - blob
    - 

- NoSql的聚合模型
    - KV键值
    - Bson
    - 列族
        - 是按列存储数据的，最大的特点是**方便存储结构化和半结构化数据，方便做数据压缩**
        - 对针对某一列或某几列的查询有非常大的IO优势
        - 图 ??????????? vedio4
    - 图

## NoSql数据库的四大分类
[top](#catalog)
- KV键值
    - Memcache, redis
- 文档型数据库(bson格式比较多)
    - CouchDB
    - MongoDB
    
- 列存储数据库
    - Cassandra, HBase
    - 分布式文件系统
- 图关系数据库
    - Neo4J, InfoGrid
- 四类数据库的对比

    |分类|Example|典型应用场景|数据模型|优点|缺点|
    |-|-|-|-|-|-|
    |KV键值|redis|内容缓存，主要用于处理大量数据的高访问负载，也用于一些日志系统|KV对，通常用hashTable实现（对Value使用来获取key）|查询速度快|数据无结构化，通常值被当作字符串或者二进制数据|
    |列存储数据库|Cassandra, HBase|分布式文件系统|以列簇形式存储，将同一列数据存储在一起|查找速度快，可扩展性强，更容易进行分布式扩展|功能相对局限|
    |文档型数据库|CouchDB，MongoDB|Web应用( 与KV类似，Value是结构化的，不同的是数据库能够了解Value的内容???)|KV对应的键值，Value为结构化数据|数据结构要求不严格，表结构可变，**不需要像关系型数据库一样需要预先定义表结构**|查询性能不高，而且缺乏统一的查询语法|
    |图关系数据库|Neo4J, InfoGrid|社交网络，推荐系统|图结构|利用图结构相关算法|很多时候需要对整个图做计算才能得出需要的信息，并且这种结构不太好做分布式的集群方案|


## 分布式数据库中CAP原理
[top](#catalog)
- 原理：CAP+BASE
- CAP的概念
    - C: Consistency ，强一致性
    - A: Availability，可用性
    - P: Partition tolerance 分区容错性
- 经典CAP图 ?????vedio 6

- CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求，<label style="color:red">最多同时只能满足两个</label>


- <label style="color:red">CAP的3进2</label>
    - 




## MongoDB简介
[top](#catalog)
- MongoDB是一个**基于分布式文件存储的数据库**，由C++编写，是为了给WEB应用提供可扩展的改性数据存储解决方案
- MongoDB是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库中功能最丰富，最想关系数据库的

# Redis
[top](#catalog)
KV + Cache + Persisten



[top](#catalog)
