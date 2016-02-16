/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Giuseppe Campanelli
 */
public class RegisteredUserJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(RegisteredUser registeredUser) throws RollbackFailureException, Exception {
        if (registeredUser.getReviewList() == null) {
            registeredUser.setReviewList(new ArrayList<Review>());
        }
        try {
            utx.begin();
            Title titleId = registeredUser.getTitleId();
            if (titleId != null) {
                titleId = em.getReference(titleId.getClass(), titleId.getTitleId());
                registeredUser.setTitleId(titleId);
            }
            Province provinceId = registeredUser.getProvinceId();
            if (provinceId != null) {
                provinceId = em.getReference(provinceId.getClass(), provinceId.getProvinceId());
                registeredUser.setProvinceId(provinceId);
            }
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : registeredUser.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            registeredUser.setReviewList(attachedReviewList);
            em.persist(registeredUser);
            if (titleId != null) {
                titleId.getRegisteredUserList().add(registeredUser);
                titleId = em.merge(titleId);
            }
            if (provinceId != null) {
                provinceId.getRegisteredUserList().add(registeredUser);
                provinceId = em.merge(provinceId);
            }
            for (Review reviewListReview : registeredUser.getReviewList()) {
                RegisteredUser oldUserIdOfReviewListReview = reviewListReview.getUserId();
                reviewListReview.setUserId(registeredUser);
                reviewListReview = em.merge(reviewListReview);
                if (oldUserIdOfReviewListReview != null) {
                    oldUserIdOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldUserIdOfReviewListReview = em.merge(oldUserIdOfReviewListReview);
                }
            }
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

    public void edit(RegisteredUser registeredUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            RegisteredUser persistentRegisteredUser = em.find(RegisteredUser.class, registeredUser.getUserId());
            Title titleIdOld = persistentRegisteredUser.getTitleId();
            Title titleIdNew = registeredUser.getTitleId();
            Province provinceIdOld = persistentRegisteredUser.getProvinceId();
            Province provinceIdNew = registeredUser.getProvinceId();
            List<Review> reviewListOld = persistentRegisteredUser.getReviewList();
            List<Review> reviewListNew = registeredUser.getReviewList();
            if (titleIdNew != null) {
                titleIdNew = em.getReference(titleIdNew.getClass(), titleIdNew.getTitleId());
                registeredUser.setTitleId(titleIdNew);
            }
            if (provinceIdNew != null) {
                provinceIdNew = em.getReference(provinceIdNew.getClass(), provinceIdNew.getProvinceId());
                registeredUser.setProvinceId(provinceIdNew);
            }
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewId());
                attachedReviewListNew.add(reviewListNewReviewToAttach); 
            }
            reviewListNew = attachedReviewListNew;
            registeredUser.setReviewList(reviewListNew);
            registeredUser = em.merge(registeredUser);
            if (titleIdOld != null && !titleIdOld.equals(titleIdNew)) {
                titleIdOld.getRegisteredUserList().remove(registeredUser);
                titleIdOld = em.merge(titleIdOld);
            }
            if (titleIdNew != null && !titleIdNew.equals(titleIdOld)) {
                titleIdNew.getRegisteredUserList().add(registeredUser);
                titleIdNew = em.merge(titleIdNew);
            }
            if (provinceIdOld != null && !provinceIdOld.equals(provinceIdNew)) {
                provinceIdOld.getRegisteredUserList().remove(registeredUser);
                provinceIdOld = em.merge(provinceIdOld);
            }
            if (provinceIdNew != null && !provinceIdNew.equals(provinceIdOld)) {
                provinceIdNew.getRegisteredUserList().add(registeredUser);
                provinceIdNew = em.merge(provinceIdNew);
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    reviewListOldReview.setUserId(null);
                    reviewListOldReview = em.merge(reviewListOldReview);
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    RegisteredUser oldUserIdOfReviewListNewReview = reviewListNewReview.getUserId();
                    reviewListNewReview.setUserId(registeredUser);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldUserIdOfReviewListNewReview != null && !oldUserIdOfReviewListNewReview.equals(registeredUser)) {
                        oldUserIdOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldUserIdOfReviewListNewReview = em.merge(oldUserIdOfReviewListNewReview);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registeredUser.getUserId();
                if (findRegisteredUser(id) == null) {
                    throw new NonexistentEntityException("The registeredUser with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            RegisteredUser registeredUser;
            try {
                registeredUser = em.getReference(RegisteredUser.class, id);
                registeredUser.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registeredUser with id " + id + " no longer exists.", enfe);
            }
            Title titleId = registeredUser.getTitleId();
            if (titleId != null) {
                titleId.getRegisteredUserList().remove(registeredUser);
                titleId = em.merge(titleId);
            }
            Province provinceId = registeredUser.getProvinceId();
            if (provinceId != null) {
                provinceId.getRegisteredUserList().remove(registeredUser);
                provinceId = em.merge(provinceId);
            }
            List<Review> reviewList = registeredUser.getReviewList();
            for (Review reviewListReview : reviewList) {
                reviewListReview.setUserId(null);
                reviewListReview = em.merge(reviewListReview);
            }
            em.remove(registeredUser);
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

    public List<RegisteredUser> findRegisteredUserEntities() {
        return findRegisteredUserEntities(true, -1, -1);
    }

    public List<RegisteredUser> findRegisteredUserEntities(int maxResults, int firstResult) {
        return findRegisteredUserEntities(false, maxResults, firstResult);
    }

    private List<RegisteredUser> findRegisteredUserEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from RegisteredUser as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public RegisteredUser findRegisteredUser(Integer id) {
        return em.find(RegisteredUser.class, id);
    }
    
        public List<RegisteredUser> findAll() {
        Query q = em.createNamedQuery("findAll", RegisteredUser.class);
        return q.getResultList();
    }
    
    public RegisteredUser findUserById(int id) {
        Query q = em.createNamedQuery("findByUserId", RegisteredUser.class);
        q.setParameter("userId", id);
        return (RegisteredUser) q.getResultList().get(0);
    }
    
    public RegisteredUser findUserByEmail(String email) {
        Query q = em.createNamedQuery("findByEmailAddress", RegisteredUser.class);
        q.setParameter("emailAddress", email);
        return (RegisteredUser) q.getResultList().get(0);
    }

    public int getRegisteredUserCount() {
        Query q = em.createQuery("select count(o) from RegisteredUser as o");
        return ((Long) q.getSingleResult()).intValue();
    }
}
