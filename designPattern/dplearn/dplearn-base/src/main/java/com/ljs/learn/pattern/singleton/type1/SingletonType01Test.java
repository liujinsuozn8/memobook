package com.ljs.learn.pattern.singleton.type1;

public class SingletonType01Test{
    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        // output:true
        System.out.println(instance1 == instance2);

        // 两个实例的hashCode是相同的
        System.out.println("instance1 hashCode = " + instance1.hashCode());
        System.out.println("instance2 hashCode = " + instance2.hashCode());
    }
}

// 饿汉式-静态变量
class Singleton{
    // 创建步骤

    // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
    private Singleton() {

    }

    // 2. 在类的内部实例化一个：私有的、静态的、不可变的 对象
    private final static Singleton instance = new Singleton();

    // 3. 向外暴露一个静态的公共方法，一般写作：`getInstance`
    public static Singleton getInstance(){
        return instance;
    }
}
