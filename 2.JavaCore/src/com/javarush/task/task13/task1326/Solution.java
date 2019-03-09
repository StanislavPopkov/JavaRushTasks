package com.javarush.task.task13.task1326;
import java.io.*;
import java.util.*;
/* 
Сортировка четных чисел из файла
*/

public class Solution {
    public static void main(String[] args) throws IOException  {
        // напишите тут ваш код
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String path = buff.readLine();
        InputStream file = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        ArrayList<Integer> result = new ArrayList<>();

        int s;
        while(br.ready()){
            s = Integer.parseInt(br.readLine());
            if(s%2 == 0) result.add(s);



        }

        Collections.sort(result);


        for(int i : result){
            System.out.println(i);
        }
        buff.close();
        br.close();

    }
}
