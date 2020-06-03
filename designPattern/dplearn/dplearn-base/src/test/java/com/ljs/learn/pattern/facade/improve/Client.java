package com.ljs.learn.pattern.facade.improve;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        HomeFacade homeFacade = new HomeFacade();
        homeFacade.ready();
        homeFacade.play();
        homeFacade.end();
    }
}
