package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        String text = bis.readLine();
        return text;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException{
        writeMessage(Dish.allDishesToString());
        writeMessage("Введите желаемое блюдо: ");
        List<Dish> list = new ArrayList<>();
        String wishes = null;
        do {
            try {
                wishes = readString();
                list.add(Dish.valueOf(wishes));
            } catch (IllegalArgumentException e) {
                writeMessage("Такого блюда нет в меню.");
            } catch (NullPointerException e) {
                writeMessage("Такого блюда нет в меню2");
        }
        } while (!wishes.equals("exit"));
        return list;

    }
}
