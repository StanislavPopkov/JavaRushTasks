package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {
    final AdvertisementStorage storage;
    int timeSeconds;
    private ArrayList<List<Advertisement>> bigList = new ArrayList<List<Advertisement>>();

    public AdvertisementManager(int timeSeconds) {
        storage = AdvertisementStorage.getInstance();
        this.timeSeconds = timeSeconds;
    }

    private static long getAmountOfList(List<Advertisement> list)
    {
        long res = 0;
        for(Advertisement a : list)
            res += a.getAmountPerOneDisplaying();
        return res;
    }

    private static int getDurationOfList(List<Advertisement> list)
    {
        int res = 0;
        for(Advertisement a : list)
            res += a.getDuration();
        return res;
    }

    private void permutate(ArrayList<Advertisement> list)
    {
        // Очищаем статический массив комбинаций
        bigList = new ArrayList<List<Advertisement>>();
        for (int i = 1; i < (int)(Math.pow(2,list.size())); i++)
        {
            // Текущая выборка
            List<Advertisement> curList = new ArrayList<>();

            // Строка - битовое поле, задающее номера элементов в списке элементов для текущей выборки
            // Для удобства переворачиваем ее
            String binaryField = Integer.toBinaryString(i).toString();
            while(binaryField.length() < list.size())
                binaryField = "0" + binaryField;
            binaryField = new StringBuffer(binaryField).reverse().toString();
            // Проходим строку от начала до конца
            // Индекс '1' - индикатор добавления элемента list.get(j) в тек. выборку
            for (int j = 0; j < binaryField.length(); j++)
            {
                if(binaryField.charAt(j) == '1')
                {
                    // Добавляем текущий элемент в тек. выборку
                    curList.add(list.get(j));
                }
            }
            // Тек. выборку собрали , перед добавлением в общий список проверим ,
            // не привышает ли она длительность заказа. Если превышает, то она нам не нужна
            if(getDurationOfList(curList) <= timeSeconds)
                bigList.add(curList);
        }
    }

    public void processVideos() throws NoVideoAvailableException{
        {
            if(storage.list().isEmpty())
                throw new NoVideoAvailableException();

            // Список подходящих по длительности и кол-ву показов роликов
            List<Advertisement> sourceList = new ArrayList<>();
            for(Advertisement a : storage.list())
            {
                if(a.getDuration() <= timeSeconds && a.getHits() > 0)
                    sourceList.add(a);
            }
            if(sourceList.isEmpty())
                throw new NoVideoAvailableException();

            // Получаем все возможные выборки (кроме пустой) с учетом общего времени (неподходящие не добавляем)
            permutate((ArrayList<Advertisement>) sourceList);

            // Еще одна проверка на существование подходящих списков роликов
            if(bigList.isEmpty())
                throw new NoVideoAvailableException();

            // Сортируем полученный список по "дороговизне" (по длительности, по кол-ву роликов) по убыванию
            Collections.sort(bigList, new Comparator<List<Advertisement>>()
            {
                @Override
                public int compare(List<Advertisement> o1, List<Advertisement> o2)
                {
                    // По стоимости списков
                    if(getAmountOfList(o1) > getAmountOfList(o2))
                        return(-1);
                    if(getAmountOfList(o1) < getAmountOfList(o2))
                        return (1);

                    // По суммарной длительности
                    if(getDurationOfList(o1) > getDurationOfList(o2))
                        return (-1);
                    if(getDurationOfList(o1) < getDurationOfList(o2))
                        return (1);

                    // По кол-ву роликов в списке
                    if(Integer.compare(o1.size(),o2.size()) == 1)
                        return (1);
                    if(Integer.compare(o1.size(),o2.size()) == -1)
                        return (-1);

                    return (0);
                }
            });

            // Первый элемент из отсортированного списка - нужный нам, досортировываем
            Collections.sort(bigList.get(0), new Comparator<Advertisement>()
            {
                @Override
                public int compare(Advertisement o1, Advertisement o2)
                {
                    // По стоимости показа
                    if(o1.getAmountPerOneDisplaying() > o2.getAmountPerOneDisplaying())
                        return (-1);
                    if(o1.getAmountPerOneDisplaying() < o2.getAmountPerOneDisplaying())
                        return (1);

                    // По стоимости секунды
                    if(Long.compare(o1.getAmountPerSecond(),o2.getAmountPerSecond()) == 1)
                        return (1);
                    if(Long.compare(o1.getAmountPerSecond(),o2.getAmountPerSecond()) == -1)
                        return (-1);

                    return (0);
                }
            });

            // Регистратор событий
            List<Advertisement> advertisement = bigList.get(0);
            StatisticManager.getInstance().register(new VideoSelectedEventDataRow(advertisement,
                    getAmountOfList(advertisement),
                    getDurationOfList(advertisement)));

            // Выводим на экран
            for (Advertisement a : advertisement)
            {
                // First Video is displaying... 50, 277
                ConsoleHelper.writeMessage(a.getName() + " is displaying... " + a.getAmountPerOneDisplaying() + ", " + a.getAmountPerSecond());
                a.revalidate();
            }
        }
}
    }
