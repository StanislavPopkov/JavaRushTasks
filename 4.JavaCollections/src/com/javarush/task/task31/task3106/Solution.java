package com.javarush.task.task31.task3106;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {


            String result = args[0];
            List<String> listFiles = new ArrayList<String>(Arrays.asList(args));
            listFiles.remove(0);
            // sort by file name
            Collections.sort(listFiles);

            List<FileInputStream> fisList = new ArrayList<>();
            for (int i = 0; i < listFiles.size(); i++) {
                fisList.add(new FileInputStream(listFiles.get(i)));
            }

            SequenceInputStream seqInStream = new SequenceInputStream(Collections.enumeration(fisList));

            ZipInputStream zipInStream = new ZipInputStream(seqInStream);
            FileOutputStream fileOutStream = new FileOutputStream(result);
            byte[] buf = new byte[1024 * 1024]; // 1MB buffer
            while (zipInStream.getNextEntry() != null) {
                int count;
                while ((count = zipInStream.read(buf)) != -1) {
                    fileOutStream.write(buf, 0, count);
                }
            }
            seqInStream.close();
            zipInStream.close();
            fileOutStream.close();
        }


}
