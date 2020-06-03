package com.ljs.learn.pattern.facade.improve;

// 爆米花机
public class Popcorn {
    private static volatile Popcorn instance;
    private Popcorn(){
    }

    public static Popcorn getInstance(){
        if (instance == null){
            synchronized (Popcorn.class){
                if (instance == null){
                    instance = new Popcorn();
                }
            }
        }

        return instance;
    }

    public void on(){
        System.out.println("popcorn on");
    }

    public void off(){
        System.out.println("popcorn off");
    }
    public void pop(){
        System.out.println("popcorn is poping");
    }
}
