package com.javarush.task.task26.task2613;

import java.util.Arrays;

public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i){
        Operation[] values = Operation.values();
        if (i <= 0 || values.length < i) {
            throw new IllegalArgumentException();
        }
        Operation[] returnValues = Arrays.copyOfRange(values, 1, values.length);
        return returnValues[i - 1];
    }
}
