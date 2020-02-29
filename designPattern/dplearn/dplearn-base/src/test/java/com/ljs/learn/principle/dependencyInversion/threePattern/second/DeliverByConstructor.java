package com.ljs.learn.principle.dependencyInversion.threePattern.second;

// 依赖关系传递的三种方式和应用案例
// 构造方法传递
public class DeliverByConstructor {
    public static void main(String[] args) {
        // 创建一个电视
        ITV mytv = new MyTV();
        // 创建一个开关
        // 创建时，将电视作为依赖传递到开关中
        IOpenAndClose openAndClose = new OpenAndClose(mytv);

        // 直接调用方法
        openAndClose.open();
    }
}

// 从接口上看，两个接口ITV和IOpenAndClose没有直接的关系
interface ITV {
    void play();
}

interface IOpenAndClose{
    void open();
}

// 在实现类中，将ITV的实现类对象作为私有属性，通过构造方法传递依赖
class OpenAndClose implements IOpenAndClose{
    private ITV tv;

    public OpenAndClose(ITV tv) {
        this.tv = tv;
    }

    // 调用依赖对象的方法
    @Override
    public void open() {
        tv.play();
    }
}

class MyTV implements ITV{

    @Override
    public void play() {
        System.out.println("MyTV is showing");
    }
}