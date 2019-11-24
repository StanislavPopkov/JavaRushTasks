package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper {

    public static String generateRandomString(){
        return new BigInteger(130, new SecureRandom()).toString(36);

//        List<BigInteger> bigIntegerList = new ArrayList<>();
//        int targetStringLength = 36;
//        BigInteger maxLimitLetter = new BigInteger("122");
//        BigInteger minLimitLetter = new BigInteger("97");
//        BigInteger maxLimitDigit = new BigInteger("57");
//        BigInteger minLimitDigit = new BigInteger("48");
//        BigInteger bigIntegerLetter = maxLimitLetter.subtract(minLimitLetter);
//        BigInteger bigIntegerDigit = maxLimitDigit.subtract(minLimitDigit);
//        SecureRandom random = new SecureRandom();
//        int lenLet = maxLimitLetter.bitLength();
//        int lenDig = maxLimitDigit.bitLength();
//        StringBuilder buffer = new StringBuilder();
//        for (int i = 0; i < targetStringLength; i++) {
//            BigInteger resLet = new BigInteger(lenLet, random);
//            if (resLet.compareTo(minLimitLetter) < 0)
//                bigIntegerLetter.add(resLet.add(minLimitLetter));
//            if (resLet.compareTo(bigIntegerLetter) >= 0)
//                bigIntegerList.add(resLet.mod(bigIntegerLetter).add(minLimitLetter));
//
//            BigInteger resDig = new BigInteger(lenDig, random);
//            if (resDig.compareTo(minLimitDigit) < 0)
//                bigIntegerLetter.add(resDig.add(minLimitDigit));
//            if (resDig.compareTo(bigIntegerLetter) >= 0)
//                bigIntegerList.add(resDig.mod(bigIntegerDigit).add(minLimitDigit));
//        }
//        Collections.shuffle(bigIntegerList);
//        bigIntegerList.stream().forEach(b -> buffer.append((char) b.intValue()));
//        return buffer.toString();
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
