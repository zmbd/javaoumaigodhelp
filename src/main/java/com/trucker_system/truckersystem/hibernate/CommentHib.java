package com.trucker_system.truckersystem.hibernate;

import com.trucker_system.truckersystem.model.Comment;
import com.trucker_system.truckersystem.model.Forum;
import com.trucker_system.truckersystem.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class CommentHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }


    public void createComment(Comment comment) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteComment(int id) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comment persistentInstance = entityManager.find(Comment.class, id);
            //Cargo persistentInstance = entityManager.merge(cargo);
            entityManager.remove(persistentInstance);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Comment getCommentById(int id) {
        entityManager = emf.createEntityManager();
        Comment comment = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Comment c WHERE c.id=:id";
            Query query = entityManager.createQuery(select);
            query.setParameter("id", id);
            comment = (Comment) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (entityManager != null) entityManager.close();
        }

        return comment;
    }

    public List<Comment> getAllCommentsOfForum(Forum forum) {
        entityManager = emf.createEntityManager();
        List<Comment> commentList = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Comment c WHERE c.forum=:forum";
            Query query = entityManager.createQuery(select);
            query.setParameter("forum", forum);
            commentList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return commentList;
    }

    public List<Comment> getAllComments() {
        entityManager = emf.createEntityManager();
        List<Comment> commentList = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Comment";
            Query query = entityManager.createQuery(select);
            commentList = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }

        return commentList;
    }

    public Comment getCommentByValue(String value, Forum forum) {
        entityManager = emf.createEntityManager();
        Comment comment = null;
        try {
            entityManager.getTransaction().begin();
            String select = "FROM Comment c WHERE c.commentText=:value AND c.forum=:forum";
            Query query = entityManager.createQuery(select);
            query.setParameter("value", value);
            query.setParameter("forum", forum);

            comment = (Comment) query.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return comment;
    }
}
