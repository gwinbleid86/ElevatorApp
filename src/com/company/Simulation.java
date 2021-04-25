package com.company;

import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    private final Elevator elevator = new Elevator();
    private final List<Floor> building = new Building().getBuilding();

    public static void start() {
        Simulation s = new Simulation();
        try {
            s.moveElevator();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void moveElevator() throws InterruptedException {
        while (true) {
            oneStep();
            Thread.sleep(1000);
        }
    }

    private int stage = 1;

    private void oneStep() {
        StringBuilder str;
        str = new StringBuilder(String.format("\n\n***   Stage %s   ***\n\n", stage));
        str.append(String.format("Floor %s |", elevator.getCurrentFloor() + 1));
        actionFloor();
        print(str);
        changeCurrentFloor();
        stage++;
    }

    //проверяем, есть-ли желающие выйти из лифта на текущем этаже
    private boolean needExit() {
        return elevator.getPassengers().stream().noneMatch(e -> e.getWishedFloor() == elevator.getCurrentFloor());
    }

    //проверяем есть-ли на текущем этаже желающие ехать по направлению движения лифта
    private boolean isElevatorCall() {
        return building.get(elevator.getCurrentFloor()).getPassengers().stream().noneMatch(e -> e.isDown() == elevator.isDown());
    }

    //действия с лифтом и пассажирами на этаже
    private void actionFloor() {
        var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers()
                .stream()
                .collect(Collectors.groupingBy(Passenger::isDown));
        movePassengersToFloor();
        if (building.get(elevator.getCurrentFloor()).getPassengers().size() > 0) {
            if (elevator.isDown()) {
                movePassengersToElevator(passengersForward.get(true));
            } else {
                movePassengersToElevator(passengersForward.get(false));
            }
        }
    }

    //смена текущего этажа у лифта
    private void changeCurrentFloor() {
        if (elevator.getCurrentFloor() < building.size() - 1 && !elevator.isDown()) {
            elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
            if ((elevator.getMaxCapacity() == elevator.getPassengers().size() || isElevatorCall()) && needExit()) {
                changeCurrentFloor();
            }
        } else if (elevator.getCurrentFloor() > 0 && elevator.isDown()) {
            elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
            if ((elevator.getMaxCapacity() == elevator.getPassengers().size() || isElevatorCall()) && needExit()) {
                changeCurrentFloor();
            }
        }
        if (elevator.getCurrentFloor() == building.size() - 1 || elevator.getCurrentFloor() == 0) {
            changeForward();
        }
    }

    //смена направления движения лифта
    private void changeForward() {
        var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers()
                .stream()
                .anyMatch(e -> e.isDown() == elevator.isDown());
        if (elevator.getPassengers().isEmpty() && !passengersForward) {
            elevator.setDown(!elevator.isDown());
        }
    }

    //реализация посадки пассажира в лифт
    private void movePassengersToElevator(List<Passenger> passengers) {
        int size = passengers == null ? 0 : passengers.size();
        for (int i = 0; i < size; i++) {
            if ((elevator.getMaxCapacity() - elevator.getPassengers().size()) > i) {
                elevator.addPassenger(passengers.get(i));
                building.get(elevator.getCurrentFloor()).removePassenger(passengers.get(i));
            }
        }
    }

    //реализация выхода пассажира из лифта
    private void movePassengersToFloor() {
        for (int i = 0; i < elevator.getPassengers().size(); i++) {
            if (elevator.getPassengers().get(i).getWishedFloor() == elevator.getCurrentFloor()) {
                elevator.removePassenger(elevator.getPassengers().get(i));
                building.get(elevator.getCurrentFloor()).addPassenger(new Passenger(building.size(), elevator.getCurrentFloor()));
                i--;
            }
        }
    }

    private void print(StringBuilder str) {
        for (int j = 0; j < elevator.getMaxCapacity(); j++) {
            if (elevator.getPassengers().size() > j) {
                str.append("_").append(elevator.getPassengers().get(j).getWishedFloor() + 1);
            } else {
                str.append('_');
            }
        }
        str.append("_|\n");
        System.out.println(str);
    }
}
