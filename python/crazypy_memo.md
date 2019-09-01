## 2. 变量和简单类型
* 帮助函数
    * dir()： 查看指定类或模块包含的全部内容
    * helo()：查看莫哥函数或方法的帮助文档
    
* del：可以使用del来删除普通变量。删除后再访问该变量时会引发NameError异常
* print (value, ... , sep='', end='\n', file=sys.stdout, flush=False)
    * seq：多个value时的分隔符
    * end：默认输出后再输出换行
    * file：函数的输出目标。默认为`sys.sudout`系统的标准输出，即屏幕。可以使用文件对象，使结果输出到文件中
    * flush：控制输出缓存，默认为false，这样可以获得较好的性能

* 变量命令规则
    * 必须以字母、`_`开头
    * 可以包含数字、`_`、字母(不限于英文字母，可以包含中文等)

* 关键字
    * 关键字不能作为变量名，会报错
    * 显示所有关键字
    ```python
    import keyword
    keyword.kwlist
    ```

* 内置函数
    * 内置函数的函数名可以作为变量名，不会报错，只是内置函数被覆盖了，且该内置函数不能再使用了
    * print(): 在python2.x中 print是关键字，在python3.x中print是内置函数
    * unicode()只是python2.x的内置函数，在python3.x中取消了，为了保证兼容性，最好不做为变量名使用

* 数值类型
    * 整型
        * 4钟整型数值形式
            * 10进制
            * 2进制：`0b、0B`开头
            * 8进制：`0o、0O`开头（大小写的字母`o`）
            * 16进制：`0x、0X`开头, 10~15以`a～f`表示，且`a～f`不区分大小写
        * python3.x为了增加可读性，允许为数值(包括浮点型)，增加`_`作为分隔符，这些分隔符不会影响数值本身
    * 浮点型
        * 十进制形式：必须包含一个小数点，如3.14，否则会当成整型处理
        * 科学计数形式：5.12e2、5.12E2（使用科学计数形式的都是浮点型，无论实际结果是整数还是小数）
            * **51200是整型，512E2是浮点型。512E2会自动变为51200.0**
    * 复数
        * 虚部用`j/J`表示
            * 如：`3 + 0.2j`
            * 类型是：`complex`
            * 可以导入cmath，来使用各种支持复数运算的函数
            * 

* 如果需要对表达式换行，需要使用`\`来进行转义
    * 如
    ```python
    num = 20 + 3 / 4 + \
        2 * 3
    ```

* python 支持的转义字符
    * `\b` ：退格符
    * `\n` ：换行符
    * `\r` ：回车符
    * `\t` ：制表符
    * `\"` ：双引号
    * `\'` ：单引号
    * `\\` ：反斜线

