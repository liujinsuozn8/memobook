package com.ljs.mvc.dao;

import java.util.HashMap;
import java.util.Map;

public class CustomerDaoFactory {
    // 使用单例模式
    private static CustomerDaoFactory instance = new CustomerDaoFactory();
    private Map<String, CustomerDao> daoMap= new HashMap<>();

    // 私有化构造器，只能在类内部示例化对象
    private CustomerDaoFactory(){
        // 初始化时，构造所有可能类型的对象
        daoMap.put("jdbc", new CustomerDaoImpl());
        daoMap.put("xml", new CustomerXmlImpl());
    }

    // 在外部获取工厂的单例对象
    public static CustomerDaoFactory getInstance(){
        return instance;
    }

    // 根据类型来获取真实的Dao对象
    private String type = null;
    public void setType(String type){
        this.type = type;
    }

    public CustomerDao getDao(){
        return daoMap.get(type);
    }
}
