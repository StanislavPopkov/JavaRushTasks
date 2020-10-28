package com.javarush.task.task39.task3903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Неравноценный обмен
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter a number: ");

        long number = Long.parseLong(reader.readLine());
        System.out.println("Please enter the first index: ");
        int i = Integer.parseInt(reader.readLine());
        System.out.println("Please enter the second index: ");
        int j = Integer.parseInt(reader.readLine());
/*        long number = 0b00001010;
        int i = 4;
        int j = 1;*/
        System.out.println("The result of swapping bits is " + swapBits(number, i, j));
    }

    public static long swapBits(long number, int i, int j) {
        boolean i1 = (number & (1 << i)) == (1 << i);
        boolean j1 = (number & (1 << j)) == (1 << j);
        if ((!i1 && !j1) || (i1 && j1)) {
            return number;
        } else if (!i1 && j1) {
            number = number | (1 << i);
            return number & ~(1 << j);
        } else {
            number = number & ~(1 << i);
            return number | (1 << j);
        }

/*        if (((number >>> i) & 1) != ((number >>> j) & 1)) {
            long bitMask = (1L << i) | (1L << j);
            number ^= bitMask;
        }
        return number;
    }*/
    }
}