* 字符串
    * 字符串时不可变的，字符串生成之后，**它包含的字符序列就不能发生任何改变**
    * 字符串连接
        * `+`连接
            * `s = s1 + s2`
        * 特殊方法：两个字符串挨在一起写，将会自动拼接到一切
            * `s = 'aaa' 'bbb'`
    * repr(), str()
        * 可以将数值转换为字符串
        * str本身是python的内置类型
        * repr是一个函数
        * repr(字符串) ：返回的结果是`字符串的python的表达式形式`(字符串两端带有`'`或`"`)
        * 在交互式解释器中输入一个变量或表达式是，将会自动使用repr()处理变量或表达式
    * param = input()
        * 通过输入获取参数值，返回的结果都是字符串
    * 长字符串
        * 三个单引号或双引号
        * 如果没有赋值给变量，将自动被解释器忽略，即被当作注释
        * 内部可以同时放置单引号和双引号
        * 使用`\` 来进行换行（即用转义字符对换行符进行转移）
    * 原始字符串
        * 以`r`开头，不会吧`\`当成特殊字符
        * 如果原始字符串中包含引号，同样需要进行转移
            * `s2 = r' "let\'s go", said Charlie' `
        * 原始字符的结尾处不能是反斜线`\`，否则**结尾处的单引号会被反斜线转义**，导致字符串不能正确结束
            * 此种情况建议不是用原始字符串，可以使用长字符串
            * 可以使用字符串连接方式中的`特殊方法`，其中将反斜线单独写：
                * ` s = r'aaaa' '\\'` 
    * **字节串**
        * bytes类型，不可变序列，以字节(2进制格式)序列来记录数据
            * 因为保存的是原始的字节数据，所以bytes对象可以在网络上传输数据
            * 可以存储各种二进制格式的文件：如图片、音乐等文件
        * 字符串由多个**字符组成**，字节串由**多个字节组成**
            * 如果采用合适的字符集，字符串可以转换成字节串；字节串也可以恢复成对应的字符串
            * str --> bytes 的几种方法
                * 如果str是ASCII字符，可以通过在字符串前添加b来构建字节串：
                    * `b'xxxx'`
                    * 创建空bytes: `x = bytes()`
                * 调用bytes()函数--bytes的构造方法，按指定字符集转换成字节串，如过不指定字符集则默认使用UTF-8
                    * `a = bytes('啊啊啊', enconding='utf-8')`
                * 调用str本身的encode()，按指定字符集转换成字节串，如过不指定字符集则默认使用UTF-8
                    * `a = '啊啊啊'.encode('utf-8')`
            * bytes --> str
                * `s = bx.decode('utf-8')`

        * bytes和str操作的数据单元不同，但支持的方法都基本相同
            * bytes中的每个数据单元都是**1个字节**，每个单元8bit，分为4bit * 2；4bit的范围是0～15，可以用16进制表示：如`\xe6`，其中e是一部分，6是一部分
            * 
    * 字符串格式化
        * `'ddd %s' % x` 或 `'ddd %s ee %s' % (x,y)`
            * %s: 转换说明符，相当于一个占位符
                * %s指定将变量或值使用str()转换为字符串
                * 如果有多个占位符，需要用`()`将变量括起来
                * 多种转换说明符
                    * d,i：带符号的10进制整数
                    * o：带符号的8进制整数
                    * x，X：带符号的16进制整数(x:a-f,X:A-F)
                    * e：科学计数法表示的浮点数(e小写)
                    * E：科学计数法表示的浮点数(E大写)
                    * f，F：10进制浮点数
                    * g：自动选择f或e格式
                    * G：自动选择F或E格式
                    * C：转换为单字符(只接受`整数`或`单字符字符串`)
                    * r：使用repr()将变量或表达式转换为字符串
                    * s：使用str()将变量或表达式转换为字符串
            * %[+-0]数值s
                * 指定字符串的最小宽度，长度不足时程序会自动在左侧补充空格，即右对齐
                    * 如果数字小于字符串的长度，则以字符串的长度为准
                    ```python
                    a = 143
                    b = 'aaa:%2d' % a
                    print(b) #aaa:143
                    ```
                    * 对于浮点数，还可以指定小数宽度：[整数长度.小数长度]
                * 改变行为的符号
                    * **几个符号可以同时存在**
                    * `-`：指定左对齐，在右边添加空格
                    * `+`：表示数值需要带符号（+ 或 -），符号会占一位
                        * 如果不是转换成数值型，则无效
                        * 不使用`0`时：`33 --> +33`
                        * 使用`0`时：`33 --> +00033`
                    * `0`：不补充空格，补充0
    * 序列操作
        * 字符串是由字符组成的，所以可以使用索引来操作字符
        * 可以用`in`运算符来判定是否包含某个子串：`'xxx' in s`
        * len(str) 获取长度
        * max(str)、min(str) 获取字符串中最大、最小字符（通过字符集编号来判断）
    * 大小写操作
        * title()：首字母大写
        * lower()：小写变换
        * upper()：大写变换
    * 删除前后的字符
        * strip(): 删除字符串前后的字符
        * lstrip(): 删除字符串左侧的字符
        * rstrip(): 删除字符串右侧的字符
        * 默认删除`空格`
        * 删除多个字符，如`s.lstrip('abcd')`，删除`a,b,c,d`四个字符（不是删除字符串abcd）
    * 查找、替换
        * startswith(str)：判断字符串开头
        * endswith(str)：判断字符串结尾
        * find(str, start=)：搜索子串出现的位置，没找到返回`-1`
        * index(str, start=)：搜索子串出现的位置，没找到会引发ValueError
        * replace(old, new)：使用子串替换目标串
        * translate(table)：使用指定的翻译映射表对字符串执行替换
        ```python
        table = {97:945, 98:946, 116:964}
        s.translate(table)
        ```
        * str.maketrans(原始字符，映射字符)：创建翻译映射表，生成结果可以配合translate使用
    * 分割、连接
        * split(字符, 最大分割次数)：分割字符串，默认使用`空格`分割(没有参数，或None)
        * 'str'.join()：使用`str`连接多个字符串

* 运算符
    * 赋值运算符`=`     
        * python的复制表达式是**有值的**，值就是被赋的值，所以可以连续赋值
            * `a = b = c = 20`，从右向左，a、b、c都赋值为20
    * 算数运算符
        * `*`：如果是数值型变量，进行乘法运算；如果是字符串变量，将N个字符串连接起来
        ```python
        s = 'a'
        s * 5 #a a a a a
        ```
        * `//`：整除，结果只有整数部分，小数部分会被舍弃
        * `%`：求余
            * 小数求余：`被除数-N*除数`的剩余结果
    * 位运算
        * & 与，| 或，^ 异或，～ 取反
        * `<<` 左移右侧补零
        * `>>` 右移，正数左侧补0，负数左侧补1
    * 比较运算符
        * is 判断两个变量所引用的对象`是否相同`
            * is 相当于 id(a) == id(b)，两个变量引用相同时为True （通过id()来获取对象的内存地址）
            * == 只比较两个变量的值
        * is not 判断两个变量所引用的对象`是否不相同`
        * **True可以当成整数1，False可以当成整数0**使用，
            * 1 == True
            * 0 == False
            * 可以参与各种算数运算
            ```python
            
            print(True + False) #1
            print(False - True) #-1
            ```
    * 逻辑运算符
        * and
        * or
        * not
    * 三目运算符
        * True_表达式 if 判断表达式 else False_表达式
        * `True/False_表达式`中可以放置多条语句
            * `,`分隔，返回多个语句返回值组成的`元祖`
                * 如果某条语句没有返回值，则返回`None`,如：`print(...)`语句
            * `;`分隔，只返回第一条语句的返回值
        * 三目运算符可以进行嵌套
        `True_表达式1 if 判断表达式1 else (True_表达式2 if 判断表达式2 else False_表达式) `
    * in/not in
        * 判断某个成员是否位域序列中
        * 判断字符串是否包含特定的子串
        * **判读序列是否包含子序列** ？？？？？？？？？
    * 运算符优先级
        |运算符||优先级|
        |-|-|-|
        |索引运算符|x[index] 或 x[index:index2[:index3]]|18,19|
        |属性访问|x.attribute|17|
        |乘方|**|16|
        |按位取反|～|15|
        |符号运算符|+/- 数字|14|
        |乘除|*、/、//、%|13|
        |加减|+、-|12|
        |位移|>>, <<|11|
        |按位与|&|10|
        |按位异或|^|9|
        |按位或|`|`|8|
        |比较运算符|==、!=、>、>=、<、<=|7|
        |is运算符|is、is not|6|
        |in运算符|in、not in|5|
        |逻辑非|not|4|
        |逻辑与|and|3|
        |逻辑或|or|2|

