package com.ljs.learn.myjvm.jvm01Structure;

public class JVMLifeCycle {
    // 执行后在控制台执行：jps，来观察进程
    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
