<span id="catalog"></span>
- [基础](#基础)

# 基础
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
* Rust和Cargo的指令在各系统中都相同

* `func main(){}`，在可运行的rust程序中，`main`总是最先运行的代码，并且**没有参数，也没有返回值**
* 编译与运行相互独立
* 每一行以`;`结尾
* 调用方法/宏
    * `method!(...);`，如果方法名后面有`!`，表示调用宏
    * `method(...);`，表示调用方法
