package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {
    public void recurse(int n) {
        if (n == 1) return;
        boolean flag = true;
        int numb = 2;
        int natNumberReq = getNatNumber(numb, n);
        while (flag) {
            if (n % natNumberReq == 0) {
                System.out.println(natNumberReq);
                //System.out.println(n);
                recurse(n / natNumberReq);
                flag = false;
            } else if (n % natNumberReq != 0) {
                natNumberReq = getNatNumber(numb++, n);
                continue;
            }
            else {
                System.out.println(natNumberReq);
                return;
            }
        }
    }

    private int getNatNumber(int numb, int n){
        int natNumber = 0;
        for(int i=numb;i<=n;i++) {
            int a = 2;
            int b = 0;
            while (a * a <= i & b != 1) {
                if (i % a == 0) {
                    b = 1;
                    a++;
                } else a++;
            }
            if (b == 1) continue;
            else {
                natNumber = i;
                break;
            }
        }
        return natNumber;
    }

    public static void main(String[] args) {
        //Solution solution = new Solution();

        //solution.recurse(132);
    }
}
