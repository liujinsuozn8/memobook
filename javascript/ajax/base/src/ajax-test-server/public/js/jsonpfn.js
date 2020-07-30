function jsonp(options){
    // 0. 创建默认值
    let defaults = {
        url:'',
        data:{},
        success:function(){}
    };

    Object.assign(defaults, options);

    // 1. 生成随机函数名: fn + 随机数，需要去除随机数中的小数点
    const randomName = 'fn' + (Math.random().toString().replace('.', ''));

    // 2. 将函数绑定到 window 对象，变成全局对象
    window[randomName] = defaults.success;

    // 3. 设置参数
    let paramStr = 'callback=' + randomName;
    for(let k in defaults.data){
        paramStr += `&${k}=${defaults.data[k]}`;
    }

    // 4. 创建 script 标签
    const script = document.createElement('script');
    script.src = `${defaults.url}?${paramStr}`
    script.onload = function(){
        // 5. 删除 script 标签
        document.body.removeChild(this);
        // 6. 从 window 对象中删除函数
        delete window[randomName];
    };

    // 5. 发送请求
    document.body.appendChild(script);
}