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
    private boolean isAdmin;

    public Manager(String dtype, String login, String password, String email, String name, String surname, String phoneNumber, boolean isAdmin) {
        super(dtype, login, password, email, name, surname, phoneNumber);
        this.isAdmin = isAdmin;
    }

    public Manager(String login, String password, String email, String name, String surname, String phoneNumber, boolean isAdmin) {
        super(login, password, email, name, surname, phoneNumber);
        this.isAdmin = isAdmin;
    }


}
