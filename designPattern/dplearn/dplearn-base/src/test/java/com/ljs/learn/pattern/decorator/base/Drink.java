package com.ljs.learn.pattern.decorator.base;

public abstract class Drink {
    // 描述
    public String des;
    // 单价
    private float price = 0.0f;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // 计算费用的抽象方法
    public abstract float cost();
}
