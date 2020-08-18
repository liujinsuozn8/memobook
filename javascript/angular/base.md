<span id="catalog"></span>
- 参考
    - https://www.bilibili.com/video/BV1bt411e71b

### 目录--Angular
- [angular环境搭建](#angular环境搭建)
- [angular指令](#angular指令)
- [目录结构分析](#目录结构分析)
- [创建组件](#创建组件)
- [指令](#指令)
    - [Mustache语法](#Mustache语法)
    - [innerHTML--渲染html](#innerHTML--渲染html)
    - [数据绑定](#数据绑定)
        - [单向属性绑定](#单向属性绑定)
        - [双向数据绑定](#双向数据绑定)
    - [引入图片](#引入图片)
    - [*ngFor--遍历数组](#*ngFor--遍历数组)
    - [*ngIf--条件指令](#*ngIf--条件指令)
    - [ngSwitch](#ngSwitch)
    - [ngClass](#ngClass)
    - [ngStyle](#ngStyle)
    - [管道](#管道)
    - [事件绑定](#事件绑定)
- [](#)

# angular环境搭建
[top](#catalog)
- 安装cli脚手架
    - `npm i -g @angular/cli`
- 创建项目
    - `ng new <项目名称>`
- 启动项目
    - `ng serve`
    - `npm run start`

# angular指令
[top](#catalog)
- `npm i -g @angular/cli`，安装cli脚手架
- `ng new 项目名`，创建组件
    - `--skip-install`，可选参数，跳过包安装
- `ng g component <app下的路径>/<组件名>`，创建组件

# 目录结构分析
[top](#catalog)
- src目录
    ```
    src
     ├─ app     <<<<< 模块目录
     │   ├─ app.component.html  <<<<< 根组件
     │   ├─ app.component.less  <<<<< 根组件
     │   ├─ app.component.ts    <<<<< 根组件
     │   ├─ app.component.spec.ts
     │   └─ app.module.ts
     └─ assets  <<<<< 静态资源目录
    ```
- 根组件由三个文件组成
    - app.component.html
    - app.component.less
    - app.component.ts

- 根模块说明
    - 参考代码
        - [src/base-test/src/app/app.module.ts](src/base-test/src/app/app.module.ts)
    - 代码内容
        ```js
        // 浏览器解析模块
        import { BrowserModule } from '@angular/platform-browser';
        // Angular核心模块
        import { NgModule } from '@angular/core';
        // 根组件
        import { AppComponent } from './app.component';

        // 装饰器接收一个元数据对象，告诉angular如何编译和启动应用
        @NgModule({
        declarations: [ // 配置当前项目的组件
            AppComponent
        ],
        imports: [  // 配置当前模块运行依赖的其他模块
            BrowserModule
        ],
        providers: [],  // 配置项目所需要的服务
        // 指定应用的主视图，即根组件
        // 通过引导根组件来启动应用
        bootstrap: [AppComponent]
        })

        // 根模块不需要导出任何东西，因为其他组件不需要导入模块
        export class AppModule { }
        ```
- 组件说明
    - 参考代码
        - [src/base-test/src/app/app.component.ts](src/base-test/src/app/app.component.ts)
    - 代码内容
        ```js
        // 引入核心模块
        import { Component } from '@angular/core';

        @Component({
        selector: 'app-root', // 当前组件名称
        templateUrl: './app.component.html',  // 需要使用的html
        styleUrls: ['./app.component.less'] // 需要使用的css
        })
        export class AppComponent {
        title = 'base-test';
        }
        ```

# 创建组件
[top](#catalog)
- 创建组件的方法
    1. 命令行切换带项目目录
    2. `ng g component <app下的路径>/<组件名>`，创建组件
        - 如果不指定路径，则默认添加到`app/`下

- angular会自动引入组件
    - 组件创建之后，默认会导入到根模块中 `app/app.module.ts`
    - 同时会注册到项目运行所需的组件中: `@NgModule({declarations}`

- 组件注册后，可以在其他组件的`html` 中引入
    - 通过组件名: `@Component({selector}` 的标签形式来引入
        ```html
        <selector></selector>
        ```

# 指令
## Mustache语法
[top](#catalog)
[top](#catalog)
- 语法
    ```html
    <div>{{变量名}}</div>
    ```
- 功能
    1. 动态（双向）绑定**content部分**数据
    2. 可以同时应用多个Mustache语法来绑定多个数据
        ```html
        <div>{{param1}} - {{param2}}</div>
        ```
    3. 可以绑定表达式
        ```html
        <!-- 拼接内容 -->
        <div>{{param1 + ' ' + param2}}</div>
        <!-- 显示count的2倍 -->
        <div>{{count * 2}}</div>
        ```

## innerHTML--渲染html
[top](#catalog)
- 如果数据是一个 html 标签，直接使用Mustache语法，浏览器无法直接渲染
- 渲染html的语法
    ```html
    <span [innerHTML]='变量'></span>
    ```
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <!-- 属性绑定 -->
        <span [innerHTML]=htmlText></span>
        ```
        ```ts
        public htmlText = "<a href='https://www.baidu.com'>baidu</a>";
        ```

## 数据绑定
### 单向属性绑定
[top](#catalog)
- 语法
    ```html
    <div [属性名]=ts中的变量名></div>
    ```
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <!-- 属性绑定 -->
        <p [class]='changeText'>changeText</p>
        ```
        ```ts
        // 属性绑定测试
        public changeText:string = 'text__deleted--bule'
        
        ngOnInit(): void {
            // 属性绑定测试
            // 设置一个定时器，来切换 changeText 的样式
            setInterval(
            ()=>this.changeText = Math.floor(Math.random()*(1-0+1)) === 1 ? 'text__danger--red':'text__deleted--bule',
            500
            )
        }
        ```

## 双向数据绑定
[top](#catalog)
- 需要在**根组件**中，引入并配置包: `FormsModule`
    ```js
    import { NgModule } from '@angular/core';
    @NgModule({
    imports: [  // 配置当前模块运行依赖的其他模块
        BrowserModule, FormsModule
    ],
    ```
- 双向数据绑定的语法
    ```html
    <input type="text" [(ngModel)]="需要执行双向绑定的变量名">
    ```
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <input type="text" [(ngModel)]="dynamicVal">
        <span>{{dynamicVal}}</span>
        ```

## 引入图片
[top](#catalog)
- 引入本地图片的步骤
    1. 将图片保存到 `src/assets` 目录
    2. 在`<img src=''>` 中直接通过 `assets/图片路径` 来引入
- 引入外部图片
    - 通过绑定属性来引入
        ```html
        <img [src]='保存路径的变量名'>
        ```

## *ngFor--遍历数组
[top](#catalog)
- 语法
    - 遍历数据
        ```html
        <!-- 相当于ES6的 for(let n of list) -->
        <li *ngFor="let item of 变量名">{{item}}</li>
        ```
    - 遍历数据和索引
        ```html
        <!-- 相当于ES6的 for(let n of list) -->
        <li *ngFor="let item of 变量名; let key = index;">{{key}} {{item}}</li>
        ```

- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <!-- 遍历key和数据 -->
        <ul>
            <li *ngFor="let item of arr; let key = index">{{key}}---{{item}}</li>
        </ul>
        <!-- 遍历数据 -->
        <ul>
            <li *ngFor="let item of arr">{{item}}</li>
        </ul>
        ```
        ```ts
        public arr:string[] = ['aaa', 'bbb', 'ccc'];
        ```

## *ngIf--条件指令
[top](#catalog)
- 用于控制标签是否显示
    ```html
    <div *ngIf="变量名"></div>
    ```

## ngSwitch
[top](#catalog)
- 语法
    ```html
    <div [ngSwitch]="变量名">
        <div *ngSwitchCase="匹配值1"></div>
        <div *ngSwitchCase="匹配值2"></div>
        <!-- 其他匹配值 -->

        <!-- 默认值 -->
        <div *ngSwitchDefault></div> 
    </div>
    ```
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <div [ngSwitch]="switchValue">
            <div *ngSwitchCase="1">aaa</div>
            <div *ngSwitchCase="2">bbb</div>
            <div *ngSwitchCase="3">ccc</div>
            <div *ngSwitchCase="4">ddd</div>
            <div *ngSwitchDefault>default</div>
        </div>
        ```
        ```ts
        public switchValue:number = 3;
        ```

## ngClass
[top](#catalog)
- 用于设置样式
    ```html
    <div [ngClass]="{cssClass: expression}"></div>
    ```
- `expression` 需要返回 `true`
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
    - 代码内容
        ```html
        <div [ngClass]="{'text__deleted--bule': flag, 'text__danger--red': !flag}">ngClass</div>
        ```

## ngStyle
[top](#catalog)
- 用于设置内联样式
    ```html
    <div [ngStyle]="{'css属性': 属性值}"></div>
    ```
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <div [ngStyle]="{'height': styleObj.height, 'width':styleObj.height, 'background-color': styleObj.bc}"></div>
        ```
        ```ts
        public styleObj:any = {'height':'100px', 'width':'150px', 'bc': '#bfa'};
        ```

## 管道
[top](#catalog)

## 事件
[top](#catalog)
- 绑定事件语法
    ```html
    <div (事件名)="ts中的方法名()"></div>

    <!-- 触发方法时，将事件对象作为参数传给响应函数 -->
    <div (事件名)="ts中的方法名($event)"></div>
    ```
- 方法名后面必须添加 `()`
- 驼峰命名的事件名，需要换成小写字母
- 示例
    - 参考代码
        - [src/base-test/src/app/components/news/news.component.html](src/base-test/src/app/components/news/news.component.html)
        - [src/base-test/src/app/components/news/news.component.ts](src/base-test/src/app/components/news/news.component.ts)
    - 代码内容
        ```html
        <button (click)="add()">+</button>
        <span>{{count}}</span>
        <button (click)="sub()">-</button>
        ```
        ```ts
        public count:number = 0;

        add():void{
            this.count++;
        }
        sub():void{
            this.count--;
        }
        ```

[top](#catalog)