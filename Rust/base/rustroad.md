<span id="catalog"></span>
- [语言精要](#语言精要)
    - [核心库和标准库](#核心库和标准库)
    - [表达式](#表达式)
    - [变量](#变量)
    - [闭包](#闭包)
    - [流程控制](#流程控制)
    - [类型](#类型)
    - [常用集合类型](#常用集合类型)
    - [智能指针](#智能指针)
    - [泛型和trait](#泛型和trait)
    - [注释和打印](#注释和打印)
- [类型系统](#类型系统)
    - [通用概念](#通用概念)
    - [Rust类型系统](#rust类型系统)

# 语言精要
# 核心库和标准库
[top](#catalog)
- 核心库
    - 通过在模块顶部引用#![no_std]来使用核心库
    - 核心库和标准库的功能有一些重复，包括如下部分：
        - 基础的trait，如`Copy、Debug、Display、Option`等
        - 基本原始类型，如`bool、char、i8/u8、i16/u16、i32/u32、i64/u64、isize/usize、f32/f64、str、array、slice、tuple、pointer`等
        - 常用功能型数据类型，满足常见的功能性需求，如`String、Vec、HashMap、Rc、Arc、Box`等
        - 常用的宏定义，如`println！、assert！、panic！、vec！`等
    - 做嵌入式应用开发的时候，核心库是必需的
- 标准库
    - 提供应用程序开发所需要的基础和跨平台支持，包括
        - 与核心库一样的基本trait、原始数据类型、功能型数据类型和常用宏等，以及与核心库几乎完全一致的API。
        - 并发、I/O和运行时
            - 例如线程模块、用于消息传递的通道类型、Sync trait等并发模块，文件、TCP、UDP、管道、套接字等常见I/O。
        - 平台抽象
            - os模块提供了许多与操作环境交互的基本功能，包括程序参数、环境变量和目录导航；路径模块封装了处理文件路径的平台特定规则。
        - 底层操作接口
            - 比如 std：：mem、std：：ptr、std：：intrinsics 等，操作内存、指针、调用编译器固有函数。
        - 可选和错误处理类型Option和Result，以及各种迭代器等。

## 表达式
[top](#catalog)
- 块表达式
    - 由一对花括号和一系列表达式组成
    - 总是返回最后一个表达式的值
- 位置表达式和值表达式
    - 位置表达式
        - 左值
        - 表示内存位置的表达式
        - 主要进行写操作
        - 代表了持久性数据
        - 包括
            - 本地变量
            - 静态变量
            - 解引用`(*expr)`
            - 数组索引`(expr[expr])`
            - 字段引用`(expr.field)`
            - 位置表达式组合
    - 值表达式
        - 右值
        - 一般指引用了某个存储单元地址中的数据，相当于**数据值**，只能进行读操作
        - 代表了临时数据
- 表达式优先级
    - **Rust中一切皆表达式**

## 变量   
[top](#catalog)    
- 每个变量绑定实际上都**拥有该存储单元的所有权**， 转移内存地址的行为就是所有权的转移
    - 在Rust中成为Move
    - 不转移的成为Copy
- `println!("{:p}", b);`
- 要获取可变引用，必须先声明可变绑定
- `CTFE机制`，编译时函数执行：
    - 使用`const fn`定义函数
        - 与`fn`的区别
            - `const fn`可以强制编译器**在编译期执行函数**

    - 必须可以确定值，不能存在歧义
    - 示例：
        ```rust
        fn main(){
            let arr = [1, init_len()];
            println!("{:?}", arr);
        }

        const fn init_len() ->i32{
            return 5;
        }
        ```

## 闭包
[top](#catalog)
- 闭包实际上是有一个匿名结构体和trait来组合实现的
- 闭包作为参数
    ```rust
    fn closure_math<F: Fn() -> i32>(op: T) -> i32 {
        op()
    }

    fn main(){
        let a = 2;
        let b = 3;
        assert_eq!(math(||a+b), 5)
        assert_eq!(math(||a×b), 6)
    }
    ```
- 闭包默认会使用**引用**来捕获变量
    - 可以使用`move`将变量的所有权移动到闭包中
- 闭包作为返回值
    - 为什么使用`move`
    - 在以闭包作为返回值时，如果使用默认的变量捕获方式，那么返回闭包时，引用也会跟着返回
    - 但是在函数`two_times_impl`调用完毕之后，遍历`i`就被销毁了，会导致返回的变量`i`的引用变成**悬垂引用**
    - 为了防止异常必须使用`move`,否则会引发编译异常
    - 通过`move`将变量`i`的所有权移动到闭包中
    ```rust
    fn two_times_impl() -> impl Fn(i32) -> i32{
        let i = 2;
        move |j| j * i
    }

    fn main(){
        let result = two_times_impl();
        assert_eq!(result(2), 4);
    }
    ```
## 流程控制
[top](#catalog)
- Rust中不叫流程控制语句，叫做`流程控制表达式`
- if
    - `if`表达式的**分支必须返回同一个类型的值**
    - Rust没有三元运算符`? :`
    - 示例
        - 编译器默认推断if返回值的类型为`i32`
            - 因为类型已被确定，所以`n/2`的结果会被截取
        ```rust
        fn main() {
            let n = 13;
            let big_n = if (n < 10 && n > -10){
                10 * n
            } else {
                n / 2
            };

            assert_eq!(big_n, 6);
        }
        ```
- 循环
    - 三种方式
        - `while`
        - `loop`
        - `for...in`
    - `for...in`
        - 这种方式本身是一个迭代器
    - 无限循环应该使用`loop`,不应该使用`while true`
        - 示例：`while true`无限循环
            - 编译器在对while循环做流分析的时候，不会检查循环条件
                - 编译器认为while的循环条件可真可假，所以循环体里的表达式也会被忽略
                - 编译器只知道`while true`循环返回的是单元值(忽略了`return`)，而函数返回的是i32
            - **`if true`在只有一条分支时，也会发生类似的问题**
            ```rust
            fn while_true(x: i32) -> i32{
                while true{
                    return x + 1
                }

                // x //通过增加这一行，使得编译器认为能够返回i32类型，这样执行时就可以使用无限循环了
            }

            fn main(){
                let y = while_true(5);
                assert_eq!(y, 6);
            }
            ```
- `match`
    - **分支左边是模式，右边是执行代码**
    - 所有分支的返回值类型必须相同
    - `绑定模式`：可以使用操作符`@`将模式中的值绑定到一个变量，供右侧的执行代码使用 
        ```rust
        fn main(){
            let number = 42;
            match number {
                0 => println!("origin"),
                1...3 => println!("all"),
                |5|7|13 => println!("bad luck"),
                n @ 42 => println!("answer is {}", n),
                _ => println!("Common"),
            }
        }
        ```
- `if let`
    - 相当于`macth`的一个分支，**左边是模式，右边是匹配的值**
    ```rust
    fn main(){
        let boolean = true;
        let mut binary = 0;
        if let true = boolean {
            binary = 1;
        }

        assert_eq!(binary, 1);
    }
    ```
- `while let`
    - 如果只使用match
        ```rust
        fn main(){
            let mut v = vec![1,2,3,4,5];
            loop {
                match v.pop(){
                    Some(x) => println!("{}", x),
                    None => break, //如果没有值了，则跳出循环
                }
            }
        }
        ```
    - 使用`while let`来简化
        - `while let Some(x) = v.pop()`，`pop()`取出的值与`Some(x)`匹配，如果返回的是`None`则跳出循环
        ```rust
        fn main(){
            let mut v = vec![1, 2, 3, 4, 5];
            while let Some(x) = v.pop() {
                println!("{}", x);
            }
        }
        ```
## 类型
[top](#catalog)
- 单元类型`()`
    - 单元类型唯一拥有的值，就是它本身
- 基本数据类型
    - bool
        - 任何比较操作都会产生bool类型
        - 可以通过`as`将bool类型转换为数字0和1
            - Rust**不支持将数字转换为bool**
        ```rust
        fn main(){
            let x = true;
            let y: bool = false;
            if x > 1 {println!("x is bigger than 1")};
            assert_eq!(x as i32, 1);
            assert_eq!(y as i32, 0);
        }
        ```
    - 基本数字类型
        - 固定大小类型
            - 无符号整数
                - u8,u16,u32,u64,u128
            - 符号整数
                - i8,i16,i32,i64,i128
        - 动态大小类型：取决于机器的字长
            - usize
            - iszie
        - 浮点型
            - f32
                - 单精度32位浮点数，至少6位有效数字
                - 范围：-3.4 × 10^38 ~ 3.4 * 10^38
            - f64
                - 单精度64位浮点数，至少15位有效数字
                - 范围：-1.8 × 10^308 ~ 1.8 * 10^308
        - 可以使用`类型后缀`:`let a = 42u32;`
        - 如果没有加类型后缀，或没有指定类型，Rust编译器会默认推断数字为`i32`类型
        - 可以用前缀`0x,0o,0b`表示16进制、8进制、2进制
        - 使用字节字面量
            - `b'*'`，等价与`42u8`
    - 字符类型
        - 字符类型代表的是一个`Unicode标量值`,每个字符占**4个字节**
        - 字符可以使用`ASCII码`, `Unicode`码来定义
            - `ASCII码`的格式：`\xHH`,`HH`是16进制数
                - 如：`'\x2A'`为ASCII码表中表示符号`*`的16进制数
            - `Unicode`的格式：`'\u{HHH}'`，`HHH`是16进制数
                - 如：`\u{151}`
            - 使用多个`Unicode`值来定义字符
        - 可以使用`as`将字符转为数字类型
            - `assert_eq!('%' as i8, 37);`, `%`的十进制ASCII值是37
            - 如果字符值过大，数值类型过小，将会产生`高位截断`
    - 数组类型
        - 数组的类型签名`[T; N]`
            - `T`是一个泛型
            - `N`是数组长度，是一个`编译时常量`，必须在编译时确定其值
        - 数组长度是固定的，不允许添加或删除元素
        - 可以通过`[0; Count]`的方式来创建初始值为`0`且长度为`10`的数组
        - 目前，只有实现了`Copy trait`的类型才能作为数组元素
            - 即只有可以存在栈上的元素才可以存在该类型的数组中
        - 将来会有`VLA 可变长度数组`，来在数组中保存可以动态分配内存的类型
        - **Rust对向量和数组都会做越界检查，以保证安全**
    - 范围类型
        - 两种区间
            - `std::ops::Range`, 左闭右开：`1..5`
            - `std::ops::RangeInclusive`, 全闭:`1..=5`
        - 两种区间是不同的
    - 切片类型
        - 切片**是对一个数组的引用片段**，有利于安全有效的访问数组的一部分，**而不需要拷贝**
        - 在底层，切片代表**一个执行数组起始位置的指针和数组长度**
        - 用`[T]`类型表示`连续序列`, 所以切片类型是`&[T]`和`&mut [T]`
        - 切片提供了两个`const fn`方法：
            - `len` 获取切片的长度
            - `is_empty` 判断切片是否为空
    - str字符串类型
        - 原生的字符串类型str，即：`字符串切片`
        - 通常以不可变借用形式存在，即`&str`
        - 出于内存安全的考虑，Rust将字符串分为两种类型
            - 固定长度字符串`str`
            - 可增长字符串`String`
        - 字符串字面量也属于`str`类型，是**静态声明周期字符串**`'static str`
        - str字符串由两部分组成：指向字符串序列的指针、记录长度的值
            - `as_ptr` 获得指针
            - `len`获取长度
        - 字符串本质是：**一段有效的UTF8字节序列**
            - 可以将字节序列转换为str字符串
                ```rust
                fn main(){
                    let truth = "xsdfsfaf";
                    let ptr = truth.as_ptr();
                    let len = truth.len()
                    let s = unsafe { //转换过程没有验证字节序列是否为合法的UTF8字符串，需要防止unsafe中执行
                        // 输入指针和长度，将字节序列转换为切片类型:&[u8]
                        let slice: Result<&str, Utf8Error> = std::slice::from_raw_parts(ptr, len);
                        // 将切片转换为str字符串
                        std::str::from_utf8(slice)
                    };

                    assert_eq!(s, Ok(truth));
                }
                ```
    - 原生指针
        - `指针`：表示内存地址的类型
        - Rust提供了多种类型的指针：
            - 引用 Reference
            - 原生指针 Raw Pointer
                - 不可变原生指针：`*const T`
                - 可变原生指针：`*mut T`
            - 函数指针 fn Pointer
            - 智能指针 Smart Pointer
        - 引用主要用于`Safe Rust`
        - 原生指针主要用于`Unsafe Rust`
            ```rust
            fn main(){
                let mut x = 10;
                let ptr_x = &mut x as *mut i32; //只能是可变引用转换为可变原生指针
                let y = Box::new(20);
                let ptr_y = &*y as *const i32;
                unsafe {
                    *ptr_x += *ptr_y;
                }

                assert_eq!(x, 30);
            }
            ```
    - `never`类型
        - 即: `!`
        - 该类型用于表示不可能有返回值的计算类型
        - `never`类型可以强制转换为任何类型
            ```rust
            fn foo() -> u32 {
                let x: != {
                    return 123 //因为return会将123返回，绑定的x永远不会被赋值，所以这里使用never类型不会出现编译错误
                };
            }

            fn main() {
                let num: Option<32> = Some(42);
                match num {
                    Some(num) => num,
                    None => panic!("Nothing"),
                }
            }
            ```
- 复合数据类型
    - 元祖
        - 元组是一种:`异构有限序列`
            - 异构指：元素内的元素可以是不同类型
            - 有限指：元组有固定长度
        - `(T, U, M, N...)`，元祖内的元素可以是不同类型的
        - 当元组只有一个值的时候，需要加逗号：`(0, )`

    - 结构体
        - 具名结构体
        - 元组结构体
            - newtype模式：
                - **一个元组结构体只有一个字段**
                - 相当于将旧类型包装成了新类型
                    ```rust
                    struct Integer(u32);
                    type Int = i32; // 对类型进行了重命名
                    fn main() {
                        let int = Integer(10);
                        assert_eq!(int.0, 10);
                        let int: Int = 10;
                        assert_eq!(int.), 10);
                    }
                    ```
        - 单元结构体
            - 没有字段的结构体
            - 在Release编译模式下，单元结构体变量会使用相同的地址；在Debug模式下，则不同
            - 标准库中表示全范围`(..)`的`std::ops::RangeFull`,就是一个单元结构体
            ```rust
            struct Empty;
            fn main() {
                let x = Empty;
                println!("{:p}", &x);
                let y = x;
                println!("{:p}", &y);
                let z = Empty;
                println!("{:p}", &z);
                assert_eq!((..), std::ops::RangeFull);
            }
            ```

    - 枚举
        - 需要使用`枚举类型::枚举值`的方式来使用
        - 无参数枚举体
            ```rust
            enum Number{
                Zero,
                One,
                Two,
            }
            fn main(){
                let a = Number::One;
                match a {
                    Number::Zero => println!("0"),
                    Number::One => println!("1"),
                    Number::Two => println!("2"),
                }
            }
            ```
        - 类C枚举体
            ```rust
            enum Color{
                Red = 0xff0000,
                Green = 0x00ff00,
                Blue = 0x0000ff,
            }

            fn main(){
                println!("#{:06x}", Color::Red as i32);
                println!("#{:06x}", Color::Blue as i32);
            }
            ```
        - 带参数的枚举
            - 枚举值中携带类型参数，这样的枚举值本质上属于**函数指针类型**

    - 联合体

## 常用集合类型
[top](#catalog)
- `std::collections`模块下有4中通用集合类型
    - 线性序列
        - 向量 Vec
        - 双端队列 VecQueue
        - (双向))链表 LinkedList
    - Key-Value映射表
        - 无序哈希表 `HashMap<K, V>`
        - 有序哈希表 `BTreeMap<K, V>`
    - 集合类型
        - 无序集合 HashSet
        - 有序集合 BTreeSet
    - 优先队列
        - 二叉堆 BinaryHeap
- 向量Vec
    - 三种初始化方法
        ```rust
        let mut v1 = vec![]; //1, 需要指定类型
        let mut v2 = vec![0; 10];  //len=10，全部初始化为0
        let mut v3 = Vec::new(); // 1， 需要指定类型
        ```
- 双端队列
    - 需要引入`use std::collections::VecDeque;`
    - 两种`push`方式
        - `push_front`在队头添加
        - `push_back`在队尾添加
    - `get(index)`来获取队列中的值
- 链表
    - 需要引入`use std::collections::LinkedList;`
    - Rust默认提供的是`双向链表`，可以在任意一端插入或弹出元素
    - 通常最好使用`Vec`、`VecDeque`，因为他们**比链表更快、内存访问效率更高，可以更好的利用CPU缓存**
- KV映射表
    - 需要引入
        - `use std::collections::HashMap;`
        - `use std::collections::BTreeMap;`
    - `Key`必须是**可哈希**类型
    - `Value`必须是**编译器已知大小的类型**
    - 通过`new`创建对象，通过`insert`来插入键值对
        ```rust
        use std::collections::HashMap;
        use std::collections::BTreeMap;
        fn main(){
            let mut hmap = HashMap::new();
            let mut bmap = BTreeMap::new();

            hmap.insert(3, "c");
            hmap.insert(1, "a");

            bmap.insert(3, "c");
            bmap.insert(2, "c");

            println!("{:?}", hmap);
            println!("{:?}", bmap);
        }
        ```
- 集合
    - `HashSet`、`BTreeSet`是`HashMap`,`BTreeMap`把`Value`设为空元组的特定类型
    - 基本特性与`HashMap`,`BTreeMap`相同
- 优先队列 BinaryHeap
    - Rust的优先队列是基于**二叉最大堆**实现的

## 智能指针
[top](#catalog)
- Rsut中的**值默认被分配到栈内存**
- 可以通过`Box<T>`将值装箱
    - `Box<T>`是指向类型为`T`的堆内存分配值的指针
    - `Box<T>`离开作用域是，将调用`Drop`来销毁内部对象，释放堆中的内存
    - 可以通过`解引用操作符*`来获取`Box<T>`中的`T`
    - `Box<T>`的行为像引用、可以自动释放内存，所以称为智能指针

## 泛型和trait
[top](#catalog)
- trait是Rust实现零成本抽象的基石
- trait的机制
    - trait是Rust唯一的接口抽象方式
    - 可以静态生成，也可以动态调用
    - 可以当作**标记**类型拥有**某些特定行为**的**标签**来使用
    - 静态分发
        - 通过`fly_static::<Pig>(pig)`调用函数，Rust编译器会给这两个具体类型的调用生成特殊化的代码
        - 编译器做完静态分发后，使得实际使用时，**抽象并不存在**
    - 动态分发
        - 通过`fly_dyn(&pig)`调用函数，函数将会在运行时查找相应类型的方法，**会产生一定的运行时开销，不过很小**
    ```rust
    struct Duck;
    struct Pig;

    trait Fly {
        fn fly(&self) -> bool;
    }

    impl Fly for Duck {
        fn fly(&self) -> bool {
            return true;
        }
    }

    impl Fly for Pig {
        fn fly(&self) -> bool {
            return false;
        }
    }

    fn fly_static<T: Fly> (s: T) -> bool { //s必须是实现了Fly的类型，或者拥有fly这种行为的类型
        s.fly()
    }

    fn fly_dyn(s: &Fly) -> bool{ // 代表所有拥有fly这种行为的类型
        s.fly()
    }

    fn main(){
        let pig = Pig;
        assert_eq!(fly_static::<Pig>(pig), false); // 通过指定类型来调用方法，使用了静态分发
        let duck = Duck;
        assert_eq!(fly_static::<Duck>(duck), true);
        assert_eq!(fly_dyn(&Pig), false);
        assert_eq!(fly_dyn(&Duck), true);
    }
    ```

## 错误处理
[top](#catalog)
- 通过返回`Result<T, E>`类型的方式进行错误处理
- 允许`main`函数返回`Result<T, E>`
    ```rust
    use std::fs::File;
    fn main() -> Result<(), std::io::Error>{
        let f = File:open("bar.txt")?;
        Ok(());
    }
    ```
## 注释和打印
[top](#catalog)
- 注释
    - 普通注释
        - `//`，整行注释
        - `/*..*/`， 区块注释
    - 文档注释
        - `///`，一般用于函数或结构体的说明
        - `//!`，一般用于说明整个模块的功能，置于文件的头部

- 打印:`println!`中的格式化形式列表
    - `{}`，Display
    - `{:?}`，Debug
    - `{:o}`，8进制输出
    - `{:x}`，16进制小写
    - `{:X}`，16进制大写
    - `{:p}`，输出指针
    - `{:b}`，二进制输出
    - `{:e}`，指数小写
    - `{:E}`，指数大写


- 宏语句可以使用`[]`, `{}`, `()`
    - 常用`[]`来创建数组

# 类型系统
## 通用概念
[top](#catalog)
- Rust属于显示静态类型
- 如果一个类型系统允许一段代码在不同的上下文中具有不同的类型，这样的类型系统就叫做**多类型系统**
- 现代编程语言的三种多态形式
    - 参数化多态
        - 即：**泛型**
    - Ad-hoc多态，也称为`特定多态`
        - 指同一种行为定义，在不同的上下文中会响应不同的行为实现
        - 即**Trait**
    - 子类型多态
        - 即：java中的继承
        - 子类可以视作父类的对象来使用
        - **Rust中没有子类型多态**
## Rust类型系统
[top](#catalog)
- Rust代码被编译为`LLVM IR`，其中携带了内存分配的信息。所以编译器需要事先知道类型的大小，才能分配合理的内存
- 可确定大小类型和动态大小类型
    - 如str类型的大小无法确定，需要使用引用类型
        - `&str`同时包含了地址信息和长度信息，称为**胖指针**
    - 数组`[T]`是动态大小类型，编译器无法确定大小，需要增加长度`[T; len]`
        - 但是`[T]`和`[T; len]`是两种类型
    - 数组切片
        ```rust
        fn main(){
            let mut a = [1,2,3,4,5];
            println!("{:?}",a);
            reset(&mut a);
            println!("{:?}",a);
        }

        fn reset(arr: &mut [i32]){
            arr[0]=5;
        }
        ```
- 零大小类型 zero sized type，ZST
    - 包括：单元类型、单元结构体，零长度的数组，空枚举
    - 由零大小类型组成的类型也是零大小类型
        ```rust
        enum Void{}
        struct Foo;
        struct Baz{
            foo:Foo,
            qux:(),
            baz:[u8;0],
        }

        fn main(){
            assert_eq!(std::mem::size_of::<()>(), 0);
            assert_eq!(std::mem::size_of::<Foo>(), 0);
            assert_eq!(std::mem::size_of::<Void>(), 0);
            assert_eq!(std::mem::size_of::<Baz>(), 0);
            assert_eq!(std::mem::size_of::<[();10]>(), 0);
        }
        ```
    - 零大小类型的值就是本身
    - **运行时不占用空间**
    - 单元类型向量：`Vec<()>`
        - <label style="color:red">单元类型向量在一些只需要迭代次数的场合中，性能更高</label>
            - Vec内部迭代器中会针对ZST类型做优化
            ```rust
            fn main(){
                let a = vec![(); 10];
                for v in a{
                    println!("{:?}", v);
                }
            }
            ```
    - 标准库中将`HashMap`,`BTreeMap`中的Value替换成`()`，得到了`HashSet`,`BTreeSet`，不用再重新实现
- 底类型
    - 即`never`类型，用`!`表示
        - 也被称为`Bang Type`
    - 特点
        - 没有值
        - **是其他任意类型的子类型**
    - ZST类型表示**空**，底类型表示**无**
    - 底类型无值，可以**等价于任意类型**
    - Rust中没有值的情况
        - 发散函数，这类函数永远没有返回值
            - `panic!`
            - 退出函数`std::process:exit`
        - continue、break
            - 只表示流程的跳转，不会返回什么
        - loop循环
        - 空枚举，如`enum Void{}`
        - if中的无返回类型分支
            ```rust
            
            ```