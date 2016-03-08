/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.beans.AuthBean;
import com.g3w16.entities.RegisteredUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class UserAuthView {

    @Inject
    UserController userController;

    @Inject
    AuthBean authBean;

    public String authenticate(AuthBean authBean) {
        
        Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Submitted email : {0}", authBean.getEmail());
        Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Submitted password : {0}", authBean.getPassword());
        
        RegisteredUser registeredUser;
        try {
            registeredUser = userController.authenticate(authBean);
            // return home will redirect to the home page if authentication is successful
            //   else, we re-render the page with an h:message thing
        }catch (InvalidCredentialsException ex) {
            // TODO: place this hardcoded message in the exception.
            //          It'd look better to me
            FacesContext.getCurrentInstance().addMessage("auth_form", new FacesMessage("Username or password is incorrect"));
            return null;
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", authBean.getEmail());
        return "home"; // TODO: Change that, it's ugly to use hardcoded filename to redirect !!
    }
}
