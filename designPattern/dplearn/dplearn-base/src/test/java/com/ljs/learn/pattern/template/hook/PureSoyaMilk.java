package com.ljs.learn.pattern.template.hook;

// 纯豆浆
public class PureSoyaMilk extends SoyaMilk {
    // 使用空方法完成继承
    @Override
    public void add() { }

    @Override
    boolean wantAdd() {
        return false;
    }
}
