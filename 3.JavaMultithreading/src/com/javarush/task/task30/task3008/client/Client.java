package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;
import com.sun.org.apache.xerces.internal.parsers.CachingParserPool;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress(){
        return ConsoleHelper.readString();
    }

    protected int getServerPort(){
        return ConsoleHelper.readInt();
    }

    protected String getUserName(){
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            connection.send(new Message(MessageType.TEXT, text));
        }catch (IOException e){
            ConsoleHelper.writeMessage("Ошибка отправки сообщения на сервер");
            clientConnected = false;
        }
    }

    public void run(){
        try {
            SocketThread sThread = getSocketThread();
            sThread.setDaemon(true);
            sThread.start();
            synchronized (this) {
                    this.wait();
            }
            if (clientConnected)
                ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
            else {
                ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
            }
            String consoleText = null;
            do {
                consoleText = ConsoleHelper.readString();
                if (shouldSendTextFromConsole()) sendTextMessage(consoleText);

            }
            while (clientConnected & !consoleText.equals("exit"));
        }catch (InterruptedException e){
            ConsoleHelper.writeMessage("Ошибка прерывания потока");
        }

    }
    public class SocketThread extends Thread{
        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage(userName+" присоединился к чату");
        }

        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage(userName+ " покинул чат");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            synchronized (Client.this){
                Client.this.clientConnected = clientConnected;
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message mes = connection.receive();
                if (mes.getType() == MessageType.NAME_REQUEST) {
                    String name = getUserName();
                    connection.send(new Message(MessageType.USER_NAME, name));
                }
                else if (mes.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    break;
                } else throw new IOException("Unexpected MessageType");

            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException{
            while (true) {
                Message mes = connection.receive();
                if (mes.getType() == MessageType.TEXT) {
                    processIncomingMessage(mes.getData());
                }
                else if (mes.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(mes.getData());
                }
                else if (mes.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(mes.getData());
                }
                else throw new IOException("Unexpected MessageType");

            }
        }

        public void run(){
            try {
                Socket clientSocket = new Socket(getServerAddress(), getServerPort());
                Client.this.connection = new Connection(clientSocket);
                clientHandshake();
                clientMainLoop();
            }catch(ClassNotFoundException | IOException e){
                notifyConnectionStatusChanged(false);
            }
        }

    }

    public static void main(String args[]){
        Client client = new Client();
        client.run();
    }
}
