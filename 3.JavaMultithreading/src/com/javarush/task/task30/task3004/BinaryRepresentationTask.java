package com.javarush.task.task30.task3004;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BinaryRepresentationTask extends RecursiveTask<String> {
    int number;

    public BinaryRepresentationTask(int i) {
        this.number = i;
    }

    @Override
    protected String compute() {

        int a = number % 2;
        int b = number / 2;
        String result = String.valueOf(a);

        if (b > 0) {
            BinaryRepresentationTask binari = new BinaryRepresentationTask(b);
            binari.fork();
            return binari.join()+result;
        }

        return result;
    }
}
