package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.HelloApplication;
import com.trucker_system.truckersystem.hibernate.*;
import com.trucker_system.truckersystem.model.*;
import com.trucker_system.truckersystem.utils.AlertDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

enum EditType {
    LOGIN,
    PASSWORD,
    NAME,
    SURNAME,
    EMAIL,
    PHONE,
    ADMIN,
}

enum TruckEditType {
    BRAND,
    MODEL,
    HP,
    ENGINE,
    RELEASE
}

enum CargoEditType {
    CLIENT,
    CARGO,
    PICKUP,
    DELIVERY,
    DEADLINE,
    FINISHED
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
    @FXML
    public TableColumn<UsersTable, String> passwordColumn;
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
    public TableColumn<UsersTable, String> deleteColumn;
    @FXML
    public ChoiceBox<String> truckersTripsChoiceBox;
    @FXML
    public Text brandText;
    @FXML
    public Text modelText;
    @FXML
    public Text hpText;
    @FXML
    public Text engineText;
    @FXML
    public Text releaseText;
    @FXML
    public Button saveTruckBtn;
    @FXML
    public Button tripsDetailsBtn;
    @FXML
    public Label myTruckLabel;
    @FXML
    public ChoiceBox<String> trucksChoiceBox;
    @FXML
    public Text cargoDetailsText;
    @FXML
    public Text deliveryAddressText;
    @FXML
    public Text pickupAddressText;
    @FXML
    public Text assignedAtText;
    @FXML
    public Text clientText;
    @FXML
    public Text tripStatusText;
    @FXML
    public Text deliveryUntilText;
    @FXML
    public AnchorPane truckerAnchorPane;
    @FXML
    public AnchorPane truckerAnchorPaneTrips;
    @FXML
    public AnchorPane truckerAnchorPaneTruck;
    @FXML
    public Tab truckerTripsTab;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab managerTrucksTab;
    @FXML
    public TableView<TrucksTable> trucksTableView;
    @FXML
    public TableColumn<TrucksTable, Integer> trucksColumnId;
    @FXML
    public TableColumn<TrucksTable, String> trucksColumnBrand;
    @FXML
    public TableColumn<TrucksTable, String> trucksColumnModel;
    @FXML
    public TableColumn<TrucksTable, Integer> trucksColumnHp;
    @FXML
    public TableColumn<TrucksTable, Double> trucksColumnEngine;
    @FXML
    public TableColumn<TrucksTable, Integer> trucksColumnReleased;
    @FXML
    public Button addTruckBtn;
    @FXML
    public Tab managerCargoTab;
    @FXML
    public TableColumn<TrucksTable, String> trucksColumnDelete;
    @FXML
    public TableView<CargoTable> cargoTableView;
    @FXML
    public TableColumn<CargoTable, Integer> cargoColumnId;
    @FXML
    public TableColumn<CargoTable, String> cargoColumnClient;
    @FXML
    public TableColumn<CargoTable, String> cargoColumnCargo;
    @FXML
    public TableColumn<CargoTable, String> cargoColumnPickup;
    @FXML
    public TableColumn<CargoTable, String> cargoColumnDelivery;
    @FXML
    public TableColumn<CargoTable, LocalDate> cargoColumnDeadline;
    @FXML
    public TableColumn<CargoTable, Boolean> cargoColumnFinished;
    @FXML
    public TableColumn<CargoTable, String> cargoColumnDelete;
    @FXML
    public Button addCargoBtn;
    @FXML
    public ListView<Forum> forumListView;
    @FXML
    public TreeView<String> forumTreeView;
    @FXML
    public MenuItem forumCreateTopic;
    @FXML
    public MenuItem forumDeleteThread;
    @FXML
    public MenuItem forumReply;
    @FXML
    public MenuItem forumDelete;
    @FXML
    public MenuItem forumEditReply;
    @FXML
    public MenuItem forumEditTopic;


