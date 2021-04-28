package com.company.util;

import java.util.Random;

public class Generator {
    private static Random rnd = new Random();
    public static int getRandomFromRange(int start, int end){
        return rnd.nextInt(end)+start;
    }
}
