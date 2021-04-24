package com.ljs.learn.myspringannotation.extension.ex03;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Ex03Service {
    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("Ex03Service listener event = " + event);
    }
}
