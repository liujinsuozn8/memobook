- 内容来源于：
    - https://www.bilibili.com/video/av37383358
    - https://blog.csdn.net/weixin_34268843/article/details/93103820
    - https://blog.51cto.com/xuanwei/1844305

<span id="catalog"></span>

### 目录
- [测试内容](#测试内容)
- [通配符](#通配符)
- [Linux正则表达式](#linux正则表达式)
    - [基础正则](#基础正则)
    - [扩展正则](#扩展正则)
    - [posix方括号字符集](#posix方括号字符集)
    - [元字符](#元字符)


# 测试内容
[top](#catalog)
- 测试内容来源于：`https://doc.rust-lang.org/std/index.html#primitives`
- 测试文件：test.txt，并增加了一些空格和空行
```
array    A fixed-size array, denoted [T; N], for the element type, T, and the non-negative compile-time constant size, N.
bool    The boolean type.
char    A character type.
f32     The 32-bit floating point type.
f64     The 64-bit floating point type.
fn      Function pointers, like fn(usize) -> bool.
i8      The 8-bit signed integer type.
i16     The 16-bit signed integer type.
i32     The 32-bit signed integer type.
i64     The 64-bit signed integer type.
i128    The 128-bit signed integer type.
isize   The pointer-sized signed integer type.
pointer Raw, unsafe pointers, *const T, and *mut T.
reference       References, both shared and mutable.
        
slice   A dynamically-sized view into a contiguous sequence, [T]. Contiguous here means that elements are laid out so that every element is the same distance from its neighbors.
         
str     String slices.
tuple   A finite heterogeneous sequence, (T, U, ..).

u8      The 8-bit unsigned integer type.
u16     The 16-bit unsigned integer type.
u32     The 32-bit unsigned integer type.

u64     The 64-bit unsigned integer type.
u128    The 128-bit unsigned integer type.
unit    The () type, sometimes called "unit" or "nil".
usize   The pointer-sized unsigned integer type.
never   ExperimentalThe ! type, also called "never".
```

# 通配符
[top](#catalog)
- 通配符一般用于用户命令行bash环境，而linux正则表达式用于grep，sed，awk场景
- 常用通配符

    |通配符|含义|示例|
    |-|-|-|
    |`*`|代表任意个字符，0到多个|`ll *.txt`|
    |`?`|代表任意1个字符||
    |`;`|连续不同命令的分隔符|`pwd;pwd`|
    |`#`|配置文件注释||
    |`|`|管道(效率并不高)||
    |`~`|用户家目录||
    |`-`|上次使用的目录||
    |`$变量`|通过`$`显示变量的值||
    |`/`|任意路径分隔符、根目录||
    |`>`、`1>`|输出重定向，覆盖原有数据||
    |`>>`、`1>>`|输出重定向，追加到文件末尾||
    |`<`、`0<`|输入重定向<br/>如：xargs，tr||
    |`<<`、`0<<`|输入重定向，追加||
    |`''`|单引号，不具有变量置换功能，输出时所见即所得|`echo 'date'`，输出`date`<br/>`echo '$USER'`，输出`$USER`|
    |`""`|双引号，具有变量置换功能，解析变量后输出；不加引号相当于双引号||
    |\`\`|执行指令，等价于`$()`|
    |`{}`|中间为命令区块组合或内容序列||
    |`!`|取反||
    |`&&`|短路与，**前一个指令执行成功**时，执行后一个指令||
    |`||`|短路或，**前一个指令执行失败**时，执行后一个指令||
    |`.`|当前目录||
    |`..`|上级目录||

- `{}`内容序列的输出与使用
    - 正序输出
        - `echo test{1,2,3}`，输出：`test1 test2 test3`
        - `echo test{1..3}`，输出：`test1 test2 test3`
        - `echo test{b..d}`，输出：`testb testc testd`
    - 逆序输出
        - `echo test{3..1}`，输出：`test3 test2 test1`
        - `echo test{d..b}`，输出：`testd testc testb`
    - 同时创建多个目录
        - `mkdir /tmp/{001,002}/data -p`， **从根目录开始指定**
        - `mkdir ./{001,002}/data -p`， **从当前目录开始指定**

# Linux正则表达式
[top](#catalog)
- 正则表达式和通配符是有本质区别的
- Linux正则表达式一般配合grep、awk使用
- 注意事项
    - linux正则表达式一般以**行为单位处理**
    - 需要调整字符集:`export LC_ALL=C`，去除所有本地化的设置，让命令能正确执行
    - **在命令中使用正则表达式应该用`""`扩起来**

## 基础正则
[top](#catalog)

|功能|表达式|含义|示例|
|-|-|-|-|
|匹配开头结尾|`^`|`^word`，匹配以`word`开头的字符|`grep "^f" test.txt`，搜索以F开头的行|
|匹配开头结尾|`$`|`word$`，匹配以`word`结尾的字符|`grep "\.$" test.txt`，搜索以`.`结尾的行|
|匹配开头结尾|`^$`|表示空行|`grep "^$" test.txt`，搜索空行|
|字符匹配|`.`|表示**任意一个**字符|`grep -n "." test.txt`|
|字符匹配|`*`|`x*`，表示0个或多个字符`x`||
|字符匹配|`.*`|匹配所有字符||
|字符匹配|`^.*`|以任意多个字符开头||
|字符匹配|`.*$`|以任意多个字符结尾||
|转义|`\`|转义特殊字符|`grep "\.$" test.txt`，搜索以`.`结尾的行|
|设定字符集|`[abc]`|匹配字符集内的任意一个字符<br/>如：[a-zA-Z], [0-9]|`grep "[abc]" test.txt`<br/>`grep "[0-9]" test.txt`|
|设定字符集|`[^abc]`|匹配不包含字符集中任意一个字符的内容<br/>此处表示取反，与`^xxx`以`xxx`开头不同|`grep "[^abc]" test.txt`|
|设定重复次数|`a\{n,m\}`|重复n-m次`a`<br/>使用`egrep`、`grep -E`、`sed -r`时，可以去掉斜线<br/>逗号`,`前后**不能有空格**||
|设定重复次数|`a\{n,}`|至少重复n次`a`<br/>使用`egrep`、`grep -E`、`sed -r`时，可以去掉斜线<br/>逗号`,`前后**不能有空格**||
|设定重复次数|`a\{n}`|重复n次`a`<br/>使用`egrep`、`grep -E`、`sed -r`时，可以去掉斜线<br/>逗号`,`前后**不能有空格**||
|设定重复次数|`a\{,m\}`|?????||

## 扩展正则
[top](#catalog)
- 使用的命令是：`grep -E`、`egrep`

|功能|表达式|含义|示例|
|-|-|-|-|
|字符匹配|`+`|`x+`表示字符`x`重复1次及1次以上|`echo xcvx good sgofsdf rytr|egrep "go+"`|
|字符匹配|`?`|`x?`表示字符`x`重复0次或1次|`echo xcvx good sgofsdf rytr|egrep "go?"`|
|过滤|`|`|同时过滤多个字符串|`egrep 3306|1521 /etc/services`，只保留3306、1521的内容|
|过滤|`()`|分组过滤|`echo abc good sdf glad ewre dxv|egrep "g(la|oo)d"`<br/>`echo abc good sdf glad ewre goad god dxv|egrep "g(la|o.)d"`|
|引用|`()`|后向引用，使用`\n`来引用第`n`个`()`中的内容|`echo "09/Oct/2024:04:037:35"|sed -r 's@([0-9]{2})/([A-Z][a-z]{2})/([0-9]{4}):(.*)@\1 \2 \3 \4@g'`|

## posix方括号字符集
[top](#catalog)
|符号|含义|
|-|-|
|`[[:digit:]]`|数字字符，0-9|
|`[[:lower:]]`|小写字母字符，a-z|
|`[[:upper:]]`|大写字母字符，A-Z|
|`[[:alpha:]]`|大小写字母字符，A-Za-z|
|`[[:alnum:]]`|数字、字母字符，0-9A-Za-z|
|`[[:space:]]`|空白（whitespace）字符|
|`[[:blank:]]`|空格（space）与定位（tab）字符|
|`[[:graph:]]`|非空格（nospace）字符|
|`[[:punct:]]`|标点符号字符|
|`[[:print:]]`|可打印字符|
|`[[:cntrl:]]`|控制字符|
|`[[:xdigit:]]`|十六进制字符|

## 元字符
[top](#catalog)
|正则表达式|描述|示例|
|-|-|-|
|**\b**, `\<单词\>`|单词边界|\bcool\b 匹配cool，不匹配coolant|
|\B|非单词边界|cool\B 匹配coolant，不匹配cool|
|\d|单个数字字符|b\db 匹配b2b，不匹配bcb|
|\D|单个非数字字符|b\Db 匹配bcb，不匹配b2b|
|\w|单个单词字符（字母、数字与_）|\w 匹配1或a，不匹配&|
|\W|单个非单词字符|\W 匹配&，不匹配1或a|
|\n|换行符|\n 匹配一个新行|
|\s|单个空白字符|x\sx 匹配x x，不匹配xx|
|\S|单个非空白字符|x\S\x 匹配xkx，不匹配xx|
|\r|回车|\r 匹配回车|
|\t|横向制表符|\t 匹配一个横向制表符|
|\v|垂直制表符|\v 匹配一个垂直制表符|

[top](#catalog)