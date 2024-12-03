package com.trucker_system.truckersystem.service;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.util.List;

public class CargoServiceOld {
    private static final int MIN_CLIENT_LENGTH = 5;
    private static final int MIN_DESTINATION_LENGTH = 10;

    private final UserHib userHib;
    private final CargoHib cargoHib;

    public CargoServiceOld(UserHib userHib, CargoHib cargoHib) {
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

    public boolean validateCargoInput(String client, String startDest, String endDest,
                                      LocalDate assignedDate, LocalDate deliverUntil,
                                      boolean hasTruckerSelected) {
        return isValidClient(client) &&
                isValidDestinations(startDest, endDest) &&
                isValidDates(assignedDate, deliverUntil) &&
                hasTruckerSelected;
    }

    private boolean isValidClient(String client) {
        return client != null && client.length() > MIN_CLIENT_LENGTH;
    }

    private boolean isValidDestinations(String startDest, String endDest) {
        return startDest != null && endDest != null &&
                startDest.length() > MIN_DESTINATION_LENGTH &&
                endDest.length() > MIN_DESTINATION_LENGTH;
    }

    private boolean isValidDates(LocalDate assignedDate, LocalDate deliverUntil) {
        return assignedDate != null && deliverUntil != null;
    }

    public void updateListViews(Cargo cargo, ListView<String> mainList, ListView<String> secondaryList) {
        if (cargo == null) {
            removeCargoFromMainList(mainList);
            return;
        }

        updateCargoInLists(cargo, mainList, secondaryList);
    }

    private void removeCargoFromMainList(ListView<String> mainList) {
        int selectedIndex = mainList.getSelectionModel().getSelectedIndex();
        mainList.getSelectionModel().clearSelection();
        mainList.getItems().remove(selectedIndex);
    }

    private void updateCargoInLists(Cargo cargo, ListView<String> mainList, ListView<String> secondaryList) {
        int selectedIndex = mainList.getSelectionModel().getSelectedIndex();

        if (canMoveToSecondaryList(cargo, secondaryList)) {
            moveCargoToSecondaryList(cargo, mainList, secondaryList, selectedIndex);
        } else {
            mainList.getItems().add(cargo.getClient());
        }
    }

    private boolean canMoveToSecondaryList(Cargo cargo, ListView<String> secondaryList) {
        return secondaryList != null && cargo.getTrucker() != null;
    }

    private void moveCargoToSecondaryList(Cargo cargo, ListView<String> mainList,
                                          ListView<String> secondaryList, int selectedIndex) {
        secondaryList.getItems().add(cargo.getClient());
        mainList.getItems().remove(selectedIndex);
    }
}