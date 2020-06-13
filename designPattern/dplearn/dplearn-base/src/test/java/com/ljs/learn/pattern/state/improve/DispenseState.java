package com.ljs.learn.pattern.state.improve;

// 发放奖品的状态
public class DispenseState implements State {
    private RaffleActivity activity;

    public DispenseState(RaffleActivity activity) {
        this.activity = activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("积分已扣除，可以抽奖");
    }

    @Override
    public boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }

    // 发放奖品
    @Override
    public void dispensePrize() {
        if (activity.getCount() > 0){
            System.out.println("您中奖了");
            // 将状态设置为不能抽奖
            activity.setState(activity.getNoRaffleState());
        } else {
            System.out.println("奖品已经发送完了");
            // 将状态设置为奖品发送完毕
            activity.setState(activity.getDispenseOutState());
        }
    }
}
