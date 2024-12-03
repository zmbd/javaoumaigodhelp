package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.TestCargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoTableInitializerTest extends ApplicationTest {

    private TestCargoHib cargoHib;
    private TableView<CargoTable> cargoTableView;
    private TableColumn<CargoTable, Integer> cargoColumnId;
    private TableColumn<CargoTable, String> cargoColumnClient;
    private TableColumn<CargoTable, String> cargoColumnCargo;
    private TableColumn<CargoTable, String> cargoColumnPickup;
    private TableColumn<CargoTable, String> cargoColumnDelivery;
    private TableColumn<CargoTable, LocalDate> cargoColumnDeadline;
    private TableColumn<CargoTable, Boolean> cargoColumnFinished;
    private TableColumn<CargoTable, String> cargoColumnDelete;

    private CargoTableInitializer cargoTableInitializer;

    @Override
    public void start(javafx.stage.Stage stage) {
        // This method is required by TestFX, but we don't need to do anything here
    }

    @BeforeEach
    void setUp() {
        cargoHib = new TestCargoHib();
        cargoTableView = new TableView<>();
        cargoColumnId = new TableColumn<>();
        cargoColumnId.setId("id");
        cargoColumnClient = new TableColumn<>();
        cargoColumnClient.setId("client");
        cargoColumnCargo = new TableColumn<>();
        cargoColumnCargo.setId("cargo");
        cargoColumnPickup = new TableColumn<>();
        cargoColumnPickup.setId("pickupAddress");
        cargoColumnDelivery = new TableColumn<>();
        cargoColumnDelivery.setId("deliveryAddress");
        cargoColumnDeadline = new TableColumn<>();
        cargoColumnDeadline.setId("deadline");
        cargoColumnFinished = new TableColumn<>();
        cargoColumnFinished.setId("finished");
        cargoColumnDelete = new TableColumn<>();
        cargoColumnDelete.setId("deleteBtn");

        cargoTableInitializer = new CargoTableInitializer(
                cargoHib,
                cargoTableView,
                cargoColumnId,
                cargoColumnClient,
                cargoColumnCargo,
                cargoColumnPickup,
                cargoColumnDelivery,
                cargoColumnDeadline,
                cargoColumnFinished,
                cargoColumnDelete
        );
    }

    @Test
    void testInitializeCargoTable() {
        cargoTableInitializer.initialize();

        assertEquals("id", cargoColumnId.getId());
        assertEquals("client", cargoColumnClient.getId());
        assertEquals("cargo", cargoColumnCargo.getId());
        assertEquals("pickupAddress", cargoColumnPickup.getId());
        assertEquals("deliveryAddress", cargoColumnDelivery.getId());
        assertEquals("deadline", cargoColumnDeadline.getId());
        assertEquals("finished", cargoColumnFinished.getId());
        assertEquals("deleteBtn", cargoColumnDelete.getId());
    }

    @Test
    void testUpdateCargoOnCellCommit() {
        Cargo cargo = new Cargo();
        cargo.setClient("Old Client");

        cargoTableInitializer.updateCargoOnCellCommit(1, "New Client", CargoEditType.CLIENT);

        List<Cargo> updatedCargos = cargoHib.getUpdatedCargos();
        assertFalse(updatedCargos.isEmpty());
        assertEquals("New Client", updatedCargos.get(0).getClient());
    }

    @Test
    void testGetEditedCargo() {
        Cargo cargo = cargoTableInitializer.getEditedCargo(1);

        assertNotNull(cargo);
        assertTrue(cargo instanceof Cargo);
    }

    @Test
    void testUpdateCargoForAllEditTypes() {
        cargoTableInitializer.updateCargoOnCellCommit(1, "New Cargo", CargoEditType.CARGO);
        List<Cargo> updatedCargos = cargoHib.getUpdatedCargos();
        assertEquals("New Cargo", updatedCargos.get(0).getCargo());

        cargoTableInitializer.updateCargoOnCellCommit(1, "New Pickup", CargoEditType.PICKUP);
        assertEquals("New Pickup", updatedCargos.get(1).getStartDestination());

        cargoTableInitializer.updateCargoOnCellCommit(1, "New Delivery", CargoEditType.DELIVERY);
        assertEquals("New Delivery", updatedCargos.get(2).getFinalDestination());

        LocalDate newDate = LocalDate.now();
        cargoTableInitializer.updateCargoOnCellCommit(1, newDate, CargoEditType.DEADLINE);
        assertEquals(newDate, updatedCargos.get(3).getDeliverUntil());

        cargoTableInitializer.updateCargoOnCellCommit(1, true, CargoEditType.FINISHED);
        assertTrue(updatedCargos.get(4).isFinished());
    }

    @Test
    void testTableEditCommitEvents() {
        cargoTableInitializer.initialize();
        CargoTable testCargoTable = createTestCargoTable();
        cargoTableView.getItems().add(testCargoTable);

        TableColumn.CellEditEvent<CargoTable, String> clientEditEvent = createCellEditEvent(
                cargoColumnClient, 0, "New Client");
        cargoColumnClient.getOnEditCommit().handle(clientEditEvent);
        assertEquals("New Client", cargoTableView.getItems().get(0).getClient());

        TableColumn.CellEditEvent<CargoTable, String> cargoEditEvent = createCellEditEvent(
                cargoColumnCargo, 0, "New Cargo Name");
        cargoColumnCargo.getOnEditCommit().handle(cargoEditEvent);
        assertEquals("New Cargo Name", cargoTableView.getItems().get(0).getCargo());

        TableColumn.CellEditEvent<CargoTable, String> pickupEditEvent = createCellEditEvent(
                cargoColumnPickup, 0, "New Pickup Address");
        cargoColumnPickup.getOnEditCommit().handle(pickupEditEvent);
        assertEquals("New Pickup Address", cargoTableView.getItems().get(0).getPickupAddress());

        TableColumn.CellEditEvent<CargoTable, String> deliveryEditEvent = createCellEditEvent(
                cargoColumnDelivery, 0, "New Delivery Address");
        cargoColumnDelivery.getOnEditCommit().handle(deliveryEditEvent);
        assertEquals("New Delivery Address", cargoTableView.getItems().get(0).getDeliveryAddress());
    }

    @Test
    void testDateConverterInDeadlineColumn() {
        cargoTableInitializer.initialize();

        TextFieldTableCell<CargoTable, LocalDate> cell =
                (TextFieldTableCell<CargoTable, LocalDate>) cargoColumnDeadline.getCellFactory()
                        .call(cargoColumnDeadline);

        LocalDate testDate = LocalDate.of(2024, 1, 1);
        String dateStr = cell.getConverter().toString(testDate);
        assertEquals("2024-01-01", dateStr);

        LocalDate convertedDate = cell.getConverter().fromString("2024-01-01");
        assertEquals(testDate, convertedDate);
    }

    @Test
    void testBooleanConverterInFinishedColumn() {
        cargoTableInitializer.initialize();

        TextFieldTableCell<CargoTable, Boolean> cell =
                (TextFieldTableCell<CargoTable, Boolean>) cargoColumnFinished.getCellFactory()
                        .call(cargoColumnFinished);

        assertEquals("YES", cell.getConverter().toString(true));
        assertEquals("NO", cell.getConverter().toString(false));

        assertTrue(cell.getConverter().fromString("YES"));
        assertFalse(cell.getConverter().fromString("NO"));
    }

    @Test
    void testDeleteButtonColumn() {
        cargoTableInitializer.initialize();

        var cellFactory = cargoColumnDelete.getCellFactory();

        var cell = cellFactory.call(cargoColumnDelete);

        CargoTable testData = createTestCargoTable();
        cargoTableView.getItems().add(testData);
        Cargo cargo = new Cargo();
        cargo.setId(1);
        cargoHib.createCargo(cargo);

        assertNotNull(cellFactory);
        assertTrue(cell instanceof TableCell);

        int initialSize = cargoTableView.getItems().size();
        cargoTableView.getItems().remove(testData);
        cargoHib.deleteCargoById(1);

        assertEquals(initialSize - 1, cargoTableView.getItems().size());
        assertTrue(cargoHib.getDeletedCargosIds().contains(testData.getId()));
    }

    private CargoTable createTestCargoTable() {
        return new CargoTable(
                1,
                "Test Client",
                "Test Cargo",
                "Test Pickup",
                "Test Delivery",
                LocalDate.now(),
                false
        );
    }

    private <T> TableColumn.CellEditEvent<CargoTable, T> createCellEditEvent(
            TableColumn<CargoTable, T> column, int rowIndex, T newValue) {
        TablePosition<CargoTable, T> position =
                new TablePosition<>(cargoTableView, rowIndex, column);
        return new TableColumn.CellEditEvent<>(
                cargoTableView,
                position,
                TableColumn.editCommitEvent(),
                newValue
        );
    }
}

