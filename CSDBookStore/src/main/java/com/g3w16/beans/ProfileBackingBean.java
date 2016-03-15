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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private RegisteredUserJpaController registeredUserJpaController;
    @Inject
    private TitleJpaController titleJpaController;
    @Inject
    private ProvinceJpaController provinceJpaController;
    
    private RegisteredUser registeredUser;
    
    private String selectedTitle;
    private List<Title> availableTitles;
    
    private String selectedProvince;
    private List<Province> availableProvinces;
    
    public ProfileBackingBean(){
        super();
    }
    
    @PostConstruct
    public void init(){
        availableTitles = titleJpaController.findAll();
        availableProvinces = provinceJpaController.findAll();
    }
    public RegisteredUser getRegisteredUser(){
        if (registeredUser == null){
            registeredUser = new RegisteredUser();
        }
        return registeredUser;
    }
    public String getSelectedTitle(){
        return selectedTitle;
    }
    
    public List<Title> getAvailableTitles(){
        return availableTitles;
    }
    
    public String getSelectedProvince(){
        return selectedProvince;
    }
    
    public List<Province> getAvailableProvinces(){
        return availableProvinces;
    }
}