    private Trucker trucker = null;
    private Manager manager = null;
    private Truck truck = null;
    private boolean isManager;
    private List<Cargo> unassignedCargos = null;
    private List<Cargo> assignedCargos = null;
    private List<Cargo> assignedFilteredCargos = new ArrayList<>();
    private List<Trucker> allTruckers = null;
    private List<Manager> allManagers = null;
    private List<Truck> allTrucks = null;
    private List<Cargo> allCargos = null;
    private List<Forum> allForumThreads = null;
    private Forum selectedForumThread = null;
    private Comment selectedComment = null;
    private EntityManagerFactory entityManagerFactory = null;
    private UserHib userHib = null;
    private CargoHib cargoHib = null;
    private TruckHib truckHib = null;
    private ForumHib forumHib = null;
    private CommentHib commentHib = null;
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

        unassignedCargosListView.getItems().clear();
        cargosListView.getItems().clear();

        this.unassignedCargos.forEach(uc -> {
            unassignedCargosListView.getItems().add(uc.getClient());
        });

        if (manager != null) {
            this.manager = manager;

            this.usersTableView.setItems(getAllUsers());
            this.trucksTableView.setItems(getAllTrucks());
            this.cargoTableView.setItems(getAllCargos());

            welcomeName.setText(this.manager.getName());

            this.assignedCargos = cargoHib.getOnlyAssignedCargos();

            this.assignedCargos.forEach(c -> {
                if (!c.isFinished()) {
                    this.assignedFilteredCargos.add(c);
                    cargosListView.getItems().add(c.getClient());
                }

            });
        } else {
            managementTab.setDisable(true);
            this.trucker = trucker;
            welcomeName.setText(this.trucker.getName());
            this.assignedCargos = this.trucker.getCargosList();

            //refactor this s to generic method as used two times
            this.assignedCargos.forEach(c -> {
                if (!c.isFinished()) {
                    this.assignedFilteredCargos.add(c);
                    cargosListView.getItems().add(c.getClient());
                }
            });
        }

