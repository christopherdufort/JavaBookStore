/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Province;
import com.g3w16.entities.ProvinceJpaController;
import com.g3w16.entities.RegisteredUserJpaController;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.Title;
import com.g3w16.entities.TitleJpaController;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for profile.xhtml
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class ProfileBackingBean implements Serializable {
    
    @Inject
    private AuthenticatedUser authenticatedUser;
    @Inject
    private RegisteredUserJpaController registeredUserJpaController;
    @Inject
    private TitleJpaController titleJpaController;
    @Inject
    private ProvinceJpaController provinceJpaController;
    
    private List<Title> availableTitles;
    
    private List<Province> availableProvinces;
    
    public ProfileBackingBean(){
        super();
    }
    
    @PostConstruct
    public void init(){
        availableTitles = titleJpaController.findAll();
        availableProvinces = provinceJpaController.findAll();
    }
    
    public AuthenticatedUser getAuthenticatedUser(){
        return authenticatedUser;
    }
    
    public List<Title> getAvailableTitles(){
        return availableTitles;
    }
      
    public List<Province> getAvailableProvinces(){
        return availableProvinces;
    }
    
    public String persistChangesToProfile(AuthenticatedUser authenticatedUser){
        try {
            registeredUserJpaController.edit(authenticatedUser.getRegisteredUser());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ProfileBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR ROLL BACK IN ProfileBackinggBean");
        } catch (Exception ex) {
            Logger.getLogger(ProfileBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR EXCEPTION IN ProfileBackingBean");
        }
        System.out.println("Changes made to profile -> returning home");
        return "home"; // TODO: Change that, it's ugly to use hardcoded filename to redirect !!
    }
}
