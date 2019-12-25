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
    - [为什么用nosql](为什么用nosql)

# NoSql入门
## 为什么用nosql
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

- 为什么使用nosql
    - 传统的关系型数据库已经无法解决数据量增长的问题

- nosql是什么
    - **泛指非关系型数据库**
    - nosql的产生就是为了解决大规模数据集合多重数据种类带来的挑战，尤其是海量数据应用难题，包括超大规模数据的存储
    - **这些类型的数据存储不需要固定的模式，无需多余操作就可以横向扩展**

## nosql能做什么
[top](#catalog)
- 易扩展
    - nosql种类繁多，它们的共同特点是去掉关系数据库的关系特新


[top](#catalog)
