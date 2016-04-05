/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 * This class is adsBackingBean that manage all the ad pages
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_ads")
@RequestScoped
public class m_adsBackingBean implements Serializable {

    private Ad ad;

    private List<Ad> allAd;

    private UploadedFile file;
    @Inject
    AdJpaController adJpa;

    @PostConstruct
    public void init() {
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    public void active(Ad a) {
        ad = adJpa.findAdById(a.getAdId());
        ad.setActive(a.getActive());
        try {
            adJpa.edit(ad);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroyAd(Ad a) {
        try {
            adJpa.destroy(a.getAdId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_adsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAd = adJpa.findAllAds();
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

    /**
     * This method will upload an image for ad
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void upload() throws FileNotFoundException, IOException {
        String filename = ad.getAdFilename();
        InputStream input = file.getInputstream();
        String extension = FilenameUtils.getExtension(file.getFileName());

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("./../resources/images");
        OutputStream output = new FileOutputStream(new File(path, filename + "." + extension));

        try {
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
