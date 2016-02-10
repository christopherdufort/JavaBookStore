/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1040570
 */
public class NewsFeedJpaController implements Serializable {

    public NewsFeedJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NewsFeed newsFeed) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NewsFeed newsFeed) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            newsFeed = em.merge(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = newsFeed.getNewsFeedId();
                if (findNewsFeed(id) == null) {
                    throw new NonexistentEntityException("The newsFeed with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NewsFeed newsFeed;
            try {
                newsFeed = em.getReference(NewsFeed.class, id);
                newsFeed.getNewsFeedId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The newsFeed with id " + id + " no longer exists.", enfe);
            }
            em.remove(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NewsFeed> findNewsFeedEntities() {
        return findNewsFeedEntities(true, -1, -1);
    }

    public List<NewsFeed> findNewsFeedEntities(int maxResults, int firstResult) {
        return findNewsFeedEntities(false, maxResults, firstResult);
    }

    private List<NewsFeed> findNewsFeedEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from NewsFeed as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NewsFeed findNewsFeed(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NewsFeed.class, id);
        } finally {
            em.close();
        }
    }

    public int getNewsFeedCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from NewsFeed as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
