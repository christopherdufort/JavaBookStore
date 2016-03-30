/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private String searchInvoice;

    private Invoice invoice;
    private InvoiceDetail invoiceDetail;

    private List<Invoice> all;
    private List<Invoice> searched;
//    private Date date1;
//    private Date date2;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;

    public String getText() {
        return searchInvoice;
    }
    
    @PostConstruct
    public void init() {
        all=invoiceJpa.findInvoiceEntities();       
    }

    public void setText(String searchInvoice) {
        this.searchInvoice = searchInvoice;
    }

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
    
    public List<Invoice> getSearchedInvoices() {
        return searched;
    }
 
    public void setSearchedInvoices(List<Invoice> searched) {
        this.searched = searched;
    }

//    public void setAllInvoices(List<Invoice> all) {
//        this.all = all;
//    }

    /**
     * Returning all invoices
     *
     * @return
     */
    public List<Invoice> getAllInvoices() {

//        all = invoiceJpa.findInvoiceEntities();
//        //  all= invoiceJpa.findInvoiceEntities();
////         for(int i =0; i<invoiceJpa.findInvoiceEntities().size();i++){
////         
////             all.add(invoice)
////         }
        return all;
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

    public void handleSearchInvoice() {

        all = invoiceJpa.findInvoiceByUserNumber(Integer.parseInt(searchInvoice));

    }

    public String cancel() {

        return "m_invoices";
    }

}
