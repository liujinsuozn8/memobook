<span id="catalog"></span>

- 参考
    - https://bemcss.com/#
    - https://blog.csdn.net/chenmoquan/article/details/17095465

### 目录
- [BEM概述](#BEM概述)
- [BEM的命名规则](#BEM的命名规则)
    - [基本命名规则](#基本命名规则)
- [关系选择器](#关系选择器)
- [组件与其组成部分的关联](#组件与其组成部分的关联)
- [](#)
- [](#)


# BEM概述
[top](#catalog)
- BEM: Block ELement Modifier，块元素编辑器
- 通过 BEM 可以创建可复用的前端组件和前端代码
- 3大要素
    1. Block
        - 代表一个独立的块级元素、独立的功能组件
        - 如头部的block、表单输入框也可以是一个block
    2. Element
        - Block的一部分，不能独立使用
        - 所有的Element都与Block相关联
        - 如一个带有图标的输入框，脱离了输入框，图标就没有意义了
    3. Modifier
        - 用于修饰Block、Element
        - 表示lock、Element在外观或者行为上的改变
        - 如点击输入框后，输入框高亮显示，高亮的效果就应该用Modifier来实现

# BEM的命名规则
## 基本命名规则
[top](#catalog)
- 尽量**只使用类名选择器**，不使用标签或id选择器
- 命名规则
    - 每个部分的描述
        - 使用小写字母、数字 来描述，多个单词使用 `-` 来连接
    - 块名可以直接作为组件名使用
        ```css
        .block{}
        ```
    - 使用两个 `_` 连接 Block 和 Element
        ```css
        .block__element{}
        ```
    - 使用两个 `-` 来连接 Modifier 和 BLock 或 Element
        ```css
        .block--modifier{}
        .block__element--modifier{}
        ```

- 命名时只考虑当前的元素本身，不考虑父元素以及层次结构
    - 错误示例
        ```html
        <div class="page-btn"> 
            <ul class="page-btn__list">
                <li class="page-btn__list__item">
                    <a href="#" class="page-btn__list__item__link">
                        第一页
                    </a>
                </li>
            </ul>
        </div> 
        ```
    - 去掉层级关系，可以改为
        - page-btn__list__item ---> page-btn__li
        - page-btn__list__item__link ---> page-btn__btn
    - 去掉层级关系后，即使发生结构修改，也不会修改名称
        - 不修改名称，也就不涉及js代码的修改

## 关系选择器
[top](#catalog)
- BEM禁止使用关系选择器
- 过长的关系选择器会增加维护、修改的难度
    - 问题示例
        ```css
        .page-btn button:first-child {}
        .page-btn ul li a {}
        ```
    - 修改时，需要同时对照dom和css来修改
    - 在多次修改后，同一个的css可能会同时存在与多个地方，定位的选择器也会不同
    - 连续的关系选择器会有**权重问题**

# 组件与其组成部分的关联
[top](#catalog)
- 说明示例
    ```css
    .person{}
    .person__hand{}
    .person--female{}
    .person--female__hand{}
    .person__hand--left{}
    ```