package com.ljs.test.filter.sample.authority.dao;

import com.ljs.test.filter.sample.authority.bean.Authority;
import com.ljs.test.filter.sample.authority.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    // 没有真正的DB链接，添加一些数据
    private static Map<String, User> datas = new HashMap<>();
    private static List<Authority> commonAuthorities = new ArrayList<>();

    static{

        // 创建通用权限数据
        commonAuthorities.add(new Authority("page1","/filter/sample/authority/pages/page1.jsp"));
        commonAuthorities.add(new Authority("page2","/filter/sample/authority/pages/page2.jsp"));
        commonAuthorities.add(new Authority("page3","/filter/sample/authority/pages/page3.jsp"));
        commonAuthorities.add(new Authority("page4","/filter/sample/authority/pages/page4.jsp"));


        //用户AAA
        List<Authority> authoritiesOfA = new ArrayList<>();
        authoritiesOfA.add(new Authority("page1","/filter/sample/authority/pages/page1.jsp"));
        authoritiesOfA.add(new Authority("page2","/filter/sample/authority/pages/page2.jsp"));
        User a = new User("AAA", authoritiesOfA);
        datas.put("AAA", a);

        //用户BBB
        List<Authority> authoritiesOfB = new ArrayList<>();
        authoritiesOfB.add(new Authority("page3","/filter/sample/authority/pages/page3.jsp"));
        authoritiesOfB.add(new Authority("page4","/filter/sample/authority/pages/page4.jsp"));
        User b = new User("BBB", authoritiesOfB);
        datas.put("BBB", b);
    }

    //返回通用权限数据
    public static List<Authority> getCommonAuthorities() {
        return commonAuthorities;
    }

    // 根据用户名来获取用户，如果没有获取到，则返回null
    public User get(String username){
        if (datas.containsKey(username)){
            return datas.get(username);
        } else{
            return null;
        }
    }

    public void update(String username, List<Authority> authorities){
        datas.get(username).setAuthorities(authorities);
    }
}
