/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "review", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findByReviewId", query = "SELECT r FROM Review r WHERE r.reviewId = :reviewId"),
    @NamedQuery(name = "Review.findByUserId", query = "SELECT r FROM Review r WHERE r.userId = :userId"),
    @NamedQuery(name = "Review.findByDateSubmitted", query = "SELECT r FROM Review r WHERE r.dateSubmitted = :dateSubmitted"),
    @NamedQuery(name = "Review.findByRating", query = "SELECT r FROM Review r WHERE r.rating = :rating"),
    @NamedQuery(name = "Review.findByApprovalId", query = "SELECT r FROM Review r WHERE r.approvalId = :approvalId"),
    @NamedQuery(name = "Review.findByIsbn", query = "SELECT r FROM Review r WHERE r.isbn = :isbn"),
    @NamedQuery(name = "Review.findByReviewTitle", query = "SELECT r FROM Review r WHERE r.reviewTitle = :reviewTitle"),
    @NamedQuery(name = "Review.findByReviewText", query = "SELECT r FROM Review r WHERE r.reviewText = :reviewText")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "review_id")
    private Integer reviewId;
    @Column(name = "date_submitted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSubmitted;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "review_title")
    private String reviewTitle;
    @Column(name = "review_text")
    private String reviewText;
    @JoinColumn(name = "approval_id", referencedColumnName = "approval_id")
    @ManyToOne
    private Approval approvalId;
    @JoinColumn(name = "isbn", referencedColumnName = "isbn")
    @ManyToOne
    private Book isbn;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private RegisteredUser userId;

    public Review() {
    }

    public Review(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Approval getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Approval approvalId) {
        this.approvalId = approvalId;
    }

    public Book getIsbn() {
        return isbn;
    }

    public void setIsbn(Book isbn) {
        this.isbn = isbn;
    }

    public RegisteredUser getUserId() {
        return userId;
    }

    public void setUserId(RegisteredUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Review[ reviewId=" + reviewId + " ]";
    }
    
}
