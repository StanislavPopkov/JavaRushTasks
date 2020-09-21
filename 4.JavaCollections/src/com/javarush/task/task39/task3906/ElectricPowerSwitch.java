package com.javarush.task.task39.task3906;

public class ElectricPowerSwitch {
    private Switchable system;

    public ElectricPowerSwitch(Switchable securitySystem) {
        this.system = securitySystem;
    }

    public void press() {
        System.out.println("Power switch flipped.");
        if (system.isOn()) {
            system.turnOff();
        } else {
            system.turnOn();
        }
    }
}
