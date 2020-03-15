package com.ljs.mavenlearn.service;

public class ServiceTest{
    public void service(){
        String port= "${port}";
        System.out.println("this is service, port is " + port);
    }
}