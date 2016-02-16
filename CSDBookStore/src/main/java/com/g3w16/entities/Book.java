/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jonas Faure
 */
@Entity
@Table(name = "book", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findByBookId", query = "SELECT b FROM Book b WHERE b.bookId = :bookId"),
    @NamedQuery(name = "Book.findByIsbn", query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title LIKE :title"),
    @NamedQuery(name = "Book.findByPublisher", query = "SELECT b FROM Book b WHERE b.publisher LIKE :publisher"),
    @NamedQuery(name = "Book.findByFormat", query = "SELECT b FROM Book b WHERE EXISTS( SELECT 1 FROM b.formatList f WHERE f.formatId = :formatId )"),
    @NamedQuery(name = "Book.findByGenre", query = "SELECT b FROM Book b WHERE EXISTS( SELECT 1 FROM b.genreList g WHERE g.genreId = :genreId )"),
    @NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM Book b WHERE EXISTS( SELECT 1 FROM b.authorList a WHERE a.authorId = :authorId )"),
    // All function before this should be re-implemented with order ( price, date pub, date enter, rating, pages ) 
    //      + with some care about availability ( in case of client's functions vs manager's functions )
    
    //@NamedQuery(name = "Book.findByPublishDate", query = "SELECT b FROM Book b WHERE b.publishDate = :publishDate"),
    //@NamedQuery(name = "Book.findByPageNumber", query = "SELECT b FROM Book b WHERE b.pageNumber = :pageNumber"),
    //@NamedQuery(name = "Book.findByWholesalePrice", query = "SELECT b FROM Book b WHERE b.wholesalePrice = :wholesalePrice"),
    //@NamedQuery(name = "Book.findByListPrice", query = "SELECT b FROM Book b WHERE b.listPrice = :listPrice"),
    //@NamedQuery(name = "Book.findBySalePrice", query = "SELECT b FROM Book b WHERE b.salePrice = :salePrice"),
    //@NamedQuery(name = "Book.findByDateEntered", query = "SELECT b FROM Book b WHERE b.dateEntered = :dateEntered"),
    //@NamedQuery(name = "Book.findByAvailable", query = "SELECT b FROM Book b WHERE b.available = :available"),
    //@NamedQuery(name = "Book.findByOverallRating", query = "SELECT b FROM Book b WHERE b.overallRating = :overallRating"),
})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "book_id")
    private Integer bookId;
    @Basic(optional = false)
    @Column(name = "isbn")
    private String isbn;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "publisher")
    private String publisher;
    @Basic(optional = false)
    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
    @Basic(optional = false)
    @Column(name = "page_number")
    private int pageNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "wholesale_price")
    private BigDecimal wholesalePrice;
    @Column(name = "list_price")
    private BigDecimal listPrice;
    @Column(name = "sale_price")
    private BigDecimal salePrice;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Basic(optional = false)
    @Column(name = "available")
    private boolean available;
    @Basic(optional = false)
    @Column(name = "overall_rating")
    private BigDecimal overallRating;
    @Lob
    @Column(name = "synopsis")
    private String synopsis;
    @ManyToMany(mappedBy = "bookList")
    private List<Author> authorList;
    @JoinTable(name = "book_format", joinColumns = {
        @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "format_id", referencedColumnName = "format_id")
    })
    @ManyToMany
    private List<Format> formatList;
    @JoinTable(name = "book_genre", joinColumns = {
        @JoinColumn(name = "book_id", referencedColumnName = "book_id")}, inverseJoinColumns = {
        @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")})
    @ManyToMany
    private List<Genre> genreList;
    @OneToMany(mappedBy = "isbn")
    private List<Review> reviewList;

    public Book() {
    }

    public Book(Integer bookId) {
        this.bookId = bookId;
    }

    public Book(Integer bookId, String isbn, String title, String publisher, Date publishDate, int pageNumber, BigDecimal wholesalePrice, boolean available, BigDecimal overallRating) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageNumber = pageNumber;
        this.wholesalePrice = wholesalePrice;
        this.available = available;
        this.overallRating = overallRating;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public BigDecimal getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(BigDecimal overallRating) {
        this.overallRating = overallRating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

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

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Book[ bookId=" + bookId + " ]";
    }
    
}
