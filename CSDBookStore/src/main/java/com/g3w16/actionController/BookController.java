/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.beans.SearchBackingBean;
import com.g3w16.entities.Author;
import com.g3w16.entities.AuthorJpaController;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.Genre;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class BookController {

    @Inject
    BookJpaController bookJpaController;
    
    @Inject
    AuthorJpaController authorJpaController;

    @Inject
    SearchBackingBean searchBackingBean;

    public List<Book> searchByTitle(String title) {
        return bookJpaController.findBookEntitiesByTitleLikeAsClient(title);
    }

    public List<Book> searchByIsbn(String isbn) {
        return bookJpaController.findBookEntitiesByISBNAsClient(isbn);
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<Book>();
        List<Author> authorObjects = authorJpaController.findAuthorEntitiesLikeAsClient(author); 
        
        for (int i = 0; i < authorObjects.size(); i++) {
            results.addAll(bookJpaController.findBookEntitiesByAuthorAsClient(authorObjects.get(i)));
        }
        return results;
    }

    public List<Book> searchByPublisher(String publisher) {
        return bookJpaController.findBookEntitiesByPublisherLikeAsClient(publisher);
    }
    
    public List<Book> browseByGenre(Genre genre){
        return bookJpaController.findBookEntitiesByGenreAsClient(genre);
    }
    
    public List<Book> getNewestBook(int limit){
        return bookJpaController.findNewBook(limit);
    }

    public List<Book> getDiscountedBook(int limit) {
        List<Book> books = bookJpaController.findDiscountedBook();
        Collections.shuffle(books);
        return books.subList(0, limit);
    }

    public List<Book> getSuggestedBook(Genre genre, int limit) {
        return bookJpaController.findBookEntitiesByGenre(genre,limit,0);
    }

    public List<Book> getRandomBook(int limit) {
        return bookJpaController.findBookEntitiesAsClient(limit, 12);
    }
}
