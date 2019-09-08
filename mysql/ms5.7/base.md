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
            * myisamchk:用来描述、检查、优化和维护`MyISAM`表的实用工具？？？？？
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
* 查看当前用户下所有的存在的数据库:`show databases`/`SHOW DATABASES;`
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
* InnoDB存储引擎