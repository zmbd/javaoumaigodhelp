package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.utils.ConfirmationDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CargoDetailPage extends Stage {
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
    @FXML
    public ChoiceBox<String> cargoTruckerChoiceBox;

    private Cargo cargo = null;
    private Trucker trucker = null;
    private List<Trucker> truckerList = null;
    private Manager manager = null;
    private ListView<String> listView;
    private ListView<String> listViewSecondary;

    private Stage stage = null;

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
    private final UserHib userHib = new UserHib(entityManagerFactory);
    private final CargoHib cargoHib = new CargoHib(entityManagerFactory);

    public Cargo updateParent() {
        return this.cargo;
    }

    public void initData(Cargo cargo, Trucker trucker, Manager manager, ListView<String> listView, ListView<String> listViewSecondary) {
        this.cargo = cargo;
        this.trucker = trucker;
        this.manager = manager;
        this.listView = listView;
        this.listViewSecondary = listViewSecondary;

        clientLabel.setText("Client:");
        cargoStartDestLabel.setText("Pickup address:");
        cargoEndDestLabel.setText("Deliver address:");
        cargoAssignedLabel.setText("Assigned on:");
        cargoDeliverLabel.setText("Deliver deadline:");
        cargoTruckerLabel.setText("Driver:");

        if (this.trucker != null || this.manager != null) {
            cargoClientText.setText(this.cargo.getClient());
            cargoStartDestText.setText(this.cargo.getStartDestination());
            cargoEndDestText.setText(this.cargo.getFinalDestination());
            cargoAssignedDate.setValue(this.cargo.getAssignedAt());
            cargoEndDate.setValue(this.cargo.getDeliverUntil());
        }

        if (this.trucker == null && this.cargo != null) {
            cargoCreateBtn.setVisible(false);

            this.truckerList = userHib.getAllTruckers();

            cargoClientInput.setText(this.cargo.getClient());
            cargoStartDestInput.setText(this.cargo.getStartDestination());
            cargoEndDestInput.setText(this.cargo.getFinalDestination());

            Trucker assignedTrucker = this.cargo.getTrucker();
            AtomicInteger assignedTruckerInd = new AtomicInteger();
            AtomicInteger lambdaIndex = new AtomicInteger(0);

            this.truckerList.forEach(tl -> {
                if (assignedTrucker != null && tl.getLogin().equals(assignedTrucker.getLogin())) {
                    assignedTruckerInd.set(lambdaIndex.get());
                }

                cargoTruckerChoiceBox.getItems().add(tl.getName() + " " + tl.getSurname() + ", " + tl.getEmail());
                lambdaIndex.getAndIncrement();
            });

            //fix automatically selected when manager is selecting item from ListView, as it already has trucker assigned.
            if (assignedTrucker != null) {
                cargoTruckerChoiceBox.getSelectionModel().select((Integer) assignedTruckerInd.get());
            }
        } else {
            cargoClientInput.setVisible(false);
            cargoStartDestInput.setVisible(false);
            cargoEndDestInput.setVisible(false);
            cargoAssignedDate.setDisable(true);
            cargoAssignedDate.setOpacity(1);
            cargoEndDate.setDisable(true);
            cargoEndDate.setOpacity(1);
            cargoTruckerChoiceBox.setVisible(false);
            cargoCreateBtn.setVisible(false);
            cargoUpdateBtn.setVisible(false);
            cargoDeleteBtn.setVisible(false);

            cargoTruckerText.setText(this.trucker.getName() + " " + this.trucker.getSurname() + ", " + this.trucker.getEmail());
        }
    }

    public void updateListView(ListView<String> listView, ListView<String> listViewSecondary) {
        var selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if(this.cargo != null) {
            listView.getSelectionModel().clearSelection();
            listView.getItems().set(selectedIndex, this.cargo.getClient());
            if (listViewSecondary != null && this.cargo.getTrucker() != null) {
                listViewSecondary.getItems().add(this.cargo.getClient());
                listView.getItems().remove(selectedIndex);
            }
        }
        else {
            listView.getSelectionModel().clearSelection();
            listView.getItems().remove(selectedIndex);
        }

    }

    public void cargoUpdateBtnAction(ActionEvent event) {
        if (cargoClientInput.getText().length() > 5 && cargoStartDestInput.getText().length() > 10 && cargoEndDestInput.getText().length() > 10 && cargoAssignedDate.getValue() != null && cargoEndDate.getValue() != null && !cargoTruckerChoiceBox.getSelectionModel().isEmpty()) {
            this.cargo.setClient(cargoClientInput.getText());
            this.cargo.setStartDestination(cargoStartDestInput.getText());
            this.cargo.setFinalDestination(cargoEndDestInput.getText());
            this.cargo.setAssignedAt(cargoAssignedDate.getValue());
            this.cargo.setDeliverUntil(cargoEndDate.getValue());
            this.cargo.setTrucker(this.truckerList.get(cargoTruckerChoiceBox.getSelectionModel().getSelectedIndex()));

            updateListView(this.listView, this.listViewSecondary);

            this.stage = (Stage) cargoUpdateBtn.getScene().getWindow();
            this.stage.close();

            cargoHib.updateCargo(this.cargo);

        }
    }

    public void cargoDeleteBtnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        boolean result = ConfirmationDialog.deletionConfirmation(alert.getAlertType(), "", "Are you sure you want to delete cargo?");

        if (result) {
            cargoHib.deleteCargo(this.cargo);
            this.cargo = null;

            updateListView(this.listView, this.listViewSecondary);

            this.stage = (Stage) cargoDeleteBtn.getScene().getWindow();
            this.stage.close();
        }
    }

    public void cargoCreateBtnAction(ActionEvent event) {
    }
}
