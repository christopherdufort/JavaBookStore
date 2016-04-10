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
 * Backing bean used to keep track and display orders of the currently logged in user
 * @author Christopher Dufort
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

    /**
     * Constructor
     */
    public OrderBackingBean(){
        super();
    }
    /**
     * Currently not in use
     */
    @PostConstruct
    public void init(){
        
    }
    
    /**
     * Getter
     * @return 
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Setter
     * @param orderNumber 
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * getter
     * @return 
     */
    public LocalDate getSaleDate() {
        return saleDate;
    }

    /**
     * setter
     * @param saleDate 
     */
    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
    
    /**
     * getter
     * @return 
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * setter
     * @param clientName 
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * getter
     * @return 
     */
    public BookBackingBean getOrdersBookBackingBean() {
        return ordersBookBackingBean;
    }

    /**
     * setter
     * @param ordersBookBackingBean 
     */
    public void setOrdersBookBackingBean(BookBackingBean ordersBookBackingBean) {
        this.ordersBookBackingBean = ordersBookBackingBean;
    }

    /**
     * getter
     * @return 
     */
    public BigDecimal getTotalPst() {
        return totalPst;
    }

    /**
     * setter
     * @param totalPst 
     */
    public void setTotalPst(BigDecimal totalPst) {
        this.totalPst = totalPst;
    }

    /**
     * getter
     * @return 
     */
    public BigDecimal getTotalGst() {
        return totalGst;
    }

    /**
     * setter
     * @param totalGst 
     */
    public void setTotalGst(BigDecimal totalGst) {
        this.totalGst = totalGst;
    }

    /**
     * getter
     * @return 
     */
    public BigDecimal getTotalHst() {
        return totalHst;
    }

    /**
     * setter
     * @param totalHst 
     */
    public void setTotalHst(BigDecimal totalHst) {
        this.totalHst = totalHst;
    }

    /**
     * getter
     * @return 
     */
    public BigDecimal getNetTotal() {
        return netTotal;
    }

    /**
     * setter
     * @param netTotal 
     */
    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    /**
     * getter
     * @return 
     */
    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    /**
     * setter
     * @param grossTotal 
     */
    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }

    /**
     * getter
     * @return 
     */
    public List<OrderDetailBackingBean> getDetailsForThisOrder() {
        return detailsForThisOrder;
    }

    /**
     * setter
     * @param detailsForThisOrder 
     */
    public void setDetailsForThisOrder(List<OrderDetailBackingBean> detailsForThisOrder) {
        this.detailsForThisOrder = detailsForThisOrder;
    }
    
    

    
    
    
}
