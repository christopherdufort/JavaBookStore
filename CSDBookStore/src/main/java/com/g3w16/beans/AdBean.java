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
public class AdBean {
    private int adId;
    private String adLink;

    public AdBean() {
    }

    public AdBean(final int adId, final String adLink) {
        this.adId = adId;
        this.adLink = adLink;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(final int adId) {
        this.adId = adId;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(final String adLink) {
        this.adLink = adLink;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.adId;
        hash = 67 * hash + Objects.hashCode(this.adLink);
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
        final AdBean other = (AdBean) obj;
        if (this.adId != other.adId) {
            return false;
        }
        if (!Objects.equals(this.adLink, other.adLink)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AdBean{" + "adId=" + adId + ", adLink=" + adLink + '}';
    }
    
    
}