## 3 列表、元祖、字典
* 列表/元祖
    * 元祖不可变，列表可变
    * 元祖
        * 只有元素的变量初始化：`list = (a, )`(必须包含一个`,`);如果只有(a)，则(a)就是a
    * 列表
        * 可以使用list()：创建列表、将元祖转换成列表
        * append():添加元素
        * extend():添加一个序列中的全部元素
        * insert(index, element):在指定索引位置添加一个元素，如果索引位置大于列表的元素数量，则在列表的末尾添加
        * del:删除元素
            * del list[index]：删除指定索引上的元素
            * del list[start:end] / del list[start:end:step]，可以通过切片来批量删除
        * remove(element)：使用元素本事来进行删除操作，该方法只删除找到的第一个元素，如果元素不存在，会引发ValueError异常
        * clear()：清空列表
        * count(value)：统计某个值在元素中出现的次数
        * index(value, start, end)：判断某个元素在列表中出现的位置，如果元素不存在，会引发ValueError
        * pop()：从列表中弹出元素
        * reverse()：反转数组
        * sort(key=比较func, reverse=True)：排序，可以通过key指定排序的比较方法；默认升序排列，reverse=True降序排列
        * 修改元素：
            * 通过slice对多个元素赋值：`list[1:3] = ['a', 'b']`
                * 如果等号右侧序列的元素数量比slice多，则变为插入数据
                * slice中包含step时，元素的数量必须和别赋值的元素个数相同：`list[2:9:2] = ['a','b','c','d']`
            * 对空slice赋值可以插入元素：`list[2:2] = [2,3,4,5,5]`
            * 对列表中的一段赋值为`空列表`，就变成了从列表中删除元素：`list[2:5] = []`
    * 子序列：list[start: end: step]
        * end 元素不包含
        * step 可以使用负数，但是没有意义，只返回一个空列表：`[]`
    * 加法
        * 两个列表/元祖所包含的元素的总和（不会进行去重）
        * 加法规则
            * 列表+列表
            * 元祖+元祖
    * 乘法
        * 把列表、元祖的元素重复N次
            * [a,b,c] * 3 --> [a,b,c,a,b,c,a,b,c]
    * in运算符：判断列表/元祖中是否包含某个元素
    * max()，min() 获取元祖、列表的元素，要求各个元素必须时相同类型且可以比较大小的

