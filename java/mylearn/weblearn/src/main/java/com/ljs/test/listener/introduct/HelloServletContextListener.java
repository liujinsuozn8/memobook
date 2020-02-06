package com.ljs.test.listener.introduct;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HelloServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("HelloServletContextListener.contextInitialized...");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("HelloServletContextListener.contextDestroyed...");
    }
}
