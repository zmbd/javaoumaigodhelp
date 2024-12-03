package com.trucker_system.truckersystem.service;

import com.trucker_system.truckersystem.hibernate.TestCargoHib;
import com.trucker_system.truckersystem.hibernate.TestUserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CargoServiceTest extends ApplicationTest {
    private TestUserHib userHib;
    private TestCargoHib cargoHib;
    private CargoService cargoService;
    private Cargo testCargo;
    private Trucker testTrucker;

    @BeforeEach
    void setUp() {
        userHib = new TestUserHib();
        cargoHib = new TestCargoHib();
        cargoService = new CargoService(userHib, cargoHib);

        testCargo = new Cargo();
        testCargo.setClient("Test Client");
        testCargo.setStartDestination("Start Address");
        testCargo.setFinalDestination("End Address");
        testCargo.setAssignedAt(LocalDate.now());
        testCargo.setDeliverUntil(LocalDate.now().plusDays(7));

        testTrucker = new Trucker();
        testTrucker.setName("John");
        testTrucker.setSurname("Doe");
        testTrucker.setEmail("john@test.com");
        testTrucker.setLogin("johndoe");
    }

    @Test
    void testGetAllTruckers() {
        userHib.setTruckers(List.of(testTrucker));
        List<Trucker> truckers = cargoService.getAllTruckers();
        assertEquals(1, truckers.size());
        assertEquals(testTrucker, truckers.get(0));
    }

    @Test
    void testUpdateCargo() {
        LocalDate now = LocalDate.now();
        cargoService.updateCargo(testCargo, "Updated Client", "New Start", "New End",
                now, now.plusDays(7), testTrucker);

        assertEquals("Updated Client", testCargo.getClient());
        assertEquals("New Start", testCargo.getStartDestination());
        assertEquals("New End", testCargo.getFinalDestination());
        assertEquals(testTrucker, testCargo.getTrucker());
        assertTrue(cargoHib.getUpdatedCargos().contains(testCargo));
    }

    @Test
    void testDeleteCargo() {
        cargoService.deleteCargo(testCargo);
        assertTrue(cargoHib.getDeletedCargos().contains(testCargo));
    }
}