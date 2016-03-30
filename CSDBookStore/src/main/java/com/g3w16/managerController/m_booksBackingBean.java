/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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

    private Book book;
    private Author author;
    private List<Author> authorList;
    private List<Format> formatList;
    private Format format;
    private Genre genre;

    @Inject
    BookJpaController bookJpa;

    @Inject
    AuthorJpaController authorJpa;

    @Inject
    FormatJpaController formatJpa;

    @Inject
    GenreJpaController genreJpa;

    @Inject
    ReviewJpaController reviewJpa;

    @Inject
    InvoiceDetailJpaController invoiceJpa;

    public String preCreateBook() {

        //book = new Book();
        return "m_createBook";
    }

    public Book getBook() {
        if (book == null) {
            book = new Book();
        }
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        if (author == null) {
            author = new Author();
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        if (genre == null) {
            genre = new Genre();
        }
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * This get the format.
     *
     * @return
     */
    public Format getFormat() {
        if (format == null) {
            format = new Format();
        }
        return format;
    }

    /**
     * This sets format.
     *
     * @param format
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    public String createBook() {
        try {
            bookJpa.create(book);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_books";
    }

    public String editBook(Book b) throws IOException {
//        if (book == null) {
            book = bookJpa.findBookEntitiesById(b.getBookId());
//        }
//        
//        book = b;
//        FacesContext.getCurrentInstance().getExternalContext().redirect("m_editBook.xhtml");
        return "m_editBook";
    }

    public String bookDetails(Book b) {
        book = bookJpa.findBookEntitiesById(b.getBookId());
//        
      
        return "m_viewBook";
    }

    public String updateBook(Book b) {
        try {

            book.setFormatList(getAllFormatForBook());
            book.setAuthorList(getAllAuthorsForBook());
            book.setReviewList(getAllReviews());
            bookJpa.edit(book);

        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_books";
    }

    public String destroyBook(Book b) {

        try {
            bookJpa.destroy(b.getBookId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "m_books";
    }

    public String cancel() {
        return "m_books";
    }

    public List<Book> getAllBook() {
        return bookJpa.findBookEntities();
    }

    public List<Author> getAllAuthors() {
        return authorJpa.findAuthorEntities();
    }

    public List<Author> getAllAuthorsForBook() {
        return authorJpa.findAuthorEntitiesByBook(book);
    }

    public List<Format> getAllFormat() {
        return formatJpa.findFormatEntities();
    }

    public List<Format> getAllFormatForBook() {
        return formatJpa.findFormatEntitiesByBook(book);
    }

    public List<Genre> getAllGenre() {
        return genreJpa.findGenreEntities();
    }

    public List<Genre> getAllGenreForBook() {
        return genreJpa.findGenreEntitiesByBook(book);
    }

    public List<Review> getAllReviews() {
        return reviewJpa.findReviewByIsbn(book);
    }

    public int getBookCount() {
        return bookJpa.getBookCount();
    }
}
