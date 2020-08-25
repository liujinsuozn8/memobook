package com.ljs.learn.mythread.threadmethod.yield;

public class YieldUsage {
    public static void main(String[] args) {
        Runnable task1 = ()->{
          for(int i=0;;) {
              System.out.println("---->task1: " + i++);
          }
        };

        Runnable task2 = ()->{
          for(int i=0;;) {
              Thread.yield();
              System.out.println("    ---->task2: " + i++);
          }
        };

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");

        t1.start();
        t2.start();
    }
}
