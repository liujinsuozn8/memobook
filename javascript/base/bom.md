<span id="catalog"></span>

### 目录
- [BOM概述](#BOM概述)
- [Navigator对象](#Navigator对象)
- [History对象](#History对象)
- [Location对象](#Location对象)
- [定时器Interval](#定时器Interval)
    - [定时器的基本使用方法](#定时器的基本使用方法)
    - [定时器应用-颜色自动切换](#定时器应用-颜色自动切换)
    - [定时器应用-解决键盘事件的延迟](#定时器应用-解决键盘事件的延迟)
- [延时器](#延时器)
- [](#)
- [](#)
- [](#)
- [](#)


# BOM概述
[top](#catalog)
- BOM，Browzer Object Model
    - BOM是浏览器对象模型
    - js通过BOM来操作浏览器

- BOM中提供了一组对象，来完成浏览器操作，包括：

    |对象|说明|功能|
    |-|-|-|
    |Window|代表整个浏览器的窗口|网页中的全局对象|
    |Navigator|代表当前浏览器的信息|通过该对象可以**识别不同的浏览器**|
    |Location|代表当期浏览器的地址栏信息|通过location可以获取地址栏信息，或者操作浏览器跳转页面|
    |History|代表浏览器的历史记录|<ul><li>通过该对象可以操作浏览器的历史记录</li><li>因为存在隐私问题，所以该对象不能获取到具体的历史记录，只能操控浏览器向前或向后移动，该操作只在当次访问时有效</li></ul>|
    |Screen|代表用户的屏幕信息|<ul><li>通过该对象可以获取到用户显示器的相关信息</li><li>该属性在移动端更常用</li></ul>|
        
- 如何使用BOM对象
    - navigator、location、history、screen 这四个BOM对象在浏览器中**作为window对象的属性**保存
    - 可以通过 window 对象来使用，也可以直接使用

# Navigator对象
[top](#catalog)
- Navigator 代表当前浏览器的信息，通过该对象可以**识别不同的浏览器**

- 由于历史原因，Navigator中的大部分属性已经不能帮助开发者识别浏览器了，一般只会使用 `user-Agent` 属性来判浏览器信息

- `user-Agent` 属性
    - 本质是一个字符串，包含描述浏览器信息的内容
    - 各浏览器中的属性值

        |浏览器|`user-Agent` 属性值|
        |-|-|
        |chrome|Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36|
        |edge|Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; rv:11.0) like Gecko|
        |ie10|Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)|
        |ie9|Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)|
        |ie8|Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)|
        |firefox|?????|

- 判断浏览器的方法
    - chrome : 在 `user-Agent` 属性中检查是否包含：`chrome`
    - ie11之前的版本：在 `user-Agent` 属性中检查是否包含：`mise`
    - ie11及其之后的版本，无法通过 `user-Agent`属性 来判断，可以通过一些浏览器中持有的对象来判断浏览器的信息，如：`window.ActiveXObject`
        - 判断方法
            1. `if (window.ActiveXObject)`，**不推荐**，无法判断IE11
                - 在IE11中，window.ActiveXObject 会自动转换为false无法捕获
            2. `"ActiveXObject" in window`，通过反射来判断。**推荐**，在IE11中也可以使用

- Navigator中的所有可用属性

    |属性名|含义|备注|
    |-|-|-|
    |userAgent|用户代理，会返回由客户机发送服务器的 user-agent 头部的值||
    |appCodeName|返回浏览器的代码名||
    |appMinorVersion|返回浏览器的次级版本||
    |appName|返回浏览器的名称||
    |appVersion|返回浏览器的平台和版本信息||
    |browserLanguage|返回当前浏览器的语言||
    |cookieEnabled|返回指明浏览器中是否启用 cookie 的布尔值||
    |cpuClass|返回浏览器系统的 CPU 等级||
    |onLine|返回指明系统是否处于脱机模式的布尔值||
    |platform|返回运行浏览器的操作系统平台||
    |systemLanguage|返回 OS 使用的默认语言||
    |userLanguage|返回 OS 的自然语言设置||

- 示例
    - 参考代码
        - [/javascript/base/src/bom/navigator.html](/javascript/base/src/bom/navigator.html)

    - js内容
        ```js
        if (/chrome/i.test(navigator.userAgent)){
            console.log("this is chrome");
        } else if (/msie/i.test(navigator.userAgent)){
            console.log("this is ie")
        } else if ("ActiveXObject" in window){
            console.log("this is ie 11");
        }
        ```

# History对象
[top](#catalog)
- History对象代表浏览器的历史记录
- 通过 History对象 可以操作浏览器的历史记录
    - 因为存在隐私问题，所以该对象不能获取到具体的历史记录，只能操控浏览器向前或向后移动
    - 该操作只在当次访问时有效

- 如何检查页面出现了历史记录？
    - 浏览器的回退按钮/前进按钮可用

- History对象属性
    - `length`，返回当期页面历史列表中的URL数量

- History对象方法

    |方法|功能|备注|
    |-|-|-|
    |`back()`|回到上一个页面，作用和浏览器的后退按钮相同||
    |`forward()`|跳转到下一个页面，作用和浏览器的前进按钮相同|如果没有下一个页面，则该操作无效|
    |`go(整数)`|可以跳转到指定页面|正数：跳转到下n个页面，负数：跳转到上n个页面<br>|

- 示例
    - 参考代码
        - [/javascript/base/src/bom/history/history.html](/javascript/base/src/bom/history/history.html)
        - [/javascript/base/src/bom/history/test01.html](/javascript/base/src/bom/history/test01.html)
        - [/javascript/base/src/bom/history/test02.html](/javascript/base/src/bom/history/test02.html)
        - [/javascript/base/src/bom/history/test03.html](/javascript/base/src/bom/history/test03.html)

    - 示例的跳转过程
        1. test01 --> test02
        2. test02 --> history
        3. history --> test03
        4. test03 点击浏览器的后退按钮，回退到 history
        5. 在history 点击 back 按钮，回到 test02
        6. 如果是从 test03回退到的 history，在history 点击 forward 按钮，回到 test03
        7. 在history 点击 go 按钮，跳转到上2个页面，回退到test01
        8. 在history 点击 showLength 按钮，在提示框中显示历史列表中的URL数量
    
    - 控制hisitory对象的页面：[history.html](/javascript/base/src/bom/history/history.html)
        - html内容
            ```html
            <p>history</p>

            <button id="showLength">showLength</button><br>
            <button id="backBtn">back</button><br>
            <button id="forwardBtn">forward</button><br>
            <button id="goBtn">go</button><br>

            <a href="test03.html">go to test03.html</a>
            ```

        - js内容
            ```js
            // 显示历史列表的URL数量
            var showLength = document.getElementById("showLength");
            showLength.onclick = function(){
                alert("history.length = " +history.length);
            };

            // 跳转到上一个页面
            var backBtn = document.getElementById("backBtn");
            backBtn.onclick = function(){
                history.back();
            };

            // 跳转到下一个页面
            var forwardBtn = document.getElementById("forwardBtn");
            forwardBtn.onclick = function(){
                history.forward();
            };

            // 跳转到上2个页面
            var goBtn = document.getElementById("goBtn");
            goBtn.onclick = function(){
                history.go(-2);
            };
            ```

# Location对象
[top](#catalog)
- Location 代表当期浏览器的地址栏信息
- 通过Location对象可以获取地址栏信息，或者操作浏览器跳转页面
- 直接打印Location对象时，会显示地址栏中的完整路径
- 如果将 `Location对象` 修改为其他路径后，页面会自动跳转
    - 可以使用绝对路径，或相对路径
    - 页面跳转后，**会生成历史记录**
        - 如果加载的是当前页面的路径，则不会生成新的历史记录，即无法回退到当前页面之气的状态?????

- Location对象的常用方法
    
    |方法|功能|备注|
    |-|-|-|
    |`assign("URL")`|加载新的文档，即跳转到其他页面|<ul><li>与直接使用URL字符串修改Location对象相同</li><li>跳转页面后会生成历史记录，但是跳转到当前页面时不会生成新的历史记录</li></ul>|
    |`reload([true])`|重新加载当前文档|与 F5/刷新按钮的功能相同|使用：`reload(true)`，在刷新页面时，会强制情况缓存|
    |`replace()`|用新的文档替换当前文档，即跳转到其他页面|<ul><li>与直接使用URL字符串修改Location对象相同</li><li>跳转页面后<label style="color:red">不会生成当前页面的历史记录</label></li></ul>|

- 示例
    - 参考代码
        - [/javascript/base/src/bom/location.html](/javascript/base/src/bom/location.html)

    - html内容
        ```html
        <button id="toBaidu">go to other html</button> <br>
        <button id="assignBtn">加载baidu</button> <br>
        <button id="assignSelf">加载当前页面</button> <br>
        <button id="reloadBtn">重新加载</button> <br>
        <input type="text"> <button id="clearReload">强制情况缓存刷新页面</button> <br>
        <button id="replaceBtn">页面替换</button> <br>
        ```
    
    - js内容
        ```js
        // 1. 手动修改 Location对象
        var toBaidu = document.getElementById("toBaidu");
        toBaidu.onclick = function(){
            location = "https://www.baidu.com";
            // location = "history/test01.html";
        };

        // 2. 加载其他页面
        var assignBtn = document.getElementById("assignBtn");
        assignBtn.onclick = function(){
            location.assign("https://www.baidu.com");
        };
        
        // 3. 加载自身
        var assignSelf = document.getElementById("assignSelf");
        assignSelf.onclick = function(){
            location.assign("location.html");
        };

        // 4. 重新加载当前页面
        var reloadBtn = document.getElementById("reloadBtn");
        reloadBtn.onclick = function(){
            location.reload();
        };

        // 5. 强制情况缓存刷新页面
        var clearReload = document.getElementById("clearReload");
        clearReload.onclick = function(){
            location.reload(true);
        };

        // 6. 页面替换
        var replaceBtn = document.getElementById("replaceBtn");
        replaceBtn.onclick = function(){
            location.replace("https://www.baidu.com");
        };
        ```

# 定时器Interval
## 定时器的基本使用方法
[top](#catalog)
- 定时器每个一段时间执行一次，只要定时器没有被清除，就会一直执行
- 创建定时器：`setInterval(回调函数, 毫秒间隔)`
    - 可以将一个函数**每隔一段时间执行一次**
    - 该方法会返回一个Number类型的结果，并且该结果作为**定时器的唯一标识**

- 清除定时器：`clearInterval(定时器标识)`
    - 该函数可以接受任何类型的参数，包括：null、undefined
    - 如果参数是一个有效的定时器标识，则清除该定时器；如果不是，则不执行任何操作

## 定时器应用-颜色自动切换
[top](#catalog)
- 功能需求
    - 点击 start 按钮，开始切换颜色
    - 点击 end 按钮，停止切换样色

- 实现方式
    - 点击 start 按钮时，添加定时器，并将定时器的标识设置到全局变量中
    - 点击 end 按钮，通过全局变量来清除定时器

- 多次点击的问题
    - 产生的问题
        - 点击 start 按钮时，如果只是添加定时器，那么每次点击时，都会创建一个定时器。多次点击按钮后，就会创建多个定时器，导致图片切换加速
        - 每次创建的定时器标识都会设置到全局变量中，导致全局变量中保存的是最新的定时器。点击 end 按钮时，**只能关闭最新的定时器，旧的定时器无法关闭**
    - 问题的解决方法
        - 为了保证在元素上只有一个有效的定时器，点击 start 按钮后，添加定时器之前，需要**先通过全局变量清除定时器**

- 示例
    - 参考代码
        - [/javascript/base/src/bom/timer/interval/autoChangeColor.html](/javascript/base/src/bom/timer/interval/autoChangeColor.html)
    
    - html内容
        ```html
        <div id="box01"></div>
        <button id="startBtn">start</button>
        <button id="endBtn">end</button>
        ```
    
    - css内容
        ```css
        #box01{
            width: 100px;
            height:100px;
            background-color: #bfa;
        }
        ```
    
    - js内容
        ```js
        // 使用全局变量来保存定时器
        var timer;
        var colorList = ["#bfa", "#47e", "#ccc", "#eda"]
        var colorIndex = 0;
        var box01 = document.getElementById("box01");
        
        // 每次点击都会创建一个定时器，多次点击按钮后，就会创建多个定时器，
        // 导致图片切换加速
        // 但是timer只会保存最新的定时器，所以点击关闭按钮时，也只能关闭最新的定时器，
        // 无法关闭其他的定时器
        // var startBtn = document.getElementById("startBtn");
        // startBtn.onclick = function(){
        //     // 点击开始按钮后，创建定时器
        //     timer = setInterval(function(){
        //         colorIndex = (colorIndex + 1)%colorList.length;
        //         box01.style.backgroundColor = colorList[colorIndex];
        //     }, 500);
        // };

        
        var startBtn = document.getElementById("startBtn");
        startBtn.onclick = function(){
            // 点击开始按钮后，先关闭当前元素中的上一个定时器
            clearInterval(timer);

            // 创建定时器
            timer = setInterval(function(){
                colorIndex = (colorIndex + 1)%colorList.length;
                box01.style.backgroundColor = colorList[colorIndex];
            }, 500);
        };

        var endBtn = document.getElementById("endBtn");
        endBtn.onclick = function(){
            clearInterval(timer);
        };
        ```

## 定时器应用-解决键盘事件的延迟
[top](#catalog)
- 最基本的做法
    - 在onkeydown事件中，根据方向键的keycode，控制移动方向，并每次移动一定的距离
    - 元素移动的两个要素
        - 方向 : keycode
        - 速度 : 每次出发事件的时候，移动多大的距离
    - 方向没有问题，但是速度有问题。浏览器为了避免键盘事件的异常捕获，每次按下按钮都会产生一个延迟，所以连续按下键盘时，第一次与第二次直接会产生延迟

- 解决键盘事件延迟的方法
    - 分离元素移动的两个要素
        - onkeydown : 控制方向
        - 定时器 : 控制速度

- 实现方式
    - 按下键盘时，设置方向参数，并创建定时器
        - 为了避免多次设置定时器，导致移动加速，每次创建定时器之前需要先清除定时器，保证定时器只有一个
    - 松开键盘时，清除定时器
- 示例
    - 参考代码
        - [/javascript/base/src/bom/timer/interval/moveElemByKeydown.html](/javascript/base/src/bom/timer/interval/moveElemByKeydown.html)
    
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
            position: absolute;
        }
        ```

    - js内容
        ```js
        var box01 = document.getElementById("box01");
        var direction = 0;
        var timer

        // 1. 按下键盘，添加一个定时器，并开始移动
        document.onkeydown = function(event){
            // 固定方向
            direction = event.keyCode;

            // 每次创建定时器之前需要先清除定时器，保证定时器只有一个
            clearInterval(timer);
            // 每30毫秒进行移动
            timer = setInterval(moveBox01, 30);
            
            // 2. 松开键盘，清除定时器，同时清除松开键盘的事件
            document.onkeyup = function(event){
                clearInterval(timer);
                document.onkeyup = null;
            }
        }
        
        function moveBox01(){
            switch (direction) {
                case 37: // 左
                    box01.style.left = box01.offsetLeft - 5 + "px";
                    break;
                case 38: // 上
                    box01.style.top = box01.offsetTop - 5 + "px";
                    break;
                case 39: // 右
                    box01.style.left = box01.offsetLeft + 5 + "px";
                    break;
                case 40: // 下
                    box01.style.top = box01.offsetTop + 5 + "px";
                    break;
            }
        }
        ```

# 延时器
[top](#catalog)
- 延时器使一个函数不马上执行，而是隔一段时间之后再执行，并且只执行一次
- 创建延时器：`setTimeout(回调函数, 毫秒间隔)`
    - 该函数会返回一个延时器标识
- 清除延时器：`clearTimeout(延时器标识)`

- 延时器与定时器互相转化
    - 连续多次调用延时器 = 定时器
    - 定时器只调用一次，然后被清除 = 延时器

