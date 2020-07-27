<span id="catalog"></span>

### 目录
- [dom概述](#dom概述)
- [dom对象的包含关系](#dom对象的包含关系)
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

- [事件基础](#事件基础)
    - [DOMEvent事件对象](#DOMEvent事件对象)
        - [事件对象](#事件对象)
        - [示例-获取鼠标坐标](#示例-获取鼠标坐标)
        - [示例-div跟随鼠标移动](#示例-div跟随鼠标移动)
    - [事件的冒泡](#事件的冒泡)
        - [事件冒泡的基本原理](#事件冒泡的基本原理)
        - [示例-阻止元素跟随鼠标移动](#示例-阻止元素跟随鼠标移动)
        - [事件冒泡的应用-事件委派](#事件冒泡的应用-事件委派)
    - [事件绑定](#事件绑定)
    - [事件传播](#事件传播)
- [事件应用](#事件应用)
    - [元素拖拽](#元素拖拽)
    - [滚轮事件](#滚轮事件)
    - [键盘事件](#键盘事件)
- [浏览器默认行为](#浏览器默认行为)
- [类的操作](#类的操作)
- [其他](#其他)
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

# dom对象的包含关系
[top](#catalog)
- window对象
    - documnet对象: `window.document`，或直接使用 `document`
        - html对象：`document.documentElement`
            - 可以通过 `document.documentElement.getElementsByTagName("body")[0];`，以获取子节点的方式获取 body对象
        - body对象：`document.body`
            - 页面中的其他对象 `document.getXXXX(...)`

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

        - 获取html对象，`document.documentElement`
        - 获取页面中的所有元素
            - 方法1：`document.getElementsByTagName("*")`
            - 方法2：`document.all`
                - 该属性的bug：`typeof document.all`，会返回 undefined


- 获取元素节点的子对象
    - 常用方法
        - <label style="color:red">`document` 下的获取元素的方法，在元素对象中都可以使用，只是返回的是当前节点下的**子节点** </label>
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

    |增删改方法|功能|
    |-|-|
    |`父节点.appendChild()`|向父节点中添加新的子节点|
    |`父节点.removeChild()`|从父节点中删除某个子节点|
    |`父节点.insertBefore(新节点, 目标节点)`|在目标节点之前添加新节点|
    |`父节点.replaceChild(新节点, 旧节点)`|新节点替换就节点|
    |`当前节点.remove()`|删除节点自身|
    |`document.createElement("标签名")`|通过标签名创建元素节点|
    |`document.createTextNode("文本内容")`|创建文本节点|


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

            // 6. 删除当前按钮
            addElemClickById(
                "btn06",
                function(){this.remove()}
            )
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

- 通过 `style` 设置内联样式的缺点以及解决方式
    - 缺点
        - 每次使用该方式设置元素的样式时，浏览器都需要重新渲染一次画面，执行的性能比较差
        - 修改多个样式时很不方便
        - 使css与js耦合，即表现与动作耦合
    - 解决方法
        - 将样式封装到css的类选择器中。修改时，通过修改class来修改样式
            - 这种方式只会重新渲染一次页面
        - 参考：
            - [类的操作](#类的操作)

- **读取**元素节点**当前正在使用**的样式
    - 不限于内联、内部、外部中的任意一种，当前元素节点使用的是哪个位置的样式，就操作哪个
    - 当前正在使用的样式都是<label style='color:red'>只读的</lable>

    - 不同浏览器版本的使用方法

        ||其他浏览器|IE浏览器|
        |-|-|-|
        |使用方法|`window.getComputedStyle(元素对象, null/伪元素)`|`元素.currentStyle`|
        |参数说明|第二个参数一般都会使用 null|-|
        |auto属性值|自动将 `auto` 转换为真实值|直接返回 `auto`|
        |备注|<label style="color:red">IE8及以下浏览器不兼容</label>||

    - 不同浏览器版本的兼容
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
- 在DOM中，每个Element对象都代表一个html标签
- Element对象中的方法和属性，每个元素都可以使用
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
    |scrollWidth|获取元素滚动区域的宽度||
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


# 事件基础
## DOMEvent事件对象
### 事件对象
[top](#catalog)
- 当事件的响应函数被触发时，浏览器**每次**都会将一个事件对象作为实参传递给响应函数
    - 无论响应函数中是否有形参，浏览器都会传递事件对象
    - 如果函数没有形参，可以通过 `arguments` 来获取；如果有形参，则第一个形参默认为事件对象

- 事件对象中封装了与当前事件相关的一切信息
    - 信息包括：鼠标的坐标、哪个键盘按钮被按下、鼠标滚轮滚动的方向

- IE8及以下版本浏览器的兼容
    - IE8及以下版本的浏览器**不会**向响应函数中传递事件对象
    - 在IE8及以下版本的浏览器中，事件对象是 `window` 对象的一个属性
    - 兼容处理
        ```js
        event = event || window.event;
        ```

- 常用属性
    - 属性及其含义

        |属性名|含义|备注|
        |-|-|-|
        |clientX|相对于可见窗口的鼠标水平坐标|该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |clientY|相对于可见窗口的鼠标垂直坐标|该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |offsetX|相对于元素左上角鼠标的水平坐标|该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |offsetY|相对于元素左上角鼠标的垂直坐标|该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |pageX|相对于当前页面的鼠标水平坐标|IE8及以下不兼容<br>该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |pageY|相对于当前页面的鼠标垂直坐标|IE8及以下不兼容<br>该属性只返回数值<br><label style="color:red">如果要用来设置css，需要加`px`</label>|
        |target|触发事件的真实对象|在事件冒泡中，可以辅助查找真实调用对象<br>参考：[事件冒泡的应用-事件委派](#事件冒泡的应用-事件委派)|
        |wheelDelta|鼠标滚轮的滚动方向<br>向上滚是正数，向下滚是负数<br>该属性一般只关系正负，不关系大小|firefox中没有该属性，使用`detail`替代<br>参考：[滚轮事件](#滚轮事件)|
        |detail|鼠标滚轮的滚动方向<br>向上滚是负数，向下滚是正数<br>该属性一般只关系正负，不关系大小|**firefox专用属性**，用来替代 `wheelDelta`|
    
    - pageX 与 pageY 的兼容方法
        - 鼠标在可见窗口的坐标 + 页面滚动条的滚动距离
        - 滚动条产生是由于`<body>`的 height 属性过大，`<html>`标签无法容纳产生了滚动条，所以需要通过 `<html>` 对象来获取滚动条的移动距离
            ```js
            event.clientX + document.documentElement.scrollLeft
            event.clientY + document.documentElement.scrollTop
            ```

### 示例-获取鼠标坐标
[top](#catalog)
- 功能需求
    - 两个区域：box01、box02
    - 在box01中移动鼠标时，在box02中显示鼠标的坐标
- 参考代码
    - [/javascript/base/src/dom/domEvent/mouseCoordinate.html](/javascript/base/src/dom/domEvent/mouseCoordinate.html)

- html内容
    ```html
    <div id="box01"></div>
    <div id="box02"></div>
    ```

- js内容
    ```js
    window.onload = function(){
        var box01 = document.getElementById("box01");
        var box02 = document.getElementById("box02");
        box01.onmousemove = function(event){
            event = event || window.event;
            box02.innerHTML = "x = " + event.clientX + 
                                ", y = " + event.clientY;
        };
    };
    ```

### 示例-div跟随鼠标移动
[top](#catalog)
- 参考代码
    - [/javascript/base/src/dom/domEvent/divMoveWithMouse.html](/javascript/base/src/dom/domEvent/divMoveWithMouse.html)

- html内容
    ```html
    <div id="box01">pageX</div>
    <div id="box02"> <br> clientX</div>
    ```

- css内容
    ```css
    body{
        height: 1000px;
    }
    #box01{
        width: 50px;
        height: 50px;
        background-color: #bfa;
        position: absolute;
    }
    
    #box02{
        width: 50px;
        height: 50px;
        background-color: rgba(68, 119, 238, 0.5);
        position: absolute;
        clear:both;
    }
    ```

- js内容
    ```js
    window.onload = function(){
        var box01 = document.getElementById("box01");
        var box02 = document.getElementById("box02");
        document.onmousemove = function(event){
            event = event || widnow.event;

            // box01 完全跟随鼠标移动
            // box01.style.left = event.pageX + "px";
            // box01.style.top = event.pageY +  "px";
            box01.style.left = event.clientX + document.documentElement.scrollLeft + "px";
            box01.style.top = event.clientY + document.documentElement.scrollTop +  "px";
            
            // box02 相对于
            box02.style.left = event.clientX + "px";
            box02.style.top = event.clientY +  "px";
        };
    };
    ```

## 事件的冒泡
### 事件冒泡的基本原理
[top](#catalog)
- 事件的冒泡是指：事件的向上传导
    - 当后代元素上的事件被触发时，其祖先元素的**相同事件**也会被触发
    - 冒泡会从当前元素一直向上传导，到达 `<html>` 后，会再传导到 `document` 结束

- 事件冒泡的使用场景
    - 一般开发中的事件冒泡都是有价值的
    - 如：页面元素跟随鼠标移动
        - 该操作本身绑定的是 `document`

- 取消事件冒泡
    - 将**事件对象**的 `cancelBubble` 属性设为 true 来取消事件冒泡
        ```js
        event.cancelBubble = true;
        ```
    - 执行`event.stopPropagation()`
    - `event.stopPropagation()` 与 `event.cancelBubble` 的不同点
        - `stopPropagation` 方法属于W3C标准，但是IE浏览器不兼容
        - `cancelBubble` 不属于W3C标准`，开始只有IE浏览器支持，现在部分浏览器也开始支持

- 示例
    - [/javascript/base/src/dom/domEvent/eventBubble.html](/javascript/base/src/dom/domEvent/eventBubble.html)

    - html内容
        ```html
        <div id="box01">
            this is box01
            <span id="span01">this is span01</span>
        </div>
        ```

    - js内容
        ```js
        function addElemClickById(id, fn){
            var elem = document.getElementById(id);
            elem.onclick = fn;
        }

        window.onload = function(){
            // 默认事件冒泡
            addElemClickById(
                "box01",
                function(){
                    alert("this is box01");
                }
            );
                
            // 取消事件冒泡
            addElemClickById(
                "span01",
                function(event){
                    alert("this is span01");
                    event.cancelBubble = true;
                }
            );
                
            // 默认事件冒泡
            document.body.onclick = function(){
                alert("this is body");
            };
        };
        ```

### 示例-阻止元素跟随鼠标移动
[top](#catalog)
- 功能需求
    - 两个块元素：box01、box02
    - box02跟随鼠标移动
    - box01阻止事件冒泡，当鼠标移动到box01内部时，阻止box02跟随鼠标移动

- 参考代码
    - [/javascript/base/src/dom/domEvent/cancelBubbleSample.html](/javascript/base/src/dom/domEvent/cancelBubbleSample.html)
    - js内容
        ```js
        var box01 = document.getElementById("box01");
        var box02 = document.getElementById("box02");

        // 在box01中通过阻止事件冒泡来组织元素跟随鼠标移动
        box01.onmousemove = function(event){
            event.cancelBubble = true;
        };

        // 元素box02跟随鼠标移动
        document.onmousemove = function(event){
            box02.style.left = event.clientX + document.documentElement.scrollLeft + "px";
            box02.style.top = event.clientY + document.documentElement.scrollTop + "px";
        };
        ```

### 事件冒泡的应用-事件委派
[top](#catalog)
- [示例-table操作](#示例-table操作) 中的问题
    - 初始化时，需要获取所有的超链接元素，并为每个超链接元素绑定 `onclick` 事件
    - 当点击按钮添加一行数据时，需要要为新的超链接 `onclick` 事件
    - 为每个元素绑定事件的效率比较低

- 事件委派的思路：只绑定一次事件，就能够应用到多个元素上，即使元素是后添加的
- 事件委派的实现：
    - 将为多个元素绑定的事件删除，然后将事件绑定到所有元素的祖先元素中
    - 当元素的事件被触发时，通过**事件冒泡**将事件传递到祖先元素，然后触发事件

- 事件委派的优点
    - 事件委派利用了**事件冒泡**，避免的重复绑定元素事件，提供了性能

- 事件委派的缺点
    - 被绑定事件的祖先元素下的素有元素都可以触发事件委派，需要事件处理函数中**过滤事件触发对象**

- 示例
    - 功能需求
        - 初始化时，不为每个超链接绑定事件，直接将事件绑定到父元素中
        - 点击按钮，添加一个新的超链接，不绑定事件，通过事件委派使用父元素中的事件处理
        - 在元素的实现响应中，过滤事件的触发对象，保证只有点击 `<a>` 触发的事件有效
        - 事件触发后，在页面提示框中显示超链接中的文本内容
    - 参考代码
        - [/javascript/base/src/dom/domEvent/eventDelegate.html](/javascript/base/src/dom/domEvent/eventDelegate.html)

    - html内容
        ```html
        <button id="btn01">add btn</button>

        <ul id="ul01">
            <li><a href="javascript:;">hype 1</a></li>
            <li><a href="javascript:;">hype 2</a></li>
            <li><a href="javascript:;">hype 3</a></li>
        </ul>
        ```

    - js内容
        ```js
        var ul01 = document.getElementById("ul01");

        // 添加事件委派
        ul01.onclick = function(event){
            // 过滤事件触发对象
            if (event.target.tagName.toLowerCase() == "a") {      
                alert(event.target.innerText + "x");
            }
        };

        // 点击按钮后向 ul01 中添加节点
        var btn01 = document.getElementById("btn01");
        btn01.onclick = function(){
            var new_li = document.createElement("li");
            new_li.innerHTML = "<a href='javascript:;'> hype " +
                                (ul01.children.length + 1 ) +
                                "</a>";
            ul01.appendChild(new_li);
        };
        ```

## 事件绑定
[top](#catalog)
- 事件绑定方法
    - 绑定一个处理函数
        - 绑定方法：`元素.事件 = 处理函数`
        - 该方法只能一种事件绑定**一个**处理函数
        - 如果执行多次绑定，则后面的会覆盖前面的
        
    - 绑定多个处理函数

        ||IE8以上|IE8及以下|
        |-|-|-|
        |绑定方法|`元素.addEventListener(eventName, callback [,useCapture])`|`元素.attachEvent(eventName, callback)`|
        |参数说明|<ul><li>`eventName`: 事件名字符串<ul><li>不需要写开头的 `on`</li><li>如： `onclick`事件，只写 `click`</li></ul></li><li>`callback`: 回调函数</li><li>`useCapture`: 是否在捕获阶段触发事件<ul><li>bool型参数，可选参数</li><li>一般都使用 false</li></ul></li></ul>|<ul><li>`eventName`: 事件名字符串<ul><li>需要写开头的 `on`</li></ul></li><li>`callback`: 回调函数</li></ul>|
        |函数执行顺序|按照绑定顺序**顺序执行**|按照绑定顺序**逆序执行**|
        |this对象|绑定事件的元素对象|`window` 对象|

    - 绑定多个处理函数 的兼容方法
        ```js
        function mybind(obj, eventName, callback){
            if (obj.addEventListener) {
                obj.addEventListener(eventName, callback, false);
            } else if (obj.attachEvent) {
                obj.attachEvent(
                    "on"+eventName,
                    function(){callback.call(obj);} // 调整this对象为当前元素对象
                );
            }
        }
        ```

- **如果同时使用两种方法绑定事件，绑定的事件会同时存在，并按绑定顺序顺次执行**
    - 绑定方法1 `元素.事件 = 处理函数` 的特性不变，仍然是后面的覆盖前面的

- 一般情况下多个绑定事件是功能分离的，所以对于事件的执行顺序不会有严格的要求

- 有一些特殊的事件只能通过 方式2 来绑定处理函数
    - ?????

- 示例
    - 参考代码
        - [/javascript/base/src/dom/domEvent/eventBind.html](/javascript/base/src/dom/domEvent/eventBind.html)
    
    - html内容
        ```html
        <button id="btn01">btn01</button>
        <br>
        <button id="btn02">btn02</button>
        <br>
        <button id="btn03">btn03</button>
        <br>
        <button id="btn04">btn04</button>
        <br>
        <button id="btn0501">btn0501</button>
        <br>
        <button id="btn0502">btn0502</button>
        ```

    - js内容
        ```js
        window.onload = function(){
            // 1. 只为事件绑定一个方法：元素对象.事件 = 处理函数
            var btn01 = document.getElementById("btn01");

            // 绑定多个事件，后面的会覆盖前面的
            btn01.onclick = function(){
                alert("btn01_click01");
            };
            btn01.onclick = function(){
                alert("btn01_click02");
            };

            // 2. 使用 addEventListener 为btn02绑定多方法 IE8以上
            // var btn02 = document.getElementById("btn02");
            // btn02.addEventListener(
            //     'click',
            //     function(){alert("btn02_click01");},
            //     false
            // );
            // btn02.addEventListener(
            //     'click',
            //     function(){alert("btn02_click02");},
            //     false
            // );

            // 3. 使用 attachEvent 为btn03绑定多方法 IE8及以下
            // var btn03 = document.getElementById("btn03");
            // btn03.attachEvent(
            //     "onclick",
            //     function(){alert("btn03_click01");}
            // );
            // btn03.attachEvent(
            //     "onclick",
            //     function(){alert("btn03_click02");}
            // );

            // 4. 通过兼容方法，为btn04绑定多个处理函数
            mybind(
                document.getElementById("btn04"),
                "click",
                function(){
                    alert("btn04_click01, this =" + this);
                }
            );
                
            mybind(
                document.getElementById("btn04"),
                "click",
                function(){
                    alert("btn04_click02, this =" + this);
                }
            );
            
            // 5. 同时使用两种方法为同一个元素绑定方法: 
            // 5.1 先使用 addEventListener 绑定， 再使用 onclick 绑定
            var btn0501 = document.getElementById("btn0501");
            mybind(
                btn0501,
                "click",
                function(){alert("btn0502_click03");}
            );

            mybind(
                btn0501,
                "click",
                function(){alert("btn0501_click04");}
            );

            btn0501.onclick = function(){
                alert("btn0501_click01");
            };

            btn0501.onclick = function(){
                alert("btn0501_click02");
            };

            // 5.2 先使用 onclick 绑定，再使用 addEventListener 绑定
            var btn0502 = document.getElementById("btn0502");
            btn0502.onclick = function(){
                alert("btn0502_click01");
            };

            btn0502.onclick = function(){
                alert("btn0502_click02");
            };

            mybind(
                btn0502,
                "click",
                function(){alert("btn0502_click03");}
            );

            mybind(
                btn0502,
                "click",
                function(){alert("btn0502_click04");}
            );
        };

        // 事件绑定兼容方法
        function mybind(obj, eventName, callback){
            if (obj.addEventListener) {
                obj.addEventListener(eventName, callback, false);
            } else if (obj.attachEvent) {
                obj.attachEvent(
                    "on"+eventName,
                    function(){callback.call(obj);} // 调整this对象为当前元素对象
                );
            }
        }
        ```

## 事件传播
[top](#catalog)
- 两种不同的事件传播的理解
    - 微软：由内向外传播、事件冒泡
        - 事件触发时，先触发当前元素的事件，然后再向各级祖先元素传递
    - 网景：由外向内传播、事件捕获
        - 事件触发时，先触发当前元素的最外层的祖先元素的事件，然后再向个子元素传播

- **w3c标准: 事件传播的三个阶段**

    |阶段|名称|描述|备注|
    |-|-|-|-|
    |1|事件捕获|方向：从外向内<br>从最外层的祖先元素开始，向目标元素进行事件的捕获，但是默认不会触发事件|最外层元素是什么? <br>w3c的标准是document，但是大部分浏览器都设计成从window对象开始|
    |2|目标阶段|事件捕获到目标元素。捕获结束时，触发目标的事件||
    |3|冒泡阶段|方向：从内到外<br>事件从目标元素向各级祖先元素传递，并依次触发每个祖先元素的事件||

- **在IE8及以下版本的浏览器中，没有事件捕获**

- 可以通过 `addEventListener(eventName, callback, true)`，将第三个参数设置为 true，使事件在捕获阶段被触发

- 示例
    - 参考代码
        - [/javascript/base/src/dom/domEvent/eventNotify.html](/javascript/base/src/dom/domEvent/eventNotify.html)
    - html内容
        ```html
        <div id="box01"> box01
            <div id="box02"> box02
                <div id="box03"> box03 </div>
            </div>
        </div>
        ```
    - js内容
        ```js
        var box01 = document.getElementById("box01");
        var box02 = document.getElementById("box02");
        var box03 = document.getElementById("box03");

        /*
            设置事件都在捕获阶段被触发
            点击box03后的输出顺序为：
            this is box01
            this is box02
            this is box03
        */
        box01.addEventListener(
            "click",
            function(){alert("this is box01")},
            true    
        )

        box02.addEventListener(
            "click",
            function(){alert("this is box02")},
            true    
        )

        box03.addEventListener(
            "click",
            function(){alert("this is box03")},
            true    
        )
        ```

# 事件应用
## 元素拖拽
[top](#catalog)
- 实现思路
    1. 当元素被点击时，即触发 `onmousedown` 事件时，为 document 绑定鼠标移动事件 `onmousemove`
    2. 不松开鼠标并移动鼠标，在 1 中绑定的`onmousemove`事件被触发，元素跟随鼠标移动，产生拖拽效果
    3. 当鼠标松开时，触发 document 的 `onmouseup` 事件，从 document 中删除鼠标移动事件
        - 为了保证鼠标移动到任何元素的区域内都能删除`onmousemove`事件，必须将`onmouseup`事件设置在 document 中，利用事件冒泡删除事件
    4. 删除 `onmousemove` 事件之后，document 的 `onmouseup` 不再有任何意义，将 `onmouseup` 事件也删除

- 注意事项
    1. `onmousedown`、`onmouseup` 是成对出现的，需要在元素的 `onmousedown` 事件中同时设置。保证处理函数只在鼠标拖拽的时候有效
        - `onmouseup` 必须要在 `onmousedown` 内部设置。
            - 因为 `onmouseup` 会将自身删除，如果在外部设置，会导致 `onmouseup` 事件变为**一次性事件**

    2. 元素拖拽时可能会被浏览器默认行为干扰，需要手动取消
        - 参考：[浏览器默认行为](#浏览器默认行为)


- 示例
    - 参考代码
        - [/javascript/base/src/dom/eventApplication/elemDrap.html](/javascript/base/src/dom/eventApplication/elemDrap.html)

    - html内容
        ```html
        <div id="box01"></div>
        <div id="box02"></div>
        ```

    - css内容
        ```css
        #box01{
            width: 50px;
            height: 50px;
            background-color: #ccc;
            /* position: relative; */
            position: absolute;
        }   

        #box02{
            width: 50px;
            height: 50px;
            background-color: #bfa;
            /* position: relative; */
            position: absolute;
            top:100px;
        }   
        ```

    - js内容
        ```js
        window.onload = function(){
            // 分别为 box01 和 box02 绑定拖拽事件
            var box01 = document.getElementById("box01");
            drap(box01);

            var box02 = document.getElementById("box02");
            drap(box02);
        };
        
        function drap(obj){
            // 点击鼠标时，绑定鼠标移动事件
            // 鼠标移动，并且鼠标未放开
            // 放开鼠标，删除鼠标移动事件
            // 1. 在 box01 内部按下按钮
            obj.onmousedown = function(event){
                var obj_mouse_click_offsetX = event.offsetX
                var obj_mouse_click_offsetY = event.offsetY
                
                // 2. 设置鼠标的移动事件
                document.onmousemove = function(event){
                    // 减去鼠标点击时的偏移量，使鼠标移动时，元素与鼠标的相对位置不变
                    obj.style.left = event.pageX - obj_mouse_click_offsetX + "px";
                    obj.style.top = event.pageY - obj_mouse_click_offsetY + "px";
                };

                // 3. 在任意位置放开鼠标时，将鼠标的移动事件删除
                document.onmouseup = function(){
                    document.onmousemove = null;

                    // 该事件与box01.onmousedown是成对的，当鼠标放开时，该方法
                    // 不再有任何意义，需要删除
                    document.onmouseup = null;
                };

                // 取消浏览器
                return false;
            };
        }
        ```

## 滚轮事件
[top](#catalog)

- 功能需求
    - 页面上有一个块元素
    - 将鼠标放在块元素内部时，同时向下滑动鼠标滚轮时，元素的长度变长
    - 将鼠标放在块元素内部时，同时向上滑动鼠标滚轮时，元素的长度变短

- 通过 `元素.onmousewheel` 设置鼠标滚轮滚动的事件
    - firefox不兼容该事件
    - firefox的兼容方法
        - `元素.addEventListener("DOMMouseScroll", callback)`

- 通过`event.wheelDelta`属性的正负来判读是向上滚动还是向下滚动
    - 参考：[事件对象](事件对象)
    - 正数向上滚动，负数向下滚动
    - firefox浏览器需要使用 `event.detail` 来判断。正数向下滚动，负数向上滚动

- 注意事项
    - 鼠标滚轮滚动时会被浏览器的默认行为干扰，需要手动取消
        - 参考:[浏览器默认行为](#浏览器默认行为)

- 示例
    - 参考代码
        - [/javascript/base/src/dom/eventApplication/mouseWheel.html](/javascript/base/src/dom/eventApplication/mouseWheel.html)

    - html内容
        ```html
        <div id="box01"></div>
        ```
    
    - css内容
        ```css
        body{
            height: 1000px;
        }

        #box01{
            width: 50px;
            height: 50px;
            background-color: #eee;
        }
        ```

    - js内容
        ```js
        window.onload = function(){
            var box01 = document.getElementById("box01");
            
            box01.onmousewheel = function(event){
                if (event.wheelDelta > 0) {
                    if (box01.clientHeight > 20){
                        // 向上滚动
                        box01.style.height = box01.clientHeight - 10 + "px";
                    }
                } else {
                    // 向下滚动
                    box01.style.height = box01.clientHeight + 10 + "px";
                }

                // 取消浏览器的默认行为
                event.preventDefault();
            };
        };
        ```

## 键盘事件
[top](#catalog)
- 键盘事件一般会绑定给**可以获取焦点的元素对象，或者是 documnet 对象**
    - 如：按钮、input输入框、redio button、checkbox、下拉列表 等等
    - 像 `<div>` 这中元素一般不会绑定键盘事件
        - 无法定义 `<div>` 这中元素在什么状态下是 获取到了焦点

- 键盘事件

    |事件名|描述|备注|
    |-|-|-|
    |onkeydown|某个键盘按钮被按下|<ul><li>如果键盘按钮一直被按下不松开，则该事件会被连续触发</li><li>浏览器的防误操作设计<ul><li>当该事件被连续触发时，第一次和第二次之间会有一点延迟，后面的就没有问题</li><li>通过第一次和第二次之间的延迟，保证一次键盘按下的事件不会被识别为多次</li></ul></li></ul>|
    |onkeyup|某个键盘按钮被松开||
    ||||

- 通过 `event` 对象获取键盘按下时的按钮信息

    |属性|描述|备注|
    |-|-|-|
    |keyCode|按钮编码||
    |altKey|alt是否被按下<br>被按下返回true，没按下返回false|识别组合键时，用来单独判断使用|
    |ctrlKey|ctrl是否被按下<br>被按下返回true，没按下返回false|识别组合键时，用来单独判断使用|
    |shiftKey|shift是否被按下<br>被按下返回true，没按下返回false|识别组合键时，用来单独判断使用|
    ||||

- 示例
    - 参考代码
        - [/javascript/base/src/dom/eventApplication/keyboardEvent.html](/javascript/base/src/dom/eventApplication/keyboardEvent.html)
    
    - 1、进制文本框输入数字
        - html内容
            ```html
            <input type="text" id="txt01">
            ```
        - js内容
            ```js
            // 1. 禁止 txt01 输入数字
            var txt01 = document.getElementById("txt01");
            txt01.onkeydown = function(event){
                if (
                    (event.keyCode >= 48 && event.keyCode <= 57)||
                    (event.keyCode >= 96 && event.keyCode <= 105)

                ){
                    return false;
                }
            };
            ```
    - 2、按方向键移动元素
        - html内容
            ```html
            <div id="box01"></div>
            ```

        - css内容
            ```css
            #box01{
                width: 50px;
                height: 50px;
                background-color: #ccc;
                position:absolute;
            }            
            ```

        - js内容
            ```js
            var box01 = document.getElementById("box01");
            document.addEventListener(
                "keydown",
                function(event){
                    switch (event.keyCode){
                        // 左 37
                        case 37: 
                            box01.style.left = box01.offsetLeft - 10 + "px";
                            break;
                        // 上 38
                        case 38:
                            box01.style.top = box01.offsetTop - 10 + "px";
                            break;
                        // 右 39
                        case 39:
                            box01.style.left = box01.offsetLeft + 10 + "px";
                            break;
                        // 下 40
                        case 40:
                            box01.style.top = box01.offsetTop + 10 + "px";
                            break;
                    }
                }
            );
            ```

# 浏览器默认行为
[top](#catalog)
- 浏览器默认行为
    
    |行为|描述|备注|
    |-|-|-|
    |元素拖拽|当拖拽一个网页中的内容时，浏览器会默认到搜索引擎中搜索内容|这种 默认行为 可能会导致拖拽功能的异常|
    |鼠标滚轮滚动|如果页面有滚动条，鼠标在某个元素上滚轮滚动时，会触发浏览器的默认事件，整个页面也会同时滚动||
    |键盘按下|当在文本框中按下键盘时，一些按钮的内容会输入到文本框中||
    |submit按钮按下|form的submit按钮按下后，会**自动提交表单**||
    
- 取消默认行为
    - 两种取消方法
        1. `return false;`
        2. `event.preventDefault()`
            - **该方法不兼容IE8及以下**

    - 对于不同的事件绑定方法
        - `元素.事件 = 处理函数`
            - 可以使用 `return false;` 或 `event.preventDefault()` 取消默认行为
        - `addEventListener`        
            - 只能使用 `event.preventDefault()` 取消默认行为

# 类的操作
[top](#catalog)
- 问题引入
    - css 修改方式： `obj.style.css样式名 = 样式值` 的问题
        - 每次使用该方式设置元素的样式时，浏览器都需要重新渲染一次画面，执行的性能比较差
        - 修改多个样式时很不方便
        - 使css与js耦合，即表现与动作耦合

    - 优化方式：通过 class 来修改样式
        - 将多个样式封装到一个css中，然后通过设置class来设置样式，即可同时设置多个样式
        - 无论css中封装了多少个样式，浏览器**只会重新渲染页面一次**，性能比较好
        - 通过这种方式可以进一步分离 css与js

- 最简单的类操作方法:
    - 操作代码
        ```js
        obj.className = "指定类名";
        ```
    - 这种方式的问题
        - 会使用指定类名直接覆盖原有的类名
        - 大多数情况下会希望保留原有类名，并在此基础上添加或删除其他类
        - 这种方式开发时使用的比较少

- 将类的：增、删、查操作封装为函数
    - 判断一个元素是否包含某个属性
        ```js
        function hasClassName(obj, cn){
            var regExp = new RegExp("\\b" + cn + "\\b");
            return regExp.test(obj.className);
        }
        ```
    - 为一个元素添加指定的 class
        ```js
        function addClassName(obj, cn){
            // 取出元素的class，检查 cn 是否已经存在
            // 如果不包含，则添加；已包含则跳过
            if ( !hasClassName(obj, cn) ){
                obj.className += " " + cn;
            }
        }
        ```
    - 从元素中删除指定的 class，即将匹配结果替换为空字符串
        ```js
        function removeClassName(obj, cn){
            // 删除时，不需要判断元素是否包含指定的class
            // 直接进行替换，有就替换，没有就跳过
            var regExp = new RegExp("\\b" + cn + "\\b");
            obj.className = obj.className.replace(regExp, "");
        }
        ```
    - 切换元素中的指定类：如果元素中未包含指定类，就添加；如果元素中包含指定类，就删除
        ```js
        function toggleClassName(obj, cn){
            var regExp = new RegExp("\\b" + cn + "\\b");
            if (regExp.test(obj.className)){
                // 如果元素中包含指定类，就删除
                obj.className = obj.className.replace(regExp, "");
            } else {
                // 如果元素中未包含指定类，就添加
                obj.className += " " + cn;
            }
        }
        ```

- 示例
    - 参考代码
        - [/javascript/base/src/dom/class/classOperator.html](/javascript/base/src/dom/class/classOperator.html)

    - 功能
        - change01 按钮：直接将 div 的class替换为box02
        - add box3 按钮：向 div 的class中添加新的类: box03
        - remove box3 按钮，从 div 中将类: box03 删除
        - toggle box3 按钮，点击按钮后，判读 div 中是否包含类: box03，如果包含就删除，入股不包含就添加
    
    - 类的增、删、查分别使用上面封装的函数来执行

    - 代码内容
        - html内容
            ```html
            <button id="changeBtn01">change01</button>
            <button id="addBtn">add box3</button>
            <button id="removeBtn">remove box3</button>
            <button id="toggleBtn">toggle box3</button>
            <div id="box" class="box01"></div>
            ```

        - css内容
            ```css
            .box01{
                width: 50px;
                height:50px;
                background-color: #ccc;
            }

            .box02{
                width: 70px;
                height: 70px;
                background-color: #bfa;
            }

            /* 添加删除测试使用，width默认使用原始class中的值，而不是使用auto */
            .box03{
                height: 70px;
                background-color: #ebc;
            }
            ```

        - js内容
            ```js
            var box = document.querySelector("#box");

            // 1. 通过class直接替换样式
            var changeBtn01 = document.getElementById("changeBtn01");
            changeBtn01.onclick = function(){
                box.className = "box02";
            };

            // 2. 在原有class的基础上附加其他的样式
            var addBtn = document.getElementById("addBtn");
            addBtn.onclick = function(){
                // 每次点击都会添加一次 box03
                // box.className += " box03";
                // 通过方法来添加class，每次添加前自动检查类是否已经存在
                addClassName(box, "box03");
            };

            // 3. 从元素的class中删除指定的class
            var removeBtn = document.getElementById("removeBtn");
            removeBtn.onclick = function(){
                removeClassName(box, "box03");
            };

            // 4. 切换元素中的类 box3
            var toggleBtn = document.getElementById("toggleBtn");
            toggleBtn.onclick = function(){
                toggleClassName(box, "box03");
            };
            ```


# 其他
[top](#catalog)
- window对象
    - onload 事件会在整个页面加载完成之后才触发
