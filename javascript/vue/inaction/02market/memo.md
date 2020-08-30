<span id="catalog"></span>

### 目录
- [项目准备](#项目准备)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)


# 项目准备
[top](#catalog)
- 目录划分
    ```
    - src
        - common            <<< 保存一些公共的js
            - const.js      <<< 常量
            - utils.js      <<< 工具方法等
            - mixin.js      <<< 混入处理
        - assets            <<< 静态资源目录
            - img
            - css
                - normalize.css <<< 去除浏览器的默认 css
                - base.css      <<< 整个项目的基础css
        - components        <<< 保存公共组件
            - common        <<< 当前项目使用的组件，在其他项目中也可以使用的组件
            - content       <<< 与当前项目的业务相关的组件
        - views             <<< 保存大规模的视图
        - router            <<< 路由配置
        - store             <<< Vuex配置
        - network/api       <<< 请求配置
    ```

- `vue.config.js` 中配置路径别名
- 添加 `.editorconfig` 文件，控制书写规范
- 引入 tabbar
- v-for 的引用
    - 双引号里面套单引号
    - :src="'~@assets/img/tabbar/' + itemName + '_active.svg'"

[top](#catalog)