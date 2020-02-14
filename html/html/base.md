<span id="catalog"></span>

- [网页开发的简介](#网页开发的简介)
- [HTML](#HTML)
    - [什么是HTML](#什么是HTML)
    - [HTML中的一些概念](#HTML中的一些概念)
        - [HTML整体结构](#HTML整体结构)
        - [HTML标签](#HTML标签)
        - [HTML实体(转义字符)](#HTML实体(转义字符))
- [<meta>元数据标签](#<meta>元数据标签)
- [语义标签](#语义标签)
    - [块元素与行内元素的介绍与区别]](#块元素与行内元素的介绍与区别)
    - [浏览器对不规范内容的自动调整](#浏览器对不规范内容的自动调整)
    - [行内元素](#行内元素)
    - [块元素](#块元素)
    - [布局标签-结构化语义标签](#布局标签-结构化语义标签)
- [](#)
- [](#)
- [](#)

# 网页开发的简介
[top](#catalog)
- 客户端的形式
    - 文字客户端
    - 图形化界面
        - 通过点击拖动等来使用软件
        - 包括:windows、macos、Andriod、ios中的大部分应用
        - 属于C/S架构
    - <label style="color:red">>网页</label>
        - 通过访问网页来使用软件，所有的网站都属于这个范畴
        - 属于B/S架构
- 网页的优点
    - 不需要安装
    - 无需更新
    - 跨平台，开发一次处处使用
- 网页中使用的语言
    - HTML
    - CSS
    - JavaScript
- **网页的编写需要遵守W3C规范**
- **网页的组成**
    - 根据W3C标准，一个网页主要有3部分组成：
        1. 结构：HTML用于描述页面的结构
        2. 表现：CSS用于控制页面中元素的样式
        3. 行为：JavaScript用于响应用户操作
    - ![??????]()

# HTML
## 什么是HTML
[top](#catalog)
- HTML （Hypertext Markup Language） 超文本标记语言
    - **超文本**指定是**超链接**，使用超链接可以从一个页面跳转到另一个页面
- 负责网页的结构
- HTML使用**标签**(**也可以成为元素**)的形式来识别网页中的不同组成部分

## HTML中的一些概念
### HTML整体结构
[top](#catalog)
- `<!doctype html>`，共浏览器识别（表示当前文本是一个html文件）,<label style="color:red">一定要在签名添加`!`</label>
- `<html>`，作为整个html的的根标签
    - `<head lang="当前文本的使用语言(en/zh/...)">`，html的头部信息，里面的内容不会显示到页面上，是浏览器和搜索引擎识别、分析使用的
        - `<meta charset="字符编码">`，设定网页的字符编码，避免乱码，虽然很多的浏览器已经可以自动识别，但是**建议添加该标签**
        - `<title>title内容</title>`，页面标题，会显示在页面标签上
    - `<body>`，页面上的显示内容都写在`body`中，(<label style="color:red">开发的主要内容</label>)

- 注释：`<!-- 注释内容 -->`
    - 可以写在任何地方
    - <label style="color:red">注释不可以嵌套，否则会出现识别问题。因为html是按照标签出现的顺序来解析的。</label>
        - 下面这种情况，外层的注释无法配对，最后会导致页面上出现一个`-->`
        ```html
        <!-- 
            commend test
            <!-- test2 -->
        -->
        ```

- 示例
    ```html
    <!doctype html>
    <html>
        <head>
            <title>页面标题</title>
        </head>
        <body>
            <h1>一级标题（会自动换行）</h1>
            <h2>二级标题（会自动换行）<h2>
            <h3>三级标题（会自动换行）<h3>
            <p>段落（会自动换行）<p>
        </body>
    </html>
    ```

### HTML标签
[top](#catalog)
- 标签的两种形式
    1. 成对标签：`<tag>...</tag>`
        - 有开始标签，则必须结束标签
    2. 自结束标签,如`img`和`input`。下面两种形式都可以，但是**建议自结束标签使用`<tag>`这种形式**
        - `<img>`或`<img/>`
        - `<input>`或`<input/>`
- 标签的属性以名值对的形式存在，<labe style="color:red">属性值必须用引号括起来</labe> **可以用双引号或单引号**
- 整体结构的标签，参考：[HTML的整体标签结构](#HTML的整体标签结构)

- `<font>`
    - 用于设置字符的显示格式，**可以添加在任何的成对标签中**
        - 如：
            ```html
            <h1>H1 <font color="red">test</font></h1>
            ```
    - 目前的开发中不建议使用`<font>`，因为该标签会打乱css对页面显示的控制

### HTML实体(转义字符)
[top](#catalog)
- 什么是实体？
    - 一些特殊符号，在html中，因为它们往往具有特殊含义，不能直接书写
    - 一些使用场景，如：
        - 字母两侧的`<`、`>`，会被浏览器和字母一起解析成标签
        - **多个空格，会被浏览器解析成一个空格**

    - 如果需要在html中书写这些特殊符号，则需要使用html中的实体(转义字符)

- 实体的语法： `&实体的名字;`

    |特殊字符|实体|
    |-|-|
    |空格|`&nbsp;`|
    |`>`|`&gt;`|
    |`<`|`&lt;`|
    |版权符号（圈C）|`&copy;`|
    ||`&;`|
    
- `<title>...</title>`
    - `title`中的内容会作为浏览器标签中的显示内容
        - [图](?????)
    - 使用搜索引擎时，`title`会作为搜索结构中超链接的显示内容
        - [图](?????)

# <meta>元数据标签
[top](#catalog)
- 主要用于设置网页的元数据，共浏览器和搜索引擎解析使用
- **几个主要用途**
    - 设定页面字符编码
    - 设置供搜索引擎使用的关键字和描述信息
- `<meta>`标签可以有多个，来设定不同的元数据
- 属性

    |属性名|含义|
    |-|-|
    |charset|指定网页的字符编码|
    |name|指定元数据名称|
    |content|指定元数据的内容|
    |http-equiv|可以模拟一个http响应头|

- `<meta charset="字符编码">`，**这一个元数据最好添加到html结构中，防止页面出现乱码**
- 常用的元数据name

    |可用name|示例|说明|备注|
    |-|-|-|-|
    |**keywords**|`<meta name="keywords" content="key1,key2,key3">`|表示当前网站的关键字|使用搜索引擎时搜索时，能够被搜索引擎识别<br>值可以有多个，使用`,`分割|
    |**description**|`<meta name="description" content="learn memo">`|当前网站的描述信息|使用搜索引擎时，描述信息会显示在搜索结构中的每个链接的下方|
    |author|`<meta name="author" content="xxxx">`|html文档的作者||
    ????
- http-equiv
    ????

# 语义标签
## 块元素与行内元素的介绍与区别
[top](#catalog)
- **块元素**：在页面中占一行的元素称为块元素(block element)
    - 在网页中一般通过块元素来**进行页面布局**
    - 块元素中什么内容都可以放，包括：块元素、行内元素、文字等等
        - `<p>`比较特殊，标签内部不能放块元素
- **行内元素/内联元素**：在页面中不会独占一行的元素
    - 行内元素主要**用来包裹文字**
    - 一般情况下会在块元素中添加行内元素，但是不会在行内元素中添加块元素

## 浏览器对不规范内容的自动调整
[top](#catalog)
- 浏览器解析html时，会自动对网页中不符合规范的内容进行修正（不修改源文件，只修改内存中的内容），包括
    1. 标签写在了根元素`<html>`的外部
    2. 在`<p>`中嵌套了块元素
    3. 根元素`<html>`中出现了`<head>``<body>`以外的子元素

- **情况1：**标签写在了根元素`<html>`的外部
    - 解析时会被包含到`<body></body>`的中
        - 写在`<html>`前的内容，按顺序包含在`<body></body>`的最前面
        - 写在`</html>`后的内容，按顺序包含在`<body></body>`的最后面

    - 示例
        - 错误的写法
            ```html
            <html>
                ...
            </html>

            <p>out html end test 01</p>
            <p>out html end test 02</p>
            ```
        - 浏览器解析之后的内容，根元素外部的`<p>`将会自动的添加到`<body>`中
            ```html
            <html>
                ...
                <body>
                    ...
                    <p>out html end test 01</p>
                    <p>out html end test 02</p>
                </body>
            </html>
            ```

- **情况2：**在`<p>`中嵌套了块元素
    - 由于`<p>`的特殊性，如果在`<p>`内部嵌套了块元素，浏览器会自动对该部分的元素进行调整，**为`<p>`和`</p>`重新配对并补全成两个`<p>...</p>`**

    - 浏览器的调整方法
        1. `<p>abcd...<第一个块元素标签>`，`<p>`的内容会被解析为`<p>abcd...</p>`
        2. 将内部的块元素、没有所属的标签文字取出，并顺次排列
        3. 将原本的`</最后一个元素标签>abcd....</p>`，`</p>`的内容被解析为`<p></p>`

    - 示例
        - 错误的写法
            ```html
            <p>
                StartText<blockquote>blockquoteStr1</blockquote>EndText
                formText<blockquote>blockquoteStr2</blockquote>toText
            </p>
            ```
        - 浏览器解析之后的内容
            ```html
            <p>
                        StartText</p>
            <blockquote>blockquoteStr1</blockquote>
            "EndText
                        formText"
            <blockquote>blockquoteStr2</blockquote>
            "toText
                    "
            <p></p>
            ```
        - ![图](?????)

- **情况3：**根元素`<html>`中出现了`<head>`、`<body>`以外的子元素
    - 解析时会被包含到`<body></body>`的中
    - 示例
        - 错误的写法
            ```html
            <html>
                <head>
                    ...
                </head>

                <body>
                    ...
                </body>
                
                <p>not head body</p>
            </html>
            ```
        - 浏览器解析之后的内容
            ```html
            <html>
                <head>
                    ...
                </head>

                <body>
                    ...
                    <p>not head body</p>
                </body>
            </html>
            ```

## 块元素
[top](#catalog)
- `<hn>` : 标题
    - 共6级标题，重要性逐渐减小(从SEO的角度看)
        - `<h1>...</h1>`，一级标题
        - `<h2>...</h2>`，一级标题
        - `<h3>...</h3>`，三级标题
        - `<h4>...</h4>`，四级标题
        - `<h5>...</h5>`，五级标题
        - `<h6>...</h6>`，六级标题
    - 一般情况下一个页面只有一个`<h1>`
    - 标签一般会使用`h1～h3`，很少用`h4～h6`


- `<hgroup>...</hgroup>` : 标题组
    - 将**多个关联**的标题放在一起
    - 示例
        ```html
        <hgroup>
            <h1>first</h1>
            <h2>second</h2>
        </hgroup>
        ```
- `<p>...</p>` : 段落
    - <label style="color:red">该标签中不能放任何块元素</label>

- `<br>` : 换行

- `<blockquote>` : 长引用
    - 引用的文字会在左右两边进行缩进
    
## 行内元素
[top](#catalog)
- `<em>` : 文字斜体

- `<strong>` : 文字加粗

- `<q>` : 短引用
    - 在文字两侧会使用：`"`、`「」`字符将文字括起来



## 布局标签-结构化语义标签
[top](#catalog)
- 块元素
    - `<div>...</div>`，没有实际的语义，类似于`<section>`，表示一个区块，是**开发时使用的主要布局元素**

    - h5新增的**几个不常用的标签**, 这些新增的标签实际使用的还是比较少
        - `<header>...</header>` : 网页的头部
            - 一个页面中可以有多个
        - `<main>...</main>` : 网页的主体
            - **一个页面中只有一个**
        - `<footer>...</footer>` : 网页的底部
        - `<nav>...</nav>` : 网页导航
        - `<aside>...</aside>` : 和主体相关的但不属于主题的内容,如侧边栏
        - `<article>...<article>` : 表示一个独立的文章
        - `<section>...</section>` : 表示一个独立的区块，使用其他的语义标签都不太合适时，都可以使用`<section>`

- 行内元素
    - `<span>...</span>`，没有任何语义，一般用于在网页中选中文字
        - html中如果有某些文字不属于任何标签，则可以使用`<span>`来包含

## 列表
[top](#catalog)
- 列表及其列表项都是**块元素**
- html中的三种列表
    1. `<ul>...</ul>`：有序列表
    2. `<ol>...</ol>`：无序列表
    3. `<dl>...</dl>`：定义列表

- `<ul>...</ul>`：无序列表
    - `<li>...</li>`：表示列表的每一项，即`list item`
    - 在无序列表中，每一个列表项前都会显示一个`.`
    - 示例
        - 结构
            ```html
            <ul> ul test
                <li>test01</li>
                <li>test02</li>
                <li>test03</li>
                <li>test04</li>
                <li>test05</li>
            </ul>
            ```
        - 页面显示
            - ![图](?????)

- `<ul>...</ul>`：有序列表
    - `<li>...</li>`：表示列表的每一项，即`list item`
    - 在有序列表中，每一个列表项前都会显示当前项的序号
    - 示例
        - 结构
            ```html
            <ol> ol test
                <li>test01</li>
                <li>test02</li>
                <li>test03</li>
                <li>test04</li>
                <li>test05</li>
            </ol>
            ```
        - 页面显示
            - ![图](?????)

- `<dl>...</dl>`：定义列表
    - `<dl>`的整体类似于一个多级列表
    - 类列表：`<dt>`+`<dd>`
        - `<dt>...</dt>`，表示定义的内容?????
            - 一个`<dt>`可以有多个`<dd>`，也可以不使用`<dd>`
        - `<dd>...</dd>`，对`<dt>`中定义的内容进行解释说明
    - 示例
        - 结构
            ```html
            <dl>
                <dt>dt01</dt>
                <dd>this is dt01's dd01</dd>
                <dd>this is dt01's dd02</dd>
                <dt>dt02</dt>
                <dd>this is dt02's dd</dd>
                <dt>dt03</dt>
                <dd>this is dt03's dd</dd>
                <dt>dt04</dt>
                <dt>dt05</dt>
            </dl>
            ```
        - 页面显示
            - [图](?????)


- 列表间的多级嵌套
    - 嵌套时，浏览器会自动缩进
    - 正确的嵌套方式
        - `<ul>`、`<ol>`、`<dl>`嵌套在列表项`<li>`中
    - 示例
        - 结构
            ```html
            <ul>
                level-0
                <li>level-0-1</li>
                <li>level-0-2</li>
                <li>
                    level-0-3
                    <!-- 嵌套1级列表  将ol嵌套到li中-->
                    <ol>
                        level-1
                        <li>level-1-1</li>
                        <li>level-1-2</li>
                        <li>level-1-3</li>
                        <li>
                            level-1-4
                            <!-- 嵌套2级列表  将dl嵌套到li中-->
                            <dl>
                                level-2
                                <dt>level-2-dt01</dt>
                                <dd>dd01 of level-2-dt01</dd>
                                <dd>dd02 of level-2-dt01</dd>
                                <dd>dd03 of level-2-dt01</dd>
                                <dt>level-2-dt02</dt>
                                <dd>dd01 of level-2-dt02</dd>
                                <dt>level-2-dt03</dt>
                            </dl>
                        </li>
                    </ol>
                    <span>xxxx</span>
                </li>
                <li>level-0-4</li>
                
                <!-- 将ul嵌套到ul中 -->
                <!-- <ul>
                    <li>llll</li>
                    <li>bbbbb</li>
                    <li>erewe</li>
                </ul> -->
            </ul>
            ```
        - 页面结果
            - ![图](?????)
