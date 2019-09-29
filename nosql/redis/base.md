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