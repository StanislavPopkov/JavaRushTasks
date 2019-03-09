package com.javarush.task.task27.task2707;



/*
Определяем порядок захвата монитора
*/
public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isLockOrderNormal(final Solution solution, final Object o1, final Object o2) throws Exception {
        //do something here
        Thread thr1 = new Thread() {
            @Override
            public void run() {
                synchronized (o1) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }
            }

        };
            Thread thr2 = new Thread() {
                @Override
                public void run() {
                    //try {
                    solution.someMethodWithSynchronizedBlocks(o1, o2);
                    //Thread.sleep(1000);
                    //}catch (InterruptedException e){
                    //    Thread.currentThread().interrupt();
                    //}
                }
            };
            Thread thr3 = new Thread() {
                @Override
                public void run() {
                    synchronized (o2) {

                    }
                }
            };

        thr1.start();
        //Thread.sleep(50);
        thr2.start();
        //Thread.sleep(50);
        thr3.start();
        Thread.sleep(500);

        if(thr3.getState()==Thread.State.BLOCKED)return false;
        else return true;



    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object martin  = new Object();
        final Object luter = new Object();

        System.out.println(isLockOrderNormal(solution, luter, martin));
        //solution.someMethodWithSynchronizedBlocks(luter, martin);
    }
}
