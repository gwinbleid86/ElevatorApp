package com.company.entity;

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

    public void addPassenger(Passenger passenger) throws Exception {
        if (passengers.size() < getMaxCapacity()) passengers.add(passenger);
        else throw new Exception("The maximum lift capacity has been reached. The passenger will have to wait for the next lift.");
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
