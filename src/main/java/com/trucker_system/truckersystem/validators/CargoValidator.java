package com.trucker_system.truckersystem.validators;

import java.time.LocalDate;

public class CargoValidator {
    private static final int MIN_CLIENT_LENGTH = 5;
    private static final int MIN_DESTINATION_LENGTH = 10;

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
}