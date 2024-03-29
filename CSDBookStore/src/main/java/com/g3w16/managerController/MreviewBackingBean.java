/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 * This class is reviewBackingBean that manage all the review pages
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_reviews")
@RequestScoped
public class MreviewBackingBean implements Serializable {

    private Review review;

    private Approval approval;

    private Book book;

    private RegisteredUser user;

    private List<Review> allReview;

    @Inject
    ApprovalJpaController approvalJpa;

    @Inject
    BookJpaController bookJpa;

    @Inject
    RegisteredUserJpaController userJpa;

    @Inject
    ReviewJpaController reviewJpa;

    @PostConstruct
    public void init() {
        allReview = reviewJpa.findReviewEntities();
    }

    public Book getBook() {
        if (book == null) {
            return new Book();
        }
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public RegisteredUser getUser() {
        if (user == null) {
            return new RegisteredUser();
        }
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Review getReview() {
        if (review == null) {
            review = new Review();
        }
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Approval getApproval() {
        if (approval == null) {
            approval = new Approval();
        }
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public String viewReview(Review r) {
        review = reviewJpa.findReview(r.getReviewId());
        return "m_viewReview";
    }

    public String editReview() {
        try {
            reviewJpa.edit(review);
        } catch (Exception ex) {
            Logger.getLogger(MreviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allReview = reviewJpa.findReviewEntities();
        return "m_reviews";
    }

    public void updateReview(Review r) {
        try {
            reviewJpa.edit(r);

        } catch (Exception ex) {
            Logger.getLogger(MreviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String cancel() {
        return "m_reviews";
    }

    public List<Review> getAllReview() {
        return allReview;
    }

    public List<Review> getPendingReview() {
        Approval a = new Approval(2);
        return reviewJpa.findReviewByApprovalId(a);
    }

    /**
     * This method will get the number of pending reviews, which will display on notification icon 
     * @return
     */
    public int getPendingCount() {
        Approval a = new Approval(2);
        return reviewJpa.findReviewByApprovalId(a).size();
    }

    public int getReviewCount() {
        return reviewJpa.getReviewCount();
    }

    public List<Approval> getAllApproval() {
        return approvalJpa.findApprovalEntities();
    }
}
