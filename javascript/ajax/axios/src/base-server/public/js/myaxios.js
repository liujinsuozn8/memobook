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