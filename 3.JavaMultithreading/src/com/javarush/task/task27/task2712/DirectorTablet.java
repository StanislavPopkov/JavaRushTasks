package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit(){
       Map<String, Double> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        map.putAll(StatisticManager.getInstance().getProfitVideoData());
        double total = 0.0;
        for(Map.Entry<String, Double> entry: map.entrySet()){
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"%s - %.2f", entry.getKey(), entry.getValue()));
            total += entry.getValue();
        }
        if(total > 0.0)ConsoleHelper.writeMessage(String.format(Locale.ENGLISH,"%s - %.2f", "Total", total));
    }

    public void printCookWorkloading(){
        Map<Date,Map<String,Integer>> res = StatisticManager.getInstance().getCookTimeWork();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        for(Map.Entry<Date,Map<String,Integer>> entry : res.entrySet())
        {
            // Дата
            ConsoleHelper.writeMessage(String.format("%s",sdf.format(entry.getKey())));
            // Статистика по поварам
            for(Map.Entry<String,Integer> cookStat : entry.getValue().entrySet())
            {
                ConsoleHelper.writeMessage(cookStat.getKey() + " - " + (int)(Math.ceil(cookStat.getValue()/60f)) + " min");
            }
            ConsoleHelper.writeMessage("");
        }
    }


    public void printActiveVideoSet(){
        Map<String,Integer> map = StatisticAdvertisementManager.getInstance().getVideo();
        for(Map.Entry<String,Integer> entry : map.entrySet()) {
            if(entry.getValue() != 0)
                ConsoleHelper.writeMessage(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void printArchivedVideoSet(){
        //ConsoleHelper.writeMessage("");
        Map<String,Integer> map = StatisticAdvertisementManager.getInstance().getVideo();
        for(Map.Entry<String,Integer> entry : map.entrySet()) {
            if(entry.getValue() == 0)
                ConsoleHelper.writeMessage(entry.getKey());
        }
    }

}
