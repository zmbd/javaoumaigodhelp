package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(insertable = false, updatable = false)
    private String dtype;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;

    public User(String dtype, String login, String password, String email, String name, String surname, String phoneNumber) {
        this.dtype = dtype;
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.surname = surname.substring(0, 1).toUpperCase() + name.substring(1);
        this.phoneNumber = phoneNumber;
    }

    public User(String login, String password, String email, String name, String surname, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.surname = surname.substring(0, 1).toUpperCase() + name.substring(1);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", dtype='" + dtype +
                ", login='" + login +
                ", password='" + password +
                ", email='" + email +
                ", name='" + name +
                ", surname='" + surname +
                '}';
    }
}
