package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;
    private List<Path> foundFiles = new ArrayList<>();

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        String content2 = new String(content);
        boolean containsName = true;
        if(partOfName!= null && !file.getFileName().toString().contains(partOfName))
            containsName = false;

        boolean containsContent = true;
        if(partOfContent!= null && !content2.contains(partOfContent))
            containsContent = false;

        boolean sizeMax = true;
        if(maxSize != 0 && content.length >  maxSize) sizeMax = false;

        boolean sizeMin = true;
        if(minSize != 0 && content.length <  minSize) sizeMin = false;

        if(containsName & containsContent & sizeMax & sizeMin) foundFiles.add(file);
/*
        List<String> list = Files.readAllLines(file);
        if(partOfName != null  && file.getFileName().toString().equals(partOfName)) foundFiles.add(file.getFileName());
        else if(partOfName != null && list.contains(partOfContent)) foundFiles.add(file.getFileName());
        else if(attrs.size() < (long) maxSize) foundFiles.add(file.getFileName());
        else if(attrs.size() > (long) minSize) foundFiles.add(file.getFileName());*/
        return FileVisitResult.CONTINUE;
    }
}
