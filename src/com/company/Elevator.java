package com.company;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final List<Passenger> passengers = new ArrayList<>();
    private int currentFloor = 0;
    private boolean isDown;

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public int getMaxCapacity() {
        return 5;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger){
        passengers.add(passenger);
    }
    public void removePassenger(Passenger passenger){
        passengers.remove(passenger);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
