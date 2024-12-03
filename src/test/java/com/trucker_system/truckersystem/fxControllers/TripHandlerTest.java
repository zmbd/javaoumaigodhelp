package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Truck;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripHandlerTest extends ApplicationTest {
    private TripHandler tripHandler;
    private Text clientText;
    private Text assignedAtText;
    private Text deliveryUntilText;
    private Text pickupAddressText;
    private Text deliveryAddressText;
    private Text cargoDetailsText;
    private Text tripStatusText;
    private Text brandText;
    private Text modelText;
    private Text hpText;
    private Text engineText;
    private Text releaseText;
    private ChoiceBox<String> tripsChoiceBox;
    private ChoiceBox<String> trucksChoiceBox;

    @Override
    public void start(Stage stage) {
        // Required by TestFX, but we don't need to do anything here
    }

    @BeforeEach
    void setUp() {
        clientText = new Text();
        assignedAtText = new Text();
        deliveryUntilText = new Text();
        pickupAddressText = new Text();
        deliveryAddressText = new Text();
        cargoDetailsText = new Text();
        tripStatusText = new Text();
        brandText = new Text();
        modelText = new Text();
        hpText = new Text();
        engineText = new Text();
        releaseText = new Text();

        tripsChoiceBox = new ChoiceBox<>();
        trucksChoiceBox = new ChoiceBox<>();

        tripHandler = new TripHandler(
                clientText, assignedAtText, deliveryUntilText,
                pickupAddressText, deliveryAddressText, cargoDetailsText,
                tripStatusText, brandText, modelText, hpText,
                engineText, releaseText
        );
    }

    @Test
    void testHandleTripSelectionWithValidCargo() {
        tripsChoiceBox.getItems().add("TestClient, 2024-01-01");
        tripsChoiceBox.getSelectionModel().select(0);

        Cargo testCargo = new Cargo();
        testCargo.setClient("TestClient");
        testCargo.setAssignedAt(LocalDate.of(2024, 1, 1));
        testCargo.setDeliverUntil(LocalDate.now().plusDays(5));
        testCargo.setStartDestination("Start");
        testCargo.setFinalDestination("End");
        testCargo.setCargo("Test Cargo");
        testCargo.setFinished(false);

        List<Cargo> cargos = new ArrayList<>();
        cargos.add(testCargo);
        tripHandler.setAssignedCargos(cargos);

        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("TestClient", clientText.getText());
        assertEquals("2024-01-01", assignedAtText.getText());
        assertEquals("Start", pickupAddressText.getText());
        assertEquals("End", deliveryAddressText.getText());
        assertEquals("Test Cargo", cargoDetailsText.getText());
        assertTrue(tripStatusText.getText().contains("days remaining"));
    }

    @Test
    void testHandleTripSelectionWithOverdueCargo() {
        tripsChoiceBox.getItems().add("TestClient, 2024-01-01");
        tripsChoiceBox.getSelectionModel().select(0);

        Cargo testCargo = new Cargo();
        testCargo.setClient("TestClient");
        testCargo.setAssignedAt(LocalDate.of(2024, 1, 1));
        testCargo.setDeliverUntil(LocalDate.now().minusDays(5));
        testCargo.setFinished(false);

        List<Cargo> cargos = new ArrayList<>();
        cargos.add(testCargo);
        tripHandler.setAssignedCargos(cargos);

        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("Deadline is overdue, cargo is not delivered", tripStatusText.getText());
        assertEquals(Color.RED, tripStatusText.getFill());
    }

    @Test
    void testHandleTripSelectionWithDeliveredCargo() {
        tripsChoiceBox.getItems().add("TestClient, 2024-01-01");
        tripsChoiceBox.getSelectionModel().select(0);

        Cargo testCargo = new Cargo();
        testCargo.setClient("TestClient");
        testCargo.setAssignedAt(LocalDate.of(2024, 1, 1));
        testCargo.setDeliverUntil(LocalDate.now());
        testCargo.setStartDestination("Start");
        testCargo.setFinalDestination("End");
        testCargo.setCargo("Test Cargo");
        testCargo.setFinished(true);

        List<Cargo> cargos = new ArrayList<>();
        cargos.add(testCargo);
        tripHandler.setAssignedCargos(cargos);

        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("Delivered", tripStatusText.getText());
        assertEquals(Color.GREEN, tripStatusText.getFill());

        assertEquals("TestClient", clientText.getText());
        assertEquals("2024-01-01", assignedAtText.getText());
        assertEquals("Start", pickupAddressText.getText());
        assertEquals("End", deliveryAddressText.getText());
        assertEquals("Test Cargo", cargoDetailsText.getText());
    }

    @Test
    void testHandleTruckSelection() {
        trucksChoiceBox.getItems().add("Volvo FH16");
        trucksChoiceBox.getSelectionModel().select(0);

        Truck testTruck = new Truck();
        testTruck.setBrand("Volvo");
        testTruck.setModel("FH16");
        testTruck.setHp(750);
        testTruck.setEngine(16.1);
        testTruck.setReleaseYear(2024);

        List<Truck> trucks = new ArrayList<>();
        trucks.add(testTruck);

        tripHandler.handleTruckSelection(new ActionEvent(), trucksChoiceBox, trucks);

        assertEquals("Volvo", brandText.getText());
        assertEquals("FH16", modelText.getText());
        assertEquals("750", hpText.getText());
        assertEquals("16.1", engineText.getText());
        assertEquals("2024", releaseText.getText());
        assertEquals(testTruck, tripHandler.getSelectedTruck());
    }

    @Test
    void testHandleTripSelectionWithNullSelection() {
        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("", clientText.getText());
    }

    @Test
    void testHandleTruckSelectionWithNullSelection() {
        List<Truck> trucks = new ArrayList<>();
        tripHandler.handleTruckSelection(new ActionEvent(), trucksChoiceBox, trucks);

        assertEquals("", brandText.getText());
    }

    @Test
    void testHandleTripSelectionWithNonMatchingCargo() {
        tripsChoiceBox.getItems().add("NonExistingClient, 2024-01-01");
        tripsChoiceBox.getSelectionModel().select(0);

        Cargo testCargo = new Cargo();
        testCargo.setClient("DifferentClient");
        testCargo.setAssignedAt(LocalDate.of(2024, 1, 1));

        List<Cargo> cargos = new ArrayList<>();
        cargos.add(testCargo);
        tripHandler.setAssignedCargos(cargos);

        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("", clientText.getText());
        assertEquals("", assignedAtText.getText());
    }

    @Test
    void testHandleTruckSelectionWithNonMatchingTruck() {
        trucksChoiceBox.getItems().add("NonExisting Model");
        trucksChoiceBox.getSelectionModel().select(0);

        Truck testTruck = new Truck();
        testTruck.setBrand("Different");
        testTruck.setModel("Truck");

        List<Truck> trucks = new ArrayList<>();
        trucks.add(testTruck);

        tripHandler.handleTruckSelection(new ActionEvent(), trucksChoiceBox, trucks);

        assertEquals("", brandText.getText());
        assertEquals("", modelText.getText());
    }

    @Test
    void testHandleTripSelectionWithEmptyAssignedCargos() {
        tripsChoiceBox.getItems().add("TestClient, 2024-01-01");
        tripsChoiceBox.getSelectionModel().select(0);

        tripHandler.setAssignedCargos(new ArrayList<>());

        tripHandler.handleTripSelection(new ActionEvent(), tripsChoiceBox);

        assertEquals("", clientText.getText());
        assertEquals("", assignedAtText.getText());
    }

    @Test
    void testHandleTruckSelectionWithEmptyTruckList() {
        trucksChoiceBox.getItems().add("Volvo FH16");
        trucksChoiceBox.getSelectionModel().select(0);

        tripHandler.handleTruckSelection(new ActionEvent(), trucksChoiceBox, new ArrayList<>());

        assertEquals("", brandText.getText());
        assertEquals("", modelText.getText());
    }
}