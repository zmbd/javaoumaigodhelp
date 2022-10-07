package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UserHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }


    public void createUser(User user) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void getUserData(String login, String password) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Manager getManager(User user) {
        Manager manager = null;
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE u.login=:login";
            Query query = entityManager.createQuery(select);
            query.setParameter("login", user.getLogin());

            manager = (Manager) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return manager;
    }
    public Trucker getTrucker(User user) {
        Trucker trucker = null;
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE u.login=:login";
            Query query = entityManager.createQuery(select);
            query.setParameter("login", user.getLogin());

            trucker = (Trucker) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return trucker;
    }

    public User authenticateLogin(String login, String password) {
        User user = null;
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE u.login=:login and u.password=:password";

            Query query = entityManager.createQuery(select);
            query.setParameter("login", login);
            query.setParameter("password", password);

            user = (User) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        if (user != null) return user;

        return null;
    }
}
