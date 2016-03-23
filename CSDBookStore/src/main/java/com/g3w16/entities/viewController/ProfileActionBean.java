/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.beans.ProfileBackingBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Action Bean
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class ProfileActionBean implements Serializable{
    @Inject
    private UserController userController;

    @Inject
    private ProfileBackingBean profileBackingBean;
    
    public ProfileActionBean(){
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "ProfileActionBean was constructed via CDI");
    }

    public String editProfile() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "EditProfile in profile action bean is invoked!");
        try {
            userController.editProfile();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage(ex.toString()));
            return null; //Go No where
        }
        return "home"; // TODO: Remove that drity hardcoded string at one point !
    }
}
