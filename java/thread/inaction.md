<span id="catalog"></span>

参考

- https://www.bilibili.com/video/BV1jE411j7uX

### 目录
- [防止CPU占用100%](#防止CPU占用100%)
    - [sleep实现](#sleep实现)
- [任务统筹](#任务统筹)
    - [引入问题--烧水泡茶](#引入问题--烧水泡茶)
    - [任务统筹--基本实现](#任务统筹--基本实现)
- [卖票问题](#卖票问题)
- [转账问题--一个临界区内有两个相同类的对象](#转账问题--一个临界区内有两个相同类的对象)
- [](#)
- [](#)
- [](#)


# 防止CPU占用100%
## sleep实现
[top](#catalog)
- 会产生（单核） CPU 占用100%的情况
    ```java
    while(true){...}
    ```
- 什么时候需要这样的代码？
    
    - 如服务器程序，需要服务器一直运行，来接收请求、返回响应
- 这种状态下的缺点
    1. 没有利用CPU来执行有效的计算
    2. `while(true){...}` 一直处于空转状态，浪费了大量的CPU
- 使用sleep进行改进
    - 改进方法
        ```java
        while(true){
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        ```
    - sleep 时间不需要太长，但是**必须要有sleep**
    - sleep 适用于无需锁同步的场景
- 也可以使用 yield 来让出CPU
        
# 任务统筹
## 引入问题--烧水泡茶
[top](#catalog)
- 每件操作花费的时间
    - 洗水壶 1 分钟
    - 烧开水 15 分钟
    - 洗茶壶 1 分钟
    - 洗茶杯 2 分钟
    - 拿茶叶 1 分钟
- 时间最少的解决方案
    ```
    线程1: 洗水壶 1 分钟 --->> 烧开水 15 分钟 ------------------>> 
                                                              泡茶
    线程2: 洗茶壶 1 分钟 --->> 洗茶杯 2 分钟 --->> 拿茶叶 1 分钟-->>
    ```

## 任务统筹--基本实现
[top](#catalog)
- 参考代码
    
    - [/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t01/T0101.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t01/T0101.java)
    
- 代码内容
    ```java
    public static void main(String[] args) {
        final Thread t1 = new Thread(() -> {
            try {
                log.debug("洗水壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("烧开水");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A");

        final Thread t2 = new Thread(() -> {
            try {
                log.debug("洗茶壶");
                TimeUnit.SECONDS.sleep(1);
                log.debug("洗茶杯");
                TimeUnit.SECONDS.sleep(1);
                log.debug("拿茶叶");
                TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.debug("泡茶");
        }, "B");
    
        t1.start();
        t2.start();
    }
    ```
    
- 这种实现的问题
    1. 模拟的内容是 t2 等 t1 将水烧开，t2泡茶。如何反过来实现: t1 等 t2 拿茶叶，t1泡茶？代码如何适应这两种情况
    2. t1、t2 是分别执行的，如何模拟 t1 将水壶交给 t2 泡茶，或者 t2 将茶叶交给 t1 泡茶?

# 卖票问题

[top](#catalog)

- 问题

    - 参考代码

        - [src/main/java/com/ljs/learn/threadinaction/t02/TicketingOrigin.java](src/main/java/com/ljs/learn/threadinaction/t02/TicketingOrigin.java)

    - 代码内容

        ```java
        @Slf4j(topic = "c.TicketingOrigin")
        public class TicketingOrigin {
            static Random random = new Random();
        
            public static int randomAmount() {
                return random.nextInt(5) + 1;
            }
        
            public static void main(String[] args) throws InterruptedException {
                // 最终需要证明： 卖出的票 + 余票 = 1000
                int totalTicket = 1000;
                int totalPeople = 10000;
                TicketWindow window = new TicketWindow(totalTicket);
                List<Thread> tlist = new ArrayList<>();
        
                // 存储卖了多少票
                Vector<Integer> sellCount = new Vector<>();
                for (int i = 0; i < totalPeople; i++) {
                    Thread t = new Thread(() -> {
                        // 分析竞态条件
                        // 存在对共享资源 window 的多线程操作
                        int count = window.sell((randomAmount()));
                        // sellCount 会被多线程共享，所以使用线程安全的 Vector
                        sellCount.add(count);
                    });
        
                    tlist.add(t);
                    t.start();
                }
        
                // 等待所有线程结束
                for (Thread t : tlist) {
                    t.join();
                }
        
                // 计算卖出了多少票
                log.debug("selled out count: {}", sellCount.stream().mapToInt(c -> c).sum());
                // 计算余票
                log.debug("remainder count: {}", window.getCount());
            }
        }
        
        class TicketWindow {
            private int count;
        
            public TicketWindow(int count) {
                this.count = count;
            }
        
            public int getCount() {
                return count;
            }
        
            // 会操作共享变量 count，会产生临界区
            public int sell(int amount) {
                // 检查余票是否充足，如果不充足则返回 0
                if (this.count >= amount) {
                    this.count -= amount;
        
                    return amount;
                } else {
                    return 0;
                }
            }
        }
        ```

- 改进，为 `TicketWindow.sell` 添加synchronized

    - 参考代码

        - [src/main/java/com/ljs/learn/threadinaction/t02/TicketingImprove.java](src/main/java/com/ljs/learn/threadinaction/t02/TicketingImprove.java)

    - 代码内容

        ```java
        class TicketWindowImprove {
            private int count;
        
            public TicketWindowImprove(int count) {
                this.count = count;
            }
        
            public int getCount() {
                return count;
            }
        
            public synchronized int sell(int amount) {
                // 检查余票是否充足，如果不充足则返回 0
                if (this.count >= amount) {
                    this.count -= amount;
        
                    return amount;
                } else {
                    return 0;
                }
            }
        }
        ```

# 转账问题--一个临界区内有两个相同类的对象

[top](#catalog)

- 问题

    - 参考代码

        - [/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t03/AccountTestImprove.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t03/AccountTestImprove.java)

    - 代码内容

        ```java
        class Account {
            private int money;
        
            public Account(int money) {
                this.money = money;
            }
        
            public int getMoney() {
                return money;
            }
        
            public void setMoney(int money) {
                this.money = money;
            }
        
            // 临界区,内部包含 Account 类的两个对象
            public void transfer(Account target, int amount){
                if (this.money > amount){
                    this.setMoney(this.getMoney() - amount);
                    target.setMoney(target.getMoney() + amount);
                }
            }
        }
        ```

        ```java
        public class AccountTestOrigin {
            static Random random = new Random();
            public static int randomAmount(){
                return random.nextInt(100) + 1;
            }
        
            public static void main(String[] args) throws InterruptedException {
                Account a = new Account(1000);
                Account b = new Account(1000);
                Thread t1 = new Thread(() -> {
                    for (int i = 0; i < 1000; i++) {
                        a.transfer(b, randomAmount());
                    }
                }, "t1");
        
                Thread t2 = new Thread(() -> {
                    for (int i = 0; i < 1000; i++) {
                        b.transfer(a, randomAmount());
                    }
                }, "t2");
        
                t1.start();
                t2.start();
                t1.join();
                t2.join();
        
                // 输出来那个账户的总和，是否为两千
                log.debug("total = " + (a.getMoney() + b.getMoney()));
            }
        }
        
        ```

- 问题改进，在临界区加锁

    - 参考代码

        - [/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t03/AccountTestOrigin.java](/java/mylearn/mythread/src/main/java/com/ljs/learn/threadinaction/t03/AccountTestOrigin.java)

    - 代码内容

        ```java
        class Account2 {
            private int money;
        
            public Account2(int money) {
                this.money = money;
            }
        
            public int getMoney() {
                return money;
            }
        
            public void setMoney(int money) {
                this.money = money;
            }
        
            // 在临界区内加锁。加锁对象为 类
            // 如果加锁对象为 this， 则内部的 target 仍然会产生问题
            // 所以加锁对象为类，每次只能由 当前的 this和target 进入
            // 但是性能会下降，如果有多个对象，但是每次只能有两个进入临界区，会降低性能
            public void transfer(Account2 target, int amount) {
                synchronized (Account2.class) {
                    if (this.money > amount) {
                        this.setMoney(this.getMoney() - amount);
                        target.setMoney(target.getMoney() + amount);
                    }
                }
            }
        }
        ```

        





[top](#catalog)