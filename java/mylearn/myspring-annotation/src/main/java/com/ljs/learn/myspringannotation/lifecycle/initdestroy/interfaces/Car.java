package com.ljs.learn.myspringannotation.lifecycle.initdestroy.interfaces;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// 只实现初始化和销毁的接口
public class Car implements InitializingBean, DisposableBean {
    // InitializingBean 接口实现
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("car afterPropertiesSet");
    }

    // DisposableBean 接口实现
    @Override
    public void destroy() throws Exception {
        System.out.println("car DisposableBean destroy");
    }
}
