package com.g3w16.beans;

import com.g3w16.entities.ApprovalJpaController;
import com.g3w16.entities.Author;
import com.g3w16.entities.Book;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.Format;
import com.g3w16.entities.Genre;
import com.g3w16.entities.Review;
import com.g3w16.entities.ReviewJpaController;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * backing bean for book.xhtml
 * Manages a books, its reviews, and similar products.
 * 
 * @author Giuseppe Campanelli
 */
@Named("bookBB")
@SessionScoped
public class BookBackingBean implements Serializable {
    
    private Book book;
    private Review review;
    private int rating = 1;
    private List<Book> booksFromGenre;
    private List<Book> booksFromAuthor;
    
    @Inject
    private BookJpaController bookJpaController;
    @Inject
    private ReviewJpaController reviewJpaController;
    @Inject
    private ApprovalJpaController approvalJpaController;
    @Inject
    private AuthenticatedUser authenticatedUser;
    
    /**
     * Gets the current book.
     * 
     * @return current book
     */
    public Book getBook() {
        if (book == null)
            book = new Book();
        return book;
    }
    
    /**
     * Sets the current book.
     * 
     * @param book book to set
     */
    public void setBook(Book book) {
        this.book = book;
        setFromSameGenre();
        setFromSameAuthor();
    }
    
    /**
     * Gets the current review.
     * 
     * @return current review
     */
    public Review getReview() {
        if (review == null)
            review = new Review();
        return review;
    }
    
    /**
     * Sets the current review.
     * 
     * @param review review to set
     */
    public void setReview(Review review) {
        this.review = review;
    }
    
    /**
     * Gets the rating
     * 
     * @return rating
     */
    public int getRating() {
        return rating;
    }
    
    /**
     * Sets the rating
     * 
     * @param rating rating to set
     */
    public void setRating (int rating) {
        this.rating = rating;
    }
    
    /**
     * Creates the review by adding it to the database
     * 
     * @throws Exception 
     */
    public void createReview() throws Exception {
        review.setApprovalId(approvalJpaController.findApproval(2));
        review.setDateSubmitted(new Date());
        review.setIsbn(book);
        review.setRating(rating);
        review.setUserId(authenticatedUser.getRegisteredUser());
        reviewJpaController.create(review);
        review = null;
        rating = 1;
    }
    
    /**
     * Gets the overall star rating for a book
     * 
     * @return overall rating
     */
    public int getStarsForOverallRating() {
        return book.getOverallRating().intValue();
    }
    
    /**
     * Gets the amount of reviews for a book
     * 
     * @return amount of reviews
     */
    public int getReviewsAmount() {
        return book.getReviewList().size();
    }
    
    /**
     * Gets the authors of a book
     * 
     * @return comma separated string of authors
     */
    public String getAuthors() {
        List<Author> authorsList = book.getAuthorList();
        String authors = authorsList.get(0).getAuthorName();
        for (int i = 1; i < authorsList.size(); i++) {
            authors += ", " + authorsList.get(i).getAuthorName();
        }
        return authors;
    }
    
    /**
     * Gets the genres of a book
     * 
     * @return comma separated string of genres
     */
    public String getGenres() {
        List<Genre> genresList = book.getGenreList();
        String genres = genresList.get(0).getGenreName();
        for (int i = 1; i < genresList.size(); i++) {
            genres += ", " + genresList.get(i).getGenreName();
        }
        return genres;
    }
    
    /**
     * Gets the formats of a book
     * 
     * @return comma separated string of formats
     */
    public String getFormats() {
        List<Format> formatsList = book.getFormatList();
        String formats = formatsList.get(0).getExtension();
        for (int i = 1; i < formatsList.size(); i++) {
            formats += ", " + formatsList.get(i).getExtension();
        }
        return formats;
    }
    
    /**
     * Formats a date to yyyy-MM-dd
     * 
     * @param date date for format
     * 
     * @return formatted date
     */
    public String formatDate(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(date);
    }
    
    /**
     * Get the genre name of the current book
     * 
     * @return genre name of current book
     */
    public String getGenreName() {
        return book.getGenreList().get(0).getGenreName();
    }
    
    /**
     * Sets similar products of the same genre
     */
    public void setFromSameGenre() {
        booksFromGenre = new ArrayList<>();
        List<Book> allSimilarBooks = bookJpaController.findBookEntitiesByGenre(book.getGenreList().get(0));
        int similarBooksAmount = allSimilarBooks.size();
        
        for (int i = 0; i < 6 && !allSimilarBooks.isEmpty(); i++) {
            int random = (int)(Math.random()* similarBooksAmount);
            if (allSimilarBooks.get(random).getBookId() != book.getBookId()) {
                booksFromGenre.add(allSimilarBooks.get(random));
            } else {
                i--;
            }
            allSimilarBooks.remove(random);
            similarBooksAmount--;
        }
    }
    
    /**
     * Gets similar products from the same genre
     * 
     * @return products from same genre
     */
    public List<Book> getFromSameGenre() {
        return booksFromGenre;
    }
    
    /**
     * Sets similar products of the from the same author
     */
    public void setFromSameAuthor() {
        booksFromAuthor = new ArrayList<>();
        List<Book> allSimilarBooks = bookJpaController.findBookEntitiesByAuthor(book.getAuthorList().get(0));
        int similarBooksAmount = allSimilarBooks.size();
        
        for (int i = 0; i < 6 && !allSimilarBooks.isEmpty(); i++) {
            int random = (int)(Math.random()* similarBooksAmount);
            if (allSimilarBooks.get(random).getBookId() != book.getBookId()) {
                booksFromAuthor.add(allSimilarBooks.get(random));
            } else {
                i--;
            }
            allSimilarBooks.remove(random);
            similarBooksAmount--;
        }
    }
    
    /**
     * Gets products by the same author
     * 
     * @return products of same author
     */
    public List<Book> getFromSameAuthor() {
        return booksFromAuthor;
    }
    
    public String displayBook(Book book) throws IOException {
        setBook(book);
        // -- the following code place a cookie on client side so we can recover the last genre later
        int forever = 7; // TODO: replace this with something else
        Cookie cookie = new Cookie(
                "lastGenreId",
                book.getGenreList().get(0).getGenreId().toString()
        );
        cookie.setMaxAge(forever);
        ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).addCookie(cookie);
        return "book?faces-redirect=true";
    }
}
