<span id="catalog"></span>

### 目录
- [less概述](#less概述)
- [开发设置](#开发设置)
- [在vscode开发less](#在vscode开发less)
- [less语法](#less语法)
    - [注释](#注释)
    - [选择器嵌套](#选择器嵌套)
    - [变量](#变量)
    - [引用](#引用)
    - [扩展与并集选择器](#扩展与并集选择器)
    - [混合函数mixins](#混合函数mixins)
    - [数值自动运算](#数值自动运算)
    - [导入其他less文件](#导入其他less文件)
- [](#)


# less概述
[top](#catalog)
- less是一门css的预处理语言
- 可以理解为css的增强版
    - 通过less可以编写更少的代码，实现更好的功能
- 在less中添加了许多新特性，包括对变量的支持，对mixin的支持等
- less的语法和css语法基本一直，但是less中添加了许多对css的扩展
- 浏览器无法直接执行less，必须先将less转换为css
- 文件后缀：`*.less`

# 开发设置
[top](#catalog)
- 在vscode中，需要安装`easy less`
- 设置 `easy less` 的开发配置
    1. settings
    2. extensions
    3. easy less configuration
    4. edit in setting.json
    5. 在 `setting.json` 文件中添加配置
        ```json
        "less.compile": {
            "compress": true, // 生成的css是否进行压缩
            "sourceMap": true, //是否显示less代码与生成的css代码的对应关系
            "out":false //是否生成css文件
        }
        ```
# less语法
## 注释
[top](#catalog)
- 单行注释，不会进入解析后的css
    ```less
    // 单行注释，
    ```
- 多行注释，会进入解析后的css
    ```less
    /* 
        多行注释
    */
    ```

## 选择器嵌套
[top](#catalog)
- 祖先选择器可以通过嵌套的方式编写
    - css
        ```css
        body {
            width: 100px;
            height: 100px;
        }
        body div {
            color: #bfa;
        }

        ```
    - less
        ```less
        body{
            width: 100px;
            height: 100px;
            
            div {
                color:#bfa;
            }    
        }
        ```

- 父子关系选择器在嵌套时需要 附加`>`
    ```less
    父选择器{
        > 子选择器 {
            ...
        }
    }
    ```

- `&`表示当前选择器自身
    - css
        ```css
        .box1:hover {
           background-color: #bfa;
        }
        div .box1 {
            width: 200px;
        }
        .box1-wrapper {
            height: 200px;
        }
        ```
    - less
        ```less
        .box1{
            // 在前面使用
            &:hover{
                background-color: #bfa;
            }
            
            // 在后面使用
            div &{
                width: 200px;
            }
            
            // 直接作为连接字符使用
            &-wrapper{
                height: 200px;
            }
        }
        ```

## 变量
[top](#catalog)
- 声明变量
    - 在全局声明
        ```less
        @变量名: 变量值;
        ```
    - 在选择器内部声明
        ```less
        选择器{
            @变量名: 变量值;
        }
        ```
- 使用变量
    - **如果变量重名，则通过就近原则使用变量**
    - 变量作为属性值
        ```less
        选择器{
            属性名: @变量名
        }   
        ```
    - 变量作为类名、标签名、函数参数使用
        ```less
        .@{变量名}{
            ...
        }
        
        @{变量名}{
            ...
        }

        选择器{
            background-image: url(@{变量名})
        }
        ```

## 引用
[top](#catalog)
- 引用其他属性的属性值
    - 语法
        ```less
        选择器{
            属性1:属性值;
            属性2:$属性名 //引用属性名对应的属性值
        }
        ```
    - 示例
        - less
            ```less
            .box{
                width: 200px;
                height: $width;
            }
            ```
        - css
            ```css
            .box {
                width: 200px;
                height: 200px;
            }
            ```
- 引用其他选择器中的所有属性
    - 语法
        ```less
        选择器1{...}
        选择器2{
            选择器1();
        }
        ```
    - 示例
        - less
             ```less
            .p1{
                width: 200px;
                height: 200px;
            }

            .p3{
                .p1();
                color:red; 
            }
            ```
        - css
            ```css
            .p1 {
                width: 200px;
                height: 200px;
            }
            .p3 {
                width: 200px;
                height: 200px;
                color: red;
            }
            ```

## 扩展与并集选择器
[top](#catalog)
- 扩展类似于继承，会被解析为并集选择器
    - 语法
        ```less
        选择器1:{
            ...
        }

        // :extend 之间不能有空格 
        选择器2:extend(选择器1){
            ...
        }

        // 或者
        选择器2{
            &:extend(选择器1)
            ...
        }

        ```
    - less
        ```less
        .p1{
            width: 200px;
            height: 200px;
        }

        .p2:extend(.p1){
            color:red;
        }

        .p4{
            &:extend(.p1);
            color:#bfa
        }
        ```
    - css
        ```css
        .p1,
        .p2,
        .p4 {
            width: 200px;
            height: 200px;
        }

        .p2 {
            color: red;
        }

        .p4 {
           color: #bfa;
        }
        ```

## 混合函数mixins
[top](#catalog)
- 创建无参的混合函数
    - 语法
        ```less
        // 创建引用内容，该内容不会被写入解析后的css
        函数名(){
            ...
        }

        // 引用
        选择器{
            函数名();
            ...
        }
        ```
    - 示例
        - less
            ```less
            .block(){
                width: 200px;
                height: 200px;
            }
            .box02{
                .block();
                color:red;
            }
            ```
        - css
            ```css
            .box02 {
                width: 200px;
                height: 200px;
                color: red;
            }
            ```

- 创建有参的混合函数
    - 语法
        ```less
        // 创建引用内容，该内容不会被写入解析后的css
        函数名(@参数1, @参数2,...){
            属性1:@参数1;
            属性2:@参数2;
        }

        // 引用
        选择器{
            函数名(参数值1, 参数值2, ...);
            ...
        }
        ```
    - 示例
        - less
            ```less
            .test(@w, @h){
                width: @w;
                height: @h;
            }

            .box03{
                .test(200px, 300px);
                color:red;
            }
            ```
        - css
            ```css
            .box03 {
                width: 200px;
                height: 300px;
                color: red;
            }
            ```

## 数值自动运算
[top](#catalog)
- 在less中数值可以自动运算
- 运算符两侧
    - 运算符两侧必须同时有空格，或者同时没有空格
    - 如果只有一边有空格，则无法解析
- 运算数中的单位
    - 如果有一个数带有单位，则结果会包含单位
    - 如果所有运算数都不包含单位，则结果也不包含单位
    - 最终结果的单位与第一个包含单位的运算数的单位相同
- 示例
    - less
        ```less
        .box04{
            width: 100px - 40px;
            height: 100px - 50;
            margin: 100px-30;
            padding: 100-20px;
            line-height: 100em - 10px;
            
            // 无法解析的属性值
            font-size: 100px -20px;
        }
        ```
    - css
        ```css
        .box04 {
            width: 60px;
            height: 50px;
            margin: 70px;
            padding: 80px;
            line-height: 90em;
            font-size: 100px -20px;
        }
        ```

## 导入其他less文件
[top](#catalog)
- 通过导入功能，可以对css进行模块化管理
- 语法
    ```less
    @import "less文件路径"
    ```

[top](#catalog)