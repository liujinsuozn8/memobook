package com.ljs.learn.mythread.threadmethod.sleep;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TimeUnitUsage")
public class TimeUnitUsage {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("sleeping");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    log.debug("interrupt");
                    e.printStackTrace();
                }
            }
        };

        t.start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("main running");
        t.interrupt();
    }
}
