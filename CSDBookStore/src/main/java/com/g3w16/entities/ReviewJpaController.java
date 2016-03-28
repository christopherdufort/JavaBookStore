/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.Date;

import java.util.List;
import javax.annotation.ManagedBean;
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
 * @author Xin Ma
 */
@Named
@SessionScoped
public class ReviewJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public ReviewJpaController() {
        super();
    }

    public void create(Review review) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            Approval approvalId = review.getApprovalId();
            if (approvalId != null) {
                approvalId = em.getReference(approvalId.getClass(), approvalId.getApprovalId());
                review.setApprovalId(approvalId);
            }
            Book isbn = review.getIsbn();
            if (isbn != null) {
                isbn = em.getReference(isbn.getClass(), isbn.getBookId());
                review.setIsbn(isbn);
            }
            RegisteredUser userId = review.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                review.setUserId(userId);
            }
            em.persist(review);
            if (approvalId != null) {
                approvalId.getReviewList().add(review);
                approvalId = em.merge(approvalId);
            }
            if (isbn != null) {
                isbn.getReviewList().add(review);
                isbn = em.merge(isbn);
            }
            if (userId != null) {
                userId.getReviewList().add(review);
                userId = em.merge(userId);
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

    public void edit(Review review) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Review persistentReview = em.find(Review.class, review.getReviewId());
            Approval approvalIdOld = persistentReview.getApprovalId();
            Approval approvalIdNew = review.getApprovalId();
            Book isbnOld = persistentReview.getIsbn();
            Book isbnNew = review.getIsbn();
            RegisteredUser userIdOld = persistentReview.getUserId();
            RegisteredUser userIdNew = review.getUserId();
            if (approvalIdNew != null) {
                approvalIdNew = em.getReference(approvalIdNew.getClass(), approvalIdNew.getApprovalId());
                review.setApprovalId(approvalIdNew);
            }
            if (isbnNew != null) {
                isbnNew = em.getReference(isbnNew.getClass(), isbnNew.getBookId());
                review.setIsbn(isbnNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                review.setUserId(userIdNew);
            }
            review = em.merge(review);
            if (approvalIdOld != null && !approvalIdOld.equals(approvalIdNew)) {
                approvalIdOld.getReviewList().remove(review);
                approvalIdOld = em.merge(approvalIdOld);
            }
            if (approvalIdNew != null && !approvalIdNew.equals(approvalIdOld)) {
                approvalIdNew.getReviewList().add(review);
                approvalIdNew = em.merge(approvalIdNew);
            }
            if (isbnOld != null && !isbnOld.equals(isbnNew)) {
                isbnOld.getReviewList().remove(review);
                isbnOld = em.merge(isbnOld);
            }
            if (isbnNew != null && !isbnNew.equals(isbnOld)) {
                isbnNew.getReviewList().add(review);
                isbnNew = em.merge(isbnNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getReviewList().remove(review);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getReviewList().add(review);
                userIdNew = em.merge(userIdNew);
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
                Integer id = review.getReviewId();
                if (findReview(id) == null) {
                    throw new NonexistentEntityException("The review with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Review review;
            try {
                review = em.getReference(Review.class, id);
                review.getReviewId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The review with id " + id + " no longer exists.", enfe);
            }
            Approval approvalId = review.getApprovalId();
            if (approvalId != null) {
                approvalId.getReviewList().remove(review);
                approvalId = em.merge(approvalId);
            }
            Book isbn = review.getIsbn();
            if (isbn != null) {
                isbn.getReviewList().remove(review);
                isbn = em.merge(isbn);
            }
            RegisteredUser userId = review.getUserId();
            if (userId != null) {
                userId.getReviewList().remove(review);
                userId = em.merge(userId);
            }
            em.remove(review);
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
    
    public List<Review> findReviewEntities() {
        return findReviewEntities(true, -1, -1);
    }

    public List<Review> getFindReviewEntities() {
        return findReviewEntities(true, -1, -1);
    }

    public List<Review> findReviewEntities(int maxResults, int firstResult) {
        return findReviewEntities(false, maxResults, firstResult);
    }

    private List<Review> findReviewEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Review as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Review findReview(Integer id) {
        return em.find(Review.class, id);
    }

    public List<Review> findReviewByUserId(RegisteredUser registeredUser) {
        Query q = em.createNamedQuery("Review.findByUserId");
        q.setParameter("userId", registeredUser.getUserId());
        return q.getResultList();
    }

    public List<Review> findReviewByDateSubmitted(Date dateSubmitted) {
        Query q = em.createNamedQuery("Review.findByDateSubmitted");
        q.setParameter("dateSubmitted", dateSubmitted);
        return q.getResultList();
    }

    public List<Review> findReviewByApprovalId(Approval approval) {
        Query q = em.createNamedQuery("Review.findByApprovalId");
        q.setParameter("approvalId", approval.getApprovalId());
        return q.getResultList();
    }

    public List<Review> findReviewByIsbn(Book book) {
        Query q = em.createNamedQuery("Review.findByIsbn");
        q.setParameter("isbn", book.getIsbn());
        return q.getResultList();
    }

    public List<Review> findReviewByRating(Integer rating) {
        Query q = em.createNamedQuery("Review.findByRating");
        q.setParameter("rating", rating);
        return q.getResultList();
    }
    public int getReviewCount() {
        Query q = em.createQuery("select count(o) from Review as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
