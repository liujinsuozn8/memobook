package com.ljs.learn.reflect.structure;

import java.io.Serializable;

public class Creature<T> implements Serializable {
    private char gender;
    public  double weight;

    private void breath(){
        System.out.println("is breathing");
    }

    public void eat(){
        System.out.println("is eatting");
    }
}
