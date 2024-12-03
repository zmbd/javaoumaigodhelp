//package com.trucker_system.truckersystem.fxControllers;
//
//import com.trucker_system.truckersystem.hibernate.CargoHib;
//import com.trucker_system.truckersystem.hibernate.UserHib;
//import com.trucker_system.truckersystem.model.Cargo;
//import com.trucker_system.truckersystem.model.Manager;
//import com.trucker_system.truckersystem.model.Trucker;
//import com.trucker_system.truckersystem.service.CargoService;
//import com.trucker_system.truckersystem.utils.AlertDialog;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class CargoDetailPage extends Stage {
//    @FXML
//    public Label cargoDetailsLabel;
//    @FXML
//    public Label clientLabel;
//    @FXML
//    public Label cargoStartDestLabel;
//    @FXML
//    public Label cargoEndDestLabel;
//    @FXML
//    public Label cargoAssignedLabel;
//    @FXML
//    public Label cargoDeliverLabel;
//    @FXML
//    public Label cargoTruckerLabel;
//    @FXML
//    public Button cargoUpdateBtn;
//    @FXML
//    public Button cargoDeleteBtn;
//    @FXML
//    public Button cargoCreateBtn;
//    @FXML
//    public Text cargoStartDestText;
//    @FXML
//    public Text cargoTruckerText;
//    @FXML
//    public Text cargoEndDestText;
//    @FXML
//    public DatePicker cargoAssignedDate;
//    @FXML
//    public DatePicker cargoEndDate;
//    @FXML
//    public TextField cargoClientInput;
//    @FXML
//    public TextField cargoStartDestInput;
//    @FXML
//    public TextField cargoEndDestInput;
//    @FXML
//    public Text cargoClientText;
//    @FXML
//    public ChoiceBox<String> cargoTruckerChoiceBox;
//
//    Cargo cargo = null;
//    private Trucker trucker = null;
//    List<Trucker> truckerList = null;
//    private Manager manager = null;
//    private ListView<String> listView;
//    private ListView<String> listViewSecondary;
//
//    private Stage stage = null;
//
//    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
//    UserHib userHib = new UserHib(entityManagerFactory);
//    CargoHib cargoHib = new CargoHib(entityManagerFactory);
//
//    private final CargoService cargoService;
//
//    public CargoDetailPage() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("truckerdb");
//        UserHib userHib = new UserHib(emf);
//        CargoHib cargoHib = new CargoHib(emf);
//        this.cargoService = new CargoService(userHib, cargoHib);
//    }
//
//    // Constructor for testing
//    CargoDetailPage(CargoService cargoService) {
//        this.cargoService = cargoService;
//    }
//
//    public Cargo updateParent() {
//        return this.cargo;
//    }
//
//    public void initData(Cargo cargo, Trucker trucker, Manager manager, ListView<String> listView, ListView<String> listViewSecondary) {
//        this.cargo = cargo;
//        this.trucker = trucker;
//        this.manager = manager;
//        this.listView = listView;
//        this.listViewSecondary = listViewSecondary;
//
//        initializeLabels();
//
//        if (this.trucker != null || this.manager != null) {
//            displayCargoDetails();
//        }
//
//        if (this.trucker == null && this.cargo != null) {
//            setupManagerView();
//        } else {
//            setupTruckerView();
//        }
//    }
//
//    private void initializeLabels() {
//        clientLabel.setText("Client:");
//        cargoStartDestLabel.setText("Pickup address:");
//        cargoEndDestLabel.setText("Deliver address:");
//        cargoAssignedLabel.setText("Assigned on:");
//        cargoDeliverLabel.setText("Deliver deadline:");
//        cargoTruckerLabel.setText("Driver:");
//    }
//
//    private void displayCargoDetails() {
//        cargoClientText.setText(this.cargo.getClient());
//        cargoStartDestText.setText(this.cargo.getStartDestination());
//        cargoEndDestText.setText(this.cargo.getFinalDestination());
//        cargoAssignedDate.setValue(this.cargo.getAssignedAt());
//        cargoEndDate.setValue(this.cargo.getDeliverUntil());
//    }
//
//    private void setupManagerView() {
//        cargoCreateBtn.setVisible(false);
//        this.truckerList = cargoService.getAllTruckers();
//
//        cargoClientInput.setText(this.cargo.getClient());
//        cargoStartDestInput.setText(this.cargo.getStartDestination());
//        cargoEndDestInput.setText(this.cargo.getFinalDestination());
//
//        setupTruckerChoiceBox();
//    }
//
//    private void setupTruckerView() {
//        cargoClientInput.setVisible(false);
//        cargoStartDestInput.setVisible(false);
//        cargoEndDestInput.setVisible(false);
//        cargoAssignedDate.setDisable(true);
//        cargoAssignedDate.setOpacity(1);
//        cargoEndDate.setDisable(true);
//        cargoEndDate.setOpacity(1);
//        cargoTruckerChoiceBox.setVisible(false);
//        cargoCreateBtn.setVisible(false);
//        cargoUpdateBtn.setVisible(false);
//        cargoDeleteBtn.setVisible(false);
//
//        if (this.trucker != null) {
//            cargoTruckerText.setText(this.trucker.getName() + " " + this.trucker.getSurname() + ", " + this.trucker.getEmail());
//        }
//    }
//
//    private void setupTruckerChoiceBox() {
//        Trucker assignedTrucker = this.cargo.getTrucker();
//        AtomicInteger assignedTruckerInd = new AtomicInteger();
//        AtomicInteger lambdaIndex = new AtomicInteger(0);
//
//        this.truckerList.forEach(tl -> {
//            if (assignedTrucker != null && tl.getLogin().equals(assignedTrucker.getLogin())) {
//                assignedTruckerInd.set(lambdaIndex.get());
//            }
//            cargoTruckerChoiceBox.getItems().add(tl.getName() + " " + tl.getSurname() + ", " + tl.getEmail());
//            lambdaIndex.getAndIncrement();
//        });
//
//        if (assignedTrucker != null) {
//            cargoTruckerChoiceBox.getSelectionModel().select((Integer) assignedTruckerInd.get());
//        }
//    }
//
//    public void updateListView(ListView<String> listView, ListView<String> listViewSecondary) {
//        cargoService.updateListViews(this.cargo, listView, listViewSecondary);
//    }
//
//    public void cargoUpdateBtnAction(ActionEvent event) {
//        boolean isValid = cargoService.validateCargoInput(
//                cargoClientInput.getText(),
//                cargoStartDestInput.getText(),
//                cargoEndDestInput.getText(),
//                cargoAssignedDate.getValue(),
//                cargoEndDate.getValue(),
//                !cargoTruckerChoiceBox.getSelectionModel().isEmpty()
//        );
//
//        if (isValid) {
//            cargoService.updateCargo(
//                    this.cargo,
//                    cargoClientInput.getText(),
//                    cargoStartDestInput.getText(),
//                    cargoEndDestInput.getText(),
//                    cargoAssignedDate.getValue(),
//                    cargoEndDate.getValue(),
//                    this.truckerList.get(cargoTruckerChoiceBox.getSelectionModel().getSelectedIndex())
//            );
//
//            this.stage = (Stage) cargoUpdateBtn.getScene().getWindow();
//            this.stage.close();
//
//            updateListView(this.listView, this.listViewSecondary);
//        }
//    }
//
//    public void cargoDeleteBtnAction(ActionEvent event) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        boolean result = AlertDialog.deletionConfirmation(alert.getAlertType(), "", "Are you sure you want to delete cargo?");
//
//        if (result) {
//            cargoService.deleteCargo(this.cargo);
//            this.cargo = null;
//
//            updateListView(this.listView, this.listViewSecondary);
//
//            this.stage = (Stage) cargoDeleteBtn.getScene().getWindow();
//            this.stage.close();
//        }
//    }
//
//    public void cargoCreateBtnAction(ActionEvent event) {
//    }
//}

