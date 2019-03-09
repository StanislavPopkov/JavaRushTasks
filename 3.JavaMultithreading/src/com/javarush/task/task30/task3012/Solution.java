package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

import java.util.ArrayList;
import java.util.List;

public class Solution {
    List<String> list = new ArrayList<>();
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(1234);
        System.out.println(Double.POSITIVE_INFINITY+10);
    }

    public void createExpression(int number) {
        //напишите тут ваш код
        StringBuilder sb = new StringBuilder(number + " =");
        recursiveHelpMethod(number);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("0")) {
                sb.append(" " + list.get(i) + " " + String.valueOf((int)Math.pow(3, i)));
            }
        }
        System.out.println(sb.toString());
    }

    public void recursiveHelpMethod(int number) {
        int ostatok = number % 3;
        int result = number / 3;

        switch (ostatok) {
            case 0: {
                list.add("0");
                break;
            }
            case 1: {
                list.add("+");
                break;
            }
            case 2: {
                list.add("-");
                result++;
                break;
            }
        }

        if (result == 1) {
            list.add("+");
        }
        else if(result >= 2){
            recursiveHelpMethod(result);
        }
    }
}