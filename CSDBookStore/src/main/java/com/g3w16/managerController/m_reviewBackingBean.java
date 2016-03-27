/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_reviews")
@RequestScoped
public class m_reviewBackingBean {

    private Review review;

    @Inject
    ReviewJpaController reviewJpa;

    private Approval approval;

    @Inject
    ApprovalJpaController approvalJpa;

    @Inject
    BookJpaController bookJpa;

    @Inject
    RegisteredUserJpaController userJpa;

    private String isbn;

    private String userId;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String selectedApproval;

    public String getSelectedApproval() {
        return selectedApproval;
    }

    public void setSelectedApproval(String selectedApproval) {
        this.selectedApproval = selectedApproval;
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

    public void editReview(Review r) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + selectedApproval);
        try {
            review = reviewJpa.findReview(r.getReviewId());
            //review.setIsbn(bookJpa.findBookEntitiesById(review.getIsbn().getBookId()));
            //review.setUserId(userJpa.findUserById(review.getUserId().getUserId()));
            review.setApprovalId(approvalJpa.findByApprovalStatus(selectedApproval));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + review.getApprovalId());

            reviewJpa.edit(review);
        } catch (Exception ex) {
            Logger.getLogger(m_reviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Review> getAllReview() {
        return reviewJpa.findReviewEntities();
    }

    public int getReviewCount() {
        return reviewJpa.getReviewCount();
    }

    public List<Approval> getAllApproval() {
        return approvalJpa.findApprovalEntities();
    }

    public Approval setSelApproval(String s) {
        return approvalJpa.findByApprovalStatus(s);
    }

}
