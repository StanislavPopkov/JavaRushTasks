package com.javarush.task.task26.task2601;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {
        //Integer[] mass = {13, 8, 15, 5, 17, 7};
        //System.out.println(Arrays.toString(sort(mass)));
    }

    public static Integer[] sort(Integer[] array) {
        Integer[] ourAr = array;
        Arrays.sort(ourAr);
        System.out.println(Arrays.toString(ourAr));
        final Integer mediana;
        if(ourAr.length % 2 == 0) {
            mediana = (ourAr[ourAr.length/2]+ ourAr[ourAr.length/2 -1]) /2;
        }
        else {
            mediana = ourAr[ourAr.length / 2];
        }
        System.out.println(mediana);
        Arrays.sort(ourAr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //Math.abs(mediana-o1)-Math.abs(mediana-o2);
                int i1 = mediana - o1;
                int i2 = mediana - o2;
                if( i1 < 0) i1 = -i1;
                if(i2 < 0) i2 =-i2;

                return i1 - i2;


            }
        });

        return ourAr;
    }
}
