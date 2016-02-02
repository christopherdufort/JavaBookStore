/*
 * 
 */
package com.g3w16.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Rita Lazaar
 * @author Christopher Dufort
 * @version 0.0.4 - Last modified 2/2/2016
 * @since 0.0.1
 */
public class InvoiceBean {

    private int SaleNumber;
    private LocalDateTime SaleDate;
    private int ClientNumber;
    private double TotalNetValueOfSale;
    private double PST;
    private double GST;
    private double HST;
    private double TotalGrossValueOfSale;
    private ArrayList<InvoiceDetailBean> details;

    public InvoiceBean() {

    }

    public InvoiceBean(final int SaleNumber, final LocalDateTime SaleDate, final int ClientNumber,final double TotalNetValueOfSale,final double PST,final double GST, final double HST,final double TotalGrossValueOfSale) {
        this.SaleNumber = SaleNumber;
        this.SaleDate = SaleDate;
        this.ClientNumber = ClientNumber;
        this.TotalNetValueOfSale = TotalNetValueOfSale;
        this.PST = PST;
        this.GST = GST;
        this.HST = HST;
        this.TotalGrossValueOfSale = TotalGrossValueOfSale;
    }

    public final int getSaleNumber() {
        return SaleNumber;
    }

    public void setSaleNumber(final int SaleNumber) {
        this.SaleNumber = SaleNumber;
    }

    public final LocalDateTime getSaleDate() {
        return SaleDate;
    }

    public void setSaleDate(final LocalDateTime SaleDate) {
        this.SaleDate = SaleDate;
    }

    public final int getClientNumber() {
        return ClientNumber;
    }

    public void setClientNumber(final int ClientNumber) {
        this.ClientNumber = ClientNumber;
    }

    public final double getTotalNetValueOfSale() {
        return TotalNetValueOfSale;
    }

    public void setTotalNetValueOfSale(final double TotalNetValueOfSale) {
        this.TotalNetValueOfSale = TotalNetValueOfSale;
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

    public final double getTotalGrossValueOfSale() {
        return TotalGrossValueOfSale;
    }

    public void setTotalGrossValueOfSale(final double TotalGrossValueOfSale) {
        this.TotalGrossValueOfSale = TotalGrossValueOfSale;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.SaleNumber;
        hash = 37 * hash + Objects.hashCode(this.SaleDate);
        hash = 37 * hash + this.ClientNumber;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.TotalNetValueOfSale) ^ (Double.doubleToLongBits(this.TotalNetValueOfSale) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.PST) ^ (Double.doubleToLongBits(this.PST) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.GST) ^ (Double.doubleToLongBits(this.GST) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.HST) ^ (Double.doubleToLongBits(this.HST) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.TotalGrossValueOfSale) ^ (Double.doubleToLongBits(this.TotalGrossValueOfSale) >>> 32));
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
        final InvoiceBean other = (InvoiceBean) obj;
        if (this.SaleNumber != other.SaleNumber) {
            return false;
        }
        if (this.ClientNumber != other.ClientNumber) {
            return false;
        }
        if (Double.doubleToLongBits(this.TotalNetValueOfSale) != Double.doubleToLongBits(other.TotalNetValueOfSale)) {
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
        if (Double.doubleToLongBits(this.TotalGrossValueOfSale) != Double.doubleToLongBits(other.TotalGrossValueOfSale)) {
            return false;
        }
        if (!Objects.equals(this.SaleDate, other.SaleDate)) {
            return false;
        }
        return true;
    }

    
    
}
