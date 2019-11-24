package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;


public class FunctionalTest {

    public void testStorage(Shortener shortener){
        String str1 = "Тест тест тест";
        String str2 = "Тест тест";
        String str3 = "Тест тест тест";

        Long key1 = shortener.getId(str1);
        Long key2 = shortener.getId(str2);
        Long key3 = shortener.getId(str3);

        Assert.assertNotEquals(str2, str1);
        Assert.assertNotEquals(str2, str3);
        Assert.assertEquals(str1, str3);

        String strId1 = shortener.getString(key1);
        String strId2 = shortener.getString(key2);
        String strId3 = shortener.getString(key3);

        Assert.assertEquals(strId1, str1);
        Assert.assertEquals(strId2, str2);
        Assert.assertEquals(strId3, str3);
    }

    @Test
    public void testHashMapStorageStrategy(){
        HashMapStorageStrategy storageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);

    }
    @Test
    public void testOurHashMapStorageStrategy(){
        OurHashMapStorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);

    }
    @Test
    public void testFileStorageStrategy(){
        FileStorageStrategy storageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy(){
        HashBiMapStorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy(){
        DualHashBidiMapStorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy(){
        OurHashBiMapStorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
}
