/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_ads")
@SessionScoped
public class m_adsBackingBean implements Serializable {

    private Ad ad;
    
    private List<Ad> allAd;
    
    @Inject
    AdJpaController adJpa;

    @PostConstruct
    public void init(){
        allAd = adJpa.findAllAds();
    }
    public String preCreateAd() {
        return "m_createAd";
    }

    public Ad getAd() {
        if (ad == null) {
            ad = new Ad();
        }
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public String createAd() {
        try {
            adJpa.create(ad);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAd = adJpa.findAllAds();
        return "m_ads";
    }

    public String editAd(Ad a) {
        ad = adJpa.findAdById(a.getAdId());
        return "m_editAd";
    }

    public String updateAd() {
        try {
            adJpa.edit(ad);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAd = adJpa.findAllAds();
        return "m_ads";
    }

    public String destroyAd(Ad a) {
        try {
            adJpa.destroy(a.getAdId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAd = adJpa.findAllAds();
        return "m_ads";
    }

    public String cancel() {
        return "m_ads";
    }

    public List<Ad> getAllAds() {
        return allAd;
    }

    public int getAdCount() {
        return adJpa.getAdCount();
    }
 
}
