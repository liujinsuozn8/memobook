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
- [数组](#数组)
- [切片](#切片)

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
    * **切片定义完后，不能使用，因为本身是空的，徐哟引用到一个数组，或者使用make来创建**
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

[top](#catalog)
[top](#catalog)
    


* 生成随机数
	```go
	rand.Seed(time.Now().UnixNano())
    n := rand.Intn(100)+1
	```
