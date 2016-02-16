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
 * @author Rita Lazaar
 */
public class InvoiceDetailJpaController implements Serializable {

    public InvoiceDetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Invoice invoiceId = invoiceDetail.getInvoiceId();
            if (invoiceId != null) {
                invoiceId = em.getReference(invoiceId.getClass(), invoiceId.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceId);
            }
            em.persist(invoiceDetail);
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InvoiceDetail invoiceDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InvoiceDetail persistentInvoiceDetail = em.find(InvoiceDetail.class, invoiceDetail.getInvoiceDetailId());
            Invoice invoiceIdOld = persistentInvoiceDetail.getInvoiceId();
            Invoice invoiceIdNew = invoiceDetail.getInvoiceId();
            if (invoiceIdNew != null) {
                invoiceIdNew = em.getReference(invoiceIdNew.getClass(), invoiceIdNew.getInvoiceId());
                invoiceDetail.setInvoiceId(invoiceIdNew);
            }
            invoiceDetail = em.merge(invoiceDetail);
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
            InvoiceDetail invoiceDetail;
            try {
                invoiceDetail = em.getReference(InvoiceDetail.class, id);
                invoiceDetail.getInvoiceDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoiceDetail with id " + id + " no longer exists.", enfe);
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return findInvoiceDetailEntities(true, -1, -1);
    }

    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return findInvoiceDetailEntities(false, maxResults, firstResult);
    }
    

    private List<InvoiceDetail> findInvoiceDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from InvoiceDetail as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public InvoiceDetail findInvoiceDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InvoiceDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceDetailCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from InvoiceDetail as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
