package com.trucker_system.truckersystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private String model;
    private int hp;
    private double engine;
    private int releaseYear;
    @OneToMany (mappedBy = "truck", cascade = CascadeType.ALL)
    private List<Trucker> trucker;

    public Truck(int id, String brand, String model, int hp, double engine, int releaseYear) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.hp = hp;
        this.engine = engine;
        this.releaseYear = releaseYear;
    }

    public Truck(String brand, String model, int hp, double engine, int releaseYear) {
        this.brand = brand;
        this.model = model;
        this.hp = hp;
        this.engine = engine;
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", hp=" + hp +
                ", engine=" + engine +
                ", releaseYear=" + releaseYear +
                ", trucker=" + trucker +
                '}';
    }
}
