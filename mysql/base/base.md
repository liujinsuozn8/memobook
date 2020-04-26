<span id="catalog"></span>
- [基本配置](#基本配置)
- [DQL语言](#dql语言)
    - [基础查询](#基础查询)
    - [常见函数](#常见函数)
    - [多表查询](#多表查询)
    - [子查询](#子查询)
    - [分页查询](#分页查询)
    - [联合查询](#联合查询)
- [DML语言](#dml语言)
    - [插入数据](#插入数据)
    - [修改数据](#修改数据)
    - [删除数据](#删除数据)
- [DDL语言](#ddl语言)
    - [表和库的管理](#表和库的管理)
    - [数据类型](#数据类型)
    - [常见约束](#常见约束)
        - [约束的基本规则](#约束的基本规则)
        - [删除约束](#删除约束)
    - [标识列/自增长列](#标识列/自增长列)
- [TCL语言](#tcl语言)
    - [事务的基本知识](#事务的基本知识)
    - [会自动提交的操作](#会自动提交的操作)
    - [事务的创建](#事务的创建)
    - [事务的隔离](#事务的隔离)
- [视图](#视图)
- [变量](#变量)
- [存储过程和函数](#存储过程和函数)
    - [存储过程和函数的基本概念](#存储过程和函数的基本概念)
    - [存储过程的语法](#存储过程的语法)
    - [流程控制结构](#流程控制结构)
- [总结](#总结)

# 基本配置
[top](#catalog)
- 一个数据库连接就是一个`Socket`连接
- 环境变量配置：
    - 添加Mysql的`bin`目录
    - 如果放在环境变量末尾无效，可以尝试放在环境变量的开头
        - 可能是环境变量中间有干扰
- 查询mysql使用的是哪个配置文件`mysql --verbose --help | grep my.cnf`
- 登录指令：
    - `mysql -h 主机名 -P 端口号 -u 用户名 -p`，然后输入密码
        - `-h`可以是`localhost`
        - `-P`
        - `-p密码`，`-p`和密码中间不能有空格
    - 直接连接本机，可以不输入`-h`和`-P`
        - `mysql -u 用户名 -p`，然后输入密码
- 修改root密码
    - mysql8
        - mysql停机
        - `cd /usr/local/mysql/bin/`
        - `sudo ./mysqld_safe --skip-grant-table`
        - 启动一个新窗口
        - 免密登录：`./mysql`
        - `use mysql;`
        - 清空旧密码：`update user set authentication_string=''  where user='root';`
        - 刷新：`flush privileges;`
        - 重设秘密：`ALTER user 'root'@'localhost' IDENTIFIED BY '123456';`
        - 刷新：`flush privileges;`
        - 退出：`quit;`
        - 再退出：`exit;`
- **mysql不区分大小写**
    - 建议**关键字大写**
- **mysql字符串索引从1开始**
- 注释
    - 单行注释
        - `#注释内容`
        - `--注释内容`
    - 多行注释
        - `/* 注释内容 */`
- 几个基本库
    - mysql，保存用户信息
    - information_schema，保存元数据信息
    - performance_schema，保存性能信息

# DQL语言
## 基础查询
[top](#catalog)
- data query language
- 查询select
    - 可以查询的内容
        - 查询表字段
        - 查询常量值
            - `select 100;`
            - `select 'xxxx'`
                - mysql不区分字符与字符串，都当作字符处理，可以使用`''`或`""`
        - 查询表达式
            - `select 100*20`
        - 查询函数
            - `select VERSION()`
    - 别名
        - 起别名的方式
            - 使用`as`来起别名，`select 列名 as 别名 ...`
            - 使用`空格`来分隔列名与别名，`select 列名 别名 ...`
        - 如果别名中有特殊字符，需要用`''`或`""`将别名扩起来
            - 建议使用`""`
            - `select VERSION()  "the version"`
    - 去重`distinct`
        - `distinct`放在去重字段的前面
        - `select distinct 字段`
    - `+`
        - mysql的`+`只能作为运算符，不能作为连接符
        - `select 100+10`，将会进行运算得到`110`
        - `select '100'+10`， 输出110
            - 其中一方为字符型，mysql将试图**把字符型数值转换成数值型**
                - 如果转换成功时，会继续进行运算
                - **如果转换失败，则将字符型数值转换成`0`**
        - `select 'aaa'+10`， 输出`10`
            - 转换失败，字符型数值转换成`0`
        - `select null+10`，结果为`null`
            - **只要有一方为`null`，则结果一定是`null`**
    - `concat(参数列表)`连接字符串
        - `select concat(p1, p2, p3, ....)`
        - 如果参数中有`null`，则结果一定是`null`
    - 逻辑运算符
        - `&&`, `||`, `!`
        - 推荐使用：`and`, `or`, `not`
    - 通配符
        - `%`匹配多个字符
        - `_`匹配单 个字符
        - 默认的通配符转义
            - `\%`
            - `\_`
        - 指定转义符号`ESCAPE`
            ```sql
            select * from employees 
            where last_name like '_$_%' ESCAPE '$'
            ```
    - 模糊运算符
        - like
        - between and
            - 包含临界值
        - in
            - 列表中的值必须一致或兼容
        - is null / is not null
    - 安全等于`<=>`
        - 可以用来比较基本数据类型和null
- 排序
    - order by asc|desc
    - 默认升序

## 常见函数
[top](#catalog)
- 调用方法：`select 函数名(参数列表)...`
- 函数分类
    - 单行函数
        - 如`concat`,`length`,`isnull`等
    - 多行函数/聚合函数
        - 一般作统计函数
    - 流程控制函数
- 单行函数
    - 字符函数
        - `length(str)`，获取参数的**字节数**
        - `concat(str1, str2, ....)`，连接字符串
        - `upper(str)`，转换大写
        - `lower(str)`，转换小写
        - `substr()`, `substring()`
            - `substr(str, start)`，从start开始截取到末尾
            - `substr(str, start, lenght)`，从start开始,截取length个字符
            - 如果`start`越界了会返回空字符串
        - `instr(str1, str2)`，寻找`str2`在`str1`中的index
            - 如果找不到，则返回`0`
        - `trim(str)`，去除左右两边的空格
        - `trim(str1 from str2)`，从`str2`的左右两边去除字符`str1`
        - `lpad(str, len, padstr)`，在`str`的左边连续补充`padstr`，直到长度为`len`
            - 长度不足时会进行截位
                - `select lpad('abcd', 5, 'xv')`，得到`1abcd`  ????????????
            - 如果`str > len`，会对`str`截位，从**左开始**截`len`位
                - `select lpad('abcd', 2, '12')`，得到`ab`
        - `rpad(str, len, padstr)`，在`str`的左边连续补充`padstr`，直到长度为`len` 
            - 如果`str > len`，会对`str`截位，从**左开始**截`len`位
                - `select rpad('abcd', 2, '12')`，得到`ab`
        - `replace(str, from, to)`，字符串替换

    - 数学函数
        - `round(x)`，对`x`进行四舍五入
            - 先取绝对值，做四舍五入，再添加符号
        - `round(x，d)`，对`x`进行四舍五入，小数点后保留`d`位
            - 如果x的位数比d小，会在后边补零
                - `select round(1.23456, 7)`，得到`1.2345600`
        - `ceil(x)`，向上取整 (返回>=x的最小整数)
            - `select ceil(1.00)`，返回`1`
        - `floor(x)`，向下取整 (返回<=x的最大整数)
        - `truncate(x, d)`，截断小数位，小数位保留`d`位
            - 小数为不足会在后边补零
                - `select truncate(1.2, 2)`，得到`1.20`
            - 如果没有小数位，则没有任何变化
                - `select truncate(1, 2)`，得到`1`
        - `mod(a, b)`，取余
            - 与`a%b`相同
            - 计算方法`a - a/b *b`
    - 日期函数
        - `now()`，返回当前系统日期+时间
        - `curdate`，返回当前系统日期，不包含时间   ????????????
        - `curtime`，返回当前系统时间，不包含日期   ????????????
        - 获取时间的指定部分
            - 获取年:`year(date)`
                - 参数可以是日期类型，也可以是对应的字符
                   - `select year('1999-02-03')`，返回`1999`
            - 获取月:`Month(date)`
            - 获取月的名称:`MonthName(date)`
        - `str_to_date(str, format)`，将日期格式的字符串`str`转换成指定格式的日期
            - `str_to_date('9-13-1999', '%m-%d-%Y')`

            |格式符|功能|
            |-|-|
            |%Y|4位的年份|
            |%y|2位的年份|
            |%m|月份(01, 02...11, 12)|
            |%c|月份(1, 2...11, 12)|
            |%d|日(01, 02...)|
            |%H|小时，24小时制|
            |%h|小时，12小时制|
            |%i|分钟(00, 01...59)|
            |%s|秒(00, 01... 59)|
        
        - `date_format(date, format)`，将日期转换成指定格式的字符
        - `datediff(date1,date2)`，计算两个日期的差
            - 日期可以是符合格式的字符串
    - 随机函数
        - `rand()`，产生一个[0~1)的随机值
    - 其他函数
        - `version()`，查询当前数据库版本
        - `database()`，查询当前使用的数据库名
        - `user()`，查询当前用户
        - `ifnull(表达式, null时的返回值)`

- 流程控制函数
    - `if(表达式, ture, false)`
        - `select if(true, 'is true', 'is false');`
    - case
        - 对某个值的状态进行判断
            ```sql
            case 要判断的字段或表达式
            when 常量1 then 值1/语句1
            when 常量2 then 值2/语句2
            ...
            else 值n/语句n
            end
            ```

            ```sql
            select  salary, department_id,
            case department_id
            when 30 then salary*1.1
            when 40 then salary*1.2
            else  salary
            end as newSA

            from employees
            ```
        - 对一系列条件进行判断
            ```sql
            case
            when 条件1 then 值1/语句1
            when 条件2 then 值2/语句2
            ...
            else 值n/语句n
            end
            ```
            ```sql
            select  salary,
            case 
            when salary>20000 then 'A'
            when salary>10000 then 'B'
            else 'C'
            end as level
            from employees
            ```

- 多行函数/聚合函数
    - 几个基本函数
        - sum
        - avg
        - max
        - min
        - count
            - `count(字段名)`
            - `count(*)`，统计行数
            - `count(1)`，统计行数
                - 会增加一列常量值
            - 效率
                - `MYISAM`存储引擎下，`count(*)`的效率高
                - `INNODB`存储引擎下，`count(*)`和`count(1)`的效率差不多，比`count(字段名)`的效率要高
                    - 因为`count(字段名)`需要判断`字段值==null`
        - 几个基本函数的特性
            - sum, avg **一般用于处理数值型**
            - max, min, count**可以处理任何类型**
            - 分组函数会忽略null值
            - 可以配合`distinct`使用
                - 如：`sum(distinct 字段名)`
    - `having 分组后的筛选条件`, `where 分组前的筛选条件`
        - `where`的数据源，是原始表
        - `having`的数据源是分组后的结果集
        - 能用分组前筛选的，就优先考虑使用分组前筛选
    - 和分组函数一起查询的字段要求是`group by`后的字段
        - 支持单个字段、多个字段分组，表达式或函数分组(使用的比较少)
    - `order by`放在分组之后

## 多表查询
[top](#catalog)
- 分类标准
    - 年代
        - sql92标准
            - 仅支持`内连接`
        - sql99标准【推荐】
            - 支持：内连接、外连接(左外与右外)、交叉连接
    - 功能分类
        - 内连接
            - 等值连接
            - 非等值连接
            - 自连接
        - 外连接
            - 左外连接
            - 右外连接
            - 全外连接
        - 交叉连接/笛卡尔积
- 多表查询时，如果表使用了别名，则查询字段中也要使用别名
- sql92
    - 等值连接
        - 多表之间使用','连接
        - 多个表的顺序可以互换
        - **执行原理：初始的匹配结果仍然是`笛卡尔积`，然后在做`where`筛选**
        ```sql
        select field1, field2...
        from table1, table2
        where table1.x = table2.y
        ```
    - 非等值连接
        - 连接条件不是`=`

- sql99
    - 内连接
        - 等值连接：效果和sql92的等值连接是一样的
            ```sql
            select 查询列表
            from 表1 别名
            inner join 表2 别名
            on 连接条件
            and 连接条件
            ```
        - 非等值连接 
        - 自连接
    - 外连接
        - 左外连：left (outer) join
        - 右外连：right (outer) join
        - 全外连接：full (outer) join = 内连+左外+右外
            - mysql 不支持
    - 交叉连接/笛卡尔积
        ```sql
        select 查询列表
        from 表1 别名
        cross join 表2 别名
        ```

# 子查询
[top](#catalog)
- 出现在其他语句的select语句，成为子查询或内查询
    - 可以用在增删改查中
    - 外部的查询语句，称为主查询或外查询
- 分类
    - 按结果集的行列数
        - 标量子查询（结果集只有一行一列）
        - 列子查询（结果集只有一列多行）
        - 行子查询（结果集有一行多列）
        - 表子查询（结果集为多行多列）
    - 按子查询出现的位置
        - select后
            - 只能放：标量子查询
        - from后
            - 表子查询
        - **where/having后面**
            - 标量子查询
            - 列子查询
            - 行子查询
        - exists后面（相关子查询）
            - 表子查询
- 子查询放在`()`中
- 子查询一般放在条件的右侧
- 标量子查询，一般搭配**单行操作符**使用
    - 单行操作符：`>`, `<`, `>=`, `<=`, `<>`
- 列子查询，一般搭配**多行操作符**使用
    - 多行操作符
        - `in/not in`
            - 等于列表中的任意一个
        - `any/some`
            - 和子查询返回的某一个值比较
        - `all`
            - 和子查询返回的所有值比较
- 行子查询
    - 将多个字段当作一行来比较
    ```sql
    select * 
    from employees
    where (employee_id, salary) = (
        select min(employee_id), max(salary)
        from employees
    )
    ```

- exists后面（相关子查询）
    - 基本使用
        - 返回`1`则有值，返回`0`则无值
        ```sql 
        select exists(select employee_id from employees)
        ```
## 分页查询
[top](#catalog)
- 语法
    - 必须放在查询语句的最后
    - `offset`, 起始索引/偏移量
        - <label style="color:red">索引从`0`开始</label>
        - 每一页的计算方法：`(page-1) * size`
        - 如果从`0`开始取，可以省略offset
    - 方式1:`size`, n
        - 从结果的第`size`开始取n条
    - 方式2:`size`
        - 从结果中取第`size`条
    ```sql
    select *
    from 表名
    limit offset, size；
    ```

## 联合查询
[top](#catalog)
- 合并多条查询语句
- 多条语句需要遵守的规则
    - 列数必须相同
    - 每一列的类型和顺序必须相同
- `union`会去重
- `union all`不会去重


# DML语言
## 插入数据
[top](#catalog)
- 方法1
    - 语法1
        ```sql
        insert into 表名(列名...)
        values (值,...);
        ```
    - 语法2
        - 可以省略列明，默认使用所有列，而且列的顺序和表中列的顺序必须一致
        - `values`中的列数、类型、顺序，需要与表中的列相同
        ```sql
        insert into 表名
        values (值,...);
        ```    
    - **支持插入多行**
    - 插入值的类型要与列的类型一致或兼容
    - `Nullable`字段可以插入`NULL`，也可以不插入值
    - 列数和values数必须一致
    
    - **支持子查询**
        ```sql
        insert into 表名(列名...)
        select ...
        ```
- 方法2
    ```sql
    insert into 表名
    set 列名=值, 列名=值
    ```
    - 只能插入单行

## 修改数据
[top](#catalog)
- 修改单表的记录
    ```sql
    update 表名
    set 列=值，列=值
    where 筛选条件
    ```
    - 如果没有`where`会更新所有数据
- 修改多表的记录
    ```sql
    update 表1 别名
    inner | left | right join 表2 别名
    on 连接条件
    set 别名.列=值...
    where 筛选条件
    ```

## 删除数据
[top](#catalog)
- delete
    - 单表的删除
        ```sql
        delete from 表名 where 筛选条件 
        ```
        - **如果没有筛选条件会删除所有的数据**
    - 多表的删除
        ```sql
        delete 表1别名，表2别名
        from 表1 别名
        inner | left | right join 表2 别名 on 连接条件
        where 筛选条件 
        ```
- truncate
    - 会清空表中的全部数据，效率比`delete`高一点
    ```sql
    truncate table 表名
    ```
- 如果表中有`自增长列` 
    - `delete`删除后，再插入数据，自增长列的值从断点开始
    - `truncate`删除后，再插入数据，自增长列的值从1开始
- `delete`有返回值，`truncate`没有返回值
    - `truncate`执行后，不会返回删了多少行数据，`delete`会返回
- `truncate`不能回滚，`delete`可以回滚
- **delete可以回滚、truncate可以回滚**

# DDL语言
## 表和库的管理
[top](#catalog)
- 创建：`create`
- 修改：`alter`
- 删除：`drop`

- 库的管理
    - 库的创建
        - `create database 库名`
        - `create database if not exists 库名`，如果库已存在，就不创建
    - 库的修改
        - 更改库的字符集
            - `alter database 库名 character set 字符集`
    - 库的删除
        - `drop database 库名`
        - `drop database if exists 库名`，如果库已存在，就删除
    - 库的修改
 
- 表的管理
    - 创建表
        ```sql
        create table 表名 (
            列名 类型 [(长度)约束],
            列名 类型 [(长度)约束],
            ...
            列名 类型 [(长度)约束]
        )
        ```
    - 修改表
        - `alter table 表名 操作类型 column 列名 。。。`
        - 修改列名
            ```sql
            alter table 表名 change column 旧列名 新列名 类型
            ```
        - 修改列的类型/约束
            ```sql
            alter table 表名 modify column 列名 类型
            ```
        - 添加列
            ```sql
            alter table 表名 add column 列名 类型
            ```
        - 删除列
            ```sql
            alter table 表名 drop column 列名
            ```
        - 修改表名
            ```sql
            alter table 表名 rename to 新表名
            ```
    - 删除表
        ```sql
        drop table 表名
        ```
        ```sql
        drop table is exists 表名
        ```
    - 表的复制
        - 表的复制可以跨库操作
        - 只复制表的结构
            ```sql
            create table 新表名 like 库名.旧表名
            ```
        - 复制结构+数据
            ```sql
            create table 新表名
            select .. from 库名.表...
            ```
        - 只复制表的部分数据+全部结构
            ```sql
            create table 新表名
            select 部分列... from 库名.表... where ...
            ```
        - 只复制部分结构，不复制数据
            - 制造一个所有数据都不满足的条件
            ```sql
            create table 新表名
            select 部分列... from 库名.表... where 1=2
            ```

## 数据类型
[top](#catalog)
- 无符号关键字`unsigned`
    - 如无符号整型：`int unsigned`
- **选择数据类型的原则**
    - 越简单越好
    - 能保存数值的类型越小越好
- 整型
    - 默认是**有符号类型**
    - 如果超出范围会导致异常
    - 如果不设置长度会有默认的长度
    - 整型长度表示显示的长度，与数值范围无关
        - 通过`zerofill`在数据左侧补零，但是会默认变为无符号整数
            ```sql
            create table xxx {
                t int(11) zerofill,
                ...
            } 
            ```

    |类型|字节|范围|
    |-|-|-|
    |Tinyint|1|有符号：[-128, 127]<br/>无符号：[0,255]|
    |Smallint|2|有符号：[-32768, 32767]<br/>无符号：[0,65535]|
    |Mediumint|3|有符号：[-8388608, 8388607]<br/>无符号：[0,1677215]|
    |Int,integer|4|有符号：[-2147483648, 2147483647]<br/>无符号：[0,4294967295]|
    |BIgint|8|有符号：[-9223372036854775808, 9223372036854775807]<br/>无符号：[0,9223372036854775807*2+1]|

- 小数
    - 定点数
        - **如果保存精度较高的数，应该使用定点数**
        - 默认的`M`,`D`为：`(10, 0)`

        |类型|字节|范围|
        |-|-|-|
        |dec(M,D) <br/>decimal(M,D)|M+2|最大取值范围与`double`相同，给定`decimal`的有效取值范围由`M`和`D`决定|

    - 浮点数
        - 如果不指定`M`,`D`，则会**根据插入数值的精度来决定精度**

        |类型|字节|
        |-|-|
        |float(M,D)|4|
        |double(M,D)|8|
    - 特点
        - `D`，小数点后的位数，不足会自动补0
        - `M`，整数和小数位的总长度
        - 如果超过数字范围，会插入临界值
        - `M`和`D`都可以省略

- 字符型
    - 较短文本
        - char，固定长度字符
            - 使用时，不管存储了多少个字符，都会使用M个字符的空间
        - varchar，可变长度字符
            - 使用时，根据存储的字符数量来开辟空间

        |类型|最大字符数|描述|效率|空间的消耗
        |-|-|-|-|-|
        |char(M)|M，可以省略默认为1|M为0-255之间的整数|高|消耗固定的空间，消耗比较大|
        |varchar(M)|M，不可以省略|M为0-65535之间的整数|低|动态空间，消耗比较小

    - 较长的文本
        - text
        - blob，较大的二进制数据
            - 几种blob类型
                |类型|大小(字节)|
                |-|-|
                |TinyBlob|最大255|
                |Blob|最大65k|
                |MediumBlob|最大16M|
                |LongBlob|最大4G|
            - **如果存储的文件过大，数据库的性能会下降**
    - binary/varbinary
        - 用于保存较短的二进制数据
        - 包含二进制字符串，不包含非二进制字符串????????
    - enum类型
        - 枚举类型，要求插入的值必须属于列表中指定的值之一
        - 如果列表成员为1～255，则只需要**1个字节存储**
        - 如果列表成员为255～65535，则需要**2个字节存储**
        - 最多需要65535个成员
        - 如果插入值不再指定范围内，则默认插入空值
        - 插入内容不区分大小写

        ```sql
        create table xxx (
            c1 enum('a', 'b', 'c')
        )
        ```
    - set类型
        - 用于保存集合
        - 可以保存0～64个成员
        - 和`enum`类型的区别是：`set`一次可以**选取多个成员**，`enum`只能选一个
        - 根据成员个数不同，存储所占的字节也不同

            |成员数|字节数|
            |-|-|
            |1～8|1|
            |9～16|2|
            |17～24|3|
            |25～32|4|
            |33～64|8|

        ```sql
        create table xxxx(
            c1 set('a', 'b', 'c', 'd')
        );

        insert into test_set
        values
        ('a'),
        ('a,b'); --插入时 ','后面不能有空格
        ```

- 日期型
    - timestampstamp和实际时区有关，更能反应实际的日期
        - 受mysql版本和sqlmode的影响很大
    - datetime只能反应出插入时的当地时区

    |日期和时间类型|字节|最小值|最大值|
    |-|-|-|-|
    |date|4|1000-01-01|9999-12-31|
    |datetime|8|1000-01-01 00:00:00|9999-12-31 23:59:59|
    |timestamp|4|19700101080001|2038年的某个时刻|
    |time|3|-838:59:59|838:59:59|
    |year|1|1901|2155|

## 常见约束
### 约束的基本规则
[top](#catalog)
- 用于限制表中的数据，保证数据的一致性
- 约束
    - 6种约束
        - `not null`，非空约束，保证该字段不能为空
        - `default`，默认约束，该字段有默认值
        - `primary key`，主键约束，保证该字段的值具有唯一性
            - **在mysql中，主键约束的名称都会自动变为PRIMARY**
        - `unique key`，唯一约束，用于保证该字段的值具有唯一性，可以为空
        - `check`，检查约束，mysql不支持，限制插入的具体值
        - `foreign key`，外键约束，用于限制两个表的关系，用于保证该字段的值必须来自于主表的关联列的值
    - 主键和唯一的对比

        ||保证过唯一性|是否允许为空|数量|可以组合
        |-|-|-|-|-|
        |主键|是|否|只能有一个|是|
        |唯一|是|是|可以有多个|是|
    - 外键
        - 要求在`从表`设置外键关系
        - 从表的外键列的类型和主表关联列的**类型要一致或兼容，名称无要求**
        - 主表的关联列必须是一个key
            - 一般是**主键、唯一**
        - 插入数据时，先插入主表，再插入从表
        - 删除时，先删除从表，再删除主表

- 约束添加的分类
    - 列级约束
        - **6中约束都可以，但是外键约束无效**
    - 表级约束
        - 除了非空约束、默认约束，其他的都可以

- `show index from 表名` 查看索引
    - 包括：主键、外键、唯一

### 添加约束
[top](#catalog)
- 语法
    - 一个列可以有多个列级约束，使用空格分开
    ```sql
    create table 表名(
        字段名 字段类型 列级约束1 列级约束2...,
        字段名 字段类型,
        表级约束1,
        表级约束2,
        ...
    )
    ```
- **创建表**时添加
    - 添加列级约束
        - 主键约束：`primary key`
        - 非空约束：`not null`
        - 唯一约束：`unique`
        - 默认约束：`default 默认值`

        ```sql
        create table stuinfo(
            id int primary key,
            stuName varchar(20) not null,
            gender char(1) check(gender='男' or gender='女'), --可以使用但是不支持
            seat int unique,
            age int default 18,
            majorid int references major(id) --可以声明但是无效
        );
        ```
    - 添加表级约束
        - 基本语法:`【constraint 约束名】 约束类型(字段名)`
            - 如果不写`【constraint 约束名】`部分，会自动使用**字段名命名**
        - 主键约束：`constraint 约束名 primary key(列名)`
        - 唯一约束：`constraint 约束名 unique(列名)`
        - 检查约束：`constraint 约束名 check(gender='男' or gender='女')`
        - 外键约束：`constraint 约束名 foreign key(当前表的列名) references 其他表名(列名)`
        
        ```sql 
        create table stuinfo(
            id int,
            stuName varchar(20),
            gender char(1),
            seat int ,
            age int,
            majorid int,
            
            constraint pk primary key(id),
            constraint uk unique(seat),
            constraint ck check(gender='男' or gender='女'),
            constraint fk_studio_major foreign key(majorid) references major(id)
        );
        ```
- **修改表**时添加
    - 语法
    - 非空约束
        ```sql
        alter table 表名 modify column 列名 类型(长度) not null
        ```
    - 默认约束
        ```sql
        alter table 表名 modify column 列名 类型(长度) default 默认值
        ```
    - 主键约束
        ```sql
        alter table 表名 add 【constraint 主键名】 primary key (列名)
        ```
    - 唯一约束
        ```sql
        alter table 表名 modify column 列名 类型(长度) unique
        alter table 表名 add unique(列名)
        ```
    - 外键约束
        ```sql
        alter table 表名 add constraint 约束名 foreign key(当前表的外键列) references 主表名(列名) 
        ```

### 删除约束
[top](#catalog)
- 删除非空约束
    ```sql
    alter table 表名 modify column 列名 类型 null
    ```
- 删除默认
    - 相当于对列做了重新设定
    ```sql
    alter table 表名 modify column 列名 类型
    ```
- 删除主键
    ```sql
    alter table 表名 drop primary key
    ```
- 删除唯一
    ```sql
    alter table 表名 drop index 唯一约束名
    ```
- 删除外键
    ```sql
    alter table 表名 drop foreign key 外键约束名
    ```

## 标识列/自增长列
[top](#catalog)
- 可以不用手动插入值，系统提供默认的序列值
- 创建表时，设置标识列:`auto_increment`
    ```sql
    create table 表名 (
        列1 类型 primary key auto_increment,
        列2 类型
    )

    insert into 表名 values (NULL, 'xxx') --使用null来填充
    ```
- 修改表时，设置标识列
    ```sql
    alter table 表名 modify column 列名 类型 auto_increment
    ```
- 删除标识列
    - 相当于对列做重新设定
    ```sql
    alter table 表名 modify column 列名 类型
    ```

- 基本特性
    - 标识列必须和key搭配
    - 一个表只能有一个标识列
    - 标识列的类型：**只能是数值型**
    - 标识列可以通过变量：`auto_increment_increment`设置步长
    - 可以通过手动插入值，设置起始值
- `show variables like '%auto_increment%'`
    - 查询自动增长的相关设置
        - `auto_increment_increment` 增长的步长，可以被修改
        - `auto_increment_offset` 增长的起始值，无法别修改


# TCL语言
## 事务的基本知识
[top](#catalog)
- 事务控制语言
- 一个或一组sql语句组成一个执行单元，这个执行单元要么全部执行，要么全部不执行
    - 在这个单元中，每个mysql语句是相互依赖的
    - 如果某条sql语句执行失败或产生错误，则整个单元将会回滚
- MySql的存储引擎
    - 在mysql中的数据用各种不同的技术存储在文件(或内存)中
    - 通过`show engines`来查看mysql支持的存储引擎
    - mysql中使用最多的存储引擎有：innodb、myisam、memory等
        - innodb支持事务，myisam、memory等不支持事务
- 事务的ACID属性
    - 原子性 Atomicity
        - 值事务是一个不可分隔的工作单位，要么都发生，要么都不发生
    - 一致性 Consistency
        - 事务必须使数据库从一个一致性状态变换到另一个一致性状态
    - 隔离性 Isolation
        - 一个事务的执行不能被其他事务干扰
        - 事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰
    - 持久性 Durability
        - 一个事务一旦被提交，它对数据库中数据的改变就是永久性的，之后的其他操作和数据库故障不应该对其有任何影响
## 会自动提交的操作
[top](#catalog)
- DDL操作一旦执行，都会自动提交
- DML操作，默认状态下，执行后，会自动提交
    - 可以通过`set autocommit = 0`关闭自动提交
- 默认在**关闭连接时**，会自动提交

## 事务的创建
[top](#catalog)
- `show variables like 'autocommit'`
    - 查看`自动提交功能`是否开启
- 隐式事务
    - 事务没有明显的开启和结束的标记
    - 如insert，update，delete 语句
- 显示事务
    - 显示事务具有明显的开启和结束标记
    - 前提：**必须设置自动提交功能为禁用**
        - `set autocommit = 0`
        - 单次设定只对当前会话有效
    - 创建方法
        1. 开启事务：
            - 必须写：`set autocommit = 0`
            - 可选`start transaction`
        2. 编写事务中的sql语句 
            - 一般是：select，insert，update，delete
        3. 结束事务
            - `commit;` 提交事务
            - `rollback;` 回滚事务

## 事务的隔离
[top](#catalog)
- 对于同时允许的多个事务，当这些事务访问数据库中相同的数据时，如果没有采取必要的隔离机制，就会导致各种并发问题
    - 脏读：T2**更新**了数据但**没有提交**，T1读取了T2更新的部分，如果T2回滚，T1读取的将是临时且无效的内容
    - 不可重复读：T1读取了一个字段，T2**更新**了该字段**并提交**，T1再次读取同一个字段，值就不同了
    - 幻读：T1从表中读取了一个字段，T2在表中**插入**了一些行，T1再次读取时，就会多出几行
        - **select可能不会发现，但是update时会发现**
- 数据库系统必须具有隔离并发允许各个事务的能力，使他们不会互相影响，避免各种并发问题
- 4中事务隔离级别
    1. `READ UNCOMMITTED` 读未提交数据
        - 允许事务对去未被其他事务提交的变更，脏读、不可重复读、幻读的问题都会出现
    2. `READ COMMITTED` 读已提交数据
        - 只允许事务读取已被其他事务提交的变更，可以避免脏读，**但不可重复读和幻读问题仍然可能出现**
        - 在其他事务修改且未提交时，未提交的部分，不会访问，所以可以避免脏读；但是其他事务提交后，再次读取时会产生不可重复读和幻读的问题
    3. `REPEATABLE READ` 可重复读
        - 确保事务可以多次从一个字段中读取相同的值，在这个事务持续期间，禁止其他事务对这个字段进行更新，**可以避免脏读和不可重复读，但幻读的问题仍然存在**
    4. `SERIALIZABLE` 串行化
        - 确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表执行insert、update、delete，**所有并发问题都可以避免，但性能十分低**
        - 在该级别中，对同一块数据，一个事务会阻塞其他事务
- 首先要在当前会话中启动事务
    - `set autocommit = 0`
- 查询MySql的默认事务隔离级别
    - 5.7:`select @@tx_isolation`
    - 8:`select @@transaction_isolation`
- 设置隔离级别
    - 设置当前连接的隔离级别
        - `set session transaction isolation level 隔离级别`
    - 设置数据系统的全局隔离级别，一般需要重启才有效
        - `set global transaction isolation level 隔离级别`
- **MySql的事务隔离级别**
    - 默认事务隔离级别：`repeatable read`，可重复读
- **回滚点**
    - `save 节点名`，设置保存节点
    - `rollback to a;`，回滚到保存点

# 视图
[top](#catalog)
- 虚拟表，和普通表一样使用，通过表动态生成数据
- 表中只保存了sql逻辑，不保存查询结构
    - 实际没有将具体的数据保存在物理空间中
- 应用场景
    - 多个地方用到同样的查询结果
    - 该查询结果使用的sql语句较复杂
- 视图的好处
    - 重用sql
    - 简化复杂的sql操作，不必知道它的查询细节
    - 保护数据，提高安全性
- **一般不对视图中的具体数据做增删改，应该直接操作表中的数据**
- 创建视图
    ```sql
    create view 视图名
    as
    查询语句
    ```
- 修改视图
    - 方式1
        - 如果视图不存在就创建，如果存在就修改
        ```sql
        create or replace view 视图名
        as
        查询语句
        ```
    - 方式2
        ```sql
        alter view 视图名
        as
        查询语句
        ```
- 删除视图
    ```sql
    drop view 视图名1, 视图名2, 视图名3....
    ```
- 查看视图的结构
    - 查看基本的视图结构
        ```sql
        desc 视图名
        ```
    - 详细显示创建视图的sql
        ```sql
        show create view 视图名
        ```
- 不能做增删改的视图
    - 没有权限的视图
    - 包含以下关键字的sql语句
        - 分组函数、distinct、group by、having、union、union all
    - 常量视图
        ```sql
        create or replace view 视图名
        as
        select 'join' name;
        ```
    - select中包含子查询
    - join
    - from一个不能更新的视图
    - where子句的子查询引用了from子句中的表


# 变量
[top](#catalog)
- 变量的分类
    - 系统变量
        - 全局变量
        - 会话变量
    - 自定义变量
        - 用户变量
        - 局部变量

- 系统变量
    - 由系统提供，属于服务器层面
    - `全局变量`作用域
        - 服务器每次启动，将为所有的全局变量赋初始值，针对所有的会话有效，但不能跨重启
            - 如果需要保存系统变量的修改，需要修改配置文件
    - `会话变量`的作用域
        - 仅针对当前会话有效
    - 使用语法
        - 查看所有的系统变量: 
            - `show global | [session] variables`
        - 查看满足条件的部分系统变量
            - `show global | [session] variables like '%关键字%'`
        - 查看某个具体的系统变量
            - `select @@global | [session].系统变量名`
        - 为系统变量赋值
            - `set global | [session] 系统变量名 = 值`

- 自定义变量
    - 使用步骤
        - 声明、赋值、使用(查看 比较 运算)
    - `用户变量`的作用域
        - 针对当前会话有效，与`会话变量`的作用域相同
        - `用户变量`要求：声明时**必须初始化**
        - 使用步骤
            ```sql
            -- 1 声明并初始化，3种声明方法
            set @用户变量名=值;
            set @用户变量名:=值;
            select @用户变量名:=值;

            -- 2 
            -- 赋值方式1，通过select 或 set，即又可以声明、又可以赋值
            set @用户变量名=值;
            set @用户变量名:=值;
            select @用户变量名:=值;

            -- 赋值方式2，通过 select into
            -- 如保存某个表的数据量
            -- set @count=0;
            -- select count(*) into @count from 表名;
            select 字段 into @变量名 from 表;

            -- 3 查看用户变量
            select @用户变量名;
            ```
        - 变量没有类型
            - `set @a='aaa'`之后，再重新设定`set @a=100`不会有异常
    - `局部变量`的作用域
        - 仅在定义局部变量的`begin end`中有效
        - **应用在`begin end`的第一句话**
        - 使用步骤
            ```sql
            -- 1 声明变量
            -- 声明
            declare 变量名 类型;
            -- 声明+初始化
            declare 变量名 类型 default 值;

            -- 2 赋值 
            -- 通过set或select
            set 局部变量名=值;
            set 局部变量名:=值;
            select @局部变量名:=值;

            -- 通过 select into
            select 字段 into 局部变量名
            from 表;

            -- 3 使用
            select 局部变量名;
            ```
    - 两种变量的比较

        ||作用域|定义和使用的位置|语法|
        |-|-|-|-|
        |用户变量|当前会话|会话中的任何地方|必须加`@`符号，不用限定类型|
        |局部变量|`begin end`中|只能在`begin end`中，且为第一句话|一般不用加`@`符号，必须限定类型|
    
    - 示例：声明两个变量并赋初始值，求和，并打印
        - 用户变量
            ```sql
            set @m=1;
            set @n=2;
            set @sum = @m+@n;
            select @sum;
            ```
        - 局部变量
            ```sql
            declare m int default 1;
            declare n int default 2;
            declare sum int;
            set sum=m+m;
            select sum;
            ```

# 存储过程和函数
## 存储过程和函数的基本概念
[top](#catalog)
- 存储过程一组预先编译好的sql语句的集合，可以理解成批处理语句
    - 每次使用时会查看语句是否已被编译，如果已经编译则直接使用
- 存储过程的好处
    - 提供代码的重用性
    - 简化操作
    - 减少编译次数
    - 减少了和数据库服务器的连接次数
- 存储过程和函数的区别
    - 返回值
        - 存储过程可以有0个返回，也可以有多个返回
        - 函数有且仅有一个返回值
    - 应用场景
        - 存储过程适合批量插入，批量更新
        - 函数适合处理数据后返回一个结果

## 存储过程的语法
[top](#catalog)
- 创建语法
    - 参数列表：`参数模式 参数名 参数类型`
        - 参数模式
            - `IN`，输入参数
            - `OUT`，返回值
            - `INOUT`，又是参数又是返回值
        - 示例：`IN name varchar(20)`
    - 如果`存储过程体`只有一句话，则`begin end`可以省略
    - `存储过程体`的每句sql末尾都必须添加`;`
    - 存储过程的结尾可以使用`delimiter`重新设置
    ```sql
    delimiter 结束标记 -- 重新设定结尾标识
    create procedure 存储过程名(参数列表)
    begin

    end 结束标记
    ```
- 调用语法
    ```sql
    call 存储过程名(实参列表);
    ```
- 删除语法
    - 一次只能删除一个存储过程
    ```sql
    drop procedure 存储过程名;
    ```
- 查看存储过程的信息
    ```sql
    show create procedure 存储过程名;
    ```
- 示例
    - 空参列表
        ```sql
        create table mytest(
            id int,
            name varchar(20)
        );
        ```
        ```sql
        delimiter $
        create procedure p1()
        begin
            insert into mytest values 
            (1, 'aaa'),
            (2, 'bbb'),
            (3, 'ccc'),
            (4, 'ddd');
        end $
        ```
        ```sql
        -- 使用前需要将结束符修改为 ';'
        call p1();
        ```
    - IN模式：查看用户是否登录成功
        ```sql
        create table myadmin (
            id int,
            name varchar(20),
            password varchar(20)
        );

        insert into myadmin values
        (1, 'aa', '1234'),
        (2, 'bb', '2222'),
        (3, 'cc', '3333');
        ``` 
        ```sql
        delimiter $
        create procedure p2(IN name varchar(20), IN passward varchar(20))
        begin
            declare result int default 0;

            select count(*) into result
            from myadmin
            where myadmin.name = name
            and myadmin.password = password;

            select if(result, 'success', 'failure');
        end $
        ```
        ```sql
        call p2('aa', '1234');
        ```
    - OUT模式：查询密码
        ```sql
        create table myadmin (
            id int,
            name varchar(20),
            password varchar(20)
        );

        insert into myadmin values
        (1, 'aa', '1234'),
        (2, 'bb', '2222'),
        (3, 'cc', '3333');
        ``` 
        ```sql
        delimiter $
        create procedure p3(IN name varchar(20), OUT password varchar(20))
        begin
            select myadmin.password into password
            from myadmin
            where myadmin.name = name;
        end $
        ```
        ```sql
        -- 可以不用声明用户变量，而直接使用call p3('aa', @mypassword);
        set @mypassword:='';
        call p3('aa', @mypassword);
        select @mypassword;
        ```
    - 多个OUT模式：通过id查询信息
        ```sql
        create table myadmin (
            id int,
            name varchar(20),
            password varchar(20)
        );

        insert into myadmin values
        (1, 'aa', '1234'),
        (2, 'bb', '2222'),
        (3, 'cc', '3333');
        ``` 
        ```sql
        delimiter $
        create procedure p4(IN id int, OUT name varchar(20), OUT password varchar(20))
        begin
            select myadmin.name, myadmin.password into name, password
            from myadmin
            where myadmin.id = id;
        end $
        ```
        ```sql
        -- 可以不用声明用户变量，而直接使用call p3('aa', @mypassword);
        call p4(2, @myname ,@mypassword);
        select @myname, @mypassword;
        ```
    - INOUT模式：传入a、b两个值，加倍后返回
        ```sql
        delimiter $
        create procedure p5(INOUT a int, INOUT b int)
        begin
            set a = a*2;
            set b = b*2;
        end $
        ```
        ```sql
        set @m = 10;
        set @n = 20;
        call p5(@m, @n);
        select @m, @n;
        ```
    - 比较两个日期并返回结果，正数=大于，负数=小于，0=等于
        ```sql
        delimiter $
        create procedure p6(IN date1 DATETIME, IN date2 DATETIME, OUT result INT)
        begin
            select datediff(date1, date2) into result;
        end $
        ```
        ```sql
        call p6('1999-01-02', now(), @result);
        select @result;
        ```
    - 传入一个日期，格式化成`xx/xx/xx`，并返回
        ```sql
        delimiter $
        create procedure p7(IN mydate DATETIME, out strdate varchar(50))
        begin
            select date_format(mydate, '%y/%m/%d') into strdate;
        end $
        ```
        ```sql
        call p7(now(), @strdate);
        select @strdate;
        ```
## 函数语法
[top](#catalog)
- 创建函数
    - 参数列表包含两部分：`参数名 参数类型`
    - 如果没有`return`语句也不会报错，但不建议
    - 当函数体中仅有一句话，则可以省略`begin end`
    ```sql
    delimiter $
    create function 函数名(参数列表) returns 返回类型
    begin
        函数体
        return 值;
    end $
    ```
- 调用函数
    ```sql
    select 函数名(函数列表)
    ```
- 查看函数
    ```sql
    show create function 函数名
    ```
- 删除函数
    ```sql
    drop function 函数名
    ```

## 流程控制结构
[top](#catalog)
- 分支结构
    - if函数
        - 如果表达式1成立，则返回表达式2的值，否则返回表达式3的值
        ```sql
        select if(表达式1, 表达式2, 表达式3)
        ```
    - case结构
        - 可以作为表达式，嵌套在其他语句中使用，可以放在任何地方，`begin end`内或`begin end`外
        - 可以作为独立的语句来使用，只能放在`begin end`中
        - 如果所有条件都不满足，则执行`else`中的语句
            - 如果没有else语句，则返回`null`
        - 用于实现等值判断
            ```sql
            case 变量|表达式|字段
            when 要判断的值 then 返回的值1|语句1;
            when 要判断的值 then 返回的值2|语句2;
            ...
            else 返回的值n|语句n;
            end case;
            ```
        - 多重If，区间判断
            ```sql
            case
            when 要判断的条件1 then 返回的值1|语句1;
            when 要判断的条件1 then 返回的值2|语句2;
            ...
            else 返回的值n|语句n;
            end case;
            ```
        - 示例：根据分数不同显示不同的等级
            ```sql
            delimiter $
            create procedure test_case(IN score int)
            begin
                case
                when score>=90 and score<=100 then select 'A';
                when score>=80 then select 'b';
                when score>=60 then select 'c';
                else select 'd';
                end case;
            end $
            ```
    - if结构
        - 只能用在`begin end`中
        ```sql
        if 条件1 then 语句1;
        elseif 条件2 then 语句2;
        ...
        [else 语句n;]
        end if;
        ```
- 循环结构
    - 分类
        - while
        - loop
        - repeat
    - 循环控制
        - `iterate`，类似于`continue`
        - `leave`，类似于`break`
    - while，类似于`while`
        ```sql
        [标签:] while 循环条件 do
            循环体;
        end while [标签];
        ```
    - loop
        - 可以模拟死循环
        - 需要搭配`leave`
        ```sql
        [标签:] loop
            循环体;
        end loop [标签];
        ```
    - repeat，类似于`do while`
        ```sql
        [标签:] repeat
            循环体;
        until 结束条件
        end repeat [标签];
        ```
    - 示例：插入数据，次数大于20则停止
        ```sql
        create table myadmin(
            id varchar(20),
            name varchar(20)
        );
        ```
        ```sql
        delimiter $
        create procedure test_while(IN count int)
        begin
            declare i int default 1;
            a: while i<=count do
                insert into myadmin value (concat('aaa', i), 'xxxx');
                if i >= 20 then leave a;
                end if

                set i=i+1;
            end while a;
        end $
        ```
    - 示例：向表中插入指定个数的随机字符串
        ```sql
        create table stringcontent(
            id int primary key auto_increment,
            content varchar(20)
        );
        ```
        ```sql
        delimiter $
        create procedure test_randstr_insert(IN count int)
        begin
            declare i int default 1;
            declare str varchar(26) default 'abcdefghijklmnopqrstuvwxyz';

            #字符串的起始索引
            declare startIndex int default 1;
            #截取的字符串长度
            declare len int default 1;
            while i <= count do
                # 产生一个随机数代表起始索引
                set startIndex = floor(rand() * 26 + 1);
                # 产生一个随机数，代表截取的长度，范围：1~(20-startIndex+1)
                set len = floor(rand() * abs(20-startIndex+1) + 1);

                insert into stringcontent(content) values (substr(str, startIndex, len));
                
                set i = i+1;
            end while;
        end $
        ```
# 总结
[top](#catalog)
- 常用指令
    - `show databases;`,查看所有数据库
    - `user 数据库名`，指定当前使用的库
    - `show tables`，显示当前库的所有表
    - `show tables from 数据库名`，显示指定数据库下的所有表
    - `select database();`，查看当前处于哪个库
    - `select version();`，查看当前数据库版本
        - `mysql --version`
        - `mysql -V`
    - `desc 表名;`，查看表结构
    - `show create table 表;`，显示建表语句
    - `show index from 表名` 查看索引
    - `show variables like '%auto_increment%'` 查询字段增长设置
    - `show variables like 'autocommit'`，查看`自动提交功能`是否开启
    - `set autocommit = 0`，启动事务、设置自动提交功能为禁用
    - 查询MySql的默认事务隔离级别
        - 5.7:`select @@tx_isolation`
        - 8:`select @@transaction_isolation`
    - `set global | [session] transaction isolation level 隔离级别`，设置隔离级别
    - 回滚点
        - `save 节点名`，设置保存节点
        - `rollback to a;`，回滚到保存点
    - `show global | [session] variables [like '%关键字%']`，查找系统变量
    - `select @@global | [session].系统变量名`，查看指定系统变量
    - `set global | [session] 系统变量名 = 值`，重设系统变量

- 数据类型
    ```sql
    int(长度) [zerofill] 自动左侧补零
    decimal(总长,小数位长度)
    float(总长,小数位长度)
    double(总长,小数位长度)
    char(长度)
    varchar(长度)
    text
    blob
    binary/varbinary
    enum('枚举1', '枚举2', '枚举3',....)
    set('集合内容1', '集合内容2', '集合内容3', '集合内容4')

    date
    datetime
    timestamp
    time
    year
    ```

- 数据操作:增删改
    ```sql
    insert into 表名(列名...)
    values (值,...),
    values (值,...),
    ...
    values (值,...);

    insert into 表名
        values (值,...),
        values (值,...),
        ...
        values (值,...);

    insert into 表名(列名...)
        select ...

    insert into 表名
        set 列名1=值, 列名2=值...

    update 表名
    set 列=值，列=值
    where 筛选条件

    update 表1 别名
    inner | left | right join 表2 别名
    on 连接条件
    set 别名.列=值...
    where 筛选条件

    delete from 表名 -- 全部删除，可以回滚

    delete from 表名 where 筛选条件 

    delete 表1别名，表2别名
    from 表1 别名
    inner | left | right join 表2 别名 on 连接条件
    where 筛选条件 

    truncate table 表名 -- 全部删除，bu可以回滚
    ```

- 库操作
    ```sql
    create database 库名
    create database if not exists 库名

    alter database 库名 character set 字符集

    drop database 库名
    drop database if exists 库名
    ```

- 表操作
    - 创建
        ```sql
        -- 直接创建
        create table 表名 (
            列名 int primary key auto_increment,  -- 四种有效的列级约束
            列名 float not null,
            列名 varchar(20) unique,
            列名 int default 18,
            列名 类型[长度] [约束类型1] [约束类型2] ...
            ...
            列名 类型[长度],

            constraint pk primary key(列名),  -- 三种有效的表级约束
            constraint uk unique(列名),
            constraint fk foreign key(列名) references 主表(主表列名)
            【constraint 约束名1】约束类型(字段名)
            【constraint 约束名2】约束类型(字段名)
        )
        ```
        ```sql
        -- 拷贝表
        create table 新表名 like 库名.旧表名

        create table 新表名
        select .. from 库名.表...

        create table 新表名
        select 部分列... from 库名.表... where ...

        create table 新表名
        select 部分列... from 库名.表... where 1=2
        ```
    - 修改
        - 列级修改：`alter table 表名 change | modify | drop column 列名 参数`
            ```sql
            alter table 表名 change column 旧列名 新列名 类型               --修改列名
            alter table 表名 modify column 列名 类型                       --修改列类型，修改非空约束，修改默认约束，修改唯一约束
            
            alter table 表名 modify column 列名 类型(长度) not null         --添加非空
            alter table 表名 modify column 列名 类型(长度) default 默认值   --添加默认
            alter table 表名 modify column 列名 类型(长度) unique          --添加唯一 ??? 删除????????
            alter table 表名 modify column 列名 类型 auto_increment         --添加标识

            alter table 表名 modify column 列名 类型 null                  --删除非空
            alter table 表名 modify column 列名 类型                       --删除默认， 删除标识

            alter table 表名 drop column 列名                              --删除列

            ```

        - 表级修改:`alter table 表名 rename to | add | drop 操作内容`
            ```sql
            alter table 表名 rename to 新表名                            -- 表重命名

            alter table 表名 add 【constraint 主键名】 primary key (列名) -- 添加主键
            alter table 表名 add unique(列名)                            -- 添加唯一
            alter table 表名 add constraint 约束名 foreign key(当前表的外键列) references 主表名(列名)  --添加外键

            alter table 表名 drop primary key                            -- 删除主键  ????????????主键约束名
            alter table 表名 drop index 唯一约束名                        -- 删除唯一
            alter table 表名 drop foreign key 外键约束名                  -- 删除外键
            ```
    - 删除表
        ```sql
        drop table [is exists] 表名
        ```
- 视图
    ```sql
    create or replace view 视图名
    as
    查询语句

    alter view 视图名
    as
    查询语句

    drop view 视图名1, 视图名2, 视图名3....

    desc 视图名     -- 查看视图结构

    show create view 视图名   --查看视图创建语句
    ```
- 存储过程
    ```sql
    delimiter 结束标记 -- 重新设定结尾标识
    create procedure 存储过程名(参数列表)
    begin
        ...
    end 结束标记

    call 存储过程名(实参列表);

    drop procedure 存储过程名;

    show create procedure 存储过程名;
    ```
