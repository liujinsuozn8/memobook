package com.ljs.learn.pattern.responsibility.improve;

public class CollegeApprover extends Approver {
    public CollegeApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 5000 && request.getPrice() <= 10000){
            System.out.println("request id = " + request.getId() + ", processed by: " + name);
        } else {
            approver.processRequest(request);
        }
    }
}
