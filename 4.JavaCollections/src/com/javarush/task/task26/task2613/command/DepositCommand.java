package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class DepositCommand implements Command{
    private ResourceBundle res = java.util.ResourceBundle.getBundle(RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        while (true) {
            try {
                String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(code);
                int denomination = Integer.parseInt(validTwoDigits[0]);
                int count = Integer.parseInt(validTwoDigits[1]);
                if (denomination == 0 || count == 0) {
                    continue;
                }
                manipulator.addAmount(denomination, count);
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination * count, code));
                break;
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
    }
}
