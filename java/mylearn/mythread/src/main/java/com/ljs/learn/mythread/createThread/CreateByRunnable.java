package com.ljs.learn.mythread.createThread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.CreateByRunnable")
public class CreateByRunnable {
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
}
