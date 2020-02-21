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
- [页面的长度单位](#页面的长度单位)
- [](#)
- [](#)
- [](#)

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
        - 选择`兄弟元素1选择器`**后边的所有兄弟元素**：`兄弟元素1选择器 + 兄弟元素2选择器{ 声明块 }`
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
                - ![](?????)
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
                - ![](?????)
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
                - ![](?????)

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
                - ![](?????)
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
                - ![](?????)
        
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
                - ![](?????)
        
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
                - ![](?????)


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
                - ![](?????)
        
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
                - ![](?????)
        
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
                - ![](?????)
        
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
                - ![](?????)
        
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
                - ![](?????)
        
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
                - ![](?????)
    
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
                - ![](?????)


## 伪元素选择器
[top](#catalog)
- 伪元素表示页面中一些特殊的但不存在的元素或特殊位置
- 伪元素的语法, `其他选择器::伪元素名`
- 常用伪元素
    
    |伪元素|描述|备注|
    |-|-|-|
    |`::first-letter`|表示第一个字母||
    |`:first-line:`|表示第一行||
    |`::selection`|表示选中的内容||
    |`::before`|表示元素的开始|1.该元素的位置处于开始标签后，标签内容之前<br>如：`<tag>|文字`，假设`|`不真实存在，`|`就是`::before`的位置<br><br>2. `before`必须结合`content`属性来使用，所有的样式都是在设置`content`中的内容<br><br>3. 如果使用了`content`属性，在控制台可以看到该元素；如果不使用`content`属性则无法看到|
    |`::after`|表示元素的最后|1.该元素的位置处于标签内容之后，结束标签之前<br>如：`文字|<tag>`，假设`|`不真实存在，`|`就是`::after`的位置<br><br>2. `after`必须结合`content`属性来使用，所有的样式都是在设置`content`中的内容<br><br>3. 如果使用了`content`属性，在控制台可以看到该元素；如果不使用`content`属性则无法看到|

- 在短引用标签`<q>`中，页面中自动出现的`""`或`[]`，就是通过在css中设置`::before`和`::after`来实现的

- 示例
    - 参考代码:[/frontend/css/base/src/selector/pseudo_class.html](/frontend/css/base/src/selector/pseudo_class.html)
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
        - ![](?????)

    - 控制台中的页面元素
        - ![](?????)


# 选择器的权重
[top](#catalog)
- 样式冲突
    - 发生样式冲突的条件：<label style="color:red">不同的选择器，选中相同的元素，为相同的样式设置不同的值</label>
- 发生样式冲突时，由选择器的权重来决定。如果优先级相同，则使用后声明的选择器中的样式（可以理解为后声明的内容覆盖了先声明的内容）

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
    - 可以在<label style="color:red">样式</label>的后面添加`!important`。该样式将会获得最高的权重，比内联样式还高
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

        - ![](?????)

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

        - ![](?????)

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
        - ![](?????)

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
        - ![](?????)

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
        - ![](?????)

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

    - ![](?????)

# 页面的长度单位
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
