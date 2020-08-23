package com.ljs.learn.mythread.state;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.SleepAndInterrupt")
public class SleepAndInterrupt {
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
}
