<span id="catalog"></span>
- [基本操作](#基本操作)

# 基础
[top](#catalog)
* 数据库系统组成
    * 数据库(DB)
    * 数据库管理系统(DBMS) 管理数据库的软件
    * 数据库应用程序：负责与DBMS通信、访问和管理DBMS中存储的数据
* SQL语言
    * SQL的四部分
        * DDL 数据定义：DROP, CREATE, ALTER
        * DML 数据操纵：INSTER, UPDATE, DELETE
        * DQL 数据查询：SELECT
        * DCL 数据控制：GRANT, REVOKE, COMMIT, ROLLBACK
* 数据库访问接口
    * ODBC 开放数据库互连：基于ODBC的应用程序对数据块的操作不依赖任何DBMS，不直接与DBMS交互，所有的操作由对应的DBMS的ODBC驱动程序完成。优点时以统一的方式处理所有的数据库
    * JDBC
    * ADO.NET 微软.NET框架开发的库
    * PDO PHP访问数据块的接口
* mysql的优势
    * 速度快
    * 免费
    * 容易使用：与其他大型数据库的设置和管理相比，复杂程度较低，易于学习
    * 可移植性
    * 丰富的接口
    * 支持查询语言
    * 安全性和连接性：允许基于主机的验证。连接到服务器时，所有的密码传输均采用加密形式，从而保证了密码安全。mysql时网络化的，可以在网络上的任何地方访问，提高数据共享的效率
* MySql工具
    * 命令行工具
        * 服务器工具
            * mysqld:SQL后台访问程序(mysql服务器进程)。该程序必须运行之后，客户端才能通过链接服务器来访问数据库
            * mysqld_safe:服务器启动脚本，增加了一些安全特性。如出现错误时，重启服务器并向错误日志文件写入运行时间
            * mysql.server:服务器启动脚本。用于使用包含为特定级别的、运行启动服务的脚本的、运行目录的系统。它调用mysqld_safe来启动mysql服务器
            * mysql_multi:服务器启动脚本，可以启动或体制系统上安装的多个服务器
            * myisamchk:用来描述、检查、优化和维护`MyISAM`表的实用工具
            * mysqlbug:mysql缺陷报告脚本。可以用来向mysql邮件洗头发送缺陷报告
            * mysql_install_db:该脚本用默认权限创建mysql授权表。通常只是在系统上首次安装mysql时执行一次
        * 客户端工具
            * myisampack:压缩MyISAM表以产生更小的只读表的一个工具
            * mysql:交互式输入SQL语句或**从文件以批处理模式执行它们的命令行工具**
            * mysqlaccess:检查访问主机名、用户名、数据库组合的权限脚本
            * MySQLadmin:执行管理操作的客户程序，如：创建、删除数据库，重载授权表，将表刷新到硬盘上，以及重新打开日志文件。还可以用来检索版本、进程，以及服务器的状态信息
            * mysqlbinlog:从二进制日志读取语句的工具，在二进制文件中包含执行过的语句，**可以用来帮助系统从崩溃中恢复**
            * mysqlcheck:检查、修复、分析以及优化表的表维护客户程序
            * mysqldump:将mysql数据库转储到一个文件
            * mysqlhotcopy:当服务器在运行时，快速备份MyISAM或ISAM表的工具
            * mysqlimport:使用LOAD DATA INFILE将文本文件导入相关表的客户程序
            * mysqlshow:娴熟数据库、表、类以及索引相关信息
            * perror:显示系统或，ysql错误代码含义的工具
    * MySQL Workbench
        * 可视化数据库设计软件

# 基本操作
[top](#catalog)
* 查看当前用户下所有的存在的数据库:`show databases;`
* 查看所有表：`show tables；`
* 指定需要操作的数据库:`use db_name;`
* 创建数据库
    * 创建数据库：`create database db_name;`
    * 查看数据库定义：`show create database db_name;`
* 删除数据库：`DROP DATABASE db_name;`
* 数据库存储引擎
    * DBMS使用数据引擎进行增删改查操作，不同的引擎提供不同的**存储机制、索引技巧、锁定水平等功能**
    * mysql提供了多个不同的存储引擎，包括
        * 处理事务安全表的引擎
        * 处理非事务安全表的引擎
    * 在mysql中，**不需要在整个服务器中使用同一种存储引擎**，**可以针对每个表使用不同的存储引擎**
    * 支持的引擎：
        * InnoDB, MyISAM, Memory, Merge, Archive, Federated, CSV, BLACKHOLE
        * 查看系统所支持的引擎类型:`show engines`
		* 查看默认存储引擎：`show variables like 'storage_engine';`
* InnoDB存储引擎
	* mysql的默认存储引擎
	* 事务型数据库的首选引擎，支持事务安全表(ACID)，支持行锁定和外键
	* 主要特性
		* 提供了提交、回滚、崩溃恢复功能
		* 在SQL查询中，可以将InnoDB类型的表与其他MySQL的表的类型混合起来，在同一个查询中也可以混合
		* 为处理巨大数据量的最大性能设计
		* 在主内存中缓存数据和索引而维持它自己的缓冲池。InnoDB将表和索引存在一个逻辑表空间中，表空间可以包含数个文件（或原始磁盘分区）.InnoDB的表可以是任何尺寸，即使操作系统中存在文件尺寸限制
		* 支持外键完整性约束
			* 每张表的存储都按**主键顺序存放**
			* **如果没有显示在表定义是指定主键，InnoDB会为每一行生成一个6B的ROWID，并以ROWID作为主键**
		* 可用在需要高性能的大型数据库站点上
* MyISAM 存储引擎
	* 基于ISAM的存储引擎
	* 是在web、数据存储、其他应用环境下最常使用的存储引擎之一
	* 拥有较高的插入、查询速度，但是不支持事务
	* mysql 5.5.5之前的版本的默认存储引擎
	* 主要特性
		* 在支持大文件的文件系统和操作系统上，可以使用大文件
		* 增删改操作混合使用时，**动态尺寸的行**产生更少的碎片
		* 表的最大索引数是64，可以通过重新编译来改变，每个索引的最大列数是16个
		* 最大的键长度是1000B，可以通过重新编译来改变
		* BLOB、TEXT列可以被索引
		* 索引列中可以包含NULL，并且**占每个键的0～1个字节**
		* 所有数字键值以高字节优先被存储以允许一个更高的索引压缩？？？？
		* 每表一个AUTO_INCREMENT列的内部处理。MyISAM为INSERT和UPDATE操作自动更新这一列。这使得AUTO_INCREMENT列更快。在序列顶的值被删除之后就不能再利用（第一行是1，删除第一行插入第二行时不是1，是2）
		* **数据文件和索引文件可以放在不同目录**
		* **每个字符列可以有不同的字符集**
		* 有VARCHAR的表可以固定或动态记录长度
		* VARCHAR和CHAR列可以多达64KB
	* 使用MyISAM创建数据库，会产生3个文件：
		* 文件名都以**表名**开始
		* .frm 存储表定义
		* .MYD 数据文件
		* .MYI 索引文件
* Memory存储引擎
	* 该引擎会将表中的数据存储到内存中，为查询和引用其他表提供快速访问
	* 主要特性
		* 表的每个表可以有多大32个索引，每个索引16列，以及500B的最大键长度
		* 执行HASH和BTREE索引
		* 表使用一个固定的记录长度格式？？？
		* 不支持BLOB或TEXT列
		* 支持AUTO_INCREMENT列
		* 支持包含NULL值的列的索引
		* 表可以在所有客户端之间共享
		* 表内容存在内存中，**内存是Memory表和服务器在查询处理时的空闲中创建的内部表共享**
		* 不需要Memory表的内容时，需要释放被Memory表使用的内存。可以执行：`DELETE FROM` 或 `TRUNCATE TABLE`,或者删除整个表(DROP TABLE)
	* mysql中使用Memory引擎作为临时表，存放查询的中间结果
* Archive引擎
	* 该引擎本身并不是事务安全的
	* 支持高并发的插入操作
	* 适合存储归档数据，如日志信息
	* 文件的压缩比高，容量小
	
* 存储引擎的选择
	* 需要提交、回滚、崩溃恢复等事务安全能力 + 并发控制 = InnoDB
	* 主要进行插入、查询 = MyISAM
	* 临时存放数据且数据量不大，不需要较高的数据安全性 =Memory
	* **如果只有插入和查询** = Archive
	
|功能|MyISAM|Memory|InnoDB|Archive|
|-|-|-|-|-|
|存储限制|256TB|RAM|64TB|None|
|支持事务|N|N|Y|N|
|支持全文索引|Y|N|N|N|
|支持数索引|Y|Y|Y|N|
|支持哈希索引|N|Y|N|N|
|支持数据缓存|N|N/A|Y|N|
|支持外键|N|N|Y|N|


# 表的基本操作
[top](#catalog)
* 创建数据表
	* 创建语法
		* **表名不区分大小写**
		* 不能使用SQL中的关键字
			```
			create table <tb_name>(
				字段名， 数据类型[（长度）] [列级别约束条件] [默认值]，
				...
				[表级别约束条件]
			);
			```
		* 实例
			* 表结构 tb_emp1
			
			|字段名称|数据类型|备注|
			|-|-|-|
			|id|INT(11)|员工编号|
			|name|VARCHAR(25)|员工名称|
			|deptId|INT(11)|所在部门编号|
			|salary|FLOAT|工资|
			
			* SQL
				```
				create table tb_emp1(
					id INT(11),
					name VARCHAR(25),
					deptId INT(11),
					salary FLOAT
				);
				```
	* 主键约束
		* 主键列的数据必须惟一，并且不允许为空
		* 唯一标识表中的一条记录，**可以加快查询速度**
		* **主键不可以为NULL**
		* 两种类型主键：
			* **单字段主键**
				* 定义表时指定主键
					* 语法：`字段名 数据类型 primary key [默认值]`
					* 实例:定义tb_emp1的主键为id
						```
						create table tb_emp1(
							id INT(11) primary key,
							name VARCHAR(25),
							deptId INT(11),
							salary FLOAT
						);
						```
				* 定义所有列之后指定主键
					* 语法：`[CONSTRAINT <约束名>] primary key [字段名]` (字段名要加括号)
					* 实例:定义tb_emp1的主键为id
						```
						create table tb_emp1(
							id INT(11),
							name VARCHAR(25),
							deptId INT(11),
							salary FLOAT,
							primary key(id)
						);
						```
			* **多字段联合主键**(只能在定义所有列之后再设定)
				```
				create table tb_emp1(
					id INT(11),
					name VARCHAR(25),
					deptId INT(11),
					salary FLOAT,
					primary key(name, deptId)
				);
				```
	* 非空约束
		* 指定字段值不能为空
		* 有非空约束的字段，如果插入数据时没有指定值，会报错
		* 语法： `字段名 数据类型(长度) NOT NULL`
	* 默认约束
		* 字段的默认值，如果插入数据时，该字段为指定，则自动使用默认值
		* 语法：`字段名 数据类型 default 默认值`
			```
			create table tb_emp1(
				id INT(11) primary key,
				name VARCHAR(25) not null,
				deptId INT(11) default 1111,
				salary FLOAT
			);
			```
	* 属性值自动增加
		* 如果希望每次插入新数据时,字段可以生成自增的值,可以为该字段添加关键字:**auto_increment**
			* 该字段必须是主键的一部分
			* **auto_increment的初始值为1**,每添加一条记录,字段值+1
			* 一个表只能有一个字段使用auto_increment
		* 语法:`字段名 数据类型 auto_increment`
			```
			id 字段会在添加是自增
			create table tb_emp1(
				id INT(11) primary key auto_increment,
				name VARCHAR(25) not null,
				deptId INT(11) default 1111,
				salary FLOAT
			);
			```
	* 外键约束
		* 属于**参照完整性**
		* 用来在两个表的数据之间建立链接，**保持数据引用的完整性、一致性**
		* 外键可以为NULL；若不为NULL，**tableA.每一个外键值 必须等于 tableB.主键的某个值**
		* 外键：
			* 一个表可以有一个或多个外键
			* 外键是一个表中的一列或多列，列可以不是主键
			* **与外键对应的必须是另一个表的主键**
			* 定义外键后，不允许删除在另一个表中具有关联关系的行
			* 外键名不能重复
			* 两个关联的字段的数据类型必须一样，如果不一致会出现一场：ERROR 1005 (HY000)
		* 主表：相关联字段中主键所在的那个表
		* 从表：相关联字段中外键所在的那个表
		* 语法：
			```
			[CONSTRAINT <外键名>] foregin key (字段名,....)
			references <主表名> (主键列1,....)
			```
		* 实例:tb_emp5 与 tb_dept1关联
			```
			create table tb_dept1(
				id int(11) primary key,
				name varchar(22) not null,
				location varchar(50)
			);
			
			create table tb_emp5(
				id int(11) primary key.
				name varchar(25),
				deptId int(11),
				salary float,
				constraint fk_emp_dept1 foregin key(deptId) references tb_dept1(id)
			);
			```
	* 唯一性约束
		* 要求列的值不重复，可以为NULL(只能出现一次)
		* 可以声明多个字段
		* 语法：
			* 定义列时声明：`字段名 数据类型 UNIQUE;`
				```
				create table tb_dept1(
					id int(11) primary key,
					name varchar(22) unique,
					location varchar(50)
				);
				```
			* 定义完列之后声明： `[constraint <约束名>] unique(<字段名>)`
				```
				create table tb_dept1(
					id int(11) primary key,
					name varchar(22),
					location varchar(50),
					constraint sth unique(name)
				);
				```
* 查看数据表结构
	* 查看表基本结构： `describe 表名；` 或 `desc 表名；`
		* 可以查看表的字段信息，包括：字段名、字段数据类型、是否是主键、是否有默认值
	* 查看表详细结构： `show create table <表名\G>;`
		* 可以用来显示创建表时使用的create语句
		* 添加参数`\G`可以使显示结果更易于查看
* 修改数据表
	* 修改表名：`alter table <旧表名> rename [to] <新表名>;` (to对执行结果没有影响)
	* 修改字段数据类型：`alter table <表名> modify <字段名> <数据类型>；`
********************************************
