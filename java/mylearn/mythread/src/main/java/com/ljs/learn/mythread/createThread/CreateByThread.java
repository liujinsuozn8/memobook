package com.ljs.learn.mythread.createThread;

import lombok.extern.slf4j.Slf4j;


@Slf4j(topic = "c.CreateByThread")
public class CreateByThread {
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
}
