package com.ljs.learn.partten.singleton.type8;

public class SingletonType08Test {
    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        Singleton instance2 = Singleton.INSTANCE;

        System.out.println(instance == instance2);

        instance.work();
    }
}

// 通过枚举来完成单例模式
enum Singleton{
    INSTANCE;

    public void work(){
        System.out.println("singleton instance is working");
    }
}