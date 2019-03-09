package com.javarush.task.task26.task2603;

import java.util.Collections;
import java.util.Comparator;

/*
Убежденному убеждать других не трудно
*/
public class Solution {

    public static class CustomizedComparator<T> implements Comparator<T> {
        private Comparator<T>[] comparators;

        public CustomizedComparator(Comparator<T> ... vararg) {
            this.comparators = vararg;
        }

        @Override
        public int compare(T o1, T o2) {
            int result = 0;
            for(int i = 0; i < comparators.length; i++){
                if(comparators[i].compare(o1, o2) == 0 & i+1 != comparators.length){
                    return comparators[i+1].compare(o1, o2);
                }
                else return comparators[i].compare(o1, o2);
                }

            return result;
        }
    }

    public static void main(String[] args) {

    }
}
