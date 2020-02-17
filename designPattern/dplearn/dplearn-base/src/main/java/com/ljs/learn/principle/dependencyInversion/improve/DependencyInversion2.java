package com.ljs.learn.principle.dependencyInversion.improve;

public class DependencyInversion2 {
    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        person.receive(new Phone());
        person.receive(new Weixin());
    }
}

// 引入一个接口IReceive，表示接受者
interface IReceive{
    String getInfo();
}
class Email implements IReceive{
    public String getInfo(){
        return "email Info ";
    }
}

class Phone implements IReceive{
    public String getInfo(){
        return "phone Infor";
    }
}

class Weixin implements IReceive{
    public String getInfo(){
        return "weixin Infor";
    }
}

// 完成接受消息的功能
// 方案2
// 引入一个接口IReceive，表示接受者，使Person类与接口IReceive发生依赖
// Email，Wexin等等都属于接受者的范畴，这些类分别实现IReceive接口，来完成依赖的倒转
class Person {
    // 与接口IReceive发生依赖，来接收不同的信息
    public void receive(IReceive receiver){
        System.out.println(receiver.getInfo());
    }
}


