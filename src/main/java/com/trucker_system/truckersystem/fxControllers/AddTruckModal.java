package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.TruckHib;
import com.trucker_system.truckersystem.model.Truck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class AddTruckModal {
    @FXML
    public TextField addBrandField;
    @FXML
    public TextField addModelField;
    @FXML
    public TextField addHpfield;
    @FXML
    public TextField addEngineField;
    @FXML
    public TextField addReleaseField;
    @FXML
    public Button addBtn;


    private Consumer<Truck> truckConsumer = null;
    private Truck truck = null;
    private TruckHib truckHib = null;

    public void setTruckConsumerCallback(Consumer<Truck> truckConsumer) { this.truckConsumer = truckConsumer; }

    public void initData(TruckHib truckHib) { this.truckHib = truckHib; }


    public void onTruckAdd(ActionEvent actionEvent) {
        if (addBrandField.getText().length() >= 3 && !addModelField.getText().isEmpty() && !addHpfield.getText().isEmpty() && !addEngineField.getText().isEmpty() && addReleaseField.getText().length() == 4) {
            this.truck = new Truck(addBrandField.getText(), addModelField.getText(), Integer.parseInt(addHpfield.getText()), Double.parseDouble(addEngineField.getText()), Integer.parseInt(addReleaseField.getText()));
            List<Truck> truckList = this.truckHib.getAllTrucks();

            if (truckList.stream().filter(t -> t.getBrand() == truck.getBrand() && t.getModel() == truck.getModel()).findAny().orElse(null) == null) {
                this.truckHib.createTruck(truck);
                truckConsumer.accept(truck);
            }

            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.close();
        }
    }
}
