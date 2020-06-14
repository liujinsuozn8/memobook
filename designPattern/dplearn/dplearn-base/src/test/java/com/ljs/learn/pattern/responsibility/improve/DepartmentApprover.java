package com.ljs.learn.pattern.responsibility.improve;

public class DepartmentApprover extends Approver {
    public DepartmentApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getPrice() <= 5000){
            System.out.println("request id = " + request.getId() + ", processed by: " + name);
        } else {
            approver.processRequest(request);
        }
    }
}
