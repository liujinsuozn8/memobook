package com.ljs.learn.myspringbootshiro.config;

import com.ljs.learn.myspringbootshiro.pojo.User;
import com.ljs.learn.myspringbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    // 整合持久层
    @Autowired
    private UserService userService;

    // 实现授权与认证的方法

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");

        // 添加授权检测
        // 1. 这种方式会为每一个对象都添加授权信息
        // SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // info.addStringPermission("user:add");

        // 2. 通过数据库检索的结构来进行检测
        // 2.1 获取当前页面登录的这个对象的用户信息
        // （new SimpleAuthenticationInfo 时的第一个参数）
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();

        // 2.2 从用户信息中获取授权信息，然后进行检测
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    // 认证

    // 1. 使用假数据进行测试
    // @Override
    // protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    //     System.out.println("认证");
    //
    //     // 假数据
    //     String name = "root";
    //     String pwd = "1234";
    //
    //     // 从token中获取用户密码
    //     UsernamePasswordToken userToken = (UsernamePasswordToken) token;
    //
    //     // 手动执行用户认证
    //     if (!name.equals(userToken.getUsername())){
    //         // 如果用户名不存在，返回null
    //         // 将会自动抛出 UnknownAccountException
    //         return null;
    //     }
    //
    //     // 通过shiro执行密码认证
    //     return new SimpleAuthenticationInfo("", pwd, "");
    // }

    // 2. 整合持久层，通过查询数据库来获取用户信息并进行验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");

        // 从token中获取用户密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 通过token中的信息，检索数据库中的用户数据
        User userInfo = userService.getUserByName(userToken.getUsername());

        // 手动执行用户认证
        if (userInfo == null){
            // 如果用户名不存在，返回null
            // 将会自动抛出 UnknownAccountException
            return null;
        }

        // 通过shiro执行密码认证
        // return new SimpleAuthenticationInfo("", dbUser.getPwd(), "");
        // 传递用户信息，在授权检测时，可以继续使用
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPwd(), "");
    }
}
