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

    @Inject
    Review review;

    @Inject
    ReviewJpaController reviewJpa;

    @Inject
    Approval approval;

    @Inject
    ApprovalJpaController approvalJpa;

    public Review getSelectedReview() {
        return review;
    }

    public String editReview(Review r) {
        review = reviewJpa.findReview(r.getReviewId());
        return "m_editReview";
    }

    public String updateReview(Review r) {
        try {
            reviewJpa.edit(r);
        } catch (Exception ex) {
            Logger.getLogger(m_reviewBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_reviews";
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
}
