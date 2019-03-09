package com.javarush.task.task25.task2511;

import java.util.TimerTask;

/* 
Вооружаемся до зубов!
*/
public class Solution extends TimerTask {
    protected TimerTask original;
    protected final Thread.UncaughtExceptionHandler handler;

    public Solution(TimerTask original) {
        if (original == null) {
            throw new NullPointerException();
        }
        this.original = original;
        this.handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e);
                System.out.println(e.getCause().getCause());
            }
        };

    }

    public void run() {
        try {
            original.run();
        } catch (Throwable cause) {
            Thread currentThread = Thread.currentThread();
            handler.uncaughtException(currentThread, new Exception("ABC", new RuntimeException("DEF", new IllegalAccessException("GHI"))));
        }
    }

    public long scheduledExecutionTime() {
        return original.scheduledExecutionTime();
    }

    public boolean cancel() {
        return original.cancel();
    }

    public static void main(String[] args) throws IllegalAccessException, RuntimeException, Exception {
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                throw  new RuntimeException("DEF", new IllegalAccessException("GHI"));
            }
        };
        new Thread(new Solution(t)).start();
    }
}