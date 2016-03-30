/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Book;
import java.math.BigDecimal;

/**
 *
 * @author Christopher
 */
public class OrderDetailBackingBean {
    
    private Book book;
    private BigDecimal pst;
    private BigDecimal gst;
    private BigDecimal hst;
    private BigDecimal bookPrice;

    public OrderDetailBackingBean(Book book, BigDecimal pst, BigDecimal gst, BigDecimal hst, BigDecimal bookPrice) {
        this.book = book;
        this.pst = pst;
        this.gst = gst;
        this.hst = hst;
        this.bookPrice = bookPrice;
    }

    public OrderDetailBackingBean() {
        super();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
    
}
