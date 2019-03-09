package com.javarush.task.task31.task3105;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
Добавление файла в архив
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path fileSource = Paths.get(args[0]);
        Path arch = Paths.get(args[1]);

        if (Files.isRegularFile(fileSource) & Files.isRegularFile(arch)) {
            HashMap<Path, Byte []> map = new HashMap<>();
            String str = "new/"+fileSource.getFileName().toString();
            Path newPath = Paths.get(str);
            //System.out.println(newPath.toString());
            Byte[] bufferNewFile = new Byte[8 * 1024];
            //читаем содержимое нового файла
            try (InputStream inputStream = new FileInputStream(fileSource.toString())) {

                while (inputStream.available() > 0) {
                    byte[] buffer = new byte[8 * 1024];
                    inputStream.read(buffer);
                    for (int i = 0; i < buffer.length; i++) {
                        bufferNewFile[i] = (Byte) buffer[i];

                    }
                }
            }

            //читаем содержимое архива в мапу
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(arch))) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null) {
                    Path fileName = Paths.get(zipEntry.getName());
                    System.out.println(fileName.toString());
                    if(fileName.toString().endsWith(fileSource.getFileName().toString())){
                        fileName = newPath;
                    }
                    byte[] buffer = new byte[8 * 1024];
                    Byte[] buffer2 = new Byte[buffer.length];
                    while (zipInputStream.available() > 0) {
                        zipInputStream.read(buffer);
                        for(int i = 0; i < buffer.length; i++){
                            buffer2[i] = (Byte) buffer[i];
                        }
                        map.put(fileName, buffer2);
                    }
                    zipInputStream.closeEntry();
                    zipEntry = zipInputStream.getNextEntry();
                }
            }
            //проверяем есть ли в мапе файл с именем как у нового файла и удаляем его если есть
            Iterator<Map.Entry<Path, Byte[]>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Path, Byte[]> pair = iterator.next();
                if (pair.getKey().endsWith(newPath.getFileName())) {
                    iterator.remove();
                }
            }
            //добавляем новый файл в мапу
            map.put(newPath, bufferNewFile);

            //записываем все в новый архив с таким же именем
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(arch))) {
                for(Map.Entry<Path, Byte[]> entry: map.entrySet()){
                    zipOutputStream.putNextEntry(new ZipEntry(entry.getKey().toString()));
                    byte[] buffer = new byte[8 * 1024];
                    Byte[] buffer2 = entry.getValue();
                    for(int i = 0; i < buffer2.length; i++){
                        buffer[i] =  buffer2[i];
                    }
                    zipOutputStream.write(buffer);
                    zipOutputStream.closeEntry();

                }
            }
        }
    }

}
