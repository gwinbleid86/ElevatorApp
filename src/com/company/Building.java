package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Building {
    private final Random rnd = new Random();
    private final List<Floor> building = new ArrayList<>();

    public List<Floor> getBuilding() {
        return building;
    }

    public Building() {
        generateBuild();
    }

    private void generateBuild() {
        int buildingHeight = rnd.nextInt(16)+5;

        for (int i = 0; i < buildingHeight; i++) {
            building.add(new Floor(buildingHeight, i));
        }
    }

}
