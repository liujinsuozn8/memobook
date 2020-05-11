package com.ljs.learn.myspringboot.service.mission;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
    // 设置定时任务
    @Scheduled(cron="2 * * * * 0-7")
    public void hello(){
        System.out.println("hello running");
    }
}
