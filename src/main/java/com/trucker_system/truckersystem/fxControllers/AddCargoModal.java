package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Truck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddCargoModal implements Initializable {
    @FXML
    public TextField addClientField;
    @FXML
    public TextField addCargoField;
    @FXML
    public TextField addPickupField;
    @FXML
    public TextField addDeliveryField;
    @FXML
    public DatePicker selectDeadlineDate;
    @FXML
    public Button addCargoBtn;
    public Label labelValidate;

    private Consumer<Cargo> cargoConsumer;
    private CargoHib cargoHib = null;

    public void setCargoConsumerCallback(Consumer<Cargo> cargoConsumer) { this.cargoConsumer = cargoConsumer; }

    public void initData(CargoHib cargoHib) { this.cargoHib = cargoHib; }

    public void onCargoAdd(ActionEvent actionEvent) {
        if (addClientField.getText().length() > 3 && addCargoField.getText().length() >= 5 && addPickupField.getText().length() > 10 && addDeliveryField.getText().length() > 10 && selectDeadlineDate.getValue() != null) {
            Cargo cargo = new Cargo(addClientField.getText(), addPickupField.getText(), addDeliveryField.getText(), LocalDate.now(), selectDeadlineDate.getValue(), addCargoField.getText(), false);

            this.cargoHib.createCargo(cargo);
            cargoConsumer.accept(cargo);

            Stage stage = (Stage) addCargoBtn.getScene().getWindow();
            stage.close();
        } else labelValidate.setText("Some details are invalid.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelValidate.setText("");
    }
}
