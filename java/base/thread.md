<span id="catalog"></span>

- [并发编程的问题](#并发编程的问题)
- [Java并发机制的底层实现原理](#Java并发机制的底层实现原理)
    - [volatile的原理与应用](#volatile的原理与应用)
    - [synchronized的原理与应用](#synchronized的原理与应用)
    - [Java对象头](#Java对象头)
    - [锁的升级与对比](#锁的升级与对比)
    - [原子操作的实现原理]](#原子操作的实现原理)
- [Java内存模型](#java内存模型)
    - [Java内存模型基础](#Java内存模型基础)
        - [并发编程模型的两个关键问题](#并发编程模型的两个关键问题)
        - [内存可见性问题](#内存可见性问题)
        - [Java内存模型的抽象结构](#Java内存模型的抽象结构)
        - [从源代码到指令序列的重排序](#从源代码到指令序列的重排序)
        - [并发编程模型的分类](#并发编程模型的分类)
        - [happens-before简介](#happens-before简介)
    - [重排序](#重排序)
        - [数据依赖性](#数据依赖性)
        - [as-if-serial语义](#as-if-serial语义)
        - [重排序对多线程的影响](#重排序对多线程的影响)
    - [顺序一致性](#顺序一致性)
        - [数据竞争与顺序一致性](#数据竞争与顺序一致性)
    - [volatile的内存语义](#volatile的内存语义)
        - [volatile的特性](#volatile的特性)
        - [volatile写-读建立的happens-before关系](#volatile写-读建立的happens-before关系)
        - [volatile写-读的内存语义](#volatile写-读的内存语义)
        - [volatile内存语义的实现](#volatile内存语义的实现)
        - [为什么要增强volatile的内存语义](#为什么要增强volatile的内存语义)
    - [锁的内存语义](#锁的内存语义)
        - [锁的释放-获取建立的happens-before关系](#锁的释放-获取建立的happens-before关系)
        - [锁的释放和获取的内存语义](#锁的释放和获取的内存语义)
        - [锁的内存语义实现](#锁的内存语义实现)
        - [concurrent包的实现](#concurrent包的实现)
- [](#)
- [](#)
- [](#)

# 并发编程的问题
[top](#catalog)
- 上下文切换
    - 如何减少上下文切换
        - 无锁并发编程
            - 多线程竞争锁时，会引起上下文切换
            - 避免方法，如：将数据的ID按照Hash算法取模分段，不同的哦线程处理不同段的数据
        - CAS算法
            - Java的Atomic包使用CAS算法来更新数据，不需要加锁
        - 使用最少线程
        - 使用协程
            - 在单线程里实现多任务的调度，并在单线程里维持多个任务间的任务切换
- 死锁
    - 避免死锁的几种方法
        - 避免在一个线程同时获取多个锁
        - 避免在一个线程在锁内占用多个资源，尽量保证每个锁只占用一个资源
        - 尝试使用定时锁，使用`lock.tryLock(timeout)`来替代使用内部锁机制
        - 对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况
- 资源限制
    - 资源限制是指：在并发编程时，程序的执行速度受限于计算机硬件资源或软件资源
    - 硬件资源
        - 宽带的上传/下载速度
        - 硬盘读写速度
        - CPU的处理速度
    - 软件资源，如：
        - 数据库的连接数
        - socket连接数
    - 资源限制导致的问题
        - 多线程受限于资源，仍然在串行执行
        - 由于增加了上下文切换和资源调度，导致执行变慢
    - 如何解决资源受限
        - 对于硬件资源限制
            - 使用集群并行执行程序
                - 如通过`数据ID % 机器数`来得到一个机器编号，然后有对应的机器来执行
        - 对于软件资源限制
            - 可以使用资源池来**复用资源**，如：
                - 使用连接池来复用数据库和socket连接
                - 在调用对方webservice接口获取数据时，只建立一个连接
    - 如何在资源限制情况下进行并发编程
        - 根据不同的资源限制调整程序的并发度

# Java并发机制的底层实现原理
[top](#catalog)
- java代码的运行
    1. java代码-->编译-->java字节码
    2. java字节码-->类加载器-->加载到JVM
    3. JVM执行字节码-->汇编指令-->在CPU上执行
- java中使用的并发机制依赖于jvm的实现和cpu指令

## volatile的原理与应用
[top](#catalog)
- `volatile`的基本概念
    - 是轻量级的`synchronized`
    - 在多处理器开发中保证了共享变量的可见性
    - 可见性：当一个线程修改一个共享变量时，另一个线程能读到这个修改后的值
    - **不会引起线程上下文的切换和调度**
        - 如果使用得当，它比`synchronized`的使用和执行成本更低

- 一些CPU术语

|术语|英文|描述|
|-|-|-|
|内存屏障|memory barriers|是一组处理器指令，用于实现对内存操作的顺序限制|
|缓存行|cache line|缓存中可以分配的最小存储单位。处理器填写缓存线时会加载整个缓存线，需要使用多个主内存读周期|
|原子操作|atomic operations|不可中断的一个或一系列操作|
|缓存行填充|cache line fill|当处理器识别到从内存中读取操作数是可缓存的，处理器读取整个缓存行到适当的缓存（L1，L2，L3 或所有）|
|缓存命中|cache hit|如果进行高速缓存行填充操作的内存位置仍然是下次处理器访问的地址时，处理器从缓存中读取操作数，而不是从内存读取|
|写命中|write hit|当处理器将操作数写回到一个内存缓存的区域时，它首先会检查这个缓存的内存地址是否在缓存行中，如果存在一个有效的缓存行，则处理器将这个操作数写回到缓存，而不是写回到内存，这个操作被称为写命中|
|写缺失|write misses the cache|一个有效的缓存行被写入到不存在的内存区域|

- `volatile`的定义与实现原理
    - 如果一个字段被声明成`volatile`，Java线程内存模型确保**所有线程看到这个变量的值是一致的**
    - 对`volatile`修饰的共享变量进行写操作时，在汇编代码中会多出一行`lock`代码
        - Lock的操作
            - 将当前处理器缓存行的数据写回到系统内存
            - 写回操作会使其他CPU核心中缓存了该内存地址的数据无效化
    - `volatile`的两条原则
        - Lock前缀指令会引起处理器缓存回写到内存
        - 一个处理器的缓存回写到内存会导致其他处理器的缓存无效
- `volatile`的使用优化
    - `LinkedTransferQueue`，通过追加字节的方式来优化性能
        - 追加字节到64个字节，15个填充对象+当前对象自身，共64字节，一次读取填满一个缓存行
        - 填满后，即头节点占满了一个缓存行，进行入队和出队操作时，就可以避免在一个缓存行中不停的修改数据，而其他处理器不能访问的问题，提高了多处理器下的性能

    - 不是所有的`volatile`变量都应该追加到64字节
        - 缓存行不是64字节的处理器
        - 共享变量不会被频繁地写
            - 追加字节的方式需要处理器读取更多的字节到高速缓存区，这本身就会带来一定的性能消耗
            - 如果共享变量不会被频繁地写，锁的几率也非常小，就没有必要通过追加字节来避免相互锁定

    - 追加字节的方式在Java7中下可能不生效
        - Java7会淘汰或重新排列无用字段，需要使用其他追加字节的方式


## synchronized的原理与应用
[top](#catalog)
- `JavaSE 1.6`对`synchronized`进行了优化，有些情况下不再是`重量级锁`
    - 减少`获取锁/释放锁`带来的性能消耗的优化
        - 引入`偏向锁`和`轻量级锁`
        - 锁的存储结构和升级过程 ???????????????/
- `synchronized`实现同步的基础:Java中的每一个对象都可以作为锁，表现为3点
    1. `synchronized`方法，锁是当前实例对象
    2. `synchronized`类，锁是当前类的Class对象?????????
    3. `synchronized`代码块，锁是`()`中配置的对象

- 访问同步代码时，必须先得到锁，**退出或抛出异常时必须释放锁**

- `synchronized`**在JVM中的实现原理**
    - JVM基于进入和退出`Monitor`对象的方式来实现`方法同步`和`代码块同步`  ????????????????Monitor对象
        - 但方法和代码块的实现细节不一样

    - JVM的实现
        - 代码块同步使用： `monitorenter`, `monitorexit`指令实现
        - 方法同步的实现细节在JVM规范中没有详细说明
            - 方法同步也可以使用`monitorenter`和`monitorexit`指令实现
        - `monitorenter`指令是在**编译后**插入到同步代码块的开始位置
        - `monitorexit`指令是在**编译后**插入到同步代码块的结束位置和异常位置

    - 加锁的过程
        - 前提：
            - JVM会保证**每个monitorenter必须有对应的monitorexit**
            - 任何对象都有一个`monitor`与之关联

        - 当一个`monitor`被持有后，它将处于锁定状态
        - 线程执行到`monitorenter`指令时，会尝试获取对象所对应的`monitor`的所有权，即**尝试获得对象的锁**

- `synchronized`用的锁存在Java对象头中


## Java对象头
[top](#catalog)
- 对象头的内容
    - 如果对象是数组类型，则使用3个字宽；非数组类型，用2个字段

    |长度|内容|说明|
    |-|-|-|
    |32/64bit|Mark Word|存储对象的hashCode或锁信息等|
    |32/64bit|Class Metadata Address|存储到对象类型数据的指针|
    |32/32bit|Array length|数组的长度(如果当前对象是数组)|

- `Mark Word`的存储内容(32位JVM) ?????64位????????????

## 锁的升级与对比
[top](#catalog)
- JavaSE1.6为了减少获得锁和释放锁带来的性能消耗，引入了：`偏向锁`、`轻量级锁`
- JavaSE1.6中锁的状态
    - 4种状态**从低到高**
        1. 无锁状态
        2. 偏向锁状态
        3. 轻量级锁状态
        4. 重量级锁状态
    - 锁的状态会随着竞争情况逐渐升级
    - 锁**可以升级但不能降级**
        - 这种策略是为了**提高获得锁和释放锁的效率**
- 偏向锁
    - 大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得，为了让线程获得锁的代价更低而引入的偏向锁
    - 偏向锁的获得过程
        - 一个线程访问同步块并获得锁时
            - 在对象头和栈帧中的`锁记录`中存储：锁偏向的线程ID
        - 之后再进入同步块时，测试对象头的`Mark Word`中是否存储：**指向当前线程的偏向锁**
            - 不需要使用CAS操作来加锁/解锁
            - 测试成功则获得锁
            - 测试失败，则再测试`Mark Word`中偏向锁的标识是否为`1`(表示当前是偏向锁)
                - 如果` ！= 1`，使用CAS竞争锁
                - 如果` = 1`，尝试使用CAS将对象头的偏向锁指向当前线程
    - 偏向锁的撤销 ????????????????
        - 偏向锁使用了一种等待竞争出现才释放锁的机制，当其他线程尝试竞争偏向锁时，持有偏向锁的线程才会释放锁
        - 偏向锁的撤销需要等待全局安全点，即该时点上没有正在执行的字节码
        - 撤销过程
            - 暂停拥有偏向锁的线程
            - 检查持有偏向锁的线程是否活着
                - 线程不活动，将对象头设置成无锁状态
                - 线程活动，拥有偏向锁的栈会被执行，遍历偏向对象的锁记录，栈中的锁记录和对象头的`Mark Word`要么重新偏向与其他线程，要么恢复到无锁或标记对象不适合作为偏向锁
            - 唤醒暂停的线程
    - 关闭偏向锁
        - 偏向锁在Java6、Java7中默认是启用的，是在应用程序启动几秒之后才激活
            - 可以通过JVM参数来关闭延迟：`-XX:BiasedLockingStartupDelay=0`
        - 如果确定应用程序里所有的锁通常情况下处于竞争状态，可以通过JVM参数关闭偏向锁：`-XX:UseBiasedLocking=false`
            - 程序默认会进入轻量级锁状态
- 轻量级锁
    - 加锁（执行同步块之前）
        - 开辟`锁记录空间`: 在当前线程的`栈帧`开辟`锁记录`的空间
        - `Displaced Mark Word`：将对象头中的`Mark Word`复制到锁记录中
        - 替换`Mark Word`: 线程尝试使用CAS将对象头中的`Mark Word`替换为指向`锁记录`的指针
            - 替换成功，当前线程获得锁
            - 替换失败，表示其他线程竞争锁，当前线程使用`自旋锁`来获取锁
    - 解锁
        - 使用原子的CAS操作将`Displaced Mark Word`替换到对象头
            - 如果成功，表示没有竞争发生
            - 如果失败，表示当前锁存在竞争，锁会**膨胀为重量级锁**  ???????????????????
    - 自旋锁会消耗CPU，为了避免无用的自旋(如获得锁的线程被阻塞了)，锁升级为重量级锁
        - 处于重量级锁时，其他视图获取锁的线程都会被阻塞
        - 当持有锁的线程释放锁之后，会唤醒被阻塞线程，这些线程开始重新竞争

- 锁的对比

    |类型|优点|确定|适用场景|
    |-|-|-|-|
    |偏向锁|加锁、解锁不需要额外的CPU消耗，和执行非同步方法相比仅存在纳秒级的差距|如果线程间存在锁竞争，会带来额外的锁撤销消耗|适用于只有一个线程访问同步块|
    |轻量级锁|竞争的线程不会阻塞，提供了程序的响应速度|如果始终得不到锁竞争的线程，会使用自旋锁消耗CPU|追求响应时间、同时块执行速度非常块|
    |重量级锁|线程竞争不使用自旋锁，不会消耗CPU|线程阻塞，响应时间缓慢|追求吞吐量、同步块执行较慢|

## 原子操作的实现原理
[top](#catalog)
- 原子操作：不可中断的一个或一系列操作
- 处理器上的实现原理
    - 一些概念

        |术语|英文|解释|
        |-|-|-|
        |缓存行|Cache line|缓存的最小操作单位|
        |比较并交换(CAS操作)|Compare and Swap|两个参数：新值、旧值，if 变量==旧值，set 变量=新值|
        |CPU流水线|CPU pipeline|CPU中由5～6个不同功能的电路单元组成一条指令处理流水线，然后将一条X86指令分成5～6步后再由这些电路单元分别执行，来实现在一个CPU时钟周期内完成一条指令，提供CPU的运算速度|
        |内存顺序冲突|Memory order violation|一般由假共享引起，假共享指多个CPU同时修改同一个缓存行的不同部分而引起其中一个CPU的操作无效，当出现这个内存顺序冲突时，CPU必须清空流水线|

    - 处理器如何实现原子操作
        - 处理器会自动保证基本的内存操作的原子性
            - 处理器从内存中读取/写入一个字节是原子的，即一个处理器读取一个字节时，其他处理器不能访问这个字节的内存地址
        - 复杂的内存操作，处理器提供`总线锁定`和`缓存锁定`两个机制来保证复杂内存操作的原子性
            - 如：跨总线宽度、跨多个缓存行、跨页表的访问
            - `总线锁定`
                - `总线锁定`: 使用处理器提供的一个`LOCK#`信号，当一个处理器在总线上输出此信号时，其他处理器的请求被阻塞住，使该处理器可以独占共享内存
                - 解决的问题
                    - 多个处理器同时对共享变量进行`读改写`操作(如`i++`)，可能会导致计算结果与期望值不符
                        - 如两次`i++`，分别在两个CPU中执行，结果肯为2，不是3
                    - 通过总线锁，保证CPU1操作共享变量时，CPU2不能操作缓存了该共享变量内存地址的缓存
                - 缺点
                    - `总线锁定`会阻塞CPU和内存之间的通信
                    - 锁定期间，其他处理器不能操作其他内存地址的数据，使得**总线锁的开销比较大**

            - `缓存锁定`
                - `缓存锁定`：内存区域如果被缓存在处理器的缓存行中，并且在`Lock`操作期间被锁定，那么当它执行锁操作回写到内存时，处理器不在总线上声言`LOCK#`信号，而是**修改内部的内存地址**，并允许它的缓存一致性机制来保证操作的原子性
                    - 缓存一致性机制会阻止同时修改两个以上处理器缓存的内存区域数据，当其他处理器回写已被锁定的缓存行的数据时，会使缓存行无效

                - 在某些情况下，处理器使用`缓存锁定`来替代`总线锁定`来优化
                - 频繁使用的内存会缓存在处理器的L1，L2，L3高速缓存中，原子操作就可以直接在处理器内部缓存中进行，不需要`总线锁定`
                - 两种情况不使用`缓存锁定`
                    1. 被操作数据不能被缓存在处理器内部，或操作的数据跨多个缓存行时，处理器会使用`总线锁定`
                    2. 有些处理器不支持`缓存锁定`

- Java的实现原理
    - Java中通过`锁`和`循环/自旋CAS`的方式来实现原子操作
    - 使用`循环/自旋CAS`实现原子操作
        - JVM中的CAS操作使用处理器提供的`COMPXCHG`指令实现
        - 基本思路：循环进行CAS操作直到成功为止
        - CAS实现原子操作的3大问题
            1. ABA问题
                - CAS会检查值有没有发生变化
                - 如果一个值是`A`，变成了`B`，有变成了`A`，CAS在运行时可能会认为**没有发生变化**，但实际是变化了
                - 解决方法：使用版本号
                    - 在变量前添加版本号：1A->2B->3A
                    - 从JDK1.5开始，`Atomic`包里提供了一个`AtomicStampedReference`来解决ABA问题
            2. 循环时间长开销大
                - 如果`自旋CAS`长时间不成功，会给CPU带来非常大的执行开销
                    - 如果JVM能支持处理器提供的`pause指令`，那么效率会有一定提升
                    - `pause指令`的作用
                        1. 延迟流水线执行和资料，使CPU不会消耗过多的执行资源，延迟时间取决于具体实现版本，一些处理器的延迟时间是0
                        2. 避免退出循环时因内存顺序冲突而引起CPU流水线被清空，来提高CPU的执行效率
            3. 只能保证一个共享变量的原子操作
                - 对多个共享变量时操作时，`循环CAS`无法保证操作的原子性
                - 可以使用锁来解决
                - 可以将多个共享变量合成一个
                    - JDK1.5开始，提供了`AtomicReference`来保证引用对象之间的原子性，可以把多个变量放在一个对象里进行CAS操作
    - 使用`锁`实现原子操作
        - 除了偏向锁，JVM实现锁的方式都用了`循环CAS`
            - 即当一个线程想进入同步块的时候使用`循环CAS`获取锁，退出时使用`循环CAS`释放锁


# Java内存模型
## Java内存模型基础
### 并发编程模型的两个关键问题
[top](#catalog)
1. 如何通信/交换信息
    - 两种通信机制
        - 共享内存
            - 通过对象内存中的公共状态来进行`隐式通信`
        - 消息传递
            - 线程间通过发送消息来进行`显示通信`
2. 如何同步
    - 同步：`程序中用于控制不同线程间操作发生相对顺序的机制`
    - 共享内存机制下，同步是显示的，必须通过方法或指令来使线程互斥
    - 消息传递机制下，同步是隐式的，
- Java采用**共享内存模型**
- 原子性、可见性、重排序 ?????????????????

### Java内存模型的抽象结构
[top](#catalog)
- Java的所有实例域、静态域、数组元素都存储在堆内存中，**堆内存在线程之间共享**
- 局部变量、方法参数、异常处理器参数不会在线程之间共享，**没有内存可见性问题，也不受内存模型的影响**
- JMM
    - Java线程之间的通信由Java内存模型(JMM)控制
    - JMM定义了线程和主内存之间的抽象关系：
        - 线程之间的共享变量存储在`主内存(Main Memory)`中
        - 每个线程都有一个私有的`本地内存(Local Memory)`，本地内存中存储了**该线程读/写共享变量的副本**
            - 本地内存是JMM的一个**抽象概念**，不是真实存在
                - 它包含了：缓存、写缓冲区、寄存器、其他的硬件和编译器优化
            - <label style="style:red">每次会先在本地内存中查找变量，如果没找到再到主内存中查找，然后保存到本地内存中</label>
    - JMM通过控制`主内存`与每个线程的`本地内存`之间的交互，来提供内存可见性的保证
- JMM的抽象示意图??????????
    - 线程A、线程B的通信过程
        1. A把本地内存中更新过的共享变量刷新到主内存中
        2. B从主内存中读取由A更新过的共享变量

### 内存可见性问题
[top](#catalog)
- 产生问题的原因
    - 多个线程同时读取同一个变量(包括主线程)，各个线程分别从`主内存`中读取变量，然后保存到个线程的`本地内存`中
    - 当任意一个线程更新变量时，其他线程无法得到最新值
- `可见性`：多个线程共享变量时，一个线程更新共享变量时，其他线程能及时得到最新值
- `不可见性`：多个线程共享变量时，一个线程更新共享变量时，其他线程能无法取到最新值
- 解决方法
    - 同步： 使用`synchronized`或`锁`来同步多个线程
        - 使用同步之后，<label style="color:red">会使程序去主内存中获取变量，保证使用的变量永远都是最新的，但是会损耗性能</label>
    - `volatile`关键字修饰变量
        - 使用`volatile`后，如果有线程更新了共享变量，会强制其他线程重新从`主内存`读取共享变量，并保存到`本地内存`中，每次使用时仍然从`本地内存`中读取

    - 参考：`https://blog.csdn.net/wb_zjp283121/article/details/88951175`
    ```java
    class ThreadVisibility {
        private static boolean isStop = false;
        private static volatile isStopVolatile = false;
        private static Lock lock = new ReentrantLock(true);

        private runThread(Thread t) {
            System.out.print("before start, isStop = " + isStop);

            // 启动线程
            t.start();

            // 主线程暂停
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            // 修改isStop状态，来停止子线程
            isStop = true

            System.out.print("after start, isStop = " + isStop);
        }

        private runThreadVolatile(Thread t) {
            System.out.print("before start, isStop = " + isStopVolatile);

            // 启动线程
            t.start();

            // 主线程暂停
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            // 修改isStopVolatile状态，来停止子线程
            isStopVolatile = true

            System.out.print("after start, isStop = " + isStopVolatile);
        }

        @Test
        public void method01() {
            // 构造线程
            Thread t = new Thread() {
                public void run() {
                    while(!isStop) {

                    }
                }
            }

            // 内存不可见，导致程序无法终止
            runThread(t);
        }

        
        @Test
        public void method02() {
            // 使用synchronized来同步
            // 构造线程
            Thread t = new Thread() {
                public void run() {
                    while(!isStop) {
                        synchronized(this) {
                            // 同步，使线程到主内存中获取变量：isStop
                        }
                    }
                }
            }

            runThread(t);
        }

        @Test
        public void method03() {
            // 使用锁同步
            // 构造线程
            Thread t = new Thread() {
                public void run() {
                    while(!isStop) {
                        // 获取锁
                        lock.lock();
                        try{

                        } finally {
                            // 释放锁
                            lock.unlock();
                        }
                    }
                }
            }

            runThread(t);
        }
        
        @Test
        public void method04() {
            // 使用volatile
            // 构造线程
            Thread t = new Thread() {
                public void run() {
                    while(!isStopVolatile) {
                    }
                }
            }

            runThreadVolatile(t);
        }
    }
    ```

### 从源代码到指令序列的重排序
[top](#catalog)
- 执行程序时，为了提高性能，**编译器和处理器**会对指令做`重排序`
- 重排序分类
    - 编译器重排序
        - 编译器优化的重排序
    - 处理器重排序
        - 指令级并行的重排序
        - 内存系统的重排序
- 3种重排序
    1. 编译器优化的重排序
        - 编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序
    2. 指令级并行的重排序
        - 现代处理器采用了指令级并行技术来将多条指令重叠执行
        - 如果不存在[数据依赖性](#数据依赖性)，处理器可以改变对应机器指令的执行顺序
    3. 内存系统的重排序
        - 由于处理器使用`缓存`和`读/写缓冲区`，会允许对`读/写操作`进行重排序
            - 实际的读写顺序与代码顺序不一致

- 从java源代码到最终实际执行的指令序列，会经历下面3种重排序
    ```
    源代码--> 1:编译器优化重排序 --> 2:指令级并行重排序 --> 3:内存系统重排序--> 最终执行的指令序列
    ```
- 重排序会导致多线程程序出现内存`可见性`问题，如何解决
    - 对于编译器，JMM的编译器重排序规则会**禁止特定类型**的编译器重排序
    - 对于处理器，JMM的处理器重排序规则会要求Java编译器在生成指令序列时，插入特定类型的`内存屏障`指令
        - 通过`内存屏障`指令来**禁止特定类型的处理器重排序**
- 4种`内存屏障`
    - `StoreLoad Barriers`是一个全能型的屏障，同时具有其他3个屏障的效果
        - 现代的处理器大多支持该屏障
        - 执行该屏障开销很昂贵，因为当前处理器通常要把`写缓冲区`中的数据**全部刷新到内存中**

    |屏障类型|指令示例|用途|
    |-|-|-|
    |LoadLoad Barriers|Load1;LoadLoad;Load2|确保Load1数据的装载优先于Load2及所有后续装载指令的装载|
    |StoreStore Barriers|Store1;StoreStore;Store2|确保Store1数据对其他处理器可见(刷新到内存)优先于Store2及所有后续存储指令的存储|
    |LoadStore Barriers|Load1;LoadStore;Store2|确保Load1数据装载优先于Store2及所有后续的存储指令刷新到内存|
    |StoreLoad Barriers|Store1;StoreLoad;Load2|确保Store1数据对其他处理器变得可见(刷新到内存)优先于Load2及所有后续装载指令的装载。StoreLoad Barriers会使该屏障之前的所有内存访问指令(Load、Store)完成之后，才执行该屏障之后的内存访问指令|

- JMM是**语言级的内存模型**，确保在不同的编译器和不同的处理器平台之上，通过禁止特定类型的编译器重排序和处理器重排序，来提供一致的`内存可见性`保证

### 并发编程模型的分类
[top](#catalog)
- 写缓冲区
    - 处理器使用`写缓冲区`保存写入的数据
        - 避免处理器等待向内存写入数据产生的延迟
        - 保证指令流水线持续运行
    - 通过`批处理`的方式刷新`写缓冲区`，**合并**写缓冲区中对**同一内存地址**的**多次写**操作
        - 可以减少**内存总线的占用**
    - `写缓冲区`的问题
        - 各个处理器的`写缓冲区`**只对所在的处理器自身可见**
        - 会影响内存操作的执行顺序
            - 处理器对内存的`读/写操作`的执行顺序，不一定与内存实际发生的`读/写操作`操作顺序一致

### happens-before简介
[top](#catalog)
- JDK5开始，Java使用`JSR-133`内存模型
- `JSR-133`内存模型使用`happens-before`来阐述操作之间的**内存可见性**
    - <label style="color:red">在JMM中，如果一个操作执行的结果需要对另一个操作可见，两个操作之间必须存在`happens-before`关系</label>
        - 两个操作可以是在一个线程内，也可以在不同线程
- `happens-before`规则     ??????????????????????????
    - 程序顺序规则：前操作 `happens-before` any 后操作
        - 一个线程中的每个操作，`happens-before`于该线程中的任意后续操作
    - 监视器锁规则：解锁 `happens-before` 加锁
        - 对一个锁的解锁，`happens-before`于随后对这个锁的加锁
    - volatile变量规则：写 `happens-before` any 读
        - 对一个volatile域的写，`happens-before`于任意后续对这个volatile域的读
    - 传递性：
        ```
        A happens-before B
        B happens-before C
        ===>
        A happens-before C
        ```
- 程序顺序规则
    - JMM不要求操作A一定要在操作B之前执行，**只要前一个操作对后一个操作可见，且前一个操作按顺序排在第二个操作之前**
    - 示例
        - A和B的执行结果不需要对对方可见，而且重排序前后，结果一致，JMM会认为这种重排序并不非法

        ```java
        double pi = 3.14; //A
        double r = 1.0;  //B
        double area = pi * r * r //C
        ```
        ```
        A happens-before B
        B happens-before C
        A happens-before C
        ```

## 重排序
### 数据依赖性
[top](#catalog)
- 如果两个操作访问一个变量，且有一个为`写操作`，此时两个操作之间就存在`数据依赖性`
- 3种数据依赖类型
    - 只考虑单处理器中执行的指令序列、单线程中执行的操作
    - 对于这三种数据依赖，**只要重排序两个操作的执行顺序，程序的执行结果就会被改变**

    |名称|代码示例|说明|
    |-|-|-|
    |写后读|a=1;b=a;|写一个变量之后，再读这个位置|
    |写后写|a=1;a=2;|写一个变量之后，再写这个变量|
    |读后写|a=b;b=1;|读一个变量之后，再写这个变量|

### as-if-serial语义
[top](#catalog)
- `as-if-serial的语义`：
    - 无论如何排序，(单线程)程序的执行结果不能被改变
    - 编译器、runtime、处理器 都必须遵守`as-if-serial`语义
- 如何遵守`as-if-serial语义`
    - 编译器和处理器**不会对存在数据依赖关系的操作进行重排序**
        - 如果操作之间不存在数据依赖关系，这些操作就可能被重排序
    - 示例分析：计算圆面积
        - 代码
            ```java
            double pi = 3.14; //A
            double r = 1.0;  //B
            double area = pi * r * r //C
            ```
        - 依赖关系
            ```
            A-->C
            B-->C
            ```
        - C依赖于A、B，所以C不能被重排到A、B之前；A、B之间没有数据依赖关系，所以可以被重排序
        - 两种可能的执行顺序
            - A-->B-->C
            - B-->A-->C
- `as-if-serial语义`保护了**单线程程序**
    - 实际执行时不一定是按照代码的书写顺序执行的
    - 通过遵守`as-if-serial语义`，重排序不会对程序运行产生干扰
    - 无需担心内存可见性问题 ??????????????

### 重排序对多线程的影响
[top](#catalog)
- 示例
    ```java
    class ReorderExample {
        int a = 0;
        boolean flag = false;
        public void writer(){
            a = 1;          //1
            flag = true;    //2
        }
        public void reader(){
            if (flag) {         //3
                int i = a*a;    //4
            }
        }
    }
    ```
- 假设有两线程A和B，A首先执行`write()`,B执行`reader()`方法，但是**操作4不一定能看到共享变量a的写入**
    - 在`writer()`中，操作1、操作2没有数据依赖关系，编译器可以对这两个操作进行重排序
        - 时序图????????????
    - 操作3和操作4重排序
        - 时序图 ?????????????
        - 操作3和操作4存在控制依赖
            - 当代码中存在控制依赖性时，会影响指令序列执行的并行度
            - 编译器和处理器会采用`猜测执行`来克服相关性对并行度的影响
                - 执行线程B的处理器可以提前读取并计算`a*a`，然后把计算结果临时保存到一个名为重排序缓存(Reorder Buffer ROB)的硬件缓存中
                - 当操作3的条件为真时，就把该计算结果写入变量i中
            - **单线程中，对存在控制依赖的操作重排序不会改变执行结果**
            - **多线程中，对存在控制依赖的操作重排序可能会改变执行结果**

## 顺序一致性
### 数据竞争与顺序一致性
[top](#catalog)
- 当数据没有正确同步时，就可能会存在`数据竞争`
- JMM规范对`数据竞争`的定义
    - 在线程A写一个变量，在线程B读同一个变量，**写和读没有通过同步来排序**
- JMM对正确同步的多线程程序的内存一致性保证
    - 如果程序是正确同步的，程序将具有顺序一致性
        - 即程序的执行结果与程序在顺序一致性内存模型中的执行结果相同

- JMM不保证对64位的long型和double型变量的写操作具有原子性，而顺序一致性模型保证对所有的内存读/写操作都具有原子性
    - 在计算机中，数据通过`总线事务`在处理器和内存之间传递
    - `总线事务`
        - 读事务
            - 数据: 内存--->处理器
        - 写事务
            - 数据: 处理器--->内存
        - 每个事务会读写内存中一个或多个物理上连续的字
        - **总线会试图并发使用总线事务**
        - 在一个处理器执行总线事务期间，总线会禁止其他的处理器和IO设备执行内存的读/写
            - 即同一时间只能有一个处理器可以访问内存
            - 保证了单个总线事务中的内存读写操作的原子性
    - 在32位处理器上，如果要求对64位数据的写操作具有原子性，会有比较大的开销
        - 对于32为处理器，Java语言规范**鼓励但不强求**JVM对64位long型和double型变量的写操作具有原子性
        - JVM在这种处理器上运行时，可能会把一个64位long/double型变量的写操作拆分为**两个32为的写操作**来执行
            - 两个32位的写操作可能会被分配到不同的总线事务中执行
            - 此时对这个64位变量的写操作将不具有原子性
    - 从`JSR-133`内存模型开始，仅允许写操作将64位变量拆分成两个32位写操作，但是读操作必须在单个读事务中进行，来保证原子性

## volatile的内存语义
[top](#catalog)
- <label style="color:red">通过定制的重排序规则和内存屏障，来保证volatile变量的原子性、内存可见性</label>
### volatile的特性
[top](#catalog)
- `volatile`的特性
    - **可见性：对一个`volatile`变量的读，总能看到对这个变量最后的写入**
    - **原子性：对任意单个`volatile`变量的读/写具有原子性**
        - 类似于`volatile++`这种**复合操作**，**不具有原子性**

- 可以将`volatile`看作使用同一个锁对单个读/写操作做了同步
- 示例
    - 使用`volatile`
        ```java
        class VolatileFeaturesExample {
            volatile long v1 = 0L;
            public void set(long l){
                v1 = l;
            }
            public void getAndIncrement(){
                v1++;
            }
            public long get(){
                return v1;
            }
        }
        ```
    - 使用`synchronized`来替换
        ```java
        class VolatileFeaturesExample {
            long v1 = 0L;
            public synchronized void set(long l){
                v1 = l;
            }
            public void getAndIncrement(){
                // 复合操作无法保证原子性
                long temp = get();
                temp += 1L;
                set(temp);
            }
            public synchronized long get(){
                return v1;
            }
        }
        ```
### volatile写-读建立的happens-before关系
[top](#catalog)
- 从`JSR-133`开始，`volatile写-读`可以实现**线程之间的通信**
- 在内存语义上，`volatile写-读`与`锁的释放-获取`有相同的内存效果
- 示例
    - 假设：线程A先执行`write()`,线程B再执行`reade()`
    - `happens-before`规则
        - 基本规则
            - 程序顺序规则
                - 1-->2
                - 3-->4
            - volatile规则
                - 2-->3
            - 传递性
                - 1-->4
        - 规则的组合
            ```
            1-->2-->3-->4
            1---------->4
            ```
        - 根据规则的组合，**1和4具有happens-before关系**，所以`1`写入的值，**在读取`volatile`变量之后**，立即在`4`可见
    ```java
    class VolatileExample {
        int a = 0;
        volatile boolean flag = false;
        public void writer() {
            a = 1;      //1
            flag = true;//2
        }
        public void reader() {
            if (flag) {     //3
                int i = a;  //4
            }
        }
    }
    ```

### volatile写-读的内存语义
[top](#catalog)
- 写： 当写一个`volatile`变量时，**JMM会把该线程对应的本地内存中的共享变量刷新到主内存**
    - 导致其他处理器的本地内存都会刷新
- 读：当读一个`volatile`变量时，JMM会把该线程的本地内存置为无效，线程将从主内存中读取变量
- 内存语义
    - 线程A写`volatile变量`：线程A向接下来将要读这个`volatile变量`的某个线程发出了消息(共享变量被修改的消息)
    - 线程B读`volatile变量`：线程B接收了之前某个线程发出的消息
    - 线程A写`volatile变量`、线程B读`volatile变量`：`写线程`会通过主内存向其他`读线程`发送消息，然后从主内存读取变量

### volatile内存语义的实现
[top](#catalog)
- 为了实现volatile语义，做的处理
    - JMM限制了编译器重排序和处理器重排序

- JMM针对`编译器`制定的`volatile`重排序规则
    - 如果第二个操作是：`volatile写`，则一定不会重排序
    - 如果第一个操作是：`volatile读`，则一定不会重排序
    - `volatile写`-->`volatile读`，不会重排序

    ||第二个操作|第二个操作|第二个操作|
    |-|-|-|-|
    |第一个操作|普通读/写|volatile读|volatile写|
    |普通读/写|||NO|
    |volatile读|NO|NO|NO|
    |volatile写||NO|NO|

- 编译器在生成`字节码`时，会在指令序列中插入`内存屏障`来禁止特定类型的`处理器重排序`
    - 对于编译器，发现一个最优布置来最小化插入屏障的总数几乎不可能，所以JMM采用保守策略
        - 以此来保证在任意处理器平台、任意的程序中都能得到正确的`volatile`语义
    - JMM内存屏障插入规则
        - `StoreStore volatile写 StoreLoad`
            - `StoreStore`保证`volatile写`之前的所有`普通写`操作**对所有处理器可见**
            - 编译器无法判断`volatile写`后面是否是`volatile读`，所以总是在后面添加`StoreLoad`
        - `volatile读 LoadLoad LoadStore`
            - `LoadLoad`保证`volatile读`不与后续的`普通读`重排序
            - `LoadStore`保证`volatile读`不与后续的`普通写`重排序
    - 实际执行时，**只要不改变volatile写-读的内存语义**，编译器可以根据具体情况省略不必要的屏障
    - 不同的处理器有不同的处理器内存模型，内存屏障的插入还可以根据具体的处理器进行优化
        - X86架构下
            - X86架构只会对`写-读`操作重排序，`读-写`、`读-读`、`写-写`不会重排序
            - 在X86架构下，JMM只在`volatile写`后插入一个`StoreLoad`屏障
                - 这导致在X86下`volatile写`比`volatile读`的消耗更大
    - 示例
        - 
            ```java
            class VolatileBarrierExample {
                int a;
                volatile int v1 = 1;
                volatile int v2 = 2;

                void readAndWrite() {
                    int i = v1; //volatile读1
                    int j = v2; //volatile读2
                    a = i + j;    //普通写
                    v1 = i + 1;   //volatile写1
                    v2 = j * 2;   //volatile写2
                }
            }
            ```

        - 实际的优化
            ```
            volatile读1
            LoadLoad        禁止上面的volatile读和下面的volatile读重排序，
                            省略了LoadStore，因为下面的普通写根本不可能越过上面的volatile读
            volatile读2
                            省略了LoadLoad，因为下面根本没有普通读操作
            LoadStore       禁止下面的普通写和上面的volatile读重排序
            普通写
            StoreStore      禁止上面的普通写和下面的volatile写重排序
            volatile写1
                            省略StoreLoad，因为下面有一个volatile写
            StoreStore      禁止上面的volatile和下面的volatile重排序
            volatile写2
            StoreLoad       防止volatile写2与后面可能出现的volatile读/写重排序
            ```

### 为什么要增强volatile的内存语义
[top](#catalog)
- 旧的JMM中，允许**volatile变量与普通变量重排序**
- 旧的JMM中，当1与2没有数据依赖时，可能会被重排序，导致不一定能在4中看到1做的修改
    ```java
    class VolatileExample {
        int a = 0;
        volatile boolean flag = false;
        public void writer() {
            a = 1;      //1
            flag = true;//2
        }
        public void reader() {
            if (flag) {     //3
                int i = a;  //4
            }
        }
    }
    ```
- 通过加强volatile的语义，提供一种比锁更轻量级的线程通信机制
    - `volatile写-读`的语义与`锁的释放-获取`具有相同的内存语义
- volatile只保证单个的volatile变量的读写具有原子性，锁互斥执行特性可以确保整个临界区代码的执行具有原子性
    - 功能上，锁比volatile更强大
    - 在可伸缩性和执行性能上，volatile更具优势

## 锁的内存语义
### 锁的释放-获取建立的happens-before关系
[top](#catalog)
- 锁可以让临界区互斥执行
- 锁可以让释放锁的线程向获取同一个锁的线程发送消息
- 示例
    - 释放锁-获取锁：线程A执行`write()`，线程B执行`reader()`
        ```java
        class MonitorExample {
            int a = 0;
            public synchronized void writer() { //1
                a++;                            //2
            }                                   //3
            public synchronized void reader() { //4
                int i = a;                      //5
                ...
            }                                   //6
        }
        ```
    - `happens-before关系`
        - 程序顺序规则
            ```
            1 --> 2
            2 --> 3
            4 --> 5
            5 --> 6
            ```
        - 监视器锁规则：解锁-->加锁
            ```
            3 --> 4
            ```
        - 传递性
            ```
            2 --> 5
            ```
        - 规则组合
            ```
            1 --> 2 --> 3 --> 4 --> 5 --> 6
                  2 --------------> 5
            ```
    - 在线程A修改该了共享变量，并释放锁，在线程B获取同一个锁之后，将立刻对B可见

### 锁的释放和获取的内存语义
[top](#catalog)
- 当线程释放锁时，**JMM会把该线程对应的本地内存中的共享变量刷新到主内存**
    - 与`volatile写`是相同的
- 当线程获取锁时，JMM会把该线程对应的本地内存置为无效，从而使的被监视器保护的临界区代码必须从主内存中读取共享变量
    - 与`volatile读`是相同的
- 内存语义
    - 线程A`释放锁`：线程A向接下来将要`获取锁`的某个线程发出了消息(共享变量被修改的消息)
    - 线程B`获取锁`：线程B接收了之前某个线程发出的消息
    - 线程A`释放锁`、线程B`获取锁`：`写线程`会通过主内存向其他`读线程`发送消息，然后从主内存读取变量

### 锁的内存语义实现
[top](#catalog)
- `ReentrantLock`的实现依赖于Java同步器框架`AbstractQueuedSynchronizer` (AQS)
    - AQS使用一个整型的`volatile变量`：state，来维护同步状态，它是`ReentrantLock`内存语义实现的关键
    - 继承关系????????????????
- `ReentrantLock`分为`公平锁`和`非公平锁`
- `公平锁`
    - 调用`lock()`时的调用轨迹
        - `ReentrantLock:lock()`
        - `FairSync:lock()`
        - `AbstractQueuedSynchronizer:acquire(int arg)`
        - `ReentrantLock:tryAcquire(int acquires)`, 在这里开始加锁
            - 源码
            ```java
            protected final boolean tryAcquire(int acquires) {
                final Thread current = Thread.currentThread();
                int c = getState(); //读取volatile变量：state
                if (c == 0) {
                    if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires)) {
                        setExclusiveOwnerThread(current);
                        return true;
                    }
                }
                else if (current == getExclusiveOwnerThread()) {
                    int nextc = c + acquires;
                    if (nextc < 0)
                        throw new Error("Maximum lock count exceeded");
                    setState(nextc);
                    return true;
                }
                return false;
            }
            ```
    - 调用`unlock()`
        - `ReentrantLock:unlock()`
        - `AbstractQueuedSynchronizer:release(int arg)`
        - `Sync:tryRelease(int releases)`
            - 源码
                ```java
                protected final boolean tryRelease(int releases) {
                    int c = getState() - releases;
                    if (Thread.currentThread() != getExclusiveOwnerThread())
                        throw new IllegalMonitorStateException();
                    boolean free = false;
                    if (c == 0) {
                        free = true;
                        setExclusiveOwnerThread(null);
                    }
                    setState(c); //释放锁后重载volatile变量
                    return free;
                }
                ```
    - `公平锁`在`释放锁`的最后写`volatile`变量`state`；在`获取锁`时，首先读这个变量
        - `释放锁`的线程，先**写共享变量**，然后`volatile写`，`volatile变量`之前的所有内容对其他处理器可见
        - `获取锁`的线程，读取同一个`volatile变量`，所有共享变量的修改可见

- `非公平锁`
    - 调用`lock()`
        - `ReentrantLock:lock()`
        - `NonfairSync:lock()`
        - `AbstractQueuedSynchronizer:compareAndSetState(int expect, int update)`，CAS，在这里开始加锁
            - CAS同时具有`volatile读`和`volatile写`的语义
            - 源码
                ```java
                protected final boolean compareAndSetState(int expect, int update) {
                    // See below for intrinsics setup to support this
                    return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
                }
                ```
    - 调用`unlock()`
        - 与`公平锁相同`
- 内存语义
    - 释放锁时，最后都要写一个`volatile`变量state
    - 获取锁时
        - 公平锁会读`volatile变量`
        - 非公平锁会用CAS更新volatile变量，这个操作同时具有`volatile读`和`volatile写`语义
- 内存语义的实现方式
    - 利用`volatile变量`的写-读所具有的内存语义
    - 利用CAS附带的`volatile读`和`volatile写`内存语义
            

```java
class ReentranLockExample {
    int a = 0;
    ReentrantLock lock = new ReentrantLock();
    public void writer(){
        lock.lock();
        try{
            a++;
        }finally {
            lock.unlock();
        }
    }

    public void reader(){
        lock.lock();
        try{
            int i = a;
            ...
        } finally {
            lock.unlock();
        }
    }
}
```

### concurrent包的实现
[top](#catalog)
- 由于java的CAS同时具有`volatile读`和`volatile写`的内存语义，所有Java线程之间的通信有4中方式
    - 线程A写`volatile变量`，随后B线程读这个`volatile变量`
    - 线程A写`volatile变量`，随后B线程用CAS更新这个`volatile变量`
    - 线程A用CAS更新一个`volatile变量`，随后线程B用CAS更新这个`volatile`变量
    - 线程A用CAS更新一个`volatile变量`，随后B线程读这个`volatile变量`
- Java的CAS会使用处理器上提供的 高效机器级别的原子指令，这些原子指令以原子方式对内存执行`读-改-写操作`,**这是多处理器实现同步的关键**
- `concurrent`包的实现示意图???????????????????
- volatile变量的读/写和CAS可以实现线程之间的通信，两者的特性整合在一起，就形成了`concurrent`包的基础
- `concurrent`包的通用实现模式
    - 声明共享变量为`volatile`
    - 使用CAS的原子条件更新来实现线程之间的同步
    - 配合`volatile`的读/写和CAS所具有的`volatile读`、`volatile写`的内存语义来实现线程之间的通信

- AQS，非阻塞数据结构和原子变量类，这些`concurrent`包中的基础类都是使用通用模式实现的，`concurrent`包中的高层类又依赖于这些基础类来实现


[top](#catalog)
