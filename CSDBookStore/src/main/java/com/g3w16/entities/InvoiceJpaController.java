/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.IllegalOrphanException;
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
public class InvoiceJpaController implements Serializable {

    public InvoiceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Invoice invoice) throws RollbackFailureException, Exception {
        if (invoice.getInvoiceDetailList() == null) {
            invoice.setInvoiceDetailList(new ArrayList<InvoiceDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<InvoiceDetail> attachedInvoiceDetailList = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailListInvoiceDetailToAttach : invoice.getInvoiceDetailList()) {
                invoiceDetailListInvoiceDetailToAttach = em.getReference(invoiceDetailListInvoiceDetailToAttach.getClass(), invoiceDetailListInvoiceDetailToAttach.getInvoiceDetailId());
                attachedInvoiceDetailList.add(invoiceDetailListInvoiceDetailToAttach);
            }
            invoice.setInvoiceDetailList(attachedInvoiceDetailList);
            em.persist(invoice);
            for (InvoiceDetail invoiceDetailListInvoiceDetail : invoice.getInvoiceDetailList()) {
                Invoice oldInvoiceIdOfInvoiceDetailListInvoiceDetail = invoiceDetailListInvoiceDetail.getInvoiceId();
                invoiceDetailListInvoiceDetail.setInvoiceId(invoice);
                invoiceDetailListInvoiceDetail = em.merge(invoiceDetailListInvoiceDetail);
                if (oldInvoiceIdOfInvoiceDetailListInvoiceDetail != null) {
                    oldInvoiceIdOfInvoiceDetailListInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListInvoiceDetail);
                    oldInvoiceIdOfInvoiceDetailListInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailListInvoiceDetail);
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

    public void edit(Invoice invoice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Invoice persistentInvoice = em.find(Invoice.class, invoice.getInvoiceId());
            List<InvoiceDetail> invoiceDetailListOld = persistentInvoice.getInvoiceDetailList();
            List<InvoiceDetail> invoiceDetailListNew = invoice.getInvoiceDetailList();
            List<String> illegalOrphanMessages = null;
            for (InvoiceDetail invoiceDetailListOldInvoiceDetail : invoiceDetailListOld) {
                if (!invoiceDetailListNew.contains(invoiceDetailListOldInvoiceDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InvoiceDetail " + invoiceDetailListOldInvoiceDetail + " since its invoiceId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<InvoiceDetail> attachedInvoiceDetailListNew = new ArrayList<InvoiceDetail>();
            for (InvoiceDetail invoiceDetailListNewInvoiceDetailToAttach : invoiceDetailListNew) {
                invoiceDetailListNewInvoiceDetailToAttach = em.getReference(invoiceDetailListNewInvoiceDetailToAttach.getClass(), invoiceDetailListNewInvoiceDetailToAttach.getInvoiceDetailId());
                attachedInvoiceDetailListNew.add(invoiceDetailListNewInvoiceDetailToAttach);
            }
            invoiceDetailListNew = attachedInvoiceDetailListNew;
            invoice.setInvoiceDetailList(invoiceDetailListNew);
            invoice = em.merge(invoice);
            for (InvoiceDetail invoiceDetailListNewInvoiceDetail : invoiceDetailListNew) {
                if (!invoiceDetailListOld.contains(invoiceDetailListNewInvoiceDetail)) {
                    Invoice oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail = invoiceDetailListNewInvoiceDetail.getInvoiceId();
                    invoiceDetailListNewInvoiceDetail.setInvoiceId(invoice);
                    invoiceDetailListNewInvoiceDetail = em.merge(invoiceDetailListNewInvoiceDetail);
                    if (oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail != null && !oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail.equals(invoice)) {
                        oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail.getInvoiceDetailList().remove(invoiceDetailListNewInvoiceDetail);
                        oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail = em.merge(oldInvoiceIdOfInvoiceDetailListNewInvoiceDetail);
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
                Integer id = invoice.getInvoiceId();
                if (findInvoice(id) == null) {
                    throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Invoice invoice;
            try {
                invoice = em.getReference(Invoice.class, id);
                invoice.getInvoiceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The invoice with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<InvoiceDetail> invoiceDetailListOrphanCheck = invoice.getInvoiceDetailList();
            for (InvoiceDetail invoiceDetailListOrphanCheckInvoiceDetail : invoiceDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Invoice (" + invoice + ") cannot be destroyed since the InvoiceDetail " + invoiceDetailListOrphanCheckInvoiceDetail + " in its invoiceDetailList field has a non-nullable invoiceId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(invoice);
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

    public List<Invoice> findInvoiceEntities() {
        return findInvoiceEntities(true, -1, -1);
    }

    public List<Invoice> findInvoiceEntities(int maxResults, int firstResult) {
        return findInvoiceEntities(false, maxResults, firstResult);
    }

    private List<Invoice> findInvoiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Invoice as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Invoice findInvoice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Invoice.class, id);
        } finally {
            em.close();
        }
    }

    public int getInvoiceCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Invoice as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
