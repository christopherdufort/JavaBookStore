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
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1040570
 */
@Named
@SessionScoped
public class NewsFeedJpaController implements Serializable {
    
    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public NewsFeedJpaController(){
        super();
    }

    public void create(NewsFeed newsFeed) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(newsFeed);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(NewsFeed newsFeed) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
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
                if (findNewsFeedById(id) == null) {
                    throw new NonexistentEntityException("The newsFeed with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
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
        }
    }

    public List<NewsFeed> findAllNewsFeeds() {
        return findNewsFeedEntities(true, -1, -1);
    }
    
    /**
     * zero based counting in db for first result, max results includes first found?
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<NewsFeed> findNewsFeedPagination(int maxResults, int firstResult) {
        return findNewsFeedEntities(false, maxResults, firstResult);
    }

    private List<NewsFeed> findNewsFeedEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from NewsFeed as o order by o.newsFeedId ASC");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public NewsFeed findNewsFeedById(Integer id) {
        return em.find(NewsFeed.class, id);

    }
    
    /**
     * @author Christopher Dufort  
     * @version 0.2.6 - Last modified 2/17/2016
     * @param newsFeedLink
     * @return 
     */
    public NewsFeed findNewsFeedByLink(String newsFeedLink){
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("NewsFeed.findByNewsFeedLink"); 
        
        //binding for names parameters
        query.setParameter("newsFeedLink",newsFeedLink);
        
        //execute query returning single result
        NewsFeed result = (NewsFeed)query.getSingleResult(); 
        return result;
    }

    public int getNewsFeedCount() {
        Query q = em.createQuery("select count(o) from NewsFeed as o");
        return ((Long) q.getSingleResult()).intValue();
    }
}
