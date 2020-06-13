package com.ljs.learn.pattern.memento.priciple;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        // 保存状态
        originator.setState("状态1");
        caretaker.add(originator.saveMemento());
        originator.setState("状态2");
        caretaker.add(originator.saveMemento());
        originator.setState("状态3");
        caretaker.add(originator.saveMemento());

        // 当前状态
        System.out.println(originator.getState());
        // 恢复到状态2
        originator.getStateFromMemento(caretaker.get(1));
        System.out.println(originator.getState());
    }
}
