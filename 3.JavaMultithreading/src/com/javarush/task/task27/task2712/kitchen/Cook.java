package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    String name;
    boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>() ;


    public Cook(String name) {
        this.name = name;
    }


    public void run() {
        try {
            while (true){
                Thread.currentThread().sleep(10);
                //System.out.println("ПОток" +orderQueue.size());
                if (!this.busy & queue.size() > 0) {
                    this.startCookingOrder(queue.poll());
                }
            }
        }catch(InterruptedException e){
        }
    }



    @Override
    public String toString() {
        return name;
    }



    public void startCookingOrder(Order order){
        this.busy = true;
        ConsoleHelper.writeMessage("Start cooking - "+order.toString()+", cooking time "+order.getTotalCookingTime()+"min");
        try{
            Thread.currentThread().sleep(order.getTotalCookingTime() * 10);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        EventDataRow cookEvent = new CookedOrderEventDataRow(order.tablet.toString(), this.name, order.getTotalCookingTime()*60, order.getDishes());
        StatisticManager sMannager = StatisticManager.getInstance();
        sMannager.register(cookEvent);
        setChanged();
        notifyObservers(order);
        this.busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.queue = orderQueue;
    }
}
