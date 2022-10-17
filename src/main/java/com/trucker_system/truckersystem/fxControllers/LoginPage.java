package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.HelloApplication;
import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pwField;
    @FXML
    public Label loginLabel;

    private Trucker trucker = null;
    private Manager manager = null;

    public void loginAction(ActionEvent event) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
        UserHib userHib = new UserHib(entityManagerFactory);

        User user = userHib.authenticateLogin(loginField.getText(), pwField.getText());

        if (user != null) {
            if (user.getDtype().equals("Trucker")) {
                List<Cargo> cargoList = new ArrayList<>();
                CargoHib cargoHib = new CargoHib(entityManagerFactory);
                this.trucker = userHib.getTrucker(user);
                cargoList = cargoHib.getCargoListById(this.trucker);
                this.trucker.setCargosList(cargoList);
            }
            else {
                this.manager = userHib.getManager(user);
            }
            openMainWindow();
        } else loginLabel.setText("Details entered are incorrect, or the account is not registered in the system.");


    }

    private void openMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setScene(scene);

        MainPage mainPage = fxmlLoader.getController();

        if (this.trucker != null) {
            mainPage.initData(this.trucker, false);
        } else mainPage.initData(this.manager, true);

        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLabel.setText("");
    }
}
