package com.trucker_system.truckersystem.UIManager;

import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CargoUIManagerTest extends ApplicationTest {
    private CargoUIManager cargoUIManager;
    private Cargo testCargo;
    private Trucker testTrucker;

    @Override
    public void start(Stage stage) {
        // Required by TestFX, but we don't need to do anything here
    }

    @BeforeEach
    void setUp() {
        cargoUIManager = new CargoUIManager();
        testCargo = new Cargo();
        testTrucker = new Trucker();
    }

    @Test
    void testUpdateListViewsWithCargoNoTrucker() {
        ListView<String> mainList = new ListView<>();
        mainList.setItems(FXCollections.observableArrayList("Item1", "Item2"));

        testCargo.setClient("New Client");
        testCargo.setTrucker(null);

        cargoUIManager.updateListViews(testCargo, mainList, null);

        assertTrue(mainList.getItems().contains("New Client"));
        assertEquals(3, mainList.getItems().size());
    }

    @Test
    void testUpdateListViewsWithCargoAndTrucker() {
        ListView<String> mainList = new ListView<>();
        ListView<String> secondaryList = new ListView<>();

        mainList.setItems(FXCollections.observableArrayList("Item1", "TestClient", "Item3"));
        secondaryList.setItems(FXCollections.observableArrayList());

        mainList.getSelectionModel().select(1);

        testCargo.setClient("TestClient");
        testCargo.setTrucker(testTrucker);

        cargoUIManager.updateListViews(testCargo, mainList, secondaryList);

        assertTrue(secondaryList.getItems().contains("TestClient"));
        assertFalse(mainList.getItems().contains("TestClient"));
        assertEquals(2, mainList.getItems().size());
        assertEquals(1, secondaryList.getItems().size());
    }

    @Test
    void testUpdateListViewsWhenAllArgsProvided() {
        ListView<String> mainList = new ListView<>();
        ListView<String> secondaryList = new ListView<>();

        ObservableList<String> mainListItems = FXCollections.observableArrayList("1337");
        mainList.setItems(mainListItems);
        mainList.getSelectionModel().select(0);

        ObservableList<String> secondaryListItems = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");
        secondaryList.setItems(secondaryListItems);
        secondaryList.getSelectionModel().select(1);

        testCargo.setClient("New Client");
        testCargo.setTrucker(new Trucker("login", "password", "email", "name", "surname", "phone"));

        cargoUIManager.updateListViews(testCargo, mainList, secondaryList);

        assertTrue(secondaryList.getItems().contains("New Client"));
        assertEquals(0, mainListItems.size());
        assertFalse(mainListItems.contains("1337"));
    }

    @Test
    void testUpdateListViewsWhenCargoIsNull() {
        ListView<String> mainList = new ListView<>();
        ListView<String> secondaryList = new ListView<>();

        ObservableList<String> mainListItems = FXCollections.observableArrayList("1337");
        mainList.setItems(mainListItems);
        mainList.getSelectionModel().select(0);

        cargoUIManager.updateListViews(null, mainList, secondaryList);

        assertEquals(0, mainListItems.size());
    }

    @Test
    void testUpdateListViewsWhenSecondaryListIsNullButTruckerIsNotNull() {
        ListView<String> mainList = new ListView<>();
        ObservableList<String> mainListItems = FXCollections.observableArrayList("1337");
        mainList.setItems(mainListItems);
        mainList.getSelectionModel().select(0);

        testCargo.setClient("New Client");
        testCargo.setTrucker(new Trucker("login", "password", "email", "name", "surname", "phone"));

        cargoUIManager.updateListViews(testCargo, mainList, null);

        assertTrue(mainListItems.contains("1337"));
        assertEquals(2, mainListItems.size());
    }
}