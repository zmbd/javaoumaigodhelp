package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Truck;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class TripHandler {
    @FXML
    private final Text clientText;
    @FXML
    private final Text assignedAtText;
    @FXML
    private final Text deliveryUntilText;
    @FXML
    private final Text pickupAddressText;
    @FXML
    private final Text deliveryAddressText;
    @FXML
    private final Text cargoDetailsText;
    @FXML
    private final Text tripStatusText;
    @FXML
    private final Text brandText;
    @FXML
    private final Text modelText;
    @FXML
    private final Text hpText;
    @FXML
    private final Text engineText;
    @FXML
    private final Text releaseText;

    private Truck selectedTruck;
    private List<Cargo> assignedCargos;

    public TripHandler(
            Text clientText, Text assignedAtText, Text deliveryUntilText,
            Text pickupAddressText, Text deliveryAddressText, Text cargoDetailsText,
            Text tripStatusText, Text brandText, Text modelText, Text hpText,
            Text engineText, Text releaseText) {
        this.clientText = clientText;
        this.assignedAtText = assignedAtText;
        this.deliveryUntilText = deliveryUntilText;
        this.pickupAddressText = pickupAddressText;
        this.deliveryAddressText = deliveryAddressText;
        this.cargoDetailsText = cargoDetailsText;
        this.tripStatusText = tripStatusText;
        this.brandText = brandText;
        this.modelText = modelText;
        this.hpText = hpText;
        this.engineText = engineText;
        this.releaseText = releaseText;
    }

    public void handleTripSelection(ActionEvent event, ChoiceBox<String> tripsChoiceBox) {
        String value = tripsChoiceBox.getSelectionModel().getSelectedItem();
        if (value != null) {
            String[] parts = value.split(", ");
            Cargo cargo = assignedCargos.stream()
                    .filter(c -> c.getClient().equals(parts[0]) &&
                            c.getAssignedAt().toString().equals(parts[1]))
                    .findAny()
                    .orElse(null);

            if (cargo != null) {
                updateCargoDetails(cargo);
                updateTripStatus(cargo);
            }
        }
    }

    public void handleTruckSelection(Event event, ChoiceBox<String> trucksChoiceBox, List<Truck> trucks) {
        String value = trucksChoiceBox.getSelectionModel().getSelectedItem();
        if (value != null) {
            String[] parts = value.split(" ");
            this.selectedTruck = trucks.stream()
                    .filter(t -> t.getBrand().equals(parts[0]) && t.getModel().equals(parts[1]))
                    .findAny()
                    .orElse(null);

            if (selectedTruck != null) {
                updateTruckDetails(selectedTruck);
            }
        }
    }

    private void updateCargoDetails(Cargo cargo) {
        clientText.setText(cargo.getClient());
        assignedAtText.setText(cargo.getAssignedAt().toString());
        deliveryUntilText.setText(cargo.getDeliverUntil().toString());
        pickupAddressText.setText(cargo.getStartDestination());
        deliveryAddressText.setText(cargo.getFinalDestination());
        cargoDetailsText.setText(cargo.getCargo());
    }

    private void updateTripStatus(Cargo cargo) {
        long daysBetween = DAYS.between(LocalDate.now(), cargo.getDeliverUntil());

        if (daysBetween < 0 && !cargo.isFinished()) {
            tripStatusText.setText("Deadline is overdue, cargo is not delivered");
            tripStatusText.setFill(Color.RED);
        } else if (cargo.isFinished()) {
            tripStatusText.setText("Delivered");
            tripStatusText.setFill(Color.GREEN);
        } else {
            tripStatusText.setText(daysBetween + " days remaining until cargo delivery deadline");
            tripStatusText.setFill(Color.RED);
        }
    }

    private void updateTruckDetails(Truck truck) {
        brandText.setText(truck.getBrand());
        modelText.setText(truck.getModel());
        hpText.setText(String.valueOf(truck.getHp()));
        engineText.setText(String.valueOf(truck.getEngine()));
        releaseText.setText(String.valueOf(truck.getReleaseYear()));
    }

    public void setAssignedCargos(List<Cargo> assignedCargos) {
        this.assignedCargos = assignedCargos;
    }

    public Truck getSelectedTruck() {
        return selectedTruck;
    }
}