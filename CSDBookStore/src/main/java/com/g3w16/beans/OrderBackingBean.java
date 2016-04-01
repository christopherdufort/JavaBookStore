/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 1040570
 */
@Named
@RequestScoped
public class OrderBackingBean {
     
    @Inject
    private BookBackingBean ordersBookBackingBean;
    
    private int orderNumber;
    private LocalDate saleDate;
    private String clientName;
    private BigDecimal totalPst;
    private BigDecimal totalGst;
    private BigDecimal totalHst;
    private BigDecimal netTotal;
    private BigDecimal grossTotal;
    private List<OrderDetailBackingBean> detailsForThisOrder;

    public OrderBackingBean(){
        super();
    }
    @PostConstruct
    public void init(){
        
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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

    public BookBackingBean getOrdersBookBackingBean() {
        return ordersBookBackingBean;
    }

    public void setOrdersBookBackingBean(BookBackingBean ordersBookBackingBean) {
        this.ordersBookBackingBean = ordersBookBackingBean;
    }

    public BigDecimal getTotalPst() {
        return totalPst;
    }

    public void setTotalPst(BigDecimal totalPst) {
        this.totalPst = totalPst;
    }

    public BigDecimal getTotalGst() {
        return totalGst;
    }

    public void setTotalGst(BigDecimal totalGst) {
        this.totalGst = totalGst;
    }

    public BigDecimal getTotalHst() {
        return totalHst;
    }

    public void setTotalHst(BigDecimal totalHst) {
        this.totalHst = totalHst;
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

    public List<OrderDetailBackingBean> getDetailsForThisOrder() {
        return detailsForThisOrder;
    }

    public void setDetailsForThisOrder(List<OrderDetailBackingBean> detailsForThisOrder) {
        this.detailsForThisOrder = detailsForThisOrder;
    }
    
    

    
    
    
}
