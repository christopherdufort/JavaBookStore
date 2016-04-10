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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This bean is used to store and retrieve the current locale of the session.
 * Current language is retrieved from the cookie and applied to the session.
 * 
 * @author Christopher Dufort
 */
@Named
@SessionScoped
public class LocalizationBean implements Serializable{
    
    private Locale currentLocale;
    
    @Inject
    InternationalizationController internationalizationController;
    
    /**
     * Constructor
     */
    public LocalizationBean(){
        super();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "new LocalizationBean created");
    }
    
    /**
     * Getter
     * @return 
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * setter
     * @param currentLocale 
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale= currentLocale;
    }
    
    /**
     * Swapper
     * @return 
     */
    public String getCurrentLanguage() {
        if (currentLocale==null){
            currentLocale = new Locale("EN");
        }
        return currentLocale.getLanguage();
    }

    /**
     * Store the current language in the locale cookie.
     * @param language 
     */
    public void setCurrentLanguage(String language){
        currentLocale = new Locale(language);
        internationalizationController.setCookie("LocaleCookie", language, 604800); // cookie last 1 week
        FacesContext.getCurrentInstance().getViewRoot().setLocale(currentLocale);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Current Language changed to " + language);
    }
    
    /**
     * Testing method...
     */
    public void displayLang() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "User Selected " + currentLocale.getLanguage());
    }
    
    
    
}
