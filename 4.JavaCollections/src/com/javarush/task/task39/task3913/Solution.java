package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("G:\\JavaRushTasks\\JavaRushTasks\\4.JavaCollections\\" +
                "src\\com\\javarush\\task\\task39\\task3913\\logs"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
        System.out.println(logParser.getUniqueIPs(null, null));
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = parser.parse("04.01.2014 03:45:23");
            date2 = parser.parse("04.01.2021 20:22:55");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(logParser.getIPsForUser("Eduard Petrovich Morozko", date1,date2));
        System.out.println(logParser.getIPsForEvent(Event.LOGIN, null, null));
        System.out.println(logParser.getIPsForStatus(Status.OK, null, null));
    }
}