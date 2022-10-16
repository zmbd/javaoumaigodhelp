package com.trucker_system.truckersystem.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertDialog {
    public static boolean deletionConfirmation(Alert.AlertType alertType, String header, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle("TruckerSystem");
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        }

        return false;
    }

    public static void permissionsDialog(Alert alert) {
        alert.setTitle("TruckerSystem");
        alert.setHeaderText("ERROR!");
        alert.setContentText("You do not have permissions to perform this action.");
        alert.showAndWait();
    }
}
