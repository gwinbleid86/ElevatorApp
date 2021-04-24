package com.company;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.css.CSSUnknownRule;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {

    private final Elevator elevator = new Elevator();
    private final List<Floor> building = new Building().getBuilding();

    public static void start() {
        Simulation s = new Simulation();
        s.moveElevator();
    }

    private void moveElevator() {
//        int count = 1;
        while (true) {
//            if (!elevator.isDown() && elevator.getCurrentFloor() < building.size()) {
            oneStep(elevator.getCurrentFloor());
//                elevator.setCurrentFloor(elevator.getCurrentFloor()+1);
//            } else if (elevator.getCurrentFloor() == building.size()) {
//                oneStep(elevator.getCurrentFloor());
//            } else {
//                oneStep(elevator.getCurrentFloor());
//                elevator.setCurrentFloor(elevator.getCurrentFloor()-1);
//            }
//            count++;
//        var wishedFloors = building.stream()
//                .map(Floor::getPassengers)
//                .flatMap(Collection::stream)
//                .map(Passenger::getWishedFloor)
//                .sorted()
//                .collect(Collectors.toList());

//        for (int i = 0; i < wishedFloors.get(wishedFloors.size() - 1); i++) {
//            if (elevator.getMaxCapacity() == elevator.getPassengers().size()
//                    && !needExit(i)) continue;
//            str = new StringBuilder(String.format("\n\n***   Stage %s   ***\n\n", stage));
//            str.append(String.format("Floor %s |", elevator.getCurrentFloor() + 1));
//            actionFloor(i);
//            elevator.setCurrentFloor(i + 1);
//            print(str);
//            stage++;
        }
    }

    private int stage = 1;

    private void oneStep(int currentFloor) {
        StringBuilder str;
        if (elevator.getMaxCapacity() != elevator.getPassengers().size() || needExit(currentFloor) || isElevatorCall(currentFloor)) {
            str = new StringBuilder(String.format("\n\n***   Stage %s   ***\n\n", stage));
            str.append(String.format("Floor %s |", elevator.getCurrentFloor() + 1));
            actionFloor(currentFloor);
            print(str);
        }
        stage++;
    }

    //проверяем, есть-ли желающие выйти из лифта на текущем этаже
    private boolean needExit(int currentFloor) {
        boolean exit = false;
        for (Passenger passenger : elevator.getPassengers()) {
            exit = passenger.getWishedFloor() == currentFloor;
        }
        return exit;
    }

    //проверяем есть-ли на этаже желающие ехать вверх
    private boolean isElevatorCall(int currentFloor) {
        boolean entrance = false;
        for (Passenger p : building.get(currentFloor).getPassengers()) {
            entrance = p.getWishedFloor() > currentFloor;
        }
        return entrance;
    }

    //действия с лифтом и пассажирами на этаже
    private void actionFloor(int currentFloor) {

//        if (currentFloor == building.size() || currentFloor == 0) {
//            var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers();
//        }else{
        var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers()
                .stream()
                .collect(Collectors.groupingBy(Passenger::isDown));
//        }
//        var needExit =
        movePassengersToFloor();
        if (building.get(currentFloor).getPassengers().size() > 0) {
            if (elevator.isDown()) {
//                if (elevator.getPassengers() == null && passengersForward.get(true) == null) {
//                    elevator.setDown(!elevator.isDown());
//                }else {
                    movePassengersToElevator(currentFloor, passengersForward.get(true));
//                }
            } else {
//                if (elevator.getPassengers() == null && passengersForward.get(false) == null) {
//                    elevator.setDown(!elevator.isDown());
//                }else{
                    movePassengersToElevator(currentFloor, passengersForward.get(false));
//                }
            }
        }
        if (elevator.getCurrentFloor() < building.size() - 1 && !elevator.isDown()) {
            elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
        } else if (currentFloor > 0 && elevator.isDown()) {
            elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
        }
//        else {
//            changeForward(currentFloor);
//            elevator.setDown(!elevator.isDown());
//        }
//        var passengersForward = building.get(currentFloor).getPassengers()
//                .stream()
//                .filter(p -> p.isDown() == elevator.isDown())
//                .collect(Collectors.toList());
//        if (elevator.getPassengers() == null && passengersForward == null) {
//            elevator.setDown(!elevator.isDown());
//        }



    }

    //смена направления движения лифта
    private void changeForward(int currentFloor) {
        var passengersForward = building.get(currentFloor).getPassengers()
                .stream()
                .filter(p -> p.isDown() == elevator.isDown())
                .collect(Collectors.toList());
        if (elevator.getPassengers() == null && passengersForward == null) {
            elevator.setDown(!elevator.isDown());
        }
    }

    //реализация посадки пассажира в лифт
    private void movePassengersToElevator(int floor, List<Passenger> passengers) {
        int size = passengers == null? 0 : passengers.size();
        for (int i = 0; i < size; i++) {
            if (elevator.getMaxCapacity() > i - 1) {
                if (elevator.getPassengers().size() <= i) {
                    elevator.addPassenger(passengers.get(i));
                    building.get(floor).removePassenger(passengers.get(i));
                }
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
