* 预处理命令
    * 以`#`开头的命令，也称为宏定义命令
* 变量的声明
    * int a 既声明，也定义，在内存中建立了存储空间
    * extern int a 只声明，不定义，变量a可以在别的文件中定义
        * 如在函数内部通过extern来修改外部变量
        * 如果要在文件A中引用其他文件中定义的变量，需要在文件A中将变量加上extern
    * int a=1 声明并初始化
    * 非静态变量没有初始化时是没有定义的，静态变量没有初始化时会隐式初始化位NULL（各字节为0）
* 左值和右值
    * 左值：指向内存位置，可以出现在赋值符号的左边或右边
    * 右值：存在内存中某些地址的数值。右值不能被赋值，只能出现在等号的右边
* 常量
    * 定义后不能进行修改
    * #define
        * 用一个标识符来表示一个常量，称为符号常量
        * 符号常量在使用之前必须定义
            * #define 标识符 常量
    * const
        * 指定类型的常量：const type variable = value;
    * 编译时会在代码中进行替换
    * 不能再被赋值
    * 数值型常量
        * 后缀
            * U 表示无符号整数
            * L 表示长整数
            * UL 表示无符号长整数
        * 前缀
            * 0开始表示八进制数
            * 0x开始表示十六进制数
* 实型数据
    * 一半占4字节(32位)内存空间。按指数形式存储
        * 3.14159的存储方式：

        |符号|小数部分|指数部分|
        |-|-|-|
        |+|.314159|1|
        * float型：1位符号，8位指数，23位小数
        * double型：1位符号，11位指数，52位小数
        * 小数部分占的位数越多，精度越高  
        * 超出数据类型的有效位时，会产生舍入误差
    * 允许浮点数使用后缀：f/F，如`356f`和`356.`是相同的

