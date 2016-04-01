/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.BookController;
import com.g3w16.actionController.GenreController;
import com.g3w16.beans.AuthenticatedUser;
import com.g3w16.entities.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

    @Inject
    GenreController genreController;

    public List<Book> getNewestBook() {
        int limit = 4;
        return bookController.getNewestBook(limit);
    }

    public List<Book> getDiscountedBook() {
        int limit = 6;
        return bookController.getDiscountedBook(limit);
    }

    /**
     * check co whether is null otherwise run on a new computer (no cookies)
     * always nullpointerException
     *
     * edit by Xin Ma
     */
    public List<Book> getSuggestedBook() {
        int limit = 15;
        List<Book> toBeReturnBooks;

        Cookie[] co = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getCookies();
        if (co != null) {
            List<Cookie> cookies = Arrays.asList(co);
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("lastGenreId")) {
                        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Using ''lastGenreId'' cookie ( {0} )", c.getValue());
                        return bookController.getSuggestedBook(genreController.getById(Integer.parseInt(c.getValue())), limit);
                    }
                }
            }
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "No 'lastGenreId' cookie found");
        return bookController.getRandomBook(limit);

    }
}
