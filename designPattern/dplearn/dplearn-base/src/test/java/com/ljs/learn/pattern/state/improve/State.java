package com.ljs.learn.pattern.state.improve;

// 抽象状态
public interface State {
    // 扣除50积分
    void deductMoney();

    // 是否抽中奖品
    boolean raffle();

    // 发放奖品
    void dispensePrize();
}
