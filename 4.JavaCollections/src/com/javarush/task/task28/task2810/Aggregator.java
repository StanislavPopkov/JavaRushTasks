package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.*;
import com.javarush.task.task28.task2810.view.HtmlView;
import com.javarush.task.task28.task2810.view.View;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Aggregator {

    public static void main(String[] args) {
        Strategy hhStrategy = new HHStrategy();
        Provider hhProvider = new Provider(hhStrategy);
        Strategy moiKrugStrategy = new MoikrugStrategy();
        Provider moiKrugProvider = new Provider(moiKrugStrategy);
        HtmlView  view = new HtmlView();
        Model model = new Model(view, hhProvider, moiKrugProvider);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
