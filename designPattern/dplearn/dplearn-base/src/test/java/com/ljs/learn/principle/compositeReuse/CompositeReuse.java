package com.ljs.learn.principle.compositeReuse;

public class CompositeReuse {
}

class A{
    public void method01(){}
    public void method02(){}
}

// B使用A的方法的几种方式

// 继承
// B和A的耦合性增强
// 如果A增加了其他方法，B也会增加
// 如果A发生了修改，可能会A影响到B
class B1 extends A{
}

// 依赖
// 通过方法参数来利用A
class B2{
    public void method01(A a){
        a.method01();
    }
}

// 聚合
// 通过setter方法来利用A
class B3{
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public void method01(){
        a.method01();
    }
}

// 组合
// 创建B时，同时创建A对象，来利用A
class B4{
    private A a = new A();

    public void method01(){
        a.method01();
    }
}
