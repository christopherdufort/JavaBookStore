/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

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
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named
@RequestScoped
public class ManagerView {

    // injecting controllers
    @Inject
    AdJpaController adJpa;

    @Inject
    BookJpaController bookJpaController;

    @Inject
    RegisteredUserJpaController registeredUserJpaController;

    @Inject
    ReviewJpaController reviewJpaController;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpa;
    @Inject
    RegisteredUserJpaController userJpa;

    @Inject
    NewsFeedJpaController newsFeedJpaController;

    @Inject
    SurveyJpaController surveyJpaController;

    //injecting beans
    @Inject
    Ad ad;

    @Inject
    Book book;

    @Inject
    RegisteredUser registeredUser;

    @Inject
    Review review;

    @Inject
    Invoice invoice;

    @Inject
    InvoiceDetail invoiceDetail;

    @Inject
    RegisteredUser user;

    @Inject
    NewsFeed newsFeed;

    @Inject
    Survey survey;

    /**
     *
     *
     * All adds.
     */
    public List<Ad> getAllAds() {
        return adJpa.findAllAds();
    }

    /**
     *
     *
     * Review table methods.
     */
    public List<Review> getAllReview() {
        return reviewJpaController.findReviewEntities();
    }

    /**
     * Book table methods
     */
    public List<Book> getAllBook() {
        return bookJpaController.findBookEntities();
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
     * @return
     */
    public List<Invoice> getAllInvoicesByDateAndUser(Date date1, Date date2, Integer user) {
        return invoiceJpa.findInvoiceByDateAndUser(date1, date2, user);

    }

    /**
     * Returning all invoices
     *
     * @return
     */
    public List<RegisteredUser> getAllUsers() {
        return userJpa.findAll();

    }

    /**
     * News table methods
     *
     * @return
     */
    public List<NewsFeed> getAllNews() {
        return newsFeedJpaController.findAllNewsFeeds();
    }

    /**
     * Survey table methods
     */
    public List<Survey> getAllSurvey() {
        return surveyJpaController.findAllSurveys();
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
     * @return
     */
    public BigDecimal getTotalSalesForOneInvoice() {
        Invoice id = new Invoice(1);
        BigDecimal detailTotal = BigDecimal.valueOf(0);
        double total = 0;

        List<InvoiceDetail> byInvoiceId = invoiceDetailJpa.findInvoiceDetailByInvoice(id);

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

        List<Invoice> allInvoices = getAllInvoices();

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

        List<Invoice> allInvoices = getAllInvoicesByDateAndUser(date1, date2, user);

        for (int i = 0; i < allInvoices.size(); i++) {

            Invoice in = allInvoices.get(i);
            t += in.getTotalGrossValueOfSale().doubleValue();
        }
        total = BigDecimal.valueOf(t).setScale(2, RoundingMode.CEILING);
        return total;
    }
}
