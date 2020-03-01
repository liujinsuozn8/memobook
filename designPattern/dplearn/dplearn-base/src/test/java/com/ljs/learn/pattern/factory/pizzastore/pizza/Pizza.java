package com.ljs.learn.pattern.factory.pizzastore.pizza;

public abstract class Pizza {
    private String name;

    public Pizza(String name) {
        this.name = name;
    }

    public abstract void prepare();

    public void bake(){
        System.out.println("is baking");
    }

    public void cut(){
        System.out.println("is cutting");
    }

    public void box(){
        System.out.println("is boxing");
    }
}
