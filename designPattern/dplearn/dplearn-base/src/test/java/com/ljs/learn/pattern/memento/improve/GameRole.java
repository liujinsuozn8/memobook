package com.ljs.learn.pattern.memento.improve;

public class GameRole {
    private int atk;
    private int def;

    public GameRole(int atk, int def) {
        this.atk = atk;
        this.def = def;
    }

    // 创建Memento
    public Memento createMemento(){
        return new Memento(atk, def);
    }

    // 使用Memento恢复
    public void recover(Memento memento){
        atk = memento.getAtk();
        def = memento.getDef();
    }

    // 显示当前角色的状态
    public void display(){
        System.out.println("atk=" + atk + ", def=" + def);
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
