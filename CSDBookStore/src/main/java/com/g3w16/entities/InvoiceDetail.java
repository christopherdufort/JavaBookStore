/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "invoice_detail", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "InvoiceDetail.findAll", query = "SELECT i FROM InvoiceDetail i"),
    @NamedQuery(name = "InvoiceDetail.findByInvoiceDetailId", query = "SELECT i FROM InvoiceDetail i WHERE i.invoiceDetailId = :invoiceDetailId"),
    @NamedQuery(name = "InvoiceDetail.findByIsbn", query = "SELECT i FROM InvoiceDetail i WHERE i.isbn = :isbn"),
    @NamedQuery(name = "InvoiceDetail.findByPst", query = "SELECT i FROM InvoiceDetail i WHERE i.pst = :pst"),
    @NamedQuery(name = "InvoiceDetail.findByGst", query = "SELECT i FROM InvoiceDetail i WHERE i.gst = :gst"),
    @NamedQuery(name = "InvoiceDetail.findByHst", query = "SELECT i FROM InvoiceDetail i WHERE i.hst = :hst"),
    @NamedQuery(name = "InvoiceDetail.findByBookPrice", query = "SELECT i FROM InvoiceDetail i WHERE i.bookPrice = :bookPrice"),
    @NamedQuery(name = "InvoiceDetail.findByQuantity", query = "SELECT i FROM InvoiceDetail i WHERE i.quantity = :quantity")})
public class InvoiceDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_detail_id")
    private Integer invoiceDetailId;
    @Basic(optional = false)
    @Column(name = "isbn")
    private String isbn;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pst")
    private BigDecimal pst;
    @Column(name = "gst")
    private BigDecimal gst;
    @Column(name = "hst")
    private BigDecimal hst;
    @Column(name = "book_price")
    private BigDecimal bookPrice;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;

    public InvoiceDetail() {
    }

    public InvoiceDetail(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public InvoiceDetail(Integer invoiceDetailId, String isbn, int quantity) {
        this.invoiceDetailId = invoiceDetailId;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPst() {
        return pst;
    }

    public void setPst(BigDecimal pst) {
        this.pst = pst;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getHst() {
        return hst;
    }

    public void setHst(BigDecimal hst) {
        this.hst = hst;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceDetailId != null ? invoiceDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvoiceDetail)) {
            return false;
        }
        InvoiceDetail other = (InvoiceDetail) object;
        if ((this.invoiceDetailId == null && other.invoiceDetailId != null) || (this.invoiceDetailId != null && !this.invoiceDetailId.equals(other.invoiceDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.InvoiceDetail[ invoiceDetailId=" + invoiceDetailId + " ]";
    }
    
}