package com.ljs.learn.pattern.facade.improve;

// 投影仪
public class Projector {
    private static volatile Projector instance;
    private Projector(){
    }

    public static Projector getInstance(){
        if (instance == null){
            synchronized (Projector.class){
                if (instance == null){
                    instance = new Projector();
                }
            }
        }

        return instance;
    }

    public void on(){
        System.out.println("Projector on");
    }

    public void off(){
        System.out.println("Projector off");
    }
    public void focus(){
        System.out.println("Projector focus");
    }
}
