package com.trucker_system.truckersystem.fxControllers;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CargoTable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty client;
    private SimpleStringProperty cargo;
    private SimpleStringProperty pickupAddress;
    private SimpleStringProperty deliveryAddress;
    private ObjectProperty<LocalDate> deadline;
    private SimpleBooleanProperty finished;
    private SimpleStringProperty deleteBtn;

    public CargoTable() {
    }

    public CargoTable(int id, String client, String cargo, String pickupAddress, String deliveryAddress, LocalDate deadline, boolean finished) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleStringProperty(client);
        this.cargo = new SimpleStringProperty(cargo);
        this.pickupAddress = new SimpleStringProperty(pickupAddress);
        this.deliveryAddress = new SimpleStringProperty(deliveryAddress);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.finished = new SimpleBooleanProperty(finished);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getClient() {
        return client.get();
    }

    public SimpleStringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }

    public String getCargo() {
        return cargo.get();
    }

    public SimpleStringProperty cargoProperty() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo.set(cargo);
    }

    public String getPickupAddress() {
        return pickupAddress.get();
    }

    public SimpleStringProperty pickupAddressProperty() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress.set(pickupAddress);
    }

    public String getDeliveryAddress() {
        return deliveryAddress.get();
    }

    public SimpleStringProperty deliveryAddressProperty() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress.set(deliveryAddress);
    }

    public ObjectProperty<LocalDate> getDeadline() {
        return deadline;
    }

    public void setDeadline(ObjectProperty<LocalDate> deadline) {
        this.deadline = deadline;
    }

    public boolean isFinished() {
        return finished.get();
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public String getDeleteBtn() {
        return deleteBtn.get();
    }

    public SimpleStringProperty deleteBtnProperty() {
        return deleteBtn;
    }

    public void setDeleteBtn(String deleteBtn) {
        this.deleteBtn.set(deleteBtn);
    }
}
