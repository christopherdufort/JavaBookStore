/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.SigninBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class UserController {
    
    @Inject
    RegisteredUserJpaController registeredUserJpaController;
    
    public RegisteredUser create(SigninBean signinBean) throws Exception{
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress(signinBean.getEmail());
        registeredUser.setPassword(signinBean.getPassword());
        registeredUserJpaController.create(registeredUser);
        return registeredUser;
    }
    
    public boolean isClient(RegisteredUser user){
        try{
            registeredUserJpaController.findUserByEmail(user.getEmailAddress());
        }catch(NoResultException ex){
            return false;
        }
        return true;
    }
    
    public void logout() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("email");
        FacesContext.getCurrentInstance().getExternalContext().redirect("home");
    }
    
    public RegisteredUser authenticate(AuthBean authBean) throws InvalidCredentialsException{
        RegisteredUser registeredUser;
        try{
            registeredUser = registeredUserJpaController.findUserByEmail(authBean.getEmail());
        }catch(NoResultException ex){
            throw new InvalidCredentialsException();
        }
        Logger.getLogger(UserController.class.getName()).log(Level.INFO, "Real user password : {0}", registeredUser.getPassword());
        if (!registeredUser.getPassword().equals(authBean.getPassword())){
            throw new InvalidCredentialsException();
        }
        return registeredUser;
    }
}
