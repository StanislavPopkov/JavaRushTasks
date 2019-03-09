package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        List<Integer> list =new ArrayList<>();
        int count = 3;
        while(count > 0){
            int symbol = random.nextInt(48, 58);
            list.add(symbol);
            count--;
        }
        count = 2;
        while(count > 0){
            int symbol = random.nextInt(65, 91);
            list.add(symbol);
            count--;
        }
        count = 3;
        while(count > 0){
            int symbol = random.nextInt(97, 123);
            list.add(symbol);
            count--;
        }
        Collections.shuffle(list);
        for(Integer number : list){
            byteArray.write(number);
        }
        return byteArray;
    }
}