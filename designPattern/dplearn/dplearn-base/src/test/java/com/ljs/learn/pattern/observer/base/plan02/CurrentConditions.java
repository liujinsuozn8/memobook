package com.ljs.learn.pattern.observer.base.plan02;

// 显示天气信息状况，相当于气象站/第三方
public class CurrentConditions {
    private float temperature;
    private float pressure;
    private float humidity;

    // 由 WeatherData 对象来调用，使用推送模式
    public void update(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    public void display(){
        System.out.println("temperature = " + temperature);
        System.out.println("pressure = " + pressure);
        System.out.println("humidity = " + humidity);
    }
}
