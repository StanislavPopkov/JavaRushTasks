package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("G:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method

        String temp = urlString.substring(urlString.lastIndexOf("/")+1);
        String [] tempMass = temp.split("\\.");
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        //System.out.println(tempMass[0]+" "+tempMass[1]);

        Path tempFile = Files.createTempFile(tempMass[0],"."+tempMass[1]);
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

        Path destinationPath = downloadDirectory.resolve(temp);
        if(Files.notExists(downloadDirectory)) Files.createDirectories(downloadDirectory);
        Files.move(tempFile, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return destinationPath;
    }
}
