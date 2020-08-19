package com.javarush.task.task38.task3811;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Ticket {
    String[] s= new String[]{"dfd"};
    String[] tags() default {};
    String createdBy() default "Amigo";
    Priority priority () default Priority.MEDIUM;

    enum Priority{
        LOW,
        MEDIUM,
        HIGH;
    }
}


