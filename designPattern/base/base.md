- 参考
    - https://www.bilibili.com/video/av57936239

<span id="catalog"></span>

### 目录
- [设计模式简介](#设计模式简介)
- 设计原则
    - [设计模式七大原则](#设计模式七大原则)
    - [设计原则核心思想](#设计原则核心思想)
    - [单一职责原则](#单一职责原则)
    - [接口隔离原则](#接口隔离原则)
    - [依赖倒转原则](#依赖倒转原则)
        - [如何使用依赖倒转原则](#如何使用依赖倒转原则)
        - [依赖关系传递的三种方式和应用案例](#依赖关系传递的三种方式和应用案例)
        - [依赖倒转原则的注意事项和细节](#依赖倒转原则的注意事项和细节)
    - [里氏替换原则](#里氏替换原则)
        - [什么是里氏替换原则](#什么是里氏替换原则)
        - [OO中的继承性思考和说明](#OO中的继承性思考和说明)
    - [开闭原则](#开闭原则)
    - [迪米特法则](#迪米特法则)
    - [合成复用原则](#合成复用原则)
- [UML类图](#UML类图)
    - [UML图简介](#UML图简介)
    - [UML类图](#UML类图)
    - [UML类图的6种实体关系](#UML类图的6种实体关系)
- [设计模式的分类](#设计模式的分类)
- 创建型-单例设计模式
    - [单例设计模式简介](#单例设计模式简介)
    - [单例模式形式1-饿汉式-静态常量](#单例模式形式1-饿汉式-静态常量)
    - [单例模式形式2-饿汉式-静态代码块](#单例模式形式2-饿汉式-静态代码块)
    - [单例模式形式3-懒汉式-线程不安全](#单例模式形式3-懒汉式-线程不安全)
    - [单例模式形式4-懒汉式-线程安全-同步方法](#单例模式形式4-懒汉式-线程安全-同步方法)
    - [单例模式形式5-懒汉式-线程不安全-同步代码块](#单例模式形式5-懒汉式-线程不安全-同步代码块)
    - [单例模式形式6-懒汉式-双重检查](#单例模式形式6-懒汉式-双重检查)
    - [单例模式形式7-静态内部类中的静态属性](#单例模式形式7-静态内部类中的静态属性)
    - [单例模式形式7-枚举](#单例模式形式7-枚举)
    - [单例模式在JDK中的应用](#单例模式在JDK中的应用)
- 创建型-工厂模式
    - [问题引入-pizza订购](#问题引入-pizza订购)
    - [简单工厂模式](#简单工厂模式)
    - [静态工厂模式](#静态工厂模式)
    - [工厂方法模式](#工厂方法模式)
    - [抽象工厂模式](#抽象工厂模式)
    - [工厂模式总结](#工厂模式总结)
- [创建型-原型模式](#创建型-原型模式)
    - [问题引入-克隆羊](#问题引入-克隆羊)
    - [原型模式的基本概念](#原型模式的基本概念)
- [](#)
- 结构型-代理模式
    - [代理模式简介](#代理模式简介)
    - [静态代理](#静态代理)
    - [动态代理](#动态代理)
    - [Cglib代理](#Cglib代理)
    - [代理模式的变体](#代理模式的变体)
- [](#)


# 设计模式简介
[top](#catalog)
- 经常会被问到的问题：用过什么设计模式，怎么使用的，解决了什么问题
- 软件使用设计模式的目的

    |No|目的|实际意义|
    |-|-|-|
    |1|代码重用|相同功能的代码，不用多次编写|
    |2|可读性|待的规范性，便于其他人的阅读和理解|
    |3|可扩展性|当需要添加新的功能是，非常的方便，也称为可维护性|
    |4|可靠性|当增加新的功能后，对原来的功能没有影响|
    |5|高内聚，低耦合||
    
- 设计模式需要思考内容：**如何使用项目结构更加合理**，而不是如何实现
- 设计模式包含了面向对象的精髓
- 设计模式在软件中的那些位置会用到？
    - 功能模块 = 设计模式 + 算法(数据结构)
    - 框架 = 多种设计模式

# 设计原则
## 设计模式七大原则
[top](#catalog)
- 什么是设计原则
    - 设计原则就是**编写程序时，应该遵守的原则**
    - 设计原则是**各种设计模式的基础**，即各个模式的设计依据
    
- 设计模式常用的七大原则
    1. 单一职责原则
    2. 接口隔离原则
    3. 依赖倒转原则
    4. 里氏替换原则
    5. 开闭原则ocp
    6. 迪米特法则
    7. 合成复用原则

## 设计原则核心思想
[top](#catalog)
- 分离应用中经常变化和固定不变的部分
- 针对接口编程，而不是针对实现编程
- 为了交互对象之间的松耦合设计而努力

## 单一职责原则
[top](#catalog)
- SingleReponsibility
- 基本介绍：对类来说，即<label style="color:red">一个类应该只负责一项职责</label>
- 多职责的问题？
    - 如果存在A负责两个不同职责：`职责1`、`职责2`，当`职责1`需要改变A时，可能会造成`职责2`的执行错误
    - 为了避免为题，可以将类A分解为A1、A2
    
- 结合实例说明:交通工具的功能分解
    - 方案1
        - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility1.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility1.java)
        - 打印结果的问题：`vehicle.run("飞机");`，打印了飞机正在路上行驶，这不符合常识
        - 方案1的问题：所有功能集中在run方法中，违反了单一职责原则，导致了不符合常识的打印内容
        - 解决方案：根据交通工具的运行方法，分解成不同类 --> 方案2
        - 代码
            ```java
            // 客户端
            public class SingleResponsibility1 {
                public static void main(String[] args) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.run("摩托车");//摩托车正在路上行驶
                    vehicle.run("汽车"); //汽车正在路上行驶
                    vehicle.run("飞机"); //打印：飞机正在路上行驶，不符合常识
                }
            }
            
            // 交通工具
            class Vehicle{
                public void run(String vehicle){
                    System.out.println(vehicle + "正在路上行驶");
                }
            }
            ```
    - 方案2
        - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility2.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility2.java)
        - 方案2的优点：不同的类负责不同的功能，遵守了单一职责原则
        - 方案2的问题：在方案1的基础上**做了非常大的改动**，同时修改了类和客户端
        - 解决方案：直接修改Vehicle类，改动的代码会比较少 --> 方案3
        - 代码：
            ```java
            // 客户端
            public class SingleResponsibility2 {
                public static void main(String[] args) {
                    RoadVehicle roadVehicle = new RoadVehicle();
                    roadVehicle.run("摩托车");//摩托车正在路上行驶
            
                    AirVehicle airVehicle = new AirVehicle();
                    airVehicle.run("飞机");//飞机正在飞行
            
                    WaterVehicle waterVehicle = new WaterVehicle();
                    waterVehicle.run("船"); //船正在水中行驶
                }
            }
            
            // 交通工具
            class RoadVehicle{
                public void run(String vehicle){
                    System.out.println(vehicle + "正在路上行驶");
                }
            }
            
            class AirVehicle{
                public void run(String vehicle){
                    System.out.println(vehicle + "正在飞行");
                }
            }
            
            class WaterVehicle{
                public void run(String vehicle){
                    System.out.println(vehicle + "正在水中行驶");
                }
            }
            ```
    - 方案3
        - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility3.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/singleresponsibility/SingleResponsibility3.java)
        - 方案3的优点：这种修改方法没有对原来的类做过多的修改，只是增加了方法
        - 方案3的问题：没有在类这个级别上遵守单一职责原则，**只是在方法级别上，遵守了单一职责**
        - 代码
            ```java
            // 客户端
            public class SingleResponsibility3 {
                public static void main(String[] args) {
                    Vehicle2 vehicle2 = new Vehicle2();
                    vehicle2.run("汽车");
                    vehicle2.runAir("飞机");
                    vehicle2.runWater("船");
                }
            }
            
            // 交通工具
            class Vehicle2{
                public void run(String vehicle){
                    System.out.println(vehicle + "正在路上行驶");
                }
            
                public void runAir(String vehicle){
                    System.out.println(vehicle + "正在飞行");
                }
            
                public void runWater(String vehicle){
                    System.out.println(vehicle + "正在水中行驶");
                }
            }
            ```
        
- **单一原则的注意事项和细节**
    - 降低类的复杂度，一个类只负责一项职责
    - 提高类的可读性，可维护性
    - 降低变更带来的风险
        - 如果使用的是`方式2`，则哪种类别需要变更就改哪个类，不会影响其他类的功能
        - 如果使用的是`方式3`，则哪种别需要变更就改哪个方法，不会影响其他方法的功能
        - 如果使用方式1，则改动都集中在一个方法中，会降低可维护性
    - 通常情况下，应当遵守单一职责原则，但是特殊情况下可以进行**退化**
        1. 只有在逻辑足够简单时，才能在代码级别违反单一职责原则
        2. 只有类中方法数量足够少时，才可以在方法级别保持单一职责原则，如`方式2`
    
## 接口隔离原则
[top](#catalog)
- Interface Segregation Principle
- 基本介绍：客户端不应该依赖他不需要的接口，即<label style="color:red">一个类对另一个类的依赖应该建立在最小的接口上</label>
- 接口冗余的示例说明
    - 示例的UML图
        - ![dependency_origian](imgs/principle/Interface_Segregation_Principle/dependency_origian.png)
    - 示例代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/interfaceSegregation/origian/Segregation1.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/interfaceSegregation/origian/Segregation1.java)
    - 依赖关系
        1. B、D是接口Interface1的接口
        2. A通过Interface1依赖B，但是A只会使用到接口中的1、2、3方法；4、5方法没有使用，导致B中产生了多余的实现
        3. C通过Interface1依赖D，但是C只会使用到接口中的1、4、5方法；2、3方法没有使用，导致D中产生了多余的实现
    - 接口冗余的问题
        - 类A通过接口Interface1依赖类B，类C通过接口Interface1依赖D，**如果接口Interface1对于类A和类C不是最小接口，那么类B和类B必须去实现他们不需要的方法**
- 接口隔离原则的处理方式
    - 将接口Interface1拆分为独立的几个接口，类A和类C分别与他们需要的接口建立依赖关系
    - 将接口Interface1中出现的方法，根据实际情况拆分为3个接口
- 接口冗余的改进
    - 改进的UML图
        - ![dependency_improve](imgs/principle/Interface_Segregation_Principle/dependency_improve.png)
    - 示例代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/interfaceSegregation/improve/Segregation2.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/interfaceSegregation/improve/Segregation2.java)
    - 依赖关系
        - B实现了`Interface1`和`Interface2`
        - D实现了`Interface1`和`Interface3`
        - A通过`Interface1`和`Interface2`接口来依赖B
        - C通过`Interface1`和`Interface2`接口来依赖D
    - 改进后的优点
        - 接口实现类中没有多余的实现
        - 类之间的依赖都建立在最小的接口上


## 依赖倒转原则
### 如何使用依赖倒转原则
[top](#catalog)
- Dependency Inversion Principle
- 中心思想
    - 中心思想是面向接口编程
    - 高层模块不应该依赖低层模块，二者都应该依赖抽象
    - 抽象不应该依赖细节，细节应该依赖抽象
- 依赖倒转原则的设计理念
    - 相对于细节的多变性，抽象的东西要稳定的多，以抽象为基础搭建的架构比以细节为基础搭建的架构要稳定的多。
- 依赖倒转原则在java中的体现
    - `抽象=接口、抽象类`
    - `细节=具体的实现类`
    - 使用接口或抽象类的目的是制定好规范，**而不涉及任何具体的操作**，把展现细节的任务交给他们的实现类去完成

- 直接依赖的示例说明：接受消息的功能
    - 示例的UML图
        - ![person_origian](imgs/principle/Dependency_Inversion/person_origian.png)
    - 代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/origian/DependencyInversion1.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/origian/DependencyInversion1.java)
        
        ```java
        public class DependencyInversion1 {
            public static void main(String[] args) {
                Person person = new Person();
                person.receive(new Email());
                person.receive(new Phone());
            }
        }
        
        class Email{
            public String getInfo(){
                return "email Info ";
            }
        }
        
        class Phone{
            public String getInfo(){
                return "phone Infor";
            }
        }
        
        // 完成接受消息的功能
        class Person {
            // 分别接收不同信息
            public void receive(Email email){
                System.out.println(email.getInfo());
            }
        
            public void receive(Phone phone){
                System.out.println(phone.getInfo());
            }
        }
        ```
    - 直接依赖的问题
        - 每当新增信息源时，都需要新增一个对应类，并且在Person中也要增加相应的接收方法重载
    - 解决方案：
        - 引入一个接口IReceive，表示接收者，使Person类只与接口IReceive发生依赖
        - Email，Wexin等等都属于接收者的范畴，这些类分别实现IReceive接口，来完成依赖的倒转

- 通过依赖倒转原则来解决示例的问题
    - 改善后的UML图
        - ![person_improve.png](imgs/principle/Dependency_Inversion/person_improve.png)
        - 代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/improve/DependencyInversion2.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/improve/DependencyInversion2.java)
            ```java
            public class DependencyInversion2 {
                public static void main(String[] args) {
                    Person person = new Person();
                    person.receive(new Email());
                    person.receive(new Phone());
                    person.receive(new Weixin());
                }
            }
            
            // 引入一个接口IReceive，表示接受者
            interface IReceive{
                String getInfo();
            }
            class Email implements IReceive{
                public String getInfo(){
                    return "email Info ";
                }
            }
            
            class Phone implements IReceive{
                public String getInfo(){
                    return "phone Infor";
                }
            }
            
            class Weixin implements IReceive{
                public String getInfo(){
                    return "weixin Infor";
                }
            }
            
            // 完成接受消息的功能
            class Person {
                // 与接口IReceive发生依赖，来接收不同的信息
                public void receive(IReceive receiver){
                    System.out.println(receiver.getInfo());
                }
            }
            ```
    - 改进后的优点
        - Person依赖于接口IReceive，每次增加新的信息源时，不需要修改Person，只需要添加IReceive的实现类
        - 由由于Person依赖于接口IReceive，自需要一个方法来处理接口的实例对象，减少了不必要的重载方法
        
### 依赖关系传递的三种方式和应用案例
[top](#catalog)
- 三种传递方式
    1. 接口传递
    2. 构造方法传递
    3. setter方式传递

- **接口传递**的应用
    - 使用方法：在主要方法中声明接口参数，来传递依赖
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/first/DeliverByInterface.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/first/DeliverByInterface.java)
        ```java
        public class DeliverByInterface {
            public static void main(String[] args) {
                //创建一个电视机
                ITV mytv = new MyTV();
                //创建一个开关
                IOpenAndClose openAndClose = new OpenAndClose();
        
                //通过接口将依赖传递到类中
                openAndClose.open(mytv);
            }
        }
        
        //电视机接口
        interface ITV{
            void play();
        }
        
        //电视机开关接口 通过接口来传递依赖
        interface IOpenAndClose {
           void open(ITV tv);
        }
        
        //电视机开关接口的实现
        class OpenAndClose implements IOpenAndClose{
        
            // 通过接口将依赖传递到了类内部
            @Override
            public void open(ITV tv) {
                tv.play();
            }
        }
        
        // 电视机接口
        class MyTV implements ITV {
        
            @Override
            public void play() {
                System.out.println("myTV is showing");
            }
        }
        ```
    - UML类图
        - ![imgs/principle/Dependency_Inversion/DeliverByInterface.png](imgs/principle/Dependency_Inversion/DeliverByInterface.png)
        
- 构造方法传递
    - 接口之间完全看不出依赖关系，只是通过实现类的构造器来关联
    - 使用方法：
        1. 在实现类中添加用来保存依赖接口对象的属性
        2. 创建带参数的构造器，将依赖传入，并保存在对象中
        3. 使用时，直接使用对象内部保存的依赖对象
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/second/DeliverByConstructor.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/second/DeliverByConstructor.java)
        ```java
        // 依赖关系传递的三种方式和应用案例
        // 构造方法传递
        public class DeliverByConstructor {
            public static void main(String[] args) {
                // 创建一个电视
                ITV mytv = new MyTV();
                // 创建一个开关
                // 创建时，将电视作为依赖传递到开关中
                IOpenAndClose openAndClose = new OpenAndClose(mytv);
        
                // 直接调用方法
                openAndClose.open();
            }
        }
        
        // 从接口上看，两个接口ITV和IOpenAndClose没有直接的关系
        interface ITV {
            void play();
        }
        
        interface IOpenAndClose{
            void open();
        }
        
        // 在实现类中，将ITV的实现类对象作为私有属性，通过构造方法传递依赖
        class OpenAndClose implements IOpenAndClose{
            private ITV tv;
        
            public OpenAndClose(ITV tv) {
                this.tv = tv;
            }
        
            // 调用依赖对象的方法
            @Override
            public void open() {
                tv.play();
            }
        }
        
        class MyTV implements ITV{
        
            @Override
            public void play() {
                System.out.println("MyTV is showing");
            }
        }
        ```
    - UML类图
        - ![DeliverByConstructor](imgs/principle/Dependency_Inversion/DeliverByConstructor.png)
        
- setter方式传递
    - 使用方法
        1. 在实现类中添加用来保存依赖接口对象的属性
        2. 在接口中添加一个setter方法，用来保存依赖
        3. 使用时，直接使用对象内部保存的依赖对象
    - 与`构造方法传递`类似，只不过**传递依赖的时间被推迟了**
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/third/DeliverBySetter.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/dependencyInversion/threePattern/third/DeliverBySetter.java)
        ```java
        public class DeliverBySetter {
            public static void main(String[] args) {
                //创建一个电视机
                ITV mytv = new MyTV();
                //创建一个开关
                IOpenAndClose openAndClose = new OpenAndClose();
        
                //通过setter将依赖传递到开关中
                openAndClose.setTv(mytv);
                //执行fangf
                openAndClose.open();
            }
        }
        
        interface ITV {
            void play();
        }
        
        interface IOpenAndClose{
            void open();
            void setTv(ITV tv);
        }
        
        // 在实现类中，将ITV的实现类对象作为私有属性，通过setter方法传递依赖
        class OpenAndClose implements IOpenAndClose{
            private ITV tv;
        
            public void setTv(ITV tv) {
                this.tv = tv;
            }
        
            @Override
            public void open() {
                tv.play();
            }
        }
        
        class MyTV implements ITV {
        
            @Override
            public void play() {
                System.out.println("MyTV is showing");
            }
        }
        ```
    - UML类图
        - ![DeliverBySetter](imgs/principle/Dependency_Inversion/DeliverBySetter.png)
        
### 依赖倒转原则的注意事项和细节
[top](#catalog)
- 底层模块尽量都要有抽象类或接口，或者两者都有，程序的稳定性更好
- **变量的声明类型尽量使用抽象类或接口**，这样我们的变量引用和实际对象间，就存在一个缓冲层，利于程序扩展和优化
- 继承时需要遵守里氏替换原则

## 里氏替换原则
### OO中的继承性思考和说明
[top](#catalog)
- 继承包含的另一层含义
    - 父类中已经实现的方法，实际上是在设定一直规范和契约。但是不强制子类必须遵守这些规范
    - 如果子类对已经实现的方法进行**任意修改，将会破坏整个继承体系**
- 继承带来的弊端
    - 使用继承会给程序带来侵入性，导致程序的可移植性降低、增加对象间的耦合度
    - 如果一个类A被其他的类所继承，当类A需要修改时，**必须考虑到所有的子类**。并且父类修改后，**所有涉及到子类的功能都可能产生故障**
- 在编程中，如何正确的使用继承：里氏替换原则

### 什么是里氏替换原则
[top](#catalog)
- 里氏替换原则的概念
    - 详细描述：如果对每个类型为T1的对象o1，都有类型为T2的对象o2，使得以T1定义的所有程序P在所有的对象o1都代换成o2时，程序P的行为没有发生变化，那么类型T2是类型T1的子类型
    - 简单来说，即：所有引用基类的地方必须能透明的使用其子类的对象
- 里氏替换原则的中心思想： **在子类中尽量不要重写父类的方法**
- 继承实际上使两个类耦合性增强了，在适当的情况下，可以通过**聚合、组合、依赖、抽象出更高阶的父类**来解决问题

### 示例说明里氏替换原则
[top](#catalog)
- 不实用里氏替换原则
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/liskov/LiskovOrigian.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/liskov/LiskovOrigian.java)
    - B继承了A，但是重写方法时，更改了父类方法的功能，导致调用时会有不同的结果
    - 代码
        ```java
        public class LiskovOrigian {
            public static void main(String[] args) {
                A a = new A();
                B b = new B();

                // 虽然有继承关系，但是B重写了父类的方法，导致同样的方法结果不同
                a.func1(11,8);
                b.func1(11,8);
            }
        }


        class A{
            public int func1(int num1, int num2){
                return num1 - num2;
            }
        }

        class B extends A {
            //B无意中重写了A的方法，并且两个方法的含义完全不同
            public int func1(int a, int b){
                return a + b;
            }

            public int func2(int a, int b){
                return func1(a, b) + 9;
            }
        }
        ```

- 使用里氏替换原则来改进：抽象出更高阶的父类
    - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/liskov/LiskovImprove.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/liskov/LiskovImprove.java)
    - 抽象出一个更基本的类Base，A和B都继承Base，并提供相同的方法，但实现内容各不相同。
    - 在调用A和B的功能时更加清晰，不会被A和B的继承关系误导
    - 在B中组合A，使得通过B也能完成A的功能
    - 改进之后的UML图
        ![Liskov_improve_uml](imgs/principle/Liskov/Liskov_improve_uml.png)
    - 代码
        ```java
        public class LiskovImprove {
            public static void main(String[] args) {
                A2 a = new A2();
                B2 b = new B2();

                // A和B通过父类Base进行了解耦，在调用时，各方法的意义更加明确
                a.func1(11,8);
                b.func1(11,8);

                // 如果在B中仍然想使用A的方法，可以通过组合关系来调用
                b.func3(11, 8);
            }
        }

        // 构造更基础的类，来降低耦合
        abstract class Base{
            public abstract int func1(int num1, int num2);
        }

        class A2 extends Base{
            public int func1(int num1, int num2){
                return num1 - num2;
            }
        }

        // 在B中，与A使用组合关系，并通过A来实现部分功能
        class B2 extends Base {
            private A2 a = new A2();
            //B无意中重写了A的方法，并且两个方法的含义完全不同
            public int func1(int a, int b){
                return a + b;
            }

            public int func2(int a, int b){
                return func1(a, b) + 9;
            }

            //使用A的方法来实现功能
            public int func3(int a, int b){
                return this.a.func1(a, b);
            }
        }
        ```

## 开闭原则
[top](#catalog)
- Open Closed Principle
- 开闭原则是编程中最基础，最重要的设计原则
- **基本原则**
    - 一个软件实体如类、模块、函数应该对扩展开放(**对提供方**)，对修改关闭(**对使用方**)。用抽象构建框架，用实现扩展细节
    - 当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化
    - 编程中遵循其他原则，以及使用设计模式的目的就是遵循开闭原则
    
- 结合示例说明
    - 参照：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/openClose/origian/OcpOrigian.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/openClose/origian/OcpOrigian.java)
    - 该示例的问题
        - 没有遵守ocp原则，当新增一个三角形时，绘图类(即使用方)需要修改判断逻辑，并添加相应的处理方法，修改比较大
    - 代码
        ```java
        public class OcpOrigian {
            public static void main(String[] args) {
                GraphicEditor graphicEditor = new GraphicEditor();
        
                graphicEditor.drawShape(new Rectangle());
                graphicEditor.drawShape(new Circle());
        
                graphicEditor.drawShape(new Triangle());
        
            }
        }
        
        class Shape{
            int m_type;
        }
        
        class Rectangle extends Shape{
            public Rectangle() {
                super.m_type = 1;
            }
        }
        
        class Circle extends Shape {
            public Circle() {
                super.m_type = 2;
            }
        }
        
        // 新增一个三角形
        class Triangle extends Shape{
            public Triangle() {
                super.m_type = 3;
            }
        }
        
        // 一个用于绘图的类
        class GraphicEditor{
            //根据Shape的值，来绘制不通过的图形
            public void drawShape(Shape s){
                if (s.m_type == 1)
                    drawRectangle(s);
                else if (s.m_type == 2)
                    drawCircle(s);
                //如果新增了一个三角形，则需要添加判断逻辑
                else if (s.m_type == 3)
                    drawTriangle(s);
            }
        
            public void drawRectangle(Shape r){
                System.out.println("Rectangle");
            }
        
            public void drawCircle(Shape r){
                System.out.println("Circle");
            }
        
            //如果新增了一个三角形，则需要单独添加处理方法
            public void drawTriangle(Shape s){
                System.out.println("Triangle");
            }
        }
        ```
      
- 使用开闭原则来修改示例
    - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/openClose/improve/OcpImprove.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/openClose/improve/OcpImprove.java)
    - 解决方案
        1. 将Shape类作为抽象类，提供一个抽象的draw方法，让子类去实
        2. 当出现新的图形类别时，让图形继承Shape，并实现draw方法，防止修改使用方的代码
    - 修改后的UML图
        - ![ocp_improve_uml](imgs/principle/Open_Close/ocp_improve_uml.png)
    - 代码
        ```java
        public class OcpImprove {
            public static void main(String[] args) {
                GraphicEditor graphicEditor = new GraphicEditor();
        
                graphicEditor.drawShape(new Rectangle());
                graphicEditor.drawShape(new Circle());
        
                graphicEditor.drawShape(new Triangle());
        
            }
        }
        
        // 创建抽象类
        abstract class Shape{
            public abstract void draw();
        }
        
        // 个图形分别实现绘制方法
        class Rectangle extends Shape {
            @Override
            public void draw() {
                System.out.println("Rectangle");
            }
        }
        
        class Circle extends Shape {
            @Override
            public void draw() {
                System.out.println("Circle");
            }
        }
        
        //如果新增了一个三角形，则只需要添加图形类即可，不需要修改绘图类
        class Triangle extends Shape {
            @Override
            public void draw() {
                System.out.println("Triangle");
            }
        }
        
        // 一个用于绘图的类
        class GraphicEditor{
            //根据Shape的值，来绘制不通过的图形
            public void drawShape(Shape s) {
                s.draw();
            }
        }
        ```

## 迪米特法则
[top](#catalog)
- Demeter Principle
- 迪米特法则也称为**最少知道原则**，即一个类对自己依赖的类知道的越少越好
    - 类与类关系越密切，耦合度越大
    - 即：对于被依赖的类不管多么复杂，都尽量将逻辑封装在类的内部，对外除了提供public方法，不对外暴露任何信息
    - 类与类之间只通过public方法来通信
- 迪米特法则的简单定义：**只与直接的朋友通信**
- 朋友类
    - 什么是朋友关系
        - 每个对象都会与其他对象有耦合关系，只要两个对象之间有耦合关系，就称这两个对象是朋友关系
        - 耦合的方式很多：依赖、关联、组合、聚合等等
    - 直接的朋友：出现在成员变量、方法参数、方法返回值中的类
    - 不是直接的朋友：出现在局部变量中的类
    - 陌生的类最好不要以局部变量的形式出现在类的内部
- 迪米特法则注意事项和细节
    - 迪米特法则的核心是是**降低类之间的耦合**
    - 由于每个类都减少了不必要的依赖，因此迪米特法则只是要求**降低**类之间的耦合关系，**并不是要求完全没有依赖关系**

- 违反迪米特法则的示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/demeter/origian/Demeter.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/demeter/origian/Demeter.java)
    - 基本功能
        - 通过`CollegeManager`创建`CollegeEmployee`
        - 通过`SchooleManager`创建`Employee`
        - `SchooleManager`可以打印所有`CollegeEmployee`和`Employee`的信息
    - `SchooleManager`的分析
        - 直接朋友类：Employee，CollegeManager
        - 非直接朋友：CollegeEmployee，
            - CollegeEmployee以局部变量的形式出现在printAllEmployee方法中，违背了迪米特法则
    - 代码
        ```java
        //学校员工类
        class Employee{
            private String id;
        
            public String getId() {return id;}
        
            public void setId(String id) {this.id = id;}
        }
        
        //学院员工
        class CollegeEmployee{
            private String id;
        
            public String getId() {return id;}
        
            public void setId(String id) {this.id = id;}
        }
        
        // 学院员工管理类
        class CollegeManager{
            //返回所有员工
            public List<CollegeEmployee> getAllEmployee(){
                List<CollegeEmployee> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {//添加10个员工
                    CollegeEmployee emp = new CollegeEmployee();
                    emp.setId("collegeID="+i);
                    list.add(emp);
                }
                return list;
            }
        }
        
        // 学校员工管理类
        class SchooleManager{
            // 返回所有总部员工
            public List<Employee> getAllEmployee(){
                List<Employee> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Employee emp = new Employee();
                    emp.setId("schoolID="+i);
                    list.add(emp);
                }
                return list;
            }
        
            // 输出学校总部和学院员工学校的方法
            void printAllEmployee(CollegeManager cm){
                //输出学院员工
                List<CollegeEmployee> list1 = cm.getAllEmployee();
                System.out.println("----------CollegeEmployee-----------");
                for (CollegeEmployee e : list1) {
                    System.out.println(e.getId());
                }
        
                //输出学校员工
                List<Employee> list2 = this.getAllEmployee();
                for (Employee e : list2) {
                    System.out.println(e.getId());
                }
            }
        }
        ```
    - 示例UML图
        - `SchooleManager`与`CollegeEmployee`产生了依赖，增加了耦合度
        - ![origain_uml](imgs/principle/Demeter/origain_uml.png)
        
- 示例的改进
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/demeter/improve/Demeter.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/demeter/improve/Demeter.java)
    - 改进方法
        - 将`CollegeEmployee`的打印逻辑从`SchooleManager`抽出，移动到直接朋友类`CollegeManager`中
    - 代码
        ```java
        // 学院员工管理类
        class CollegeManager {
            //返回所有员工
            public List<CollegeEmployee> getAllEmployee() {
                List<CollegeEmployee> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {//添加10个员工
                    CollegeEmployee emp = new CollegeEmployee();
                    emp.setId("collegeID=" + i);
                    list.add(emp);
                }
                return list;
            }
        
            //输出学院员工
            public void printAllEmployee() {
                List<CollegeEmployee> list1 = this.getAllEmployee();
                System.out.println("----------CollegeEmployee-----------");
                for (CollegeEmployee e : list1) {
                    System.out.println(e.getId());
                }
            }
        
        }
        
        // 学校员工管理类
        // 直接朋友类：Employee，CollegeManager
        // 非直接朋友：CollegeEmployee，
        // CollegeEmployee以局部变量的形式出现在printAllEmployee方法中，违背了迪米特法则
        class SchooleManager{
            // 返回所有总部员工
            public List<Employee> getAllEmployee(){
                List<Employee> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Employee emp = new Employee();
                    emp.setId("schoolID="+i);
                    list.add(emp);
                }
                return list;
            }
        
            // 输出学校总部和学院员工学校的方法
            void printAllEmployee(CollegeManager cm){
                //输出学院员工
                // 使用迪米特法则将逻辑封装在被依赖类中
                cm.printAllEmployee();
        
                //输出学校员工
                List<Employee> list2 = this.getAllEmployee();
                for (Employee e : list2) {
                    System.out.println(e.getId());
                }
            }
        }
        ```
    - UML图
        - 去除了`SchooleManager`与`CollegeEmployee`之间的依赖
        - ![improve_uml](imgs/principle/Demeter/improve_uml.png)

## 合成复用原则
[top](#catalog)
- Composite Reuse Priciple
- 基本原则：尽量使用合成/聚合的方式，而不是使用继承
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/compositeReuse/CompositeReuse.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/principle/compositeReuse/CompositeReuse.java)
    - 功能类`A`
        ```java
        class A{
            public void method01(){}
            public void method02(){}
        }
        ```
    - 类B使用A的方法的几种方式
        1. 继承
            - 继承后B可以直接使用A的方法
            - 继承使得B和A的耦合性增强
            - 如果A增加了其他方法，B也会增加
            - 如果A发生了修改，可能会A影响到B
            ```java
            class B1 extends A{
            }
            ```
        2. 依赖
            ```java
            class B2{
                public void method01(A a){
                    a.method01();
                }
            }
            ```

        3. 聚合
            ```java
            class B3{
                private A a;
            
                public A getA() {
                    return a;
                }
            
                public void setA(A a) {
                    this.a = a;
                }
            
                public void method01(){
                    a.method01();
                }
            }
            ```

        4. 组合
            - 创建B时，A也被创建好了
            ```java
            class B4{
                private A a = new A();
            
                public void method01(){
                    a.method01();
                }
            }
            ```
    - 四种合成方式的UML图
        - ![AB_uml](imgs/principle/Composite_Reuse/AB_uml.png)


# UML类图
[top](#catalog)
## UML图简介
- UML，Unified modeling language，统一建模语言
- UML是一种用于软件系统分析和设计的语言工具。UML本身是一套符号的规定，这些符号用于描述软件模型中的各个元素和他们之间的关系 
- UML图分类
    - 用例图(use case)
    - 静态结构图: 类图、对象图、包图、组件图、部署图
    - 动态行为图: 交互图(时序图与协作图)、状态图、活动图

## UML类图
[top](#catalog)
- 类图是UML图中最核心的图
- 类图用于描述系统中的类(对象)本身的组成和类(对象)之间的各种静态关系
- UML实体
    - 类 Class
    - 实体 Interface
- 实体间6种关系
    - 依赖
    - 关联
    - 继承/泛化（类）
    - 实现(接口) 
    - 聚合
    - 组合

- 实体间关系的表示方法
    - ![entity_relation](imgs/uml/entity_relation.png)
    
## UML类图的6种实体关系
[top](#catalog)
1. 依赖关系：Dependence
    - 表示方式：**箭头+虚线**
    - **只要在类图中用到了对方，那么他们之间就存在依赖关系**，如果没有对方连就无法编译
    - 产生依赖关系的前提：类中用到了对方
    - 会产生依赖关系的情况
        - 类的成员属性
        - 方法的返回值类型
        - 方法的参数
        - 方法中的局部变量

2. 继承/泛化关系：Generalization
    - 表示方式：**三角+实线**
    - 继承/泛化关系是依赖关系的特例
    
3. 实现关系：Implementation
    - 表示方式：**三角+虚线**
    - 实现关系是依赖关系的特例
    - 实际上就是A类实现B类
    
4. 关联关系：Association
    - 表示方式：**箭头+实线**
    - 关联关系实际上是类与类之间的关系，它是依赖关系的特例
        - 这种关系经常表现为<label style="color:red">类的成员属性</label>
    - 关联关系具有**导航性**，即双向关系或单向关系
    - 关联关系的多重性：1:1、1:n、n:m的关系
    - 单向1:1关系
        - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/association/oneway/OneWay.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/association/oneway/OneWay.java)
            ```java
            //一个人对应一个身份证ID
            class Person{
                private IDCard card; 
            }
            
            class IDCard{}
            ```
        - ![association_oneway](imgs/uml/association_oneway.png)
        
    - 双向1:1关系
        - 参考：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/association/twoway/TwoWay.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/association/twoway/TwoWay.java)
            ```java
            class Person{
                private IDCard card; 
            }
            
            class IDCard{
                private Person person;
            }
            ```
        - ![association_twoway](imgs/uml/association_twoway.png)
    
5. 聚合关系：Aggregation
    - 表示方式：**空心菱形+实线**
        - 菱形指向整体
    - 聚合关系表示的是整体和部分的关系，整体与部分可以分开
    - **聚合关系是关联关系的特例**，所以他具有关联关系的导航性与多重性
    - 示例
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/aggregation/Aggregation.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/aggregation/Aggregation.java)
            ```java
            class Computer{
                private Mouse mouse;
                private Monitor monitor1;
            }
            
            class Mouse{}
            class Monitor{}
            ```
        - `Computer`与`Mouse`、`Monitor`具有1:1的聚合关系
        - UML图
            - ![aggregation_uml](imgs/uml/aggregation_uml.png)
6. 组合关系：Composition
    - 表示方式：**实心菱形+实线**
        - 菱形指向整体
    - 组合关系也是整体和部分的关系，但是**整体和部分不可分开**
        - 不可分开即同时创建，同时销毁，无法相互分离
        - 如：
    - 示例
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/composition/Composition.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/UML/composition/Composition.java)
            ```java
            class Laptop {
                private Mouse mouse;
                private Monitor monitor = new Monitor();
            }
            
            class Monitor {}
            class Mouse{}
            ```
        - `Computer`与`Monitor`同时创建、同时销毁，两者不可分离，是组合关系
        - `Computer`与`Mouse`可以分离，是聚合关系
        - UML图
            - ![composition_uml](imgs/uml/composition_uml.png)
        

# 设计模式的分类
[top](#catalog)
- 设计模式分为三种类型，共23种
    1. 创建型模式：**如何创建对象**
    2. 结构型模式：**提高软件整体的扩展性**
    3. 行为型模式：**如何设计类的方法，使得方法的调用和开发更加合理**

- 创建型模式
    1. <labe style="color:red">单例模式</label>
    2. <labe style="color:red">工厂模式</label>
    3. 抽象工厂模式
    4. 原型模式
    5. 建造者模式
    
- 结构型模式
    1. <labe style="color:red">代理模式</label>
    2. <labe style="color:red">装饰器模式</label>
    3. 适配器模式
    4. 桥接模式
    5. 组合模式
    6. 外观模式
    7. 享元模式
    
- 行为型模式
    1. <labe style="color:red">观察者模式</label>
    2. 模版方法模式
    3. 命令模式
    4. 访问者模式
    5. 迭代器模式
    6. 中介者模式
    7. 备忘录模式
    8. 解释器模式/Interpreter模式
    9. 状态模式
    10. 策略模式
    11. 职责链模式/责任链模式

# 创建型-单例设计模式
## 单例设计模式简介
[top](#catalog)
- 什么是单例模式
    - 采取一定的方法保证在整个软件系统中，类只存在一个实例对象，并且该类只提供一个取得实例的方法(一般是静态方法)

- 单例模式保证了系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能
- 实例化一个单例类时，必须使用对应的方法来获取，不能使用`new`来创建
- 单例模式的使用场景：
    - 需要频繁的进行创建和销毁的对象
    - 创建对象时耗时过多，即重量级对象
    - 经常用到的对象
    - 工具类对象
    - 频繁访问数据库或文件的对象，如数据源、session工厂等

- **单例模式的8中形式**
    1. 饿汉式（静态常量）
    2. 饿汉时（静态代码块）
    3. 懒汉式（线程不安全）
    4. 懒汉式（线程安全，同步方法）
    5. 懒汉式（线程不安全，同步代码块）
    6. 双重检查
    7. 静态内部类
    8. 枚举
    
## 单例模式形式1-饿汉式-静态常量
[top](#catalog)
- 创建步骤
    1. 构造器私有化，防止调用方通过`new`来创建实例对象
    2. 在类的内部实例化一个**私有的、静态的(不可变的)**对象`instance`
        - 私有：防止外部的直接访问
        - 静态：使得该对象可以通过静态方法访问
        - 不可变：防止外部对该对象的直接替换
    3. 向外暴露一个静态的公共方法，一般写作：`getInstance()`

- 饿汉式的优缺点<span id="singleton_hungry_advantages_and_disadvantages"></span>
    - 优点：写法简单，在类装载时就完成了实例化，避免了线程同步的问题
    - 缺点：
        - 在类装载时就完成了实例化，没有达到`Lazy Loading`的效果
        - 如果一直没有使用过这个实例，则会造成内存的浪费
    - 这种方式基于`classloader`机制，避免了多线程的同步问题。不过，instance在类装载时就实例化，在单例模式中大多数都是调用`getInstance`方法，但是导致类装载的原因有很多种，因此不能确定有其他的方式或者其他的静态方法导致类装载，这时候初始化instance就没有达到`Lazy Loading`的效果

- 结论：饿汉式-静态常量<label style="color:red">可以用，可能会造成内存浪费</label>
    - 如果确定类装载方式，则没有问题
    - 如果无法确定类装载方式，则可能会造成内存浪费

- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type1/SingletonType01Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type1/SingletonType01Test.java)
    - 单例模式类
        ```java
        // 饿汉式-静态变量
        class Singleton{
            // 创建步骤
        
            // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
            private Singleton() {
        
            }
        
            // 2. 在类的内部实例化一个：私有的、静态的、不可变的 对象
            private final static Singleton instance = new Singleton();
        
            // 3. 向外暴露一个静态的公共方法，一般写作：`getInstance`
            public static Singleton getInstance(){
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        // output:true
        System.out.println(instance1 == instance2);

        // 两个实例的hashCode是相同的
        System.out.println("instance1 hashCode = " + instance1.hashCode());
        System.out.println("instance2 hashCode = " + instance2.hashCode());
        ```

## 单例模式形式2-饿汉式-静态代码块
[top](#catalog)
- 创建步骤
    1. 构造器私有化，防止调用方通过`new`来创建实例对象
    2. 添加一个**私有的、静态的(不可变的)**成员属性`instance`
        - 私有：防止外部的直接访问
        - 静态：使得该对象可以通过静态方法访问
        - 不可变：防止外部对该对象的直接替换
    3. 在静态代码块中为`instance`提供实例化对象
    4. 向外暴露一个静态的公共方法，一般写作：`getInstance()`
    
- 优缺点与[饿汉式-静态常量](#singleton_hungry_advantages_and_disadvantages)相同
- 结论：这种单例模式<label style="color:red">可用，但是可能会造成内存浪费</label>
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type2/SingletonType02Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type2/SingletonType02Test.java)
    - 单例模式类
        ```java
        class Singleton{
            // 创建步骤
        
            // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
            private Singleton() {
        
            }
        
            // 2. 添加一个私有的、静态的、不可变的成员属性instance
            private final static Singleton instance;
        
            // 3. 在静态代码块中为instance提供实例化对象
            static{
                instance = new Singleton();
            }
        
            // 4. 向外暴露一个静态的公共方法，一般写作：`getInstance`
            public static Singleton getInstance(){
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        // output:true
        System.out.println(instance1 == instance2);

        // 两个实例的hashCode是相同的
        System.out.println("instance1 hashCode = " + instance1.hashCode());
        System.out.println("instance2 hashCode = " + instance2.hashCode());
        ```

## 单例模式形式3-懒汉式-线程不安全
[top](#catalog)
- 创建步骤
    1. 构造器私有化，防止调用方通过`new`来创建实例对象
    2. 添加一个**私有的、静态的**成员属性`instance`
        - 私有：防止外部的直接访问
        - 静态：使得该对象可以通过静态方法访问
    3. 向外暴露一个静态的公共方法，一般写作：`getInstance()`
    4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
        - 如果`instance`尚未创建，则创建实例化对象并返回
        - 如果`instance`已创建，则直接返回
        
- 懒汉式的优缺点
    - 优点：懒汉式起到了`Lazy Loading`的效，但是**只能在单线程下使用**
    - 缺点：可能同时产生多个实例，违背单例模式的基本原则
        - 在多线程下，如果一个线程进入了`if(singleton==null)`判断语句，还未开始创建对象时，另一个线程也通过了这个判断语句，这时便**会产生多个实例**。所以多线程环境下不可以使用这种方式

- 结论：<label style="color:red">在实际开发中，不可以使用这种方式</label>

- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type3/SingletonType03Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type3/SingletonType03Test.java)
    - 单例模式代码
        ```java
        class Singleton{
            // - 创建步骤
            // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
            private Singleton() {
            }
            // 2. 添加一个**私有的、静态的**成员属性`instance`
            private static Singleton instance;
        
            // 3. 向外暴露一个静态的公共方法，一般写作：`getInstance()`
            public static Singleton getInstance(){
                // 4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
                if (instance == null){
                    instance = new Singleton();
                }
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        public static void main(String[] args) {
            Callable<Singleton> r = () -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(
                        Thread.currentThread().getName() +
                                " : instance hashCode = " +
                                instance
                );
                return instance;
            };
    
            FutureTask<Singleton> f1 = new FutureTask<>(r);
            FutureTask<Singleton> f2 = new FutureTask<>(r);
            FutureTask<Singleton> f3 = new FutureTask<>(r);
            FutureTask<Singleton> f4 = new FutureTask<>(r);
    
            // 创建线程对象
            Thread t1 = new Thread(f1);
            Thread t2 = new Thread(f2);
            Thread t3 = new Thread(f3);
            Thread t4 = new Thread(f4);
    
            // 启动线程
            t1.start();
            t2.start();
            t3.start();
            t4.start();
    
            // 获取线程的返回单例对象并比较
            try {
                Singleton singleton1 = f1.get();
                Singleton singleton2 = f2.get();
                Singleton singleton3 = f3.get();
                Singleton singleton4 = f4.get();
                System.out.println(singleton1 == singleton2);
                System.out.println(singleton1 == singleton3);
                System.out.println(singleton1 == singleton4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ```
    - 测试结果
        - 可能需要多次测试才能测出单例对象被多次创建了
        - ![type03_test_result](/designPattern/base/imgs/pattern/singleton/type03_test_result.png)
      
## 单例模式形式4-懒汉式-线程安全-同步方法
[top](#catalog)
- 创建步骤
    1. 构造器私有化，防止调用方通过`new`来创建实例对象
    2. 添加一个**私有的、静态的**成员属性`instance`
        - 私有：防止外部的直接访问
        - 静态：使得该对象可以通过静态方法访问
    3. 向外暴露一个**线程同步的**静态的公共方法，一般写作：`getInstance()`，
    4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
        - 如果`instance`尚未创建，则创建实例化对象并返回
        - 如果`instance`已创建，则直接返回
        
- 使用同步方法的懒汉式的优缺点
    - 优点
        - 起到了`Lazy Loading`的效
        - 可以在多线程下使用，<label style="color:red">解决了线程不安全的问题</label>
    - 缺点
        - 同步方法的效率低，每个线程在获取`instance`时，都需要同步。其实这个同步方法只需要同步一次即可，后面想获取`instance`时，直接`return`即可
        
- 结论：在实际开发中，<label style="color:red">不推荐使用这种方式</label>

- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type4/SingletonType04Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type4/SingletonType04Test.java)
    - 单例代码
        ```java
        class Singleton{
            // - 创建步骤
            // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
            private Singleton() {
            }
            // 2. 添加一个**私有的、静态的**成员属性`instance`
            private static Singleton instance;
        
            // 3. 向外暴露一个**线程同步的**静态的公共方法，一般写作：`getInstance()`，
            public static synchronized Singleton getInstance(){
                // 4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
                if (instance == null){
                    instance = new Singleton();
                }
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        public static void main(String[] args) {
            Callable<Singleton> r = () -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(
                        Thread.currentThread().getName() +
                                " : instance hashCode = " +
                                instance
                );
                return instance;
            };
    
            FutureTask<Singleton> f1 = new FutureTask<>(r);
            FutureTask<Singleton> f2 = new FutureTask<>(r);
            FutureTask<Singleton> f3 = new FutureTask<>(r);
            FutureTask<Singleton> f4 = new FutureTask<>(r);
    
            // 创建线程对象
            Thread t1 = new Thread(f1);
            Thread t2 = new Thread(f2);
            Thread t3 = new Thread(f3);
            Thread t4 = new Thread(f4);
    
            // 启动线程
            t1.start();
            t2.start();
            t3.start();
            t4.start();
    
            // 获取线程的返回单例对象并比较
            try {
                Singleton singleton1 = f1.get();
                Singleton singleton2 = f2.get();
                Singleton singleton3 = f3.get();
                Singleton singleton4 = f4.get();
                System.out.println(singleton1 == singleton2);
                System.out.println(singleton1 == singleton3);
                System.out.println(singleton1 == singleton4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ```
    - 测试结果
        - ![type04_test_result](/designPattern/base/imgs/pattern/singleton/type04_test_result.png)
        
## 单例模式形式5-懒汉式-线程不安全-同步代码块  
[top](#catalog)
- 将[单例模式形式4-懒汉式-线程安全-同步方法](#单例模式形式4-懒汉式-线程安全-同步方法)中的同步方法变成了同步代码快
    ```java
    public static Singleton getInstance(){
        if(instance == null){
            // 将同步方法改为同步代码块
            synchronized (Singleton.class){
                instance = new Singleton();
            }
        }

        return instance;
    }
    ```
- <label style="color:red">这种方式无法做到线程同步，无法解决线程安全的问题</label>
- 同步代码块的问题分析
    1. 当两个线程同时进入`if(instance == null){`
    2. 一个线程拿到同步监视器，创建了一个单例对象A，然后释放同步监视器
    3. 另一个线程获取到同步监视器，又创建了一个单例对象B
    4. 最终导致单例对象被多次创建
- 结论：<label style="color:red">在实际开发中不能使用这种方式</label>
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type4/SingletonType04Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type4/SingletonType04Test.java)
    - 代码内容
        ```java
        class Singleton{
            private static Singleton instance;
        
            private Singleton() {
            }
        
            public static Singleton getInstance(){
                if(instance == null){
                    synchronized (Singleton.class){
                        instance = new Singleton();
                    }
                }
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        public static void main(String[] args) {
            Callable<Singleton> r = ()-> {
                Singleton instance = Singleton.getInstance();
                System.out.println(
                        Thread.currentThread().getName() +
                                " : instance hashCode = " +
                                instance
                );
                return instance;
            };
    
            FutureTask<Singleton> f1 = new FutureTask<>(r);
            FutureTask<Singleton> f2 = new FutureTask<>(r);
            FutureTask<Singleton> f3 = new FutureTask<>(r);
            FutureTask<Singleton> f4 = new FutureTask<>(r);
    
            // 创建线程对象
            Thread t1 = new Thread(f1);
            Thread t2 = new Thread(f2);
            Thread t3 = new Thread(f3);
            Thread t4 = new Thread(f4);
    
            // 启动线程
            t1.start();
            t2.start();
            t3.start();
            t4.start();
    
            // 获取线程的返回单例对象并比较
            try {
                Singleton singleton1 = f1.get();
                Singleton singleton2 = f2.get();
                Singleton singleton3 = f3.get();
                Singleton singleton4 = f4.get();
                System.out.println(singleton1 == singleton2);
                System.out.println(singleton1 == singleton3);
                System.out.println(singleton1 == singleton4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ```
    - 测试结果
        - ![type05_test_result](/designPattern/base/imgs/pattern/singleton/type05_test_result.png)
        
## 单例模式形式6-懒汉式-双重检查  
[top](#catalog)
- 多重检查的方式
    ```java
    if (instance == null){
        synchronized(Singleton.class){
            if (instance == null){
                instance = new Singleton();
            }
        }
    }
    ```

- 多重检查是多线程开发中经常使用到的，通过两个检查`if (instance == null){`就可以保证线程安全
- 实例化代码只执行一次，后面再执行到`if (instance == null){`(无论是第一层还是第二层)，都直接`return`实例化对象，避免了重复进行方法同步
- 这种方法是线程安全的，能做到延迟加载，效率比较高
- 结论：<label style="color:red">在实际开发中，推荐使用这种单例设计模式</label>
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type6/SingletonType06Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type6/SingletonType06Test.java)
    - 代码内容
        ```java
        class Singleton{
            private static volatile Singleton instance;
        
            private Singleton() {
            }
        
            public static Singleton getInstance(){
                if (instance == null){
                    synchronized(Singleton.class){
                        if (instance == null){
                            instance = new Singleton();
                        }
                    }
                }
        
                return instance;
            }
        }
        ```
    - 测试内容
        ```java
        public static void main(String[] args) {
            Callable<Singleton> r = ()-> {
                Singleton instance = Singleton.getInstance();
                System.out.println(
                        Thread.currentThread().getName() +
                                " : instance hashCode = " +
                                instance
                );
                return instance;
            };
    
            FutureTask<Singleton> f1 = new FutureTask<>(r);
            FutureTask<Singleton> f2 = new FutureTask<>(r);
            FutureTask<Singleton> f3 = new FutureTask<>(r);
            FutureTask<Singleton> f4 = new FutureTask<>(r);
    
            // 创建线程对象
            Thread t1 = new Thread(f1);
            Thread t2 = new Thread(f2);
            Thread t3 = new Thread(f3);
            Thread t4 = new Thread(f4);
    
            // 启动线程
            t1.start();
            t2.start();
            t3.start();
            t4.start();
    
            // 获取线程的返回单例对象并比较
            try {
                Singleton singleton1 = f1.get();
                Singleton singleton2 = f2.get();
                Singleton singleton3 = f3.get();
                Singleton singleton4 = f4.get();
                System.out.println(singleton1 == singleton2);
                System.out.println(singleton1 == singleton3);
                System.out.println(singleton1 == singleton4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        ```

## 单例模式形式7-静态内部类中的静态属性
[top](#catalog)
- 静态内部类的特点
    - 外部类被加载时，静态内部类不会被加载
    - 当调用该类的属性或方法时，静态内部类才会被加载
    - 类的装载只会发生一次，并且是**线程安全的**
    
- 使用静态内部类中的静态属性的优点
    - 延迟加载，效率高：
        - 加载外部类时，不会加载内部类
        - 保证单例对象只在需要时进行实例化，即只在调用`getInstance()`时才会装载静态内部类，并完成对象的实例化
    - 线程安全：
        - 这种方式采用了类装载机制来保证初始化实例时只有一个线程
        - 类的静态属性只会在第一次加载时初始化，所以间接的通过JVM来实现了线程的安全性。**在类初始化时，其他线程时无法进入的**
- 结论：<labe style="color:red">推荐使用</label>
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type7/SingletonType07Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type7/SingletonType07Test.java)
    - 代码内容
        ```java
        class Singleton{
            private Singleton() {
            }
            
            // 创建静态内部类及其静态属性
            private static class SingletonInstance{
                public static final Singleton INSTANCE = new Singleton();
            }
        
            // 直接返回静态内部类中的静态属性
            public static Singleton getInstance(){
                return SingletonInstance.INSTANCE;
            }
        }
        ```

## 单例模式形式7-枚举
[top](#catalog)
- 通过使用枚举，**不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象**
- 这种方式也是Effective Java作者所提倡的方式
- 结论：<label style="color:red">推荐使用</label>
- 示例
    - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type8/SingletonType08Test.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/singleton/type8/SingletonType08Test.java)
    - 代码内容
        ```java
        enum Singleton{
            INSTANCE;
        
            public void work(){
                System.out.println("singleton instance is working");
            }
        }
        ```
    - 测试内容
        ```java
        public static void main(String[] args) {
            Singleton instance = Singleton.INSTANCE;
            Singleton instance2 = Singleton.INSTANCE;
    
            System.out.println(instance == instance2);
    
            instance.work();
        }
        ```

## 单例模式在JDK中的应用
[top](#catalog)
- JDK中 `java.lang.Runtime`就是经典的单例模式
- 源码分析
    ```java
    public class Runtime {
        private static final Runtime currentRuntime = new Runtime();
    
        private static Version version;
    
        public static Runtime getRuntime() {
            return currentRuntime;
        }
    
        private Runtime() {}
        
        ...
    }
    ```
- `java.lang.Runtime`采用的是饿汉式-静态常量的方式

# 创建型-工厂模式
## 问题引入-pizza订购
[top](#catalog)
- 需求
    - 披萨店项目：要便于披萨种类的扩展，要便于维护
    - 基本流程：披萨店去订购，然后制作披萨
    - 披萨有很多种类，但基本制作流程相同：prepare、bake、cut、box
    
- 传统设计
    - UML图：
        - ![problem_uml](/designPattern/base/imgs/pattern/factory/import_problem/problem_uml.png)
    - 披萨类及其子类
        - 参考代码
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/Pizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/Pizza.java)
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/SubPizza01.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/SubPizza01.java)
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/SubPizza02.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/pizza/SubPizza02.java)
        - 代码内容
            ```java
            public abstract class Pizza {
                private String name;
            
                public Pizza(String name) {
                    this.name = name;
                }
            
                public abstract void prepare();
            
                public void bake(){
                    System.out.println("is baking");
                }
            
                public void cut(){
                    System.out.println("is cutting");
                }
            
                public void box(){
                    System.out.println("is boxing");
                }
            }
          
            public class SubPizza01 extends Pizza {
            
                public SubPizza01() {
                    super("SubPizza01");
                }
            
                @Override
                public void prepare() {
                    System.out.println("SubPizza01 is preparing");
                }
            }
          
            public class SubPizza02 extends Pizza{
                public SubPizza02() {
                    super("SubPizza02");
                }
            
                @Override
                public void prepare() {
                    System.out.println("SubPizza02 is preparing");
                }
            }
            ```
    - 订单类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/order/OrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/order/OrderPizza.java)
        - 代码内容：
            ```java
            public class OrderPizza {
                public void order(){
                    Pizza p = null;
                    String orderType;
            
                    do{
                        orderType = getType();
                        //创建pizza对象
                        if (orderType.equals("sub01")){
                            p = new SubPizza01();
                        }else if (orderType.equals("sub02")){
                            p = new SubPizza02();
                        }else{
                            break;
                        }
                        //制作pizza
                        p.prepare();
                        p.bake();
                        p.cut();
                        p.box();
                    }while(true);
                }
            
                // 从控制台获取pizza种类
                private String getType(){
                    try {
                        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("input pizza type : ");
                        String type= is.readLine();
                        return type;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            ```
    - 披萨店类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/store/PizzaStore.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/pizzastore/store/PizzaStore.java)
        - 代码内容：
            ```java
            public class PizzaStore {
                public static void main(String[] args) {
                    //作为客户端发出pizza的定做任务
                    OrderPizza order = new OrderPizza();
                    order.order();
                }
            }
            ```
          
- 传统设计的优缺点
    - 优点：理解容易，操作简单
    - 缺点
        - 违反了设计模式的ocp原则，没有对修改关闭。当新增一个Pizza子类时，需要在订单类`OrderPizza`中的构造器中添加处理逻辑
        - 如果有多个`OrderPizza`类，当添加Pizza子类时，会造成大量的修改
                
## 简单工厂模式
[top](#catalog)
- 简单工厂模式
    - 基本思路：定义一个创建对象的工厂类，封装创建对象的逻辑
    - 简单工厂模式是最简单实用的工厂模式
- 在软件开发中，当我们会用到大量的创建某些对象时，就会使用工厂模式

- 披萨店项目的改进方法
    - 问题分析：`OrderPizza`类负责创建pizza，使得增加pizza时，`OrderPizza`会产生大量的修改
    - 解决方法：将创建Pizza的逻辑封装到一个工厂类中，当新增pizza，只需要修改该工厂类即可，其他类不需要修改

- 使用简单工厂模式来修改萨店项目
    - 修改方法：将创建`OrderPizza`中的创建pizza的逻辑封装到工厂类`PizzaFactory`中，并组合`PizzaFactory`
    - 改进后的UML
        - ![problem_uml](imgs/pattern/factory/simplefactory/problem_uml.png)
    - 工厂类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/factory/PizzaFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/factory/PizzaFactory.java)
        - 代码内容
            ```java
            public class PizzaFactory {
                public Pizza create(String orderType){
                    Pizza p = null;
            
                    if (orderType.equals("sub01")){
                        p = new SubPizza01();
                    }else if (orderType.equals("sub02")){
                        p = new SubPizza02();
                    }
                    return p;
                }
            }
            ```
    - 订单类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/order/OrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/order/OrderPizza.java)
            ```java
            public class OrderPizza {
                private PizzaFactory pf;
                public OrderPizza(PizzaFactory pf) {
                    this.pf = pf;
                }
            
                public void order(){
                    Pizza p = null;
                    String orderType;
            
                    do{
                        orderType = getType();
                        // 使用工厂创建pizza
                        p = pf.create(orderType);
                        // 制作pizza
                        if (p != null){
                            p.prepare();
                            p.bake();
                            p.cut();
                            p.box();
                        }else {
                            break;
                        }
                    }while(true);
                }
            
                // 从控制台获取pizza种类
                private String getType(){
                    try {
                        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("input pizza type : ");
                        String type= is.readLine();
                        return type;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            ```
    - 披萨店类
        - 参考代码:[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/store/PizzaStore.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/simplefactory/store/PizzaStore.java)
        - 代码内容
            ```java
            public class PizzaStore {
                public static void main(String[] args) {
                    //作为客户端发出pizza的定做任务
                    OrderPizza order = new OrderPizza(new PizzaFactory());
                    order.order();
                }
            }
            ```

## 静态工厂模式
[top](#catalog)
- 基本思路与：[简单工厂模式](#简单工厂模式)基本相同，只是提供逻辑的方法是**静态方法**
- 在使用工厂类的类中，不需要再聚合或组合工厂类对象，直接通过调用工厂类的静态方法来创建对象
- 使用静态工厂模式来修改萨店项目
    - 工厂类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/staticfactory/factory/PizzaFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/staticfactory/factory/PizzaFactory.java)
        - 代码内容
            ```java
            public class PizzaFactory {
                // 通过静态方法对外提供创建方式
                public static Pizza create(String orderType){
                    Pizza p = null;
            
                    if (orderType.equals("sub01")){
                        p = new SubPizza01();
                    }else if (orderType.equals("sub02")){
                        p = new SubPizza02();
                    }
                    return p;
                }
            }
            ```
    - 订单类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/staticfactory/order/OrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/staticfactory/order/OrderPizza.java)
        - 代码内容
            ```java
            public class OrderPizza {
            
                public void order(){
                    Pizza p = null;
                    String orderType;
            
                    do{
                        orderType = getType();
                        // 使用静态工厂创建pizza
                        p = PizzaFactory.create(orderType);
                        // 制作pizza
                        if (p != null){
                            p.prepare();
                            p.bake();
                            p.cut();
                            p.box();
                        }else {
                            break;
                        }
                    }while(true);
                }
            
                // 从控制台获取pizza种类
                private String getType(){
                    try {
                        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("input pizza type : ");
                        String type= is.readLine();
                        return type;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            ```

## 工厂方法模式
[top](#catalog)
- 工厂方法模式：
    - 定义一个创建对象的抽象方法，由子类决定要实例化的类。
    - 工厂方法模式将**对象的实例化推迟到子类中**

- pizza项目中应用工厂方法模式
    - 新需求
        - 原始的需求：客户可以点不同类型的披萨
        - 新需求：客户在点披萨时，可以点**不同地区**的各种披萨

    - 改进方式
        - 方法1：使用简单工厂模式
            - 对应不同的地区，创建不同的简单工厂类，如`ChinaPizzaFactory`、`AmericanPizzaFactory`
            - 但是这种方式**会产生大量的简单工厂类，不利于扩展与维护**
        - 方法2：使用工厂方法模式
            - 不直接使用工厂类，将披萨的实例化功能抽象成抽象方法，在不同口味的订单子类中具体实现
            
- 修改示例
    - 修改方法
        1. 去除工厂类
        2. 在`OrderPizza`中添加抽象方法来创建pizza，创建方法有该类的子类提供实现
        3. 添加`OrderPizza`的子类，并提供方法实现
    - UML图：
        - ![problem_uml](imgs/pattern/factory/factoryMethod/problem_uml.png)
    - 抽象类`OrderPizza`
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/factoryMethod/order/OrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/factoryMethod/order/OrderPizza.java)
        - 代码内容
            ```java
            public abstract class OrderPizza {
                // 工厂方法模式，将实现交给子类
                public abstract Pizza createPizza(String orderType);
            
                public void order(){
                    Pizza p = null;
                    String orderType;
            
                    do{
                        orderType = getType();
                        // 使用静态工厂创建pizza
                        p = createPizza(orderType);
                        // 制作pizza
                        if (p != null){
                            p.prepare();
                            p.bake();
                            p.cut();
                            p.box();
                        }else {
                            break;
                        }
                    }while(true);
                }
            
                // 从控制台获取pizza种类
                private String getType(){
                    try {
                        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("input pizza type : ");
                        String type= is.readLine();
                        return type;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            ```
    - `OrderPizza`的子类实现
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/factoryMethod/order/ChinaOrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/factoryMethod/order/ChinaOrderPizza.java)
        - 代码内容
            ```java
            public class ChinaOrderPizza extends OrderPizza {
                @Override
                public Pizza createPizza(String orderType) {
                    Pizza p = null;
            
                    if (orderType.equals("sub01")){
                        p = new ChinaSubPizza01();
                    }else if (orderType.equals("sub02")){
                        p = new ChinaSubPizza02();
                    }
                    return p;
                }
            }
            ```
        
## 抽象工厂模式
[top](#catalog)
- 什么是抽象工厂模式
    - **定义一个interface用于创建相关或有依赖的对象簇，而无需指明具体的类**
    - 抽象工厂模式可以将简单工厂模式和工厂方法模式进行整合
    - 从设计层面看，抽象工厂模式就是对简单工厂模式和工厂方法模式的进一步抽象
    - 使用时，根据创建对象的类型使用对应的工厂子类，从而将单个简单工厂类变成了工厂簇，更利于维护和扩展
    
- pizza项目中应用工厂方法模式
    - 新需求
        - 原始的需求：客户可以点不同类型的披萨
        - 新需求：客户在点披萨时，可以点**不同地区**的各种披萨
    - 改进方式
        1. 提供一个创建pizza的抽象工厂`AbsFactory`
        2. 提供抽象工厂的实例，分别对应不同地区的pizza：`ChinaFactory`、`AmericanFactory`
        3. 在订单类`OrderPizza`中通过注入不同的工厂实例，来创建不同地区的披萨

- 修改示例
    - UML图
        - ![problem_uml](imgs/pattern/factory/abstractFactory/problem_uml.png)
    - 修改之后，当添加新的`OrderPizza`类时，只需要注入抽象工厂`AbsFactory`的实现类即可
    - 抽象工厂及其实现类
        - 参考代码：
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/AbsFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/AbsFactory.java)
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/AmericanFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/AmericanFactory.java)
            - [/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/ChinaFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/factory/ChinaFactory.java)
        - 代码内容
            ```java
            // 抽象工厂
            public interface AbsFactory {
                Pizza createPizza(String orderType);
            }
          
            // 抽象工厂的实现类
            public class ChinaFactory implements AbsFactory {
                @Override
                public Pizza createPizza(String orderType) {
                    System.out.println("use ChinaFactory");
                    Pizza p = null;
            
                    if (orderType.equals("sub01")){
                        p = new ChinaSubPizza01();
                    }else if (orderType.equals("sub02")){
                        p = new ChinaSubPizza02();
                    }
                    return p;
                }
            }
          
            // 抽象工厂的实现类
            public class AmericanFactory implements AbsFactory {
                @Override
                public Pizza createPizza(String orderType) {
                    System.out.println("use AmericanFactory");
                    Pizza p = null;
            
                    if (orderType.equals("sub01")){
                        p = new AmericanSubPizza01();
                    }else if (orderType.equals("sub02")){
                        p = new AmericanSubPizza02();
                    }
                    return p;
                }
            }
            ```
    - 订单类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/order/OrderPizza.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/order/OrderPizza.java)
        - 代码内容
            ```java
            public class OrderPizza {
                // 聚合一个抽象工厂子类
                private AbsFactory factory;
            
                public OrderPizza(AbsFactory factory) {
                    this.factory = factory;
                }
            
                public void order(){
                    Pizza p = null;
                    String orderType;
            
                    do{
                        orderType = getType();
                        // 使用抽象工厂的实现类创建pizza
                        p = factory.createPizza(orderType);
                        // 制作pizza
                        if (p != null){
                            p.prepare();
                            p.bake();
                            p.cut();
                            p.box();
                        }else {
                            break;
                        }
                    }while(true);
                }
            
                // 从控制台获取pizza种类
                private String getType(){
                    try {
                        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("input pizza type : ");
                        String type= is.readLine();
                        return type;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            ```
        - 披萨店类
            - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/store/PizzaStore.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/factory/abstractFactory/store/PizzaStore.java)
            - 代码内容
                ```java
                public class PizzaStore {
                    public static void main(String[] args) {
                        //创建抽象工厂并注入订单类中
                        AbsFactory f = new ChinaFactory();
                        OrderPizza orderPizza = new OrderPizza(f);
                        orderPizza.order();
                    }
                }
                ```     

## 工厂模式在JDK中的应用
[top](#catalog) 
- JDK中的`java.util.Calendar`类中，使用了简单工厂模式
- 使用`Calendar cal = Calendar.getInstance();`获取对象时的创建过程
    ```java
    // 1. 创建单例
    public static Calendar getInstance()
    {
        Locale aLocale = Locale.getDefault(Locale.Category.FORMAT);
        return createCalendar(defaultTimeZone(aLocale), aLocale);
    }
    
    // 2. 通过简单工厂创建实例，但是这里没有直接创建简单工厂类，而是直接通过私有方法封装所有创建逻辑
    private static Calendar createCalendar(TimeZone zone,
                                               Locale aLocale)
    {
        // 根据参数情况，分别封装实例化的逻辑
        CalendarProvider provider =
            LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                                 .getCalendarProvider();
        if (provider != null) {
            try {
                return provider.getInstance(zone, aLocale);
            } catch (IllegalArgumentException iae) {
                // fall back to the default instantiation
            }
        }
    
        Calendar cal = null;
    
        if (aLocale.hasExtensions()) {
            String caltype = aLocale.getUnicodeLocaleType("ca");
            if (caltype != null) {
                switch (caltype) {
                case "buddhist":
                cal = new BuddhistCalendar(zone, aLocale);
                    break;
                case "japanese":
                    cal = new JapaneseImperialCalendar(zone, aLocale);
                    break;
                case "gregory":
                    cal = new GregorianCalendar(zone, aLocale);
                    break;
                }
            }
        }
        if (cal == null) {
            // If no known calendar type is explicitly specified,
            // perform the traditional way to create a Calendar:
            // create a BuddhistCalendar for th_TH locale,
            // a JapaneseImperialCalendar for ja_JP_JP locale, or
            // a GregorianCalendar for any other locales.
            // NOTE: The language, country and variant strings are interned.
            if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
                cal = new BuddhistCalendar(zone, aLocale);
            } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
                       && aLocale.getCountry() == "JP") {
                cal = new JapaneseImperialCalendar(zone, aLocale);
            } else {
                cal = new GregorianCalendar(zone, aLocale);
            }
        }
        return cal;
    }
    ```

## 工厂模式总结
[top](#catalog)
- 工厂模式的意义
    - <label style="color:red">将实例化对象的代码提取出来</label>，放到一个类中统一管理和维护，将实例化对象的逻辑与主项目的逻辑解耦，从而提高项目的扩展性和维护性
- 三种工厂模式
    - 简单工厂（可以改造成静态工厂）
    - 工厂方法模式
    - 抽象工厂模式

- 需要遵循设计模式的**依赖倒转**原则
    - 创建对象实例时，将new对象的逻辑封装到工厂的方法中，并返回对象
    - 不要让类继承具体类，而是继承抽象类或者实现interface接口
    - 不要覆盖基类中已经实现的方法
 
# 创建型-原型模式
## 问题引入-克隆羊
[top](#catalog)
- 需求
    1. 创建一只羊
    2. 使用第一只羊来克隆其他羊

- 传统方式完成需求
    - UML图
        - ![problem_uml](imgs/pattern/prototype/import_problem/problem_uml.png)
    - Sheep类
        - 提供基本的属性
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/cloneSheep/Sheep.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/cloneSheep/Sheep.java)
        - 代码内容
            ```java
            public class Sheep {
                private String name;
                private int age;
                private String color;
            
                public Sheep(String name, int age, String color) {
                    this.name = name;
                    this.age = age;
                    this.color = color;
                }
            
                @Override
                public String toString() {
                    return "Sheep{" +
                            "name='" + name + '\'' +
                            ", age=" + age +
                            ", color='" + color + '\'' +
                            '}';
                }
            
                public String getName() {
                    return name;
                }
            
                public void setName(String name) {
                    this.name = name;
                }
            
                public int getAge() {
                    return age;
                }
            
                public void setAge(int age) {
                    this.age = age;
                }
            
                public String getColor() {
                    return color;
                }
            
                public void setColor(String color) {
                    this.color = color;
                }
            }
            ```
    - Client类
        ```java
        public class ClientTest {
            @Test
            public void test01(){
                // 创建1只羊
                Sheep sheep = new Sheep("aa", 1, "aaaa");
        
                // 克隆9只
                Sheep clone1 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone4 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone5 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone6 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone7 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone8 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
                Sheep clone9 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        
                System.out.println(sheep);
                System.out.println(clone1);
                System.out.println(clone2);
                System.out.println(clone3);
                System.out.println(clone4);
                System.out.println(clone5);
                System.out.println(clone6);
                System.out.println(clone7);
                System.out.println(clone8);
                System.out.println(clone9);
            }
        }
        ```

- 传统方式的优缺点
    - 优点：容易理解，操作简单
    - 缺点
        - 使用原始对象来克隆新对象时，总是需要获取原始对象的属性，如果对象比较复杂，效率会比较低
        - 每次克隆对象时都需要重新初始化，而不是动态的获取原始对象运行时的状态，当原始对象发生改变时，克隆对象无法作出相同的改变。整体不够灵活


## 原型模式的基本概念
[top](#catalog)
- 什么是原型模式？
    - 用原型实例指定创建对象的种类，并且通过拷贝这些原型来创建新的对象
    - 原型模式允许一个对象再创建另一个可定制的对象，并且无需知道创建的细节
- 原型模式的工作原理
    - 将一个原型对象传给要执行创建的对象，并通过请求原型对象拷贝自身来完成创建过程，即：`原型对象.clone()`
- 原型模式的UML
    - [prototype_uml](imgs/pattern/prototype/base/prototype_uml.png)
- java中原型模式的使用方法
    - 实现`Cloneable`接口，该接口表示该类能够复制且具有复制的能力
    - 可以重写`clone()`方法
        - Object类是所有类的根类，`Object`类提供了一个`clone()`方法，该方法可以将一个Java对象复制一份
    - 使用`clone()`方法克隆对象
    
- 使用原型模式改造克隆羊项目
    - 改造方法
        - `Sheep`实现`Cloneable`接口，并重写`clone()`方法
        - 在`ClientTest`中通过`clone()`方法来克隆对象
    - Sheep类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/base/Sheep.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/base/Sheep.java)
        - 代码内容
            ```java
            public class Sheep implements Cloneable {
                private String name;
                private int age;
                private String color;
            
                public Sheep(String name, int age, String color) {
                    this.name = name;
                    this.age = age;
                    this.color = color;
                }
            
                // 克隆该实例，使用默认的clone方法来完成
                @Override
                protected Object clone()  {
                    Sheep sheep = null;
                    try {
                        sheep = (Sheep) super.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    return sheep;
                }
              ...
              ...
            }
            ```
    - ClientTest类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/base/ClientTest.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/prototype/base/ClientTest.java)
        - 代码内容
            ```java
            @Test
            public void test01(){
                // 创建1只羊
                Sheep sheep = new Sheep("aa", 1, "aaaa");
        
                // 克隆9只
                Sheep clone1 = (Sheep) sheep.clone();
                Sheep clone2 = (Sheep) sheep.clone();
                Sheep clone3 = (Sheep) sheep.clone();
                Sheep clone4 = (Sheep) sheep.clone();
                Sheep clone5 = (Sheep) sheep.clone();
                Sheep clone6 = (Sheep) sheep.clone();
                Sheep clone7 = (Sheep) sheep.clone();
                Sheep clone8 = (Sheep) sheep.clone();
                Sheep clone9 = (Sheep) sheep.clone();
        
                System.out.println(sheep);
                System.out.println(clone1);
                System.out.println(clone2);
                System.out.println(clone3);
                System.out.println(clone4);
                System.out.println(clone5);
                System.out.println(clone6);
                System.out.println(clone7);
                System.out.println(clone8);
                System.out.println(clone9);
            }
            ```

# 结构型-代理模式
## 代理模式简介
[top](#catalog)
- 什么是代理模式？
    - 为一个对象提供一个替身，以控制对这个对象的访问。即通过代理对象访问目标对象
- 代理模式的好处：可以在目标对象实现的基础上，增强额外的功能操作，即扩展目标对象的功能
- 被代理的对象：远程对象、创建开销大的对象、需要安全控制的对象
- 代理模式的形式
    1. 静态代理
    2. 动态代理(JDK代理、接口代理)
    3. Cglib代理，可以在内存中动态的创建对象，而不需要实现接口，他是属于动态代理的范畴

## 静态代理
[top](#catalog)
- 静态代理的要点
    1. 需要定义接口或者父类，
    2. 被代对象(即目标对象)、代理对象一起实现相同的接口或者是继承相同的父类
- 静态代理的优缺点
    - 优点：在不修改目标对象的功能前提下，通过代理对象对目标功能扩展
    - 缺点：
        - 代理对象与目标对象需要实现相同的接口，会导致存在很多的代理类
        - 如果接口增加了方法，则目标对象和代理对象都需要维护
- 应用示例
    - 具体要求
        - 定义一个接口：`ITeacherDao`
        - 目标对象`TeacherDAO`实现接口`ITeacherDAO`
        - 代理对象`TeacherDAOProxy`实现接口`ITeacherDAO`
        - 通过代理对象来调用目标对象
        - 代理对象于目标对象实现相同的接口，然后通过调用相同的方法来调用目标对象的方法
    - UML图
        - ![static_sample_uml](imgs/pattern/proxy/staticProxy/static_sample_uml.png)
    - 接口
        - 参考代码:[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/ITeacherDao.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/ITeacherDao.java)
                  
        - 代码内容
            ```java
            public class TeacherDao implements ITeacherDao{
                @Override
                public void teach() {
                    System.out.println("teacher is teaching");
                }
            }
            ```
    - 被代理类
        - 参考代码:[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/TeacherDao.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/TeacherDao.java)
        - 代码内容
            ```java
            public class TeacherDao implements ITeacherDao{
                @Override
                public void teach() {
                    System.out.println("teacher is teaching");
                }
            }
            ```
    - 代理类
        - 参考代码:[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/TeacherDaoProxy.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/TeacherDaoProxy.java)
        - 代码内容
            ```java
            public class TeacherDaoProxy implements ITeacherDao {
                private ITeacherDao target;
            
                public TeacherDaoProxy(ITeacherDao target) {
                    this.target = target;
                }
            
                @Override
                public void teach() {
                    System.out.println("proxy start");
                    target.teach();
                    System.out.println("proxy end");
                }
            }
            ```
    - 测试类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/ClientTest.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/staticProxy/ClientTest.java)
        - 代码内容
            ```java
            public class ClientTest {
                @Test
                public void test01(){
                    // 创建被代理对象
                    ITeacherDao teacherDao = new TeacherDao();
            
                    // 创建代理对象，同时将被代理对象作为参数传递给代理对象
                    TeacherDaoProxy proxy = new TeacherDaoProxy(teacherDao);
            
                    // 通过代理对象调用被代理对象的方法
                    // 即执行的是代理对象的方法，内部执行的是被代理对象的方法
                    proxy.teach();
                }
            }
            ```
        - 测试结果
            ```
            proxy start
            teacher is teaching
            proxy end
            ```
          
## 动态代理
[top](#catalog)
- 动态代理的特点
    - 代理对象不需要实现接口，但是目标对象要实现接口，否则不能使用动态代理
    - 代理对象的生成，是利用JDK的API，动态的在内存中构建代理对象
    - 动态代理也叫做：JDK代理，接口代理
    
- JDK中生成代理对象的API
    - 代理类所在包：`java.lang.reflect.Proxy`
    - JDK实现代理需要使用newProxyInstance方法，该方法需要三个参数
        - `ClassLoader loader`: 指定当前目标对象使用的类加载器
        - `Class<?>[] interfaces`:目标对象实现的接口类型，使用泛型方法确认类型
        - `InvocationHandler h`:事件处理器。执行目标对象的方法时，会触发事件处理器方法，会把当前执行的目标对象方法作为参数传入
        ```java
        static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
        ```

- 示例
    - 具体要求
        - 定义一个接口：`ITeacherDao`
        - 目标对象`TeacherDAO`实现接口`ITeacherDAO`
        - 通过工厂`ProxyFactory`来创建代理对象
        - 实例化`ProxyFactory`对象并获取代理对象，通过代理对象调用方法
    - UML图
        - ![dynamic_sample_uml](imgs/pattern/proxy/dynamic/dynamic_sample_uml.png)
    - 接口
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ITeacherDao.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ITeacherDao.java)
        - 代码内容
            ```java
            public interface ITeacherDao {
                void teach();
            }
            ```
    - 目标对象
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/TeacherDao.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/TeacherDao.java)
        - 代码内容
            ```java
            public class TeacherDao implements ITeacherDao {
                @Override
                public void teach() {
                    System.out.println("is teaching");
                }
            }
            ```
    - 工厂类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ProxyFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ProxyFactory.java)
        - 代码内容
            ```java
            public class ProxyFactory<T> {
                //维护一个目标对象
                private T target;
            
                public ProxyFactory(T target) {
                    this.target = target;
                }
            
                //给目标对象生成一个代理对象
                public T getProxyInstance(){
                    return (T)Proxy.newProxyInstance(
                            target.getClass().getClassLoader(),
                            target.getClass().getInterfaces(),
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                    System.out.println("proxy start");
                                    // 通过反射调用目标对象的方法
                                    Object invoke = method.invoke(target, args);
            
                                    System.out.println("proxy end");
                                    return invoke;
                                }
                            }
                    );
                }
            }
            ```
    - 测试类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ClientTest.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/dynamic/ClientTest.java)
        - 代码内容
            ```java
            @Test
            public void test01(){
                // 创建目标对象
                ITeacherDao target = new TeacherDao();
                
                // 通过com.sun.proxy.$Proxy4在内存中动态生成代理对象
                ITeacherDao proxyInstance = new ProxyFactory<ITeacherDao>(target).getProxyInstance();
        
                System.out.println(proxyInstance);
                System.out.println(proxyInstance.getClass());
        
                // 通过代理对象，调用目标对象的方法
                proxyInstance.teach();
            }
            ```
        - 测试结果
            ```java
            proxy start
            proxy end
            com.ljs.learn.pattern.proxy.dynamic.TeacherDao@5679c6c6
            class com.sun.proxy.$Proxy4
            proxy start
            is teaching
            proxy end
            ```
          
## Cglib代理
[top](#catalog)
- 什么是Cglib代理
    - Cglib代理也叫做子类代理
    - 当目标对象只是一个单独的对象，没有实现一个接口时，这个时候可以使用目标对象的子类来实现代理
    - Cglib代理会在内存中创建一个子类对象从而实现对目标对象功能的扩展
- Cglib包
    - Cglib包是一个高性能的代码生成包，底层是通过使用字节吗处理框架ASM来转换字节码并生成新的类
    - Cglib包可以在运行期扩展java类与实现java接口，它广泛的被许多AOP框架使用，例如SpringAOP，实现方法拦截
- 在AOP编程中如何选择代理模式
    1. 目标对象需要实现接口，用JDK代理
    2. 目标对象不需要实现接口，用Cglib代理
- 使用Cglib代理的步骤
    1. 引入cglib的jar包
    2. 在内存中动态构建子类，并且**目标类不能是final**
        1. 创建一个工具类`Enhancer`对象
        2. 使用工具类对象设置父类
        3. 设置回调函数
        4. 创建子类，即代理对象
    3. 实现`MethodInterceptor`接口的`intercept`方法，通过反射来调用目标对象的方法
    4. 目标对象的方法如果为`final`、`static`，不会被拦截，即不会执行目标对象额外的业务方法
    
- 示例
    - 具体要求
        - 定义目标对象`TeacherDAO`
        - 工厂`ProxyFactory`实现`MethodInterceptor`
        - 实例化`ProxyFactory`对象并获取代理对象，通过代理对象调用方法
    - UML图
        - ![cglib_sample_uml](imgs/pattern/proxy/cglib/cglib_sample_uml.png)
    - 目标对象
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/TeacherDao.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/TeacherDao.java)
        - 代码内容：
            ```java
            public class TeacherDao {
                public void teach(){
                    System.out.println("is teaching");
                }
            }
            ```
    - 工厂类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/ProxyFactory.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/ProxyFactory.java)
        - 代码内容
            ```java
            public class ProxyFactory<T> implements MethodInterceptor {
                private T target;
            
                public ProxyFactory(T target) {
                    this.target = target;
                }
            
                // 返回一个target的代理对象
                public T getProxyInstance(){
                    // 1. 创建一个工具类
                    Enhancer enhancer = new Enhancer();
                    // 2. 设置父类
                    enhancer.setSuperclass(target.getClass());
                    // 3. 设置回调函数
                    enhancer.setCallback(this);
                    // 4. 创建子类，即代理对象
                    return (T)enhancer.create();
                }
            
                // 重写intercept方法，实现对目标对象方法的调用
                @Override
                public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    System.out.println("proxy is start");
                    Object invoke = method.invoke(target, args);
                    System.out.println("prroxy is end");
                    return invoke;
                }
            }
            ```
    - 测试类
        - 参考代码：[/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/ClientTest.java](/designPattern/dplearn/dplearn-base/src/test/java/com/ljs/learn/pattern/proxy/cglibProxy/ClientTest.java)
        - 代码内容
            ```java
            @Test
            public void test01(){
                // 创建目标对象
                TeacherDao target = new TeacherDao();
        
                // 创建cglib代理对象
                TeacherDao proxyInstance = new ProxyFactory<TeacherDao>(target).getProxyInstance();
                proxyInstance.teach();
            }
            ```
        - 测试结果
            ```
            proxy is start
            is teaching
            prroxy is end
            ```

## 代理模式的变体
[top](#catalog)
- 防火墙代理
    - 内网通过代理穿透防火墙，实现对公网的访问
- 缓存代理
    - 如请求图片文件等资源时，先带缓存代理获取，如果没取到再到公网或者数据库取，然后缓存
- 远程代理
    - 远程对象的本地代表，通过它**可以把远程对象当本地对象**来调用，远程代理通过网络和真正的远程对象沟通信息
- 同步代理
    - 主要使用在多线程编程中，完成多线程间同步工作
   