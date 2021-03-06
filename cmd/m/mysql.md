# mysql

MySQL CLI tool ( not a command )

---

## Install

Homebrew

```bash
brew install mysql
```

Or using native packages

- https://dev.mysql.com/doc/refman/8.0/en/osx-installation-pkg.html

## Commands

References

- Getting Started with MySQL : https://dev.mysql.com/doc/mysql-getting-started/en/

### Dump

References

- Dump Data : http://www.runoob.com/mysql/mysql-database-export.html
- Import Data : http://www.runoob.com/mysql/mysql-database-import.html

Trouble-shooting

- How should I tackle --secure-file-priv in MySQL? https://stackoverflow.com/a/40419548/5110899

Dump

```bash
mysqldump -u USERNAME -p DATABASE | tee -a dump.sql
# then enter password
```

Import

- Read & Execute SQL

```bash
mysql -u USERNAME -p DATABASE < dump.sql
# then enter password
```

### Start

```bash
$ mysql.server
Usage: mysql.server  {start|stop|restart|reload|force-reload|status}  [ MySQL server options ]

$ mysql.server start
```

### Connect

```bash
$ mysql -h HOST -P PORT -u USERNAME -pPASSWORD
# e.g.
$ mysql -h db.icehe.xyz -P 5104 -u username -ppassword
```

First time

```bash
$ mysql -u root -p
Enter password:
# then press ENTER ↩ ( as no password )
```

### Version

```bash
mysql> select version();
+-----------+
| version() |
+-----------+
| 5.7.24    |
+-----------+
1 row in set (0.00 sec)
```

### Status

```bash
mysql> status;
# or
mysql> \s
--------------
mysql  Ver 14.14 Distrib 5.7.24, for osx10.13 (x86_64) using  EditLine wrapper

Connection id:          29
Current database:       life_log
Current user:           root@localhost
SSL:                    Not in use
Current pager:          less
Using outfile:          ''
Using delimiter:        ;
Server version:         5.7.24 Homebrew
Protocol version:       10
Connection:             Localhost via UNIX socket
Server characterset:    utf8
Db     characterset:    utf8
Client characterset:    utf8
Conn.  characterset:    utf8
UNIX socket:            /tmp/mysql.sock
Uptime:                 23 days 8 hours 5 min 10 sec

Threads: 5  Questions: 909871  Slow queries: 225  Opens: 282  Flush tables: 1  Open tables: 243  Queries per second avg: 0.451
--------------
```

### Create User

_仅供参考_

```bash
# login as root user
mysql -u root -p

# create new user
create user 'springuser'@'localhost' identified by 'ThePassword';

# grant privileges to new user ( DML )
GRANT ALL PRIVILEGES ON *.* TO 'username'@'localhost' IDENTIFIED BY 'password';

# if encounter 'Cannot load from mysql.procs_priv, the table is probably corrupted'
mysql_upgrade -u root -p
```

### Change Password

```bash
mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
Query OK, 0 rows affected (0.02 sec)
```

### Database

#### Create

```bash
mysql> CREATE DATABASE test_db;
Query OK, 1 row affected (0.11 sec)
```

#### Show

```bash
mysql> SHOW DATABASES;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| test_db            |
+--------------------+
5 rows in set (0.00 sec)
```

#### Use

```bash
mysql> USE test_db;
Database changed
```

### Current DB & User

```bash
mysql> select DATABASE();
+------------+
| DATABASE() |
+------------+
| life_log   |
+------------+
1 row in set (0.00 sec)

mysql> select USER();
+----------------+
| USER()         |
+----------------+
| root@localhost |
+----------------+
1 row in set (0.00 sec)
```

### Table

#### Desc Definition

```bash
desc table_name
```

#### Show Create Table

```bash
show create table table_name
# e.g.
show create table jobs
```

### Info

```bash
mysql> show variables like '%version%';
+--------------------------+-------------------------------+
| Variable_name            | Value                         |
+--------------------------+-------------------------------+
| admin_tls_version        | TLSv1,TLSv1.1,TLSv1.2,TLSv1.3 |
| immediate_server_version | 999999                        |
| innodb_version           | 8.0.23                        |
| original_server_version  | 999999                        |
| protocol_version         | 10                            |
| slave_type_conversions   |                               |
| tls_version              | TLSv1,TLSv1.1,TLSv1.2,TLSv1.3 |
| version                  | 8.0.23                        |
| version_comment          | Homebrew                      |
| version_compile_machine  | x86_64                        |
| version_compile_os       | osx10.15                      |
| version_compile_zlib     | 1.2.11                        |
+--------------------------+-------------------------------+
12 rows in set (0.00 sec)

```

