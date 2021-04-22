package com.company;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final int maxCapacity = 5;
    private List<Passenger> passengers = new ArrayList<>();
    private int currentFloor;

    public int getMaxCapacity() {
        return maxCapacity;
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
