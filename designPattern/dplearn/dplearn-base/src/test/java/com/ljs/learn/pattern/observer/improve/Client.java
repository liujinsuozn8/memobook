package com.ljs.learn.pattern.observer.improve;

import org.junit.Test;

import java.util.Observable;

public class Client {
    @Test
    public void test01(){
        WeatherData weatherData = new WeatherData();

        // 创建观察者
        CurrentCondition c1 = new CurrentCondition();
        Baidu c2 = new Baidu();

        // 注册观察者
        weatherData.registerObserver(c1);
        weatherData.registerObserver(c2);

        weatherData.setData(10,20,30);
        weatherData.setData(300,200,1000);
    }
}
