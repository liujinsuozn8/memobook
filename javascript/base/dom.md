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
        - [dom增删改的常用方法与属性](#dom增删改的常用方法与属性)
        - [示例-table操作](#示例-table操作)
    - [dom操作css](#dom操作css)
    - [DOMElement对象的一些常用属性与方法](#DOMElement对象的一些常用属性与方法)
- [事件绑定](#事件绑定)
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
        - `document` 下的方法，元素节点中都有，只是返回的是当前节点下的**子节点**
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

- 功能需求
    - 在颜色数组：`["#bfa", "#47e","#fff", "#aaa"]` 中进行切换
    - 点击 prev 按钮向前变化
    - 点击 next 按钮向后变化
    - 到达数组的上界或下界时，不做处理

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
- 功能需求
    - checkbox:全选/全不选
        - 【全选/全不选】 与 业务checkbox 同步
        - 点击 【全选/全不选】 时，业务checkbox保持全选/全不选的状态
        - 业务checkbox中，如果有未选择的，则【全选/全不选】变为**未选中**状态
        - 业务checkbox中，如果全部选择，则【全选/全不选】变为**选中**状态
    - button:全选
        - 业务checkbox 全部选中
    - button:全不选
        - 业务checkbox 全部不选择
    - button:反选
        - 反转业务checkbox的选中状态
    - button:提交
        - 弹出页面提示框，并显示所有已选中的业务checkbox的value

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
### dom增删改的常用方法与属性
[top](#catalog)
- 常用方法
    - `父节点.appendChild()`，向父节点中添加新的子节点
    - `父节点.removeChild()`，从父节点中删除某个子节点
    - `父节点.insertBefore(新节点, 目标节点)`，在目标节点之前添加新节点
    - `父节点.replaceChild(新节点, 旧节点)`，新节点替换就节点
    - `document.createElement("标签名")`，通过标签名创建元素节点
    - `document.createTextNode("文本内容")`，创建文本节点

- 通过 `innerHTML` 来进行子元素的增删改
    - 与常用方法的区别
        - 常用方法的修改内容只限于执行了增删改的元素
        - `innerHTML`的修改内容是 `innerHTML` 中的所有内容
            - 修改方式：`元素节点.innerHTML += xxxx;`
                - 这种修改方式的**性能低**
                - 这种修改该方式虽然和之前有相同的部分，也会被视作全部替换
                - <label style="color:red">如果原来的 innerHTML 中的节点上有绑定的事件，在替换后，绑定内容会消失，需要重新设置</label>

    - 一般会将 `innerHTML` 和 常用方法 结合使用
        - 通过常用方法创建子节点，通过 `innerHTML` 设置子节点的文本内容
        - 示例
            ```js
            // 创建一个节点
            var hh = document.createElement("li");
            hh.innerHTML = "hh";
            ```

- 一般会通过：`当前节点.parentNode.增删改方法()`，来获取父节点并进行增删该操作

- 示例
    - 参考代码
        - [/javascript/base/src/dom/domCUD.html](/javascript/base/src/dom/domCUD.html)
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
            </div>
        </div>
        ```
    
    - js内容
        ```js
        function addElemClickById(id, fn){
            var elem = document.getElementById(id);
            elem.onclick = fn;
        }

        window.onload = function(){
            // 1. 向 ul01 下添加子节点：<li>ee</li>
            addElemClickById(
                "btn01",
                function(){
                    // 创建子节点
                    var ee = document.createElement("li");
                    // 创建元素的文本节点
                    var ee_text = document.createTextNode("ee");
                    // 文本节点添加到子节点中
                    ee.appendChild(ee_text);
                    // 将子节点添加到父节点中
                    document.getElementById("ul01").appendChild(ee);
                }
            );

            // 2. 将子节点 ff 添加到 bb 之前
            addElemClickById(
                "btn02",
                function(){
                    // 创建元素
                    var ff = document.createElement("li");
                    ff.appendChild(document.createTextNode("ff"));

                    // 获取bb节点
                    var bb = document.querySelector("#aa + li");

                    // 将子节点添加到父节点中
                    document.getElementById("ul01").insertBefore(ff, bb);
                }
            );

            // 3. 创建子节点 gg 替换 cc
            addElemClickById(
                "btn03",
                function(){
                    // 创建 gg 节点
                    var gg = document.createElement("li");
                    gg.appendChild(document.createTextNode("gg"));

                    // 获取 cc 节点
                    var cc = document.querySelector("#aa ~ li:nth-of-type(3)")

                    // 替换节点
                    cc.parentNode.replaceChild(gg, cc);
                }
            );

            // 4. 删除dd节点
            addElemClickById(
                "btn04",
                function(){
                    // 获取dd节点
                    var dd = document.querySelector("#aa ~ li:nth-of-type(4)");
                    // 删除节点
                    dd.parentNode.removeChild(dd);
                }
            );

            // 5. 结合 innerHTML 和 常用方法 来添加 hh 节点
            addElemClickById(
                "btn05",
                function(){
                    // 创建 hh 节点
                    var hh = document.createElement("li");
                    hh.innerHTML = "hh";

                    // 添加节点
                    var ul01 = document.getElementById("ul01");
                    ul01.appendChild(hh);
                }
            );
        };
        ```

### 示例-table操作
[top](#catalog)
- 功能需求
    - 点击表格中的 delete 连接可以将当前行删除
    - 点击按钮后，读取表单中的数据，并创建一个 tr 节点
- 参考代码
    - [/javascript/base/src/dom/tableCRUD.html](/javascript/base/src/dom/tableCRUD.html)

- html内容
    ```html
    <table class="showBox">
        <tr>
            <th>name</th>
            <th>email</th>
            <th>salary</th>
            <th></th>
        </tr>
        <tr>
            <td>aaa</td>
            <td>aaa@email</td>
            <td>111</td>
            <td><a href="javascript:;">delete</a></td>
        </tr>
        <tr>
            <td>bbb</td>
            <td>bbb@email</td>
            <td>222</td>
            <td><a href="javascript:;">delete</a></td>
        </tr>
        <tr>
            <td>ccc</td>
            <td>ccc@email</td>
            <td>333</td>
            <td><a href="javascript:;">delete</a></td>
        </tr>
    </table>

    <form action="" class="addBox" autocomplete="off">
        <p>add:</p>
        name: <input type="text" name="name"><br>
        email: <input type="text" name="email"><br>
        salary: <input type="text" name="salary"><br>
        <input type="button" value="submitByElem" id="addBtn01">
        <input type="button" value="submitByHTML" id="addBtn02">

    </form>
    ```

- js内容
    ```js
    function hypelinkOnclick() {
        // 确认是否删除
        var tr = this.parentNode.parentNode;
        if (confirm("delete user :" + tr.children[0].innerHTML + " ?")){
            tr.parentNode.removeChild(tr);
        }
    }

    window.onload = function(){
        
        var a_list = document.querySelectorAll(".showBox a");

        // 1. 设置table中每个超链接的事件
        for(var i=0; i<a_list.length; i++){
            a_list[i].onclick = hypelinkOnclick;
        };

        // 2. 添加一行，通过创建元素来添加
        var addBtn01 = document.getElementById("addBtn01");
        addBtn01.onclick = function(){
            // 读取输入信息
            var name = document.querySelector(".addBox input[name='name']");
            var email = document.querySelector(".addBox input[name='email']");
            var salary = document.querySelector(".addBox input[name='salary']");
            
            // 创建 tr
            var td_name = document.createElement("td");
            td_name.innerHTML = name.value;

            var td_email = document.createElement("td");
            td_email.innerHTML = email.value;
            
            var td_salary = document.createElement("td");
            td_salary.innerHTML = salary.value;

            var td_delete = document.createElement("td");
            var td_delete_a = document.createElement("a")
            td_delete_a.href = "javascript:;";
            td_delete_a.innerHTML = "delete";
            // td_delete.innerHTML = '<a href="javascript:;">delete</a>';
            // 设置 超链接的 onclick 事件
            td_delete_a.onclick = hypelinkOnclick;
            td_delete.appendChild(td_delete_a);

            var new_tr = document.createElement("tr");
            new_tr.appendChild(td_name);
            new_tr.appendChild(td_email);
            new_tr.appendChild(td_salary);
            new_tr.appendChild(td_delete);

            // 将 tr 节点添加到 tobody下，与html自动生成的内容保持一致
            var addBox = document.querySelector(".showBox>tbody");
            addBox.appendChild(new_tr);
        };
        
        // 3. 添加一行，通过创建HTML来添加
        var addBtn02 = document.getElementById("addBtn02");
        addBtn02.onclick = function(){
            // 获取输入信息
            var name = document.querySelector(".addBox>input[name='name']");
            var email = document.querySelector(".addBox>input[name='email']");
            var salary = document.querySelector(".addBox>input[name='salary']");

            // 创建tr节点
            var tr = document.createElement("tr");
            // 创建子节点的html
            var td_html = "<td>" + name.value + "</td>" +
                        "<td>" + email.value + "</td>" +
                        "<td>" + salary.value + "</td>" +
                        "<td><a href='javascript:;'>delete</a></td>";
            tr.innerHTML = td_html;

            // 绑定超链接的点击事件
            tr.querySelector("a").onclick = hypelinkOnclick;

            // 添加td 到tbody下
            var showBox = document.querySelector(".showBox>tbody");
            showBox.appendChild(tr);

        };
    };
    ```

## dom操作css
[top](#catalog)
- 通过 `style` 属性操作元素节点的**内联样式**
    - 操作语法：
        - 读: `元素.style.样式名`
        - 写: `元素.style.样式名 = 样式值`
    - 读写操作的目标都是**内联样式**，无法读取样式表中的内容
    - 优先级
        - 因为内联样式的优先级比较高，所以通过js设置后，会立即生效
        - 如果样式中已经设置了 `!important`，则无法覆盖
    - 写操作的结果
        - 执行写操作后，会直接在当前的html标签中添加 `style` 属性


- **读取**元素节点**当前正在使用**的样式
    - 不限于内联、内部、外部中的任意一种，当前元素节点使用的是哪个位置的样式，就操作哪个
    - 当前正在使用的样式都是**只读**的
    - 方式1 ： 通过 `currentStyle`属性
        - <label style="color:red">只限IE浏览器可以使用</label>
        - 操作语法： `元素.currentStyle.样式名`
    
    - 方式2 ： 通过 `window.getComputedStyle(元素对象, null/为元素)` 方法
        - <label style="color:red">IE8及以下浏览器不兼容</label>
        - 第二个参数一般都会使用 null
        - 该方法位于 window 对象中，可以直接通过方法名来调用
        - 对于css属性值是 auto 的值，该方法会自动转换为真实值

    - 方式1 和 方式2 的兼容
        ```js
         var style = 元素节点.currentStyle || getComputedStyle(元素节点, null);
        ```

- 对于包含特殊字符 `-` 的css样式名，使用时需要将样式名转换为**驼峰命名法**
    - 如：`background-color` ---> `backgroundColor`

- 示例
    - 参考代码
        - [/javascript/base/src/dom/domCss.html](/javascript/base/src/dom/domCss.html)
    
    - html内容
        ```html
        <div class="box01"></div>
        <button id="change01">change01</button>
        <button id="read01">read01</button>
        <br>
        
        <div class="box2"></div> 
        <button id="read02">read02</button>
        ```

    - js内容
        ```js
        function addElemClickById(id, fn){
            var elem = document.getElementById(id);
            elem.onclick = fn;
        }

        window.onload = function(){
            // 写内联样式
            addElemClickById(
                "change01",
                function(){
                    var box01 = document.querySelector(".box01");
                    box01.style.width = "100px";
                    box01.style.backgroundColor = "#47e";
                }
            );

            // 读内联样式
            addElemClickById(
                "read01",
                function(){
                    var box01 = document.querySelector(".box01");
                    alert(box01.style.width);
                }
            );

            // 当前正在使用的样式是只读的，无法写
            // addElemClickById(
            //     "change02",
            //     function(){
            //         var box2 = document.querySelector(".box2");
                    
            //         var style = box2.currentStyle || getComputedStyle(box2, null);
            //         style.backgroundColor = "rgb(88,119,228)";
            //     }
            // );

            // 读当前元素正在使用的样式
            addElemClickById(
                "read02",
                function(){
                    var box2 = document.querySelector(".box2");
                    
                    var style = box2.currentStyle || getComputedStyle(box2, null);
                    alert(style.width);
                }
            );
        };
        ```

## DOMElement对象的一些常用属性与方法
[top](#catalog)
- 常用属性，这些属性都是只读的

    |属性名|返回内容|备注|
    |-|-|-|
    |clientHeight|<ul><li>元素的可见高度</li><li>content + padding*2 - 滚动条的宽度17px</li></ul>|返回一个数字，没有单位后缀|
    |clientWidth|<ul><li>元素的可见宽度</li><li>content+padding*2 - 滚动条的宽度17px</li></ul>|返回一个数字，没有单位后缀|
    |offsetHeight|<ul><li>元素的高度</li><li>content + padding*2 + border*2</li><ul>||
    |offsetWidth|<ul><li>元素的宽度</li><li>content + padding*2 + border*2</li></ul>||
    |offserParent|<ul><li>获取距离离当前元素最近的**开启了定位**的祖先元素</li><li>如果所有祖先元素都没有开启定位，则返回 `body`</li></ul>||
    |offserLeft|当前元素相对于**定位元素**的水平偏移量||
    |offsetTop|当前元素相对于**定位元素**的垂直偏移量||
    |scrollHeight|获取元素滚动区域的高度||
    |scrollWidth|获取元素滚动区域的高度||
    |scrollLeft|滚动条移动的水平距离||
    |scrollTop|滚动条移动的垂直距离||

- 当**一个带有滚动条的元素**满足下面两个公式时，表示滚动条滚动到最下侧/最右侧
    - `scrollHeight - scrollTop = clientHeight` 
    - `scrollWidth - scrollLeft = clientWidth`

- 示例
    - 参考代码
        - [/javascript/base/src/dom/domElementObj.html](/javascript/base/src/dom/domElementObj.html)

    - html内容
        ```html
        <section>1. 读取 clientXXXX 和 offsetXXX 属性</section>
        <div class="box1"></div> 
        <button id="read01">read01</button>
        <br>
        <br>

        <section>2. 获取box0204的 offsetParent</section>
        <div id="box0201" style="position: relative;">
            <div id="box0202" style="position: relative;">
                <div id="box0203">
                    <div id="box0204"></div>
                </div>
            </div>
        </div>
        <button id="read02">read02</button>
        <br>
        <br>

        <section>3. 分别获取box0303、box0302、box0301 的 offsetLeft offsetTop</section>
        <div id="box0301" style="position: relative; padding:100px; border:black 1px solid;">
            <div id="box0302" style="position: relative;">
                <div id="box0303"></div>
            </div>
        </div>
        <button id="read03">read03</button>
        <br>
        <br>
        
        <section>4. 获取box0401的 scrollHeight 和 scrollTop</section>
        <div id="box0401">
            <div id="box0402"></div>
        </div>
        <button id="read04">read04</button>
        <br>
        <br>

        <section>5. 测试滚动公式。当滚动条到底的时候，submit05按钮可用</section>
        <p id="info05">
            ...
        </p>
        <button id="submit05" disabled="disabled">submit05</button>    
        ```

    - js内容
        ```js
        // 1. 读取 clientXXXX 和 offset 属性
        addElemClickById(
            "read01",
            function(){
                var box1 = document.querySelector(".box1");
                alert(
                    "box1.clientHeight = " + box1.clientHeight + "\n" +
                    "box1.clientWidth = " + box1.clientWidth + "\n" +
                    "box1.offsetHeight = " + box1.offsetHeight + "\n" +
                    "box1.offsetWidth = " + box1.offsetWidth + "\n" +
                    "box1.offsetLeft = " + box1.offsetLeft + "\n" +
                    "box1.offsetTop = " + box1.offsetTop + "\n"
                );
            }
        );

        // 2. 获取box0204的 offserParent
        addElemClickById(
            "read02",
            function(){
                var box0204 = document.getElementById("box0204");
                alert(box0204.offsetParent.id);
            }
        );

        // 3. 分别获取box0303、box0302、box0301 的 offsetLeft offsetTop
        addElemClickById(
            "read03",
            function(){
                var box0303 = document.getElementById("box0303");
                var box0302 = document.getElementById("box0302");
                var box0301 = document.getElementById("box0301");
                alert(
                    "box0303.offsetLeft =" + box0303.offsetLeft + "\n" +
                    "box0303.offsetTop =" + box0303.offsetTop + "\n" +
                    "box0302.offsetLeft =" + box0302.offsetLeft + "\n" +
                    "box0302.offsetTop =" + box0302.offsetTop + "\n" +
                    "box0301.offsetLeft =" + box0301.offsetLeft + "\n" +
                    "box0301.offsetTop =" + box0301.offsetTop + "\n"
                );
            }
        );

        // 4. 获取box0401的 scrollHeight 和 scrollTop
        addElemClickById(
            "read04",
            function(){
                var box0401 = document.getElementById("box0401");
                alert(
                    "box0401.clientHeight = " + box0401.clientHeight + '\n' +
                    "box0401.clientWidth = " + box0401.clientWidth + '\n' +
                    "box0401.scrollHeight = " + box0401.scrollHeight + '\n' +
                    "box0401.scrollTop = " + box0401.scrollTop + '\n'
                );
            }
        );

        // 5. 测试滚动公式。当滚动条到底的时候，submit05按钮可用
        var info05 = document.getElementById("info05");
        // 绑定滚动条的滚动事件
        info05.onscroll = function(){
            var submit05 = document.getElementById("submit05");
            if (this.scrollHeight - this.scrollTop === this.clientHeight){
                submit05.disabled = false;
            } else {
                submit05.disabled = true;
            }
        };
        ```


## 事件绑定
[top](#catalog)
- 通过将处理函数绑定到页面对象的事件，来设置事件的响应方式：`元素节点.事件 = 处理函数`

- window对象
    - onload 事件会在整个页面加载完成之后才触发
- 一般元素节点
    - onclick

- 在事件的响应函数中返回 `return false;`，可以取消事件的默认行为



