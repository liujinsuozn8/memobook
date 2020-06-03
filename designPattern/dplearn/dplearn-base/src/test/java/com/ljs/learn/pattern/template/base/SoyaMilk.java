package com.ljs.learn.pattern.template.base;

// 抽象类，表示豆浆
public abstract class SoyaMilk {
    // 模版方法
    public final void make(){
        select();
        add();
        soak();
        beat();
    }

    // 选材料
    public void select(){
        System.out.println("SoyaMilk select");
    }

    // 添加配料
    public abstract void add();

    // 浸泡
    public void soak(){
        System.out.println("SoyaMilk soak");
    }

    // 打磨
    public void beat(){
        System.out.println("SoyaMilk beat");
    }
}
