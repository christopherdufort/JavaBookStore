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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Rita Lazaar
 *
 */
@Named
@SessionScoped
public class InvoiceDetailJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            Book bookId = invoiceDetail.getBookId();
            if (bookId != null) {
                bookId = em.getReference(bookId.getClass(), bookId.getBookId());
                invoiceDetail.setBookId(bookId);
            }
            Invoice invoiceId = invoiceDetail.getInvoiceId();
            if (invoiceId != null) {
                invoiceId = em.getReference(invoiceId.getClass(), invoiceId.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceId);
            }
            em.persist(invoiceDetail);
            if (bookId != null) {
                bookId.getInvoiceDetailList().add(invoiceDetail);
                bookId = em.merge(bookId);
            }
            if (invoiceId != null) {
                invoiceId.getInvoiceDetailList().add(invoiceDetail);
                invoiceId = em.merge(invoiceId);
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

    public void edit(InvoiceDetail invoiceDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            InvoiceDetail persistentInvoiceDetail = em.find(InvoiceDetail.class, invoiceDetail.getInvoiceDetailId());
            Book bookIdOld = persistentInvoiceDetail.getBookId();
            Book bookIdNew = invoiceDetail.getBookId();
            Invoice invoiceIdOld = persistentInvoiceDetail.getInvoiceId();
            Invoice invoiceIdNew = invoiceDetail.getInvoiceId();
            if (bookIdNew != null) {
                bookIdNew = em.getReference(bookIdNew.getClass(), bookIdNew.getBookId());
                invoiceDetail.setBookId(bookIdNew);
            }
            if (invoiceIdNew != null) {
                invoiceIdNew = em.getReference(invoiceIdNew.getClass(), invoiceIdNew.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceIdNew);
            }
            invoiceDetail = em.merge(invoiceDetail);
            if (bookIdOld != null && !bookIdOld.equals(bookIdNew)) {
                bookIdOld.getInvoiceDetailList().remove(invoiceDetail);
                bookIdOld = em.merge(bookIdOld);
            }
            if (bookIdNew != null && !bookIdNew.equals(bookIdOld)) {
                bookIdNew.getInvoiceDetailList().add(invoiceDetail);
                bookIdNew = em.merge(bookIdNew);
            }
            if (invoiceIdOld != null && !invoiceIdOld.equals(invoiceIdNew)) {
                invoiceIdOld.getInvoiceDetailList().remove(invoiceDetail);
                invoiceIdOld = em.merge(invoiceIdOld);
            }
            if (invoiceIdNew != null && !invoiceIdNew.equals(invoiceIdOld)) {
                invoiceIdNew.getInvoiceDetailList().add(invoiceDetail);
                invoiceIdNew = em.merge(invoiceIdNew);
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
                Integer id = invoiceDetail.getInvoiceDetailId();
                if (findInvoiceDetail(id) == null) {
                    throw new NonexistentEntityException("The invoiceDetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }  
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            InvoiceDetail invoiceDetail;
            try {
                invoiceDetail = em.getReference(InvoiceDetail.class, id);
                invoiceDetail.getInvoiceDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoiceDetail with id " + id + " no longer exists.", enfe);
            }
            Book bookId = invoiceDetail.getBookId();
            if (bookId != null) {
                bookId.getInvoiceDetailList().remove(invoiceDetail);
                bookId = em.merge(bookId);
            }
            Invoice invoiceId = invoiceDetail.getInvoiceId();
            if (invoiceId != null) {
                invoiceId.getInvoiceDetailList().remove(invoiceDetail);
                invoiceId = em.merge(invoiceId);
            }
            em.remove(invoiceDetail);
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

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return findInvoiceDetailEntities(true, -1, -1);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return findInvoiceDetailEntities(false, maxResults, firstResult);
    }

    private List<InvoiceDetail> findInvoiceDetailEntities(boolean all, int maxResults, int firstResult) {
        
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InvoiceDetail.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        
    }

    public InvoiceDetail findInvoiceDetail(Integer id) {
        
            return em.find(InvoiceDetail.class, id);
        
    }

    public List<InvoiceDetail> findInvoiceDetailByInvoice(Invoice invoiceId) {
        Query q = em.createNamedQuery("InvoiceDetail.findByInvoiceId", InvoiceDetail.class);
        q.setParameter("invoiceId", invoiceId);
        return q.getResultList();

    }

    public int getInvoiceDetailCount() {
        
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InvoiceDetail> rt = cq.from(InvoiceDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        
    }

}
