package com.javarush.task.task30.task3008;

import com.javarush.task.task30.task3008.client.BotClient;
import com.javarush.task.task30.task3008.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread{
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            while (true){
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message answer = connection.receive();
                if (answer.getType() != MessageType.USER_NAME) continue;
                String userName = answer.getData();
                if (userName == null || userName.isEmpty()) continue;
                if (connectionMap.containsKey(userName)) continue;
                connectionMap.put(userName, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return userName;
            }
        }
        private void notifyUsers(Connection connection, String userName) throws IOException{
            for(String str : connectionMap.keySet()){
                Message message = new Message(MessageType.USER_ADDED, str);
                if(str != userName) connection.send(message);
            }
        }

        private void serverMainLoop(Connection connection, String userName) {
            while (true) {
                try {
                    Message mes = connection.receive();
                    if (mes.getType() == MessageType.TEXT) {
                        String text = userName + ": " + mes.getData();
                        sendBroadcastMessage(new Message(MessageType.TEXT, text));
                    } else ConsoleHelper.writeMessage("Ошибка типа сообщения");
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public void run(){
            ConsoleHelper.writeMessage("Установлено соединение с адресом: "+ socket.getRemoteSocketAddress());
            String userName = null;
            try(Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
            }catch (ClassNotFoundException | IOException e){
                ConsoleHelper.writeMessage("Произошла ошибка при обмене с данными");
            }finally {
                if (userName != null && !userName.isEmpty()) {
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                }
                ConsoleHelper.writeMessage("Соединение с сервером закрыто.");
            }

        }
    }
    public static void main(String args[]){

            ConsoleHelper.writeMessage("Введите порт сервера");
        try (ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())){
            ConsoleHelper.writeMessage("Сервер запущен");
            Socket clientSocket;

            while (true) {
                clientSocket = serverSocket.accept();
                new Handler(clientSocket).start();
            }
        }catch (IOException e){
            ConsoleHelper.writeMessage(e.getMessage());
        }
        Client.main(args);
        BotClient.main(args);

    }

    public static void sendBroadcastMessage(Message message){
        for(String str : connectionMap.keySet()){
            try {
                connectionMap.get(str).send(message);
            }catch (IOException e){
                ConsoleHelper.writeMessage("Не получилось отправить сообщение");
            }
        }
    }
}
