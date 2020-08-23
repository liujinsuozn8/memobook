package com.ljs.learn.mythread.createThread;

import ch.qos.logback.core.ConsoleAppender;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.CreateByFutureTask")
public class CreateByFutureTask {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 创建任务
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(2000); // 延迟返回结果
                return 100;
            }
        });

        // 2. 创建线程执行任务
        Thread t = new Thread(task);
        t.start();

        // 3. 获取线程返回的数据
        // get() 方法 阻塞当前线程，并等待结果返回
        Integer result = task.get();
        log.debug("result = {}", result);
    }
}
