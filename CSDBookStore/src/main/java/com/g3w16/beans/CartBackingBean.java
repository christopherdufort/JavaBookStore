/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Author;
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
@Named("cartBB")
@SessionScoped
public class CartBackingBean implements Serializable {
    
    @Inject
    private CheckoutBackingBean checkoutBB;
    
    private List<Book> cart = new ArrayList<Book>();
    
    public String addToCart(Book book) {
        if (!cart.contains(book))
            cart.add(book);
        
        return "cart";
    }
    
    public String removeFromCart(Book book) {
        cart.remove(book);
        
        return "cart";
    }
    
    public List<Book> getCart() {
        return cart;
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        
        for (int i = 0; i < cart.size(); i++) {
            total = total.add(cart.get(i).getSalePrice());
        }
        return total;
    }
    
    public String getAuthors(Book book) {
        List<Author> authorsList = book.getAuthorList();
        String authors = authorsList.get(0).getAuthorName();
        for (int i = 1; i < authorsList.size(); i++) {
            authors += ", " + authorsList.get(i).getAuthorName();
        }
        return authors;
    }
    
    public String checkOut() {
        checkoutBB.setOrder(cart);
        checkoutBB.setSubtotal(getTotal());
        
        return "checkout";
    }
    
    //FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("fr"));;
}