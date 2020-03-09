- 参考
    - https://www.bilibili.com/video/av77217003

<span id="catalog"></span>

### 目录
- [CSS简介](#CSS简介)
- [CSS的使用方式](#CSS的使用方式)
    - [内联样式](#内联样式)
    - [内部css](#内部css)
    - [外部css](#外部css)
- [css语法](#css语法)
- [常用CSS选择器](#常用CSS选择器)
    - [基本选择器](#基本选择器)
    - [复合选择器](#复合选择器)
    - [关系选择器](#关系选择器)
    - [属性选择器](#属性选择器)
    - [伪类选择器](#伪类选择器)
    - [伪元素选择器](#伪元素选择器)
- [选择器的权重](#选择器的权重)
- [继承](#继承)
- [web页面中的单位](#web页面中的单位)
    - [页面的长度单位](#页面的长度单位)
    - [颜色单位](#颜色单位)
- [文档流](#文档流)
- [盒子模型](#盒子模型)
    - [盒子模型的基本组成](#盒子模型的基本组成)
    - [内容区content](#内容区content)
    - [边框border](#边框border)
    - [内边距padding](#内边距padding)
    - [外边距margin](#外边距margin)
    - [盒子模型中可以设置为auto的属性](#盒子模型中可以设置为auto的属性)
    - [块元素盒子模型的水平方向布局](#块元素盒子模型的水平方向布局)
    - [盒子模型的垂直方向布局](#盒子模型的垂直方向布局)
    - [垂直外边距的折叠](#垂直外边距的折叠)
    - [行内元素的盒子模型](#行内元素的盒子模型)
    - [盒子的大小](#盒子的大小)
    - [盒子的轮廓](#盒子的轮廓)
    - [盒子的阴影](#盒子的阴影)
    - [盒子的圆角](#盒子的圆角)
- [浏览器的默认样式](#浏览器的默认样式)
- [](#)
- [](#)
- [](#)
- [](#)
- [常用的通用属性](#常用的通用属性)
    - [display](#display)
    - [visibility](#visibility)
- [总结](#总结)
- [练习](#练习)


# CSS简介
[top](#catalog)
- 层叠样式表
- 网页实际上是一个多层的结构，通过CSS可以分别为网页的每一个层来设置样式，最终看到的只是网页最上边的一层

# CSS的使用方式
## 内联样式
[top](#catalog)
- 使用方法：
    - 在标签内部通过`style`属性来设置元素的样式
    - 通过`样式名:样式值`的方式设置样式
    - 可以同时设置多个样式，使用`;`来分割
    ```html
    <tag style="样式1:value1; 样式2:value2; 样式3:value3;">
    ```

- 示例
    - 参考：[/frontend/css/base/src/usage/usage.html](/frontend/css/base/src/usage/usage.html)
    - 代码
        ```html
        <p style="color:red; font-size: 20px;">
            p tab css test
        </p>
        ```
- 在实际开发中不推荐使用
    - 维护困难
    - css和html耦合在一起

## 内部css
[top](#catalog)
- 使用方法
    - 将css写到`<head>`中的`<style>`标签中
    - 通过css选择器为元素设置样式，可以同时设置多种元素的样式
    ```html
    <head>
        <style>
            选择器{
                样式1:value1;
                样式2:value2;
                样式3:value3;
            }
            
        </style>  
    </head>
    ```
- **内联样式可以覆盖内部css**
- 内部css不能跨页面使用
- 示例
    - 代码：[/frontend/css/base/src/usage/usage.html](/frontend/css/base/src/usage/usage.html)
      
        ```html
        <head>
            <style>
                p{
                    color:green; 
                    font-size: 20px;
                }
            </style>
        </head>
        <body>
            <p style="color:red; font-size: 20px;">
                p tab css test
            </p>

            <p>p tab common test01</p>
            <p>p tab common test02</p>
            <p>p tab common test03</p>
        </body>
        ```
    - 结果：
        - 按照设置显示，但是第一个`<p>`被覆盖了
        - ![page_inner_css_test_result](imgs/usage/page_inner_css_test_result.png)
        
## 外部css
[top](#catalog)
- 使用方法：   
    1. 需要将css样式写到一个外部`*.css`文件中
    2. 通过`<head>`下的`<link>`来引入外部的css文件，引入之后css会自动生效

    ```html
    <head>
        <link rel="stylesheet" href="*.css文件路径">
    </head>
    ```
- **内联样式可以覆盖内部css**
- 外部css和内部css同时存在时的原则：**后加载的优先**
    - 如果外部css和内部css中有相同的css选择器，则后声明的覆盖先声明的
- 优点：
    1. 使用外部css可以在不同页面之间进行复用
    2. 将css写到外部文件中，可以使用浏览器的缓存机制，从而加快页面的加载速度
        - 第一次某个`*.css`文件被加载过之后，会保存到浏览器缓存中，之后再次需要时，直接从缓存中获取
- 示例
    - css文件：[/frontend/css/base/src/usage/usage.css](/frontend/css/base/src/usage.css)

        ```css
        p{
            color: blueviolet;
            font-size: 30px;
        }
        ```

    - 页面：[/frontend/css/base/src/usage/usage.html](/frontend/css/base/src/usage/usage.html)

        ```html
        <!doctype html>
        <html>
            <head>
                <link rel="stylesheet" href="mystyle.css">
            </head>
            <body>
                <!-- 覆盖外部css -->
                <p style="color:red; font-size: 20px;">
                    p tab css test
                </p>

                <p>p tab common test01</p>
                <p>p tab common test02</p>
                <p>p tab common test03</p>
            </body>
        </html>
        ```
    - 页面结果
        - 按照设置显示，但是第一个`<p>`被覆盖了
        - ![out_css_test_result](imgs/usage/out_css_test_result.png)

# css语法
[top](#catalog)
- 注释：`/* .... */`
- css的基本语法：`选择器{ 声明块 }`
- 选择器
    - 通过选择器可以选择页面中的指定元素
        - 如：`p{....}`，表示选择页面中所有的p元素

- 声明块
    - 通过声明块指定需要为元素设置的样式
    - 声明块有多个声明组成
    - 声明是一个名值对结构，`样式名:样式值;`

- **多个css中如果出现的重复的部分，则使用后声明的部分**

# 常用CSS选择器
## 基本选择器
[top](#catalog)
- 4中基本选择器
    1. 元素选择器
    2. id选择器
    3. class选择器
    4. 通配选择器

- 元素选择器
    - 根据标签名来选择指定元素
    - 语法：`标签名{ 声明块 }`

- id选择器
    - 根据元素的id属性值选中元素
    - 语法：`#id属性值{ 声明块 }`
    - id选择器的缺点，只能设置一个元素

- class选择器
    - 根据标签的class属性来选择(一组)元素，**开发中比较常用**
    - 语法：`.class值{ 声明块 }`
    - class可以重复使用
    - **可以为一个元素指定多个class，多个class之间通过空格分割**

- 通配选择器
    - 选中页面中的**所有元素**
    - 语法：`*`

- 示例
    - 代码：[/frontend/css/base/src/selector/base.html](/frontend/css/base/src/selector/base.html)
    - css
        ```css
        /* 4.通配选择器 */
        * {
            /* 斜体字符 */
            font-style: italic; 
        }

        /* 1.元素选择器*/
        p{
            color:green; 
            font-size: 20px;
        }

        /* 2.id选择器 */
        #redPTag{
            color: red;
        }

        /* 3.class选择器 */
        .blue{
            color:blue;
            /* 斜体字符 */
            font-style: normal;
        }

        .font30{
            font-size: 30px;
        }
        ```
    - html
        ```html
        <body>
            <!-- 2.id选择器测试 -->
            <p id='redPTag'>p 01 test</p>

            <!-- 3.class选择器测试 -->
            <p class='blue'>p 02 test</p>
            
            <!-- 3.多个class选择器 测试 -->
            <p class='blue font30'>p 03 test</p>
            
            <!-- 1.元素选择器 -->
            <p>p 04 test</p>
            <p>p 05 test</p>
        </body>
        ```
    - 页面结果
        - 由于先设定了通配符选择器，所以`p01`、`p04`、`p05`是斜体；在后边的`.blue`选择器中，又重新设定了字体格式，覆盖了前面的样式，所以`p02`、`p03`是正常显示
        - ![base_selector_html_result](imgs/selector/base_selector_html_result.png)

## 复合选择器
[top](#catalog)
- 两种复合选择器
    1. 交集选择器
    2. 并集选择器

- 交集选择器
    - 选中同时包含多个选择器的元素
    - 语法：`选择器1选择器2选择器3{ 声明块 }`
        - 选择器中**如果有元素选择器，则必须以元素选择器开头**
- 并集选择器 (选择器分组)
    - 同时选择多种选择器对应的元素
    - 语法：`选择器1, 选择器2, 选择器3{ 声明块 }`

- 并集选择器中混合交集选择器
    - 如：
        ```css
        h2.complex, h3.complex, h4.complex {
            color:coral;
            font-style: normal;
        }
        ```

- 示例
    - 参考：[/frontend/css/base/src/selector/complex.html](/frontend/css/base/src/selector/complex.html)
    - css 
        ```css
        /* 1.交集选择器 */
        /* 条件：使用blue类，并且是div */
        div.blue{
            /* 加粗字符 */
            font-style: italic;
            font-size: 30px;
        }

        /* 2.并集选择器 */
        h1, span{
            color: green;
        }

        /* 3.并集选择器中混合交集选择器 */
        h2.complex, h3.complex, h4.complex {
            color:coral;
            font-style: normal;
        }
        ```
    - html
        ```html
        <body>
            <!-- 1.交集选择器测试 -->
            <div class="blue">div 01 test</div>
            <p class="blue">p 01 test</p>

            <!-- 2.并集选择器测试 -->
            <H1> h1 test</H1>
            <span>span 01 test</span>

            <!-- 3.并集选择器中混合交集选择器测试 -->
            <h2 class="complex">h2 complex test</h2>
            <h3 class="complex">h3 complex test</h3>
            <h4 class="complex">h4 complex test</h4>
        </body>
        ```
    - 页面结果
        - ![complex_selector_html_result](imgs/selector/complex_selector_html_result.png)

## 关系选择器
[top](#catalog)
- 三种关系选择器
    1. 子元素选择器
    2. 后代选择器
    3. 兄弟选择器

- 子元素选择器
    - 语法：
        - 1级：`选择器1 > 子元素 {声明块}`
        - 多级：`选择器1 > 选择器2 > ... > 子元素 {声明块}`
    - 子元素选择器可以可复合选择器进行混合
    - 示例
        - 参考：[/frontend/css/base/src/selector/relation_son.html](/frontend/css/base/src/selector/relation_son.html)
        - css
            ```css
            /* 1. 子元素选择器 */
            /* 1.1 子元素选择器：设置 div 下 span 的样式 */
            div > span {
                color: orange;
            }

            /* 1.2 子元素选择器混合复合选择器：设置 class是red的div 下 span 的样式 */
            div.red > span {
                color: red;
                font-style: italic;
                font-size: 30px;
            }

            /* 1.3 多级子元素选择器：设置 div 下 p 下 span 的样式 */
            div > p > span  {
                color: green;
                font-size: 30px;
            }
            ```
        - html
            ```html
            <body>
                <section>0. 不符合任何选择器</section>
                <span>out span</span>

                <section>1.1 子元素选择器测试</section>
                <div>
                    <span>div - span1 </span>
                </div>
                <br>

                <section>1.2 子元素选择器混合复合选择器</section>
                <div class="red">
                    <span>div - span2 </span>
                </div>
                <br>

                <section>1.3 多级子元素选择器</section>
                <div>
                    <p>
                        div - p - 3
                        <span>div - p - span - 3 </span>
                    </p>
                </div>
                <br>

            </body>
            ```
        - 页面结果
            - ![relation_son_selector_html_result](imgs/selector/relation_son_selector_html_result.png)

- 后代选择器
    - 语法：`祖先元素选择器 后代元素选择器{声明块}`
    - 使用后，**所有的后代元素**都会应用样式，无论元素之间有多少嵌套关系
    - 子元素选择器可以可复合选择器进行混合
    - 示例
        - 参考：[/frontend/css/base/src/selector/relation_later.html](/frontend/css/base/src/selector/relation_later.html)
        - css
            ```css
            /* 2. 后代选择器 */
            /* 选择 div下的所有p后代元素 */
            div p{
                color: mediumblue;
                font-weight: bold;
            }
            ```
        - html
            ```html
            <body>
                <section>0. 不符合任何选择器</section>

                <section>2. 后代选择器</section>
                <div>
                    <div>
                        <p>div - p - 4 - 1 </p>
                    </div>
                    <p>div - p - 4 - 2</p>
                </div>
                <br>
            </body>
            ```
        - 页面结果
            - ![relation_later_selector_html_result.png](imgs/selector/relation_later_selector_html_result.png)


- 兄弟选择器
    - 语法
        - 选择`兄弟元素1选择器`**后边紧挨的第一个兄弟元素**：`兄弟元素1选择器 + 兄弟元素2选择器{ 声明块 }`
            - 兄弟元素1和兄弟元素2之间必须紧挨，如果出现其他元素则无法选择
        - 选择`兄弟元素1选择器`**后边的所有兄弟元素**：`兄弟元素1选择器 ~ 兄弟元素2选择器{ 声明块 }`
            - 兄弟元素之间可以有其他元素，不会影响选择结果
    - **兄弟选择器只能选择`兄弟元素1`后面的元素，无法选择前面的元素**
    - 可以混合复合选择器
    - 示例
        - 参考：[/frontend/css/base/src/selector/relation_brother.html](/frontend/css/base/src/selector/relation_brother.html)
        - css
            ```css
            /* 3. 兄弟选择器 */
            /* 3.1 选择 兄弟元素1选择器 后边紧挨的第一个兄弟元素 */
            /* 选择p后面的第一个span */
            p + span{
                color:mediumpurple;
                text-decoration:underline;
                font-size: 20px;
            }

            /* 3.2 选择 兄弟元素1选择器 后边的所有兄弟元素 */
            /* 选择 p 后边所有的 ul 和 ol 兄弟元素 */
            p ~ ul,ol{
                color:red;
                text-decoration:underline;
                font-size: 20px;
            }
            ```
        - html
            ```html
            <body>
                <section>0. 不符合任何选择器</section>
                
                <section>3. 兄弟选择器</section>
                <section>3.1-1 选择 兄弟元素1选择器后边紧挨的第一个兄弟元素，选择p后面的第一个span</section>
                <p>p1</p>
                <span>p1 - span1</span><br>
                <span>p1 - span2</span><br>
                <br>

                <section>3.1-2 兄弟元素1与兄弟元素2之间有其他元素，选择p后面的第一个span</section>
                <p>p2</p>
                <div>p2 - div</div>
                <span>p2 - span1</span><br>
                <span>p2 - span2</span><br>
                <br>

                <section>3.1-2 兄弟元素1与兄弟元素2之间有其他元素，选择 p 后边所有的 ul 和 ol 兄弟元素 </section>
                <p>p2</p>
                <div></div>
                <ul>
                    <li>p2-list1-item1</li>
                    <li>p2-list1-item2</li>
                </ul>
                <div></div>
                <ol>
                    <li>p2-list2-item1</li>
                    <li>p2-list2-item2</li>
                </ul>
                <ul>
                    <li>p2-list3-item1</li>
                    <li>p2-list3-item2</li>
                </ul>
                <br>

            </body>
            ```
        - 页面结果
            - ![relation_brother_selector_html_result](imgs/selector/relation_brother_selector_html_result.png)

## 属性选择器
[top](#catalog)
- 语法
    1. `元素[属性名] {声明块}`， 选择含有指定属性的元素
    2. `元素[属性名=属性值] {声明块}`， 选择含有指定属性、指定属性值的元素
    3. `元素[属性名^=属性值] {声明块}`， 选择含有指定属性、以指定属性值**开头**的元素
    4. `元素[属性名$=属性值] {声明块}`， 选择含有指定属性、以指定属性值**结尾**的元素
    5. `元素[属性名*=属性值] {声明块}`， 选择含有指定属性、**包含**指定属性值的元素

- `属性值`是否需要加引号
    - 可以加引号也可以不加
    - 单引号、双引号都可以
    - <label style="color:red">如果属性值是数字，则必须加引号，否则选择无法选中元素</labe>
- 示例
    - 代码：[/frontend/css/base/src/selector/property.html](/frontend/css/base/src/selector/property.html)
    - css
        ```css
        /* 1. 选择含有指定属性的元素 */
        p[title] {
            color: green;
        }

        /* 2. 选择含有指定属性、属性值=abc的元素 */
        p[title="abc"] {
            color:red;
        }
        
        /* 3. 选择含有指定属性、属性值以 1234 开头的元素 */
        p[title^='1234']{
            color:blue;
        }

        /* 4. 选择含有指定属性、属性值以 xyz 结尾的元素 */
        p[title$="xyz"]{
            font-size: 20px;
            color: rebeccapurple;
        }

        /* 5. 选择含有指定属性、属性值中包含 qwe 元素 */
        p[title*="qwe"]{
            font-style: italic;
            color: orange;
        }
        ```
    
    - html
        ```html
        <body>
            <section>0. 没有title属性，无法被任何选择器选择</section>
            <p>no title test</p>

            <section>1. 选择含有指定属性的元素</section>
            <p title="qqqq">title=qqqq test</p>
            <p title="zzzz">title=zzzz test</p>

            <section>2. 选择含有指定属性、属性值=abc的元素</section>
            <p title="abc">title=abc test</p>
            
            <section>3. 选择含有指定属性、属性值以 1234 开头的元素</section>
            <p title="1234">title=1234 test</p>
            <p title="12346789">title=12346789 test</p>
            
            <section>4. 选择含有指定属性、属性值以 xyz 结尾的元素</section>
            <p title="xyz">title=xyz test</p>
            <p title="zxcvbxyz">title=zxcvbxyz test</p>
            <p title="1234xyz">title=1234xyz test</p>
            
            <section>5. 选择含有指定属性、属性值中包含 qwe 元素</section>
            <p title="qwe">title=qwe test</p>
            <p title="asdfqwerty">title=asdfqwerty test</p>
        </body>
        ```
    
    - 页面结果
        - ![property_selector_html_result](imgs/selector/property_selector_html_result.png)

## 伪类选择器
[top](#catalog)
- 什么是伪类
    - 不存在的类，是特殊的类
- 伪类的用途
    - 用来描述一个**元素的特殊状态**。如：第一个子元素，被点击的元素，鼠标移入的元素
- 伪类的语法，`其他选择器:伪类名`
- 伪类

    |伪类|描述|备注|
    |-|-|-|
    |`:first-child`|所有子元素中的第一个子元素|将对所有的子元素进行排序,如果多个元素中间有不符合规则的元素，将会影响显示结果|
    |`:last-child`|所有子元素中的最后一个子元素|将对所有的子元素进行排序,如果多个元素中间有不符合规则的元素，将会影响显示结果|
    |`:nth-child(index)`|所有子元素中的第n个子元素|将对所有的子元素进行排序,如果多个元素中间有不符合规则的元素，将会影响显示结果<br>index从0开始<br>`index=n`时，选中所有子元素，表示从1到正无穷<br>`index=2n`、`index=even`时，表示选择偶数位置的子元素<br>`index=2n+1`、`index=odd`时，表示选择奇数数位置的子元素|
    |`:first-of-type`|同类元素中的第一个子元素|仅对同类元素进行排序,如果多个元素中间有不符合规则的元素，则忽略这些元素|
    |`:last-of-type`|同类元素中的最后一个子元素|仅对同类元素进行排序,如果多个元素中间有不符合规则的元素，则忽略这些元素|
    |`:nth-of-type(index)`|同类元素中的第n个子元素|仅对同类元素进行排序,如果多个元素中间有不符合规则的元素，则忽略这些元素<br>index从0开始<br>`index=n`时，选中所有子元素，表示从1到正无穷<br>`index=2n`、`index=even`时，表示选择偶数位置的子元素<br>`index=2n+1`、`index=odd`时，表示选择奇数数位置的子元素|
    |`:not(选择器|伪类)`|否定伪类，将符合条件的元素从选择器中删除|如果使用`属性选择器`，只写`[属性名=属性值]`部分即可|

- 示例
    - 参考代码：[/frontend/css/base/src/selector/pseudo_class.html](/frontend/css/base/src/selector/pseudo_class.html)

    - 对所有元素排序
        1. 选择 ul下的第一个和最后一个li子元素（与并集选择器混合）
            - css
                ```css
                ul.test1 > li:first-child {
                    color:blue;
                }

                ul.test1 > li:last-child {
                    color:orange;
                }
                ```

            - html
                ```html
                <section>
                    1. 选择 ul下的第一个和最后一个li子元素
                    <br>
                    2. 选择 ul下的第3个li子元素
                </section>
                <ul class="test1">
                    <li>ul1 item 01</li>
                    <li>ul1 item 02</li>
                    <li>ul1 item 03</li>
                    <li>ul1 item 04</li>
                    <li>ul1 item 05</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_01.png](imgs/selector/pseudoClass/html_result_01.png)
        2. 选择 ul下的第3个li子元素
            - css
                ```css
                ul.test1 > li:nth-child(3){
                    color:red
                }
                ```

            - html
                ```html
                <section>
                    1. 选择 ul下的第一个和最后一个li子元素
                    <br>
                    2. 选择 ul下的第3个li子元素
                </section>
                <ul class="test1">
                    <li>ul1 item 01</li>
                    <li>ul1 item 02</li>
                    <li>ul1 item 03</li>
                    <li>ul1 item 04</li>
                    <li>ul1 item 05</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_02](imgs/selector/pseudoClass/html_result_02.png)
        3. 选择 ol下的所有li子元素
            - css
                ```css
                ol.test1 > li:nth-child(n){
                    color:green
                }
                ```

            - html
                ```html
                <section>3. 选择 ol下的所有li子元素</section>
                <ol class="test1">
                    <li>ol1 item 01</li>
                    <li>ol1 item 02</li>
                    <li>ol1 item 03</li>
                    <li>ol1 item 04</li>
                    <li>ol1 item 05</li>
                    <li>ol1 item 06</li>
                </ol>
                ```
            
            - 页面结果
                - ![html_result_03](imgs/selector/pseudoClass/html_result_03.png)

        4. 选择ul下的所有偶数li子元素，通过2n的方式
            - css
                ```css
                ul.test2 > li:nth-child(2n){
                    color:hotpink;
                }
                ```

            - html
                ```html
                <section>4. 选择ul下的所有偶数li子元素，通过2n的方式</section>
                <ul class="test2">
                    <li>ul.test2 01 item01</li>
                    <li>ul.test2 01 item02</li>
                    <li>ul.test2 01 item03</li>
                    <li>ul.test2 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_04](imgs/selector/pseudoClass/html_result_04.png)
        5. 选择ul下的所有偶数li子元素，通过even的方式
            - css
                ```css
                ul.test3 > li:nth-child(even){
                    color:hotpink;
                }
                ```

            - html
                ```html
                <section>5. 选择ul下的所有偶数li子元素，通过even的方式</section>
                <ul class="test3">
                    <li>ul.test3 01 item01</li>
                    <li>ul.test3 01 item02</li>
                    <li>ul.test3 01 item03</li>
                    <li>ul.test3 01 item04</li>
                </ul>
                ```
            - 页面结果
                - ![html_result_05](imgs/selector/pseudoClass/html_result_05.png)
        
        6. 选择ul下的所有奇数li子元素，通过2n+1的方式
            - css
                ```css
                ul.test4 > li:nth-child(2n+1){
                    font-size: 20px;
                }
                ```

            - html
                ```html
                <section>6. 选择ul下的所有奇数li子元素，通过2n+1的方式</section>
                <ul class="test4">
                    <li>ul.test4 01 item01</li>
                    <li>ul.test4 01 item02</li>
                    <li>ul.test4 01 item03</li>
                    <li>ul.test4 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_06](imgs/selector/pseudoClass/html_result_06.png)
        
        7. 选择ul下的所有奇数li子元素，通过odd的方式
            - css
                ```css
                ul.test5 > li:nth-child(odd){
                    font-size: 20px;
                }
                ```

            - html
                ```html
                <section>7. 选择ul下的所有奇数li子元素，通过odd的方式</section>
                <ul class="test5">
                    <li>ul.test5 01 item01</li>
                    <li>ul.test5 01 item02</li>
                    <li>ul.test5 01 item03</li>
                    <li>ul.test5 01 item04</li>
                </ul>
                ```
            - 页面结果
                - ![html_result_07](imgs/selector/pseudoClass/html_result_07.png)


    - 只对同类元素排序
        1. 选择ul下的所有li元素中的第一个和最后一个li子元素
            - css
                ```css
                ul.test6 > li:first-of-type{
                    color:red;
                }

                ul.test6 > li:last-of-type{
                    color:blue;
                }
                ```

            - html
                ```html
                <section>8. 选择ul下的所有li元素中的第一个li子元素</section>
                <section>9. 选择ul下的所有li元素中的最后一个li子元素</section>
                <ul class="test6">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test6 01 item01</li>
                    <li>ul.test6 01 item02</li>
                    <span>span03</span>
                    <li>ul.test6 01 item03</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_08_09](imgs/selector/pseudoClass/html_result_08_09.png)
        
        2. 选择ul下的所有的li元素，忽略其他元素
            - css
                ```css
                ul.test7 > li:nth-of-type(n){
                    color:green;
                }
                ```

            - html
                ```html
                <section>10. 选择ul下的所有的li元素，忽略其他元素</section>
                <ul class="test7">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test7 01 item01</li>
                    <li>ul.test7 01 item02</li>
                    <span>span03</span>
                    <li>ul.test7 01 item03</li>
                    <li>ul.test7 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_10](imgs/selector/pseudoClass/html_result_10.png)
        
        3. 选择ul下的所有的偶数li元素，忽略其他元素，使用2n方式
            - css
                ```css
                ul.test8 > li:nth-of-type(2n){
                    color:hotpink;
                }
                ```

            - html
                ```html
                <section>11. 选择ul下的所有的偶数li元素，忽略其他元素</section>
                <ul class="test8">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test8 01 item01</li>
                    <li>ul.test8 01 item02</li>
                    <span>span03</span>
                    <li>ul.test8 01 item03</li>
                    <li>ul.test8 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_11](imgs/selector/pseudoClass/html_result_11.png)
        
        4. 选择ul下的所有的偶数li元素，忽略其他元素，使用event方式
            - css
                ```css
                ul.test9 > li:nth-of-type(even){
                    color:hotpink;
                }
                ```

            - html
                ```html
                section>12. 选择ul下的所有的偶数li元素，忽略其他元素，使用odd方式</section>
                <ul class="test9">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test9 01 item01</li>
                    <li>ul.test9 01 item02</li>
                    <span>span03</span>
                    <li>ul.test9 01 item03</li>
                    <li>ul.test9 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_12](imgs/selector/pseudoClass/html_result_12.png)
        
        5. 选择ul下的所有的奇数li元素，忽略其他元素，使用2n+1方式
            - css
                ```css
                ul.test10 > li:nth-of-type(2n+1){
                    font-size: 20px;
                }
                ```

            - html
                ```html
                <section>13. 选择ul下的所有的奇数li元素，忽略其他元素，使用2n+1方式</section>
                <ul class="test10">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test10 01 item01</li>
                    <li>ul.test10 01 item02</li>
                    <span>span03</span>
                    <li>ul.test10 01 item03</li>
                    <li>ul.test10 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_13](imgs/selector/pseudoClass/html_result_13.png)
        
        6. 选择ul下的所有的奇数li元素，忽略其他元素，使用odd方式
            - css
                ```css
                ul.test11 > li:nth-of-type(odd){
                    font-size: 20px;
                }
                ```

            - html
                ```html
                <section>14. 选择ul下的所有的奇数li元素，忽略其他元素，使用odd方式</section>
                <ul class="test11">
                    <span>span01</span>
                    <span>span02</span>
                    <li>ul.test11 01 item01</li>
                    <li>ul.test11 01 item02</li>
                    <span>span03</span>
                    <li>ul.test11 01 item03</li>
                    <li>ul.test11 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_14](imgs/selector/pseudoClass/html_result_14.png)
    
    - 否定伪类
        - 否定伪类，将title属性是 abcd 的li去除

            - css
                ```css
                ul.test12 > li:not([title="abcd"]){
                    color: red;
                }
                ```

            - html
                ```html
                <section>15. 否定伪类，将title属性是 abcd的li去除</section>
                <ul class="test12">
                    <li>ul.test12 01 item01</li>
                    <li title="abcd">ul.test12 01 item02 :title="abcd"</li>
                    <li>ul.test12 01 item03</li>
                    <li>ul.test12 01 item04</li>
                </ul>
                ```
            
            - 页面结果
                - ![html_result_15](imgs/selector/pseudoClass/html_result_15.png)


## 伪元素选择器
[top](#catalog)
- 伪元素表示页面中一些特殊的但不存在的元素或特殊位置
- 伪元素的语法, `其他选择器::伪元素名`
- 常用伪元素
    
    |伪元素|描述|备注|
    |-|-|-|
    |`::first-letter`|表示第一个字母||
    |`::first-line`|表示第一行||
    |`::selection`|表示选中的内容||
    |`::before`|表示元素的开始|1.该元素的位置处于开始标签后，标签内容之前<br>如：`<tag>|文字`，假设`|`不真实存在，`|`就是`::before`的位置<br><br>2. `before`必须结合`content`属性来使用，所有的样式都是在设置`content`中的内容<br><br>3. 如果使用了`content`属性，在控制台可以看到该元素；如果不使用`content`属性则无法看到|
    |`::after`|表示元素的最后|1.该元素的位置处于标签内容之后，结束标签之前<br>如：`文字|<tag>`，假设`|`不真实存在，`|`就是`::after`的位置<br><br>2. `after`必须结合`content`属性来使用，所有的样式都是在设置`content`中的内容<br><br>3. 如果使用了`content`属性，在控制台可以看到该元素；如果不使用`content`属性则无法看到|

- 在短引用标签`<q>`中，页面中自动出现的`""`或`[]`，就是通过在css中设置`::before`和`::after`来实现的

- 示例
    - 参考代码:[/frontend/css/base/src/selector/pseudo_element.html](/frontend/css/base/src/selector/pseudo_element.html)
    - css
        ```css
        /* 1. 设置所有p标签的第一个字符的大小 */
        p::first-letter{
            font-size: 20px;
        }

        /* 2. 设置所有p标签的第一行的颜色 */
        p::first-line{
            color: green;
        }

        /* 3. 设置p标签中，文字被选中时的颜色 */
        p::selection{
            background:orange;
        }

        /* 4. 在div的内容之前和之后添加文字及样式 */
        div.test01::before{
            content: "be";
            color:red;
        }

        div.test01::after{
            content:"af";
            color: green;
        }
        
        /* 5. 为div中的文字添加书名号 */
        div.test02::before{
            content:"【"
        }
        div.test02::after{
            content:"】"
        }

        /* 6. 测试::before 和 ::after 中不是用content的效果（没有任何效果） */
        div.test03::before{
            color:green;
        }
        div.test03::after{
            color:green;
        }
        ```
    - html
        ```html
        <section>
            1. 设置所有p标签的第一个字符的大小
            <br>
            2. 设置所有p标签的第一行的颜色
            <br>
            3. 设置p标签中，文字被选中时的颜色
        </section>
        <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Cumque cupiditate voluptates libero. Tempora porro id libero laboriosam, reiciendis modi rem beatae itaque facilis provident rerum numquam, aperiam, fugiat quod quae!</p>
        <br>

        <section>4. 在div的内容之前和之后添加文字及样式</section>
        <div class="test01">div test01</div>
        <br>

        <section>5. 为div中的文字添加书名号</section>
        <div class="test02">div test02</div>
        <br>

        <section>6. 测试::before 和 ::after 中不是用content的效果(没有任何效果)</section>
        <div class="test03">div test03</div>
        ```
    - 页面结果
        - ![html_result_01](imgs/selector/pseudoElement/html_result_01.png)

    - 控制台中的页面元素
        - ![html_result_02](imgs/selector/pseudoElement/html_result_02.png)


# 选择器的权重
[top](#catalog)
- 样式冲突
    - 发生样式冲突的条件：<label style="color:red">不同的选择器，选中相同的元素，为相同的样式设置不同的值</label>
- 发生样式冲突时，由选择器的权重来决定。如果权重相同，则使用后声明的选择器中的样式（可以理解为后声明的内容覆盖了先声明的内容）

- 选择器类型及其对应的权重
    |选择器|权重|
    |-|-|
    |内联样式|`1,0,0,0`|
    |id|`0,1,0,0`|
    |类和伪类选择器|`0,0,1,0`|
    |元素选择器|`0,0,0,1`|
    |通配符选择器|`0,0,0,0`|
    |从祖先元素继承的样式|没有权重|

- 复合选择器的权重计算方式
    - 对于交集选择器，它的权重=所有的选择器的权重相加
        - 选择器的累加不会超过其最大的数量级。如有n个类选择器，无论n多大，权重求和的结果也不会超过id选择器，即数量级无法从`10`跨越到`100`

    - 对于并集选择器，每个选择器单独计算，各算各的

- 强制最大权重：`!important`
    - 可以在<label style="color:red">样式的后面</label>添加`!important`。该样式将会获得最高的权重，比内联样式还高
    - 语法
        ```css
        选择器{
            样式名:样式值 !important;
        }
        ```

- 示例
    - 参考代码：[/frontend/css/base/src/selector/selector_level.html](/frontend/css/base/src/selector/selector_level.html)
    
    - 权重大小测试 id > 类选择 > 元素选择器
        - css
            ```css
            #box1{
                color:blue;
            }
            div{
                color:blueviolet;
            }

            .red{
                color:red;
            }
            ```
        - html
            ```html
            <section>1.1 测试基本的权重大小</section>
            <div id="box1" class="red">div id=box1 class=red</div>
            <div class="red">div class=red</div>
            <div>only div</div>
            <section>1.2. 测试内联样式</section>
            <div id="box1" style="color:green">div with inner css</div>
            ```

        - ![html_result_01](imgs/selector/selectorLevel/html_result_01.png)

    - 测试交集选择器的权重
        - css
            ```css
            div.aa.bb.cc.dd.ee.ff.gg.hh.ii.jj{
                color:orange;
            }

            #box2{
                color: green;
            }
            ```
        - html
            ```html
            <section>2. 测试交集选择器的权重相加</section>
            <div class='aa bb cc dd ee ff gg hh ii jj'>
                div class='aa bb cc dd ee ff gg hh ii jj'
                <br>
                权重【10个class+div】=101 > id的100，但实际上没有跨越数量级
            </div>
            <div id="box2" class='aa bb cc dd ee ff gg hh ii jj'>div id="box2" class='aa bb cc dd ee ff gg hh ii jj'</div>
            <br>
            ```

        - ![html_result_02](imgs/selector/selectorLevel/html_result_02.png)

    - 测试 `!important`
        - css
            ```css
            .test01{
                color:indigo !important;
            }
            ```
        - html
            ```html
            <section>4. 测试!important</section>
            <div class="test01" style="color:orange">div class="test01" style="color:orange"</div>
            <div style="color:orange">div style="color:orange"</div>
            ```
        - ![html_result_04](imgs/selector/selectorLevel/html_result_04.png)

    - 测试权重相同的选择器
        - css
            ```css
            .test0201{
                color:orange;
            }

            .test0202{
                color:green;
            }
            ```

        - html
            ```html
            <section>5. 测试权重相同的选择器</section>
            <div class="test0201">div class="test0201"</div>
            <div class="test0202">div class="test0202"</div>
            <div class="test0201 test0202">div class="test0201 test0202"</div>
            <br>
            ```
        - ![html_result_05](imgs/selector/selectorLevel/html_result_05.png)

    - 测试通配符选择器和继承样式的权重
        - css
            ```css
            /* 设置 div.test03 下的所有后代元素的样式 */
            div.test03 *{
                color:green;
            }
            
            /* 仅设置 div.test03的样式 */
            div.test03 {
                color:red;
            }
            ```
        - html
            ```html
            <section>6. 测试通配符选择器和继承样式的权重</section>
            <div class="test03">
                div class="test03"，
                <br>
                只有这里是红色，其他的子元素因为通配选择器的样式，都是绿色
                <br>
                <span>div - span</span>
                <br>
                <div>div - div<p>div - div - p </p>
                </div>
            </div>
            ```
        - ![html_result_06](imgs/selector/selectorLevel/html_result_06.png)

# 继承
[top](#catalog)
- 样式的继承
    - 为一个元素设置的样式同时也会应用到它的**后代元素**
    - 继承是发生在祖先元素和后代元素上的
- 继承的目的
    - 为了方便开发，利用继承可以将一些通用的样式设置到祖先元素中，只设置一次，就可以使所有后代元素都能获取到该样式

- 不会自动继承的样式
    - 背景相关的样式，如背景色、背景图片
    - 布局相关等样式

- 示例
    - 参考代码[/frontend/css/base/src/inherit/inherit.html](/frontend/css/base/src/inherit/inherit.html)
    - css
        ```css
        p{
            color: blue;

        }

        div{
            color:green;
        }
        ```
    - html
        ```html
        <section>继承测试--只设置祖先元素的样式，后代元素也能获取到该样式</section>
        <p>
            p element
            <span>span inner p</span>
        </p>

        <span>span outer p</span>

        <div>
            div element
            <span>
                span inner div ：level1
                <em>em inner div:level2</em>
            </span>

        </div>
        ```

    - ![html_result_01](imgs/inherit/html_result_01.png)

# web页面中的单位
## 页面的长度单位
[top](#catalog)
- 像素
    - 单位：`px`
    - 不同屏幕的像素大小是不同的，像素越小的屏幕显示的效果越清晰
        - 相同的像素值在不同分辨率设备下的显示效果不同
- 百分比
    - 百分比会自动将属性值设置为相对其父元素属性的百分比
    - 设置百分比可以使子元素跟随父元素的改变而改变
    - 如果设置的是`div`元素，并且`div`元素**没有文字内容和父元素**，页面上将会没有任何显示
- em
    - `1em = 1font-size`
    - em 是相对于自身字体大小而计算的，即`font-size`是当前元素默认的或适用了某个样式的字体大小
    - em将会根据字体大小的改变而改变
    - 使用场景
        - ??????

- rem
    - rem是相对于根元素`<html>`的字体大小而计算的

- 示例
    - 参考代码：[/frontend/css/base/src/sizeunit/sizeunit.html](/frontend/css/base/src/sizeunit/sizeunit.html)
    - 百分比自动变化测试
        - css
            ```css
            .box1{
                width: 200px;
                height: 200px;
                background-color: green;
            }
            
            .box2{
                width:50%;
                height:50%;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>1.1. 百分比自动变化测试(包含父元素)</section>
            <div class="box1">
                <div class="box2"></div>
            </div>
            <br>

            <section>1.2. 百分比自动变化测试(不包含父元素，直接使用)</section>
            <div class="box2">dddd</div>
            <br>
            ```
        - ![html_result_01](imgs/sizeunit/sizeunit/html_result_01.png)

    - em测试: 分别使用不同的文字大小
        - css
            ```css
            .box3{
                font-size:5px;
                width:10em;
                height: 10em;
                background-color: green;
            }

            .box4{
                font-size:10px;
                width:10em;
                height: 10em;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>2. em测试: 分别使用不同的文字大小</section>
            <div class="box3"></div>
            <div class="box4"></div>
            ```
        - ![html_result_02](imgs/sizeunit/sizeunit/html_result_02.png)
    
    - 3. rem测试 
        - css
            ```css
            html{
                font-size: 15px;
            }

            .box5{
                width:10rem;
                height:10rem;
                background-color:green;
            }

            .box6{
                width:15rem;
                height:15rem;
                background-color:green;
            }
            ```
        - html
            ```html
            <section>3. rem测试</section>
            <div class="box5"></div>
            <div class="box6"></div>
            ```
        - ![html_result_03](imgs/sizeunit/sizeunit/html_result_03.png)

## 颜色单位
[top](#catalog)
- 在css中可以使用颜色名来设置颜色，如：red、green、yellow等，但是不方便使用
- RGB值
    - RGB通过三种颜色的不同浓度来调配出不同的颜色
    - R-red，G-green，B-blue
    - 每一种颜色的范围是：`[0, 255]`
        - 值越大，对应的颜色越深
    - 颜色的范围也可以使用百分数：`[0%, 100%]`
    - 语法:`RGB(红色值, 绿色值, 蓝色值)`
    - `RGB(0,0,0)`是黑色，`RGB(255,255,255)`是白色

- 16进制RGB值
    - 语法：`#红色绿色蓝色`
    - 颜色的范围：`[00, ff]`
    - 如果RGB都是重复的两位数则可以简写，如`#aabbcc`可以简写为`#abc`

- RGBA值
    - 在RGB的基础上添加了一个A，即不透明度
    - R、G、B的范围参照RGB
    - A的范围：`[0, 1]`
        - `1`是不透明，`0`是完全透明
    - 语法：`RGBA(R, G, B, A)`

- HSL值、HSLA值
    - 表示的含义
        - H，色相，范围：`[0, 360]`
        - S，饱和度，表示颜色的浓度，范围`[0%, 100%]`，必须使用百分数
        - L，亮度，颜色的亮度，范围`[0%, 100%]`，必须使用百分数
        - A，表示透明度

- 示例
    - 参考代码：[/frontend/css/base/src/sizeunit/color.html](/frontend/css/base/src/sizeunit/color.html)
    - 颜色名测试
        - css
            ```css
            .box1{
                width: 200px;
                height: 200px;
                background-color: green;
            }
            ```
        - html
            ```html
            <section>1. 颜色名测试</section>
            <div class="box1"></div>
            ```
        - ![html_result_01](imgs/sizeunit/color/html_result_01.png)

    - RGB颜色值测试 
        - css
            ```css
            .box2{
                width: 200px;
                height: 200px;
                background-color: rgb(50,100,200)
            }
            ```
        - html
            ```html
            <section>2.RGB颜色值测试</section>
            <div class="box2"></div>
            ```
        - ![html_result_02](imgs/sizeunit/color/html_result_02.png)

    - RGB颜色百分比测试
        - css
            ```css
            .box3{
                width: 200px;
                height: 200px;
                background-color: rgb(10%,20%,30%)
            }
            ```
        - html
            ```html
            <section>3.RGB颜色百分比测试</section>
            <div class="box3"></div>
            ```
        - ![html_result_03](imgs/sizeunit/color/html_result_03.png)

    - 4. 16进制RGB值 测试
        - css
            ```css
            .box4{
                width:200px;
                height:200px;
                background-color: #aabbcc;
            }
            ```
        - html
            ```html
            <section>4. 16进制RGB值 测试</section>
            <div class="box4"></div>
            ```
        - ![html_result_04](imgs/sizeunit/color/html_result_04.png)


    - RGBA测试
        - css
            ```css
            .box5{
                width: 200px;
                height:200px;
                background-color: rgba(100, 100, 100, 0.5)
            }
            .box6{
                width: 200px;
                height:200px;
                background-color: rgba(100, 100, 100, 1)
            }
            ```
        - html
            ```html
            <section>5. RGBA测试</section>
            <div class="box5"></div>
            <div class="box6"></div>
            ```
        - ![html_result_05](imgs/sizeunit/color/html_result_05.png)

    - HSL值 测试
        - css
            ```css
            .box7{
                width: 200px;
                height:200px;
                background-color: hsl(20, 50%, 70%)
            }
            ```
        - html
            ```html
            <section>6. HSL值 测试</section>
            <div class="box7"></div>
            ```
        - ![html_result_06](imgs/sizeunit/color/html_result_06.png)

# 文档流
[top](#catalog)
- 文档流：normal flow
- 什么是文档流：网页是一个多层次的结构，通过css可以分别为每一层设置样式，但是作为用户只能看到最上面的一层，最下面的一层被称为文档流
- <label style="color:red">文档流是网页的基础，所有元素默认都是在文档流中进行排列</label>
- 元素相对于文档流的两种状态
    - 在文档流中
    - 不再文档流中，即脱离文档流
        - 使用[浮动](#浮动)属性后，元素将脱离文档流
- 元素**在文档流中**的特点
    - 块元素
        - 块元素在页面内独占一行
            - 参考：[为什么块元素独占一行](#reasonofblockelementuseline)
        - 多个块元素在页面内从上到下垂直排列
        - 默认宽度是父元素的的宽度，会将父元素撑满
        - 默认高度是元素内容的高度，即子元素
        - 如`<div></div>`元素，没有元素内容时，不会在页面中显示;有内容时，默认宽度与页面块度相同
    - 行内元素
        - 行内元素不会独占页面的一行，只占自身的大小
        - 多个行内元素在页面中从左到右水平排列，如果一行无法容纳多个行内元素，则自动切换到第二行显示
        - **行内元素的宽和高默认都是该元素内容的宽和高**

# 盒子模型
## 盒子模型的基本组成
[top](#catalog)
- box model
- 为了方便页面布局，css将页面中的每个元素都设置为一个矩形盒子。将元素设置为矩形盒子后，页面的布局就变成了如何安排这些盒子的位置

- 盒子模型的示例
    - ![boxSample](imgs/boxModel/base/boxSample.png)

- 盒子的组成
    - 组成元素

        |名称|含义|描述|
        |-|-|-|
        |`context`|内容区||
        |`border`|边框||
        |`padding`|内边距|表示是内容区与边框的内容|
        |`margin`|外边距|当两个盒子放在一起时，两个盒子的间距|
    
- <label style="color:red">盒子的可见大小 = `width/height` + `border`×2 + `padding`×2</label>
    - 计算方式与属性设置的关系：[盒子的大小](#盒子的大小)

## 内容区content
[top](#catalog)
- 元素中的所有**子元素和文本内容**都在内容区中排列
- 内容区的大小由`width`、`height`属性来设置
- <label style="color:red">width的默认值为auto。如果不写，会由浏览器自动计算</labe>

## 边框border
[top](#catalog)
- 边框至少要设置三个属性
    1. 边框的宽度：`border-width`
        - 默认值为`3px`，所以不指定该属性也能显示边框
    2. 边框的颜色：`border-color`
        - 默认值为黑色，所以不指定该属性也能显示边框
    3. 边框的样式：`border-style`
        - `style`的默认值为`none`，即表示没有边框。所以**如果要显示边框，该属性必须写**
        - 如实线solid、虚线dashed、点线dotted等等

- 对于设置4个边的统一属性，如：`border-width`、`border-color`、`border-style`，参数值有如下的规定
    - 1个值：上下左右
    - 2个值：上下 左右
    - 3个值：上 左右 下
    - 4个值：上 右 下 左
            
- 单边的各种属性。使用时不方便，所以使用的比较少
    - 上:`border-top-xxx`
    - 下:`border-bottom-xxx`
    - 左:`border-left-xxx`
    - 右:`border-right-xxx`

- 简写属性（**最常用的方法**）
    - 语法：
        - 统一设置4个边框的属性：`border: color width style`
        - 分别设置4个边框的属性
            - `border-top: color width style`
            - `border-bottom: color width style`
            - `border-left: color width style`
            - `border-right: color width style`
    - 该属性可以同时设置边框的所有相关样式，并且**没有顺序的要求**

- 通过设置统一属性和单边属性来控制边框的整体的样式，同时可以还可以对某个边框作微调
    - 如，用统一属性设置边框整体样式，通过单边属性来隐藏某个边框
        ```css
        border: color width style;
        border-top: none;
        ```

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/border.html](/frontend/css/base/src/boxModel/border.html)
    - 通过border-width设置边框的宽度
        - css
            ```css
            /* 2. 通过border-width设置边框的宽度 */
            /* 2.1. 1个参数设置边框宽度，统一设置4个边框的宽度*/
            .box2{
                /* 内容区设置 */
                /* 设置内容区的宽 */
                width: 200px; 
                /* 设置内容区的高 */
                height:200px;
                background-color: rgb(124, 200, 61);
                
                /* 边框设置 */
                border-width: 5px;
                border-color: orange;
                border-style: dashed;
            }

            /* 2.2. 2个参数设置边框宽度，分别设置：上下、左右 */
            .box3{
                width: 200px;
                height:200px;
                background-color: rgb(124, 200, 61);

                /* 设置边框 */
                border-width: 10px 20px;
                border-color: orange;
                border-style: solid;
            }
            
            /* 2.3. 3个参数设置边框宽度，分别设置：上、左右、下 */
            .box4{
                width: 200px;
                height:200px;
                background-color:  rgb(124, 200, 61);

                /* 设置边框 */
                border-width: 10px 20px 30px;
                border-color: orange;
                border-style: solid;
            }
            
            /* 2.4. 4个参数设置边框宽度，分别设置：上、右、下、左 */
            .box5{
                width: 200px;
                height:200px;
                background-color:  rgb(124, 200, 61);

                /* 设置边框 */
                border-width: 10px 20px 30px 40px;
                border-color: orange;
                border-style: solid;
            }
            ```
        - html
            ```html
            <section>2. 通过border-width设置边框的宽度</section>
            <section>2.1. 1个参数设置边框宽度</section>
            <div class="box2"></div>
            <section>2.2. 2个参数设置边框宽度，分别设置：上下、左右</section>
            <div class="box3"></div>
            <section>2.3. 3个参数设置边框宽度，分别设置：上、左右、下</section>
            <div class="box4"></div>
            <section>4个参数设置边框宽度，分别设置：上、右、下、左</section>
            <div class="box5"></div>
            ```
        - 页面结果
            - ![html_result_02_01](imgs/boxModel/border/html_result_02_01.png)
            - ![html_result_02_02](imgs/boxModel/border/html_result_02_02.png)

    - 通过4个边的宽度属性来设置边框
        - css
            ```css
            /* 3. 通过4个边的宽度属性来设置边框 */
            /* 3.1. 分别设置4个边框的属性 */
            .box6{
                width:200px;
                height:200px;
                background-color: rgb(83, 150, 218);

                /* 设置边框 */
                /* 分别设置4个边框 */
                /* 上 */
                border-top-width: 10px;
                border-top-color: orange;
                border-top-style: dashed;
                /* 下 */
                border-bottom-width: 20px;
                border-bottom-color: green;
                border-bottom-style: solid;
                /* 左 */
                border-left-width:40px;
                border-left-color: blueviolet;
                border-left-style: double;
                /* 右 */
                border-right-width: 80px;
                border-right-color: rgb(194, 154, 21);
                border-right-style: groove;
            }

            /* 3.2. 只设置3个边框 */
            .box7{
                width:200px;
                height:200px;
                background-color: rgb(83, 150, 218);

                /* 设置边框 */
                /* 分别设置4个边框 */
                /* 上 */
                border-top-width: 10px;
                border-top-color: orange;
                border-top-style: dashed;
                /* 下 */
                border-bottom-width: 20px;
                border-bottom-color: green;
                border-bottom-style: solid;
                /* 左 */
                border-left-width:40px;
                border-left-color: blueviolet;
                border-left-style: double;
                /* 右 */
            }
            ```
        - html
            ```html
            <section>3. 通过4个边的属性分别设置边框</section>
            <section>3.1. 分别设置4个边框的属性</section>
            <div class="box6"></div>
            <section>3.2. 只设置3个边框</section>
            <div class="box7"></div>
            ```
        - 页面结果
            - ![html_result_03](imgs/boxModel/border/html_result_03.png)

    - 简写属性测试
        - css
            ```css
            /* 4. 简写属性测试*/
            /* 4.1. 通过简写属性同时设置4个边框 */
            .box8{
                width:200px;
                height:200px;
                background-color: rgb(83, 150, 218);

                /* 通过简写属性同时设置4个边框 */
                border:rgb(124, 200, 61) 20px solid;
            }

            /* 4.2. 分别设置4个边框的属性 */
            .box9{
                width:200px;
                height:200px;
                background-color: rgb(83, 150, 218);

                /* 通过简写属性同时设置4个边框 */
                border-top:rgb(124, 200, 61) 10px solid;
                border-bottom:rgb(245, 109, 172) 20px dashed;
                border-left:rgb(200, 174, 61) 30px double;
                border-right:rgb(75, 178, 219) 40px dotted;
            }
            ```
        - html
            ```html
            <section>4. 简写属性测试</section>
            <section>4.1. 通过简写属性同时设置4个边框</section>
            <div class="box8"></div>
            <section>4.2. 分别设置4个边框的属性</section>
            <div class="box9"></div>
            ```
        - 页面结果
            - ![html_result_04](imgs/boxModel/border/html_result_04.png)

    - 统一属性和单边属性的配合
        - css
            ```css
            /* 5. 统一属性和单边属性的配合 */
            .box10{
                width:200px;
                height:200px;
                background-color: coral;

                /* 使用统一属性设置边框的整体样式 */
                border:green 20px solid;
                /* 使用单边属性来隐藏某个边 */
                border-right:none;
            }
            ```
        - html
            ```html
            <section>5. 统一属性和单边属性的配合</section>
            <div class="box10"></div>
            ```
        - 页面结果
            - ![html_result_05](imgs/boxModel/border/html_result_05.png)

## 内边距padding
[top](#catalog)
- 内容区和边框的距离
- 内边距的颜色默认与内容区相同
- 内边距的4个单边属性，分别是:
    - padding-top
    - padding-bottom
    - padding-left
    - padding-right
- 内边距的简写属性：
    - `padding:上 右 下 左`
    - 内边距属性值的设置方式与内容区的设置方式相同
        - 1个值：上下左右
        - 2个值：上下 左右
        - 3个值：上 左右 下
        - 4个值：上 右 下 左        

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/padding.html](/frontend/css/base/src/boxModel/padding.html)
      
    - 通过单边属性分别设置4个方向的内边距
        - css
            ```css
            .box1{
                /* 设置内容区 */
                width: 200px;
                height:200px;
                background-color: green;

                /* 设置边框 */
                border:orange 20px solid;

                /* 设置内边距 */
                padding-top: 10px;
                padding-bottom: 20px;
                padding-left: 30px;
                padding-right: 40px;
            }

            /* 通过在div内部嵌套class=inner的块元素来显示内边距 */
            .inner{
                width:100%;
                height: 100%;
                background-color: rgb(56, 150, 212);
            }
            ```
        - html
            ```html
            <section>1. 通过单边属性分别设置4个方向的内边距</section>
            <div class="box1">
                <div class="inner"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_01](imgs/boxModel/padding/html_result_01.png)
    
    - 通过简写属性统一设置内边距
        - css
            ```css
            .box2{
                /* 设置内容区 */
                width: 200px;
                height:200px;
                background-color: green;

                /* 设置边框 */
                border:orange 20px solid;
                
                /* 设置内边距 */
                padding: 10px 20px 30px 40px;
            }

            /* 通过在div内部嵌套class=inner的块元素来显示内边距 */
            .inner{
                width:100%;
                height: 100%;
                background-color: rgb(56, 150, 212);
            }
            ```
        - html
            ```html
            <section>2. 通过简写属性统一设置内边距</section>
            <div class="box2">
                <div class="inner"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_02](imgs/boxModel/padding/html_result_02.png)

## 外边距margin
[top](#catalog)
- 外边距表示当前盒子与其他盒子之间的距离
- 外边距不会影响盒子可见部分的大小，只会**影响盒子的位置**
- 外边距的4个单边属性，分别是:
    - `margin-top`
    - `margin-bottom`
    - `margin-left`
    - `margin-right`
- 外边距的简写属性
    - `margin:上 右 下 左`
    - 外边距属性值的设置方式与内容区的设置方式相同
        - 1个值：上下左右
        - 2个值：上下 左右
        - 3个值：上 左右 下
        - 4个值：上 右 下 左  

- margin的单边设置对元素的影响
    - 设置`top`、`left`时，会移动元素自身
    - 设置`bottom`时，会移动其他元素
    - `right`默认情况下，不会产生任何效果

- margin的值可以是正数，也可以是负数。负数表示向相反的方向移动
- 示例
    - 参考内容：[/frontend/css/base/src/boxModel/margin.html](/frontend/css/base/src/boxModel/margin.html)
    - 测试margin的单边属性
        - css
            ```css
            /* 1. 测试margin的单边属性 */
            .box1{
                /* 设置内容区 */
                width:200px;
                height:200px;
                background-color: green;
                
                /* 设置边框 */
                border:orange 20px solid;

                /* 设置外边距 */
                margin-top: 10px;
                margin-bottom: 20px;
                margin-left: 30px;
                margin-right: 40px;
            }
            .box2{
                /* 设置内容区 */
                width:200px;
                height: 200px;
                background-color:rgb(51, 114, 230);

                /* 设置边框 */
                border:orange 20px solid;
            }
            ```
        - html
            ```html
            <section>1. 测试margin的单边属性</section>
            <div class="box1"></div>
            <div class="box2"></div>
            ```
        - 页面结果
            - ![html_result_01](imgs/boxModel/margin/html_result_01.png)
    
    - 通过简写属性设置外边距
        - css
            ```css
            .box3{
                /* 设置内容区 */
                width: 200px;
                height: 200px;
                background-color: green;

                /* 设置边框 */
                border:orange 20px solid;

                /* 通过简写属性设置外边距 */
                margin: 10px 20px 30px 40px;
            }
            ```
        - html
            ```html
            <section>2. 通过简写属性设置外边距</section>
            <div class="box3"></div>
            <div class="box2"></div>
            ```
        - 页面结果
            - ![html_result_02](imgs/boxModel/margin/html_result_02.png)

    - 测试负数的margin值
        - css
            ```css
            .box4{
                /* 设置内容区 */
                width: 200px;
                height: 200px;
                background-color: green;

                /* 设置边框 */
                border:orange 20px solid;

                /* 通过简写属性设置外边距 */
                margin: -10px -20px -30px -40px;
            }
            ```
        - html
            ```html
            <section>3. 测试负数的margin值</section>
            <div class="box4"></div>
            <div class="box2"></div>
            ```
        - 页面结果
            - ![html_result_03](imgs/boxModel/margin/html_result_03.png)

## 盒子模型中可以设置为auto的属性
[top](#catalog)
- 如果某个属性为auto，则浏览器会自动计算属性值，来满足页面布局的各种规则
- 可以设置为auto的属性
    - `width`
    - `margin-left`
    - `margin-right`

## 块元素盒子模型的水平方向布局
[top](#catalog)
- 子元素在其父元素中水平方向的位置的决定因素。方向是从左到右、从外到内
    1. `margin-left`
    2. `border-left`
    3. `padding-left`
    4. `width`
    5. `padding-right`
    6. `border-right`
    7. `margin-right`

- 一个子元素在父元素中，水平布局<label style="color:red">必须满足以下等式</label>
    - `父元素.width = 子元素.sum(margin-left, border-left, padding-left, width, padding-right, border-right, margin-right)`
        - 即：父元素内容区的宽度 = 子元素盒子模型的整体宽度
    - 当计算结果不成立时，浏览器会自动调整，使等式成立
        - `父元素.width > 子元素.sum`时，称为**过度约束**，调整方法：
            - 在子元素的7个属性中，如果没有auto，则浏览器会自动调整`margin-right`
            - 在子元素的7个属性中，如果有auto，则浏览器会自动计算该属性值
                - 存在多个多个auto的属性时
                    - 如果包含`width`，则只计算`width`的值，其他属性值=0
                    - 如果只有`margin-left`和`margin-right`，则将剩余的宽度区平均，使得`margin-left = margin-right`
        - `父元素.width < 子元素.sum`时，调整方法：
            - 浏览器会将`margin-right`设为负数，来满足等式

    - <label style="color:red"><span id="reasonofblockelementuseline">为什么块元素会独占一行？</span></label> 
        - 因为需要强制满足

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/horizontalLayout.html](/frontend/css/base/src/boxModel/horizontalLayout.html)
    - 过度约束测试。子元素的各属性中没有auto
        - css
            ```css
            .inner1{
                width: 200px;
                height: 200px;
                background-color: rgb(129, 226, 112);

                margin-left:100px;
            }

            .outter{
                width: 800px;
                height:200px;
                border:rgb(63, 124, 238) 10px solid;
            }
            ```
        - html
            ```html
            <section>1. 过度约束测试。子元素的各属性中没有auto</section>
            <section>等式：800 = 100 + 0 + 0 + 200 + 0 + 0 + 0，等式不满足，浏览器会自动调整margin-right=500</section>
            <div class="outter">
                <div class="inner1"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_01](imgs/boxModel/horizontalLayout/html_result_01.png)

    - 过度约束测试。子元素.width = auto
        - css
            ```css
            .inner2{
                width: auto;
                height: 200px;
                background-color: rgb(129, 226, 112);

                margin-left:100px;
            }
            .outter{
                width: 800px;
                height:200px;
                border:rgb(63, 124, 238) 10px solid;
            }
            ```
        - html
            ```html
            <section>2. 过度约束测试。子元素.width = auto</section>
            <section>等式：800 = 100 + 0 + 0 + auto + 0 + 0 + 0，浏览器会自动计算width=700</section>
            <div class="outter">
                <div class="inner2"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_02](imgs/boxModel/horizontalLayout/html_result_02.png)

    - 过度约束测试。子元素中包含多个auto
        - css
            ```css
            /* 3.1. margin-left = auto; width = auto */
            .inner3{
                width: auto;
                height: 200px;
                background-color: rgb(129, 226, 112);

                margin-left:auto;
                margin-right: 100px;
            }

            /* 3.2. margin-left = auto; margin-right = auto */
            .inner4{
                width: 200px;
                height: 200px;
                background-color: rgb(129, 226, 112);

                margin-left:auto;
                margin-right: auto;
            }
            .outter{
                width: 800px;
                height:200px;
                border:rgb(63, 124, 238) 10px solid;
            }
            ```
        - html
            ```html
            <section>3. 过度约束测试。子元素中包含多个auto</section>
            <br>
            <section>3.1. margin-left = auto; width = auto</section>
            <section>等式：800 = auto + 0+ 0 + auto + 0 + 0 + 100，浏览器会自动计算width=700，margin-left=0</section>
            <div class="outter">
                <div class="inner3"></div>
            </div>
            <br>
            
            <section>3.2. margin-left = auto; margin-right = auto</section>
            <section>等式：800 = auto + 0+ 0 + 200 + 0 + 0 + auto，剩余600px，浏览器会自动除2，设置成相同的值：margin-left = margin-right = 300px</section>
            <div class="outter">
                <div class="inner4"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_03](imgs/boxModel/horizontalLayout/html_result_03.png)
    
    - 父元素.width < 子元素.sum
        - css
            ```css
            .inner5{
                width: 1000px;
                height: 200px;
                background-color: rgb(129, 226, 112);
            }
            .outter{
                width: 800px;
                height:200px;
                border:rgb(63, 124, 238) 10px solid;
            }
            ```
        - html
            ```html
            <section>4. 父元素.width &lt; 子元素.sum</section>
            <section>等式：800 = 0 + 0+ 0 + 1000 + 0 + 0 + 0，浏览器会设置：margin-left = -200</section>
            <div class="outter">
                <div class="inner5"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_04](imgs/boxModel/horizontalLayout/html_result_04.png)
                    
## 盒子模型的垂直方向布局
[top](#catalog)
- 元素的高度
    - 如果元素没有设置高度，则`元素.height = 子元素/文字内容.height`
    - 如果元素设置了高度，则使用自身的高度
    - 子元素是在父元素的内容区排列的，如果子元素的高度超过了父元素的高度，则子元素会从父元素中溢出
- 通过`overflow`属性，设置父元素如何处理溢出的子元素
    - visible，默认值，子元素会从父元素中溢出，并在父元素的外部显示
    - hidden，隐藏溢出的部分
    - scroll，在父元素上同时生成水平和垂直方向的两个滚动条，可以通过滚动条来查看子元素的完整内容
    - auto，在父元素上，根据需要生成水平或垂直方向的滚动条，可以通过滚动条来查看子元素的完整内容
    - inherit， ?????
    - initial， ?????
    - unset， ?????
    - -moz-hidden-unscrollable， ?????

- overflow-x，处理水平方向的溢出
- overflow-y，处理垂直方向的溢出

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/verticalLayout.html](/frontend/css/base/src/boxModel/verticalLayout.html)
    - 父元素没有设置高度
        - css
            ```css
            .outer1{
                background-color: rgb(121, 219, 56);
            }

            .inner1{
                height:100px;

                width:100px;
                background-color: orange;
                
                /* 如果当前元素下方没有其他元素，则该属性没有显示效果 */
                margin-bottom:50px;
            }
            ```
        - html
            ```html
            <section>1. 父元素没有设置高度，子元素设置了高度</section>
            <div class="outer1">
                <div class="inner1">outer1.inner1 01</div>
                <div class="inner1">outer1.inner1 02</div>
            </div>
            ```
        - [](????)

    - 父元素设置高度
        - css
            ```css
            .outer2{
                width:300px;
                height: 300px;
                background-color: rgb(121, 219, 56);
            }

            .inner2{
                height:100px;

                width:100px;
                background-color: orange;
                
                /* 如果当前元素下方没有其他元素，则该属性没有显示效果 */
                margin-bottom:50px;
            }
            ```
        - html
            ```html
            <section>2. 父元素设置高度，子元素设置了高度</section>
            <div class="outer2">
                <div class="inner2">outer2.inner2 01</div>
                <div class="inner2">outer2.inner2 02</div>
            </div>
            ```
        - [](????)

    - 子元素没有设置高度
        - css
            ```css
            <section>3. 父元素设置高度，子元素没有设置高度</section>
            <section>(子元素中如果没有文字内容或下一层的子元素，则这个子元素不会显示)</section>
            <div class="outer3">
                <div class="inner3">outer3.inner3</div>
                <div class="inner3"></div>
            </div>
            ```
        - html
            ```html
            <section>3. 父元素设置高度，子元素没有设置高度</section>
            <section>(子元素中如果没有文字内容或下一层的子元素，则这个子元素不会显示)</section>
            <div class="outer3">
                <div class="inner3">outer3.inner3</div>
                <div class="inner3"></div>
            </div>
            ```
        - [](????)

    - 子元素的高度 > 父元素的高度
        - css
            ```css
            .outer4{
                width:200px;
                height: 200px;
                background-color: rgb(121, 219, 56);
            }

            .inner4{
                height:250px;

                width:100px;
                background-color: orange;
                
            }
            ```
        - html
            ```html
            <section>4. 子元素的高度 > 父元素的高度，子元素会从父元素中溢出</section>
            <div class="outer4">
                <div class="inner4">outer4.inner4</div>
            </div>
            ```
        - [](????)

    - 处理子元素溢出，`overflow=hidden`
        - css
            ```css
            .outer5{
                width:200px;
                height: 200px;
                background-color: rgb(121, 219, 56);
                overflow: hidden;
            }

            .inner5{
                height:300px;

                width:100px;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>5. 处理子元素溢出，overflow=hidden</section>
            <div class="outer5">
                <div class="inner5">outer5.inner5</div>
            </div>
            ```
        - [](????)

    - 处理子元素溢出，`overflow=scroll`
        - css
            ```css
            .outer6{
                width:200px;
                height: 200px;
                background-color: rgb(121, 219, 56);
                overflow: scroll;
            }

            .inner6{
                height:300px;

                width:100px;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>6. 处理子元素溢出，overflow=scroll</section>
            <div class="outer6">
                <div class="inner6">outer6.inner6</div>
            </div>
            ```
        - [](????)

    - 处理子元素溢出，`overflow=auto`
        - css
            ```css
            .outer7{
                width:200px;
                height: 200px;
                background-color: rgb(121, 219, 56);
                overflow:auto;
            }

            .inner7{
                height:300px;

                width:100px;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>7. 处理子元素溢出，overflow=auto</section>
            <div class="outer7">
                <div class="inner7">outer7.inner7</div>
            </div>
            ```
        - [](????)



## 垂直外边距的折叠
[top](#catalog)
- 垂直方向的、相邻的外边距会发生折叠
- 兄弟元素
    - 两个兄弟元素在垂直方向上相邻的外边距属性：上边元素的`margin-bottom`与下边元素的`margin-top`
    - 计算方式
        
        |外边距的值|计算方法|公式|
        |-|-|-|
        |如果两者都是正数|取最大值|`外边距 = Max(elem1.margin-bottom, elem2.margin-top)`|
        |如果一正一负|求和|`外边距 = elem1.margin-bottom + elem2.margin-top`|
        |如果两者都是负数|取绝对值的最大值|`外边距 = Max(abs(elem1.margin-bottom), abs(elem2.margin-top))`|

    - 兄弟元素之间的重叠对开发是有利的，一般不需要进行处理

- 父子元素
    - 父子元素在垂直方向上相邻的外边距属性：`margin-top`
    - 子元素的`margin-top`会传递给父元素，所以父子元素间的外边距折叠会影响整个页面的布局，**必须要进行处理**
    - 为什么子元素的`margin-top`会传递给父元素？
        - 父元素中没有设置边框来隔离，导致了`margin-top`的传递
    - 解决方式
        - 方式1：子元素不使用`margin-top`，同时调整父元素
            - 子元素
                - 删除：`margin-top`
            - 父元素
                - 增加`padding-top`
                - 从`height`中减去与`padding-top`相同的值，维持整体的布局不变

        - 方式2：在父元素中添加边框来隔离父子元素
            - 父元素：
                - `border-top`增加`1px`的，隔离父子元素
                - `height`减去`1px`，维持整体的布局不变
            - 子元素：
                - `margin-top`减去`1px`，抵消父元素增加`1px`边框导致的子元素布局下移

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/marginFold.html](/frontend/css/base/src/boxModel/marginFold.html)
    - 兄弟元素之间的外边距折叠，两者都是正数
        - css
            ```css
            .box1{
                width:100px;
                height:100px;
                background-color: rgb(113, 212, 46);
                
                margin-bottom: 50px;
            }

            .box2{
                width:100px;
                height:100px;
                background-color: orange;

                margin-top:100px;
            }
            ```
        - html
            ```html
            <section>1. 兄弟元素之间的外边距折叠，两者都是正数</section>
            <div class="box1"></div>
            <div class="box2"></div>
            ```
        - 页面结果
            - ![html_result_01](imgs/boxModel/marginFold/html_result_01.png)

    - 兄弟元素之间的外边距折叠，一正一负
        - css
            ```css
            .box3{
                width:100px;
                height:100px;
                background-color: rgb(113, 212, 46);
                
                margin-bottom: 50px;
            }

            .box4{
                width:100px;
                height:100px;
                background-color: orange;

                margin-top:-20px;
            }
            ```
        - html
            ```html
            <section>2. 兄弟元素之间的外边距折叠，一正一负</section>
            <div class="box3"></div>
            <div class="box4"></div>
            ```
        - 页面结果
            - ![html_result_02](imgs/boxModel/marginFold/html_result_02.png)

    - 兄弟元素之间的外边距折叠，两者都是负数
        - css
            ```css
            .box5{
                width:200px;
                height:200px;
                background-color: rgb(113, 212, 46);
                
                margin-bottom: -50px;
            }

            .box6{
                width:200px;
                height:200px;
                background-color: orange;

                margin-top:-100px;
            }
            ```
        - html
            ```html
            <section>3. 兄弟元素之间的外边距折叠，两者都是负数</section>
            <div class="box5"></div>
            <div class="box6"></div>
            ```
        - 页面结果
            - ![html_result_03](imgs/boxModel/marginFold/html_result_03.png)

    - 父子元素之间的外边距折叠，子元素的外边距会传递给父元素
        - css
            ```css
            .box7{
                width:100px;
                height:100px;
                background-color: rgb(113, 212, 46);
                
                /* margin-bottom: -50px; */
            }

            .box8{
                width:50px;
                height:50px;
                background-color: orange;

                margin-top:50px;
            }
            ```
        - html
            ```html
            <section>4. 父子元素之间的外边距折叠</section>
            <div class="box7">
                <div class="box8"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_04](imgs/boxModel/marginFold/html_result_04.png)

    - 父子元素之间的外边距折叠。解决方式1：子元素不使用margin-top，同时调整父元素
        - css
            ```css
            .box9{
                width:100px;
                /* 父元素同时从height中减去与padding-top相同的值 */
                /* height:100px; */
                height:50px;
                background-color: rgb(113, 212, 46);
                
                /* 父元素，增加`padding-top` */
                padding-top:50px;
            }

            .box10{
                width:50px;
                height:50px;
                background-color: orange;

                /* 子元素，删除：`margin-top` */
                /* margin-top:50px; */
            }
            ```
        - html
            ```html
            <section>5. 父子元素之间的外边距折叠。解决方式1：子元素不使用margin-top，同时调整父元素</section>
            <div class="box9">
                <div class="box10"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_05](imgs/boxModel/marginFold/html_result_05.png)

    - 父子元素之间的外边距折叠。解决方式2：在父元素中添加边框来隔离父子元素
        - css
            ```css
            .box11{
                width:100px;
                /* height减去1px，维持整体的布局不变 */
                /* height:100px; */
                height:99px;
                background-color: rgb(113, 212, 46);
                
                /* border-top增加1px的，隔离父子元素 */
                border-top: red 1px solid;          
            }

            .box12{
                width:50px;
                height:50px;
                background-color: orange;

                /* margin-top减去1px，抵消父元素增加1px边框导致的子元素布局下移 */
                /* margin-top:50px; */
                margin-top:49px;
            }
            ```
        - html
            ```html
            <section>6. 父子元素之间的外边距折叠。解决方式2：在父元素中添加边框来隔离父子元素</section>
            <div class="box11">
                <div class="box12"></div>
            </div>
            ```
        - 页面结果
            - ![html_result_06](imgs/boxModel/marginFold/html_result_06.png)

## 行内元素的盒子模型
[top](#catalog)
- 行内元素的内容区content不支持`width`和`height`属性，行内元素的宽、高由元素中的内容决定
- 行内元素可以设置`padding`，并且垂直方向的属性不会影响页面布局，但是可能会覆盖某些元素
- 行内元素可以设置`border`，并且垂直方向的属性不会影响页面布局，但是可能会覆盖某些元素
- 行内元素可以设置`margin`，并且垂直方向的属性不会影响页面布局，但是垂直方向的margin看不到
- 通过[通用属性：display](#display)可以将行内模型转换为块元素

## 盒子的大小
[top](#catalog)
- 默认情况下
- 可以通过`box-sizing`属性来设置盒子大小的计算方式，即设置`width`和`height`的作用范围
    - `content-box`，默认值，宽度和高度设置内容区的大小
        - 盒子的可见大小 = `width/height` + `border`×2 + `padding`×2
    - `border-box`，宽度和高度用来设置整个盒子的可见大小
        - 盒子的可见大小 = `width/height`
        - `width/height` = `content + padding*2 + border*2`

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/boxsize.html](/frontend/css/base/src/boxModel/boxsize.html)
    - `content-box`和`border-box`的区别
        - css
            ```css
            /* 1. box-sizing: content-box;使用width/height 设置内容区的大小 */
            .box1{
                width:100px;
                height: 100px;
                background-color: rgb(117, 218, 23);
                padding:10px;
                border:10px red solid;

                /* 设置内容区的大小 */
                box-sizing: content-box;
            }
            /* 2. box-sizing:border-box; 使用width/height 设置整个盒子的可见大小 */
            .box2{
                width:100px;
                height: 100px;
                background-color: rgb(117, 218, 23);
                padding:10px;
                border:10px red solid;

                /* 设置整个盒子的可见大小 */
                box-sizing:border-box;
            }
            ```
        - html
            ```html
            <section>1. box-sizing: content-box;使用width/height 设置内容区的大小</section>
            <div class="box1"></div>
            <br>
            
            <section>2. box-sizing:border-box; 使用width/height 设置整个盒子的可见大小</section>
            <div class="box2"></div>
            <br>
            ```
        - [](?????)


## 盒子的轮廓
[top](#catalog)
- 轮廓的语法： `outline : 宽度 颜色 样式`，与`border`属性的设置方法相同
- 轮廓与边框的不同点
    - 在默认情况下(`box-sizing:content-box`)，添加边框会增加盒子可见框的大小，会使后边的元素向下移动，影响页面布局
    - 轮廓不会影响**可见框**的大小，**不会影响布局**
- 常用的使用场景
    - 鼠标移入某个元素时，给元素添加边框
- 轮廓属性在实际开发中不太常用
- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/outline.html](/frontend/css/base/src/boxModel/outline.html)
    - `outline`和`border`的比较
        - css
            ```css
            /* 0. 测试基准 */
            .box0{
                width:200px;
                height:200px;
                background-color: rgb(170, 226, 80);
            }
            
            /* 1. 给box1添加border，会导致span中的文字下移，破坏页面整体布局 */
            .box1{
                width:200px;
                height:200px;
                background-color: rgb(170, 226, 80);
                border: 10px red solid;
            }

            /* 2. 给box0添加轮廓outline */
            .box2{
                width:200px;
                height:200px;
                background-color: rgb(170, 226, 80);

                /* 设置元素的轮廓线 */
                outline: 10px red solid;
            }
            ```
        - html
            ```html
            <section>0. 测试基准</section>
            <div class="box0"></div>
            <span>test string0</span>
            <br>
            <br>
            
            <section>1. 给box0添加border，会导致span中的文字下移，破坏页面整体布局</section>
            <div class="box1"></div>
            <span>test string1</span>
            <br>
            <br>
            
            <section>2. 给box0添加轮廓outline</section>
            <br>
            <div class="box2"></div>
            <span>test string2</span>
            ```
        - [](?????)

    - outline的使用场景：当鼠标移入时，为元素添加边框
        - css
            ```css
            .box3{
                width:200px;
                height:200px;
                background-color: rgb(170, 226, 80);
            }

            .box3:hover{
                outline:10px red solid;
            }
            ```
        - html
            ```html
            <section>3. outline的使用场景：当鼠标移入时，为元素添加边框</section>
            <div class="box3"></div>
            <span>test string3</span>
            ```
        - 测试结果
            - 正常显示
                - [](?????)
            - 鼠标移入
                - [](?????)

## 盒子的阴影
[top](#catalog)
- 语法：`box-shadow: 水平偏移量 垂直偏移量 阴影的模糊半径 阴影颜色 `
    - 偏移量
        - 正数：阴影向右/下偏移
        - 负数：阴影向左/上偏移
    - 阴影的模糊半径越大越模糊
    - 一般情况下，为了使阴影具有透明效果，阴影颜色会使用`rgba`来设定
- **阴影不会影响页面布局**
- 阴影默认是在盒子的正下方，即：`box-shadow: 0px 0px`
- 示例
    - 参考代码:[/frontend/css/base/src/boxModel/boxshadow.html](/frontend/css/base/src/boxModel/boxshadow.html)
    - 阴影在盒子的正下方
        - css
            ```css
            .box1{
                width: 100px;
                height: 100px;
                background-color: rgb(139, 235, 49);
                box-shadow:0px 0px orange ;
            }
            ```
        - html
            ```html
            <section>1. 阴影在盒子的正下方</section>
            <div class="box1"></div>
            ```
        - ![](?????)

    - 阴影偏移-正数，阴影向右/下偏移
        - css
            ```css
            .box2{
                width:100px;
                height:100px;
                background-color: rgb(139, 235, 49);
                box-shadow:10px 10px 0px rgba(253, 190, 0, 0.6) ;
            }
            ```
        - html
            ```html
            <section>2. 阴影偏移-正数，阴影向右/下偏移</section>
            <div class="box2"></div>
            ```
        - ![](?????)

    - 阴影偏移-负数，阴影向左/上偏移
        - css
            ```css
            .box3{
                width:100px;
                height:100px;
                background-color: rgb(139, 235, 49);
                box-shadow:-10px -10px 0px rgba(253, 190, 0, 0.6) ;
            }
            ```
        - html
            ```html
            <section>3. 阴影偏移-负数，阴影向左/上偏移</section>
            <br>
            <div class="box3"></div>
            ```
        - ![](?????)

    - 阴影的模糊半径
        - css
            ```css
            .box4{
                width:100px;
                height:100px;
                background-color: rgb(139, 235, 49);
                box-shadow:10px 10px 10px rgba(253, 190, 0, 0.6) ;
            }
            ```
        - html
            ```html
            <section>4. 阴影的模糊半径</section>
            <div class="box4"></div>
            ```
        - ![](?????)

    - 使用0偏移量和阴影的模糊半径来制造羽化效果
        - css
            ```css
            .box5{
                width:100px;
                height:100px;
                background-color: rgb(139, 235, 49);
                box-shadow:0px 0px 10px rgba(253, 190, 0, 0.6) ;
            }
            ```
        - html
            ```html
            <section>5. 使用0偏移量和阴影的模糊半径来制造羽化效果</section>
            <br>
            <div class="box5"></div>
            ```
        - ![](?????)
    

## 盒子的圆角
[top](#catalog)
- 语法：
    - 统一属性
        - `border-radius: 4个方向的正圆圆角半径`
        - `border-radius: 左上 右上 右下 左下`，仍然遵守顺时针的顺序
        - `border-radius: 左上 右上/左下 右下`，仍然遵守顺时针的顺序
        - `border-radius: 左上/右下 右上/左下 `，仍然遵守顺时针的顺序
        - 在统一属性设置椭圆
            - `border-radius: 正圆圆角半径 / 椭圆圆角半径`

    - 单角属性
        - `border-top-left-radius: 正圆圆角半径，椭圆圆角半径`
        - `border-top-right-radius: 正圆圆角半径，椭圆圆角半径`
        - `border-bottom-left-radius: 正圆圆角半径，椭圆圆角半径`
        - `border-bottom-right-radius: 正圆圆角半径，椭圆圆角半径`

- 当`正圆圆角半径=椭圆圆角半径`时，页面显示的仍然是圆角
- 常用方式
    - 可以直接使用：`border-radius: 50%`将盒子设置为圆

- 示例
    - 参考代码：[/frontend/css/base/src/boxModel/borderRadius.html](/frontend/css/base/src/boxModel/borderRadius.html)
    - 使用单角属性来设置正圆圆角
        - css
            ```css
            .box1{
                width: 150px;
                height: 150px;
                background-color: rgb(142, 224, 49);
                
                border-top-left-radius: 20px;
                border-top-right-radius: 30px;
                border-bottom-left-radius: 40px;
                border-bottom-right-radius: 50px;
            }
            ```
        - html
            ```html
            <section>1. 使用单角属性来设置正圆圆角</section>
            <div class="box1"></div>
            ```
        - ![](?????)

    - 使用单角属性来设置椭圆圆角
        - css
            ```css
            .box2{
                width: 150px;
                height: 150px;
                background-color: rgb(142, 224, 49);
                
                border-top-left-radius: 30px 60px;
            }
            ```
        - html
            ```html
            <section>2. 使用单角属性来设置椭圆圆角</section>
            <div class="box2"></div>
            ```
        - ![](?????)

    - 使用统一属性，给4个角设置相同的半径
        - css
            ```css
            .box3{
                width: 100px;
                height: 100px;
                background-color: rgb(142, 224, 49);

                border-radius: 30px;
            }
            ```
        - html
            ```html
            <section>3. 使用统一属性，给4个角设置相同的半径</section>
            <div class="box3"></div>
            ```
        - ![](?????)

    - 使用统一属性，分别设置：左上 右上 右下 左下
        - css
            ```css
            .box4{
                width: 150px;
                height: 150px;
                background-color: rgb(142, 224, 49);

                border-radius: 20px 30px 40px 50px;
            }
            ```
        - html
            ```html
            <section>4. 使用统一属性，分别设置：左上 右上 右下 左下</section>
            <div class="box4"></div>
            ```
        - ![](?????)

    - 使用统一属性，分别设置：左上 右上/左下 右下
        - css
            ```css
            .box5{
                width: 200px;
                height: 200px;
                background-color: rgb(142, 224, 49);

                border-radius: 30px 50px 70px;
            }
            ```
        - html
            ```html
            <section>5. 使用统一属性，分别设置：左上 右上/左下 右下</section>
            <div class="box5"></div>
            ```
        - ![](?????)

    - 使用统一属性，分别设置：左上/右下 右上/左下
        - css
            ```css
            .box6{
                width: 200px;
                height: 200px;
                background-color: rgb(142, 224, 49);

                border-radius: 30px  70px;
            }
            ```
        - html
            ```html
            <section>6. 使用统一属性，分别设置：左上/右下 右上/左下</section>
            <div class="box6"></div>
            ```
        - ![](?????)

    - 在统一属性设置椭圆
        - css
            ```css
            .box7{
                width: 200px;
                height: 200px;
                background-color: rgb(142, 224, 49);

                border-radius: 30px / 70px;
            }
            ```
        - html
            ```html
            <section>7. 在统一属性设置椭圆</section>
            <div class="box7"></div>
            ```
        - ![](?????)

    - 通过：border-radius: 50%，将盒子设置为50%
        - css
            ```css
            .box8{
                width: 200px;
                height: 200px;
                background-color: rgb(142, 224, 49);

                border-radius:50%;
            }
            ```
        - html
            ```html
            <section>8. 通过：border-radius: 50%，将盒子设置为50%</section>
            <div class="box8"></div>
            ```
        - ![](?????)



# 浏览器的默认样式
[top](#catalog)
- 默认样式
    - 一般情况下，浏览器都会为元素设置默认样式
    - html控制结构，css控制页面显示，如果只有html没有css，则页面无法显示任何信息。所以浏览器为了在没有css的情况下能正常显示，会使用默认样式
    - 通常情况下，默认样式会影响页面布局，所以要去除默认样式（主要是在pc端）
        - 主要希望去除的是：`margin`、`padding`这些会影响页面布局的属性

- 去除默认样式的方法
    - 方法1：可以使用通配符来统一去除默认样式
        ```css
        /* 统一出去默认样式的 外边距和内边距 */
        *{
            margin: 0px;
            padding: 0px;
        }
        ```
    - 方法2：手动调整某个标签的样式
    - 方法3：从外部引入重置样式表：
        - `reset.css`
            - **去除**所有的默认样式
        - `nomalize.css`
            - **统一**所有的默认样式

- 示例
    - 去除body的默认css
        - 参考代码：[/frontend/css/base/src/browserDefalutCss/defalutCss.html](/frontend/css/base/src/browserDefalutCss/defalutCss.html)
        - css
            ```css
            body{
                margin:0px;
            }
            .box1{
                width: 100px;
                height: 100px;
                
                border:black 1px solid;
            }
            ```
        - html
            ```html
            <section>1. 去除body的默认css</section>
            <div class="box1"></div>
            <p>p1</p>
            <p>p2</p>
            ```
        - 页面结果
            - ![html_result_01](imgs/browserDefalutCss/defalutCss/html_result_01.png)

    - 去除ul的样式
        - 参考代码：[/frontend/css/base/src/browserDefalutCss/defalutCss.html](/frontend/css/base/src/browserDefalutCss/defalutCss.html)
        - css
            ```css
            ul{
                list-style: none;
            }
            ```
        - html
            ```html
            <section>2. 去除ul的样式</section>
            <ul>
                <li>aaaa</li>
                <li>bbbb</li>
                <li>cccc</li>
                <li>dddd</li>
            </ul>
            ```
        - 页面结果
            - ![html_result_02](imgs/browserDefalutCss/defalutCss/html_result_02.png)

    - 使用通配符统一去除浏览器的默认样式
        - 参考代码：[/frontend/css/base/src/browserDefalutCss/deleteDefaultCss.html](/frontend/css/base/src/browserDefalutCss/deleteDefaultCss.html)
        - css
            ```css
            *{
                margin: 0px;
                padding: 0px;
            }
            ```
        - html
            ```html
            <body>
                <p>p1</p>
                <p>p2</p>
                <p>p3</p>
                <p>p4</p>

                <ul>
                    <li>li01</li>
                    <li>li02</li>
                    <li>li03</li>
                    <li>li04</li>
                    <li>li05</li>
                </ul>
            </body>
            ```
        - 页面结果
            - ![html_result_01](imgs/browserDefalutCss/deleteDefaultCss/html_result_01.png)
            
# 常用的通用属性
## display
[top](#catalog)
- `display`，用来设置元素的显示类型
- 常用的属性值

    |属性值|描述|备注|
    |-|-|-|
    |inline|将元素设置为行内元素||
    |block|将元素设置为块元素||
    |inline-block|行内块元素|<ul><li>优缺点<ul><li>同时兼具行内元素和块元素的优点：既可以设置`width`和`height`(块元素)，又**不会独占一行**（行内元素）</li><li>同时兼具行内元素和块元素的缺点：换行符会被解析为空白距离。即如果行内块元素间有换行，页面上的两个元素之间会产生一个空白的距离</li></ul></li><li>一般开发时尽量不要使用</li></ul>|
    |table|将元素设置为表格元素||
    |none|元素不再页面中显示。|可以用来隐藏元素，在需要的时候，通过页面控制再显示出来|

- 示例
    - 参考代码：[/frontend/css/base/src/commonProperties/display.html](/frontend/css/base/src/commonProperties/display.html)
    - 测试行内元素转化为`display:block`
        - css
            ```css
            .s1{
                background-color: rgb(214, 177, 54);
                /* 转化为块元素之后可以设置 width 和 height */
                width: 250px;
                height:250px;

                display: block;
            }
            .box1{
                width:200px;
                height:200px;
                background-color: green;
            }
            ```
        - html
            ```html
            <section>1. 测试行内元素的 display:block</section>
            <span class="s1">this is span5</span>
            <div class="box1"></div>
            <br>
            ```
        - 页面结果
            - ![](?????)

    - 测试行内块元素`display:inline-block`
        - css
            ```css
            .s2{
                background-color: rgb(214, 177, 54);

                width: 50px;
                height:50px;

                display: inline-block;
            }
            .box2{
                width:200px;
                height:200px;
                background-color: green;
            }
            ```
        - html
            ```html
            <section>2. 测试行内块元素 display:inline-block</section>
            <section>2.1. 多个行内块元素之间有换行</section>
            <span class="s2">span2 01</span>
            <span class="s2">span2 02</span>
            <span class="s2">span2 03</span>

            <br>
            <br>
            <section>2.2. 多个行内块元素之间没有换行</section>
            <span class="s2">span2 04</span><span class="s2">span2 05</span><span class="s2">span2 06</span>
            <div class="box2"></div>
            <br>
            ```
        - 页面结果
            - ![](?????)

    - 测试元素隐藏`display:none`
        - css
            ```css
            .s3{
                background-color: rgb(214, 177, 54);

                width: 50px;
                height:50px;

                display: none;
            }
            .box3{
                width:200px;
                height:200px;
                background-color: green;
            }
            ```
        - html
            ```html
            <section>3. 测试元素隐藏 display:none</section>
            <span class="s7">span6 04</span>
            abcdefg
            <span class="s7">span6 05</span>
            <div class="box7"></div>
            ```
        - 页面结果
            - ![](?????)

## visibility
[top](#catalog)
- visible
    - 默认值，显示元素
- hidden
    - 隐藏元素，但是元素仍然会占据页面的位置

- 示例
    - 参考代码：[/frontend/css/base/src/commonProperties/visibility.html](/frontend/css/base/src/commonProperties/visibility.html)
    - 1. 测试元素隐藏`visibility:hidden`
        - css
            ```css
            .s1{
                background-color: rgb(214, 177, 54);

                width: 100px;
                height:100px;

                visibility: hidden
            }
            .box1{
                width:200px;
                height:200px;
                background-color: green;
            }
            ```
        - html
            ```html
            <section>1. 测试元素隐藏 visibility:hidden</section>
            <span class="s1"></span>
            <div class="box1"></div>
            ```

# 浮动
## 浮动简介
[top](#catalog)
- 通过浮动可以使一个元素向其**父元素**的左侧或右侧移动
- 语法： `float: 属性值`
    - 属性值包括
        - `none`，默认值元素不会浮动
        - `left`，元素向左浮动
        - `right`，元素向右浮动
- 使用了`float:left`或`float:right`之后，<label style="color:red">元素将脱离文档流</label>，使得[块元素盒子模型的水平方向布局](#块元素盒子模型的水平方向布局)中的水平布局等式不再强制成立
    - 如：对一个`div`使用了`float`属性之后，在元素检查器中会发现，盒子模型中不会强制产生用于满足水平布局等式的`margin-right`或`margin-left`
        - 参考代码：[/frontend/css/base/src/float/floatBase.html](/frontend/css/base/src/float/floatBase.html)
        - css
            ```css
            /* 0. 不使用float 的div盒子模型 */
            .box0{
                width:100px;
                height:100px;
                background-color: rgb(80, 207, 7);
            }
            
            /* 1. 使用float:left 后 的div盒子模型 */
            .box1{
                width:100px;
                height:100px;
                background-color: rgb(80, 207, 7);
                float:left;
            }
            ```
        
        - html
            ```html
            <section>0. 不使用float 的div盒子模型</section>
            <div class="box0"></div>
            
            <section>1. 使用float:left 后的div盒子模型</section>
            <div class="box1"></div>
            ```
        - 元素检查器中的结果
            - ![](?????)

- 浮动元素的特点
    - 浮动元素会脱离文档流，不用遵守水平布局的等式
    - 设置浮动之后，浮动元素会向父元素的左/右侧移动。默认情况下，不会从父元素中移出
    - 默认情况下，浮动元素不会覆盖/超过其前面的兄弟浮动元素，会接在前一个浮动元素的后面
    - 如果浮动元素前面不是浮动元素，则无法移动
    - 浮动元素不会覆盖文字，**文字会自动环绕**在浮动元素周围


- **通过浮动元素可以进行水平布局**

- 示例
    - 参考代码：[/frontend/css/base/src/float/floatBase.html](/frontend/css/base/src/float/floatBase.html)
    - 测试使用float属性后，元素脱离文档流
        - css
            ```css
            .box201{
                width:100px;
                height: 100px;
                background-color: green;
                float: left;
            }
            .box202{
                width:200px;
                height: 200px;
                background-color: orange;
            }
            ```
        - html
            ```html
            <section>2. 测试使用float属性后，元素脱离文档流</section>
            <div class="box101"></div>
            <div class="box102"></div>
            ```
        - ![](?????)

    - 多个使用float属性的元素横向排列
        - css
            ```css
            .box301{
                width:100px;
                height: 100px;
                background-color: green;
                float: left;
            }
            .box302{
                width:100px;
                height: 100px;
                background-color: orange;
                float: left;
            }
            .box303{
                width:100px;
                height: 100px;
                background-color: yellow;
                float: left;
            }
            ```
        - html
            ```html
            <section>3. 多个使用float属性的元素横向排列</section>
            <div class="box301"></div>
            <div class="box302"></div>
            <div class="box303"></div>
            ```
        - ![](?????)

    - 默认情况下，浮动元素不会从父元素中移出
        - css
            ```css
            .box401{
                width:200px;
                height: 200px;
                background-color: green;
            }
            
            .box402{
                width:50px;
                height: 50px;
                background-color: orange;
                float: left;
            }
            
            .box403{
                width:50px;
                height: 50px;
                background-color: orange;
                float:right;
            }
            ```
        - html
            ```html
            <div class="box401">
                <div class="box402"></div>
                <div class="box403"></div>
            </div>
            ```
        - ![](?????)

    - 浮动不会覆盖文字--文字自动环绕效果
        - css
            ```css
            .box5{
                width:50px;
                height: 50px;
                background-color: orange;
                float:left;
            }
            ```
        - html
            ```html
            <section>5. 浮动不会覆盖文字--文字自动环绕效果</section>
            <div class="box5"></div>
            <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Cumque adipisci doloribus praesentium similique dignissimos eaque velit accusantium nisi, dolores facilis dolorem inventore non, eos placeat. Molestias rerum odio esse culpa.</p>
            <br>
            ```
        - ![](?????)


# 元素脱离文档流之后的特点
[top](#catalog)
- 块元素
    - 块元素不再独占一行
    - 块元素的宽、高等于元素内容的宽、高
- 行内元素
    - 行内元

# 总结
[top](#catalog)
- width的默认值为auto。如果不写，会由浏览器自动计算
- 一个子元素在父元素中，水平布局<label style="color:red">必须满足以下等式</label>
    - `父元素.width = 子元素.sum(margin-left, border-left, padding-left, width, padding-right, border-right, margin-right)`

- 行内元素的宽和高默认都是元素内容的宽和高
- 如果父元素中没有设置`height`，当子元素中使用负数的`margin`时，会导致下方的元素向上移动。

- 阴影不会影响页面布局
- 轮廓不会影响**可见框**的大小，**不会影响布局**

- 默认情况下，块元素独占一行是因为要强制遵守水平布局的等式
    - [为什么块元素独占一行](#reasonofblockelementuseline)

- 浮动相关的连锁问题（根本原因：在不在文档流中与水平布局等式）
    - 块元素的特点
    - 块元素为什么能独占一行
    - 浮动为什么不会独占一行

- 行内元素、块元素的互相转化
    - 行内元素---> 块元素
        - float，脱离文档流
        - display:block，将元素设置为块元素
    - 块元素 ---> 行内元素
        - displ


    |属性值|描述|备注|
    |-|-|-|
    |inline|将元素设置为行内元素||
    |block|将元素设置为块元素||
    |inline-block|行内块元素|<ul><li>优缺点<ul><li>同时兼具行内元素和块元素的优点：既可以设置`width`和`height`(块元素)，又**不会独占一行**（行内元素）</li><li>同时兼具行内元素和块元素的缺点：换行符会被解析为空白距离。即如果行内块元素间有换行，页面上的两个元素之间会产生一个空白的距离</li></ul></li><li>一般开发时尽量不要使用</li></ul>|
    |table|将元素设置为表格元素||
    |none|元素不再页面中显示。|可以用来隐藏元素，在需要的时候，通过页面控制再显示出来|


# 练习
[top](#catalog)
- 图片列表
    - 01piclist.html
- 文字导航条
    - 02navigation.html
- 网易新闻列表
    - 03newlist.html
