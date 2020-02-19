package com.ljs.learn.principle.liskov;

public class LiskovImprove {
    public static void main(String[] args) {
        A2 a = new A2();
        B2 b = new B2();

        // A和B通过父类Base进行了解耦，在调用时，各方法的意义更加明确
        a.func1(11,8);
        b.func1(11,8);

        // 如果在B中仍然想使用A的方法，可以通过组合关系来调用
        b.func3(11, 8);
    }
}

// 构造更基础的类，来降低耦合
abstract class Base{
    public abstract int func1(int num1, int num2);
}

class A2 extends Base{
    public int func1(int num1, int num2){
        return num1 - num2;
    }
}

// 在B中，与A使用组合关系，并通过A来实现部分功能
class B2 extends Base {
    private A2 a = new A2();
    //B无意中重写了A的方法，并且两个方法的含义完全不同
    public int func1(int a, int b){
        return a + b;
    }

    public int func2(int a, int b){
        return func1(a, b) + 9;
    }

    //使用A的方法来实现功能
    public int func3(int a, int b){
        return this.a.func1(a, b);
    }
}