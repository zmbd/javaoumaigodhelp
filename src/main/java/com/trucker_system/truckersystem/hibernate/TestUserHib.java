package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Trucker;

import java.util.ArrayList;
import java.util.List;

public class TestUserHib extends UserHib {
    private List<Trucker> truckers = new ArrayList<>();

    public TestUserHib() {
        super(null);
    }

    public void setTruckers(List<Trucker> truckers) {
        this.truckers = truckers;
    }

    @Override
    public List<Trucker> getAllTruckers() {
        return truckers;
    }
}
