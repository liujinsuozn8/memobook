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
    - [复合Lambda表达式的使用方法](#复合Lambda表达式的使用方法)
    - [类型检查&类型推断以及限制](#类型检查&类型推断以及限制)
- [流的引入](#流的引入)
    - [Dish定义](#dish定义)
    - [流的基本概念](#流的基本概念)
    - [流与集合](#流与集合)
    - [流操作](#流操作)
- [流的使用](#流的使用)
    - [筛选和切片](#筛选和切片)
    - [映射](#映射)
    - [查找和匹配](#查找和匹配)
    - [reduce归约](#reduce归约)
    - [交易员示例](#交易员示例)
    - [数值流](#数值流)
    - [构建流](#构建流)
- [用流收集数据](#用流收集数据)
    - [Collector收集器](#collector收集器)
    - [归约和汇总](#归约和汇总)
    - [分组](#分组)
    - [对收集器的结果进行转换](#对收集器的结果进行转换)
    - [分区](#分区)
        - [Collector接口的定义](#collector接口的定义)
        - [自定义ToListCollector收集器](#自定义toListCollector收集器)
    - [Collectors类的静态工厂方法](#collectors类的静态工厂方法)
    - [收集器接口](#收集器接口)
    - [开发收集器以获得更好的性能](#开发收集器以获得更好的性能)
- [并行处理数据与性能](#并行处理数据与性能)
    - [并行流](#并行流)
    - [分支合并框架](#分支合并框架)
    - [Spliterator](#spliterator)
- [重构+测试+调试](#重构+测试+调试)
    - [为改善可读性和灵活性重构代码](#为改善可读性和灵活性重构代码)
    - [使用Lambda重构面向对象的设计模式](#使用lambda重构面向对象的设计模式)
    - [调试Lambda](#调试lambda)
- [默认方法](#默认方法)
    - [默认方法的基本概念](#默认方法的基本概念)
    - [默认方法的使用模式](#默认方法的使用模式)
    - [解决冲突的规则](#解决冲突的规则)
- [用Optional取代null](#用optional取代null)
- [CompletableFuture组合式异步编程](#completableFuture组合式异步编程)
    - [Future接口](#future接口)
    - [实现异步API](#实现异步api)
    - [CompletableFuture实现异步API](#CompletableFuture实现异步API)
    - [避免同步阻塞](#避免同步阻塞)
    - [使用哪种方式来处理并行](#使用哪种方式来处理并行)
    - [对多个异步任务进行流水线操作](#对多个异步任务进行流水线操作)
- [日期时间API](#日期时间api)
    - [旧API的问题](#旧api的问题)
    - [TemporalField接口](#temporalfield接口)
    - [新的日期时间类](#新的日期时间类)
    - [操作-解析-格式化日期](#操作-解析-格式化日期)
    - [处理不同的时区和历法](#处理不同的时区和历法)
- [函数式编程](#函数式编程)
    - [函数式java编程](#函数式java编程)
    - [高阶函数](#高阶函数)
    - [](#)
    - [](#)
    - [](#)
    - [](#)
    - [](#)
    - [](#)


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
        - 不属于某个特定的类
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
            - `String result = processFile((BufferedReader br) -> br.readLine());`
        - 读取两行
            - `String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());`

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
    - 在使用泛型时，无法使用基本类型，只能使用对应的包装类，这是由泛型的实现机制决定的，但是java的自动装箱操作会浪费性能
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
        - 使用java提供的内部函数式接口时，可以将Lambda包裹在一个`try/catch`块中
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
    List<String> list = Arrays.asList("a","b","c","d");
    
    //使用lambda表达式
    list.sort((s1, s2) -> s1.compatToIgnoreCase(s2));

    //使用方法引用
    list.sort(String::compatToIgnoreCase);
    ```
### 构造函数引用
[top](#catalog)
- 使用构造函数名称和`new`，来创建该构造函数的引用：`ClassName::new`
    - 与指向静态方法的引用类似
    - 根据所使用的函数式接口，需要有对应的构造函数，否则会产生异常
- 示例1，通过构造函数引用来实例化对象
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
- 示例2，通过构造函数引用来执行`forEach`
    ```java
    class Apple{
        int weight;

        public Apple(){
            System.out.println("this is Apple");
        }

        public Apple(Integer weight){
            this.weight = weight;
            System.out.println("this is Apple, weight = " + this.weight);
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    '}';
        }
    }
    class MyMap{
        public static <T, R> List<R> map(List<T> list, Function<T, R> f){
            List<R> result = new ArrayList<>();
            for (T e : list) {
                result.add(f.apply(e));
            }

            return result;
        }
    }

    @Test
    public void MyMapTest(){
        List<Integer> list = Arrays.asList(3,6,1,7);
        List<Apple> result = MyMap.map(list, Apple::new);
        System.out.println(result);

    }
    ```
- 使用map保存一些构造函数引用，根据需求实例化对应的对象
    ```java
    interface Fruit{}

    class Apple implements Fruit{
        int weight;

        public Apple(Integer weight){
            this.weight = weight;
            System.out.println("this is Apple, weight = " + this.weight);
        }
    }

    class Orange implements Fruit{
        int weight;
        public Orange(Integer weight){
            this.weight = weight;
            System.out.println("this is orange, weight = " + weight);
        }
    }

    class MyFruits{
        static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
        static {
            map.put("apple", Apple::new);
            map.put("orange", Orange::new);
        }

        public static void getFruit(String type, int weight){
            map.get(type.toLowerCase()).apply(weight);
        }
    }

    @Test
    public void MyFruitsTest(){
        MyFruits.getFruit("orange", 100);
    }
    ```
- 针对构造函数、数组构造函数、父类调用(super-call)的一些特殊形式的方法引用？？？

## 复合Lambda表达式的使用方法
[top](#catalog)
- 多个简单的Lambda可以复合成复杂的表达式
- 比较复合器
    - 基本的比较排序:`list.sort(comparing(Apple::getWeight));`
    - 逆序比较
        - 使用Comparator接口的默认方法`reversed`
        ```java
        list.sort(comparing(Apple::getWeight).reversed());
        ```
    - 比较器链
        - 使用默认方法`thenComparing`
        - 通过第一个条件比较，如果第一个条件的比较结果相等，再使用另一个条件进行比较
        ```java
        list.sort(comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getCountry));
        ```
- Predicate接口的复合
    - Predicate接口包括三个方法
        - negate 取反
        - and
        - or
    - 复合时，从左向右确定优先级`a.or(b).and(c)`相当于`(a||b)&&c`
    - 通过谓词可以重用已有的`Predicate`来创建更复杂谓词
        ```java
        readApple.and(a->a.getWeight() > 150)
                 .or(a-> "green".equals(a.getColor())); 
        ```
- Function接口的复合
    - Function接口提供的复合方法
        - andThen
            - 将会先应用第一个Function，在应用另一个Function
            ```java
            @Test
            public void andThenTest(){
                Function<Integer, Integer> f1 = x -> x+1;
                Function<Integer, Integer> f2 = x -> x*2;
                Function<Integer, Integer> f3 = f1.andThen(f2);
                assert(f3.apply(3) == 8);
            }
            ```
        - compose
            - `f.compose(g)`，先执行`g`，再执行`f`
            ```java
            @Test
            public void composeTest(){
                Function<Integer, Integer> f1 = x -> x+1;
                Function<Integer, Integer> f2 = x -> x*2;
                Function<Integer, Integer> f3 = f1.compose(f2);
                assert(f3.apply(3) == 7);
            }
            ```

## 类型检查&类型推断以及限制
[top](#catalog)
- Lambda表达式本身并不包含它实现了哪个函数式接口的信息
- 类型检查
    - Lambda的类型是从**使用Lambda的上下文推断出来的**
    - 上下文：接收Lambda的方法的参数，接收Lambda的局部变量
    - **目标类型**：上下文中Lambda表达式需要的类型称为目标类型
- 类型检查的过程
    - `List<Apple> result = filter(list, (Apple a) -> a.getWeight() > 150);`
    - 找出`filter`方法的声明：`filter(List<Apple> list, Predicate<Apple> p)`
    - 确定目标类型：`Predicate<Apple>`
    - 确定`Predicate<Apple>`是一个函数式接口，定义了一个`test`抽象方法
    - `test`方法描述了一个**函数描述符**，可以：接收一个`Apple`、返回一个`boolean`
    - `filter`的任何实际参数都必须匹配这个要求
        - Lambda表达式的函数签名相同，接收一个`Apple`、返回一个`boolean`
        - <lambda style="color:red">如果抽象方法中有`throws`，Lambda表达式抛出的异常也必须匹配</lambda>
- 同样的Lambda，不同的函数式接口
    - 只要接口的函数签名相同，同一个Lambda可以适用于不同的接口
    - void兼容规则：如果Lambda的主体是一个表达式，则它和一个返回void的函数描述符兼容
        ```java
        @Test
        public void voidRuleTest(){
            List<String> list = new ArrayList<>();
            list.add("aaaa");
            list.add("bbb");
            System.out.println(list);

            Predicate<String> p = s -> list.add(s); //String->boolean
            Consumer<String> b = s -> list.add(s); //String->void

            p.test("ccc");
            System.out.println(list); // ["aaaa", "bbb", "ccc"]

            b.accept("ddd");
            System.out.println(list); // ["aaaa", "bbb", "ccc", "ddd"]
        }
        ```
- 类型推断
    - java编译器会从**上下文/目标类型**推断出用什么函数式接口来配合Lambda表达式
        - 通过目标类型，可以推断出函数签名，再推断出适合Lambda的签名
        - 编译器能够了解Lambda表达式的参数类型，这样就**可以在Lambda语法中省去标注参数类型**
        ```java
        //标记参数类型
        Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

        //省略参数类型
        Comparator<Apple> c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());
        ```
    - 当Lambda只有一个参数时，可以省略`()`
        ```java
        List<Apple> result = filter(list, a -> "green".equals(a.getColor()));
        ```
- 使用局部变量
    - Lambda表达式允许使用**自由变量**，即在Lambda表达式的外部定义的变量
        - 包括局部变量和静态变量
    - 使用自由变量的称作**Lambda**
        ```java
        @Test
        public void localParamTest(){
            int a = 10;
            Runnable r = () -> System.out.println(a);
            r.run();
        }
        ```
    - 使用局部变量的限制
        - Lambda表达式引用的局部变量必须是`final`的，或只有一次赋值
        - 限制的原因（为什么只是**局部变量**）
            - 实例变量存储在堆中，局部变量保存在栈上
            - 如果Lambda可以直接访问局部变量，而且Lambda是在一个线程中使用的，则在使用Lambda线程时，可能会在变量被回收之后去访问
                - java在访问自由局部变量时，实际上是在访问它的副本，而不是访问原始变量 ??????????????
            - 这一限制不鼓励使用改变外部变量的命令式编程模型

# 流的引入

## Dish定义
[top](#catalog)
```java
public class Dish{
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type){
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public boolean isVegetarian(){
        return vegetarian;
    }

    public int getCalories(){
        return calories;
    }

    public Type getType(){
        return type;
    }

    @Override
    public String toString(){
        return name;
    }

    public enum Type {MEAT, FISH, OTHER}
}

List<Dish> menu = Arrays.asList(
    new Dish("pork", false, 800, Dish.Type.MEAT),
    new Dish("beef", false, 700, Dish.Type.MEAT),
    new Dish("chicken", false, 400, Dish.Type.MEAT),
    new Dish("fench fries", true, 530, Dish.Type.OTHER),
    new Dish("rice", true, 350, Dish.Type.OTHER),
    new Dish("season fruit", true, 120, Dish.Type.OTHER),
    new Dish("pizza", true, 550, Dish.Type.OTHER),
    new Dish("prawns", false, 300, Dish.Type.FISH),
    new Dish("salmon", false, 450, Dish.Type.FISH),
);
```

## 流的基本概念
[top](#catalog)
- 流允许以声明性方式处理数据集合，即：通过查询语句表达需求，而不是临时编写一个实现
    - 可以当作一种高级的数据集迭代器
- 流的优点
    - 声明性：代码是以声明性方式写的，说明需求，而不是说明如何实现一个操作
    - 可复合：流能够把几个基础操作连接起来，来表达复杂的数据处理流水线
    - 可并行：流可以透明地并行处理，无需写任何多线程代码
        - 因为操作都是与具体线程模型无关的高层次构建，所有其内部的实现可以说单线程，也可以是多线程

- java8中集合支持一个新的`stream`方法，它会返回一个流`java.util.stream.Stream`
- 流的定义：**从支持数据处理操作的源生成的元素序列**
    - 元素序列
        - 像集合一样，流也提供了一个接口，可以访问特定元素类型的一组有序值
        - 集合与流的目的不同
            - 集合的目的是**数据**,集合是**数据结构**，集合存在的目的是以特定的时间/空间复杂度**存储和访问元素**
            - 流的目的是**表达计算**
    - 源
        - 流会使用一个提供数据的源，包括：**集合、数组、输入/输出资源**
            - 从**有序集合**生产流时会保留原有的顺序
            - 由列表生成的流，其元素顺序与列表一致
    - 数据处理操作
        - 流的数据处理功能支持类似于数据库的操作，以及函数式编程语言中的常用操作
        - 包括：filter, map, reduce, find, match, sort
        - **流的操作可以顺序执行，也可以并行执行** (stream/parallelStream)
- 流的两个重要特点
    - 流水线
        - 很多流操作本身会返回一个流，多个操作可以进行链接，形成一个大的流水线
        - 可以在流水线中进行优化，包括：`延迟、短路` 等等
        - 流水线的操作可以看作：**对数据源进行数据库式查询**
        - **流水线本身并会不生产任何结果，相当于做了一系列的配置**
    - 内部迭代
        - 与迭代器的显示迭代不同，流的迭代实在内部进行的
 
- 示例：处理菜单
    - 数据源是：菜单List，它给流提供了一个元素序列
    - 对流的一些流处理：`filter, sorted, map, limit`， 但不包括`collect`
        - 所有的这些操作都会**返回另一个流**，这样它们就可以形成一条流水线
            - 这一条流水线**相当于一个查询**
    - `collect()`开始处理流水线，并返回结果
        - `collect()`和别的操作不一样，它**返回的不是流**，在这里返回一个`List`
        - `collect()`调用之前，没有从`menu`中选择元素，也没有任何结果产生
        - `collect()`调用之前，所有操作都处于**排队等待**的状态
        - 可以将`collect()`理解为：将流转换成其他形式
    - StreamAPI会对流水线的优化比手写更加灵活，过滤-提取-截断可以一次进行
    ```java
    List<String> lowCaloriDishesName = menu.stream()
                                           .filter(d->d.getCalories() < 400) //过滤
                                           .map(Dish::getName) //提取名称
                                           .limit(3) //只选择前3个
                                           .collect(toList()); //将结果保存到List中
    ```

## 流与集合
[top](#catalog)
- 集合与流的目的不同
    - 集合的目的是**数据**,集合是**数据结构**，集合存在的目的是以特定的时间/空间复杂度**存储和访问元素**
    - 流的目的是**表达计算**
- 流与集合的差异在于**什么时候进行计算**
    - 对于集合，每个元素必须先计算出来，才能添加到集合中，成为数据结构中的一部分
    - 对于流，流水线运行在固定的数据结构，因此**无法添加/删除元素**，流中的元素是`按需计算的`
        - `按需计算/生成`可以理解为`生产者-消费者`的关系
        - `按需计算/生成`也可以理解为**延迟创建的集合**：只有在消费者要求的时候才会计算值
- **流只能遍历一次**（这一点与迭代器相同）
    - 遍历结束后，这个流就已经被**消费掉了**
    - 如果想再次遍历，可以从数据源再获取一个流，并重新遍历一遍
        - 集合数据源可以再次遍历，如果是IO资源，可能就不行了
        ```java
        List<String> title = Arrays.asList("aaa", "bbb", "ccc");
        Stream<String> s = titile.stream();
        s.forEach(System.out::println);
        s.forEach(System.out::println); // 会引发IllegaStateException异常，因为流已经被关闭了
        ```
- 外部迭代与内部迭代
    - 外部迭代：使用`Collection`接口需要手动写迭代部分的代码
    - 内部迭代：Stream库使用 内部迭代
        - Stream库在内部实现中执行了迭代，把计算得到的值保存到了某个地方，只要提供函数就可以执行

    - 几种迭代形式
        ```java
        // 外部迭代：for-each迭代
        List<String> names = new ArrayList<>();
        for(Dish d: menu){
            names.add(d.getName());
        }

        // 外部迭代：手动调用迭代器
        List<String> names = new ArrayList<>();
        Iterator<String> a = menu.iterator();
        while(a.hasNext()){
            names.add(a.next().getName());
        }

        // 使用流
        List<String> names = menu.stream().map(Dish::getName).collection(toList());
        ```
    - 在Stream库的内部迭代可以自动选择一种适合硬件的数据表示和并行实现，即执行的过程是被优化过的
    - 如果通过`for-each`使用外部迭代，需要自行解决并行问题，优化比较困难

## 流操作
[top](#catalog)
- `java.util.stream.Stream`接口中的操作可以分为两类
    - `filter, map, limit`等，可以构成流水线、可以连接的 `中间操作`
    - `collect`等，触发流水线执行、并可以关闭流的 `终端操作`
- 中间操作
    - 只有流水线上触发了一个`终端操作`时，中间操作才会执行处理
        - 这是因为中间操作一般都可以**合并**，在终端操作时**一次性全部处理**
    - 中间操作的优化
        - `limit`和短路操作
        - 循环合并：filter和map合并到一次遍历中
        ```java
        List<String> names = menu.stream()
                                 .filter(d -> {
                                    System.out.println(d.getName());
                                    return d.getCalories() > 400;
                                  })
                                 .map(d -> {
                                    System.out.println(d.getName());
                                    return d.getName();
                                  })
                                 .limit(3)
                                 .collect(toList());
        System.out.println(names);        
        ```
- 终端操作
    - 终端操作会从流水线中生产结果，该结果不是流(可以是List，Integer，甚至是void)
    - 生成void的示例
        ```java
        menu.stream().forEach(System.out::println);
        ```
- 使用流
    - 使用流的**三个要素**
        - 一个数据源：在数据源中进行查询
        - 一个中间操作链：形成一条流水线
        - 一个终端操作
    - 流水线背后的理念类似于**构建器模式**
        - 构建器模式中有一个调用链来设置一套配置;相当于流的中间操作
        - 构建器模式会调用built方法;相当于终端操作

- 无状态和有状态
    - 无状态操作：没有内部状态
        - 像`map`,`filter`等操作会从输入流中获取每一个元素，并在输出流中得到0或1个结果
    - 有状态操作
        - 像`reduce`,`sum`,`max`等操作需要内部状态来累计结果。但不管流中有多少元素要处理，内部状态都是**有界的**
        - 像`sort`,`distinct`等操作，虽然也是中间操作，但是从流中排序和删除重复项目是都需要知道先前的历史
            - 排序需要所有元素都放入缓冲区之后才能给输出流加入一个项目，这个操作的存储要求是**无界的**，当流比较大或是无限时，就可能会有问题

- StreamAPI提供的操作

|操作|类型|返回类型|操作参数|函数描述符|
|-|-|-|-|-|
|filter|中间|Stream<T>|Predicate<T>|T -> boolean|
|distinct|中间(有状态-无界)|Stream<T>|-|-|
|skip|中间(有状态-有界)|Stream<T>|long||
|limit|中间(有状态-有界)|Stream<T>|long|-|
|map|中间|Stream<T>|Function<T,R>|T -> R|
|flatMap|中间|Stream<T>|Function<T,Stream<R>>|T -> Stream<R>|
|sorted|中间(有状态-无界)|Stream<T>|Comparator<T>|(T, T) -> int|
|anyMatch|终端|boolean|Predicate<T>|T->boolean|
|noneMatch|终端|boolean|Predicate<T>|T->boolean|
|allMatch|终端|boolean|Predicate<T>|T->boolean|
|findAny|终端|Optional<T>|-|-|
|FindFirst|终端|Optional<T>|-|-|
|forEach|终端|void|Consume<T>|T->void|
|collect|终端|R|Collector<T, A, R>||
|reduce|终端(有状态-有界)|Optional<T>|BinaryOperator<T>|(T, T)->T|
|count|终端|long|||


# 流的使用
## 筛选和切片
[top](#catalog)
- `filter`使用`Predicate`筛选
    - `filter`方法本身接受一个`Predicate`参数，并返回一个包括所有符合元素的流
    ```java
    meun.stream().filter(d -> d.getCalories() > 400).collect(toList());
    ```
- `distinct`去重
    - 根据流所生成的元素的`hashCode`和`equals`方法实现的
        ```java
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
            .filter(i -> i % 2 == 0)
            .distinct()
            .forEach(System.out::println);
        ```
- `limit(n)`截断
    - 该方法会返回一个**不超过给定长度**的流
    - `limit`可以用在有序流，也可以用在无序流
        - 有序流，则返回前n个元素
            ```java
            List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
            numbers.stream()
                .limit(3)
                .forEach(System.out::println);
            ```

        - 无序流，则结果不会以任何形式进行排列
            ```java
            Set<Integer> numbers = new HashSet<>();
            numbers.add(1);
            numbers.add(3);
            numbers.add(4);
            numbers.add(5);
            numbers.add(6);

            numbers.stream()
                .limit(3)
                .forEach(System.out::println);
            ```
- `skip(n)`跳过前n个元素
    - `skip(n)`返回一个丢弃了前n个元素的流
    - 如果`流中的元素个数 < n`，将会返回一个`空流`
    - `skip`与`limit`是互补的
        - `skip`跳过前n个
        - `limit`保留前n个
    ```java
    menu.stream().filter(d -> d.getCalories() > 300)
                 .skip(2)
                 .foreach(System.out::println);
    ```

## 映射
[top](#catalog)
- `map`对流中的每一个元素应用函数
    - `map`接受一个函数作为参数，该函数会应用到每个元素
    - `map`本身不会修改元素，而是**通过原始数据来创建新数据,即映射**
        ```java
        List<String> names = menu.stream()
                                 .map(Dish::getName) //提取名称，Stream<Dish>  -> Stream<String>
                                 .map(String::length) //获取名称的长度, Stream<String> -> Stream<Integer> (实际上是int)
                                 .forEach(System.out::println);
        ```
- `flatMap`流的扁平化/合并
    - `Arrays.stream()`接受一个数组并产生一个流
        ```java
        String[] a = {"Goodbye", "World"};
        Stream<String> streamOfWord = Arrays.stream(a);
        ```
    - 通过`flatMap`，可以将多个流进行合并
        - 即：将一个流中的每一个值都转换成另一个流，然后把所有的流连接起来成为一个流

    - 示例：统计单词数组中出现过的字母
        - 如果在一个流中使用`Arrays.stream`，会把一个流变成多个流，需要使用`flatMap`来进行合并
        - 通过`flatMap`,分割后的字符数组不是分别映射成一个流，而是全部映射成流的内容
        ```java
        List<String> words = Arrays.asList("Hello", "World"); // 数组
        List<String> result = words.stream()
             .map(w -> w.split(""))
             .flatMap(Arrays::stream)
             .distinct()
             .collect(toList());
        ```
    - 示例：组合两个列表，返回数对，并且数对的和能够整除3
        ```java
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> pairs = numbers1.stream()
                                    .flatMap(i -> numbers2.stream()
                                                          .filter(j -> (j+i) % 3 == 0)
                                                          .map(j -> new int[] {i, j}) //只需要流，在外部进行flatMap，不需要终端操作
                                                          // 与Arrays.stream()类似，将值转换为流
                                     )

                                    .collect(toList());
        ```

## 查找和匹配
[top](#catalog)
- `allMatch, anyMatch, noneMatch, findFirst, findAny`，来查看数据集中是否匹配一个给定的属性
    - 这些操作都是**短路操作**，类似与java的`&&, ||`
        - 只要有一个表达式为`false`，就可以推断出整个表达式为`false`，所以不需要计算整个表达式
        - 对于`limit`，它会创建一个给定大小的流，而不用处理流中所有的元素，可以把无限流变为有限流

- `anyMatch`
    - 检查流中是否有一个元素能匹配给定的`Predicate`
    - 返回一个`boolean`
    - `anyMatch`是一个终端操作
    - 示例：查看菜单中是否有素食
        ```java
        if (menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("has vegetarian");
        }
        ```
- `allMatch`
    - 检查流中的所有元素是否都**能匹配**给定的`Predicate`
    - 返回一个`boolean`
    - `allMatch`是一个终端操作
    - 示例
        ```java
        boolean isHealthy = menu.stream().anyMatch(d -> d.getCalories() >= 1000);
        ```

- `noneMatch`
    - 检查流中的所有元素是否都**不能匹配**给定的`Predicate`
    - 返回一个`boolean`
    - `noneMatch`是一个终端操作
    - 示例
        ```java
        boolean isHealthy = menu.stream().noneMatch(d -> d.getCalories() >= 1000);
        ```

- `findAny`
    - 可以返回流中的任意元素，可以与其他流操作结合使用
    - `findAny`是一个终端操作
    - 返回一个`Optional<T>`
    - 流水线在后台进行优化使其只需走一遍，并在利用短路找到结果时立即结束
    - 示例
        ```java
        Optional<Dish> dish = menu.stream()
                                  .filter(Dish::isVegetarian)
                                  .findAny();
        ```

- `findFirst`
    - 查找第一个元素
    - `findFirst`是一个终端操作
    - 返回一个`Optional<T>`
    - 示例
        ```java
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstValue = someNumbers.stream()
                                                  .map(x -> x * x)
                                                  .filter(x -> x % 3 == 0)
                                                  .findFirst();
        ```

## reduce归约
[top](#catalog)
- 元素求和
    - 使用`for-each`做元素求和
        - 
        ```java
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        int sum = 0;
        for (int x : numbers){
            sum += x;
        }
        ```
    - 使用`reduce(初始值，BinaryOperator<T>)`来做元素求和
        - reduce操作会考虑新值和流中的下一个元素，并产生一个新的值，直到整个流消耗完
            ```java
            List<Integer> numbers = Arrays.asList(1,2,3,4,5);
            int sum = numbers.stream()
                            .reduce(0, (a, b) -> a + b);
            ```
    - 使用`Integer`提供的求和方法进行辅助
        ```java
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        int sum = numbers.stream()
                         .reduce(0, Integer::sum);
        ```
    - 无初始值`reduce(BinaryOperator<T>)`重载
        - 返回一个`Optional<Integer>`
            - 如果是`空流`,reduce操作无法返回结果，因为没有初始值，通过`Optional`来表明结果可能不存在
    - 使用`reduce`的三参数重载
        - `<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);`
        - 需要提供：初始值、计算方法、合并方法

- 最大最小值
    - 使用`无初始值reduce`来计算最大最小值
        ```java
        Optional<Integer> max =  numbers.stream().reduce(Integer::max);
        ```
- 求元素的个数
    ```java
    //使用reduce
    int count1 = menu.stream()
                    .map(d -> 1)
                    .reduce(0, Integer::sum);
    //使用count
    int count2 = menu.stream().count();
    ```

- `reduce`相较于`for-each`的**并行化**优势
    - `reduce`的迭代被内部迭代抽象掉了，使内部实现可以选择**并行执行**reduce操作
    - `for-each`求和需要更新共享变量`sum`,不容易并行化
        - 如果加入了同步，可能会导致线程竞争抵消了并行的性能提升
        - 这种计算的并行化需要另一中方法：输入分块--分块求和--合并结果
            - 但是这种方法的代码会完全不一样
        - <label style="color:red">可变的累加器模式对于并行化来说是死路一条</label>
- `reduce`如果要并行，必须满足： TODO
    - Lambda表达式不能更改状态，如示例变量
    - 操作必须满足结合律，才可以按照任意顺序执行

## 交易员示例
[top](#catalog)
- 类定义
    ```java
    public class Trader {
        private final String name;
        private final String city;

        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader:" + name + "in" + city;
        }
    }
    ```
    ```java
    public class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }
    ```
- 测试
    ```java
    @Test
    public void test(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //(1) 找出2011年发生的所有交易，并按交易额排序(从低到高)。
        List<Transaction> c1 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        //System.out.println(c1);
        //(2) 交易员都在哪些不同的城市工作过?
        List<String> c2 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(toList());
        //System.out.println(c2);

        //(3) 查找所有来自于剑桥的交易员，并按姓名排序。
        List<Trader> c3 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> "Cambridge".equals(t.getCity()))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        //System.out.println(c3);

        //(4) 返回所有交易员的姓名字符串，按字母顺序排序。
        String c4 = transactions.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                .collect(joining());
        //System.out.println(c4);

        //(5) 有没有交易员是在米兰工作的?
        boolean c5 = transactions.stream()
                .anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
        System.out.println(c5);
        //System.out.println(c5);

        //(6) 打印生活在剑桥的交易员的所有交易额。
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        //(7) 所有交易中，最高的交易额是多少?
        Optional<Integer> maxValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(maxValue.get());

        //(8) 找到交易额最小的交易。
        Optional<Integer> minValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        System.out.println(minValue.get());
    }
    ```

## 数值流
[top](#catalog)
- 原始类型流特化
    - 三个原始类型特化流接口
        - `IntStream`
        - `DoubleStream`
        - `LongStream`
    - 特化接口会将流中的元素特化为`int`,`long`,`double`，从而避免装箱成本
    - 出现特化接口的原因：防止装箱造成的复杂性
    - 映射到数值流
        - 将流转换为特化版本的常用方法
            - mapToInt
            - mapToLong
            - mapToDouble  
        - 这些方法和map的工作方式相同，只是它们返回一个特化流，而不是`Stream<T>`
        - 特化流上的操作只能产生原始数据
            - 如IntStream的map操作接受的Lambda必须接受int并返回int
        - 转换后会提供`sum`,`max`,`min`,`average`等方法            
        - 示例：求和
            - 如果是空流，`sum`默认返回`0`
            ```java
            int sumValue = menu.stream()
                            .mapToInt(Dish::getCalories)
                            .sum();
            ```
    - 转换回对象流 
        - `boxed`方法，将原始流转换成一般流
            - 示例
                ```java
                IntStream intStream = menu.stream().mapToInt(Dish::getCaloried);
                Stream<Integer> stream = intStream.boxed();
                ```
        - `mapToObj`方法，返回一个对象值流
    - 默认值`Optional`的特化版本
        - `OptionalInt`,`OptionalDouble`,`OptionalLong`
        - 通过`Optional`的特化版本来表示值是否存在
        ```java
        OptionalInt max = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        // 如果值不存在，则使用默认值1
        System.out.println(max.orElse(1));
        ```
- 数值范围
    - java8引入了两个可用于`IntStream`和`LongStream`的静态方法，来生成范围
        - `range(起始值, 结束值)`，不包含结束值
        - `rangeClosed(起始值, 结束值)`，包含结束值

    ```java
    long count = IntStream.rangeClosed(1, 100)
                          .filter(i -> i % 2 == 0)
                          .count();
    ```

- 数值流应用：勾股数
    - `t[2]%1 == 0` 保证结果为整数 (1.0可以，1.1不可以)
    ```java
    IntStream.rangeClosed(1, 100).boxed()
            .flatMap(a -> IntStream.rangeClosed(1, 100)
                                    .mapToObj(b-> new double[]{a, b, Math.sqrt(a*a + b*b)})
                                    .filter(t -> t[2]%1 == 0)
            ).limit(5)
            .forEach(t-> System.out.println(Arrays.toString(t)));
    ```

## 构建流
[top](#catalog)
- 可以构建流的数据源
    - 值序列
    - 数组
    - 文件数据
    - 函数
- 由值创建流
    - `Stream.of`，可以通过显示值创建一个流
        - **该方法可以接受任意数量的参数**
        - 示例：创建一个字符串流，并转换为大写
            ```java
            Stream.of("aaa", "bbb","ccc")
                    .map(String::toUpperCase)
                    .forEach(System.out::println);
            ```
    - `Stream.empty()`获得一个空流
        ```java
        Stream<String> emptyStream = Stream.empty();
        ```
- 由数组创建流
    - `Arrays.stream`，从数组创建一个流
        - 方法接受一个数组作为参数
        - 示例：将`int[]`转换为流，并求和
            ```java
            int[] a = new int[]{1,2,3,6,7,8};
            int sum = Arrays.stream(a).sum();
            System.out.println(sum);

            ```
- 由文件生成流
    - `java.nio.file.Files`中的很多静态方法都会返回一个流
- 由函数生成流：创建无限流
    - 通过两个静态方法从函数生成流
        - `Stream.iterate(初始值, UnaryOperator<T>)`
        - `Stream.generate(Supplier<T>)`
        - 原始类型流特化接口`IntStream`、`DoubleStream`、`LongStream`中也有相同的方法，但是对应的函数也需要使用特化的函数式接口

    - **无限流不能做排序或归约，因为所有元素都需要处理，但是永远无法完成**
    - 通过`iterate`、`generate`会用给定的函数创建值，可以无穷尽的计算下去，需要`limit(n)`来对流进行限制
    - `iterate`
        - 示例：
            ```java
            Stream.iterate(0, n->n+2).limit(10).forEach(System.out:println);
            ```
        - 示例：斐波那契数列
            ```java
            //0,1,1,2,3,5,8,13,21
            //0,1为起始数
            Stream.iterate(new int[]{1, 1}, n-> new int[]{n[1], n[0]+n[1]})
                .limit(20)
                .forEach(n->System.out.println(Arrays.toString(n)));
            ```
    - `generate`
        - 示例:处理随机值
            ```java
            Stream.generate(Math::random)
                  .limit(5)
                  forEach(System.out::println)
            ```
        - 示例：内部有可变状态的**斐波那契数列**
            - 不使用lambda，直接使用函数式接口的匿名类对象，并在对象内部保存可变状态
            ```java
            IntSupplier fib = new IntSupplier(){
                private int previous = 0;
                private int current = 1;
                public int getAsInt(){
                    int oldPrevious = current;
                    int next = current + previous;
                    previous = current;
                    current = next;
                    return oldPrevious;
                }
            }

            int[] ints = IntStream.generate(fib).limit(6).toArray();
            System.out.println(Arrays.toString(ints));
            ```

# 用流收集数据

## Collector收集器
[top](#catalog)
- 传给`collect`的参数需要是一个`Collector`
    - 对流做`collect`方法将对流中的元素触发一个归约操作，由`Collector`来提供参数化
    - `Collector`会对元素应用一个转换函数，经常是不体现任何效果的恒等转换，并将结果积累在一个数据结构中，再产生最终输出
- 预定义收集器
    - 都是`java.util.stream.Collectors`类提供的工厂方法创建的收集器
    - 预定义收集器主要提供了三大功能：
        - 将流元素归约和汇总为一个值
        - 元素分组
        - 元素分区
- `Collector`在某种程度上比`Stream`接口上直接提供的方法用起来更复杂，但是能提供更改水平的抽象和概括，也更容易重用和自定义
    - 因为函数式编程本身就就提供了很多种方法来执行同一个操作
    - 应该为问题寻找不同的解决方案，并选择**最专门化的**
        - 要同时兼顾可读性和性能
    - 
## 归约和汇总
[top](#catalog)
- 计算元素的个数：`counting`
    - 示例：计算菜单里有多少中菜
        ```java
        // 使用counting工厂方法返回的收集器
        long dishCount1 = menu.stream().collect(Collectors.counting());

        // 直接使用count()
        long dishCount2 = menu.stream().count();
        ```
- 求最大值、最小值
    - 可以使用两个收集器
        - `Collectors.maxBy(Comparator<T>)`
        - `Collectors.minBy(Comparator<T>)`
        - 返回值都是`Optional<T>`，防止空流
    - 示例：查找流中的最大值、最小值
        ```java
        Comparator<Dish> com = Comparator.comparingInt(Dish::getCalories); //创建比较器
        Optional<Dish> maxDish = menu.stream().collect(maxBy(com));
        ```
- 汇总
    - 求和：`Collectors.summingInt(T->int)`, `summingLong`, `summingDouble`
        - 返回一个收集器`Collector`
        - 遍历的初始值默认是`0`
        - 示例：计算菜单列表的热量
            ```java
            IntSummaryStatistics collect = menu.stream().collect(summingInt(Dish::getCalories));
            ```
    - 计算平均值`Collectors.averagingInt`, `averagingLong`, `averagingDouble`
        ```java
        double s = menu.stream().collect(averagingInt(Dish::getCalories));
        ```
    - 一次操作分别执行：求和，最大最小值，均值  `Collectors.summarizingInt`, `summarizingLong`,  `summarizingDouble`
        - 返回`IntSummaryStatistics`，`LongSummaryStatistics`，`DoubleSummaryStatistics`对象
            - 通过`getter`来访问结果
        ```java
        int s = menu.stream().collect(summarizingInt(Dish::getCalories));

        //IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}
        ```
- 连接字符串：`joining`
    - `joining`返回的收集器会对流中每一个对象应用`toString`，将得到的所有字符串连接成一个字符串
        - 如果类内部有`toString`方法，则不用对流进行转换，直接对原始流应用`joining`即可
            ```java
            String result = menu.stream().collect(joining());
            ```
    - `joining`内部使用了`StringBuilder`来连接字符串
    - `joining(String split)`, 通过重载方法来提供连接字符串的分隔符
            ```java
            String result = menu.stream().collect(joining(", "));
            ```

- 广义的归约汇总 : `Collectors.reducing`
    - 上面这些收集器都是`Collectors.reducing`工厂方法定义的归约过程的特殊情况
    - 重载：`reducing(起始值, 转换函数, BinaryOperator)`
        - 三个参数
            - 起始值，也是空流的返回值
            - 转换函数，将对象转换为数值
            - `BinaryOperator`，对数值进行求和
        
        ```java
        int total = menu.stream().collect(reducing(0, Dish::getCalories, (i, j)->i+j ));
        ```

    - 单参数重载：`reducing(BinaryOperator)`
        - 可以看作三个参数的特殊情况
        - 起始值：默认使用流中的第一个项目作为起始值
        - 恒等函数作为转换函数：`x=y`
        - 因为该重载没有起始值，所以在`collect()`中使用时，会返回`Optional<T>`

    - 通过`reducing`来做`collect`
        - 使用上的问题
            - `reducing`和`collect`的语义区别
                - `reduce`是为了把两个值结合起来生产一个新的值
                - `collect`是为了改变容器，积累需要输出的结果
            - 虽然可以实现`collect`的功能，但却是在滥用`reduce`
                - 使用过程中修改了，作为累加器的List
                - 无法正常执行并行处理
                    - 多个线程同时操作同一个`List`可能会破坏`List`本身
                    - 这种情况下如果想要线程安全就需要每次都分配一个新的`List`，但是对象分配会影响性能
            ```java
            Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
            List<Integer> numbers = stream.reduce(
                                    new ArrayList<Integer>(),
                                    (List<Integer> l, Integer e) -> {l.add(e); return l;}, //l是前一次的累积结果
                                    (List<Integer> l1, List<Integer> l2) -> {l1.addAll(l2); return l1;}
            )
            ```

    - 收集框架的灵活性：以不同的方法执行同样的操作
        - 简化求和
        - 归约过程 ![归约过程](./imgs/inAction/归约过程.png)
            ```java
            // 简化前
            int total = menu.stream().collect(reducing(0, Dish::getCalories, (i, j)->i+j ));
            // 简化后
            int total = menu.stream().collect(reducing(0, Dish::getCalories, Integer:sum);
            ```
        - `stream`内部利用三参数`reducing`来实现`counting`
            - 在内部，`reducing`会将每一个元素都转换成一个值为1的`Long`对象，然后在相加
                ```java
                public static <T> Collector<T, ?, Long> counting() {
                    return summingLong(e -> 1L);
                }
                ```
    - 根据情况选择最佳解决方案
        - 收集器内部很多处理都源于`reducing`，需要根据情况决定如何使用

## 对收集器的结果进行转换
[top](#catalog)
- 使用`Collectors.collectingAndThen(需要转换的收集器, 转换函数)`
    - 该方法会返回另一个收集器，相当于：用`转换函数`包装过的收集器
    ```java
    Map<Dish.Type, Dish> result = menu.stream().collect(
        groupingBy(
            Dish::getType,
            collectingAndThen(maxBy(comparingInt(Dish::getCalories), Optional::get)
        )
    );
    ```

## 分组
[top](#catalog)
- 使用`groupingBy(分组函数)`进行简单分组
    - `分组操作`的结果是一个`Map`
    - 分组函数的**返回值**作为`Key`，把流中所有具有这个分类值的项目的列表作为对应的`Value`
        - `groupingBy`在应用分组函数后，**只有第一次在流中找到某个键对应的元素时**才会把`Key`加入分组Map中

    - 示例：对菜单按照类型分组
        - 分组流程 ![分组流程](./imgs/inAction/对菜单按照类型分组.png)
        ```java
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType))
        ```

    - 示例：根据菜的热量进行分组
        - 因为`根据热量对菜的类型进行划分`这个处理并没有写在类中，所以需要提供一个`Lambda表达式`
        
        ```java
        enum CaloricLevel {DIET, NORMAL, FAT}

        Map<CaloricLevel, List<Dish>> result = menu.stream().collect(groupingBy(
            dish -> {
                if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                else return CaloricLevel.FAT;
            }
        ));
        ```
    - `groupingBy(分组函数)`内部是`groupingBy(分组函数, toList())`
- 多级分组
    - `groupingBy(分组函数1, groupingBy(分组函数2))`
        - 即多个分组收集器的嵌套
    - n级分组最后会得到一个代表n级树形结构的`n级Map`
    - 示例：对菜单按照 类型和热量 进行多级分组
        ```java
        enum CaloricLevel {DIET, NORMAL, FAT}

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> result = menu.stream().collect(groupingBy(
            Dish::getType,
            groupingBy(
                dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }
            )
        ));
        ```
        
- 按子组收集数据
    - 传递给第一个`groupingBy`的第二个收集器不一定是另一个`groupingBy`，可以是其他收集器，来完成不同的任务
    - `groupingBy(分组函数, counting())`，对每个分组中的数量进行统计
        - 示例：统计每个类型下菜的数量
            ```java
            Map<Dish.Type, Long> result = menu.stream().collect(
                groupingBy(Dish::getType, counting())
            );
            ```
    - `groupingBy(分组函数, maxBy())`，计算每个分组中的最大值
        - 示例：统计每个类型下热量最高的菜
            - 此处的`Optional`类型实际上没有什么用，因为不存在实际`Value`的`Key`不会加入到`Map`中
                ```java
                Map<Dish.Type, Optional<Dish>> result = menu.stream().collect(
                    groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))
                );
                ```
            - 直接对`maxBy`的结果进行转换
                ```java
                Map<Dish.Type, Dish> result = menu.stream().collect(
                    groupingBy(
                        Dish::getType,
                        collectingAndThen(maxBy(comparingInt(Dish::getCalories), Optional::get)
                    )
                );
                ```
            - 示例的工作方式
                - 一级收集器
                    - 原始流根据分组函数进行划分，分为多个子流，有二级收集器进行处理
                - 二级收集器
                    - 由`maxBy`对各子流进行处理
                    - `Optional::get`将结果进行转换
                    - 二级收集器的结果作为一级收集器中各分组的值
                - 嵌套关系：![嵌套关系](./imgs/inAction/多级分组的嵌套关系.png)

    - `groupingBy(分组函数, summingXXX())`, 分组求和
        - 虽然使用了原始流，但是结果只能保存到包装类型中
        ```java
        Map<Dish.Type, Integer> result = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)) 
        ```
    - `groupingBy(分组函数, mapping(转换函数，收集器))`, 让接受特定类型元素的收集器适应不同类型的对象
        - 示例： 查看各菜单类型下，都有哪些`CaloricLevel`
            ```java
            enum CaloricLevel {DIET, NORMAL, FAT}

            Map<Dish.Type, Set<CaloricLevel>> result = menu.stream().collect(groupingBy(
                Dish::getType, mapping(
                    dish->{
                        if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT;
                    },
                    toSet()
                )
            ));
            ```
        - 示例： 查看各菜单类型下，都有哪些`CaloricLevel`，通过`toCollection()`来指定生成的结果类型
            ```java
            public enum CaloricLevel {DIET, NORMAL, FAT};
            Map<Dish.Type, Set<CaloricLevel>> result = menu.stream().collect(groupingBy(
                Dish::getType, mapping(
                    dish->{
                        if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT;
                    },
                    toCollection(HashSet::new)
                )
            ));
            ```

## 分区
[top](#catalog)
- 分区是分组的特殊情况
    - `partitioningBy(Predicate分区函数)`
    - 由一个`Predicate`作为分类函数，称为`分区函数`
    - `分区函数`的返回值是`true/false`，所以`Map`的`Key`是`Boolean`型的
- 示例：找出菜单中的所有素食
    - 使用分区
        ```java
        Map<Boolean, List<Dish>> result = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        List<Dish> ds = result.get(true);
        ```
    - 使用流水线
        ```java
        List<Dish> result = menu.stream().filter(Dish::isVegetarian).collect(toList());
        ```
- 分区的优势
    - 分区返回了`true`和`false`两组流的列表
- `partitioningBy`和其他收集器进行组合
    - `partitioningBy(Predicate分区函数, groupingBy(分组函数))`， 在分区中再分组
        - 示例: 素食/非素食 下的各类菜单中有哪些菜
            ```java
            Map<Boolean, Map<Dish.Type, List<Dish>>> result = menu.stream().collect(partitioningBy(
                Dish::isVegetarian,
                groupingBy(Dish::getType)
            ));
            ```
    - `partitioningBy(Predicate分区函数, partitioningBy)`，可以实现多级分区
        - 示例:
            ```java
            menu.stream().collect(partitioningBy(
                Dish::isVegetarian,
                partitioningBy(dish -> dish.getCalories() > 500)
            ));
            ```
    - 将数字按质数/非质数分区
        ```java
        // 只测试小于等待测数平方根的因子
        public boolean isPrime(int candidate) {
            int candidateRoot = (int) Math.sqrt((double)candidate);
            // 如果能被前面的某个整除，则不是质数
            return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
        }

        // 对流中的数字按照质数/非质数分区
        IntStream.rangeClosed(2, n).boxed().collect(
            partitioningBy(candidate -> isPrime(candidate))
        );
        ```

## Collectors类的静态工厂方法
[top](#catalog)

|工厂方法|返回类型|用途|使用示例|
|-|-|-|-|
|-|-|-|Stream<T> menuStream = menu.stream()|
|toList|List<T>|将流中的元素收集到一个List|List<Dish> result = menuStream.collect(toList())|
|toSet|Set<T>|将流中的元素收集到一个List，去重|Set<Dish> result = menuStream.collect(toSet())|
|toCollection|Collection<T>|对每个元素使用指定的方法，创建并收集到集合中|Collection<Dish> result = menuStream.collect(toCollection(), ArrayList::new())|
|counting|Long|计算流中的元素个数|long result = menuStream.collect(count())|
|summingInt|Integer|对各元素的求和项进行求和|int result = menuStream.collect(summingInt(Dish::getCalories))|
|averagingInt|Double|计算流中Interger属性的平均值|double result = menuStream.collect(averagingInt(Dish::getCalories))|
|summarizingInt|IntSummaryStatistics|同时统计所有元素的：最大值、最小值、求和、平均值|IntSummaryStatistics resutlt = menuStream.collect(summarizingInt(Dish::getCalories))|
|joining|String|对流中的每个元素调用`toString()`，并连接成一个字符串|String result = menuStream.collect(joining(", "))|
|maxBy|Optional<T>|返回流中的最大值，空流返回`Optional.empty()`|Optional<Dish> result = menuStream.collect(maxBy(comparingInt(Dish::getCalories)))|
|minBy|Optional<T>|返回流中的最小值，空流返回`Optional.empty()`|Optional<Dish> result = menuStream.collect(minBy(comparingInt(Dish::getCalories)))|
|reducing|T||int result = menuStream.collect(reducing(0, Dish::getCalories, Integer::sum))|
|collectingAndThen|转换函数返回的类型|包装另一个收集器，并对其结果调用转换函数|int result = menuStream.collect(collectingAndThen(toList(), List::size))|
|groupingBy|`Map<K, List<T>>`|对流进行分组|`Map<Dish.Type, List<Dish>> result = menuStream.collect(groupingBy(Dish::getType))`|
|partitioningBy|`Map<Boolean, List<Dish>`|对流进行分区|`Map<Dish.Type, List<Dish>> result = menuStream.collect(partitioningBy(Dish::isVegetarian))`|

## 收集器接口
### Collector接口的定义
[top](#catalog)
- 接口方法定义
    ```java
    public interface Collector<T, A, R> {
        Supplier<A> supplier();
        BiConsumer<A, T> accumulator();
        BinaryOperator<A> combiner();
        Function<A, R> finisher();
        Set<Characteristics> characteristics();
    }
    ```
- 三个泛型
    - `T`是流中要收集的项目的泛型
    - `A`是累加器的类型，累加器是在收集过程中用于累计部分结果的对象
    - `R`是收集操作得到的对象的类型（不一定是集合）

- `Supplier<A> supplier();`，建立新的结果容器
    - 调用时，会创建一个**空的累加器实例**，供数据收集过程使用
- `BiConsumer<A, T> accumulator();`，将元素添加到结果容器
    - 该方法会返回**归约操作的函数**
    - 归约函数的参数
        - 参数1：保存结果的累加器
        - 参数2：第n个元素本身
- `Function<A, R> finisher();`，对结果容器应用最终转换
    - 变量完流后，调用`finisher`，返回一个函数来将累加器对象转换为整个集合操作的最终结果
- 通过`supplier`, `accumulator`, `finisher`三个操作就可以完成顺序归约
    - 基本的归约流程 ![基本的归约流程](./imgs/inAction/基本的归约流程.png)

- `BinaryOperator<A> combiner();`，合并两个结果容器
    - 返回一个函数，该函数定义了：对流的各个子部分进行并行处理时，各个子部分的累加器要如何合并
        - `toList`的`combiner`函数:
            ```java
            (left, right) -> { left.addAll(right); return left; }
            ```
    - 并行处理的归约过程
        ![图](./imgs/)

- `Set<Characteristics> characteristics();`,
    - 返回一个不可变的`Characteristics`集合
        - `Characteristics`定义了收集器的行为，包括能否并行，以及如何优化
        - `Characteristics`是一个包含三个项目的枚举
            - `UNORDERED`，归约结果不受流中项目的遍历和累计顺序的影响，**即无序的**
            - `CONCURRENT`，`accumulator`函数可以从多个线程同时调用，且该收集器可以并行归约流
                - 如果收集器没有标为`UNORDERED`，那它**仅在用于无序数据源时才可以并行归约**，
            - `IDENTITY_FINISH`，表明`finisher`返回的是一个`恒等函数`，可以跳过
                - 这种情况下，累加器的结果将直接作为归约的结果

### 自定义ToListCollector收集器
[top](#catalog)
- 自定义收集器
    - 自定义列表收集器
        ```java
        public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
            // 集合操作的起始点
            @Override
            public Supplier<List<T>> supplier() {
                return ArrayList::new;
            }

            //累加操作
            @Override
            public BiConsumer<List<T>, T> accumulator(){
                return List::add;
            }

            @Override
            public BinaryOperator<List<T>> combiner(){
                return (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                };
            }

            @Override
            public Function<List<T>, List<T>> finisher(){
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics(){
                // 结果需要保证顺序，所以不是：UNORDERED的
                // finsher是一个恒等函数
                // 仅在数据源时无序时做并行处理
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT,
                                                        Collector.Characteristics.IDENTITY_FINISH));
            }
        }
        ```

    - 收集器的调用
        ```java
        List<Dish> result = menu.stream().collect(new ToListCollector<Dish>());
        ```

- 通过`collect`的重载来完成收集处理
    - `collect`的重载
        - 这种重载默认的`Characteristics`是：`CONCURRENT`, `IDENTITY_FINISH`
        - 重载所使用的`combiner`与接口中的`combiner`是不同的
            - 接口中的是：`BinaryOperator<A> combiner()`
                - 函数需要返回一个结果
            - 重载中使用的是：`BiConsumer<R, R> combiner`
                - 函数不需要返回结果
            ```java
            <R> R collect(Supplier<R> supplier,
                    BiConsumer<R, ? super T> accumulator,
                    BiConsumer<R, R> combiner);
            ```
    - 调用
        ```java
        List<Dish> result = meun.stream().collect(
            ArrayList::new,
            List::add,
            List::addAll
        );
        ```

## 开发收集器以获得更好的性能
[top](#catalog)
- 再讨论`将数字按质数/非质数分区`
    - 只测试小于等于测数平方根的因子，避免遍历[2, n-1]个数来提供性能
    ```java
    // 只测试小于等于测数平方根的因子，避免遍历[2, n-1]各数来提供性能
    public boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double)candidate);

        // 如果能被前面的某个整除，则不是质数
        return IntStream.rangeCloser(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    // 对流中的数字按照质数/非质数分区
    public Map<Boolean, List<Integer>> partitionPrime(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(
                    partitioningBy(candidate -> isPrime(candidate))
            );
    }
    ```
- 优化方法：仅用质数做除数
    - 只看被测试的数**是否能够被质数整除**
        - 作为除数的质数都小于等于当前数的平方根
    - 如果只使用java框架提供的方法，在收集的过程中无法访问部分结果

- 通过当前测试的数值来构造:`质数除数列表`
    ```java
    // 给定一个数，返回小于该数平方根的质数列表
    // 在迭代过程中，通过p来判断当前元素是否满足条件
    // 如果不满足了，则对列表进行截取并返回
    // 如果全部满足，则将整个list返回
    public static<A> List<A> takeWhile(List<A> list, Predicate<A> p){
        int i = 0;
        for (A item : list){
            if (!p.test(item)){
                // 如果不满足了，则对列表进行截取并返回
                return list.subList(0, i);
            }
            i++;
        }

        //如果全部满足，则将整个list返回
        return list;
    }
    ```

- 定义判断质数的方法
    - 将`迭代质数除数列表`作为数据源，并进行流处理
    ```java
    /*
    @param primes 质数列表
    @param candidate 当期要检测的数值
    */
    public static boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int) Math.sqrt((double)candidate);
        return takeWhile(primes, i->i<candidateRoot) //制作数据源
                .stream()                            //流水线
                .noneMatch(i -> candidate % i == 0); //判断
    }
    ```
- 自定义收集器来进行质数/非质数的分区
    ```java
    // 数据源是List<Integer>, 累加器和最终结果都是分区类型：Map<Boolean, List<Integer>>
    // true=质数列表，false=非质数列表
    // public interface Collector<T, A, R>
    public class PrimeNumbersCollector 
            implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier(){
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }}; // 双{{}} ???????????
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator(){
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                // 先从Map中获取ture部分/质数部分的List，作为参数，使用isPrime来验证当前数是否为质数
                acc.get(isPrime(acc.get(true), candidate))
                    .add(candidate);
            };
        }

        // 定义并行处理的方法
        // 但是实际上这个收集器本身是不能并行运行的，因为该算法本身是顺序的，所以永远都不可能调用该方法
        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            // 需要做的只是合并两部分的结果
            return (Map<Boolean, List<Integer>> map1,
                    Map<Boolean, List<Integer>> map2) -> {
                        map1.get(true).addAll(map2.get(true));
                        map1.get(false).addAll(map2.get(false));
                        return map1;
                    };
                    
            // 由于该方法永远都不可能被调用，更好的处理方法是引发异常
            // throw new UnsupportedOperationException();
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            // 返回的结果不需要转换
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            // 该处理必须是顺序执行的，且无法并行处理
            // 返回结果不需要进行转换
            return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        }
    }
    ```
- 调用自定义收集器
    ```java
    public Map<Boolean, List<Integer>> partitionPrimeWithConsume(int n) {
        return IntStream.rangeClosed(2, n)
                                    .boxed()
                                    .collect(new PrimeNumbersCollector());
    }
    ```


# 并行处理数据与性能
## 并行流
[top](#catalog)
- 通过对数据源调用`parallelStream()`，将集合转换为并行流
- 处理无限数字集的求和
    - 使用`stream`做顺序流处理
        ```java
        public static long sequentialSum(long n) {
            return Stream.iterate(1L, i -> i+1)
                            .limit(n)
                            .reduce(0, Long::sum);
        }
        ```
    - 使用`stream`，相当于在做，
        ```java
        public static long iterativeSum(long n) {
            long sum = 0;
            for(long i = iL; i<=n; i++) {
                sum += i;
            }

            return sum;
        }
        ```
    - 使用`parallelStream`做并行流处理
        ```java
        public static long parallelSum(long n) {
            return Stream.iterate(1L, i -> i+1)
                            .limit(n)
                            .parallel() //转换为并行流
                            .reduce(0, Integer::sum);
        }
        ```
        - 归纳操作的基本流程：![并行归纳操作](./imgs/inAction/并行归纳操作.png)
- `parallel()`和`sequential()`
    - 两个方法实际上没有对前面一系列方法的处理方式做管理，只是设定了一个`boolean标志`
    - 只有**最后调用**的`parallel()`或`sequential()`会生效

- 测试流性能
    - 测试代码
        ```java
        class ParallelStream{
            // 测试方法
            public static long measureSunPerf(Function<Long, Long> adder, long n){
                long fastest = Long.MAX_VALUE;
                for(int i = 0; i < 10; i++){
                    long start = System.nanoTime();
                    long sum = adder.apply(n);
                    long duration = (System.nanoTime() - start) / 1_000_000;
                    System.out.println("Result: " + sum);
                    if (duration < fastest) fastest = duration; //去n此测试的最大值
                }

                return fastest;
            }

            public static long sequentialSum(long n) {
                return Stream.iterate(1L, i -> i+1)
                                .limit(n)
                                .reduce(0L, Long::sum);
            }

            public static long iterativeSum(long n) {
                long sum = 0;
                for(long i = 1L; i<=n; i++) {
                    sum += i;
                }

                return sum;
            }

            public static long parallelSum(long n) {
                return Stream.iterate(1L, i -> i+1)
                                .limit(n)
                                .parallel() //转换为并行流
                                .reduce(0L, Long::sum);
            }
        }

        class ParallelStreamTest{
            @Test
            public void method(){
                System.out.println(ParallelStream.measureSunPerf(ParallelStream::sequentialSum, 10_000_000));
                System.out.println(ParallelStream.measureSunPerf(ParallelStream::iterativeSum, 10_000_000));
                System.out.println(ParallelStream.measureSunPerf(ParallelStream::parallelSum, 10_000_000));
            }
        }
        ```
    - 测试结果的分析
        - `for`循环最快，`parallel`最慢
        - 为什么并行变慢了
            - `iterate`生产的是装箱对象，必须拆箱成数字才能用
            - 很难把`iterate`分成多个独立块来并行执行
                - `iterate`很难分割成能够独立执行的小块，因为**每次应用这个函数都要依赖前一次应用的结果**
                - 整个数据源在开始时无法做分割，所以无法有效的使用并行处理
                - 虽然使用`parallel`将流标记为并行，但实际上是**给顺序处理增加了开销**
                    - 除了任务分配，每次还需要将求和操作分到一个不同的线程上
- 使用`LongStream`来改进
    - 使用原始类型特化流`LongStream`来避免装箱和拆箱的操作
    - 使用`LongStream.rangeClosed`
        - 直接生成`long`型的数字
        - 生成的数字范围可以拆分成独立的小块，便于并行
    ```java
    class ParallelStream{
        ...

        //顺序处理
        public static long rangedSum(long n) {
            return LongStream.rangeClosed(1L, i -> i+1)
                            .limit(n)
                            .reduce(0, Integer::sum);
        }

        //并行处理
        public static long parallelRangedSum(long n) {
            return LongStream.rangeClosed(1L, i -> i+1)
                            .limit(n)
                            .parallel()
                            .reduce(0, Integer::sum);
        }
    }
    ```

- 并行化本身的代价
    - 并行化过程：
        - 对流做递归划分
        - 把每个子流的归纳操作分配到不同的线程
        - 把子流的操作结果合并成一个值
    - **需要保证在内核中并行执行工作的时间比在内核之间传输数据的时间长**
        - 多个内核之间移动数据的代价可能比想象的要大
    - 很多情况下不可能或不方便并行化，使用之前必须确保使用得当


- 使用并行流产生错误的原因：**使用的算法改变了某些共享状态**
    - 如果使用同步来修复问题，就失去了并行的意义
- 如何使用并行流
    1. 避免装箱和拆箱： 使用原始类型流来避免装箱和拆箱
    2. 有些操作本身在并行流上的性能就比顺序流差
        - 如`limit`、`findFirst`等依赖于元素顺序的操作，在并行流上的执行代价非常大
        - `findAny`比`findFirst`更好，因为它不一定要按照顺序来执行
        - 可以调用`unordered`方法来把有序流变成无序流
            - 变成无序流之后，再调用`limit`的效率更高
    3. 考虑流水线的总计算成本
        - `N`是流中元素的总数, `Q`是一个元素通过流水线的大致处理成本
        - 总成本 = `N * Ｑ`
        - 如果`Q`比较高，则使用并行流能提高性能的可能性比较大
    4. **数据量小，并行流不是很好**
    5. 需要考虑流的数据结构是否易于分解
        - 如`ArrayList`的拆分效率比`LinkedList`高很多
            - 因为`ArrayList`不需要遍历就可以`平均拆分`
    6. 流自身的特点、中间操作修改流的方式，**可能会改变分解过程的性能**
        - 一个`sized`流可以分成大小相等的两部分，每部分可以高效的并行处理
        - `filter`操作可能会丢弃元素，丢弃的数量未知，会导致流本身的大小未知
    7. `combiner`合并操作的代价
        - 如果`combiner`合并两个结果的代价很大，就可能超过并行流得到的性能提升

- 一些数据源的可分解性
    - 易于分解的结构更适合并行处理

    |数据源|可分解性|
    |-|-|
    |ArrayList|极佳|
    |LinkedList|差|
    |IntStream.range|极佳|
    |Stream.iterate|差|
    |HashSet|好|
    |TreeSet|好|

## 分支合并框架
[top](#catalog)
- 分支合并框架的目的
    1. 以递归的方式将可以并行的任务拆分成更小的任务
    2. 将每个子任务的结果合并，得到总体结构
- 该框架是`ExecutorService`接口的一个实现
    - `ExecutorService`将子任务分配给线程池中的工作线程
- 使用`RecursiveTask`
    - 如果要把任务提交到这个池，必须创建`RecursiveTask<R>`的一个子类
        - `R`是并行化任务产生的结果类型
        - 如果任务不返回结果，则是`RecursiveAction`类型
    - 定义`RecursiveTask`，只需要实现`protected abstract R compute();`
        - 该方法定义了
            1. 将任务拆分成子任务的逻辑
            2. 无法再拆分或不方便再拆分时，产生单个子任务结果的逻辑

- 使用分支合并框架执行并行求和
    - 实现分支合并框架
        ```java
        class ForkJoinSumCalculator extends java.util.concurrent.RecursiveTask<Long> {
            private final long[] numbers; // 需要求和的数组

            // 子任务处理是的起始和终止index
            private final int start; 
            private final int end;

            public static final long THRESHOLD = 10_000; // 数组拆分的最小值

            public ForkJoinSumCalculator (long[] numbers) {
                this(numbers, 0, numbers.length);
            }

            //通过私有构造来创建对象
            private ForkJoinSumCalculator (long[] numbers, int start, int end) {
                this.numbers = numbers;
                this.start = start;
                this.end = end;
            }

            private long computeSequentially() {
                long sum = 0;
                for (int i = start; i <end; i++){
                    sum += numbers[i];
                }
                return sum;
            }

            @Override
            protected Long compute() {
                int length = end - start; // 求当前任务负责的块大小

                // 终止拆分，进行计算
                if (length < THRESHOLD) { 
                    return computeSequentially();
                }

                // 继续拆分，拆成两半：左节点和右节点
                ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start+length/2);
                // 利用另一个ForkJoinPool线程异步执行左节点的任务
                leftTask.fork();
                
                ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start+length/2, end);
                // 同步执行右节点的任务，可能会发生进一步的递归划分
                Long rightResult = rightTask.compute();

                //等待左节点的任务结束
                Long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
        ```
    - 使用自定义`RecursiveTask`，来并行计算前n个数，并进行测试
        ```java
        class ParallelStream{
            ...
            public static long forkJoinSum(long n) {
                long[] numbers = LongStream.rangeClosed(1, n).toArray();
                ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
                return new ForkJoinPool().invoke(task); // 创建线程池并执行
            }
        }

        class ParallelStreamTest{
            @Test
            public void method(){
                ...

                System.out.println(ParallelStream.measureSunPerf(ParallelStream::forkJoinSum, 10_000_000));
            }
        }
        ```
- `ForkJoinPool`, 分支合并框架使用线程池
    - 在实际应用时，使用多个`ForkJoinPool`是无意义的
    - 一般情况，应该使用单例：实例化一次，并保存在静态字段中，并在任意位置重用
    - 无参构造器:`ForkJoinPool()`
        - 默认使用JVM能够使用的所有处理器
            - 即使用：`Runtime.availableProcessors`的返回值来决定线程池使用的线程数
                - 该方法返回的是**可用内核**的数量，**包括超线程生成的虚拟内核**

- 合理使用分支合并框架
    - `join()`，阻塞调用者，直到该任务完成
        - 所以应该在所有任务都启动之后，再使用该方法
    - 不应该在`RecursiveTask`内部使用`ForkJoinPool`的`invoke`方法，应该调用子任务的`compute`进行计算，或者调用`fork`方法将任务放到其他线程执行
        - 只应该在顺序代码中应用`invoke`来计算
    - `fork`会将任务排进`ForkJoinPool`中，但是调用该方法的效率要比`compute`要低
        - 在有n个子任务时，可以将`n-1`个任务放入`ForkJoinPool`中，剩余的一个任务在当前线程中执行，来重用当前线程，避免在`ForkJoinPool`多启动一个线程
    - 一个可以分解成多个子任务的任务，才能让性能在并行化时有提升
        - 需要保证：`每个子任务的运行时间 > 创建新任务的时间`
            - 惯用方法：
                - <label style="color:red">`输入/输出`放在一个子任务中，`计算`放在另一个任务中</label>
    - 分支合并框架需要`预热`
        - 在测试同一算法的顺序版本和并行版本前，需要**多执行几遍，代码才能被JIT编译器优化**
        - 需要知道：编译器内置的优化可能会为顺序版本带来一些优势
            - 如: 执行死代码分析--删除从未被使用的计算
    - 需要自行制定标准，来决定拆分任务的临界值

- 工作窃取算法
    - 工作窃取算法一般用于：**在线程池中的工作线程之间重新分配和平衡任务**
    - 求和任务的设定和执行分析
        - 在求和任务中硬编码了规定:当包含`10_000`个元素时就不再创建子任务
            - 没有更好的方式来直接确定如何划分，只能尝试多个不同的值来尝试优化
        - 测试求和目标为`n=10_000_000`，将会产生1000个子任务，但实际只有几个内核可以使用
            - 当前的划分规则产生了大量的小任务
                - 理想情况下，划分并行任务时，应该让每个任务都用相同的时间完成，让所有的cpu一样繁忙
                - 实际情况下，每个子任务花费的时间是完全不同的，原因包括
                    - 划分策略低
                    - 存在不可知原因，如
                        - 磁盘访问慢
                        - 需要和外部服务协调执行

    - 分支合并框架通过`工作窃取`的技术来解决问题
        - 工作流程
            - 任务被平均分配到`ForkJoinPool`的所有线程中，每个线程都将任务保存在一个`双向队列`中
            - 线程每做完一个任务，就会从`双向队列`的`队头`取出下一个任务来执行
            - 当某个线程执行完所有任务后，`双向队列`为空，但是其他线程可能还有任务没有执行，则当前线程会`随机选择`一个其他线程，从目标线程的`双向队列`的`队尾`偷走一个任务
            - 重复这种执行自身任务，全部完成后偷取其他线程任务的过程，直到所有线程的所有任务都完成
        - 递归示意图：![工作窃取的递归处理](./imgs/inAction/工作窃取的递归处理.png)
        - 所以通常将一个任务划分成**多个 小 任务**，而不是**几个大任务**，这种划分方式有助于更好的在工作线程之间`平衡负载`


## Spliterator
[top](#catalog)
- 在流中不需要直接使用分支合并框架，在Stream框架内部使用了`Spliterator`来自动拆分流
- `Spliterator`，Java8中的新接口，表示：`可分迭代器`
    - 和`Iterato`一样，`Spliterator`也用于遍历数据
    - `Spliterator`是为并行处理而设计的
    - 一般不用重新开发接口实现
        - Java8已经为集合框架中包含的所有数据结构提供了一个默认的`Spliterator`实现
- `Spliterator`接口
    ```java
    public interface Spliterator<T> {
        boolean tryAdvance(Consumer<? super T> action);
        Spliterator<T> trySplit();
        long estimateSize();
        int characteristics();
    }
    ```
    - `T`是遍历的元素类型
    - `tryAdvance`类似于普通`Iterator`，按顺序一个一个使用`Spliterator`中的元素，如果还有元素则返回`true`
    - `trySplit`用于将一些元素划分为第二个`Spliterator`,让两个`Spliterator`**并行处理**
    - `estimateSize`用于估计当前还剩下多少元素需要遍历
        - 可能会不准，但能快速算出一个值，有助于将任务拆分的更均匀
    - `characteristics`，该方法返回一个`int`，代表`Spliterator`自身特性集的编码，通过这些特性来更好的控制和优化

        |特性|含义|
        |-|-|
        |ORDERED|元素有序(如`List`)，`Spliterator`在遍历和划分时也会遵守这个规则|
        |DISTINCT|全部元素具有唯一性|
        |SORTED|遍历的元素按照一个预定义的顺序排序|
        |SIZED|当前`Spliterator`由一个已知大小的源建立(如`Set`??????)，因此`estimateSize()`返回的是准确值?????????|
        |NONNULL|保证遍历的元素不为空|
        |IMMUTABLE|`Spliterator`数据源不能修改。即在遍历时，**不能添加、删除、修改 任何元素**|
        |CONCURRENT|`Spliterator`的数据源可以被其他线程同时修改而无需同步|
        |SUBSIZED|当前`Spliterator`和所有从它自身拆分出来的`Spliterator`都是`SIZED`的|

- `Spliterator`的拆分过程
    - 将`Stream`拆分成多个部分是一个递归过程，不停调用`trySplit`直到返回`null`
        - ![递归拆分过程](./imgs/inAction/Spliterator的递归拆分过程.png)

- 自定义`Spliterator`
    - 自定义`Spliterator`来:计算字符串中的单词数
    - 使用`for-each`迭代式统计
        ```java
        public int countWord(String s){
            int counter = 0;
            boolean lastSpace = true;
            for (char c : s.toCharArray()) {
                if (Character.isWhitespace(c)){
                    lastSpace = true;
                } else {
                    if (lastSpace) count++;
                    lastSpace = false;
                }
            }
        }

        //测试
        @Test
        public void method(){
            String a = "aa bb cc ddd";
            System.out.println(countWord(a));
        }
        ```
    - 使用三参数重载的`reduce`
        - 创建流
            - 无法直接通过String创建流
            - 通过`IntStream`创建一个索引流，然后将索引转换成字符串中对应的字符
                ```java
                Stream<Character> stream = IntStream.range(0, xxxx.length()).mapToObj(xxxx::charAt);
                ```
        - 自定义收集器
            - 收集器中需要保存两个状态
                - `counter`，单词数量的统计结果
                - `lastSpace`，上一个单词是否为空格
            ```java
            class WordCounter{
                public final int counter;
                public final boolean lastSpace;
                public WordCounter(int counter, boolean lastSpace) {
                    this.counter = counter;
                    this.lastSpace = lastSpace;
                }

                public WordCounter accumulate(Character c){
                    if (Character.isWhitespace(c)) {
                        return lastSpace? this : new WordCounter(counter, true);
                    } else {
                        return lastSpace? new WordCounter(counter + 1, false) : this;
                    }
                }

                public WordCounter combine(WordCounter wordCounter) {
                    return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
                }

                public int getCounter() {
                    return counter;
                }
            }
            ```
        - 测试
            - 测试类
                ```java
                class WordCounterTest{
                    //20
                    public static final String testStr = "aasfsfa bbsdfdfb ccewrewc dtyrydd eertyre ffrtyrfff ssfdsfs rtyrr xcrtyrtyrvv ljkrtllk xvcvvcvvxv rrrrrrr ggggggggggg sssssssss qqqqqqqqqqq  iooiioou sfdfdfsf kjklklkjlkl ewerrwe sfddfsdf";
                    public static int countWords(Stream<Character> stream) {
                        WordCounter counter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
                                
                        return counter.getCounter();        
                    }

                    // 顺序测试
                    @Test
                    public void streamTest(){
                        Stream<Character> stream = IntStream.range(0, testStr.length()).mapToObj(testStr::charAt);
                        System.out.println(countWords(stream));
                    }

                    // 并行测试
                    @Test
                    public void parallelTest(){
                        Stream<Character> stream = IntStream.range(0, testStr.length()).mapToObj(testStr::charAt);
                        System.out.println(countWords(stream.parallel()));
                    }
                }
                ```
            - 并行测试的统计结果不正确
                - 原始的字符串会在任意位置进行拆分，导致**一个词变成了两个词**


    - 使用自定义`Spliterator`来做并行处理
        - 如何解决`WordCounter`的问题
            - 只能从单词尾部进行拆分
        - 自定义`Spliterator`
            ```java
            class WordCounterSpliterator implements Spliterator<Character> {
                private final String s;
                private int currentChar = 0;

                public WordCounterSpliterator(String s) {
                    this.s = s;
                }

                @Override
                public boolean tryAdvance(Consumer<? super Character> action){
                    // 处理当前字符
                    action.accept(s.charAt(currentChar));
                    currentChar++;

                    // 返回是否还有字符需要处理
                    return currentChar < s.length();
                }

                @Override
                public Spliterator<Character> trySplit(){
                    // 获取当前 [未处理]的字符数
                    int currentSize = s.length() - currentChar;

                    // 不再分割任务
                    if (currentSize < 10) {
                        return null;
                    }

                    // 试探性切分字符串
                    // 从剩余部分的中间开始尝试
                    // 如果剩余的字符串中没有 [空格了]，则不再划分子任务
                    for (int splitPos = currentSize/2 + currentChar; splitPos < s.length(); splitPos++){

                        // 如果找到空格，则从当前位置：currentChar，切分到：splitPos-1的位置
                        // 从 currentChar 开始切分，可能会在单词中间切分
                        // 即保证在 [尾部] 切一个完整的单词
                        if (Character.isWhitespace(s.charAt(splitPos))) {
                            Spliterator<Character> spliterator = new WordCounterSpliterator(s.substring(currentChar, splitPos));

                            currentChar = splitPos;
                            return spliterator;
                        }
                    }

                    return null;
                }

                @Override
                public long estimateSize(){
                    return s.length() - currentChar; 
                }

                @Override
                public int characteristics(){
                   return ORDERED + SIZED + NONNULL + IMMUTABLE + SUBSIZED;
                }
            }
            ```
        - 测试
            ```java
            class WordCounterTest{
                //20
                public static final String testStr = "aasfsfa bbsdfdfb ccewrewc dtyrydd eertyre ffrtyrfff ssfdsfs rtyrr xcrtyrtyrvv ljkrtllk xvcvvcvvxv rrrrrrr ggggggggggg sssssssss qqqqqqqqqqq  iooiioou sfdfdfsf kjklklkjlkl ewerrwe sfddfsdf"

                public static int countWords(Stream<Character> stream) {
                    WordCounter counter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
                            
                    return counter.getCounter();        
                }

                ...
                
                // 使用自定义Spliterator做并行测试
                @Test
                public void spliteratorParallelTest(){
                    Spliterator<Character> spliterator = new WordCounterSpliterator(testStr);
                    Stream<Character> stream = StreamSupport.stream(spliterator, true);

                    System.out.println(countWords(stream.parallel()));
                }
            }
            ```
- 延迟绑定的`Spliterator`
    - 在第一次遍历、第一次拆分、第一次查询估计大小时绑定数据源，而不是在创建时就绑定


# 重构+测试+调试
## 为改善可读性和灵活性重构代码
[top](#catalog)
- 从匿名类到Lambda表达式的转换
    - `self`和`super`的含义
        - 匿名类的`self`和`super`的含义不同
            - `self`代表类自身
            - `super`
        - Lambda表达式中的`self`和`super`含义
            - `self`代表的是包含类

    - 匿名类中可以屏蔽包含类的变量；但是Lambda表达式中不能屏蔽，而且会导致编译错误
        ```java
        int a = 10;

        Runnable r1 = () -> {
            int a = 2; //编译错误
            System.out.println(a);
        };

        Runnable r2 = new Runnable(){
            public void run(){
                int a = 2; //屏蔽包含类的变量
                System.out.println(a);
            }
        };
        ```
    - 在涉及重载的上下文里，将匿名类转换为Lambda表达式可能会导致最终的代码更加难以理解
        - 类型的确定方法
            - 匿名类的类型是在:**初始化时确定的**
            - Lambda的类型**取决于上下文**
        - 通过强转类型来适应重载
            ```java
            // 函数签名与Runnable相同
            public interface Task {
                public void execute();
            }

            class MyClass{
                public static void doSomething(Runnable r) {r.run();}
                public static void doSomething(Task t) {t.execute();}
                
                @Test
                public void method01(){
                    // 传递匿名类，可以正常运行
                    doSomething(new Task(){
                        System.out.println("this is task");
                    });
                }

                @Test
                public void method02(){
                    // 如果直接使用使用Lambda表达式，Runnable和Task都匹配
                    // doSomething(() -> System.out.println("this is task"));

                    // 通过类型强转来解决问题
                    doSomething((Task)() -> System.out.println("this is task"));
                }
            }
            ```

- 从Lambda表达式到方法引用的转换
    - 方法引用往往可读性更强
    - 示例：从Lambda归约转换为方法引用
        ```java
        // Lambda归约
        int result = menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1+c2);

        // 使用方法引用
        int result = menu.stream().collect(summingInt(Dish::getCalories));
        ```

- 从命令式的数据处理切换到Stream
    - 建议将**使用迭代器处理集合的代码**转换成**StreamAPI**
    - 转换时需要考虑`break`,`continue`,`return`，`http://refactoring.info/tools/LambdaFicator` ???????????

- 增加代码的灵活性
    - 采用函数式接口
        - 没有函数式接口，就无法使用Lambda表达式，所以需要在代码中引入函数式接口
        - 两种通用的改造模式
            - `有条件的延迟执行`
            - `环绕执行`
    - `有条件的延迟执行`
        - 示例：一段日志代码
            - 代码的问题
                - 日志器的状态通过`isLoggable`方法保留给了客户端代码
                - 每次输出一条日志之前都需要查询日志器对象的状态
                ```java
                if (logger.isLoggable(Log.FINER))){
                    logger.finer("xxxx");
                }
                ```
            - 一种解决方案
                - 这种方式不需要在代码中添加条件判断
                - 日志器的状态也不用暴露
                - 剩余的问题
                    - 输出日志之前，仍然需要判断，**即使已经关闭了日志**
                ```java
                logger.log(Log.FINER, "xxxxx");
                ```
        - 改造的过程
            - 改造的条件
                - 如果需要频繁的从客户端代码去查询一个对象的状态，只是为了传递参数、然后执行一个方法
            - 改造的方法
                - 实现一个新的方法，以Lambda或方法表达式作为参数，新方法在检查完对象的状态之后再调用参数方法
        - 通过改造使代码更易读
    
    - `环绕执行`
        - 改造的条件
            - 业务部分不同，但是准备和清理阶段的逻辑相同
        - 改造的目的
            - **重用**准备和清理阶段的逻辑
            - [环绕执行模式](#环绕执行模式)

## 使用Lambda重构面向对象的设计模式
[top](#catalog)
- 通过Lambda表达式来**避免常规面向对象设计中的僵化的模板代码**
- 策略模式的改造
    - 策略模式的三部分内容
        - 一个代表某个算法的接口，即策略模式的接口
        - 一个或多个接口的具体实现，即算法的具体实现
        - 一个或多个策略对象的客户
        
- 模板方法
    - 使用的场景：**需要使用某个算法，同时又希望有一定的灵活度，可以对某些部分进行改造**
    - 示例：在线银行
        - 搭建了基本的算法框架
        - 不同的银行需要通过继承，来提供不同的实现
        ```java
        abstract class OnlineBanking{
            public void processCustomer(int id) {
                Customer c = Database.getCustomerWithId(id);
                makeCustomerHappy(c);
            }

            abstract void makeCustomerHappy(Customer c);
        }
        ```
    - 使用Lambda表达式来改造示例
        - 使用时直接通过Lambda表达式来调用
        ```java
        class OnlineBanking{
            public void processCustomer(int id, Consume<Customer> makeCustomerHappy) {
                Customer c = Database.getCustomerWithId(id);
                makeCustomerHappy.accept(c);
            }
        }

        //调用
        new OnlineBanking().processCustomer(1111, (Customer, c) -> System.out.println("..."));
        ```

- 观察者模式
    - 示例：新闻订阅
        ```java
        // 观察者
        interface Observer {
            void notify(String tweet);
        }

        class NYTimes implements Observer{
            public void notify(String tweet) {
                if (tweet != null && tweet.contains("NYTimes")){
                    System.out.println("this is NYTimes");
                }
            }
        }

        class Guardian implements Observer{
            public void notify(String tweet) {
                if (tweet != null && tweet.contains("Guardian")){
                    System.out.println("this is Guardian");
                }
            }
        }

        class LeMonde implements Observer{
            public void notify(String tweet) {
                if (tweet != null && tweet.contains("LeMonde")){
                    System.out.println("this is LeMonde");
                }
            }
        }

        // 可观察对象
        interface Subject {
            void registObject(Observer o);
            void notifyObject(String tweet);
        }

        class Feed implements Subject {
            private final List<Observer> observers = new List<Observer>();

            public void registObject(Observer o) {
                observers.add(o);
            }
            
            public void notifyObject(String tweet){
                for(Observer o : observers) {
                    o.notify();
                }
            }
        }

        // 调用
        class ObserverModelTest {
            @Test
            public void method01(){
                Subject f = new Feed();
                f.registObject(new NYTimes());
                f.registObject(new Guardian());
                f.registObject(new LeMonde());
                f.notifyObject("LeMonde");
            }

            // 直接使用Lambda表达式改造
            @Test
            public void method02(){
                Subject f = new Feed();
                f.registObject((String tweet) -> {
                    if (tweet != null && tweet.contains("NYTimes")){
                        System.out.println("this is NYTimes");
                    }
                });

                f.registObject((String tweet) -> {
                    if (tweet != null && tweet.contains("LeMonde")){
                        System.out.println("this is LeMonde");
                    }
                });
                f.registObject(new LeMonde());
                f.notifyObject("LeMonde");
            }
        }
        ```
    - 当观察者的逻辑过于复杂时，还是需要使用类的方式

- 责任链模式
    - 责任链是一种船舰处理对象序列的统一方案
        - 一个处理对象完成一些工作后，将结果传递给另一个对象，再做其他工作，再传递给其他对象，以此类推
    - 通常这种模式是通过定义一个代表处理对象的抽象类来实现
        - 抽象类中包含一个字段来记录后续对象
        - 完成后在交给它后续的对象
    - 示例
        ```java
        public abstract class ProcessingObject<T> {
            protected ProcessingObject<T> successor;

            public void setSuccessor(ProcessingObject<T> successor){
                this.successor = successor;
            }

            public T handle(T input) {
                // 先做当前的任务
                T r = handleWork(input);
                // 如果有后续的处理对象，则进行迭代调用
                if(successor != null) {
                    return successor.handle(r);
                }

                return r;
            }

            abstract protected T handleWork(T input);
        }

        class HeaderTextProcessing extends ProcessingObject<String> {
            public String handleWork(String input) {
                return "HeaderText" + input;
            }
        }

        class SpellCheckerProcessing extends ProcessingObject<String> {
            public String handleWork(String input) {
                return input.replaceAll("labda", "lambda");
            }
        }

        // 测试
        class ProcessingObjectTest {

            @Test
            public void method01(){
                ProcessingObject<String> p1 = new HeaderTextProcessing();
                ProcessingObject<String> p2 = new SpellCheckerProcessing();

                p1.setSuccessor(p2);

                String result =p1.handle("labdas really ");
                System.out.println(result);
            }
        }
        ```

    - 使用lambda进行改造
        - `abstract protected T handleWork(T input);`的函数签名与`UnaryOperator`相同
        - 责任链实际上是在做函数的链式调用
        - 通过`andThen`方法来改造 ??????????????????
        ```java
        class ProcessingObjectTest {
            ...
            
            @Test
            public void method02(){
                UnaryOperator<String> headerProcessing = (String input) -> "HeaderText" + input;
                UnaryOperator<String> spellCheckerProcessing = (String input) -> input.replaceAll("labda", "lambda");

                Function<String, String> pipline = headerProcessing.andThen(spellCheckerProcessing);

                String result = pipline.apply("labdas really ");
                System.out.println(result);
            }
        }

        ```

## 调试Lambda
[top](#catalog)
- `peek()`
    - 每个流操作之前添加`peek()`，来输出操作之间的中间值
    ```java
    numbers.stream().peek(x -> System.out.println(x)).map(...).collect(toList());
    ```

# 默认方法
[top](#catalog)

## 默认方法的基本概念
[top](#catalog)
- Java8允许在接口内声明`静态方法`
- Java8引入了`默认方法`，通过`默认方法`可以指定接口方法的默认实现
    - 实现接口的类，如果**不显示地**提供该方法的具体实现，就会**自动继承默认的实现**
- 使用`default`来修饰`默认方法`
- `默认方法`是种`非抽象方法`

- 默认方法的引入是为了以`兼容的方式`解决**类库的演进问题**
    - **向接口添加新方法是二进制兼容的，所以如果不重新编译该类，即使不实现新的方法，现有类的实现依旧可以运行**

- 不同类型的兼容性：二进制、源代码、函数行为
    - 变更对Java程序的影响大一可以分成三种类型的兼容性，分别是：二进制级兼容、源代码级兼容、函数行为的兼容
    - 二进制级兼容
        - 表示现有的二进制执行文件能无缝持续链接(包括验证、准备和解析)和运行
        - 例如：为接口添加一个方法
            - 这种方式下，**如果新添加的方法不被调用**,接口的实现仍然可以运行，不会出现异常

    - 源代码级兼容
        - 表示引入变化之后，现有的程序依然能成功编译通过
        - 接口添加新方法就不是源码级兼容，因为旧代码没有实现新引入的方法，所以会无法通过编译
        
    - 函数行为的兼容
        - 表示变更发生之后，程序接受同样的输入能得到同样的结果
        - 为接口添加方法就是函数行为兼容的，因为新添加的方法在程序中没有被调用，或该接口在实现中被覆盖了

## 默认方法的使用模式
[top](#catalog)
- 使用默认方法的两种用例
    - 可选方法
    - 行为的多继承

- 可选方法
    - 实现接口时，可能会有一些方法不用实现，这样会产生很多的模板代码
    - 通过默认方法可以减少各个实现类中的这种模板代码
    - java8，`Iterator`的`remove`默认实现
        ```java
        interface Ierator<T>  {
            boolean hasNext();
            T next();
            default void remove() {
                throw new UnsupportedOperationException();
            }
        }
        ```
- 行为的多继承
    - 利用正交方法的精简接口
        - 将一些方法通过默认方法提供
    - 组合接口
        - 实现多个接口的抽象方法，然后即可以使用接口中的默认方法
        - 以另一种方式实现了多继承

## 解决冲突的规则
[top](#catalog)
- 冲突问题：如果一个类使用相同的函数签名从多个地方继承了方法，包括类和接口，如何确定调用的目标？
    - 示例：`main`方法中输出什么
        ```java
        interface A{
            default void hello() {
                System.out.println("Hello from A");
            }
        }

        interface B extends A{
            default void hello() {
                System.out.println("Hello from B");
            }
        }

        class C implements B, A {
            public static void main(String[] args) {
                new C().hello(); // Hello from B
            }
        }
        ```
- 解决问题的三条规则
    1. 类中的方法优先级最高
        - **类或父类**优先级 > `默认方法`优先级
    2. 如果`1`无法判断，子接口的优先级更高
        - 函数签名相同时，优先选择拥有**最具体实现的默认方法的接口**
        - 如果B接口继承了A接口，则使用B比A具体
    3. 如果`1`、`2`都无法判断，类必须覆盖方法，并显示的指定要调用哪个接口中的方法

- 三条规则的应用
    - 示例的分析
        1. 规则1，类c中没有覆盖`hello`，无法判断
        2. 规则2，实现了两个接口`A`、`B`，`B`继承了`A`，所以`B`比`A`更具体，会调用`B`中的`hello`
    
    - 在示例中添加类继承
        1. 规则1，`C`、`D`都未覆盖`hello`无法判断
        2. 规则2，`C`实现了`B`、`A`，但是应该选择更具体实现的接口中的方法
            ```java
            interface A{
                default void hello() {
                    System.out.println("Hello from A");
                }
            }

            interface B extends A{
                default void hello() {
                    System.out.println("Hello from B");
                }
            }
            
            class D implements A {}

            class C extends D implements B, A {
                public static void main(String[] args) {
                    new C().hello(); // Hello from B
                }
            }
            ```
    - 为`D`添加实现
        - 规则1，类`C`中没有实现，父类`D`中有实现，所以会调用`D`中的`hello`
            ```java
            interface A{
                default void hello() {
                    System.out.println("Hello from A");
                }
            }

            interface B extends A{
                default void hello() {
                    System.out.println("Hello from B");
                }
            }
            
            class D implements A {
                void hello() {
                    System.out.println("Hello from D");
                }
            }

            class C extends D implements B, A {
                public static void main(String[] args) {
                    new C().hello(); // Hello from D
                }
            }
            ```
    - 如果`D`是抽象类
        - 这种情况下，C必须显示的提供hello的实现，否则会导致编译异常
            ```java
            interface A{
                default void hello() {
                    System.out.println("Hello from A");
                }
            }

            interface B extends A{
                default void hello() {
                    System.out.println("Hello from B");
                }
            }
            
            abstract class D implements A {
                public abstract void hello();
            }

            class C extends D implements B, A {

                public static void main(String[] args) {
                    new C().hello(); // 编译异常
                }
            }
            ```

- 规则3的使用方法：冲突及如何显示地消除歧义
    - 如果`A`、`B`两个接口没有任何关联，则无法应用规则2，此时`A`和`B`的`hello`都是有效的选择
    - Java编译器会产生异常，因为它**无法判断哪一个方法更合适**
    - 解决方法
        1. 在`C`中显示的实现`hello`方法
        2. 显示的调用：
            - Java8中的新语法：`X.super.m(...)`, `X`是希望调用的方法`m(...)`所在的父接口
            - <label style="color:red">显示的调用只能调用直接的父接口，再上一层的接口无法调</label>
            - 显示的调用`B.super.hello()`来调用`B`中的默认方法
            
        ```java
        interface A{
            default void hello() {
                System.out.println("Hello from A");
            }
        }

        interface B{
            default void hello() {
                System.out.println("Hello from B");
            }
        }

        class C implements B, A {
            void hello() {
                B.super.hello(); //覆盖接口方法，同时调用接口B中的方法
            }

            public static void main(String[] args) {
                new C().hello(); // Hello from B
            }
        }
        ```

- 菱形继承问题
    - 只有`A`中声明了`hello`方法，则会输出：`Hello from A`
        ```java
        interface A{
            default void hello() {
                System.out.println("Hello from A");
            }
        }

        interface B extends A{}
        interface C extends A{}

        class D implements B, C {
            public static void main(String[] args) {
                new D().hello(); // Hello from A
            }
        }
        ```
    - 菱形继承，B中添加默认方法，C中不添加
        - 使用用规则2，
        ```java
        interface A{
            default void hello() {
                System.out.println("Hello from A");
            }
        }

        interface B extends A{
            default void hello() {
                System.out.println("Hello from B");
            }
        }
        interface C extends A{}

        class D implements B, C {
            public static void main(String[] args) {
                new D().hello(); // Hello from B
            }
        }
        ```
    - 菱形继承，B中添加默认方法，C中添加一个同名的接口
        - 必须在D中提供C的实现，否则会导致编译异常
        ```java
        interface A{
            default void hello() {
                System.out.println("Hello from A");
            }
        }

        interface B extends A{
            default void hello() {
                System.out.println("Hello from B");
            }
        }
        interface C extends A{
            void hello();
        }

        class D implements B, C {
            @Override
            public void hello() {
                B.super.hello();
            }

            public static void main(String[] args) {
                new D().hello(); // Hello from B
            }
        }
        ```


# 用Optional取代null
[top](#catalog)
- 每次不确定一个变量是否为`null`时，都需要添加一个嵌套的`if`块，增加了代码的缩进层数
    - 这种方式不具备扩展性，牺牲了代码的可读性
- 使用`Optional`重新定义`Preson/Car/Insurance`的数据类型
    ```java
    class Person {
        private Optional<Car> car;
        public Optional<Car> getCar(){ return car;}
    }

    class Car {
        private Optional<Insurance> insurance;
        public Optional<Insurance> getInsurance() { return insurance;}
    }

    class Insurance {
        private String name;
        public String getName() { return name;}
    }
    ```

- 创建`Optional`对象
    - `empty()`: 声明一个空的`Optional`
        ```java
        Optional<T> t = Optional.empty();
        ```
    - `of`: 通过一个非空值创建`Optional`
        - 如果`t`是`null`，会抛出异常`NullPointerException`
        ```java
        Optional<T> opt = Optional.of(t);
        ```
    - `ofNullable()`: 可接受`null`的`Optional`
        - 如果`t`是`null`，则Optional对象是一个空对象
        ```java
        Optional<T> opt = Optional.ofNullable(t);
            ```

- `Optional`类型**无法序列化**
    - `Optional`的设计是为了支持能返回`Optional`对象的语法，没有考虑过将这样的类型作为字段
    - 替代方案：提供一个访问`Optional`值的接口
        ```java
        class Person {
            private Car car;
            public Optional<Car> getCar(){ 
                return Optional.ofNullable(car);
            }
        }   
        ```

- 读取`Optional`中的变量值
    - `get()`
        - 如果变量存在，则返回对象值
        - 如果`Optional`对象空对象，则抛出异常`NoSuchElementException`
    - `orElse(T other)`
        - 如果有值则返回，否则返回一个默认值
    - `osElseGet(Supplier<? extends T> other)`
        - `orElse`方法的**延迟调用**
        - 如果有值则返回，否则返回一个由`Supplier`创建的对象
    - `osElseThrow(Supplier<? extends X> exceptionSupplier)`
        - 如果有值则返回，否则返回一个由`Supplier`创建的异常
    - `ifPresent()`
        - 返回变量值的状态: 
            - 变量存在，返回`true`
            - 空对象，返回`false`
            ```java
            public boolean isPresent() {
                return value != null;
            }
            ```

- `Optional`的几种应用方法
    - 使用`map`将返回值包装到`Optional`中
        - `map`源码
            ```java
            public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
                Objects.requireNonNull(mapper);
                if (!isPresent()) {
                    return empty(); //如果Optional是空对象，就什么都不做，直接返回空对象
                } else {
                    return Optional.ofNullable(mapper.apply(value)); //若如果mapper的返回值为null，则创建一个空对象
                }
            }
            ```

        - 示例
            ```java
            Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
            Optional<String> name = optInsurance.map(Insurance::getName);
            ```
    - 使用`flatMap`链接`Optional`对象
        - `flatMap`源码
            ```java
            public <U> Optional<U> flatMap(Function<? super T, ? extends Optional<? extends U>> mapper) {
                Objects.requireNonNull(mapper);
                if (!isPresent()) {
                    return empty();
                } else {
                    @SuppressWarnings("unchecked")
                    Optional<U> r = (Optional<U>) mapper.apply(value);
                    return Objects.requireNonNull(r);
                }
            }
            ```

        - 链式调用`map`会导致异常，`Person:geteCar`返回的是`Optional<Car>`类型，经过`map`的保证，会变成`Optional<Optional<Car>>`，无法调用`Car::getInsurance`
            ```java
            Optional<Person> optPerson = Optional.of(person);
            Optional<String> name = optPerson.map(Person:geteCar)
                                            .map(Car::getInsurance)
                                            .map(Insurance::getName);
            ```
        - 通过`flatMap`将嵌套的`Optional`扁平化
            - 重写链式调用
                ```java
                Optional<Person> optPerson = Optional.of(person);
                String name = optPerson.flatMap(Person:geteCar)
                                                .flatMap(Car::getInsurance)
                                                .map(Insurance::getName)
                                                .orElse("UnKnow");
                ```
    - 使用`filter`来过滤特定的值
        - `filter`源码
            ```java
            public Optional<T> filter(Predicate<? super T> predicate) {
                Objects.requireNonNull(predicate);
                if (!isPresent()) {
                    return this; //如果Optional是空对象，就什么都不做，直接返回空对象
                } else {
                    return predicate.test(value) ? this : empty(); // 应用predicate 如果没有通过则返回空对象
                }
            }
            ```

        - 判断`Insurance`的`name`字段是否是指定内容
            ```java
            Insurance insurance = new Insurance(...);
            if (insurance != null && "xxxx".equals(insurance.getName())) {
                System.out.println("Ok");
            }
            ```
        - 使用`filter`来过滤
            ```java
            Insurance insurance = new Insurance(...);
            Optional<Insurance> optInsurance = Optional.ofNullable(insurance);

            optInsurance.filter(insurance -> "xxxx".equals(insurance.getName()) )
                        .isPresent(x -> System.out.println("Ok"));  //??????????????/ 重载????
            ```

- 两个`Optional`对象的组合
    - 通过`person` 和 `car` 来查找最便宜的保险
        ```java
        public Insurance findCHeapestInsurance(Person person, Car car) {
            ...
            return cheapestCompany;
        }
        ```
    - 使用`Optional`构造一个`null`安全的版本
        - 直接使用`isPresent()`与判断对象为`null`类似
            ```java
            public Optional<Insurance> nullSafeFindCHeapestInsurance(Optional<Person> person, Optional<Car> car) {
                if (person.isPresent() && cat.isPresent()) {
                    return Optional.of(findCHeapestInsurance(person.get(), car.get()));
                } else {
                    return Optional.empty();
                }
            }
            ```
        - 通过`flatMap`和`map`以**不解包**的方式组合两个`Optional`对象
            - 组合过程
                - 如果`person`是空对象，整个处理会立刻停止，并返回**空对象**
                - 如果`car`是空对象，则后续处理会立刻停止，并返回**空对象**
                - 执行`findCHeapestInsurance`,并返回结果，`map`会将结果包装成`Optional`对象
                - `flatMap`直接将`Optional`对象返回

                ```java
                public Optional<Insurance> nullSafeFindCHeapestInsurance(Optional<Person> person, Optional<Car> car) {
                    return person.flatMap(p -> c.map(c -> findCHeapestInsurance(p, c)));
                }
                ```

- 应该避免使用基本类型的`Optional`对象
    - 基本类型的`Optional`: `OptionalInt`, `OptionalLong`, `OptionalDouble`
    - 基本类型的`Optional`不支持`map`,`flatMap`, `filter`方法
        - 同时也无法作为方法引用传递给另一个`Optional`对象的这些方法

- 使用`Optional`的实战示例
    - 将`String`转`int`
        ```java
        class OptionalUtility{
            public static Optional<Integer> stringToInt(String s) {
                try {
                    return Optional.of(Integer.parse(s));
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            }
        }
        ```
    - 从属性中读取`duration`值
        - 不是数字的，返回0
        - 如果是负数，返回0
            
        ```java
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        public int readDuration(Properties props, String name) {
            return Optional.ofNullable(props.getProperty(name))
                           .flatMap(OptionalUtility::stringToInt)
                           .filter(i -> i > 0)
                           .orElse(0);
        }
        ```

# CompletableFuture组合式异步编程
## Future接口
[top](#catalog)
- `Future`接口是用来对将来某个时刻会发生的结果进行建模
    - `Future`建模了一种异步计算，返回一个执行运算结果的引用，当运算结束后，这个引用被返回给调用方
    - 在`Future`中触发那些耗时的操作，来将线程解放出来，让它能继续执行其他有价值的工作
- `Future`比底层的`Thread`更易用
- 使用`Future`时，需要将耗时的操作封装到一个`Callable`对象中，再提交给`ExecutorService`，通过`isDone`来检测异步操作是否已经结束
    - Java7中的写法
        ```java
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            public Double call() {
                return doSomeLongComputation();
            }
        });

        doSomthingElse();

        try {
            Double result =  future.get(1, TimeUnit.SECONDS); //等待1s后，如果仍然被阻塞则引发异常
        } catch (ExecutionException ee){
            //计算抛出一个异常
        } catch (InterruptedException ie) {
            //当前线程在等待过程中被中断
        } catch(TimeoutException te) {
            //future等待超时
        }
        ```
    - Future的异步操作流程
        - ![图](./imgs/inAction/Future的异步操作流程.png)
- `Future`接口的局限性
    - 很难表述`Future`结果之间的依赖性


## CompletableFuture实现异步API
[top](#catalog)
- `get()`，等待异步处理完成，会一直阻塞当前进程
- `get(long timeout, TimeUnit unit)`，指定等待时间，来防止永久阻塞
- `join()`与`get()`相似，但是**join不会抛出任何检测到的异常**
- 错误处理
    - 如果计算的线程产生异常，该异常会被限制在这个线程内，最终杀死该线程，会导致**等待get()方法的调用线程被永久的阻塞**
    - 可以通过`CompletableFuture`的`completeException`方法会将内部发生的异常抛出

- 工厂方法
    - `supplyAsync`创建`CompletableFuture`
        - 两种重载
            ```java
            public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
                return asyncSupplyStage(ASYNC_POOL, supplier);
            }

            public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
                                                        Executor executor) {
                return asyncSupplyStage(screenExecutor(executor), supplier);
            }
            ```
        - `Supplier`参数会交给`ForkJoinPool`池中的某个执行线程(`Executor`)运行
            - 可以通过第二个参数指定不同的执行线程执行`Supplier`
    - `allOf`
        - 返回一个`CompletableFuture<Void>`，将多个对象合在一起，可以对该对象执行`join()`
    - `anyOf`
        - 与`allOf`类似，返回第一个完成的对象

- 避免同步API的阻塞
    - 使用并行流：`parallelStream`, 对请求进行并行操作
    - 使用`CompletableFuture`发起异步请求

- 使用哪种方式来处理并行
    - 计算密集型操作，推荐使用Stream接口
        - 因为实现简单，同时效率也可能是最高的
        - **如果所有线程都是计算密集型的，那就没有必要创建比处理器核数更多的线程**
    - IO密集型操作，使用`CompletableFuture`灵活性更好
        - 可以依据W/C的比例设定需要使用的线程数
        - 处理流的流水线中如果发生IO等待，流的延迟特性会然我们很难判断：**什么时候触发的等待**

- `CompletableFuture`的任务组合
    - `thenApply`，将`CompletableFuture`对象进行其他转换
    - `thenCompose`，合并两个操作，第一个任务的结果作为第二个任务的输入，两个任务在同一个线程中运行
    - `thenComposeAsync`，合并两个操作，第一个任务的结果作为第二个任务的输入，但是第二个任务是由不同线程运行的
        - 第二个任务取决于第一个任务，所以`thenCompose`更高效，少了很多线程切换的开销
    - `thenCombine`，定义两个任务结束后如何合并
    - `thenCombineAsync`，定义两个任务结束后如何合并，合并操作将提交到线程池中，由另一个线程执行

```java
public class MyShopApp {
    List<Shop> shops = Arrays.asList(
            new Shop("aaa"),
            new Shop("bbb"),
            new Shop("ccc"),
            new Shop("ddd"),
            new Shop("eee")
            );

    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    // 在各商店中寻找某个产品的价格，使用 Stream 对同步API 做顺序执行
    public List<String> findPrices(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    // 在各商店中寻找某个产品的价格，使用 ParallelStream 对同步API 做并行执行
    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    // 在各商店中寻找某个产品的价格
    // 对同步API 用CompletableFuture构造异步请求
    // 使用 Stream 做异步执行
    public List<String> findPricesAsync(String product) {
        List<CompletableFuture<String>> collect = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
                        )
                )
                .collect(toList());

        return collect.stream().map(CompletableFuture::join).collect(toList());
    }

    // 在各商店中寻找某个产品的价格
    // 对同步API 用CompletableFuture构造异步请求
    // 使用 Stream 做异步执行
    // 手动添加executor
    public List<String> findPricesAsyncByExecutor(String product) {
        List<CompletableFuture<String>> collect = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
                                executor
                        )
                )
                .collect(toList());

        return collect.stream().map(CompletableFuture::join).collect(toList());
    }

    // 使用流水线处理多个操作
    public List<String> findPricesWithDiscount(String product) {
        return shops.stream().map(shop -> shop.getPriceWithDiscount(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    // 合并多个异步操作
    public List<String> findPricesWithDiscountAsync(String product) {
        List<CompletableFuture<String>> collect = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscount(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)
                ))
                .collect(toList());

        return collect.stream().map(future -> future.join()).collect(toList());
    }
}


class Shop {
    private String name;
    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //根据指定名称返回价格
    public double getPrice(String product){
        //可以执行：DB访问，联系其他外部服务
        return calculatePrice(product);
    }

    //返回带有折扣的价格字符串
    public String getPriceWithDiscount(String product) {
        double price = calculatePrice((product));
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    //根据指定名称返回价格，返回Future对象，可以立即返回，可以通过get方法获取执行结果
    public Future<Double> getPriceAsync(String product) {
        // 创建一个代表异步计算的实例
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(
                () -> {
                    try {
                        double price = calculatePrice(product); //获取价格
                        futurePrice.complete(price); //结束当前任务，设置Future的返回值
                    } catch (Exception ex) {
                        futurePrice.completeExceptionally(ex); //将异步计算中异常抛出
                    }
                }
        ).start();

        return futurePrice;
    }

    // 通过工程方法创建Future对象，与getPriceAsync的功能相同
    public Future<Double> getPriceAsyncByFactory(String product) {
        return CompletableFuture.supplyAsync( () -> calculatePrice(product) );
    }

    // 生成随机价格
    private double calculatePrice(String product){
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    // 模拟延迟的方法
    public static void delay() {
        try{
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Quote {
    private final String shopName;
    private final double price;
    private final Discount.Code discountCount;

    public Quote(String shopName, double price, Discount.Code discountCount) {
        this.shopName = shopName;
        this.price = price;
        this.discountCount = discountCount;
    }

    public static Quote parse(String s){
        String[] split = s.split(":");
        String name = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code code = Discount.Code.valueOf(split[2]);

        return new Quote(name, price, code);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCount() {
        return discountCount;
    }
}

class Discount {
    public enum Code{
        NONE(0),SILVE(5),GOLD(10),PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " +
                Discount.apply(quote.getPrice(),
                        quote.getDiscountCount());
    }
    private static double apply(double price, Code code) {
        delay();
        return price * (100 - code.percentage) / 100;
    }

    // 模拟延迟的方法
    public static void delay() {
        try{
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

# 日期时间API
## 旧API的问题
[top](#catalog)
- `java.util.Date`的问题
    - 无法表示日期，只能以**毫秒的精度**表示时间
    - 易用性太差，如：
        - **不支持时区**
        - 年份从`1900`开始
        - 月份从`0`开始
        - 创建过程不直观
            ```java
            //创建2000-2-11
            Date date = new Date(100, 1, 11);
            ```
- `java.util.Date`和`java.util.Calendar`共存的问题
    - `java.util.Calendar`与`java.util.Date`的问题类似
    - 容易混淆
    - 两个类的对象是可变的，日期可能会被修改
- 开始使用第三方的日期和时间库：`Joda-Time`

## TemporalField接口
[top](#catalog)
- **`LocalDate`、`LocalTime`、`Instant`都实现了`Temporal`接口**
- 接口通过`get()`和`with()`来读写和修改对象
    - 某些实现类可能不支持某个方法，使用时会导致异常：`UnsupportedTemporalTypeException`
- 通用方法

    |方法名|是否是静态方法|用途|
    |-|-|-|
    |from|是|根据传入的Temporal对象创建对象实例|
    |now|是|根据系统时钟创建Temporal对象|
    |of|是|由Temporal对象的某个部分创建该对象的实例|
    |parse|是|由字符串创建Temporal对象|
    |atOffset|否|将Temporal对象和某个时区偏移相结合|
    |atZone|否|将Temporal对象和某个时区相结合|
    |format|否|使用某个指定的格式器将Temporal对象转换为字符串(Instant不提供)|
    |get|否|读取Temporal对象的某一部分|
    |with|否|以该Temporal对象为模板，对某些状态进行修改并创建副本|
    |minus|否|将当前Temporal对象的值减去一定的时长来创建一个副本|
    |plus|否|将当前Temporal对象的值增加一定的时长来创建一个副本|



## 新的日期时间类
[top](#catalog)
- `java.time`
    - `LocalDate`，`LocalTime`，`LocalDateTime`，`Instant`，`Duration`，`Period`的对象都是不可修改对象
- `LocalDate`和`LocalTime`
    - `LocalDate`
        - 创建日期类型对象
        - 特性
            - 一个不可变对象
            - 只提供了简单的日期，并不含当前的时间信息
            - 不附带任何与时区相关的信息
        - 使用方法
            - 使用静态方法构造的对象
                ```java
                LocalDate date = LocalDate.of(2014, 3, 18);
                int year = date.getYear(); // 获取:年
                Month month = date.getMonth(); // 获取:月份，返回一个Month对象
                int day = date.getDayOfMonth(); // 获取 日
                DayOfWeek dow = date.getDayOfWeek(); // 获取:星期
                int len = date.lengthOfMonth(); // 获取:当前月份有几天
                boolean leap = date.isLeapYear(); // 检查是否是闰年
                ```
            - 使用当前时间创建对象
                ```java
                LocalDate date = LocalDate.now();
                ```
    - `LocalTime`
        - 创建时间类型对象
            - 两种静态重载
                ```java
                public static LocalTime of(int hour, int minute, int second) {} //接收 时分秒
                public static LocalTime of(int hour, int minute, int second, int nanoOfSecond) {} //接收 时分秒 毫秒
                ```
        - 使用
            ```java
            LocalTime time = LocalTime.of(12, 45, 20);  //12:45:20
            int hour = time.getHour(); //12
            int minute = time.getMinute(); //45
            int second = time.getSecond(); //20
            ```
    - 通过对应的格式化字符串来创建`LocalDate`和`LocalTime`
        - 如果字符串无法被解析，将会导致异常：`DateTimeParseException`
        ```java
        LocalDate date = LocalDate.parse("2014-03-18");
        LocalTime time = LocalTime.parse("13:45:20");
        ```

- `TemporalField`接口
    - `TemporalField`接口定义了如何读取和操作为时间建模的对象的值
    - 可以使用`TemporalField`接口的实现类，通过`get()`来获取某些属性
    - `ChronoField`枚举 是 `TemporalField`接口的一个实现
        ```java
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.get(ChronoField.YEAR);
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        int day = date.get(ChronoField.DAY_OF_MONTH);
        ```

- `LocalDateTime`合并日期和时间
    - 特性
        - 同时表示了日期和时间，但**不带有时区信息**
    - 使用方法
        - 创建对象
            - 可以直接创建该对象，也可以通过合并`LocalDate`和`LocalTime`来构造
            - `LocalDate`对象通过`atTime(LocalTime)`来合并`LocalTime`对象
            - `LocalTime`对象通过`atDate(LocalDate)`来合并`LocalDate`对象
            ```java
            LocalDate date = LocalDate.of(2014, 3, 18);
            LocalTime time = LocalTime.of(12, 45, 20);
            
            // 1.直接创建对象
            LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); //2014-03-18 18:13:45:20

            // 2.合并LocalDate和LocalTime
            LocalDateTime dt2 = LocalDateTime.of(date, time);

            // 3.LocalDate合并LocalTime对象
            LocalDateTime dt3 = date.atTime(time);

            // 4.LocalDate合并具体时间
            LocalDateTime dt4 = date.atTime(11, 12, 13);

            // 5.LocalTime合并LocalDate对象
            LocalDateTime dt5 = time.atDate(date);
            ```
        - 转换为:`LocalDate`或`LocalTime`
            ```java
            LocalDateTime dt = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); //2014-03-18 18:13:45:20
            LocalDate date = dt.toLocalDate();
            LocalTime time = dt.toLocalTime();
            ```

- `Instant` : 机器的日期和时间格式
    - 对于机器，建模时间最自然的格式是：`表示一个持续时间段上某个点的单一大整数`
    - `java.time.Instant`遵循这种机器建模时间的方式
        - 它以Unix元年时间开始经历的秒数计算
        - Unix元年时间：`1970-01-01 00:00:00`

    - 创建方法
        - 静态方法`ofEpochSecond`，返回在`1970-01-01 00:00:00`基础上增加毫秒数之后的Instant对象，类似于`Date(long date)`
            ```java
            // 4个一样的时间
            Instant.ofEpochSecond(3);
            Instant.ofEpochSecond(3, 0); // 通过第二个参数：纳秒来调整
            Instant.ofEpochSecond(2, 1_000_000_000); // 加1秒
            Instant.ofEpochSecond(4, -1_000_000_000); //减1秒
            ```
        - 构造当前时间的时间戳
            ```java
            Instant ins = Instant.now();
            ```

    - 它包含的是秒级纳秒所构成的数字，所以无法处理一般意义上的时间单位，如：年月日等等
        - 如果按照常规意义去获取某些值时，会导致异常：`UnsupportedTemporalTypeException`
            ```java
            int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
            ```

- 时间间隔：`Duration`或`Period`
    - `Duration`，使用秒和纳秒衡量时间的长短
    - `Period`，使用年、月、日的方式对多个时间单位建模
    - 时间间隔的计算不能混用，`Instant`和`LocalXXX`类型之间不能计算

    - 通过`between`来获取两个`Temporal`对象的时间间隔：
        ```java
        LocalTime time1 = LocalTime.of(11, 12, 13);
        LocalTime time2 = LocalTime.of(12, 13, 14);
        LoaclDate date1 = LoaclDate.of(2014, 01, 02);
        LoaclDate date2 = LoaclDate.of(2014, 01, 24);
        Instant instant1 = Instant.now();
        Instant instant2 = Instant.of(100);

        Duration d1 = Duration.between(time1, time2);
        Duration d2 = Duration.between(date1, date2);
        DUration d3 = Duration.between(instant1, instant2);

        Period p1 = Period.between(date1, dat2);
        ```
    - 通过静态方法直接定义`Duration`或`Period`对象
        - 示例
            ```java
            Durantion d1 = Durantion.ofMinutes(3);
            Durantion d2 = Durantion.of(3, ChronoUnit.MINUTES);

            Period p1 = Period.ofDays(10);
            Period p2 = Period.ofWeeks(3);
            Period p2 = Period.of(3);
            ....
            ```
    - `Duration`、`Period`中的一些构造对象的方法
        |方法名|静态方法|用途|
        |-|-|-|
        |between|是|创建两个时间点之间的interval|
        |from|是|由一个临时时间点创建interval|
        |of|是|由其组成部分来创建interval|
        |parse|是|由字符串创建interval|
        |addTo|否|创建当前interval的副本，并将其叠加到某个指定的temporal对象|
        |get|否|读取该interval的状态|
        |isNegative|否|检查interval是否为负值|
        |isZero|否|检查interval的时长是否为0|
        |minus|否|减去一定的时间创建该interval的副本|
        |multipliedBy|否|将interval的值乘一个标量来创建副本|
        |negated|否|以忽略某个时长的方式创建该interval的副本|
        |plus|否|以增加某个指定的时长的方式创建interval的副本|
        |subtractFrom|否|从指定的temporal对象中减去该interval|
        
## 操作-解析-格式化日期
[top](#catalog)
- 绝对修改方式：通过withAttribute方法来创建时间对象的副本，并按照需求修改它的属性
    ```java
    LocalDate date1 = LocalDate.of(2014, 3, 18); //2014-03-18
    LocalDate date2 = date1.withYear(2011);  //2011-03-18
    LocalDate date3 = date2.withDayOfMonth(25);  //2011-03-25
    LocalDate date4 = date1.with(ChronoField.MONTH_OF_YEAR, 9);  //2011-09-25
    ```
- 相对修改方式
    ```java
    LocalDate date1 = LocalDate.of(2014, 3, 18); //2014-03-18
    LocalDate date2 = date1.plusWeeks(1); //2014-03-25
    LocalDate date3 = date2.minusYears(3); //2011-03-25
    LocalDate date4 = data3.plus(6, ChronoUnit.MONTHS); //2011-09-25
    ```
- 使用`TemporalAdjuster`
    - 使用`Temporal`的with重载调用`TemporalAdjuster`来更加灵活的处理日期
        ```java
        default Temporal with(TemporalAdjuster adjuster) {
            return adjuster.adjustInto(this);
        }
        ```
    - 使用`TemporalAdjuster`的静态方法
        ```java
        LocalDate date1 = LocalDate.of(2014, 3, 18); //2014-03-18
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY)); //2014-03-23
        LocalDate date3 = date2.with(lastDayofMonth()); //2014-03-31
        ```
    - `TemporalAdjuster`提供的静态方法
        |方法名|用途|
        |-|-|
        |dayOfWeekInMonth|创建一个新的日期，它的值为同一个月中每一周的几天??????|
        |firstDayOfMonth|创建一个新的日期，值为当月的第一天|
        |lastDayOfMonth|创建一个新的日期，值为当月的最后一天|
        |firstDayOfNextMonth|创建一个新的日期，值为下月的第一天|
        |lastDayOfNextMonth|创建一个新的日期，值为下月的最后一天|
        |firstDayOfNextYear|创建一个新的日期，值为明年的一天|
        |lastDayOfNextYear|创建一个新的日期，值为明年的最后一天|
        |firstDayOfYear|创建一个新的日期，值为当年的第一天|
        |lastDayOfYear|创建一个新的日期，值为当年的最后一天|
        |firstInMonth|创建一个新的日期，值为同一个月中，第一个符合星期几要求的值 ?????|
        |lastInMonth|创建一个新的日期，值为同一个月中，最后一个符合星期几要求的值 ?????|
        |next/previous|创建一个新的日期，将日期向前后向后调整，第一个符合指定星期几要求的日期???????|
        |nextOrSame/previousOrSame|创建一个新的日期，将日期向前后向后调整，第一个符合指定星期几要求的日期，如果该对象已经符合要求，则直接返回|
    - `TemporalAdjuster`接口的内容
        ```java
        @FunctionalInterface
        public interface TemporalAdjuster {
        Temporal adjustInto(Temporal temporal);
        }
        ```
- `DateTimeFormatter`格式器
    - `DateTimeFormatter`的实例都是线程安全的
        - 可以通过单例模式创建实例，并在多个线程间共享这些实例
    - 格式化
        - 格式化`Temporal`对象
            ```java
            LocalDate date = LocalDate.of(2014, 3, 18);
            String s1 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); //2014-03-18
            ```
        - 在`parse`方法中指定解析方式
            ```java
            LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
            ```
    - `DateTimeFormatter.ofPattern(String pattern)` 自定义格式器
        ```java
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //自定义格式器
        LocalDate date1 = LocalDate.of(2014, 3, 18);    
        String dateStr = date1.format(formater); //格式化日期对象
        LocalDate date2 = LocalDate.parse(dateStr, formater); //通过自定义格式器来解析字符串
        ```
    - `DateTimeFormatter.ofPattern(String pattern, Locale locale)` 自定义格式器
        - 通过`locale`来创建某个Locale的格式器
            ```java
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("d. MMMM yyyy", Local.ITALIAN); //自定义格式器
            LocalDate date1 = LocalDate.of(2014, 3, 18);    
            String dateStr = date1.format(formater); //格式化日期对象
            LocalDate date2 = LocalDate.parse(dateStr, formater); //通过自定义格式器来解析字符串
            ```
    - 通过`DateTimeFormatterBuilder`来做细粒度的控制
        - 模型`Locale`的构建
            ```java
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .paeseCaseInsensitve()
                .toFormatter(Locale.ITALIAN);
            ```

## 处理不同的时区和历法
[top](#catalog)
- 使用新的`java.time.ZoneId`替代了`java.util.TimeZone`
- `ZoneId`的对象是不可修改的
- `ZondRules`这个类中包含了40个时区的实例，可以通过`ZondId`的`getRules`来获取指定时区的规则
- 获取默认时区
    ```java
    ZoneId.systemDefault();
    ```
- 每个`ZoneId`对象都由一个地区ID标识
    - 地区ID的格式：`区域/城市`，这些地区集合的设定有英特网编号分配机构的**时区数据库提供**
        ```java
        ZoneID romeZone = ZoneId.of("Europe/Rome");
        ```
    - 使用`toZondId()`将`TimeZone`转换为`ZoneId`
        ```java
        ZoneID zone = TimeZone.getDefault().toZondId();
        ```
    - 构造`ZonedDateTime`实例
        - 将`ZoneId`与`LocalDate`、`LocalDateTime`、`Instant`结合，它代表了相对于指定时区的时间点
            ```java
            ZoneID romeZone = ZoneId.of("Europe/Rome");
            
            // 与LocalDate结合
            LocalDate date = LocalDate.of(2014, 3, 18);
            ZonedDateTime zdt1 = date.atStartOfDay(romeZone);

            // 与LocalDateTime结合
            LocalDateTime dateTime = LocalDateTime.of(2014, 3, 18, 11, 12, 13);
            ZonedDateTime zdt2 = dateTime.atZone(romeZone);

            // 与Instant结合
            Instant instant = Instant.now();
            ZonedDateTime zdt3 = instant.atZone(romeZone);
            ```
    - 通过`ZoneId`，将`LocalDateTime`和`Instant`互相转换
        ```java
        ZoneID romeZone = ZoneId.of("Europe/Rome");

        // LocalDateTime ---> Instant
        LocalDateTime dateTime = LocalDateTime.of(2014, 3, 18, 11, 12, 13);
        Instant instant = dateTime.toInstant(romeZone);
        
        // Instant ---> LocalDateTime
        Instant now = Instant.now();
        LocalDateTime nowdt = LocalDateTime.ofInstant(now, romeZone);
        ```
- 利用和UTC/格林尼治时间的固定偏差计算时区
    - 另一种通用的时区表达方式：`计算当前时区时间和UTC/格林尼治时间的固定偏差`
    - `ZoneOffset`类
        - 该类表示的是：`计算当前时间和伦敦格林尼治子午线时间的差异`
            - 如：`ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");`
                - 使用这种方式定义的`ZoneOffset`未考虑任何日光时的影响，一般**不推荐使用**
        
        - `ZoneId`的一个子类
            - 可以像使用`ZoneId`一样使用`ZoneOffset`
        - `OffsetDateTime`
            - 使用`ISO-8601`历法系统
            - 将`ZoneOffset`与其他时间对象结合，创建`OffsetDateTime`
                ```java
                ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
                LocalDateTime dt = LocalDateTime.of(2014, 3, 18, 11, 12, 13);
                OffsetDateTime odt = OffsetDateTime.of(dt, newYorkOffset);
                ```
    - 使用别的日历系统
        - Java8默认使用`ISO-8601`日历系统
        - Java8中提供的其他四种日历系统
            - ThaiBuddhistDate
            - MinguoDate
            - JapaneseDate
            - HijrahDate
        - 四种日历系统+`LocalDate`都实现了`ChronoLocalDate`接口，能够对公历的日期进行建模
            - 通过`LocalDate`可以创建者四种日历系统的实例
                ```java
                LocalDate date = LocalDate.of(2014, 3, 18);
                JapaneseDate jdate = JapaneseDate.of(date);
                ```
        - 为某个`Locale`显示的创建日历系统，并创建该`Locale`对应的日期的实例
            - `Chronology`接口建模了一个日历系统，通过它的静态方法`ofLocale`来得到一个实例
                ```java
                Chronology jc = Chronology.ofLocale(Locale.JAPAN);
                ChronoLocalDate now = japaneseChronology.dateNow();
                ```
        - 应该尽量避免使用`ChronoLocalDate`，因为在它的实现中会存在一些假设
        - 存储、操作是应该使用`LocalDate`，做本地化时可以使用`ChronoLocalDate`


# 函数式编程
## 函数式java编程
[top](#catalog)
- 无副作用的含义
    - 一个方法既不修改它内嵌类的状态，也不修改其他对象的状态，使用`return`返回所有的计算结果
    - 副作用包括
        - 除了构造方法之外，对类中数据结构的任何修改，包括字段的赋值操作
        - 抛出一个异常
- 函数式的要求
    - 需要保证没有人能察觉代码中的副作用
        - 如果另一个线程可以查看字段的值，或者方法会同时被多个线程并发调用，就**不能称为函数式的实现**
            - 对于并发，可以对共享资源进行加锁，但是会丧失并发执行的能力

    - 函数式方法/函数都**只能修改本地变量，它引用的对象都应该是不可修改的对象**
    - 函数式方法**不能抛出任何异常**
        - 因为一点抛出异常，执行过程就被终止了，不能像黑盒模式一样，有`return`返回一个恰当的结果值
        - 使用`Optional<T>`类型；
    - 如果函数有副作用，必须设法隐藏它们的非函数式行为，否则就不能调用会泽县方法
        - 需要确保对数据结构的任何修改对于调用者都是不可见的
            - 可以通过首次赋值、捕获任何可能抛出的异常来实现
- 引用透明性
    - 如果一个函数只要传递同样的参数值，总是返回同样的结果，那这个函数就是`引用透明的`
- 尾递归优化
    - 编写一个迭代方法的定义，但是迭代的调用发生在函数的最后
    - 示例：计算阶乘
        ```java
        static long factorialTailRecursive(long n) {
            return factorialHelper(1, n);
        }

        static long factorialHelper(long acc, long n) {
            return n ==1 ? acc : factorialHelper(acc * n, n-1);
        }
        ```
## 函数的类型
[top](#catalog)
- 高阶函数
    - 高阶函数的条件
        - 接受至少一个函数作为参数
        - 返回的结果是一个函数
- 科里化
    - 原始的计算方式
        - 不是每次都需要传递`f`和`b`，每次调用都传递三个参数可能会造成输入错误
        ```java
        static double converter(double x, double f, double b) {
            return x * f + b;
        }
        ```
    - 使用科里化改造
        ```java
        static DoubleUnaryOperator converter
        ```




[top](#catalog)
---------------------------------------------------------------------------


    ```java
    public class Shop {
        //根据指定名称返回价格
        public double getPrice(String product){
            //可以执行：DB访问，联系其他外部服务
            calculatePrice(product);
        }

        // 生成随机价格
        private double calculatePrice(String product){
            delay();
            return random.nextDouble() * product.charAt(0) + product.charAr(1);
        }

        // 模拟延迟的方法
        public static void delay() {
            try{
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    ```

        ```java
        public class Shop {
            //根据指定名称返回价格
            public double getPrice(String product){
                //可以执行：DB访问，联系其他外部服务
                calculatePrice(product);
            }

            //根据指定名称返回价格，返回Future对象，可以立即返回，可以通过get方法获取执行结果
            public Future<Double> getPriceAsync(String product) {
                // 创建一个代表异步计算的实例
                CompletableFuture<Double> futurePrice = new CompletableFuture<>();
                new Thread(
                    () -> {
                        double price = calculatePrice(product); //获取价格
                        futurePrice.complete(price); //结束当前任务，设置Future的返回值
                    }
                );

                return futurePrice;
            }

            // 生成随机价格
            private double calculatePrice(String product){
                delay();
                return random.nextDouble() * product.charAt(0) + product.charAr(1);
            }

            // 模拟延迟的方法
            public static void delay() {
                try{
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ```




`Stream.of` 可以通过显示值创建一个流
`Stream.empty()`获得一个空流
`Arrays.stream()`，从数组创建一个流
`Stream.iterate(初始值, UnaryOperator<T>)`从函数生成流
`Stream.generate(Supplier<T>)`

中间操作
filter, map, limit, distinct, sort
终端操作
collect, reduce, sum, max

复合Lambda表达式
Comparator:reversed,thenComparing
Predicate:negate,and,or
Function:andThen,compose

收集器
counting
maxBy
minBy
summingXXX
averagingXXX
summarizingXXX
joining

comparing
comparingInt




-----------------------------------
- 参考:`https://www.cnblogs.com/CarpenterLee/p/6637118.html`
- Stream流水线解决方案
    - 操作如何记录
        - 指中间操作
        - 操作的描述：`Stage<数据来源，操作，回调函数>`
            - 使用`pipelineHelper`表示`Stage`
        - `Head`表示第一个Stage，如`Collection.stream()`产生的Stage
            - 这个Stage中不包含任何操作
        - `StatelessOp`，表示无状态的中间操作
        - `StatefulOp`，表示有状态的中间操作
    - 操作如何叠加
        - 基本问题：
            - 前面的Stage并不直到后面的Stage执行了哪种操作、回调函数是哪种形式
            - 只有当前Stage本身才知道该如何执行自己包含的动作
        - 通过`Sink`协议接口来解决问题
            - 包含的方法
                - `void begin(long size)`:开始遍历元素之前调用该方法，通知Sink做好准备
                - `void end()`:所有元素遍历完成之后调用，通知Sink没有更多的元素了
                - `boolean cancellationRequested()`:是否可以结束操作，可以让短路操作尽早结束
                - `void accept(T t)`
                    - **遍历元素时**调用，接受一个待处理元素，并对元素进行处理
                    - `Stage`把自己包含的**操作和回调函数**封装到该方法力，前一个Stage只需要调用当前`Stage.accept(T t)`方法就可以了???????

        - 每个Stage都会将自己的操作封装到一个Sink中，前一个Stage只要调用后一个`Stage.accept()`，不需要知道其内部是如何处理的
            - 对于**有状态**操作，必须实现`begin`,`end`
                - 如`sorted`，它的`begin`会创建一个保存结果的容器，`accept`会将元素添加到容器中，`end`负责对容器进行排序
        - <label style="color:red">`Stream API`内部实现的本质，就是如何重载`Sink`接口</label>
        - 执行时，只需要从流水线的`head`开始对数据源依次调用每个`Stage`对应的`Sink.{begin, accept, cancellationRequested, end}`

- Map的Sink解析
    -


数据保存在：spliterator
操作保存在各PipelineHelper
    执行时转换成sink
        所以每个sink的Sink操作需要调用下一个sink的Accept来完成链式调用
整体要通过stream的终端操作来启动
        


----------

可自定义部分
- 收集器Collector
    ```java
    public interface Collector<T, A, R> {
        Supplier<A> supplier();
        BiConsumer<A, T> accumulator();
        BinaryOperator<A> combiner();
        Function<A, R> finisher();
        Set<Characteristics> characteristics();
    }
    ```

- 直接使用并行流出现问题是，使用自定义`RecursiveTask`,通过`ForkJoin`调用
    ```java
    public interface RecursiveTask<Long> { 
        protected Long compute();
    }
    ```

- 使用`Spliterator`接口来拆分流
    ```java
    public interface Spliterator<T> {
        boolean tryAdvance(Consumer<? super T> action);
        Spliterator<T> trySplit();
        long estimateSize();
        int characteristics();
    }
    ```
