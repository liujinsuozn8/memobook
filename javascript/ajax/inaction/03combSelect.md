<span id="catalog"></span>

### 目录-省市区三级拉框联动
- [实现方法](#实现方法)
- [请求接口设计](#请求接口设计)
- [注意事项](#注意事项)
- [实现代码](#实现代码)

# 实现方法
[top](#catalog)
- 页面初始化
    1. 通过接口，获取`省`信息
    2. 使用JS获取省下拉框元素
    3. 将服务端返回的省份信息显示在下拉框中
    4. 为省、市下拉框绑定 `onchange` 事件
- 选择省份时
    - 根据省份id，获取城市信息
- 选择市时
    - 根据市id，获取区信息
- 下拉框的选择内容发生改变
    1. 触发 `onchange` 事件
    2. 获取各下拉框的数据，并作为参数发送请求
    3. 将服务端返回的省份信息显示在下拉框中

# 请求接口设计
[top](#catalog)
- 获取省信息
    - 请求地址
        - `/03combSelect/province`
    - 请求方式
        - GET
    - 返回值
        ```js
        [{
            id:'001',
            name: 'a'
        },{
            id:'002',
            name: 'b'
        },{
            id:'003',
            name: 'c'
        }]
        ```
- 根据省id获取市信息
    - 请求地址
        - `/03combSelect/city`
    - 请求方式
        - GET
    - 请求参数

        |参数名|必须|类型|说明|
        |-|-|-|-|
        |id|是|string|省份id|
    - 返回值
        ```js
        [{
            id:'110',
            name: 'a1'
        },{
            id:'120',
            name: 'a2'
        },{
            id:'130',
            name: 'a3'
        }]
        ```
- 根据省id、市id获取区信息
    - 请求地址
        - `/03combSelect/area`
    - 请求方式
        - GET
    - 请求参数

        |参数名|必须|类型|说明|
        |-|-|-|-|
        |id|是|string|省份id|
    - 返回值
        ```js
        [{
            id:'111',
            name: 'a1-11'
        },{
            id:'112',
            name: 'a1-12'
        },{
            id:'113',
            name: 'a3-13'
        }]
        ```

# 注意事项
[top](#catalog)
- 默认选项
    - 每个下拉列表都必须有一个默认选项: `please select`
    - 当选择了默认选项时，应该忽略这次请求
- 修改省后的一系列设置
    1. 根据选择的 省id，获取市数据，并设置到页面
    2. 同时，因为市数据的改变，区数据及其显示内容需要清空
- 选择默认选项后再修改为原来的选项
    1. 这种操作不应该再重新发送请求
    2. 可以在dom对象自身设置一个变量，来保存每次修改后的值
    3. 保存时，如果选择的时默认选项则不修改
    4. 每次触发 `onchange` 事件前，与保存的数据进行比较
        - 如果相同，则不发送请求
        - 如果不相同，则发送请求
    5. 需要为 省、市 两个下拉框添加该处理

# 实现代码
[top](#catalog)
- 浏览器地址
    - http://localhost:3333/html/03combSelect/combSelect.html
- 请求代码
    - 参考代码
        - [src/ajax-inaction/public/html/03combSelect/combSelect.html](src/ajax-inaction/public/html/03combSelect/combSelect.html)
    - 代码内容
        - 设置默认选项
            ```js
            const defaultValue = 'default';
            const defaultOption = `<option value="${defaultValue}">please select</option>`;
            ```
        - 页面初始化
            ```js
            ajax({
                type: 'get',
                url: '/03combSelect/province',
                success(data) {
                    // 将返回的 省 数据设置到页面中
                    optStr = defaultOption;
                    for (const node of data) {
                        optStr += `<option value="${node.id}">${node.name}</option>`;
                    }

                    provinceSelector.innerHTML = optStr;
                }
            });
            ```
        - 为 省 下拉框设置 onchange 事件
            ```js
            // 2.1 根据省id获取 市 数据
            provinceSelector.selectValue = defaultValue;
            provinceSelector.onchange = function () {
                // 2.1.1 如果选择了 default，则忽略当前选择
                if (this.value === defaultValue) return;

                // 2.1.2 如果当前选择的值和历史记录的值相同，则不发送请求
                // 避免前一次选择的是 默认选项
                const curValue = this.value;
                if (provinceSelector.selectValue === curValue) return;

                // 2.1.3 发送请求
                ajax({
                    type: 'get',
                    url: '/03combSelect/city',
                    data: {
                        id: curValue
                    },
                    success(data) {
                        // 2.1.4 将 市 的数据设置到页面
                        optStr = defaultOption;
                        for (const node of data) {
                            optStr += `<option value="${node.id}">${node.name}</option>`;
                        }
                        citySelector.innerHTML = optStr;

                        // 2.1.4 清空 区 的数据
                        areaSelector.innerHTML = defaultOption;

                        // 2.1.5 将选项保存到历史记录
                        // 保存当前选择的数据
                        provinceSelector.selectValue = curValue;
                        // 清空区中保存的历史数据
                        citySelector.selectValue = defaultValue;
                    }
                })
            };
            ```
        - 为 市 下拉框设置 onchange 事件
            ```js
            // 2.2 根据 市i 获取 区 数据
            citySelector.selectValue = defaultValue;
            citySelector.onchange = function () {
                // 2.2.1 如果选择了 default，则忽略当前选择
                if (this.value === defaultValue) return;

                // 2.2.2 如果两次选择的值相同，则不发送请求。避免前一次选择的是 默认选项
                const curValue = this.value;
                if (citySelector.selectValue === curValue) return;

                // 2.2.3 发送请求
                ajax({
                    type: 'get',
                    url: '/03combSelect/area',
                    data: {
                        id: curValue
                    },
                    success(data) {
                        // 2.2.4 将 市 的数据设置到页面
                        optStr = defaultOption;
                        for (const node of data) {
                            optStr += `<option value="${node.id}">${node.name}</option>`;
                        }
                        area.innerHTML = optStr;

                        // 2.2.5 将选项保存到历史记录
                        citySelector.selectValue = curValue;
                    }
                })
            };
            ```