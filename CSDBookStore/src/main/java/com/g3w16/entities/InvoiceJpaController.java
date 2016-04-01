/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Rita Lazaar
 * @author Christopher Dufort
 *
 */
@Named
@RequestScoped
public class InvoiceJpaController implements Serializable{

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    /**
     * This method allows to create an invoice in the database. We send it an
     * invoice object and it adds it to the database.
     *
     * @param invoice
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void create(Invoice invoice) throws RollbackFailureException, Exception {
        if (invoice.getInvoiceDetailList() == null) {
            invoice.setInvoiceDetailList(new ArrayList<InvoiceDetail>());
        }
        try {
            utx.begin();
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
        }
    }

    /**
     * This returns a list of all invoices.
     *
     * @return
     */
    public List<Invoice> findInvoiceEntities() {
        return findInvoiceEntities(true, -1, -1);
    }

    /**
     * This method will return a list of invoices based on the bounds we have
     * given to it.
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Invoice> findInvoiceEntities(int maxResults, int firstResult) {
        return findInvoiceEntities(false, maxResults, firstResult);
    }

    /**
     * This method will return all invoices if all is true , else it will return
     * the appropriate list of invoice between maxResults and firstResult.
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Invoice> findInvoiceEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Invoice as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    /**
     * This will return one particular invoice based on the id given to the
     * method.
     *
     * @param id
     * @return
     */
    public Invoice findInvoice(Integer id) {
        return em.find(Invoice.class, id);
    }

    /**
     * This will return a list of invoices belonging to the user given to it.
     *
     * @param userId
     * @return
     */
    
    public List<Invoice> findInvoiceByUserId(Integer userId) {
        Query q = em.createNamedQuery("Invoice.findByUserId", Invoice.class);
        q.setParameter("userId", userId);
        return q.getResultList();
    }

    /**
     * This will return a list of invoices belonging to the user given to it.
     *
     * @param userId
     * @return
     */
    public List<Invoice> findInvoiceByDate(Date date1, Date date2) {
        Query q = em.createNamedQuery("Invoice.findAllInvoicesByDate", Invoice.class);
        q.setParameter(1, date1);
        q.setParameter(2, date2);

        return q.getResultList();
    }

    /**
     * This will return a list of invoices belonging to the user given to it.
     *
     * @param userId
     * @return
     */

    public List<Invoice> findInvoiceByDateAndUser(Date date1, Date date2, Integer userId) {

        Query q = em.createNamedQuery("Invoice.findAllInvoicesByDateAndUser", Invoice.class);
        q.setParameter(1, date1);
        q.setParameter(2, date2);
        q.setParameter(3, userId);

        return q.getResultList();
    }

    /**
     * This will return a count of all invoices existing in database.
     *
     * @return
     */
    public int getInvoiceCount() {
        Query q = em.createQuery("select count(o) from Invoice as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
