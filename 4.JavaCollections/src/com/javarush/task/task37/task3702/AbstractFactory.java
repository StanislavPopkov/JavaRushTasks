package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.Human;

public interface AbstractFactory {

    public Human getPerson(int age);
}
