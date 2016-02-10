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
 * @author 1040570
 */
@Entity
@Table(name = "format", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Format.findAll", query = "SELECT f FROM Format f"),
    @NamedQuery(name = "Format.findByFormatId", query = "SELECT f FROM Format f WHERE f.formatId = :formatId"),
    @NamedQuery(name = "Format.findByExtension", query = "SELECT f FROM Format f WHERE f.extension = :extension")})
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

    @Override
    public String toString() {
        return "com.g3w16.entities.Format[ formatId=" + formatId + " ]";
    }
    
}
