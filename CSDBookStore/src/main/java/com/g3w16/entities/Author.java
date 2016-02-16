    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Jonas Faure
 */
@Entity
@Table(name = "author", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
    @NamedQuery(name = "Author.findByAuthorId", query = "SELECT a FROM Author a WHERE a.authorId = :authorId"),
    @NamedQuery(name = "Author.findByAuthorName", query = "SELECT a FROM Author a WHERE a.authorName LIKE :authorName"),
    @NamedQuery(name = "Author.findByBookId", query = "SELECT a FROM Author a WHERE EXISTS(SELECT 1 FROM a.bookList b WHERE b.bookId = :bookId)"),
    @NamedQuery(name = "Author.findByGenreId", query = "SELECT a FROM Author a JOIN a.bookList b WHERE EXISTS(SELECT 1 FROM b.genreList g WHERE g.genreId = :genreId)"),
    @NamedQuery(name = "Author.findByFormatId", query = "SELECT a FROM Author a WHERE EXISTS(SELECT 1 FROM a.bookList b WHERE EXISTS(SELECT 1 FROM b.formatList f WHERE f.formatId = :formatId) )"),
    @NamedQuery(name = "Author.findAllAvailable", query = "SELECT a FROM Author a WHERE EXISTS(SELECT 1 FROM a.bookList b WHERE b.available = 1)"),
    @NamedQuery(name = "Author.findByAuthorIdAvailable", query = "SELECT a FROM Author a WHERE a.authorId = :authorId AND EXISTS (SELECT 1 FROM a.bookList b WHERE b.available = 1)"),
    @NamedQuery(name = "Author.findByAuthorNameAvailable", query = "SELECT a FROM Author a WHERE a.authorName LIKE :authorName AND EXISTS ( SELECT 1 FROM a.bookList b WHERE b.available = 1)"),
    @NamedQuery(name = "Author.findByBookIdAvailable", query = "SELECT a FROM Author a WHERE EXISTS(SELECT 1 FROM a.bookList b WHERE b.bookId = :bookId and b.available = 1 )"),
    @NamedQuery(name = "Author.findByGenreIdAvailable", query = "SELECT a FROM Author a JOIN a.bookList b WHERE EXISTS(SELECT 1 FROM b.genreList g WHERE g.genreId = :genreId) AND b.available = 1"),
    @NamedQuery(name = "Author.findByFormatIdAvailable", query = "SELECT a FROM Author a WHERE EXISTS(SELECT 1 FROM a.bookList b WHERE b.available = 1 AND EXISTS(SELECT 1 FROM b.formatList f WHERE f.formatId = :formatId) )")    
})
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "author_id")
    private Integer authorId;
    
    @Basic(optional = false)
    @Column(name = "author_name")
    private String authorName;
    
    @JoinTable(name = "book_author", joinColumns = {
        @JoinColumn(name = "author_id", referencedColumnName = "author_id")}, inverseJoinColumns = {
        @JoinColumn(name = "book_id", referencedColumnName = "book_id")})
    @ManyToMany
    private List<Book> bookList;

    public Author() {
    }

    public Author(Integer authorId) {
        this.authorId = authorId;
    }

    public Author(Integer authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorId != null ? authorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.authorId == null && other.authorId != null) || (this.authorId != null && !this.authorId.equals(other.authorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Author[ authorId=" + authorId + " ]";
    }
    
}
