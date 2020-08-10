<span id="catalog"></span>

### 目录-axios
- [分别使用xhr和axios发送请求](#分别使用xhr和axios发送请求)
- [使用xhr封装axios的基本实现](#使用xhr封装axios的基本实现)
- [axios的特性](#axios的特性)

# 分别使用xhr和axios发送请求
[top](#catalog)
- 浏览器端实现
    - 参考代码
        - [src/base-server/public/html/base/base.html](src/base-server/public/html/base/base.html)
    - 浏览器访问地址
        - http://localhost:3333/html/base/base.html
    - 原生xhr发送请求
        ```js
        // 为按钮注册发送ajax请求的事件
        document.getElementById('get').onclick = function () {
            var xhr = new XMLHttpRequest();
            xhr.open('get', '/base/handle?aaa=123&bbb=234');
            xhr.send();
            xhr.onload = function () {
                console.log(JSON.parse(xhr.responseText));
            }
        }

        document.getElementById('put').onclick = function () {
            var xhr = new XMLHttpRequest();
            xhr.open('put', '/base/handle');
            xhr.setRequestHeader('Content-type', 'application/json')
            xhr.send(JSON.stringify({ name: 123, age: 345 }));
            xhr.onload = function () {
                console.log(JSON.parse(xhr.responseText));
            }
        }

        document.getElementById('post').onclick = function () {
            var xhr = new XMLHttpRequest();
            xhr.open('post', '/base/handle');
            xhr.setRequestHeader('Content-type', 'application/json')
            xhr.send(JSON.stringify({ name: 123, age: 345 }));
            xhr.onload = function () {
                console.log(JSON.parse(xhr.responseText));
            }
        }

        document.getElementById('delete').onclick = function () {
            var xhr = new XMLHttpRequest();
            xhr.open('delete', '/base/handle/123');
            xhr.setRequestHeader('Content-type', 'application/json')
            xhr.send();
            xhr.onload = function () {
                console.log(JSON.parse(xhr.responseText));
            }
        }
        ```
    - axios发送请求
        ```js
        // 使用发 axios 送请求
        // 为按钮注册发送ajax请求的事件
        document.getElementById('get').onclick = function () {
            axios.get('/base/handle?aaa=123&bbb=234').then(resp => {
                console.log(resp.data)
            });
        }

        document.getElementById('post').onclick = function(){
            axios.post('/base/handle', { name: 123, age: 345 }).then(resp => {
                console.log(resp.data)
            });
        }

        document.getElementById('put').onclick = function(){
            axios.put('/base/handle', { name: 256, age: 345 }).then(resp => {
                console.log(resp.data)
            });
        }

        document.getElementById('delete').onclick = function(){
            axios.delete('/base/handle/001').then(resp => {
                console.log(resp.data)
            });
        }
        ```
- 服务端代码
    - 参考代码
        - [src/base-server/router/base.js](src/base-server/router/base.js)
    - 代码内容
        ```js
        // 使用 ctx.query 获取请求参数-------------------
        router.get('/handle', async ctx => {
            ctx.query.type = 'get';
            ctx.body = ctx.query;
        })

        router.delete('/handle/:id', async ctx => {
            ctx.query.type = 'delete';
            ctx.query.param = ctx.params.id;
            ctx.body = ctx.query;
        })

        // 使用 ctx.request.body 获取请求参数-------------------
        router.post('/handle', async ctx => {
            ctx.request.body.type = 'post';
            ctx.body = ctx.request.body
        })

        router.put('/handle', async ctx => {
            ctx.request.body.type = 'put';
            ctx.body = ctx.request.body
        })
        ```

# 使用xhr封装axios的基本实现
[top](#catalog)
- 封装函数的特点
    1. 函数的返回值为 promise，成功的结果为 response，异常的结果为error
    2. 能够处理多种类型的请求
        - get、post、put、delete
    3. 函数的参数是一个配置对象
        ```js
        {
            url:'',
            method:'',
            params:{},  // get、delete 请求的参数
            data:{}     // post put 请求的参数
        }
        ```
    4. 如果响应结果是JSON字符串，则自动解析为JS对象

- 函数的响应结果
    - 正常响应
        - 参考
            - https://github.com/axios/axios#response-schema
        - 包括
            ```js
            {
                data: {},   // 响应数据
                status: 200,    // http状态码
                statusText: 'OK',   // http状态码的描述
                headers: {},    // 所有请求头
                config: {}, // axios 的请求配置
                request: {} // xhr对象
            }
            ```
    - 异常响应
        - 返回一个 `Error()` 对象
- 判断响应成功的方法
    1. 使用 `onreadystatechange` 来监听ajax状态码的变化，知道变为`4`
        - 不使用 `onload`，需要同时监听成功和失败
    2. 响应结束后，根据http状态码来判断请求是否成功
        - http状态是 `[200, 300)` 之间的整数时，则请求成功

- 示例
    - 参考代码
        - axios封装
            - [src/base-server/public/js/myaxios.js](src/base-server/public/js/myaxios.js)
        - 测试代码
            - [src/base-server/public/html/myaxios/base.html](src/base-server/public/html/myaxios/base.html)
    - 浏览器访问地址
        - http://localhost:3333/html/base/base.html
    - axios封装代码
        ```js
        // 使用原生xhr对象封装axios
        function axios({
            url,
            method = 'GET',
            params = {},  // get、delete 请求的参数
            data = {}     // post put 请求的参数
        }) {
            return new Promise((resolve, reject) => {
                // 1. 创建xhr对象
                const request = new XMLHttpRequest();

                // 2. 监听请求响应
                request.onreadystatechange = function () {
                    // 2.1 知道请求完成才响应
                    if (request.readyState !== 4) {
                        return;
                    }

                    const { status, statusText } = request;
                    // 2.2 检查status。如果status在 [200,300)的区间内，则resolve
                    if (200 <= status && status < 300) {
                        // 2.3 创建响应头对象
                        let headers = {}, h_key, h_value
                        request.getAllResponseHeaders()
                            .trim()
                            .split(/[\r\n]+/)
                            .forEach(line => {
                                [h_key, h_value] = line.split(': ');
                                headers[h_key] = h_value;
                            });

                        // 2.4 检查响应数据格式是否是JSON
                        let data = request.responseText;
                        if (headers['content-type'].indexOf('application/json') !== -1) {
                            data = JSON.parse(data);
                        }

                        // 2.5 创建响应数据，并返回
                        resolve({
                            data,
                            status,
                            statusText,
                            headers,
                            request
                        });
                    } else {
                        // 2.6 否则出现异常，reject
                        reject(new Error(`error status is ${status}`))
                    }
                }

                // 将请求方式转换为大写，再进行请求方式的判断
                method = method.toUpperCase();

                // 3. 设置请求方式，统一使用异步方式发送请求
                if (method === 'GET' || method === 'DELETE') {
                    // 拼接请求参数
                    const entries = Object.entries(params);
                    if (entries.length > 0) {
                        url += '?' + entries.map(elem => `${elem[0]}=${elem[1]}`).reduce((prev, cur) => `${prev}&${cur}`);
                    }
                    request.open(method, url, true);
                    request.send();
                } else if (method === 'POST' || method === 'PUT') {
                    request.open(method, url, true);

                    // 如果参数不为空，则发送参数
                    if (Object.keys(data).length > 0) {
                        // 默认使用json发送请求
                        request.setRequestHeader('Content-Type', 'application/json;charset=utf-8');
                        request.send(JSON.stringify(data));
                    } else {
                        request.send();
                    }
                }
            })
        }
        ```
    - 测试代码
        ```js
        document.getElementById('get').onclick = function () {
            axios({
                url: '/base/handle',
                method: 'get',
                params: { name: 123, age: 345 },    // get请求使用 params 发送参数
            }).then(resp => {
                console.log(resp.data)
            });
        }
        ```

# axios的特性
[top](#catalog)
- axios 比 jquery 更轻量
- axios 的特性
    - 基于 promise 的异步ajax请求库
    - 浏览器端和node端都可以使用
        - node端发送的就是普通http请求
    - <span style='color:red'>支持请求/响应拦截器</span>
    - 支持请求取消
    - 自动进行 请求/响应 的数据转换
    - 可以批量发送多个请求
        - 就是 `Promise.all`
    - 能够防止 XSRF


[top](#catalog)