/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.util.Objects;

/**
 * @author Rita Lazaar
 * @author Christopher Dufort
 * @version 0.0.5 - Last modified 2/3/2016
 * @since 0.0.1
 */
public class InvoiceDetailBean {
    
  private int InvoiceDetailId;
  private int SaleNumber;
  private String ISBN;
  private double BookPrice;
  private double PST;
  private double GST;
  private double HST;

    public InvoiceDetailBean(){
        
    }
    public InvoiceDetailBean(final int InvoiceDetailId, final int SaleNumber, final String ISBN, final double BookPrice, final double PST,final double GST, final double HST) {
       
        this.InvoiceDetailId = InvoiceDetailId;
        this.SaleNumber = SaleNumber;
        this.ISBN = ISBN;
        this.BookPrice = BookPrice;
        this.PST = PST;
        this.GST = GST;
        this.HST = HST;
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
    
    public final double getPST() {
        return PST;
    }

    public void setPST(final double PST) {
        this.PST = PST;
    }

    public final double getGST() {
        return GST;
    }

    public void setGST(final double GST) {
        this.GST = GST;
    }

    public final double getHST() {
        return HST;
    }

    public void setHST(final double HST) {
        this.HST = HST;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.InvoiceDetailId;
        hash = 59 * hash + this.SaleNumber;
        hash = 59 * hash + Objects.hashCode(this.ISBN);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.BookPrice) ^ (Double.doubleToLongBits(this.BookPrice) >>> 32));
       
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.PST) ^ (Double.doubleToLongBits(this.PST) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.GST) ^ (Double.doubleToLongBits(this.GST) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.HST) ^ (Double.doubleToLongBits(this.HST) >>> 32));
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
       
         if (Double.doubleToLongBits(this.PST) != Double.doubleToLongBits(other.PST)) {
            return false;
        }
        if (Double.doubleToLongBits(this.GST) != Double.doubleToLongBits(other.GST)) {
            return false;
        }
        if (Double.doubleToLongBits(this.HST) != Double.doubleToLongBits(other.HST)) {
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
