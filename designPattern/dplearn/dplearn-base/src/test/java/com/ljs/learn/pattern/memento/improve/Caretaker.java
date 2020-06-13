package com.ljs.learn.pattern.memento.improve;

import java.util.ArrayList;
import java.util.HashMap;

public class Caretaker {
    // 只保存一次状态
    private Memento memento;
    // 保存多次状态
    // private ArrayList<Memento> mementos;
    // 保存多个角色的多个状态
    // private HashMap<String, ArrayList<Memento>> rolesMementos;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
