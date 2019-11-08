<span id="catalog"></span>
- [基本使用](#基本使用)
- [常见编程概念](#常见编程概念)
    - [变量和可变性](#变量和可变性)
    - [数据类型](#数据类型)
        - [基本数据类型](#基本数据类型)
        - [其他数据类型](#其他数据类型)
            - [String类型](#string类型)
            - [Slice类型](#slice类型)
    - [语句和表达式](#语句和表达式)
    - [函数](#函数)
    - [控制流](#控制流)
- [所有权](#所有权)
    - [为什么需要所有权](#为什么需要所有权)

# 基本使用
[top](#catalog)
* Rust是一种**预编译静态类型**语言
* 安装Rust：
    * 检测安装成功
        * 检测Rust：`rustc --version`
        * 检测Cargo：`cargo --version`
* Rust的代码以`.rs`为文件后缀
* Rust的代码包称为**crates**
* 基本的编译与运行
    * 编译指令：`rustc xxx.rs`
    * 编译后生成：`xxxx.exe/xxxx`可执行文件，使用CMD时会有一个包含调试信息的文件`xxxx.pdb`

* 使用Cargo
    * 创建项目
        * 创建指令:`cargo new 项目名`
        * 生成的结果
            ```
            项目名
            |--- .gitignore
            |--- Cargo.toml
            |--- src
                |--- main.rs
            ```
        * Cargo期望用户**将代码放在src下**，根目录下放**README、license 信息、配置文件和其他跟代码无关的文件**
        * Cargo.toml文件的内容
            ```xml
            [package]
            name = "xxxx"
            version = "0.1.0"
            authors = ["Your Name <you@example.com>"]
            edition = "Rust版本"

            [dependencies]
            //在下面添加工程依赖
            ```
    * 编译、运行项目
        * 编译指令:`cargo build`
            * 在工程目录下执行该指令
            * 构建之后，会在工程目录下创建一个可执行文件：`target/debug/工程名`
            * 第一次运行该指令，将会在项目的根目录下创建一个新文件`Cargo.lock`
                * 该文件记录了依赖的实际版本，不需要做修改
        * 编译并运行:`cargo run`
            * 如果Cargo发现代码没有修改将不会重新构建项目，而是直接运行旧的二进制文件
        * 快速检测代码:`cargo check`
            * 该指令会快速检查代码确保它可以编译
            * 执行该指令**不会产生可执行文件**
            * `cargo check`比`cargo build`快，因为他忽略了生成可执行文件的步骤
    * 发布项目(release)
        * `cargo build --release`
            * 该指令视为了构建最终程序，编译速度比较慢
* Rust和Cargo的指令在各系统中都相同

* 编译与运行相互独立
* 每一行以`;`结尾
* 调用方法/宏
    * `method!(...);`，如果方法名后面有`!`，表示调用宏
    * `method(...);`，表示调用方法

# 常见编程概念
## 变量和可变性
[top](#catalog)
* 不可变变量
    * 创建常量：`let x = 5`
    * 常量只能赋值一次，否则会产生编译错误
    * 可以通过**隐藏特性**来创建新的同名的新变量
* 可变变量
    * `let mut x = 5;`
    * 值可以改，类型不可以改
* 常量
    * `const xxx: 类型 = xxxx;`
* 变量后可以增加类型声明：`let x: i8 = 5`
* 隐藏 Shadowing
    * 通过重复使用`let`来隐藏之前设定的变量值
    * 可以进行多次隐藏
        ```rust
        fn main() {
            let x = 5;
            println!("the value of x is:{}", x);
            let x = x+1;
            println!("the value of x is:{}", x);
            let x = x*2;
            println!("the value of x is:{}", x);
        }
        ```
    * 隐藏时，实际上是**创建了一个新变量，可以改变变量的类型，并复用这个变量名**

* **常量命名规范：使用下划线分隔大写字母单词**
* **可以在数字字面值中插入下划线来提升可读性**

## 数据类型
### 基本数据类型
[top](#catalog)
* Rust中的每一个值都属于某一个数据类型
* **基本数据类型都是存储在栈上的，并且当其离开作用域时被移除栈**(元祖？？？)
* 转换数据类型时必须设定数据类型注解
    * `let x: u32 = "42".parse().expect("Not a number!");`
* 标量类型
    * 标量类型代表一个单独的值
    * 四种基本的标量类型
        1. 整型
            * 可用类型

                |长度|有符号|无符号|
                |-|-|-|
                |8-bit|i8|u8|
                |16-bit|i16|u16|
                |32-bit|i32|u32|
                |64-bit|i64|u64|
                |arch|isize|usize|
            * `isize` 和 `usize`依赖于运行程序的计算机架构
                * 64位架构上是64位
                * 32位架构上是32位
            * 默认的整型类型是i32，通常是最快的？？？
            * 在64位系统上，`isize`、`usize`主要作为某些集合的索引
            * byte以外的所有数字字面值，**允许使用类型后缀**，如u8类型的数字：`57u8`
            * 整型字面值示例

                |数字字面值|例子|
                |-|-|
                |Decimal|98_222|
                |Hex|0xff|
                |Octal|0o77|
                |Binary|0b1111_0000|
                |Byte(u8 only)|b'A'|

            * 整型溢出
                * 在debug模式编译时，Rust会检查这类问题并使程序panic
                * 在release构建中，Rust不检测溢出，会转换成二进制补码
        2. 浮点型
            * 表示带小数点的数字
            * 两种浮点类型：`f32`、`f64`
            * 默认类型是:`f64`
                * 该类型在现代CPU中与`f32`速度机会一样，不过精度更高
            ```rust
            fn main() {
                let x = 2.0; // 默认为f64
                let y: f32 = 3.0; // f32
            }
            ```
        3. 布尔型
            * `bool`表示布尔型变量
            * 两个可能的值：`true`、`false`
            ```rust
            fn main() {
                let t = true;
                let f: bool = false;
            }
            ```
        4. 字符类型
            * `char`表示字符型
            * 字符类型表示**语言中最原生的字母类型**
            * 大小为4byte
            * 代表了一个Unicode标量值
            * Rust中，中文、日文、韩文、emoji字符、0长度的空字符 等，都是有效char值
            * 表示的范围：`U+0000~U+D7FF` 和 `U+E000`
* 复合类型
    * 将多个值组合成一个类型
    * Rust的两个原生复合类型
        1. 元组tuple
            * `(,)`创建元祖
            * 每个位置都有一个类型，这些**类型可以不相同，可以使用类型注解**
                ```rust
                let tup: (i32, f64, u8) = (500, 6.4, 1);
                ```
            * 元祖的解构赋值(模式匹配)
                ```rust
                let tup = (500, 6.4, 1);
                let (x, y, z) = tup;
                ```
            * 直接访问方式:`元祖名.index`
                ```rust
                let x:(i32, f64, u8) = (500, 6.4, 1);
                let y = x.0;
                ```
        2. 数组array
            * `[,]`创建数组
            * **数组每个元素的类型必须相同**
            * Rust中的数组是**固定长度**的，长度不能变化: `let a = [1,2,3,4,5];`
            * 数组的类型：`[type; number]`
                `let a [i32; 5] = [1, 2, 3, 4, 5];`
            * **当想要在栈上为数据分配空间、或者想要确保总是有固定数量的元素时，应该使用数组**
            * 如果不确定数组大小应该使用vector
            * 数组是**分配在栈上的连续内存**，可以使用索引来访问数组元素，访问方式：`数组名[index]`
                ```rust
                let a = [1, 2, 3, 4, 5];
                let first = a[0];
                ```
            * 越界访问，编译时不会产生异常，执行时会产生一个panic
                * 通过index访问数组时，**会检查索引是否小于数组的长度，超过了会长生panic，不会指向无效内存**

### 其他数据类型
#### String类型
[top](#catalog)
* 该类型被分配到堆上，能够存储在编译时未知大小的文本
* 使用`from()`函数基于字符串字面量来创建String：`let s = String::from("test");`
    * `::`允许将`from`置于`String`类型的命名空间 TODO
* 向**可变**字符串变量的末尾添加新字符串
    ```rust
    let mut s = String::from("this");
    s.push_str(" is test");
    println!("{}", s); // this is test
    ```
* 字符串字面量不可变，如`let s = "test";`；**可变的字符串变量**可变
    * 字符串字面量的内存分配
        * 字符串字面量是**编译时就知道的内容**,所以字面量会**被直接硬编码到最终的可执行文件中**，这使得字符串字面量快速、高效
    * 可变字符串变量
        * 为了支持一个可变、可增长的文本片段，需要**分配一块在编译时未知大小的内存来存放内容**，包含两点
            1. 必须在运行时向操作系统请求内存
                * 由`String::from`的实现来申请内存
            2. 需要有一个方法，在String使用完之后，将内存释放会操作系统
                * 一般GC系统的策略：自动记录并清除不再使用的内存
                * 没有GC系统的编程策略：精确的为每一个`allocate`配对一个`free`
                    * free早了，会浪费内存
                    * free晚了，会出现无效变量
                    * 如果重复回收，会产生异常
                * Rust的策略
                    * **在变量离开作用域后自动释放**
                        ```rust
                        {
                            let s = String::from("hello");  // 从此处起，s 是有效的

                            // 使用 s
                        }  // 此作用域已结束，
                        // s 不再有效(内存已被释放)
                        ```
                    * 当s离开作用域时，即末尾的`}`处，Rust会自动调用特殊函数`drop`
* String的底层
    * 基本结构，这部分存储在栈上
    
    |name|value|
    |-|-|
    |ptr|指针，指向存储字符串内容的数据|
    |len|长度，String当前使用了多少字节的内存|
    |capacity|容量，String从操作系统获取了多少字节的内存|

    * 存储字符串内容的数据，这部分存储在堆上

    |index|val|
    |-|-|
    |0|t|
    |1|e|
    |2|s|
    |3|t|

#### Slice类型
[top](#catalog)
* slice类型没有所有权
* slice运行引用集合中一段连续的元素序列，而不用引用这个集合
* 通过slice来关联原始数据和索引
* 语法1：`&变量名[start..end]`，表示从start开始，但不包含end的range
* 语法2：`&变量名[start..=end]`，表示从start开始，到end的range
* 在slice的数据结构中存储了：slice的开始位置和长度
* 字符串slice
    * 字符串slice是String中一部分值的引用
        ```rust
        let s = String::from("hello world");
        let hello = &s[0..5]; //对String的部分引用
        ```
    ```rust
    fn first_word(s: &String) -> usize {
        let bytes = s.as_bytes();
        for (i,&item) in bytes.iter().enumerate(){
            if item == b' ' {
                return i;
            }
        }

        return s.len();
    }
    ```

## 语句和表达式
[top](#catalog)
* 语句
    * 执行一些操作但不返回值的指令
    * 如：`let y = 6;`
    * 语句不能赋给另一个变量
    * 已`;`结尾
* 表达式
    * 能够计算并产生一个值
    * **表达式的结尾没有`;`**，加上`;`就变成了语句
    * 表达式可以是语句中的一部分
    * `let y = 6;`中`6`是一个表达式
    * 函数调用是一个表达式
    * 宏调用是一个表达式
    * 代码块`{...}`是一个表达式
        * 如
            ```rust
            let y = {
                let x = 3;
                x + 1 //表达式，返回x+1的结果
            }; //结尾必须有分号
            ```
        * 代码块的值是其最后一个表达式的值
## 函数
[top](#catalog)
* 声明函数的形式
    * 一个返回值:`fn 函数名(参数1: 类型, 参数2: 类型,....) -> 返回值类型`
    * 多个返回值，使用元祖：`fn 函数名(参数1: 类型, 参数2: 类型,....) -> (返回值类型1, 返回值类型2,....)`

* 函数的返回值等同于**函数体的最后一个表达式的值**
* 使用`return`指定返回值
* 大部分函数**隐式的返回最后的表达式**
    ```rust
    fn five() -> i32{
        5 //没有';'，是表达式，且是最后一个表达式，会作为返回值返回
    }

    fn main(){
        let x = five();
        println!("{}", x)
    }
    ```
* 控制台的错误信息中会使用空元祖`()`来表示不返回值
* `fn main(){}`，在可运行的rust程序中，`main`总是最先运行的代码，并且**没有参数，也没有返回值**
* 函数和变量使用snake case风格，所有字母都是小写并使用下划线分隔单词
* 函数的参数**是函数签名的一部分**

## 控制流
[top](#catalog)
* if表达式
    * 基本格式
        ```rust
        if exp {
            ...
        } else if exp{
            ...
        } else {
            ...
        }
        ```
    * **每个分支的判断exp的结果必须是`bool`类型**
    * if 是有一个表达式，可以在let语句的右侧使用它
        ```rust
        let condition = true;
        let number = if condition {
            5
        } else {
            6
        }; //必须以';'结尾

        println!("{}", number);
        ```
        * 每个分支的数据类型必须相同，否则代码无法编译
            ```rust
            let number = if condition {
                5
            } else {
                "xxxx" //将会产生编译异常
            };
            ```
* 循环
    * loop 循环
        * **loop是一个表达式**
        * 可以使用break来停止循环
            * break可以添加返回值：`break 返回值;`
        ```rust
        let mut count = 0;
        let result = loop {
            count += 1;
            if count == 10{
                break count + 2;
            }
        };

        println!("{}", result)
        ```
    * while循环
        * while条件为真时继续循环
            ```rust
            let mut x = 10;
            while x > 0 {
                println!("{}", x);
                x = x-1;
            }
            println!("end");
            ```
        * 使用这种方式，每次系统都会检查index是否越界，使程序变慢
    * for 循环
        * 使用for遍历集合
            ```rust
            let a = [1,2,3,4,5];
            for e in a.iter() {
                println!("{}", e);
            }
            println!("end");
            ```
        * 使用for变量集合可以防止越界
        * 遍历range类型
            ```rust
            // 只输出1 2 3 end
            for num in (1..4) {
                println!("{}", num);
            }
            println!("end");
            ```


# 所有权
## 为什么需要所有权
[top](#catalog)
* 所有权让 Rust 无需垃圾回收即可保障内存安全，不需要经常考虑栈和堆的问题
* 堆和栈
    * 堆和栈都是运行时可供使用的内存，但是结构不同
    * 栈
        * 栈的操作非常快，因为数据存储的位置总是在栈顶，不需要寻找数据存储的位置
        * 另一个提升栈速度的属性是：**栈中的所有数据都必须占用已知且固定的大小**
    * 堆
        * 在**编译时**大小未知或大小可能变化的数据，应该存在堆上
        * 堆是缺乏组织的
        * **在堆上分配内存(简称分配)**:操作系统在堆的某处找到一块足够大的空位，并标记为**已使用**，并**返回一个表示该地址的指针**
            * 简单来说：向堆中放入数据时，需要**请求一定大小的空间**
        * 访问栈的数据比堆慢，**因为必须通过指针来访问**
            * **现代处理器在内存中跳转越少就越快**
    * 数据保存到栈中不是分配，因为数据的大小是已知且固定的。数据的指针可以存储在栈上，但是需要数据时必须访问数据

* 所有权系统需要处理的问题：
    * 跟踪哪些代码正在使用堆上的哪些数据
    * 最大限度减少堆上**重复数据**的数量
    * 清理堆上不再使用的数据，确保不会耗尽空间

## 所有权规则
[top](#catalog)
* 规则
    * Rust中的每一个值都有一个被称为其所有者(owner)的变量
    * 值有且只有一个所有者
    * 当所有者(变量)离开作用域，这个值将被丢弃

* 变量作用域
    * 示例
        * 从s进入作用域到s离开为止，它是有效的，
        ```rust
        {                      // s 在这里无效, 它尚未声明
            let s = "hello";   // 从此处起，s 是有效的
            // 使用 s
        }                      // 此作用域已结束，s 不再有效
        ```

* 变量内存的释放
    * 一般GC系统的策略：自动记录并清除不再使用的内存
    * 没有GC系统的编程策略：精确的为每一个`allocate`配对一个`free`
        * free早了，会浪费内存
        * free晚了，会出现无效变量
        * 如果重复回收，会产生异常
    * Rust的策略
        * **在变量离开作用域后自动释放**
            ```rust
            {
                let s = String::from("hello");  // 从此处起，s 是有效的

                // 使用 s
            }  // 此作用域已结束，
            // s 不再有效(内存已被释放)
            ```
        * 当s离开作用域时，即末尾的`}`处，Rust会自动调用特殊函数`drop`

* 变量与数据的交互方式--移动
    * 对于基本数据类型的整型变量
        ```rust
        let x = 5; //将整形变量5绑定到x
        let y = x; //生成一个x的拷贝，并绑定到y上
        //最终两个5被放入栈中
        ```
    * 对于String
        ```rust
        {
            let s1 = String::from("test");
            let s2 = s1;
            //println!("{}", s1); //会产生编译异常
            println!("{}", s2);
        }
        ```
        * s1复制给s2时，没有执行复制，而是将栈的内容移动到s2名下
        * **移动(move)，而不是拷贝**:在这种使用场景下，Rust将会认为**s1不再有效**，所以`let s2 = s1;`后，无法打印s1，因为s1被当作无效变量
        * 在s2离开作用域时，会分别释放：基本结构、堆中的字符串内容数据
            * 堆中的字符串内容数据 只会释放一次，因为s1已经无效了，防止了二次free的bug

* 变量与数据的交互方式--克隆
    * 使用`clone()`来执行数据的深拷贝
        ```rust
        let s1 = String::from("test");
        let s2 = s1.clone();
        println!("{},{}", s1, s2);
        ```
    * 对于基本数据类型，可以通过直接赋值来完成克隆，而不会造成移动
        * 因为基本数据类型的长度是已知的，并且在栈上做拷贝非常快
            ```rust
            let x = 5; //将整形变量5绑定到x
            let y = x; //直接拷贝，不会产生变量的移动
            //最终两个5被放入栈中
            ```
* Copy特性
    * Copy可以用在存储在栈上的类型上
    * 如果一个类型拥有Copy，则在将其赋值给另一个变量后仍然可以使用
    * Rust不允许自身或部分拥有Drop的类型使用Copy，如果使用了会产生编译异常
        * 拥有Drop特性的如果有Copy，对于String这种类型，会产生二次free
    * Copy的一个通用规则
        * 任何简单标量值的组合可以是Copy
            * 对于元祖，当且仅当其包含的类型都是Copy时是Copy的，如`(i8,i32)`，但是`(i8,String)`不是
        * 不需要分配内存或某种形式资源的类型是Copy的

* 所有权与函数
    ```rust
    fn main() {
        let s = String::from("hello");  // s 进入作用域

        takes_ownership(s);             // s 的值移动到函数里 ...
                                        // ... 所以到这里不再有效

        let x = 5;                      // x 进入作用域

        makes_copy(x);                  // x 应该移动函数里，
                                        // 但 i32 是 Copy 的，所以在后面可继续使用 x

    } // 这里, x 先移出了作用域，然后是 s。但因为 s 的值已被移走，
    // 所以不会有特殊操作

    fn takes_ownership(some_string: String) { // some_string 进入作用域
        println!("{}", some_string);
    } // 这里，some_string 移出作用域并调用 `drop` 方法。占用的内存被释放

    fn makes_copy(some_integer: i32) { // some_integer 进入作用域
        println!("{}", some_integer);
    } // 这里，some_integer 移出作用域。不会有特殊操作
    ```

* 返回值与作用域(所有权的变化)
    ```rust
    fn main() {
        let s1 = gives_ownership();         // gives_ownership 将返回值
                                            // 移给 s1

        let s2 = String::from("hello");     // s2 进入作用域
                                            // s2 不能再使用

        let s3 = takes_and_gives_back(s2);  // s2 被移动到
                                            // takes_and_gives_back 中, 
                                            // 它也将返回值移给 s3
    } // 这里, s3 移出作用域并被丢弃。s2 也移出作用域，但已被移走，
    // 所以什么也不会发生。s1 移出作用域并被丢弃

    fn gives_ownership() -> String {             // gives_ownership 将返回值移动给
                                                // 调用它的函数

        let some_string = String::from("hello"); // some_string 进入作用域.

        some_string                              // 返回 some_string 并移出给调用的函数
    }

    // takes_and_gives_back 将传入字符串并返回该值
    fn takes_and_gives_back(a_string: String) -> String { // a_string 进入作用域

        a_string  // 返回 a_string 并移出给调用的函数
    }
    ```

## 引用与借用
* 引用没有所有权
* (不可变)引用
    * 使用`&变量名`表示该变量的引用
    * 通过引用可以使用变量的值，但是不获取其所有权
        ```rust
        fn main() {
            let s1 = String::from("hello");

            let len = calculate_length(&s1);//创建s1的引用

            println!("The length of '{}' is {}.", s1, len);
        }

        fn calculate_length(s: &String) -> usize {
            s.len()
        }//因为s只是一个引用，没有变量的所有权，所以离开作用域时什么也不会发生
        //离开后，将引用的变量归还

        ```
    * 类似于指向变量的指针，但只是指向，不能修改指向的值，**因为没有所有权
* 借用
    * 将引用作为函数参数称为**借用**
    * 无法通过借用来修改原始变量，这会导致编译异常---引用不可变

* 可变引用
    * 使用`&mut 变量名` 来表示可变引用
        ```rust
        fn main() {
            let mut s = String::from("hello");

            change(&mut s);
            println!("{}", s)
        }

        fn change(some_string: &mut String) {
            some_string.push_str(", world");
        }
        ```
    * 可变引用的限制
        * 特定作用域中的特定数据**有且只有一个可变引用**
        * 通过限制来防止数据竞争(主要防止以下三种行为)
            * 两个或更多指针同时访问同一数据
            * 没有同步数据访问的机制
            * 至少有一个指针被用来写入数据
        * 可以通过多个作用域来拥有多个可变引用
            ```rust
            let mut s = String::from("hello");

            {
                let r1 = &mut s;

            } // r1 在这里离开了作用域，所以我们完全可以创建一个新的引用

            let r2 = &mut s;
            ```

* 可变和不可变引用
    * 在一个作用域中：**要么只有一个可变引用，要么只能有多个不可变引用**
    * 引用的作用域从声明的地方开始一直持续到**最后一次使用为止**，在最后一次使用之后可以继续声明可变引用
        ```rust
        let mut s = String::from("hello");

        let r1 = &s; // 没问题
        let r2 = &s; // 没问题
        println!("{} and {}", r1, r2);
        // 此位置之后 r1 和 r2 不再使用

        let r3 = &mut s; // 没问题
        println!("{}", r3);
        ```
* 悬垂引用
    * 编译器会防止悬垂指针
        ```rust
        fn dangle() -> &String { // dangle 返回一个字符串的引用

            let s = String::from("hello"); // s 是一个新字符串

            &s // 返回字符串 s 的引用
        } // 这里 s 离开作用域并被丢弃。其内存被释放。会产生编译异常
        ```

# 标准库提供的类型
## Range