<span id="catalog"></span>

### 目录
- [dom概述](#dom概述)
- [dom操作](#dom操作)
    - [dom查询](#dom查询)
        - [元素与属性的读写](#元素与属性的读写)
        - [示例-dom的基本操作](#示例-dom的基本操作)
        - [示例-dom基本操作的补充](#示例-dom基本操作的补充)
        - [示例-颜色切换](#示例-颜色切换)
        - [示例-checkbox控制](#示例-checkbox控制)
    - [dom的增删改](#dom的增删改)
- [事件绑定](#事件绑定)
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
        
    - 节点的几个基本属性

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
## dom查询
### 元素与属性的读写
[top](#catalog)
- 元素对象的分类
    - 子元素：一个标签对象
    - 子节点：包或子元素，换行/空白也会被当作子节点

- 通过document对象调用方法获取元素节点
    - 常用方法
        - `document.querySelector("css选择器");`，通过css选择器来获取元素，只返回符合条件的第一个元素节点
            - 一些IE8不兼容的方法都可以通过此方法进行替换
        - `document.querySelectorAll("css选择器");`，通过css选择器来获取元素，返回所有符合条件的元素节点，返回的是一个数组
        - `document.getElementById("元素id");`，通过元素id获取一个元素节点
        - `document.getElementsByTagName("标签名");`，通过标签名获取一组元素节点
        - `document.getElementsByName("name属性值");`，通过name属性获取一组元素节点
        - `document.getElementsByClassName("class名");`，通过class名获取一组元素节点
            - <label style="color:red">IE8不兼容</label>
    - 常用属性
        - 获取body对象
            - 方法1：`document.getElementsByTagName("body")[0]`
                - 使用 getElementsByTagName 获取对象时，返回的一个类数组对象，所以需要获取索引是 0 的对象
            - 方法2：`documnet.body`
                - document 对象的body属性中，默认保存body元素对象的引用

        - `document.documentElement`，获取html对象
        - 获取页面中的所有元素
            - 方法1：`document.getElementsByTagName("*")`
            - 方法2：`document.all`
                - 该属性的bug：`typeof document.all`，会返回 undefined


- 获取元素节点的子对象
    - 常用方法
        - `document` 下的方法，元素节点中都有，知识返回的是当前节点下的**子节点**
    - 常用属性
        - `firstChild`，返回当前节点下的第一个**子节点**
        - `lastChild`，返回当前节点下的最后一个**子节点**
        - `firstElementChild`，返回当前节点下的第一个**子元素**
            - <label style="color:red">IE8不兼容</label>
        - `laseElementChild`，返回当前节点下的最后一个**子元素**
            - <label style="color:red">IE8不兼容</label>
        - `children`，返回当前节点下的所有**子元素**
        - `childNodes`，返回当前节点下的**所有子节点**
            - <label style="color:red">IE8不兼容，不会将换行/空白当作子节点</label>
            - 如下: ul01 下共有 8个 childNode，包括：4个 li 标签、3个换行、1个空格
                ```html
                <ul class="showList clearfixed" id="ul01"> 
                    <li id="aa_id">aa</li>
                    <li>bb</li>  <li>cc</li><li>dd</li>
                </ul>
                ```

- 获取元素节点的兄弟对象和子对象
    - 常用属性
        - `parentNode`，当前节点的父**节点**
        - `previousSibling`，当前节点的前一个兄弟**节点**
        - `nextSibling`，当前节点的下一个兄弟**节点**
        - `previousSibling`，当前节点的前一个兄弟**元素**
        - `nextSibling`，当前节点的下一个兄弟**元素**


- `元素.属性名`，获取和设置元素节点的属性
    - 特例
        - 读取class属性时，需要使用 `元素节点.className`
        - `元素节点.innerHTML`，元素节点中的html内容
            - 对于**自结束标签**，该属性没有值 (没有意义)
        - `元素节点.innerText`，元素节点中的文本内容
            - 该属性与 `innerHTML` 类似，但是会**去除html标签**
        - `文本节点.nodeValue`， 获取文本节点中的文本内容
            - 因为文本节点中没有 `value` 属性，所以只能通过该基本属性来获取


### 示例-dom的基本操作
[top](#catalog)
- 参考代码
    - [/javascript/base/src/dom/dom.html](/javascript/base/src/dom/dom.html)

- html内容
    ```html
    <div class="leftbox">
        <div class="select">
            <p>1. problem01</p>
            <ul class="showList clearfixed" id="ul01">
                <li id="aa">aa</li>
                <li>bb</li>
                <li>cc</li>
                <li>dd</li>
            </ul>
            
            <p>2. problem02</p>
            <ul class="showList clearfixed">
                <li>ee</li>
                <li id="ff">ff</li>
                <li>gg</li>
                <li>hh</li>
            </ul>
            
            <p>3. problem03</p>
            <ul class="showList clearfixed">
                <li>ii</li>
                <li>jj</li>
                <li>kk</li>
                <li>ll</li>
            </ul>
        </div>

        <div class="inputs">
            <span>gender: </span>
            <input type="radio" class="rc0101 rc0102" name="gender" id="radio01" value="1">
            <span>r01</span>
            <input type="radio" class="rc02" name="gender" id="radio01" value="2" >
            <span>r02</span>
            <input type="radio" class="rc03" name="gender" id="radio01" value="3" >
            <span>r03</span>
            <br>
            <span>text01：</span>
            <input type="text" name="text01" id="text01" value='default'>
        </div>
    </div>
    ```

- js内容
    ```js
    // 通过id查找元素，并设置onclick事件的处理函数
    function addElemClickById(id, fn){
        var elem = document.getElementById(id);
        elem.onclick = fn;
    }

    window.onload = function(){
        // 1. 通过id获取元素，并输出元素的文本内容
        var btn01 = document.getElementById("btn01");
        btn01.onclick = function(){
            var aa = document.getElementById("aa");
            output(aa); // 输出当前元素
            output(aa.innerHTML);
        };

        // 2. 通过li标签获取一组元素，并遍历输出各元素的文本内容
        var btn02 = document.getElementById("btn02");
        btn02.onclick = function(){
            var lis = document.getElementsByTagName("li");
            for (var i=0; i< lis.length; i++){
                output(lis[i].innerHTML);
            }
        }
        
        // 3. 获取name=gender的对象，并遍历输出各元素的文本内容
        var btn03 = document.getElementById("btn03");
        btn03.onclick = function(){
            var gender = document.getElementsByName("gender");
            for(var i=0; i<gender.length; i++){
                output(
                    "type = " + gender[i].type +
                    ", class = " + gender[i].className + 
                    ", id = " + gender[i].id + 
                    ", value = " + gender[i].value
                );
            }
        };

        // 4. 获取ul01元素节点下的所有li子节点: getElementsByTagName
        addElemClickById(
            "btn04",
            function(){
                // 获取ul01元素节点
                var ul01 = document.getElementById("ul01");
                // 获取该节点下的所有li子节点
                var ul01_lis = ul01.getElementsByTagName("li");
                // 输出数组的长度
                output("1. ul01_lis.length = " + ul01_lis.length);
                for (var i=0; i<ul01_lis.length; i++){
                    output(ul01_lis[i].innerHTML);
                }
            }
        );

        // 5. 获取ul01元素节点下的所有 子节点:childNodes
        addElemClickById(
            "btn05",
            function(){
                // 获取ul01元素节点
                var ul01 = document.getElementById("ul01");
                // 获取该节点下的所有子节点
                var ul01_lis = ul01.childNodes;
                // 输出数组的长度
                output("2. ul01_lis.length = " + ul01_lis.length);
                for (var i=0; i<ul01_lis.length; i++){
                    output(ul01_lis[i].innerHTML);
                }
            }
        );

        // 6. 获取ul01元素节点下的所有 子元素:children
        addElemClickById(
            "btn06",
            function(){
                // 获取ul01元素节点
                var ul01 = document.getElementById("ul01");
                // 获取该节点下的所有子元素
                var ul01_lis = ul01.children;
                // 输出数组的长度
                output("2. ul01_lis.length = " + ul01_lis.length);
                for (var i=0; i<ul01_lis.length; i++){
                    output(ul01_lis[i].innerHTML);
                }
            }
        );
        
        // 7. 获取ul01元素节点下的第一个 子元素:firstElementChild
        addElemClickById(
            "btn07",
            function(){
                // 获取ul01元素节点
                var ul01 = document.getElementById("ul01");
                // 获取该节点下的所有子元素
                var li01 = ul01.firstElementChild;
                // 输出数组的长度
                output(li01.innerHTML);
            }
        )

        // 8. 获取 aa 元素的父对象
        addElemClickById(
            "btn08",
            function(){
                var aa = document.getElementById("aa");
                output(aa.parentNode);
            }
        )
        
        // 9. 获取 ff 元素的前一个兄弟节点和前一个兄弟元素
        addElemClickById(
            "btn09",
            function(){
                var aa = document.getElementById("ff");
                // 返回文本
                var prevNode = aa.previousSibling;
                output(prevNode);
                // 返回元素
                var prevElem = aa.previousElementSibling;
                output(prevElem);
            }
        )
        
        // z. 读取ff节点下的文本节点中的文本内容
        addElemClickById(
            "btnz",
            function(){
                // 获取ff元素对象
                var ff = document.getElementById("ff");
                // 获取ff下的文本节点、
                var ff_text_node = ff.firstChild;
                
                output(ff_text_node);
                output(ff_text_node.nodeValue);
                output(ff_text_node.value); // undefined
            }
        );

        // y. 读写 text01 元素的value属性
        addElemClickById(
            "btny",
            function(){
                var text01 = document.getElementById("text01");
                output(text01.value);
                text01.value = "newValue";
            }
        );

        // x. 获取元素节点中的文本内容:innerText
        addElemClickById(
            "btnx",
            function(){
                var ul01 = document.getElementById("ul01");
                output(ul01.innerText);
            }
        );
    };

    function output(param){
        console.log(param);
    }
    ```

### 示例-dom基本操作的补充
[top](#catalog)
- 参考代码
    - [/javascript/base/src/dom/domOther.html](/javascript/base/src/dom/domOther.html)

- html内容
    ```html
    <body>
        <div class="box" id="abc">
            <div>
                1234qwer
            </div>
        </div>
    </body>
    ```

- js内容
    ```js
    window.onload = function(){
        // 1. 获取body对象
        var body01 = document.getElementsByTagName("body")[0];
        console.log(body01 == document.body);

        // 2. 获取html对象
        console.log(document.documentElement);

        // 3. 获取页面上的所有元素
        // 3.1 通过document的方法获取
        var all01 = document.getElementsByTagName("*");
        console.log("all01 = ", all01);
        console.log("all01.length = ", all01.length);
        console.log("typeof all01 = ", typeof all01);

        // 3.2 通过document的属性获取
        var all02 = document.all;
        console.log("all02 = ", all02);
        console.log("all02.length = ", all02.length);
        console.log("typeof all02 = ", typeof all02);

        // 4. 通过css选择器来获取元素
        var selector = document.querySelector(".box div");
        console.log(selector.innerHTML);

        // 5. 比较 querySelector 和 getElementById 获取的对象
        var abc01 = document.querySelector("#abc");
        var abc02 = document.getElementById("abc");
        console.log("abc01 == abc02 : ",abc01 == abc02);
    };
    ```

### 示例-颜色切换
[top](#catalog)
- 参考代码
    - [/javascript/base/src/dom/changeColor.html](/javascript/base/src/dom/changeColor.html)

- html内容
    ```html
     <body>
        <div class="outter">
            <div class="colorBox"></div>
            <button id="prev">prev</button>
            <button id="next">next</button>
        </div>
    </body>
    ```
- css内容
    ```css
    .outter{
        width: 200px;
        border:black 1px solid;
        margin: 10px auto;
        text-align: center;
    }
    .colorBox{
        width: 100px;
        height:100px;
        margin: 0px auto;
        background: #bfa;
    }
    ```
- js内容
    ```js
    window.onload = function(){
        var colorBox = document.querySelector(".colorBox");

        // 设置颜色信息
        var colorList = ["#bfa", "#47e","#fff", "#aaa"];
        var colorIndex= 0;

        // 设置前后按钮的事件
        var prev = document.getElementById("prev");
        var next = document.getElementById("next");
        
        prev.onclick = function(){
            if (colorIndex <= 0){
                return;
            }

            colorBox.style.background = colorList[--colorIndex];
        };

        next.onclick = function(){
            if (colorIndex < colorList.length ){
                colorBox.style.background = colorList[++colorIndex];
            }

        };
    };
    ```

### 示例-checkbox控制
[top](#catalog)
- 参考代码
    - [/javascript/base/src/dom/checkboxSelect.html](/javascript/base/src/dom/checkboxSelect.html)

- html内容
    ```html
    <body>
        <form action="" >
            please select : 
            <input type="checkbox" id="selectControl">全选/全不选
            <br>

            <input type="checkbox" name="selectBox" value="aa">aa
            <input type="checkbox" name="selectBox" value="bb">bb
            <input type="checkbox" name="selectBox" value="cc">cc
            <input type="checkbox" name="selectBox" value="dd">dd
            <br>

            <input type="button" id="selectAll" value="全选">
            <input type="button" id="unSelectAll" value="全不选">
            <input type="button" id="selectOther" value="反选">
            <input type="button" id="submit" value="提交">

        </form>
    </body>
    ```

- js内容
    ```js
    function addElemClickById(id, fn){
        var elem = document.getElementById(id);
        elem.onclick = fn;
    }

    window.onload = function(){
        var selectBox = document.getElementsByName("selectBox");
        var selectControl = document.getElementById("selectControl");

        // 1. 全选
        addElemClickById(
            "selectAll",
            function(){
                for (var i=0; i<selectBox.length; i++){
                    selectBox[i].checked = true;
                }

                // selectControl 与 selectBox 同步
                selectControl.checked = true;
            }
        );

        // 2. 全不选
        addElemClickById(
            "unSelectAll",
            function(){
                for (var i=0; i<selectBox.length; i++){
                    selectBox[i].checked = false;
                }

                // selectControl 与 selectBox 同步
                selectControl.checked = false;
            }
        );

        // 3. 反选
        addElemClickById(
            "selectOther",
            function(){
                var control = true;
                for (var i=0; i<selectBox.length; i++){
                    selectBox[i].checked = !selectBox[i].checked;

                    // selectControl 与 selectBox 同步
                    if (!selectBox[i].checked){
                        control=false;
                    }
                }

                selectControl.checked = control;
            }
        );

        // 3. submit，显示所有被选中选项的value信息
        addElemClickById(
            "submit",
            function(){
                var result = "";
                for(var i=0; i<selectBox.length; i++){
                    if (selectBox[i].checked){
                        result += selectBox[i].value + ", ";
                    }
                }

                result = result.replace(/,\s$/g, "");
                alert(result);
            }
        );

        // 4. 全选/全不选checkbox
        addElemClickById(
            "selectControl",
            function(){
                for(var i=0; i<selectBox.length; i++){
                    selectBox[i].checked = this.checked;
                }
            }
        );
        
        // 5. selectControl 与 selectBox 同步
        for (var i=0; i<selectBox.length; i++){
            selectBox[i].onclick = function(){
                for (var i=0; i<selectBox.length; i++){
                    if (!selectBox[i].checked){
                        selectControl.checked = false;
                        return;
                    }
                }

                selectControl.checked = true;
            }
        }
    };
    ```

## dom的增删改
[top](#catalog)



## 事件绑定
[top](#catalog)
- 通过将处理函数绑定到页面对象的事件，来设置事件的响应方式：`元素节点.事件 = 处理函数`

- window对象
    - onload 事件会在整个页面加载完成之后才触发
- 一般元素节点
    - onclick




