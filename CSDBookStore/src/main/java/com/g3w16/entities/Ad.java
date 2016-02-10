/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "ad", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Ad.findAll", query = "SELECT a FROM Ad a"),
    @NamedQuery(name = "Ad.findByAdId", query = "SELECT a FROM Ad a WHERE a.adId = :adId"),
    @NamedQuery(name = "Ad.findByAdFilename", query = "SELECT a FROM Ad a WHERE a.adFilename = :adFilename")})
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ad_id")
    private Integer adId;
    @Basic(optional = false)
    @Column(name = "ad_filename")
    private String adFilename;

    public Ad() {
    }

    public Ad(Integer adId) {
        this.adId = adId;
    }

    public Ad(Integer adId, String adFilename) {
        this.adId = adId;
        this.adFilename = adFilename;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getAdFilename() {
        return adFilename;
    }

    public void setAdFilename(String adFilename) {
        this.adFilename = adFilename;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adId != null ? adId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ad)) {
            return false;
        }
        Ad other = (Ad) object;
        if ((this.adId == null && other.adId != null) || (this.adId != null && !this.adId.equals(other.adId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Ad[ adId=" + adId + " ]";
    }
    
}
