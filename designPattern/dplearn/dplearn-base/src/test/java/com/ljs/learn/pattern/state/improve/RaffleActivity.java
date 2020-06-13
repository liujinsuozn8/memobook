package com.ljs.learn.pattern.state.improve;

// 抽奖活动
public class RaffleActivity {
    // 表示活动的当前状态。状态会不断变化
    private State state;

    // 奖品数量
    private int count = 0;

    // 包含可变化的几种状态
    private State noRaffleState =  new NoRaffleState(this);
    private State canRaffleState = new CanRaffleState(this);
    private State dispenseState = new DispenseState(this);
    private State dispenseOutState = new DispenseOutState(this);

    // 构造器初始化：奖品数量 和 并初始化为【不能抽奖】的状态
    public RaffleActivity(int count) {
        this.state = getNoRaffleState();
        this.count = count;
    }

    // 扣除积分
    public void deductMoney(){
        state.deductMoney();
    }

    // 是否抽中奖品
    public void raffle(){
        // 如果当前状态是：抽奖成功，则领取奖品
        if (state.raffle()){
            state.dispensePrize();
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    // 每次获取数量时，将数量减1
    public int getCount() {
        int curCount = count;
        count--;
        return curCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getNoRaffleState() {
        return noRaffleState;
    }

    public void setNoRaffleState(State noRaffleState) {
        this.noRaffleState = noRaffleState;
    }

    public State getCanRaffleState() {
        return canRaffleState;
    }

    public void setCanRaffleState(State canRaffleState) {
        this.canRaffleState = canRaffleState;
    }

    public State getDispenseState() {
        return dispenseState;
    }

    public void setDispenseState(State dispenseState) {
        this.dispenseState = dispenseState;
    }

    public State getDispenseOutState() {
        return dispenseOutState;
    }

    public void setDispenseOutState(State dispenseOutState) {
        this.dispenseOutState = dispenseOutState;
    }
}
