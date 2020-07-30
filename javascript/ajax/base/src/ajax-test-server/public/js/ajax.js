function ajax(options) {
    // 1. 设置默认对象
    var defaults = {
        type: 'get',
        url: '',
        data: {},
        contentType: ajax.PARAM_TYPE_COMMON,
        success: function(){},
        error: function(){},
    }

    // 2. 将选项参数拷贝到默认对象中，后续只使用默认对象
    Object.assign(defaults, options);

    var xhr = new XMLHttpRequest();
    // 3. 根据请求类型，来处理路径、参数、http请求头
    if (defaults.type === 'get') {
        var url = defaults.url;
        // 如果包含 data 选项，并且参数个数不为0，则在地址后面拼接请求参数
        if (Object.keys(defaults.data).length > 0) {
            url += '?' + ajax.paramFormat(ajax.PARAM_TYPE_COMMON, defaults.data);
        }

        // 设置请求方式和请求路径，并发送请求
        xhr.open('get', url);
        xhr.send();
    } else if (defaults.type === 'post') {
        var contentType = defaults.contentTyp || ajax.PARAM_TYPE_COMMON;
        var params = '';
        // 如果包含 data 选项，并且参数个数不为0，则在地址后面拼接请求参数
        if (Object.keys(defaults.data).length > 0) {
            params = ajax.paramFormat(contentType, defaults.data);
        }

        // 设置请求方式、请求路径、请求参数，并发送请求
        xhr.open('post', defaults.url);
        // 设置请求头
        xhr.setRequestHeader('Content-Type', contentType);
        xhr.send(params);
    }

    // 4. 监听服务器返回的响应
    xhr.onload = function () {
        // 4.1 获取响应头信息，如果是JSON格式，则转换为对象类型
        var resData = xhr.responseText;
        if (xhr.getResponseHeader('Content-Type').indexOf(ajax.RES_TYPE_JSON) !== -1){
            resData = JSON.parse(resData);
        }

        // 4.2 根据http状态码，调用不同的处理函数
        if (xhr.status === 200) {
            defaults.success(resData, xhr);
        } else {
            defaults.error(resData, xhr);
        }
    }
}

// 静态变量: 请求头类型
ajax.PARAM_TYPE_COMMON = 'application/x-www-form-urlencoded';
ajax.PARAM_TYPE_JSON = 'application/json';
// 静态变量: 响应头类型
ajax.RES_TYPE_COMMON = 'text/plain';
ajax.RES_TYPE_JSON = 'application/json';

// 静态方法: 格式化请求参数
ajax.paramFormat = function (contentType, data) {
    if (contentType === ajax.PARAM_TYPE_COMMON) {
        // 普通格式
        var params = '';
        for (var k in data) {
            params += `${k}=${data[k]}&`;
        }
        return params.replace(/&$/, '');
    } else if (contentType === ajax.PARAM_TYPE_JSON) {
        // JSON格式
        return JSON.stringify(data);
    } else {
        throw 'Unknown ContentType';
    }
}