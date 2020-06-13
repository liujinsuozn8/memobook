package com.ljs.learn.pattern.mediator.imporve;

import com.ljs.learn.pattern.mediator.imporve.colleague.Alarm;
import com.ljs.learn.pattern.mediator.imporve.colleague.CoffeeMachine;
import com.ljs.learn.pattern.mediator.imporve.colleague.Curtains;
import com.ljs.learn.pattern.mediator.imporve.colleague.TV;
import com.ljs.learn.pattern.mediator.imporve.mediator.ConcreteMediator;
import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 1. 创建 中介者
        ConcreteMediator mediator = new ConcreteMediator();
        // 创建 Colleague 并注册到中介者
        Alarm alarm = new Alarm(mediator, "alarm");
        CoffeeMachine coffeeMachine = new CoffeeMachine(mediator, "coffeeMachine");
        Curtains curtains = new Curtains(mediator, "curtains");
        TV tv = new TV(mediator, "TV");

        // 3. 向中介者发送消息，并执行具体任务
        alarm.sendAlarm(0);
        coffeeMachine.finishCoffee();
        alarm.sendAlarm(1);
    }
}
