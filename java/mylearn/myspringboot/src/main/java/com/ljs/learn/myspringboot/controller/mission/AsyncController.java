package com.ljs.learn.myspringboot.controller.mission;

import com.ljs.learn.myspringboot.service.mission.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAsync
@RestController
public class AsyncController {
    @Autowired
    private AsyncService service;

    @GetMapping("/async/hello")
    public String hello(){
        service.hello();
        return "end";
    }
}
