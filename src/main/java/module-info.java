module com.trucker_system.truckersystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires mysql.connector.java;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.beans;

    opens com.trucker_system.truckersystem.fxControllers to javafx.base, javafx.fxml;



    exports com.trucker_system.truckersystem;

    exports com.trucker_system.truckersystem.model to org.hibernate.orm.core;
    opens com.trucker_system.truckersystem.model to org.hibernate.orm.core;
}