/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
    private Date date1;
    private Date date2;

    @Inject
    m_invoicesBackingBean m_invoicesBackingBean;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    BookJpaController bookJpa;

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

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

        Invoice invoice = new Invoice(id);
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

    /**
     * This gets all the invoice details for a specific book.
     *
     * @param book
     * @return
     */
    public List<InvoiceDetail> getAllBooksInvoiceDetailPerBook(Book book) {

        return invoiceDetailJpa.findInvoiceDetailByBook(book);

    }

    /**
     * This gets the total sales for a specific book from beginning.
     *
     * @param book
     * @return
     */
    public BigDecimal getTotalPerBook(Book book) {

        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        // calling the method to get all details for this specific book
        List<InvoiceDetail> allBooks = getAllBooksInvoiceDetailPerBook(book);

        for (int i = 0; i < allBooks.size(); i++) {
            t += allBooks.get(i).getBookPrice().doubleValue();
        }
        //calculating total
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    /**
     * This gets the total per publisher given and between the dates that were
     * given.
     *
     * @param publisher
     * @param date1
     * @param date2
     * @return
     */
    public BigDecimal getTotalPerPublisher(String publisher, Date date1, Date date2) {

        List<InvoiceDetail> pubInvDetail = null;
        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        List<Invoice> invoices = getInvoicesWithDate(date1, date2);
        List<InvoiceDetail> invoiceD;
        //running through all the invoices of this date
        for (int i = 0; i < invoices.size(); i++) {
            //for each invoice detail related to this invoice
            // we are checking whether the book it points to has the publisher
            // we are looking for 
            List<InvoiceDetail> id = invoices.get(i).getInvoiceDetailList();
            for (int j = 0; j < id.size(); j++) {
                Book b = id.get(j).getBookId();
                // ** check because publisher is only a string
                if (b.getPublisher().equalsIgnoreCase(publisher)) {
//                    pubInvDetail.add(id.get(j));
                    // for the moment it is only list price.
                    t += b.getListPrice().doubleValue();
                }

            }
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;

    }

    /**
     * This gets the total sales for a specific author and between certain
     * dates.
     *
     * @param author
     * @param date1
     * @param date2
     * @return
     */
    public BigDecimal getTotalPerAuthor(Author author, Date date1, Date date2) {
        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        List<Invoice> invoices = getInvoicesWithDate(date1, date2);
        List<InvoiceDetail> invoiceD;
        //running through all the invoices of this date
        for (int i = 0; i < invoices.size(); i++) {
            //for each invoice detail related to this invoice
            // we are checking whether the book it points to has the publisher
            // we are looking for 
            List<InvoiceDetail> id = invoices.get(i).getInvoiceDetailList();
            for (int j = 0; j < id.size(); j++) {
                Book b = id.get(j).getBookId();
                // checks if this book has this author
                if (b.getAuthorList().contains(author)) {
//                    pubInvDetail.add(id.get(j));
                    // for the moment it is only list price.
                    t += b.getListPrice().doubleValue();
                }

            }
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }
}
