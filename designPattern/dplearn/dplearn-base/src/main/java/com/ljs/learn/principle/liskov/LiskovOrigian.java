package com.ljs.learn.principle.liskov;

public class LiskovOrigian {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        // 虽然有继承关系，但是B重写了父类的方法，导致同样的方法结果不同
        a.func1(11,8);
        b.func1(11,8);
    }
}


class A{
    public int func1(int num1, int num2){
        return num1 - num2;
    }
}

class B extends A {
    //B无意中重写了A的方法，并且两个方法的含义完全不同
    public int func1(int a, int b){
        return a + b;
    }

    public int func2(int a, int b){
        return func1(a, b) + 9;
    }
}