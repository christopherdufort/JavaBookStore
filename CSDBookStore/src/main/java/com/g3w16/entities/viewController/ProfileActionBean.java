/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.beans.ProfileBackingBean;
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class ProfileActionBean {
    @Inject
    UserController userController;

    @Inject
    ProfileBackingBean profileBackingBean;

    public String editProfile(ProfileBackingBean profileBackingBean) {
        try {
            userController.editProfile(profileBackingBean);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage(ex.toString()));
            return null; //Go No where
        }
        return "home"; // TODO: Remove that drity hardcoded string at one point !
    }
}
