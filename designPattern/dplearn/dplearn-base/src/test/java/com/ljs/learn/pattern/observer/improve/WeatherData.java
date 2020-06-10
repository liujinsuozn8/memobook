package com.ljs.learn.pattern.observer.improve;

import java.util.ArrayList;

/*
    保存最新的天气情况信息
    包含观察者，使用ArrayList管理
    当数据更新时，调用 CurrentConditions.update方法，推送数据

    使用时的流程
        1. 创建 WeatherData
        2. 注册 Observer
        3. 调用 setData() 设置数据
        4. 内部调用 notifyObservers() 通知观察者
 */
public class WeatherData implements Subject {
    private float temperature;
    private float pressure;
    private float humidity;
    private ArrayList<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<Observer>();
    }

    // 设置最新的数据，并推送给 所有的 Observer
    public void setData(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;

        // 将最新的数据推送给每个 Observer
        notifyObservers();
    }

    // 注册观察者
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    // 删除观察者
    @Override
    public void removeObserver(Observer o) {
        if (observers.contains(o)){
            observers.remove(o);
        }
    }

    // 遍历并通知所有的观察者
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, pressure, humidity);
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }
}
