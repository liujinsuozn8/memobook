package com.ljs.learn.pattern.state.improve;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 创建活动
        RaffleActivity activity = new RaffleActivity(1);

        // 执行抽奖
        for (int i = 0; i < 30; i++) {
            System.out.println("-----第" + (i+1) + "次抽奖---");
            activity.deductMoney();
            activity.raffle();
        }
    }
}
