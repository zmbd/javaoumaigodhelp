package com.trucker_system.truckersystem.service;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;

import java.time.LocalDate;
import java.util.List;

public class CargoService {
    private final UserHib userHib;
    private final CargoHib cargoHib;

    public CargoService(UserHib userHib, CargoHib cargoHib) {
        this.userHib = userHib;
        this.cargoHib = cargoHib;
    }

    public List<Trucker> getAllTruckers() {
        return userHib.getAllTruckers();
    }

    public void updateCargo(Cargo cargo, String client, String startDest, String endDest,
                            LocalDate assignedDate, LocalDate deliverUntil, Trucker trucker) {
        cargo.setClient(client);
        cargo.setStartDestination(startDest);
        cargo.setFinalDestination(endDest);
        cargo.setAssignedAt(assignedDate);
        cargo.setDeliverUntil(deliverUntil);
        cargo.setTrucker(trucker);

        cargoHib.updateCargo(cargo);
    }

    public void deleteCargo(Cargo cargo) {
        cargoHib.deleteCargo(cargo);
    }
}