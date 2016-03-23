/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.actionController.exception.AlreadyExistingUserException;
import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.AuthenticatedUser;
import com.g3w16.beans.ProfileBackingBean;
import com.g3w16.beans.SignupBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 * This class is the middle man between the DAO Classes(JPA Controllers) and the
 * View (ActionsBeans/Backing Beans). Primarily it is responsible as a layer of
 * abstraction between different parts of the project. Any methods that interact
 * with the RegisteredUserJPAController go here.
 *
 * @author jesuisnuageux
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class UserController implements Serializable {

    @Inject
    private RegisteredUserJpaController registeredUserJpaController;
    
    @Inject
    private AuthenticatedUser authenticatedUser;
    
    @Inject
    private ProfileBackingBean profileBackingBean;
    

    public RegisteredUser create(SignupBean signinBean) throws Exception {
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress(signinBean.getEmail());
        registeredUser.setPassword(signinBean.getPassword());
        registeredUser.setActive(Boolean.TRUE);
        registeredUser.setManager(Boolean.FALSE);
        /*
        Not needed, handled by ConfirmPasswordValidator
        
        if (!signinBean.getPassword().equals(signinBean.getConfirm_password())) {
            throw new PasswordConfirmationFailedException();
        }
         */
        try {
            registeredUserJpaController.create(registeredUser);
        } catch (Exception ex) {
            throw new AlreadyExistingUserException();
        }
        return registeredUser;
    }

    public boolean isClient(RegisteredUser user) {
        RegisteredUser registeredUser;
        try {
            registeredUser = registeredUserJpaController.findUserByEmail(user.getEmailAddress());
        } catch (NoResultException ex) {
            return false;
        }
        if (registeredUser == null) {
            // that should never happend, but for reason, it does happend
            return false;
        }
        return registeredUser.getActive();
    }

    public boolean isManager(RegisteredUser user) {
        RegisteredUser registeredUser;
        try {
            registeredUser = registeredUserJpaController.findUserByEmail(user.getEmailAddress());
        } catch (NoResultException ex) {
            return false;
        }
        if (registeredUser == null) {
            // that should never happend, but for reason, it does happend
            return false;
        }
        return registeredUser.getManager() && registeredUser.getActive();
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("email");
        FacesContext.getCurrentInstance().getExternalContext().redirect("home");
    }

    public RegisteredUser authenticate(AuthBean authBean) throws InvalidCredentialsException {
        RegisteredUser registeredUser;
        try {
            registeredUser = registeredUserJpaController.findUserByEmail(authBean.getEmail());
        } catch (NoResultException ex) {
            throw new InvalidCredentialsException();
        }
        Logger.getLogger(UserController.class.getName()).log(Level.INFO, "Real user password : {0}", registeredUser.getPassword());
        if (!registeredUser.getPassword().equals(authBean.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return registeredUser;
    }
    
    public void editProfile() throws Exception{
        
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "EditProfile in user controller is invoked!");
        
        //Update the fields of the currently logged in user and then persist to the db.
        RegisteredUser userToUpdate = authenticatedUser.getRegisteredUser();
        userToUpdate.setEmailAddress(profileBackingBean.getEmailAddress());
        userToUpdate.setPassword(profileBackingBean.getPassword());
        userToUpdate.setFirstName(profileBackingBean.getFirstName());
        userToUpdate.setLastName(profileBackingBean.getLastName());
        userToUpdate.setCompanyName(profileBackingBean.getCompanyName());
        userToUpdate.setAddressOne(profileBackingBean.getAddressOne());
        userToUpdate.setAddressTwo(profileBackingBean.getAddressTwo());
        userToUpdate.setCity(profileBackingBean.getCity());
        userToUpdate.setCountry(profileBackingBean.getCountry());
        userToUpdate.setPostalCode(profileBackingBean.getPostalCode());
        userToUpdate.setHomePhone(profileBackingBean.getHomePhone());
        userToUpdate.setCellPhone(profileBackingBean.getCellPhone());
        userToUpdate.setTitleId(profileBackingBean.getTitleId());
        userToUpdate.setProvinceId(profileBackingBean.getProvinceId());
        
        try {
            registeredUserJpaController.edit(userToUpdate);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
