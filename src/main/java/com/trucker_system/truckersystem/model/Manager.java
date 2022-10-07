package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Manager extends User implements Serializable {
    private String phoneNumber;
    private boolean isAdmin;

    public Manager(String login, String password, String email, String name, String surname, String phoneNumber, boolean isAdmin) {
        super(login, password, email, name, surname);
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }


}
