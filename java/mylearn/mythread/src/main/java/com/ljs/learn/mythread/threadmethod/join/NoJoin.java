package com.ljs.learn.mythread.threadmethod.join;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.NoJoin")
public class NoJoin {
    static int count = 0;
    public static void main(String[] args) {
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
            count = 20;
            log.debug("count = " + count);
            log.debug("end");
        });

        // 3. 启动线程，检查 count 是否被修改
        t1.start();
        t2.start();
        log.debug("count = " + count);  // 一直输出 count=0
        log.debug("end");
    }
}
