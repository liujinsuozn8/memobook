<span id="catalog"></span>
- [基本知识](#基本知识)
- [数据类型](#数据类型)
    - [基本数据类型](#基本数据类型)
    - [派生-复杂数据类型](#派生-复杂数据类型)
- [运算符](#运算符)
- [输入输出](#输入输出)
- [流程控制](#流程控制)
- [包](#包)
- 函数
	- [基本用法](#基本用法)
	- [init函数](#init函数)
	- [匿名函数](#匿名函数)
	- [defer延时机制](#defer延时机制)
    - [字符串常用的系统函数](#字符串常用的系统函数)
    - [日期函数](#日期函数)
- 数组
	- [数组](#数组)
	- [二维数组](#二维数组)
- [切片](#切片)
- [map](#map)
- [排序和查找](#排序和查找)
- OOP
	- [结构体](#结构体)
	- [方法](#方法)
    - [工厂模式](#工厂模式)
    - 三大特性
        - [封装](#封装)
        - [继承](#继承)
        - [接口](#接口)
		- [多态](#多态)
- [文件操作](#文件操作)
- [命令行参数解析](#命令行参数解析)
- [json](#json)
- [单元测试](#单元测试)
- goroutine和channel
	- [goroutine](#goroutine)
	- [goroutine通信问题](#goroutine通信问题)
	- [channel](#channel)
- [反射](#反射)
- [tcp编程](#tcp编程)
- [操作redis](#操作redis)

# 基本知识
[top](#catalog)
* Go 程序开发的注意事项
    * Go 源文件以 "go" 为扩展名。
    * Go应用程序的执行入口是main()函数。这个是和其它编程语言(比如java/c)
    * Go语言严格区分大小写。
    * Go方法由一条条语句构成，每个语句后不需要分号(Go语言会在每行后自动加分号)，这也体现出 Golang 的简洁性
    * go编译器是一行一行编译的，因此一行就写一句话，不能把多行语句写在同一行，否则会报错
    * go语言定义的变量或者import的包如果没有使用到，代码不能编译通过
    * 大括号都是成对出现的，缺一不可
    * 一行最长不超过 80 个字符
* 转义字符
    * `\t=制表符`
    * `\n=换行符`
    * `\\=\`
    * `\"="`
    * `\r=回车`
    * ``
* 注释
    * 单行注释：`\\`
    * (块注释)多行注释：`\* *\`
        * 多行注释不能嵌套
* 变量
    * 表示内存中的一个存储区域，且该区域有名称和类型
    * 全局变量：在函数外部定义的变量
    * 变量的类型在使用过程中不能变，在类型范围内可以变化
    * 作用域内不能重复声明
    * **三大要素：变量=变量名+值+数据类型**
    * 变量如果没有赋值，编译器会使用默认值
    * 使用步骤
        1. 声明变量(定义变量) : `var 变量名 类型`
        2. 非变量赋值
        3. 使用变量
    * 使用的三种方式
        1. 声明、不赋值=使用默认值
        2. 不声明类型，直接赋值=自动推导类型：`var a = 10`
        3. 使用`:=`赋值、省略`var`，且**左侧变量必须是未声明过的**，否则会导致**编译错误**， <label style="color:red">不能用这种方式声明/定义全局变量</label>
            * `a := "tom"`
        4. 声明多个变量
            * `a, b, c, d := 1, 2, "a", 4`
        5. 声明多个全局变量
            ```go
            var(
                a = 1
                b = 2
                c = "cccc"
            )
            ```
    * 声明、初始化、赋值
        ```go
        var a int //声明
        var b int = 45 //初始化
        b = 60 //赋值
        ```
* 常量
	* 使用const修饰
	* 常量在定义时必须初始化
	* 常量不能修改
	* 常量只能修饰bool、数值类型、string类型
	* 语法`const 常量名 [type] = value`
	* `const a = 9/3`可以，`const a = 其他变量/3`不可以，`const a = 函数()`不可以
	* 声明多个常量
		```go
		const (
			a=1
			b=2
		)
		```
		```go
		const (
			a = iota //表示给a赋值为0，b=a+1，c=b+1
			b //1
			c //2
			d //3
		)
		```
		```go
		const (
			a    = iota //0
			b    = iota //1
			c, d = iota, iota //2, 2
		)
		```
* 标识符
    * 对变量、方法、函数等命名时使用的字符序列
    * 组成：26英文字母、0-9，_
    * go中区分大小些
    * `_` 空标识符，可以代表任何其他的标识符，但是它对应的值会被忽略，所以**仅能被作为占位符使用，不能作为标识符使用**
    * 包名：保持package和目录一致，尽量不要和标准库冲突
    * 变量、函数名、常量名：采用**驼峰法**命名
        * 如果首字母大写，则可以被其他的包访问（public）
        * 如果首字母小些，只能在本包中使用（private）
		* 首字母不能是数字
* go中没有public、private等关键字


# 数据类型
## 值类型和引用类型
[top](#catalog)
* 值类型(变量存储的是值)
    * 基本数据类型：int系列，float系列，bool，string
    * 复杂/派生类型：数组、结构体struct
    * **变量直接存储值，内存通常在栈中分配**
* 引用类型：指针、slice切片、管道channel、interface接口
    * **变量存储一个地址，内存通常在堆上分配。没有任何变量引用地址时，这个地址将变成垃圾有GC回收**

## 基本数据类型
[top](#catalog)
* 数值型
    * 整数型
        * 有符号
            * rune 等价于int32，**表示一个Unicode码**
            * int，**整型的默认类型**
                * 32位系统=int32
                * 64位系统=int64
            * int8，1字节，[-128, 127]
            * int16，2字节，[-2^15, 2^15-1]
            * int32，4字节，[-2^31, 2^31-1]
            * int64，8字节，[-2^63, 2^63-1]
        * 无符号
            * uint
                * 32位系统=uint32
                * 64位系统=uint64
            * uint8，1字节，[0, 225]
            * uint16，2字节，[0, 2^16-1]
            * uint32，4字节，[0, 2^32-1]
            * uint64，8字节，[0, 2^64-1]
        * byte，1字节，[0, 225]，等价于uint8，用来存储字符
        * **整型变量遵守：保小不保大**
            * `var age byte = 90`，会转换为uint8
    * 浮点型(符号为+指数位+尾数位)
        * float32，4字节，[-3.403e38, 3.403e38]
        * float64，8字节，[-1.798e308, 1.798e308]
        * 默认浮点类型为float64
        * 浮点型有固定范围和字段长度，不受操作系统影响
        * 表示方式
            * 数值：`a = 123.45`
            * 科学计数法：`a = 1.2345e2`
* 字符型
    * 单引号扩起来
    * **没有专门的字符型，使用byte来保存单个字母字符**，范围在[0,255]
    * 如果字符的码值大于255，可以使用int类型保存
    * go中的字符本质是**整数**
    * 字符使用UTF-8编码，英文字母=1字节，汉字=3字节
    * 字符可以和数值进行运算：`var = 10 + 'a' // 10 + 97`
* 布尔型
    * 1字节，**只有true/false**
* 字符串型
    * **底层是是字节数组**
    * 字符串赋值后，字符串中的字符不能再修改
    * 两种形式
        * 双引号：能识别转义字符
        * 反引号：以原生形式输出
    * 通过`+`来拼接字符串
        * 过长时可以用`+`连接，`+`**必须留在上一行**
            ```go
            var a = "dddd" +
                "xxxx"
            ```
    * string和slice
        * string底层是一个byte数组，所以string可以进行切片
            ```go
            a := "abcdef"
            b := a[2:4]
            fmt.Println(a) //abcdef
            fmt.Println(b) //cd
            ```
        * string是不可变的，所以不能通过切片来修改字符串
    * 修改字符串内容的方法
        * 通过`[]byte(str)`将字符串转换成byte数组，对数组的元素进行修改，在通过string()转换成字符串。**但是`[]byte`不能处理中文**
            ```go
            a := "abcdef"
            fmt.Println(a) //abcdef
            arr1 := []byte(a)
            arr1[1] = 'x'
            a = string(arr1)
            fmt.Println(a) //axcdef
            ```
        * 使用`[]rune(str)`处理字符串，方法和[]byte相同，**可以处理中文**
            ```go
            a := "abcd中文f"
            fmt.Println(a) //abcd中文f
            arr1 := []rune(a)
            arr1[5] = '啊'
            a = string(arr1)
            fmt.Println(a) //abcd中啊f
            ```


* <label style="color:red">基本数据类型一般都会分配到栈区</label>
* 基本数据类型的默认值
    * 整型=0
    * 浮点型=0
    * 字符串=""
    * 布尔型=false
* 基本数据类型的相互转换
    * **go中没有自动转换**
    * 转换方法：`类型(变量)`
    * 变换的是值，数据类型本身没有变换
* 基本数据类型和string的转换
    * 基本数据类型--->string
        * `fmt.Sprintf("%参数", 表达式)`
        * strconv包的函数
            * `strconv.FormatXXX`
    * string--->基本数据类型
        * **转换时，需要确保数据的有效性**
        * strconv包的函数
            * `strconv.ParseXXXX`
* 默认值
    * 数值型：0
    * 字符串：`""`
    * bool：false
## 派生-复杂数据类型
[top](#catalog)
* 指针pointer
    * 使用方法：
        ```go
        var a int
        var ptr *int = &a // int型指针
        *ptr //使用指针指向的值
        ```
    * 值类型，都有对应的指针类型
* 数组
* 结构体struct
* 管道channel
* 函数
* 切片slice
* 接口interface
* map

## 自定义数据类型
* 基本语法`type 自定义数据类型名 数据类型`， 相当于给数据类型起了一个别名
* 基本数据类型：`type myInt int`
* 函数：`type myFunc func(参数列表) (返回值列表)`

# 运算符
* 算数运算符
    * `+`
        * 符号两边是数值，做加法运算
        * 符号两边是字符串，做字符拼接
        * 字符可以通过`+`与数值进行运算
    * `/`
        * 如果运算的数都是整数，运算后，只保留整数部分
            ```go
            fmt.Println(10/4)
            ```
        * 如果需要保留小数部分，需要有浮点数参与
            ```go
            fmt.Println(10.0/4)
            ```

    * `%`
        * a%b，结果符号与a相同
    * `++`,`--`
        * 自增自减只能独立使用，不能：`var a = i++`
        * go的自增自减**只能写在变量后面**
* 关系运算符
    * `==`,`!=`,`<`,`>`,`<=`,`>=`,
* 逻辑运算符
    * 用于连接多个条件
    * && 短路与，第一个条件为false，不会判断第二个
    * || 短路或，第一个条件为true，不会判断第二个
* 赋值运算符
    * =
		* 交换同类型的值： `a, b = b, a`
    * `+=`,`-=`,`*=`,`/=`,`%=`,`<<=`,`>>=`,`&=`,`^=`,`|=`
* 位运算符
    * &
    * |
    * ^
    * <<
    * `>>`
* 其他运算符
    * & 返回变量存储地址
    * `*变量名` 指针变量
* 运算优先级
    1. 括号，++，--
    2. 单目运算符 正负号，!，~，(type)，*，&，sizeof
    3. 算数运算符 +，-，*，/
    4. 移位运算 <<，>>
    5. 关系运算符 
    6. 位运算符 &，|，^
    7. 逻辑运算符 &&，||
    8. 赋值运算符
    9. 逗号

# 输入输出
* 格式化输出
    * %T，查看数据类型
    * %d，数值
    * %c，字符
    * %p，指针地址
    * unsage.Sizeof(变量名)，查看变量占用的字节数
* 键盘输入语句
    * 调用fmt包的fmt.Scanln() 或者 fmt.Scanf()
    * fmt.Scanln(&变量名) 通过换行结束输入
    * fmt.Scanf("%s %d...", &变量1, &变量2.....) 按照指定格式输入数据
# 流程控制
* if
	* 条件表达式不需要括号（有也可以，最好不用）
    * 单分支**{}**必须有，即使只有一行代码
        ```go
        if 条件表达式 { //左括号必须在这里
            代码块
        }
        ```
	* 双分支
		```go
		if 条件表达式 {
			代码块
		} else { //else不能换行
			代码块
		}
		```
	* 多分支
		```go
		if 条件表达式 {
			代码块
		} else if {
			代码块
		} else {
			代码块
		}
		```
	* 可以在if中定义变量
		```go
		if age:=20; age>18{
			...
		}
		```
* switch
	* 匹配向后面**不需要加break**
	* 基本语法
		```go
		switch 表达式x{
			case 表达式1，表达式2，...:
				代码块1
			case 表达式3，表达式4，...:
				代码块2
			....
			default:
				代码块
		}
		```
	* default部分不是必须的
	* 表达式可以是：常量值，变量，一个返回值的函数
	* case 和 switch 表达式的值的数据类型必须相同
	* case中的表达式如果是**字面值，则不能重复**
		```go
		switch a {
			case 5, 10, 11:
				.....
			case 5: //上一个case已经包含5，不能重复定义
				..... 
		}
		```
	* switch可以不带分支，类似于if-else分支
		```go
		var a = 10
		switch {
			case a == 10:
				....
			case a == 11:
				....
			default:
				....
		}
		```
	* switch 后可以声明/定义一个变量,以分号结束，但是不建议使用
		```go
		switch grade := 90; {
			case xxx:
				....
			case yyy:
				....
		}
		```
	* switch 穿透： fallthrough
		* 在case结束时使用fallthrough，会继续执行下一个case，并且是忽略匹配条件直接执行下一个case的代码块
		* 默认只能穿透一层
		* 实例
			```go
			// 输出
			//this is 2
			//this is 3
			var a int = 10
			switch a {
				case 11:
					fmt.Println("this is 1")
				case 10:
					fmt.Println("this is 2")
					fallthrough
				case 13:
					fmt.Println("this is 3")
				default:
					fmt.Println("this is default")
			}
			```
	* type-switch，判断某个interface变量中实际指向的变量类型
		```go
		var x = interface{}
		var y = 10.0
		var x=y
		switch i:=x.(type){
			case nil:
				...
			case int:
				...
			case float32:
				...
		}
		```
* for
	* 基本语法
		* 普通写法
			```go
			for 循环变量初始化; 循环条件; 循环变量迭代 {
				循环语句
			}
			
			for i := 1; i <= 10; i++ {
				...
			}
			```
		* 将变量初始化和变量迭代写到其他位置
			```go
			i := 1 //循环变量初始化
			for 循环条件 { 
				循环语句
			}
			```
		* 无限循环，需要配合break来使用
			```go
			for {
				//循环执行语句
			}
			```
		* for-range 可以进行字符串和数组的遍历
			* for-range 的两种形式
				```java
				a := []int{2,4,6,8}

				// 循环时只用index
				// 如果不使用index，可以使用：for _ := range a {
				for i := range a {
					fmt.Println(i) // 只输出index，即：0 1 2 3
				}

				// 循环时使用index和value
				for i, v := range a {
					fmt.Println(i, v) // 分别输出index和value
				}
				```

			* 对于普通的遍历方式：字符串遍历时， 是按照字节来遍历的。中文的utf8编码是3个字节，如果有字符串有中文，遍历会出现乱码，需要将字符串转换成`[]rune(字符串变量)`切片
			* 对于for-range遍历方式，有中文也没关系
			```go
			// 普通的字符串遍历
			var str = "abcde"
			for i :=0; i<len(str); i++ {
				 fmt.Printf("%c\n",str[i])
			}
			
			// 包含中文
			var str = "abcde中文"
			str2 := []rune(str)
			for i :=0; i<len(str2); i++ {
				 fmt.Printf("%c\n",str2[i])
			}
			
			// for-range遍历
			for index, val := range str {
				fmt.Printf("index=%d, val=%c\n", index, val)
			}
			```
	* 实例：打印空心金字塔
		```go
		var total int = 6
		for i:=1; i<=total; i++{
			for k := 1; k <= total-i; k++{
				fmt.Print(" ")
			}
			for j:=1; j <= 2*i -1; j++ {
				if j==1 || j == 2*i -1 || i ==total {
					fmt.Print("*")    
				} else {
					fmt.Print(" ")
				}
			}
			fmt.Println()
			
		}
		```
* while和do...while
	* go中没有while和do...while，可以使用for来实现
	* while的实现
		```go
		循环变量初始化
		for {
			if 循环条件表达式{
				break //跳出for
			}
			循环操作
			循环变量迭代
		}
		```
	* do...while的实现
		```go
		循环变量初始化
		for {
			循环操作
			循环变量迭代
			if 循环条件表达式{
				break //跳出for
			}
		}
		```
* break
	* `break` 默认跳出最近的for循环
	* `break 标签名` 跳出标签对应的for循环
* continue
	* `continue` 默认结束本次循环，继续执行下一次循环
	* `continue 标签名` 结束到标签对应的for循环，再执行下一次循环
* goto
	* 无条件转义到程序中的指定行
	* 使用方法：
		```go
		goto label
		...
		label:statement
		```
* return
	* 跳出方法或函数
		* 在普通函数中，表示跳出该函数，不再执行return后面的代码
		* 在main函数中，表示终止程序

# 包
[top](#catalog)
* 包的本质就是创建不同的文件夹，来存放程序文件
* go以包的形式来管理文件和项目目录结构
* go的每一个文件都属于一个包
* 包的三大作用
	* 区分相同名字的函数、变量等标识符
	* 项目管理
	* 控制函数、变量的作用域
* import包时，如果不想使用包中的任何程序实体，只需要执行包的init操作，可以使用`_`来重命名：`import _ "xxx/yyy/zzz"`
* 使用方法
	* 打包：`package 包名`
		* 打包指令需要在文件的第一行
		* 打包时，**包对应一个文件夹**
		* 文件的包名通常和文件所在的文件夹名一致，一般为小写字母
		* 跨包访问的标识符的首字母需要大写
		* 同包下，不能有相同的函数名，也不能有相同的**全局变量名**，否则会包重复定义
		* 如果要编译成一个可执行文件，需要将该包声明为main；如果写一个库，包名可以自定义
    * `go run XXX.go` 执行该指令时，go文件的包必须是main package
	* 引包：`import 包路径` 
		* 引入时，从$GOPATH的src下开始
		* 如果包名较长，可以给包取别名
			* 取别名后，原来的包名就不能使用了
			```go
			import (
				别名 包名
			)
			```
		* 引入多个包：
			```go
			import (
				"xxxx"
				"yyyy"
			)
			```

# 函数
## 基本用法
[top](#catalog)
* 语法
	* 基本写法
		```go
		// 形参类表写法1： 形参名 类型，形参名 类型，形参名 类型.....
		// 形参类表写法2： 形参名1 ，形参名2， 形参名3 类型   此时三个形参的类型相同
		// 多个返回值或1个返回值
		func 函数名(形参列表) (返回值列表) {
			代码块
			return 返回值列表 //可以有返回值，也可以没有
		}
		//接收返回值
		a, b, c.... := method()
		```
	* 1个返回值也可以省略返回值的()
		```go
		func 函数名(形参列表) 返回值列表 {
			代码块
			return 返回值
		}
	```
	* 如果没有返回值，则省略返回值列表部分
		```go
		func 函数名(形参列表) {
			代码块
		}
		```
	* 可以对函数返回值重命名
		```go
		// 返回值列表一般是类型列表，如： (int, float.....)
		// 也可以对返回值进行命名
		func 函数名(形参列表) (返回值名1 类型， 返回值名2 类型){
			返回值名1 = ...
			返回值名2 = ...
			return //此时可以不写返回的对象
		}
		
		//多个返回值
		func method(a int, b int) (c int, d int) {
			c = a + b
			d = a + 1
			return
		}
		//1个返回值
		func method(a int, b int) c int {
			c = a + b
			return
		}
		```
	* 可变参数
		```go		
		func 函数名(args...类型) (返回值列表){
		}
		
		func method(args...int)
		```
		* args是一个slice切片，通过args[index]可以访问各个值
		* 可变参数必须放在形参类表的最后
* 函数调用过程
	* 调用时，给该函数分配一个新的栈空间
	* 在这个栈空间内，数据空间是独立的
	* 执行完毕后，将该栈空间销毁
* return 返回值
	* 可以同时有多个返回值
	* 接受返回值时，如果需要忽略某个返回值，则使用`_`表示占位忽略
* **go不支持函数重载**，会引发重复定义错误
* go的函数是一种自定义数据类型
	* 函数的数据类型是：`func(形参列表) (返回值列表)`
	* 函数可以赋值给一个变量，该变量就是一个**函数类型的变量**，通过该变量可以调用函数
	* 函数可以作为形参，并调用
		```go
		func method (n1 int, n2 int) int{
			return n1 + n2
		}
		func myFun(funvar func(int, int) int, num1 int, num2 int) int {
			return funvar(num1, num2)
		}
		
		func main(){
			a := method
			
			result = myFun(method, 1,2)
		}
		```
## init函数
[top](#catalog)
* 每个源文件都可以包含一个init函数
* 该函数会在main函数执行前，被go运行框架调用
* 一个文件的执行流程： import包的全局变量定义 -->import包的init函数 --> 本包全局变量定义 --> 本包init函数 -->本包main函数
* init函数的主要作用是**完成一些初始化工作**
* 语法格式
	```go
	func inti(){
	}
	```
## 匿名函数
[top](#catalog)
* 一次性匿名函数
	```go
	method := func (n1 int, n2 int) int {
		return n1 + n2
	} (10, 20)
	```
* 将匿名函数对象赋值给变量，通过该变量来调用函数
	```go
	a := func (n1 int, n2 int) int {
		return n1 - n2
	}
	result := a(1,2)
	```
* 全局匿名函数，将匿名函数赋值给对象，就成为一个全局匿名函数
	```go
	var (
		Fun1 = func (n1 int, n2 int) int {
			return n1 + n2
		}
	)
	
	func main(){
		result := Fun1(1,2)
	}
	```

## defer延时机制
[top](#catalog)
* 作用：在函数执行完毕后，即使的释放函数创建的资源
	* 如文件资源，DB连接资源等，在函数结束后可以进行关闭
* 执行到defer时，暂不执行，将defer后的语句压入独立的defer栈；函数执行完后，再从栈中按照先入后出的方式出栈
	```go
	//输出
	//this is 3
	//this is 2
	//this is 1
	defer fmt.Println("this is 1")
    defer fmt.Println("this is 2")
    fmt.Println("this is 3")
	```
* 执行defer时，相关值的拷贝也会同时入栈
	```go
	//输出
	//result=5
	//m=2
	//n=1
	n := 1
    m := 2
    defer fmt.Printf("n=%d\n", n)
    defer fmt.Printf("m=%d\n", m)
    n++
    m++
    result := n + m
    fmt.Printf("result=%d\n", result)
	```

## 函数传参方式
* 值类型：值传递(值的拷贝)
* 引用类型：引用传递(地址的拷贝)

## 变量作用域
* 函数内部定义/声明的变量叫局部变量，作用域仅限于函数内部
* 函数外部声明/定义的是全局变量，作用域在整个包都有效
	* 如果全局变量的首字母大写，则在整个程序中都有效
	* 如果函数内部的变量名和全局变量相同，则在该函数中，全局变量会被局部变量覆盖
* 如果变量在一个代码块中，如for、if，那么这个变量的作用于就在该代码块中
	```go
	i := 100
    for i := 1; i <= 3; i++ {
        fmt.Println("for", i)
    }
    
    fmt.Println(i)
	```
## 字符串常用的系统函数
[top](#catalog)
* 统计字符串的长度:`len(str)`
* 字符串切片:`r:=[]rune(str)`
* 整数<-->字符串
    * `n, err := strconv.Atoi("12")` 字符串转整数
    * `str=strconv.Itoa(12345)` 整数转字符串
* 字符串<--->byte数组
    * `var bytes = []byte("hello")` 字符串转byte数组
    * `str = stirng([]byte{97, 98, 99})` byte数组转字符串
* 10进制转2、8、16进制，**返回对应的字符串**
    * `strconv.FormatInt(123,2)`
    * `strconv.FormatInt(123,16)`
* 不区分大小写的字符串比较：`strings.EqualFold("abc", "ABC")`
* 子串查找
    * 返回子串在字符串中第一次出现的index，如果没有返回-1:`strings.Index("fsdabcd", "abc")`
    * 返回子串在字符串最后一次出现的index:`strings.LastIndex("abcabcabcd", "abc")`
    * 查找子串是否在指定字符串中:`strings.Contains(""seafood", "foo")`，返回bool
    * 统计一个字符串中有几个指定的子串：`strings.Count("aaaxxx", "aa")`，返回int
* 将指定的子串替换成另一个子串：`strings.Replace("ababababcccc", "ab", "x", n)` n是替换多少次，如果n=-1表示全部替换
* 按照指定字符将字符串拆分成字符串数组：`strings.Split("hello, world, ok",",")`
* 大小写转换：`strings.ToLower("Go")`,`strings.ToUpper("Go")`
* 去掉字符
    * 去掉字符串两边的空格：`strings.TrimSpace(" dfsf  ")`
    * 去掉左侧的指定字符：`strings.TrimLeft("!!fsf!!", "!")`
    * 去掉右侧的指定字符：`strings.TrimRight("!!fsf!!", "!")`
* 判断字符串的开头：`strings.HasPrefix("abcdsef", "abcd")`
* 判断字符串的结束：`strings.HasSuffix("abcdsef", "abcd")`
* 格式化日期
    * Printf 格式化输出
    * Sprintf 返回一个格式化后的字符串
## 日期函数
[top](#catalog)
* 需要导入time包
* time.Time类型，用于表示时间
* 获取当前时间：`now := time.Now()`
* 获取日期的其他信息
    * 年`now.Year()`
    * 月`now.Month()`
    * 天`now.Day()`
    * 小时`now.Hour()`
    * 分钟`now.Minute()`
    * 秒`now.Second()`
* 时间的常量
    * `time.Nanosecond` 1纳秒
    * `time.Microsecond` 微秒
    * `time.Millisecond` 豪秒
    * `time.Second` 秒
    * `time.Minute` 分钟
    * `time.Hour` 小时
* `time.Sleep(时间)` 暂停
* `Time.Unix()` 从1970/01/01 00:00:00至今的秒数
* `Time.UnixNano()` 从1970/01/01 00:00:00至今的纳秒数
    * 如果纳秒单位的unix时间超出了int64的表示范围，结果是为定义的？？？
* 计算时间差
	```go
	func main(){
		a := time.Now().Unix()
		time.Sleep(time.Second*10) 暂停10秒
		b := time.Now().Unix()
		fmt.Println(b-a) //输出10
	}
	```

## 内置函数
[top](#catalog)
* `len()` 
    * 求stirng、array、slice、map、channel的长度
* `new()`
    * 用来分配内存，主要用来分配值类型
    * 返回的结果是指针
    * 实例
        ```go
        num := new(int)
        *num = 100
        ```
* `make()`
    * 用来给引用类型分配内存

## 错误处理
[top](#catalog)
* 通过：defer、panic、recover来处理异常
    * 程序抛出一个panic异常，然后在defer中通过recover来捕获异常，然后正常处理
    * 实例
        ```go
        defer func() {
            err := recover()
            if err != nil {
                fmt.Println("err=", err)
                return
            }
        }()
        num1 := 0
        num2 := 100
        num3 := num2 / num1
        fmt.Println(num3)
        ```
* 自定义错误
    * `errors.New("错误说明")` 返回一个error类型的值，表示一个错误
    * panic(interface{}) 内置函数，接收任何值作为参数，可以接收error类型的变量，输出错误信息，并退出程序
    * 实例
        ```go
        //输出：this is error
        func main() {
            // 增加defer后会拦截errors.New("this is error")中的信息
            // 并且只输出：this is defer
            // defer func() {
            // 	err := recover()
            // 	if err != nil {
            // 		fmt.Println("this is defer")
            // 	}
            // }()
            errortest()
        }

        func myerror() (err error) {
            return errors.New("this is error")
        }

        func errortest() {
            err := myerror()
            if err != nil {
                panic(err)
            }
            fmt.Println("this is errortest")
        }
        ```

# 数组
## 数组
[top](#catalog)
* **数组是值类型**
* 定义一个数组：`var 数组名 [长度]数据类型`
* 给数组元素赋值：`数组名[index] = 值`
* 数组的长度是固定的，不能变化
* 数组的内存布局
    * 数组的地址可以通过`&数组名` 来获取
    * **数组第一个元素的地址，就是数组的首地址**
    * 各个元素的地址间隔是依据数组的类型决定
    ```go
    var a [4]int64
	a[0] = 0
	a[1] = 1
	a[2] = 3
    a[3] = 4

	fmt.Printf("%p\n", &a) //0xc000092000
	fmt.Printf("%p\n", &a[0]) //0xc000092000
	fmt.Printf("%p\n", &a[1]) //0xc000092008
	fmt.Printf("%p\n", &a[2]) //0xc000092010
	fmt.Printf("%p\n", &a[3]) //0xc000092018
    ```
* 初始化数组的方式
	1. `var a [3]int`， 数组中的值为默认值
    2. `var a [3]int = [3]int{1,2,3}`
    3. `var a = [3]int{1,2,3}`
    4. `var a = [...]int{1,2,3}`
    5. `var a = [...]int{1:1,0:2,2:3}`，`var a = [3]int{1:1,0:2,2:3}`
        * 初始化时指定位置
    6. 类型推导：`a := [...]int{1:1,0:2,2:3}`
* 数组遍历
    * `for i := 0; i<len(a); i++{}`
    * for-range
        ```go
        for index, value := range a{

        }
        ```
        * index是数组的下标，value是该index下的值
        * index、value是仅在for循环内部可见的局部变量
* 有长度的是数组：`var a [4]int`，没有长度的是切片：`var a []int`
* 数组创建后如果没有赋值，则使用默认值
* go的数组是**值类型**
    * 数组作为函数参数时，使用**值传递**，会进行值拷贝，数组间不会互相影响
    * 如果想在其他函数中修改原来的数组，可以使用引用传递--指针
    * **传递数组参数时，数组的长度必须相同**
* 使用数组的指针时需要用括号括起来:`(*arr)[index]`

## 二维数组
[top](#catalog)
* 声明二维数组：`var 数组名 [维度1][维度2]数据类型`
	```go
	//声明二维数组
	var arr [4][6]int
	```
* 直接初始化： **二维数组的第二个维度不能省略**
	* `var 数组名 [维度1][维度2]数据类型 = [维度1][维度2]数据类型{{...},{....},,,,,}`
	* `var 数组名 [维度1][维度2]数据类型 = [...]][维度2]数据类型{{...},{....},,,,,}`
	* `var 数组名 = [维度1][维度2]数据类型{{...},{....},,,,,}`
	* `var 数组名 = [...][维度2]数据类型{{...},{....},,,,,}`
	```go
	var a [3][2]int = [3][2]int{{1,2}, {3,4}, {5,6}}
	```
* 内存中的形式
	* 第二维度存储的是具体的元素
	* 第一维度存储的是指向第二维度的指针
* for-range遍历
	```go
	var a [3][2]int = [3][2]int{{1,2}, {3,4}, {5,6}}
    for i, row := range a{
        for j, v := range row{
            fmt.Printf("a[%d][%d]=%d\n", i, j, v)
        }
    }
	```

# 切片
[top](#catalog)
* 切片是数组的一个引用，所以**切片是引用类型**
* 遍历、访问元素、求切片长度的方法和数组相同
* 切片的长度可以变化，所以**切片是动态数组**
* 定义切片：`var 切片名 []类型`
* 切片在内存中的形式
    * slice 的底层是一个数据结构(struct结构体)
        ```go
        type slice struct{
            ptr *[长度]int //指向第一个元素的地址
            len int //切片的长度
            cap int //切片的容量cap>=len
        }
        ```

* 切片的使用：
    * 1.定义切片，然后引用一个已经创建好的数组，使用数组时index不能越界
        ```go
        var a [5]int = [...]int{1,2,3,4,5}
        var s = a[2:4]
        ```
    * 2.通过make来创建切片:`var 切片名 []type = make([]type, len, [cap])`
        * 通过make创建的切片可以指定切片的大小和容量
        * 如果没有赋值，会使用默认值
        * 通过make创建的切片**对应的数组是有make底层维护，对外不可见，只能通过slice来访问**
        ```go
        var s []int = make([]int, 5, 10)
        s[1] = 11
        s[2] = 22
        ```
    * 3.定义切片，直接指定具体数组，原理类似于make的方式
        ```go
        var s []int = []int{1,2,3,4}
        ```
    * 1和2的区别
        * 1是直接引用数组，该数组事先存在，且在程序中可以操作
        * 2是由make创建切片，同时创建一个数组，底层由make维护，无法直接使用
    * **切片定义完后，不能使用，因为本身是空的，需要引用到一个数组，或者使用make来创建,或者通过`append`添加也可以**
		* 定义后的切片虽然是空的：`[]`,但是不是`nil`
		* 通过make创建后，所有的元素都是默认值
    * **切片可以继续切片，但是指向的数据空间是相同的，修改时会影响所有的切片**
        ```go
        var a [5]int = [5]int{1, 2, 3, 4, 5}
        // var s []int = []int{1, 2, 3, 4}
        var s []int = a[1:4]
        var s2 []int = s[1:3]
        fmt.Println(s) //[2 3 4]
        fmt.Println(s2) //[3 4]
        fmt.Println(a) //[1 2 3 4 5]
        fmt.Println()

        s2[1] = 100
        fmt.Println(s) //[2 3 100]
        fmt.Println(s2) //[3 100]
        fmt.Println(a) //[1 2 3 100 5]
        ```

* 切片的遍历
    * `for i:=0; i<len(s); i++{}`
    * for-range
        ```go
        for index, value := range s{
            ...
        }
        ```
* 切片的特殊写法
    ```go
    var s = a[0:end] --> var s = a[:end]
    var s = a[start:len(a] --> var s = a[start:]
    var s = a[0:len(a)] --> var s = a[:]
    ```
    
* 使用append内置函数，对切片进行**动态添加**
    * 实例
    ```go
    var a []int = []int{1, 2, 3, 4, 5}
    fmt.Println(a) //[1 2 3 4 5]

    a = append(a, 10, 20)
    fmt.Println(a) //[1 2 3 4 5 10 20]
    ```

    * 底层原理
        * 本质上是对数组进行扩容
        * 添加元素后，底层会创建一个新的数组newArr，将oldArr拷贝到newArr中，然后写入新元素。做完newArr后slice中的指针指向newArr

* `cap(切片名)` 统计切片容量
* 使用copy()内置函数拷贝切片
    * `copy(a, b)` 将b拷贝到a
        ```go
        var a []int = []int{1, 2, 3, 4, 5}
        var b []int = make([]int, 1)
        copy(b, a)
        fmt.Println(a) //[1 2 3 4 5]
        fmt.Println(b) //[1]

        b[0] = 33
        fmt.Println(a) //[1 2 3 4 5]
        fmt.Println(b) //[1]
        ```
    * 两个切片的数据空间是独立的，互不影响
* 切片做为参数传递
    ```go
    func main() {
        var a []int = []int{1, 2, 3, 4, 5}
        fmt.Println(a) //[1 2 3 4 5]
        test(a)
        fmt.Println(a) //[333 2 3 4 5]
    }

    func test(s []int) {
        fmt.Println(s) //[1 2 3 4 5]
        s[0] = 333
        fmt.Println(s) //[333 2 3 4 5]
    }
    ```
    * 传递切片的指针，修改元素时，会直接修改底层数组的元素
* string和slice
    * string底层是一个byte数组，所以string可以进行切片
        ```go
        a := "abcdef"
        b := a[2:4]
        fmt.Println(a) //abcdef
        fmt.Println(b) //cd
        ```
    * string是不可变的，所以不能通过切片来修改字符串


# map
[top](#catalog)
* 基本语法：`var map 变量名 map[keytype]valuetype`
* map中的数据默认是无序的
* key的类型：bool、数字、string、指针、channel，以及包含这几个类型的接口、结构体、数组
	* 通常key为int、string
	* 不能使用slice、map、function，因为这些类型无法使用`==`来判断
* value的类型，与key基本相同
	* 通常使用：数字，string，map，**struct**
* <label style="color:red">map声明不会分配内存，需要使用`make(map类型，size)`初始化，分配内存后才能使用</label>
	* make初始创建的map取决于size，但是产生的map长度为0；如果省略size，会自动分配一个小的起始大小
* 使用实例
	* 声明，分配内存，赋值 
		```go
		var a map[string]string
		a = make(map[string]string, 10)
		a["a"] = "aaa"
		a["b"] = "bbb"
		a["c"] = "ccc"
		```
	* 通过make创建对象，再赋值
		```go
		a := make(map[string]string, 10)
		a["a"] = "aaa"
		a["b"] = "bbb"
		a["c"] = "ccc"
		fmt.Println(a)
		```
	* 直接创建对象并初始化
		```go
		a := map[string]string{
			"a":"aaa",
			"b":"bbb",
			"c":"ccc",
		}
		fmt.Println(a)
		```
* **map的容量满了后，会自动扩容，即map可以动态增长**
* map是引用类型，如果作为函数参数，在函数中修改map会直接影响原来的map
* map基本操作
	* 更新/添加：`a[key] = value`，如果key存在，是更新；如果不存在，是添加
	* 删除:`delete(map, "key")`
		* 如果key存在，就删除key-value；如果key不存在，则不操作，也不报错
		* 如果map是nil则不操作，也不报错
	* 清空map：go没有清空map的方法，可以
		* 遍历key，逐个删除
		* `map=make(...)` 创建一个新的对象，让原来的对象成为垃圾，被gc回收
	* 查找：`val, has ：= a["key"]`，`val：= a["key"]`
		* 返回value和一个bool值，bool值表示key是否存在。
		* 如果bool是false，则val是空值；
		* 如果直接赋值给一个变量，则只返回value
		* 如果valuetype是string，判断时不能用nil，如果是引用类型可以使用nil比较
* map遍历
	* 因为map内部是无序的,所以每次遍历的结果可能不一样
	* for-range遍历
		```go
		a := map[string]string{
			"a":"aaa",
			"b":"bbb",
			"c":"ccc",
		}
		
		for k,v := range a{
			fmt.Printf("a[%v]=%v\n", k, v)
		}
		```
* 获取map长度：`len(map对象)`
	* 长度等于实际有效的key-value个数,不等于make分配的size
* map切片
	* slice of map,**使用map切片后,map个数就可以动态变化**
	* 可以理解为数据类型是map的动态数组
	* 实例
		```go
		var a []map[string]string
		a = make([]map[string]string, 5) //先构造切片的内存空间,
		
		if a[0] == nil{
			//因为map是引用类型,所以make底层的数组中保存的是map的地址,实际使用每个map元素时,需要给map构造内存空间,让指针指向该内存空间
			a[0] = make(map[string]string, 2)
			a[0]["name"] = "aaa"
			a[0]["age"] = "555"
		}
		
		if a[1] == nil{
			a[1] = make(map[string]string, 2)
			a[1]["name"] = "bbb"
			a[1]["age"] = "334"
		}
		
		fmt.Println(a)
		
		b := map[string]string{
			"name":"ccc",
			"age":"666",
		}
		a = append(a, b) // 向切片中添加map对象
		
		fmt.Println(a)
		```
* map排序
	* go中没有直接给map排序的方法
	* 一般的map排序流程: 获取key-->将key排序-->遍历有序key来访问map对象
		```go
		a := map[int]int{
			10:11,
			9:43,
			30:54,
			25:33,
		}
		
		var keys []int
		for k,_ :=range a{
			keys = append(keys, k)
		}
		fmt.Println(keys)
		sort.Ints(keys)
		fmt.Println(keys)
		
		for _, k := range keys{
			fmt.Printf("a[%d]=%d\n", k, a[k])
		}
		```
		
# 排序和查找
## 排序
[top](#catalog)
* 内部排序:将数据加载到内存中进行排序
	* 包括:交换式排序法,选择式排序法,插入式排序法
* 外部排序法:数据量过大,无法全部加载到内存中,需要借助外部存储进行排序
	* 包括:合并排序法,直接合并排序法
* 冒泡排序
	```go
	func main() {
		var a = [...]int{55,33,77,11,22,33,44}
		fmt.Println(a)
		method(&a)
		fmt.Println(a)
	}

	func method(arr *[7]int) {
		var temp int
		for i :=0; i<7-1; i++{ // 比较到最后会剩下第一个位置不需要比较,所以整体的循环次数是:数组长度-1
			for j := 0; j<7-1-i; j++{ //每次从index=0开始比较, 将最后一个位置空出来,这样有空间进行交换
				if (*arr)[j] > (*arr)[j+1]{
					temp = (*arr)[j]
					(*arr)[j] = (*arr)[j+1]
					(*arr)[j+1] = temp
				}
			}
		}
	}
	```
## 查找
[top](#catalog)
* go中常用的两种查找
	* 顺序查找
	* 二分查找(需要数组有序)
* 二分查找
	```go
	func main() {
		var a = [...]int{1,2,3,4,5,6}
		halfFind(&a, 0, len(a)-1, 6)
	}

	func halfFind(arr *[6]int, start int, end int, val int){
		if (start > end){
			fmt.Println("can not find")
			return
		}
		
		var middle = (start + end) / 2
		
		if (val < (*arr)[middle]){
			halfFind(arr, start, middle-1, val)
		} else if (val > (*arr)[middle]){
			halfFind(arr, middle+1, end, val)
		} else{
			fmt.Println("find, index=", middle)
			return
		}
	}
	```

# OOP
* go没有类，go的struct与其他语言的类有相同的地位，即go用struct来实现OOP的特性
* go的面向对象编程去掉了：继承、方法重载、构造函数、析构函数、隐藏的this指针等等

## 结构体
[top](#catalog)
* 结构体是自定义的数据类型
* `struct{}`代表**空结构体类型**
	* 空结构体类型的变量是不占用没存的
	* 所有该类型的变量都拥有相同的内存地址
	* 可以用作传递信号的通道的元素
* 声明结构体
	```go
	type 自定义结构体名 struct{
		字段1 type
		字段2 type
		字段3 type
		...
	}
	
	type Student struct{
		Name, add string //如果类型相同可以同时定义
		Age int
		Score float32
		
	}
	```
* 创建结构体变量
    1. 直接声明：`var a Student`
    2. 使用`{}`创建，创建时可以指定字段的值
        ```go
        type Student struct{
            Name string
            Age int
        }
        func main() {
            var a = Student{Name:"aaa", Age:18}
            fmt.Println(a) //{aaa 18}
            
            var b = Student{}
            fmt.Println(b) //{ 0}
        }
        ```
    3. `var a *Student = new(Student)`，new返回一个指针
        ```go
        type Student struct{
            Name string
            Age int
        }
        func main() {
            var a *Student = new(Student)
            (*a).Name = "aaa"
            a.Age = 16 //底层自动调整为(*a).Age
            fmt.Println(*a) //{aaa 16}
        }
        ```
    4. `var a *Student = &Student{...}`
        ```go
        type Student struct{
            Name string
            Age int
        }
        func main() {
            var a = Student{Name:"aaa", Age:18}
            fmt.Println(a) //{aaa 18}
            
            var b = Student{}
            fmt.Println(b) //{ 0}
        }
        ```
    5. 直接按照位置设定个字段的值，数组类型必须和字段的类型相同
        ```go
        type Student struct{
            Name string
            Age int
        }
        func main() {
            var a = Student{"aaa", 18}
            fmt.Println(a) //{aaa 18}
        }
        ```
			
* 字段
	* 字段是结构体的组成部分
	* 字段可以是：基本数据类型，数组，引用类型
	* 创建结构体变量后，如果没有给字段赋值，则字段使用默认的0值(数值=0，string=""，bool=false，引用=nil{指针，slice，map等，表示未分配空间})
	* 对于引用类型的字段，使用前一定要分配空间(如使用make())
* <label style="color:red">使用字段时的隐藏处理</label>
	* go底层做了一个简化，即可以使用`结构体对象.字段`，也可以使用`结构体指针.字段`
	* `a.Name`，对于这样的使用方法，底层会进行修改该：`(*a).Name`，即自动加上取值运算
	* 对于结构体变量指针，直接使用该对象时，仍然需要使用取值处理：`*a`
* **结构体是值类型，用结构体对象进行赋值时，默认使用值拷贝，不会互相影响**
	```go
	type Student struct{
		Name string
		Age int
	}
	func main() {
		var a Student
		a.Name = "aaa"
		a.Age = 16
		fmt.Println(a) //{aaa 16}
		
		b := a
		b.Name = "bbb" //b是值拷贝，修改b的字段不会影响a
		fmt.Println(a) //{aaa 16}
		fmt.Println(b) //{bbb 16}
	}
	```
* 结构体在内存中的结构类似于数组,内部的字段顺序排列且连续
	```go
	type Point struct{
		x int
		y int
	}
	type Rect struct{
		leftUp, rightDown Point
	}
	type Rect2 struct{
		leftUp, rightDown *Point
	}
	func main() {
		r1 := Rect{Point{1,2}, Point{3,4}}
		//0xc21000a020,0xc21000a028,0xc21000a030,0xc21000a038  结构体字段占用的内存空间是连续的，结构体整体上也是连续的
		fmt.Printf("%p,%p,%p,%p\n", &r1.leftUp.x, &r1.leftUp.y, &r1.rightDown.x, &r1.rightDown.y)
		//0xc21000a020,0xc21000a030 
		fmt.Printf("%p,%p\n", &r1.leftUp, &r1.rightDown)
		
		r2 := Rect2{&Point{1,2}, &Point{3,4}}
		//0xc21001e160,0xc21001e168,0xc21001e170,0xc21001e178
		fmt.Printf("%p,%p,%p,%p\n", &r2.leftUp.x, &r2.leftUp.y, &r2.rightDown.x, &r2.rightDown.y)
		//0xc21001e150,0xc21001e158 如果结构体中的字段是指针，指针指向的内存空间不一定连续，但是保存指针的内存空间一定是连续的
		fmt.Printf("%p,%p\n", &r2.leftUp, &r2.rightDown)
	}
	```	
* 结构体作为自定义类型，和其他类型进行转换时，必须要有相同的字段，包括字段的名字、个数、类型
	```go
	type A struct{
		Num int
	}
	type B struct{
		Num int
	}
	func main() {
		var a = A{Num:2}
		var b B
		b = B(a)
		fmt.Println(a) //{2}
		fmt.Printf("%T\n", a) //main.A
		fmt.Println(b) //{2}
		fmt.Printf("%T\n", b) //main.B
	}
	```
* 如果对结构体(或其他数据类型)，使用type进行重定义，go会认为是新的类型，但是**新类型和旧类型之间可以进行强转**
	```go
	type A struct{
		Num int
	}

	type X A
	func main() {
		var a = A{Num:2}
		var b X
		b = X(a)
		fmt.Println(b)
		
		var c A
		var d = X{Num:3}
		c = A(d)
		fmt.Println(c)
	}
	```
* 在字段上可以添加tag，tag可以通过反射机制获取，常见的使用场景是序列化和反序列化
	```go
    import (
        "encoding/json"
        "fmt"
    )
	type Student struct{
		Name string `json:"name"`
		Age int `json:"age"`
	}
		
	func main() {
		a := Student{Name:"aaa", Age:16}
		fmt.Println(a) //{aaa 16}
		jsonStr, err := json.Marshal(a)
		if err != nil {
			fmt.Println("this is err")
        }

        //{"name":"aaa","age":16}
        //解析时用的时tag
		fmt.Println(string(jsonStr)) 
	}
	```
	
## 方法
[top](#catalog)
* 在go中，**方法是和指定数据类型绑定的**，因此自定义类型可以有方法，不仅仅是struct
* 方法的声明和调用
	```go
	type A struct{
		Num int
	}
	
	// (a A) 表示A结构体和方法的绑定
	func (a A) test(){ //表示A结构体有一方法，方法名为test
		fmt.Printf(a.Num) // 在方法中调用结构体对象的字段
	}
	
	func main() {
		a := A{Num:123}
		a.test() //输出：123
	}
	```
	* test 方法和 A结构体绑定
	* test()只能通过A的对象来调用
	* `func (a A) test(){}` 中的p是调用方法的A结构体变量，p是这个变量的副本
* 方法的调用和传参原理
	* 调用和传参机制和函数基本相同
	* 方法调用时，会将调用方法的对象也**当作实参传递给方法**
    * 因为结构体是值类型，调用对象作为参数传递时，会给方法传递对象的值拷贝
    * 如果调用方法的对象是值类型，则进行值拷贝；如果对象时引用类型，则进行地址拷贝
        * **具体是值拷贝还是地址拷贝，主要看和方法绑定的类型:(s Student)值绑定，(s *Student)地址绑定**
    * 在方法中如果需要修改结构体变量的值，可以通过结构体指针的方式来处理
* 访问权限和函数相同，首字母小些只能在本包访问；首字母大写，可以在本包和其他包使用
* 如果一个类型实现了`String()`，使用`fmt.Println`默认会调用这个变量的`String()`
    ```go
    type Student struct {
        Name string
        Age  int
    }

    func (s *Student) String() string {
        str := fmt.Sprintf("Name=%v Age=%v", s.Name, s.Age)
        return str
    }

    func main() {
        a := Student{Name: "dfs", Age: 18}
        // 调用时需要使用指针
        fmt.Println(&a) // Name=dfs Age=18

        //如果是func (s Student) String() string {
        //可以直接使用：fmt.Println(a)
    }
    ```
## 工厂模式
[top](#catalog)
* go的结构体没有构造函数，可以使用工厂模式来解决这个问题
* 如果包中的结构体首字母小写，则不能直接使用可以使用工厂模式解决
* 实例
    ```go
    //model.go
    package model

    type student struct {
        Name string
        Age  int
    }

    func NewStudent(name string, age int) *student {
        return &student{Name: "ddd", Age: 22}
    }
    ```
    ```go
    //main.go
    package main

    import (
        "fmt"
        "study/model"
    )

    func main() {
        // a := otherpkg.Student{Name: "aaa", Age: 20}
        a := model.NewStudent("aaa", 20)
        fmt.Println(*a)
    }

    ```

## 三大特性
### 封装
[top](#catalog)
* 将结构体、字段的首字母小写
* 在结构体所在包内提供一个工厂函数，且首字母大写，通过该函数来构造结构体对象
* 添加get/set方法来对属性赋值
### 继承
[top](#catalog)
* 继承的方法，结构体中需要复用的部分使用**嵌套匿名结构体**
    ```go
    type Goods struct {
        Name string
        Price int
    }
    type Book struct {
        Goods //嵌套匿名结构体
        Writer string
    }
    ```
* 字段或方法的查找方法
    1. 结构体内部查找
    2. 如果在1中未找到，则在匿名结构体中查找
    3. 如果在多个匿名结构体中有同名的字段或方法，需要通过匿名结构体来区分，如:`对象.匿名结构体X.字段名`
    4. 如果没找到则异常
* 创建时，也可以直接指定各个匿名结构体字段的值
* 匿名字段也可以使用基本数据类型，但是**每个基本数据类型的匿名字段只能有1个，如果有多个则必须指定字段名**
    ```go
    type A struct{
        int
        n int
    }
    ```
* 如果一个struct中**嵌套了多个匿名结构体，则相当于实现了多重继承**
* 实例
    ```go
    type Goods struct {
        Name  string
        Price float64
    }
    type Brand struct {
        Name    string
        Address string
    }

    type TV struct {
        Goods
        Brand
    }

    type TV2 struct {
        *Goods
        *Brand
    }

    func main() {
        tv := TV{Goods{Name: "tvGoodName", Price: 111}, Brand{Name: "tvBrandName", Address: "tvBrandAddr"}}
        //{{tvGoodName 111} {tvBrandName tvBrandAddr}}
        fmt.Println(tv)

        var tv2 TV
        tv2.Goods.Name = "tv2GoodName"
        tv2.Price = 222
        tv2.Brand.Name = "tv2BrandName"
        tv2.Address = "tv2BrandAddr"
        //{{tv2GoodName 222} {tv2BrandName tv2BrandAddr}}
        fmt.Println(tv2)

        tv3 := TV2{
            &Goods{Name: "tv3GoodName", Price: 444},
            &Brand{Name: "tv3BrandName", Address: "tv3BrandAddr"},
        }
        //{tv3GoodName 444} {tv3BrandName tv3BrandAddr}
        fmt.Println(*tv3.Goods, *tv3.Brand)
    }
    ```
### 接口
[top](#catalog)
* go中的多态特性通过接口来实现
* 基本语法
    ```go
    type 接口名 interface{
        method1(参数列表) (返回值列表)
        method2(参数列表) (返回值列表)
        ...
    }

    func (t 自定义类型)method1(参数列表) (返回值列表){...}
    func (t 自定义类型)method2(参数列表) (返回值列表){...}
    ```
    ```go
    type Usb interface {
        Start()
        Stop()
    }

    type Phone struct{}

    func (p Phone) Start() {
        fmt.Println("Phone is start")
    }
    func (p Phone) Stop() {
        fmt.Println("Phone is stop")
    }

    type Camera struct{}

    func (p Camera) Start() {
        fmt.Println("Camera is start")
    }
    func (p Camera) Stop() {
        fmt.Println("Camera is stop")
    }

    type Computer struct{}

    func (c Computer) Work(u Usb) {
        u.Start()
        u.Stop()
    }

    func main() {
        comp := Computer{}

		// 如果绑定方法和类型时使用指针：func (p &Phone) Stop()
		// 应该声明一个对象指针：phone := &Phone{}
		// 因为和方法绑定的是结构体指针
        phone := Phone{}
        comp.Work(phone)

        camera := Camera{}
        comp.Work(camera)

        //接口直接指向实现了该接口的对象
        var usb Usb = camera
        comp.Work(usb)
    }
    ```
* interface类型**默认是一个指针(引用类型)**，如果**interface没有初始化就使用**，将会**输出nil**
* interface 类型可以定义一组方法，但是**不需要实现**
* **interface中不能包含任何变量**
* 只要实现了接口中的方法，就相当于实现了这个接口，一个自定义类型可以实现多个接口
	```go
	type AInterface interface{
		methodA()
	}
	type BInterface interface{
		methodB()
	}
	type Test struct{
	}

	func (t Test) methodA() {
		fmt.Println("this is Test MethodA")
	}

	func (t Test) methodB() {
		fmt.Println("this is Test MethodB")
	}
		
	func main() {
		a := Test{}
		var aif AInterface = a
		var bif BInterface = a
		
		aif.methodA()
		bif.methodB()
	}
	```
* 接口A中可以继承多个别的接口B、C，即在接口A中嵌套其他接口B、C。实现的时候，ABC中的方法都必须实现
	* 接口继承的时候，接口中的**方法名不能重复(只看方法名，不看参数列表和返回值列表)**
		```go
		type A interface{
			Method01()
			Method02()
		}

		type B interface{
			Method01()
			Method03()
		}

		type C interface {
			A
			B
		}
		```
* <label style="color:red">空接口:</label>`interface{}`
    * 空接口内没有任何方法
    * 所有类型都实现了空接口
    * **可以把任何一个变量赋值个空接口**
        ```go
		type A interface{}
		
		func main() {
			obj := Object{}
			var a A = obj
		}
        ```
* 接口不能实例化对象，但是**可以指向一个实现了该接口的自定义类型变量**
	* 指向实现接口的对象后，只能调用该接口内的方法，不能调用其他的方法和字段
* **只要是自定义数据类型，就可以实现接口，不限于结构体**
    ```go
    type Integer int
    
    type Interface interface {
        Run()
    }

    func (i Integer) Run() {
        fmt.Println(i)
    }
    func main(){
        var integer Integer = 10
        var interf Interface = integer
        interf.Run()
    }
    ```
* 实例：结构体切片排序
	```go
	type Person struct{
		Name string
		Age int
	}

	type PList []Person

	func (pl PList) Len() int{
		return len(pl)
	}
	
	//按从小到大排序
	func (pl PList) Less(i, j int) bool {
		return pl[i].Age < pl[j].Age
	}

	func (pl PList) Swap(i, j int) {
		pl[i], pl[j] = pl[j], pl[i]
	}

	func main() {
		var intslice = []int{3,2,5,1,4}
		fmt.Println(intslice) //[3 2 5 1 4]
		sort.Ints(intslice)
		fmt.Println(intslice) //[1 2 3 4 5]
		
		var pl = PList{
			Person{Name:"a", Age:16},
			Person{Name:"b", Age:10},
			Person{Name:"c", Age:18},
			Person{Name:"d", Age:9},
		}
		
		fmt.Println(pl) //[{a 16} {b 10} {c 18} {d 9}]
		sort.Sort(pl)
		fmt.Println(pl) //[{d 9} {b 10} {a 16} {c 18}]
	}
	```
* 继承与接口：继承用于解决了代码的复用性和可维护性；接口用于设计规范，让自定义类去实现这些方法
	
### 多态
* go 的多态通过接口实现
    ```go
    type Usb interface {
        Start()
        Stop()
    }

    type Phone struct{}

    func (p Phone) Start() {
        fmt.Println("Phone is start")
    }
    func (p Phone) Stop() {
        fmt.Println("Phone is stop")
    }

    type Camera struct{}

    func (p Camera) Start() {
        fmt.Println("Camera is start")
    }
    func (p Camera) Stop() {
        fmt.Println("Camera is stop")
    }

    type Computer struct{}

	//参数()u Usb)会根据传入的实参，来判断对象的类型
    func (c Computer) Work(u Usb) {
        u.Start()
        u.Stop()
    }

    func main() {
        comp := Computer{}
		
        phone := Phone{}
        comp.Work(phone)

        camera := Camera{}
        comp.Work(camera)

        var usb Usb = camera
        comp.Work(usb)
    }
    ```
* 多态的两种形式
	* 多态参数
	* 多态数组:数组中可以存放实现了接口方法的结构体对象
		```go
		var usblist [3]Usb
		usblist[0] = Camera{}
		usblist[1] = Camera{}
		usblist[2] = Phone{}
		```
* 类型断言
	* 接口是一般类型，不知道具体类型。如果需要转换成具体类型，就需要使用类型断言
	* 使用方法：`新对象 = 接口对象.(数据类型)`
		* 如果类型不匹配，会报panic。使用类型断言时，需要确保接口对象指向的是断言的数据类型
			```go
			type A struct{
				x int
				y int
			}

			func main(){
				var a interface{}
				b := A{x:1,y:2}
				a = b
				var c A
				c = a.(A)
				fmt.Println(c)
			}
			```
		* 带检测的类型断言:异常时返回false
			```go
			var a interface{}
			var b float64 = 11
			a = b
			// var c float64
			c, ok := a.(float64)
			if ok {
				fmt.Println(c)
			} else{
				fmt.Println("error")
			}
			```
* 判断参数类型：`x.(type)`,需要配合switch使用
	```go
	switch x.(type){
		case int：
			...
		case float:
			...
		...
	}
	```

# 文件操作
* os.File 封装了所有文件相关操作，File是一个结构体
* 打开和关闭文件
    * 打开文件(os包)：`func Open(name string)(file *File, err error)`
        * 返回一个File的指针/句柄
    * 关闭文件：`func (f *File) Close()error`
    * 实例：
        ```go
        func main() {
            var p string = "./a.txt"
            f, err := os.Open(p)
            if err != nil {
                fmt.Println("this is open error = ", err)
            }
            defer func() {
                err := f.Close()
                if err != nil {
                    fmt.Println("this is close error = ", err)
                }
            }()

            fmt.Printf("file= %v\n", f)
        }
        ```
* 文件读取
    * 通过`bufio.NewReader(f)`来读取文件的每一行内容
        ```go
        func main() {
            var p string = "./a.txt"
            f, err := os.Open(p)
            if err != nil {
                fmt.Println("this is open error = ", err)
                return
            }
            defer func() {
                err := f.Close()
                if err != nil {
                    fmt.Println("this is close error = ", err)
                }
            }()

            //创建缓冲区
            const (
                defaultBufSize = 4096 //默认的缓冲区为 4096
            )

            // 创建一个带缓冲的*Reader
            reader := bufio.NewReader(f)
            for {
                str, err := reader.ReadString('\n') //读到换行符就结束
                if err == io.EOF {
                    break
                }
                fmt.Print(str)
            }
        }
        ```
    * 使用`ioutil.ReadFile(路径)`来将文件一次性读取到内存中，适用于文件不大的情况。返回的是字节数组`[]byte`
        * 只是读取，没有Open文件，也不需要Close，这两个操作都在该方法的内部
        ```go
        func main() {
            var p string = "/Users/liujinsuo/gosys/others/a.txt"
            content, err := ioutil.ReadFile(p)
            if err != nil {
                fmt.Println("this is error ", err)
                return
            }
            fmt.Println(string(content))
        }
        ```
* 写文件
    * `os.OpenFile`函数
        * `func OpenFile(name string, flag int, perm FileMode)(file *File, err error)`
            * flag 文件打开模式(可以组合)

                |id|权限|
                |-|-|
                |os.O_RDONLY|只读模式打开文件|
                |os.O_WRONLY|只写模式打开文件|
                |os.O_RDWR|读写模式打开文件|
                |os.O_APPEND|写操作是将数据附加到文件尾部|
                |os.O_CREATE|如果不存在则创建一个新文件|
                |os.O_EXCL|和O_CREATE配合使用，文件必须不存在|
                |os.O_SYNC|打开文件用于同步IO|
                |os.O_TRUNC|如果可能，打开是清空文件|
            
            * perm 权限控制(linux)
                * r=4、w=2、x=1
        * 一个更一般文件打开函数，使用指定的选项、模式打开文件
        * 如果操作成功返回一个可以操作的IO对象；如果异常，返回`*PathError`
    * `bufio.NewWriter(f)`创建带缓存的*Writer
        ```go
        func main() {
            var p string = "./a.txt"
            f, err := os.OpenFile(p, os.O_WRONLY|os.O_CREATE, 0666)
            if err != nil {
                fmt.Println("this is error ", err)
                return
            }
            defer f.Close()
            str := "aaaaaaaa\n"
            //使用带缓存的*Writer
            writer := bufio.NewWriter(f)
            for i := 0; i < 5; i++ {
                writer.WriteString(str)// 写入缓存
            }
            //将缓冲中的数据写入文件
            writer.Flush()
        }
        ```
    * `ioutil.WriterFile` 直接将数据写入文件，**文件必须存在**
        ```go
        func main() {
            var p1 string = "./a.txt"
            var p2 string = "./b.txt"
            data, err := ioutil.ReadFile(p1)
            if err != nil {
                fmt.Println("this is read err ", err)
                return
            }

            err = ioutil.WriteFile(p2, data, 0666)
            if err != nil {
                fmt.Println("this is writer err ", err)
                return
            }
        }
        ``` 
* 拷贝文件(二进制流数据) io包：`func Copy(dst Writer, src Reader)(written int64, err error)`
	```go
	func main(){
		var p1 = `...`
		var p2 = `...`
		_, err := CopyFile(p2, p1)
		if err != nil{
			fmt.Println("this is copy error", err)
		}
	}
	func CopyBiFile(dst string, src string)(written int64, err error){
		sf, err := os.Open(src)
		if err != nil {
			fmt.Println("this is src open err", err)
			return
		}
		defer sf.Close()
		
		sfr := bufio.NewReader(sf)
		
		df, err := os.OpenFile(dst, os.O_WRONLY|os.O_CREATE, 0666)
		if err != nil {
			fmt.Println("this is dst open err", err)
			return
		}
		defer df.Close()
		
		dfw := bufio.NewWriter(df)
		
		return io.Copy(dfw, sfr)
	}
	```
* 判断文件是否存在
    * `os.Stat()` 根据函数返回的错误值进行判断
        * nil，文件/目录**存在**
        * 对错误类型使用`os.IsNotExist()`判读为true，则文件/目录不存在
        * 如果返回的错误为其他类型，则不确定文件是否存在
        ```go
        func PathExist(path string) (bool, error) {
            _, err := os.Stat(path)
            if err == nil {
                return true, nil
            } else if os.IsNotExist(err) {
                return false, nil
            }

            return false, err
        }
        ```

# 命令行参数解析
[top](#catalog)
* 原生方式：`os.Args`用来存储所有的命令行参数，是一个**string切片**
	```go
	func main(){
		fmt.Println(len(os.Args))
		
		for _,v := range os.Args{
			fmt.Println(v)
		}
	}
	```
* flag包解析命令行参数
	* 使用`flag.XxxxVar(数据指针， flag名， 默认值， 提示信息)`，将参数绑定到变量
	```go
	go run main.go -u xxxx -p xxxx -h xxxx -port xxxx
	func main(){
		var user string
		var pwd string
		var host string
		var port int
		
		flag.StringVar(&user, "u", "", "user name,default is null")
		flag.StringVar(&pwd, "p", "", "pwd name,default is null")
		flag.StringVar(&host, "h", "localhost", "host,default is localhost")
		flag.IntVar(&port, "port", 8888, "port default is 8888")
		
		flag.Parse()
		fmt.Println(user)
		fmt.Println(pwd)
		fmt.Println(host)
		fmt.Println(port)
	}
	```
	
# json
[top](#catalog)
* json序列化:`json.Marshal(a)`，在序列化结构体时需要传递结构体指针：`json.Marshal(&a)`
* json反序列化：`json.Unmarshal([]byte(序列化字符串)， &绑定转换对象)`
	* 反序列化时，只需要声明绑定对象的类型，不需要make来分配空间，Unmarshal内已经封装了make操作

# 单元测试
[top](#catalog)
* go的轻量级测试框架：testing
* 测试用例文件名必须以：`_test.go`结尾
* 测试用例函数必须以**Test**开头，一般是Test+被测试函数名
* `TestXXXX(t *testing.T)`，测试方法的形参必须是：t *testing.T
* 测试指令：`go test -v`
	* 测试单个文件时，需要指定文件:`go test -v XXXX.go`
	* 测试单个方法：
* 出现错误时，可以使用`t.Fatalf`来**格式化输出错误信息，并推出程序**
* `t.Logf`可以用来输出相应的日志

# goroutine和channel
## goroutine
[top](#catalog)
* 一个进程下可以有多个线程，且至少有一个线程
* Go主线程：Go的主线程可以理解成进程(也直接被称为线程)； 一个Go线程上，可以启动**多个协程**
	* <label style="color:red">如果主线程退出了，即使协程还没有执行完，也会退出</label>
		* 如果在主线程中启动了过多的协程，而主线程提前结束，会导致一部分协程终止、执行的结果不正确
	* **主线程是一个物理线程，直接作用在CPU上，是重量级的，非常耗费CPU资源**
	* **协程**从线程开启，是轻量级的线程，**是逻辑态的，对资源的消耗比较小**
	
* Go协程的特点
	* 有独立的栈空间
	* 共享程序堆空间
	* 调度由用户控制
	* **协程是轻量级的线程(编译器会进行优化)**
	* go可以开启上万个协程，其他语言的并发机制是基于线程的，开启过多的线程，资源耗费大
* goroutine实例
	```go
	//执行流程
	//1.主线程启动
	//2.go test()开启协程
	//3.主线程、协程同时执行(几乎是同时，但是实际上会有资源的竞争)
	//4.主线程结束
	// 每隔1秒输出：test() hello
	func test(){
		for i := 1; i<=10; i++{
			fmt.Println("test() hello" ,i)
			time.Sleep(time.Second)
		}
	}

	// 每隔1秒输出：main() hello
	func main(){
		go test() //开启了一个协程
		
		for i := 1; i<=10; i++{
			fmt.Println("main() hello" ,i)
			time.Sleep(time.Second)
		}
	}
	```
* goroutine的调度模型
	* MPG模型
		* M:操作系统的主线程(是物理线程)
		* P:协程执行需要的上下文(运行时的资源或操作状态)
		* G:协程
* 设置GoLang运行的cpu数
	* `runtime.NumCPU()`，返回本地的逻辑CPU数
	* `runtime.GOMAXPROCS()`，设置可同时执行的最大CPU数
		* Go1.8之后，默认让程序运行在多个核上，可以不用设置
		* Go1.8之前，需要手动设置，来高效利用CPU
	```go
	func main(){
		num := runtime.NumCPU()
		fmt.Println(num)
		runtime.GOMAXPROCS(num-1)
		fmt.Println("end")
	}
	```

## goroutine通信问题
[top](#catalog)
* 使用goroutine计算1-200每个数的阶乘
	```go
	var (
		result=make(map[int]int, 10)
	)
	
	func test(n int){
		res := 1
		for i:=1; i<=n; i++{
			res *= i
		}
		result[n] = res
	}

	func main(){
		for i:=1; i<=200; i++{
			go test(i)
		}

		time.Sleep(time.Second * 10)
		for i,v := range result{
			fmt.Printf("result[%d]=%d\n", i, v)
		}
	}
	```
* 如果只使用goroutine
	1. 肯能会产生数据竞争(数据安全问题)
	2. 需要等待所有的协程停止，但是无法准确的预估停止时间
* goroutine通信-全局变量的互斥锁，使用前加锁、使用后解锁
	* 互斥锁的问题
		1. 无法确定等待时间
		2. 通过加锁同步来实现通讯，不利于多个协程对全局变量的读写操作(需要分析在哪些位置加锁)
	```go
	var (
		result=make(map[int]int, 10)
		lock sync.Mutex //声明互斥锁
	)

	func test(n int){
		res := 1
		for i:=1; i<=n; i++{
			res += i
		}
		lock.Lock()
		result[n] = res
		lock.Unlock()
	}

	func main(){
		for i:=1; i<=200; i++{
			go test(i)
		}

		time.Sleep(time.Second * 10)
		
		lock.Lock()
		for i,v := range result{
			fmt.Printf("result[%d]=%d\n", i, v)
		}
		lock.Unlock()
	}
	```
* goroutine通信-channel
	* channel的本质是一个队列，数据是先进先出
	* channel本身是线程安全的，多goroutine操作同一个channel时不会发生数据竞争
	* channel是有类型的，一个string的channel只能存储string类型的数据
		* 存放各种类型的数据，可以声明为：`interface{}`，一般不推荐这样使用

# channel
* channel是引用类型
* channel中只能放指定类型的数据
* channel**必须初始化才能写入数据(make后才能使用)**
* 数据放满后，就不能再放数据了，会引发deadlock异常
* 在没有使用goroutine的情况下，如果channel的数据取完了，再取，就会报deadlock
* channel定义/声明：`var 变量名 chan 数据类型`，如：`var intChan chan int`声明了一个存放int数据的管道
* `变量名 = <- 管道`，`变量名 := <- 管道`，从管道中取数据
	* `<- 管道` 也可以只取数据，取出的数据直接丢弃
* `管道 <- 变量名`，向管道中写入数据
* channel 基本使用实例
	```go
	func main() {
		//创建一个可以存放3个int数据的管道
		var intChan chan int
		intChan = make(chan int, 3)
		fmt.Println(intChan)

		//向管道写入数据
		intChan <- 10
		num := 111
		intChan <- num

		//管道的长度和容量cap
		fmt.Println(len(intChan)) //2
		fmt.Println(cap(intChan)) //3

		//写入数据时不能超过管道的容量
		intChan <- 50
		// intChan <- 51 //all goroutines are asleep - deadlock!

		//从管道中取数据
		var num2 int
		num2 = <-intChan
		fmt.Println(num2) // 10

		num3 := <-intChan
		num4 := <-intChan
		fmt.Println(num3) //111
		fmt.Println(num4) //50

		// num5 := <-intChan //fatal error: all goroutines are asleep - deadlock
	}
	```
* channel的关闭
	* 使用内置函数close可以关闭channel，
	* channel关闭后，不能再向channel写数据，但是**可以从channel中读取数据**
	* 关闭后，取数据是可以进行检查：`v, ok := <- channel`，`ok==true`表示正常获取数据
	```go
	func main() {
		intChan := make(chan int, 3)
		intChan <- 10
		intChan <- 11
		close(intChan)
		//intChan <- 13 //panic: send on closed channel

		n1 := <-intChan
		fmt.Println(n1) //10
	}
	```

* channel的遍历，主要使用for-range
	* 遍历时，如果channel未关闭，会阻塞，（没有goroutine时，会引发deadlock错误）
		* 没有结束标识，读到channel为空时，还要进行读取，引发了deadlock错误
	* 遍历时，如果channel已关闭，会正常遍历数据。
	```go
	func main() {
		intChan := make(chan int, 100)
		//向管道输入100个数据
		for i := 0; i < 100; i++ {
			intChan <- i
		}

		//遍历
		close(intChan)
		for v := range intChan {
			fmt.Println(v)
		}
	}
	```
* 实例：读写管道然后退出
	```go
	func writeDate(intChan chan int) {
		for i := 1; i <= 50; i++ {
			intChan <- i
			fmt.Println("write:", i)

		}
		close(intChan)
	}

	func readData(intChan chan int, exitChan chan bool) {
		for {
			v, ok := <-intChan
			if !ok {
				break
			}
			fmt.Println("read:", v)
		}

		exitChan <- true
		close(exitChan)
	}

	func main() {
		intChan := make(chan int, 50) //引用类型，在各goroutine中共享
		exitChan := make(chan bool, 1)

		go writeDate(intChan)
		go readData(intChan, exitChan)

		for {
			_, ok := <-exitChan
			if !ok {
				break
			}
		}
	}
	```
* 阻塞
	* 如果只有程序向channel写数据，而没有程序从channel中读数据，当channel的容量满了，再写数据会引发deadlock异常，即**阻塞**
	* 如果写管道和读管道的频率不一致，不会阻塞，底层会控制读写频率
	
* 实例：取素数
	```go
	func putNum(intChan chan int) {
		for i := 1; i <= 80; i++ {
			intChan <- i
		}

		//放完数据后，关闭管道
		close(intChan)
	}

	func primeNum(intChan chan int, primeChan chan int, exitChan chan bool) {
		var num int
		var flag bool
		var ok bool
		for {
			//time.Sleep(time.Millisecond * 10)
			num, ok = <-intChan
			if !ok {
				break
			}

			flag = true
			//判读素数
			for i := 2; i < num; i++ {
				if num%i == 0 {
					flag = false
					break
				}
			}

			if flag {
				//将数据保存到管道
				primeChan <- num
			}
		}

		fmt.Println("goroutine exit")
		//有可能其他goroutine还在处理
		//或者当前协程速度过快，intChan中还没有放入数据，导致协程终止
		//向退出管道写入标识
		exitChan <- true
	}

	func main() {
		//产生数据的管道
		intChan := make(chan int, 1000)
		// 保存素数结果的管道
		primeChan := make(chan int, 2000)
		// 标识协程退出的管道
		exitChan := make(chan bool, 4)

		//启动协程，向intchan中放入1-8000
		go putNum(intChan)

		//开启4各协程，从intchan取出数据，并判断是否为素数
		//如果是素数则保存到primechan中
		for i := 0; i < 4; i++ {
			go primeNum(intChan, primeChan, exitChan)
		}

		//使用匿名函数启动该协程，来判断其他协程是否完成
		go func() {
			for i := 0; i < 4; i++ {
				<-exitChan
			}
			//4个协程都完成后，将素数管道关闭
			close(primeChan)
		}()

		//遍历素数管道
		for {
			res, ok := <-primeChan
			if !ok {
				break
			}
			fmt.Println("素数：", res)
		}

		fmt.Println("main end")
	}
	```

* channel可以声明为只读，或只写；默认情况下管道是可读可写的
	* 只读管道
		```go
		var ch2 <-chan int
		num2 := <-ch2
		```
	* 只写管道
		```go
		var ch chan<- int
		ch = make(chan int, 3)
		```
	* 可以用在方法的参数中，来防止误操作
	* 只读只写不代表管道的类型，只代表管道的属性，类型都是管道
* 使用select来解决阻塞
	```go
	func main() {
		intChan := make(chan int, 10)
		for i := 0; i < 10; i++ {
			intChan <- i
		}

		stringChan := make(chan string, 5)
		for i := 0; i < 5; i++ {
			stringChan <- fmt.Sprintf("%d", i)
		}

		//label:
		for {
			select {
			case v := <-intChan: //如果管道一直没有关闭，不会一值阻塞而deadlock
				fmt.Println("intChan", v)
			case v := <-stringChan:
				fmt.Println("stringChan", v)
			default:
				fmt.Println("default")
				return //直接退出，在协程中是退出协程
				//break //break只能退出select 无法退出for
				//break label //不建议使用
			}
		}
	}
	```

* 使用recover，解决协程中出现的panic
	* 如果一个协程中出现的panic，如果没有捕获，就会造成程序崩溃
	* 使用recover捕获panic，是主线程不受影响可以继续运行
	```go
	func sayhello() {
		for i := 0; i < 10; i++ {
			time.Sleep(time.Second)
			fmt.Println("hello,world")
		}
	}

	func test() {
		defer func() {
			//捕获异常
			err := recover()
			if err != nil {
				fmt.Println("test is err")
			}
		}()
		var mymap map[int]string
		mymap[0] = "golang"
	}
	func main() {
		go sayhello()
		go test()

		for i := 0; i < 10; i++ {
			fmt.Println("this is main", i)
			time.Sleep(time.Second)
		}
	}
	```

# 反射
* 反射可以在运行时动态获取变量的各种信息，如变量的类型，类别
	* 对于结构体变量，可以获得结构体的字段和方法
* 通过反射可以修改变量的值，带用关联的方法
* 使用反射，需要`import "reflect"`
* 重要函数
	* `reflect.TypeOf(变量名)`，获取变量的类型，返回`reflect.Type`类型
	* `reflect.ValueOf(变量名)`，获取变量的值，返回`reflect.Value`类型，一个结构体
		* `reflect.Value`是一个结构体类型。通过`reflect.Value`，可以获取变量的很多信息
* 变量、`interface{}`、`reflect.Value`可以互相转换，**以`interface{}`为中介进行转换**
	* `interface{}`-->`reflect.Value`
		```go
		rVal := reflect.ValueOf(b)
		```
	* `reflect.Value`-->`interface{}`
		```go
		iVal := rVal.Interface()
		```
	* `interface{}`-->变量，使用类型断言
		```go
		v := iVal.(类型)
		```
* `reflect.Value.kind`，获取变量的类别，返回一个常量
	* Type和Kind的区别
		* Type和Kind可能相同，也可能不相同
			* `var a int` Type、Kind都是int
			* `var a Student` Type是Student，Kind是struct
* 使用反射来获取变量值：`reflect.ValueOf(x).XXX()`，使用时要求数据类型必须匹配，否则会引发异常
* 通过反射来修改变量的值
	* 需要使用对象的指针
	* 修改值时需要使用：`reflect.ValueOf(a).Elem()`来获取对象(等同于使用：`*指针变量`取得目标值)
	* 使用`reflect.ValueOf(a).Elem().SetXXX()`来修改变量的值
		* 如果变量不是指针或者未使用`Elem()`，会引发异常：`reflect.Value.SetXXX using unaddressable value`
* 处理Struct
	* 判断kind是否为结构体：`if reflect.ValueOf(x).Kind() == reflect.Struct`
	* `reflect.ValueOf(x).NumField()`获取结构体有几个字段
	* 遍历字段:
		* 通过索引获取字段：`reflect.ValueOf(x).Field(i)`，返回的结果仍然是：`reflect.Value`
		* 获取标签：`reflect.TypeOf(x).Field(i).Tag.Get(标签的key)`
			* Field 返回的是StructField(结构体)
			* 如果某个字段没有标签，则`Get()`的返回值为`""`
		```go
		val := reflect.ValueOf(x)
		for i:=0; i<.NumField(); i++{
			val.Field(i) // 
		}
		```
	* 调用方法
		* `reflect.ValueOf(x).NumMethod()`获取结构体有几个方法
		* `reflect.ValueOf(x).Method(index)`通过索引获取某个方法
			* 按照函数的名排序
		* `reflect.ValueOf(x).Method(index).Call(参数列表)`调用某个方法方法
			* 参数必须是`reflect.Value`类型的变量
			* `Call()`的返回值是`[]reflect.Value`切片

# tcp编程
* 简单服务器/客户端实例
	* 服务器监听8888端口
	* 可以和多个客户端创建链接
	* 链接成功后，客户端可以发送数据，并显示在终端上
	
	* server.go
		```go
		func process(conn net.Conn) {
			//循环接收客户端发送的数据
			defer conn.Close()

			for {
				// 创建一个新的切片
				buf := make([]byte, 1024)
				// 等待客户端通过conn发送信息
				// 如果客户端没有write[发送]，那么协程就阻塞在这里
				fmt.Println("server wait client info, ", conn.RemoteAddr().String())
				n, err := conn.Read(buf)
				// if err != nil {
				// 	fmt.Println("server read err= ", err)
				// 	return
				// }
				if err == io.EOF {
					fmt.Println("host exit")
					return
				}
				// 显示客户端发送的内容到服务器的终端
				fmt.Println(string(buf[:n]))
			}
		}

		func main() {
			fmt.Println("server start listen")
			//监听本地8888端口
			listen, err := net.Listen("tcp", "127.0.0.1:8888")
			if err != nil {
				fmt.Println("listen err:", err)
				return
			}
			defer listen.Close() //延时关闭

			//主线程负责：循环等待客户端连接
			//协程负责和客户端交互
			for {
				fmt.Println("server:wait Accept")
				conn, err := listen.Accept()
				if err != nil {
					fmt.Println("accpet() err=", err)
				} else {
					fmt.Println("accept() sub con = ", conn)
					fmt.Println("conn ip = ", conn.RemoteAddr().String())
				}
				go process(conn)
			}
		}

		```
	* client.go
		```go
		func main() {
			conn, err := net.Dial("tcp", "127.0.0.1:8888")
			if err != nil {
				fmt.Println("client dial err = ", err)
				return
			}

			//功能1:客户端可以发送单行数据，然后退出
			//获取输入
			reader := bufio.NewReader(os.Stdin)
			str, err := reader.ReadString('\n')
			if err != nil {
				fmt.Println("readString err = ", err)
			}
			//向服务器发送数据
			n, err := conn.Write([]byte(str))
			if err != nil {
				fmt.Println("conn.Write err=", err)
			}
			fmt.Println("client send byte count = ", n)
			// fmt.Println("client con = ", conn)
		}
		```

# 操作redis
[top](#catalog)
 * 连接redis
	```go
	import (
		"fmt"

		"github.com/garyburd/redigo/redis"
	)

	func main() {
		// 连接redis
		conn, err := redis.Dial("tcp", "127.0.0.1:6379")
		if err != nil {
			fmt.Println("conn error = ", err)
			return
		}
		defer conn.Close()
		fmt.Println("conn succ = ", conn)
	}
	```
* get/set接口
	```go
	func main() {
		// 链接redis
		conn, err := redis.Dial("tcp", "127.0.0.1:6379")
		if err != nil {
			fmt.Println("conn error = ", err)
			return
		}
		defer conn.Close()

		// fmt.Println("conn succ = ", conn)
		_, err = conn.Do("Set", "name", "tom")
		if err != nil {
			fmt.Println("set error = ", err)
			return
		}
		fmt.Println("set success")

		// 获取value并转换为string
		r, err := redis.String(conn.Do("Get", "name"))
		if err != nil {
			fmt.Println("get error = ", err)
			return
		}
		// 返回结果是interface{}
		// nameStr := r.(string)
		fmt.Println("get name = ", r)
	}
	```
* Hget/Hset
	```go
	func main() {
		// 链接redis
		conn, err := redis.Dial("tcp", "127.0.0.1:6379")
		if err != nil {
			fmt.Println("conn error = ", err)
			return
		}
		defer conn.Close()

		_, err = conn.Do("Hset", "user1", "name", "tom", "age", 16)
		if err != nil {
			fmt.Println("set error = ", err)
			return
		}
		fmt.Println("set success")

		r1, err := redis.String(conn.Do("HGet", "user1", "name"))
		if err != nil {
			fmt.Println("hset err = ", err)
			return
		}

		r2, err := redis.Int(conn.Do("HGet", "user1", "age"))
		if err != nil {
			fmt.Println("hset err = ", err)
			return
		}

		fmt.Println("r1 = ", r1)
		fmt.Println("r2 = ", r2)
	}
	```
* HMset/HMget
	```go
	func main() {
		// 链接redis
		conn, err := redis.Dial("tcp", "127.0.0.1:6379")
		if err != nil {
			fmt.Println("conn error = ", err)
			return
		}
		defer conn.Close()

		_, err = conn.Do("HMset", "user1", "name", "tom", "age", 16)
		if err != nil {
			fmt.Println("set error = ", err)
			return
		}
		fmt.Println("set success")

		r, err := redis.Strings(conn.Do("HMGet", "user1", "name", "age"))
		if err != nil {
			fmt.Println("hset err = ", err)
			return
		}

		fmt.Println(r)
	}
	```
* redis连接池
	```go
	var pool *redis.Pool

	func init() {
		pool = &redis.Pool{
			MaxIdle:     8,
			MaxActive:   0,
			IdleTimeout: 100,
			Dial: func() (redis.Conn, error) {
				return redis.Dial("tcp", "localhost:6379")
			},
		}
	}

	func main() {
		// 从pool中取出一个链接
		conn := pool.Get()
		defer conn.Close()

		//从pool中取出链接时，必须保证连接池打开状态
		pool.Close()
		conn = pool.Get() //redigo: get on closed pool

		_, err := conn.Do("Set", "name2", "hhhhh")
		if err != nil {
			fmt.Println("conn.Do err = ", err)
			return
		}

		r, err := redis.String(conn.Do("Get", "name2"))
		if err != nil {
			fmt.Println("con.Do err=", err)
		}
		fmt.Println("r=", r)
	}
	```

#？？？？
* make()默认创建的容量是多少
* append()如何对切片进行扩容
* string的底层
* 声明后，需要分配空间才能使用的类型
	* map ，make()分配空间
	* slice	

[top](#catalog)
[top](#catalog)
[top](#catalog)
[top](#catalog)

* 生成随机数
	```go
	rand.Seed(time.Now().UnixNano())
    n := rand.Intn(100)+1
	```
