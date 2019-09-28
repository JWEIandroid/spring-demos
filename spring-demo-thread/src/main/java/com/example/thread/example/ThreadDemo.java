package com.example.thread.example;

public class ThreadDemo {

    public static void main(String[] args) {
        threadTest();
    }


    private static void threadTest() {

        Object lock0 = new Object();
        Object lock1 = new Object();
        Object lock2 = new Object();



        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock0) {
                    for (int i = 0; i < 10; i++) {
                        if (i == 5) {
                            try {
                                System.out.println(Thread.currentThread().getName() + "wait...");
                                lock0.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + "current:" + i);
                    }
                    System.out.println("thread1 finish...");
                    lock1.notify();
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1){
                    for (int i = 0; i < 10; i++) {
                        System.out.println(Thread.currentThread().getName() + "current:" + i);
                        if (i == 5) {
                            try {
                                System.out.println(Thread.currentThread().getName() + "wait...");
                                lock0.notify();
                                lock1.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                System.out.println("thread2 finish...");
            }
        });


        thread0.start();

    }


}
