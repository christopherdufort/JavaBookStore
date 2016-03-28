/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.actionController.InternationalizationController;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class LocalizationBean implements Serializable{
    
    private Locale currentLocale;
    
    @Inject
    InternationalizationController internationalizationController;
    
    public LocalizationBean(){
        super();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "new LocalizationBean created");
    }
    
//    @PostConstruct
//    public void init() {
//        //Change the value based on locale.
//        currentLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
//    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    public String getCurrentLanguage() {
        return currentLocale.getLanguage();
    }

    public void setCurrentLanguage(String language){
        currentLocale = new Locale(language);
        internationalizationController.setCookie("LocaleCookie", language, 604800); // cookie last 1 week
        FacesContext.getCurrentInstance().getViewRoot().setLocale(currentLocale);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Current Language changed to " + language);
    }
    
    public void displayLang() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User Selected " + currentLocale.getLanguage());
    }
    
    
    
}
