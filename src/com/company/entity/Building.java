package com.company.entity;

import com.company.util.Generator;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private final List<Floor> building = new ArrayList<>();

    public List<Floor> getBuilding() {
        return building;
    }

    public Building() {
        generateBuild();
    }

    private void generateBuild() {
        int buildingHeight = Generator.getRandomFromRange(5,16);

        for (int i = 0; i < buildingHeight; i++) {
            building.add(new Floor(buildingHeight, i));
        }
    }

}
