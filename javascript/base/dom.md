<span id="catalog"></span>

### 目录
- [dom概述](#dom概述)
- [dom操作](#dom操作)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)


# dom概述
[top](#catalog)
- js通过DOM来对HTML文档进行操作

- DOM，全称：Document Object Model， 文档对象模型
    - **文档**，表示html文档
    - **对象**，表示将网页中的每一个部分都转换为一个对象
    - **模型**，使用模型表示对象之间的关系，方便获取对象

- 模型：HTML DOM树
    - html
        ```html
        <html>
        <head>
            <title>dom base</title>
        </head>
        <body>
            <a href="javascript:;">xxxx</a>
        </body>
        </html>
        ```
    - 对象关系
        ```
             html
               │
          ┌────┴────┐
        head       body
          │         │
        title       a
          │         │
        文本节点   文本节点
        ```

- 节点
    - 节点：Node，构成HTML文档的最基本单元，**网页中的每一个部分都可以是一个节点**
        - 如： html标签、属性、文本、注释、整个文档等都是一个节点

    - 节点的类型

        |节点类型|html内容|
        |-|-|
        |文档节点|整个html文档|
        |元素节点|标签|
        |属性节点|属性|
        |文本节点|标签中的文本|
        
    - 节点的属性

        ||nodeName|nodeType|nodeValue|
        |-|-|-|-|
        |文档节点|#document|9|null|
        |元素节点|标签名|1|null|
        |属性节点|属性名|2|属性值|
        |文本节点|#text|3|文本内容|
        
    - **浏览器默认提供文档节点对象：document，该对象是 window 对象的属性，可以直接使用**

- 事件
    - 事件就是文档或浏览器窗口中发生的一些特定的**交互瞬间**
    - js与html之间的交互是通过事件实现了
    - 事件的处理方式
        - 在事件对应的属性中设置一些js代码。当事件被触发时，会自动执行js代码
            - 页面的结果与行为耦合，尽量不使用这种方式
        - 为页面对象的对应事件绑定处理函数来响应事件

- **文档的加载顺序**
    - 浏览器按照从上到下的顺序加载文档，读取到一行就执行一行
    - 默认情况下js写到 `<script></script>` 标签中的问题
        - 页面启动后，先执行js，但是DOM对象还没有全部加载，无法通过js操作页面对象，会导致事件绑定和一些对象操作异常
    - js代码写到 `<script></script>` 标签中能够正常执行的方法
        - 将对页面对象操作的js代码封装到 `window.onload` 的处理函数中
        - `onload`事件会在整个页面加载完成之后才触发，可以为 `window` 对象绑定`onload` 事件，来使**部分js在页面加载完成之后触发**
        - 示例
            ```html
            <script type="text/javascript">
                window.onload = function(){
                    ...
                };
            </script>
            ```

# dom操作
[top](#catalog)
- 获取元素节点 (页面对象)
    - `document.getElementById("元素id");`，通过元素id获取一个元素节点对象
    - `document.getEmelmentByTagName("标签名");`，通过标签名获取一组元素节点对象
    - `document.getEmelmentByName("name属性值");`，通过name属性获取一组元素节点对象

- `元素节点.innerHTML` 属性，获取元素节点中的文本内容
    - 对于**自结束标签**，该属性没有值 (没有意义)
// 通过属性修改文本
 = "xxx";

// 将处理函数绑定到页面对象的事件

// onload 事件会在整个页面加载完成之后才触发
