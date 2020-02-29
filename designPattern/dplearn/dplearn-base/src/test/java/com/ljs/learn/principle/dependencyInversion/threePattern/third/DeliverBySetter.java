package com.ljs.learn.principle.dependencyInversion.threePattern.third;

// 依赖关系传递的三种方式和应用案例
// setter方式传递
public class DeliverBySetter {
    public static void main(String[] args) {
        //创建一个电视机
        ITV mytv = new MyTV();
        //创建一个开关
        IOpenAndClose openAndClose = new OpenAndClose();

        //通过setter将依赖传递到开关中
        openAndClose.setTv(mytv);
        //执行fangf
        openAndClose.open();
    }
}

interface ITV {
    void play();
}

interface IOpenAndClose{
    void open();
    void setTv(ITV tv);
}

// 在实现类中，将ITV的实现类对象作为私有属性，通过setter方法传递依赖
class OpenAndClose implements IOpenAndClose{
    private ITV tv;

    public void setTv(ITV tv) {
        this.tv = tv;
    }

    @Override
    public void open() {
        tv.play();
    }
}

class MyTV implements ITV {

    @Override
    public void play() {
        System.out.println("MyTV is showing");
    }
}
