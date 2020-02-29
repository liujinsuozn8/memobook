package com.ljs.learn.principle.dependencyInversion.origian;

public class DependencyInversion1 {
    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
        person.receive(new Phone());
    }
}

class Email{
    public String getInfo(){
        return "email Info ";
    }
}

class Phone{
    public String getInfo(){
        return "phone Infor";
    }
}

// 完成接受消息的功能
// 方案1
// 方案1的问题：每当新增信息源时，都需要新增一个对应类，并且在Person中也要增加相应的接收方法
// 方案2的解决方案：
//      引入一个接口IReceive，表示接受者，使Person类与接口IReceive发生依赖
//      Email，Wexin等等都属于接受者的范畴，这些类分别实现IReceive接口，来完成依赖的倒转
class Person {
    // 分别接收不同信息
    public void receive(Email email){
        System.out.println(email.getInfo());
    }

    public void receive(Phone phone){
        System.out.println(phone.getInfo());
    }
}


