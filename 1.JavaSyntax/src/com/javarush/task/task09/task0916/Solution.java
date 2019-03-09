package com.javarush.task.task09.task0916;

import java.io.IOException;
import java.rmi.RemoteException;

/* 
Перехват checked-исключений
*/

public class Solution {
    public static void main(String[] args) {
        handleExceptions(new Solution());

    }

    public static void handleExceptions(Solution obj) {
        try {
            System.out.println("Ку1");
            //obj.method1();
            System.out.println("Ку2");
            //obj.method2();
            //obj.method3();
        } catch (Exception  e) {
            System.out.println(e);
        }/* catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchFieldException e) {
            System.out.println(e);
        }*/
    }

    public void method1() throws IOException {
        throw new IOException();
    }

    public void method2() throws NoSuchFieldException {
        throw new NoSuchFieldException();
    }

    public void method3() throws RemoteException {
        throw new RemoteException();
    }
}
