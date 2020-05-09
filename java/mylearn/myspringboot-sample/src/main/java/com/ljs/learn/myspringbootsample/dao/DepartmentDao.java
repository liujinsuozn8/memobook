package com.ljs.learn.myspringbootsample.dao;

import com.ljs.learn.myspringbootsample.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 标识dao层，有spring接管
@Repository
public class DepartmentDao {
    // 模拟DB数据
    private static Map<Integer, Department> map = null;
    static {
        map = new HashMap<>();
        map.put(001, new Department(001, "departA"));
        map.put(002, new Department(002, "departB"));
        map.put(003, new Department(003, "departC"));
        map.put(004, new Department(004, "departD"));
    }

    // 返回所有部门信息
    public Collection<Department> getAllDepartment(){
        return map.values();
    }

    // 通过id返回部门信息
    public Department getDepartment(Integer id){
        return map.get(id);
    }
}