* 字典
    * key必须是不可变类型，str、tuple都可以作为key
    * 可以通过dice(key=value)来闯将字典
    * del d[key] 删除字典中的key-value对
    * 可以使用`in/not in`来判断key是否在字典中
    * 使用字典
        * clear()：清空字典
        * get(key, default=value)：通过key取值，如果值不存在则返回`default`，默认为None
        * update({key:value})：使用一个新字典来更新当前字典。如果key存在，则用新值覆盖，如果key不存在，则添加到当前字典中
        * items(),values(),keys()：返回可操作的序列，(key, value), (value),(keys)
        * pop(key)：通过key获取value，并删除key-value
        * popitem()：随机弹出字典中的一个key-valued对(实际弹出的是底层存储中的最后一个key-value对)
        * setdefault(key, value)：根据key设定value，如果key不存在，则在字典中先添加这个key-value对，然后再返回该key对应的value
            * setdefault总能返回值
        * a = dict.fromkeys([key1,key2...], d)：使用给定的key，创建字典，默认value都是None，如果设置了d，则value都是d
    * 使用字典格式化字符串
    ```python
    temp = 'aaa:%(name)s, %(price)010.2f'
    book = {'name':'xxxx', 'price':1111.22}
    print(temp % book)
    ```

## 4 流程控制
* if
    * if条件的类型
        * if条件可以是任意类型
        * 会被当作False处理的值：False，None，0，''，()，[]，{}
            * 各种空值多会被当作False处理
* 断言 assert
    * 用于对一个bool表达式进行断言
    * 如果bool表达式为True，则程序向下执行，否则会引发AssertError错误
        * 如果 `assert x > 20`
* while
    * 使用方法
        ```python
        [init_statements]
        while test_expression :
            body_statements
            [iteration_statements]
        ```
    * 每次执行循环之前，都要先对test_expression循环条件求值

* for-in
    * 用于遍历任何可迭代对象(对象包含`__iter__`方法)
    * `for element in list:`，循环结束时，element依然等于序列最后一个元素的值

* 在循环后使用else
    * 如果循环中使用`break`终止循环，则不会执行else块
    * while - 循环后都可以定义else代码块，当循环条件为False时，程序会执行else代码块
        ```python
        while test_expression :
            body_statements
        else:
            else_statements
        ```
    * for 
        ```python
        for element in list:
            body_statements
        else:
            else_statements
        ```
* for表达式（列表推导式）
    * 返回列表
        * [表达式 for 循环计数器 in 可法代对象]
        * 添加if判断：[表达式 for 循环计数器 in 可法代对象 if 判断表达式]
    * 返回生成器
        * (表达式 for 循环计数器 in 可法代对象)
    * 表达式嵌套
        * [(x,y) for x in range(5) for y in range(4)] 相当于
        ```python
        d = []
        for x in range(5):
            for y in range(4):
                d.append((x,y))
        ```
        * 后面的for是前一个for的内存循环

