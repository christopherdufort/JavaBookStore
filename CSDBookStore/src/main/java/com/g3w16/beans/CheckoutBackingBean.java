/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for cart.xhtml
 * @author Giuseppe Campanelli
 */
@Named("checkoutBB")
@SessionScoped
public class CheckoutBackingBean implements Serializable {
    
    private List<Book> order = new ArrayList<Book>();
    private BigDecimal subtotal;
    private BigDecimal governmentTax = new BigDecimal(0);
    private BigDecimal provincialTax = new BigDecimal(0);
    private BigDecimal total = new BigDecimal(0);
    
    @Inject
    private AuthenticatedUser user;
    
    public void setOrder(List<Book> order) {
        this.order = order;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public List<Book> getOrder() {
        return order;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public BigDecimal calculateGst(BigDecimal gst) {
        governmentTax = (subtotal.multiply(gst)).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
        return governmentTax;
    }
    
    public BigDecimal calculateProvincialTax(BigDecimal provincialTax) {
        this.provincialTax = (subtotal.multiply(provincialTax)).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
        return this.provincialTax;
    }
    
    public BigDecimal calcTotal() {
        total = subtotal.add(governmentTax).add(provincialTax).setScale(2, BigDecimal.ROUND_UP);
        return total;
    }
}
