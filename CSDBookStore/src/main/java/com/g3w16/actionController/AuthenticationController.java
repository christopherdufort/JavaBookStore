package com.g3w16.actionController;

import com.g3w16.beans.AuthBean;
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
 * @author Christopher Dufort
 * This Class is used during pre renders of various pages, if a user is not logged in
 * or if a user is not a manager they are redirected to a login page.
 * This class also contains methods for logging out a user.
 */
@Named
@RequestScoped
public class AuthenticationController implements SystemEventListener, Serializable{
    
    @Inject
    UserController userController;
    
    @Inject
    AuthenticatedUser authenticatedUser;
    
    @Inject
    AuthBean authBean;
    
    @Inject
    NavigationController navigationController;
    
    /**
     * created for testing
     */
    public AuthenticationController() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "AuthenticationController constructor is invoked!");
    }
    
    /**
     * @author Jonas Faure
     * @edited Christopher Dufort
     * 
     * This method is used to check if a user is logged in if not redirects to a login page.
     * This method checks the authenticated user bean if the current session has a logged in user.
     * Modified to use boomerang login, so any page you came from when attempting to log in you will return to.
     * 
     * @param event
     * @throws IOException 
     */
    public void mustBeClient(ComponentSystemEvent event) throws IOException{
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticationController.isClient invoked !");
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticatedUser.getRegisteredUser value is null ? = {0}", (authenticatedUser.getRegisteredUser()==null));
        if ( authenticatedUser.getRegisteredUser()==null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("/CSDBookStore/faces"+navigationController.boomerangLogin());
            //FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }else{
            if (!userController.isClient(authenticatedUser.getRegisteredUser())){
                FacesContext.getCurrentInstance().getExternalContext().redirect("/CSDBookStore/faces"+navigationController.boomerangLogin());
                //FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            }
        }
    }
    
    /**
     * @author Jonas Faure
     * @edited Christopher Dufort
     * 
     * This method checks the authenticated user bean to see if the current session contains a manager account.
     * If not you are redirected to a login page.
     * 
     * @param event
     * @throws IOException 
     */
    public void mustBeManager(ComponentSystemEvent event) throws IOException{
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticationController.isClient invoked !");
        Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "AuthenticatedUser value is = {0}", (authenticatedUser.getRegisteredUser()==null));
        if ( authenticatedUser.getRegisteredUser()==null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("/CSDBookStore/faces"+navigationController.boomerangLogin());
            //FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }else{
            if (!userController.isManager(authenticatedUser.getRegisteredUser())){
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            }
        }
    }
    
    public void mustBeAnonymous(ComponentSystemEvent event) throws IOException{
        if (authenticatedUser.getRegisteredUser()!=null){
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/CSDBookStore/faces"+navigationController.boomerangLogin());
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        }
    }
    
    /**
     * @author Jonas Faure
     * @edited Christopher Dufort
     * 
     * This logout method is used to nullify the logged in user as well as reset all the authentication bean fields.
     * A user who is logged out is simply nulled in various session locations.
     * 
     * @param event
     * @throws IOException 
     */
    public void logout(ComponentSystemEvent event) throws IOException{
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        authenticatedUser.setRegisteredUser(null);
        authBean.setBackurl(null);
        authBean.setEmail(null);
        authBean.setPassword(null);  
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
    }
    
    public boolean isClient(){
        if (authenticatedUser.getRegisteredUser() == null){
            return false;
        }
        return userController.isClient(authenticatedUser.getRegisteredUser());
    }
    
    public boolean isManager(){
        if (authenticatedUser.getRegisteredUser() == null){
            return false;
        }
        return userController.isManager(authenticatedUser.getRegisteredUser());
    }
    
    public boolean isAnonymous(){
        return authenticatedUser.getRegisteredUser() == null;
    }


    @Override
    public void processEvent(SystemEvent se) throws AbortProcessingException {
        //Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "processEvent has been called");
    }

    @Override
    public boolean isListenerForSource(Object o) {
        //Logger.getLogger(AuthenticationController.class.getName()).log(Level.INFO, "isListenerForSource has been called -> always true");
        return true;
    }
}
