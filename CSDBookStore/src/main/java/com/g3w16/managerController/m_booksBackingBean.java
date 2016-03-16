/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_books")
@RequestScoped
public class m_booksBackingBean {
    
    @Inject
    Book book;
    
    @Inject
    BookJpaController bookJpa;

    public String preCreateBook() {
        return "m_createBook";
    }

    public Book getSelectedBook() {
        return book;
    }

    public String createBook() {
        try {
            bookJpa.create(book);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_books";
    }

    public String editBook(Book b) {
        book =bookJpa.findBookEntitiesById(b.getBookId());
        return "m_editBook";
    }

    public String updateBook() {
        try {
            bookJpa.edit(book);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_books";
    }

    public String destroyBook(Book b) {
        Book current = bookJpa.findBookEntitiesById(b.getBookId());
        if (current != null) {
            try {
                bookJpa.destroy(b.getBookId());
            } catch (RollbackFailureException ex) {
                Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "m_books";
    }
    
    public List<Book> getAllBook() {
        return bookJpa.findBookEntities();
    }
    
    public int getBookCount(){
        return bookJpa.getBookCount();
    }
}
