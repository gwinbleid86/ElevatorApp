package com.company.entity;

import com.company.util.Generator;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private final List<Passenger> passengers = new ArrayList<>();

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Floor(int buildingHeight, int numFloor) {
        for (int i = 0; i <= Generator.getRandomFromRange(0, 11); i++) {
            passengers.add(new Passenger(buildingHeight, numFloor));
        }
    }

    public void addPassenger(Passenger p) throws Exception {
        if (passengers.size() < 10) passengers.add(p);
        else throw new Exception("The maximum number of passengers per floor has been reached. In order not to crowd around the elevator, the passenger went about his business.");
    }

    public void removePassenger(Passenger p) {
        if (passengers.size() > 0) passengers.remove(p);
    }
}
