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
@Table(name = "format", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Format.findAll", query = "SELECT f FROM Format f"),
    @NamedQuery(name = "Format.findByFormatId", query = "SELECT f FROM Format f WHERE f.formatId = :formatId"),
    @NamedQuery(name = "Format.findByExtension", query = "SELECT f FROM Format f WHERE f.extension LIKE :extension"),
    @NamedQuery(name = "Format.findByBookId", query = "SELECT f FROM Format f WHERE EXISTS( SELECT 1 FROM f.bookList b WHERE b.bookId = :bookId )"),
    @NamedQuery(name = "Format.findByGenreId", query = "SELECT f FROM Format f WHERE EXISTS( SELECT 1 FROM f.bookList b WHERE EXISTS ( SELECT 1 FROM b.genreList g WHERE g.genreId = :genreId ) )"),
    @NamedQuery(name = "Format.findByAuthorId", query = "SELECT f FROM Format f WHERE EXISTS ( SELECT 1 FROM f.bookList b WHERE EXISTS ( SELECT 1 FROM b.authorList a WHERE a.authorId = :authorId ) )"),
    @NamedQuery(name = "Format.findAllAvailable", query = "SELECT f FROM Format f WHERE EXISTS( SELECT 1 FROM f.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Format.findByFormatIdAvailable", query = "SELECT f FROM Format f WHERE f.formatId = :formatId AND EXISTS( SELECT 1 FROM f.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Format.findByExtensionAvailable", query = "SELECT f FROM Format f WHERE f.extension LIKE :extension AND EXISTS( SELECT 1 FROM f.bookList b WHERE b.available = 1 )"),
    @NamedQuery(name = "Format.findByBookIdAvailable", query = "SELECT f FROM Format f WHERE EXISTS( SELECT 1 FROM f.bookList b WHERE b.bookId = :bookId AND b.available = 1 )"),
    @NamedQuery(name = "Format.findByGenreIdAvailable", query = "SELECT f FROM Format f WHERE EXISTS( SELECT 1 FROM f.bookList b WHERE b.available = 1 AND EXISTS( SELECT 1 FROM b.genreList g WHERE g.genreId = :genreId ) )"),
    @NamedQuery(name = "Format.findByAuthorIdAvailable", query = "SELECT f FROM Format f WHERE EXISTS ( SELECT 1 FROM f.bookList b WHERE b.available = 1 AND EXISTS ( SELECT 1 FROM b.authorList a WHERE a.authorId = :authorId ) )")
})
public class Format implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "format_id")
    private Integer formatId;
    @Basic(optional = false)
    @Column(name = "extension")
    private String extension;
    @ManyToMany(mappedBy = "formatList")
    private List<Book> bookList;

    public Format() {
    }

    public Format(Integer formatId) {
        this.formatId = formatId;
    }

    public Format(Integer formatId, String extension) {
        this.formatId = formatId;
        this.extension = extension;
    }

    public Integer getFormatId() {
        return formatId;
    }

    public void setFormatId(Integer formatId) {
        this.formatId = formatId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
        hash += (formatId != null ? formatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Format)) {
            return false;
        }
        Format other = (Format) object;
        if ((this.formatId == null && other.formatId != null) || (this.formatId != null && !this.formatId.equals(other.formatId))) {
            return false;
        }
        return true;
    }
    /**
     * Edit by Xin Ma 
     * @return 
     */
    @Override
    public String toString() {
        return extension;
    }
    
}
