<span id="catalog"></span>

### 目录-检索输入框提示列表
- [实现方法](#实现方法)
- [实现代码](#实现代码)
- [注意事项](#注意事项)
- [请求接口设计](#请求接口设计)


# 实现方法
[top](#catalog)
1. 获取输入框并添加输入事件: `input`
2. 在输入时，获取输入的关键字
3. 向服务端发送请求，并携带关键字作为请求参数
4. 服务端使用请求参数检索相关数据，并将检索结果返回
5. 页面接受到数据之后，在输入框下方的列表中显示

# 请求接口设计
[top](#catalog)
- 接口描述
    - 根据用户文本框中输入的关键字，匹配相关内容
- 请求地址
    - `/02search_list/keywords`
- 请求方式
    - GET
- 参数

    |参数名|必须|类型|说明|
    |-|-|-|-|
    |key|是|string|用户输入的关键字|

- 返回值示例
    ```js
    [
        'aaaaa',
        'bbbbb',
        'ccccc',
    ]
    ```

# 注意事项
[top](#catalog)
- 去除首尾空格
    - 浏览器显示的内容不需要修改，输入什么就显示什么
    - 触发input事件时，需要先读输入的数据去除首尾的空格，然后再做处理
    - 浏览器端，在获取到 `key` 后，也需要先去除首尾的空格
- 浏览器和服务端对空字符串的处理
    - 在浏览器端
        - 如果是 空字符串，则不需要发送请求
    - 在服务端
        - 如果是 空字符串，则不需要搜索，直接返回空数组

- 处理无意义的请求
    - 无意义的请求的产生
        - 在使用中文输入的时候，每次输入一个拼音字母，都会发送一次请求
        - 应该在一个汉字，或一个单词输入完成之后，在发送请求
    - 解决方法: 设置时间间隔
        1. 在全局作用域中设置一个 延时器`setTimeout` 对象的标识
        2. 在 `oninput` 事件，将用于发送 ajax 请求的函数，包在setTimeout 中，并设置一定的延迟
        3. 当一定时间内用户没有输入时，则触发延时器，发送请求
        4. 如果用户正在输入中文，则会连续触发 `oninput` 事件
            - 在事件开始前，情况旧的延时器，创建一个新的延时器
            - 通过延时器，使用户能够有足够的时间完成中文的输入

# 实现代码
[top](#catalog)
- 浏览器地址
    - http://localhost:3333/html/02searchList/search.html
- 请求代码
    - 参考代码
        - [src/ajax-inaction/public/html/02searchList/search.html](src/ajax-inaction/public/html/02searchList/search.html)
    - 代码内容
        ```js
        const searchInput = document.querySelector('.search-box__input');
        const keywordList = document.querySelector('.search-box__keywords');
        // 设置一个全局延时器标识
        let timeOuter = null;
        /*
            1. 在文本框中输入内容时，向服务端发送请求，
               如果返回了有效的keyword，则创建dom元素来显示keywords
        */
        searchInput.oninput = function () {
            // 1.1 触发事件后，将延时器清空
            clearTimeout(timeOuter);

            // 1.2 去除输入内容的首尾空格
            const key = this.value.trim();

            // 1.3 如果是空字符串，则不发送请求
            if (key === '') {
                reactiveKeywordList();
                return;
            }

            // 1.4  将请求包在一个新的延时器中，并重设全局延时器标识
            timeOuter = setTimeout(
                () => ajax({
                    type: 'get',
                    url: '/02searchList/keywords',
                    data: { key },
                    success(data) {
                        if (data.length > 0) {
                            activeKeywordList(data);
                            console.log(data);
                        } else {
                            reactiveKeywordList();
                        }
                    }
                }),
                500
            );
        }

        //2. 在文本框失去焦点时，隐藏keywords
        searchInput.onblur = function () {
            reactiveKeywordList();
        }

        // 3. 激活keyword列表，并将数据转换成li标签
        function activeKeywordList(data) {
            addClassName(keywordList, 'search-box__keywords--active');
            let liStr = '';
            for (let i = 0; i < data.length; i++) {
                liStr += `<li class='search-box__key'>${data[i]}</li>`;
            }
            keywordList.innerHTML = liStr;
        }

        // 4. 反激活keyword列表
        function reactiveKeywordList() {
            delClassName(keywordList, 'search-box__keywords--active');
        }
        ```
- 服务端代码
    - 参考代码
        - [src/ajax-inaction/router/02searchList.js](src/ajax-inaction/router/02searchList.js)
    - 代码内容
        ```js
        router.get('/keywords', async ctx=>{
            // 1. 去除 key 的首尾空格
            const inputKey = ctx.query.key.trim();

            if (inputKey === ''){
                // 2. 如果key是空字符串，则返回空数组
                ctx.body = [];
            } else {
                // 3. 如果key不是空字符串，则进行搜索
                let result = [];
                for (let kw of defaultKeywords){
                    if (kw.indexOf(inputKey) != -1){
                        result.push(kw);
                    }
                }

                ctx.body = result;
            }
        });
        ```
