package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String client;
    private String startDestination;
    private String finalDestination;
    private LocalDate assignedAt;
    private LocalDate deliverUntil;
    private String cargo;
    private boolean finished;
    @ManyToOne
    private Trucker trucker;

    public Cargo(String client, String startDestination, String finalDestination, LocalDate assignedAt, LocalDate deliverUntil, String cargo, boolean finished) {
        this.client = client;
        this.startDestination = startDestination;
        this.finalDestination = finalDestination;
        this.assignedAt = assignedAt;
        this.deliverUntil = deliverUntil;
        this.cargo = cargo;
        this.finished = finished;
    }

    public Cargo(String client, String startDestination, String finalDestination, LocalDate assignedAt, LocalDate deliverUntil, boolean finished, Trucker trucker) {
        this.client = client.substring(0, 1).toUpperCase() + client.substring(1);
        this.startDestination = startDestination;
        this.finalDestination = finalDestination;
        this.assignedAt = assignedAt;
        this.deliverUntil = deliverUntil;
        this.finished = finished;
        this.trucker = trucker;
    }

    public Cargo(String client, String startDestination, String finalDestination, LocalDate assignedAt, LocalDate deliverUntil, String cargo, boolean finished, Trucker trucker) {
        this.client = client;
        this.startDestination = startDestination;
        this.finalDestination = finalDestination;
        this.assignedAt = assignedAt;
        this.deliverUntil = deliverUntil;
        this.cargo = cargo;
        this.finished = finished;
        this.trucker = trucker;
    }

    public Cargo(String client, String startDestination, String finalDestination, LocalDate assignedAt, LocalDate deliverUntil, boolean finished) {
        this.client = client;
        this.startDestination = startDestination;
        this.finalDestination = finalDestination;
        this.assignedAt = assignedAt;
        this.deliverUntil = deliverUntil;
        this.finished = finished;
    }
}
