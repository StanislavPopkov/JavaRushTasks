package com.javarush.task.task36.task3602;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
//        Class cls = null;
//        List<String> list = new LinkedList<>();
//        Stack<String> se = new Stack<>();
//        se.get(2);
//        try {
//            cls = Class.forName("java.util.LinkedList");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Class[] classes = Collectors.class.getDeclaredClasses();
//        return Arrays.stream(classes).filter(i -> i.getSimpleName().equalsIgnoreCase("Node"))
//                .collect(Collectors.toList()).get(0);
//        SortedSet<String> set = new TreeSet<String>();
//        Set unmodsortset = Collections.unmodifiableSortedSet(set);
//        unmodsortset.
        Class result = null;
        Class[] classes = Collections.class.getDeclaredClasses();
        List<Class> list = new ArrayList<>();
        for(Class myClass: classes){
            if(Arrays.stream(myClass.getInterfaces()).anyMatch(i -> i.toString().equalsIgnoreCase("interface java.util.List")) |
                    Arrays.stream(myClass.getSuperclass().getInterfaces()).anyMatch(i -> i.toString().equalsIgnoreCase("interface java.util.List"))){
                list.add(myClass);
            }
        }
        for(Class myClass: list){
            result = myClass;

            //List<Class> list = Arrays.stream(myClass.getInterfaces()).filter(i -> i.toString().equalsIgnoreCase("interface java.util.List")).collect(Collectors.toList());
            //boolean k = Arrays.stream(myClass.getInterfaces()).anyMatch(i -> i.toString().equalsIgnoreCase("interface java.util.List"));
            if(Modifier.isPrivate(myClass.getModifiers()) & Modifier.isStatic(myClass.getModifiers())){
                try {
                    Constructor constructor = myClass.getDeclaredConstructor();
                    constructor.setAccessible(true);

                    Method method = myClass.getDeclaredMethod("get", int.class);
                    method.setAccessible(true);

                    method.invoke(constructor.newInstance(), 3);
                }catch (InvocationTargetException e){
                    if (e.getCause().toString().contains("IndexOutOfBoundsException")) {
                        break;
                    }
                }catch (Exception e){
                    continue;
                }
        }
        }
        return result;
    }
}
