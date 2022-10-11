module com.trucker_system.truckersystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.java;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;


    opens com.trucker_system.truckersystem.fxControllers to javafx.base, javafx.fxml;

    exports com.trucker_system.truckersystem;

    exports com.trucker_system.truckersystem.model to org.hibernate.orm.core;
    opens com.trucker_system.truckersystem.model to org.hibernate.orm.core;
}