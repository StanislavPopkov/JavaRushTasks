package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> ids = new HashSet<>();
        for(String str : strings){
            Long key = shortener.getId(str);
            if(key != null){
                ids.add(key);
            }
        }
        return ids;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> strings = new HashSet<>();
        for(Long key : keys){
            String str = shortener.getString(key);
            if(str != null){
                strings.add(str);
            }
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        for(long i = 0; i < elementsNumber; i++){
            strings.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date startTime = new Date();
        Set<Long> ids = getIds(shortener, strings);
        Date endTime = new Date();
        long result = endTime.getTime() - startTime.getTime();
        Helper.printMessage(String.valueOf(result));

        startTime = new Date();
        Set<String> stringSet = getStrings(shortener, ids);
        endTime = new Date();
        result = endTime.getTime() - startTime.getTime();
        Helper.printMessage(String.valueOf(result));

        if(strings.equals(stringSet)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }

    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10000);
        testStrategy(new OurHashMapStorageStrategy(), 10000);
        testStrategy(new FileStorageStrategy(), 100);
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
    }


}
