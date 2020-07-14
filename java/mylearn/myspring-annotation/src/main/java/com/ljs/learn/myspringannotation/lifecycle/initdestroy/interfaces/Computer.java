package com.ljs.learn.myspringannotation.lifecycle.initdestroy.interfaces;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// 同时实现初始化、销毁的接口 和 @Bean参数中的方法
public class Computer implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("computer afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("computer destroy");
    }

    public void init(){
        System.out.println("computer init");
    }
    public void end(){
        System.out.println("computer end");
    }
}
