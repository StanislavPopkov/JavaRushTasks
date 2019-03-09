package com.javarush.task.task32.task3205;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {
    private SomeInterfaceWithMethods object;

    public CustomInvocationHandler(SomeInterfaceWithMethods object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+" in");
        if(method.getReturnType().getSimpleName().equals("String")) {
            String result = (String)method.invoke(object, args);
            System.out.println(method.getName() + " out");
            return result;
        }
        else {
            method.invoke(object, args);
            System.out.println(method.getName()+" out");
            return null;
        }

    }

}
