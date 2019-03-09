package com.javarush.task.task29.task2909.car;

public class Truck extends Car {
    private int numberOfPassengers;

    public Truck(int numberOfPassengers) {
        super(numberOfPassengers);
        this.numberOfPassengers = numberOfPassengers;
    }

    @Override
    public int getMaxSpeed() {
        return MAX_TRUCK_SPEED;
    }
}
