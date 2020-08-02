<span id="catalog"></span>
- 参考
    - https://developer.mozilla.org/zh-CN/docs/Web/API/Console
    - https://www.bilibili.com/video/BV1oz4y1R7yu

### 目录
- [console在JS中的通用方法](#console在JS中的通用方法)
    - [方法列表](#方法列表)
    - [不同的日志级别](#不同的日志级别)
    - [log的输出方法](#log的输出方法)
    - [断言](#断言)
    - [清空控制台](#清空控制台)
    - [分组显示](#分组显示)
    - [计数功能](#计数功能)
    - [计时功能](#计时功能)
    - [追踪堆栈调用](#追踪堆栈调用)
    - [不同形式的输出](#不同形式的输出)
- [chrome控制台的特殊功能](#chrome控制台的特殊功能)
- [](#)

# 标准的console方法
## 方法列表
[top](#catalog)

|方法                               |用途                          |功能                         |
|-----------------------------------|-----------------------------|----------------------------|
|`console.log()`                    |[不同的日志级别](#不同的日志级别)|输出日志|
|`console.warn()`                   |[不同的日志级别](#不同的日志级别)|输出日志|
|`console.info()`                   |[不同的日志级别](#不同的日志级别)|输出日志|
|`console.error()`                  |[不同的日志级别](#不同的日志级别)|输出日志|
|`console.debug()`                  |[不同的日志级别](#不同的日志级别)|输出日志|
|`console.assert(断言表达式, 异常输出)`|[断言](#断言)                 |断言表达式，false时输出异常|
|`console.clear()`                  |[清空控制台](#清空控制台)       |清空控制台|
|`console.group('分组标题')`         |[分组显示](#分组显示)           |分组的开始，并且分组自动呈打开状态|
|`console.groupCollapsed('分组标题')`|[分组显示](#分组显示)           |分组的开始，并且分组自动呈折叠状态|
|`console.groupEnd()`               |[分组显示](#分组显示)           |结束分组|
|`console.count('计数器')`           |[计数功能](#计数功能)           |用于计数，如函数被执行了多少次|
|`console.countReset('计数器')`      |[计数功能](#计数功能)           |重置`计数器`|
|`console.time('计时器')`            |[计时功能](#计时功能)           |开始计时|
|`console.timeEnd('计时器')`         |[计时功能](#计时功能)           |结束计时|
|`console.timeLog('计时器')`         |[计时功能](#计时功能)           |在开始和结束之间使用，输出时间|
|`console.trace()`                  |[追踪堆栈调用](#追踪堆栈调用)     |追踪堆栈调用|
|`console.dirxml(dom对象)`          |[不同形式的输出](#不同形式的输出)  |非标准方法,输出结点及结点下的子元素|
|`console.dir(dom对象/普通对象)`      |[不同形式的输出](#不同形式的输出)  |非标准方法,以`key:value`的方式输出|
|`console.table(obj)`               |[不同形式的输出](#不同形式的输出)  |表格形式输出|

## 不同的日志级别
[top](#catalog)
- `console.log()`
- `console.warn()`
- `console.info()`
- `console.error()`
    - 只是输出一个异常**信息**，不是真的JS异常，后面的代码还会继续执行
- `console.debug()`

## log的输出方法
[top](#catalog)
- `console.log(arg1, arg2, arg3....)`
    - 输出多个参数
    - 参数可以是：字符串、模版字符串、对象

- `console.log('格式化字符串', arg2, arg3....)`
    - 用于格式化输出
    - 占位符

        |占位符|功能|
        |-|-|
        |`%o`、`%O`|表示对象|
        |`%d`、`%i`|表示整数。支持格式化，如`%.2d`<br>如果在数字的位置放了一个字符串，会显示`NaN`|
        |`%s`|表示字符串|
        |`%f`|表示浮点数。支持格式化，如`%.2f`|
    - 不同的浏览器中，`%d`、`%f`的格式化实现不同
    - 示例
        ```js
        var str = 'teststr'
        var int = 1234
        var float = 23.45
        var obj = {name:'testName', age:22}

        console.log('str=%s, int=%.5i, float=%.4f, obj=%o', str, int, float, obj);
        ```
    - `console.log('%c日志信息1%c日志信息2', 样式1, 样式2)`
        - 通过站位符 `%c` 为日志信息添加css样式
            - 有几个`%c`就应该有几个css样式
            - `%c`的样式会设置给后面的字符串，
        - 示例
            ```js
            console.log(
                '%cmsg01%cmsg02%c',
                'color:red;font-size:20px',
                'color:#bfa;font-size:25px'
            );
            ```
    - 占位符转译
        - 如果要输出占位符的文字，需要重复两次`%`，如 `%%c`
## 断言
[top](#catalog)
- `console.assert(断言表达式, 异常时的输出内容)`
    - 判断第一个参数是否为真，false 的话抛出异常并且在控制台输出相应信息
- 示例
    - 输出内容是一个变量
        ```js
        var msg = 'num error';
        var num = 1;
        console.assert(num === 0, {num, msg});
        ```
    - 格式化异常输出
        ```js
        var msg = 'num error';
        var num = 1;
        console.assert(num === 0, 'number is %d', num);
        ```

## 清空控制台
[top](#catalog)
- `console.clear()`
    - 情况控制台，并输出 `Console was cleared`

## 分组显示
[top](#catalog)
- 可用方法

    |方法|功能|
    |-|-|
    |`console.group('分组标题')`|表示分组的开始，并且分组自动呈打开状态|
    |`console.groupCollapsed('分组标题')`|分组的开始，并且分组自动呈折叠状态|
    |`console.groupEnd()`|结束分组|

- 开始和结束之间，可以使用console的其他输出功能，作为分组输出的内容
    ```js
    console.group('group01')    // 分组开始，自动打开
    console.log('zzzz')
    console.log('cccc')
    console.groupEnd()          // 分组结束
    console.groupCollapsed('group02')   // 分组开始，自动折叠
    console.log('aaaa')
    console.log('bbbb')
    console.groupEnd()          // 分组结束
    ```

## 计数功能
[top](#catalog)
- 可用方法

    |方法|功能|
    |-|-|
    |`console.count('计数器')`|用于计数，如函数被执行了多少次|
    |`console.countReset('计数器')`|重置`计数器`|

- 使用字符串作为计数器
    - 相同的`计数器`被认为是一组，并在`计数器`的基础上累加
    - 只认`计数器`，忽略代码位置
    - 计数从 1 开始

- 输出的格式: `计数器: 次数`

- 可以不使用计数器，来创建一个全局的计数器
    ```js
    console.count();
    console.countReset();
    ```

- 示例
    - 在循环中输出
        ```JS
        function foo(){
            console.count('counter');
        }

        // 循环调用包含 console.count 的函数
        for (var i=0; i<10; i++) {
            if (i === 5){
                // i 等于 5 时，初始化
                console.countReset('counter')
            }
            foo();
        }
        // 输出:
        // counter: 1
        // counter: 2
        // counter: 3
        // counter: 4
        // counter: 5
        // counter: 1
        // counter: 2
        // counter: 3
        // counter: 4
        // counter: 5
        ```

    - 使用标识符来区别不同的计数
        ```js
        var obj = {
            foo(){
                console.count('aaa')
            }
        }
        obj.foo()
        console.count('bbb')
        console.count('aaa')
        // 输出:
        // aaa: 1
        // bbb: 1
        // aaa: 2
        ```

## 计时功能
[top](#catalog)
- 可用方法

    |方法|功能|
    |-|-|
    |||
    |`console.time('计时器')`|**开始**计时，不会有输出|
    |`console.timeEnd('计时器')`|**结束**计时，会输出结束时间|
    |`console.timeLog('计时器')`|在**开始**和**结束**之间使用，输出已经计时的时间|

- 使用字符作为计时器标识
- 示例
    ```js
    console.time('aaa');
    console.log('timeStart');
    // 创建一个费时的操作
    for(let i=0; i<1000000; i++){
        if (i % 200000 === 0 ){
            console.timeLog('aaa')
        }
    }
    console.log('timeEnd');
    console.timeEnd('aaa')

    // 输出:
    // timeStart
    // aaa: 0.147216796875ms
    // aaa: 2.3232421875ms
    // aaa: 4.42919921875ms
    // aaa: 4.88623046875ms
    // aaa: 5.325927734375ms
    // timeEnd
    // aaa: 5.8369140625ms
    ```

## 追踪堆栈调用
- `console.trace()`
    - 追踪堆栈调用
- 示例
    ```js
    function f1(){
        f2();
    }
    function f2(){
        f3()
    }
    function f3(){
        f4()
    }
    function f4(){
        console.trace();
    }
    f1();
    // 输出:
    // Trace
    //     at f4 (repl:2:21)
    //     at f3 (repl:2:13)
    //     at f2 (repl:2:13)
    //     at f1 (repl:2:13)
    //     at repl:1:9
    //     at Script.runInThisContext (vm.js:116:20)
    //     at REPLServer.defaultEval (repl.js:403:29)
    //     at bound (domain.js:422:14)
    //     at REPLServer.runBound [as eval] (domain.js:435:12)
    //     at REPLServer.onLine (repl.js:714:10)
    ```

## 不同形式的输出
[top](#catalog)
- `console.dirxml(dom对象)`，<span style='color:red'>非标准方法</span>
    - 输出结点及结点下的子元素
    - console.log 也有同样的功能
- `console.dir(dom对象/普通对象)`，<span style='color:red'>非标准方法</span>
    - 以`key:value`的方式输出属性与属性值
    - <span style='color:red'>最适合显示dom对象的属性</span>
- `console.table(obj)`
    - 以表格的形式输出对象的属性与属性值

# chrome控制台的特殊功能
[top](#catalog)
- `console.memory`，打印内存信息
- `copy(对象)`
    - 拷贝对象内容到剪贴板
- `$_`
    - 该功能在Chrome上有，在NodeJs上没有
    - 重新显示上一次控制台中，输入内容的输出结果
        - 不是`snippets`的结果，是手动在控制台输入的代码片段的输出结果
- `$0`、`$1`、`$2`、`$3`、`$4`
    - 访问最近选择的元素和对象
    - 控制台会存储最近5个被选择器选择的对象
        - 在元素面板中选择一个元素或分析面板选择一个对象的时候，记录都会存在一个堆栈当中
- `$(selector, [startNode])`
    - 类似与jquery选择器
- `$$(selector, [startNode])`
    - 相当于 `document.querySelectorAll`
- `$x(selector, [startNode])`
    - 通过xpath来选择元素
- `keys(对象)`
    - 获取对象的所有key
- `values(对象)`
    - 获取对象的所有value
- `monitor(function)`
    - 监控某个函数是否被执行
- `unmonitor(function)`
    - 停止对某个函数的监控
- `monitorEvents(对象, [event1, event2,...])`
    - 监控对象的一个或多个事件
    - 示例
        ```js
        // 监控窗口的变化和滚动条事件
        monitorEvents(window, ['size', 'scroll'])
        ```
- `unmonitorEvents(对象, [event1, event2,...])`
    - 停止对对象的事件监控
    