        selectListViewItem(cargosListView, this.assignedFilteredCargos, null);
        selectListViewItem(unassignedCargosListView, this.unassignedCargos, cargosListView);
    }

    public void populateForumListView() {
        this.allForumThreads = this.forumHib.getAllForumsThreads();

        this.allForumThreads.forEach(f -> {
            forumListView.getItems().add(f);
        });
    }

    public void populateForumTreeView(Forum forum) {
        if (forum != null) {
            TreeItem<String> rootItem = new TreeItem<>(forum.displayMessage());

            int index = 0;
            for (Comment comment : forum.getComments()) {
                if (index > 0) {
                    System.out.println("INDEX: " + index);
                    setChildComment(comment, comment.getParentComment(), rootItem, forum);
                }
                index++;
            }

            forumTreeView.setRoot(rootItem);
        }
    }

    public void setChildComment(Comment comment, Comment parent, TreeItem<String> parentItem, Forum forum) {
        if (comment.getForum().getId() == forum.getId()) {
            System.out.println("Comment ID: " + comment.getId() + " , " + comment.getCommentText() + "\tParent: " + parent.getId() + ", " + parent.getCommentText());
            TreeItem<String> treeItem = new TreeItem<>(comment.displayMessage());
            parentItem.getChildren().add(treeItem);
            parentItem.setExpanded(true);

            if (comment.getReplies() != null) comment.getReplies().forEach(reply -> setChildComment(reply, reply.getParentComment(), treeItem, forum));
        }
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

    public ObservableList<CargoTable> getAllCargos() {
        ObservableList<CargoTable> observableList = FXCollections.observableArrayList();
        this.allCargos = this.cargoHib.getAllCargos();

        this.allCargos.forEach(c -> {
            observableList.add(new CargoTable(c.getId(), c.getClient(), c.getCargo(), c.getStartDestination(), c.getFinalDestination(), c.getDeliverUntil(), c.isFinished()));
        });

        return observableList;
    }


    public ObservableList<TrucksTable> getAllTrucks() {
        ObservableList<TrucksTable> observableList = FXCollections.observableArrayList();
        this.allTrucks = truckHib.getAllTrucks();

        this.allTrucks.forEach(t -> {
            observableList.add(new TrucksTable(t.getId(), t.getBrand(), t.getModel(), t.getHp(), t.getEngine(), t.getReleaseYear()));
        });

        return observableList;
    }

    public void tripsTab() {
        List<Truck> trucks = truckHib.getAllTrucks();
        trucks.forEach(truck -> {
            trucksChoiceBox.getItems().add(truck.getBrand() + " " + truck.getModel());
            System.out.println(this.trucker);
            if (this.trucker.getTruck() != null && truck.getId() == this.trucker.getTruck().getId())
                trucksChoiceBox.getSelectionModel().select(truck.getBrand() + " " + truck.getModel());
        });

        if (trucksChoiceBox.getSelectionModel().getSelectedItem() != null) {
            Truck truck = trucks.stream().filter(t -> t.getId() == this.trucker.getTruck().getId()).findAny().orElse(null);
            brandText.setText(truck.getBrand());
            modelText.setText(truck.getModel());
            hpText.setText(String.valueOf(truck.getHp()));
            engineText.setText(String.valueOf(truck.getEngine()));
            releaseText.setText(String.valueOf(truck.getReleaseYear()));
        } else {
            brandText.setText("N/A");
            modelText.setText("N/A");
            hpText.setText(String.valueOf(0));
            engineText.setText(String.valueOf(0));
            releaseText.setText(String.valueOf(0));
        }

        this.assignedCargos = cargoHib.getCargoListById(this.trucker);

        this.assignedCargos.forEach(cargo -> {
            truckersTripsChoiceBox.getItems().add(cargo.getClient() + ", " + cargo.getAssignedAt());
        });

        truckersTripsChoiceBox.getSelectionModel().select(0);
    }


    public <T> void initData(T value, boolean isManager) {
        this.isManager = isManager;

        if (isManager) {
            this.manager = (Manager) value;
            tabPane.getTabs().remove(truckerTripsTab);
        } else {
            this.trucker = (Trucker) value;
            tabPane.getTabs().remove(managerTrucksTab);
            tabPane.getTabs().remove(managerCargoTab);
            tripsTab();
        }

        updateListView(this.trucker, this.manager);
    }

    public <T extends User> T filterList(List<T> list, int id) {
        return list.stream().filter(i -> id == i.getId()).findAny().orElse(null);
    }

    public Truck getEditedTruck(int id) {
        return this.allTrucks.stream().filter(t -> id == t.getId()).findAny().orElse(null);
    }

    public Cargo getEditedCargo(int id) {
        return this.allCargos.stream().filter(c -> id == c.getId()).findAny().orElse(null);
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

    public <T> void updateCargoOnCellCommit(int id, T value, CargoEditType editType) {
        Cargo cargo = getEditedCargo(id);
        switch (editType) {
            case CLIENT -> cargo.setClient((String) value);
            case CARGO ->  cargo.setCargo((String) value);
            case PICKUP -> cargo.setStartDestination((String) value);
            case DELIVERY -> cargo.setFinalDestination((String) value);
            case DEADLINE -> cargo.setDeliverUntil((LocalDate) value);
            case FINISHED -> cargo.setFinished((Boolean) value);
        }

        cargoHib.updateCargo(cargo);
    }

    public <T> void updateTruckOnCellCommit(int id, T value, TruckEditType editType) {
        Truck truck = getEditedTruck(id);
        switch (editType) {
            case BRAND -> truck.setBrand((String) value);
            case MODEL -> truck.setModel((String) value);
            case ENGINE -> truck.setEngine((Double) value);
            case HP -> truck.setHp((Integer) value);
            case RELEASE -> truck.setReleaseYear((Integer) value);
        }

        this.truckHib.updateTruck(truck);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
        userHib = new UserHib(entityManagerFactory);
        cargoHib = new CargoHib(entityManagerFactory);
        truckHib = new TruckHib(entityManagerFactory);
        forumHib = new ForumHib(entityManagerFactory);
        commentHib = new CommentHib(entityManagerFactory);


        this.trucksChoiceBox.setOnAction(this::onTruckSelected);
        this.truckersTripsChoiceBox.setOnAction(this::onTripSelected);

        populateForumListView();
        selectForumListView(forumListView);

        initializeTrucksTable();
        initializeCargoTable();
        initializeUsersTable();
    }

    public void initializeCargoTable() {
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


    public void initializeTrucksTable() {
        trucksTableView.setEditable(true);
        trucksColumnId.setCellValueFactory(new PropertyValueFactory<TrucksTable, Integer>("id"));

        trucksColumnBrand.setCellValueFactory(new PropertyValueFactory<TrucksTable, String>("brand"));
        trucksColumnBrand.setCellFactory(TextFieldTableCell.forTableColumn());
        trucksColumnBrand.setOnEditCommit(trucksTableStringCellEditEvent -> {
            updateTruckOnCellCommit(trucksTableStringCellEditEvent.getRowValue().getId(), trucksTableStringCellEditEvent.getNewValue(), TruckEditType.BRAND);
            trucksTableStringCellEditEvent.getTableView().getItems().get(trucksTableStringCellEditEvent.getTablePosition().getRow()).setBrand(trucksTableStringCellEditEvent.getNewValue());
        });

        trucksColumnModel.setCellValueFactory(new PropertyValueFactory<TrucksTable, String>("model"));
        trucksColumnModel.setCellFactory(TextFieldTableCell.forTableColumn());
        trucksColumnModel.setOnEditCommit(trucksTableStringCellEditEvent -> {
            updateTruckOnCellCommit(trucksTableStringCellEditEvent.getRowValue().getId(), trucksTableStringCellEditEvent.getNewValue(), TruckEditType.MODEL);
            trucksTableStringCellEditEvent.getTableView().getItems().get(trucksTableStringCellEditEvent.getTablePosition().getRow()).setModel(trucksTableStringCellEditEvent.getNewValue());
        });

        trucksColumnHp.setCellValueFactory(new PropertyValueFactory<TrucksTable, Integer>("hp"));
        setIntegerCellFactory(trucksColumnHp);
        trucksColumnHp.setOnEditCommit(trucksTableIntegerCellEditEvent -> {
            updateTruckOnCellCommit(trucksTableIntegerCellEditEvent.getRowValue().getId(), trucksTableIntegerCellEditEvent.getNewValue(), TruckEditType.HP);
            trucksTableIntegerCellEditEvent.getTableView().getItems().get(trucksTableIntegerCellEditEvent.getTablePosition().getRow()).setHp(trucksTableIntegerCellEditEvent.getNewValue());
        });

        trucksColumnEngine.setCellValueFactory(new PropertyValueFactory<TrucksTable, Double>("engine"));
        trucksColumnEngine.setCellFactory(TextFieldTableCell.<TrucksTable, Double>forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                return String.valueOf(aDouble);
            }

            @Override
            public Double fromString(String s) {
                return Double.parseDouble(s);
            }
        }));
        trucksColumnEngine.setOnEditCommit(trucksTableDoubleCellEditEvent -> {
            updateTruckOnCellCommit(trucksTableDoubleCellEditEvent.getRowValue().getId(), trucksTableDoubleCellEditEvent.getNewValue(), TruckEditType.ENGINE);
            trucksTableDoubleCellEditEvent.getTableView().getItems().get(trucksTableDoubleCellEditEvent.getTablePosition().getRow()).setEngine(trucksTableDoubleCellEditEvent.getNewValue());
        });

        trucksColumnReleased.setCellValueFactory(new PropertyValueFactory<TrucksTable, Integer>("releaseYear"));
        setIntegerCellFactory(trucksColumnReleased);
        trucksColumnReleased.setOnEditCommit(trucksTableIntegerCellEditEvent -> {
            updateTruckOnCellCommit(trucksTableIntegerCellEditEvent.getRowValue().getId(), trucksTableIntegerCellEditEvent.getNewValue(), TruckEditType.RELEASE);
            trucksTableIntegerCellEditEvent.getTableView().getItems().get(trucksTableIntegerCellEditEvent.getTablePosition().getRow()).setReleaseYear(trucksTableIntegerCellEditEvent.getNewValue());
        });

        trucksColumnDelete.setCellValueFactory(new PropertyValueFactory<TrucksTable, String>("deleteBtn"));
        Callback<TableColumn<TrucksTable, String>, TableCell<TrucksTable, String>> cellFactory
                = new Callback<>() {

            @Override
            public TableCell<TrucksTable, String> call(TableColumn<TrucksTable, String> trucksTableStringTableColumn) {
                final TableCell<TrucksTable, String> cell = new TableCell<TrucksTable, String>() {
                    final Button btn = new Button("DELETE");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                TrucksTable selectedRow = getTableView().getItems().get(getIndex());
                                trucksTableView.getItems().remove(selectedRow);
                                truckHib.deleteTruck(selectedRow.getId());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        trucksColumnDelete.setCellFactory(cellFactory);
    }

    private void setIntegerCellFactory(TableColumn<TrucksTable, Integer> trucksColumnReleased) {
        trucksColumnReleased.setCellFactory(TextFieldTableCell.<TrucksTable, Integer>forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer integer) {
                return String.valueOf(integer);
            }

            @Override
            public Integer fromString(String s) {
                return Integer.parseInt(s);
            }
        }));
    }

    public void initializeUsersTable() {
        final Trucker trucker1 = null;
        final Manager manager1 = null;

        usersTableView.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, Integer>("id"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("userType"));

        loginColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("login"));
        loginColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.LOGIN);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.LOGIN);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setLogin(usersTableStringCellEditEvent.getNewValue());
        });

        passwordColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PASSWORD);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PASSWORD);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setPassword(usersTableStringCellEditEvent.getNewValue());
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.NAME);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.NAME);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setName(usersTableStringCellEditEvent.getNewValue());
        });

        surnameColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.SURNAME);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.SURNAME);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setSurname(usersTableStringCellEditEvent.getNewValue());
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.EMAIL);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.EMAIL);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setEmail(usersTableStringCellEditEvent.getNewValue());
        });

        phoneColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("phoneNumber"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(usersTableStringCellEditEvent -> {
            if (usersTableStringCellEditEvent.getRowValue().getUserType().equals("Trucker"))
                updateObjectOnCellCommit(allTruckers, trucker1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PHONE);
            else
                updateObjectOnCellCommit(allManagers, manager1, usersTableStringCellEditEvent.getRowValue().getId(), usersTableStringCellEditEvent.getNewValue(), EditType.PHONE);
            usersTableStringCellEditEvent.getTableView().getItems().get(usersTableStringCellEditEvent.getTablePosition().getRow()).setPhoneNumber(usersTableStringCellEditEvent.getNewValue());
        });

        adminColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, Boolean>("isAdmin"));
        adminColumn.setCellFactory(ac -> new TableCell<UsersTable, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "TRUE" : "FALSE");
            }
        });
