package com.ljs.mavenlearn;

import com.ljs.mavenlearn.Hello;

// 测试依赖范围，但是并没有使用
// 由于test范围的依赖对于main程序不可见，所以无法编译
// import org.junit.Test;

public class HelloFriend {
    public String sayHelloToFriend(String name){
        Hello hello = new Hello(); // 调用其他Maven工程中的类com.ljs.mavenlearn.Hello
        String str = hello.sayHello(name)+" I am "+this.getMyName();
        System.out.println(str);
        return str;
    }
    public String getMyName(){
        return "John";
    }
}