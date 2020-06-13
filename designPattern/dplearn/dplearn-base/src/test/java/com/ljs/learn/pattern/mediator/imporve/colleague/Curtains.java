package com.ljs.learn.pattern.mediator.imporve.colleague;

import com.ljs.learn.pattern.mediator.imporve.mediator.Mediator;

public class Curtains extends Colleague {
    public Curtains(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }

    public void upCurtains(){
        System.out.println("upCurtains");
    }
}
