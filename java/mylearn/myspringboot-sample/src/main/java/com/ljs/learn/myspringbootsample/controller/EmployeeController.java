package com.ljs.learn.myspringbootsample.controller;

import com.ljs.learn.myspringbootsample.dao.DepartmentDao;
import com.ljs.learn.myspringbootsample.dao.EmployeeDao;
import com.ljs.learn.myspringbootsample.pojo.Department;
import com.ljs.learn.myspringbootsample.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {
    // 注入Dao
    @Autowired
    private EmployeeDao dao;

    @Autowired
    private DepartmentDao departmentDao;

    // 查询所有员工信息
    @RequestMapping("/employee/list")
    public String allEmployee(Model model){
        Collection<Employee> employees = dao.getAll();
        model.addAttribute("employees", employees);
        return "/employee/list";
    }

    // 跳转到添加员工页面
    @GetMapping("/employee")
    public String toAddEmployee(Model model){
        //获取所有的部门信息，然后显示到页面上
        Collection<Department> departments = departmentDao.getAllDepartment();
        model.addAttribute("departments", departments);
        return "employee/add";
    }

    // 添加员工
    @PostMapping("/employee")
    public String addEmployee(Employee e){
        // System.out.println(e);
        dao.save(e);
        return "redirect:/employee/list";
    }

    // 跳转到修改页面
    @GetMapping("employee/{id}")
    public  String toUpdateEmployee(@PathVariable("id") Integer id, Model model){
        // 查询数据
        Employee e = dao.getById(id);
        model.addAttribute("employee", e);

        Collection<Department> departments = departmentDao.getAllDepartment();
        model.addAttribute("departments", departments);

        return "employee/update";
    }

    // 修改员工信息
    @PostMapping("/updateEmployee")
    public String updateEmployee(Employee e){
        dao.save(e);
        return "redirect:/employee/list";
    }

    // 删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        dao.deleteById(id);
        return "redirect:/employee/list";
    }
}
