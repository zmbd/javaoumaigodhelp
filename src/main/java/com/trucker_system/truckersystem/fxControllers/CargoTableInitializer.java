package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CargoTableInitializer {
    private final CargoHib cargoHib;
    private final TableView<CargoTable> cargoTableView;
    private final TableColumn<CargoTable, Integer> cargoColumnId;
    private final TableColumn<CargoTable, String> cargoColumnClient;
    private final TableColumn<CargoTable, String> cargoColumnCargo;
    private final TableColumn<CargoTable, String> cargoColumnPickup;
    private final TableColumn<CargoTable, String> cargoColumnDelivery;
    private final TableColumn<CargoTable, LocalDate> cargoColumnDeadline;
    private final TableColumn<CargoTable, Boolean> cargoColumnFinished;
    private final TableColumn<CargoTable, String> cargoColumnDelete;

    public CargoTableInitializer(CargoHib cargoHib,
                                 TableView<CargoTable> cargoTableView,
                                 TableColumn<CargoTable, Integer> cargoColumnId,
                                 TableColumn<CargoTable, String> cargoColumnClient,
                                 TableColumn<CargoTable, String> cargoColumnCargo,
                                 TableColumn<CargoTable, String> cargoColumnPickup,
                                 TableColumn<CargoTable, String> cargoColumnDelivery,
                                 TableColumn<CargoTable, LocalDate> cargoColumnDeadline,
                                 TableColumn<CargoTable, Boolean> cargoColumnFinished,
                                 TableColumn<CargoTable, String> cargoColumnDelete) {
        this.cargoHib = cargoHib;
        this.cargoTableView = cargoTableView;
        this.cargoColumnId = cargoColumnId;
        this.cargoColumnClient = cargoColumnClient;
        this.cargoColumnCargo = cargoColumnCargo;
        this.cargoColumnPickup = cargoColumnPickup;
        this.cargoColumnDelivery = cargoColumnDelivery;
        this.cargoColumnDeadline = cargoColumnDeadline;
        this.cargoColumnFinished = cargoColumnFinished;
        this.cargoColumnDelete = cargoColumnDelete;
    }

    public void initialize() {
        cargoTableView.setEditable(true);

        cargoColumnId.setCellValueFactory(new PropertyValueFactory<CargoTable, Integer>("id"));

        cargoColumnClient.setCellValueFactory(new PropertyValueFactory<CargoTable, String>("client"));
        cargoColumnClient.setCellFactory(TextFieldTableCell.forTableColumn());
        cargoColumnClient.setOnEditCommit(cargoTableStringCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableStringCellEditEvent.getRowValue().getId(), cargoTableStringCellEditEvent.getNewValue(), CargoEditType.CLIENT);
            cargoTableStringCellEditEvent.getTableView().getItems().get(cargoTableStringCellEditEvent.getTablePosition().getRow()).setClient(cargoTableStringCellEditEvent.getNewValue());
        });

        cargoColumnCargo.setCellValueFactory(new PropertyValueFactory<CargoTable, String>("cargo"));
        cargoColumnCargo.setCellFactory(TextFieldTableCell.forTableColumn());
        cargoColumnCargo.setOnEditCommit(cargoTableStringCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableStringCellEditEvent.getRowValue().getId(), cargoTableStringCellEditEvent.getNewValue(), CargoEditType.CARGO);
            cargoTableStringCellEditEvent.getTableView().getItems().get(cargoTableStringCellEditEvent.getTablePosition().getRow()).setCargo(cargoTableStringCellEditEvent.getNewValue());
        });

        cargoColumnPickup.setCellValueFactory(new PropertyValueFactory<CargoTable, String>("pickupAddress"));
        cargoColumnPickup.setCellFactory(TextFieldTableCell.forTableColumn());
        cargoColumnPickup.setOnEditCommit(cargoTableStringCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableStringCellEditEvent.getRowValue().getId(), cargoTableStringCellEditEvent.getNewValue(), CargoEditType.PICKUP);
            cargoTableStringCellEditEvent.getTableView().getItems().get(cargoTableStringCellEditEvent.getTablePosition().getRow()).setPickupAddress(cargoTableStringCellEditEvent.getNewValue());
        });

        cargoColumnDelivery.setCellValueFactory(new PropertyValueFactory<CargoTable, String>("deliveryAddress"));
        cargoColumnDelivery.setCellFactory(TextFieldTableCell.forTableColumn());
        cargoColumnDelivery.setOnEditCommit(cargoTableStringCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableStringCellEditEvent.getRowValue().getId(), cargoTableStringCellEditEvent.getNewValue(), CargoEditType.DELIVERY);
            cargoTableStringCellEditEvent.getTableView().getItems().get(cargoTableStringCellEditEvent.getTablePosition().getRow()).setDeliveryAddress(cargoTableStringCellEditEvent.getNewValue());
        });

        cargoColumnDeadline.setCellValueFactory(cell -> cell.getValue().getDeadline());
        cargoColumnDeadline.setCellFactory(TextFieldTableCell.<CargoTable, LocalDate>forTableColumn(new StringConverter<LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate localDate) {
                return formatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String s) {
                return LocalDate.parse(s);
            }
        }));
        cargoColumnDeadline.setOnEditCommit(cargoTableLocalDateCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableLocalDateCellEditEvent.getRowValue().getId(), cargoTableLocalDateCellEditEvent.getNewValue(), CargoEditType.DEADLINE);
            cargoTableLocalDateCellEditEvent.getTableView().getItems().get(cargoTableLocalDateCellEditEvent.getTablePosition().getRow()).setDeliveryAddress(String.valueOf(cargoTableLocalDateCellEditEvent.getNewValue()));
        });

        cargoColumnFinished.setCellValueFactory(new PropertyValueFactory<CargoTable, Boolean>("finished"));
        cargoColumnFinished.setCellFactory(TextFieldTableCell.<CargoTable, Boolean>forTableColumn(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean aBoolean) {
                return aBoolean ? "YES" : "NO";
            }

            @Override
            public Boolean fromString(String s) {
                return s.equals("YES");
            }
        }));
        cargoColumnFinished.setOnEditCommit(cargoTableBooleanCellEditEvent -> {
            updateCargoOnCellCommit(cargoTableBooleanCellEditEvent.getRowValue().getId(), cargoTableBooleanCellEditEvent.getNewValue(), CargoEditType.FINISHED);
            cargoTableBooleanCellEditEvent.getTableView().getItems().get(cargoTableBooleanCellEditEvent.getTablePosition().getRow()).setFinished(cargoTableBooleanCellEditEvent.getNewValue());
        });

        cargoColumnDelete.setCellValueFactory(new PropertyValueFactory<CargoTable, String>("deleteBtn"));
        Callback<TableColumn<CargoTable, String>, TableCell<CargoTable, String>> cellFactory
                = new Callback<>() {

            @Override
            public TableCell<CargoTable, String> call(TableColumn<CargoTable, String> cargoTableStringTableColumn) {
                final TableCell<CargoTable, String> cell = new TableCell<CargoTable, String>() {
                    final Button btn = new Button("DELETE");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                CargoTable selectedRow = getTableView().getItems().get(getIndex());
                                cargoTableView.getItems().remove(selectedRow);
                                cargoHib.deleteCargoById(selectedRow.getId());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        cargoColumnDelete.setCellFactory(cellFactory);
    }

    <T> void updateCargoOnCellCommit(int id, T value, CargoEditType editType) {
        Cargo cargo = getEditedCargo(id);
        switch (editType) {
            case CLIENT -> cargo.setClient((String) value);
            case CARGO -> cargo.setCargo((String) value);
            case PICKUP -> cargo.setStartDestination((String) value);
            case DELIVERY -> cargo.setFinalDestination((String) value);
            case DEADLINE -> cargo.setDeliverUntil((LocalDate) value);
            case FINISHED -> cargo.setFinished((Boolean) value);
        }
        cargoHib.updateCargo(cargo);
    }

    Cargo getEditedCargo(int id) {
        return cargoHib.getCargoById(id);
    }
}