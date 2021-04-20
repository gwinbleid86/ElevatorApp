package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Floor {
    private final Random rnd = new Random();
    private final List<Passenger> passengers = new ArrayList<>(rnd.nextInt(11));

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Floor(int buildingHeight){
        for (int i = 0; i <= rnd.nextInt(11); i++){
            passengers.add(new Passenger(buildingHeight, i));
        }
    }
}
