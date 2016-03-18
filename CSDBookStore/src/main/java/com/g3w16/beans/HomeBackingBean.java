/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.AuthorJpaController;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.FormatJpaController;
import com.g3w16.entities.GenreJpaController;
import com.g3w16.entities.Province;
import com.g3w16.entities.Review;
import com.g3w16.entities.ReviewJpaController;
import com.g3w16.entities.Title;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for book.xhtml
 * @author Giuseppe Campanelli
 */
@Named("homeBB")
@RequestScoped
public class HomeBackingBean implements Serializable {
    
    @Inject
    private BookBackingBean bookBB;
    @Inject
    private BookJpaController bookJpaController;
    
    public String displayBook(int id) {
        bookBB.setBook(bookJpaController.findBookEntitiesById(id));
        return "book";
    }
    
    public List<Book> getSimilarProducts() {
        return bookJpaController.findBookEntitiesAsClient(4, 0);
    }
    
    public List<Book> getSimilarProducts2() {
        return bookJpaController.findBookEntitiesAsClient(6, 0);
    }
    
    public List<Book> getSimilarProducts3() {
        return bookJpaController.findBookEntitiesAsClient(3, 0);
    }
}
