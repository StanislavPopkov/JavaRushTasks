package com.javarush.task.task34.task3412;

import org.slf4j.Logger;
import org.slf4j.*;

import java.util.Date;

/* 
Добавление логирования в класс
*/

public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        logger.debug("Constructor 1:"+this.value1+" 2: "+this.value2+" 3: "+this.value3);
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static void main(String[] args) {

    }

    public void calculateAndSetValue3(long value) {
        logger.info("metod calculateAndSetValue3, param="+value);
        value -= 133;
        if (value > Integer.MAX_VALUE) {
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("metod calculateAndSetValue3, value > Integer.MAX_VALUE, new value1="+value1);
        } else {
            value1 = (int) value;
            logger.debug("metod calculateAndSetValue3, value < Integer.MAX_VALUE, new value1="+value1);
        }
    }

    public void printString() {
        logger.info("metod printString log, value2.length()="+value2.length());
        if (value2 != null) {
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        logger.info("metod printDateAsLong log, value3.getTime()="+value3.getTime());
        if (value3 != null) {
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        logger.info("method divide, number1 / number2 ="+number1 / number2);
        try {
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            logger.error("Arithmetic error", e);
        }
    }

    public void setValue1(int value1) {
        logger.debug("Change field value1, new value1="+value1);
        this.value1 = value1;
    }

    public void setValue2(String value2) {
        logger.debug("Change field value2, new value12="+value2);
        this.value2 = value2;
    }

    public void setValue3(Date value3) {
        logger.debug("Change field value3, new value3="+value3);
        this.value3 = value3;
    }
}
