package com.ljs.learn.principle.dependencyInversion.threePattern.first;

// 依赖关系传递的三种方式和应用案例
// 接口传递
public class DeliverByInterface {
    public static void main(String[] args) {
        //创建一个电视机
        ITV mytv = new MyTV();
        //创建一个开关
        IOpenAndClose openAndClose = new OpenAndClose();

        //通过接口将依赖传递到类中
        openAndClose.open(mytv);
    }
}

//电视机接口
interface ITV{
    void play();
}

//电视机开关接口 通过接口来传递依赖
interface IOpenAndClose {
   void open(ITV tv);
}

//电视机开关接口的实现
class OpenAndClose implements IOpenAndClose{

    // 通过接口将依赖传递到了类内部
    @Override
    public void open(ITV tv) {
        tv.play();
    }
}

// 电视机接口
class MyTV implements ITV {

    @Override
    public void play() {
        System.out.println("myTV is showing");
    }
}