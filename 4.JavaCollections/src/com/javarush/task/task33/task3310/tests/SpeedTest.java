package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.Solution;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date startTime = new Date();
        ids.addAll(Solution.getIds(shortener, strings));
        Date endTime = new Date();
        long result = endTime.getTime() - startTime.getTime();
        return result;
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings){
        Date startTime = new Date();
        strings.addAll(Solution.getStrings(shortener, ids));
        Date endTime = new Date();
        long result = endTime.getTime() - startTime.getTime();
        return result;
    }

    @Test
    public void testHashMapStorage(){
        HashMapStorageStrategy storageStrategyHash = new HashMapStorageStrategy();
        HashBiMapStorageStrategy storageStrategyBiHash = new HashBiMapStorageStrategy();
        Shortener shortener1 = new Shortener(storageStrategyHash);
        Shortener shortener2 = new Shortener(storageStrategyBiHash);

        Set<String> origStrings = new HashSet<>();
        for(long i = 0; i < 10000; i++){
            origStrings.add(Helper.generateRandomString());
        }
        Set<Long> idsHash = new HashSet<>();
        long idtime1 = getTimeToGetIds(shortener1, origStrings,  idsHash);
        Set<Long> idsBiHash = new HashSet<>();
        long idtime2 = getTimeToGetIds(shortener2, origStrings,  idsBiHash);
        Assert.assertTrue(idtime1>idtime2);
        //Assert.assertEquals((float)idtime2, (float)idtime1, 5000f);

        Set<String> stringsHash = new HashSet<>();
        long idtime3 = getTimeToGetStrings(shortener1, idsHash, stringsHash);
        Set<String> stringsBiHash = new HashSet<>();
        long idtime4 = getTimeToGetStrings(shortener2, idsBiHash, stringsBiHash);
        Assert.assertEquals((float)idtime3, (float)idtime4, 60f);

    }
}
