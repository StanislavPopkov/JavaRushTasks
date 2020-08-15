package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class LogParser implements IPQuery, UserQuery, DateQuery {
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

    public Date getDate(String [] strSplit) {
        SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = parser.parse(strSplit[2]);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

    /**
     * должен возвращать всех пользователей
     */
    @Override
    public Set<String> getAllUsers() {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            allUser.add(strSplit[1]);
        }
        return allUser;
    }

    /**
     *должен возвращать количество уникальных пользователей за период
     */
    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            if (after == null & before == null) {
                allUser.add(strSplit[0]);

            } else {
                Date date = getDate(strSplit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate) {
                    allUser.add(strSplit[1]);
                }
            }
        }
        return allUser.size();
    }

    /**
     *должен возвращать количество событий от определенного пользователя
     */
    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            boolean isCharDigit = Character.isDigit(strSplit[strSplit.length - 2].charAt(0));
            String eventSplit = null;
            if (isCharDigit) {
                eventSplit = strSplit[strSplit.length - 3];
            } else {
                eventSplit = strSplit[strSplit.length - 2];
            }
            if (after == null & before == null  & strSplit[1].equals(user)) {
                allUser.add(eventSplit);

            } else {
                Date date = getDate(strSplit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate & strSplit[1].equals(user)) {
                    allUser.add(eventSplit);
                }
            }
        }
        return allUser.size();
    }

    /**
     * должен возвращать пользователей с определенным IP
     */
    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            if (after == null & before == null & strSplit[0].equals(ip)) {
                allUser.add(strSplit[1]);

            } else {
                Date date = getDate(strSplit);
                boolean isBetweenDate = isBetweenDate(after, before, date);
                if (isBetweenDate & strSplit[0].equals(ip)) {
                    allUser.add(strSplit[1]);
                }
            }
        }
        return allUser;
    }

    public Set<String> getSetUserEvent(Date after, Date before, Event event) {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            Date date = getDate(strSplit);
            String[] eventSplit = strSplit[3].split("\\s");
            boolean isBetweenDate = false;
            switch (event) {
                case LOGIN:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.LOGIN.toString())) {
                        allUser.add(strSplit[1]);
                    }
                    break;
                case DOWNLOAD_PLUGIN:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.DOWNLOAD_PLUGIN.toString())) {
                        allUser.add(strSplit[1]);
                    }
                    break;
                case WRITE_MESSAGE:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.WRITE_MESSAGE.toString())) {
                        allUser.add(strSplit[1]);
                    }
                    break;
                case SOLVE_TASK:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.SOLVE_TASK.toString())) {
                        allUser.add(strSplit[1]);
                    }
                    break;
                case DONE_TASK:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.DONE_TASK.toString())) {
                        allUser.add(strSplit[1]);
                    }
                    break;
            }

        }
        return allUser;
    }

    public Set<String> getSetUserEventTaskNumber(Date after, Date before, Event event, int number) {
        Set<String> allUser = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            Date date = getDate(strSplit);
            int numberTsk = 0;
            String[] eventSplit = strSplit[3].split("\\s");
            boolean isBetweenDate = false;
            if(eventSplit.length > 1) {
                numberTsk = Integer.parseInt(eventSplit[1]);
            }
            switch (event) {
                case SOLVE_TASK:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.SOLVE_TASK.toString()) & numberTsk == number) {
                        allUser.add(strSplit[1]);
                    }
                    break;
                case DONE_TASK:
                    isBetweenDate = isBetweenDate(after, before, date);
                    if (isBetweenDate & eventSplit[0].equals(Event.DONE_TASK.toString()) & numberTsk == number) {
                        allUser.add(strSplit[1]);
                    }
                    break;
            }
        }
        return allUser;
    }

    /**
     *должен возвращать пользователей, которые делали логин
     */
    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return getSetUserEvent(after, before, Event.LOGIN);
    }

    /**
     * должен возвращать пользователей, которые скачали плагин
     */
    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return getSetUserEvent(after, before, Event.DOWNLOAD_PLUGIN);
    }


    /**
     * должен возвращать пользователей, которые отправили сообщение
     */
    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return getSetUserEvent(after, before, Event.WRITE_MESSAGE);
    }

    /**
     * должен возвращать пользователей, которые решали любую задачу
     */
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return getSetUserEvent(after, before, Event.SOLVE_TASK);
    }

    /**
     * должен возвращать пользователей, которые решали задачу с номером
     */
    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return getSetUserEventTaskNumber(after, before, Event.SOLVE_TASK, task);
    }

    /**
     *должен возвращать пользователей, которые решили любую задачу.
     */
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return getSetUserEvent(after, before, Event.DONE_TASK);
    }

    /**
     *должен возвращать пользователей, которые решили любую задачу с номером.
     */
    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return getSetUserEventTaskNumber(after, before, Event.DONE_TASK, task);
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (eventSplit[0].equals(event.toString()) & strSplit[1].equals(user) & isBetweenDate) {
                dates.add(getDate(strSplit));
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[strSplit.length - 1].equals(Status.FAILED.toString()) & isBetweenDate) {
                dates.add(date);
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[strSplit.length - 1].equals(Status.ERROR.toString()) & isBetweenDate) {
                dates.add(date);
            }
        }
        return dates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            Date date = getDate(strSplit);
            String[] eventSplit = strSplit[3].split("\\s");
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[1].equals(user) & eventSplit[0].equals(Event.LOGIN.toString()) & isBetweenDate) {
                dates.add(date);
            }
        }
        if(!dates.isEmpty()) {
            List<Date> list = new ArrayList<>(dates);
            Collections.sort(list);
            return list.get(0);
        } else {
            return null;
        }
