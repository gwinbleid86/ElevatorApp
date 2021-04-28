package com.company;

import com.company.entity.Building;
import com.company.entity.Elevator;
import com.company.entity.Floor;
import com.company.entity.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    private final Elevator elevator = new Elevator();
    private final List<Floor> building = new Building().getBuilding();

    public static void start() {
        Simulation s = new Simulation();
        try {
            s.moveElevator();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void moveElevator() throws Exception {
        while (true) {
            oneStep();
            Thread.sleep(1000);
        }
    }

    private int stage = 1;

    private void oneStep() throws Exception {
        StringBuilder str;
        str = new StringBuilder(String.format("\n\n***   Stage %s   ***\n\n", stage));
        str.append(String.format("Floor %s |", elevator.getCurrentFloor() + 1));
        actionFloor();
        print(str);
        changeCurrentFloor();
        stage++;
    }

    //check if there is anyone who wants to get off the elevator on the current floor
    private boolean needExit() {
        return elevator.getPassengers().stream().noneMatch(e -> e.getWishedFloor() == elevator.getCurrentFloor());
    }

    //check if there is anyone on the current floor who wants to go in the direction of the elevator
    private boolean isElevatorCall() {
        return building.get(elevator.getCurrentFloor()).getPassengers().stream().noneMatch(e -> e.isDown() == elevator.isDown());
    }

    //actions with the elevator and passengers on the floor
    private void actionFloor() throws Exception {
        var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers()
                .stream()
                .collect(Collectors.groupingBy(Passenger::isDown));
        movePassengersToFloor();
        if (building.get(elevator.getCurrentFloor()).getPassengers().size() > 0) {
            movePassengersToElevator(passengersForward.get(elevator.isDown()));
        }
    }

    //change of the current floor at the elevator
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

    //change the direction of the lift
    private void changeForward() {
        var passengersForward = building.get(elevator.getCurrentFloor()).getPassengers()
                .stream()
                .anyMatch(e -> e.isDown() == elevator.isDown());
        if (elevator.getPassengers().isEmpty() && !passengersForward) {
            elevator.setDown(!elevator.isDown());
        }
    }

    //implementation of passenger boarding in the elevator
    private void movePassengersToElevator(List<Passenger> passengers) throws Exception {
        int size = passengers == null ? 0 : passengers.size();
        for (int i = 0; i < size; i++) {
            if ((elevator.getMaxCapacity() - elevator.getPassengers().size()) > i) {
                elevator.addPassenger(passengers.get(i));
                building.get(elevator.getCurrentFloor()).removePassenger(passengers.get(i));
            }
        }
    }

    //implementation of the passenger's exit from the elevator
    private void movePassengersToFloor() throws Exception {
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
