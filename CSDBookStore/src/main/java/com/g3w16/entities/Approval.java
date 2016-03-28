/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "approval", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Approval.findAll", query = "SELECT a FROM Approval a"),
    @NamedQuery(name = "Approval.findByApprovalId", query = "SELECT a FROM Approval a WHERE a.approvalId = :approvalId"),
    @NamedQuery(name = "Approval.findByApprovalStatus", query = "SELECT a FROM Approval a WHERE a.approvalStatus = :approvalStatus")})
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "approval_id")
    private Integer approvalId;
    @Column(name = "approval_status")
    private String approvalStatus;
    @OneToMany(mappedBy = "approvalId")
    private List<Review> reviewList;

    public Approval() {
    }

    public Approval(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (approvalId != null ? approvalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Approval)) {
            return false;
        }
        Approval other = (Approval) object;
        if ((this.approvalId == null && other.approvalId != null) || (this.approvalId != null && !this.approvalId.equals(other.approvalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return approvalStatus;
    }
    
}