//        return logRecords.stream()
//                .filter(r -> условия фильтрации)
//                .map(LogRecord::getDate)
//                .sorted()
//                .findFirst()
//                .orElse(null);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            int numberTsk = 0;
            if(eventSplit.length > 1) {
                numberTsk = Integer.parseInt(eventSplit[1]);
            }
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[1].equals(user) & eventSplit[0].equals(Event.SOLVE_TASK.toString()) & numberTsk == task
                    & isBetweenDate) {
                dates.add(date);
            }
        }
        if(!dates.isEmpty()) {
            List<Date> list = new ArrayList<>(dates);
            Collections.sort(list);
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            int numberTsk = 0;
            if(eventSplit.length > 1) {
                numberTsk = Integer.parseInt(eventSplit[1]);
            }
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[1].equals(user) & eventSplit[0].equals(Event.DONE_TASK.toString())  & numberTsk == task
                    & isBetweenDate) {
                dates.add(date);
            }
        }
        if(!dates.isEmpty()) {
            List<Date> list = new ArrayList<>(dates);
            Collections.sort(list);
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[1].equals(user) & eventSplit[0].equals(Event.WRITE_MESSAGE.toString()) & isBetweenDate) {
                dates.add(date);
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[1].equals(user) & eventSplit[0].equals(Event.DOWNLOAD_PLUGIN.toString()) & isBetweenDate) {
                dates.add(date);
            }
        }
        return dates;
    }


    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }


    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> allEvent = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (isBetweenDate) {
                allEvent.add(Event.valueOf(eventSplit[0]));
            }
        }
        return allEvent;
    }

    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> allEvent = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (ip.equals(strSplit[0]) & isBetweenDate) {
                allEvent.add(Event.valueOf(eventSplit[0]));
            }
        }
        return allEvent;
    }

    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> allEvent = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (user.equals(strSplit[1]) & isBetweenDate) {
                allEvent.add(Event.valueOf(eventSplit[0]));
            }
        }
        return allEvent;
    }

    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> allEvent = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[strSplit.length-1].equals(Status.FAILED.toString()) & isBetweenDate) {
                allEvent.add(Event.valueOf(eventSplit[0]));
            }
        }
        return allEvent;
    }

    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> allEvent = new HashSet<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (strSplit[strSplit.length-1].equals(Status.ERROR.toString()) & isBetweenDate) {
                allEvent.add(Event.valueOf(eventSplit[0]));
            }
        }
        return allEvent;
    }


    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        Map<Integer, Integer> map = getAllSolvedTasksAndTheirNumber(after, before);
        if (!map.isEmpty() & map.containsKey(task)) {
            return map.get(task);
        } else {
            return 0;
        }
    }


    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        Map<Integer, Integer> map = getAllDoneTasksAndTheirNumber(after, before);
        if (!map.isEmpty() & map.containsKey(task)) {
            return map.get(task);
        } else {
            return 0;
        }
    }

    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (eventSplit[0].equals(Event.SOLVE_TASK.toString()) & isBetweenDate) {
                Integer value = map.getOrDefault(Integer.parseInt(eventSplit[1]), 0) + 1;
                map.put(Integer.parseInt(eventSplit[1]), value);
            }
        }
        return map;
    }

    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> map = new HashMap<>();
        List<String> listLogs = readAllLogs();
        for (String line : listLogs) {
            String[] strSplit = line.split("\\t");
            String[] eventSplit = strSplit[3].split("\\s");
            Date date = getDate(strSplit);
            boolean isBetweenDate = isBetweenDate(after, before, date);
            if (eventSplit[0].equals(Event.DONE_TASK.toString()) & isBetweenDate) {
                Integer value = map.getOrDefault(Integer.parseInt(eventSplit[1]), 0) + 1;
                map.put(Integer.parseInt(eventSplit[1]), value);
            }
        }
        return map;
    }
}