package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.computeIfPresent(denomination, (key, value) -> value + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int total = 0;
        for (Integer denomination : denominations.keySet()) {
            total += denomination * denominations.get(denomination);
        }
        return total;
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        if (expectedAmount == 0) {
            throw new NotEnoughMoneyException();
        }
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        ArrayList<Integer> sortArrayList = new ArrayList();
        for (Integer key : denominations.keySet()) {
            int value = denominations.get(key);
            for (int i = 1; i <= value; i++) {
                sortArrayList.add(key);
            }
        }
        Collections.sort(sortArrayList, Comparator.reverseOrder());
        int amountOfBanknotes = 0;
        int subtract = expectedAmount;
        for (Integer key : sortArrayList) {
            for (int i = key; i <= subtract; i = i+key) {
                amountOfBanknotes++;
                subtract -= key;
            }
            if (amountOfBanknotes != 0) {
                Integer amountFromMap = resultMap.get(key);
                if (amountFromMap != null) {
                    resultMap.put(key, amountFromMap + amountOfBanknotes);
                } else {
                    resultMap.put(key, amountOfBanknotes);
                }
            }
            amountOfBanknotes = 0;
            if (subtract <= 0) {
                break;
            }
        }
        int total = getTotalAmountFromResultMap(resultMap);
        if ((expectedAmount - total) != 0) {
            throw new NotEnoughMoneyException();
        }
        subtractAmount(resultMap);
        return resultMap;
    }

    private void subtractAmount(Map<Integer, Integer> resultMap) {
        for (Integer key : resultMap.keySet()) {
            Integer value = denominations.get(key) - resultMap.get(key);
            if (value == 0) {
                denominations.remove(key);
            } else {
                denominations.put(key, value);
            }
        }
    }

    private int getTotalAmountFromResultMap(Map<Integer, Integer> resultMap) {
        int total = 0;
        for (Integer denomination : resultMap.keySet()) {
            total += denomination * resultMap.get(denomination);
        }
        return total;
    }
}
