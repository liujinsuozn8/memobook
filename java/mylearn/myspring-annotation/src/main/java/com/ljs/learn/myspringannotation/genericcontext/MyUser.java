package com.ljs.learn.myspringannotation.genericcontext;

import org.springframework.context.support.GenericApplicationContext;

public class MyUser {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        // 手动创建并注册
        context.refresh();
        context.registerBean(MyUser.class, ()-> new MyUser());
        // 获取对象
        MyUser bean = context.getBean(MyUser.class);
        System.out.println(bean);
    }
}
