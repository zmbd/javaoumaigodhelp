package com.trucker_system.truckersystem;

import com.trucker_system.truckersystem.hibernate.CargoHib;
import com.trucker_system.truckersystem.hibernate.UserHib;
import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
        UserHib userHib = new UserHib(entityManagerFactory);
        CargoHib cargoHib = new CargoHib(entityManagerFactory);

        //List<Trucker> truckerList = userHib.getAllTruckers();

//        Manager manager = new Manager("login", "password", "email", "im da manager", "surname", "phonenumber", true);
        //Trucker trucker = new Trucker("trucker", "trucker123", "truckeris", "im da trucker", "truckeris", 19);
       // Trucker trucker1 = new Trucker("trucker2", "trucker123", "truckeris", "im da second trucker", "truckeris", 7);
        Cargo cargo = new Cargo("UAB Dazai", "Riga, ssss st. 2929", "Spain, Madrid, 2020299", LocalDate.of(2022, Month.OCTOBER, 7), LocalDate.of(2022, Month.OCTOBER, 29), false);
        cargoHib.createCargo(cargo);
//
//
       // cargoHib.updateCargoAssignedTrucker(cargoHib.getCargoById(1), userHib.getTrucker(trucker));


//
//        userHib.createUser(trucker);
//        userHib.createUser(trucker1);
//        userHib.createUser(manager);
//        cargoHib.createCargo(cargo);

//        Cargo cargo = new Cargo("UAB Mesa", "Vilnius", "Paris, Bordeaux, cargo destination address", trucker);
//        CargoHib cargoHib = new CargoHib(entityManagerFactory);
    }
}
