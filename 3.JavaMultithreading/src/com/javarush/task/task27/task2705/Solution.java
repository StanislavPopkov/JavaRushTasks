package com.javarush.task.task27.task2705;

/* 
Второй вариант deadlock
*/
public class Solution {
    private final Object lock = new Object();

    public synchronized void firstMethod() {
        synchronized (lock) {
            doSomething();
        }
    }

    public void secondMethod() {
        synchronized (lock) {
            synchronized (this) {
                doSomething();
            }
        }


    }

    private void doSomething() {
        System.out.println(Thread.currentThread().getName() + " успел");
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        new Thread(new Runnable() {
            public void run() {
                sol.firstMethod();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                sol.secondMethod();
            }
        }).start();

    }
}