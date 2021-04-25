package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Floor {
    private final List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Floor(int buildingHeight, int numFloor){
        Random rnd = new Random();
        for (int i = 0; i <= rnd.nextInt(11); i++){
            passengers.add(new Passenger(buildingHeight, numFloor));
        }
    }
    public void addPassenger(Passenger p){
        if (passengers.size() < 10) passengers.add(p);
    }
    public void removePassenger(Passenger p){
        if (passengers.size() > 0) passengers.remove(p);
    }
}
