/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 *
 * @author Rita Lazaar
 */
@Named("m_reports")
@RequestScoped
public class m_reportsBackingBean {

    private InvoiceDetail invoiceDetail;

    private Book book;

    @Inject
    m_invoicesBackingBean m_invoicesBackingBean;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;
    @Inject
    InvoiceJpaController invoiceJpa;
    @Inject
    BookJpaController bookJpa;

    public InvoiceDetail getInvoiceDetail() {
        if (invoiceDetail == null) {
            invoiceDetail = new InvoiceDetail();
        }
        return invoiceDetail;
    }

    public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public List<Invoice> getInvoicesWithDate(Date date1, Date date2) {
        return invoiceJpa.findInvoiceByDate(date1, date2);
    }

    /**
     * This method calculates the total sales that exist for one invoice.
     * Including taxes.
     *
     * Probably to be used when adding to cart and adding to invoice.
     *
     * It returns it as a big decimal
     *
     * @author Rita Lazaar
     * @version 0.0.1 - testing
     * @param id
     * @return
     */
    public BigDecimal getTotalSalesForOneInvoice(int id) {

        Invoice invoice = new Invoice(1);
        //this is a test value
        // originally it should come from the invoice that we send it
        BigDecimal detailTotal = BigDecimal.valueOf(0);
        double total = 0;

        List<InvoiceDetail> byInvoiceId = invoiceDetailJpa.findInvoiceDetailByInvoice(invoice);

        for (int i = 0; i < byInvoiceId.size(); i++) {

            InvoiceDetail detail = byInvoiceId.get(i);
            total += (detail.getBookPrice().add(detail.getGst()).add(detail.getHst()).add(detail.getPst())).doubleValue();

        }
        detailTotal = BigDecimal.valueOf(total).setScale(2, RoundingMode.CEILING);
        return detailTotal;
    }

    // ------------ REPORTS METHODS ----------------
    /**
     * This method returns the total gross value of all sales in the databases.
     *
     * @return
     */
    public BigDecimal getAllTotalSales() {

        BigDecimal total = BigDecimal.valueOf(0);
        double t = 0;

        List<Invoice> allInvoices = m_invoicesBackingBean.getAllInvoices();

        for (int i = 0; i < allInvoices.size(); i++) {

            Invoice in = allInvoices.get(i);
            t += in.getTotalGrossValueOfSale().doubleValue();
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    /**
     * This method return the total of sale for a particular user in a range of
     * time given
     *
     * @param date1
     * @param date2
     * @param user
     * @return
     */
    public BigDecimal getAllSalesByClient(Date date1, Date date2, Integer user) {

        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        List<Invoice> allInvoices = m_invoicesBackingBean.getAllInvoicesByDateAndUser(date1, date2, user);

        for (int i = 0; i < allInvoices.size(); i++) {

            Invoice in = allInvoices.get(i);
            t += in.getTotalGrossValueOfSale().doubleValue();
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    public List<Book> getAllBooks() {

        return bookJpa.findBookEntities();
    }

    public List<InvoiceDetail> getAllBooksInvoiceDetailPerBook(Book book) {

        return invoiceDetailJpa.findInvoiceDetailByBook(book);

    }
/**
 * This gets the total sales for a specific book
 * @param book
 * @return 
 */
    public BigDecimal getTotalPerBook(Book book) {
        
        BigDecimal total = BigDecimal.ZERO;
        double t = 0;
        List<InvoiceDetail> allBooks = getAllBooksInvoiceDetailPerBook(book);

        for (int i = 0; i < allBooks.size(); i++) {
            t += allBooks.get(i).getBookPrice().doubleValue();
        }

        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

}
