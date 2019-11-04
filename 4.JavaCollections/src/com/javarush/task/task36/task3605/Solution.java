package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        TreeSet<Character> stringSet = new TreeSet<>();
        String contents = new String(Files.readAllBytes(Paths.get(args[0])));
        //List<String> stringList = Arrays.asList(contents.split("\\PL+"));
        char[] letter = contents.toCharArray();
        for(char ch : letter){
            if(Character.isLetter(ch)){
                stringSet.add(Character.toLowerCase(ch));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Character ch : stringSet){
            stringBuilder.append(ch);
        }
        String strResult = stringBuilder.toString();
        if(strResult.length() < 5){
            System.out.println(strResult);
        }
        else{

            System.out.println(strResult.substring(0, 5));
        }

    }
}
