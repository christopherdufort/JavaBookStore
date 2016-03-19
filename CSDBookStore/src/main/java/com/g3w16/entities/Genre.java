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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Jonas Faure
 */
@Entity
@Table(name = "genre", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Genre.findAll", query = "SELECT g FROM Genre g"),
    @NamedQuery(name = "Genre.findByGenreId", query = "SELECT g FROM Genre g WHERE g.genreId = :genreId"),
    @NamedQuery(name = "Genre.findByGenreName", query = "SELECT g FROM Genre g WHERE g.genreName LIKE :genreName"),
    @NamedQuery(name = "Genre.findByBookId", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE b.bookId = :bookId )"),
    @NamedQuery(name = "Genre.FindByAuthorId", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE EXISTS ( SELECT 1 FROM b.authorList a WHERE a.authorId = :authorId ) )"),
    @NamedQuery(name = "Genre.FindByFormatId", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE EXISTS ( SELECT 1 FROM b.formatList f WHERE f.formatId = :formatId ) )"),
    @NamedQuery(name = "Genre.findAllAvailable", query = "SELECT g FROM Genre g WHERE EXISTS( SELECT 1 FROM g.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Genre.findByGenreIdAvailable", query = "SELECT g FROM Genre g WHERE g.genreId = :genreId AND EXISTS( SELECT 1 FROM g.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Genre.findByGenreNameAvailable", query = "SELECT g FROM Genre g WHERE g.genreName LIKE :genreName AND EXISTS( SELECT 1 FROM g.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Genre.findByBookIdAvailable", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE b.bookId = :bookId AND b.available = 1)"),
    @NamedQuery(name = "Genre.FindByAuthorIdAvailable", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE b.available = 1 AND EXISTS ( SELECT 1 FROM b.authorList a WHERE a.authorId = :authorId ) )"),
    @NamedQuery(name = "Genre.FindByFormatIdAvailable", query = "SELECT g FROM Genre g WHERE EXISTS ( SELECT 1 FROM g.bookList b WHERE b.available = 1 AND EXISTS ( SELECT 1 FROM b.formatList f WHERE f.formatId = :formatId ) )")
})
public class Genre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "genre_id")
    private Integer genreId;
    @Basic(optional = false)
    @Column(name = "genre_name")
    private String genreName;
    @ManyToMany(mappedBy = "genreList")
    private List<Book> bookList;

    public Genre() {
    }

    public Genre(Integer genreId) {
        this.genreId = genreId;
    }

    public Genre(Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
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
        hash += (genreId != null ? genreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genre)) {
            return false;
        }
        Genre other = (Genre) object;
        if ((this.genreId == null && other.genreId != null) || (this.genreId != null && !this.genreId.equals(other.genreId))) {
            return false;
        }
        return true;
    }
    
    /**
     * Modified to return the genre's name to be used on the webpage.
     * @author Jonas Faure
     * @author Christopher Dufort
     * @return 
     */
    @Override
    public String toString() {
        //return "com.g3w16.entities.Genre[ genreId=" + genreId + " ]";
        return genreName;
    }
    
}
