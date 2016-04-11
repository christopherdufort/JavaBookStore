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
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * This class is Bookbackingbean for all the admin book page
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_books")
@RequestScoped
public class MbooksBackingBean implements Serializable {

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

    /**
     * This method will create a new book
     *
     * @return
     */
    public String createBook() {
        Logger.getLogger(MbooksBackingBean.class.getName()).log(Level.SEVERE, "I'm trying to create a book !");
        Date d = new Date(System.currentTimeMillis());
        try {
            book.setOverallRating(BigDecimal.ZERO);
            book.setDateEntered(d);
            List<Review> reviewList = new ArrayList<>();
            book.setReviewList(reviewList);
            bookJpa.create(book);
            //upload();
        } catch (Exception ex) {
            Logger.getLogger(MbooksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    /**
     * This method will redirect to edit page
     *
     * @param b
     * @return
     */
    public String editBook(Book b) {
        book = bookJpa.findBookEntitiesById(b.getBookId());
        return "m_editBook";
    }

    /**
     * This method will redirect to detail page
     *
     * @param b
     * @return
     */
    public String bookDetails(Book b) {
        book = bookJpa.findBookEntitiesById(b.getBookId());
        return "m_viewBook";
    }

    /**
     * This method will edit a book
     *
     * @return
     */
    public String updateBook() {
        try {
            book.setReviewList(reviewJpa.findReviewByIsbn(book));
            bookJpa.edit(book);

        } catch (Exception ex) {
            Logger.getLogger(MbooksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
        return "m_books";
    }

    /**
     * This method will delete a book
     *
     * @param b
     */
    public void destroyBook(Book b) {
        try {
            bookJpa.destroy(b.getBookId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MbooksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MbooksBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        all = bookJpa.findBookEntities();
    }

    public String cancel() {
        return "m_books";
    }

    public List<Book> getAllBook() {
        return all;
    }

    /**
     * This method get all Author entities
     *
     * @return
     */
    public List<Author> getAllAuthors() {
        return authorJpa.findAuthorEntities();
    }

    /**
     * This method will get all Format entities
     *
     * @return
     */
    public List<Format> getAllFormat() {
        return formatJpa.findFormatEntities();
    }

    /**
     * This method will get all the Genre entities
     *
     * @return
     */
    public List<Genre> getAllGenre() {
        return genreJpa.findGenreEntities();
    }

    /**
     * This method will get the total number of book entities
     *
     * @return
     */
    public int getBookCount() {
        return bookJpa.getBookCount();
    }

    /**
     * This method will upload the image for this book
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void upload() throws FileNotFoundException, IOException {
        String filename = book.getIsbn();
        System.out.println(">>>>>>>>>>>" + filename);

        InputStream input = file.getInputstream();
        String extension = FilenameUtils.getExtension(file.getFileName());

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("./../resources/images/");
        OutputStream output = new FileOutputStream(new File(path, filename + "." + extension));

        try {
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

}
