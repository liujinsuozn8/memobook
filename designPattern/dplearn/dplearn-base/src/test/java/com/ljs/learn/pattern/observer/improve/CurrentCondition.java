package com.ljs.learn.pattern.observer.improve;

public class CurrentCondition implements Observer {
    private float temperature;
    private float pressure;
    private float humidity;

    // 由 WeatherData 对象来调用，使用推送模式
    @Override
    public void update(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    public void display(){
        System.out.println("CurrentCondition temperature = " + temperature);
        System.out.println("CurrentCondition pressure = " + pressure);
        System.out.println("CurrentCondition humidity = " + humidity);
    }
}
