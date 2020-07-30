<span id="catalog"></span>

### 目录-输入框邮箱验证
- [实现方法](#实现方法)
- [请求接口设计](#请求接口设计)
- [实现代码](#实现代码)

# 实现方法
[top](#catalog)
- 在焦点离开输入框时，向服务端发送**get**请求，验证邮箱有效性
- 在服务端接受请求，并检验邮箱有效性
    - 在服务端设置一组默认的邮箱列表
    - 检查请求参数是否在默认邮箱列表中
        - 如果在，返回失败
        - 如果不在，返回成功

# 请求接口设计
[top](#catalog)
- 接口描述
    - 验证邮箱地址是否唯一
- 默认已使用的邮箱
    ```
    aaabbbcc@123.com
    qwerty123@qq.com
    asdf12@qq.com
    ```
- 请求地址
    - `/01email/emailCheck`
- 请求方式
    - GET
- 参数

    |参数名|必须|类型|说明|
    |-|-|-|-|
    |email|是|string|待验证的邮箱地址|

- 返回值
    - 成功: 邮箱地址唯一
        - http code: 200
        - 返回数据
            ```js
            { msg: 'has been used'}
            ```
    - 失败: 邮箱已使用
        - http code: 400
        - 返回数据
            ```js
            { msg: 'usable'}
            ```

# 实现代码
[top](#catalog)
- 浏览器地址:
    - http://localhost:3333/html/01email/email.html
- 在焦点离开输入框时，发送**get**请求，验证邮箱有效性
    - 参考代码
        - [src/ajax-inaction/public/html/01email/email.html](src/ajax-inaction/public/html/01email/email.html)
    - 代码内容
        ```js
        const emailCheckInfo = document.getElementById('emailCheckInfo');
        // 1. 为文本框绑定焦点离开事件
        const emailInput = document.getElementById('email');
        emailInput.onblur = function () {
            // 2. 调用 ajax 封装函数，发送请求
            ajax({
                type: 'get',
                url: '/01email/emailCheck',
                data: {
                    email: this.value
                },
                // 验证成功/失败之后，显示服务端返回的信息，并设置响应的样式
                success(data){
                    emailCheckInfo.innerText = data.msg;
                    emailCheckInfo.className= 'success-info';
                },
                error(data){
                    emailCheckInfo.innerText = data.msg;
                    emailCheckInfo.className= 'error-info';
                }
            })
        }
        ```
- 服务端响应
    - 参考代码
        - [src/ajax-inaction/router/01email.js](src/ajax-inaction/router/01email.js)
    - 代码内容
        ```js
        router.get('/emailCheck', async ctx=>{
            // 1. 获取参数 email
            const paramEmail = ctx.request.query.email;
            // 2. 设置默认的 email 数组
            const defaultEmails = [
                'aaabbbcc@123.com',
                'qwerty123@qq.com',
                'asdf12@qq.com',
            ];

            // 3. 如果请求参数在默认数组中，则已使用，返回失败
            if (defaultEmails.includes(paramEmail)){
                ctx.status = 400;
                ctx.body = { msg: 'has been used'};
            } else {
                // 4. 如果请求参数不在默认数组中，则未使用，返回成功
                ctx.body = { msg: 'usable'};
            }
        });
        ```

