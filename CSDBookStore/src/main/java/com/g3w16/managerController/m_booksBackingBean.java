/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_books")
@SessionScoped
public class m_booksBackingBean implements Serializable {

    private Book book;
    private Author author;
    private Format format;
    private Genre genre;
    private List<Book> all;

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

    @PostConstruct
    public void init() {
        all = bookJpa.findBookEntities();
    }

    public String preCreateBook() {
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

    public Format getFormat() {
        if (format == null) {
            format = new Format();
        }
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String createBook() {
        try {
            bookJpa.create(book);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    public String editBook(Book b) throws IOException {
        book = bookJpa.findBookEntitiesById(b.getBookId());
        return "m_editBook";
    }

    public String bookDetails(Book b) {
        book = bookJpa.findBookEntitiesById(b.getBookId());
        return "m_viewBook";
    }

    public String updateBook() {
        try {
            book.setReviewList(reviewJpa.findReviewByIsbn(book));
            bookJpa.edit(book);

        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    public void destroyBook(Book b) {
        try {
            bookJpa.destroy(b.getBookId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
    }

    public String cancel() {
        return "m_books";
    }

    public List<Book> getAllBook() {
        return all;
    }

    public List<Author> getAllAuthors() {
        return authorJpa.findAuthorEntities();
    }

    public List<Format> getAllFormat() {
        return formatJpa.findFormatEntities();
    }

    public List<Genre> getAllGenre() {
        return genreJpa.findGenreEntities();
    }

    public int getBookCount() {
        return bookJpa.getBookCount();
    }

}
