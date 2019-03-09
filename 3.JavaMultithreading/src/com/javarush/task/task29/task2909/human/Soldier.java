package com.javarush.task.task29.task2909.human;

public class Soldier extends Human {
    private BloodGroup bloodGroup;

    public Soldier(String name, int age) {
        super(name, age);
    }
/*
    public void setBloodGroup(int code) {
        if(code == 1) bloodGroup = BloodGroup.first();
        else if(code == 2) bloodGroup = BloodGroup.second();
        else if(code == 3) bloodGroup = BloodGroup.third();
        else if(code == 4) bloodGroup = BloodGroup.fourth();

    }

    public int getBloodGroup() {
        return bloodGroup.getCode();
    }*/

    public void live() {
        fight();
    }

    public void fight() {
    }
}
