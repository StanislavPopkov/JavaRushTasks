package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <V extends Convertable>Map convert(List<V> list) {
        Map result = new HashMap();
        for(V value : list){
            result.put(value.getKey(), value);
        }
        return result;
    }
}
