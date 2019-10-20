package com.javarush.task.task36.task3601;

import java.util.List;

public class Controller {

    Service service = new Service();

    public List<String> onShowDataList() {
        return service.getStringDataList();
    }
}
