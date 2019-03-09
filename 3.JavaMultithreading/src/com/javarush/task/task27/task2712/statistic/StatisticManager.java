package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager instance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();


    private StatisticManager() {
    }

    public static StatisticManager getInstance(){
        return instance;
    }

    public void register(EventDataRow data){
        statisticStorage.put(data);
    }




    public Map<String, Double> getProfitVideoData(){
        List<EventDataRow> list = statisticStorage.get(EventType.SELECTED_VIDEOS);
        TreeMap<String, Double> map = new TreeMap<>();
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for(EventDataRow even : list){
            VideoSelectedEventDataRow vid = (VideoSelectedEventDataRow) even;
            if(map.containsKey(dFormat.format(vid.getDate()))){
                double temp = map.get(dFormat.format(vid.getDate()));
                //System.out.println("Дата !0"+dFormat.format(vid.getDate())+" "+(double)vid.getAmount()/100);
                map.put( dFormat.format(vid.getDate()), temp + (double)vid.getAmount()/100);
                //System.out.println(map.get("25-Feb-2019"));
            }
            else {
                //System.out.println("Дата 0"+dFormat.format(vid.getDate())+" "+(double)vid.getAmount()/100);
                map.put(dFormat.format(vid.getDate()), (double)vid.getAmount()/100);

            }
        }
        return map;
    }

    public Map<Date,Map<String,Integer>> getCookTimeWork() {
        Map<Date,Map<String,Integer>> res = new TreeMap<>(Collections.reverseOrder());

        for(EventDataRow data : statisticStorage.get(EventType.COOKED_ORDER))
        {
            CookedOrderEventDataRow cookData = (CookedOrderEventDataRow) data;
            // Дата записи
            Date shortDate = getLocalDate(cookData.getDate());
            // Данные о тек. записи (повар и его время)
            String cookName = cookData.getCookName();
            int curTime = cookData.getTime();
            Map<String,Integer> curDate;

            // Ищем запись о дате в мапе :
            if(!res.containsKey(shortDate))
            {
                // Записей в результате о текущей дате нет, добавляем
                curDate = new TreeMap<>();
                res.put(shortDate,curDate);
            }
            else
            {
                // Получим текущую сабмапу для этой даты :
                curDate = res.get(shortDate);
            }
            // Ищем нужного нам повара и заполняем (или добавляем) данные
            int temp = curDate.containsKey(cookName) ? curDate.get(cookName) + curTime : curTime;
            curDate.put(cookName,temp);
            res.put(shortDate,curDate);
        }

        return res;
    }
    private Date getLocalDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private class StatisticStorage{
        Map<EventType, List<EventDataRow>> storage;

        StatisticStorage() {

            this.storage = new HashMap<>();
            EventType[] event = EventType.values();
            for(EventType eventType : event){
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data){

                List<EventDataRow> list = storage.get(data.getType());
                list.add(data);
                //System.out.println("Добавлено событие "+data.getType()+" "+ list.toString());
                storage.put(data.getType(), list);

        }

        private List<EventDataRow> get(EventType type){
            return storage.get(type);
        }
    }


}
