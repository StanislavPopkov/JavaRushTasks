package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
        this.initDishes();
    }

    @Override
    protected void initDishes() throws IOException {
        Random rand = new Random();
        int numberOfDishes = 0;
        dishes = new ArrayList<>();
        numberOfDishes = rand.nextInt(Dish.values().length);
        for (int i = 0; i < numberOfDishes ; i++) {
            int dish = rand.nextInt(Dish.values().length);
            dishes.add(Dish.values()[dish]);
        }
    }
}
