/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.entities.*;
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
    BookJpaController bookJpaController;

    @Inject
    RegisteredUserJpaController registeredUserJpaController;

    @Inject
    ReviewJpaController reviewJpaController;

    @Inject
    InvoiceJpaController invoiceJpa;

    @Inject
    RegisteredUserJpaController userJpa;
    
    @Inject
    NewsFeedJpaController newsFeedJpaController;

    @Inject
    SurveyJpaController surveyJpaController;

    //injecting beans
    @Inject
    Book book;

    @Inject
    RegisteredUser registeredUser;

    @Inject
    Review review;

    @Inject
    Invoice invoice;

    @Inject
    RegisteredUser user;

    @Inject
    NewsFeed newsFeed;

    @Inject
    Survey survey;

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

}
