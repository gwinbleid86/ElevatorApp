package com.company;

import java.util.Random;

public class Passenger {
    private final int wishedFloor;
    private final boolean isDown;

    public int getWishedFloor() {
        return wishedFloor;
    }

    public boolean isDown() {
        return isDown;
    }

    public Passenger(int maxFloor, int currentFloor) {
        Random rnd = new Random();
        while (true){
            int random = rnd.nextInt(maxFloor);
            if (random != currentFloor) {
                wishedFloor = random;
                break;
            }
        }
        isDown = wishedFloor < currentFloor;
    }
}
