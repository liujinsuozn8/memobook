<span id="catalog"></span>
- 参考

### 目录
- [SVG基本框架](#SVG基本框架)
- [在html中引入SVG](#在html中引入SVG)
    - [圆形](#圆形)
    - [矩形](#矩形)
    - [线](#线)
    - [polyline--折线](#polyline--折线)
    - [polygon--多边形](#polygon--多边形)
- [标签](#标签)
    - [g--分组标签](#g--分组标签)
    - [text--文字标签](#text--文字标签)
    - [image--图片标签](#image--图片标签)
- [实例--关系图](#实例--关系图)
- [用JS绘制SVG](#用JS绘制SVG)
    - [JS操作SVG--基础](#JS操作SVG--基础)
        - [创建svg节点](#创建svg节点)
        - [圆周分布svg节点](#圆周分布svg节点)
        - [为svg节点添加事件](#为svg节点添加事件)
    - [折线操作--绘制地图测距图](#折线操作--绘制地图测距图)
- [](#)


# SVG基本框架
[top](#catalog)
- svg基于xml技术
- svg基本框架
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
    <!-- svg 是根标签 -->
    <!-- xmlns 表示 svg 的命名空间 -->
    <svg xmlns="http://www.w3.org/2000/svg">
        <!-- 在根标签内部绘制图形 -->
    </svg>
    ```


# 在html中引入SVG
[top](#catalog)
- 图片标签: `<img/>`
    ```html
    <img src="xxx.svg" alt="">
    ```
- css 的背景图片
    ```html
    <div style='height: 300px; width: 200px;background: url(xxx.svg);'></div>
    ```
- 框架
    ```html
    <iframe src="xxx.svg" frameborder="0"></iframe>
    ```
- 直接将 svg 嵌入html
    ```html
    <div style='width: 200px;height: 200px;'>
        <svg width='100%' height='100%' xmlns="http://www.w3.org/2000/svg">
            <circle cx='100' cy='100' r='40' fill="#bbbbbb"></circle>
        </svg>
    </div>
    ````
- 示例
    - 参考代码
        - [src/base/import/test.svg](src/base/import/test.svg)

# 基本图形
## 圆形
[top](#catalog)
- 属性

    |属性|说明|能否在style中使用|
    |-|-|-|
    |cx|x轴坐标，表示圆心到浏览器左边的距离|-|
    |cy|y轴坐标|-|
    |r|半径|-|
    |fill|填充颜色。默认为黑色。`transparent`为透明/空心圆形|Y|
    |stroke|边框颜色|Y|
    |stroke-width|边框宽度|Y|
    |style|用样式的方式统一设置多种属性<br>不是所有的属性都能设置在 style 中|-|
- 示例
    - 参考代码
        - [src/base/shape/circle.svg](src/base/shape/circle.svg)
    - 代码内容
        ```xml
        <svg width="100%" height="100%" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <!-- 1. 圆形 -->
            <!--
                1.1. 实心圆形
                cx：x轴坐标，表示圆心到浏览器左边的距离
                cy：y轴坐标
                r：半径
                fill：填充颜色，默认为黑色
                stroke：边框颜色
                stroke-width: 边框宽度
            -->
            <circle cx='100' cy='100' r='40' fill="#ededed"
                stroke="#47e" stroke-width="5">
            </circle>

            <!-- 1.2 透明/空心圆形 -->
            <circle cx='200' cy='100' r='40' fill="transparent"
                stroke="#47e" stroke-width="5">
            </circle>

            <!-- 1.3 用样式设置圆形 -->
            <circle cx='300' cy='100' r='40' style='fill:transparent;stroke:#47e;stroke-width:5;'>
            </circle>
        </svg>
        ```

## 矩形
[top](#catalog)
- 属性

    |属性|说明|
    |-|-|
    |width|矩形的宽|
    |height|矩形的高|
    |x|矩形中心坐标|
    |y|矩形中心坐标|
    |rx|角弧度。该弧度由一个椭圆切出。`rx`表示椭圆中心的x轴距离|
    |ry|角弧度。该弧度由一个椭圆切出。`ry`表示椭圆中心的y轴距离|
    |ry|y轴坐标|
    |fill|填充颜色。默认为黑色。`transparent`为透明|
    |stroke|边框颜色|
    |stroke-width|边框宽度|

- `rx`、`ry` 如果只设置一个，则使用圆形切出
- 示例
    - 参考代码
        - [src/base/shape/rect.svg](src/base/shape/rect.svg)
    - 代码内容
        ```xml
        <rect width="100" height="100" x='100' y='100' fill='#adadad' rx='30' ry='40'/>
        ```

## 线
[top](#catalog)
- 属性

    |属性|说明|
    |-|-|
    |x1, y1|端点坐标|
    |x2, y2|端点坐标|
    |stroke|线的颜色，可以设置为`transparent`|
    |stroke-width|线的宽度|
    |stroke-opacity|线的透明度，可选值: `[0, 1]`|

- 示例
    - 参考代码
        - [src/base/shape/line.svg](src/base/shape/line.svg)
    - 代码内容
        ```xml
        <line x1="50" y1="50" x2="200" y2="200" stroke="#47e" stroke-width='5'
            stroke-opacity='0.5'/>
        ```

## polyline--折线
[top](#catalog)
- 属性
    - points，设置折线节点的坐标
        - 用`空格`分割坐标
            ```xml
            <polyline ponits='x1 y1 x2 y2 x3 y3 ....'>
            ```
        - 用`逗号`分隔坐标
            ```xml
            <polyline ponits='x1,y1,x2,y2,x3,y3,....'>
            ```
        - 用`空格`、`逗号`可以混用
    - `fill='none'`
        - 折线默认有填充，需要清除填充效果
- 示例
    - 参考代码
        - [src/base/shape/polyline.svg](#src/base/shape/polyline.svg)
    - 代码内容
        ```xml

        ```

## polygon--多边形
[top](#catalog)
- polygon 默认是**自动闭合**的
- 相关属性与 `polygon` 类似



# 标签
## g--分组标签
[top](#catalog)
- `<g>` 是一个容器标签，用来组合元素
- 功能: 可以统一控制图形
    - 设置整体样式
    - 设置 id
- 设置偏移位置 `transform='translate(x轴, y轴)'`

- 示例: 同心圆
    - 参考代码
        - [src/base/tag/g.svg](src/base/tag/g.svg)
    - 代码内容
        ```xml
        <svg width="500" height="700" version="1.1" xmlns="http://www.w3.org/2000/svg">
            <!-- 统一设置偏移位置 -->
            <g transform='translate(300,400)' stroke='black' stroke-width='2'>
                <circle r='200' fill='transparent' />
                <circle r='150' fill='transparent' />
                <circle r='100' fill='transparent' />
                <circle r='50' fill='transparent' />
            </g>
        </svg>
        ```

## text--文字标签
[top](#catalog)
- 属性

    |属性|说明|
    |-|-|
    |x|默认以文字左下角为基准的，x轴坐标，可以通过`text-anchor`设置基准|
    |y|默认以文字左下角为基准的，y轴坐标，可以通过`text-anchor`设置基准|
    |text-anchor|设置文字对齐的基准，包括: `start`、`middle`、`end`|
    |font-size|文字大小|
    |fill|填充颜色。默认为黑色。`transparent`为透明|

- 垂直居中
    - `text-anchor='middle'` 只能保证水平居中，无法保证垂直居中
    - 垂直居中需要手动计算
        - 计算方法
            ```
            y + font-size / 2 - 1
            ```
        - 即在 y 轴上添加半个字符的大小。但是底部是根据`基线`对齐的，会多移动一点，所以要减 1 或者 2
- 示例
    - 参考代码
        - [src/base/tag/text.svg](src/base/tag/text.svg)
    - 代码内容
        ```xml
        <g cursor="pointer">
            <circle cx='100' cy='100' r='50' fill='transparent' stroke='black' stroke-width='5'/>
            <!-- 文字垂直居中: y + font-size / 2 - 1 -->
            <text x='100' y='106' font-size="15" text-anchor="middle">tests</text>
        </g>
        ```

## image--图片标签
[top](#catalog)
- 属性

    |属性|说明|
    |-|-|
    |x|图片**左上角**的 x 轴 坐标|
    |y|图片**左上角**的 y 轴 坐标|
    |width|图片的宽度|
    |height|图片的高度|
    |xlink:href|图片的路径|

# 实例--关系图
[top](#catalog)
- 参考代码
    - [src/base/demo/base.svg](src/base/demo/base.svg)
- 代码内容
    ```xml
        <g cursor="pointer">
        <line x1="100" y1="100" x2="390" y2="390" stroke='#ababab' stroke-width='1'/>
        <!-- 设置一条透明的、更宽的线，使得上面大小为 1 的线可以更容易被选中 -->
        <line x1="100" y1="100" x2="390" y2="390" stroke='transparent' stroke-width='10'/>
        <rect x='227' y='227' width="50" height="50" fill='#ababab' rx='5'/>
        <text x='250' y='268' font-size="50" fill='white' text-anchor="middle">?</text>
    </g>
    <!-- 在线的两个端点绘制圆形 -->
    <g cursor="pointer">
        <circle cx='100' cy='100' r='90' fill='white' stroke='black' stroke-width='2'/>
        <text x='100' y='110' font-size="30" fill='black' text-anchor="middle">testa</text>
    </g>
    <g cursor="pointer">
        <circle cx='390' cy='390' r='90' fill='white' stroke='black' stroke-width='2'/>
        <text x='390' y='400' font-size="30" fill='black' text-anchor="middle">center</text>
    </g>
    ```

# 用JS绘制SVG
## JS操作SVG--基础
### 创建svg节点
[top](#catalog)
- 创建节点的方法
    ```js
    /*
        按照指定的 name space 创建元素
        两个参数:
            命名空间
            标签名
    */
    document.createElementNS("http://www.w3.org/2000/svg", svg节点标签名);
    ```
- svg 节点只有创建方式与普通 DOM 节点不同，其他操作方式相同
- 创建时，应该从根节点: `svg` 节点开始创建，每个内部节点都需要添加到 `svg` 节点中
- 全部创建完之后，应该将 `svg` 节点添加到`父DOM节点`中
- 示例--用JS绘制关系图
    - 参考代码
        - [src/base/jsusage/base/create.html](src/base/jsusage/base/create.html)
    - 代码内容
        ```js
        // 创建 SVG 节点
        function createSVGNode(type, props) {
            /*
                按照指定的 name space 创建元素
                两个参数:
                    命名空间
                    标签名
            */
            var node = document.createElementNS("http://www.w3.org/2000/svg", type);
            for (var k in props) {
                node.setAttribute(k, props[k]);
            }
            return node;
        }

        // 设置 svg 标签的属性
        var svg = createSVGNode('svg', {
            'height': '100%',
            'width': '100%',
            'xmlns': "http://www.w3.org/2000/svg",
        });

        // 手动创建
        // g1
        var g1 = createSVGNode('g', { cursor: 'pointer' });
        var line1 = createSVGNode('line', {
            x1: '100',
            y1: '100',
            x2: '390',
            y2: '390',
            stroke: '#aaaaaa',
            'stroke-width': '5'
        });
        var line2 = createSVGNode('line', {
            x1: '100',
            y1: '100',
            x2: '390',
            y2: '390',
            stroke: 'transparent',
            'stroke-width': '20'
        })
        var rect = createSVGNode('rect', {
            width: '50',
            height: '50',
            fill: '#aaaaaa',
            x: '225',
            y: '225',
            rx: '10',
        })
        var g1_text = createSVGNode('text', {
            x: '250',
            y: '265',
            'font-size': '50',
            fill: 'white',
            'text-anchor': 'middle',
        })
        g1_text.innerHTML = '?'

        g1.appendChild(line1)
        g1.appendChild(line2)
        g1.appendChild(rect)
        g1.appendChild(g1_text)
        svg.appendChild(g1);

        // 获取根标签
        var box = document.getElementById('box');

        // 将 svg 添加到 父DOM节点 中
        box.appendChild(svg);
        ```

### 圆周分布svg节点
[top](#catalog)
- 圆周分布计算方式
    1. 根据节点数量，计算每个节点所占的角度
        ```
        itemCount / 360
        ```
    2. 用分布的半径 和 角度 分别计算距离圆心坐标的位置
        ```js
        // 将数值还原成角度: angler * Math.PI / 180
        // 用 sin 计算 x 轴偏移量，用 cos 计算 y 轴偏移量
        var cx = Math.sin(i * angler * Math.PI / 180) * centerR + centerX;
        var cy = Math.cos(i * angler * Math.PI / 180) * centerR + centerY;
        ```
- 示例
    - 参考代码
        - [src/base/jsusage/base/circle_layout.html](src/base/jsusage/base/circle_layout.html)
    - 代码内容
        ```js
                function createOtherNodes(infos) {
            var result = [];

            // 获取节点数量
            var infoCount = infos.length;
            // 计算每个节点所需的角度
            var angler = 360 / infoCount;
            // 设置分布半径
            var centerR = 300;

            for (var i = 0; i < infoCount; i++) {
                var info = infos[i];
                // 将数值还原成角度: angler * Math.PI / 180
                var cx = Math.sin(i * angler * Math.PI / 180) * centerR + centerX;
                var cy = Math.cos(i * angler * Math.PI / 180) * centerR + centerY;

                var g = createSVGNode('g', { cursor: 'pointer' });

                // ........

                // 绘制圆形
                var circle = createSVGNode('circle', {
                    cx,
                    cy,
                    r: '50',
                    stroke: '#aaaaaa',
                    'stroke-width': '5',
                    fill: 'white',
                })
                g.appendChild(circle);

                var text = createSVGNode('text', {
                    x: cx,
                    y: cy + 15, // 文字居中
                    'text-anchor': 'middle',
                    'font-size': '30',
                })
                text.innerHTML = info.text
                g.appendChild(text);

                result.push(g);
            }
            return result;
        }
        ```

### 为svg节点添加事件
[top](#catalog)
- svg 可以设置 id 和 class ，可以通过 id、class、标签名来获取节点
- 可以通过 style 获取样式对象来设置样式
- 示例
    - 参考代码
        - [src/base/jsusage/base/svg_event.html](src/base/jsusage/base/svg_event.html)
    - 代码内容
        ```js
        var g = createSVGNode('g', { cursor: 'pointer', class:'other' });
        // 绑定鼠标移入移出事件
        g.onmouseenter = function(){
            var otherCircle = this.querySelector('.other-circle');
            otherCircle.setAttribute('stroke', 'red');
            otherCircle.style.transition = 'r 0.5s';
            otherCircle.setAttribute("r", 60);

            this.querySelector('.other-line').setAttribute('stroke', 'blue');
            this.querySelector('.other-rect').setAttribute('fill', 'orange');
        }
        g.onmouseleave = function(){
            var otherCircle = this.querySelector('.other-circle');
            otherCircle.setAttribute('stroke', '#aaaaaa');
            otherCircle.style.transition = '';
            otherCircle.setAttribute("r", 50);
            this.querySelector('.other-line').setAttribute('stroke', '#aaaaaa');
            this.querySelector('.other-rect').setAttribute('fill', '#aaaaaa');
        }
        ```

## 折线操作--绘制地图测距图
[top](#catalog)
- 参考代码
    - [src/base/jsusage/polyline/base.html](src/base/jsusage/polyline/base.html)
- 实现方式
    - 需要添加三种事件
        1. `mousedown`，每次点击鼠标时，计算鼠标在 svg 节点内的位置，并修改折线的 `points` 属性
            - 第一次点击时，还没有折线元素，需要进行创建
        2. `mousemove`，鼠标移动时，实时计算鼠标位置，并更新 `points` 属性
        3. `contextmenu`，点击右键时，删除 `mousemove` 事件，停止绘制
    - `mousedown`
        ```js
        // 保存折线节，鼠标点击、移动时，实时修改 points 属性
        var polyline=null;

        // 保存 polyline 的point数据
        var prev_pointsStr = ''

        // 为 svg 节点添加点击事件，每次点击都修改折线的 points 属性
        svg.addEventListener('mousedown', function (event) {
            // 计算鼠标点击时在 svg 元素中的具体位置
            var x = event.clientX - box.offsetLeft;
            var y = event.clientY - box.offsetTop;
            // 如果是第一次点击，则创建 折线
            if (!polyline){
                polyline = createSVGNode('polyline', {
                    stroke: 'black',
                    'stroke-width': '5',
                    fill: 'none'
                })
                svg.appendChild(polyline);
                prev_pointsStr += `${x}, ${y}`;

                // 为svg添加移动事件，移动时，计算线段位置，实现线段移动的效果
                svg.addEventListener('mousemove', lineMoveHandle);
                // 为svg添加右键点击事件，右键点击时，删除移动事件
                svg.addEventListener('contextmenu', deleteLineMoveHandle);
            } else {
                prev_pointsStr += `, ${x}, ${y}`;
            }
            polyline.setAttribute('points', prev_pointsStr);

            // 每次点击的时候，在点击位置创建一个圆
            svg.appendChild(createSVGNode('circle', {
                cx:x,
                cy:y,
                r:5,
                fill:'white',
                stroke:'red',
                'stroke-width':'2'
            }));
        });
        ```
    - `mousemove`
        ```js
        // 移动时，计算线段位置，实现线段移动的效果
        function lineMoveHandle(event) {
            var x = event.clientX - box.offsetLeft;
            var y = event.clientY - box.offsetTop;
            polyline.setAttribute('points', `${prev_pointsStr}, ${x}, ${y}`);
        }
        ```
    - `contextmenu`
        ```js
        // 为svg添加右键点击事件，右键点击时，删除移动事件
        function deleteLineMoveHandle(event){
            svg.removeEventListener('mousemove', lineMoveHandle);
            // 阻止菜单弹出
            event.cancelBubble = true;
        }
        ```


[top](#catalog)

|stroke-width|线的宽度|
|stroke-opacity|线的透明度，可选值: `[0, 1]`|
cursor="pointer" 鼠标移入的效果
fill 不设置默认为黑色，`none`

stroke 为边着色
fill 为面积着色