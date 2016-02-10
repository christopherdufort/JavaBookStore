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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1040570
 */
public class ApprovalJpaController implements Serializable {

    public ApprovalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Approval approval) throws RollbackFailureException, Exception {
        if (approval.getReviewList() == null) {
            approval.setReviewList(new ArrayList<Review>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : approval.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            approval.setReviewList(attachedReviewList);
            em.persist(approval);
            for (Review reviewListReview : approval.getReviewList()) {
                Approval oldApprovalIdOfReviewListReview = reviewListReview.getApprovalId();
                reviewListReview.setApprovalId(approval);
                reviewListReview = em.merge(reviewListReview);
                if (oldApprovalIdOfReviewListReview != null) {
                    oldApprovalIdOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldApprovalIdOfReviewListReview = em.merge(oldApprovalIdOfReviewListReview);
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Approval approval) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Approval persistentApproval = em.find(Approval.class, approval.getApprovalId());
            List<Review> reviewListOld = persistentApproval.getReviewList();
            List<Review> reviewListNew = approval.getReviewList();
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewId());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            approval.setReviewList(reviewListNew);
            approval = em.merge(approval);
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    reviewListOldReview.setApprovalId(null);
                    reviewListOldReview = em.merge(reviewListOldReview);
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Approval oldApprovalIdOfReviewListNewReview = reviewListNewReview.getApprovalId();
                    reviewListNewReview.setApprovalId(approval);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldApprovalIdOfReviewListNewReview != null && !oldApprovalIdOfReviewListNewReview.equals(approval)) {
                        oldApprovalIdOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldApprovalIdOfReviewListNewReview = em.merge(oldApprovalIdOfReviewListNewReview);
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
                Integer id = approval.getApprovalId();
                if (findApproval(id) == null) {
                    throw new NonexistentEntityException("The approval with id " + id + " no longer exists.");
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
            Approval approval;
            try {
                approval = em.getReference(Approval.class, id);
                approval.getApprovalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The approval with id " + id + " no longer exists.", enfe);
            }
            List<Review> reviewList = approval.getReviewList();
            for (Review reviewListReview : reviewList) {
                reviewListReview.setApprovalId(null);
                reviewListReview = em.merge(reviewListReview);
            }
            em.remove(approval);
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

    public List<Approval> findApprovalEntities() {
        return findApprovalEntities(true, -1, -1);
    }

    public List<Approval> findApprovalEntities(int maxResults, int firstResult) {
        return findApprovalEntities(false, maxResults, firstResult);
    }

    private List<Approval> findApprovalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Approval as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Approval findApproval(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Approval.class, id);
        } finally {
            em.close();
        }
    }

    public int getApprovalCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Approval as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
