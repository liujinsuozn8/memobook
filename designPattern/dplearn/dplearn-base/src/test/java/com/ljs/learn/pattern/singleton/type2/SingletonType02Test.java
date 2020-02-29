package com.ljs.learn.partten.singleton.type2;

public class SingletonType02Test{
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

// 饿汉式-静态代码块
class Singleton{
    // 创建步骤

    // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
    private Singleton() {

    }

    // 2. 添加一个私有的、静态的、不可变的成员属性instance
    private final static Singleton instance;

    // 3. 在静态代码块中为instance提供实例化对象
    static{
        instance = new Singleton();
    }

    // 4. 向外暴露一个静态的公共方法，一般写作：`getInstance`
    public static Singleton getInstance(){
        return instance;
    }
}