### Slow Log

```bash
mysql> set global slow_query_log=1;
Query OK, 0 rows affected (0.00 sec)

mysql> show variables like '%slow%';
+---------------------------+--------------------------------------------+
| Variable_name             | Value                                      |
+---------------------------+--------------------------------------------+
| log_slow_admin_statements | OFF                                        |
| log_slow_slave_statements | OFF                                        |
| slow_launch_time          | 2                                          |
| slow_query_log            | ON                                         |
| slow_query_log_file       | /usr/local/var/mysql/icehe-laptop-slow.log |
+---------------------------+--------------------------------------------+
5 rows in set (0.00 sec)

mysql> show variables like '%long_query%';
+-----------------+----------+
| Variable_name   | Value    |
+-----------------+----------+
| long_query_time | 0.000000 |
+-----------------+----------+
1 row in set (0.00 sec)

mysql> system tail /usr/local/var/mysql/icehe-laptop-slow.log
# Time: 2019-02-05T02:05:43.401259Z
# User@Host: root[root] @ localhost []  Id:    20
# Query_time: 0.000048  Lock_time: 0.000000 Rows_sent: 0  Rows_examined: 0
SET timestamp=1549332343;
show processlist;
# Time: 2019-02-05T02:06:47.968559Z
# User@Host: root[root] @ localhost []  Id:    20
# Query_time: 0.001060  Lock_time: 0.000100 Rows_sent: 5  Rows_examined: 1032
SET timestamp=1549332407;
show variables like '%slow%';

mysql> select @@global.tx_isolation,@@tx_isolation,version(),"custom content";
+-----------------------+-----------------+-----------+----------------+
| @@global.tx_isolation | @@tx_isolation  | version() | custom content |
+-----------------------+-----------------+-----------+----------------+
| REPEATABLE-READ       | REPEATABLE-READ | 5.7.24    | custom content |
+-----------------------+-----------------+-----------+----------------+
1 row in set, 2 warnings (0.01 sec)
```

### Processlist

Ref : MySQL慢查询&分析SQL执行效率浅谈 - 简书 : https://www.jianshu.com/p/43091bfa8aa7

```bash
mysql> show processlist;
+----+------+-----------------+----------+---------+------+----------+------------------+
| Id | User | Host            | db       | Command | Time | State    | Info             |
+----+------+-----------------+----------+---------+------+----------+------------------+
| 16 | root | localhost:62050 | life_log | Sleep   |   27 |          | NULL             |
| 17 | root | localhost:62051 | NULL     | Sleep   |   27 |          | NULL             |
| 20 | root | localhost       | life_log | Query   |    0 | starting | show processlist |
+----+------+-----------------+----------+---------+------+----------+------------------+
3 rows in set (0.00 sec)
```

### Warnings

```bash
mysql> show warnings;
ERROR 2006 (HY000): MySQL server has gone away
No connection. Trying to reconnect...
Connection id:    21
Current database: life_log

+---------+------+------------------------------------------------------------------------------------------------------------------------+
| Level   | Code | Message                                                                                                                |
+---------+------+------------------------------------------------------------------------------------------------------------------------+
| Warning | 1287 | 'COM_FIELD_LIST' is deprecated and will be removed in a future release. Please use SHOW COLUMNS FROM statement instead |
| Warning | 1287 | 'COM_FIELD_LIST' is deprecated and will be removed in a future release. Please use SHOW COLUMNS FROM statement instead |
| Warning | 1287 | 'COM_FIELD_LIST' is deprecated and will be removed in a future release. Please use SHOW COLUMNS FROM statement instead |
| Warning | 1287 | 'COM_FIELD_LIST' is deprecated and will be removed in a future release. Please use SHOW COLUMNS FROM statement instead |
+---------+------+------------------------------------------------------------------------------------------------------------------------+
4 rows in set (0.01 sec)
```

### Lock

References

- MySQL Transactional and Locking Commands - MySQL Reference Manual [Book] : https://www.oreilly.com/library/view/mysql-reference-manual/0596002653/ch06s07.html

```sql
> lock table t write;
Query OK, 0 rows affected (0.00 sec)

> unlock tables;
Query OK, 0 rows affected (0.00 sec)
```

### Isolation

隔离级别

```bash
mysql> select @@tx_isolation;
+-----------------+
| @@tx_isolation  |
+-----------------+
| REPEATABLE-READ |
+-----------------+
1 row in set, 1 warning (0.00 sec)

mysql> select @@global.tx_isolation;
+-----------------------+
| @@global.tx_isolation |
+-----------------------+
| REPEATABLE-READ       |
+-----------------------+
```

### \G \g

References