* 常用工具
    * zip(list1, list2，...)，
        * 将两个列表各索引上的元素组成tuple
        * 如果list1、list2... 的长度不相等，则以长度短的列表为准
    * reversed(iter)
        * 反转序列，不改变序列本身，返回一个反序排列的`迭代器`
    * sorted(iter, key=比较func, reverse=True) 
        * 对传入的序列进行排序，返回一个新的排好序的`列表`
        * 默认升序排列

## 6 类和对象
* 类
    * python的类可以理解为一个`命名空间`
* 可以通过`del`删除某个实例变量：`del p.xxx`
* self:
    * 对于实例方法，第一个self参数是自动绑定的，是在构造方法汇总给你自动绑定到该构造方法初始化的对象
    * 不同方法中的区别
        * 在构造方法中引用该构造方法正在初始化的对象
        * 在普通实例方法中是引用该方法的对象
    * 在方法中将self作为返回值，则可以进行方法的链式调用
* 方法
    * 在类体中定义的方法默认都是实例方法
    * 可以在类范围内放置`可执行`代码，当执行该类定义是，这些代码会被执行，但只会执行一次
    * 动态添加实例方法
        * 可以使用`lambda表达式或其他函数`来动态的为实例对象添加实例方法，但是动态增加的方法，python无法自动将调用者`self`绑定到函数的第一个参数，所以需要手动传参
        ```python
        p.xx = lambda self:.....
        #或者
        def new_method(self):
            pass
        p.xx = new_method

        # 调用xx
        p.xx(p) # 手动将实例对象传入方法中
        ```

        * 如果希望self能绑定到方法的第一个参数，可以使用types模块下的MethodType
        ```python 
        from types import MethodType

        def new_method(self):
            pass

        p.xx = MethodType(new_method, p) # 将实例对象p绑定到方法中
        ```
    * 未绑定方法：可以通过类来调用实例方法，但是需要手动传入`self`
    ```python
    class Test(object):
        def method(self):
            pass 
    t = Test()
    Test.method(t) # 调用方式和 t.method() 相同
    
    # 也可以传入实例对象以外的东西，如字符串，但需要方法内部的支持
    Test.method('this is a str')
    ```
    
    * 类方法
        * 第一个参数为`cls`，会自动绑定到类本身
        * @classmethod 修饰的方法就是类方法
        * 可以通过实例对象来调用类方法，但是实际上还是类在调用，并且不会受到实例对象复写类变量的影响
        ```python
        class A(object):
            pa = 'this is A'

            @classmethod
            def printa(cls):
                print(cls.pa)
            
        a = A()
        a.pa = 'this is a'
        print(a.pa) # this is a
        print(A.pa) # this is A

        a.printa() # this is A 使用的仍然是类变量
        A.printa() # this is A
        ```
    * 静态方法
        * @staticmethod 修饰的方法 

* 成员变量
    * python允许通过对象访问类变量，但是本质依然是通过`类名来访问`；如果对象对类变量进行了赋值，则会变成`向实例对象中动态添加了一个新的变量`，而类变量本身不会受到影响
    * 使用property定义属性
        * property(fget=None, fset=None, fdel=None, doc=None)
        * 装饰器方式：
            * get：@proerty
            * set：@get属性名.setter

* 隐藏和封装
    * python中没有真正的隐藏机制，所以类定义的所有成员默认都是`公开的`
    * 为了隐藏类中的成员，可以给类的成员用`__`开头来命名
        * 通过`__`，python会自动进行该名：`__abc -->_className__abc`
        * 想调用`__abc`的时候可以使用`_className__abc`来调用

* 继承
    * 可以通过`未绑定方法`来调用被重写方法
    * 如果子类重写了父类的构造方法，那么子类的构造方法必须调用父类的构造方法。掉用父类构造方法的两种方法
        * 未绑定方法
        * 可以使用`super().__init__(...)`来调用父类的构造方法
            * 如果有多个父类，排在前面的会被优先调用
    * 多继承
        * 多继承初始化时，`super().__init__(...)`只能调用排在前面的父类构造方法，剩余的父类构造方法可以通过`未绑定方法来调用`
        * 如果多个父类中包含相同的方法，排在前面的父类方法会覆盖后面的
        ```python
        class A(object):
            def info(self):
                print('this is A')

        class B(object):
            def info(self):
                print('this is B')

        class C(A, B):
            pass

        c = C()
        c.info() # this is A


        class D(B, A):
            pass

        d = D()
        d.info() # this is B
        ```
