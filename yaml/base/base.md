<span id="catalog"></span>

- 参考
    - https://yaml.org/
    - js-yaml转换工具：http://nodeca.github.io/js-yaml/

### 目录
- [yaml简介](#yaml简介)
- [yaml的语法](#yaml的语法)
    - [基本语法](#基本语法)
    - [yaml映射](#yaml映射)
    - [yaml序列](#yaml序列)
    - [流样式的表示方式](#流样式的表示方式)
    - [yaml文档分割](#yaml文档分割)
    - [锚点与引用](#锚点与引用)
    - [换行与折叠](#换行与折叠)
    - [双引号与单引号](#双引号与单引号)
    - [类型标记](#类型标记)
- [](#)

# yaml简介
[top](#catalog)
- yaml是什么?
    - yaml不是标记语言，是数据序列化语言。与xml这种可扩展标记语言不同
    - yaml是一种简洁的、跨语言的、基于Unicode的数据序列化语言，可以与现代编程语言和好的配合

- yaml的优点
    - 易于查看与编写
    - yaml中的数据可在编程语言之间移植
    - 易于实现和使用
    - 具有统一的模式来支持文档生成工具

- yaml的缺点
    - 生成和解析比较复杂

- yaml是如何实现简洁性的？
    - 最小化结构特征的数量
    - 允许数据以自然而有意义的方式显示

- yaml的三种基本原语
    1. 映射：hash/字典
    2. 序列：arrays/listss
    3. 标量：字符串/数字 

- yaml**原语的分类**
    - 集合
        - 映射
        - 序列
    - 标量

- yaml表示数据结构的几种重要元素
    - yaml的三种原语 
    - 输入系统
    - 别名机制

- 虽然大多数编程语言都可以使用yaml进行序列化，但是yaml更擅长应对那些围绕三个基本原语构建的编程语言，**例如**：
    - Perl
    - Python
    - PHP
    - Ruby
    - Javascript

# yaml的语法
## 基本语法
[top](#catalog)
- 注释：以 `#` 开头，表示注释
- 空行会被解释被换行符
- 使用 两个空格 来表示层级关系
- 映射的表示方式
    ```yaml
    key: value
    ```
- 序列的表示方式
    ```yaml
    - node1
    - node2
    - node3
    ```

## yaml映射
[top](#catalog)
- 基本的使用方法：`key: value` ，表示一个键值对
    ```yaml
    hr:  65    # Home runs
    avg: 0.278 # Batting average
    rbi: 147   # Runs Batted In
    ```

- 复杂的key
    - 语法
        - 使用 `? key` 表示复杂的key

    - 示例
        ```yaml
        # key 是序列
        ? - Detroit Tigers
        - Chicago cubs
        :
        - 2001-07-23

        # key是流序列
        ? [ New York Yankees,
            Atlanta Braves ]
        : [ 2001-07-02, 2001-08-12,
            2001-08-14 ]
        ```

- `key: value` 对可以接在：`- `, `? `, `: `的后面 (:后面 ?????)
    - 示例
        ```yaml
        ---
        # Products purchased
        - item    : Super Hoop
        quantity: 1
        - item    : Basketball
        quantity: 4
        - item    : Big Shoes
        quantity: 1
        ```

## yaml序列
[top](#catalog)
- 序列中的一个项目：使用 `- scalar` 表示序列中的一个项目
    - 写法
        ```yaml
        - Mark McGwire
        - Sammy Sosa
        - Ken Griffey
        ```
- 一个序列映射到一个变量
    - 写法
        ```yaml
        变量名1: 
          - node0101
          - node0102
          - node0103
        变量名2: 
          - node0201
          - node0202
          - node0203
        ```
    - 相当于：
        ```js
        var 变量名1 = ["node0101", "node0102", "node0103"]
        var 变量名2 = ["node0201", "node0202", "node0203"]
        ```

- 多个映射对应序列的一个项目
    - 写法
        ```yaml
        -
          name: Mark McGwire
          hr:   65
          avg:  0.278
        -
          name: Sammy Sosa
          hr:   63
          avg:  0.288
        ```
    - 相当于
        ```js
        [ 
            {name: "Mark McGwire", hr: 65, avg: 0.278}
            {name: "Sammy Sosa", hr: 63, avg: 0.288}
        ]
        ```

## 流样式的表示方式
[top](#catalog)
- 流序列：在`方括号`内通过`逗号`来分割列表，
    - 写法
        ```yaml
        - [name        , hr, avg  ] # 列表的表头
        - [Mark McGwire, 65, 0.278] # 个字段对应列表的一项
        - [Sammy Sosa  , 63, 0.288]
        ```
    - 普通写法
        ```yaml
        -
          name: Mark McGwire
          hr:   65
          avg:  0.278
        -
          name: Sammy Sosa
          hr:   63
          avg:  0.288
        ```

- 流映射：在`大括号`内通过`逗号`来分割列表，
    - 写法
        ```yaml
        Mark McGwire: {hr: 65, avg: 0.278}
        Sammy Sosa: {hr: 63, avg: 0.288}
        ```
    - 对应的普通写法
        ```yaml
        Mark McGwire: 
          hr: 65
          avg: 0.278
        Sammy Sosa: 
          hr: 63
          avg: 0.288
        ```
    
- 流映射与流序列混合
    - 普通写法
        ```yaml
        -
          name: Mark McGwire
          hr:   65
          avg:  0.278
        -
          name: Sammy Sosa
          hr:   63
          avg:  0.288
        ```
    - 流样式的写法
        ```yaml
        [{name: Mark McGwire, hr: 65, avg:  0.278},{name: Sammy Sosa, hr: 63, avg: 0.288}]
        ```

## yaml文档分割
[top](#catalog)
- 语法
    - 两个分割标记
        - `---`，用于分割文档，或者表示文档的开头
        - `...`， 表示文档的结尾
    - 可以只使用 `---` 来分割文档
- 分割文档的用途
    - 通过文档分割，可以在一个yaml文件中编写多套配置
    - 可以在一个yaml中编写一个应用在不同环境中的配置

- 示例
    - 只使用 `---` 分割文件
        ```yaml
        # 第一份配置
        ---
        - Mark McGwire
        - Sammy Sosa
        - Ken Griffey

        # 第二份配置
        ---
        - Chicago Cubs
        - St Louis Cardinals
        ```
    - 同时使用 `---`、`...`分割文件
        ```yaml
        # 第一份配置
        ---
        time: 20:03:20
        player: Sammy Sosa
        action: strike (miss)
        ...

        # 第二份配置
        ---
        time: 20:03:47
        player: Sammy Sosa
        action: grand slam
        ...
        ```

## 锚点与引用
[top](#catalog)
- 通过锚点与引用可以**重复利用配置中的数据**
- 语法
    - `- &锚点名 数据内容`，创建锚点
    - `- *锚点名`，通过锚点名来引用数据

- 示例
    - 不使用锚点
        ```yaml
        ---
        hr: 
          - Mark McGwire
          - Sammy Sosa  # 出现重复数据
        rbi:
          - Sammy Sosa  # 出现重复数据
          - Ken Griffey
        ```
    - 使用锚点
        ```yaml
        ---
        hr:
          - Mark McGwire
            # 为重复数据创建锚点 相当于：var SS = Sammy Sosa
          - &SS Sammy Sosa
        rbi:
          - *SS # 通过锚点引用数据，相当于使用变量中的值
          - Ken Griffey
        ```

## 换行与折叠
[top](#catalog)
- 语法
    - `> `，符号后续的换行符会被解析为**空格**
        - 使用 `>` 后，可以使用空行表示一个有效的换行
    - `|`， 可以保留换行符

- 如果没有使用 `>` 或 `|`，yaml中的换行会默认被解析为空格

- 示例
    ```yaml
    name: Mark McGwire
    # 相当于：var accomplishment = "Mark set a major league home run record in 1998."
    accomplishment: >
      Mark set a major league
      home run record in 1998.
    
    # 相当于
    # var stats = "65 Home Runs
    # 0.278 Batting Average"
    stats: |
      65 Home Runs
      0.278 Batting Average
    ```

## 双引号与单引号
[top](#catalog)
- 双引号、单引号的区别
    - 双引号会自动**转译**字符串中的转译字符、unicode字符等
    - 单引号会保留字符串中的原始字符
    - 示例
        - yaml内容
            ```yaml
            temp01: "Sosa did fine.\u263A"
            temp02: 'Sosa did fine.\u263A'
            temp03: 'testName \n fxvfd'
            temp04: "testName \n fxvfd"
            ```
        - 输出结果
            ```
            Sosa did fine.☺

            Sosa did fine.\u263A

            testName \n fxvfd

            testName 
                fxvfd
            ```

- 单引区域中的单引号需要使用两个单引表示：`''`
    - yaml内容
        ```yaml
        temp01: ' # Not a ''comment''.'
        ```
    - 输出结果
        ```
        # Not a 'comment'.
        ```

- 双引区域中的双引需要转译：`\"`
    - yaml内容
        ```yaml
        temp01: "ABCD\"1234\"xyz"
        ```
    - 输出结果
        ```
        ABCD\"1234\"xyz
        ```

## 类型标记
[top](#catalog)
- 如果没有类型标记，会被应用程序自动赋予一种类型
- 不显示设置类型
    - 整型数据
        ```yaml
        canonical: 12345
        decimal: +12345
        octal: 0o14
        hexadecimal: 0xC
        ```

    - 浮点型数据
        ```yaml
        canonical: 1.23015e+3
        exponential: 12.3015e+02
        fixed: 1230.15
        negative infinity: -.inf
        not a number: .NaN
        ```
        
    - null型，可以使用 `~` 来表示null值
        ```yaml
        # 没有 具体的值，会被解析为null
        null:
        temp: ~
        ```

    - 时间戳
        ```yaml
        canonical: 2001-12-15T02:59:43.1Z
        iso8601: 2001-12-14t21:59:43.10-05:00
        spaced: 2001-12-14 21:59:43.10 -5
        date: 2002-12-14
        ```

- 使用内置类型来定义数据的类型
    - 内置类型

        |类型|表示方法|备注|
        |-|-|-|
        |整型|!!int|可使用10进制，2进制，8进制，16进制表示|
        |浮点型|!!float||
        |布尔型|!!bool|true、false，书写时忽略大小写|
        |字符串|!!str||
        |二进制|!!binary||
        |日期时间|!!timestamp|采用ISO8601格式|
        |空值|!!||
        |集合|!!set||
        |序列|!!seq||
        |键值对|!!map|键是唯一的|
        |键值对数组|!!omap||
        |对象列数组|!!pairs||

    - 示例
        ```yaml
        not-date: !!str 2002-04-28

        picture: !!binary |
        R0lGODlhDAAMAIQAAP//9/X
        17unp5WZmZgAAAOfn515eXv
        Pz7Y6OjuDg4J+fn5OTk6enp
        56enmleECcgggoBADs=

        --- !!set
        ? Mark McGwire
        ? Sammy Sosa
        ? Ken Griff

        --- !!omap
        - Mark McGwire: 65
        - Sammy Sosa: 63
        - Ken Griffy: 58
        ```




- 通过 `!自定义类型 数据` 来设置自定义类型
    - ?????

yaml信息模型 + 转换过程 + yaml文本果实
基本的yaml模式



[top](#catalog)
