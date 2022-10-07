package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.model.Cargo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CargoDetailPage {
    @FXML
    public Label cargoDetailsLabel;
    @FXML
    public Label clientLabel;
    @FXML
    public Label cargoStartDestLabel;
    @FXML
    public Label cargoEndDestLabel;
    @FXML
    public Label cargoAssignedLabel;
    @FXML
    public Label cargoDeliverLabel;
    @FXML
    public Label cargoTruckerLabel;
    @FXML
    public Button cargoUpdateBtn;
    @FXML
    public Button cargoDeleteBtn;
    @FXML
    public Button cargoCreateBtn;
    @FXML
    public Text cargoStartDestText;
    @FXML
    public Text cargoTruckerText;
    @FXML
    public Text cargoEndDestText;
    @FXML
    public DatePicker cargoAssignedDate;
    @FXML
    public DatePicker cargoEndDate;
    @FXML
    public TextField cargoClientInput;
    @FXML
    public TextField cargoStartDestInput;
    @FXML
    public TextField cargoEndDestInput;
    @FXML
    public Text cargoClientText;

    private Cargo cargo;

    public void initData(Cargo cargo, boolean isManager) {
        this.cargo = cargo;

        clientLabel.setText("Client:");
        cargoStartDestLabel.setText("Pickup address:");
        cargoEndDestLabel.setText("Deliver address:");
        cargoAssignedLabel.setText("Assigned on:");
        cargoDeliverLabel.setText("Deliver deadline:");
        cargoTruckerLabel.setText("Driver:");


        if (isManager) {
            //later
        } else {
            cargoClientInput.setVisible(false);
            cargoStartDestInput.setVisible(false);
            cargoEndDestInput.setVisible(false);


        }
    }

    public void cargoUpdateBtnAction(ActionEvent event) {
    }

    public void cargoDeleteBtnAction(ActionEvent event) {
    }

    public void cargoCreateBtnAction(ActionEvent event) {
    }
}
