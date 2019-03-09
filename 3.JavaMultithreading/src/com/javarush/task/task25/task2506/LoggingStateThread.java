package com.javarush.task.task25.task2506;

public class LoggingStateThread extends Thread {
    Thread targetT;

    public LoggingStateThread(Thread targetT) {
        //super(targeT);
        this.targetT = targetT;
        setDaemon(true);
    }

    public void run() {
        State state, lastState = null;
        do {
            state = targetT.getState();
            if (state != lastState) {
                System.out.println(state);
                lastState = state;
            }
        } while (state != State.TERMINATED);
       }


}
