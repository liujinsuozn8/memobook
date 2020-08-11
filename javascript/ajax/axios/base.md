<span id="catalog"></span>
- 参考
    - https://www.bilibili.com/video/BV1NJ41197u6

### 目录-axios
- [axios的特性](#axios的特性)
- [axios常用方法](#axios常用方法)
- [分别使用xhr和axios发送请求](#分别使用xhr和axios发送请求)
- [使用xhr封装axios的基本实现](#使用xhr封装axios的基本实现)
- [自定义axios函数对象---二次封装](#自定义axios函数对象---二次封装)
- [拦截器](#拦截器)
- [取消请求](#取消请求)
    - [取消请求的步骤](#取消请求的步骤)
    - [使用拦截器简化取消操作](#使用拦截器简化取消操作)
- [](#)

# axios的特性
[top](#catalog)
- axios 比 jquery 更轻量
- axios 的特性
    - 基于 promise 的异步ajax请求库
    - 浏览器端和node端都可以使用
        - node端发送的就是普通http请求
    - <span style='color:red'>支持请求/响应拦截器</span>
    - 支持取消请求
    - 自动进行 请求/响应 的数据转换
    - 可以批量发送多个请求
        - 就是 `Promise.all`
    - 能够防止 XSRF

# axios常用方法
[top](#catalog)
- 发送请求的方法

    |方法|功能|
    |-|-|
    |`axios(config)`|最基本的请求发送发放，可以发送各种方式的请求|
    |`axios(url [, config])`|向url发送get请求|
    |`axios.request(config)`|就是 `axios(config)`|
    |`axios.get(url [, config])`|向url发送get请求|
    |`axios.delete(url [, config])`|向url发送delete请求|
    |`axios.post(url [, data, config])`|向url发送post请求|
    |`axios.put(url [, data, config])`|向url发送put请求|

- 添加拦截器

    |方法|功能|
    |-|-|
    |`axios.interceptors.request.use()`|添加请求拦截器|
    |`axios.interceptors.response.use()`|添加响应拦截器|

- 取消请求的方法

    |方法|功能|
    |-|-|
    |`axios.Cancel()`|创建取消请求的错误对象|
    |`new axios.CancelToken()`|用于创建取消请求的 `token` 函数对象|
    |`axios.isCancel()`|判断是否是一个取消请求的错误|

- 批量发送请求的方法

    |方法|功能|
    |-|-|
    |`axios.all(promises)`|批量执行多个异步请求，类似于`Promise.all()`|
    |`axios.spread()`|指定接收所有成功数据的回调函数|

- 其他方法

    |方法|功能|
    |-|-|
    |`axios.defaults.xxx`|请求的默认全局配置|
    |`axios.create([config])`|自定义一个新的 axios 函数对象|

- config对象的配置

    |属性|功能|
    |-|-|
    |url|请求地址|
    |method|请求方式|
    |params|get、delete 请求参数|
    |data|post、put 请求参数|
    |||

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
        1. 使用axios中指定的请求方式发送请求
            ```js
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
        2. 直接使用 `axios` 函数对象发送请求
            ```js
            document.getElementById('getAxios').onclick = function(){
                axios({
                    url:'/base/handle',
                    params:{
                        name: 'tom',
                        age:22
                    }
                }).then(resp => console.log(resp.data));
            }

            document.getElementById('postAxios').onclick = function(){
                axios({
                    url:'/base/handle',
                    method: 'post',
                    data:{
                        name: 'tom',
                        age:22
                    }
                }).then(resp => console.log(resp.data));
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

# 自定义axios函数对象---二次封装
[top](#catalog)
- `axios.create(config)`
    - 根据指定配置，创建一个axios，
    - 新的 axios 函数对象**没有取消请求和批量发送请求的方法**，其他方法全部包含
    - 实际开发中，通过二次开发，可以为不同的请求接口或业务请求，定制 axios

- 示例
    - 参考代码
        - [src/base-server/public/html/create/base.html](src/base-server/public/html/create/base.html)
    - 浏览器访问地址
        - http://localhost:3333/html/create/base.html
    - 代码内容
        - 自定义 axios
            ```js
            // 根据请求的基础路径，创建自定义 axios
            const instance3333 = axios.create({
                // 指定基础请求路径
                baseURL: 'http://localhost:3333',
            });

            const instance5555 = axios.create({
                // 指定基础请求路径
                baseURL: 'http://localhost:5555',
            });
            ```
        - 使用自定义axios发送请求
            ```js
            // 向 3333 发送请求
            document.getElementById('get3333').onclick = function(){
                instance3333({
                    url: '/base/handle',
                    params: {
                        name:'bob',
                        age:13
                    }
                }).then(resp => console.log(resp.data));
            }

            // 向 5555 发送请求
            document.getElementById('get5555').onclick = function(){
                instance5555({
                    url: '/base/handle',
                    params: {
                        name:'tom',
                        age:24
                    }
                }).then(resp => console.log(resp.data));
            }
            ```

# 拦截器
[top](#catalog)
- 请求/响应拦截器都可以设置多个
- 添加拦截器之后，整体的执行顺序
    - 基本执行顺序
        1. 调用 `axios` 函数，并构造 `config` 对象
        2. 执行请求拦截器
        3. 发送请求
        4. 服务器返回喜爱那个硬
        5. 执行响应拦截器`
        6. 执行 `then()`，或 `catch`

    - 多个拦截器间的执行顺序
        - 多个**请求拦截器**
            - <span style='color:red'>倒序执行</span>，即: 先添加后执行
        - 多个**响应拦截器**
            - <span style='color:red'>正序执行</span>，即: 先添加先执行

- 请求拦截器的设置
    - 可以同时设置正常和异常的拦截器
    - 设置说明
        ```js
        axios.interceptors.request.use(
            // 接收 config 参数，并且必须返回 config
            // config 就是 axios 发送请求时所需的 config对象
            config =>{  // 正常处理的拦截器
                // ...
                return config;
            },
            // 接收一个异常对象，并且必须返回一个异常的Promise对象
            error =>{
                // ...
                return Promise.reject(error);
            }
        )
        ```
    - 注意事项
        - 正常响应 `config => {...}` 中，必须主动返回 `config`，否则会异常
            - `config` 对象就是 axios 发送请求时使用的配置内容
            - 如果没有返回 `config`，则无法正常发送请求

- 响应拦截器的设置
    - 可以同时设置正常和异常的拦截器
    - 设置说明
        ```js
        axios.interceptors.response.use(
            // 接收 response 参数，并且必须返回 response
            resp =>{  // 正常处理的拦截器
                console.log('response interceptors01 success');
                return resp;
            },
            error =>{   // 接收一个异常对象，并且必须返回一个异常的Promise对象
                console.log('request interceptors01 error');
                // return Promise.reject(error);
            }
        )
        ```
    - 注意事项
        - 正常响应 `resp => {...}`中，必须主动返回 `response`
            - 否则 `then()` 中将无法获得 `response` 对象
        - 异常响应 `error => {...}`中，必须主动返回一个异常的 Promise 对象
            - 否则，后续的拦截器将执行**正常响应**。不会执行 `catch`，而是执行 `then`

- 示例
    - 参考代码
        - [src/base-server/public/html/interceptor/base.html](src/base-server/public/html/interceptor/base.html)
    - 浏览器访问地址
        - http://localhost:3333/html/interceptor/base.html
    - 代码内容
        1. 设置 请求拦截器
            ```js
            axios.interceptors.request.use(
                // 接收 config 参数，并且必须返回 config
                // config 就是 axios 发送请求时所需的 config对象
                config =>{
                    console.log('request interceptors01 success');
                    return config;
                },
                // 接收一个异常对象，并且必须返回一个异常的Promise对象
                error =>{
                    console.log('request interceptors01 error');
                    return Promise.reject(error);
                }
            )
            axios.interceptors.request.use(
                config =>{
                    console.log('request interceptors02 success');
                    return config;
                },
                error =>{
                    console.log('request interceptors02 error');
                    return Promise.reject(error);
                }
            )
            ```
        2. 设置 响应拦截器
            ```js
            axios.interceptors.response.use(
                // 正常处理的拦截器
                // 接收 response 参数，并且必须返回 response
                resp =>{
                    console.log('response interceptors01 success');
                    return resp;
                },
                // 接收一个异常对象，并且必须返回一个异常的Promise对象
                error =>{
                    console.log('request interceptors01 error');
                    return Promise.reject(error);
                }
            )
            axios.interceptors.response.use(
                resp =>{
                    console.log('response interceptors02 success');
                    return resp;
                },
                error =>{
                    console.log('request interceptors02 error');
                    return Promise.reject(error);
                }
            )
            ```
        3. 发送正常请求
            ```js
            document.getElementById('success').onclick = function(){
                axios.post('/base/handle', {
                    name:'bob',
                    age:13
                }).then(resp => {
                    console.log(resp.data);
                }).catch(error => console.log(error))
            }
            // 输出
            // request interceptors02 success  <<<<<<<< 请求拦截器倒序输出
            // request interceptors01 success
            // response interceptors01 success <<<<<<<< 响应拦截器正序输出
            // response interceptors02 success
            // {name: "bob", age: 13, type: "post"}
            ```
        4. 发送异常请求
            ```js
            document.getElementById('error').onclick = function(){
                axios.get('/base/error').then(resp => {
                    console.log(resp.data);
                }).catch(error => console.log(error))
            }
            // 输出
            // request interceptors02 success
            // request interceptors01 success
            // GET http://localhost:3333/base/error 404 (Not Found)
            // request interceptors01 error
            // request interceptors02 error
            // Error: Request failed with status code 404
            ```

# 取消请求
## 取消请求的步骤
[top](#catalog)
- 基本使用步骤
    1. 设置一个全局变量 `cancel`，用于保存请求取消函数对象
        ```js
        let cancel;
        ```
        ```js
        ```js
    2. 创建`axios.CancelToken()`对象，并保存在`config`对象中。在实例化时，将取消函数对象保存到`cancel`
        ```js
        axios({
            url: '...',
            // 创建 `axios.CancelToken()` 对象
            // 将 取消函数对象 保存到cancel对象
            cancelToken: new axios.CancelToken(function executor(c){
                cancel = c;
            })
        })
        ```
    3. 为了防止重复发送相同请求，可以判断`cancel`对象是否存在
        - 如果存在，则说明正在重复发送请求，需要取消上一次请求
        - 判断方法
            ```js
            // 如果 cancel 存在，并且是一个函数对象，则取消请求
            if (typeof cancel === 'function'){
                cancel('init cancel');
            }
            ```
    4. 请求结束时，清空取消函数
        ```js
        axios(...).then(resp => {
            cancel = null;  // 3. 请求结束时，清空取消函数
        }).catch(error=>{
            // 如果是请求取消导致的异常，则不清空
            // 如果清空则会情况当前的新创建的对象，而不是上一次请求产生的对象
            if (axios.isCancel(error)){
                console.log(error)
            } else {
                cancel = null;  // 3. 请求结束时，清空取消函数
            }
        })
        ```
    5. 手动取消，和 第 3 步相同
        ```js
        // 如果 cancel 存在，并且是一个函数对象，则取消请求
        if (typeof cancel === 'function'){
            cancel('button cancel');
        }
        ```
- 执行取消函数后的执行流程
    - 无论实在发送请求前，还是发送请求后，执行取消函数后，都会执行 `catch()`
    - 在 `catch()` 中可以通过 `axios.isCancel(error)` 来判断是不是取消异常
- 什么时候清除全局变量 `cancel`?
    - 执行 `then()` 时清除
    - 执行 `catch()` 时，并且不是执行取消操作触法的 `catch`时，清除 `cancel`
- 示例
    - 参考代码
        - [src/base-server/public/html/abort/base.html](src/base-server/public/html/abort/base.html)
    - 浏览器访问地址
        - http://localhost:3333/html/abort/base.html
    - 代码内容
        ```js
        // 1. 设置一个全局变量，用于保存请求取消函数对象
        let cancel;
        function sendTimeOut() {
            if (typeof cancel === 'function'){
                cancel('init cancel');
            }
            axios({
                url: '/base/timeout',
                params: { name: 'bob', age: 22 },
                // 2. 保存请求取消函数对象
                cancelToken: new axios.CancelToken(function executor(c){
                    cancel = c;
                })
            }).then(resp => {
                cancel = null;  // 3. 请求结束时，清空取消函数
                console.log(resp.data);
            }).catch(error=>{
                if (axios.isCancel(error)){
                    console.log(error)
                } else {
                    cancel = null;  // 3. 请求结束时，清空取消函数
                    console.log(error)
                }
            })
        }

        // 取消请求事件
        function abort() {
            // 4. 如果 cancel 存在，并且是一个函数对象，则取消请求
            if (typeof cancel === 'function'){
                cancel('button cancel');
            }
        }
        ```

## 使用拦截器简化取消操作
[top](#catalog)
- 参考代码
    - http://localhost:3333/html/abort/abort_interceptor.html
- 拦截器实现
    ```js
    // 1. 设置一个全局变量，用于保存请求取消函数对象
    let cancel;

    // 2. 设置请求拦截器
    axios.interceptors.request.use(config => {
        // 2.1 检查 cancel 是否是函数对象，防止重复发送请求
        if (typeof cancel === 'function') {
            cancel('init cancel');
        }

        /* 2.2
            设置 cancel 对象，此时 config已经创建完了
            必须在这里 (检查完 cancel 之后) 设置

            如果在 axios({...}) 内部设置，
            执行顺序会变成 先设置 cancel，然后检查 cancel 是否是函数对象，
            这样每次都会因为已经创建了 cancel 而取消操作，导致永远无法发送请求
        */ 
        config.cancelToken = new axios.CancelToken(function executor(c) {
            cancel = c;
        })
        return config;
    })

    // 3. 设置响应拦截器
    axios.interceptors.response.use(
        resp => {
            cancel = null;  // 3.1 成功响应后，清除 cancel
            return resp;
        },
        error => {
            if (axios.isCancel(error)) {
                // 3.2 如果是 cancel 造成的异常，返回一个包含空对象的 Promise 对象
                console.log(error.message);
                return Promise.resolve({});
            } else {
                // 3.3 如果是异常，则清空 cancel， 并返回一个包含异常的 Promise 对象
                cancel = null;
                return new Promise.reject(error);
            }
        }
    )
    ```
- 按照正常的方式发送请求
    ```js
    function sendTimeOut() {
        axios({
            url: '/base/timeout',
            params: { name: 'bob', age: 22 },
        }).then(resp => {
            console.log(resp.data);
        }).catch(error => {
            console.log(error)
        })
    }
    ```
- 取消发送请求
    ```js
    function abort() {
        if (typeof cancel === 'function') {
            cancel('button cancel');
        }
    }
    ```

[top](#catalog)