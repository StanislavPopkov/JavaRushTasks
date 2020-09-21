package com.javarush.task.task39.task3906;

/* 
Интерфейсы нас спасут!
*/
public class Solution {
    public static void main(String[] args) {
        Switchable securitySystem = new SecuritySystem();
        ElectricPowerSwitch electricPowerSwitch = new ElectricPowerSwitch(securitySystem);

        electricPowerSwitch.press();
        electricPowerSwitch.press();

        Switchable lightBulb = new LightBulb();
        ElectricPowerSwitch electricPowerSwitch2 = new ElectricPowerSwitch(lightBulb);

        electricPowerSwitch2.press();
        electricPowerSwitch2.press();
    }
}
