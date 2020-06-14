package com.ljs.learn.pattern.responsibility.improve;

import com.ljs.learn.pattern.iterator.base.Department;
import org.junit.Test;

public class Client {
    @Test
    public void test01() {
        // 1. 创建一个请求
        PurchaseRequest request = new PurchaseRequest(1, 35000, 1);

        // 2. 创建各级审批者
        Approver department = new DepartmentApprover("Department A");
        Approver college = new CollegeApprover("College A");
        Approver viceSchoolMaster = new ViceSchoolMasterApprover("ViceSchoolMaster A");
        Approver schoolMaster = new SchoolMasterApprover("SchoolMaster A");

        // 3. 将审批着组成责任链
        // 如果希望从任何一个级别开始都能够处理，则需要处理人构成环形
        department.setApprover(college);
        college.setApprover(viceSchoolMaster);
        viceSchoolMaster.setApprover(schoolMaster);
        schoolMaster.setApprover(department);

        // 4. 处理请求
        department.processRequest(request);
    }
}
