/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rita Lazaar
 */
@Entity
@Table(name = "invoice", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvoiceId", query = "SELECT i FROM Invoice i WHERE i.invoiceId = :invoiceId"),
    @NamedQuery(name = "Invoice.findBySaleDate", query = "SELECT i FROM Invoice i WHERE i.saleDate = :saleDate"),
    @NamedQuery(name = "Invoice.findByUserNumber", query = "SELECT i FROM Invoice i WHERE i.userNumber = :userNumber"),
    @NamedQuery(name = "Invoice.findByTotalNetValueOfSale", query = "SELECT i FROM Invoice i WHERE i.totalNetValueOfSale = :totalNetValueOfSale"),
    @NamedQuery(name = "Invoice.findByTotalGrossValueOfSale", query = "SELECT i FROM Invoice i WHERE i.totalGrossValueOfSale = :totalGrossValueOfSale")})

public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_id")
    private Integer invoiceId;
    @Column(name = "sale_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saleDate;
    @Basic(optional = false)
    @Column(name = "user_number")
    private int userNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_net_value_of_sale")
    private BigDecimal totalNetValueOfSale;
    @Column(name = "total_gross_value_of_sale")
    private BigDecimal totalGrossValueOfSale;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceId")
    private List<InvoiceDetail> invoiceDetailList;

    public Invoice() {
    }

    public Invoice(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Invoice(Integer invoiceId, int userNumber) {
        this.invoiceId = invoiceId;
        this.userNumber = userNumber;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public BigDecimal getTotalNetValueOfSale() {
        return totalNetValueOfSale;
    }

    public void setTotalNetValueOfSale(BigDecimal totalNetValueOfSale) {
        this.totalNetValueOfSale = totalNetValueOfSale;
    }

    public BigDecimal getTotalGrossValueOfSale() {
        return totalGrossValueOfSale;
    }

    public void setTotalGrossValueOfSale(BigDecimal totalGrossValueOfSale) {
        this.totalGrossValueOfSale = totalGrossValueOfSale;
    }

    public List<InvoiceDetail> getInvoiceDetailList() {
        return invoiceDetailList;
    }

    public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceId != null ? invoiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiceId == null && other.invoiceId != null) || (this.invoiceId != null && !this.invoiceId.equals(other.invoiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Invoice[ invoiceId=" + invoiceId + " ]";
    }
    
}
