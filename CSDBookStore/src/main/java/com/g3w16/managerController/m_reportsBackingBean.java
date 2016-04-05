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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_reports")
@RequestScoped
public class m_reportsBackingBean {

    private InvoiceDetail invoiceDetail;

    private Book book;
    private Date date1;
    private Date date2;
    private RegisteredUser user;
    private Author author;
    private String publisher;

    private List<Book> allBooks;
    private List<RegisteredUser> allUsers;
    private List<String> allPublishers;

    @Inject
    m_invoicesBackingBean m_invoicesBackingBean;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    BookJpaController bookJpa;

    @Inject
    RegisteredUserJpaController userJpa;

    @PostConstruct
    public void init() {
        allBooks = bookJpa.findBookEntities();
        allUsers = userJpa.findAll();

    }

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public void setDate2(Date date2) {
        this.date2 = date2;
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

    public List<Book> getAllBooks() {

        return allBooks;
    }

    public List<RegisteredUser> getAllUsers() {

        return allUsers;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceJpa.findInvoiceEntities();
    }

    public List<Invoice> getInvoicesWithDate(Date date1, Date date2) {
        return invoiceJpa.findInvoiceByDate(date1, date2);
    }

    public List<Invoice> getAllInvoicesByUser(Integer user) {
        return invoiceJpa.findInvoiceByUserId(user);

    }

    public List<Invoice> getAllInvoicesByDateAndUser(Date date1, Date date2, Integer user) {
        return invoiceJpa.findInvoiceByDateAndUser(date1, date2, user);

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

    public List<String> getAllPublishers() {
        allPublishers = new ArrayList<String>();
        List<Book> books = bookJpa.findBookEntities();

        for (int i = 0; i < books.size(); i++) {
            String p = books.get(i).getPublisher();
            if (!allPublishers.contains(p)) {
                allPublishers.add(p);
            }

        }
        return allPublishers;
    }

    // ------------ REPORTS METHODS ----------------
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

    /**
     * This method returns the total gross value of all sales in the databases.
     *
     * @return
     */
    public BigDecimal getAllTotalSales() {

        BigDecimal total = BigDecimal.valueOf(0);
        double t = 0;

        List<Invoice> allInvoices = getAllInvoices();

        for (int i = 0; i < allInvoices.size(); i++) {

            Invoice in = allInvoices.get(i);
            t += in.getTotalGrossValueOfSale().doubleValue();
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    /**
     * This method returns all books bought by a particular user in a range of
     * time given
     *
     * @param date1
     * @param date2
     * @param user
     * @return
     */
    public List<Book> getAllBooksByClient() {

        List<Invoice> allInvoices = new ArrayList<Invoice>();
        //find all invoices related to user without dates
        if (user == null) {
            return null;
        }
        if (date1 == null || date2 == null) {
            allInvoices = getAllInvoicesByUser(user.getUserId());
        } else {
            //find all invoices within the dates and with the specific user id
            allInvoices = getAllInvoicesByDateAndUser(date1, date2, user.getUserId());

        }
//        BigDecimal total = BigDecimal.ZERO;
//        double t = 0;
        allBooks = new ArrayList<Book>();

        for (int i = 0; i < allInvoices.size(); i++) {

//            Invoice in = allInvoices.get(i);
//            t += in.getTotalGrossValueOfSale().doubleValue();
            List<InvoiceDetail> inDetail = allInvoices.get(i).getInvoiceDetailList();

            for (int j = 0; j < inDetail.size(); j++) {
//                books.add(inDetail.get(j).getBookId());
                if (!allBooks.contains(inDetail.get(j).getBookId())) {
                    allBooks.add(inDetail.get(j).getBookId());
                }
            }
        }
//        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return allBooks;
    }

    public List<Book> getAllBooksWithDate() {

        if (date1 == null || date2 == null) {
            return getAllBooks();
        }

        allBooks = new ArrayList<Book>();
        List<Invoice> in = getInvoicesWithDate(date1, date2);
        System.out.println("in>>>>>>>>>>>>>>>>" + in.toString());
        for (int i = 0; i < in.size(); i++) {
            List<InvoiceDetail> inDetail = in.get(i).getInvoiceDetailList();

            for (int j = 0; j < inDetail.size(); j++) {
//                books.add(inDetail.get(j).getBookId());
                if (!allBooks.contains(inDetail.get(j).getBookId())) {
                    allBooks.add(inDetail.get(j).getBookId());
                }
            }
        }
        // allBooks.add(new Book(1));
        return allBooks;
    }

    public List<Book> getAllBooksWithoutSale() {

        allBooks = bookJpa.findBookEntities();

        List<Invoice> in = new ArrayList<Invoice>();
        if (date1 == null || date2 == null) {
            in = getAllInvoices();
        } else {
            in = getInvoicesWithDate(date1, date2);
        }

        for (int i = 0; i < in.size(); i++) {
            List<InvoiceDetail> inDetail = in.get(i).getInvoiceDetailList();

            for (int j = 0; j < inDetail.size(); j++) {
                Book b = inDetail.get(j).getBookId();
                if (allBooks.contains(b)) {
                    //all books with sales are removed from this list
                    allBooks.remove(b);
                }
            }
        }
        return allBooks;
    }

    /**
     * This method will returns only a list of books who have been sold, either
     * within a date range or from the beginning.
     *
     * @return
     */
    public List<Book> getAllBooksWithSalesOnly() {

        List<Invoice> in = new ArrayList<Invoice>();

        if (date1 == null || date2 == null) {
            in = getAllInvoices();
        } else {
            in = getInvoicesWithDate(date1, date2);
        }

        //clear all the books in the var
        allBooks.clear();
        for (int i = 0; i < in.size(); i++) {

            List<InvoiceDetail> inDetail = in.get(i).getInvoiceDetailList();

            for (int j = 0; j < inDetail.size(); j++) {
                Book b = inDetail.get(j).getBookId();
                //all books with sales are removed from this list
                if (!allBooks.contains(b)) {
                    allBooks.add(b);
                }

            }
        }
        return allBooks;
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
        List<InvoiceDetail> bookInvoiceDetails = getAllBooksInvoiceDetailPerBook(book);

        for (int i = 0; i < bookInvoiceDetails.size(); i++) {

            t += bookInvoiceDetails.get(i).getBookPrice().doubleValue();
        }
        //calculating total
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    /**
     * This gets the total sales for a specific book from beginning.
     *
     * @param u
     * @param book
     * @return
     */
    public BigDecimal getTotalSalesByClient(RegisteredUser u) {

        BigDecimal total = BigDecimal.ZERO;
        double t = 0;
        user = u;
        allBooks = getAllBooksByClient();
        for (int i = 0; i < allBooks.size(); i++) {

            t += allBooks.get(i).getSalePrice().doubleValue();
        }
        //calculating total
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }

    /**
     * This gets the list of book sold for a certain publisher and between the
     * dates that were given.
     *
     * @param publisher
     * @param date1
     * @param date2
     * @return
     */
    public List<Book> getTotalPerPublisher() {

        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        List<Invoice> invoices = new ArrayList<Invoice>();

        if (date1 == null || date2 == null) {
            invoices = getAllInvoices();
        } else {
            invoices = getInvoicesWithDate(date1, date2);
        }

        //clear all elements from list
        allBooks.clear();

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
                    if (!allBooks.contains(b)) {
                        allBooks.add(b);
                    }

                    t += b.getListPrice().doubleValue();
                }

            }
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return allBooks;

    }

    /**
     * This gets the list of book bought by a specific author and between
     * certain dates.
     *
     * @param author
     * @param date1
     * @param date2
     * @return
     */
    public List<Book> getTotalPerAuthor() {
        BigDecimal total = BigDecimal.ZERO;
        double t = 0;

        if (date1 == null || date2 == null) {
            return null;
        }
        allBooks.clear();
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
                    allBooks.add(b);
                    t += b.getListPrice().doubleValue();
                }

            }
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return allBooks;
    }

    public List<RegisteredUser> getTopClients() {

        List<Invoice> in = new ArrayList<Invoice>();

        if (date1 == null || date2 == null) {
            in = getAllInvoices();
        } else {
            in = getInvoicesWithDate(date1, date2);
        }
        allUsers = new ArrayList<>();
        for (int i = 0; i < in.size(); i++) {
            RegisteredUser u = userJpa.findUserById(in.get(i).getUserId());
            if (!allUsers.contains(u)) {
                allUsers.add(u);
            }
        }

        return allUsers;
    }
}
