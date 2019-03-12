package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@XmlRootElement(name = "shop")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Shop {
    //@XmlElement
    public Goods goods;
    //@XmlElement(name = "count")
    public int count;
    //@XmlElement(name = "profit")
    public double profit;
    //@XmlElement(name = "secretData")
    public String [] secretData;

    //@XmlType( name = "goods")
    public static class Goods{
        //@XmlElement
        public List<String> names = new ArrayList<>();

        @Override
        public String toString() {
            return "Goods{" +
                    "names=" + names +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Shop{" +
                "count=" + count +
                ", profit=" + profit +
                ", goods=" + goods +
                ", secretData=" + Arrays.toString(secretData) +
                '}';
    }
}
