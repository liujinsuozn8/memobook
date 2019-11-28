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
    - [复合Lambda表达式的有用方法](#复合lambda表达式的有用方法)
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
- 使用map保存一些构造函数引用，根据需求示例化对应的对象
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

## 复合Lambda表达式的有用方法
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
        - 流会使用那个一个提供数据的源，包括：**集合、数组、输入/输出资源**
            - 从**有序集合**生产流时会保留原有的顺序
            - 由列表生成的流，其元素顺序与列表一致
    - 数据处理操作
        - 流的数据处理功能支持类似与数据库的操作，以及函数式编程语言中的常用操作
        - 包括：filter, map, reduce, find, match, sort
        - **流的操作可以顺畅执行，也可以并行执行** (stream/parallelStream??????)
- 流的两个重要特点
    - 流水线
        - 很多流操作本身会返回一个流，多个操作可以进行链接，形成一个大的流水线
        - 可以在流水线中进行优化，包括：`延迟、短路` 等等
        - 流水线的操作可以看作：**对数据源进行数据库式查询**
        - **流水线本身并会生产任何结果，相当于做了一系列的配置**
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
    - 对于流，流水在概念行固定的数据结构，因此**无法添加/删除元素**，流中的元素是`按需计算的`
        - `按需计算/生成`可以理解为`生产者-消费者`的关系
        - `按需计算/生成`也可以理解为**延迟创建的集合**：只有在消费者要求的时候才会计算值
- **流只能遍历一次**（这一点与迭代器相同）
    - 遍历结束后，这个流就已经被**消费掉了**
    - 如果想再次遍历，可以从数据源再获取一个流，并重新遍历一遍
        - 集合数据源可以，如果是IO资源，可能就不行了
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
    - 无状态：没有内部状态
    - 像`map`,`filter`等操作会从输入流中获取每一个元素，并在输出流中得到0或1个结果

-StreamAPI提供的操作
    - 中间操作

    |操作|类型|返回类型|操作参数|函数描述符|
    |-|-|-|-|-|
    |filter|中间|Stream<T>|Predicate<T>|T -> boolean|
    |map|中间|Stream<T>|Function<T,R>|T -> R|
    |limit|中间|Stream<T>|-|-|
    |sorted|中间|Stream<T>|Comparator<T>|(T, T) -> int|
    |distinct|中间|Stream<T>|-|-|

    - 终端操作

    |操作|类型|目的|
    |-|-|-|
    |forEach|终端|消费流中的每个元素并对其应用Lambda表达式。返回void|
    |count|终端|返回流中的元素个数。返回long|
    |collect|终端|把流归约成一个集合，如List、Map、甚至是Integer|

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
        - 即：将一个流中的每一个值都转换成另一个流，然后把usuoyou的流连接起来称为一个流

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
                                    .flatMap(i -> numbers1.stream()
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
    - 流水线在后台进行优化使其只需走一遍，并在利用短路找到结果时立即结束?????????????????????????
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
    - 无初始值`reduce(BinaryOperator<T>)`
        - 返回一个`Optional<Integer>`
            - 如果是`空流`,reduce操作无法返回结果，因为没有初始值，通过`Optional`来表明结果可能不存在

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




[top](#catalog)
