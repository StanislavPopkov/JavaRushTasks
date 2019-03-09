package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>() ;

    public static void main(String[] args) throws IOException {
        DirectorTablet dirTablet = new DirectorTablet();
        Cook cook = new Cook("SheFF");
        cook.setQueue(orderQueue);
        Cook cook2 = new Cook("Haizenberg");
        cook2.setQueue(orderQueue);
        Thread thread1 = new Thread(cook);
        Thread thread2 = new Thread(cook2);
        thread1.start();
        thread2.start();


        //Tablet tablet10 = new Tablet(7);
        //TestOrder t = new TestOrder(tablet10);
        //tablet10.addObserver(cook);
        //tablet10.createOrder();


        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook2.addObserver(waiter);
        List<Tablet> tabletList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tabletList.add(tablet);

        }

        Thread thread = new Thread(new RandomOrderGeneratorTask(tabletList, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return;
        }
        thread.interrupt();



        dirTablet.printAdvertisementProfit();
        dirTablet.printCookWorkloading();
        dirTablet.printActiveVideoSet();
        dirTablet.printArchivedVideoSet();

    }
}