package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.UIManager.CargoUIManager;
import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.service.CargoService;
import com.trucker_system.truckersystem.utils.AlertDialog;
import com.trucker_system.truckersystem.validators.CargoValidator;
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

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
    private final CargoService cargoService;
    private final CargoValidator cargoValidator;
    private final CargoUIManager cargoUIManager;

    public CargoDetailPage() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("truckerdb");
        UserHib userHib = new UserHib(emf);
        CargoHib cargoHib = new CargoHib(emf);
        this.cargoService = new CargoService(userHib, cargoHib);
        this.cargoValidator = new CargoValidator();
        this.cargoUIManager = new CargoUIManager();
    }

    // Constructor for testing
    CargoDetailPage(CargoService cargoService, CargoValidator cargoValidator, CargoUIManager cargoUIManager) {
        this.cargoService = cargoService;
        this.cargoValidator = cargoValidator;
        this.cargoUIManager = cargoUIManager;
    }

    public Cargo updateParent() {
        return this.cargo;
    }

    public void initData(Cargo cargo, Trucker trucker, Manager manager, ListView<String> listView, ListView<String> listViewSecondary) {
        this.cargo = cargo;
        this.trucker = trucker;
        this.manager = manager;
        this.listView = listView;
        this.listViewSecondary = listViewSecondary;

        initializeLabels();

        if (this.trucker != null || this.manager != null) {
            displayCargoDetails();
        }

        if (this.trucker == null && this.cargo != null) {
            setupManagerView();
        } else {
            setupTruckerView();
        }
    }

    private void initializeLabels() {
        clientLabel.setText("Client:");
        cargoStartDestLabel.setText("Pickup address:");
        cargoEndDestLabel.setText("Deliver address:");
        cargoAssignedLabel.setText("Assigned on:");
        cargoDeliverLabel.setText("Deliver deadline:");
        cargoTruckerLabel.setText("Driver:");
    }

    private void displayCargoDetails() {
        cargoClientText.setText(this.cargo.getClient());
        cargoStartDestText.setText(this.cargo.getStartDestination());
        cargoEndDestText.setText(this.cargo.getFinalDestination());
        cargoAssignedDate.setValue(this.cargo.getAssignedAt());
        cargoEndDate.setValue(this.cargo.getDeliverUntil());
    }

    private void setupManagerView() {
        cargoCreateBtn.setVisible(false);
        this.truckerList = cargoService.getAllTruckers();

        cargoClientInput.setText(this.cargo.getClient());
        cargoStartDestInput.setText(this.cargo.getStartDestination());
        cargoEndDestInput.setText(this.cargo.getFinalDestination());

        setupTruckerChoiceBox();
    }

    private void setupTruckerView() {
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

        if (this.trucker != null) {
            cargoTruckerText.setText(this.trucker.getName() + " " + this.trucker.getSurname() + ", " + this.trucker.getEmail());
        }
    }

    private void setupTruckerChoiceBox() {
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

        if (assignedTrucker != null) {
            cargoTruckerChoiceBox.getSelectionModel().select((Integer) assignedTruckerInd.get());
        }
    }

    private void updateListView(ListView<String> listView, ListView<String> listViewSecondary) {
        cargoUIManager.updateListViews(this.cargo, listView, listViewSecondary);
    }

    public void cargoUpdateBtnAction(ActionEvent event) {
        boolean isValid = cargoValidator.validateCargoInput(
                cargoClientInput.getText(),
                cargoStartDestInput.getText(),
                cargoEndDestInput.getText(),
                cargoAssignedDate.getValue(),
                cargoEndDate.getValue(),
                !cargoTruckerChoiceBox.getSelectionModel().isEmpty()
        );

        if (isValid) {
            cargoService.updateCargo(
                    this.cargo,
                    cargoClientInput.getText(),
                    cargoStartDestInput.getText(),
                    cargoEndDestInput.getText(),
                    cargoAssignedDate.getValue(),
                    cargoEndDate.getValue(),
                    this.truckerList.get(cargoTruckerChoiceBox.getSelectionModel().getSelectedIndex())
            );

            this.stage = (Stage) cargoUpdateBtn.getScene().getWindow();
            this.stage.close();

            updateListView(this.listView, this.listViewSecondary);
        }
    }

    public void cargoDeleteBtnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        boolean result = AlertDialog.deletionConfirmation(alert.getAlertType(), "", "Are you sure you want to delete cargo?");

        if (result) {
            cargoService.deleteCargo(this.cargo);
            this.cargo = null;

            updateListView(this.listView, this.listViewSecondary);

            this.stage = (Stage) cargoDeleteBtn.getScene().getWindow();
            this.stage.close();
        }
    }

    public void cargoCreateBtnAction(ActionEvent event) {
        // Implementation for cargo creation if needed
    }
}