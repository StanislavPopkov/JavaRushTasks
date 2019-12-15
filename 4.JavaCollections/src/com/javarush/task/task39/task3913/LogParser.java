package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery {
    private Path logdir;

    public LogParser(Path logDir) {
        this.logdir = logDir;
    }

    public List<String> readAllLogs() {
        List<String> listLogs = new ArrayList<>();
//        String[] files = logdir.toFile().list((dir, name) -> name.endsWith(".log"));
        File[] listFile = logdir.toFile().listFiles();
        for (File file : listFile) {
            try {
                if(file.toString().endsWith(".log")) {
                    listLogs.addAll(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listLogs;
    }

    public Date getDate(String [] strSplit, boolean offSet) {
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            if (offSet) {
                date = parser.parse(strSplit[strSplit.length - 5] + " "+ strSplit[strSplit.length - 4]);
            } else {
                date = parser.parse(strSplit[strSplit.length - 4] + " "+ strSplit[strSplit.length - 3]);
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean isBetweenDate(Date after, Date before, Date dateFromLog) {
        if (after == null && before == null) {
            return true;
        } else if (after == null) {
            return dateFromLog.compareTo(before) <= 0;
        } else if (before == null) {
            return dateFromLog.compareTo(after) >= 0;
        } else  {
            return dateFromLog.compareTo(after) >= 0 & dateFromLog.compareTo(before) <= 0;
        }
    }

    /**
     Возвращает количество уникальных IP адресов за выбранный период включаяя after before
     Если параметр after равен null, то нужно обработать все записи, у которых дата меньше или равна before.
     Если параметр before равен null, то нужно обработать все записи, у которых дата больше или равна after.
     Если и after, и before равны null, то нужно обработать абсолютно все записи (без фильтрации по дате)
     */
    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    /**
     * Возвращает множество, содержащее все не повторяющиеся IP
     */
    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> uniqueIP = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\s+");
            if (after == null & before == null) {
                uniqueIP.add(strSplit[0]);
            } else {
                boolean isCharDigit = Character.isDigit(strSplit[strSplit.length - 2].charAt(0));
                Date date = getDate(strSplit, isCharDigit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate) {
                    uniqueIP.add(strSplit[0]);
                }
            }
        }
        return uniqueIP;
    }


    /**
     *Возвращает IP, с которых работал переданный пользователь.
     */
    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> uniqueIP = new HashSet<>();
        List<String> listLogs = readAllLogs();
        String[] userSplit = user.split("\\s+");
        for (String line : listLogs) {
            String[] strSplit = line.split("\\s+");
            StringBuilder userNameBuild = new StringBuilder();
            for (int i = 1; i < userSplit.length + 1; i++) {
                userNameBuild.append(strSplit[i]).append(" ");
            }
            String userName = userNameBuild.toString().trim();
            if (after == null & before == null & userName.equals(user)) {
                uniqueIP.add(strSplit[0]);
            } else {
                boolean isCharDigit = Character.isDigit(strSplit[strSplit.length - 2].charAt(0));
                Date date = getDate(strSplit, isCharDigit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate & userName.equals(user)) {
                    uniqueIP.add(strSplit[0]);
                }
            }
        }
        return uniqueIP;
    }

    /**
     *возвращает IP, с которых было произведено переданное событие.
     */
    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> uniqueIP = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\s+");
            boolean isCharDigit = Character.isDigit(strSplit[strSplit.length - 2].charAt(0));
            String eventSplit = null;
            if (isCharDigit) {
                eventSplit = strSplit[strSplit.length - 3];
            } else {
                eventSplit = strSplit[strSplit.length - 2];
            }
            if (after == null & before == null & eventSplit.equals(event.toString())) {
                uniqueIP.add(strSplit[0]);
            } else {
                Date date = getDate(strSplit, isCharDigit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate & eventSplit.equals(event.toString())) {
                    uniqueIP.add(strSplit[0]);
                }
            }
        }
        return uniqueIP;
    }

    /**
     *Возвращает IP, события с которых закончилось переданным статусом.
     */
    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> uniqueIP = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\s+");
            boolean charDigit = Character.isDigit(strSplit[strSplit.length - 2].charAt(0));
            String statusSplit = strSplit[strSplit.length - 1];
            if (after == null & before == null & statusSplit.equals(status.toString())) {
                uniqueIP.add(strSplit[0]);

            } else {
                Date date = getDate(strSplit, charDigit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate & statusSplit.equals(status.toString())) {
                    uniqueIP.add(strSplit[0]);
                }
            }
        }
        return uniqueIP;
    }
}