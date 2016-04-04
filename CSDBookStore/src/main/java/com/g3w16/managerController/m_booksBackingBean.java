/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_books")
@RequestScoped
public class m_booksBackingBean implements Serializable {

    private Book book;
    private Author author;
    private Format format;
    private Genre genre;
    private List<Book> all;
    private UploadedFile file;

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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String createBook() {
        Date d = new Date(System.currentTimeMillis());
        try {
            book.setOverallRating(BigDecimal.ZERO);
            book.setDateEntered(d);
            List<Review> reviewList = new ArrayList<>();
            book.setReviewList(reviewList);
            bookJpa.create(book);
            upload();
        } catch (Exception ex) {
            Logger.getLogger(m_booksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    public String editBook(Book b) {
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
    
    public void upload() throws FileNotFoundException, IOException{
        String filename = "999";      
        System.out.println(">>>>>>>>>>>"+filename);
        if(file==null){
            System.out.println("************null");
        }
        InputStream input = file.getInputstream();
        String extension = FilenameUtils.getExtension(file.getFileName());
        
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("./../resources/images");
        OutputStream output = new FileOutputStream(new File(path, filename + "." + extension));
  
        try {
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

}
