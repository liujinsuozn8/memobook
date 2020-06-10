package com.ljs.learn.pattern.observer.base.plan02;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 创建接入方
        CurrentConditions currentConditions = new CurrentConditions();
        WeatherData weatherData = new WeatherData(currentConditions);

        // 更新并推送数据
        weatherData.setData(10, 20, 30);
        weatherData.setData(60, 30, 10);
    }
}
