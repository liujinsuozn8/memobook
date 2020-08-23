<span id="catalog"></span>
- 参考
    - https://www.bilibili.com/video/BV1jE411j7uX

# 目录---并发编程
- [进程与线程](#进程与线程)
    - [进程与线程的概念](#进程与线程的概念)
    - [进程与线程的对比](#进程与线程的对比)
    - [并行与并发](#并行与并发)
- [Java线程](#Java线程)
    - [创建和运行线程的几种方法](#创建和运行线程的几种方法)
    - [Thread和Runnable的关系](#Thread和Runnable的关系)
    - [查看java进程](#查看java进程)
    - [线程运行原理](#线程运行原理)
        - [栈与栈帧](#栈与栈帧)
        - [线程上下文切换（Thread_Context_Switch）](#线程上下文切换（Thread_Context_Switch）)
    - [Java线程的6种状态](#Java线程的6种状态)
    - [Java线程操作的常见方法](#Java线程操作的常见方法)
    - [start与run方法](#start与run方法)
    - [sleep与yield](#sleep与yield)
- [](#)

# 进程与线程
## 进程与线程的概念
[top](#catalog)
- 进程
    - 用来加载指令、管理内存、管理IO
    - 当一个程序被运行，从磁盘加载这个程序的代码到内存，就开启了一个进程
    - 进程可以视为程序的一个实例，大部分程序可以同时运行多个实例进行，如记事本
        - 也有程序只能启动一个实例进程，如app应用
- 线程
    - 一个进程之内可以分为一个到多个线程
    - 一个线程就是一个指令流，指令流中的所有指令以一定的顺序交给CPU执行
- 在 windows 中，进程是不活动的，只是作为线程的容器
- 在Java中
    - 线程是 `最小调度单位`
    - 进程是 `最小资源分配单位`

## 进程与线程的对比
[top](#catalog)
- 进程基本上相互独立，而线程存在于进程内，是进程的一个子集
- 进程用用共享的资源，如内存空间等，共其内部的线程共享
- 进程间通信较为复杂
    - 同一台计算机的进程通信称为IPC（Inter-process communication）
    - 不同计算机之间的进程通信需要通过网络，并遵守共同的协议，如HTTP
- 线程通信比较简单
    - 因为他们共享进程的内存
    - 如多个线程可以共享一个遍历
- 线程更加轻量
    - 线程上下文切换的成本一般比进程上下文切换要低

## 并行与并发
[top](#catalog)
- 单核cpu下，线程实际是`串行执行`的
- 任务调度器
    - 操作系统中的一个组件
    - 可以将cpu的时间片分给不同的线程使用
        - windows下时间片最小约为15毫秒
    - cpu在线程间的切换非常快，给人的感觉是同时运行的
- **微观串行，宏观并行**
- <span style='color:red'>什么是并发，concurrennt</span>
    - 一般将线程轮流使用CPU的做法称为并发
- <span style='color:red'>什么是并行，parallel</span>
    - 多核CPU下，每个核都可以调度、运行线程，此时线程是并行的
    - 即没有发生切换，多个线程通过运行到不同的核内
- 并行与并发的简要描述
    - 并发，concurrent，是同一时间应对多件事的能力
    - 并行，parallel，是同一时间动手做多件事情的能力
- 场景的场景是: **又有并发、又有并行**

# Java线程
## 创建和运行线程
[top](#catalog)
- 启动java程序时，默认会启动一个主线程
- 创建方式
    1. 使用 `Thread`，需要重写 `run` 方法
        - 语法
            ```java
            // 1. 创建线程对象
            Thread t = new Thread(){
                public void run(){...}
            }
            // 2. 启动线程
            t.start(); 
            ```
        - 示例: [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByThread.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByThread.java)
            ```java
            public static void main(String[] args) {
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        log.debug("running");
                    }
                };
        
                t.setName("t1");
                t.start();
        
                log.debug("running");
            }
            ```
    2. Runnable + Thread，将线程和任务代码分开
        - Thread代表线程
        - Runnable 表示可运行的任务，需要重写 `run` 方法
        - 语法
            ```java
            public static void main(String[] args) {
                // 1. 创建任务
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        ...
                    }
                };
                
                // 2. 创建线程，并将任务添加的线程中
                Thread t = new Thread(r);
                
                // 3. 启动线程
                t.start();
            }
            ``` 
        - 示例: [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByRunnable.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByRunnable.java)
            ```java
            public static void main(String[] args) {
                // 1. 创建任务
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        log.debug("running");
                    }
                };
                
                // 2. 创建线程，并将任务添加的线程中
                Thread t = new Thread(r);
                
                // 3. 启动线程
                t.start();
                
                log.debug("running");
            }
            ```
    3. 使用lambda表达式简化
        - 语法
            ```java
            Runnable r = () -> {...};
            Thread t = new Thread(r);
            t.start();
            ```
        - 示例: [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByLambda.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByLambda.java)
            ```java
            public static void main(String[] args) {
                Runnable r = () -> log.debug("lambda test");
                Thread t = new Thread(r);
                t.start();
                log.debug("main log");
            }
            ```
    4. FutureTask配合Thread
        - FutureTask能够接收 `Callable` 类型的参数，用来处理线程的返回结果
        - 语法
            ```java
            // 创建任务
            FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    // 设置返回值
                    return ...;
                }
            });
          
            // 启动线程执行任务
            new Thread(task).start();
          
            // get()方法用于获取线程返回的数据
            // get()将会阻塞当前线程，知道结果返回
            task.get();
            ```
        - 示例: [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByFutureTask.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/CreateByFutureTask.java)
            ```java
            public static void main(String[] args) throws ExecutionException, InterruptedException {
                // 1. 创建任务
                FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        log.debug("running");
                        Thread.sleep(2000); // 延迟返回结果
                        return 100;
                    }
                });
        
                // 2. 创建线程执行任务
                Thread t = new Thread(task);
                t.start();
        
                // 3. 获取线程返回的数据
                // get() 方法 阻塞当前线程，并等待结果返回
                Integer result = task.get();
                log.debug("result = {}", result);
            }
            ```
        
## Thread和Runnable的关系
[top](#catalog)
- Thread 是 Runnable 接口的实现类
- 最终是由 `Thread.run` 方法调用的，方法内是直接调用 Runnable 的 run 方法
    ```java
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
    ``` 
- 使用 Runnable 接口更容易与其他API配合
- Runnable 接口让任务类脱离的Thread的继承体系，更加灵活


## 查看java进程
[top](#catalog)
- `ps -fe | grep java`，查看java进程
- `jps，查看java进程`
- `kill java进程号`
- `top -H -p java进程号`，查看详细进程信息 
- `jstack java进程号`，查看java进程快照
- `jconsole` 在图形界面中，查看某个java进程中线程的运行情况
    - java类的运行方式
        ```shell
        java -Djava.rmi.server.hostname=`ip地址` 
          -Dcom.sun.management.jmxremote 
          -Dcom.sun.management.jmxremote.port='连接端口'
          -Dcom.sun.management.jmxremote.ssl=是否安全连接
          -Dcom.sun.management.jmxremote.authenticate=是否认证  <java类>
        ```

## 线程运行原理
### 栈与栈帧
[top](#catalog)
- Java Virtual Machine Stacks （Java 虚拟机栈）
- JVM中由堆、栈、方法区组成
- 线程的栈内存是相互独立的，每个线程有自己的栈内存，栈内存中有多个栈帧
    - JVM中的栈内存就是线程，每个线程启动后，虚拟机会为该线程分配一块内存
    - 每个栈由多个栈帧（Frame）组成，对应每次方法调用时所占用的内存
    - 每个线程<span style='color:red'>只能有一个活动的栈帧</span>，对应当前正在执行的那个方法
    - 示意图
        ```java
                线程1         │        线程2
            栈　内　存      │      栈　内　存
        栈帧4---> method04 │  
        栈帧3---> method03 │  栈帧3---> methodC
        栈帧2---> method02 │  栈帧2---> methodB
        栈帧1---> method01 │  栈帧1---> methodA
        ```

### 线程上下文切换（Thread_Context_Switch）
[top](#catalog)
- 导致cpu不再执行当前线程，转而执行另一个线程的原因
    1. 线程的cpu时间片用完
    2. 垃圾回收
    3. 有更高优先级的线程需要运行
    4. 线程自己调用了以下方法的程序
        - sleep
        - yield
        - wait
        - join
        - park
        - synchronized
        - lock
- 当上下文切换发生时的操作
    1. 由操作系统保存当前线程的状态
    2. 恢复另一个线程的状态
- 上下文切换 在 Java中对应的概念是程序计数器（Program Counter Register），
    - 用于记录下一条jvm指令的执行地址，是线程私有的
    - 记录之后开始执行
- 操作系统需要保存的线程状态包括
    - 程序计数器
    - 虚拟机栈中每个栈帧的信息，如:局部变量、操作数栈、返回地址等
- **上下文频繁切换会影响性能**
    - 当线程数超过CPU核心数，CPU必然会在多个线程中轮流切换
    - 线程越多，上下文切换的次数越多，系统消耗也会越大
- 如何选择线程数
    - ?????        

## Java线程的6种状态
[top](#catalog)
- NEW
- RUNNABLE
- BLOCKED
- WAITING
- TIMED_WAITING，有时间限制的等待
- TERMINATED
    
## Java线程操作的常见方法
[top](#catalog)
- 实例方法

    |方法名|功能|注意事项|
    |-|-|-|
    |start()|启动一个新线程，在新的线程运行`run()`方法中的代码|start方法只是让线程进入**就绪状态**，代码不一定立刻运行（CPU还没有分配时间片）<br><span style='color:red'>start方法只能调用一次</span>，如果调用了多次会出现`IllegalThreadStateException`|
    |run()|新线程启动后会调用的方法|如果在构造 `Thread` 对象时，传递了 `Runnable` 参数，则线程启动后会调用 `Runnable` 中的 `run` 方法，否则默认不执行任何操作。<br>可以创建 `Thread` 的子类对象，来覆盖默认行为|
    |join()|等待线程运行结束||
    |join(long n)|等待线程运行结束。最多等待 n **毫秒**||
    |getId()|获取线程的长整型id|id是唯一的|
    |getName()|获取线程名||
    |setName(String)|修改线程名||
    |getPriority()|获取线程的优先级||
    |setPriority(int)|修改线程优先级|java中优先级是: 1~10 的整数。优先级越大，该线程被CPU调度的<span style='color:red'>概率越大</span>|
    |getState()|获取线程状态||
    |isInterrupted()|判断是否被打断|不会清除、会添加`打断标记`|
    |isAlive()|线程是否存活||
    |interrupt|打断线程|如果被打断的线程正在 sleep、wait、join 会导致被打断的线程抛出 `InterruptedException`，并清除`打断标记`<br>如果打断正在运行的线程，会设置`打断标记`<br>park 的线程被打断，会设置打断标记|

- 静态方法

    |方法名|功能|注意事项|
    |-|-|-|
    |interrupted()|判断当前线程是否被打断|会清除`打断标记`|
    |currentThread()|获取当前正在执行的线程||
    |sleep()|让当前执行的线程休眠 n **毫秒**。休眠时会然噶出cpu的时间片给其他线程||
    |yield()|提示线程调度器让出当前对CPU的使用|主要是为了测试和调试|
    
## start与run方法
[top](#catalog)
- 直接执行 `run()` 只会会在当前线程内执行，**不会开启新线程**
    - 参考代码
        - [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/OnlyRun.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/createThread/OnlyRun.java)
    - 代码内容
        ```java
        public static void main(String[] args) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    log.debug("running");
                }
            };
    
            // 1. 无法启动新的线程
            t.run();
    
            // 输出:
            // [main] c.OnlyRun - running
    
            // 2. 创建新线程
            t.start();
            // 输出:
            // [Thread-0] c.OnlyRun - running
        }
        ```

## sleep与yield
[top](#catalog)
- `Thread.sleep()`
    - `Thread.sleep()` 会使调用该方法的线程进入 `WAITING` 状态
    - 调用sleep后的线程状态变化: `Running ---> Timed Running`
    - 其他线程可以使用 `interrupt()` 打断正在睡眠的线程，`sleep` 将方法会抛出 `InterruptedException`
    - 睡眠结束后的线程不一定立刻执行
    - 示例
        - 参考代码
            - [/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/state/SleepAndInterrupt.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/mythread/state/SleepAndInterrupt.java)
        - 代码内容
            ```java
            public static void main(String[] args) throws InterruptedException {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        log.debug("start sleep");
                        try {
                            // 2. 线程开始休眠
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            log.debug("has been interrupted");
                            e.printStackTrace();
                        }
                    }
                };
        
                // 1. 启动线程
                t.start();
        
                // 3. 主线程休眠，比子线程时间短
                Thread.sleep(500);
        
                // 4. 切断子线程
                log.debug("try interrupted");
                t.interrupt();
                
                // 输出:
                // 05.010 [Thread-0] c.SleepAndInterrupt - start sleep
                // 05.510 [main] c.SleepAndInterrupt - try interrupted
                // 05.510 [Thread-0] c.SleepAndInterrupt - has been interrupted
            }
            ```        


[top](#catalog)