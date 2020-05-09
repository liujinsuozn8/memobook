package com.ljs.learn.myspringbootsample.dao;

import com.ljs.learn.myspringbootsample.pojo.Department;
import com.ljs.learn.myspringbootsample.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 标识dao层，有spring接管
@Repository
public class EmployeeDao {
    // 模拟DB数据
    private static Map<Integer, Employee> map = null;

    @Autowired
    private DepartmentDao departmentDao;

    static{
         map = new HashMap<>();
         map.put(1001, new Employee(1001, "aa", "aa@com", 1, new Department(001, "departA")));
         map.put(1002, new Employee(1002, "bb", "bb@com", 0, new Department(002, "departB")));
         map.put(1003, new Employee(1003, "cc", "cc@com", 1, new Department(003, "departC")));
         map.put(1004, new Employee(1004, "dd", "dd@com", 0, new Department(004, "departD")));
         map.put(1005, new Employee(1005, "ee", "ee@com", 1, new Department(001, "departA")));
         map.put(1006, new Employee(1006, "ff", "ff@com", 0, new Department(002, "departB")));
    }

    // 主键的值
    private static Integer initid = 1007;

    // 增加一个员工
    public void save(Employee employee){
        if (employee.getId() == null){
            employee.setId(initid++);
        }

        employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));

        map.put(employee.getId(), employee);
    }

    // 获取全部员工
    public Collection<Employee> getAll(){
        return map.values();
    }

    public Employee getById(Integer id){
        return map.get(id);
    }

    public void deleteById(Integer id){
        map.remove(id);
    }
}
