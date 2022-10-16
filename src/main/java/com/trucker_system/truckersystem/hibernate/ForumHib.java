package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class ForumHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public ForumHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }


    public void createForum(Forum forum) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(forum);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteForum(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Forum persistentInstance = entityManager.find(Forum.class, id);
            //Cargo persistentInstance = entityManager.merge(cargo);
            entityManager.remove(persistentInstance);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Forum getForumById(int id) {
        entityManager = emf.createEntityManager();
        Forum forum = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Forum f WHERE f.id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            forum = (Forum) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (entityManager != null) entityManager.close();
        }

        return forum;
    }

    public List<Forum> getAllForumsThreads() {
        entityManager = emf.createEntityManager();
        List<Forum> forumList = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Forum";
            Query query = entityManager.createQuery(select);
            forumList = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return forumList;
    }
}
