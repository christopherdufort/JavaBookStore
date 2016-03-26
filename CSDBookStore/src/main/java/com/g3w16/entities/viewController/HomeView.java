/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.BookController;
import com.g3w16.beans.AuthenticatedUser;
import com.g3w16.entities.Book;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Giuseppe Campanelli
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class HomeView {
    @Inject
    AuthenticatedUser authenticatedUser;
    
    @Inject
    BookController bookController;
    
    public List<Book> getNewestBook(){
        int limit = 4;
        return bookController.getNewestBook(limit);
    }
    
    public List<Book> getBestRankedBook(){
        int limit = 6;
        return bookController.getBestRankedBook(limit);
    }
    
    public List<Book> getSuggestedBook(){
        int limit = 3;
        if (authenticatedUser.getLast_genre()==null){
            return bookController.getRandomBook(limit);
        }
        return bookController.getSuggestedBook(authenticatedUser.getLast_genre(), limit);
    }
}
