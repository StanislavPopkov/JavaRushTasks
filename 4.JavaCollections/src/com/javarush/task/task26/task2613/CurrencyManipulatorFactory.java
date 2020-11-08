package com.javarush.task.task26.task2613;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {
    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        String currencyCodeUp = currencyCode.toUpperCase();
        if (map.containsKey(currencyCodeUp)) {
            return map.get(currencyCodeUp);
        } else {
            CurrencyManipulator currencyManipulator = new CurrencyManipulator(currencyCodeUp);
            map.put(currencyCodeUp, currencyManipulator);
            return currencyManipulator;
        }
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }

}
