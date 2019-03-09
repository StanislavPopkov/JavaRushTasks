package com.javarush.task.task09.task0921;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/* 
Метод в try..catch
*/

public class Solution {
    public static void main(String[] args) {

         readData();

    }

    public static void readData() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer> list = new ArrayList<>();
        int d;
        boolean in = true;
        while(in){
            try{
                d = Integer.parseInt(reader.readLine());
                list.add(d);
            }catch(NumberFormatException e){
                for(int i = 0; i<list.size(); i++){
                    System.out.println(list.get(i));

            }
                e.getMessage();
                in = false;
            } catch (IOException e){
                in = false;
                e.getMessage();
            }
        }
    }
}
