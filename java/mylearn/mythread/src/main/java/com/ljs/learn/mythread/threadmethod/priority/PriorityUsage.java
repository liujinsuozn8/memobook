package com.ljs.learn.mythread.threadmethod.priority;

public class PriorityUsage {
    public static void main(String[] args) {
        Runnable task1 = ()->{
          for(int i=0;;) {
              System.out.println("---->task1: " + i++);
          }
        };

        Runnable task2 = ()->{
          for(int i=0;;) {
              System.out.println("    ---->task2: " + i++);
          }
        };

        Thread t1 = new Thread(task1, "t1");
        Thread t2 = new Thread(task2, "t2");

        // 设置优先级
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
    }
}
