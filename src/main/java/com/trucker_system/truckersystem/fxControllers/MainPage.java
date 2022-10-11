package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.HelloApplication;
import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

enum EditType {
    LOGIN,
    PASSWORD,
    NAME,
    SURNAME,
    EMAIL,
    PHONE,
    ADMIN,
}

@Getter
@Setter
public class MainPage implements Initializable {
    @FXML
    public AnchorPane tabMain;
    @FXML
    public Text welcomeName;
    @FXML
    public ListView<String> cargosListView;
    @FXML
    public ListView<String> unassignedCargosListView;
    @FXML
    public Tab managementTab;
    @FXML
    public Button createUserBtn;
    @FXML
    public TableView<UsersTable> usersTableView;
    @FXML
    public TableColumn<UsersTable, Integer> idColumn;
    @FXML
    public TableColumn<UsersTable, String> userTypeColumn;
    @FXML
    public TableColumn<UsersTable, String> loginColumn;
    @FXML public TableColumn<UsersTable, String> passwordColumn;
    @FXML
    public TableColumn<UsersTable, String> nameColumn;
    @FXML
    public TableColumn<UsersTable, String> surnameColumn;
    @FXML
    public TableColumn<UsersTable, String> emailColumn;
    @FXML
    public TableColumn<UsersTable, String> phoneColumn;
    @FXML
    public TableColumn<UsersTable, Boolean> adminColumn;
    @FXML
    public TableColumn<UsersTable, Button> deleteColumn;


    private Trucker trucker = null;
    private Manager manager = null;
    private boolean isManager;
    private List<Cargo> unassignedCargos = null;
    private List<Cargo> assignedCargos = null;
    private List<Trucker> allTruckers = null;
    private List<Manager> allManagers = null;
    private EntityManagerFactory entityManagerFactory = null;
    private UserHib userHib = null;
    private CargoHib cargoHib = null;
    private EditType editType;

    public List<Cargo> getUnassignedCargos() {
        return cargoHib.getUnassignedCargos();
    }
    public List<Cargo> getAssignedCargos(Trucker trucker) {
        if (trucker != null) return cargoHib.getCargoListById(trucker);

        return cargoHib.getOnlyAssignedCargos();
    }

    public void updateCargos(List<Cargo> unassignedCargos, List<Cargo> assignedCargos) {
        this.unassignedCargos = unassignedCargos;
        this.assignedCargos = assignedCargos;
    }

    public void updateListView(Trucker trucker, Manager manager) {
        this.unassignedCargos = cargoHib.getUnassignedCargos();

        this.unassignedCargos.forEach(uc -> {
            unassignedCargosListView.getItems().add(uc.getClient());
        });

        if (manager != null) {
            this.manager = manager;

            this.usersTableView.setItems(getAllUsers());

            welcomeName.setText(this.manager.getName());

            this.assignedCargos = cargoHib.getOnlyAssignedCargos();

            this.assignedCargos.forEach(c -> {
                cargosListView.getItems().add(c.getClient());
            });
        }
        else {
            managementTab.setDisable(true);
            this.trucker = trucker;
            welcomeName.setText(this.trucker.getName());
            this.assignedCargos = this.trucker.getCargosList();

            //refactor this s to generic method as used two times
            this.assignedCargos.forEach(c -> {
                cargosListView.getItems().add(c.getClient());
            });
        }

        selectListViewItem(cargosListView, this.assignedCargos, null);
        selectListViewItem(unassignedCargosListView, this.unassignedCargos, cargosListView);
    }

    private ObservableList<UsersTable> getAllUsers() {
        ObservableList<UsersTable> observableList = FXCollections.observableArrayList();
        this.allTruckers = userHib.getAllTruckers();
        this.allManagers = userHib.getAllManagers();

        this.allManagers.forEach(t -> {
            observableList.add(new UsersTable(t.getId(), t.getDtype(), t.getLogin(), t.getPassword(), t.getName(), t.getSurname(), t.getEmail(), t.getPhoneNumber(), t.isAdmin()));
        });

        this.allTruckers.forEach(t -> {
            observableList.add(new UsersTable(t.getId(), t.getDtype(), t.getLogin(), t.getPassword(), t.getName(), t.getSurname(), t.getEmail(), t.getPhoneNumber(), false));
        });

        return observableList;
    }


    public <T> void initData(T value, boolean isManager) {
        this.isManager = isManager;

        if (isManager) {
            this.manager = (Manager) value;
        }
        else this.trucker = (Trucker) value;

        updateListView(this.trucker, this.manager);
    }

    public <T extends User> T filterList(List<T> list, int id) {
        return list.stream().filter(i -> id == i.getId()).findAny().orElse(null);
    }

    public <T extends User> void updateObjectOnCellCommit(List<T> list, T object, int id, String value, EditType editType) {
        object = filterList(list, id);
        switch (editType) {
            case LOGIN -> object.setLogin(value);
            case PASSWORD -> object.setPassword(value);
            case NAME -> object.setName(value);
            case SURNAME -> object.setSurname(value);
            case EMAIL -> object.setEmail(value);
            case PHONE -> object.setPhoneNumber(value);
        }
        userHib.updateUser(object);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
        userHib = new UserHib(entityManagerFactory);
        cargoHib = new CargoHib(entityManagerFactory);

        final Trucker trucker1 = null;
        final Manager manager1 = null;

        usersTableView.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, Integer>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("userType"));

        loginColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("login"));
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.LOGIN);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.LOGIN);
        });

        passwordColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PASSWORD);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PASSWORD);
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.NAME);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.NAME);
        });

        surnameColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.SURNAME);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.SURNAME);
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.EMAIL);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.EMAIL);
        });

        phoneColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("phoneNumber"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker")) updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PHONE);
            else updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PHONE);
        });

        adminColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, Boolean>("isAdmin"));
        adminColumn.setCellFactory(ac -> new TableCell<UsersTable, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "TRUE" : "FALSE");
            }
        });
        deleteColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, Button>("deleteBtn"));
    }

    public void selectListViewItem(ListView<String> listView, List<Cargo> cargo, ListView<String> listViewSecondary) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //ERROR ERROR SELECTING SAME ITEM WILL NOT TRIGGER THE EVENT ERROR ERROR FIX LATER MMMMMMMM
                try {
                    System.out.println(listView.getSelectionModel().getSelectedIndex() + "\t INDEXAS");
                    openCargoDetailWindow(cargo.get(listView.getSelectionModel().getSelectedIndex()), trucker, manager, listView, listViewSecondary);
                    //listView.getSelectionModel().clearSelection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openCargoDetailWindow(Cargo cargoItem, Trucker trucker, Manager manager, ListView<String> listView, ListView<String> listViewSecondary) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cargodetails-page.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        CargoDetailPage cargoDetailPage = fxmlLoader.getController();
        cargoDetailPage.initData(cargoItem, trucker, manager, listView, listViewSecondary);

        stage.setScene(scene);
        stage.show();
    }


    public void onCreateUser(ActionEvent event) {
    }
}
