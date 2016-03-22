/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Author;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.Format;
import com.g3w16.entities.Genre;
import com.g3w16.entities.Review;
import com.g3w16.entities.ReviewJpaController;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for book.xhtml
 * @author Giuseppe Campanelli
 */
@Named("bookBB")
@RequestScoped
public class BookBackingBean implements Serializable {
    
    private Book book;
    private Review review;
    private Genre genre;
    
    @Inject
    private BookJpaController bookJpaController;
    @Inject
    private ReviewJpaController reviewJpaController;
    
    public Book getBook() {
        if (book == null)
            book = new Book();
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public Review getReview() {
        if (review == null)
            review = new Review();
        return review;
    }
    
    public void setReview(Review review) {
        this.review = review;
    }
    
    public void createReview() {
        
    }
    
    public int getStarsForOverallRating() {
        return book.getOverallRating().intValue(); //fix overall rating --> update it when a review is added to a book since it is a field in the db
    }
    
    public int getReviewsAmount() {
        return book.getReviewList().size();
    }
    
    public String getAuthors() {
        List<Author> authorsList = book.getAuthorList();
        String authors = authorsList.get(0).getAuthorName();
        for (int i = 1; i < authorsList.size(); i++) {
            authors += ", " + authorsList.get(i).getAuthorName();
        }
        return authors;
    }
    
    public String getGenres() {
        List<Genre> genresList = book.getGenreList();
        String genres = genresList.get(0).getGenreName();
        for (int i = 1; i < genresList.size(); i++) {
            genres += ", " + genresList.get(i).getGenreName();
        }
        return genres;
    }
    
    public String getFormats() {
        List<Format> formatsList = book.getFormatList();
        String formats = formatsList.get(0).getExtension();
        for (int i = 1; i < formatsList.size(); i++) {
            formats += ", " + formatsList.get(i).getExtension();
        }
        return formats;
    }
    
    public String formatDate(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(date);
    }
    
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    
    public Genre getGenre() {
        if (genre == null) {
            genre = book.getGenreList().get(0);
        }
        return genre;
    }
    
    public String getGenreName() {
        return getGenre().getGenreName();
    }
    
    public List<Book> getSimilarProducts() {
        return bookJpaController.findBookEntitiesByGenre(getGenre(), 6, 0); //add in all book images
    }
    
    public String displayBook(Book book) {
        setBook(book);
        return "book";
    }
    
}
