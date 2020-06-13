package com.ljs.learn.pattern.mediator.imporve.mediator;

import com.ljs.learn.pattern.mediator.imporve.colleague.*;

import java.util.HashMap;

public class ConcreteMediator extends Mediator{
    // 管理所有的同事对象
    public HashMap<String, Colleague> colleagueMap;
    public HashMap<String, String> interMap;

    public ConcreteMediator() {
        colleagueMap = new HashMap<String, Colleague>();
        interMap = new HashMap<String, String>();
    }

    @Override
    public void register(String colleagueName, Colleague colleague) {
        colleagueMap.put(colleagueName, colleague);

        if (colleague instanceof Alarm){
            interMap.put("Alarm", colleagueName);
        } else if (colleague instanceof CoffeeMachine) {
            interMap.put("CoffeeMachine", colleagueName);
        } else if (colleague instanceof TV) {
            interMap.put("TV", colleagueName);
        } else if  (colleague instanceof Curtains){
            interMap.put("Curtains", colleagueName);
        }
    }

    // 具体中介者中的核心方法
    // 根据得到的消息，完成对应的任务
    @Override
    public void getMessage(int stateChange, String colleagueName) {
        if (colleagueMap.get(colleagueName) instanceof Alarm){
            if (stateChange == 0){
                ((CoffeeMachine) (colleagueMap.get(
                        interMap.get("CoffeeMachine")))).startCoffee();

                ((TV) (colleagueMap.get(interMap.get("TV")))).startTV();
            } else if (stateChange == 1){
                ((TV) (colleagueMap.get(interMap.get("TV")))).stopTV();
            }
        } else if (colleagueMap.get(colleagueName) instanceof CoffeeMachine){
            ((Curtains)(colleagueMap.get(interMap.get("Curtains")))).upCurtains();
        } else if (colleagueMap.get(colleagueName) instanceof TV){

        } else if (colleagueMap.get(colleagueName) instanceof Curtains) {

        }

    }

    @Override
    public void sendMessage() {

    }
}
