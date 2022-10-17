package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class CreateUserModal implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField phoneField;
    @FXML
    public RadioButton radioTrucker;
    @FXML
    public ToggleGroup userTypeGroup;
    @FXML
    public Button createBtn;
    @FXML
    public RadioButton radioManager;
    @FXML
    public Label labelValidate;


    private Consumer<User> userConsumer = null;
    private UserHib userHib = null;

    public void setUserConsumerCallback(Consumer<User> userConsumer) { this.userConsumer = userConsumer; }

    public void createNewUser() {
        if (loginField.getText().length() > 5 && passwordField.getText().length() > 10 && !nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && emailField.getText().length() > 6 && phoneField.getText().length() > 8) {
            List<User> users = this.userHib.getAllUsers();
            User loginCheck = null;

            loginCheck = users.stream().filter(u -> u.getLogin().equals(loginField.getText())).findAny().orElse(null);

            if (loginCheck == null) {
                if (radioManager.isSelected()) {
                    Manager manager = new Manager("Manager", loginField.getText(), passwordField.getText(), emailField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText(), false);
                    this.userHib.createUser(manager);
                    userConsumer.accept(manager);
                } else {
                    Trucker trucker = new Trucker("Trucker", loginField.getText(), passwordField.getText(), emailField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText());
                    this.userHib.createUser(trucker);
                    userConsumer.accept(trucker);
                }

                Stage stage = (Stage) createBtn.getScene().getWindow();
                stage.close();
            } else labelValidate.setText("User already exists.");
        } else labelValidate.setText("Some details are invalid.");
    }

    public void initData(UserHib userHib) {
        this.userHib = userHib;
    }

    public void userTypeAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelValidate.setText("");
    }
}
