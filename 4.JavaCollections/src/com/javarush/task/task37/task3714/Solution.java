package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        int result = 0;
        String digital = s.toUpperCase();
        for (int i = 0; i < digital.length(); i++) {
            char convertToDecimal = digital.charAt(i);

            switch (convertToDecimal)
            {
                case 'M':
                    result += 1000;
                    break;

                case 'D':
                    result += 500;
                    break;

                case 'C':
                    result += 100;
                    break;

                case 'L':
                    result += 50;
                    break;

                case 'X':
                    result += 10;
                    break;

                case 'V':
                    result += 5;
                    break;

                case 'I':
                    result += 1;
                    break;
            }
        }
        if (digital.contains("IV")) {
            result-=2;
        }
        if (digital.contains("IX")) {
            result-=2;
        }
        if (digital.contains("XL")) {
            result-=10;
        }
        if (digital.contains("XC")) {
            result-=10;
        }
        if (digital.contains("CD")) {
            result-=100;
        }
        if (digital.contains("CM")) {
            result-=100;
        }
        return result;
        }

}
