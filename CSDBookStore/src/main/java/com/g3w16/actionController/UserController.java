/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.actionController.exception.AlreadyExistingUserException;
import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.actionController.exception.PasswordConfirmationFailedException;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.SignupBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import com.g3w16.entities.viewController.UserSignupView;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class UserController {

    @Inject
    RegisteredUserJpaController registeredUserJpaController;

    public RegisteredUser create(SignupBean signinBean) throws Exception{
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress(signinBean.getEmail());
        registeredUser.setPassword(signinBean.getPassword());
/*
        Not needed, handled by ConfirmPasswordValidator
        
        if (!signinBean.getPassword().equals(signinBean.getConfirm_password())) {
            throw new PasswordConfirmationFailedException();
        }
*/
        try{
            registeredUserJpaController.create(registeredUser);
        }catch(Exception ex){
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
        return registeredUser.getActive();
    }
    
    public boolean isManager(RegisteredUser user){
        RegisteredUser registeredUser;
        try {
            registeredUser = registeredUserJpaController.findUserByEmail(user.getEmailAddress());
        } catch (NoResultException ex) {
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
}
