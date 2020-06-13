package com.ljs.learn.pattern.memento.improve;

public class Memento {
    private int atk;
    private int def;

    public Memento(int atk, int def) {
        this.atk = atk;
        this.def = def;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    // public void setAtk(int atk) {
    //     this.atk = atk;
    // }

    // public void setDef(int def) {
    //     this.def = def;
    // }
}
