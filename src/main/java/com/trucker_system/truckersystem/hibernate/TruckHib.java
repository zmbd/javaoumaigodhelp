package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Truck;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class TruckHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public TruckHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }


    public void createTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(truck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteTruck(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Truck persistentInstance = entityManager.find(Truck.class, id);
            //Cargo persistentInstance = entityManager.merge(cargo);
            entityManager.remove(persistentInstance);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Truck getTruckById(int id) {
        entityManager = emf.createEntityManager();
        Truck truck = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Truck t WHERE t.id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            truck = (Truck) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (entityManager != null) entityManager.close();
        }

        return truck;
    }

    public List<Truck> getAllTrucks() {
        entityManager = emf.createEntityManager();
        List<Truck> trucks = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Truck";
            Query query = entityManager.createQuery(select);
            trucks = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return trucks;
    }

    public void updateTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(truck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
