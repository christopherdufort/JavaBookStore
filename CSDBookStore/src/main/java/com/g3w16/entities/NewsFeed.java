/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "news_feed", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "NewsFeed.findAll", query = "SELECT n FROM NewsFeed n"),
    @NamedQuery(name = "NewsFeed.findByNewsFeedId", query = "SELECT n FROM NewsFeed n WHERE n.newsFeedId = :newsFeedId"),
    @NamedQuery(name = "NewsFeed.findByNewsFeedLink", query = "SELECT n FROM NewsFeed n WHERE n.newsFeedLink = :newsFeedLink")})
public class NewsFeed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "news_feed_id")
    private Integer newsFeedId;
    @Basic(optional = false)
    @Column(name = "news_feed_link")
    private String newsFeedLink;

    public NewsFeed() {
    }

    public NewsFeed(Integer newsFeedId) {
        this.newsFeedId = newsFeedId;
    }

    public NewsFeed(Integer newsFeedId, String newsFeedLink) {
        this.newsFeedId = newsFeedId;
        this.newsFeedLink = newsFeedLink;
    }

    public Integer getNewsFeedId() {
        return newsFeedId;
    }

    public void setNewsFeedId(Integer newsFeedId) {
        this.newsFeedId = newsFeedId;
    }

    public String getNewsFeedLink() {
        return newsFeedLink;
    }

    public void setNewsFeedLink(String newsFeedLink) {
        this.newsFeedLink = newsFeedLink;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newsFeedId != null ? newsFeedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NewsFeed)) {
            return false;
        }
        NewsFeed other = (NewsFeed) object;
        if ((this.newsFeedId == null && other.newsFeedId != null) || (this.newsFeedId != null && !this.newsFeedId.equals(other.newsFeedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.NewsFeed[ newsFeedId=" + newsFeedId + " ]";
    }
    
}
