package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = java.util.ResourceBundle.getBundle(CashMachine .class.getPackage().getName() + ".resources.common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String text = bis.readLine();
            if ("EXIT".equalsIgnoreCase(text)) {
                ConsoleHelper.writeMessage(res.getString("the.end"));
                throw new InterruptOperationException();
            }
            return text;
        } catch (IOException e) {

        }
        return null;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String code = null;
        while (true) {
            writeMessage(res.getString("choose.currency.code"));
            code = readString();
            if (code.length() == 3) {
                return code.toUpperCase();
            }
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        boolean marker = true;
        while (true) {
            try {
            writeMessage(res.getString("choose.denomination.and.count.format"));
            String nominalCount = readString();
            String[] split = null;
            if (nominalCount == null || (split = nominalCount.split("\\s+")).length != 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            if (Integer.parseInt(split[0]) < 0 || Integer.parseInt(split[1]) < 0 ) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            return split;
            } catch (NumberFormatException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
        }
    }

    public static Operation askOperation() throws InterruptOperationException {
        while (true) {
            try {
                writeMessage(res.getString("operation.INFO"));
                writeMessage(res.getString("operation.DEPOSIT"));
                writeMessage(res.getString("operation.WITHDRAW"));
                writeMessage(res.getString("operation.EXIT"));
                writeMessage(res.getString("choose.operation"));
                String numberString = readString();
                int number = 0;
                if (numberString == null || (number = Integer.parseInt(numberString)) < 0) {
                    continue;
                }
                return Operation.getAllowableOperationByOrdinal(number);
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }

}
