/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_invoices")
@RequestScoped
public class m_invoicesBackingBean {

//    @Inject
//    Invoice invoice;
    private Invoice invoice;
    private InvoiceDetail invoiceDetail;
//    private Date date1;
//    private Date date2;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;

    public Invoice getInvoice() {
        if (invoice == null) {
            invoice = new Invoice();
        }

        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public InvoiceDetail getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoice(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public String getInvoiceDetailPage() {
        return "m_invoiceDetails";
    }

    /**
     * Returning all invoices
     *
     * @return
     */
    public List<Invoice> getAllInvoices() {
        return invoiceJpa.findInvoiceEntities();

    }

    /**
     * Returning all invoices
     *
     * @param date1
     * @param date2
     * @param user
     * @return
     */
    public List<Invoice> getAllInvoicesByDateAndUser(Date date1, Date date2, Integer user) {
        return invoiceJpa.findInvoiceByDateAndUser(date1, date2, user);

    }

    /**
     * This will return all the invoice details related to the invoice sent to
     * it.
     *
     *
     * @param invoice
     * @return
     */
    public List<InvoiceDetail> getAllInvoiceDetailsByInvoice() {

        return invoiceDetailJpa.findInvoiceDetailByInvoice(invoice);
    }

    public String getDetailsPage(Invoice invoice) {
        this.invoice = invoiceJpa.findInvoice(invoice.getInvoiceId());

        return "m_invoiceDetails";
    }

}
