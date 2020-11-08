package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class InfoCommand implements Command{
    private ResourceBundle res = java.util.ResourceBundle.getBundle(RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));
        Collection<CurrencyManipulator> allCurrencyManipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        boolean hasMoney = false;
        for (CurrencyManipulator manipulator : allCurrencyManipulators) {
            if (manipulator.hasMoney()) {
                ConsoleHelper.writeMessage("\t" + manipulator.getCurrencyCode() + " - " + manipulator.getTotalAmount());
                hasMoney = true;
            }
        }
        if (!hasMoney) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