* 字符型数据
    * 包含`字符常量`和`字符变量`
        * 字符常量：单引号括起来的`一个字符`
        * 字符变量：char a; 单个字符
        * 存储方式
            * 每个字符变量被分配`一个字节`的内存空间，因此只能存放一个字符。
            * 字符值以ASCII码的形式存放在变量的内存空间中
        * 可以和整形数据进行计算得到新的字符值
            * `char a=33;`和`int a=33;`两者是一样的
            * 对于`int a=33;`，可以使用`printf('%c', a)`, 来输出字符串
            * 对于`char a=33;`，可以使用`printf('%d', a)`, 来输出数值
    * 字符串常量
        * 由`双引号`括起的字符序列
        * 可以使用空格进行分割："hello, " "d" "ear"
        * 可以使用`\`换行书写：
        ```
        "hello, \
        dear"
        ```
* 字符串
    * 使用null字符`\0`终止的一维字符数组
    * 字符串的声明和初始化：
    ```
    char a[6] = {'h','e','l','l','0','\0'};
    char a[] = "hello";
    ```

* 存储类
    * 定义变量/函数的范围和生命周期，放在类型之前
    * auto
        * 所有局部变量默认的存储类
        * auto只能用在函数内，auto只能修饰局部变量
        * `int a;`,`auto int a;`，两个变量相同
    * register
        * 定义存储在寄存器中而不是RAM中的局部变量，变量的最大尺寸等与寄存器的大小，且不用应用一元的'&'运算符
        * 用于需要快速访问的变量，如计数器
        * 定义后，并不一位置变量将被存储在寄存器中，而是可能存储在寄存器中，取决与硬件和实现的限制
    * static
        * static修饰的局部变量可以在函数调用之间保持局部变量的值
        * static修饰全局变量时，会使变量的作用域限制在声明它的文件内，文件外的方法无法使用该全局变量
    * extern
        * 提供一个全局变量的引用，并对所有的程序文件都是可见的
        * 会把变量名指向一个之前定义过的存储位置

* 杂项运算符
    * &a 返回变量a的地址
    * *a 指向一个变量
    * ?: 三目运算符
    * sizeof() 返回变量的大小

* 函数
    * 函数声明
        * 函数声明会高速编译器函数名称及如何调用函数，所以函数的主体可以单独定义。定义时需要加`;`
        * 函数声明中，参数的名称并不重要，只有参数类型是必须的，如：`int max(int, int);`

* 数组
    * 数组名是一个指向数组第一个元素的常量指针
        * int a[10], a指向：&balance[0]
        * 可以使用指针来访问第i个元素：*(a+i)
    * 声明数组：type arrayName [arraySize];
    * 初始化数组：type arrayName[size] = {.....}
        * {...}中的元素数量，不能大于size
        * 省略size时，size等于{...}中的元素个数
    * 多维数组：
        * type arrayName [size1][size2]...;
        * 初始化({}嵌套和不嵌套)：
        ```
        int a[3][4] = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12}
        };

        int a[3][4] = {1,2,3,4,5,6,7,8,9,10,11,12};
        ```
    * 传递数组到函数
        * void method(int *param)
        * void method(int param[10])
        * void method(int param[])
    * 从函数返回数组：声明一个返回指针的函数
        * int * method()
    
* 枚举类型enum
    * 枚举类型
        * enum 枚举名 {元素1, 元素2...}
        * 第一个元素的默认值为`整形0`，后续元素的值在前一个成员上加1。如果第一个定义为1，则第二个为2
        ```
        enum DAY{mon=1, tue, wed, thu, fri, sat, sun};
        ```
        * 定义时也可以不连续的定义值
        ```
        enum DAY{mon, tue, wed=3, thu, fri, sat, sun};
        ```
    * 定义枚举变量：
        * enum DAY day;
        * 定义枚举类型和变量
        ```
        enum DAY
        {
            mon=1, tue, wed, thu, fri, sat, sun
        } day;
        ```
        * 省略枚举名称，直接定义枚举变量
        ```
        enum
        {
            mon=1, tue, wed, thu, fri, sat, sun
        } day;
        ```
    * 如果枚举类型的值不连续，则不能进行遍历
    * 将整数转换为枚举
    ```
    int a = 1;
    enum DAY day;
    day = (enum DAY) a;
    ```

* 指针
    * `&`
        * &a 获取变量的内存地址
    * `*`
        * type *a; 定义指针变量
        * *a 访问指针地址指向的内存
    * 指针的值的类型都一样，代表内存地址长度的16进制数
    * 空指针
        * int *a = NULL;
        * NULL指针是一个定义在标准库中的值为0的常量
        * 表明该指针不指向一个可访问的内存位置
        * 假定它不指向任何东西
        * 可以使用if来判断指针是否为NULL：if(a)
    * 指针的算数运算
        * 四种算数运算：++、--、+、-
            * a++
                * 如果a是指向1000的int指针，a++=1004
                * 如果a是指向1000的char指针，a++=1001
    * 指针数组
        * int *a[size];
        * const char *a[] = {"aaaa", "bbb",....}; 可以用一个字符指针数组来存储一个字符串数组
    * 指向指针的指针
        * int **a;

* 结构体
    * struct tag{member-list...} var-list;
        * tag,member-list,var-list 这三部分至少要出现两个
        * 如果两个结构体member相同，一个有tag另一个没有tag，则两个结构体是不同的
        * 两个结构体互相包含书，需要对其中一个结构体进行不完整声明
        ```
        struct B;    //对结构体B进行不完整声明
 
        //结构体A中包含指向结构体B的指针
        struct A
        {
            struct B *partner;
            //other members;
        };
        
        //结构体B中包含指向结构体A的指针，在A声明完后，B也随之进行声明
        struct B
        {
            struct A *partner;
            //other members;
        };
        ```
    * 指向结构体的指针
        * 访问结构指针的成员：var->title;

* 共用体
    * 允许在相同的内存位置存储不同的数据类型
    * 可以定义一个带有多个成员的共用体，**但是任何时候只能有一个成员带有值**
    * 定义，每个成员结尾使用`;`分割
    ```
    union tag {
        member-list;
    } var-list;
    ```
    * 共用体所占的内存=最大的成员
* 位域
    * 本质上是一种结构体，成员时按二进位分配的
    * 把一个字节中的二进制位划分为几个不同的区域，并说明每个区域的位数
    ```
    // data占两个字节
    struct tag {
        int a:8, //8位
        int b:2, //2位
        int c:6 // 6位
    } data;
    ```
    * 一个位域存储在同一个子节中，如一个字节所剩空间不够存放另一个位域时，会从下一个单元起存放该位域
    ```
    struct tag{
        unsigned a:4,
        unsigned  :4, //空域
        unsigned b:4, //从下一个单元开始存放
        unsigned c:4
    }
    ```
    * 位域不允许跨越两个字节，因此位域的长度不能大于一个字节的长度(8位)。如果大于了，一些编译器会允许域内存重叠，另一些编译器会把大于一个域的部分存储在下一个字节中

* 输入输出
    * getchar() & putchar()
        * int getchar()从屏幕读取一个可用字符，并返回一个整数
        * int putchar(int c) 向屏幕输出一个字符
    * gets() & put()
        * char *gets(char *s) 从stdin读取一行到s所指向的缓冲区，知道一个终止符或EOF
        * int puts(const char *s) 把字符串s和一换行符写入stdout
    * scanf() & printf()
        * int scanf(const char *format, ...) 从标准输入流 stdin 读取输入
        * int printf(const char *format, ...) 函数把输出写入到标准输出流 stdou

* 文件读写
    * 打开文件：FILE *fopen( const char * filename, const char * mode );
    * 关闭文件：int fclose( FILE *fp );
        * 如果关闭时发生错误，则函数返回EOF
    * 写入文件
        * 写入字符：int fputc( int c, FILE *fp );
        * 写入字符串：
            * int fputs( const char *s, FILE *fp );
            * int fprintf(FILE *fp,const char *format, ...) 
    * 读取文件：
        * 读取字符：int fgetc( FILE * fp );
        * 读取字符串：
            * char *fgets( char *buf, int n, FILE *fp );
                * 从文件中读取n-1个字符，并在结尾添加一个null
                * 如果在中途读取到一个换行符`'\n'`或EOF，则只返回读取到的字符，包括换行符
            * int fscanf(FILE *fp, const char *format, ...) 
                * 在遇到第一个`空格字符`时，停止读取

* 错误处理
    * errno.h 中有各种各样的错误代码
    * 发生错误时，函数调用返回1或NULL，同时会设置一个错误代码errno，该错误代码时全局变量，表示函数调用期间发生了错误
        * 在程序初始化时，应该把errno设置位0，0表示程序中没有错误
    * 通过stderr文件流来输出所有的错误
        * fprintf(stderr, "错误号: %d\n", errno);
    * perror("str") 显示str文本+冒号+空格+当前errno值的文本表示形式
    * strerror() 返回一个字符串指针，指向，当前errno值的文本表示形式
        * fprintf(stderr, "str", strerror(errnum));

* 程序退出状态
    * 正常退出： EXIT_SUCCESS = 0
    * 存在错误时：EXIT_FAILURE = -1

* 可变参数
    * #include <stdarg.h>
        * 用到的宏
            * va_list
            * va_start
    * 定义函数 type method(int num, ...)
        * num 可变参数的数量
        * ... 可变参数
    * 创建va_list变量：`va_list valist;`
    * 使用va_start宏来初始化va_list变量为一个参数列表：`va_start(valist, num)`
    * 使用va_arg宏和va_list变量来访问参数列表中的每个项:`va_arg(valist, int)`
    * 使用va_end来清理va_list变量的内存：`va_end(valist)`

* `void *`
    * 表示未确定类型的指针
    * 可以通过强制类型转换转化为其他类型的指针
    * 不能对void指针进行运算
    
* 内存管理
    * #include <stdlib.h>
        * `void *calloc(int num, int size);` 在内存中动态分配num*size个连续空间，每个字节的值都是0
        * `void free(void *address); ` 释放address所指向的内存块，释放动态分配的内存空间
        * `void *malloc(int num); ` 在`堆区`分配一块指定大小的内存空间。内存空间在函数执行后不会初始化，它的值是未知的
        * `void *realloc(void *address, int newsize); ` 重新分配内存，把内存扩展到newsize
    * 当程序退出时，操作系统会自动释放所有分配个程序的内存