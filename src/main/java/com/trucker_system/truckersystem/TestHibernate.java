package com.trucker_system.truckersystem;

import com.trucker_system.truckersystem.hibernate.*;
import com.trucker_system.truckersystem.model.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("truckerdb");
        UserHib userHib = new UserHib(entityManagerFactory);

//        userHib.createUser(new Manager("mng", "password", "email", "name", "surname", "phone", true));
//        userHib.getDtypeById(1);
        System.out.println(userHib.getDtypeById(1));
    }
}
