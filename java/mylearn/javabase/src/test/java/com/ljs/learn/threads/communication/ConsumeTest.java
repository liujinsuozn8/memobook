package com.ljs.learn.threads.communication;

// - 问题描述
//     - 生产者Productor将产品交给店员Clerk，消费者Customer从店员处取走产品
//     - 店员一次只能持有固定数量的产品，a个
//     - 如果店员已经持有a个产品，店员会叫生产者停一下，直到店中有空位了再通知生产者继续生产
//     - 如果店中没有产品了，店员会告诉消费者等一下，等店中有产品了再通知消费者来取走产品
// - 可能会出现的问题
//     - 生产者比消费者快，消费者会漏掉一些数据没有拿到
//     - 消费者比生产者快，消费者会取道相同的数据

// - 问题的分析
//     - 多线程的组成：生产者线程、消费者线程
//     - 共享数据：产品(店员)
//     - 存在共享数据需要使用**线程同步**来解决线程不安全的问题
//     - 店员毁于生产者和消费者交互，所以需要线程的通信

import org.junit.Test;

public class ConsumeTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        // 消费比生产快
        // Productor p = new Productor(clerk, 100);
        // Customer c = new Customer(clerk, 10);

        // 生产比消费快
        Productor p = new Productor(clerk, 10);
        Customer c = new Customer(clerk, 100);

        Thread tp = new Thread(p);
        Thread tc = new Thread(c);

        tp.start();
        tc.start();
    }
}


class Clerk{
    private int productCount = 0;
    private static final int MAX_COUNT = 20;

    // 消费者消费
    public synchronized void consume() {
        // 如果有产品则卖出
        if (productCount > 0){
            System.out.println("counsume : " + productCount);
            productCount--;

            //每消费一个，就说明至少现在比MAX_COUNT小，可以唤醒生产者，使其继续生产
            notify();
        }else {
            //如果没有产品则等待
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 生产者生产
    public synchronized void product() {
        // 如果有产品数量小于MAX_COUNT，则可以继续生产
        if (productCount < MAX_COUNT){
            productCount++;
            System.out.println("product : " + productCount);

            //每生产一个，就说明现在至少有一个产品，可以唤醒消费者，继续消费
            notify();
        }else {
            //如果产品过多，则停止生产
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//消费者
class Customer implements Runnable{
    private Clerk clerk;
    private int sleepTime; //通过不同的延迟时间，来控制测试结果

    public Customer(Clerk clerk, int sleepTime) {
        this.clerk = clerk;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 消费产品
            clerk.consume();
        }
    }
}

//生产者
class Productor implements Runnable{
    private Clerk clerk;
    private int sleepTime;//通过不同的延迟时间，来控制测试结果

    public Productor(Clerk clerk, int sleepTime) {
        this.clerk = clerk;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 生产产品
            clerk.product();
        }
    }
}