package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

import static com.javarush.task.task26.task2613.CashMachine.RESOURCE_PATH;

class WithdrawCommand implements Command{
    private ResourceBundle res = java.util.ResourceBundle.getBundle(RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        while(true) {
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String sum = ConsoleHelper.readString();
                int sumInt = 0;
                if (sum == null || sum.isEmpty() || (sumInt = Integer.parseInt(sum)) <= 0) {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                    continue;
                }
                if (manipulator.isAmountAvailable(sumInt)) {
                    Map<Integer, Integer> withdrawAmountMap = manipulator.withdrawAmount(sumInt);
                    ArrayList<Integer> sortArrayList = new ArrayList(withdrawAmountMap.keySet());
                    Collections.sort(sortArrayList, Comparator.reverseOrder());
                    for (Integer key : sortArrayList) {
                        ConsoleHelper.writeMessage("\t" + key + " - " + withdrawAmountMap.get(key));
                    }
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sumInt, code));
                    break;
                } else {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                }

            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }

        }
    }
}
