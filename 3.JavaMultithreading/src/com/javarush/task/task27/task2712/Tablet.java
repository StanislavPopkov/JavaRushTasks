package com.javarush.task.task27.task2712;
import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet  {
    final int number;
    static Logger logger = Logger.getLogger(Tablet.class.getName());
    private  LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>() ;

    public Tablet(int number) {
        this.number = number;
    }

    public void createOrder(){
        try {
            Order order = new Order(this);
            createOrder(order);

        }catch (IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

    }

    public void setQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.queue = orderQueue;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

    public void createTestOrder(){
        try {
            TestOrder order = new TestOrder(this);
            createOrder(order);
        }catch (IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

    }

    private void createOrder(Order order) {
        if (!order.isEmpty()) {

            AdvertisementManager manager = null;
            try {
                manager = new AdvertisementManager(order.getTotalCookingTime() * 60);
                manager.processVideos();

            } catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, "No video is available for the order "+order);
            }

            try {
                queue.add(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
