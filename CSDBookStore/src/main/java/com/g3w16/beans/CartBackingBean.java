package com.g3w16.beans;

import com.g3w16.entities.Author;
import com.g3w16.entities.Book;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for cart.xhtml
 * Maintains the items in the cart
 * 
 * @author Giuseppe Campanelli
 */
@Named("cartBB")
@SessionScoped
public class CartBackingBean implements Serializable {
    
    @Inject
    private CheckoutBackingBean checkoutBB;
    
    private List<Book> cart = new ArrayList<Book>();
    
    /**
     * Adds a book to the cart
     * 
     * @param book book to be added to the cart
     * 
     * @throws IOException 
     */
    public String addToCart(Book book) throws IOException {
        if (!cart.contains(book))
            cart.add(book);
        
        return "cart";
    }
    
    /**
     * Removes a book from the cart
     * 
     * @param book book to be removed
     */
    public void removeFromCart(Book book) {
        cart.remove(book);
    }
    
    /**
     * Gets the cart
     * 
     * @return cart
     */
    public List<Book> getCart() {
        return cart;
    }
    
    /**
     * Empties the cart.
     */
    public void clearCart() {
        cart = new ArrayList<Book>();
    }
    
    /**
     * Gets the subtotal of the cart
     * 
     * @return subtotal
     */
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        
        for (int i = 0; i < cart.size(); i++) {
            total = total.add(cart.get(i).getSalePrice());
        }
        return total;
    }
    
    /**
     * Gets the authors of a book
     * 
     * @param book book to get authors of
     * 
     * @return comma seperated string of authors
     */
    public String getAuthors(Book book) {
        List<Author> authorsList = book.getAuthorList();
        String authors = authorsList.get(0).getAuthorName();
        for (int i = 1; i < authorsList.size(); i++) {
            authors += ", " + authorsList.get(i).getAuthorName();
        }
        return authors;
    }
    
    /**
     * Checks out a cart to be purchased
     * 
     * @throws IOException 
     */
    public String checkOut() throws IOException {
        checkoutBB.setOrder(cart);
        checkoutBB.setSubtotal(getTotal());
        
        return "checkout";
    }
}