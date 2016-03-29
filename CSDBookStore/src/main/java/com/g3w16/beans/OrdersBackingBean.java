/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1040570
 */
@Named
@RequestScoped
public class OrdersBackingBean {
    
    @Inject
    private AuthenticatedUser authenticatedUser;
    
    @Inject
    private BookBackingBean ordersBookBackingBean;
    
    private LocalDate saleDate;
    private String clientName;
    private BigDecimal pst;
    private BigDecimal gst;
    private BigDecimal qst;
    private BigDecimal netTotal;
    private BigDecimal grossTotal;
    private BigDecimal bookPrice;
    private String isbn;
    private String title;
    private String author;
    private int quantity;

    public OrdersBackingBean(){
        super();
    }
    public BookBackingBean getOrdersBookBackingBean() {
        return ordersBookBackingBean;
    }

    public void setOrdersBookBackingBean(BookBackingBean ordersBookBackingBean) {
        this.ordersBookBackingBean = ordersBookBackingBean;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public BigDecimal getQst() {
        return qst;
    }

    public void setQst(BigDecimal qst) {
        this.qst = qst;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
