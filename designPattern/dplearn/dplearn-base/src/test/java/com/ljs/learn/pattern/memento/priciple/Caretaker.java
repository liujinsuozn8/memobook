package com.ljs.learn.pattern.memento.priciple;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();

    // 保存备忘录对象
    public void add(Memento memento){
        mementoList.add(memento);
    }

    // 获取第n个Originator的备忘录镀锡
    public Memento get(int index){
        return mementoList.get(index);
    }
}
