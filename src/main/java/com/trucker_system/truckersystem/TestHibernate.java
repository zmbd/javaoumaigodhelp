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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckerSystem");
        UserHib userHib = new UserHib(entityManagerFactory);
        CargoHib cargoHib = new CargoHib(entityManagerFactory);
        TruckHib truckHib = new TruckHib(entityManagerFactory);
        ForumHib forumHib = new ForumHib(entityManagerFactory);
        CommentHib commentHib = new CommentHib(entityManagerFactory);

        Manager manager = userHib.getManager(new Manager("login", "password", "email", "Im da manager", "Sm da manager", "phonenumber", true));


        forumHib.createForum(new Forum("this is forum topic", manager));
        Forum forum = forumHib.getForumById(1);
        Comment comment = new Comment("this is first comment", forum, null, null, manager);



//        Forum forum = forumHib.getForumById(1);
//        Comment comment = commentHib.getCommentById(1);
//
//        List<Comment> comments = new ArrayList<>();
//        comments.add(new Comment("comment with parent", forum, null, comment, manager));
//
//        commentHib.createComment(comments.get(0));




        //List<Trucker> truckerList = userHib.getAllTruckers();

//        Manager manager = new Manager("login", "password", "email", "im da manager", "surname", "phonenumber", true);
//        userHib.createUser(manager);
//        Trucker trucker = new Trucker("trucker", "trucker123", "truckeris", "im da trucker", "truckeris", "1234884844");
//        userHib.createUser(trucker);
//        Trucker trucker1 = new Trucker("trucker2", "trucker123", "truckeris", "im da second trucker", "truckeris", "911");
//        userHib.createUser(trucker1);


        //        cargoHib.createCargo(cargo);
//        Truck truck = new Truck("Mercedes", "Audi", 500, 19.2, 2019);
//        truckHib.createTruck(truck);

//        System.out.println(trucker);


//        Cargo cargo = new Cargo("UAB Dazai", "Riga, ssss st. 2929", "Spain, Madrid, 2020299", LocalDate.of(2022, Month.OCTOBER, 7), LocalDate.of(2022, Month.OCTOBER, 29), false);
//        cargo.setTrucker(truckers.get(0));
//        cargoHib.updateCargo(cargo);
//


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
