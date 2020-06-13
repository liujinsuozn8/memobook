package com.ljs.learn.pattern.state.improve;

import java.util.Random;

// 可以抽奖的状态
public class CanRaffleState implements State {
    private RaffleActivity activity;

    public CanRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }

    // 已经扣除积分，进入抽奖状态，不能再次扣除积分
    @Override
    public void deductMoney() {
        System.out.println("积分已扣除，可以抽奖");
    }

    // 执行抽奖。抽奖后，根据抽奖结果，改变状态
    @Override
    public boolean raffle() {
        System.out.println("正在抽奖，请稍等");
        int num = new Random().nextInt(10);
        // 10%的中奖几率
        if (num == 0){
            // 改变状态为发放奖品
            activity.setState(activity.getDispenseState());
            return true;
        } else {
            System.out.println("未中奖");
            // 改变状态为不能抽奖
            activity.setState(activity.getNoRaffleState());
            return false;
        }
    }

    @Override
    public void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}
