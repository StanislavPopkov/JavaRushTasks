package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        int[][] array = new int[3][3];

        array[0][0] = 1;
        array[0][1] = 1;
        array[0][2] = 1;
        array[1][0] = 1;
        array[1][1] = 1;
        array[1][2] = 1;
        array[2][0] = 1;
        array[2][1] = 1;
        array[2][2] = 1;
        maxSquare(array);

    }

    public static int maxSquare(int[][] matrix) {
        int rows = matrix.length;
        int cols = rows > 0 ? matrix[0].length : 0;
        int[][] temp = new int[rows + 1][cols + 1];
        int maxLen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == 1) {
                    temp[i][j] = Math.min(Math.min(temp[i][j - 1], temp[i - 1][j]), temp[i - 1][j - 1]) + 1;
                    maxLen = Math.max(maxLen, temp[i][j]);
                }
            }
        }
        return maxLen * maxLen;
    }

   /* public static int maxSquare(int[][] matrix) {
        List<Integer> squareList = new ArrayList<>();
        Solution solution = new Solution();
        List<SolutionWrapper> solutionWrapperList = solution.getOneList(matrix);
        for (SolutionWrapper solutionWrapper : solutionWrapperList) {
            int[] start = solutionWrapper.start;
            int horIterationFirst = -1;
            boolean zeroStart = false;
            for (int i = start[0]; i < matrix.length; i++) {
                horIterationFirst = i;
                if (i == 0 && matrix[i][start[1]] == 1) {
                    zeroStart = true;
                }
                if (matrix[i][start[1]] == 0){
                    horIterationFirst--;
                    break;
                }
            }
            int[] end = solutionWrapper.end;
            int horIterationSecond = -1;
            for (int i = end[0]; i < matrix.length; i++) {
                horIterationSecond = i;
                if (matrix[i][end[1]] == 0){
                    horIterationSecond--;
                    break;
                }
            }
            int vertOne = end[1] - start[1];
            if (horIterationFirst != horIterationSecond && vertOne!= horIterationFirst) {
                continue;
            } else {
                boolean flag = true;
                outer: for (int i = start[0]; i <= horIterationFirst; i++) {
                    for (int j = start[1]; j <= end[1]; j++) {
                        if(matrix[i][j] == 0) {
                            flag = false;
                            break outer;
                        }
                    }
                }
                if (flag) {
                    if (zeroStart) {
                        horIterationFirst++;
                        vertOne++;
                    }
                    int square = horIterationFirst * vertOne;
                    squareList.add(square);
                }

            }
        }
        return Collections.max(squareList);

    }

    public List<SolutionWrapper> getOneList(int[][] matrix) {
        List<SolutionWrapper> solutionWrapperList = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            SolutionWrapper solutionWrapper = new SolutionWrapper();
            boolean flag = true;
            int marker = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                marker = j;
                int matrix1 = matrix[i][j];
                if (matrix1 == 1 && flag) {
                    flag = false;
                    solutionWrapper.start = new int[]{i, j};
                    continue;
                }
                if (matrix1 == 0 && !flag) {
                    solutionWrapper.end = new int[]{i, --j};
                    solutionWrapperList.add(solutionWrapper);
                    flag = true;
                }
            }
            if (!flag && marker == matrix[i].length - 1) {
                solutionWrapper.end = new int[]{i, marker};
                solutionWrapperList.add(solutionWrapper);
            }
        }
        return solutionWrapperList;
    }

    private class SolutionWrapper {
        int[] start, end;
    }*/
}
