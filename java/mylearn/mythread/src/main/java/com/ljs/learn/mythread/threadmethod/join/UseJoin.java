package com.ljs.learn.mythread.threadmethod.join;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.NoJoin")
public class UseJoin {
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        log.debug("start");

        // 任务1: 暂停1s，然后修改 count
        Thread t1 = new Thread(()->{
            log.debug("start");
            try {
                Thread.sleep(1000);
                count = 10;
                log.debug("count = " + count);
                log.debug("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 任务2: 线程启动后，直接修改 count
        Thread t2 = new Thread(()->{
            log.debug("start");
            try {
                Thread.sleep(2000);
                count = 20;
                log.debug("count = " + count);
                log.debug("end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 3. 启动线程
        t1.start();
        t2.start();
        /*
            4. 先等待 t2 结束，在等待 t1 结束
             因为 t1 比 t2 快，所以会得到:

             count = 10  <<<<< t1 的修改
             count = 20  <<<<< t2 的修改
        */
        t2.join();
        t1.join();// 此时 t1 已经执行完了


        // 7. 最终输出 t2 中的赋值结果: count = 20
        log.debug("count = " + count);
        log.debug("end");
    }
}
