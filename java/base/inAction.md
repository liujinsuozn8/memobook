<span id="catalog"></span>
- [通过行为参数化传递代码](#通过行为参数化传递代码)
- [Lambda表达式](#lambda表达式)
    - [Lambda表达式的基本概念](#Lambda表达式的基本概念)
    - [如何使用Lambda表达式](#如何使用lambda表达式)
    - [环绕执行模式](#环绕执行模式)
    - [使用函数式接口](#使用函数式接口)
    - [java8中常用的函数式接口](#java8中常用的函数式接口)
    - [方法引用](#方法引用)
        - [方法引用的基本概念](#方法引用的基本概念)
        - [如何构建方法引用](#如何构建方法引用)

- java中的并行与无共享可变状态
    - `synchronized`的替代：库会负责分块，将大的流分成几个小的流，来进行**并行处理**
    - 流提供的并行，需要传递的方法参数在执行时没有互动，即**执行时元素无互动**
        - 因为只有在这种**数据间无共享**的状态下，才能进行安全、有效的并行执行
- 两类迭代方式
    - 内部迭代：通过`StreamAPI`,数据在类库内部完成的迭代
    - 外部迭代：通过`for-each`来迭代元素

# 通过行为参数化传递代码
[top](#catalog)
- 行为参数化
    - 拿出一个代码块，准备好，却不去执行。该代码块可以被程序的其他部分调用。即可以推迟该代码块的执行
    - 可以将这样的代码块作为参数传递给另一个方法
- 行为参数化的好处
    - 多种行为，一个参数
        - 通过行为参数化，可以**将处理逻辑 与 对集合元素的应用 进行拆分**
        - 可以重复使用同一个方法，通过传递不同的行为 来达到不同的目的
    - 能够轻松的应对需求的变化
- **谓词**:一个**返回指定类型值的函数**
- 对一个实例的改造
    - 对苹果库存的数据筛选
    - 不好的实现：通过标识来区分属性，并执行删选
        - `boolean flag`的含义容易混淆
        - 方法的调用形式非常的不好
        - 无法应对更多的变化
        ```java
        // flag == true color
        // flag == false weight
        public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag){
            List<Apple> result = new Arraylist<Apple>();
            for (Apple apple : inventory){
                if ( (flag && apple.getColor().equals(color)) || 
                    (!flag && apple.getWeight() > color)
                    ) {
                    
                    result.add(apple);
                }
            }

            return result;
        }
        ```
        ```java
        List<Apple> greenApple= filterApples(inventory, "green", 0, false);
        ```
    - 通过接口来对问题进行建模 (将需求/行为 参数化)
        - 通过接口创建一个**方法簇**，来对多种需求进行建模
            - 类似于**策略设计模式**
            ```java
            //提供一个通用接口 --- 算法簇
            public interface ApplePredicate{
                boolean test(Apple apple); // 创建一个谓词
            }

            //接口的实现:重量
            public class AppleWeightOredicate implements ApplePredicate{
                public boolean test(Apple apple){
                    return apple.getWeight() > 150;
                }
            }

            //接口的实现:颜色
            public class AppleColoeOredicate implements ApplePredicate{
                public boolean test(Apple apple){
                    return "green".equals(apple.getColor());
                }
            }
            ```
        - 改进调用方法
            ```java
            public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
                List<Apple> result = new Arraylist<Apple>();
                for (Apple apple : inventory){
                    if (p.test(apple)) {
                        result.add(apple);
                    }
                }

                return result;
            }
            ```
            ```java
            List<Apple> greenApple= filterApples(inventory, new AppleColoeOredicate());
            ``` 
        - 这种改进方法的问题：
            - 将需求/策略封装在模板代码中
                ```java
                public class AppleWeightOredicate implements ApplePredicate{
                    public boolean test(Apple apple){
                        ...
                    }
                }
                ```
            - 每次调用时，必须创建一个对象，即**声明很多只会使用一次的类**

    - 使用**匿名类**简化接口解决问题的方式
        - 匿名类允许：同时声明并实例化一个类
            - 通过匿名类，可以减少**模板代码**
            ```java
            public interface ApplePredicate{
                boolean test(Apple apple); // 创建一个谓词
            }

            public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
                List<Apple> result = new Arraylist<Apple>();
                for (Apple apple : inventory){
                    if (p.test(apple)) {
                        result.add(apple);
                    }
                }

                return result;
            }
            ```
            ```java
            List<Apple> greenApple= filterApples(inventory, new ApplePredicate(){
                public boolean test(Apple apple){
                    return "green".equals(apple.getColor());
                }             
            });
            ``` 
        - 匿名类的问题
            - 本质上还是在创建对象，是一个**比较重的过程，占用了很多的空间**
    - 使用`Lambda表达式`来解决问题
        ```java
        public interface ApplePredicate{
            boolean test(Apple apple); // 创建一个谓词
        }

        public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
            List<Apple> result = new Arraylist<Apple>();
            for (Apple apple : inventory){
                if (p.test(apple)) {
                    result.add(apple);
                }
            }

            return result;
        }
        ```
        ```java
        List<Apple> greenApple= filterApples(
            inventory, 
            (Apple apple) -> "green".equals(apple.getColor())
        );
        ``` 
    - 使用泛型来构造一个**通用的数据筛选方案**
        ```java
        public interface Predicate<T>{
            boolean test(T t); // 创建一个谓词
        }

        public static <T> List<T> filter(List<T> list, Predicate p){
            List<T> result = new Arraylist<T>();
            for (T e : list){
                if (p.test(e)) {
                    result.add(e);
                }
            }

            return result;
        }
        ```
        ```java
        List<Apple> greenApple = filter(list, (Apple a) -> "green".equals(a.getColor()));
        List<Integer> greenApple = filter(list, (Integer a) -> i % 2 == 0);
        ```

# Lambda表达式

## Lambda表达式的基本概念
[top](#catalog)
- 可以将Lambda表达式理解为:**简洁的表示可传递的匿名函数的一种方式**
- Lambda表达式的特性
    - 匿名性：没有明确的名称
    - 函数性：
        - 不属于末各特定的类
        - 和方法一样，有参数列表、函数主体、返回类型，可能还有一个可以抛出的异常列表
    - 传递性：可以作为参数传递给方法 或 存储在遍历中
    - 简洁性：无需像匿名类一样写**模板代码**
- 示例：使用lambda表达式来简化匿名对象
    ```java
    //使用匿名类
    Comparator<Apple> byWeight = new Comparator<Apple>(){
        public int compare(Apple a1, Apple a2){
            return a1.getWeight().compareTo(a2.getWeight());
        }
    };

    //使用labmda表达式
    Comparator<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
    ```
- Lambda表达式的语法
    - 基本组成
        - 参数列表
        - `->`:通过箭头将参数列表和Lambda主体分割开
        - Lambda主体:执行具体操作
    - 表达式和语句的区别
        - 表达式`expression`,有返回值
        - 语句`statements;`,没有返回值，需要以`;`结尾，并写在`{}`中
    - Lambda的基本语法
        - `(paramter) -> expression`
        - `(paramter) -> {statements;}`
        - 表达式可以包含多行语句，但是需要写在`{}`中
    - lambda表达式没有`return`语句，因为已经隐含了`return`
        - 可以主动添加`return`语句：`() -> {return ...;}`
    - 不正确的示例
        - `() -> return "aaa";`
            - 应该写作：`() -> {return "aaa";}`
            - 因为`return "aaa";`本身是语句
        - `() -> { "aaaaa"; }`
            - 应该写作：`() -> "aaaaa"`
            - `"aaaaa"`本身就是表达式，不需要写在`{}`中
## 如何使用Lambda表达式
[top](#catalog)
- 函数式接口
    - `函数式接口`:只定义一个抽象方法的接口
        - 函数式接口中可以包含多个**默认方法**，但是**必须只能有一个**抽象接口
    - java中的几个函数式接口
        ```java
        public interface Comparator<T>{
            int compare(T o1, To2);
        }

        public interface Runnable{
            void run();
        }

        public interface Callable<V>{
            V call();
        }
        ```
- Lambda表达式允许直接以**内联**的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例
    ```java
    Runnable a = () -> System.out.println("test");

    public static void process(Runnable r){
        r.run();
    }
    ```
- 函数描述符
    - 函数式接口的抽象方法的**签名**，基本上就是Lambda表达式的签名，这种抽象方法称为**函数描述符**
        - 函数描述符就是：抽象方法的签名，如`T->R`
        - `Runnable`接口可以看作一个什么也不接受、什么也不返回的函数签名
- Lambda表达式可以：
    - 赋值给变量
    - 传递给一个接受**函数式接口**作为参数的方法
        - Lambda表达式的签名需要和函数式接口的抽象方法相同
    - 可以作为函数返回值，返回值类型必须是一个函数式接口
    ```java
    // 1 赋值给变量
    Runnable a = () -> System.out.println("test");

    public static void process(Runnable r){
        r.run();
    }
    // 2 作为函数参数
    public void execute(Runnable r){
        r.run();
    }
    execute(()->());

    // 3 作为函数返回值
    public Callable<String> fetch(){
        return () -> "test";
    }
    ```

## 环绕执行模式
[top](#catalog)
- 常见示例：资源处理
    - 资源处理的常见模式是：打开一个资源，做一些处理，然后关闭资源
    - 环绕执行模式：设置和清理资源阶段的代码总是很类似，并且会围绕着那些`处理代码`
    ```java
    public static String processFile() throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("xxx.txt"))){
            return br.readLine();
        }
    }
    ```
- 对示例进行改造:
    - 通过改造，来重用：设置和清理资源的代码
    - 接口定义：行为参数化
        ```java
        @FunctionalInterface
        public interface BufferedReaderProcessor{
            String process(BufferedReader b) throws IOException;
        }
        ```
    - 修改函数
        - 使用函数式接口来传递行为
        - 执行行为
        ```java
        public static String processFile(BufferedReaderProcessor p) throws IOException{
            try(BufferedReader br = new BufferedReader(new FileReader("xxx.txt"))){
                return p.process(br);
            }
        }
        ``` 
    - 传递Labmda表达式
        - 读取一行
            - `String result processFile((BufferedReader br) -> br.readLine());`
        - 读取两行
            - `String result processFile((BufferedReader br) -> br.readLine() + br.readLine());`

## 使用函数式接口
[top](#catalog)
- 为了应付不同的Lambda表达式，需要一套能够描述常见函数描述符的函数式接口
    - `java.util.function`包中，引入了几个新的函数式接口
- Predicate
    - `java.util.function.Predicate<T>`
        ```java
        @FunctionalInterface
        public interface Predicate<T> {
            boolean test(T t);
            ...
        }
        ```
    - 示例：一个接受String对象的Lambda表达式
        ```java
        @FuncationalInterface
        public interface Predicate<T> {
            boolean test(T t);
        }

        public static <T> List<T> filter(List<T> list, Predicate<T> p){
            List<T> result = new ArrayList<>();
            for (T s: list){
                if (p.test(s)){
                    result.add(s);
                }
            }
            return result;
        }
        ```
        ```java
        Predicate<String> p = (String s) -> !s.isEmpty();
        List<String> result = filter(list, p);
        ```
- Consumer
    - `java.util.function.Consumer<T>`
        ```java
        @FunctionalInterface
        public interface Consumer<T> {
            void accept(T t);
        }
        ```
    - 如果需要访问类型`T`的对象，不需要返回值，只执行某些操作时，可以使用这个接口
    - 示例:打印数字
        ```java
        @FunctionalInterface
        public interface Consumer<T> {
            void accept(T t);
        }

        public static <T> void forEach(List<T> list, Consume<T> c) {
            for (T e: list){
                a.accept(e);
            }
        }
        ```
        ```java
        Consume<Integer> c = (Integer e) -> System.out.println(e);
        forEach(list, c);
        ``
- Function
    - `java.util.function.Function<T, R>`
        ```java
        @FunctionalInterface
        public interface Function<T, R> {
            R apply(T t);
        }
        ```
    - `apply`输入一个`T`对象，返回一个`R`对象，常用于将输入对象的信息映射到输出
    - 示例：将字符串列表转换为长度列表
        ```java
        @FunctionalInterface
        public interface Function<T, R> {
            R apply(T t);
        }

        public static <T, R> List<R> map(List<T> list, Function<T,R> f){
            List<R> result = new ArrayList<>();
            for (T e : list){
                result.add(f.apply(e));
            }
            return result;
        }
        ```
        ```java
        List<Integer> result = map(
            Arrays.asList("aaaa", "dsf", "rtetre"),
            (String s) -> s.length()
        );
        ```
- 原始类型特化
    - java对内置的几个函数式接口`Predicate, Consumer, Function`等 做了基本类型的特化处理
        - 通过`原始类型特化`，来防止基本类型的自动装箱操作
    - 在使用泛型时，无法使用基本类型，只能使用对应的包装类，这是有泛型的实现机制决定的，但是java的自动装箱操作会浪费性能
    - 示例：`IntPredicate`
        - `java.util.function.IntPredicate`
            * 内部实现，不使用泛型，直接使用`int`
            ```java
            @FunctionalInterface
            public interface IntPredicate {
                boolean test(int value);
            }
            ```
            ```java
            //没有使用装箱
            IntPredicate p1 = (int i) -> i % 2 ==0；

            //使用装箱
            Predicate<Integer> p1 = (Integer i) -> i % 2 ==0；
            ```

- 异常处理
    - 任何函数式接口都不允许抛出受检异常`check exception`
    - Lambda表达式来抛出异常的两种解决方法
        - 自己定义一个函数式接口，并声明受检异常`check exception`
            ```java
            @FunctionalInterface
            public interface BufferedReaderProcessor{
                String process(BufferedReader b) throws IOException;
            }
            ```
        - 使用java提供的内部杉树式接口时，可以将Lambda包裹在一个`try/catch`块中
            ```java
            Function<BufferedReader, String> f = (BufferedReader b) -> {
                try{
                    return b.readLine();
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            }
            ```

## java8中常用的函数式接口
[top](#catalog)

    |函数式接口|函数描述符|原始类型特化|
    |-|-|-|
    |Predicate<T>|`T->boolean`|IntPredicate,LongPredicate,DoublePredicate|
    |Consume<T>|`T->void`|IntConsume,LongConsume,DoubleConsume|
    |Function<T, R>|`T->R`|IntFunction<R>|
    |||IntToDoubleFunction|
    |||IntToLongFunction|
    |||LongFunction<R>|
    |||LongToDoubleFunction|
    |||LongToIntFunction|
    |||DoubleFunction<R>|
    |||ToIntFunction<T>|
    |||ToDoubleFunction<T>|
    |||ToLongFunction<T>|
    |Supplier<T>|`()->T`|BooleanSupplier,IntSupplier,LongSupplier,DoubleSupplier|
    |UnaryOperator<T>|`T->T`|IntUnaryOperator,LongUnaryOperator,DoubleUnaryOperator|
    |BinaryOprator<T>|`(T,T)->T`|IntBinaryOprator, LongBinaryOprator, DoubleBinaryOprator|
    |BiPredicate<L,R>|`(L,R)->boolean`|-|
    |BiConsumer<T,U>|`(T,U)->void`|ObjIntConsume<T>, ObjLongConsume<T>, ObjDoubleComsume<T>|
    |BiFunction<T,U,R>|`(T,U)->R`|ToIntBiFunction<T,U>, ToLongBiFunction<T,U>, ToDoubleBiFunction<T,U>|

## 方法引用
[top](#catalog)
### 方法引用的基本概念
- 方法引用可以**重复使用现有的方法定义，并像Lambda一样传递他们**
    - 可以把方法引用看作针对单一方法的Lambda语法糖
        - 因为作用相同，但是代码变少了
    - 一些情况下，方法引用更易读、更自然
    ```java
    //使用Lambda表达式
    list.sort((Apple a1, Apple a2) -> a1.getWeigth().compareTo(a2.getWeight()));

    //使用方法引用
    list.sort(comparing(Apple::getWeight));
    ```
- 使用方法:`目标引用::方法名`
    - 方法名不需要括号，因为这样使用时**不需要立刻调用它**
    - `Apple::getWeight` 相当于 `(Apple a) -> a.getWeight()`的快捷写法

### 如何构建方法引用
[top](#catalog)
- 主要有三种方法引用
    - 指向静态方法的方法引用
        - 如`Integer`的`parseInt`方法，写作`Integer::parseInt`
    - 指向任意类型实例方法的方法引用
        - 如`String`的`length`，写作`String::length`
        - 使用时，会对方法引用自动解包
            - 对于`String::length`
                - Lambda表达式写法：
                    ```java
                    //函数描述符： String->Integer
                    Function<String, Integer> f = (String a) -> a.length();
                    ```
                - 使用`String::length`时，自动解包成Lambda表达式相同的函数描述符:`String->Integer`
                    - 方法的实例对象，即调用者部分，会被解析成`String`
                    - 由于`length()`方法无参，所以这部分的类型被忽略了，最后输入部分的签名只有：`String`
                    - 返回值是int型，类型被解析为`Integer`

    - 指向现有对象的示例方法的方法引用
        - 假设有类`A`的示例对象`a`，它持有方法`getValue`，可以写作`a::getValue`
        - 即在lambda中调用一个已经存在的外部对象中的方法

- 与Lambda表达式一样，编译器也会根据给定的函数式接口，来对方法引用进行**类型检查**
    - 方法引用的签名必须和上下文类型匹配

- 示例：等效引用
    - `Function<String, Integer> stringToInteget = (String s) -> Integer.parseInt(s);`
        - `Integer::parseInt`
        - 使用了静态方法的引用
    - `BiPredicate<List<String>, String> contains = (list, element) -> list.contains`
        - `List::contains`
        - 使用任意类型对象的方法引用

- 示例：对List<String>进行排序，忽略大小
    ```java
    List<String> list = Arrays.toList("a","b","c","d");
    
    //使用lambda表达式
    list.sort((s1, s2) -> s1.compatToIgnoreCase(s2));

    //使用方法引用
    list.sort(String::compatToIgnoreCase);
    ```
### 构造函数引用
[top](#catalog)
- 使用构造函数名称和`new`，来创建该构造函数的引用：`ClassName::new`
    - 与指向静态方法的引用类似
    - 根据所使用的函数式接口，编译器会选择对应参数的构造函数
    - 示例
        ```java
        class Apple{
            int weigth;

            public Apple(){
                System.out.println("create a apple");
            }

            public Apple(Integer weight){
                System.out.println("create a apple, weight = " + weight);
                this.weigth = weigth;
            }
        }

        @Test
        public void test(){
            //空参构造函数引用：  Apple::new
            //函数签名：() -> Apple
            //相当于函数式接口：Supplier ()->T

            //创建空参构造函数的引用
            //等价于： Supplier<Apple> constructor = () -> new Apple();
            Supplier<Apple> constructor1 = Apple::new;
            //通过引用创建对象
            Apple a1 = constructor1.get();

            //创建 Apple(Integer weight) 的引用
            //函数签名：Integer->Apple
            //相当于函数式接口：Function<T, R> T->R
            //等价于： Function<Integer, Apple> constructor2 = (weight) -> new Apple(weight)
            Function<Integer, Apple> constructor2 = Apple::new;
            //通过引用创建对象
            Apple a2 = constructor2.apply(100);
        }
        ```
- 针对构造函数、数组构造函数、父类调用(super-call)的一些特殊形式的方法引用
