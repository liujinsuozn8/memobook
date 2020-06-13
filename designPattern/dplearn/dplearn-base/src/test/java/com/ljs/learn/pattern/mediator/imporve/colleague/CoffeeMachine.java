package com.ljs.learn.pattern.mediator.imporve.colleague;

import com.ljs.learn.pattern.mediator.imporve.mediator.Mediator;

public class CoffeeMachine extends Colleague {
    public CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }

    public void startCoffee(){
        System.out.println("CoffeeMachine startCoffee");
    }
    public void finishCoffee(){
        sendMessage(0);
    }
}
