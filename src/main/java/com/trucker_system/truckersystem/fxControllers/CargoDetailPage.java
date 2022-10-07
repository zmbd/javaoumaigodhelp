package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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
    @FXML
    public ChoiceBox<String> cargoTruckerChoiceBox;

    private Cargo cargo = null;
    private Trucker trucker = null;
    private List<Trucker> truckerList = null;
    private Manager manager = null;

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
    private final UserHib userHib = new UserHib(entityManagerFactory);

    public void initData(Cargo cargo, Trucker trucker, Manager manager) {
        this.cargo = cargo;
        this.trucker = trucker;
        this.manager = manager;

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

        if (this.trucker == null) {
            this.truckerList = userHib.getAllTruckers();

            this.truckerList.forEach(tl -> {
                cargoTruckerChoiceBox.getItems().add(tl.getName() + " " + tl.getSurname() + ", " + tl.getEmail());
            });

            //fix automatically selected when manager is selecting item from ListView, as it already has trucker assigned.
            cargoTruckerChoiceBox.getSelectionModel().select(0);
        } else {
            cargoClientInput.setVisible(false);
            cargoStartDestInput.setVisible(false);
            cargoEndDestInput.setVisible(false);
            cargoTruckerChoiceBox.setVisible(false);

            cargoTruckerText.setText(this.trucker.getName() + " " + this.trucker.getSurname() + ", " + this.trucker.getEmail());
        }
    }

    public void cargoUpdateBtnAction(ActionEvent event) {
    }

    public void cargoDeleteBtnAction(ActionEvent event) {
    }

    public void cargoCreateBtnAction(ActionEvent event) {
    }
}
