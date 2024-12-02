package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
public class MainPageTest {
    private MainPage mainPage;
    private CargoHib cargoHib;
    private TableView<CargoTable> cargoTableView;

    @Start
    public void start(Stage stage) {
        // Create and initialize table view
        cargoTableView = new TableView<>();

        // Create and initialize all required columns with correct types
        TableColumn<CargoTable, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<CargoTable, String> clientColumn = new TableColumn<>("Client");
        TableColumn<CargoTable, String> cargoColumn = new TableColumn<>("Cargo");
        TableColumn<CargoTable, String> pickupColumn = new TableColumn<>("Pickup");
        TableColumn<CargoTable, String> deliveryColumn = new TableColumn<>("Delivery");
        TableColumn<CargoTable, LocalDate> deadlineColumn = new TableColumn<>("Deadline");
        TableColumn<CargoTable, Boolean> finishedColumn = new TableColumn<>("Finished");
        TableColumn<CargoTable, String> deleteColumn = new TableColumn<>("Delete");

        // Add columns to table
        cargoTableView.getColumns().addAll(
                idColumn, clientColumn, cargoColumn, pickupColumn,
                deliveryColumn, deadlineColumn, finishedColumn, deleteColumn
        );

        // Initialize MainPage and set columns
        mainPage = new MainPage();
        mainPage.setCargoColumnId(idColumn);
        mainPage.setCargoColumnClient(clientColumn);
        mainPage.setCargoColumnCargo(cargoColumn);
        mainPage.setCargoColumnPickup(pickupColumn);
        mainPage.setCargoColumnDelivery(deliveryColumn);
        mainPage.setCargoColumnDeadline(deadlineColumn);
        mainPage.setCargoColumnFinished(finishedColumn);
        mainPage.setCargoColumnDelete(deleteColumn);

        // Set table and mock dependencies
        cargoHib = mock(CargoHib.class);
        mainPage.setCargoHib(cargoHib);
        mainPage.setCargoTableView(cargoTableView);

        // Initialize the table
        mainPage.initializeCargoTable();

        // Set up scene
        Scene scene = new Scene(cargoTableView);
        stage.setScene(scene);
    }

    @Test
    public void testCargoTableIsEditable() {
        assertTrue(cargoTableView.isEditable(), "Cargo table should be editable");
    }
}