package com.ljs.learn.pattern.responsibility.improve;

public class SchoolMasterApprover extends Approver {
    public SchoolMasterApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 30000){
            System.out.println("request id = " + request.getId() + ", processed by: " + name);
        } else {
            approver.processRequest(request);
        }
    }
}
