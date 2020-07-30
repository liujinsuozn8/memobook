<span id="catalog"></span>

### 目录-通过JSONP获取腾讯天气并显示数据
- [获取腾讯天气的访问接口](#获取腾讯天气的访问接口)
- [请求接口设计](#请求接口设计)
- [实现代码](#实现代码)

# 获取腾讯天气的访问接口
[top](#catalog)
- 获取步骤
    1. 控制台切换到Network
    2. 刷新页面
    3. 找到 `common?source=pc&weather_type=observe...` 的请求，请求的内容就是天气接口
- 天气接口的格式
    - 返回数据的内容
        ```js
        jQueryxxxxxxxxxxx({data: {alarm: {}, forecast_1h: {,…},…}, message: "OK", status: 200})
        ```
    - 接口是标准的JSONP格式

- 同源检查
    - 腾讯天气的地址开头是: tianqi.qq
    - 请求信息的地址开头是: wis.qq
    - 地址不同源，所以需要使用JSONP发送请求

# 请求接口设计
[top](#catalog)
- 请求地址
    - https://wis.qq.com/weather/common
- 请求方式
    - get
- 请求参数

    |参数名|必须|类型|说明|
    |-|-|-|-|
    |source|是|string|pc、xw|
    |weather_type|是|string|forecast_1h 未来48小时、forecast_24h 未来7天。参数之间使用 `|` 分隔|
    |province|是|string|省份|
    |city|是|string|城市|

- 返回值
    ```js
    {
        data: {
            forecast_1h: {
                0: {
                    degree: "温度",
                    update_time: "时间",
                    weather: "天气",
                    weather_code: "天气代码",
                    weather_short: "天气简要名称",
                },
                1: {...},
                ....
            },
            forecast_24h: {}
        },
        message: "OK",
        status: 200,
    }
    ```

# 实现代码
[top](#catalog)
- 参考代码
    - [src/ajax-inaction/public/html/04tecent_weather/weather.html](src/ajax-inaction/public/html/04tecent_weather/weather.html)
- 浏览器访问地址
    - http://localhost:3333/html/04tecent_weather/weather.html
- 代码内容
    ```js
    // 1. 发送JSONP请求
    jsonp({
        url: 'https://wis.qq.com/weather/common',
        data:{
            source: 'pc',
            weather_type: 'forecast_1h',
            province: '北京',
            city: '北京'
        },
        success(result){
            // 2. 获取数据后，创建html片段
            let htmlStr = '';
            for (let row of Object.values(result.data.forecast_1h)){
                // 格式化时间
                var timeStr = row.update_time.substring(0, 4) + "年" +
                                row.update_time.substring(4, 6) + "月" +
                                row.update_time.substring(6, 8) + "日" +
                                row.update_time.substring(8, 10) + "时";
                htmlStr += `
                <tr>
                    <td>${row.degree}</td>
                    <td>${timeStr}</td>
                    <td>${row.weather}</td>
                </tr>
                `;
            }
            // 3. 将html片段添加到页面中
            var weatherTable = document.querySelector('.weatherTable tbody');
            weatherTable.innerHTML += htmlStr;
        }
    });
    ```