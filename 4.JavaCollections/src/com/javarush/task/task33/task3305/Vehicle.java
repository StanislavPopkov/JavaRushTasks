package com.javarush.task.task33.task3305;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY,  property="className")
@JsonSubTypes({
        @JsonSubTypes.Type(value= RacingBike.class ), // name = "RacingBike" ,имя не используется, можно задать любое,
        @JsonSubTypes.Type(value= Motorbike.class), //но оно не будет выводиться
        @JsonSubTypes.Type(value= Car.class)})
public abstract class Vehicle {
    protected String name;
    protected String owner;
    protected int age;
}