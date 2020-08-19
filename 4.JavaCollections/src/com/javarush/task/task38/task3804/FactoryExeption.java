package com.javarush.task.task38.task3804;

public class FactoryExeption {

    public static Throwable getException(Enum message) throws Exception {
        if (ApplicationExceptionMessage.SOCKET_IS_CLOSED.equals(message) ||
                ApplicationExceptionMessage.UNHANDLED_EXCEPTION.equals(message)) {
            String editedMessage = message.name().toLowerCase().replace("_", " ");
            String first = editedMessage.substring(0, 1).toUpperCase().concat(editedMessage.substring(1));
            throw new Exception(first);
        } else if (DatabaseExceptionMessage.NO_RESULT_DUE_TO_TIMEOUT.equals(message) ||
        DatabaseExceptionMessage.NOT_ENOUGH_CONNECTIONS.equals(message)) {
            String editedMessage = message.name().toLowerCase().replace("_", " ");
            String first = String.valueOf(Character.toUpperCase(editedMessage.charAt(0)));
            throw new RuntimeException(first.concat(editedMessage.substring(1)));
        } else if (UserExceptionMessage.USER_DOES_NOT_EXIST.equals(message) ||
        UserExceptionMessage.USER_DOES_NOT_HAVE_PERMISSIONS.equals(message)){
            String editedMessage = message.name().toLowerCase().replace("_", " ");
            String first = String.valueOf(Character.toUpperCase(editedMessage.charAt(0)));
            throw  new Error(first.concat(editedMessage.substring(1)));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
