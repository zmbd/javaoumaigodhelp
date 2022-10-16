package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Truck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

public class AddCargoModal {
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

    private Cargo cargo = null;
    private Consumer<Cargo> cargoConsumer;
    private CargoHib cargoHib = null;

    public void setCargoConsumerCallback(Consumer<Cargo> cargoConsumer) { this.cargoConsumer = cargoConsumer; }

    public void initData(CargoHib cargoHib) { this.cargoHib = cargoHib; }

    public void onCargoAdd(ActionEvent actionEvent) {
        if (addClientField.getText().length() > 3 && addCargoField.getText().length() >= 5 && addPickupField.getText().length() > 10 && addDeliveryField.getText().length() > 10 && selectDeadlineDate.getValue() != null) {
            this.cargo = new Cargo(addClientField.getText(), addPickupField.getText(), addDeliveryField.getText(), LocalDate.now(), selectDeadlineDate.getValue(), addCargoField.getText(), false);

            this.cargoHib.createCargo(this.cargo);
            cargoConsumer.accept(this.cargo);

            Stage stage = (Stage) addCargoBtn.getScene().getWindow();
            stage.close();
        }
    }
}
