package com.ljs.learn.pattern.memento.priciple;

public class Memento {
    private String state;

    // 通过构造器保存状态
    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