- MySQL :: MySQL 8.0 Reference Manual :: 4.5.1.2 mysql Client Commands : https://dev.mysql.com/doc/refman/8.0/en/mysql-commands.html

```bash
mysql> help

For information about MySQL products and services, visit:
   http://www.mysql.com/
For developer information, including the MySQL Reference Manual, visit:
   http://dev.mysql.com/
To buy MySQL Enterprise support, training, or other products, visit:
   https://shop.mysql.com/

List of all MySQL commands:
Note that all text commands must be first on line and end with ';'
?         (\?) Synonym for `help'.
clear     (\c) Clear the current input statement.
connect   (\r) Reconnect to the server. Optional arguments are db and host.
delimiter (\d) Set statement delimiter.
edit      (\e) Edit command with $EDITOR.
ego       (\G) Send command to mysql server, display result vertically.
exit      (\q) Exit mysql. Same as quit.
go        (\g) Send command to mysql server.
help      (\h) Display this help.
nopager   (\n) Disable pager, print to stdout.
notee     (\t) Don't write into outfile.
pager     (\P) Set PAGER [to_pager]. Print the query results via PAGER.
print     (\p) Print current command.
prompt    (\R) Change your mysql prompt.
quit      (\q) Quit mysql.
rehash    (\#) Rebuild completion hash.
source    (\.) Execute an SQL script file. Takes a file name as an argument.
status    (\s) Get status information from the server.
system    (\!) Execute a system shell command.
tee       (\T) Set outfile [to_outfile]. Append everything into given outfile.
use       (\u) Use another database. Takes database name as argument.
charset   (\C) Switch to another charset. Might be needed for processing binlog with multi-byte charsets.
warnings  (\W) Show warnings after every statement.
nowarning (\w) Don't show warnings after every statement.
resetconnection(\x) Clean session context.

For server side help, type 'help contents'
```

### utf8 & utf8mb4

References

- 清官谈 MySQL 中 utf8 和 utf8mb4 区别 : http://blogread.cn/it/article/7546?f=wb_blogread

### Binlog

References

- mysql中如何开启binlog? : https://www.cnblogs.com/chuanzhang053/p/9335924.html

```bash
# e.g.
mysql> set @@binlog_format=row;
Query OK, 0 rows affected (0.00 sec)

mysql> select @@binlog_format;
+-----------------+
| @@binlog_format |
+-----------------+
| ROW             |
+-----------------+
1 row in set (0.00 sec)

mysql> select @@binlog_row_image;
+--------------------+
| @@binlog_row_image |
+--------------------+
| FULL               |
+--------------------+
1 row in set (0.00 sec)

mysql> show binlog events;
```

```bash
# e.g.
$ mysqlbinlog -vv mysql-bin.000001 --start-position=2078
```

### sql_safe_updates

```bash
mysql> set sql_safe_updates=1;
Query OK, 0 rows affected (0.00 sec)

mysql> show variables like '%sql_safe_updates%';
+------------------+-------+
| Variable_name    | Value |
+------------------+-------+
| sql_safe_updates | ON    |
+------------------+-------+
1 row in set (0.00 sec)
```

保护：如果忘记在 update/delete 语句添加 where 条件，或者索引字段的话，执行会报错

### Batched Key Access

References

- 35 | join语句怎么优化？: https://time.geekbang.org/column/article/80147

优化算法，开启方法

- 依赖 MRR

```sql
set optimizer_switch='mrr=on,mrr_cost_based=off,batched_key_access=on';
```

### innodb_lock_wait_timeout

**锁等待超时时间**

```sql
set Innodb_lock_wait_timeout = 5;
```

### Avg Row Len

References

- Avg_row_length是怎么计算的？: https://www.cnblogs.com/sunss/p/6122997.html
- Q & A ( on MySQL Forums )
    - How Avg_row_length is calculated? : https://forums.mysql.com/read.php?22,219129
    - Re: How Avg_row_length is calculated? : https://forums.mysql.com/read.php?22,219129,224296#msg-224296

Command

- Avg_row_length 顾名思义是：平均每行的长度
    - e.g. 查询命令 & 输出，如下

```sql
show table status like 'tb_name'\G
```

```sql
*************************** 1. row ***************************
           Name: tb_name
         Engine: InnoDB
        Version: 10
     Row_format: Compact
           Rows: 3425
 Avg_row_length: 138
    Data_length: 475136
Max_data_length: 0
   Index_length: 1572864
      Data_free: 3145728
 Auto_increment: 6894011508
    Create_time: 2016-10-12 15:03:25
    Update_time: NULL
     Check_time: NULL
      Collation: utf8mb4_general_ci
       Checksum: NULL
 Create_options:
        Comment: NULL
row in set (0.00 sec)
```

该值是如何计算得出？

- InnoDB 的行数是一个近似值
- 平均每行长度 = 数据大小 / 行数
- 大部分都是超过了每行的长度
    - 因为在 InnoDB 的老版本中为了页对齐都自动的往上增加了
    - 比如有一行行长 29 bytes 为了保证页对齐，往上加了 1 或 2 bytes
- 在经历了多次块分裂后，认为「块」到达约 69% 满了

## SQL

### Data Type

**数据类型**

INT

- tinyint 1
- smallint 2
- midiumint 3
- int 4
    - `INT(11)` 中的 11 表示显示宽度，使用了 zerofille(0) 后，未满的宽度会用 0 填充
- bigint 8

### Pagenation

- `LIMIT length`
- `LIMIT offset, length` = `LIMIT length OFFSET offset`

### ON DUPLICATE KEY UPDATE

- References
    - https://dev.mysql.com/doc/refman/8.0/en/insert-on-duplicate.html

### Prepare

- References
    - 理解Mysql prepare预处理语句 : https://www.cnblogs.com/simpman/p/6510604.html
    - MySQL :: MySQL 8.0 Reference Manual :: 13.5 Prepared SQL Statement Syntax : https://dev.mysql.com/doc/refman/8.0/en/sql-syntax-prepared-statements.html

### Explain

- References
    - MySQL 性能优化神器 Explain 使用分析 : https://segmentfault.com/a/1190000008131735

### SERIAL

`SERIAL` SQL TYPE

- an alias for `BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE`

### binlog_row_image

References

- MySQL 5.7贴心参数之binlog_row_image : http://www.cnblogs.com/gomysql/p/6155160.html

### Index & Key

References

- mysql中index和key的区别 : https://blog.csdn.net/kusedexingfu/article/details/78347354

> KEY | INDEX
>
> - KEY is normally a synonym for INDEX. The key attribute PRIMARY KEY can also be specified as just KEY when given in a column definition. This was implemented for compatibility with other database systems.

### Multi-Range Read

References

- 35 | join语句怎么优化？: https://time.geekbang.org/column/article/80147

优化开启语句

```bash
mysql> set optimizer_switch="mrr_cost_based=off";
Query OK, 0 rows affected (0.00 sec)

# e.g. `Using MRR`
mysql> explain select * from t1 where a>=1 and a<=100;
+----+-------------+-------+------------+-------+---------------+------+---------+------+------+----------+----------------------------------+
| id | select_type | table | partitions | type  | possible_keys | key  | key_len | ref  | rows | filtered | Extra                            |
+----+-------------+-------+------------+-------+---------------+------+---------+------+------+----------+----------------------------------+
|  1 | SIMPLE      | t1    | NULL       | range | a             | a    | 5       | NULL |  100 |   100.00 | Using index condition; Using MRR |
+----+-------------+-------+------------+-------+---------------+------+---------+------+------+----------+----------------------------------+
1 row in set, 1 warning (0.00 sec)
```

### Union

- union : 去重
- union all : 不去重

## Trouble Shooting

### Cannot mysql.server start

ERROR

> MySql server startup error 'The server quit without updating PID file '

- StackOverflow : https://stackoverflow.com/questions/4963171/mysql-server-startup-error-the-server-quit-without-updating-pid-file

Problem

```bash
$ mysql.server start
Starting MySQL
 ERROR! The server quit without updating PID file (/usr/local/var/mysql/icehe-mbp.local.pid).
```

Steps

```bash
$ cd /usr/local/var/mysql/
$ less icehe-mbp.local.err
# e.g.
…… [FATAL] InnoDB: Table flags are 0 in the data dictionary but the flags in file ./ibdata1 are 0x4000!…
# e.g.
…… Can't open the mysql.plugin table. Please run mysql_upgrade to create it
# e.g.
…… Can't open and lock privilege tables: Table 'mysql.user' doesn't exist
```

```bash
$ pwd
/usr/local/var/mysql
$ rm -rf *
$ mysqld --initialize-insecure
# ommited output
```

Reference

- [MYSQL Error – Server Quit Without Updating PID File – Quick fix!](https://bobcares.com/blog/mysql-error-server-quit-without-updating-pid-file/)
- [Can't open and lock privilege tables: Table 'mysql.user' doesn't exist](https://stackoverflow.com/questions/34516664/cant-open-and-lock-privilege-tables-table-mysql-user-doesnt-exist)
- [Can't open the mysql.plugin table. Please run mysql_upgrade to create it](https://stackoverflow.com/questions/41531225/cant-open-the-mysql-plugin-table-please-run-mysql-upgrade-to-create-it/41532987)
