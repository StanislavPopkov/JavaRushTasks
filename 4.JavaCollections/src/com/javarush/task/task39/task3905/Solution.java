package com.javarush.task.task39.task3905;

import java.util.Random;

import static com.javarush.task.task39.task3905.Color.*;

/* 
Залей меня полностью
*/

public class Solution {
    public static void main(String[] args) {
        Color[][] image = new Color[][] {
                {BLUE, BLUE, BLUE},
                {BLUE, GREEN, BLUE},
                {BLUE, GREEN, BLUE}
        };

        new PhotoPaint().paintFill(image, 1, 1, ORANGE);

        for (int i = 0; i < image.length ; i++) {
            for (int j = 0; j < image[i].length; j++) {
                System.out.println(image[i][j]);
            }
        }
    }
}
