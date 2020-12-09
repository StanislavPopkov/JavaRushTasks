package com.javarush.task.task40.task4009;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getWeekdayOfBirthday("30.05.1984", "2015"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) {
        //напишите тут ваш код
        LocalDate parseBirthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("d.M.y"));
        LocalDate parseYear = LocalDate.of(Year.parse(year).getValue(), parseBirthday.getMonth(), parseBirthday.getDayOfMonth());
        return parseYear.format(DateTimeFormatter.ofPattern("EEEE", Locale.ITALIAN));

    }
}
