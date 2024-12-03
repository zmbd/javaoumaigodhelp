package com.trucker_system.truckersystem.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CargoValidatorTest extends ApplicationTest {
    private CargoValidator cargoValidator;

    @BeforeEach
    void setUp() {
        cargoValidator = new CargoValidator();
    }

    @Test
    void testValidateCargoInputWithValidInput() {
        assertTrue(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Valid Start Destination",
                "Valid End Destination",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                true
        ));
    }

    @Test
    void testValidateCargoInputWithInvalidClientName() {
        assertFalse(cargoValidator.validateCargoInput(
                "Short",
                "Valid Start Destination",
                "Valid End Destination",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                true
        ));
    }

    @Test
    void testValidateCargoInputWithInvalidStartDestination() {
        assertFalse(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Short",
                "Valid End Destination",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                true
        ));
    }

    @Test
    void testValidateCargoInputWithInvalidEndDestination() {
        assertFalse(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Valid Start Destination",
                "Short",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                true
        ));
    }

    @Test
    void testValidateCargoInputWithNullAssignedDate() {
        assertFalse(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Valid Start Destination",
                "Valid End Destination",
                null,
                LocalDate.now().plusDays(7),
                true
        ));
    }

    @Test
    void testValidateCargoInputWithNullDeliveryDate() {
        assertFalse(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Valid Start Destination",
                "Valid End Destination",
                LocalDate.now(),
                null,
                true
        ));
    }

    @Test
    void testValidateCargoInputWithNoTruckerSelected() {
        assertFalse(cargoValidator.validateCargoInput(
                "Valid Client Name",
                "Valid Start Destination",
                "Valid End Destination",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                false
        ));
    }
}