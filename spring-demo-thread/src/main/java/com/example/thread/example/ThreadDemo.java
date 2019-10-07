package com.example.thread.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        SemaphoreFoo semaphoreFoo = new SemaphoreFoo();
        CountDownLatchFoo countDownLatchFoo = new CountDownLatchFoo();
        LockFoo lockFoo = new LockFoo();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatchFoo.first();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatchFoo.second();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatchFoo.third();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static class SemaphoreFoo implements FooInf {

        Semaphore semaphore0 = new Semaphore(0);
        Semaphore semaphore1 = new Semaphore(0);

        @Override
        public void first() {
            System.out.println(1);
            semaphore0.release();
        }

        @Override
        public void second() throws InterruptedException {
            semaphore0.acquire();
            System.out.println(2);
            semaphore1.release();
        }

        @Override
        public void third() throws InterruptedException {
            semaphore1.acquire();
            System.out.println(3);
        }
    }


    static class CountDownLatchFoo implements FooInf {

        private CountDownLatch countDownLatchFirst = new CountDownLatch(1);
        private CountDownLatch countDownLatchSecond = new CountDownLatch(1);

        public void first() {
            countDownLatchFirst.countDown();
            System.out.println(1);
        }

        public void second() throws InterruptedException {
            countDownLatchFirst.await();
            System.out.println(2);
            countDownLatchSecond.countDown();
        }

        public void third() throws InterruptedException {
            countDownLatchSecond.await();
            System.out.println(3);
        }
    }


    static class LockFoo implements FooInf {

        private Object lock = new Object();
        private boolean firstDone = false;
        private boolean secondDone = false;

        @Override
        public void first() throws InterruptedException {
            synchronized (lock) {
                firstDone = true;
            }
            System.out.println(1);
        }

        @Override
        public void second() throws InterruptedException {
            synchronized (lock) {
                while (!firstDone) {
                    lock.wait();
                }
            }
            System.out.println(2);
            secondDone = true;
        }

        @Override
        public void third() throws InterruptedException {
            synchronized (lock) {
                while (!secondDone) {
                    lock.wait();
                }
            }
            System.out.println(3);
        }
    }


    interface FooInf {
        void first() throws InterruptedException;

        void second() throws InterruptedException;

        void third() throws InterruptedException;
    }

}
