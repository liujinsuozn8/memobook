package com.ljs.learn.pattern.responsibility.improve;

public class ViceSchoolMasterApprover extends Approver {
    public ViceSchoolMasterApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getPrice() > 10000 && request.getPrice() <= 30000){
            System.out.println("request id = " + request.getId() + ", processed by: " + name);
        } else {
            approver.processRequest(request);
        }
    }
}
