package com.ljs.learn.principle.interfaceSegregation.improve;

public class Segregation2 {
}

interface Interface1{
    void method1();
}

interface Interface2{
    void method2();
    void method3();
}

interface Interface3{
    void method4();
    void method5();
}

class B implements Interface1,Interface2 {

    @Override
    public void method1() {
        System.out.println("B implement method1");
    }

    @Override
    public void method2() {
        System.out.println("B implement method2");
    }

    @Override
    public void method3() {
        System.out.println("B implement method3");
    }

}

class D implements Interface1,Interface3 {

    @Override
    public void method1() {
        System.out.println("D implement method1");
    }

    @Override
    public void method4() {
        System.out.println("D implement method4");
    }

    @Override
    public void method5() {
        System.out.println("D implement method5");
    }
}

// A 通过接口Interface1 依赖 B，但是只是用了 方法1、2、3
class A {
    public void dependency1(Interface1 i){
        i.method1();
    }
    public void dependency2(Interface2 i){
        i.method2();
    }
    public void dependency3(Interface2 i){
        i.method3();
    }
}

// C 通过接口Interface1 依赖 D，但是只是用了 方法1、4、5
class C {
    public void dependency1(Interface1 i){
        i.method1();
    }
    public void dependency4(Interface3 i){
        i.method4();
    }
    public void dependency5(Interface3 i){
        i.method5();
    }
}