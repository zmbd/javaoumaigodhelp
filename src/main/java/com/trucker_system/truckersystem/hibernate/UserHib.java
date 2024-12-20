package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Manager;
import com.trucker_system.truckersystem.model.Trucker;
import com.trucker_system.truckersystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

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
        entityManager = emf.createEntityManager();
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

    public void deleteUser(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User persistentInstance = entityManager.find(User.class, id);
            //Cargo persistentInstance = entityManager.merge(cargo);
            entityManager.remove(persistentInstance);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Manager> getAllManagers() {
        entityManager = emf.createEntityManager();
        List<Manager> managerList = null;
        try {
            entityManager.getTransaction().begin();

            String select = "FROM User u WHERE u.dtype=:manager";
            Query query = entityManager.createQuery(select);
            query.setParameter("manager", "Manager");
            managerList = query.getResultList();

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return managerList;
    }

    public List<User> getAllUsers() {
        entityManager = emf.createEntityManager();
        List<User> users = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u";
            Query query = entityManager.createQuery(select);
            users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return users;
    }

    public List<Trucker> getAllTruckers() {
        entityManager = emf.createEntityManager();
        List<Trucker> truckerList = null;
        try {
//            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
//            query.select(query.from(Trucker.class));
//            Query q = entityManager.createQuery(query);
//            return q.getResultList();
            entityManager.getTransaction().begin();

            String select = "FROM User u WHERE u.dtype=:trucker";
            Query query = entityManager.createQuery(select);
            query.setParameter("trucker", "Trucker");
            truckerList = query.getResultList();

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return truckerList;
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

    public boolean findByLogin(String login) {
        boolean value = false;
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE u.login=:login";
            Query query = entityManager.createQuery(select);
            query.setParameter("login", login);

            if (query.getSingleResult() != null) value = true;

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return value;
    }

    public String getDtypeById(int id) {
        String value = null;
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "SELECT dtype FROM User u WHERE id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            value = (String) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return value;
    }

    public <T> T getUserByIdType(int id, String dtype) {
        T user = null;

        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE id=:id AND dtype=:dtype";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            query.setParameter("dtype", dtype);

            user = (T) query.getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return  user;
    }

    public User getUserById(int id) {
        User user = null;
        entityManager = emf.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            String select = "FROM User u WHERE u.id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);

            user = (User) query.getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return user;
    }
}
