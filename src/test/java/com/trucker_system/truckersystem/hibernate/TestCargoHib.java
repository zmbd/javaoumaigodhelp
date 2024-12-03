package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Cargo;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class TestCargoHib extends CargoHib {
    private List<Cargo> updatedCargos = new ArrayList<>();
    private List<Cargo> deletedCargos = new ArrayList<>();
    private List<Integer> deletedCargosIds = new ArrayList<>();

    public TestCargoHib() {
        super(null); // Pass null since we won't use EntityManagerFactory in tests
    }

    public TestCargoHib(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public void createCargo(Cargo cargo) {
        updatedCargos.add(cargo);
    }

    @Override
    public void updateCargo(Cargo cargo) {
        updatedCargos.add(cargo);
    }

    @Override
    public void deleteCargo(Cargo cargo) {
        deletedCargos.add(cargo);
        deletedCargosIds.add(cargo.getId());
    }

    @Override
    public void deleteCargoById(int id) {
        deletedCargosIds.add(id);
    }

    @Override
    public Cargo getCargoById(int id) {
        return new Cargo();
    }

    public List<Cargo> getUpdatedCargos() {
        return updatedCargos;
    }

    public List<Cargo> getDeletedCargos() {
        return deletedCargos;
    }

    public List<Integer> getDeletedCargosIds() {
        return deletedCargosIds;
    }

}