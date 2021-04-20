package com.company;

import java.util.Random;

public class Passenger {
    private final int wishedFloor;

    public int getWishedFloor() {
        return wishedFloor;
    }

    public Passenger(int maxFloor, int currentFloor) {
        Random rnd = new Random();
        while (true){
            int random = rnd.nextInt(maxFloor)+1;
            if (random != currentFloor) {
                wishedFloor = random;
                break;
            }
        }
    }
}
