package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.HelloApplication;
import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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


    private Trucker trucker = null;
    private Manager manager = null;
    private boolean isManager;
    private List<Cargo> unassignedCargos = null;
    private List<Cargo> cargoList = null;

    public <T> void initData(T value, boolean isManager) {
        this.isManager = isManager;
        if (isManager) {
            this.manager = (Manager) value;
            welcomeName.setText(this.manager.getName());
        }
        else {
            this.trucker = (Trucker) value;
            welcomeName.setText(this.trucker.getName());
            cargoList = this.trucker.getCargosList();

            //refactor this s to generic method as used two times
            cargoList.forEach(c -> {
                cargosListView.getItems().add(c.getClient());
            });

            selectListViewItem(cargosListView);

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
            CargoHib cargoHib = new CargoHib(entityManagerFactory);

            unassignedCargos = cargoHib.getUnassignedCargos();

            unassignedCargos.forEach(uc -> {
                unassignedCargosListView.getItems().add(uc.getClient());
            });

            selectListViewItem(unassignedCargosListView);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void selectListViewItem(ListView<String> listView) {
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                //ERROR ERROR SELECTING SAME ITEM WILL NOT TRIGGER THE EVENT ERROR ERROR FIX LATER MMMMMMMM
                try {
                    openCargoDetailWindow(cargoList.get(listView.getSelectionModel().getSelectedIndex()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void openCargoDetailWindow(Cargo cargoItem) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cargodetails-page.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }



}
