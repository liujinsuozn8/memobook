package com.ljs.learn.pattern.state.improve;

// 奖品发完状态
public class DispenseOutState implements State{
    private RaffleActivity activity;

    public DispenseOutState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("奖品已发完，活动结束");
    }

    @Override
    public boolean raffle() {
        System.out.println("奖品已发完，活动结束");
        return false;
    }

    @Override
    public void dispensePrize() {
        System.out.println("奖品已发完，活动结束");
    }
}
