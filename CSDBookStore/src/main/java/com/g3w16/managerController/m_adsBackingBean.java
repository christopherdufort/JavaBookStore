/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_ads")
@RequestScoped
public class m_adsBackingBean {

    @Inject
    Ad ad;

    @Inject
    AdJpaController adJpa;

    public String preCreateAd() {
        return "m_createAd";
    }

    public Ad getSelectedAd() {
        return ad;
    }

    public String createAd() {
        try {
            adJpa.create(ad);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return "m_ads";
    }

    public String destroyAd(Ad a) {
        Ad current = adJpa.findAdById(a.getAdId());
        if (current != null) {
            try {
                adJpa.destroy(a.getAdId());
            } catch (RollbackFailureException ex) {
                Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "m_ads";
    }

    public List<Ad> getAllAds() {
        return adJpa.findAllAds();
    }
}
