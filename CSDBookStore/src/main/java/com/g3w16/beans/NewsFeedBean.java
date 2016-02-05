/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.util.Objects;

/**
 *
 * @author Christopher Dufort
 * @version 0.1.0 - Last modified 2/4/2016
 * @since 0.1.0 - Originally Written 2/4/2016
 */
public class NewsFeedBean {

    private int newsFeedId;
    private String newsFeedLink;

    public NewsFeedBean() {
    }

    public NewsFeedBean(final int newsFeedId, final String linkAddress) {
        this.newsFeedId = newsFeedId;
        this.newsFeedLink = linkAddress;
    }

    public int getNewsFeedId() {
        return newsFeedId;
    }

    public void setNewsFeedId(final int newsFeedId) {
        this.newsFeedId = newsFeedId;
    }

    public String getNewsFeedLink() {
        return newsFeedLink;
    }

    public void setNewsFeedLink(final String newsFeedLink) {
        this.newsFeedLink = newsFeedLink;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.newsFeedId;
        hash = 73 * hash + Objects.hashCode(this.newsFeedLink);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NewsFeedBean other = (NewsFeedBean) obj;
        if (this.newsFeedId != other.newsFeedId) {
            return false;
        }
        if (!Objects.equals(this.newsFeedLink, other.newsFeedLink)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NewsFeedBean{" + "newsFeedId=" + newsFeedId + ", linkAddress=" + newsFeedLink + '}';
    }
}
