package com.ljs.learn.principle.demeter.improve;

import java.util.ArrayList;
import java.util.List;

public class Demeter {
    public static void main(String[] args) {
        SchooleManager schooleManager = new SchooleManager();

        // 输出学校和学院员工信息
        schooleManager.printAllEmployee(new CollegeManager());
    }
}

//学校员工类
class Employee{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

//学院员工
class CollegeEmployee{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

// 学院员工管理类
class CollegeManager {
    //返回所有员工
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {//添加10个员工
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("collegeID=" + i);
            list.add(emp);
        }
        return list;
    }

    //输出学院员工
    public void printAllEmployee() {
        List<CollegeEmployee> list1 = this.getAllEmployee();
        System.out.println("----------CollegeEmployee-----------");
        for (CollegeEmployee e : list1) {
            System.out.println(e.getId());
        }
    }

}

// 学校员工管理类
// 直接朋友类：Employee，CollegeManager
// 非直接朋友：CollegeEmployee，
// CollegeEmployee以局部变量的形式出现在printAllEmployee方法中，违背了迪米特法则
class SchooleManager{
    // 返回所有总部员工
    public List<Employee> getAllEmployee(){
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee emp = new Employee();
            emp.setId("schoolID="+i);
            list.add(emp);
        }
        return list;
    }

    // 输出学校总部和学院员工学校的方法
    void printAllEmployee(CollegeManager cm){
        //输出学院员工
        // 使用迪米特法则将逻辑封装在被依赖类中
        cm.printAllEmployee();

        //输出学校员工
        List<Employee> list2 = this.getAllEmployee();
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}
