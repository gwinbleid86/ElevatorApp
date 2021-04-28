package com.company.entity;

import com.company.util.Generator;

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
        while (true){
            int random = Generator.getRandomFromRange(0, maxFloor);
            if (random != currentFloor) {
                wishedFloor = random;
                break;
            }
        }
        isDown = wishedFloor < currentFloor;
    }
}
