package com.javarush.task.task27.task2711;

import java.util.concurrent.CountDownLatch;

/* 
CountDownLatch
*/
public class Solution {


    CountDownLatch latch = new CountDownLatch(1);

    public void someMethod() throws InterruptedException {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        retrieveValue();
        latch.countDown();
    }

    void retrieveValue() {
        System.out.println("Value retrieved.");
    }

    public static void main(String[] args) {

    }
}
