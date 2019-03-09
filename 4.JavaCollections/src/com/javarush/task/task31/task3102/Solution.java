package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        Queue<File> queue = new PriorityQueue<>();
        LinkedList<String> list = new LinkedList<>();
        Path path = Paths.get(root);
        File fileRoot = new File(root);
        Collections.addAll(queue, fileRoot.listFiles());

        while (!queue.isEmpty()) {
            File currentFile = queue.remove();
            if(currentFile.isDirectory()){
                Collections.addAll(queue, currentFile.listFiles());
            } else {
                list.add(currentFile.getAbsolutePath());
            }
        }
        /*Files.walkFileTree(path, new SimpleFileVisitor<Path>(){

            public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) throws IOException {
                list.add(p.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) {
                return FileVisitResult.CONTINUE;
            }
        });*/
        return list;


    }

    public static void main(String[] args) {

        List<String> list = null;
        try {
            list = Solution.getFileTree("C://test1/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String str : list){
            System.out.println(str);
        }


    }
}
