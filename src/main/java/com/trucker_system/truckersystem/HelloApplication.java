package com.trucker_system.truckersystem;

import com.trucker_system.truckersystem.fxControllers.LoginPage;
import com.trucker_system.truckersystem.hibernate.UserHib;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;


/* MATAS ČYPAS PRIF 20-1 */

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TruckerSystem!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();



    }
}