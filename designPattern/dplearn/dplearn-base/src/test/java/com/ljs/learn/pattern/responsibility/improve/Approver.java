package com.ljs.learn.pattern.responsibility.improve;

public abstract class Approver {
    // 下一个处理者
    Approver approver;
    // 名字
    String name;

    public Approver(String name) {
        this.name = name;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    // 处理请求的方法，得到一个请求
    // 处理是由子类完成的，需要一个抽象方法
    public abstract void processRequest(PurchaseRequest request);
}
