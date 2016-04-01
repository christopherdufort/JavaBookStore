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
 * @author Christopher Dufort
 * @version 0.2.4 - Last modified 2/15/2016
 */
@Named
@SessionScoped
public class AdJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    /**
     * Default Constructor
     *
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     */
    public AdJpaController() {
        super();
    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param ad
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void create(Ad ad) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(ad);
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

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param ad
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void edit(Ad ad) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            ad = em.merge(ad);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ad.getAdId();
                if (findAdById(id) == null) {
                    throw new NonexistentEntityException("The ad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param id
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Ad ad;
            try {
                ad = em.getReference(Ad.class, id);
                ad.getAdId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ad with id " + id + " no longer exists.", enfe);
            }
            em.remove(ad);
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

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016 Return all the records in the
     * table
     * @return
     */
    public List<Ad> findAllAds() {
        return findAdEntities(true, -1, -1);
    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Ad> findAdEntities(int maxResults, int firstResult) {
        return findAdEntities(false, maxResults, firstResult);
    }

    /**
     * Either find all or find a group of records
     *
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param all True means find all, false means find subset
     * @param maxResults Number of records to find
     * @param firstResult Record number to start returning records
     * @return
     */
    private List<Ad> findAdEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Ad as o order by o.adId ASC");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param adId
     * @return
     */
    public Ad findAdById(Integer adId) {
        //em.find will find in a class by primary key
        return em.find(Ad.class, adId);
    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @param adFilename
     * @return
     */
    public Ad findAdByFilename(String adFilename) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Ad.findByAdFilename");
        //binding for names parameters
        query.setParameter("adFilename", adFilename);

        //execute query returning single result
        Ad result = (Ad) query.getSingleResult();
        return result;
    }

    /**
     * @author Christopher Dufort
     * @version 0.2.4 - Last modified 2/15/2016
     * @return
     */
    public int getAdCount() {
        //Example of JPQL query
        Query q = em.createQuery("select count(o) from Ad as o");
        return ((Long) q.getSingleResult()).intValue();
    }

    public Ad findAdByActiveType(boolean type) {
        Query query = em.createQuery("SELECT a FROM Ad a WHERE a.adType = :type");
        query.setParameter("type", type);

        //execute query returning single result
        Ad result = (Ad) query.getSingleResult();
        return result;
    }

}
