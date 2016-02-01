/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

/**
 *
 * @author rpro
 */
public class InvoiceDetailBean {
    
  private int InvoiceDetailId;
  private int SaleNumber;
  private String ISBN;
  private double BookPrice;

    public InvoiceDetailBean(int InvoiceDetailId, int SaleNumber, String ISBN, double BookPrice) {
        this.InvoiceDetailId = InvoiceDetailId;
        this.SaleNumber = SaleNumber;
        this.ISBN = ISBN;
        this.BookPrice = BookPrice;
    }

    public final int getInvoiceDetailId() {
        return InvoiceDetailId;
    }

    public void setInvoiceDetailId(final int InvoiceDetailId) {
        this.InvoiceDetailId = InvoiceDetailId;
    }

    public final int getSaleNumber() {
        return SaleNumber;
    }

    public void setSaleNumber(final int SaleNumber) {
        this.SaleNumber = SaleNumber;
    }

    public final String getISBN() {
        return ISBN;
    }

    public void setISBN(final String ISBN) {
        this.ISBN = ISBN;
    }

    public final double getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(final double BookPrice) {
        this.BookPrice = BookPrice;
    }
  
  
    
}
