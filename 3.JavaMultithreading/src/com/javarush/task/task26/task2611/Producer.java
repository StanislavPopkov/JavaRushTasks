package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Runnable {
    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                int i = 1;

                while (true) {
                    StringBuffer text = new StringBuffer("Some text for ");
                    map.put(Integer.toString(i), text.append(i).toString());
                    i++;
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getState());
                }
            } catch (InterruptedException e) {
                System.out.printf("[%s]thread was terminated" + "\n", Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }
    }


}
