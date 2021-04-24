package com.ljs.learn.myspringannotation.extension.ex03;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ljs.learn.myspringannotation.extension.ex03")
public class Ex03Config {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Ex03Config.class);
        context.publishEvent(new ApplicationEvent("publish self event") {});
        context.close();

        // event = org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@799f7e29, started on Thu Apr 22 22:25:43 CST 2021]
        // event = com.ljs.learn.myspringannotation.extension.ex03.Ex03Config$1[source=publish self event]
        // event = org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@799f7e29, started on Thu Apr 22 22:25:43 CST 2021]

    }
}
