package com.g3w16.actionController;

import com.g3w16.beans.SearchBackingBean;
import com.g3w16.entities.Author;
import com.g3w16.entities.AuthorJpaController;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.Genre;
import com.g3w16.entities.InvoiceDetailJpaController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher Dufort
 * @author Jonas Faure
 *
 * This is the primary layer of abstraction between the view and model layer.
 * This controller is responsible for transfering data between Backing Beans and
 * Entity beans are persisted to the database using JPA Controllers.
 *
 */
@Named
@RequestScoped
public class BookController {

    @Inject
    BookJpaController bookJpaController;

    @Inject
    InvoiceDetailJpaController invoiceDetailJpacontroller;

    @Inject
    AuthorJpaController authorJpaController;

    @Inject
    SearchBackingBean searchBackingBean;
    
    /**
     * Returns a list of books based on the title searched by the user.
     * @author Christopher Dufort
     * @param title 
     *          Entered string to search for by the user.
     * @return List of book Results.
     */
    public List<Book> searchByTitle(String title) {
        return bookJpaController.findBookEntitiesByTitleLikeAsClient(title);
    }
    
    /**
     * Returns a list of books based on the isbn searched for by the user.
     * @author Christopher Dufort
     * @param isbn
     *          Entered string to search for by the user.
     * @return List of book Results.
     */
    public List<Book> searchByIsbn(String isbn) {
        return bookJpaController.findBookEntitiesByISBNAsClient(isbn);
    }
    
    /**
     * Returns a list of books based on the author searched for by the user.
     * @author Christopher Dufort
     * @param author
     *          Entered string to search for by the user.
     * @return List of book Results.
     */
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<Book>();
        List<Author> authorObjects = authorJpaController.findAuthorEntitiesLikeAsClient(author);

        for (int i = 0; i < authorObjects.size(); i++) {
            results.addAll(bookJpaController.findBookEntitiesByAuthorAsClient(authorObjects.get(i)));
        }
        return results;
    }
    
   /**
     * Returns a list of books based on the publisher searched for by the user.
     * @author Christopher Dufort
     * @param publisher
     *          Entered string to search for by the user.
     * @return List of book Results.
     */
    public List<Book> searchByPublisher(String publisher) {
        return bookJpaController.findBookEntitiesByPublisherLikeAsClient(publisher);
    }
    /**
     * Returns a list of books based on the genre selected by the user.
     * @author Christopher Dufort
     * @param genre
     *          Entered genre object to search for by the user.
     * @return List of book Results.
     */
    public List<Book> browseByGenre(Genre genre) {
        return bookJpaController.findBookEntitiesByGenreAsClient(genre);
    }
    
    public List<Book> getNewestBook(int limit) {
        return bookJpaController.findNewBook(limit);
    }

    public List<Book> getDiscountedBook(int limit) {
        List<Book> books = bookJpaController.findDiscountedBook();
        Collections.shuffle(books);
        return books.subList(0, limit);
    }

    public List<Book> getSuggestedBook(Genre genre, int limit) {
        return bookJpaController.findBookEntitiesByGenre(genre, limit, 0);
    }

    public List<Book> getRandomBook(int limit) {
        return bookJpaController.findBookEntitiesAsClient(limit, 12);
    }

}
