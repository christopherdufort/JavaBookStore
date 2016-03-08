/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.beans.AuthenticatedUser;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class AuthenticationController implements SystemEventListener, Serializable{
    
    @Inject
    UserController userController;
    
    @Inject
    AuthenticatedUser authenticatedUser;
    
    public void mustBeClient(ComponentSystemEvent event) throws IOException{
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticationController.isClient invoked !");
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticatedUser value is = {0}", (authenticatedUser.getRegisteredUser()==null));
        if ( authenticatedUser.getRegisteredUser()==null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/login.xhtml");
        }
    }
    
    public void logout(ComponentSystemEvent event) throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/home.xhtml");
    }
    
    public boolean isClient(){
        if (authenticatedUser.getRegisteredUser() == null){
            return false;
        }
        return userController.isClient(authenticatedUser.getRegisteredUser());
    }

    @Override
    public void processEvent(SystemEvent se) throws AbortProcessingException {
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "processEvent has been called");
    }

    @Override
    public boolean isListenerForSource(Object o) {
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "isListenerForSource has been called -> always true");
        return true;
    }
}
