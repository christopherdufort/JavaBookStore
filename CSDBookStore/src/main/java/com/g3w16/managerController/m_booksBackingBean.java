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
import java.util.Arrays;
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

    @Inject
    private Book book;
    private Author author;
    private List<Author> authorList;

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Format> getFormatList() {
        return formatList;
    }

    public void setFormatList(List<Format> formatList) {
        this.formatList = formatList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
    private List<Format> formatList;
    private List<Genre> genreList;
  
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
        authorList=authorJpa.findAuthorEntitiesByBook(book);
        //System.out.println(">>>>>>>init");
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
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    public String editBook(Book b) throws IOException {
        //System.out.println("editBook>>>>>>" + b);
       
        
        book = bookJpa.findBookEntitiesById(b.getBookId());
        return "m_editBook";
    }

    public String bookDetails(Book b) {
       // System.out.println("bookDetails>>>>>>" + b);
        book = bookJpa.findBookEntitiesById(b.getBookId());
        //System.out.println("bookDetails>>>>>>book>>>>" + book);
        return "m_viewBook";
    }

    public String updateBook(Book b) {
        try {
            System.out.println(">>>>>>update");
            
            book.setFormatList(getAllFormatForBook());
            //book.setAuthorList(getAllAuthorsForBook());
            book.setGenreList(getAllGenreForBook());
            book.setReviewList(getAllReviews());
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

    public List<Author> getAllAuthorsForBook() {
        System.out.println(">>>>>>>>>>>>>>>book"+authorList.size());
        return authorJpa.findAuthorEntitiesByBook(book);
    }

    public List<Format> getAllFormat() {
        return formatJpa.findFormatEntities();
    }

    public List<Format> getAllFormatForBook() {
       // book = bookJpa.findBookEntitiesById(id);
        return formatJpa.findFormatEntitiesByBook(book);
    }

    public List<Genre> getAllGenre() {
        return genreJpa.findGenreEntities();
    }

    public List<Genre> getAllGenreForBook() {
        //book = bookJpa.findBookEntitiesById(id);
        return genreJpa.findGenreEntitiesByBook(book);
    }

    public List<Review> getAllReviews() {
        return reviewJpa.findReviewByIsbn(book);
    }

    public int getBookCount() {
        return bookJpa.getBookCount();
    }
    
}
