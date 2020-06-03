package com.ljs.learn.pattern.facade.improve;

// 灯光
public class TheaterLight {
    private static volatile TheaterLight instance;
    private TheaterLight(){
    }

    public static TheaterLight getInstance(){
        if (instance == null){
            synchronized (TheaterLight.class){
                if (instance == null){
                    instance = new TheaterLight();
                }
            }
        }

        return instance;
    }

    public void on(){
        System.out.println("TheaterLight on");
    }

    public void off(){
        System.out.println("TheaterLight off");
    }

    public void dim(){
        System.out.println("TheaterLight dim");
    }

    public void bright(){
        System.out.println("TheaterLight bright");
    }
}
