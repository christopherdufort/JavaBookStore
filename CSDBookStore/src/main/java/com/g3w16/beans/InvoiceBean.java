/*
 * 
 */
package com.g3w16.beans;

import java.time.LocalDateTime;

/**
 *
 * @author Rita Lazaar
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

    public InvoiceBean() {

    }

    public InvoiceBean(int SaleNumber, LocalDateTime SaleDate, int ClientNumber, double TotalNetValueOfSale, double PST, double GST, double HST, double TotalGrossValueOfSale) {
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

}
