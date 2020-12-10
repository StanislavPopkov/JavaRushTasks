package com.javarush.task.task40.task4008;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Locale;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        if(date.contains(":") && !date.contains(".")) {
            LocalTime localTime = LocalTime.parse(date, DateTimeFormatter.ofPattern("H:m:s"));
            printTimeLocalTime(localTime);
        } else if (!date.contains(":") && date.contains(".")) {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d.M.y"));
            printDateLocalDate(localDate);
        } else {
            LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("d.M.y H:m:s"));
            printDateLocalDate(localDateTime.toLocalDate());
            printTimeLocalTime(localDateTime.toLocalTime());
        }
    }

    private static void printDateLocalDate(LocalDate localdate) {
        System.out.println("День: " + localdate.getDayOfMonth());
        System.out.println("День недели: " + localdate.get(ChronoField.DAY_OF_WEEK));
        System.out.println("День месяца: " + localdate.getDayOfMonth());
        System.out.println("День года: " + localdate.getDayOfYear());
        System.out.println("Неделя месяца: " + localdate.format(DateTimeFormatter.ofPattern("W")));
        System.out.println("Неделя года: " + localdate.format(DateTimeFormatter.ofPattern("w")));
        System.out.println("Месяц: " + localdate.getMonthValue());
        System.out.println("Год: " + localdate.getYear());
    }

    private static void printTimeLocalTime(LocalTime localTime) {
        System.out.println("AM или PM: " + (localTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
        System.out.println("Часы: " + localTime.get(ChronoField.CLOCK_HOUR_OF_AMPM));
        System.out.println("Часы дня: " + localTime.get(ChronoField.CLOCK_HOUR_OF_DAY));
        System.out.println("Минуты: " + localTime.get(ChronoField.MINUTE_OF_HOUR));
        System.out.println("Секунды: " + localTime.get(ChronoField.SECOND_OF_MINUTE));
    }
}
