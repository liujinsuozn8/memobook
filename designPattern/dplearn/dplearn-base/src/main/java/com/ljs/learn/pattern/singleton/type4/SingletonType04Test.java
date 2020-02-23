package com.ljs.learn.pattern.singleton.type3;

public class SingletonType03Test {
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

class Singleton{
    // - 创建步骤
    // 1. 构造器私有化，防止调用方通过`new`来创建实例对象
    private Singleton() {
    }
    // 2. 添加一个**私有的、静态的**成员属性`instance`
    private static Singleton instance;

    // 3. 向外暴露一个静态的公共方法，一般写作：`getInstance()`
    public static Singleton getInstance(){
        // 4. 在`getInstance()`方法中，进行`懒汉式`判断。即当使用到`instance`时，才进行创建
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}