//        adminColumn.setOnEditCommit(usersTableBooleanCellEditEvent -> {
//            if (usersTableBooleanCellEditEvent.getRowValue().getUserType().equals("Manager"))
//                updateObjectOnCellCommit(allManagers, manager1, usersTableBooleanCellEditEvent.getRowValue().getId(), usersTableBooleanCellEditEvent.getNewValue(), EditType.ADMIN);
//        });
        deleteColumn.setCellValueFactory(new PropertyValueFactory<UsersTable, String>("deleteBtn"));
        Callback<TableColumn<UsersTable, String>, TableCell<UsersTable, String>> cellFactory
                = new Callback<>() {

            @Override
            public TableCell<UsersTable, String> call(TableColumn<UsersTable, String> usersTableStringTableColumn) {
                final TableCell<UsersTable, String> cell = new TableCell<UsersTable, String>() {
                    final Button btn = new Button("DELETE");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                UsersTable selectedRow = getTableView().getItems().get(getIndex());
                                if (manager.isAdmin()) deleteUser(selectedRow);
                                else AlertDialog.permissionsDialog(new Alert(Alert.AlertType.ERROR));
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        deleteColumn.setCellFactory(cellFactory);
    }

    private void onTripSelected(ActionEvent actionEvent) {
        String value = (String) truckersTripsChoiceBox.getSelectionModel().getSelectedItem();
        Cargo cargo = null;
        if (value != null) {
            String[] parts = value.split(", ");
            cargo = this.assignedCargos.stream().filter(c -> c.getClient().equals(parts[0]) && c.getAssignedAt().toString().equals(parts[1])).findAny().orElse(null);

            assert cargo != null;
            clientText.setText(cargo.getClient());
            assignedAtText.setText(cargo.getAssignedAt().toString());
            deliveryUntilText.setText(cargo.getDeliverUntil().toString());
            pickupAddressText.setText(cargo.getStartDestination());
            deliveryAddressText.setText(cargo.getFinalDestination());
            cargoDetailsText.setText(cargo.getCargo());

            long daysBetween = DAYS.between(LocalDate.now(), cargo.getDeliverUntil());

            if (daysBetween < 0 && !cargo.isFinished()) tripStatusText.setText("Deadline is overdue, cargo is not delivered");
            else if (cargo.isFinished()) {
                tripStatusText.setText("Delivered");
                tripStatusText.setFill(Color.GREEN);
            }
            else {
                tripStatusText.setText(daysBetween + " days remaining until cargo delivery deadline");
                tripStatusText.setFill(Color.RED);
            }
        }
    }

    private void onTruckSelected(Event event) {
        List<Truck> trucks = truckHib.getAllTrucks();
        String value = trucksChoiceBox.getSelectionModel().getSelectedItem();
        if (value != null) {
            String[] parts = value.split(" ");
            this.truck = trucks.stream().filter(t -> t.getBrand().equals(parts[0]) && t.getModel().equals(parts[1])).findAny().orElse(null);
            assert truck != null;
            brandText.setText(truck.getBrand());
            modelText.setText(truck.getModel());
            hpText.setText(String.valueOf(truck.getHp()));
            engineText.setText(String.valueOf(truck.getEngine()));
            releaseText.setText(String.valueOf(truck.getReleaseYear()));
        }
    }

    public void deleteUser(UsersTable usersTable) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        boolean result = AlertDialog.deletionConfirmation(alert.getAlertType(), "", "Are you sure you want to delete this user?");

        if (result) {
            usersTableView.getItems().remove(usersTable);
            this.userHib.deleteUser(usersTable.getId());
        }
    }

    public void selectListViewItem(ListView<String> listView, List<Cargo> cargo, ListView<String> listViewSecondary) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    openCargoDetailWindow(cargo.get(listView.getSelectionModel().getSelectedIndex()), trucker, manager, listView, listViewSecondary);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void selectForumListView(ListView<Forum> listView) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Forum>() {
            @Override
            public void changed(ObservableValue<? extends Forum> observableValue, Forum forum, Forum t1) {
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    selectedForumThread = forumHib.getForumById(listView.getSelectionModel().getSelectedItem().getId());
                    selectedForumThread.setComments(commentHib.getAllCommentsOfForum(selectedForumThread));
                    populateForumTreeView(selectedForumThread);
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


    public void onCreateUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("createuser-modal.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        CreateUserModal createUserModal = fxmlLoader.getController();
        createUserModal.initData(this.userHib);
        createUserModal.setUserConsumerCallback(user -> {
            System.out.println(user.getLogin());
            if (user.getDtype().equals("Trucker")) {
                this.allTruckers = this.userHib.getAllTruckers();
                usersTableView.setItems(getAllUsers());
            } else {
                this.allManagers = this.userHib.getAllManagers();
                usersTableView.setItems(getAllUsers());
            }
        });

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onSaveTruck(ActionEvent event) {
        if (this.truck != null) {
            this.trucker.setTruck(this.truck);
            userHib.updateUser(this.trucker);
        }
    }

    public void onAddTruck(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-truck-modal.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        AddTruckModal addTruckModal = fxmlLoader.getController();
        addTruckModal.initData(this.truckHib);
        addTruckModal.setTruckConsumerCallback(truck -> {
            this.allTrucks = this.truckHib.getAllTrucks();
            trucksTableView.setItems(getAllTrucks());
        });

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onAddCargo(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-cargo-modal.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        AddCargoModal addCargoModal = fxmlLoader.getController();
        addCargoModal.initData(this.cargoHib);
        addCargoModal.setCargoConsumerCallback(cargo -> {
            this.allCargos = this.cargoHib.getAllCargos();
            this.unassignedCargos = this.cargoHib.getUnassignedCargos();
            unassignedCargosListView.getItems().clear();
            this.unassignedCargos.forEach(c -> {
                unassignedCargosListView.getItems().add(c.getClient());
            });
            cargoTableView.setItems(getAllCargos());
            selectListViewItem(unassignedCargosListView, this.unassignedCargos, cargosListView);
        });

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onCreateTopic(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-forum-modal.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        CreateForumModal createForumModal = fxmlLoader.getController();
        createForumModal.initData(this.forumHib, this.trucker != null ? this.trucker : this.manager);
        createForumModal.setForumConsumerCallback(forum -> {
            this.allForumThreads = this.forumHib.getAllForumsThreads();

            forumListView.getItems().setAll(this.allForumThreads);
        });

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onEditTopic(ActionEvent actionEvent) throws IOException {
        Forum forum = forumListView.getSelectionModel().getSelectedItem();

        boolean isForumCreator = this.trucker != null && this.trucker.getId() == forum.getUser().getId();
        boolean isAdmin = this.manager != null && this.manager.isAdmin();

        if (forum != null && isForumCreator || isAdmin) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forum-topic-edit.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(fxmlLoader.load());

            ForumTopicEdit forumTopicEdit = fxmlLoader.getController();
            assert forum != null;
            forumTopicEdit.initData(this.forumHib, forum);
            forumTopicEdit.setForumConsumerCallback(f -> {
                this.allForumThreads = this.forumHib.getAllForumsThreads();
                forumListView.getItems().setAll(this.allForumThreads);
                populateForumTreeView(f);
            });

            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    public void onDeleteThread(ActionEvent actionEvent) {
        Forum forum = forumListView.getSelectionModel().getSelectedItem();

        boolean isForumCreator = this.trucker != null && this.trucker.getId() == forum.getUser().getId();
        boolean isAdmin = this.manager != null && this.manager.isAdmin();

        if (isForumCreator || isAdmin) {
            this.commentHib.deleteCommentsByForum(forum);
            //this.forumHib.deleteForum(forum.getId());
            //this.allForumThreads = this.forumHib.getAllForumsThreads();
            //forumListView.getItems().setAll(this.allForumThreads);
        }
    }

    public void onReply(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forumthread-reply-modal.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(fxmlLoader.load());

        ForumthreadReplyModal forumthreadReplyModal = fxmlLoader.getController();

        var selectedItem = forumTreeView.getSelectionModel().getSelectedItem() == null ? forumTreeView.getTreeItem(0) : forumTreeView.getSelectionModel().getSelectedItem();

        String[] parts = selectedItem.getValue().split(" commented:");
        Comment parentComment = this.commentHib.getCommentByValue(parts[1].trim(), this.selectedForumThread);

        forumthreadReplyModal.initData(this.selectedForumThread, this.commentHib, this.manager != null ? this.manager : this.trucker, parentComment);
        forumthreadReplyModal.setCommentConsumerCallback(c -> {
            Forum forum = this.forumHib.getForumById(this.selectedForumThread.getId());
            forum.setComments(this.commentHib.getAllCommentsOfForum(forum));
            populateForumTreeView(forum);
        });

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void onEditReply(ActionEvent actionEvent) {
    }

    public void onDeleteComment(ActionEvent actionEvent) {
    }
}