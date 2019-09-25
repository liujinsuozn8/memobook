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
[top](#catalog)
## 值类型和引用类型
* 值类型(变量存储的是值)
    * 基本数据类型：int系列，float系列，bool，string
    * 复杂/派生类型：数组、结构体struct
    * **变量直接存储值，内存通常在栈中分配**
* 引用类型：指针、slice切片、管道channel、interface接口
    * **变量存储一个地址，内存通常在堆上分配。没有任何变量引用地址是，这个地址将变成垃圾有GC回收**

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
		
		func method(args...int)s
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
* `Time.Sleep(时间)` 暂停
* `Time.Unix()` 从1970/01/01 00:00:00至今的秒数
* `Time.UnixNano()` 从1970/01/01 00:00:00至今的纳秒数
    * 如果纳秒单位的unix时间超出了int64的表示范围，结果是为定义的？？？

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
* 四种初始化数组的方式
    1. `var a [3]int = [3]int{1,2,3}`
    2. `var a = [3]int{1,2,3}`
    3. `var a = [...]int{1,2,3}`
    4. `var a = [...]int{1:1,0:2,2:3}`，`var a = [3]int{1:1,0:2,2:3}`
        * 初始化时指定位置
    5. 类型推导：`a := [...]int{1:1,0:2,2:3}`
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
    * 数组作为韩素参数时，使用**值传递**，会进行值拷贝，数组间不会互相影响
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
* map声明不会分配内存，需要使用`make(map类型，size)`初始化，分配内存后才能使用
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
		* 如果bool是false，则val是空值
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
* 将结构体、字段的首字母小些
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
* 只要实现了接口中的方法，就相当于实现了实现了这个接口
    * 多继承：如果也实现了其他接口的方法，则默认实现了这些接口，即实现了多继承
* <label style="color:red">空接口:</label>`interface{}`
    * 空接口内没有任何方法
    * 所有类型都实现了空接口
    * **可以把任何一个变量赋值个空接口**
        ```go
        ```
* 接口不能实例化对象，但是**可以指向一个实现了该接口的自定义类型变量**
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

#？？？？
* make()默认创建的容量是多少
* append()如何对切片进行扩容
	

[top](#catalog)
[top](#catalog)
[top](#catalog)
[top](#catalog)

* 生成随机数
	```go
	rand.Seed(time.Now().UnixNano())
    n := rand.Intn(100)+1
	```
