package com.ljs.learn.pattern.memento.priciple;

public class Originator {
    // 保存状态信息
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // 通过方法保存一个状态对象
    public Memento saveMemento(){
        return new Memento(state);
    }

    // 通过备忘录对象恢复状态
    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
