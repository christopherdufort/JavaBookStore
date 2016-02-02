/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.InvoiceDetailId;
        hash = 59 * hash + this.SaleNumber;
        hash = 59 * hash + Objects.hashCode(this.ISBN);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.BookPrice) ^ (Double.doubleToLongBits(this.BookPrice) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceDetailBean other = (InvoiceDetailBean) obj;
        if (this.InvoiceDetailId != other.InvoiceDetailId) {
            return false;
        }
        if (this.SaleNumber != other.SaleNumber) {
            return false;
        }
        if (Double.doubleToLongBits(this.BookPrice) != Double.doubleToLongBits(other.BookPrice)) {
            return false;
        }
        if (!Objects.equals(this.ISBN, other.ISBN)) {
            return false;
        }
        return true;
    }
  
  
    
}
