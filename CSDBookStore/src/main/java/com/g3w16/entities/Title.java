package com.g3w16.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Title Entity of a User.
 * 
 * @author Giuseppe Campanelli
 */
@Entity
@Table(name = "title", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Title.findAll", query = "SELECT object(t) FROM Title t"),
    @NamedQuery(name = "Title.findByTitle", query = "SELECT object(t) FROM Title t WHERE t.title = :title"),})
public class Title implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "title_id")
    private Integer titleId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "titleId")
    private List<RegisteredUser> registeredUserList;

    /**
     * Default constructor.
     */
    public Title() {
    }

    /**
     * One parameter constructor.
     * 
     * @param titleId Id of the title
     */
    public Title(Integer titleId) {
        this.titleId = titleId;
    }

    /**
     * Two parameter constructor.
     * 
     * @param titleId Id of the title
     * @param title Title of the title entity
     */
    public Title(Integer titleId, String title) {
        this.titleId = titleId;
        this.title = title;
    }

    /**
     * Gets the id of the title.
     * 
     * @return title id
     */
    public Integer getTitleId() {
        return titleId;
    }

    /**
     * Sets the id of a title entity.
     * 
     * @param titleId Id of a title
     */
    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    /**
     * Gets the title of the entity.
     * 
     * @return title of the entity
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the entity.
     * 
     * @param title Title of the entity
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets a list of all users associated with a specific title.
     * 
     * @return list of users
     */
    public List<RegisteredUser> getRegisteredUserList() {
        return registeredUserList;
    }

    /**
     * Sets the list of users associated with a specific title.
     * 
     * @param registeredUserList List of users
     */
    public void setRegisteredUserList(List<RegisteredUser> registeredUserList) {
        this.registeredUserList = registeredUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (titleId != null ? titleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.titleId == null && other.titleId != null) || (this.titleId != null && !this.titleId.equals(other.titleId))) {
            return false;
        }
        return true;
    }
    /**
     * Modified to return just the title name so that it could be used in form.
     * @author Giuseppe Campanelli
     * @author Christopher Dufort
     * @return 
     */
    @Override
    public String toString() {
//        return "com.g3w16.entities.Title[ titleId=" + titleId + " ]";
        return title;
    }
}