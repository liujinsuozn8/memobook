package com.ljs.learn.pattern.adapter.classAdapter.chargePhone;

public class Phone {
    public void charging(IVoltagge5V iVoltagge5V){
        if (iVoltagge5V.output5V() == 5)
            System.out.println("Voltagge = 5V,charging");
        else
            System.out.println("Voltagge != 5V,can't charge");
    }
}
