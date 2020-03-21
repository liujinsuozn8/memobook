package com.ljs.learn.aop.aspect.service;

public class UserServiceImpl implements UserService {
    @Override
    public void select() {
        System.out.println("user select");
    }

    @Override
    public void insert() {
        System.out.println("user insert");
    }

    @Override
    public void delete() {
        System.out.println("user delete");
    }

    @Override
    public void update() {
        System.out.println("user update");
    }
}
