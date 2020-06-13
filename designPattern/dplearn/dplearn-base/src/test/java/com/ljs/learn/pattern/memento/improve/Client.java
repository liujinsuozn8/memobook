package com.ljs.learn.pattern.memento.improve;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 创建角色
        GameRole role = new GameRole(100, 100);

        // 保存状态对象
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(role.createMemento());

        // 1. 显示状态
        role.display();

        // 2. 修改状态，并显示状态
        role.setAtk(50);
        role.setAtk(60);
        role.display();

        // 3. 恢复并显示状态
        role.recover(caretaker.getMemento());
        role.display();
    }
}
