package com.ljs.learn.mythread.createThread;

import lombok.extern.slf4j.Slf4j;

// 使用lambda表达式简化
@Slf4j(topic="c.CreateByLambda")
public class CreateByLambda {
    public static void main(String[] args) {
        Runnable r = () -> log.debug("lambda test");
        Thread t = new Thread(r);
        t.start();
        log.debug("main log");
    }
}
