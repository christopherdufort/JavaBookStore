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
 * This is the controller for the invoice detail table.
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

    /**
     * This method allows the entity object to be created for Invoice Detail and
     * it will be related to the appropriate entity objects : book and invoice.
     *
     * @param invoiceDetail
     * @throws RollbackFailureException
     * @throws Exception
     */
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

    /**
     * This will return a list of invoice detail containing all invoice details
     * existing on the database.
     *
     * @return
     */
    public List<InvoiceDetail> findInvoiceDetailEntities() {
        return findInvoiceDetailEntities(true, -1, -1);
    }

    /**
     * This will return a list of invoice details based on the max result and
     * the first results values sent into the method. Ï
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult) {
        return findInvoiceDetailEntities(false, maxResults, firstResult);
    }

    /**
     * This method will return a list of invoice details. It will return all of
     * them if all is true, and a specific range of invoice details based on the
     * values in maxResults and firstResults.
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
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

    /**
     * This method will return an invoice detail based on the id sent to the
     * method.
     *
     * @param id
     * @return
     */
    public InvoiceDetail findInvoiceDetail(Integer id) {

        return em.find(InvoiceDetail.class, id);

    }

    /**
     * This will return a list of invoice details related to a specific invoice.
     *
     * @param invoiceId
     * @return
     */
    public List<InvoiceDetail> findInvoiceDetailByInvoice(Invoice invoiceId) {
        Query q = em.createNamedQuery("InvoiceDetail.findByInvoiceId", InvoiceDetail.class);
        q.setParameter("invoiceId", invoiceId);
        return q.getResultList();

    }

    /**
     * This will return a list of invoice details related to a specific invoice.
     *
     * @param book
     *
     * @return
     */
    public List<InvoiceDetail> findInvoiceDetailByBook(Book book) {
        Query q = em.createNamedQuery("InvoiceDetail.findByBookId", InvoiceDetail.class);
        q.setParameter("bookId", book);
        return q.getResultList();

    }

    /**
     * This method returns the total amount of invoices in the database.
     *
     * @return
     */
    public int getInvoiceDetailCount() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<InvoiceDetail> rt = cq.from(InvoiceDetail.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    // missing method for the total sales, sales by client , sales by author, sales by publisher
}
