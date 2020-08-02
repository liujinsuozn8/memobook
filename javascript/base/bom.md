<span id="catalog"></span>

- 参考
    - https://www.cnblogs.com/st-leslie/p/5617130.html
    - https://www.cnblogs.com/yexiaochai/p/4509472.html

### 目录
- [BOM概述](#BOM概述)
- [Navigator对象](#Navigator对象)
- [History对象](#History对象)
    - [History对象简介](#History对象简介)
    - [History对象的基本属性与方法](#History对象的基本属性与方法)
    - [h5中的History_API](#h5中的History_API)
- [Location对象](#Location对象)
- [localStorage](#localStorage)
    - [localStorage的基本知识](#localStorage的基本知识)
    - [localStorage的CRUD操作](#localStorage的CRUD操作)
- [定时器Interval](#定时器Interval)
    - [定时器的基本使用方法](#定时器的基本使用方法)
    - [定时器应用-颜色自动切换](#定时器应用-颜色自动切换)
    - [定时器应用-解决键盘事件的延迟](#定时器应用-解决键盘事件的延迟)
- [延时器](#延时器)
- [弹出框](#弹出框)
    - [alert提示框](#alert提示框)
    - [prompt可输入提示框](#prompt可输入提示框)
    - [confirm确认取消](#confirm确认取消)
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
    |localStorage|**本地级别**的存储对象|<ul><li>在浏览器中存储 key/value 对的数据</li><li>用于长期保存**整个网站**的数据，保存的数据没有过期时间</li><li>不使用时，需要手动删除</li></ul>|
    |sessionStorage|**会话级别**的存储对象|<ul><li>允许在浏览器中存储 key/value 对的数据</li><li>将数据保存在当前会话中</li><li>该对象会保存当前页面(tab)的数据，关闭浏览器、或关闭当前页面后，将会被删除</li></ul>|

- localStorage和sessionStorage都是存储对象，只是生命周期不同，用法基本相同
    - 用法都可以参考：[localStorage的CRUD操作](#localStorage的CRUD操作)

- 如何使用BOM对象
    - BOM对象在浏览器中**作为window对象的属性**保存
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
## History对象简介
[top](#catalog)
- History对象代表浏览器的历史记录
- 通过 History对象 可以操作浏览器的历史记录
    - 因为存在隐私问题，所以该对象不能获取到具体的历史记录，只能操控浏览器向前或向后移动
    - 该操作只在当次访问时有效

- 如何检查页面出现了历史记录？
    - 浏览器的回退按钮/前进按钮可用

## History对象的基本属性与方法
[top](#catalog)
- History对象属性
    - `length`，返回当期页面历史列表中的URL数量

- History对象基本方法

    |方法|功能|同步/异步|备注|
    |-|-|-|-|
    |`go(整数)`|可以跳转到指定页面|<ul> <li>IE: 异步</li> <li>Chrome: 同步</li> </ul>|<ul> <li>正数：跳转到下n个页面</li> <li>负数：跳转到上n个页面</li> <li>`go(0)` 相当于刷新当前页面</li> </ul>|
    |`back()`|回到上一个页面，与后退按钮相同|异步|相当于 `history.go(-1)`|
    |`forward()`|跳转到下一个页面，与前进按钮相同|异步|相当于 `history.go(1)`|

- 注意事项
    - 返回上n页时，通常从浏览器缓存中重新加载页面，不会与服务器发生请求交互
    - 如果函数跳转的位置已经超出了历史记录中记录的范围，跳转会失败，但是不会抛出异常

    - `go()` 在不同浏览器中的性质不同，
        - 在ie中，和 `back()`、`forward()` 同时使用时，不会有顺序问题
        - 在chrome中，和 `back()`、`forward()` 同时使用时，需要注意延迟执行 `go()`

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

## h5中的History_API
[top](#catalog)
- `window.history.pushState(state, title [, targetURL]);`
    - 功能
        - 改变url地址
            - 与`location.hash`不同，不会从`/#...`开始，会直接附加到当前url的后面
        - 将url的变化添加到历史记录
        - 不会刷新页面，也不会检查目标页面是否存在，但是不能跨域
    - 参数说明
        - state，状态对象
            - 与当前url相关的状态对象
                - 可以是任何类型的数据，不限于 Object 类型
                - 如果不需要，可以设置为null
            - `popstate` 事件触发时，该对象会传入回调函数，并通过 `event.state` 获取
        - title，页面标题
        - targetURL，目标url
            - 不会检查url是否存在，但是不能跨域
            - 如果参数缺省，则默认为当前url
- `window.history.replaceState(state, title [, targetURL]);`
    - 功能
        - 修改当前url
        - 不会添加历史记录，会修改最后一条历史记录中保存的数据: `state`, `title`, `targetURL`
        - 不会刷新页面，也不会检查目标页面是否存在，但是不能跨域
    - 参数说明
        - state，状态对象
            - 与当前url相关的状态对象
                - 可以是任何类型的数据，不限于 Object 类型
                - 如果不需要，可以设置为null
            - `popstate` 事件触发时，该对象会传入回调函数，并通过 `event.state` 获取
        - title，页面标题
        - targetURL，目标url
            - 不会检查url是否存在，但是不能跨域
            - 如果参数缺省，则默认为当前url

- `state` 参数的功能
    - 多用于在前进、后退页面时，使页面保持离开时的状态
    - 相当于一个缓存

- 相关事件: `window.onpopstate`
    - 触发时机
        - 执行 history基本事件: `go`, `back`, `forward` 时，都会触发该事件
        - 点击浏览器的前进、后退按钮也会发挥僧
    - 该事件默认为 `null`, 需要手动设置响应函数
    - 可以通过 `event.state` 获取设置历史记录时的数据
    - 可以通过该事件来复原页面，使页面能够保持离开时的状态

- 检查浏览器对 History API 的兼容性
    ```js
    if (window.history && history.pushState){
        // 支持
    } else {
        // 不支持
    }
    ```

- 示例
    - 基本使用
        - 参考代码
            - [src/bom/history/push_replace_state.html](src/bom/history/push_replace_state.html)
        - 代码内容
            ```js
            /*
                参考:
                https://developer.mozilla.org/zh-CN/docs/Web/API/Window/onpopstate
            */

            window.onpopstate = function(event) {
                console.log('location: ' + document.location + ', state: ' + JSON.stringify(event.state));
                // alert('location: ' + document.location + ', state: ' + JSON.stringify(event.state));
                // console.log(event);
            };

            // 1. 添加并激活第 1 个历史记录
            history.pushState({page: 1}, 'title 1', '?page=1');
            console.log('pushState 01, href = ' + location.href);   // 输出url: .../push_replace_state.html?page=1
            // 2.1 添加并激活第 2 个历史记录
            history.pushState({page: 2}, 'title 2', '?page=2');
            console.log('pushState 02, href = ' + location.href);   // 输出url: .../push_replace_state.html?page=2
            /*
                2.2 修改当前激活的历史记录，将 ?page=2 修改为 ?page=3
                    此时历史记录中一共有 2 个
                    1. page=1; 2. page=3
            */
            history.replaceState({page: 3}, 'title 3', '?page=3');
            console.log('pushState 03, href = ' + location.href);   // 输出url: .../push_replace_state.html?page=3

            /*
                3. 添加并激活第 3 个历史记录
                    此时历史记录中一共有 3 个
                    1. page=1; 2. page=3; 3. page=4
            */
            history.pushState({page:4}, 'title4', '?page=4');
            console.log('pushState 04, href = ' + location.href);   // 输出url: .../push_replace_state.html?page=4

            // 4. 执行后退
            // 第 1 次后退，从 page=4 后退到 page=3，触发 page=3 的 popstate 事件
            history.back(); // 输出: location: .../push_replace_state.html?page=3, state: {"page":3}

            // 第 2 次后退，从 page=3 后退到 page=1，触发 page=1 的 popstate 事件
            history.back(); // 输出: location: .../push_replace_state.html?page=1, state: {"page":1}

            // 第 3 次后退，从 page=1 后退到 push_replace_state.html ，触发 push_replace_state.html 的 popstate 事件
            history.back(); // 输出: location: .../push_replace_state.html, state: null

            // IE 执行
            // history.go(2);

            // chrome 执行
            setTimeout(() => {
                console.log('timeout');
                history.go(2);  // 输出: location: .../push_replace_state.html?page=3, state: {"page":3}
            }, 1000);
            ```
    - 应用实例
        - 参考代码
            - [src/bom/history/push_replace_state_inaction.html](src/bom/history/push_replace_state_inaction.html)
        - html内容
            ```html
            <!-- 0. 点击按钮后刷新内容，页面前进、后退时复原离开时的页面内容 -->
            <div id="msg">
                msgbox
            </div>
            <br><br>
            <!-- 0. 点击按钮，添加历史记录，并刷新 msgbox -->
            <button class="msgBtn">page1</button>
            <button class="msgBtn">page2</button>
            <button class="msgBtn">page3</button>
            ```
        - js内容
            ```js
            // 3. 刷新msg
            function showMsg(el, msg) {
                el.innerHTML = msg
            }

            // 2. 前进后退时，取出url对应的数据，并复原离开时的页面
            window.addEventListener('popstate', function (e) {
                if (!e.state) return;
                showMsg(
                    document.getElementById('msg'),
                    e.state
                );
            });

            // 1. 每个按钮点击时，修改box的显示信息，添加并激活一条历史记录
            const msgBtns = document.querySelectorAll('.msgBtn');
            for (btn of msgBtns){
                // 为按钮绑定属性
                btn.onclick = function (e) {
                    let msg = e.target.innerHTML;
                    showMsg(
                        document.getElementById('msg'),
                        msg
                    );

                    // 设置历史记录，只能设置为 `?参数`，`/参数`会有跨域问题
                    history.pushState(msg, '', '?page=' + msg);
                }
            }
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

- Location对象的方法

    |方法|功能|备注|
    |-|-|-|
    |`assign("URL")`|加载新的文档，即跳转到其他页面|<ul><li>与直接使用URL字符串修改Location对象相同</li><li>跳转页面后会生成历史记录，但是跳转到当前页面时不会生成新的历史记录</li></ul>|
    |`reload([true])`|重新加载当前文档|与 F5/刷新按钮的功能相同|使用：`reload(true)`，在刷新页面时，会强制情况缓存|
    |`replace()`|用新的文档替换当前文档，即跳转到其他页面|<ul><li>与直接使用URL字符串修改Location对象相同</li><li>跳转页面后<span style="color:red">不会生成当前页面的历史记录</span></li></ul>|

- Location对象的属性

    |属性|可以<span style='color:red'>设置或返回</span>的内容|是否刷新页面|
    |-|-|-|
    |hash|从井号 (#) 开始的 URL（锚）|N (会产生历史记录)|
    |host|主机名/IP地址 + 端口号|Y|
    |hostname|主机名/IP地址|Y|
    |href|完整的 URL|Y|
    |pathname|当前 URL 的路径部分|Y|
    |port|当前 URL 的端口号|Y|
    |protocol|当前 URL 的协议<br>返回的结果中会包含冒号，如: `http:`、`file:`|-|
    |search|从问号 (?) 开始的 URL（查询部分）|Y|

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

# localStorage
## localStorage的基本知识
[top](#catalog)
- localStorage对象是H5新加入的特性
- localStorage对象的用途
    - 主要作为本地存储来使用，用于解决cookie存储空间不足的问题
    - 可以长期保存**整个网站**的数据，保存的数据没有过期时间。不使用时，需要手动删除
        - **不同的网站不能共用localStorage**

- localStorage对象中保存的内容
    - key/value 数据
        - 所有的 value 都会使用string来保存
        - 基本数据类型的value会自动转会为String类型
        - 对象类型的数据，只能手动转换为JSON字符串，然后保存。取出来使用之前还需要将JSON还原为js对象
    - 内置属性：`length`，可以获取当前对象中保存的键值对数量

- localStorage与cookie的对比
    - cookie
        - 大小限制在4k左右，不适合存业务数据
        - 每次随HTTP事务一起发送，浪费带宽
    - localStorage
        - 大小限制在5M左右，各浏览器的标准不同
        - 不会跟随HTTP传输

- localStorage的优势
    - 解决了cookie的存储限制
    - 降低了带宽的消耗
    - 可以将第一次请求的数据直接存储到本地，相当于一个5M大小的**针对前端页面的数据库**
        - 相比于cookie，可以节约带宽，但只有在高版本的浏览器中才支持

- localStorage的局限
    - 不同浏览器的容量不统一
    - IE8及以下不兼容
    - 只支持string类型的存储，js对象类型无法直接保存，需要借助JSON
    - 在隐私模式下不可读取
    - 不能被爬虫抓取到，不能完全取代URL传参
    - 本质是在读写文件，数据多的话会比较卡

## localStorage的CRUD操作
[top](#catalog)
- 三种读写方式
    - 官方推荐使用：`getItem`、`setItem` 来读写数据
        ```js
        // 写入/更新数据
        localStorage['a'] = "this is a";
        localStorage.b = 1234;
        localStorage.setItem("c", 23456);

        // 读取数据
        localStorage.getItem('a');
        localStorage['b'];
        localStorage.c;
        ```

- 删除操作
    - 删除所有键值对
        ```js
        localStorage.clear();
        ```
    - 删除某个键值对
        ```js
        // 该方法只负责删除，不会返回任何值
        localStorage.removeItem("key");
        ```

- 获取所有的key
    - `storage.key(i);`，通过index获取key
    - 一般在for循环中，配合 `localStorage.length`来遍历所有的key、或者value
        ```js
        for(var i=0; i<localStorage.length; i++){
            // 遍历key
            var k = localStorage.key(i);
            // 遍历value
            var v = localStorage.getItem(k);
            //...
        }
        ```

- 读写对象类型
    - 写对象：将js对象转换为JSON字符串，然后保存JSON字符串
        ```js
        localStorage.setItem("key", JSON.stringify(obj));
        ```
    - 读对象：从localStorage读取JSON字符串，然后将JSON字符串转换为js对象
        ```js
        var key_json = localStorage.getItem("key");
        var obj = JSON.parse(key_json);
        ```

- 示例
    - 参考代码
        - [/javascript/base/src/bom/localStorage/storageCRUD.html](/javascript/base/src/bom/localStorage/storageCRUD.html)
    - js内容
        ```js
        // 1. localStorage对象的读写操作
        localStorage['a'] = "this is a";
        localStorage.b = 1234;
        localStorage.setItem("c", 23456);

        console.log("localStorage.getItem('a') =", localStorage.getItem('a'));
        // localStorage.getItem('a') = this is a

        console.log("localStorage['b'] =", localStorage['b']);
        // localStorage['b'] = 1234

        console.log("localStorage.c =", localStorage.c);
        // localStorage.c = 23456

        // 2. 查看内置属性
        console.log("localStorage.length =", localStorage.length);
        // localStorage.length = 3

        // 3. 遍历所有的key 和 value
        for(var i=0; i<localStorage.length; i++){
            var k = localStorage.key(i);
            var v = localStorage.getItem(k);
            console.log("localStorage.key("+ i + ") = " + k, ", localStorage.getItem(" + k + ") = " + v);

            // localStorage.key(0) = b , localStorage.getItem(b) = 1234
            // localStorage.key(1) = c , localStorage.getItem(c) = 23456
            // localStorage.key(2) = a , localStorage.getItem(a) = this is a
        }


        // 4. 删除操作
        // 4.1 删除指定的键值对 （没有返回值）
        var c_del = localStorage.removeItem("c");
        console.log('localStorage.getItem("c") =', localStorage.getItem("c"));
        // localStorage.getItem("c") = null
        console.log("c_del =", c_del);
        // c_del = undefined
        console.log("typeof c_del =", typeof c_del);
        // typeof c_del = undefined

        // 4.2 删除所有键值对
        console.log(localStorage);
        localStorage.clear();
        console.log(localStorage);
        console.log("localStorage.length =", localStorage.length);
        // Storage {b: "1234", a: "this is a", length: 2}
        // Storage {length: 0}
        // localStorage.length = 0

        // 5. js的基本数据类型存储测试
        localStorage.setItem("myString", "this is my string");
        localStorage.setItem("myNumber", 123456);
        localStorage.setItem("myFalse", false);
        localStorage.setItem("myTrue", true);
        localStorage.setItem("myNull", null);
        localStorage.setItem("myUndefined", undefined);

        console.log('localStorage.getItem("myString") =', localStorage.getItem("myString"));
        console.log("typeof localStorage.getItem('myString') =", typeof localStorage.getItem('myString'));
        // localStorage.getItem("myString") = this is my string
        // typeof localStorage.getItem('myString') = string

        console.log('localStorage.getItem("myNumber") =', localStorage.getItem("myNumber"));
        console.log("typeof localStorage.getItem('myNumber') =", typeof localStorage.getItem('myNumber'));
        // localStorage.getItem("myNumber") = 123456
        // typeof localStorage.getItem('myNumber') = string

        console.log('localStorage.getItem("myFalse") =', localStorage.getItem("myFalse"));
        console.log("typeof localStorage.getItem('myFalse') =", typeof localStorage.getItem('myFalse'));
        // localStorage.getItem("myFalse") = false
        // typeof localStorage.getItem('myFalse') = string

        console.log('localStorage.getItem("myTrue") =', localStorage.getItem("myTrue"));
        console.log("typeof localStorage.getItem('myTrue') =", typeof localStorage.getItem('myTrue'));
        // localStorage.getItem("myTrue") = true
        // typeof localStorage.getItem('myTrue') = string

        console.log('localStorage.getItem("myNull") =', localStorage.getItem("myNull"));
        console.log("typeof localStorage.getItem('myNull') =", typeof localStorage.getItem('myNull'));
        // localStorage.getItem("myNull") = null
        // typeof localStorage.getItem('myNull') = string

        console.log('localStorage.getItem("myUndefined") =', localStorage.getItem("myUndefined"));
        console.log("typeof localStorage.getItem('myUndefined') =", typeof localStorage.getItem('myUndefined'));
        // localStorage.getItem("myUndefined") = undefined
        // typeof localStorage.getItem('myUndefined') = string

        // 6. js对象类型的对写
        var person = {
            name:"tom",
            age:18,
            address:"xxxyyyzzz",
        };
        // 转换为JSON并保存
        localStorage.setItem("person", JSON.stringify(person));
        // 读取，并将JSON转换为js对象
        var person_json = localStorage.getItem("person");
        var new_person = JSON.parse(person_json);
        console.log("new_person.name =", new_person.name, ", new_person.age =", new_person.age);
        // new_person.name = tom , new_person.age = 18
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

# 弹出框
## alert提示框
[top](#catalog)
- 提示框：`alert(参数);`
    - <span style="color:red">只支持一个参数</span>
- `alert` 没有返回值，如果使用会得到 `undefined`
- 输出什么?
    1. `alert(alert(1))`
        - 将会输出两次
        - 第一次输出 `1`, 即内部的`alert(1)`
        - 第二次输出 `undefined`，因为内层的 `alert(1)` 会返回 `undefined`
    2. `alert(alert(1), alert(2))`
        - 将会输出三次
        - 第一次输出 `1`, 即内部的`alert(1)`
        - 第二次输出 `2`, 即内部的`alert(2)`
            - 虽然第二个数不作为参数，但是作为函数调用仍然会执行
        - 第三次输出 `undefined`，因为内层的 `alert(1)` 会返回 `undefined`

## prompt可输入提示框
[top](#catalog)
- `var input = prompt("提示信息");`
- 可以通过变量来接收输入的数据

## confirm确认取消
[top](#catalog)
- `var input = confirm('xxxx')`
- 选择确认时，返回true
- 选择取消时，返回false