package com.ljs.learn.pattern.facade.improve;

// 显示器
public class Screen {
    private static volatile Screen instance;
    private Screen(){
    }

    public static Screen getInstance(){
        if (instance == null){
            synchronized (Screen.class){
                if (instance == null){
                    instance = new Screen();
                }
            }
        }

        return instance;
    }

    public void up(){
        System.out.println("Screen up");
    }

    public void down(){
        System.out.println("Screen down");
    }
}
