<span id="catalog"></span>

### 目录
- [过渡](#过渡)
- [动画效果](#动画效果)
    - [动画的基本设置方法](#动画的基本设置方法)
    - [关键帧的设置](#关键帧的设置)
- [变形平移](#变形平移)
    - [变形的原点](#变形的原点)
    - [变形的基本概念](#变形的基本概念)
    - [平移的基本用法](#平移的基本用法)
    - [z轴平移](#z轴平移)
    - [旋转](#旋转)
    - [变形示例-制作钟表](#变形示例-制作钟表)
    - [3d变形效果](#3d变形效果)
    - [缩放](#缩放)
- [](#)
- [](#)


# 过渡
[top](#catalog)
- 通过过渡可以指定一个属性发生变化时的切换方式
- 通过过渡可以创建一些效果，来提升用户的体验
- 哪些属性支持过渡？
    - 大部分属性都支持过渡
- 过渡的注意事项
    - 过渡必须是从一个有效值向另一个有效值过渡
    - 如果一个值是`auto`，则无法过渡

- 相关属性
    - `transition-property: 属性1, 属性2, ...`: 指定执行过渡的属性
        - 多个属性使用 逗号 分割
        - 如果所有的属性都需要过渡，可以使用：`all` 来指定
    - `transition-duration: 过渡时间`: 指定过渡效果的执行时间
        - 两种时间单位
            - 秒 `s`
            - 毫秒 `ms`
    - `transition-timing-function: 过渡的时序函数`: 指定过渡的执行方式
        - `ease;`，默认值
            - 效果是：慢速开始，先加速，再减速
        - `ease-in`，加速运动
        - `ease-out`，减速运动
        - `ease-in-out`，慢速开始，先加速，再减速，速度比 ease 快
        - `linear`: 匀速运动
        - `cubic-bezier(a, b, c, d)`: 通过贝塞尔曲线手动设置
        - `step(步长, start/end)`: 分步执行，没有明显的过渡效果
            - start/end 表示是在时间段的开始/结束时执行过渡

    - `transition-delay: 延迟时间`，过渡效果的延迟时间

- 简写属性
    - `transition: 过渡属性 过渡时间`
    - `transition: 过渡时间 过渡属性 延迟时间`
    - 同时使用两个时间的时候需要注意顺序，第一个给时过渡时间，第二个是延迟时间，其他属性没有顺序要求
    - 同时设置多个过渡属性
        - 使用`逗号`分割
        - 每个逗号之间设置一个过渡属性及其相关的属性
        - `transition: 过渡属性1 相关参数... , 过渡属性2 相关参数..., ... `

- 产生过渡效果的两个必要属性
    - transition-property
    - transition-duration

- 示例
    - 基本示例
        - 参考代码
            - [src/animation/base.html](src/animation/base.html)
    - 使用雪碧图制作动画效果
        - 参考代码
            - [src/animation/demo01.html](src/animation/demo01.html)
        - 代码内容
            ```css
            .box1{
                /* width: 528px; */
                width: 132px;
                height: 271px;
                margin: 0 auto;
                background-image: url("./transition_test_img.png");
                
                /* 分3次移动，制造动画效果 */
                transition: background-position 0.4s steps(3);
            }

            .box1:hover{
                background-position: -396px 0;
            }
            ```

# 动画效果
## 动画的基本设置方法
[top](#catalog)
- 过渡与动画的异同
    - 相同点
        - 动画与过渡类似，都可以实现一些动态的效果
    - 不同点
        - 过渡需要在某个属性发生变化时才会触发
        - 动画自动触发动态效果
- 关键帧
    - 设置动画效果必须先设置一个关键帧
    - 关键帧设置了动画执行的每一个步骤
    - 语法
        - 定义关键帧，通过百分比来设置不同阶段的变化
            ```css
            @keyframes 关键帧的名字 {
                /* 动画的开始位置，也可以使用0%{}*/
                from{
                    css属性:属性值;

                    /* 也可以设置动画效果的相关属性 */
                    animation-xxxx:xxx; 
                }

                /* 动画的结束位置，也可以使用100%{}*/
                to{}

                /* 同时设置多个阶段 */
                xxx%, yyy%{
                    ...
                }
            }
            ```
        - 为元素添加关键帧
            ```css
            选择器{
                animation-name: 关键帧的名字;
                animation-duration: 关键帧的执行时间;
            }
            ```

- 为元素设置动画时的相关属性
    - `animation-name`，指定关键帧的名字
    - `animation-duration`，指定关键帧的执行时间
    - `animation-delay`，指定关键帧的延迟时间
    - `animation-timing-function`，指定关键帧的执行方式
    - `animation-iteration-count`，指定动画执行次数
        - `animation-iteration-count: infinity;`，连续执行无限次
    - `animation-direction`，指定关键帧的执行方向
        - normal，默认值，从from到to
        - reverse，从to到from
        - alternate，从from到to，重复执行时，会反向执行
        - alternate-reverse，从to到from，重复执行时，会反向执行
    - `animation-play-state`，设置动画的执行状态
        - running，默认值，执行动画
        - paused，暂停
    - `animation-fill-mode`，设置动画的填充模式
        - none，默认值，动画执行结束后，元素回到初始位置
        - forward，动画执行结束后，元素停止在结束位置
        - backwords，动画处于等待时，元素会变为 `from` 中设置的状态
        - both，就是：forward + backwords

- 简写属性
    - `animation: 关键帧的名字 执行时间`
    - `animation: 执行时间 关键帧的名字 延迟时间`
    - 同时使用两个时间的时候需要注意顺序，第一个给时过渡时间，第二个是延迟时间，其他属性没有顺序要求

- 示例
    - 基本示例
        - 参考代码
            - [src/animation/animation/base.html](src/animation/animation/base.html)
    - 使用雪碧图制作动画效果
        - 参考代码
            - [src/animation/animation/demo01.html](src/animation/animation/demo01.html)
        - 代码内容
            ```css
            .box1{
                height: 256px;
                width: 256px;
                margin: 0 auto;
                background-image: url("demo01_img.png");

                /* 设置动画效果 */
                animation: run .5s infinite steps(6) ;
            }

            @keyframes run{
                from{
                    background-position: 0 0;
                }
                to{
                    /* 不做计算，直接使用图片长度，图片会自动平铺 */
                    background-position: -1536px 0;
                }
            }
            ```

## 关键帧的设置
[top](#catalog)
- 各阶段的设置
    - 设置方式，通过 `百分比设置`
    - 设置之后，每个 `百分比` 的阶段使用不同的效果
    - 多百分比可以同时设置，使用`逗号`分割
    - from 表示 100%， to 表示 0%
    - 设置方法
    ```css
    @keyframes 关键帧的名字 {
        /* 动画的开始位置，也可以使用0%{}*/
        from{
            css属性:属性值;

            /* 也可以设置动画效果的相关属性 */
            animation-xxxx:xxx; 
        }

        /* 动画的结束位置，也可以使用100%{}*/
        to{}

        /* 同时设置多个阶段 */
        xxx%, yyy%{
            ...
        }
    }
    ```

- 示例：小球下落
    - 参考代码
        - [src/animation/animation/demo02.html](src/animation/animation/demo02.html)
    - 代码内容
        ```css
        /* 设置底部边框 */
        .outter{
            height: 500px;
            border-bottom: 10px black solid;
            overflow: hidden;
        }

        .box1{
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background-color: #bfa;

            /* 使用 ease-in 模拟下落的加速效果 */
            animation: ball 3s forwards ease-in;
        }

        /* 创建效果，使小球碰到下边框 */
        @keyframes ball{
            from{
                margin-top: 0;
            }

            /* 20% = 第一次落下
               60% = 第二次落下 
               to/100% = 第三次落下 
            */
            20%, 60%, to{
                margin-top: 400px;
                animation-timing-function: ease-in;
            }

            /* 第一次弹起 */
            40%{
                margin-top: 100px;
            }
            
            /* 第二次弹起 */
            80%{
                margin-top: 200px;
            }
        }
        ```

# 变形平移
## 变形的基本概念
[top](#catalog)
- 变形是指通过css来改变元素的形状或位置
- 变形<label style="color:red">不会影响页面布局</label>，可以用来微调元素
- 通过 `transform: 变形函数1() 变形函数2(),...` 来设置变形效果

## 变形的原点
[top](#catalog)
- `transform-origin`，表示变形的原点
- 默认值：`center`
- `transform-origin: x轴, y轴`，可以通过x轴、y轴的位置来设置原点位置

## 平移的基本用法
[top](#catalog)
- 平移方法
    - `transformX(长度)`，沿x轴平移
    - `transformY(长度)`，沿y轴平移
    - `transformZ(长度)`，沿z轴平移
- 如果长度使用百分比，百分比是相对于自身计算的

- 示例
    - 元素漂浮
        - 参考代码
            - [src/animation/transform/elem_float.html](src/animation/transform/elem_float.html)
        - 代码内容
            ```css
            .box1{
                width: 200px;
                height: 200px;
                background-color: #bfa;
                margin: 0 auto;
                margin-top: 200px;
                transition: transform 1s ,box-shadow 1s;
            }
            /* 鼠标移入之后，在y轴向上平移，并添加阴影 */
            .box1:hover{
                transform: translateY(-5px);
                box-shadow: 0 0 10px black;
            }
            ```

## z轴平移
[top](#catalog)
- 如何理解z轴平移？
    - z轴平移是调整元素在z轴的位置
    - 以元素的`transform-origin`为原点**垂直向外**的方向为**z轴的正方向**
    - z轴属于立体效果，默认情况下，网页是不支持透视的，如果要显示效果，**必须设置网页的`视距：perspective`**，即人眼到网页的距离

- x、y、z轴的**移动与旋转**都相当于**直接变换了坐标系**
- 所有的元素都有各自的z轴，需要根据情况确认z轴的位置
    - 如，当x、y轴发生旋转时，需要依据`transform-origin`来重新确认z轴，重新确认坐标系

- `视距` 一般会设置到`html`中，并且不应该设置的太小
    ```css
    html{
        perspective: 800px;
    }
    ```
- 示例
    - 执行z轴平移
        - 参考代码
            - [src/animation/transform/zindex.html](src/animation/transform/zindex.html)
        - 代码内容
            ```css
            /* 开启视距 */
            html{
                perspective: 800px;
            }

            .box1{
                width: 200px;
                height: 200px;
                background-color: #bfa;
                margin: 200px auto;
                transition: transform 2s;
            }
            
            /* 鼠标移入时设置z轴平移 */
            .box1:hover{
                transform: translateZ(100px);
            }
            ```

## 旋转
[top](#catalog)
- 旋转本身还是一种变形
    ```css
    transform: 旋转方法
    ```
- **使用旋转时最好开启`视距：perspective`，否则无法显示透视效果**
- 通过旋转可以是元素沿着x、y、z 轴旋转指定的角度
- 旋转是**围绕当前元素的中心点变换的**
- 旋转方法
    - `rotateX(旋转单位)`，设置x轴旋转
    - `rotateY(旋转单位)`，设置y轴旋转
    - `rotateZ(旋转单位)`，设置z轴旋转
- 旋转单位
    - 角度单位: `deg`，设置旋转多少度
    - 圈数单位: `turn`，设置旋转多少圈

- 180度旋转与元素的**背面**
    - `rotateX(180deg)`、`rotateY(180deg)`，旋转了180度，就相当于在显示元素的背面，即元素的镜像
    - `backface-visibillity: visiable/hidden`，该属性可以用来设置是否显示元素的背面

- 旋转与变形连用时的顺序区别
    - `translate(...) rotate(..);`，先移动，然后再旋转
    - `rotate(..) translate(...);`，先旋转，然后再移动

- 示例
    - 显示元素的背面
        - 参考代码
            - [src/animation/rotate/elem_back.html](src/animation/rotate/elem_back.html)
        - 代码内容
            ```css
            html{
                perspective: 800px;
            }
            .box1{
                width: 200px;
                height: 200px;
                margin: 100px auto;
                background-color: #bfa;
                transition:transform 1s;
                /* 显示背面 */
                backface-visibility: hidden;
            }
            
            .box1:hover{
                transform: rotateY(180deg);
            }
            
            .box2{
                width: 200px;
                height: 200px;
                margin: 100px auto;
                background-color: orange;
                transition:transform 1s;
                /* 显示背面 */
                backface-visibility: visible;
            }
            
            .box2:hover{
                transform: rotateY(180deg);
            }
            ```
    - 旋转与变形连用时的顺序区别
        - 参考代码
            - [src/animation/rotate/with_translate.html](src/animation/rotate/with_translate.html)
        - 代码内容
            ```css
            html{
                perspective: 800px;
            }
            .box1{
                width: 200px;
                height: 200px;
                margin: 100px auto;
                background-color: #bfa;
                transition:transform 1s;
            }
            
            .box1:hover{
                transform: rotateY(180deg) translateZ(400px);
                /* transform: rotateY(180deg); */
            }
            
            .box2{
                width: 200px;
                height: 200px;
                margin: 100px auto;
                background-color: orange;
                transition:transform 1s;
            }
            
            .box2:hover{
                transform: translateZ(400px) rotateY(180deg);
                /* transform: rotateY(180deg); */
            }
            ```

## 变形示例-制作钟表
[top](#catalog)
- 注意事项
    - 旋转是围绕当前元素的中心点变换的，直接使用旋转无法实现表针的效果
- 表针绕中心转动的方式
    - 将表针部分装在一个外部容器中，长度为外部容器的一般
    - 由外部容器进行旋转，看起来就像表针在转动
    - 示例
        ```html
        <div class="wrapper">
            <div class="sec"></div>
        </div>
        ```
        ```css
        /* 创建分针 */
        .min-wrapper{
            height: 200px;
            width: 200px;
            animation: run 60s steps(60) infinite;
        }
        .sec{
            height: 50%;
            width: 4px;
            background-color: black;
            margin: 0 auto;
        }
        ```

- 示例
    - 参考代码
        - [src/animation/rotate/clock.html](src/animation/rotate/clock.html)
    - html内容
        ```html
        <!-- 表 -->
        <div class="clock">
            <div class="hour-wrapper">
                <div class="hour"></div>
            </div>
            <!-- 分针 -->
            <div class="min-wrapper">
                <div class="min"></div>
            </div>
            <!-- 秒针 -->
            <div class="sec-wrapper">
                <div class="sec"></div>
            </div>
        </div>
        ```
    - css内容
        ```css
        /* 创建表 */
        .clock{
            width: 500px;
            height: 500px;
            margin: 0 auto;
            margin-top: 100px;
            border-radius: 50%;
            /* border: 10px solid transparent; */
            position: relative;
            background-color: #bfa;
            background-image: url('clock_img.jpg');
            /* 图片平铺 */
            background-size: cover; 
        }

        /* 使表内部的表针全部水平垂直居中 */
        .clock > div{
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            margin:auto;
        }

        /* 所有的针包含在外部的wrapper容器中，
        长度为容器的一半，通过外部容器旋转来完整表针的旋转 */
        /* 创建时针 */
        .hour-wrapper{
            height: 50%;
            width: 50%;
            /* background-color: #bfa; */
            /* animation: run 3600s linear infinite; */
        }

        .hour{
            height: 50%;
            width: 4px;
            background-color: black;
            margin: 0 auto;
        }

        /* 创建分针 */
        .min-wrapper{
            height: 75%;
            width: 75%;
            /* background-color: #bfa; */
            animation: run 600s steps(60) infinite;
        }

        .min{
            height: 50%;
            width: 3px;
            background-color: #47e;
            margin: 0 auto;
        }

        /* 创建秒针 */
        .sec-wrapper{
            height: 90%;
            width: 90%;
            /* background-color: #bfa; */
            animation: run 10s steps(60) infinite;
        }

        .sec{
            height: 50%;
            width: 2px;
            background-color: red;
            margin: 0 auto;
        }

        @keyframes run{
            from{
                transform: rotateZ(0deg);
            }

            to{
                transform: rotateZ(360deg);
            }
        }
        ```

## 3d变形效果
[top](#catalog)
- 设置3d变形效果：`transform-style:preserve-3d`
- 示例: 3d旋转效果
    - 设置方法
        1. 通过旋转和平移分别设置6各面的图片
        2. 开启父元素的3d变形效果
        3. 平移旋转父元素
    - 参考代码
        - [src/animation/transform/cube.html](src/animation/transform/cube.html)
    - html内容
        ```html
        <div class="cube">
            <div class='box1'><img src="./cube/1.jpg"></div>
            <div class='box2'><img src="./cube/2.jpg"></div>
            <div class='box3'><img src="./cube/3.jpg"></div>
            <div class='box4'><img src="./cube/4.jpg"></div>
            <div class='box5'><img src="./cube/5.jpg"></div>
            <div class='box6'><img src="./cube/6.jpg"></div>
        </div> 
        ```
    - css内容
        ```css
        html{
            perspective: 800px;
        }
        .cube{
            width: 200px;
            height: 200px;
            /* background-color: #bfa; */
            margin:100px auto;
            /* 在父元素中设置3d变换效果 */
            transform-style: preserve-3d;

            /* transform: rotateX(45deg) rotateZ(45deg); */
            animation: run 2s linear infinite;
        }
        
        .cube > div{
            /* 使用绝对定位去除垂直排列 */
            opacity: 0.7;
            position: absolute;
        }

        /* 去除图片之间的空白 */
        img{
            vertical-align: top;
        }

        /* 设置立方体效果 */
        .box1{
            transform: rotateY(90deg) translateZ(100px);
        }
        .box2{
            transform: rotateY(-90deg) translateZ(100px);
        }
        .box3{
            transform: rotateX(90deg) translateZ(-100px);
        }
        .box4{
            transform: rotateX(-90deg) translateZ(-100px);
        }
        .box5{
            /* 转到背面再平移 */
            transform: rotateY(180deg) translateZ(100px);
        }
        .box6{
            transform: translateZ(100px);
        }

        @keyframes run {
            from{
                transform: rotateX(0) rotateZ(0);
            }
            to{
                transform: rotateX(1turn) rotateZ(1turn);
            }
        }
        ```
        
## 缩放
[top](#catalog)
- 用于放大和缩小元素
- 缩放方法
    - `scaleX(倍数)`，x轴缩放
    - `scaleY(倍数)`，y轴缩放
    - `scaleZ(倍数)`，z轴缩放
    - `scale(倍数)`，x轴、y轴同时缩放
- 放大时会从父元素中溢出，可以使用 `overflow: hidden` 来处理