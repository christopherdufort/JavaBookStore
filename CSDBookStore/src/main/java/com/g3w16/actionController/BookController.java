/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.beans.SearchBackingBean;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Christopher
 */
@Named
@RequestScoped
public class BookController {

    @Inject
    BookJpaController bookJpaController;

    @Inject
    SearchBackingBean searchBackingBean;

    public List<Book> searchByTitle(String title) {
        return bookJpaController.findBookEntitiesByTitleLikeAsClient(title);
    }

    public List<Book> searchByIsbn(String isbn) {
        return bookJpaController.findBookEntitiesByISBNAsClient(isbn);
    }

    public List<Book> searchByAuthor(String author) {
        throw new NotImplementedException();
    }

    public List<Book> searchByPublisher(String publisher) {
        throw new NotImplementedException();
    }
}
