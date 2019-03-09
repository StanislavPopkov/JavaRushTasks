package com.javarush.task.task32.task3208;



import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
RMI-2
*/
public class Solution {
    public static Registry registry;
    public static final String UNIC_BINDING_NAME = "cat";
    public static final String UNIC_BINDING_NAME2 = "dog";

    // Pretend we're starting an RMI client as the CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                for (String bindingName : registry.list()) {
                    Animal service = (Animal) registry.lookup(bindingName);
                    service.printName();
                    service.speak();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    });

    // Pretend we're starting an RMI server as the SERVER_THREAD thread
    public static Thread SERVER_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {

            Remote tab = null;
            Remote tab2 = null;
            try {
                final Animal cat = new Cat("Murzic");
                final Animal dog = new Dog("Barbos");
                registry = LocateRegistry.createRegistry(2099);
                tab = UnicastRemoteObject.exportObject(cat, 0);
                tab2 = UnicastRemoteObject.exportObject(dog, 0);
                registry.bind(UNIC_BINDING_NAME, tab);
                registry.bind(UNIC_BINDING_NAME2, tab2);
                Thread.sleep(2000);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    });

    public static void main(String[] args) throws InterruptedException {
        // Starting an RMI server thread
        SERVER_THREAD.start();
        Thread.sleep(1000);
        // Start the client
        CLIENT_THREAD.start();


    }
}