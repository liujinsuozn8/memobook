<span id="catalog"></span>
- 参考
    - https://www.bilibili.com/video/BV16W41127aQ

### 目录--轮播图开发
- [渐变式轮播图](#渐变式轮播图)
    - [渐变式---参考代码](#渐变式---参考代码)
    - [渐变式---原理](#渐变式---原理)
    - [渐变式---图片部分](#渐变式---图片部分)
    - [渐变式---向前、向后按钮部分](#渐变式---向前、向后按钮部分)
    - [渐变式---底部索引按钮](#渐变式---底部索引按钮)
- [](#)
- [](#)


# 渐变式轮播图
## 渐变式---参考代码
[top](#catalog)
- [src/swiper/swiper_by_opacity.html](src/swiper/swiper_by_opacity.html)

## 渐变式---原理
[top](#catalog)
- 渐变效果的实现
    - 将用于展示的图片部分堆叠在一起
    - 将需要展示的图片的 `z-index` **调高，使其可以优先显示**
    - 增加图片切换时的透明度特效，即设置 `opacity` 属性，来增加渐变效果
- 切换的方式
    1. 使用向前、向后按钮切换
    2. 使用底部的索引按钮切换
- 如何切换
    - 使用一个`index`记录，当前**正在显示**的图片的`索引`
    - 使用向前、向后按钮切换时，需要实时的增、减 `index`，然后让 `index` 对应的 `z-index` 的值最高
    - 使用底部的索引按钮切换时，需要根据按钮的索引值，重设 `index`，然后让 `index` 对应的 `z-index` 的值最高

## 渐变式---图片部分
[top](#catalog)
- 布局与设置
    1. 引入图片
        - 可以使用 `ul` + `li` 或 `div` 来包含`img`，来引入图片
        - 引入后，一般是纵向的、垂直排列的
    2. 堆叠图片
        - 外部使用**相对定位**
        - 所有图片的容器使用**绝对定位**
        - 然后使所有图片堆叠在一起
    3. 为图片增加切换效果
        - 需要显示的图片的 `z-index` 最大
        - 切换时，设置 `opacity` 属性
            - 正在显示的， `opacity = 1`，完全不透明
            - 非显示的， `opacity = 0`，完全透明
    4. 初始状态
        - 需要将第一张图片设置为显示状态
- 代码内容
    - 引入图片
        ```html
        <div class="wrap">
            <ul class="list">
                <li class="item item-active">
                    <span>1</span>
                    <img src="./img/1.png" alt="">
                </li>
                <li class="item">
                    <span>2</span>
                    <img src="./img/2.png" alt="">
                </li>
                <li class="item">
                    <span>3</span>
                    <img src="./img/3.png" alt="">
                </li>
                <li class="item">
                    <span>4</span>
                    <img src="./img/4.png" alt="">
                </li>
                <li class="item">
                    <span>5</span>
                    <img src="./img/5.png" alt="">
                </li>
            </ul>
        </div>
        ```
    - 设置css
        ```css
        /* 最外层容器负责设置大小 */
        .wrap{
            position: relative;
            width: 700px;
            height: 400px;
        }

        /* 1.堆叠图片: 列表部分开启相对定位 */
        .list{
            width: 100%;
            height: 100%;
            position: relative;
        }

        /*1.堆叠图片: 图片容器开启绝对定位，所有图片就可以堆叠在一起 */
        .item{
            width: 100%;
            height: 100%;
            position: absolute;
            /*
                2.3 为图片增加切换效果:
                    - 非显示状态的图片的 opacity = 0，完全透明，则不显示
                    - 图片切换到 item-active 状态时，显示过度效果
            */
            opacity: 0;
            transition: opacity 1s;
        }

        /*
            2. 为图片增加切换效果:
                2.1 为激活状态的图片添加 z-index，最优先显示
                2.2 设置 opacity = 1，完全不透明，即可正常显示
        */
        .item-active {
            z-index: 9;
            opacity: 1;
        }
        .item img{
            width: 100%;
            height: 100%;
        }

        .item span{
            position: absolute;
            top: 50%;
            left: 50%;
        }
        ```


## 渐变式---向前、向后按钮部分
[top](#catalog)
- 布局与设置
    1. 外部容器开启**相对定位**，按钮开启**绝对定位**，并分别定位到图片的左右两侧
    2. 为按钮绑定点击事件
        1. 添加 `index` 来记录当前**正在显示**的图片的索引
        2. 每次点击按钮时，先清空所有按钮的 `item-active` 属性
        3. 增、减 `index`
            - **越界问题**
                - `index` 在计算过程中**超出图片数量**，或者变为**负数** 的,
                - 为了防止该问题，每次运算后需要进行取模
                    ```js
                    ( index + 图片数量 +/- 1 ) % 图片数量
                    ```
        4. 根据`index`，获取图片容器，并将该图片设置为显示状态
- 代码内容
    - 按钮布局
        ```html
        <div class="wrap">
            <ul class="list">
                ....
            </ul>

            <button type="button" class="btn btn-left"></button>
            <button type="button" class="btn btn-right"></button>
        </div>
        ```
        ```css
        .btn{
            /* 按钮开启绝对定位 */
            position: absolute;
            z-index: 999;
            width: 40px;
            height: 70px;
            /* 将按钮定位到处置居中的位置 */
            top: 0;
            bottom: 0;
            margin:auto;
        }
        /* 将按钮定位到左侧、右侧 */
        .btn-left{
            left:0;
        }
        .btn-left::before{
            content:'<<'
        }
        .btn-right{
            right:0;
        }
        .btn-right::before{
            content:'>>'
        }
        ```
    - 添加点击事件
        ```js
        // 0. 获取所有图片容器的DOM对象
        var items = document.querySelectorAll('.item');
        var itemCount = items.length;
        // 0. 获取按钮的DOM对象
        var prevBtn = document.querySelector('.btn-left');
        var nextBtn = document.querySelector('.btn-right');

        // 0. 保存当前正在显示的图片的索引
        var index = 0;

        // 0. 清除所有图片的显示属性
        function clearActive(){
            for(var n of items){
                n.className = n.className.replace('item-active', '').trim();
            }
        }
        function goIndex(){
            // 4. 清除左右图片容器中的 `item-active` 属性，使所有图片暂不显示
            clearActive();
            // 5. 为 index 对应位置的图片设置 item-active 属性，使其显示
            items[index].className += ' item-active';
        }

        // 1. 为按钮添加点击事件
        prevBtn.addEventListener('click', function(){
            // 2. 向前: 减少 index，通过取模防止越界
            index = (index + itemCount - 1)% itemCount;
            // 3. 显示 index 对应位置的图片
            goIndex();
        })
        nextBtn.addEventListener('click', function(){
            // 2. 向后: 增加 index，通过取模防止越界
            index = (index + itemCount + 1)% itemCount;
            // 3. 显示 index 对应位置的图片
            goIndex();
        })
        ```


## 渐变式---底部索引按钮
[top](#catalog)
- 布局与设置
    1. 使用 `ul` + `li` 和图片数量一样多的按钮
    2. 按钮全部浮动，成水平排列
    3. 按钮的外部容器开启绝对定位，并定位到图片的右下
    4. 为每个按钮绑定索引， 并添加点击事件: 显示索引位置的图片
- 其他调整
    - 在点击按钮时，也需要使用调整激活状态的索引按钮
- 代码内容
    - 按钮布局
        ```html
        <div class="wrap">
            <ul class="pointList">
                <li class="point point-active"></li>
                <li class="point"></li>
                <li class="point"></li>
                <li class="point"></li>
                <li class="point"></li>
            </ul>
        </div>
        ```
        ```css
        /* 1. 开启绝对定位，定位到图片右下 */
        .pointList{
            position: absolute;
            bottom: 10%;
            right: 10%;
            z-index: 999;
        }
        .point{
            /* 2. 按钮浮动，水平排列*/
            float: left;
            height: 15px;
            width: 15px;
            border-radius: 50%;
            background-color: #aaaaaa;
            margin:10px;
            border: 6px white solid;
            transition: background-color 1s;
            /* 设置鼠标移入时的效果 */
            cursor: pointer;
        }
        .point:hover{
            box-shadow: 0 0 16px white;
        }

        .point-active {
            background-color: white;
        }
        ```
    - 点击事件
        ```js
        var items = document.querySelectorAll('.item');
        var itemCount = items.length;
        var prevBtn = document.querySelector('.btn-left');
        var nextBtn = document.querySelector('.btn-right');
        var points = document.querySelectorAll('.point');

        var index = 0;
        function clearActive(){
            // 清除 正在显示的图片
            for(var n of items){
                n.className = n.className.replace('item-active', '').trim();
            }

            // 清除 激活状态的 索引按钮
            for(var n of points){
                n.className = n.className.replace('point-active', '').trim();
            }
        }

        // 4. 切换显示图片，单击向前、向后按钮时，也可以切换激活状态的索引按钮
        function goIndex(){
            // 清除 正在显示的图片 和 激活状态的 索引按钮
            clearActive();
            // 设置显示的图片
            items[index].className += ' item-active';
            // 设置按钮状态
            points[index].className += ' point-active';
        }

        // 1. 为每个索引按钮设置点击事件
        for(let i = 0; i<itemCount; i++){
            points[i].addEventListener('click', function(){
                // 2. 每次点击时，刷新 index 为当前按钮的索引
                index = i;
                // 3. 执行切换
                goIndex();
            })
        }
        ```

[top](#catalog)

