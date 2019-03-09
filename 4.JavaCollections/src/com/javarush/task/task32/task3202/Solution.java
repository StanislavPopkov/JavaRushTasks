package com.javarush.task.task32.task3202;

import java.io.*;

/* 
Читаем из потока
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("testFile.log"));
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) throws IOException {
        StringWriter writer = new StringWriter();
        BufferedReader buff = null;
        String line = null;
        if(is == null) return writer;
        try {
            writer = new StringWriter();
            buff = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line = buff.readLine()) != null) {
                sb.append(line);
            }
            line = sb.toString();
        }catch (Exception e){

        }finally {
            buff.close();
            writer.write(line);
            return writer;
        }

    }
}