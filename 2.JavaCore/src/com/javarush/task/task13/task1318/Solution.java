package com.javarush.task.task13.task1318;

import java.io.*;
import java.util.Scanner;

/* 
Чтение файла
*/

public class Solution {
    public static void main(String[] args) {
        // напишите тут ваш код
        try{
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String path = buff.readLine();
        FileInputStream fi = new FileInputStream(path);
        //BufferedReader br = new BufferedReader(new InputStreamReader(fi));
        while(fi.available() > 0){
            int data = fi.read();
            System.out.print((char) data);
        }

        buff.close();
        //br.close();
        fi.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
            
        }
    }
