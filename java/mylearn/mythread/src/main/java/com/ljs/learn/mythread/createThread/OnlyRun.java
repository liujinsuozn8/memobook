package com.ljs.learn.mythread.createThread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.OnlyRun")
public class OnlyRun {
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
}
