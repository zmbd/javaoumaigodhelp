package com.trucker_system.truckersystem.fxControllers;

import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.List;

public class CreateUserModal {
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

    private UserHib userHib = null;

    public void userTypeAction() {

    }

    public void createNewUser() {
        if (loginField.getText().length() > 5 && passwordField.getText().length() > 10 && !nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && emailField.getText().length() > 6 && phoneField.getText().length() > 8) {
            List<User> users = this.userHib.getAllUsersByLogin();
            User loginCheck;

            loginCheck = users.stream().filter(u -> u.getLogin() == loginField.getText()).findAny().orElse(null);

            if (loginCheck == null) {
                if (radioManager.isSelected()) {
                    Manager manager = new Manager(loginField.getText(), passwordField.getText(), emailField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText(), false);
                    this.userHib.createUser(manager);
                } else {
                    Trucker trucker = new Trucker(loginField.getText(), passwordField.getText(), emailField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText());
                    this.userHib.createUser(trucker);
                }

                Stage stage = (Stage) createBtn.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void initData(UserHib userHib) {
        this.userHib = userHib;
    }
}
