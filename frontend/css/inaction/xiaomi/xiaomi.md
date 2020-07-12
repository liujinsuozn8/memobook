<span id="catalog"></span>

### 目录
- [基本设置](#基本设置)
- [topbar顶部导航条](#topbar顶部导航条)
- [二维码](#二维码)
- [购物车](#购物车)
- [头部导航条](#头部导航条)
    - [头部导航条--通用设置](#头部导航条--通用设置)
    - [头部导航条--小米图标](#头部导航条--小米图标)
    - [头部导航条--导航列表](#头部导航条--导航列表)
    - [头部导航条--搜索框](#头部导航条--搜索框)
- [](#)
- [](#)

# 基本设置
[top](#catalog)
- 当前页面所需的css与当前页面同名
    - `index.html`
    - `index.css`
- 通用css，保存在 `base.css`中

- 属性的最小值
    - min-width
    - min-xxx
    - 当小于最小值时，属性值不再发送变化，防止页面布局混乱

# topbar顶部导航条
[top](#catalog)
- 顶部导航条分为两部分
    1. 左侧
        - service部分
        - 二维码
    2. 右侧
        - 登录注册
        - 购物车
- 需要的样式
    - `.topbar`，外部容器
        - `.topbar__sub-bar--left`，居左的内容
        - `.topbar__sub-bar--right`，居右的内容 
        - `.topbar__link-btn`，链接按钮
        - `.topbar__line`，分割线

- `topbar` 样式只负责设置基本样式，大小由通用样式 `center-container` 来固定
    ```html
    <div class="topbar">
        <div class="center-container">
            ...
        </div>
    </div>
    ```
- 多个使用`.topbar__sub-bar--right`的元素需要在html中逆序排列，以保持顺序
- 需要将导航条的高度抽取为变量，以便复用
    ```less
    @topbar--height:40px;
    ```
- 通过高度变量设置导航条高度，并且设置文字居中
    ```less
    height: @topbar--height;
    line-height: @topbar--height;
    ```
- 通过浮动分别设置居左和居右的两部分内容
    ```less
    // 居左的内容
    &__sub-bar--left{
        float: left;
    }
    // 居右的内容
    &__sub-bar--right{
        float: right;
    }
    ```
- `.topbar__sub-bar--left`、`.topbar__sub-bar--right`，都需要浮动所以会导致内部的内容脱离文档流
- `.topbar__link-btn` 链接按钮写在 `sub-bar` 内部，需要开启浮动来水平排列

# 二维码
[top](#catalog)
- 需要的样式
    - `.qrcode`
        - `.qrcode__contrainer`，容器部分，保存图片和文字
        - `.qrcode:hover .qrcode__contrainer`，当二维码被选中时，显示容器部分
        - `.qrcode:hover::before`，当二维码被选中时，设置容器上方的三角形
        - `.qrcode__img`，保存二维码图片

- 二维码
    - 二维码部分，图片容器需要使用 `overflow: hidden;`，才能使容器过渡时，跟随容器变化
    - 制作三角形
        - 前提每个方向的 border 单独存在时都是三角形
        - 设置方法
            - 将所有边框设为透明
            - 将底部的 border 设置颜色就可以显示死案教训
    - 居中的方法
        - 方法1:通过margin和定位微调
        - 方法2:使用弹性元素

# 购物车
[top](#catalog)
- 需要的样式
    - `.shopping-cart-btn`
        - `.shopping-cart-btn__text`，文字部分
        - `.shopping-cart-btn__detail`，购物明细部分
- 为什么需要单独创建 `.shopping-cart-btn__text`
    - `detail` 部分有边框阴影，文字部分需要遮盖 top 部分的边框阴影
    - 如果直接使用 `<a>` 标签中的文字，是无法遮盖的
    - 单独创建`text`，使用相对定位，这样不用微调，还在原来的位置
    - `text`使用**比 `detail` 更高的 `z-index`**，来进行遮盖
- `text` 在 `&:hover` 时，必须设置颜色
    - 一般状态下，是透明的，使用`.shopping-cart-btn` 的颜色
    - `&:hover` 时 必须设置颜色，否则虽然发生了遮盖，但是`detail` 的边框阴影仍然会显示

- 为了保证鼠标移动到 `detail` 时，能够保持下拉显示状态，要通过 `&:hover &__detail` 的方式控制下拉

# 头部导航条
## 头部导航条--通用设置
[top](#catalog)
- `heigth:100px`
- 所有元素需要居中显示，为了避免重置外边距折叠，需要使用 `clearfix`

## 头部导航条--小米图标
[top](#catalog)
- 如何将两张图片设置到a标签中
    - 将变换前后的图片分别设置到 `::before` 和 `::after` 中
    - 需要给a标签开启 **相对定位**，并给两给伪类分别开启定位，进行微调
- 动画效果
    1. 通过定位，将 `::before` 移动到a标签的显示范围外边
    2. 将 `<a>` 标签设置为 `overflow:hidden`，隐藏 `::before`
    3. 当鼠标移入时，通过 `transition` 将两个图片同时向右移动

- logo放在了 `<div>` 中，如果logo比较重要，可以放在`<h1>`中

## 头部导航条--导航列表
[top](#catalog)
- 需要的样式
    - `.header-nav`，用于固定宽度，开启定位
        - `.header-nav__list`，固定列表部分居中
        - `.header-nav__item`
        - `.header-nav__link`，设置链接的可选大小，并且使链接居中显示

- 开启浮动
    - `.header-nav` 需要开启浮动，防止 `.header-nav__list` 被挤到下一行
    - `.header-nav__item` 需要开启浮动，使所有列表项可以水平排列

- `.header-nav__link` 扩展 `<a>` 的可选择范围
    - `<a>` 是行内元素，垂直方向的 `padding` 不会生效
    - 需要通过 `display:block` 将 `<a>` 设置为块元素，使垂直方向的属性生效

## 头部导航条--搜索框
[top](#catalog)
- 需要的样式
    - `search`
        - `search__form`
            - `search__input`，搜索框
            - `search__btn`，搜索按钮
            - `search__keyword-list`，搜索关键字提示列表
                - `search__keyword-link`
- 搜索框部分需要靠右显示，所以需要设置 `float:right`
- 输入框
    - `<input>`默认会包含 `padding`，需要手动去除
    - `<input>`默认有轮廓线 `outline`，需要手动去除
    - 无法继承父元素的高度，需要手动设置
- 搜索按钮
    - `<button>`默认会包含 `padding`、`border`，需要手动去除
    - 无法继承父元素的高度，需要手动设置
- 输入框和搜索按钮之间的空白
    - html内容格式
        ```html
        <input>
        <button></button>
        ```
    - 空白是因为 `<input>` 和 `<button>` 之间有换行才产生的，删除空行即可
    - 也可以将 `<input>` 和 `<button>` 同时开启 `float:left`，让两个部分水平排列

- 边框线的颜色变化有两种方式，都需要设置
    1. `search__form` 被鼠标移入时，输入框和按钮需要变色
    2. `search__input` 被点击后，按钮、输入框需要变色，同时弹出关键字提示表

- 关键字列表项的设置方式
    - html代码
        ```html
        <li><a href='javascript:;' class='search_ketword-link'>key1</a></li>
        ```
    - 链接写在了`<li>` 的内部，每个列表项的鼠标移入，变化背景色的属性，只能设置在 `<a>`
        - 如果设置在`<li>`，没有效果
    - 为了保证鼠标移入时的背景色变化效果，能够进行整行的变化，需要将 padding、width 属性都设置在 `<a>` 中
- input 有 默认的padding和border
- button 的box-size是 border-sizing，也有默认的padding和border
- 垂直方向出问题，用行高
    - 浮动之后设置line-height，来调整高度
