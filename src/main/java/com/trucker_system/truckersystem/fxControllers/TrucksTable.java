package com.trucker_system.truckersystem.fxControllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TrucksTable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty brand;
    private SimpleStringProperty model;
    private SimpleIntegerProperty hp;
    private SimpleDoubleProperty engine;
    private SimpleIntegerProperty releaseYear;
    private SimpleStringProperty deleteBtn;

    public TrucksTable() {
    }

    public TrucksTable(int id, String brand, String model, int hp, double engine, int releaseYear) {
        this.id = new SimpleIntegerProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.hp = new SimpleIntegerProperty(hp);
        this.engine = new SimpleDoubleProperty(engine);
        this.releaseYear = new SimpleIntegerProperty(releaseYear);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public int getHp() {
        return hp.get();
    }

    public SimpleIntegerProperty hpProperty() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp.set(hp);
    }

    public double getEngine() {
        return engine.get();
    }

    public SimpleDoubleProperty engineProperty() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine.set(engine);
    }

    public int getReleaseYear() {
        return releaseYear.get();
    }

    public SimpleIntegerProperty releaseYearProperty() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear.set(releaseYear);
    }

    public String getDeleteBtn() {
        return deleteBtn.get();
    }

    public SimpleStringProperty deleteBtnProperty() {
        return deleteBtn;
    }

    public void setDeleteBtn(String deleteBtn) {
        this.deleteBtn.set(deleteBtn);
    }
}
