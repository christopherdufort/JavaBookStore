/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Province Entity for a selected country.
 * 
 * @author Giuseppe Campanelli
 */
@Entity
@Table(name = "province", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT object(p) FROM Province p"),
    @NamedQuery(name = "Province.findByProvince", query = "SELECT object(p) FROM Province p WHERE p.province = :province")})
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "province_id")
    private Integer provinceId;
    @Basic(optional = false)
    @Column(name = "province")
    private String province;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pst")
    private BigDecimal pst;
    @Column(name = "gst")
    private BigDecimal gst;
    @Column(name = "hst")
    private BigDecimal hst;
    @OneToMany(mappedBy = "provinceId")
    private List<RegisteredUser> registeredUserList;

    /**
     * Default constructor
     */
    public Province() {
    }

    /**
     * One parameter constructor.
     * 
     * @param provinceId Id of the province
     */
    public Province(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * Two parameter constructor.
     * 
     * @param provinceId Id of the province
     * @param province Name of the province
     */
    public Province(Integer provinceId, String province) {
        this.provinceId = provinceId;
        this.province = province;
    }

    /**
     * Gets the province id.
     * 
     * @return province id
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    /**
     * Sets the province id.
     * 
     * @param provinceId Id of the province
     */
    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * Gets the province name.
     * 
     * @return province name
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the name of the province.
     * 
     * @param province Name of the province.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Gets the pst of the province.
     * 
     * @return pst
     */
    public BigDecimal getPst() {
        return pst;
    }

    /**
     * Sets the pst of the province.
     * 
     * @param pst pst tax
     */
    public void setPst(BigDecimal pst) {
        this.pst = pst;
    }

    /**
     * Gets the gst of the province.
     * 
     * @return gst
     */
    public BigDecimal getGst() {
        return gst;
    }

    /**
     * Sets the gst of the province.
     * 
     * @param gst gst tax
     */
    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    /**
     * Gets the hst of the province.
     * 
     * @return hst
     */
    public BigDecimal getHst() {
        return hst;
    }

    /**
     * Sets the hst of the province.
     * 
     * @param hst hst tax
     */
    public void setHst(BigDecimal hst) {
        this.hst = hst;
    }

    /**
     * Gets a list of all users associated with that province.
     * 
     * @return list of users
     */
    public List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    /**
     * Sets the list of all users associate with that province.
     * 
     * @param registeredUserList List of users
     */
    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provinceId != null ? provinceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
        if ((this.provinceId == null && other.provinceId != null) || (this.provinceId != null && !this.provinceId.equals(other.provinceId))) {
            return false;
        }
        return true;
    }
    /**
     * Modified to return just the province name so that it could be used in form.
     * @author Giuseppe Campanelli
     * @author Christopher Dufort
     * @return 
     */
    @Override
    public String toString() {
//        return "com.g3w16.entities.Province[ provinceId=" + provinceId + " ]";
        return province;
    }
}