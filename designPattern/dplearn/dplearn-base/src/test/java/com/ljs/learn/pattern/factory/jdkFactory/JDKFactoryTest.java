package com.ljs.learn.pattern.factory.jdkFactory;

import org.junit.Test;

import java.util.Calendar;

public class JDKFactoryTest {
    @Test
    public void test01(){
        Calendar cal = Calendar.getInstance();

        System.out.println("year="+cal.get(Calendar.YEAR));
        System.out.println("month="+cal.get(Calendar.MONTH)+1);
        System.out.println("day="+cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("hour="+cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("minute="+cal.get(Calendar.MINUTE));
        System.out.println("second="+cal.get(Calendar.SECOND));
    }
}
