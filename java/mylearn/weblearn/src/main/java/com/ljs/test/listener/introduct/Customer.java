package com.ljs.test.listener.introduct;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class Customer implements HttpSessionBindingListener{
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("Customer.Bound");

        //获取绑定到session中的属性值，检查该属性值是否与当前对象相等
        Object value = event.getValue();
        System.out.println(value == this);

        //获取绑定到session中的属性名
        System.out.println(event.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("Customer.Unbound");
    }
}