* 动态性
    * __slots__
        * 限定实例对象可以动态添加的属性和方法，但是不会影响类本身的动态添加
        * 使用方法 `__slots__ = ('name1', 'name2', ...)`
        * **只对当前类的实例有效，对派生类无效**。对于派生类，也需要定义__slots__属性，添加后将会变成父类的__slots__加派生类的__slots__的总和
        ```python
        class A(object):
            __slots__ = ('pa')

        class B(A):
            __slots__ = ('pb')

        b = B()
        b.pa = 'this is pa'
        print(b.pa) # this is pa

        b.pb = 'this is pb'
        print(b.pb) # this is pb

        b.pc = 'this is pc'
        print(b.pc) # AttributeError
        ```
    * 可以用type()来判断实例对象属于那个类
    * 使用type()函数定义类
        * `所有类都是type类的实例`
        * 可以使用type()来动态创建类
        ```python
        # Dog类名， (object,)继承的父类(即使只有一个父类也要使用元祖)，
        # dict(walk=fn, age=6)绑定实例变量和方法
        Dog = type ('Dog', (object,), dict(walk=fn, age=6))
        ```
    * metaclass
        * 可以在创建类时动态的修改类定义，可以动态的修改程序中的一批类，集中的向类内添加某些方法和属性
        * 继承type的类都是metaclass：
            ```python
            def method(self):
                pass
            class A(type):
                def __new(cls, name, bases, attrs):
                    attrs['xxx'] = method
                    return type.__new__(cls, name, bases, attrs)
            ```
            * cls 被动态修改的类
            * name 被动态修改的类名
            * bases 被动态修改的类的所有父类
            * attr 被动态修改的类的所有属性，方法组成的`字典`
        * 使用方法
            ```python
            class A(type):
                def __new(cls, name, bases, attrs):
                    pass
            
            class B(metaclass=A):
                ...
            ```
    * 检查类型
        * issubclass(cls, class_or_tuple):检查类是否为一个类或元祖汇总多个类中任意类的子类
        * isinstance(obj, class_or_tuple):检查类是否为一个类或元祖汇总多个类中任意类的对象
        * 每一个类都有一个**__bases__**属性，可以查看该类的**所有直接父类**,返回一个**元祖**
        ```python
        class A(object):
            pass
        class B(object):
            pass

        class C(B,A):
            pass

        print(C.__base__) # (<class '__main__.B'>, <class '__main__.A'>)
        ```
        * 每个类都有一个**__subclasses__()**方法，可以查看该类的**所有直接子类**，返回一个**列表**
        ```python
        class A(object):
            pass
        class B(A):
            pass

        class C(B,A):
            pass

        print(A.__subclasses__()) # [<class '__main__.B'>, <class '__main__.C'>]
        ```

* 枚举类
    * 枚举类不能用来实例化对象
    * 定义枚举类的两种方式
        * 直接使用Emnu来创建
            ```python
            import enum
            Season = enum.Enum(Season, ('SPRING','SUMMER','FALL','WINTER'))
            ```
            * 第一个参数是类名，第二个参数是所有枚举值（从1来时）
            * 每个成员都有两个属性：name，value
            * 几种访问方法
                * Season.SPRING # Season.SPRING
                * Season['SPRING'] # Season.SPRING
                * Season(3) # 通过value来访问 Season.FALL
            * __members__ 返回一个字典{name:member}，包含了该枚举的所有实例
                * member中包含了name和value
        * 继承Emnu基类来创建派生枚举类
            ```python
            import enum
            class Orientation(enum.Enum):
                EAST = '东'
                SOUTH = '南'
                WEST = '西'
                NORTH = '北' 
                def info (self):
                    pass
            ```
            * 可以像类一样定义方法
            * 可以为枚举指定value
    * 枚举构造器
