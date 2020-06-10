package com.ljs.learn.pattern.observer.base.plan02;

// 保存最新的天气情况信息
// 包含一个 CurrentConditions 对象，作为接入方，可以接收数据
// 当数据更新时，调用 CurrentConditions.update方法，推送数据
public class WeatherData {
    private float temperature;
    private float pressure;
    private float humidity;
    private CurrentConditions currentConditions;

    public WeatherData(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }

    // 将最新的数据推送给 CurrentConditions
    public void dataChange(){
        currentConditions.update(getTemperature(), getPressure(), getHumidity());
    }

    public void setData(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        // 将最新的数据推送给 CurrentConditions
        dataChange();
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
