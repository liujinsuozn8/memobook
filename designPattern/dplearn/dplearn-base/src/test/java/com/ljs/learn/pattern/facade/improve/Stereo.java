package com.ljs.learn.pattern.facade.improve;

// 音响
public class Stereo {
    private static volatile Stereo instance;
    private Stereo(){
    }

    public static Stereo getInstance(){
        if (instance == null){
            synchronized (Stereo.class){
                if (instance == null){
                    instance = new Stereo();
                }
            }
        }

        return instance;
    }

    public void on(){
        System.out.println("Stereo on");
    }

    public void off(){
        System.out.println("Stereo off");
    }
}
