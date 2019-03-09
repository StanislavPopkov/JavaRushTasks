package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* 
Что внутри папки?
*/
public class Solution {
    static int countFiles;
    static int countDirectory;
    static long size;

    public static void main(String[] args) throws IOException {
        //AtomicInteger countFiles = new AtomicInteger(0);
        //AtomicInteger countDirectory = new AtomicInteger(0);
        //AtomicLong size = new AtomicLong(-1);

        countFiles = 0;
        countDirectory = -1;
        size = 0;


        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        Path path = Paths.get(buff.readLine());
        if(!Files.isDirectory(path)) {
            System.out.println(path.toString()+" - не папка");
            return;
        }
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                //countDirectory.incrementAndGet();
                countDirectory++;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                //countFiles.incrementAndGet();
                countFiles++;
                //size.getAndAdd(attrs.size());
                size += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println( "Всего папок - "+countDirectory);
        System.out.println("Всего файлов - "+countFiles);
        System.out.println("Общий размер - "+size);

        buff.close();
    }

}
