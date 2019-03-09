package com.javarush.task.task28.task2805;

public class MyThread extends Thread {
    private static int count = 1;
    public MyThread() {
        withoutGroop();
    }

    public void withoutGroop() {

        for (int i = 1; i < 11; i++) {
            if (count == 11) {
                count = 1;
                this.setPriority(count);
                count++;
                break;
            } else {
                this.setPriority(count);
                count++;
                break;
            }
        }
    }

    public void withGroop(ThreadGroup group){
        for(int i = 1; i < group.getMaxPriority(); i++){
            if(count == 11) {
                count = 1;
                this.setPriority(count);
                count++;
                break;
            }
            else{
                this.setPriority(count);
                count++;
                break;
            }
        }
    }

    public MyThread(Runnable target) {
        super(target);
        withoutGroop();
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        withGroop(group);

    }

    public MyThread(String name) {
        super(name);
        withoutGroop();
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        withGroop(group);
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
        withoutGroop();
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        withGroop(group);
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        withGroop(group);
    }
}
