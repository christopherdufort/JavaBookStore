/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
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
        try {
            reviewJpa.edit(r);  
        } catch (Exception ex) {
            Logger.getLogger(m_reviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String destroyReview(Review r) {

        try {
            reviewJpa.destroy(r.getReviewId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_reviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_reviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "m_reviews";
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

    public Approval getApprovalobject(String str) {
        switch (str) {
            case "Approved":               
                return new Approval(1);

            case "Pending":             
                return new Approval(2);
                
            case "Denied":
                return new Approval(3);
        }
        return null;
    }
}
