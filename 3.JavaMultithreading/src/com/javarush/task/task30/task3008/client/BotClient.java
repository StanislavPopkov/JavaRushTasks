package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BotClient extends Client {

    @Override
    protected String getUserName() {
        return "date_bot_"+ (int) (Math.random()*100);
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

        public class BotSocketThread extends Client.SocketThread{



            @Override
            protected void clientMainLoop() throws IOException, ClassNotFoundException {
                BotClient.this.sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
                super.clientMainLoop();
            }

            @Override
            protected void processIncomingMessage(String message) {
                    //super.processIncomingMessage(message);
                ConsoleHelper.writeMessage(message);
                if (message != null && message.contains(": ")) {
                    HashSet<String> set = new HashSet<>();
                    Collections.addAll(set, "дата", "день", "месяц", "год", "время", "час", "минуты", "секунды");
                    String[] mass = message.split(":");
                    String operation = mass[1].trim().toLowerCase();
                    //ConsoleHelper.writeMessage(operation);
                    if (set.contains(operation)) {
                        Calendar calendar = new GregorianCalendar();
                        SimpleDateFormat dformat1 = new SimpleDateFormat("d.MM.YYYY", Locale.ENGLISH);
                        SimpleDateFormat dformat2 = new SimpleDateFormat("d", Locale.ENGLISH);
                        SimpleDateFormat dformat3 = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                        SimpleDateFormat dformat4 = new SimpleDateFormat("YYYY", Locale.ENGLISH);
                        SimpleDateFormat dformat5 = new SimpleDateFormat("H:mm:ss", Locale.ENGLISH);
                        SimpleDateFormat dformat6 = new SimpleDateFormat("H", Locale.ENGLISH);
                        SimpleDateFormat dformat7 = new SimpleDateFormat("m", Locale.ENGLISH);
                        SimpleDateFormat dformat8 = new SimpleDateFormat("s", Locale.ENGLISH);
                        switch (operation) {
                            case "дата":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat1.format(calendar.getTime()));
                                break;
                            case "день":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat2.format(calendar.getTime()));
                                break;
                            case "месяц":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat3.format(calendar.getTime()));
                                break;
                            case "год":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat4.format(calendar.getTime()));
                                break;
                            case "время":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat5.format(calendar.getTime()));
                                break;
                            case "час":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat6.format(calendar.getTime()));
                                break;
                            case "минуты":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat7.format(calendar.getTime()));
                                break;
                            case "секунды":
                                sendTextMessage("Информация для " + mass[0] + ": " + dformat8.format(calendar.getTime()));
                                break;
                        }
                    }
                }
            }
        }

    public static void main(String args[]){
        BotClient bot = new BotClient();
        bot.run();
        //SocketThread som =  bot.getSocketThread();

    }
}
