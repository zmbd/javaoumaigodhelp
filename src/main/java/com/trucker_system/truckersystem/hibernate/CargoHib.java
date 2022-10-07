package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Cargo;
import com.trucker_system.truckersystem.model.Trucker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CargoHib(EntityManagerFactory entityManagerFactory) { this.emf = entityManagerFactory; }

    public void createCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateCargoAssignedTrucker(Cargo cargo, Trucker trucker) {
        entityManager = emf.createEntityManager();
        cargo.setTrucker(trucker);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Cargo> getUnassignedCargos() {
        entityManager = emf.createEntityManager();
        List<Cargo> unassignedCargos = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Cargo c WHERE c.trucker IS null";
            Query query = entityManager.createQuery(select);

            unassignedCargos = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return unassignedCargos;
    }

    public Cargo getCargoById(int id) {
        entityManager = emf.createEntityManager();
        Cargo cargo = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Cargo c WHERE c.id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            cargo = (Cargo) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return cargo;
    }

    public List<Cargo> getCargoListById(Trucker trucker) {
        entityManager = emf.createEntityManager();
        List<Cargo> cargoList = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Cargo c WHERE c.trucker=:trucker";
            Query query = entityManager.createQuery(select);
            query.setParameter("trucker", trucker);
            cargoList = query.getResultList();
            entityManager.getTransaction().commit();
//            entityManager.getTransaction().begin();
//
//            cargoList.add(entityManager.find(Cargo.class, id));
//            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such cargo by given trucker id");
        }
        return cargoList;
    }
}
