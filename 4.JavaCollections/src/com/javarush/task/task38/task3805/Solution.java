package com.javarush.task.task38.task3805;

/* 
Улучшения в Java 7 (multiple exceptions)
*/

public class Solution {
    private final Connection connection;

    public Solution() throws Exception {
        try {
            connection = new ConnectionMock();
            connection.connect();
        } catch (WrongDataException | ConnectionException e) {
            throw new Exception("WrongDataException: " + e.getMessage());
        }
    }


    public void write(Object data) throws Exception {
        try {
            connection.write(data);
        } catch (WrongDataException | ConnectionException e) {
            throw new Exception("WrongDataException: " + e.getMessage());
        }
    }

    public Object read() throws Exception {
        try {
            return connection.read();
        } catch (WrongDataException | ConnectionException e) {
            throw new Exception("WrongDataException: " + e.getMessage());
        }
    }

    public void disconnect() throws Exception {
        try {
            connection.disconnect();
        } catch (WrongDataException | ConnectionException e) {
            throw new Exception("WrongDataException: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}
