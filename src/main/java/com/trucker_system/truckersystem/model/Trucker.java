package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trucker extends User implements Serializable {
    private int finishedTrips;
    @OneToMany(mappedBy = "trucker", cascade = CascadeType.ALL)
    private List<Cargo> cargosList;

    public Trucker(String login, String password, String email, String name, String surname, String phoneNumber, int finishedTrips) {
        super(login, password, email, name, surname, phoneNumber);
        this.finishedTrips = finishedTrips;
    }

    public Trucker(int id, String dtype, String login, String password, String email, String name, String surname, String phoneNumber, int finishedTrips, List<Cargo> cargosList) {
        super(id, dtype, login, password, email, name, surname, phoneNumber);
        this.finishedTrips = finishedTrips;
        this.cargosList = cargosList;
    }

    @Override
    public String toString() {
        return "Trucker{" +
                "finishedTrips=" + finishedTrips +
                ", cargosList=" + cargosList +
                '}';
    }

}
