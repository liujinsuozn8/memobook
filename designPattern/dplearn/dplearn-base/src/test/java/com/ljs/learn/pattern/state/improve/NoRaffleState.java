package com.ljs.learn.pattern.state.improve;

// 不能抽奖状态
public class NoRaffleState implements State {
    private RaffleActivity activity;

    public NoRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    // 当前状态可以扣积分，将状态设置为可以抽奖状态
    @Override
    public void deductMoney() {
        System.out.println("扣除50积分，可以抽奖");
        activity.setState(activity.getCanRaffleState());
    }

    // 当前状态不能抽奖
    @Override
    public boolean raffle() {
        System.out.println("未扣积分，不能抽奖");
        return false;
    }

    // 当前状态不能发放奖品
    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}
